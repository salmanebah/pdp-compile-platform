package pdp.compileplatform.execution;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pdp.compileplatform.frontend.Lang;
import pdp.compileplatform.frontend.RequestHandler;
import pdp.compileplatform.session.EnumResult;
import pdp.compileplatform.session.EnumStatus;
import pdp.compileplatform.session.TaskInterface;
import pdp.compileplatform.session.TaskParametersInterface;
import pdp.compileplatform.session.TaskResultsInterface;

/**
 * A concrete implementation of the <tt>TaskExecutorInterface</tt> interface using
 * docker for the sandboxing
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskExecutorInterface
 * @see pdp.compileplatform.frontend.RequestHandler
 * @see TaskQueue
 * @see TaskQueueInterface
 * @see pdp.compileplatform.frontend.Lang
 *
 */

public class TaskExecutor implements TaskExecutorInterface {
	/** The queue containing the tasks */
	private TaskQueueInterface queue;
	/** The docker image to launch the container with */
	private String dockerImg;
	/** The memory limit for the docker container */
	private String  dockerMemLimit;
	/** The docker container execution time */
	private double dockerExecTime;
	/** All languages supported by the docker image */
	private Lang[] languages;
	
	/**
	 * Constructs an executor using docker as sandbox  
	 * @param queue the queue containing the tasks
	 * @param dockerImg the docker image to use
	 * @param dockerExecTime the execution time for containers
	 * @param dockerMemLimit the memory limit for containers
	 */
	public TaskExecutor(TaskQueueInterface queue , String dockerImg , 
			            double dockerExecTime , String dockerMemLimit) 						
	{
		this.queue = queue;
		this.dockerImg = dockerImg;
		this.dockerExecTime = dockerExecTime;
		this.dockerMemLimit = dockerMemLimit;
		languages = new Lang[5];

		languages[0] = new LangInternal(1 , "c" , "gcc " , false , ".c",
				                        "gcc " + LangInternal.opt + " " + LangInternal.src, 
				                        "./a.out");
		languages[1] = new LangInternal(2 , "c++" , "gcc " , false , ".cpp",
				                        "g++ " + LangInternal.opt + " " + LangInternal.src, 
				                        "./a.out");
		languages[2] = new LangInternal(3 , "python" , "python " , true , ".py",
				                        null, 
				                        "python " + LangInternal.opt + " " + LangInternal.src);
		languages[3] = new LangInternal(4 , "java" , "javac " , false , ".java",
				                        "javac " + LangInternal.opt + " " + LangInternal.src, 
				                        "java "  + LangInternal.src);
		languages[4] = new LangInternal(5, "lisp" , "clisp" , true , ".cl",
				                        null, "clisp " + LangInternal.opt + " " + LangInternal.src);
	}
	
	/** execute tasks contained in the queue using docker as sandbox,
	 *  an execution can be a compilation, an interpretation or compilation + execution 
	 */
	@Override
	public void executeTasks() {
		TaskInterface job = queue.getTask();
		// task already compiled , then execute it simply
		if (job.isCompiled())
			doDockerExecute(job, EnumStatus.ON_EXEC);
		else
		{
			int idLang = job.getParams().getCodeLang();
			LangInternal langTmp = (LangInternal)languages[idLang - 1];
			// an interpretation 
			if (langTmp.isInterpreted())
				doDockerInterpret(job);
			else
			{
				/* compile then if no error execute or report the errors */
				doDockerCompile(job);
				if (job.getParams().getToRun() && job.isCompiled())
					doDockerExecute(job , EnumStatus.ON_EXEC);
			}
		}

	}
	
	/** Set execution duration for a container 
	 * @param timeInSec time in second
	 */
	@Override
	public void setExecutionTime(double timeInSec) {
		dockerExecTime = timeInSec;

	}
	/** Set memory limit for a container
	 * @param memLimit memory limit (e.g : 128m)
	 */
	@Override
	public void setMemLimit(String memLimit)
	{
		dockerMemLimit = memLimit;
	}
	
