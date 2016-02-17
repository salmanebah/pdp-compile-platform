package pdp.compileplatform.frontend;

import java.util.Date;

import pdp.compileplatform.execution.TaskExecutor;
import pdp.compileplatform.execution.TaskExecutorInterface;
import pdp.compileplatform.execution.TaskQueue;
import pdp.compileplatform.execution.TaskQueueInterface;
import pdp.compileplatform.session.ClientsManager;
import pdp.compileplatform.session.ClientInterface;
import pdp.compileplatform.session.ClientsManagerInterface;
import pdp.compileplatform.session.EnumResult;
import pdp.compileplatform.session.EnumStatus;
import pdp.compileplatform.session.Task;
import pdp.compileplatform.session.TaskInterface;
import pdp.compileplatform.session.TaskParametersInterface;
import pdp.compileplatform.session.TaskResultsInterface;

/**
 * A concrete implementation of the <tt> RequestHandlerInterface </tt> interface
 *
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see RequestHandlerInterface
 * @see pdp.compileplatform.execution.TaskExecutor
 * @see pdp.compileplatform.execution.TaskExecutorInterface
 * @see pdp.compileplatform.execution.TaskQueue
 * @see pdp.compileplatform.execution.TaskQueueInterface
 * @see pdp.compileplatform.session.Task
 * @see pdp.compileplatform.session.TaskInterface
 * 
 *
 */
public class RequestHandler implements RequestHandlerInterface {
	/**
	 * the executor of the compilation, interpretation or compilation + execution tasks
	 */
	private TaskExecutorInterface taskExecutor;
	/**
	 * the manager of the client's sessions
	 */
	private ClientsManagerInterface clientsManager;
	/**
	 * the queue containing the tasks to run
	 */
	private TaskQueueInterface taskQueue;
	/**
	 * Constructs a RequestHandler object
	 * @param dockerImg the docker image to use
	 * @param dockerExecTime execution time for docker containers
	 * @param dockerMemLimit memory limit for docker containers
	 * @param sessionCleanerTimeInMinutes time interval to clean sessions
	 * @param sessionMaxInactivityInMinutes maximum time of session inactivity
	 * @param taskCleanerTimeInMinutes time interval to clean tasks
	 * @param taskMaxInactivityInMinutes maximum time of task inactivity
	 */
	public RequestHandler(String dockerImg ,
					      double dockerExecTime,
			              String dockerMemLimit, 
			              int sessionCleanerTimeInMinutes, 
			              int sessionMaxInactivityInMinutes,
			              int taskMaxInactivityInMinutes) {
		
		taskQueue = new TaskQueue();
		clientsManager = new ClientsManager(sessionCleanerTimeInMinutes,
											sessionMaxInactivityInMinutes, 
											taskMaxInactivityInMinutes);
		
		taskExecutor = new TaskExecutor(taskQueue, dockerImg , dockerExecTime , dockerMemLimit);
	}
	
	/**
	 *  connect a client on the server {@link RequestListener#connect() connect} 
	 */

	@Override
	public ConnectRet connect() {
		EnumRetCode retCode = EnumRetCode.OVERLOADED;
		String sessionKey = "";
		
		ClientInterface client = clientsManager.addClient();
		/*
		 * If new client registered, that's ok!
		 */
		if (client != null) {
			sessionKey = client.getSessionKey();
			retCode = EnumRetCode.SUCCESS;
			printLog("connect() => Client connection :" + sessionKey);
			
		}
		/*
		 * else, just log the event connection error
		 */
		else{
			printLog("connect() => Client connection error");
		}
		
		InfoKey ik = new InfoKey(retCode.getRetCode(), retCode.getDescription());
		return new ConnectRet(ik, sessionKey);
	}

