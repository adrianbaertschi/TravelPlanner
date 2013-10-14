package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import model.Knot;
import model.MapEditorModel;
import model.Street;
import view.MapEditorView;
import dao.Database;

public class MapEditorController {

	private MapEditorView view;
	private MapEditorModel model;
	

	public MapEditorController(MapEditorView view, MapEditorModel model) {
		this.view = view;
		this.model = model;
		this.model.addObserver(view);
		addListener();
	}

	private void addListener() {
		view.getMapArea().addMouseListener(new MapMouseListener());
		view.getBtnSaveMap().addActionListener(new BtnSaveMapActionListener());
		view.getBtnLoadMap().addActionListener(new BtnLoadMapActioListener());
	}

	public Component showView() {
		this.view.setVisible(true);
		return this.view;
	}

	class MapMouseListener implements MouseListener {

		private Street s;
		
		public void mouseClicked(MouseEvent e) {
			Knot point = new Knot(e.getX(), e.getY());

			Knot selectedKnot = clickedOnEdge(point);

			// CASE 1: New Street
			if(selectedKnot == null) {
				// First click
				if(s == null) {
					s = new Street(point);
					
					Street selectedStreet = clickedOnStreet(point);
					model.setSelectedStreet(selectedStreet);
					
					if(selectedStreet != null) {
						s = null;
					}
					
					
					// Second click
				} else if (s.getStart() != null && s.getEnd() == null) {
					s.setEnd(point);
					model.addStreet(s);
					
					// reset Street
					s = null;
				} 
			} else {
				// CASE 2: append to existing
				if(s == null) {
					s = new Street(selectedKnot);
				} else {
					// TODO: CASE 3: connect two existing
					s.setEnd(selectedKnot);
					model.addStreet(s);
					
					// reset Street
					s = null;
				}
			}
		}

		private Street clickedOnStreet(Knot point) {
			for(Street street : model.getStreets()) {
				if(street.isPointOnStreet(point.getX(), point.getY())) {
					return street;
				}
			}
			return null;
		}

		public void mouseEntered(MouseEvent e) {	}
		public void mouseExited(MouseEvent e) 	{	}
		public void mousePressed(MouseEvent e) {	}
		public void mouseReleased(MouseEvent e) {	}
		
		private Knot clickedOnEdge(Knot k) {
			
			int x = k.getX();
			int y = k.getY();
			int toleranz = 5;
			
			for(Street street : model.getStreets()) {
				// TODO: Refactor
				
				// On Start Knoten?
				int xdiff = Math.abs(street.getStart().getX() - x);
				int ydiff = Math.abs(street.getStart().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					return street.getStart();
				}
				
				// On End?
				xdiff = Math.abs(street.getEnd().getX() - x);
				ydiff = Math.abs(street.getEnd().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					return street.getEnd();
				}
			}
			
			return null;
		}
	}
	
	class BtnSaveMapActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name");
			
			model.setName(name);
			
			Database.getInstance().saveMap(model);
		}
	}
	
	class BtnLoadMapActioListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println("load");
			
		}
		
	}

}
