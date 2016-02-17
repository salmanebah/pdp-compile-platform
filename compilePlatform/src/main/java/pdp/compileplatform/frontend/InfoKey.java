package pdp.compileplatform.frontend;

/**
 * A JavaBean included in all object returned by the platform 
 * web methods , it gives information about client's request (success, failure)      
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see RequestListenerInterface
 * @See EnumRetCode
 * 
 *
 */

public class InfoKey {
	/**
	 * code to send to the client
	 */
	private int code;
	/** 
	 * description of the code 
	 */
	private String description;
	/**
	 * Constructs a information object
	 * @param code code the use for this info
	 * @param description description to use for this InfoKey
	 */
	public InfoKey(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	/**
	 * Constructs an empty information object 
	 */
	public InfoKey()
	{
		
	}	
	
	/**
	 * get the code associated to this information object
	 * @return the code for this InfoKey
	 */
	public int getCode() {
		return code;
	}	
	
	/**
	 * set the code for this information object
	 * @param code the new code for this InfoKey
	 */
	public void setCode(int code) {
		this.code = code;
	}
	
	/**
	 * get the description associated to this information object
	 * @return the description for this InfoKey
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * set the description for this information object
	 * @param description the description for this InfoKey
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
}
