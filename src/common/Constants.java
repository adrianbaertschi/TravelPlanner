package common;

public interface Constants {
	
	/**
	 * Höhe eines Nodes in px
	 */
	public static final int NODE_HEIGHT = 	10;
	
	/**
	 * Breite eines Nodes in px
	 */
	public static final int NODE_WIDTH = 	NODE_HEIGHT;
	
	/**
	 * Noderadius in px
	 */
	public static final int NODE_RADIUS = 	NODE_HEIGHT / 2;
	
	/**
	 * Distance if driving behind slower vehicle on no-passing street
	 */
	public static final int DISTANCE_NO_PASSING = 20;
	
	/**
	 * 
	 */
	public static final String SIMULATION_FINISHED = "SIMULATION_FINISHED";
	
	/**
	 * Ratio to calculate simulation time into realtime
	 */
	public static final double TIME_RATIO = 1.955;
	
	/**
	 * Distance in px to check if user clicked on street
	 */
	public static final int CLICK_TOLERANCE_STREET = 8;

}
