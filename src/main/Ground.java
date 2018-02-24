package main;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Ground extends JButton {
	private int iX;
	private int iY;
	
	public Ground(int x, int y) {
		this.iX = x;
		this.iY = y;
		setText(x+","+y);
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
	
}
