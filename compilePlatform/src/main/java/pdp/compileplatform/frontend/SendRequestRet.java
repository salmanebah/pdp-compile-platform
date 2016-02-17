package pdp.compileplatform.frontend;

/**
 * This class represents objects returned to a client call to the web method 
 * {@link RequestListenerInterface#sendRequest(String, int, String, String, String, String, boolean) sendRequest} , 
 * it's a JavaBean to allow its creation by the <tt> JAX-WS </tt> API  
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
public class SendRequestRet {
	/**
	 * the information object associated to the connection request
	 */
	private InfoKey retCode;
	
	/**
	 * the task reference of the request to return to the client
	 */
	private int reference;
	
	/**
	 * Constructs an empty SendRequestRet object 
	 */
	public SendRequestRet(){
		
	}
	
	/**
	 * Constructs a SendRequestRet object
	 * @param retCode information object about the request
	 * @param reference task reference of the request 
	 */
	public SendRequestRet(InfoKey retCode, int reference) {
		this.retCode = retCode;
		this.reference = reference;
	}
	
	/**
	 * get the information object associated to this SendRequestRet object
	 * @return information object associated to this SendRequestRet object
	 */
	public InfoKey getRetCode() {
		return retCode;
	}

	/**
	 * set the information object for this SendRequestRet object
	 * @param retCode the new code for this SendRequestRet object
	 */
	
	public void setRetCode(InfoKey retCode) {
		this.retCode = retCode;
	}
	
	/**
	 * get the task's reference of this SendRequestRet object
	 * @return the task's reference of this SendRequestRet object 
	 */
	public int getReference() {
		return reference;
	}

	/**
	 * set the task's reference of this SendRequestRet object
	 * @param reference the new reference for this SendRequestRet object
	 */
	public void setReference(int reference) {
		this.reference = reference;
	}
	
}