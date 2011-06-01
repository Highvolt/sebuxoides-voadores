package logic;

public class Bullet {

	/*   VARIABLES   */
	private float pos_x;
	private float pos_y;
	private float aceleration;
	private float angle;
	
	/*   METHODS   */
	public float getAceleration(){
		return aceleration;
	}
	public float getAngle() {
		return angle;
	}
	public float getPos_x() {
		return pos_x;
	}
	public float getPos_y() {
		return pos_y;
	}
	public void setPos_y(float pos_y) {
		this.pos_y = pos_y;
	}
	public void setPos_x(float pos_x) {
		this.pos_x = pos_x;
	}
	public void setAceleration(float a){
		aceleration = a;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	
	/*   CONSTRUCTOR   */
	public Bullet(float x, float y, float aceleration, float angle){
		this.pos_x = x;
		this.pos_y = y;
		this.aceleration = aceleration;
		this.angle = angle;
	}
}
