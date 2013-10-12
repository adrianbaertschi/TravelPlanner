package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.MapEditorController;
import model.Knot;
import model.MapEditorModel;
import model.Street;

public class MapEditor extends JPanel implements Observer{
	
	private MapEditorModel model = new MapEditorModel();
	
	private JPanel mapArea;
	private JLabel streetInfo;
	
	private JButton btnSaveMap;
	private JButton btnLoadMap;

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
		
		// Save Button
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.setBounds(520, 480, 100, 30);
		this.add(btnSaveMap);
		
		// Load Button
		btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(630, 480, 100, 30);
		this.add(btnLoadMap);
		
	}
	
	public JPanel getMapArea() {
		return this.mapArea;
	}
	
	public JButton getBtnSaveMap() {
		return btnSaveMap;
	}
	
	public JButton getBtnLoadMap() {
		return btnLoadMap;
	}
	
	private void draw(Graphics g) {
		long start = System.currentTimeMillis();
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
		if(model instanceof MapEditorModel) {
			this.model = (MapEditorModel) model;
			repaint();
		}
		if(value instanceof Street) {
			displayStreetInfo((Street)value);
		}
	}
}
