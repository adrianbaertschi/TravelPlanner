package view;

import java.awt.BasicStroke;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

	private static final Color NODE_COLOR = 	Color.DARK_GRAY;
	private static final Color SELECTED_COLOR = Color.CYAN;

	private SimulationEditorModel model = new SimulationEditorModel();
	
	private JPanel mapArea;
	private JPanel vehicleArea;
	private JPanel vehicleSelectionArea;
	private JLabel streetInfo;
	private JLabel vehicleJL;
	
	private ImageIcon finishII;
	
	private JButton startJB;
	private JButton finishJB;		
	private JButton simulationJB;
	
	private JButton closeStreetJB;
	
	private JButton nextVehicleJB;
	private JButton previousVehicleJB;

	private ImageIcon vehicleII;
	
	private ImageIcon img;

	
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
		vehicleArea.setBounds(920, 10, 1200-910-40, 520);
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
			

		// Start Button
		
		startJB = new JButton("start");
		startJB.setBounds(0, 480, 100, 30);

		vehicleArea.add(startJB);
		
		
		// Finish JButton
		finishII = new ImageIcon("images/finish.jpg");
		finishII.setImage(finishII.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		finishJB = new JButton(finishII);
		finishJB.setBounds(110, 480, 100, 30);

		vehicleArea.add(finishJB);
		
		//Simulation JButton
		simulationJB = new JButton("Simulation");
		simulationJB.setBounds(0, 440, 100, 30);

		vehicleArea.add(simulationJB);
		
		JPanel disruptionPanel = new JPanel();
		disruptionPanel.setBorder(BorderFactory.createTitledBorder("Close"));
		disruptionPanel.setBounds(920, 550, 250, 250); //920, 10, 1200-910-40, 520);
		this.add(disruptionPanel);
		
		closeStreetJB = new JButton("Close street");
		closeStreetJB.setBounds(10, 10, 100, 30);
		disruptionPanel.add(closeStreetJB);
	}

	
	public JPanel getMapArea() {
		return this.mapArea;
	}
		
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : model.getMapEditorModel().getStreets()) {
			
			
			g2d.setColor(Color.DARK_GRAY);
			
			g2d.fillOval(street.getStart().getX() -5, street.getStart().getY() -5, 10, 10);
			g2d.fillOval(street.getEnd().getX() -5, street.getEnd().getY() -5, 10, 10);
			
			if(model.getMapEditorModel().getSelectedKnot() != null) {
				g2d.setColor(Color.CYAN);
				g2d.fillOval(model.getMapEditorModel().getSelectedKnot().getX() -5, model.getMapEditorModel().getSelectedKnot().getY() -5, 10, 10);
			}

			
			// Strassen
			if(street == model.getMapEditorModel().getSelectedStreet()) {
				g2d.setColor(SELECTED_COLOR);
			} else if(street.isClosed()) {
				g2d.setColor(Color.GREEN);
			} else {
				g2d.setColor(street.getStreetType().getColor());
			}
			g2d.setStroke(new BasicStroke(3));
			g2d.drawLine(street.getStart().getX(), street.getStart().getY(), street.getEnd().getX(), street.getEnd().getY());
		
		
		
		}		
		
		
		// display selected car in vehicle area
		if(model.getFleetEditorModel().getVehicles().size() > 0){
			vehicleII = new ImageIcon(model.getFleetEditorModel().getVehicles().get(model.getFleetEditorModel().getVehiclePos()).getVehicleTypes().getUrlVehicle());	
			vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
			vehicleJL.setIcon(vehicleII);
		}
//		for(Vehicle v : model.getFleetEditorModel().getVehicles()){
//			
//			if(v.getIsSelected()){
//				
//				vehicleII = new ImageIcon(v.getImageURL());
//
//				vehicleII.setImage(vehicleII.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));
//
//				vehicleJL = new JLabel(vehicleII, SwingConstants.CENTER);
//				
//			}
//			
//		}
		
		// display cars on mapArea
		for(Vehicle v : model.getFleetEditorModel().getVehicles()){
			
			img = new ImageIcon(v.getVehicleTypes().getUrlVehicle());
			img.setImage(img.getImage().getScaledInstance(30, 30,Image.SCALE_DEFAULT));
			if(v.getStartKnot() != null){
				
				img.paintIcon(this, g2d, v.getStartKnot().getX() - img.getIconWidth()/2, v.getStartKnot().getY() - img.getIconHeight()/2);
			
			}
			if(v.getFinishKnot() != null){
				
				img.paintIcon(this, g2d, v.getFinishKnot().getX() - img.getIconWidth()/2, v.getFinishKnot().getY() - img.getIconHeight()/2);
			
			}

			
			if(v.getCurrentPosition()!= null && ! v.getNextKnot().equals(v.getCurrentPosition())){
				
				img.setImage(img.getImage().getScaledInstance(15, 15,Image.SCALE_DEFAULT));
				img.paintIcon(this, g2d, v.getCurrentPosition().getX() - img.getIconWidth()/2, v.getCurrentPosition().getY() - img.getIconHeight()/2);			
		
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

	
}
