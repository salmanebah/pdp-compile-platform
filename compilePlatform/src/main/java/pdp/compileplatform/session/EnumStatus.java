package pdp.compileplatform.session;

/**
 * A concrete implementation of the <tt>ClientInterface</tt> interface 
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
public enum EnumStatus
{
	ERROR(-1, "error"),
    DONE(0, "done"),
    ON_COMP(1, "on compilation"),
    ON_EXEC(2, "on execution"),
    ON_INTERPRET(3, "on interpretation"),
    WAITING(4, "waiting");
    
    private int status;
    private String description;
    
    private EnumStatus(int status, String description)
    {
    	this.status = status;
    	this.description = description;
    }
    
    /**
     * Return the task's status
     * @return int corresponding to the task's status
     */
    public int getStatus()
    {
    	return this.status;
    }
    
    /**
     * Return the description message of the task's status
     * @return a string corresponding to the task's status
     */
    public String getDescription(){
    	return this.description;
    }
}
