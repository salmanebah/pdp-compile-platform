package pdp.compileplatform.frontend;


/**
 * This interface presents the services provided by the platform (public requests 
 * are handled by an object implementing this interface). An implementor 
 * must first check that the parameters are valid 
 *
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see RequestHandler
 * 
 *
 */
public interface RequestHandlerInterface{
	/**
	 * connects a client on the server in order to compile, interpret or compile + execute
	 * source code 
	 * @return an object encapsulating the session key (a digest) to use in subsequent call to other 
	 * web method of the server (except getLanguages method) , status and result of the call
	 */
     public ConnectRet connect();
     
     /**
 	 * disconnects a client from the server , all client specific data are removed after
 	 * @param sessionKey the session key as returned by precedent call to 
 	 * {@link #connect() connect}
 	 * @return an object indicating whether the request succeed or not 
 	 */
     public InfoKey disconnect(String sessionKey);
     
     /**
 	 * retrieves languages supported by the platform , does not require a 
 	 * {@link #connect() connection}
 	 * @return an object encapsulating the languages and information about the query
 	 */
     public GetLanguagesRet getLanguages();


 	/**
 	 * submits a compilation, interpretation or compilation + execution to the server
 	 * 
 	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
 	 * @param codeLang the id of the language to use as returned by {@link #getLanguages() getLanguages}
 	 * @param srcCode  the source code to compile or interpret or compile and execute 
 	 * @param compilOpt compilation or interpretation option
 	 * @param cmdArgs  command line arguments to feed the program
 	 * @param stdIn standard input to give to the program
 	 * @param toRun indicate whether the program should be executed, meaningless for interpreted languages
 	 * @return an object encapsulating the task id created by this request and information about the query 
 	 */
     public SendRequestRet sendRequest(String sessionKey,
    		 						   int codeLang, 
    		 						   String srcCode,
    		 						   String compilOpt,
    		 						   String cmdArgs,
    		 						   String stdIn, 
    		 						   boolean toRun);

     /**
 	 * runs previously  submitted task 
 	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
 	 * @param taskReference the task id as returned by 
 	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
 	 * @param cmdArgs command line argument to feed the program
 	 * @param stdIn standard input to give to the program
 	 * @return an object encapsulating information about the query
 	 */
     public InfoKey run(String sessionKey,
    		 	        int taskReference,
    		 	        String cmdArgs,
    		 	        String stdIn);
     
     
     /**
 	 * retrieves details about a submitted task
 	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
 	 * @param taskReference taskReference the task id as returned by 
 	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
 	 * @return an object encapsulating task information (standard output , standard error , 
 	 * execution duration , status , ...) and information about the query
 	 */
     public GetDetailsRet getDetails(String sessionKey,
    		 						 int taskReference);
     
     /**
 	 * retrieves status about a submitted task
 	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
 	 * @param taskReference taskReference taskReference the task id as returned by 
 	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
 	 * @return an object encapsulating task status (return code , result,...) and information about 
 	 * the query
 	 */
     public GetStatusRet getStatus(String sessionKey,
    		 					   int taskReference);
}
