package pdp.compileplatform.frontend;

/**
 * A language unit representation as seen by the remote clients , it's a JavaBean so that 
 * it can be constructed by the <tt>JAX-WS</tt> API 
 *  
 *  
 *
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see pdp.compileplatform.execution.LangInternal
 * 
 *
 */
public class Lang {
	/**
	 * unique identifier of this language
	 */
	private int idLang;
	/**
	 * language name
	 */
	private String langName;
	/**
	 * compiler used for this language
	 */
	private String compiler;
	
	/**
	 * Constructs a language unit
	 * @param idLang unique identifier of this language
	 * @param langName this language name
	 * @param compiler compiler for this language 
	 */
	public Lang(int idLang, String langName, String compiler) {
		this.idLang = idLang;
		this.langName = langName;
		this.compiler = compiler;	
	}
	
	/**
	 * Construct a language unit with empty fields
	 */
	public Lang(){
	}
	
	/**
	 * retrieve the unique identifier of this language 
	 * @return the language identifier
	 */
	public int getIdLang() {
		return idLang;
	}
	
	/**
	 * set the identifier for this language 
	 * @param idLang the new identifier
	 */
	public void setIdLang(int idLang) {
		this.idLang = idLang;
	}
	
	/**
	 * get the name of this language
	 * @return the name of this language
	 */
	public String getLangName() {
		return langName;
	}
	
	/**
	 * set the name of this language
	 * @param langName the new language name
	 */
	public void setLangName(String langName) {
		this.langName = langName;
	}
	
	/**
	 * retrieve the compiler associated with this language
	 * @return the compiler for this language
	 */
	public String getCompiler(){
		return compiler;
	}
	/**
	 * set the compiler for this language 
	 * @param compiler the new compiler for the language
	 */
	public void setCompiler(String compiler){
		this.compiler = compiler;
	}
	
}
