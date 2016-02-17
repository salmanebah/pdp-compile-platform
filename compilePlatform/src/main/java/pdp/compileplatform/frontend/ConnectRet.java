package pdp.compileplatform.frontend;

/**
 * This class represents objects returned to a client call to the web method 
 * {@link RequestListenerInterface#connect() connect} , it's a JavaBean to allow 
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
public class ConnectRet {
	
	/**
	 * the information object associated to the connection request
	 */
	private InfoKey retCode;
	/**
	 * the session key to be returned to the client
	 */
	private String sessionKey;
	
	/**
	 * Constructs a ConnectRet object
	 * @param code information object about the request
	 * @param sessionKey the session key to send to the client
	 */
	public ConnectRet(InfoKey retCode, String sessionKey) {
		this.retCode = retCode;
		this.sessionKey = sessionKey;
	}
	
	/**
	 * Constructs an empty ConnectRet object 
	 */
	public ConnectRet ()
	{	
		
	}
	
	/**
	 * get the information object associated to this ConnectRet object
	 * @return information object associated to this ConnectRet object
	 */
	public InfoKey getRetCode() {
		return retCode;
	}

	/**
	 * set the information object for this ConnectRet object
	 * @param code the new code for this ConnectRet object
	 */
	public void setRetCode(InfoKey retCode) {
		this.retCode = retCode;
	}

	/**
	 * get the session key associated to this ConnectRet object
	 * @return the session key for this ConnectRet object
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * set the session key associated to this ConnectRet object
	 * @param sessionKey the new session key for this ConnectRet object
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	
}
