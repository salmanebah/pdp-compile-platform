package pdp.compileplatform.session;

/**
 * This interface presents the different methods that must be implemented for a task. 
 * A task must permit to access to its owner, the path to the directory where its related
 * files are saved, its parameters, the results of its last execution.
 * It must also specify if it have been successfully compiled and the time of inactivity.
 * Finally the implementation must allow to destroy the related files saved on the system and 
 * permit to give or to modify the source file name.
 * 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see Task
 * @see TaskManagerInterface
 * @see TaskManager
 * @see TaskResultsInterface
 * @see TaskResults
 * @see TasksParametersInterface
 * @see TaskParameters
 * @see ClientInterface
 * @see Client
 */

public interface TaskInterface {
	
    /**  
	 * Return the ClientInterface which owns the task
	 * @return the ClientInterface which owns the task
	 */	
	public ClientInterface getClient();
	
	/** Return the path to the directory where the file related
	 * to the task are saved 
	 * @return a string describing path to the task's directory
	 */	
	public String getPath();
	
    /** Return the parameters of the task
	 * @return the TaskParametersInterface owned by the task
	 */	
	public TaskParametersInterface getParams();
	
    /** Return the results of the last execution of the task
	 * @return the TaskResultsInterface owned by the task
	 */	
	public TaskResultsInterface getResults();
	
    /** Return the name of the source file containing the code of the task
     * to compile/execute
	 * @return a string representing the name of the source file containing 
	 * the code of the task
	 */	
	public String getSrcFileName();
	
    /** Replace the name of the task's source file by the one given
     * in argument
	 * @param srcFileName the new name of the task's source file
	 */	
	public void setSrcFileName(String srcFileName);
	
    /** Return true if the task have been successfully compiled
	 * @return true if the task have been successfully compiled
	 */	
	public boolean isCompiled();
	
	/** Update the last activity time of this task 
	 * 
	 */	
	public void inActivity();
	
    /** Return the time elapsed since the last time this task
     * have been executed
	 * @return a long representing the time elapsed in seconds since the last
	 * task's execution
	 */	
	public long inactiveSince();
	
    /** Destroy the files related to the task */
	public void kill();
	
}

