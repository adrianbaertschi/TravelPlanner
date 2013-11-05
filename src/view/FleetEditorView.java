package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.FleetEditorModel;
import model.MapEditorModel;
import model.SimulationEditorModel;
import model.Street;
import model.Vehicle;
import model.VehicleType;

public class FleetEditorView extends JPanel implements Observer {

	private FleetEditorModel fleetEditorModel = new FleetEditorModel();

	private JPanel fleetAreaJP;
	
	//vehicle Selection
	private JPanel fleetSelectionAreJP;
	private JPanel fleetSelectionTitleJP;
	private JLabel fleetSelectionTitleJL;
	private JPanel fleetPreviousVehicleJP;
	private JLabel fleetPreviousVehicleJL;
	private ImageIcon fleetPreviousVehicleII;
	private JPanel fleetCurrentVehicleJP;
	private JLabel fleetCurrentVehicleJL;
	private ImageIcon fleetCurrentVehicleII;
	private JPanel fleetNextVehicleJP;
	private JLabel fleetNextVehicleJL;
	private ImageIcon fleetNextVehicleII;	
	private JButton nextFleetVehicleJB;
	private JButton previousFleetVehicleJB;


	// vehicle definition
	private JPanel fleetDefinitionAreJP;
	private JPanel fleetDefinitionTitleJP;
	private JLabel fleetDefinitionTitleJL;
	
	private JPanel fleetDefinitionOptionsJP;
	private JLabel vehicleNameJL;
	private JTextField vehicleNameJTF;
	private JLabel maxSpeedJL;
	
	
	//add vehicle 
	private JPanel addVehicleArea;
	private JPanel addVehicleTitleJP;
	private JLabel addVehicleTitleJL;

	private JPanel vehicleSelectionAreaJP;
	private JLabel vehicleSelectionJL;
	private ImageIcon vehicleSelectionII;

	private JButton nextVehicleJB;
	private JButton previousVehicleJB;
	private JButton addVehicleJB;

	private JButton saveFleetJB;
	private JButton loadFleetJB;
	private JButton deleteFleetJB;
	
	private JButton resetCurrentVehicleJB;
	private JButton deleteCurrentVehcleJB;


	private GridBagConstraints gbc;	

	public FleetEditorView() {

		initComponents();

	}

	private void initComponents() {

		this.setLayout(null);

		
		//fleetArea
		fleetAreaJP = new JPanel(){
	        @Override
            public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    draw(g);
            }
		};

		fleetAreaJP.setLayout(null);
		fleetAreaJP.setBounds(10, 10, 900, 800);
//		fleetAreaJP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		this.add(fleetAreaJP);


		//fleet selctionArea
		fleetSelectionAreJP = new JPanel();
		fleetSelectionAreJP.setBounds(0, 0, 300, 800);
		fleetSelectionAreJP.setBackground(Color.WHITE);
		fleetSelectionAreJP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		fleetAreaJP.add(fleetSelectionAreJP);
		
		fleetSelectionAreJP.setLayout(null);
		
		fleetSelectionTitleJP = new JPanel();
		fleetSelectionTitleJP.setBounds(10, 10, 280, 50);
		fleetSelectionTitleJL = new JLabel("Fleet - Vehicle Selection");
		fleetSelectionTitleJP.add(fleetSelectionTitleJL);
		
		fleetPreviousVehicleJP = new JPanel();
		fleetPreviousVehicleJP.setBounds(300/2-75, 100, 150, 150);
		fleetPreviousVehicleJL = new JLabel();
		fleetPreviousVehicleJP.add(fleetPreviousVehicleJL);
		
		fleetCurrentVehicleJP = new JPanel();
		fleetCurrentVehicleJP.setBounds(300/2-125, 275, 250, 250);
		fleetCurrentVehicleJL = new JLabel();
		fleetCurrentVehicleJP.add(fleetCurrentVehicleJL);
		
