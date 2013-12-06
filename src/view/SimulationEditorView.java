package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import model.FleetEditorModel;
import model.MapEditorModel;
import model.SimulationEditorModel;
import model.Street;
import model.Vehicle;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorView extends JPanel implements Observer{

	private SimulationEditorModel model = new SimulationEditorModel();
	
	private JPanel mapArea;
	private JPanel vehicleArea;
	private JPanel vehicleOptionsArea;
	private JPanel vehicleSelectionArea;
	private JPanel simulationOptionsArea;
	
	private JPanel disruptionPanel;

	
	private JRadioButton fastestPathJRB;
	private JRadioButton shortestPathJRB;
	private JRadioButton lowestGasConsumptionJRB;
	private JRadioButton ignoreSpeedLimitJRB;
	
	private ButtonGroup vehicleOptionBG;
	
	private JLabel streetInfo;
	private JLabel vehicleJL;
	
	private JLabel	delayJL;
	private JComboBox<Integer> delayJCB;
	private Integer[] delay;
 
	
	private JButton startJB;
	private JButton finishJB;		
	private JButton simulationJB;
	
	private JButton closeStreetJB;
	
	private JButton nextVehicleJB;
	private JButton previousVehicleJB;

	private ImageIcon vehicleII;
	
	private ImageIcon carII;
	private ImageIcon carFinishII;
	
	private boolean inSimulation = false;

	
	public SimulationEditorView() {
		
		initComponents();
		
	}
	
	private void initComponents() {
		
		// Map Area
		mapArea = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		
		
		this.setLayout(null);
		mapArea.setBounds(10, 10, 900, 800);
		mapArea.setBackground(Color.WHITE);
		mapArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		this.add(mapArea);
		
		// Details
		streetInfo = new JLabel();
		streetInfo.setBounds(920, 10, 300, 30);
		
		
		this.add(streetInfo);
		
		
		// Vehicle Area
		vehicleArea = new JPanel();
		vehicleArea.setBounds(920, 0, 250, 800);
		vehicleArea.setBackground(Color.WHITE);
//		vehicleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		vehicleArea.setLayout(null);

		
		// VehicleSelectionArea
		vehicleSelectionArea = new JPanel();
		vehicleSelectionArea.setPreferredSize(new Dimension(vehicleArea.getWidth(), 250));
		vehicleSelectionArea.setBounds(0, 0, vehicleArea.getWidth(), 250);

//		vehicleSelectionArea.setBackground(Color.BLACK);
//		vehicleSelectionArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		vehicleSelectionArea.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();		
		gbc.insets = new Insets( 5, 5, 5, 5 );	
		gbc.anchor = GridBagConstraints.WEST;	
//		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		previousVehicleJB = new JButton("<<<");
		vehicleSelectionArea.add (previousVehicleJB, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		nextVehicleJB = new JButton(">>>");
		vehicleSelectionArea.add (nextVehicleJB, gbc);

	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;

		//TODO: if else weghauen
		if(model.getFleetEditorModel().getVehicles().size() > 0){
				vehicleII = new ImageIcon(model.getFleetEditorModel().getVehicles().get(0).getVehicleTypes().getUrlVehicle());
				vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
				vehicleJL = new JLabel(vehicleII);
		}else{
			vehicleJL = new JLabel();
			vehicleJL.setPreferredSize(new Dimension(200, 200));;
		}
		vehicleSelectionArea.add (vehicleJL, gbc);

		vehicleArea.add(vehicleSelectionArea);	

		this.add(vehicleArea);
		
		
		//vehicel options 
		
		vehicleOptionsArea = new JPanel();
		vehicleOptionsArea.setLayout(null);
		vehicleOptionsArea.setBounds(0, 260, vehicleArea.getWidth(), vehicleArea.getHeight() - 260);
		vehicleOptionsArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		
		vehicleArea.add(vehicleOptionsArea);
		// Start Button
		
		startJB = new JButton("start");
		startJB.setBounds(0, 480, 100, 30);

		vehicleOptionsArea.add(startJB);
		
		
		// Finish JButton
		finishJB = new JButton("finish");
		finishJB.setBounds(110, 480, 100, 30);

		vehicleOptionsArea.add(finishJB);
		
		//Simulation JButton
		simulationJB = new JButton("Simulation");
		simulationJB.setBounds(0, 440, 100, 30);

		disruptionPanel = new JPanel();
		disruptionPanel.setBorder(BorderFactory.createTitledBorder("Close"));
		disruptionPanel.setBounds(920, 550, 250, 250);
		disruptionPanel.setVisible(false);
		this.add(disruptionPanel);
		
		closeStreetJB = new JButton("Close street");
		closeStreetJB.setBounds(10, 10, 100, 30);
		disruptionPanel.add(closeStreetJB);
		
		vehicleOptionsArea.add(simulationJB);
		
		
		shortestPathJRB = new JRadioButton("shortest path");
		shortestPathJRB.setBounds(10,10,200,30);
		fastestPathJRB = new JRadioButton("fastest path");
		fastestPathJRB.setBounds(10,50,200,30);
		lowestGasConsumptionJRB = new JRadioButton("lowest gas consumption");
		lowestGasConsumptionJRB.setBounds(10, 90, 200, 30);
		ignoreSpeedLimitJRB = new JRadioButton("ignore speedlimit");
		ignoreSpeedLimitJRB.setBounds(10, 130, 200, 30);

		delay = new Integer[60];
		
		for(int i = 0; i < 60; i++){
			delay[i] = new Integer(i);
		}

		delayJL = new JLabel("Delay");
		delayJL.setBounds(10, 170, 50, 30);
		delayJCB = new JComboBox<Integer>(delay);
		delayJCB.setBounds(60, 170, 50, 30);
		delayJCB.setSelectedIndex(0);
		
		
		vehicleOptionBG = new ButtonGroup();
		vehicleOptionBG.add(fastestPathJRB);
		vehicleOptionBG.add(shortestPathJRB);
		vehicleOptionBG.add(lowestGasConsumptionJRB);
		vehicleOptionBG.add(ignoreSpeedLimitJRB);

		vehicleOptionsArea.add(fastestPathJRB);
		vehicleOptionsArea.add(shortestPathJRB);
		vehicleOptionsArea.add(lowestGasConsumptionJRB);
		vehicleOptionsArea.add(ignoreSpeedLimitJRB);
		vehicleOptionsArea.add(delayJL);
		vehicleOptionsArea.add(delayJCB);
		
		simulationOptionsArea = new JPanel();
		simulationOptionsArea.setLayout(null);
		simulationOptionsArea.setBounds(0, 260, vehicleArea.getWidth(), vehicleArea.getHeight() - 260);
		vehicleArea.add(simulationOptionsArea);

		
	}

	
	public JPanel getMapArea() {
		return this.mapArea;
	}
		
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		DrawingUtil.drawMap(g2d, model.getMapEditorModel());

		for(Street street : model.getMapEditorModel().getStreets()) {
			if(street.isClosed()) {
				g2d.setColor(new Color(204, 204, 204));
				g2d.draw(DrawingUtil.convertStreetToLine(street));
			}
		}
		
		// display selected car in vehicle area
		if(model.getFleetEditorModel().getVehicles().size() > 0){
			
			vehicleII = new ImageIcon(model.getFleetEditorModel().getVehicles().get(model.getFleetEditorModel().getVehiclePos()).getVehicleTypes().getUrlVehicle());	
			vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
			vehicleJL.setIcon(vehicleII);
		
			
			shortestPathJRB.setEnabled(true);
			fastestPathJRB.setEnabled(true);
			lowestGasConsumptionJRB.setEnabled(true);
			ignoreSpeedLimitJRB.setEnabled(true);

			
			
			switch (model.getFleetEditorModel().getVehicles().get(model.getFleetEditorModel().getVehiclePos()).getSimulationOption()){
			
			case SHORTEST_PATH:	
				shortestPathJRB.setSelected(true);
				break;
			case FASTEST_PATH:	
				fastestPathJRB.setSelected(true);
				break;
			case LOWEST_GAS_CONSUMPTION:	
				lowestGasConsumptionJRB.setSelected(true);
				break;
			case IGNORE_SPEEDLIMIT:	
				ignoreSpeedLimitJRB.setSelected(true);
				break;
				
			}
			
			
			delayJCB.setSelectedItem(new Integer(model.getFleetEditorModel().getVehicles().get(model.getFleetEditorModel().getVehiclePos()).getDelay()));

			
		}else{
			
			shortestPathJRB.setEnabled(false);
			fastestPathJRB.setEnabled(false);
			lowestGasConsumptionJRB.setEnabled(false);
			ignoreSpeedLimitJRB.setEnabled(false);

		}
		
		// display cars on mapArea
		for(Vehicle v : model.getFleetEditorModel().getVehicles()){
			
			carII = new ImageIcon(v.getVehicleTypes().getUrlVehicle());
			carII.setImage(carII.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT));
			
			Border b = BorderFactory.createLineBorder(Color.black);
			
			if(v.getStartKnot() != null){
				
				carII.paintIcon(this, g2d, v.getStartKnot().getX() - carII.getIconWidth()/2, v.getStartKnot().getY() - carII.getIconHeight()/2);
//				carII.
			}
			if(v.getFinishKnot() != null){
				carFinishII = new ImageIcon(v.getVehicleTypes().getUrlFinish());
				carFinishII.setImage(carFinishII.getImage().getScaledInstance(35, 25,Image.SCALE_DEFAULT));

				carFinishII.paintIcon(this, g2d, v.getFinishKnot().getX() - carFinishII.getIconWidth()/2, v.getFinishKnot().getY() - carFinishII.getIconHeight()/2);
			
			}

			
			if(v.getCurrentPosition()!= null && ! v.getNextKnot().equals(v.getCurrentPosition())){
				
				carII.setImage(carII.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT));
				carII.paintIcon(this, g2d, v.getCurrentPosition().getX() - carII.getIconWidth()/2, v.getCurrentPosition().getY() - carII.getIconHeight()/2);			
		
			}

		}
	}
	
	public void update(Observable model, Object value) {

		if (model instanceof SimulationEditorModel) {
			
			this.model = (SimulationEditorModel) model; 
			
			repaint();
//			vehicleArea.repaint();
//			mapArea.paintImmediately(0, 0, mapArea.getWidth(), mapArea.getHeight());
		}
		
	}

	/**
	 * @param mapEditorModel the mapEditorModel to set
	 */
	public void setMapEditorModel(MapEditorModel mapModel) {
		this.model.setMapEditorModel(mapModel);
	}
	/**
	 * @param fleetEditorModel the fleetEditorModel to set
	 */
	public void setFleetEditorModel(FleetEditorModel fem) {
		this.model.setFleetEditorModel(fem);
	}

	/**
	 * @return the finishJB
	 */
	public JButton getFinishJB() {
		return finishJB;
	}

	/**
	 * @return the startJB
	 */
	public JButton getStartJB() {
		return startJB;
	}

	/**
	 * @return the simulationJB
	 */
	public JButton getSimulationJB() {
		return simulationJB;
	}

	/**
	 * @return the nextVehicleJB
	 */
	public JButton getNextVehicleJB() {
		return nextVehicleJB;
	}

	/**
	 * @return the previousVehicleJB
	 */
	public JButton getPreviousVehicleJB() {
		return previousVehicleJB;
	}

	public JButton getCloseStreetJB() {
		return this.closeStreetJB;
	}

	/**
	 * @return the inSimulation
	 */
	public boolean isInSimulation() {
		return inSimulation;
	}

	/**
	 * @param inSimulation the inSimulation to set
	 */
	public void setInSimulation(boolean inSimulation) {
//		this.inSimulation = inSimulation;
		this.vehicleArea.setVisible(!inSimulation);
		this.disruptionPanel.setVisible(inSimulation);
	}

	/**
	 * @return the fastestPathJRB
	 */
	public JRadioButton getFastestPathJRB() {
		return fastestPathJRB;
	}

	/**
	 * @param fastestPathJRB the fastestPathJRB to set
	 */
	public void setFastestPathJRB(JRadioButton fastestPathJRB) {
		this.fastestPathJRB = fastestPathJRB;
	}

	/**
	 * @return the shortestPathJRB
	 */
	public JRadioButton getShortestPathJRB() {
		return shortestPathJRB;
	}

	/**
	 * @param shortestPathJRB the shortestPathJRB to set
	 */
	public void setShortestPathJRB(JRadioButton shortestPathJRB) {
		this.shortestPathJRB = shortestPathJRB;
	}

	/**
	 * @return the lowestGasConsumptionJRB
	 */
	public JRadioButton getLowestGasConsumptionJRB() {
		return lowestGasConsumptionJRB;
	}

	/**
	 * @param lowestGasConsumptionJRB the lowestGasConsumptionJRB to set
	 */
	public void setLowestGasConsumptionJRB(JRadioButton lowestGasConsumptionJRB) {
		this.lowestGasConsumptionJRB = lowestGasConsumptionJRB;
	}

	/**
	 * @return the ignoreSpeedLimitJRB
	 */
	public JRadioButton getIgnoreSpeedLimitJRB() {
		return ignoreSpeedLimitJRB;
	}

	/**
	 * @param ignoreSpeedLimitJRB the ignoreSpeedLimitJRB to set
	 */
	public void setIgnoreSpeedLimitJRB(JRadioButton ignoreSpeedLimitJRB) {
		this.ignoreSpeedLimitJRB = ignoreSpeedLimitJRB;
	}

	/**
	 * @return the delayJCB
	 */
	public JComboBox<Integer> getDelayJCB() {
		return delayJCB;
	}

	/**
	 * @param delayJCB the delayJCB to set
	 */
	public void setDelayJCB(JComboBox<Integer> delayJCB) {
		this.delayJCB = delayJCB;
	}

	
	
}
