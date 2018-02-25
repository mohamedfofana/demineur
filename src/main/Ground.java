package main;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Ground extends JButton {
	private int iX;
	private int iY;
	private int nbBombNear = 0;
	private boolean bomb = false;
	private boolean bombNear = false;
	private boolean cleared = false;
	
	public Ground(int x, int y) {
		this.iX = x;
		this.iY = y;
	}
    
	public void setiX(int iX) {
		this.iX = iX;
	}
	
	public int getiX() {
		return iX;
	}
	
	public void setiY(int iY) {
		this.iY = iY;
	}
	
	public int getiY() {
		return iY;
	}
	
	public void setBomb(boolean bomb) {
		this.bomb = bomb;
	}
	
	public boolean hasBomb() {
		return bomb;
	}
	
	public void setBombNear(boolean bombNear) {
		this.bombNear = bombNear;
	}
	
	public boolean hasBombNear() {
		return bombNear;
	}
	
	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}
	
	public boolean isCleared() {
		return cleared;
	}

	public int getNbBombNear() {
		return nbBombNear;
	}

	public void setNbBombNear(int nbBombNear) {
		this.nbBombNear = nbBombNear;
	}
	
}
