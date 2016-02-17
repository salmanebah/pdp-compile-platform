package pdp.compileplatform.session;

/**
 * This interface presents the different methods that must be implemented for a Client.
 * 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @version 1.0
 * @see Client
 * @see ClientManagerInterface
 * @see TaskInterface
 * @see Task
 */
public interface ClientInterface {
	
	/**
	 * Return the sessionKey of the client
	 * @return the String representing the sessionKey of the client
	 */
	public String getSessionKey();
	
	/**
	 * Return the path to the client's directory on the server
	 * @return a string representing the path to the client's directory 
	 * on the server
	 */
	public String getPath();
	
	/**
	 * Return the duration since the client connected
	 * @return a long corresponding to the duration since the 
	 * client connected
	 */
	public long connectedSince();
	
	/**
	 * Return the duration since the client sent his last request
	 * @return a long corresponding to the duration since the 
	 * client sent his last request
	 */
	public long inactiveSince();
	
	/**
	 * Actualize the date of the client's last action
	 */
	public void inActivity();
	
	/**
	 * Add a task to the client's list of TaskInterface
	 * @param task the TaskInterface to add to the client's list of
	 * TaskInterface 
	 */
	public void addTask(TaskInterface task);
	
	/**
	 * Return the task's id given in parameters
	 * @return the TaskInterface related to the id
	 */
	public TaskInterface getTaskById(int idTask);
	
	/**
	 * Return the id of the TaskInterface given in parameters
	 * @return the id of the TaskInterface given in parameters
	 */
	public int getTaskId(TaskInterface task);
	
	/**
	 * Remove a task from the client's list of TaskInterface
	 * @param task the TaskInterface to remove from the cllient's list 
	 * of TaskInterface
	 */
	public void removeTask(TaskInterface task);
	
	
	/**
	 * Destroy all the old tasks from the client's list of tasks
	 */
	public void cleanTasks(long maxTaskInactivityDuration);
	
	/**
	 * Destroy all the tasks from the client's list of tasks and the 
	 * directory related to the client
	 */
	public void kill();	
}
