package main.java;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {
	private final int nbBombs=15;
	private final int cols = 7;
	private final int rows = 13;
	private int bombPosed = 0;
	private int flagPosed = 0;
	private int bombFreeCase = 0;
	private final ImageIcon LOOSE_ICON = new ImageIcon("resources/images/loose.jpg");
	private final ImageIcon WIN_ICON = new ImageIcon("resources/images/win.jpg");
	private final Icon MINE_ICON = new ImageIcon("resources/images/mine.jpg");
	Ground[][] map = new Ground[rows][cols];
	
	public Board() {
		long startTime = System.nanoTime();
		setLayout(new GridLayout(rows, cols));
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				Ground gr = new Ground(i,j); 
				gr.addMouseListener(new MouseAdapter() { 
			          public void mousePressed(MouseEvent me) { 
			              Ground currentGround = (Ground) me.getSource();
			              checkIfButtonHasBomb(currentGround);
			            } 
				});
				this.add(gr); 
				map[i][j]=gr;
			}
		}
		long endTime = System.nanoTime();
		long duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("Durée init map : " + duration + "ms");
		startTime = System.nanoTime();
		putBombs();
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("Durée putBombs : " + duration + "ms");
		startTime = System.nanoTime();
		setNbBombsNear();
		endTime = System.nanoTime();
		duration = (endTime - startTime)/1000000;  //divide by 1000000 to get milliseconds.
		System.out.println("Durée setNbBombsNear : " + duration + "ms");
	}
	
	private void putBombs() {
		int compt= 0; 
		int i = 0;
		while (i < nbBombs)
		{
			int x, y;
			x = randInt(0, rows-1);
			y = randInt(0, cols-1);
			if (x < rows && y < cols && compt < nbBombs){
				if (!map[x][y].hasBomb())
				{
					map[x][y].setBomb(true);
					compt++;
					i++;
				}
			}
		} 
		bombPosed = compt;
		flagPosed = compt;
	}
	
	private void setNbBombsNear(){
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++){
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
	
	private void checkIfButtonHasBomb(Ground currentGround){
		if(currentGround != null)
		{			
			if (currentGround.hasBomb()){
				gameOver(true);
			}
			else{
				if (currentGround.hasBombNear())
					currentGround.setText(""+currentGround.getNbBombNear());
				else
				{
					clearAroundRecur(currentGround.getiX(), currentGround.getiY());
				}
				currentGround.setEnabled(false);
				bombFreeCase += 1;
			}
			if (gameWon()){
				gameOver(false);
			}
		}
	}
	
	/**
	 * Pour chaque case autour faire
	 * 	Si la case ne contient pas de bombe et n'a pas une bombe autour
	 * clearAround(i,j) // fonction récursif 
	 * 
	 * 
	 *
	 **/
	private void clearAroundRecur(int i,int j)
	{
		if (i<0 || i>=rows || j<0 || j>=cols)
			; 
		else  if( map[i][j].isCleared())
			;
		else if (map[i][j].hasBomb())
			;
		else if (map[i][j].hasBombNear()){
			map[i][j].setText(""+map[i][j].getNbBombNear());
			map[i][j].setEnabled(false);
			bombFreeCase += 1;
			;
		}
		else {			 	
			
			map[i][j].setEnabled(false);
			bombFreeCase += 1;
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

	private boolean gameWon(){
		int compt = 0;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++){
				if (!map[i][j].isEnabled()){
					compt++;
				}
			} 
	    }
		if ((cols*rows) - bombPosed == compt)
			return true;
		return false;
	}
	
	/**
	 * 
	 * lost : true = perdu; false = gagné
	 *
	 **/
	private void gameOver(boolean lost)
	{
		JOptionPane jop1 = new JOptionPane();
		ImageIcon img;
		if (lost){
			jop1.showMessageDialog(null, null, "Perdu !!!", 1, LOOSE_ICON);
		}
		else{
			jop1.showMessageDialog(null, null, "Gagné !!!", 1,WIN_ICON);
		}
		int compt;
		compt = 0;
		int mapX[] = new int[100];
		int mapY[] = new int[100];
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++){
				if (map[i][j].hasBomb()){
					map[i][j].setIcon(MINE_ICON);
					mapX[compt] = i;
					mapY[compt] = j;
					compt++;
				}
				else if (map[i][j].hasBombNear()){
					map[i][j].setText(""+map[i][j].getNbBombNear());
				}
				map[i][j].setEnabled(false);
			}
			
	}
	
	private int randInt(int min, int max){
		return (int) Math.floor(Math.random() * (max + 1 - min)); 
	}
	
	private boolean checkBombs(int i, int j){
		if (i>=0 && i < rows)
			if(j>=0 && j < cols)
				if (map[i][j].hasBomb())
					return true; 
		return false;
	}
}
