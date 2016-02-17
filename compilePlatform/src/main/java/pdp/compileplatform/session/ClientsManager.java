package pdp.compileplatform.session;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import pdp.compileplatform.frontend.RequestHandler;

/**
 * A concrete implementation of the <tt>ClientInterface</tt> interface 
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see ClientManagerInterface
 * @see ClientInterface
 * @see Client
 *
 */
public class ClientsManager implements ClientsManagerInterface {
	
	protected Map<String, ClientInterface> mClients;
	
	protected long sessionCleanerTimer;
	protected long maxTaskInactivityDuration;
	protected long maxClientInactivityDuration;
	
	protected  final long MilisecondsInSecond = 1000;
	protected  final long MilisecondsInMinute = 60 * MilisecondsInSecond;	
	
	/**
	 * Construct a ClientManager
	 * @param clientsCleanerTimeInMinutes the clients cleaning time frequency in minutes 
	 * @param maxClientInactivityDurationInMinutes the amount of time from which a client is considered as 
	 * too old
	 * @param tasksCleanerTimeInMinutes the tasks cleaning frequency in minutes
	 * @param maxTaskInactivityDurationInMinutes the amount of time from which a task is considered
	 * as too old
	 */
	public ClientsManager(int clientsCleanerTimeInMinutes,
			               int maxClientInactivityDurationInMinutes, 
			               int maxTaskInactivityDurationInMinutes){
		mClients = new HashMap<String, ClientInterface>();
		
		this.sessionCleanerTimer = clientsCleanerTimeInMinutes * MilisecondsInMinute;
		this.maxClientInactivityDuration = maxClientInactivityDurationInMinutes * MilisecondsInMinute;
		
		this.maxTaskInactivityDuration = maxTaskInactivityDurationInMinutes * MilisecondsInMinute;
		
		sessionCleaner();
	}
	
	/**
	 * Create and add a ClientInterface to the ClientManager
	 * @return the ClientInterface created
	 */
	@Override
	public ClientInterface addClient() {
		ClientInterface client = new Client(generateUniqueId());
		mClients.put(client.getSessionKey(), client);
		return client;
	}
	
	/**
	 * Remove a ClientInterface from the ClientManager
	 * @param the ClientInterface to remove
	 */
	@Override
	public void removeClient(ClientInterface client){
		RequestHandler.printLog("session deleted : " + client.getPath());
		mClients.remove(client.getSessionKey());
		client.kill();
	}
	
	/**
	 * Return the ClientInterface corresponding to the sessionKey given in the parameters
	 * @return the ClientInterface corresponding to the sessionKey given in the parameters
	 */
	@Override
	public ClientInterface getClient(String sessionKey) {
		return mClients.get(sessionKey);
	}
	

	/**
	 * Count the number of clients in the ClientManager
	 * @return the number of clients in the ClientManager
	 */
	@Override
	public int countClient() {
		return mClients.size();
	}	
	
	/**
	 * Change the clients cleaning time frequency 
	 * @param the clients cleaning time frequency in minutes
	 */
	@Override
	public void setSessionCleanerTimer(int timeInMinutes){
		this.sessionCleanerTimer = timeInMinutes * MilisecondsInMinute;
	}
	
	/**
	 * Change the amount of time from which a client is considered as inactive
	 * @param durationInMinutes the amount of time from which a client is considered as inactive
	 */
	@Override
	public void setMaxClientInactivityDuration(int durationInMinutes){
		this.maxClientInactivityDuration = durationInMinutes * MilisecondsInMinute;
	}
	
	
	/**
	 * Change the amount of time from which a task is considered as too old
	 * @param durationInMinutes the amount of time from which a task is considered 
	 * as too old
	 */
	@Override
	public void setMaxTaskInactivityDuration(int durationInMinutes){
		this.maxTaskInactivityDuration = durationInMinutes * MilisecondsInMinute;
	}
	
	/**
	 * Generate an unique identifier for client's sessions 
	 * @return a digest to be used as client session key
	 */
	protected String generateUniqueId() {
		try{
			Thread.sleep(1);
		}catch(InterruptedException e) {
			System.out.println("Thread.sleep(1):ERROR");
			e.printStackTrace();
		}
		
		Long currentTime = new Long(System.currentTimeMillis());
		
		MessageDigest md = null;
	    try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	    
	    String timestamp = currentTime.toString();
	    String plainText = timestamp + "Docker2014";
	    try {
			md.update(plainText.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e){
			e.printStackTrace();
		}
	    
	    byte raw[] = md.digest();
	    /* ensuring the uniqueness of the id */
	    return convertBytesToString(raw) + timestamp;
	}
	
	/**
	 * Convert a byte array representation to string
	 * @param raw the byte array to convert
	 * @return a string representation 
	 */
	private static String convertBytesToString(byte[] raw){
		StringBuffer hexStr = new StringBuffer();
		for(int i=0; i<raw.length; ++i)
			hexStr.append(Integer.toHexString(0xFF & raw[i]));
	
		return hexStr.toString();
	}
	
	/**
	 * Clean directories for inactive sessions and tasks 
	 */
	private void sessionCleaner() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				RequestHandler.printLog("/!\\ cleanning session(s)");
				List<ClientInterface> lstOldClients = new ArrayList<ClientInterface>();
				for(Entry<String, ClientInterface> entry : mClients.entrySet()) {
					ClientInterface client = entry.getValue();
					if (client.inactiveSince() > maxClientInactivityDuration){
						lstOldClients.add(client);	
					}
				}
				for (ClientInterface client : lstOldClients) {
					ClientsManager.this.removeClient(client);
				}
		
				RequestHandler.printLog("/!\\ cleanning task(s)");
				for(Entry<String, ClientInterface> entry : mClients.entrySet()) {
					ClientInterface client = entry.getValue();
					client.cleanTasks(maxTaskInactivityDuration);					
				} 
			}
		}, sessionCleanerTimer, sessionCleanerTimer);		
	}
}
