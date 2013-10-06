package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Knot;
import model.MapEditorModel;
import model.Street;

public class MapEditor extends JPanel {
	
	private MapEditorModel model = new MapEditorModel();
	
	private JPanel mapArea;
	private JLabel streetInfo;

	public MapEditor() {
		
		// Map Area
		mapArea = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		this.setLayout(null);
		mapArea.setBounds(10, 10, 500, 500);
		mapArea.setBackground(Color.WHITE);
		mapArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		this.add(mapArea);
		
		// Details
		streetInfo = new JLabel();
		streetInfo.setBounds(520, 10, 300, 30);
		
		this.add(streetInfo);
		
	}
	
	public JPanel getMapArea() {
		return this.mapArea;
	}
	
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : model.getStreets()) {
			
			g2d.setColor(Color.BLUE);
			g2d.fillOval(street.getStart().getX() -5, street.getStart().getY() -5, 10, 10);
			g2d.fillOval(street.getEnd().getX() -5, street.getEnd().getY() -5, 10, 10);
			
			if(street == model.getSelectedStreet()) {
				g2d.setColor(Color.CYAN);
			} else {
				g2d.setColor(Color.BLACK);
			}
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(street.getStart().getX(), street.getStart().getY(), street.getEnd().getX(), street.getEnd().getY());
		}
		
	}
	
	public void updateModel(MapEditorModel model) {
		this.model = model;
		
		repaint();
	}
		

	public void displayStreetInfo(Street selectedStreet) {
		if(selectedStreet == null)  {
			streetInfo.setText("Click on street to view info.");
		} else {
			streetInfo.setText("Lenth: " + selectedStreet.getLenth());
		}
		
	}
}
