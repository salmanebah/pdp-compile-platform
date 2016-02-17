package pdp.compileplatform.frontend;

/**
 * This class represents objects returned to a client call to the web method 
 * {@link RequestListenerInterface#getLanguages() getLanguages} , it's a JavaBean to allow 
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

public class GetLanguagesRet {
	
	/**
	 * information object associated to the getLanguages request
	 */
	private InfoKey retCode;
	
	/**
	 * all the languages supported by the platform
	 */
	private Lang[] languages;
	
	/**
	 * Constructs a GetLanguagesRet object
	 * @param code information object about the request
	 * @param languages languages currently supported by the platform
	 */
	public GetLanguagesRet(InfoKey retCode, Lang[] languages) {
		this.retCode = retCode;
		this.languages = languages;
	}
	
	/**
	 * Constructs an empty GetLanguagesRet object
	 */
	public GetLanguagesRet()
	{
		
	}
	/**
	 * get the information object  for this GetLanguagesRet object
	 * @return the information object for this GetLanguagesRet object
	 */
	public InfoKey getRetCode() {
		return retCode;
	}

	/**
	 * set the information object for this GetLanguagesRet object
	 * @param code the new information object for this GetLanguagesRet object
	 */
	public void setRetCode(InfoKey retCode) {
		this.retCode = retCode;
	}
	
	/**
	 * get the languages for this GetLanguagesRet object
	 * @return the languages for this GetLanguagesRet object
	 */
	public Lang[] getLanguages() {
		return languages;
	}

	/**
	 * set the languages for this GetLanguagesRet object
	 * @param languages the languages for this GetLanguagesRet object
	 */
	public void setLanguages(Lang[] languages) {
		this.languages = languages;
	}
	

}
