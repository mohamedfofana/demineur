package main.java;

public class Utils {
	public static int randInt(int min, int max){
		return (int) Math.floor(Math.random() * (max + 1 - min)); 
	}
}