	/**
	 * disconnect a client from the server {@link RequestListener#disconnect(String) disconnect}
	 */
	@Override
	public InfoKey disconnect(String sessionKey) {
		EnumRetCode retCode = EnumRetCode.UNKNOWN_KEY_SESSION;

		ClientInterface client = clientsManager.getClient(sessionKey);
		/*
		 * if a valid registered client , then remove him , else just log the event
		 */
		if (client != null) {
			clientsManager.removeClient(client);
			retCode = EnumRetCode.SUCCESS;
			printLog("disconnect() => Client successfully disconnected : " + sessionKey);
		}else{
			printLog("disconnect() => Client disconnection error : " + sessionKey);
		}
		
		return new InfoKey(retCode.getRetCode(), retCode.getDescription());
	}

	/**
	 * get supported languages {@link RequestListener#getLanguages() getting languages}
	 */
	@Override
	public GetLanguagesRet getLanguages() {
		Lang[] languages = taskExecutor.getLanguages();
		InfoKey ik = new InfoKey(EnumRetCode.SUCCESS.getRetCode(),
				EnumRetCode.SUCCESS.getDescription());
		printLog("getLanguages() => Languages sending");
		return new GetLanguagesRet(ik, languages);
	}

	/**
	 * post a request on the server {@link RequestListener#sendRequest(String, int, String, String, String, String, boolean) sending request}.
	 * First checks that parameters are valid one 
	 *  
	 */
	@Override
	public SendRequestRet sendRequest(String sessionKey,
			int codeLang, String srcCode, String compilOpt, String cmdArgs,
			String stdIn, boolean toRun) {
		EnumRetCode retCode = EnumRetCode.UNKNOWN_KEY_SESSION;
		int reference = -1;

		ClientInterface client = clientsManager.getClient(sessionKey);
		/*
		 * safe guards , making sure that the request is a good one 
		 */
		if (client == null) {
			printLog("sendRequest() => Unknown client sends a request : " + sessionKey);
			return makeErrorInfoKey(EnumRetCode.UNKNOWN_KEY_SESSION);
		}
		if (codeLang < 1 || codeLang > taskExecutor.getLanguages().length) { 
																				
			printLog("sendRequest() => Client sends an unknown language request : " 
					  + sessionKey);
			return makeErrorInfoKey(EnumRetCode.UNKNOWN_LANG);
		}
		if (srcCode == null) {
			printLog("sendRequest() => Client sends a null pointer for source request : " 
					  + sessionKey);
			return makeErrorInfoKey(EnumRetCode.NULL_SRC);
		}
		if (compilOpt == null)
		{
			printLog("sendRequest() => Client sends a null pointer for compilation option : " 
					  + sessionKey);
			return makeErrorInfoKey(EnumRetCode.NULL_COMP_OPT);
		}
		if (cmdArgs == null)
		{
			printLog("sendRequest() => Client sends a null pointer for command line args :" 
					  + sessionKey);
			return makeErrorInfoKey(EnumRetCode.NULL_CMD_ARG);
		}
		if (stdIn == null)
		{
			printLog("sendRequest() => Client sends a null pointer for standard input :"
					  + sessionKey);
			return makeErrorInfoKey(EnumRetCode.NULL_STDIN);
		}
		/*
		 * create a task, add it to the task queue , and finally call the executor
		 */
		Task task = new Task(client, codeLang, srcCode, compilOpt, toRun,
				cmdArgs, stdIn);
		reference = client.getTaskId(task);

		retCode = EnumRetCode.ERROR;
		InfoKey ik = new InfoKey(retCode.getRetCode(), retCode.getDescription());

		if (reference == -1) {
			printLog("sendRequest() => Client sends request error : " + sessionKey);
			return new SendRequestRet(ik, reference);
		}
		
		try {
			taskQueue.addTask(task);
		} catch (Exception e) {
			task.kill();
			printLog("sendRequest() => Client sends request error : task can't be added to taskQueue : " + sessionKey + reference);
			return new SendRequestRet(ik, -1);
		}

		taskExecutor.executeTasks();

		retCode = EnumRetCode.SUCCESS;
		ik = new InfoKey(retCode.getRetCode(), retCode.getDescription());
		printLog("sendRequest() => Client successfully sends a request : " + sessionKey + " : " + reference);
		return new SendRequestRet(ik, reference);
	}

