package pdp.compileplatform.frontend;

/* 
 * This enumeration represents error codes and their corresponding description to send back
 * to the client
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
public enum EnumRetCode
{
     ERROR(-1, "error"),
     SUCCESS(0, "success"),
     OVERLOADED(1, "server overloaded"),
     UNKNOWN_LANG(2, "unknown language"),
     UNKNOWN_REF(3, "unknown reference"),
     UNKNOWN_KEY_SESSION(4, "unknown key session"),
     NULL_SRC(5, "null pointer for source code"),
     NULL_COMP_OPT(6, "null pointer for compilation option(s)"),
     NULL_CMD_ARG(7 , "null pointer for command line argument(s)"),
     NULL_STDIN(8 , "null pointer for standard input");
     /**
      * code for the error
      */
     private int retCode;
     /**
      * description for the error
      */
     private String description;
     
     /**
      * Constructs a custom EnumRetCode 
      * @param retCode the error code 
      * @param description the description of the error
      */
     private EnumRetCode(int retCode, String description)
     {
    	 this.retCode = retCode;
    	 this.description = description;
     }
     
     /**
      * get the error code associated to this EnumRetCode
      * @return the error code for this EnumRetCode
      */
     public int getRetCode()
     {
    	 return this.retCode;
     }
     
     /**
      * get the description associated to this EnumRetCode
      * @return
      */
     public String getDescription()
     {
    	 return this.description;
     }
}
