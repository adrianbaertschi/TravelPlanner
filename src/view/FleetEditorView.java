package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
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

	private JPanel mapArea;
	private JPanel vehicleArea;

	private JPanel vehicleSelectionAreaJP;
	private JLabel streetInfo;
	private JLabel vehicleJL;
	
	private JButton nextVehicleJB;
	private JButton previousVehicleJB;
	private JButton addVehicleJB;

	private JButton saveFleetJB;
	private JButton loadFleetJB;
	private JButton deleteFleetJB;
	
	private JButton resetCurrentVehicleJB;
	private JButton deleteCurrentVehcleJB;


	private GridBagConstraints gbc;	

	
	private ImageIcon vehicleII;

	private Image img;

	public FleetEditorView() {

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
		vehicleArea.setBounds(920, 10, 1200-910-40, 800);
		vehicleArea.setBackground(Color.WHITE);
//		vehicleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		vehicleArea.setLayout(null);		
		
		// vehicleSelectionAreaJP
		vehicleSelectionAreaJP = new JPanel();
		vehicleSelectionAreaJP.setPreferredSize(new Dimension(vehicleArea.getWidth(), 250));
		vehicleSelectionAreaJP.setBounds(0, 0, vehicleArea.getWidth(), 250);
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

		vehicleII = new ImageIcon(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()).getVehicleTypes().getUrlVehicle());
		vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
		vehicleJL = new JLabel(vehicleII);

		vehicleSelectionAreaJP.add(vehicleJL, gbc);			

		vehicleArea.add(vehicleSelectionAreaJP);	

		this.add(vehicleArea);

		// Save Button
		saveFleetJB = new JButton("Save Fleet");
		saveFleetJB.setBounds(0, 480, 100, 30);
		vehicleArea.add(saveFleetJB);
		
		// Load Button
		loadFleetJB = new JButton("Load Fleet");
		loadFleetJB.setBounds(110, 480, 100, 30);
		vehicleArea.add(loadFleetJB);
		
		// Reset Button
		resetCurrentVehicleJB = new JButton("Reset");
		resetCurrentVehicleJB.setBounds(0, 440, 100, 30);
		vehicleArea.add(resetCurrentVehicleJB);
		
		deleteFleetJB = new JButton("Delete");
		deleteFleetJB.setBounds(110, 440, 100, 30);
		deleteFleetJB.setEnabled(false);
		vehicleArea.add(deleteFleetJB);

		try {
			this.img = ImageIO.read(new File("images/car.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public JPanel getMapArea() {
		return this.mapArea;
	}

	private void draw(Graphics g) {
		long start = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) g;

		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		vehicleII = new ImageIcon(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()).getVehicleTypes().getUrlVehicle());
		vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200,Image.SCALE_DEFAULT));
		vehicleJL.setIcon(vehicleII);

		}
	
	public void update(Observable model, Object value) {
		if (model instanceof FleetEditorModel) {
			
			System.out.println("im update");
			this.fleetEditorModel = (FleetEditorModel) model; 
			
		}
		this.revalidate();
		this.repaint();
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