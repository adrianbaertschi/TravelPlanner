package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class MapEditor extends JPanel {
	
	Dimension d = new Dimension();
	Dimension d2 = new Dimension();

	public MapEditor() {
		this.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent arg0) {
				d2 = (Dimension) d.clone();
				d.setSize(arg0.getX(), arg0.getY());
				repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	private void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		
		
		g2d.drawLine(d.width, d.height, d2.width, d2.height);
		
	}
}
