/**
 * 
 */
package control;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import model.Car;
import model.FleetEditorModel;
import model.Vehicle;
import view.FleetEditorView;
import view.MasterGui;
import view.components.LoaderDialog;
import dao.FleetEditorDao;

/**
 * @author dimitri.haemmerli
 *
 */
public class FleetEditorController implements Controller {


	private FleetEditorView fleetEditorView;
	private FleetEditorModel fleetEditorModel;
	

	public FleetEditorController(FleetEditorView view, FleetEditorModel model ) {
		
		this.fleetEditorView = view;
		this.fleetEditorModel = model;
		this.fleetEditorModel.addObserver(fleetEditorView);
		addListener();

	}

	private void addListener() {
		fleetEditorView.getNextFleetVehicleJB().addActionListener(new BtnNextFleetVehicle());
		fleetEditorView.getPreviousFleetVehicleJB().addActionListener(new BtnPreviousFleetVehicle());
		fleetEditorView.getSaveVehicleJB().addActionListener(new BtnSaveVehicleActionListener());
		fleetEditorView.getDeleteVehicleJB().addActionListener(new BtnDeleteVehicleActionListener());
		fleetEditorView.getNextVehicleJB().addActionListener(new BtnNextVehicle());
		fleetEditorView.getPreviousVehicleJB().addActionListener(new BtnPreviousVehicle());
		fleetEditorView.getAddVehicleJB().addActionListener(new BtnAddVehicle());
		fleetEditorView.getSaveFleetJB().addActionListener(new BtnSaveFleetActionListener());
		fleetEditorView.getLoadFleetJB().addActionListener(new BtnLoadFleetActionListener());
	}
	
	public Component showView() {
		this.fleetEditorView.setVisible(true);
		return this.fleetEditorView;
	}

	public void setModel(Object o) {
		fleetEditorModel.loadModel((FleetEditorModel) o);
	}

	class BtnNextFleetVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.increaseVehiclePos();
			//TODO:
			//fahrzeug updaten
//			fleetEditorModel.updateVehicleAttributes();
		
		}
	}
	class BtnPreviousFleetVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.decreaseVehiclePos();
			//TODO:
			//fahrzeug updaten
//			fleetEditorModel.updateVehicleAttributes();

		}
	}

	class BtnNextVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.increaseVehicleSelectionPos();
		
		}
	}
	class BtnPreviousVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			fleetEditorModel.decreaseVehicleSelectionPos();

		}
	}
	class BtnAddVehicle implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			fleetEditorModel.addVehicle(fleetEditorModel.getVehicleSelection().get(fleetEditorModel.getVehicleSelectionPos()));
		}
	}
	
	class BtnSaveFleetActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog("Enter name");
			
			if(name == null) {
				return;
			}
			
			fleetEditorModel.setName(name);
			FleetEditorDao.getInstance().saveFleet(fleetEditorModel);
		}
	}
	
	class BtnSaveVehicleActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		
			fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).setName(fleetEditorView.getVehicleNameJTF().getText());;
			
			Integer maxSpeed = (Integer) fleetEditorView.getVehicleSpeedJCB().getSelectedItem();
			fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()).setMaxSpeed(maxSpeed.intValue());
			
			if(fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos()) instanceof Car){
				
				Car c = (Car) fleetEditorModel.getVehicles().get(fleetEditorModel.getVehiclePos());
				
				Float gasUsage = (Float) fleetEditorView.getVehicleGasUsageLowJCB().getSelectedItem();
				c.setGasConsumptionLow(gasUsage.floatValue());
				
				gasUsage = (Float) fleetEditorView.getVehicleGasUsageMediumJCB().getSelectedItem();
				c.setGasConsumptionMedium(gasUsage.floatValue());
				
				gasUsage = (Float) fleetEditorView.getVehicleGasUsageHighJCB().getSelectedItem();
				c.setGasConsumptionHigh(gasUsage.floatValue());

			}
		
		}
		
	}
	class BtnDeleteVehicleActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			fleetEditorModel.deleteVehicle();

		}
		
	}
	class BtnLoadFleetActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			String[] columns = new String[]{"ID",  "Name", "Vehicles", "Save Date"};
			
			List<FleetEditorModel> fleets = FleetEditorDao.getInstance().getFleets();
			
			Object[][] rowData = new Object[fleets.size()][columns.length];
			for(int i = 0; i < fleets.size(); i++) {
				rowData[i][0] = fleets.get(i).getId();
				rowData[i][1] = fleets.get(i).getName();
				rowData[i][2] = fleets.get(i).getVehicles().size();

				//TODO format util?
				DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.GERMAN);
				rowData[i][3] = df.format(fleets.get(i).getSaveDate().getTime());
			}
			
			LoaderDialog d = new LoaderDialog(MasterGui.getFrames()[0], FleetEditorController.this, FleetEditorDao.getInstance(), rowData, columns);
			d.setVisible(true);
		}
		
	}


	
}
