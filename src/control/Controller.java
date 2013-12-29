package control;

import java.awt.Component;
/**
 * Base Interface for all Controller Classes
 * 
 */
public interface Controller {
	
	/**
	 * Show the view that belongs to the controller
	 * @return The View
	 */
	public Component showView();
	
	/**
	 * Load a new model instance
	 * @param o Model instance
	 */
	public void setModel(Object o);
	
}
