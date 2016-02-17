package pdp.compileplatform.frontend;
import com.beust.jcommander.Parameter;

/**
 * Parses the command line arguments of the server for option  
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see EntryPoint
 *
 */
public class CmdLineParser {
	private final static String DEF_DOCKER_IMG = "salmanebah/pdp-compile-platform-img"; 
	private final static Double DEF_DOCKER_EXEC_TIME = 4.0; 
	private final static String DEF_DOCKER_MEM_LIMIT = "128m"; 
	private final static int DEF_SESSION_CLEANER_TIME = 15;
	private final static int DEF_SESSION_MAX_INACTIVITY = 1; 
	private final static int DEF_TASK_MAX_INACTIVITY = 1; 
	private final static int DEF_NBTHREAD = 10; 	
	
	@Parameter(names = "--dockerImg" , description = "Docker image to use")
	public String dockerImg = DEF_DOCKER_IMG;
	
	@Parameter(names = "--dockerExecTime" , description = "Request execution time")
	public Double dockerExecTime = DEF_DOCKER_EXEC_TIME;
	
	@Parameter(names = "--dockerMemLimit" , description = "Request memory limit")
	public String dockerMemLimit = DEF_DOCKER_MEM_LIMIT;
	
	@Parameter(names = "--sessionCleanerTime" , description = "Time to clean sessions")
	public Integer sessionCleanerTime = DEF_SESSION_CLEANER_TIME;
	
	
	@Parameter(names = "--sessionMaxInactivity" , description = "Maximum time of session inactivity")
	public Integer sessionMaxInactivity = DEF_SESSION_MAX_INACTIVITY;
	
	@Parameter(names = "--taskMaxInactivity" , description = "Maximum time of task inactivity")
	public Integer taskMaxInactivity = DEF_TASK_MAX_INACTIVITY;
	
	@Parameter(names = "--help" , description = "show help")
	public Boolean help = false;
	
	@Parameter(names = "--threadsNumber" , description = "number of theads in pool")
	public Integer nbThreads = DEF_NBTHREAD;
	
	/**
	 * prints help on how to run the server
	 */
	public static void printHelp()
	{
		System.out.println("EntryPoint [option]* where option can be :");
		System.out.println("--dockerImag <image> : set Docker image to use for requests"
							+ " (default : " + DEF_DOCKER_IMG +")");
		System.out.println("--dockerExecTime <time in sec> : set Docker execution time "
							+ "per request"
							+ " (default : " + DEF_DOCKER_EXEC_TIME +")");
		System.out.println("--dockerMemLimit <limit>[<b|m|g>] : set Docker memory limit"
				           + " per request [optinal b,m,g]"
						   + " (default : " + DEF_DOCKER_MEM_LIMIT +")");
		System.out.println("--sessionCleanerTime <time in min> : set time to clean sessions"
						    + " (default : " + DEF_SESSION_CLEANER_TIME +")");
		System.out.println("--sessionMaxInactivity <time in min> : "
				            + "set maximum time of session inactivity"
							+ " (default : " + DEF_SESSION_MAX_INACTIVITY +")");
		System.out.println("--taskMaxInactivity <time in min> : "
				           + "set maximum time of task inactivity"
						   + " (default : " + DEF_TASK_MAX_INACTIVITY +")");
		System.out.println("--threadsNumber <n> : number of simultaneous connection to handle"
							+ " (default : " + DEF_NBTHREAD +")");
		System.out.println("--help : show this help");
		
	}
}
