package control;

import java.awt.Color;
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
import view.MapLoaderDialog;
import view.MasterGui;
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
		view.getBtnReset().addActionListener(new BtnResetActionListener());
		view.getBtnDelete().addActionListener(new BtnDeleteActionListener());
	}

	public Component showView() {
		this.view.setVisible(true);
		return this.view;
	}
	
	public void setModel(MapEditorModel model) {
		this.model.loadModel(model);
	}

	class MapMouseListener implements MouseListener {

		private Street s;
		
		public void mouseClicked(MouseEvent e) {
			
			
			
			
			
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
		public void mousePressed(MouseEvent e) {	
			Knot point = new Knot(e.getX(), e.getY());

			Knot selectedKnot = clickedOnEdge(point);
			
			model.setSelectedKnot(selectedKnot);

			// CASE 1: New Street
			if(selectedKnot == null) {
				// First click
				if(s == null) {
					s = new Street(point);
					
					
					Street selectedStreet = clickedOnStreet(point);
					model.setSelectedStreet(selectedStreet);
					
					if(selectedStreet != null) {
						s = null;
					} else {
						model.setSelectedKnot(point);
					}
					
					
					// Second click
				} else if (s.getStart() != null && s.getEnd() == null && !s.getStart().equals(point)) {
					
					if(clickedOnStreet(point) == null) {
						s.setEnd(point);
						model.addStreet(s);
						
						// reset Street
						s = null;
						
					} else {
						model.setSelectedKnot(s.getStart());
					}
				} 
			} else {
				
				// CASE 2: append to existing
				if(s == null) {
					s = new Street(selectedKnot);
				} else {
					// CASE 3: connect two existing
					s.setEnd(selectedKnot);
					model.addStreet(s);
					model.setSelectedKnot(null);
					
					// reset Street
					s = null;
				}
			}
		}
		
		public void mouseReleased(MouseEvent e) {	}
		
		private Knot clickedOnEdge(Knot k) {
			
			int x = k.getX();
			int y = k.getY();
			int toleranz = 5;
			
			Knot returnKnot = null;
			
			for(Street street : model.getStreets()) {
				// TODO: Refactor
				
				// On Start Knoten?
				int xdiff = Math.abs(street.getStart().getX() - x);
				int ydiff = Math.abs(street.getStart().getY() - y);
				
				street.getStart().setColor(Color.DARK_GRAY);
				street.getEnd().setColor(Color.DARK_GRAY);

				if(xdiff <= toleranz && ydiff <= toleranz) {
					street.getStart().setColor(Color.CYAN);
					returnKnot = street.getStart();
				}
				
				// On End?
				xdiff = Math.abs(street.getEnd().getX() - x);
				ydiff = Math.abs(street.getEnd().getY() - y);
				
				if(xdiff <= toleranz && ydiff <= toleranz) {
					street.getEnd().setColor(Color.CYAN);
					returnKnot = street.getEnd();
				}
			}
			
			return returnKnot;
		}
	}
	
	class BtnSaveMapActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name");
			
			if(name == null) {
				return;
			}
			
			model.setName(name);
			Database.getInstance().saveMap(model);
		}
	}
	
	class BtnLoadMapActioListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			MapLoaderDialog d = new MapLoaderDialog(MasterGui.getFrames()[0], MapEditorController.this);
			d.setVisible(true);
		}
	}
	
	class BtnResetActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			model.reset();
		}
	}
	
	class BtnDeleteActionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			model.removeStreet(model.getSelectedStreet());
		}
		
	}

}
