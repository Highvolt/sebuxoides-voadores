package logic;

import java.io.Serializable;
import java.util.ArrayList;

public class screen_packet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7116179474031642257L;
	private ArrayList<SpaceShip> s;
	private ArrayList<Bullet> b;
	private ArrayList<Asteroid> a;
	private ArrayList<String> n;
	private ArrayList<Long> p;
	private int height;
	private int width;
	
	public ArrayList<Long> getP() {
		return p;
	}


	public void setP(ArrayList<Long> p) {
		this.p = p;
	}


	public int getHeight() {
		return height;
	}


	public ArrayList<String> getN() {
		return n;
	}


	public void setN(ArrayList<String> n) {
		this.n = n;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public ArrayList<SpaceShip> getS() {
		return s;
	}


	public void setS(ArrayList<SpaceShip> s) {
		this.s = s;
	}


	public ArrayList<Bullet> getB() {
		return b;
	}


	public void setB(ArrayList<Bullet> b) {
		this.b = b;
	}


	public ArrayList<Asteroid> getA() {
		return a;
	}


	public void setA(ArrayList<Asteroid> a) {
		this.a = a;
	}


	public screen_packet(ArrayList<SpaceShip> s, ArrayList<Bullet> b, ArrayList<Asteroid> a, int width,int height) {
		this.s=s;
		this.b=b;
		this.a=a;
		this.height=height;
		this.width=width;
	}

}
