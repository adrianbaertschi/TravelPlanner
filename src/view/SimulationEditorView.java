package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.text.NumberFormat;
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

import model.entity.Car;
import model.entity.FleetEditorModel;
import model.entity.MapEditorModel;
import model.entity.SimulationEditorModel;
import model.entity.Street;
import model.entity.Vehicle;

import common.Constants;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorView extends JPanel implements Observer{

	private SimulationEditorModel model = SimulationEditorModel.getInstance();
	
	private JPanel mapArea;
	private JPanel vehicleArea;
	private JPanel vehicleSelectionArea;
	private JPanel vehicleOptionsArea;
	private JPanel statisticsJP;

	
	private JPanel buttonsJP;
	private JPanel userDisruptionJP;
	
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
	private JLabel vehicleNameJL;

	private ImageIcon vehicleII;
	
	private ImageIcon carII;
	private ImageIcon carFinishII;
	
	private boolean inSimulation = false;

	private JLabel expectedTimeJL;
	private JLabel simulationDurationJL;
	private JLabel pathLengthJL;
	private JLabel pathJL;
	private JLabel gasUsageJL;
	
	private JLabel expectedTimeValueJL;
	private JLabel simulationDurationValueJL;
	private JLabel pathLengthValueJL;
	private JLabel gasUsageValueJL;
	
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
		
		//TODO: layout mit konstanten
		// Vehicle Area
		vehicleArea = new JPanel();
		vehicleArea.setBounds(920, 10, 250, 800);
//		vehicleArea.setBackground(Color.WHITE);
		vehicleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		vehicleArea.setLayout(null);

		
		// VehicleSelectionArea
		vehicleSelectionArea = new JPanel();
		vehicleSelectionArea.setBorder(BorderFactory.createTitledBorder("Your Vehicles"));
		vehicleSelectionArea.setPreferredSize(new Dimension(vehicleArea.getWidth()-10, 250));
		vehicleSelectionArea.setBounds(5, 5, vehicleArea.getWidth()-10, 255);

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

		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 3;
		gbc.gridwidth = 1;
		vehicleNameJL = new JLabel("Test");
		vehicleSelectionArea.add (vehicleNameJL, gbc);

		
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
			vehicleJL.setPreferredSize(new Dimension(100, 200));;
		}
		vehicleSelectionArea.add (vehicleJL, gbc);

		vehicleArea.add(vehicleSelectionArea);	

		this.add(vehicleArea);
		
		
		//vehicel options area
		
		vehicleOptionsArea = new JPanel();
		vehicleOptionsArea.setLayout(null);
		vehicleOptionsArea.setBounds(5, 265, vehicleArea.getWidth()-10, 160);
		vehicleOptionsArea.setBorder(BorderFactory.createTitledBorder("Simulation Options"));

		//vehicleOptionsArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Simulation Options"));	
		vehicleArea.add(vehicleOptionsArea);
		
		shortestPathJRB = new JRadioButton("shortest path");
		shortestPathJRB.setBounds(15,25,200,15);
		fastestPathJRB = new JRadioButton("fastest path");
		fastestPathJRB.setBounds(15,50,200,15);
		lowestGasConsumptionJRB = new JRadioButton("lowest gas consumption");
		lowestGasConsumptionJRB.setBounds(15, 75, 200, 15);
		ignoreSpeedLimitJRB = new JRadioButton("ignore speedlimit");
		ignoreSpeedLimitJRB.setBounds(15, 100, 200, 15);

		delay = new Integer[60];
		
		for(int i = 0; i < 60; i++){
			delay[i] = new Integer(i);
		}

		delayJCB = new JComboBox<Integer>(delay);
		delayJCB.setBounds(20, 125, 40, 20);
		delayJCB.setSelectedIndex(0);

		delayJL = new JLabel("Delay");
		delayJL.setBounds(70, 125, 50, 20);

		
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
				
		setVehicleOptionsEnabled();

		//statistics jpanel
		
		statisticsJP = new JPanel();
		statisticsJP.setBorder(BorderFactory.createTitledBorder("Statistic"));
		statisticsJP.setLayout(new GridLayout(4, 2));
		statisticsJP.setBounds(5, 430, vehicleArea.getWidth()-10,280);
		statisticsJP.setVisible(true);
		vehicleArea.add(statisticsJP);
		
		expectedTimeJL = new JLabel("Expected Time");
		simulationDurationJL= new JLabel("Simulation Duration");
		pathLengthJL = new JLabel("Path Length");
		gasUsageJL = new JLabel("Gas Usage");
		pathJL = new JLabel("Path");

		expectedTimeValueJL = new JLabel("0");
		simulationDurationValueJL= new JLabel("0");
		pathLengthValueJL = new JLabel("0");
		gasUsageValueJL = new JLabel("0");

		statisticsJP.add(expectedTimeJL);
		statisticsJP.add(expectedTimeValueJL);
		statisticsJP.add(simulationDurationJL);
		statisticsJP.add(simulationDurationValueJL);
		statisticsJP.add(pathLengthJL);
		statisticsJP.add(pathLengthValueJL);
		statisticsJP.add(gasUsageJL);
		statisticsJP.add(gasUsageValueJL);
		
		
		//user disruption
		
		userDisruptionJP = new JPanel();
		userDisruptionJP.setLayout(null);
		userDisruptionJP.setBorder(BorderFactory.createTitledBorder("User Disruption"));
		userDisruptionJP.setBounds(5, 715, vehicleArea.getWidth()-10, vehicleArea.getHeight() - 715-5);
		userDisruptionJP.setVisible(false);

		vehicleArea.add(userDisruptionJP);
		
		closeStreetJB = new JButton("Close street");
		closeStreetJB.setBounds(5, 15, 110, 30);
		userDisruptionJP.add(closeStreetJB);

		
		buttonsJP = new JPanel();
		buttonsJP.setLayout(null);
		//TODO: set title
		buttonsJP.setBorder(BorderFactory.createTitledBorder("Titel"));
		buttonsJP.setBounds(5, 715, vehicleArea.getWidth()-10, vehicleArea.getHeight() - 715-5);
		buttonsJP.setVisible(true);


		// Start Button
		
		startJB = new JButton("start");
		startJB.setBounds(5, 15, 110, 30);

		buttonsJP.add(startJB);		
		
		// Finish JButton
		finishJB = new JButton("finish");
		finishJB.setBounds(125, 15, 110, 30);

		buttonsJP.add(finishJB);
		
		//Simulation JButton
		simulationJB = new JButton("Simulation");
		simulationJB.setBounds(5, 50, 110, 30);
		buttonsJP.add(simulationJB);
		

		vehicleArea.add(buttonsJP);


		
	}

	
	public JPanel getMapArea() {
		return this.mapArea;
	}
		
	private void draw(Graphics g) {
		
		this.requestFocusInWindow();

		Graphics2D g2d = (Graphics2D)g;
		
//		System.out.println(System.currentTimeMillis());
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		DrawingUtil.drawMap(g2d, model.getMapEditorModel());

		for(Street street : model.getMapEditorModel().getStreets()) {
			if(street != model.getMapEditorModel().getSelectedStreet() && street.isClosed()) {
				g2d.setColor(new Color(204, 204, 204));
				g2d.draw(DrawingUtil.convertStreetToLine(street));
			}
		}
		
		// display selected car in vehicle area
		if(model.getFleetEditorModel().getVehicles().size() > 0){
			
			Vehicle vehicle = model.getFleetEditorModel().getVehicles().get(model.getFleetEditorModel().getVehiclePos());
			vehicleII = new ImageIcon(vehicle.getVehicleTypes().getUrlVehicle());	
			vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
			vehicleJL.setIcon(vehicleII);
			vehicleNameJL.setText(vehicle.getName());
			
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
		
			
			//statistics
			updateStatistics(vehicle);

		}
		
		setVehicleOptionsEnabled();
		
		// display cars on mapArea
		for(Vehicle v : model.getFleetEditorModel().getVehicles()){			
			
			carII = new ImageIcon(v.getVehicleTypes().getUrlVehicle());
			carII.setImage(carII.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT));
			
			if(v.getStartNode() != null){
				
				carII.paintIcon(this, g2d, v.getStartNode().getX() - carII.getIconWidth()/2, v.getStartNode().getY() - carII.getIconHeight()/2);
			}
			if(v.getFinishNode() != null){
				carFinishII = new ImageIcon(v.getVehicleTypes().getUrlFinish());
				carFinishII.setImage(carFinishII.getImage().getScaledInstance(35, 25,Image.SCALE_DEFAULT));

				carFinishII.paintIcon(this, g2d, v.getFinishNode().getX() - carFinishII.getIconWidth()/2, v.getFinishNode().getY() - carFinishII.getIconHeight()/2);
			
			}

			
			if(v.getCurrentPosition()!= null && ! v.getNextNode().equals(v.getCurrentPosition())){
				
				carII.setImage(carII.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT));
				carII.paintIcon(this, g2d, v.getCurrentPosition().getX() - carII.getIconWidth()/2, v.getCurrentPosition().getY() - carII.getIconHeight()/2);			
		
			}
			
		}
		
	}
	
	private void updateStatistics(Vehicle v) {

		this.pathLengthValueJL.setText(Double.toString(v.getPathLength()));
//		this.actualTimeValueJL.setText(Double.toString(v.getActualTime()*Constants.TIME_RATIO/3600));
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(2);
		this.simulationDurationValueJL.setText(nf.format(v.getActualTime()));
		if(v instanceof Car){
			
			this.gasUsageValueJL.setText(Double.toString(((Car) v).getGasUsage()));

		}
	}

	public void update(Observable model, Object value) {

		if(Constants.SIMULATION_FINISHED.equals(value)) {
			setInSimulation(false);
		}
		if (model instanceof SimulationEditorModel) {
			
			this.model = (SimulationEditorModel) model; 
//			System.out.println("repaint:");
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
		this.inSimulation = inSimulation;
		this.userDisruptionJP.setVisible(inSimulation);
		this.buttonsJP.setVisible(!inSimulation);
	
	}

	private void setVehicleOptionsEnabled() {

		if(!inSimulation && model.getFleetEditorModel().getVehicles().size() > 0){
			
			//disable VehicleOptions
			shortestPathJRB.setEnabled(true);
			fastestPathJRB.setEnabled(true);
			lowestGasConsumptionJRB.setEnabled(true);
			ignoreSpeedLimitJRB.setEnabled(true);
			delayJCB.setEnabled(true);

			
		}else{
			
			shortestPathJRB.setEnabled(false);
			fastestPathJRB.setEnabled(false);
			lowestGasConsumptionJRB.setEnabled(false);
			ignoreSpeedLimitJRB.setEnabled(false);
			delayJCB.setEnabled(false);

			
		
		}
		
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
