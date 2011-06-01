package logic;

public class Asteroid {

	/* VARIABLES */
	private float pos_x;
	private float pos_y;
	private float aceleration;
	private float rotation;
	private int type;

	public static final int Grande=3;
	public static final int Medio=2;
	public static final int Pequeno=1;
	
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
	}
}
