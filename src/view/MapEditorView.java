package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Knot;
import model.MapEditorModel;
import model.Street;

public class MapEditorView extends JPanel implements Observer{
	
	private MapEditorModel model = new MapEditorModel();
	
	private JPanel mapArea;
	private JLabel streetInfo;
	
	private JButton btnSaveMap;
	private JButton btnLoadMap;
	private JButton btnReset;
	private JButton btnDelete;

	public MapEditorView() {
		
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
		
		// Save Button
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.setBounds(920, 480, 100, 30);
		this.add(btnSaveMap);
		
		// Load Button
		btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(1030, 480, 100, 30);
		this.add(btnLoadMap);
		
		// Reset Button
		btnReset = new JButton("Reset");
		btnReset.setBounds(920, 440, 100, 30);
		this.add(btnReset);
		
		btnDelete = new JButton("Delete");
		btnDelete.setBounds(1030, 440, 100, 30);
		btnDelete.setEnabled(false);
		this.add(btnDelete);
		
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
	
	public JButton getBtnReset() {
		return btnReset;
	}
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : model.getStreets()) {
			
			// Knoten

			g2d.setColor(street.getStart().getColor());
			g2d.fillOval(street.getStart().getX() -5, street.getStart().getY() -5, 10, 10);
			
			g2d.setColor(street.getEnd().getColor());
			g2d.fillOval(street.getEnd().getX()   -5, street.getEnd().getY()   -5, 10, 10);
			
			// Strassen
			if(street == model.getSelectedStreet()) {
				g2d.setColor(Color.CYAN);
			} else {
				g2d.setColor(street.getStreetColor());
			}
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(street.getStart().getX(), street.getStart().getY(), street.getEnd().getX(), street.getEnd().getY());
		}
	}
	
	private void displayStreetInfo(Street selectedStreet) {
		if(selectedStreet == null)  {
			streetInfo.setText("");
			btnDelete.setEnabled(false);
		} else {
			streetInfo.setText("Lenth: " + selectedStreet.getLenth());
			btnDelete.setEnabled(true);
		}
		
	}
	
	private void displayKnotInfo(Knot selectedKnot) {
		if(selectedKnot != null) {
			streetInfo.setText("Knot Position: " + selectedKnot.getX() + " / " + selectedKnot.getY());
		} else {
			streetInfo.setText("");
		}
	}

	public void update(Observable model, Object value) {
		if(model instanceof MapEditorModel) {
			this.model = (MapEditorModel) model;
			repaint();
		}
		if(value instanceof Street) {
			if(this.model.getSelectedStreet() == value) {
				displayStreetInfo((Street)value);
			} else {
				displayStreetInfo(null);
			}
		}
		if(value instanceof Knot) {
			displayKnotInfo((Knot)value);
		}
	}
}
