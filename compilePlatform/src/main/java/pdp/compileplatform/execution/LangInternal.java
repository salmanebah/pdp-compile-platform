package pdp.compileplatform.execution;

import pdp.compileplatform.frontend.Lang;
/**
 * This class represents a language unit (language name, language id, extension,  
 * compilation command, ...) as seen internally by the server
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see pdp.compileplatform.frontend.Lang
 */

public class LangInternal extends Lang {
	/**
	 * whether the language is an interpreted one or not
	 */
	private boolean interpreted;
	/**
	 * file name extension for this language (e.g : .c , .java,...)
	 */
	private String extension;
	/**
	 * compilation string format for this language (e.g : {@code gcc [option]*  <filename> })
	 */
	private String compileCommand;
	/**
	 * execution string format for this language (e.g : {@code ./a.out})
	 */
	private String executionCommand;
	/**
	 * placeholder for the compilation or execution option
	 */
	public static final String opt = "opt";
	/**
	 * placeholder for the file containing the source code  
	 */
	public static final String src = "src";
	
	/**
	 * Constructs a language unit as seen by the server
	 * @param idLang unique id of the language
	 * @param langName name of the language
	 * @param compiler name of the compiler to be used for this language's source files
	 * @param interpreted whether the language is an interpreted one or not
	 * @param extension file name extension for the language 
	 * @param compileCommand compilation string format for this language 
	 * (e.g : {@code gcc [option]*  <filename> })
	 * @param executionCommand execution string format for this language (e.g : {@code ./a.out})
	 */
	public LangInternal(int idLang, String langName, String compiler, 
						boolean interpreted , String extension, String compileCommand, String executionCommand)
	{
		super(idLang , langName , compiler);
		this.interpreted = interpreted;
		this.extension = extension;
		this.compileCommand = compileCommand;
		this. executionCommand = executionCommand;
	}
	
	/**
	 * 
	 * @return true if this language is interpreted , false otherwise
	 */
	public boolean isInterpreted()
	{
		return interpreted;
	}
	
	/**
	 * 
	 * @return the file name extension for this language
	 */
	public String getExtension()
	{
		return extension;
	}
	
	/**
	 * construct a well formed compilation command
	 * @param fileName the file name to use 
	 * @param compileOptions compilation options 
	 * @return a well formed compilation command depending on this language 
	 * (e.g {@code gcc -std=c99 src.c})
	 */
	public String generateCompileCommand(String fileName, String compileOptions){		
		
		return compileCommand.replaceAll(opt, compileOptions).replaceAll(src, fileName);
	}
	
	/**
	 * construct a well formed execution command
	 * @param fileName the file name to use
	 * @param executionOptions execution options
	 * @return a well formed execution command depending on this language 
	 * (e.g {@code java -esa client})
	 */
	public String generateExecutionCommand(String fileName, String executionOptions){
		return executionCommand.replaceAll(opt, executionOptions).replaceAll(src, fileName);
	}
}
