package pdp.compileplatform.frontend;
import java.util.concurrent.Executors;

import javax.xml.ws.Endpoint;
import com.beust.jcommander.JCommander;

/**
 * The entry point of the server. Parses the command line arguments for option, 
 * create the web service with a <tt> RequestListenerInterface </tt> implementor , set 
 * the execution thread pool and then publish the web service. 
 *
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see CmdLineParser
 * @see RequestListenerInterface
 * @see RequestListener
 * 
 *
 */
public class EntryPoint {
	public static void main(String[] args)
	{

		CmdLineParser cli = new CmdLineParser();
		new JCommander(cli , args);
		if (cli.help == true)
		{
			CmdLineParser.printHelp();
			System.exit(1);
		}
		String dockerImg = cli.dockerImg;
		double dockerExecTime = cli.dockerExecTime;
		String dockerMemLimit = cli.dockerMemLimit;
		int sessionCleanerTimeInMinutes = cli.sessionCleanerTime;
		int sessionMaxInactivityInMinutes = cli.sessionMaxInactivity;
		int taskMaxInactivityInMinutes = cli.taskMaxInactivity;
		int nbThreads = cli.nbThreads;

		RequestListenerInterface listener = 
				new RequestListener(dockerImg ,
									dockerExecTime, dockerMemLimit,
				        		    sessionCleanerTimeInMinutes, 
				        		    sessionMaxInactivityInMinutes,
						            taskMaxInactivityInMinutes);
		Endpoint entryPoint = Endpoint.create(listener);
		entryPoint.setExecutor(Executors.newFixedThreadPool(nbThreads));
		entryPoint.publish("http://localhost:8888/ws/compileplatform");
		System.out.println("Server successfully loaded!");
	}

}
