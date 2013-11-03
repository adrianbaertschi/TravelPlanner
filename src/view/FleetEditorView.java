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
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	private JPanel vehicleSelectionArea;
	private JLabel streetInfo;
	private JLabel vehicleJL;

	private ImageIcon finishII;
	
	private JButton startJB;
	private JButton finishJB;
	private JButton simulationJB;
	
	private JButton nextVehicleJB;
	private JButton previousVehicleJB;
	
	

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
		vehicleArea.setLayout(new FlowLayout(FlowLayout.LEFT));

		
		// VehicleSelectionArea
		vehicleSelectionArea = new JPanel();
		vehicleSelectionArea.setPreferredSize(new Dimension(vehicleArea.getWidth(), 250));
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
		//TODO: replace constant 
		vehicleII = new ImageIcon("images/car.jpg");
		vehicleII.setImage(vehicleII.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
		vehicleJL = new JLabel(vehicleII, SwingConstants.CENTER);
		
		vehicleSelectionArea.add (vehicleJL, gbc);

		vehicleArea.add(vehicleSelectionArea);	

		this.add(vehicleArea);

		// Start Button

		startJB = new JButton("start");
		vehicleArea.add(startJB);

		// Finish JButton
		finishII = new ImageIcon("images/finish.jpg");
		finishII.setImage(finishII.getImage().getScaledInstance(30, 30,
				Image.SCALE_DEFAULT));
		finishJB = new JButton(finishII);

		vehicleArea.add(finishJB);

		// Simulation JButton
		simulationJB = new JButton("Simulation");
		vehicleArea.add(simulationJB);

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



		}


	/**
	 * @return the finishJB
	 */
	public JButton getFinishJB() {
		return finishJB;
	}

	/**
	 * @param finishJB
	 *            the finishJB to set
	 */
	public void setFinishJB(JButton finishJB) {
		this.finishJB = finishJB;
	}

	/**
	 * @return the startJB
	 */
	public JButton getStartJB() {
		return startJB;
	}

	/**
	 * @param startJB
	 *            the startJB to set
	 */
	public void setStartJB(JButton startJB) {
		this.startJB = startJB;
	}

	/**
	 * @return the simulationJB
	 */
	public JButton getSimulationJB() {
		return simulationJB;
	}

	/**
	 * @param simulationJB
	 *            the simulationJB to set
	 */
	public void setSimulationJB(JButton simulationJB) {
		this.simulationJB = simulationJB;
	}

	
	public void update(Observable model, Object value) {
		if (model instanceof FleetEditorModel) {
			
			System.out.println("im update");
			this.fleetEditorModel = (FleetEditorModel) fleetEditorModel; 
			
		}
		
	}

}