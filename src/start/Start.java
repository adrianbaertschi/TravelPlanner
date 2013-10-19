package start;

import javax.swing.SwingUtilities;

import view.MasterGui;

public class Start {
	public static void main(String... args) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				MasterGui gui = new MasterGui();
				gui.setVisible(true);
			}
		});
	}
}