		fleetNextVehicleJP = new JPanel();
		fleetNextVehicleJP.setBounds(300/2-75, 550, 150, 150);
		fleetNextVehicleJL = new JLabel();
		fleetNextVehicleJP.add(fleetNextVehicleJL);
		
		fleetSelectionAreJP.add(fleetSelectionTitleJP);
		fleetSelectionAreJP.add(fleetPreviousVehicleJP);
		fleetSelectionAreJP.add(fleetCurrentVehicleJP);
		fleetSelectionAreJP.add(fleetNextVehicleJP);
		
		//TODO:Text im Hochformat
		nextFleetVehicleJB = new JButton(">>>");	
		nextFleetVehicleJB.setBounds(265, 140, 25, 70);
		
		//TODO:Text im Hochformat
		previousFleetVehicleJB = new JButton("<<<");
		previousFleetVehicleJB.setBounds(265, 590, 25, 70);
		
		fleetSelectionAreJP.add(previousFleetVehicleJB);
		fleetSelectionAreJP.add(nextFleetVehicleJB);
		
		
		//fleet definition area
		fleetDefinitionAreJP = new JPanel();
		fleetDefinitionAreJP.setBounds(310, 0, 600, 800);
		fleetDefinitionAreJP.setBackground(Color.WHITE);
		fleetDefinitionAreJP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		fleetAreaJP.add(fleetDefinitionAreJP);
		
		fleetDefinitionAreJP.setLayout(null);

		fleetDefinitionTitleJP = new JPanel();
		fleetDefinitionTitleJP.setBounds(10, 10, 570, 50);
		fleetDefinitionTitleJL = new JLabel("Fleet - Vehicle Options");
		fleetDefinitionTitleJP.add(fleetDefinitionTitleJL);
		
		fleetDefinitionOptionsJP = new JPanel();
		fleetDefinitionOptionsJP.setBounds(10, 100, 570, 690);
		
		vehicleNameJL = new JLabel("Vehicle - Name");
		vehicleNameJL.setPreferredSize(new Dimension(150, 30));
		vehicleNameJTF = new JTextField();
		vehicleNameJTF.setPreferredSize(new Dimension(150, 30));
		vehicleNameJTF.setEnabled(false);
		
		fleetDefinitionOptionsJP.add(vehicleNameJL);
		fleetDefinitionOptionsJP.add(vehicleNameJTF);
		
		
		fleetDefinitionAreJP.add(fleetDefinitionTitleJP);
		fleetDefinitionAreJP.add(fleetDefinitionOptionsJP);
		
		
		// Vehicle Area
		addVehicleArea = new JPanel();
		addVehicleArea.setBounds(920, 10, 250, 800);
		addVehicleArea.setBackground(Color.WHITE);
		addVehicleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		addVehicleArea.setLayout(null);		
		
		addVehicleTitleJP = new JPanel();
		addVehicleTitleJP.setBounds(10, 10, 230, 50);
		addVehicleTitleJL = new JLabel("Add new Vehicle");
		addVehicleTitleJP.add(addVehicleTitleJL);

		addVehicleArea.add(addVehicleTitleJP);
		
