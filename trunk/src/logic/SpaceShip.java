package logic;

public class SpaceShip {

	/* VARIABLES */
	private float pos_x;
	private float pos_y;
	private float vx;
	private float vy;
	private float aceleration;
	private float rotation;
	private float accRotation;

	/* METHODS */
	public float getX() {
		return pos_x;
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
		pos_x = x;
	}

	public void setY(float y) {
		pos_y = y;
	}

	public void setAceleration(float ac) {
		aceleration = ac;
	}

	public void setRotation(float r) {
		rotation = r;
	}
	
	public float getAccRotation() {
		return accRotation;
	}

	public void setAccRotation(float accRotation) {
		this.accRotation = accRotation;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}

	/*   CONSTRUCTOR   */
	public SpaceShip(float x, float y, float aceleration, float rotation){
		this.pos_x = x;
		this.pos_y = y;
		this.vx=0;
		this.vy=0;
		this.aceleration = aceleration;
		this.rotation = rotation;
	}
}
