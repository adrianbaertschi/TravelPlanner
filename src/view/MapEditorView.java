package view;

import static common.Constants.NODE_HEIGHT;
import static common.Constants.NODE_RADIUS;
import static common.Constants.NODE_WIDTH;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Node;
import model.MapEditorModel;
import model.Street;

public class MapEditorView extends JPanel implements Observer{
	
	private static final Color STREET_COLOR = 	Color.BLUE;
	private static final Color NODE_COLOR = 	Color.DARK_GRAY;
	private static final Color SELECTED_COLOR = Color.CYAN;
	
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
			g2d.setColor(NODE_COLOR);
			g2d.fill(convertKnotToEllipse(street.getStart()));
			
			g2d.fill(convertKnotToEllipse(street.getEnd()));
			
			// Strassen
			if(street == model.getSelectedStreet()) {
				g2d.setColor(SELECTED_COLOR);
			} else {
				g2d.setColor(STREET_COLOR);
			}
			g2d.setStroke(new BasicStroke(2));
			g2d.draw(convertStreetToLine(street));
		}
		
		if(model.getSelectedKnot() != null) {
			g2d.setColor(SELECTED_COLOR);
			g2d.fill(convertKnotToEllipse(model.getSelectedKnot()));
		}
	}
	
	private Ellipse2D.Float convertKnotToEllipse(Node knot) {
		return new Ellipse2D.Float(knot.getX() - NODE_RADIUS, knot.getY() - NODE_RADIUS, NODE_WIDTH, NODE_HEIGHT);
	}
	private Line2D.Float convertStreetToLine(Street s) {
		return new Line2D.Float(s.getStart().getX(), s.getStart().getY(), s.getEnd().getX(), s.getEnd().getY());
	}
	
	private void displayKnotInfo(Node selectedKnot) {
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
				Street st = (Street) value;
				streetInfo.setText("Lenth: " + st.getLenth());
				btnDelete.setEnabled(true);
			} else {
				streetInfo.setText("");
				btnDelete.setEnabled(false);
			}
		}
		if(value instanceof Node) {
			displayKnotInfo((Node)value);
			btnDelete.setEnabled(false);
		}
	}
}
