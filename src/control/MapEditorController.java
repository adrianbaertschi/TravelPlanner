package control;

import static common.Constants.NODE_RADIUS;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import model.MapEditorModel;
import model.MapEditorModelException;
import model.Node;
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
	
	private int getNodeHeightValue() {
		Long value = (Long) view.getTxfNodeHeight().getValue();
		
		if(value == null) {
			return 0;
		}
		return value.intValue();
	}

	class MapMouseListener implements MouseListener {

		private Street currentStreet;
		
		public void mouseClicked(MouseEvent e) {	}

		private Street clickedOnStreet(Node point) {
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

			// deselect everything
			if(e.isControlDown()) {
				model.setSelectedKnot(null);
				model.setSelectedStreet(null);
				currentStreet = null;
				return;
			}
			
			Node point = new Node(e.getX(), e.getY(), getNodeHeightValue());

			Node selectedKnot = clickedOnNode(point);
			
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
				} else if (currentStreet.getStart() != null && currentStreet.getEnd() == null && !(clickedOnNode(point) == point)) {
					
					if(clickedOnStreet(point) == null) {
						currentStreet.setEnd(point);
						currentStreet.setStreetType(view.getSelectedStreetType());
						try {
							model.addStreet(currentStreet);
							
						} catch (MapEditorModelException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
						
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
					if(currentStreet.getStart().equals(selectedKnot)) {
						return;
					}
					currentStreet.setEnd(selectedKnot);
					currentStreet.setStreetType(view.getSelectedStreetType());
					try {
						model.addStreet(currentStreet);
						
					} catch(MapEditorModelException ex) {
						JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
					model.setSelectedKnot(null);
					
					// reset Street
					currentStreet = null;
				}
			}
		}
		
		public void mouseReleased(MouseEvent e) {	}
		
		private Node clickedOnNode(Node k) {

			for(Street street : model.getStreets()) {

				// On Start Knoten?
				if(isOnNode(street.getStart(), k)) {
					return street.getStart();
				}

				// On End?
				if(isOnNode(street.getEnd(), k)) {
					return street.getEnd();
				}
			}

			if(isOnNode(model.getSelectedKnot(), k)) {
				return model.getSelectedKnot();
			}

			return null;
		}
		
		private boolean isOnNode(Node source, Node point) {
			if(source == null) {
				return false;
			}
			
			int xdiff = Math.abs(source.getX() - point.getX());
			int ydiff = Math.abs(source.getY() - point.getY());
			
			return xdiff <= NODE_RADIUS && ydiff <= NODE_RADIUS;
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
