package main;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {
	int nbBombs=15;
	int nbMine = 9;
	int cols = 7;
	int rows = 13;
	int bombPosed = 0;
	int flagPosed = 0;
	int bombFreeCase = 0;
	Ground[][] map = new Ground[rows][cols];
	
	public Board() {
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
		putBombs();
		setNbBombsNear();
	}
	
	public void putBombs() {
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
					map[x][y].setBackground(Color.RED);
					map[x][y].setOpaque(true);
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
		// On teste la reussite du cast avant de proceder a un quelconque acces dessus !
		if(currentGround != null) //emetteurCasted vaut 0 si le cast a echoue
		{			
			if (currentGround.hasBomb()){
				gameOver(true);
				System.out.println("Game Over !");
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
			if (doIWin()){
				gameOver(false);
			}
		}
	}
	
	/**
	 * Pour chaque case autour faire
	 * 	Si la ces ne contient pas de bombe et n'a pas une bombe dans ses contour
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
	/*
	private void clearAround(int i, int j){
		for(int k = -1; k < 2; k++)
			for(int l = -1; l <2; l++)
				if (i+k>=0 && i+k < rows)
					if(j+l>=0 && j+l < cols)
						if (!map[i+k][j+l].hasBomb())
						{
							if (map[i+k][j+l].hasBombNear())
								map[i+k][j+l].setText(""+map[i+k][j+l].getNbBombNear());
							map[i+k][j+l].setEnabled(false);
						} 
	}
	*/
	private boolean doIWin(){
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
		if (lost)
		{
			System.out.println("Perdu !!");
		}
		else
		{
			System.out.println("Gagné !");
		}
		int compt;
		compt = 0;
		int mapX[] = new int[100];
		int mapY[] = new int[100];
		Icon warnIcon = new ImageIcon("images/mine.jpg");
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++){
				//map[i][j].setStyle("icon",null);
				if (map[i][j].hasBomb()){
					map[i][j].setIcon(warnIcon);
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
