package logic;

import java.io.Serializable;

/**
 * Class Bullet
 * 
 * Bullets are the ship's ammo and it's function is destroy {@link Asteroid} to increase score.
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 *
 */
public class Bullet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2385332666921324250L;
	/*   VARIABLES   */
	/**
	 * Bullet's x's on space
	 */
	private float pos_x;
	/**
	 * Bullet's y's on space
	 */
	private float pos_y;
	/**
	 * Bullet's delta velocity
	 */
	private float aceleration;
	/**
	 * Bullet's movement direction.
	 */
	private float angle;
	
	/*   METHODS   */
/**
 * getter for {@link #aceleration}
 */
	public float getAceleration(){
		return aceleration;
	}
	
	/**
	 * getter for {@link #angle}
	 */
	public float getAngle() {
		return angle;
	}
	/**
	 * getter for {@link #pos_x}
	 */
	public float getPos_x() {
		return pos_x;
	}
	/**
	 * getter for {@link #pos_y}
	 */
	public float getPos_y() {
		return pos_y;
	}
	/**
	 * setter for {@link #pos_y}
	 * @param {@link #pos_y} 
	 */
	public void setPos_y(float pos_y) {
		this.pos_y = pos_y;
	}
	/**
	 * setter for {@link #pos_x}
	 * @param {@link #pos_x} 
	 */
	public void setPos_x(float pos_x) {
		this.pos_x = pos_x;
	}
	/**
	 * setter for {@link #aceleration}
	 * @param {@link #aceleration} 
	 */
	public void setAceleration(float a){
		aceleration = a;
	}
	
	/**
	 * setter for {@link #angle}
	 * @param {@link #angle} 
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	/*   CONSTRUCTOR   */
	/**
	 * Constructor for new Bullet
	 * 
	 * This method creates a new object of the type {@link Bullet}
	 * 
	 * @param x {@link #pos_x}
	 * @param y {@link #pos_y}
	 * @param aceleration {@link #aceleration}
	 * @param angle {@link #angle}
	 */
	public Bullet(float x, float y, float aceleration, float angle){
		this.pos_x = x;
		this.pos_y = y;
		this.aceleration = aceleration;
		this.angle = angle;
	}
}
