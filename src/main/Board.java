package main;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class Board extends JPanel {
	int nbBomb;
	int nbMine = 9;
	int cols = 5;
	int rows = 10;
	JButton[][] tab = new JButton[rows][cols];
	
	public Board() {
		setLayout(new GridLayout(rows, cols));
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				Ground gr = new Ground(i,j);  
				this.add(gr); 
				tab[i][j]=gr;
			}
		}
	}
	
	public JButton[][] getTab() {
		return tab;
	}
	
	public JButton getTab(int i, int j) {
		return tab[i][j];
	}
}
