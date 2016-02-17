package pdp.compileplatform.session;


/**
 * A concrete implementation of the <tt>TaskResultsInterface</tt> interface 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskResultsInterface
 * @see TaskInterface
 * @see Task
 */
public class TaskResults implements TaskResultsInterface {
		
	private String stdOut;
	private String stdErr;
	
	private float timeCompilInfo;
	private float timeExecInfo;
	
	private EnumResult result;
	private EnumStatus status;
	
	
	/**
	 * Construct an object TaskResults
	 */
	TaskResults(){
		
		status = EnumStatus.WAITING;
		result = EnumResult.UNKNOWN;
		
		stdOut = "";
		stdErr = "";
		
		timeCompilInfo = 0;
		timeExecInfo = 0;
	}
	
    /**
     * Change the EnumResult with the one given in parameter
     * @param result the new result's value 
     */
    @Override
    public void setResult(EnumResult result) {
    	this.result = result;
    }
    
    /**
     * Return the value of the result
     * @return an EnumResult which is the value of the result
     */
    @Override
    public EnumResult getResult(){
    	return this.result;
    }

    /**
     * Return the value of the status
     * @return an EnumStatus which is the value of the status
     */
    @Override
    public EnumStatus getStatus() {
    	return this.status;
    }	
    
    /**
     * Change the value of the status with the one given in parameters
     * @param status the EnumStatus representing the status' value
     */
	@Override
    public void setStatus(EnumStatus status) {
    	this.status = status;
    }

	/**
	 * Return the stdOut related to the task
	 * @return a string for the stdout related to the task
	 */
    @Override
	public String getStdOut() {
		return stdOut;
	}
    
    /**
     * Change the stdout of the task with the new one given in parameters
     * @param a String representing the new task's stdout
     */
    @Override
	public void setStdOut(String stdOut) {
		this.stdOut = stdOut;
	}

    /**
     * Return the stderr related to the task
     * @return a String representing the task's stderr
     */
    @Override
	public String getStdErr() {
		return stdErr;
	}
    
    /**
     * Change the stderr of the task with the new one given in parameters
     * @param stdErr a String representing the new stderr
     */
    @Override
	public void setStdErr(String stdErr) {
		this.stdErr = stdErr;
	}

    /**
     * Return the time elapsed during the last execution of the task
     * @return a float representing the time elapsed during the last
     * execution of the task
     */
    @Override
	public float getTimeInfo() {
		return timeCompilInfo + timeExecInfo;
	}
    
    /**
     * Change the compilation's time with the new one given in parameters
     * @param timeCompilInfo a float representing the time elapsed during
     * the last compilation of the task
     */
    @Override
	public void setTimeCompilInfo(float timeCompilInfo) {
		this.timeCompilInfo = timeCompilInfo;
	}
    
    /**
     * Change the execution's time with the new one given in parameters
     * @param timeExecInfo a float representing the time elapsed during
     * the last execution of the task
     */
    @Override
	public void setTimeExecInfo(float timeExecInfo) {
		this.timeExecInfo = timeExecInfo;
	}
    
}
