/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import control.MapEditorController.BtnLoadMapActioListener;
import control.MapEditorController.BtnResetActionListener;
import control.MapEditorController.BtnSaveMapActionListener;
import control.MapEditorController.MapMouseListener;
import model.Knot;
import model.MapEditorModel;
import model.SimulationEditorModel;
import model.Street;
import view.SimulationEditorView;

/**
 * @author dimitri.haemmerli
 *
 */
public class SimulationEditorController {
	
	private SimulationEditorView sev;
	private SimulationEditorModel sem;
	private MapEditorModel mem;

	
	public SimulationEditorController(SimulationEditorView sev, SimulationEditorModel sem){
		
		this.sev = sev;
		this.sem = sem;
		this.sev.setMapEditorModel(sem.getMapEditorModel());
		this.sem.addObserver(sev);
		addListener();

		
	}
	private void addListener() {
		sev.getMapArea().addMouseListener(new MapMouseListener());
//		sev.getBtnSaveMap().addActionListener(new BtnSaveMapActionListener());
//		sev.getBtnLoadMap().addActionListener(new BtnLoadMapActioListener());
//		sev.getBtnReset().addActionListener(new BtnResetActionListener());

	}
	
	public Component showView() {
		this.sev.setVisible(true);
		return this.sev;
	}

	class MapMouseListener implements MouseListener {

		private Street s;
		
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

	}
}

