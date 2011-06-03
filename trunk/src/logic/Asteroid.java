package logic;

import java.awt.Color;
import java.awt.Polygon;
import java.util.Random;

public class Asteroid {

	/* VARIABLES */
	private float pos_x;
	private float pos_y;
	private int pol_x[];
	private int pol_y[];
	private Polygon poly;
	private float aceleration;
	private float rotation;
	private int type;
	private Color color;
	public static final int Grande=3;
	public static final int Medio=2;
	public static final int Pequeno=1;
	private int sizepol;

	/* METHODS */
	public float getX() {
		return pos_x;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public float getY() {
		return pos_y;
	}

	public float getAceleration() {
		return aceleration;
	}

	public float getRotation() {
		return rotation;
	}

	public void setX(float x) {
		//poly.translate((int) (x-pos_x), (int) 0);
		pos_x = x;

	}

	public Polygon getPoly() {
		int pol_xt[]=new int[sizepol];
		int pol_yt[]=new int[sizepol];
		//System.out.println("X: "+pos_x+" Y: "+pos_y);
		for(int i=0; i<sizepol;i++){
			pol_xt[i]=(int)pos_x+pol_x[i];
			pol_yt[i]=(int)pos_y+pol_y[i];
			//System.out.println('\t'+"X: "+pol_xt[i]+" Y: "+pol_yt[i]);
		}
		Polygon poly=new Polygon(pol_xt, pol_yt, pol_xt.length);
		return poly;
	}

	public void setPoly(Polygon poly) {
		this.poly = poly;
	}

	public void setY(float y) {
	//	poly.translate((int) 0, (int) (y-pos_y));
		pos_y = y;
		//poly.translate((int) pos_x, (int) pos_y);
	}

	public void setAceleration(float ac) {
		aceleration = ac;
	}

	public void setRotation(float r) {
		rotation = r;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/*   CONSTRUCTOR   */
	public Asteroid(float x, float y, float aceleration, float rotation,int type){
		this.type=type;
		this.pos_x = x;
		this.pos_y = y;
		this.aceleration = aceleration;
		this.rotation = rotation;
		Random a=new Random() ;
		sizepol=type*4;
		pol_x=new int[sizepol];
		pol_y=new int[sizepol];
		this.color=new Color(20+a.nextInt(235),20+a.nextInt(235),20+a.nextInt(235));
		poly= new Polygon();/*
		for(int i=0; i<type*6;i++){
			int xd=a.nextInt(type*10);
			int yd=a.nextInt(type*10);
			poly.addPoint((int) x+xd,(int)y+yd);
			poly.addPoint((int) x-xd,(int)y-yd);
		}*/
		for (int i = 0; i < sizepol; i++){
			int deltax=a.nextInt(type*7);
			int deltay=a.nextInt(type*7);
			pol_x[i]=(int) (-deltax + type*10 * Math.cos(i * 2 * Math.PI / (sizepol)));
			pol_y[i]=(int) (-deltay + type*10 * Math.sin(i * 2 * Math.PI / (sizepol)));
			/*poly.addPoint((int) (x-deltax + type*5 * Math.cos(i * 2 * Math.PI / (type*3))),
					(int) (y-deltay + type*5* Math.sin(i * 2 * Math.PI / (type*3))));

		*/
		}
		
		
	}
}
