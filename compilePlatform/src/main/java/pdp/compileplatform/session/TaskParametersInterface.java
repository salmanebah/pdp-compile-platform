package pdp.compileplatform.session;

/**
 * This interface presents the different methods that must be implemented for a TasksParametersInterface. 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see TaskParameters
 * @see TaskInterface
 * @see Task
 */
public interface TaskParametersInterface {
	
    /**
     * Return the number of the language in which the task's code have been written
     * @return the number of the language in which the task's code have been written
     */
	public int getCodeLang();
	
    /**
     * Return the string of the task's code
     * @return the string of the task's code
     */
	public String getSrcCode();
	
    /**
     * Return the string of the task's options of 
     * the compile line command
     * @return the string of the task's options of 
     * the compile line command
     */
	public String getCompilOpt();
	
    /**
     * Return a boolean specifying if the task must
     * be executed after compilation
     * @return a boolean specifying if the task must
     * be executed after compilation
     */
	public boolean getToRun(); 
	
    /**
     * Return the string of the task's arguments of 
     * the execution line command
     * @return the string of the task's arguments of 
     * the execution line command
     */
	public String getCmdArg();
	
    /**
     * Change the arguments to give at the execution of the
     * task by the one given in parameters
     * @param a string representing the arguments to give
     * while executing the task
     */
	public void setCmdArg(String cmdArg);
	
    /**
     * Return the string of the stdin to give while executing 
     * the task
     * @return he string of the stdin to give while executing 
     * the task
     */
	public String getStdIn();  
	
    /**
     * Change the stdin to give at the execution of the task by
     * the one given in parameters
     * @param a string representing the stdin to give while executing 
     * the task
     */
	public void setStdIn(String stdIn);
	
}
