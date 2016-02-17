package pdp.compileplatform.session;

/**
 * An enumeration of the possible types of results for the execution of a task.
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see GetDetailsRet
 * @see GetStatusRet 
 */
public enum EnumResult
{
	ERROR(-1, "error"),
    SUCESS(0, "success"),
    COMP_ERROR(1, "compilation error"),
    EXEC_ERROR(2, "execution error"),
    UNKNOWN(3, "unknown");
    
    private int resultCode;
    private String description;
    
    private EnumResult(int resultCode, String description)
    {
    	this.resultCode = resultCode;
    	this.description = description;
    }
    
    /**
     * Return the result of the task
     * @return the int corresponding to the result of the task
     */
    public int getResult()
    {
    	return resultCode;
    }
    
    /**
     * Return the result's message of the task
     * @return a string corresponding to the result's message of the task
     */
    public String getDescription(){
    	return description;
    }

}
