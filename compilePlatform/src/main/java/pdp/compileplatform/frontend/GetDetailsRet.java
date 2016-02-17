package pdp.compileplatform.frontend;

/**
 * This class represents objects returned to a client call to the web method 
 * {@link RequestListenerInterface#getDetails(String, int) getDetails} , it's a JavaBean to allow 
 * its creation by the <tt> JAX-WS </tt> API  
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see RequestListenerInterface
 * @see InfoKey
 * 
 *
 */

public class GetDetailsRet {

	/**
	 * the information object associated to the request
	 */
	private InfoKey retCode;
	/**
	 * the current status of the request's task (e.g : success, compilation error,...)
	 */
	private InfoKey status;
	
	/**
	 * the current result of the request's task (e.g : done , on compilation,...) 
	 */
	private InfoKey result;
	
	/**
	 * the standard output of the task
	 */
	private String stdOut;
	/**
	 * the standard error of the task
	 */
	private String stdErr;
	
	/**
	 * the execution duration of the task 
	 */
	private float timeInfo;
	
	/**
	 * Constructs an empty GetDetailsRet object
	 */
	public GetDetailsRet(){
		
	}
	
	/**
	 * Constructs a GetDetailsRet object
	 * @param retCode error code for this GetDetailsRet object
	 * @param status status for the request's task
	 * @param result result for the request's task
	 * @param stdOut standard output for the request's task
	 * @param stdErr standard error for the request's task
	 * @param timeInfo execution duration for the request's task
	 */
	public GetDetailsRet(InfoKey retCode, InfoKey status, InfoKey result, String stdOut,
			             String stdErr, float timeInfo) {
		this.retCode = retCode;
		this.status = status;
		this.result = result;
		this.stdOut = stdOut;
		this.stdErr = stdErr;
		this.timeInfo = timeInfo;
	}

	/**
	 * get the error code of this GetDetailsRet object
	 * @return the error code of this GetDetailsRet object 
	 */
	public InfoKey getRetCode() {
		return retCode;
	}
	
	/**
	 * set the error code for this GetDetailsRet object 
	 * @param code the new code for the GetDetailsRet object
	 */
	public void setRetCode(InfoKey retCode) {
		this.retCode = retCode;
	}

	/**
	 * get the status code of this GetDetailsRet object 
	 * @return the status code of this GetDetailsRet object
	 */
	public InfoKey getStatus() {
		return status;
	}

	/**
	 * set the status code of this GetDetailsRet object
	 * @param status the new status code of this GetDetailsRet object
	 */
	public void setStatus(InfoKey status) {
		this.status = status;
	}

	/**
	 * get the result code of this GetDetailsRet object
	 * @return the result code of this GetDetailsRet object
	 */
	public InfoKey getResult() {
		return result;
	}

	/**
	 * set the result code of this GetDetailsRet object 
	 * @param result the new result code of this GetDetailsRet object
	 */
	public void setResult(InfoKey result) {
		this.result = result;
	}

	/**
	 * get the standard output of this GetDetailsRet object
	 * @return the standard output of this GetDetailsRet object
	 */
	public String getStdOut() {
		return stdOut;
	}
	
	/**
	 * set the standard output of this GetDetailsRet object 
	 * @param stdOut the new standard output of this GetDetailsRet object
	 */
	public void setStdOut(String stdOut) {
		this.stdOut = stdOut;
	}
	
	/**
	 * get the standard error of this GetDetailsRet object
	 * @return the standard error of this GetDetailsRet object
	 */
	public String getStdErr() {
		return stdErr;
	}
	
	/**
	 * set the standard error of this GetDetailsRet object
	 * @param stdErr the new standard error  of this GetDetailsRet object
	 */
	public void setStdErr(String stdErr) {
		this.stdErr = stdErr;
	}

	/**
	 * get the  time duration  of this GetDetailsRet object 
	 * @return the time duration of this GetDetailsRet object
	 */
	public float getTimeInfo() {
		return timeInfo;
	}
	
	/**
	 * set the time duration of this GetDetailsRet object
	 * @param timeInfo the new time duration of this GetDetailsRet object
	 */
	public void setTimeInfo(float timeInfo) {
		this.timeInfo = timeInfo;
	}
	
}
