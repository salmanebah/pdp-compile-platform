package pdp.compileplatform.execution;

import pdp.compileplatform.session.TaskInterface;

/**
*
* This interface presents the basics methods that a queue object managing 
* tasks must implement. 
*
* @author Salmane Bah
* @author Tristan Braquelaire
* @author Wassim Romdan
* @author Timothee Sollaud
* @see pdp.compileplatform.session.TaskInterface
* @see pdp.compileplatform.session.Task
* @version 1.0
*/

public interface TaskQueueInterface {
	
	/** Add a task to the queue 
	 * @param task the object TaskInterface to add in the queue
	 */	
	public void addTask(TaskInterface task);
	
	/** Get the most recent task from the queue*/
	public TaskInterface getTask();
	
	/** Remove a task from the queue 
	 * @param task the object task to remove from the queue
	 */
	public void removeTask(TaskInterface task);
	
	/** Specify if the queue contains no element 
	 * @return true if the queue contains no element
	 */
	public boolean isEmpty();
	
	/** Count the number of elements contained in the queue 
	 * return an int representing the number of element in the queue
	 */
	public int count();
	
}
