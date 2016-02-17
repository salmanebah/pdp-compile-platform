package pdp.compileplatform.session;

/**
 * This interface presents the different methods that must be implemented for a ClientManager.
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see ClientManager
 * @see ClientInterface
 * @see Client
 */
public interface ClientsManagerInterface {
		
	/**
	 * Create and add a ClientInterface to the ClientManager
	 * @return the ClientInterface created
	 */
	public ClientInterface addClient();
	
	/**
	 * Return the ClientInterface corresponding to the sessionKey given in the parameters
	 * @return the ClientInterface corresponding to the sessionKey given in the parameters (null if unknown sessionKey)
	 */
	public ClientInterface getClient(String sessionKey); 
	
	/**
	 * Remove a ClientInterface from the ClientManager
	 * @param the ClientInterface to remove
	 */
	void removeClient(ClientInterface client);

	/**
	 * Change the session cleaning time frequency 
	 * @param the session cleaning time frequency in minutes
	 */
	public void setSessionCleanerTimer(int timeInMinutes);
	
	/**
	 * Change the amount of time from which a client is considered as inactive
	 * @param durationInMinutes the amount of time from which a client is considered as inactive
	 */
	public void setMaxClientInactivityDuration(int durationInHour);
	
	
	/**
	 * Change the amount of time from which a task is considered as too old
	 * @param durationInMinutes the amount of time from which a task is considered 
	 * as too old
	 */
	public void setMaxTaskInactivityDuration(int durationInHour);
	
	/**
	 * Count the number of clients in the ClientManager
	 * @return the number of clients in the ClientManager
	 */
	public int countClient();
}
