package pdp.compileplatform.session;

/**
 * This interface presents the different methods that must be implemented for a TasksResultsInterface. 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskResults
 * @see TaskInterface
 * @see Task
 */
public interface TaskResultsInterface {

    /**
     * Change the value of the status with the one given in parameters
     * @param status the EnumStatus representing the status' value
     */
	public void setStatus(EnumStatus status);
	
    /**
     * Change the EnumResult with the one given in parameter
     * @param result the new result's value 
     */
	public void setResult(EnumResult result); 
	
    /**
     * Change the stdout of the task with the new one given in parameters
     * @param a String representing the new task's stdout
     */
	public void setStdOut(String stdOut);

    /**
     * Change the compilation's time with the new one given in parameters
     * @param timeCompilInfo a float representing the time elapsed during
     * the last compilation of the task
     */
	public void setTimeCompilInfo(float timeCompilInfo);
	
    /**
     * Change the execution's time with the new one given in parameters
     * @param timeExecInfo a float representing the time elapsed during
     * the last execution of the task
     */
	public void setTimeExecInfo(float timeExecInfo);
	
    /**
     * Change the stderr of the task with the new one given in parameters
     * @param stdErr a String representing the new stderr
     */
	public void setStdErr(String stdErr);	
	
    /**
     * Return the time elapsed during the last execution of the task
     * @return a float representing the time elapsed during the last
     * execution of the task
     */
	public float getTimeInfo();
	
    /**
     * Return the value of the status
     * @return an EnumStatus which is the value of the status
     */
	public EnumStatus getStatus();
	
    /**
     * Return the value of the result
     * @return an EnumResult which is the value of the result
     */
	public EnumResult getResult();
	
	/**
	 * Return the stdOut related to the task
	 * @return a string for the stdout related to the task
	 */
	public String getStdOut();
	
    /**
     * Return the stderr related to the task
     * @return a String representing the task's stderr
     */
	public String getStdErr();
	
}
