package start;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Knot;
import view.MasterGui;

public class Start {
	public static void main(String... args) {
		
		final MasterGui gui = new MasterGui();
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				gui.setVisible(true);
			}
		});
		try {
			dbTest();
		} catch (Exception pe) {
			pe.printStackTrace();
			JOptionPane.showMessageDialog(gui, "Error connecting to database", "DB ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private static void dbTest() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("travelDB");
		EntityManager em = factory.createEntityManager();
		
	    em.getTransaction().begin();
	    Knot k = new Knot(10, 30);
	    
	    em.persist(k);
	    em.getTransaction().commit();
	    
	    TypedQuery<Knot> query = em.createQuery("select k from Knot k", Knot.class);
	    List<Knot> resultList = query.getResultList();
	    
	    for(Knot kn : resultList) {
	    	System.out.println(kn);
	    }
	    
	    em.close();
	}

}
