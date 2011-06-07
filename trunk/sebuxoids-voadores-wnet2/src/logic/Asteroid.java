package logic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;
import java.util.Random;

import javax.swing.text.Position;
import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * Class Asteroid
 * 
 * Asteroid are the player "enemy" and "best-friend" because they can kill the
 *  player if they reach it or give him points if he destroy them.
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 *
 */
public class Asteroid implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6417319843645886260L;
	/* VARIABLES */
	/**
	 * Asteroid x's position on space
	 */
	private float pos_x;
	/**
	 * Asteroid y's position on space
	 */
	private float pos_y;
	/**
	 * Array of x's positions for polygon points
	 * @see Polygon
	 */
	private int pol_x[];
	/**
	 * Array of y's positions for polygon points
	 * @see Polygon
	 */
	private int pol_y[];
//	private Polygon poly;
	/**
	 * Asteroid delta velocity.
	 */
	private float aceleration;
	/**
	 * Asteroid rotaion
	 */
	private float rotation;
	/**
	 * Asteroid type (aka size) 
	 */
	private int type;
	/**
	 * Asteroid polygon color
	 * @see Color
	 */
	private Color color;
	/**
	 * Constant for big asteroid
	 */
	public static final int Grande=3;
	/**
	 * Constant for medium asteroid
	 */
	public static final int Medio=2;
	/**
	 * Constant for small asteroid
	 */
	public static final int Pequeno=1;
	/**
	 * Size of polygon
	 */
	private int sizepol;

	/* METHODS */
	/**
	 * getter for {@link #pos_x}
	 */
	public float getX() {
		return pos_x;
	}

	/**
	 * getter for {@link #color}
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * setter for {@link #color}
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	/**
	 * getter for {@link #pos_y}
	 */
	public float getY() {
		return pos_y;
	}
	/**
	 * getter for {@link #aceleration}
	 */
	public float getAceleration() {
		return aceleration;
	}
	/**
	 * getter for {@link #rotation}
	 */
	public float getRotation() {
		return rotation;
	}
	/**
	 * setter for {@link #pos_x}
	 */
	public void setX(float x) {
		//poly.translate((int) (x-pos_x), (int) 0);
		pos_x = x;

	}
	/**
	 * getter for {@link #pol_x} and {@link #pol_y} converting the arrays on a polygon
	 * for being draw on the screen.
	 * 
	 * @return Polygon representation of the asteroid
	 * @see Polygon
	 */
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


	/**
	 * setter for {@link #pos_y}
	 */
	public void setY(float y) {
	//	poly.translate((int) 0, (int) (y-pos_y));
		pos_y = y;
		//poly.translate((int) pos_x, (int) pos_y);
	}
	/**
	 * setter for {@link #aceleration}
	 */
	public void setAceleration(float ac) {
		aceleration = ac;
	}
	/**
	 * setter for {@link #rotation}
	 */
	public void setRotation(float r) {
		rotation = r;
	}
	/**
	 * getter for {@link #type}
	 */
	public int getType() {
		return type;
	}
	/**
	 * setter for {@link #type}
	 */
	public void setType(int type) {
		this.type = type;
	}

	/*   CONSTRUCTOR   */
/**
 * Constructor for a new asteroid.
 * 
 * This method calculates a random shape for the asteroid with the {@link #sizepol} calculated
 * using the {@link #type}. {@link #pol_x} and {@link #pol_y} are filled with the calculated points.
 * The {@link #color} of the asteroid is randomly generated here to
 *  @see Polygon
 *  @see Color
 */
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
	/*
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
