package pdp.compileplatform.session;

/**
 * A concrete implementation of the <tt>TaskParametersInterface</tt> interface 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskParametersInterface
 * @see TaskInterface
 * @see Task
 *
 */
public class TaskParameters implements TaskParametersInterface{
	
	private int codeLang;
    private String srcCode;
    private String compilOpt;
    
    private boolean toRun;
    
    private String cmdArg;
    private String stdIn;
	
    /**
     * Construct a TaskParameter
     * @param codeLang the reference number of the language
     * in which the code is written
     * @param srcCode the string of the code to compile/execute
     * @param compilOpt the compilation's option to give to the 
     * compilator, a empty string if there are not
     * @param toRun the boolean giving the instruction to execute
     * the task after compilation or not
     * @param cmdArg the argument to give at the execution, an
     * empty string if there are not 
     * @param stdIn the standard input to give at the execution,
     * an empty string if there is not
     */
    TaskParameters(int codeLang, 
    			   String srcCode, 
    			   String compilOpt,
    			   boolean toRun,
    			   String cmdArg,
    			   String stdIn){
    
    	this.codeLang = codeLang;
    	this.srcCode = srcCode;
    	this.compilOpt = compilOpt;
    	this.toRun = toRun;
    	this.cmdArg = cmdArg;
    	this.stdIn = stdIn;
    }
    
    /**
     * Return the number of the language in which the task's code have been written
     * @return the number of the language in which the task's code have been written
     */
    @Override
    public int getCodeLang() {
	  return codeLang;
    }
    
    /**
     * Return the string of the task's code
     * @return the string of the task's code
     */
    @Override
    public String getSrcCode() {
	  return srcCode;
    }

    /**
     * Return the string of the task's options of 
     * the compile line command
     * @return the string of the task's options of 
     * the compile line command
     */
    @Override
    public String getCompilOpt() {
	  return compilOpt;
    }
    
    /**
     * Return a boolean specifying if the task must
     * be executed after compilation
     * @return a boolean specifying if the task must
     * be executed after compilation
     */
    @Override
    public boolean getToRun() {
	  return toRun;
    }
   
    /**
     * Return the string of the task's arguments of 
     * the execution line command
     * @return the string of the task's arguments of 
     * the execution line command
     */
    @Override
    public String getCmdArg() {
	  return cmdArg;
    }
    
    /**
     * Change the arguments to give at the execution of the
     * task by the one given in parameters
     * @param a string representing the arguments to give
     * while executing the task
     */
    @Override
    public void setCmdArg(String cmdArg) {
	  this.cmdArg = cmdArg;
    }

    /**
     * Return the string of the stdin to give while executing 
     * the task
     * @return he string of the stdin to give while executing 
     * the task
     */
    @Override
    public String getStdIn() {
	  return stdIn;
    }
    
    /**
     * Change the stdin to give at the execution of the task by
     * the one given in parameters
     * @param a string representing the stdin to give while executing 
     * the task
     */
    @Override
    public void setStdIn(String stdIn) {
	  this.stdIn = stdIn;
    }
}
