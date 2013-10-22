/**
 * 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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

	private MapEditorModel mapModel = new MapEditorModel();
	private FleetEditorModel fleetEditorModel = new FleetEditorModel();
	
	private JPanel mapArea;
	private JPanel vehicleArea;
	private JPanel vehicleSelectionArea;
	private JLabel streetInfo;
	
	private JButton btnSaveMap;
	private JButton btnLoadMap;
	private JButton btnReset;
	private JButton btnDelete;
	
	private ImageIcon finishII;
	private JButton finishJB;
	
	private ImageIcon startII;
	private JButton startJB;
	
	private JLayeredPane vehicleJLP;

	
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
		vehicleArea.setBounds(920, 10, 1200-910-40, 800);
		vehicleArea.setBackground(Color.WHITE);
		vehicleArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		vehicleArea.setLayout(new FlowLayout(FlowLayout.LEFT));
			
		
		this.add(vehicleArea);
		
		
		// VehicleSelectionArea
		vehicleSelectionArea = new JPanel();
		vehicleSelectionArea.setPreferredSize(new Dimension(vehicleArea.getWidth(), 210));
		vehicleSelectionArea.setBackground(Color.BLACK);
		vehicleSelectionArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		vehicleArea.add(vehicleSelectionArea);		

		
	
		// Start Button
		
		startJB = new JButton("start");
		vehicleArea.add(startJB);
		
		
		// Finish JButton
		finishII = new ImageIcon("images/finish.jpg");
		finishII.setImage(finishII.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
		finishJB = new JButton(finishII);

		vehicleArea.add(finishJB);
		
	}

	
	public JPanel getMapArea() {
		return this.mapArea;
	}
		
	private void draw(Graphics g) {
		long start = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D)g;
		
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : mapModel.getStreets()) {
			
			
			// Knoten
			if(street.getStart().equals(mapModel.getSelectedKnot())) {
				g2d.setColor(Color.CYAN);
			} else {
				g2d.setColor(Color.DARK_GRAY);
			}
			g2d.fillOval(street.getStart().getX() -5, street.getStart().getY() -5, 10, 10);
			
			if(street.getEnd().equals(mapModel.getSelectedKnot())) {
				g2d.setColor(Color.CYAN);
			} else {
				g2d.setColor(Color.DARK_GRAY);
			}
			g2d.fillOval(street.getEnd().getX()   -5, street.getEnd().getY()   -5, 10, 10);
			
			// Strassen			
			if(street == mapModel.getSelectedStreet()) {
				g2d.setColor(Color.CYAN);
			} else {
				g2d.setColor(street.getStreetColor());
			}
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(street.getStart().getX(), street.getStart().getY(), street.getEnd().getX(), street.getEnd().getY());
		
		
		
		}
		
		System.out.println("Repaint time: " + (System.currentTimeMillis() - start));
		
	}
	

	private void displayStreetInfo(Street selectedStreet) {
		if(selectedStreet == null)  {
			streetInfo.setText("Click on street to view info.");
		} else {
			streetInfo.setText("Lenth: " + selectedStreet.getLenth());
		}
		
	}

	public void update(Observable model, Object value) {
		if (model instanceof SimulationEditorModel) {
			System.out.println("im update");
			if (((SimulationEditorModel) model).getMapEditorModel() != null) {
				this.mapModel = ((SimulationEditorModel) model).getMapEditorModel();
				repaint();
			}
		}
	}

	/**
	 * @return the mapModel
	 */
	public MapEditorModel getMapModel() {
		return mapModel;
	}

	/**
	 * @param mapModel the mapModel to set
	 */
	public void setMapEditorModel(MapEditorModel mapModel) {
		this.mapModel = mapModel;
	}

	/**
	 * @return the finishJB
	 */
	public JButton getFinishJB() {
		return finishJB;
	}

	/**
	 * @param finishJB the finishJB to set
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
	 * @param startJB the startJB to set
	 */
	public void setStartJB(JButton startJB) {
		this.startJB = startJB;
	}

	
}
