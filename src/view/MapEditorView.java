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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

import model.MapEditorModel;
import model.Node;
import model.Street;
import model.StreetType;

public class MapEditorView extends JPanel implements Observer{
	
	private static final Color NODE_COLOR = 	Color.DARK_GRAY;
	private static final Color SELECTED_COLOR = Color.CYAN;
	
	private MapEditorModel model = new MapEditorModel();
	
	private JPanel mapArea;
	private JLabel streetInfo;
	
	private JComboBox<StreetType> cbxStreetType;
	
	private JLabel lblNodeHeight;
	private JFormattedTextField txfNodeHeight;
	
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
		streetInfo.setBounds(920, 200, 300, 30);
		
		this.add(streetInfo);
		
		cbxStreetType = new JComboBox<StreetType>(StreetType.values());
		cbxStreetType.setBounds(920, 10, 200, 30);
		this.add(cbxStreetType);
		
		lblNodeHeight = new JLabel("Node height:");
		lblNodeHeight.setBounds(920, 100, 100, 30);
		this.add(lblNodeHeight);
		
		txfNodeHeight = new JFormattedTextField(new NumberFormatter());
		txfNodeHeight.setBounds(1020, 100, 100, 30);
		this.add(txfNodeHeight);
		
		// Delete Street
		btnDelete = new JButton("Delete Street");
		btnDelete.setBounds(920, 440, 130, 30);
		btnDelete.setEnabled(false);
		this.add(btnDelete);
		
		// Save Button
		btnSaveMap = new JButton("Save Map");
		btnSaveMap.setBounds(920, 740, 130, 30);
		this.add(btnSaveMap);
		
		// Load Button
		btnLoadMap = new JButton("Load Map");
		btnLoadMap.setBounds(1055, 740, 130, 30);
		this.add(btnLoadMap);
		
		// Reset Map
		btnReset = new JButton("Reset Map");
		btnReset.setBounds(920, 780, 130, 30);
		this.add(btnReset);
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
	
	public JComboBox<StreetType> getCbxStreetType() {
		return this.cbxStreetType;
	}
	
	public JFormattedTextField getTxfNodeHeight() {
		return this.txfNodeHeight;
	}
	
	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : model.getStreets()) {
			
			// Strassen
			if(street == model.getSelectedStreet()) {
				g2d.setColor(SELECTED_COLOR);
			} else {
				g2d.setColor(street.getStreetType().getColor());
			}
			g2d.setStroke(new BasicStroke(3));
			g2d.draw(convertStreetToLine(street));
			
			
			// Knoten
			g2d.setColor(NODE_COLOR);
			g2d.fill(convertKnotToEllipse(street.getStart()));
			g2d.fill(convertKnotToEllipse(street.getEnd()));
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
	
	private void displayNodeInfo(Node selectedNode) {
		if(selectedNode != null) {
			streetInfo.setText("Node position: " + selectedNode.getX() + " / " + selectedNode.getY() + " height: " + selectedNode.getHeight());
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
			displayNodeInfo((Node)value);
			btnDelete.setEnabled(false);
		}
	}
	
	public StreetType getSelectedStreetType() {
		return this.cbxStreetType.getItemAt(this.cbxStreetType.getSelectedIndex());
	}
}
