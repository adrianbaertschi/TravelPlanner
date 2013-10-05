package control;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.Knot;
import model.MapEditorModel;
import model.Street;
import view.MapEditor;

public class MapEditorController {

	private MapEditor view;
	private MapEditorModel model;
	

	public MapEditorController() {
		this.view = new MapEditor();
		this.model = new MapEditorModel();

		addListener();
	}

	private void addListener() {
		view.getMapArea().addMouseListener(new MapMouseListener());
	}

	public Component showView() {
		this.view.setVisible(true);
		return this.view;
	}

	class MapMouseListener implements MouseListener {

		private Street s;
		
		public void mouseClicked(MouseEvent e) {
			Knot point = new Knot(e.getX(), e.getY());

			Knot clickResult = clickedOnEdge(point);

			// CASE 1: New Street
			if(clickResult == null) {
				// First click
				if(s == null) {
					s = new Street(point);				
					// Second click
				} else if (s.getStart() != null && s.getEnd() == null) {
					s.setEnd(point);
					model.addStreet(s);
					view.updateModel(model);
					
					// reset Street
					s = null;
				} 
			} else {
				// CASE 2: append to existing
				if(s == null) {
					s = new Street(clickResult);
				} else {
					// TODO: CASE 3: connet two existing
					s.setEnd(clickResult);
					model.addStreet(s);
					view.updateModel(model);
					
					// reset Street
					s = null;
				}
			}
			
			
			
			

		}

		public void mouseEntered(MouseEvent e) {	}
		public void mouseExited(MouseEvent e) 	{	}
		public void mousePressed(MouseEvent e) {	}
		public void mouseReleased(MouseEvent e) {	}
	}
	
	public Knot clickedOnEdge(Knot k) {
		
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
