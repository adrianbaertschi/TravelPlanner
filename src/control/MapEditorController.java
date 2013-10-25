package control;

import static common.Constants.EDGE_RADIUS;

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
import dao.MapEditorDao;

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

		private Street currentStreet;
		
		public void mouseClicked(MouseEvent e) {	}

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
				if(currentStreet == null) {
					currentStreet = new Street(point);
					
					
					Street selectedStreet = clickedOnStreet(point);
					model.setSelectedStreet(selectedStreet);
					
					if(selectedStreet != null) {
						currentStreet = null;
					} else {
						model.setSelectedKnot(point);
					}
					
					
					// Second click
				} else if (currentStreet.getStart() != null && currentStreet.getEnd() == null && !currentStreet.getStart().equals(point)) {
					
					if(clickedOnStreet(point) == null) {
						currentStreet.setEnd(point);
						model.addStreet(currentStreet);
						
						// reset Street
						currentStreet = null;
						
					} else {
						model.setSelectedKnot(currentStreet.getStart());
					}
				} 
			} else {
				
				// CASE 2: append to existing
				if(currentStreet == null) {
					currentStreet = new Street(selectedKnot);
				} else {
					// CASE 3: connect two existing
					currentStreet.setEnd(selectedKnot);
					model.addStreet(currentStreet);
					model.setSelectedKnot(null);
					
					// reset Street
					currentStreet = null;
				}
			}
		}
		
		public void mouseReleased(MouseEvent e) {	}
		
		private Knot clickedOnEdge(Knot k) {
			
			int x = k.getX();
			int y = k.getY();
			
			for(Street street : model.getStreets()) {
				
				// On Start Knoten?
				int xdiff = Math.abs(street.getStart().getX() - x);
				int ydiff = Math.abs(street.getStart().getY() - y);
				
				if(xdiff <= EDGE_RADIUS && ydiff <= EDGE_RADIUS) {
					return street.getStart();
				}
				
				// On End?
				xdiff = Math.abs(street.getEnd().getX() - x);
				ydiff = Math.abs(street.getEnd().getY() - y);
				
				if(xdiff <= EDGE_RADIUS && ydiff <= EDGE_RADIUS) {
					return street.getEnd();
				}
			}
			
			return null;
		}
	}
	
	class BtnSaveMapActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name");
			
			if(name == null) {
				return;
			}
			
			model.setName(name);
			MapEditorDao.getInstance().saveMap(model);
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
