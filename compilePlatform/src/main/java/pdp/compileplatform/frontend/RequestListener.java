package pdp.compileplatform.frontend;


import javax.jws.WebService;

/**
 * A concrete implementation of the platform public API delegating all the work to a
 * <tt>RequestHandler</tt> object
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see RequestListenerInterface
 * @see RequestHandlerInterface
 * @see RequestHandler
 * @see InfoKey
 * @see GetStatusRet
 * @see GetLanguagesRet
 * @see GetDetalsRet
 * @see ConnectRet
 * @see SendRequestRet
 *
 */
@WebService(endpointInterface="pdp.compileplatform.frontend.RequestListenerInterface", 
            portName="CompilePlatformPort",
            serviceName="CompilePlatformService")
public class RequestListener implements RequestListenerInterface {
	
	/** the task handler */
	private RequestHandlerInterface handler;
	
	/**
	 * Constructs a RequestListener 
	 * @param dockerImg docker image to use
	 * @param dockerExecTime docker container execution duration
	 * @param dockerMemLimit docker container memory limit
	 * @param sessionCleanerTimeInMinutes time interval to clean sessions
	 * @param sessionMaxInactivityInMinutes maximum time of session inactivity
	 * @param taskCleanerTimeInMinutes time interval to clean tasks
	 * @param taskMaxInactivityInMinutes maximum time of task inactivity
	 */
	
	public RequestListener(String dockerImg,
						   double dockerExecTime, 
			               String dockerMemLimit,
			               int sessionCleanerTimeInMinutes,
			               int sessionMaxInactivityInMinutes,
			               int taskMaxInactivityInMinutes)
	{
		this.handler = new RequestHandler(dockerImg, 
										  dockerExecTime,
				                          dockerMemLimit,
				                          sessionCleanerTimeInMinutes,
				                          sessionMaxInactivityInMinutes,
				                          taskMaxInactivityInMinutes);
	}
	
	/**
	 * connects a client on the server in order to compile, interpret or compile + execute
	 * source code 
	 * @return an object encapsulating the session key (a digest) to use in subsequent call to other 
	 * web method of the server (except getLanguages method) , status and result of the call
	 */
	@Override
	public ConnectRet connect() {
		return handler.connect();
	}

	/**
	 * disconnects a client from the server , all client specific data are removed after
	 * @param sessionKey the session key as returned by precedent call to 
	 * {@link #connect() connect}
	 * @return an object indicating whether the request succeed or not 
	 */
	@Override
	public InfoKey disconnect(String sessionKey) {
		return handler.disconnect(sessionKey);
	}

	
	/**
	 * retrieves languages supported by the platform , does not require a 
	 * {@link #connect() connection}
	 * @return an object encapsulating the languages and information about the query
	 */
	@Override
	public GetLanguagesRet getLanguages() {
		return handler.getLanguages();
	}

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
	@Override
	public SendRequestRet sendRequest(String sessionKey, int codeLang,
			String srcCode, String compilOpt, String cmdArgs, String stdIn,
			boolean toRun) {
		return handler.sendRequest(sessionKey, codeLang, srcCode, 
				                   compilOpt, cmdArgs, stdIn, toRun);
	}

	/**
	 * runs previously  submitted task 
	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
	 * @param taskReference the task id as returned by 
	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
	 * @param cmdArgs command line argument to feed the program
	 * @param stdIn standard input to give to the program
	 * @return an object encapsulating information about the query
	 */
	@Override
	public InfoKey run(String sessionKey, int taskReference, String cmdArgs,
			String stdIn) {
		return handler.run(sessionKey, taskReference, cmdArgs, stdIn);
	}

	/**
	 * retrieves details about a submitted task
	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
	 * @param taskReference taskReference the task id as returned by 
	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
	 * @return an object encapsulating task information (standard output , standard error , 
	 * execution duration , status , ...) and information about the query
	 */
	@Override
	public GetDetailsRet getDetails(String sessionKey, int taskReference) {
		return handler.getDetails(sessionKey, taskReference);
	}
	
	/**
	 * retrieves status about a submitted task
	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
	 * @param taskReference taskReference taskReference the task id as returned by 
	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
	 * @return an object encapsulating task status (return code , result,...) and information about 
	 * the query
	 */
	@Override
	public GetStatusRet getStatus(String sessionKey, int taskReference) {
		return handler.getStatus(sessionKey, taskReference);
	}
	

}
