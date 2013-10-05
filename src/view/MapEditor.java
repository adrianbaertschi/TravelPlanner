package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.MapEditorModel;
import model.Street;

public class MapEditor extends JPanel {
	
	private JPanel mapArea;
	private MapEditorModel model = new MapEditorModel();

	public MapEditor() {
		
		mapArea = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		this.setLayout(null);
		mapArea.setBounds(10, 10, 400, 400);
		mapArea.setBackground(Color.WHITE);
		mapArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		this.add(mapArea);
		
	}
	
	public JPanel getMapArea() {
		return this.mapArea;
	}
	


	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		
		for(Street street : model.getStreets()) {
			
			g2d.fillOval(street.getStart().getX() -5, street.getStart().getY() -5, 10, 10);
			g2d.fillOval(street.getEnd().getX() -5, street.getEnd().getY() -5, 10, 10);
			
			g2d.drawLine(street.getStart().getX(), street.getStart().getY(), street.getEnd().getX(), street.getEnd().getY());
		}
		
	}
	
	public void updateModel(MapEditorModel model) {
		this.model = model;
		
		repaint();
	}
}
