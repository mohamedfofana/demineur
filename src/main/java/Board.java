package main.java;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel {
	private static final long serialVersionUID = 5973708153113586932L;
	private final int NUM_MINE=5;
	private final int COLS = 7;
	private final int ROWS = 13;
	private final ImageIcon LOOSE_ICON = new ImageIcon("resources/images/loose.jpg");
	private final ImageIcon WIN_ICON = new ImageIcon("resources/images/win.jpg");
	private final Icon MINE_ICON = new ImageIcon("resources/images/mine.jpg");
	private int bombPosed = 0;
	private Ground[][] map = new Ground[ROWS][COLS];

	public Board() {
		setLayout(new GridLayout(ROWS, COLS));
		initMap();
		placeBombs();
		setNbBombsNear();
	}

	/**
	 * Initialize the board map
	 */
	private void initMap() {
		for(int i=0;i<ROWS;i++) {
			for(int j=0;j<COLS;j++) {
				Ground gr = new Ground(i,j); 
				gr.addMouseListener(new MouseAdapter() { 
					public void mousePressed(MouseEvent me) { 
						checkIfButtonHasBomb((Ground) me.getSource());
					} 
				});
				gr.setPreferredSize(new Dimension(40, 40));
				add(gr); 
				map[i][j]=gr;
			}
		}
	}

	/**
	 * Place boms in the board
	 */
	private void placeBombs() {
		int compt= 0; 
		int i = 0;
		while (i < NUM_MINE)
		{
			int x, y;
			x = Utils.randInt(0, ROWS-1);
			y = Utils.randInt(0, COLS-1);
			if (x < ROWS && y < COLS && compt < NUM_MINE){
				if (!map[x][y].hasBomb())
				{
					map[x][y].setBomb(true);
					compt++;
					i++;
				}
			}
		} 
		bombPosed = compt;
	}

	/**
	 * Update case and put in the label the number of bomb near 
	 * 
	 **/
	private void setNbBombsNear(){
		for(int i = 0; i < ROWS; i++)
			for(int j = 0; j < COLS; j++){
				int compt = 0;
				if (!map[i][j].hasBomb()){
					for(int k =-1; k < 2; k++)
						for(int l = -1; l <2; l++)
							if (checkBombs(i+k,j+l) == true)
								compt++;
					if (compt != 0) {
						map[i][j].setNbBombNear(compt);
						map[i][j].setBombNear(true);
					}
				}
			} 
	}

	/**
	 * Check if a case has Bomb
	 * If not show the number of bomb around
	 **/
	private void checkIfButtonHasBomb(Ground currentGround){
		if (currentGround.hasBomb()){
			gameOver();
		}
		else{
			if (currentGround.hasBombNear())
				currentGround.setText(""+currentGround.getNbBombNear());
			else
			{
				clearAroundRecur(currentGround.getiX(), currentGround.getiY());
			}
			currentGround.setEnabled(false);
		}
		checkIfPlayerWon();
	}

	/**
	 * For each case, set the number of bomb around 
	 *
	 **/
	private void clearAroundRecur(int i,int j)
	{
		if (i<0 || i>=ROWS || j<0 || j>=COLS)
			; 
		else  if( map[i][j].isCleared())
			;
		else if (map[i][j].hasBomb())
			;
		else if (map[i][j].hasBombNear()){
			map[i][j].setText(""+map[i][j].getNbBombNear());
			map[i][j].setEnabled(false);
			;
		}
		else {			 	

			map[i][j].setEnabled(false);
			map[i][j].setCleared(true);; 
			clearAroundRecur(i+1, j-1);
			clearAroundRecur(i+1, j);
			clearAroundRecur(i+1, j+1);
			clearAroundRecur(i-1, j-1);
			clearAroundRecur(i-1, j);
			clearAroundRecur(i-1, j+1);
			clearAroundRecur(i, j+1);
			clearAroundRecur(i, j-1);

		}
	}

	/**
	 * Check if the player Won 
	 * i.e. If all cases without bomb was found
	 * And display win message if it's true
	 */
	private void checkIfPlayerWon(){
		int compt = 0;
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++){
				if (!map[i][j].isEnabled()){
					compt++;
				}
			} 
		}
		if ((COLS*ROWS) - bombPosed == compt) {
			JOptionPane.showMessageDialog(null, null, "Gagné !!!", 1,WIN_ICON);
			revealMap();
		}
	}

	/**
	 * Show player status
	 * Reveal cases
	 **/
	private void gameOver(){
		JOptionPane.showMessageDialog(null, null, "Perdu !!!", 1, LOOSE_ICON);
		revealMap();
	}

	/**
	 * Reveal all cases content
	 */
	private void revealMap() {
		for(int i = 0; i < ROWS; i++) {
			for(int j = 0; j < COLS; j++){
				if (map[i][j].hasBomb()){
					map[i][j].setIcon(MINE_ICON);
				}
				else if (map[i][j].hasBombNear()){
					map[i][j].setText(""+map[i][j].getNbBombNear());
				}
				map[i][j].setEnabled(false);
			}
		}
	}

	/**
	 * Check if a case has Bomb
	 * @param i
	 * @param j
	 * @return true if has bomb or else false
	 */
	private boolean checkBombs(int i, int j){
		if (i>=0 && i < ROWS)
			if(j>=0 && j < COLS)
				if (map[i][j].hasBomb())
					return true; 
		return false;
	}
}