		// vehicleSelectionAreaJP
		vehicleSelectionAreaJP = new JPanel();
		vehicleSelectionAreaJP.setPreferredSize(new Dimension(addVehicleArea.getWidth(), 250));
		vehicleSelectionAreaJP.setBounds(10, 100, addVehicleArea.getWidth()-20, 250);
//		vehicleSelectionAreaJP.setBackground(Color.BLACK);
//		vehicleSelectionAreaJP.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		
		vehicleSelectionAreaJP.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();		
		gbc.insets = new Insets( 0, 0, 5, 5 );	
		gbc.anchor = GridBagConstraints.WEST;	

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		previousVehicleJB = new JButton("<<<");
		vehicleSelectionAreaJP.add (previousVehicleJB, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		addVehicleJB = new JButton("Add  Vehicle");
		gbc.anchor = GridBagConstraints.CENTER;
		vehicleSelectionAreaJP.add (addVehicleJB, gbc);

		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		nextVehicleJB = new JButton(">>>");
		vehicleSelectionAreaJP.add (nextVehicleJB, gbc);

	
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.anchor = GridBagConstraints.CENTER;			

		vehicleSelectionII = new ImageIcon(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()).getVehicleTypes().getUrlVehicle());
		vehicleSelectionII.setImage(vehicleSelectionII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
		vehicleSelectionJL = new JLabel(vehicleSelectionII);

		vehicleSelectionAreaJP.add(vehicleSelectionJL, gbc);			

		addVehicleArea.add(vehicleSelectionAreaJP);	

		this.add(addVehicleArea);

		// Save Button
		saveFleetJB = new JButton("Save Fleet");
		saveFleetJB.setBounds(10, 480, 100, 30);
		addVehicleArea.add(saveFleetJB);
		
		// Load Button
		loadFleetJB = new JButton("Load Fleet");
		loadFleetJB.setBounds(120, 480, 100, 30);
		addVehicleArea.add(loadFleetJB);
		
		// Reset Button
		resetCurrentVehicleJB = new JButton("Reset");
		resetCurrentVehicleJB.setBounds(10, 440, 100, 30);
		addVehicleArea.add(resetCurrentVehicleJB);
		
		deleteFleetJB = new JButton("Delete");
		deleteFleetJB.setBounds(120, 440, 100, 30);
		deleteFleetJB.setEnabled(false);
		addVehicleArea.add(deleteFleetJB);

	}

	private void draw(Graphics g) {
		long start = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) g;

		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		

		//enable user input
		if(fleetEditorModel.getVehicles().size() > 0){
			
			enableUserInput();
			
		}
		
