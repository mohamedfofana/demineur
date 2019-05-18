package main.java;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class Application extends JFrame{
	private static final long serialVersionUID = -4957996012591753440L;
	public Application() {
		initUI();
	}

	private void initUI() {
		add(new Board());
		setResizable(false);
		pack();
		setTitle("Minesweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	} 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Application app = new Application();
				app.setVisible(true);
			}
		});
	}

}