	/**
	 * run a previously submitted task {@link RequestListener#run(String, int, String, String) running task}
	 */
	@Override
	public InfoKey run(String sessionKey,
					   int taskReference, 
					   String cmdArgs, 
					   String stdIn) {
		
		EnumRetCode retCode = EnumRetCode.UNKNOWN_KEY_SESSION;

		ClientInterface client = clientsManager.getClient(sessionKey);
		/*
		 * safe guards , ensure the request is well formed
		 */
		if (client == null) {
			printLog("run() => Unknown client tries to run a task : " + sessionKey);
			return new InfoKey(retCode.getRetCode(), retCode.getDescription());
		}

		TaskInterface task = client.getTaskById(taskReference);
		if (task == null) {
			retCode = EnumRetCode.UNKNOWN_REF;
			printLog("run() => Client tries to run an unknown task : " + sessionKey + " : " + taskReference);
			return new InfoKey(retCode.getRetCode(), retCode.getDescription());
		}
		if (cmdArgs == null)
		{
			printLog("run() => Client tries to run a task with null pointer as command line args :" 
		             + sessionKey + " : " + taskReference);
			retCode = EnumRetCode.NULL_CMD_ARG;
			return new InfoKey(retCode.getRetCode() , retCode.getDescription());
		}
		if (stdIn == null)
		{
			printLog("run() => Client tries to run a task with null pointer as standard input : " 
		              + sessionKey + " : " + taskReference);
			retCode = EnumRetCode.NULL_STDIN;
			return new InfoKey(retCode.getRetCode() , retCode.getDescription());
		}
		retCode = EnumRetCode.ERROR;

		if (!task.isCompiled()) {
			printLog("run() => Client tries to run an uncompiled task : " + sessionKey + " : " + taskReference);
			return new InfoKey(retCode.getRetCode(), retCode.getDescription());
		}
		/*
		 * set the new parameter and then add the task to the task queue and finally executes it
		 */

		TaskParametersInterface params = task.getParams();
		params.setCmdArg(cmdArgs);
		params.setStdIn(stdIn);
		
		try {
			taskQueue.addTask(task);
		} catch (Exception e) {
			printLog("run() => Client tries to run a task which can't be added to taskQueue : " + sessionKey + " : " + taskReference);
			return new InfoKey(retCode.getRetCode(), retCode.getDescription());
		}

		taskExecutor.executeTasks();
		printLog("run() => Client successfully runs a task : " + sessionKey + " : " + taskReference);
		retCode = EnumRetCode.SUCCESS;
		return new InfoKey(retCode.getRetCode(), retCode.getDescription());
	}

	/**
	 * retrieve details about a submitted task {@link RequestListener#getDetails(String, int) getting details}
	 */
	@Override
	public GetDetailsRet getDetails(String sessionKey,
			                        int taskReference) {

		EnumRetCode retCode = EnumRetCode.UNKNOWN_KEY_SESSION;
		EnumResult result = EnumResult.ERROR;
		EnumStatus status = EnumStatus.ERROR;

		ClientInterface client = clientsManager.getClient(sessionKey);
		/*
		 * safe guards , ensure the request is well formed
		 */
		if (client == null) {
			InfoKey ikRetCode = new InfoKey(retCode.getRetCode(), retCode.getDescription());
			InfoKey ikResult = new InfoKey(result.getResult(), result.getDescription());
			InfoKey ikStatus = new InfoKey(status.getStatus(), status.getDescription());
			
			printLog("getDetails() => Unknown client tries to get task details : " + sessionKey);
			return new GetDetailsRet(ikRetCode, ikStatus, ikResult, "", "", -1);			
		}
		
		TaskInterface task = client.getTaskById(taskReference);
		if (task == null) {
			retCode = EnumRetCode.UNKNOWN_REF;
			result = EnumResult.ERROR;
			status = EnumStatus.ERROR;
	
			InfoKey ikRetCode = new InfoKey(retCode.getRetCode(), retCode.getDescription());
			InfoKey ikResult = new InfoKey(result.getResult(), result.getDescription());
			InfoKey ikStatus = new InfoKey(status.getStatus(), status.getDescription());
			
			printLog("getDetails() => Client tries to get unknown task details : " + sessionKey + " : " + taskReference);
			return new GetDetailsRet(ikRetCode, ikStatus, ikResult, "", "", -1);
		}
		task.inActivity();
		TaskResultsInterface taskResults = task.getResults();
		
		retCode = EnumRetCode.SUCCESS;
		result = taskResults.getResult();
		status = taskResults.getStatus();
		InfoKey ikRetCode = new InfoKey(retCode.getRetCode(), retCode.getDescription());
		InfoKey ikResult = new InfoKey(result.getResult(), result.getDescription());
		InfoKey ikStatus = new InfoKey(status.getStatus(), status.getDescription());
		
		String stdOut = taskResults.getStdOut();
		String stdErr = taskResults.getStdErr();
		float timeInfo = taskResults.getTimeInfo();
		
		printLog("getDetails() => Client successfully get task details : " + sessionKey + " : " + taskReference);
		return new GetDetailsRet(ikRetCode, ikStatus, ikResult, stdOut, stdErr, timeInfo);
	}
	