		// draw vehicles in fleetSelection
		if(fleetEditorModel.getVehicles().size() > 2){
			
			if(fleetEditorModel.getVehiclePos() - 1 == -1){
				fleetPreviousVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehicles().size()-1).getVehicleTypes().getUrlVehicle());
				fleetPreviousVehicleII.setImage(fleetPreviousVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));					
				fleetPreviousVehicleJL.setIcon(fleetPreviousVehicleII);
			}else {
				fleetPreviousVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()-1).getVehicleTypes().getUrlVehicle());
				fleetPreviousVehicleII.setImage(fleetPreviousVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));					
				fleetPreviousVehicleJL.setIcon(fleetPreviousVehicleII);
			}
			
			fleetCurrentVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).getVehicleTypes().getUrlVehicle());
			fleetCurrentVehicleII.setImage(fleetCurrentVehicleII.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
			fleetCurrentVehicleJL.setIcon(fleetCurrentVehicleII);
	
			if(fleetEditorModel.getVehiclePos() +1 == fleetEditorModel.getVehicles().size()){
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(0).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}else {
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()+1).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}

		}else if(fleetEditorModel.getVehicles().size() > 1){
			
			fleetCurrentVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).getVehicleTypes().getUrlVehicle());
			fleetCurrentVehicleII.setImage(fleetCurrentVehicleII.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
			fleetCurrentVehicleJL.setIcon(fleetCurrentVehicleII);

			if(fleetEditorModel.getVehiclePos() +1 == fleetEditorModel.getVehicles().size()){
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(0).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}else {
				fleetNextVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()+1).getVehicleTypes().getUrlVehicle());
				fleetNextVehicleII.setImage(fleetNextVehicleII.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
				fleetNextVehicleJL.setIcon(fleetNextVehicleII);
			}

			
		}else if(fleetEditorModel.getVehicles().size() > 0){
			
			fleetCurrentVehicleII = new ImageIcon(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).getVehicleTypes().getUrlVehicle());
			fleetCurrentVehicleII.setImage(fleetCurrentVehicleII.getImage().getScaledInstance(250, 250,Image.SCALE_DEFAULT));
			fleetCurrentVehicleJL.setIcon(fleetCurrentVehicleII);

		}
		
		
		// draw vehicle in vehicleSelection
		vehicleSelectionII = new ImageIcon(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()).getVehicleTypes().getUrlVehicle());
		vehicleSelectionII.setImage(vehicleSelectionII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
		vehicleSelectionJL.setIcon(vehicleSelectionII);

		}
	
	private void enableUserInput() {

		
		vehicleNameJTF.setEnabled(true);
		
	}

	public void update(Observable model, Object value) {
		if (model instanceof FleetEditorModel) {
			
			System.out.println("im update");
			this.fleetEditorModel = (FleetEditorModel) model; 
			
		}
		repaint();
	}

	/**
	 * @return the nextFleetVehicleJB
	 */
	public JButton getNextFleetVehicleJB() {
		return nextFleetVehicleJB;
	}

	/**
	 * @param nextFleetVehicleJB the nextFleetVehicleJB to set
	 */
	public void setNextFleetVehicleJB(JButton nextFleetVehicleJB) {
		this.nextFleetVehicleJB = nextFleetVehicleJB;
	}

	/**
	 * @return the previousFleetVehicleJB
	 */
	public JButton getPreviousFleetVehicleJB() {
		return previousFleetVehicleJB;
	}

	/**
	 * @param previousFleetVehicleJB the previousFleetVehicleJB to set
	 */
	public void setPreviousFleetVehicleJB(JButton previousFleetVehicleJB) {
		this.previousFleetVehicleJB = previousFleetVehicleJB;
	}

	/**
	 * @return the nextVehicleJB
	 */
	public JButton getNextVehicleJB() {
		return nextVehicleJB;
	}

	/**
	 * @param nextVehicleJB the nextVehicleJB to set
	 */
	public void setNextVehicleJB(JButton nextVehicleJB) {
		this.nextVehicleJB = nextVehicleJB;
	}

	/**
	 * @return the previousVehicleJB
	 */
	public JButton getPreviousVehicleJB() {
		return previousVehicleJB;
	}

	/**
	 * @param previousVehicleJB the previousVehicleJB to set
	 */
	public void setPreviousVehicleJB(JButton previousVehicleJB) {
		this.previousVehicleJB = previousVehicleJB;
	}

	/**
	 * @return the addVehicleJB
	 */
	public JButton getAddVehicleJB() {
		return addVehicleJB;
	}

	/**
	 * @param addVehicleJB the addVehicleJB to set
	 */
	public void setAddVehicleJB(JButton addVehicleJB) {
		this.addVehicleJB = addVehicleJB;
	}

	/**
	 * @return the saveFleetJB
	 */
	public JButton getSaveFleetJB() {
		return saveFleetJB;
	}

	/**
	 * @param saveFleetJB the saveFleetJB to set
	 */
	public void setSaveFleetJB(JButton saveFleetJB) {
		this.saveFleetJB = saveFleetJB;
	}

	/**
	 * @return the deleteFleetJB
	 */
	public JButton getDeleteFleetJB() {
		return deleteFleetJB;
	}

	/**
	 * @param deleteFleetJB the deleteFleetJB to set
	 */
	public void setDeleteFleetJB(JButton deleteFleetJB) {
		this.deleteFleetJB = deleteFleetJB;
	}

	/**
	 * @return the resetCurrentVehicleJB
	 */
	public JButton getResetCurrentVehicleJB() {
		return resetCurrentVehicleJB;
	}

	/**
	 * @param resetCurrentVehicleJB the resetCurrentVehicleJB to set
	 */
	public void setResetCurrentVehicleJB(JButton resetCurrentVehicleJB) {
		this.resetCurrentVehicleJB = resetCurrentVehicleJB;
	}

	/**
	 * @return the deleteCurrentVehcleJB
	 */
	public JButton getDeleteCurrentVehcleJB() {
		return deleteCurrentVehcleJB;
	}

	/**
	 * @param deleteCurrentVehcleJB the deleteCurrentVehcleJB to set
	 */
	public void setDeleteCurrentVehcleJB(JButton deleteCurrentVehcleJB) {
		this.deleteCurrentVehcleJB = deleteCurrentVehcleJB;
	}

	
}