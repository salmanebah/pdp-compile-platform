package pdp.compileplatform.session;

import java.io.File;
import java.util.Date;

/**
 * A concrete implementation of the <tt>TaskInterface</tt> interface 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskInterface
 * @see TaskManagerInterface
 * @see TaskManager
 * @see TaskResultsInterface
 * @see TaskResults
 * @see TasksParametersInterface
 * @see TaskParameters
 * @see ClientInterface
 * @see Client
 */
public class Task implements TaskInterface {
    
     private ClientInterface owner; 
     private Date lastActivity;
    
     private TaskParametersInterface params;
     private TaskResultsInterface results;
     
     private String srcFileName;
    
     /**  Construct an object Task
  	 * @param owner the ClientInterface that owns the task.
  	 * @param codeLang the number assigned to the langage in 
  	 * which the code is written. 
  	 * @param srcCode the code to compile/execute contained in a string.
  	 * @param compilOpt the potential options to give to
  	 * the compiler, give a empty string if there are not.
  	 * @param toRun true if the task must be compiled then executed,
  	 * false if only a compilation is required.
  	 * @param cmdArgs the potential arguments to give to the file 
  	 * to execute, give an empty string if there are not.
  	 * @param stdIn the potential standard input to give to the 
  	 * file to executed, give an empty string if there is not.
  	 * @return the ClientInterface which owns the task
  	 */
     public Task(ClientInterface owner, 
    		 	 int codeLang,
    		 	 String srcCode,
    		 	 String compilOpt, 
    		 	 boolean toRun, 
    		 	 String cmdArgs, 
    		 	 String stdIn){
    	 
    	this.owner = owner;
    	owner.addTask(this);
	  
    	this.params = new TaskParameters(codeLang, srcCode, compilOpt, toRun, cmdArgs, stdIn);
    	this.results = new TaskResults();
    	this.lastActivity = new Date(System.currentTimeMillis());
	  
    	File dir = new File(getPath());
    	dir.mkdir();
    	dir.setReadable(true,false);
    	dir.setWritable(true, false);
    	dir.setExecutable(true, false);
    }
     
     /**  
 	 * Return the ClientInterface which owns the task
 	 * @return the ClientInterface which owns the task
 	 */	
     @Override
     public ClientInterface getClient() {
    	 return owner;
     }

     /** Return the parameters of the task
 	 * @return the TaskParametersInterface owned by the task
 	 */	   
    @Override
    public TaskParametersInterface getParams() {
    	return this.params;
    }

    /** Return the results of the last execution of the task
	 * @return the TaskResultsInterface owned by the task
	 */	  
    @Override
    public TaskResultsInterface getResults() {
    	return this.results;
    }

    /** Return the time elapsed since the last time this task
     * have been executed
	 * @return a long representing the time elapsed in milliseconds since the last
	 * task's execution
	 */	  
    public long inactiveSince(){
    	return timeElapsedSince(this.lastActivity);    	
    }
    
    /** Return true if the task have been successfully compiled
	 * @return true if the task have been successfully compiled
	 */	  
    @Override
	public boolean isCompiled() {
		EnumResult enumRes = this.results.getResult();
		EnumStatus enumStat = this.results.getStatus();
		return ((enumStat == EnumStatus.DONE)
				&&(enumRes == EnumResult.EXEC_ERROR || enumRes == EnumResult.SUCESS));
	}
    
    /** Return the name of the source file containing the code of the task
     * to compile/execute
	 * @return a string representing the name of the source file containing 
	 * the code of the task
	 */	
    @Override
	public String getSrcFileName() {
		return srcFileName;
	}

    /** Replace the name of the task's source file by the one given
     * in argument
	 * @param srcFileName the new name of the task's source file
	 */	 
	@Override
	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	/** Return the path to the directory where the file related
	 * to the task are saved 
	 * @return a string describing path to the task's directory
	 */	
    @Override
    public String getPath(){
    	return owner.getPath()+ File.separatorChar + owner.getTaskId(this);
    }
    
    /** Destroy the files related to the task*/   
    @Override
	public void kill() {
		File dir = new File(getPath());
		emptyDirectory(dir);
		dir.delete();
	}
    
    /** Update the last activity time of this task */ 
    @Override
    public void inActivity() {
		this.lastActivity = new Date(System.currentTimeMillis());	
		owner.inActivity();
	}
     

    private static long timeElapsedSince(Date start) {
		long since = start.getTime();
		long now = System.currentTimeMillis();
		return (now - since);
	}
    

    private static void emptyDirectory(File folder){
    	for(File file : folder.listFiles()){
    		if(file.isDirectory()){
    			emptyDirectory(file);
    		}
    		file.delete();
    	}
    }
    
}