	/**
	 * get status about a submitted task {@link RequestListener#getStatus(String, int) getting status} 
	 */
	@Override
	public GetStatusRet getStatus(String sessionKey,
								  int taskReference) {
		
		EnumRetCode retCode = EnumRetCode.UNKNOWN_KEY_SESSION;
		EnumResult result = EnumResult.ERROR;
		EnumStatus status = EnumStatus.ERROR;

		ClientInterface client = clientsManager.getClient(sessionKey);
		
        /*
		 * safe guards , ensure the request is well formed
		 */
		if (client == null) {
			InfoKey ikRetCode = new InfoKey(retCode.getRetCode(), retCode.getDescription());
			InfoKey ikResult = new InfoKey(result.getResult(), result.getDescription());
			InfoKey ikStatus = new InfoKey(status.getStatus(), status.getDescription());
			
			printLog("getStatus() => Unknown client tries to get task status : " + sessionKey);
			return new GetStatusRet(ikRetCode, ikStatus, ikResult);			
		}
		
		TaskInterface task = client.getTaskById(taskReference);
		if (task == null) {
			retCode = EnumRetCode.UNKNOWN_REF;
			result = EnumResult.ERROR;
			status = EnumStatus.ERROR;
	
			InfoKey ikRetCode = new InfoKey(retCode.getRetCode(), retCode.getDescription());
			InfoKey ikResult = new InfoKey(result.getResult(), result.getDescription());
			InfoKey ikStatus = new InfoKey(status.getStatus(), status.getDescription());
			
			printLog("getStatus() => Client tries to get unknown task status : " + sessionKey + " : " + taskReference);
			return new GetStatusRet(ikRetCode, ikStatus, ikResult);
		}
		
		task.inActivity();
		TaskResultsInterface taskResults = task.getResults();
		
		retCode = EnumRetCode.SUCCESS;
		result = taskResults.getResult();
		status = taskResults.getStatus();
		InfoKey ikRetCode = new InfoKey(retCode.getRetCode(), retCode.getDescription());
		InfoKey ikResult = new InfoKey(result.getResult(), result.getDescription());
		InfoKey ikStatus = new InfoKey(status.getStatus(), status.getDescription());
		
		printLog("getStatus() => Client successfully get task status : " + sessionKey + " : " + taskReference);
		return new GetStatusRet(ikRetCode, ikStatus, ikResult);

	}

	public static void printLog(String logInfo){
		Date date = new Date(System.currentTimeMillis());
		System.out.println(">>> " + date + ":\n\t" + logInfo);
	}
	
	/**
	 * make a custom <tt>sendRequestRet</tt> object 
	 * @param code the type of code
	 * @return a custom <tt>sendRequest</tt> object
	 */
	private static SendRequestRet makeErrorInfoKey(EnumRetCode code)
	{
		InfoKey tmp =  new InfoKey(code.getRetCode() , code.getDescription());
		return new SendRequestRet(tmp , -1);
	}
}