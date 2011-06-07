package logic;

import java.util.ArrayList;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.Serializable;

/**
 * Class Highscore
 * 
 * This class saves the data of each higscore and calculate the ellipse of asteroids rotating
 * over it.
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 *
 */
public class Highscores implements Comparable<Highscores>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4675767888033621341L;
	/**
	 * Actual x's position of Highscore upper corner on the screen
	 */
	private	double x;
	/**
	 * Actual y's position of Highscore upper corner on the screen
	 */
	private double y;
	/**
	 * radius #1 of asteroids ellipse around the highscore
	 */
	private double a;
	/**
	 * radius #2 of asteroids ellipse around the highscore
	 */
	private double b;
	/**
	 * Player's name on the highscore
	 */
	private String name;
	/**
	 * Player's score on the highscore
	 */
	private Integer score;
//	private Integer pos;
	
	/**
	 * Array list of {@link Asteroid} of the ellipse ring.
	 */
	private ArrayList<Asteroid> ast ;
	/**
	 * Front metrics of actual font.
	 * @see FontMetrics
	 * @see Font
	 * @see Graphics2D
	 */
	private FontMetrics letras;
	/**
	 * The highscore is in it's final position
	 */
	private boolean done=false;
	/**
	 * {@link Highscores} time stamp in millisecs. Used to avoid duplicated highscores
	 */
	private long timestamp=0;
 /**
  * True if it's finished	
  * @return {@link #done}
  */
	public boolean isDone() {
		return done;
	}
/**
 *  setter for {@link #done}
 * @param done - {@link #done}
 */
	public void setDone(boolean done) {
		this.done = done;
	}
/**
 * getter for {@link #ast}
 * @return {@link #ast}
 */
	public ArrayList<Asteroid> getAst() {
		return ast;
	}
/**
 * setter for {@link #ast}
 * @param ast - {@link #ast}
 */
	public void setAst(ArrayList<Asteroid> ast) {
		this.ast = ast;
	}

/**
 * Constructor for Highscore.
 * 
 * Creates a highscore with a ellipse of asteroids around it. (This method uses System.currentTimeMillis() to put a timestamp  )
 * @param nome - {@link #name}
 * @param score - {@link #score}
 */
	public Highscores(String nome, int score){
		this.name=nome;
		this.score=score;
		this.timestamp=System.currentTimeMillis();
		

	}

/**
 * Implements equal to this type of object
 * 
 */
	@Override
	public boolean equals(Object obj) {
		Highscores a=(Highscores) obj;
		return name.equals(a.name) && score.equals(a.score) && (Math.abs(timestamp-a.timestamp)<100);
	}

	/**
	 * getter for {@link #x}
	 * @return {@link #x}
	 */
	public double getX() {
		return x;
	}
	/**
	 * setter for {@link #x}
	 * @param x - {@link #x}
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * getter for {@link #y}
	 * @return {@link #y}
	 */
	public double getY() {
		return y;
	}
	/**
	 * setter for {@link #y}
	 * @param {@link #y}
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * getter for {@link #name}
	 * @return {@link #name}
	 */
	public String getName() {
		return name;
	}
	/**
	 * setter for {@link #name}
	 * @param name - {@link #name}
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * getter for {@link #score}
	 * @return {@link #score}
	 */
	public int getScore() {
		return score;
	}
	/**
	 * setter for {@link #score}
	 * @param {@link #score}
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * Reset the highscore.
	 *
	 * Puts {@link #ast}=null and {@link #done} false.
	 * This method is used to restart a highscore to start over the animation
	 */
	public void reset(){
		ast=null;
		//System.out.println(ast);
		done=false;
	}
/**
 * This method is called every time the screen refresh to calculate the new position of the
 * highscore and the position of each asteroid ({@link #ast}) of the ring.
 * 
 * Uses {@link #getye(double, int)}
 * @param pos - position on the highscore table
 */
	public void update(int pos) {
		
		if(letras != null){
			a=letras.stringWidth((name+"   "+score))/2+letras.stringWidth((name+"   "+score))/4;
			b=letras.getHeight();
			if(this.x>30){
				x=x-3;
			}
			if(this.y>(10+50*pos)){
				y=y-4;
			}
			
			if(!(this.x>30 || this.y>(10+50*pos))){
				done=true;
			}

			if(ast==null){
				ast = new ArrayList<Asteroid>();
				for(int i=(int)(-a/2);i<(a/2+a);i=i+60){
					ast.add(new Asteroid((float) i, (float)0,0,1,2));
					ast.add(new Asteroid((float) i, (float)0,0,-1,2));
					
				}
				/*
				ast.add(new Asteroid((float)(0+a/2),(float) (0+2*b), 0, 1, 2));
				ast.add(new Asteroid((float)(0+a),(float) (0+b), 0, 1, 2));
				ast.add(new Asteroid((float)(0+a/2),(float) (0), 0, -1, 2));
				ast.add(new Asteroid((float)(0+a),(float) (0+b), 0, -1, 2));*/
			}
			for(Asteroid at: ast){
				if(Double.isNaN(getye((double)(at.getX()+at.getRotation()*1)-(0+a/2),(int)at.getRotation()))){
					at.setRotation(-1*at.getRotation());
					//System.out.println(at.getRotation());
					//at.setX((float)(at.getX()+at.getRotation()*5));
					
			
				}
				at.setX((float)(at.getX()+at.getRotation()*1));
				at.setY((float)(y-b/2)+(float)getye((double) at.getX()-(0+a/2),(int)at.getRotation()));
				/*if(at.getX()>(a+x)){
					at.setRotation(-1);
				}
				if(at.getX()<(x-a)){
					at.setRotation(1);
				}*/
				
				//System.out.println("x: "+at.getX()+" Y: "+at.getY());
			}
			//System.out.println("done!");

		} 

	}
/**
 * getter for {@link #letras}
 * @return {@link #letras}
 */
	public FontMetrics getLetras() {
		return letras;
	}

	/**
	 * calculate the position y of an ellipse defined by {@link #a} and {@link #b} given an X postion and rot.
	 * @param x - position on x axis
	 * @param rot - side of the elipse
	 * @return y's axis position
	 */
	private double getye(double x, int rot){
		//System.out.println("y calc:"+Math.sqrt((1-(Math.pow(x, 2.0)/Math.pow(a, 2.0)))*Math.pow(b,2.0)));
		if(rot>0){
		return Math.sqrt((1-(Math.pow(x, 2.0)/Math.pow(a, 2.0)))*Math.pow(b,2.0));}
		else{
			return -Math.sqrt((1-(Math.pow(x, 2.0)/Math.pow(a, 2.0)))*Math.pow(b,2.0));
		}
	}

	/**
	 * setter for {@link #letras}
	 * @param letras - {@link #letras}
	 */
	public void setLetras(FontMetrics letras) {
		this.letras = letras;
	}


	@Override
	public int compareTo(Highscores o) {
		Highscores a= (Highscores)o;
		return  a.score.compareTo(score);
	}




}