	/**
	 * Compile a task in a docker container
	 * @param job the task to compile
	 */
	private void doDockerCompile(TaskInterface job) {
		String srcCode = job.getParams().getSrcCode();
		String jobPath = job.getPath();
		String compilOpt = job.getParams().getCompilOpt();
		TaskResultsInterface jobResults = job.getResults();
		
		jobResults.setStatus(EnumStatus.ON_COMP);

		int idLang = job.getParams().getCodeLang();
			LangInternal lang = (LangInternal) languages[idLang - 1];

		String fileToCompile;
		String ext = lang.getExtension();
		
		/* if the language is java then try to match the first : public class <name>
		 * in order to get the name of the file to create 
		 */
		if (lang.getLangName().equals("java")) {
			Pattern pattern = Pattern
					.compile("^public[ \t\n\r]+class[ \t\n]+[a-zA-Z][a-zA-Z0-9_]*");
			String className = null;
			String[] lines = srcCode.split("\n");
			for (String line : lines)
			{
				Matcher matcher = pattern.matcher(line);
				if (matcher.lookingAt())
				{
					className = matcher.group(0);
					className = className.split("[ \t\n\r]+")[2];
					break;
				}
			}
			
			/* a class has not been found, set status, result and stdout */
			if (className == null)
			{
				TaskResultsInterface res = job.getResults(); 
				job.inActivity();
				res.setStatus(EnumStatus.DONE);
				res.setResult(EnumResult.COMP_ERROR);
				res.setStdOut("Error: public class not found\n");
				return;
			}
			else
				fileToCompile = className;
		}
		/* for other languages, give a default filename */
		else
			fileToCompile = "srcFile";
	    
		job.setSrcFileName(fileToCompile);
		createFile(fileToCompile + ext, srcCode, jobPath);
		
		List<String> dockerCmd = getDockerContext(jobPath);
		String tmp = lang.generateCompileCommand(fileToCompile + ext, compilOpt);
		tmp = tmp.trim().replaceAll(" +", " ");
		String[] tokens = tmp.split(" ");
		for (String token : tokens)
			dockerCmd.add(token);
		
		try {
			long timeCompilationStart = System.currentTimeMillis();
			doDockerRun(dockerCmd, job);
			long compilationDuration = System.currentTimeMillis() - timeCompilationStart;
			
			jobResults.setTimeCompilInfo(compilationDuration);
			job.inActivity();
			jobResults.setStatus(EnumStatus.DONE);
			// if compilation succeed then one or more files has been created in the task directory
			int nbElementInPath = (new File(job.getPath())).list().length;
			if (nbElementInPath >= 2)
				jobResults.setResult(EnumResult.SUCESS);
			else
				jobResults.setResult(EnumResult.COMP_ERROR);
		} catch (Exception e) {
			jobResults.setStatus(EnumStatus.ERROR);
			System.out.println("Error while docker process creation");
		}
	}
	
	/**
	 * Retrieve languages supported by this executor
	 * @return all languages supported 
	 */
	@Override
	public Lang[] getLanguages() {
		return languages;
	}
	
