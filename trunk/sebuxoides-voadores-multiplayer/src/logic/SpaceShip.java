package logic;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
/**
 * 
 * Player's vehicle class
 * 
 * This class saves the data of the vehicle such as speed, position, rotation and acceleration rotation
 * 
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 *
 */
public class SpaceShip implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1798633482450318948L;
/* VARIABLES */
/**
 * X's axis position of the ship on the screen
 */
	private float pos_x;
	/**
	 * Y's axis position of the ship on the screen
	 */
	private float pos_y;
	/**
	 * velocity x's axis position of the ship
	 */
	private float vx;
	/**
	 * velocity y's axis position of the ship
	 */
	private float vy;
	/**
	 * aceleration of the ship
	 */
	private float aceleration;
	/**
	 * rotation of the ship image
	 */
	private float rotation;
	/**
	 * angle of velocity
	 */
	private float accRotation;
//	Rectangle rect;

	/* METHODS */
	/**
	 * getter for {@link #pos_x}
	 * @return {@link #pos_x}
	 */
	public float getX() {
		return pos_x;
	}
	/**
	 * getter for {@link #pos_y}
	 * @return {@link #pos_y}
	 */
	public float getY() {
		return pos_y;
	}

	/**
	 * getter for {@link #aceleration}
	 * @return {@link #aceleration}
	 */
	public float getAceleration() {
		return aceleration;
	}
	/**
	 * getter for {@link #rotation}
	 * @return {@link #rotation}
	 */
	public float getRotation() {
		return rotation;
	}
	/**
	 * setter for {@link #pos_x}
	 * @param x - {@link #pos_x}
	 */
	public void setX(float x) {
		pos_x = x;
	}
	/**
	 * setter for {@link #pos_y}
	 * @param y - {@link #pos_y}
	 */
	public void setY(float y) {
		pos_y = y;
	}
	/**
	 * getter for an {@link Rectangle2D} representing the limits of the ship
	 * @return {@link Rectangle2D} representing the limits of the ship
	 */
	public Rectangle2D getRect2D() {
		Rectangle2D a= new Rectangle((int)pos_x, (int)pos_y, 36, 30);
		return a;
	}
	/**
	 * getter for an {@link Rectangle} representing the limits of the ship
	 * @return {@link Rectangle} representing the limits of the ship
	 */
	public Rectangle getRect() {
		Rectangle a= new Rectangle((int)pos_x, (int)pos_y, 36, 30);
		return a;
	}

	//public void setRect(Rectangle2D rect) {
		//this.rect = rect;
	//}
	/**
	 * setter for {@link #aceleration}
	 * @param ac - {@link #aceleration}
	 */
	public void setAceleration(float ac) {
		aceleration = ac;
	}
	/**
	 * setter for {@link #rotation}
	 * @param r - {@link #rotation}
	 */
	public void setRotation(float r) {
		rotation = r;
	}
	/**
	 * getter for {@link #pos_x}
	 * @return {@link #pos_x}
	 */
	public float getAccRotation() {
		return accRotation;
	}
	/**
	 * getter for {@link #pos_x}
	 * @return {@link #pos_x}
	 */
	public void setAccRotation(float accRotation) {
		this.accRotation = accRotation;
	}
	/**
	 * getter for {@link #vx}
	 * @return {@link #vx}
	 */
	public float getVx() {
		return vx;
	}
	/**
	 * setter for {@link #vx}
	 * @param {@link #vx}
	 */
	public void setVx(float vx) {
		this.vx = vx;
	}
	/**
	 * getter for {@link #vy}
	 * @return {@link #vy}
	 */
	public float getVy() {
		return vy;
	}
	/**
	 * setter for {@link #vy}
	 * @param {@link #vy}
	 */
	public void setVy(float vy) {
		this.vy = vy;
	}

	/*   CONSTRUCTOR   */
	/**
	 * Constructor of SpaceShip object.
	 * 
	 * Creates a new ship using the given arguments.
	 * 
	 * @param x - {@link #pos_x}
	 * @param y - {@link #pos_y}
	 * @param aceleration - default acceleration ({@link #aceleration})
	 * @param rotation - {@link #rotation}
	 */
	public SpaceShip(float x, float y, float aceleration, float rotation){
		this.pos_x = x;
		this.pos_y = y;
		this.vx=0;
		this.vy=0;
		this.aceleration = aceleration;
		this.rotation = rotation;
		//rect=new Rectangle(30,30);
		//rect.get
		
	}
}
