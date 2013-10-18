package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Car;

public class FleetEditorView extends JPanel {
	
	private JButton addCarJB = new JButton();
	private JTextField carNameJTF = new JTextField("insert Name");

	
	public FleetEditorView() {
		
		
		initComponents();
		
	}

	private void initComponents() {
		
		this.setLayout(new GridBagLayout());
        this.add(new JLabel("Hello, world!"));
        this.add(carNameJTF);
        this.add(addCarJB);
        		
	}

	/**
	 * @return the addCarJB
	 */
	public JButton getAddCarJB() {
		return addCarJB;
	}

	/**
	 * @param addCarJB the addCarJB to set
	 */
	public void setAddCarJB(JButton addCarJB) {
		this.addCarJB = addCarJB;
	}

	/**
	 * @return the carNameJTF
	 */
	public JTextField getCarNameJTF() {
		return carNameJTF;
	}

	/**
	 * @param carNameJTF the carNameJTF to set
	 */
	public void setCarNameJTF(JTextField carNameJTF) {
		this.carNameJTF = carNameJTF;
	}

	
}