	/**
	 * Execute a task in a docker container
	 * @param job the task to execute
	 * @param execType the execution status to indicate execution or interpretation 
	 * {@link pdp.compileplatform.session.EnumStatus}
	 */
	private void doDockerExecute(TaskInterface job , EnumStatus execType){
		TaskParametersInterface param = job.getParams();
		String jobPath = job.getPath();
		String compilOpt = param.getCompilOpt();
		String cmdArg = param.getCmdArg();
		TaskResultsInterface jobResults = job.getResults();
		String compiledFile = job.getSrcFileName();
			
		jobResults.setStatus(execType);
		
		int idLang = param.getCodeLang();
		LangInternal lang = (LangInternal) languages[idLang - 1 ];
		
		List<String> dockerCmd = getDockerContext(jobPath);
		String tokens = lang.generateExecutionCommand(compiledFile, compilOpt);
		String[] tmp = tokens.trim().replaceAll(" +", " ").split(" ");
		for (String t : tmp)
			dockerCmd.add(t);
		String tmp1 = cmdArg.trim().replaceAll(" +", " ");
		if (!tmp1.equals("") && !tmp1.equals(" "))
			dockerCmd.add(cmdArg);
		try {
			long timeExecutionStart = System.currentTimeMillis();
			doDockerRun(dockerCmd, job);
			long executionDuration = System.currentTimeMillis() - timeExecutionStart;
			
			jobResults.setTimeExecInfo(executionDuration);
			job.inActivity();
			jobResults.setStatus(EnumStatus.DONE);
			jobResults.setResult(EnumResult.SUCESS);

		} catch (Exception e) {
			jobResults.setStatus(EnumStatus.ERROR);
			System.out.println("Error while docker process creation");
		}
	}
	/**
	 * Launch an operating system process to execute docker command for a task
	 * @param dockerCmd docker command to launch
	 * @param job the task which attributes must be updated
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void doDockerRun(List<String> dockerCmd, TaskInterface job)	throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(dockerCmd);
		RequestHandler.printLog("Executing docker command " + dockerCmd.toString()); 
				                
		builder.directory(new File(job.getPath()));
		Process dockerProcess = builder.start();
		/* send the job standard input to the process */
		String stdIn = job.getParams().getStdIn();
		OutputStream os = dockerProcess.getOutputStream();
		BufferedWriter bIn = new BufferedWriter(new OutputStreamWriter(os));
		bIn.write(stdIn);
		bIn.close();
		/* retrieve process standard output and standard error in order to update 
		 * the job
		 */
		InputStream is = dockerProcess.getInputStream();
		InputStream es = dockerProcess.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader brOut = new BufferedReader(isr);
		BufferedReader brErr = new BufferedReader(new InputStreamReader(es));
		StringBuilder dockerStdOut = new StringBuilder("");
		StringBuilder dockerStdErr = new StringBuilder("");
		int c;
		while ((c = brOut.read()) != -1)
			dockerStdOut.append((char)c);
		while ((c = brErr.read()) != -1)
			dockerStdErr.append((char)c);
	
		dockerProcess.waitFor();
		
		TaskResultsInterface jobResults = job.getResults();
		jobResults.setStdOut(dockerStdOut.toString());
		jobResults.setStdErr(dockerStdErr.toString());
	}
	/**
	 * Interpret a task in a docker container
	 * @param job the job to interpret
	 */
	private void doDockerInterpret(TaskInterface job)
	{
		TaskParametersInterface param = job.getParams();
		String jobPath = job.getPath();
		String srcCode = param.getSrcCode();
	
		int idLang = param.getCodeLang();
		LangInternal lang = (LangInternal) languages[idLang - 1];
		String ext = lang.getExtension();
		String fileToInterpret;
		fileToInterpret = "srcFile" + ext;
		job.setSrcFileName(fileToInterpret);
		createFile(fileToInterpret, srcCode, jobPath);
		doDockerExecute(job , EnumStatus.ON_INTERPRET);
	}
		
	/**
	 * Constructs docker command to be executed in a process
	 * @param jobPath the task root directory
	 * @return a well formed command to be executed in a process 
	 */
	private List<String> getDockerContext(String jobPath) {
		List<String> dockerCmd = new ArrayList<>();
		dockerCmd = new ArrayList<>();
		dockerCmd.add("docker");
		dockerCmd.add("run");
		dockerCmd.add("-m");
		dockerCmd.add(dockerMemLimit);
		dockerCmd.add("-i");
		dockerCmd.add("--user=nobody");
		dockerCmd.add("--net=none");
		dockerCmd.add("--rm=true");
		dockerCmd.add("--volume");
		dockerCmd.add(jobPath + ":" + jobPath);
		dockerCmd.add("--workdir");
		dockerCmd.add(jobPath);
		dockerCmd.add(dockerImg);
		dockerCmd.add("timeout");
		dockerCmd.add("-s");
		dockerCmd.add("KILL");
		dockerCmd.add(Double.toString(dockerExecTime));
		return dockerCmd;
	}
	
	/**
	 * Create a file in a given directory and then write contents into it
	 * @param fileName name of the file to create
	 * @param content content of the file to be created
	 * @param jobPath directory where to create the file
	 */
	private void createFile(String fileName , String content , String jobPath)
	{
		File f = new File(jobPath , fileName);
		
		try (Writer output = new PrintWriter(f , "UTF-8")) {
			output.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error while file creation");
		}
	}
	
}
