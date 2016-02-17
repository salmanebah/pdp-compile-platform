package pdp.compileplatform.execution;

import java.util.Deque;
import java.util.LinkedList;


import pdp.compileplatform.session.TaskInterface;

/**
 * A concrete implementation of the <tt>TaskQueueInterface</tt> interface 
 * using the {@link java.util.Deque Deque} interface 
 * 
 * @author Salmane Bah
 * @author Tristan Braquelaire
 * @author Wassim Romdan
 * @author Timothee Sollaud
 * @see TaskQueueInterface
 * @version 1.0
 *
 */

public class TaskQueue implements TaskQueueInterface{
	
	private Deque<TaskInterface> llstTask;		
	
	
	/** Construct a TaskQueue */	
	public TaskQueue(){
		this.llstTask = new LinkedList<TaskInterface>();
	}
	
	/**
	 * Add a task to the queue 
	 * @param task the object TaskInterface to add in the queue
	 */	
	@Override
	public void addTask(TaskInterface task){
		this.llstTask.add(task);
	}
	
	
	/**
	 * Get the oldest task in the queue
	 * @return The oldest TaskInterface in the queue
	 */	
	@Override
	public TaskInterface getTask(){
		if (llstTask.isEmpty())
			return null;
		TaskInterface task = llstTask.poll();
		return task;
	}
	
	/**
	 * Remove a task from the queue. T 
	 * @param task the object task to remove from the queue
	 * @throw NullPointerException if the queue contains no such element
	 */	
	@Override
	public void removeTask(TaskInterface task){
		try {
			this.llstTask.remove(task);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Return true if the queue is empty
	 * @return true if the queue contains no element
	 */	
	@Override
	public boolean isEmpty(){
		return this.llstTask.isEmpty();
	}
	
	/**
	 * Count the number of elements contained in the queue
	 * @return an int representing the number of element in the queue
	 */	
	@Override
	public int count(){
		return this.llstTask.size();
	}
}
