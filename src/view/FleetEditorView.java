package view;

import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FleetEditorView extends JPanel implements Observer {
	
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

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	
}
