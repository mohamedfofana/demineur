package main.java;
import java.awt.EventQueue;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Application extends JFrame{
	public Application() {
		long startTime = System.nanoTime();
        initUI();
        long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("Durée initUI map : " + duration + "ms");
    }

    private void initUI() {
        // add objects to the panel
    		add(new Board());
        setSize(350, 600);
        setResizable(true);
        setTitle("Minsweeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    } 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	        		long startTime = System.nanoTime();
	            Application ex = new Application();
	            ex.setVisible(true);
	            long endTime = System.nanoTime();
		    		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		    		System.out.println("Durée invokeLater map : " + duration + "ms");
	        }
	    });
	}

}
