package pdp.compileplatform.frontend;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;



/**
 * This interface presents the platform API as seen by clients,
 * all public services that the server can handle
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see RequestListener
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


@WebService(name="CompilePlatform" , 
	        targetNamespace="http://pdp.compileplatform.frontend")
@SOAPBinding(style=Style.RPC)
public interface RequestListenerInterface {
	/**
	 * web method to connect on the server in order to compile, interpret or compile + execute
	 * source code 
	 * @return an object encapsulating the session key (a digest) to use in subsequent call to other 
	 * web method of the server (except getLanguages method) , status and result of the call
	 */
	@WebMethod
	@WebResult(partName="connectResult")
	ConnectRet connect();
	
	/**
	 * web method to disconnect from the server , all client specific data are removed after
	 * @param sessionKey the session key as returned by precedent call to 
	 * {@link #connect() connect}
	 * @return an object indicating whether the request succeed or not 
	 */
	@WebMethod
	@WebResult(partName="disconnectResult")
	InfoKey disconnect(@WebParam(partName="sessionKey") String sessionKey);
	
	/**
	 * web method to retrieve languages supported by the platform , does not require a 
	 * {@link #connect() connection}
	 * @return an object encapsulating the languages and information about the query
	 */
	@WebMethod
	@WebResult(partName="getLanguagesResult")
	GetLanguagesRet getLanguages();
	
	/**
	 * web method to submit a compilation, interpretation or compilation + execution to the server
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
	@WebMethod
	@WebResult(partName="sendRequestResult")
	SendRequestRet sendRequest(@WebParam(partName="sessionKey") String sessionKey , 
	                           @WebParam(partName="codeLang") int codeLang , 
	                           @WebParam(partName="srcCode")String srcCode,
							   @WebParam(partName="compilOpt")String compilOpt , 
							   @WebParam(partName="cmdArgs")String cmdArgs , 
							   @WebParam(partName="stdIn")String stdIn,
							   @WebParam(partName="toRun")boolean toRun);
	

	/**
	 * web method to run previously  submitted task 
	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
	 * @param taskReference the task id as returned by 
	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
	 * @param cmdArgs command line argument to feed the program
	 * @param stdIn standard input to give to the program
	 * @return an object encapsulating information about the query
	 */
	@WebMethod
	@WebResult(partName="runRequest")
	InfoKey run(@WebParam(partName="sessionKey") String sessionKey , 
				@WebParam(partName="taskReference") int taskReference , 
				@WebParam(partName="cmdArgs") String cmdArgs,
			    @WebParam(partName="stdIn") String stdIn);
	
	/**
	 * web method to retrieve details about a submitted task
	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
	 * @param taskReference taskReference the task id as returned by 
	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
	 * @return an object encapsulating task information (standard output , standard error , 
	 * execution duration , status , ...) and information about the query
	 */
	@WebMethod
	@WebResult(partName="getDetailsResult")
	GetDetailsRet getDetails(@WebParam(partName="sessionKey")String sessionKey , 
			                 @WebParam(partName="taskReference")int taskReference);
	
	/**
	 * web method to retrieve status about a submitted task
	 * @param sessionKey the key session for the client as returned by {@link #connect() connect}
	 * @param taskReference taskReference taskReference the task id as returned by 
	 * {@link #sendRequest(String, int, String, String, String, String, boolean) sendRequest}
	 * @return an object encapsulating task status (return code , result,...) and information about 
	 * the query
	 */
	@WebMethod
	@WebResult(partName="getStatusResult")
	GetStatusRet  getStatus(@WebParam(partName="sessionKey")String sessionKey , 
			                @WebParam(partName="taskReference")int taskReference);
	

}
