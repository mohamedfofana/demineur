package main;
import java.awt.EventQueue;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Application extends JFrame{
	public Application() {
        initUI();
    }

    private void initUI() {
        // add objects to the panel
    		add(new Board());
        
        setSize(350, 450);
        setResizable(false);
        setTitle("Demineur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    } 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            Application ex = new Application();
	            ex.setVisible(true);
	        }
	    });
	}

}
