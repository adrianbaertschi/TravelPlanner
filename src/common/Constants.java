package common;

public interface Constants {
	
	/**
	 * Höhe eines Knoten in px
	 */
	public static final int NODE_HEIGHT = 	10;
	
	/**
	 * Breite eines Knoten in px
	 */
	public static final int NODE_WIDTH = 	NODE_HEIGHT;
	
	/**
	 * Knotenradius in px
	 */
	public static final int NODE_RADIUS = 	NODE_HEIGHT / 2;
	
	/**
	 * Distance if driving behind slower vehicle on no-passing street
	 */
	public static final int DISTANCE_NO_PASSING = 20;
	
	public static final String SIMULATION_FINISHED = "SIMULATION_FINISHED";
	
	public static final double TIME_RATIO = 1.955;
	
	public static final String MSG_NO_PATH = "No path exists for car %s, please change Start/Finish positions"; 

}
