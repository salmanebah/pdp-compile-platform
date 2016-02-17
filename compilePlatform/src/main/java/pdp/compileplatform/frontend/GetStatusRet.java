package pdp.compileplatform.frontend;

/**
 * This class represents objects returned to a client call to the web method 
 * {@link RequestListenerInterface#getStatus(String, int) getStatus} , it's a JavaBean to allow 
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

public class GetStatusRet{
	
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
	 * Constructs an empty GetStatusRet object
	 */
	public GetStatusRet(){
		
	}
	
	
	/**
	 * Constructs a GetStatusRet object
	 * @param retCode information object for this GetStatusRet object 
	 * @param status  status for this GetStatusRet object
	 * @param result  result for this GetStatusRet object
	 */
	
	public GetStatusRet(InfoKey retCode, InfoKey status, InfoKey result) {
		this.retCode = retCode;
		this.status = status;
		this.result = result;
	}
	
	/**
	 * get the information object for this GetStatusRet object
	 * @return the information object for this GetStatusRet object
	 */
	public InfoKey getRetCode() {
		return retCode;
	}
	
	/**
	 * set the information object for this GetStatusRet object
	 * @param retCode the new information object for this GetStatusRet object
	 */
	public void setRetCode(InfoKey retCode) {
		this.retCode = retCode;
	}
	
	/**
	 * get the status for this GetStatusRet object
	 * @return the status for this GetStatusRet object
	 */
	public InfoKey getStatus() {
		return status;
	}
	
	/**
	 * set the status for this GetStatusRet object
	 * @param status the new status for this GetStatusRet object
	 */
	public void setStatus(InfoKey status) {
		this.status = status;
	}
	
	/**
	 * get the result for this GetStatusRet object
	 * @return the result for this GetStatusRet
	 */
	public InfoKey getResult() {
		return result;
	}
	
	/**
	 * set the result for this GetStatusRet object
	 * @param result the new result for this GetStatusRet object
	 */
	public void setResult(InfoKey result) {
		this.result = result;
	}
}