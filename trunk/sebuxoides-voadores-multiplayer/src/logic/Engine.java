package logic;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Main class of the asteroids game.
 * 
 * This class is responsible for calculating the movements of every asteroid, bullet or ship
 * and the collision between them and the result.
 * 
 *
 * @author Margarida Pereira
 *  @author Pedro Borges
 *
 */
public class Engine {

	/* VARIABLES */
	/**
	 * Player's ship
	 * @see SpaceShip
	 */
	private SpaceShip spaceShip;
	/**
	 * Array List of on-screen asteroids.
	 * 
	 * @see Asteroid
	 */
	private ArrayList<Asteroid> asteroids;

	/**
	 * Array List of on-screen Bullets.
	 * 
	 * @see Bullet
	 */
	private ArrayList<Bullet> bullets;

	/**
	 * Field actual height.
	 * 
	 * 
	 */
	private int height;
	/**
	 * Field actual width.
	 */
	private int width;

	/**
	 * Default bullet speed.
	 * 
	 * 
	 */
	float default_bullet_acc = 10;

	/**
	 * Player's state
	 */
	boolean vivo=true;
	/**
	 * Player's score.
	 */
	private int pontuacao=0;
	/**
	 * Asteroid Explosion sound.
	 * 
	 * @see Clip
	 */
	private Clip explosion;
	/**
	 * Ship Explosion sound.
	 * 
	 * @see Clip
	 */
	public Clip ship_explosion;
	/**
	 * Sound Effects activation status.
	 */
	boolean soundeffects=true;
	/**
	 * Players name.
	 */
	private String playername="";
	/**
	 * Time in millisecs of the last shoot
	 */
	private long last_shoot=0;
	/**
	 * Minimum time between shoots in millisecs.
	 */
	private long time_between_shoots=200;

	/**
	 * Ship is in hyperspace or not.
	 */
	private boolean hyperspace=false;
	/**
	 * Time in milisecs of the entrance of the ship in hyperspace.
	 */
	private long start_trip=0;
	/**
	 * Time of the travel in hyperspace.
	 */
	private long time_on=500;
	/**
	 * Player's lives.
	 */
	private int lives=0;
	/**
	 * Initial player's lives
	 */
	final private int MAX_LIVES=3;
	/**
	 * Time, in milisecs, of the player's dead.
	 */
	private long killed=0;
	/**
	 * True if player is waiting a respawn.
	 */
	private boolean cooldown=false;
	/**
	 * score value of small asteroids
	 */
	private final int pequeno=5;
	/**
	 * score value of medium asteroids
	 */
	private final int medio=3;
	/**
	 * score value of big asteroids
	 */
	private final int grande=1;

	//private int ultima_pont_vida=0;
	/**
	 * Multiplicity between the last score and 45, for bonus life calculation.
	 */
	private int ultima=1;

	/*
	 * Values of difficulty
	 */

	/**
	 * Maximum number of medium and big asteroids on the screen (changed by difficulty level).
	 */
	private int num_maximo = 15;

	/**
	 * Value of drag aplied to the ship (changed by difficulty level).
	 */
	private double drag=0.025;
	/**
	 * Maximum level of missil's on screen (changed by difficulty level).
	 */
	private int bmax=7;
	/**
	 * Asteroids maximum speed (changed by difficulty level).
	 */
	private float asteroids_max_speed=5;

	//	ArrayList<Highscores> pont=new ArrayList<Highscores>();

	/* METHODS */
	/*
	public ArrayList<Highscores> getPont() {
		return pont;
	}
	public void setPont(ArrayList<Highscores> pont) {
		this.pont = pont;
	}
	 */
	/*	
	private void addScore(){	
		Highscores h=new Highscores(getPlayername(), getPontuacao());
		for (Highscores a : pont) {
			if(a.equals(h)){
				System.out.println("duplicado evitado!");
				return;
			}
		}
		pont.add(h);
		System.out.println(getPlayername()+" - "+getPontuacao()+" Added");
		Collections.sort(pont);
		if(pont.size()>10){
			while(pont.size()>10){
				pont.remove(10);
			}
			Collections.sort(pont);
		}
	}
	 */


	/**
	 * Returns the state of the player
	 * 
	 * @return Returns true if ship still have lifes left
	 */
	public boolean isVivo() {
		return vivo;
	}
	/**
	 * Getter for actual player's name.
	 * 
	 * @return name of the player
	 */
	public String getPlayername() {
		return playername;
	}
	/**
	 * Setter for player's name.
	 * 
	 * @param playername - new player's name
	 */
	public void setPlayername(String playername) {
		this.playername = playername;
	}


	/**
	 * Sound is enable.
	 * 
	 * @return Returns true if sound is enabled, false otherwise.
	 * 
	 */
	public boolean isSoundeffects() {
		return soundeffects;
	}


	/**
	 * Setter for sound enabled state.
	 * 
	 * @param soundeffects - true for enable, false for disable sounds.
	 */
	public void setSoundeffects(boolean soundeffects) {
		this.soundeffects = soundeffects;
	}


	/**
	 * 
	 * @return Player's actual score.
	 */
	public int getPontuacao() {
		return pontuacao;
	}

	/**
	 * Setter for player's score.
	 * 
	 * @param pontuacao - player's actual score
	 */
	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}
	/**
	 * Setter for alive state of the player;
	 * @param vivo - true if it's alive.
	 */
	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}
	/**
	 * Getter for Player's ship
	 * @return Player's ship
	 * @see SpaceShip
	 */
	public SpaceShip getSpaceShip() {
		return spaceShip;
	}
	/**
	 * getter for array list of the asteroids on the screen
	 * @return Array list of asteroids on the screen
	 * @see Asteroid
	 */
	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}
	/**
	 * Getter for array list of the bullets on the screen
	 * @return array list of all visible bullets
	 * @see Bullet
	 */
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	/**
	 * getter for Height of game field
	 * @return height of the game field
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * getter for width of game field
	 * @return width of the game field
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * setter of width of the game field
	 * @param width of the game field in pixels
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * setter of height of the game field
	 * @param height of the game field in pixels
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Setter of array list of bullets visible on the screen
	 * @param bullets
	 * @see Bullet
	 */
	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	/**
	 * Setter of array list of Asteroids on the screen
	 * @param asteroids
	 * @see Asteroid
	 */
	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}
	/**
	 * Setter of player's ship
	 * @param spaceShip
	 * @see SpaceShip
	 */
	public void setSpaceShip(SpaceShip spaceShip) {
		this.spaceShip = spaceShip;
	}
	/**
	 * add a new bullet on ship's position.
	 * @see Bullet
	 * @deprecated
	 */
	public void shoot() {
		this.bullets.add(new Bullet(spaceShip.getX(), spaceShip.getY(),
				default_bullet_acc, spaceShip.getRotation()));
	}
	/**
	 * Getter for in Hyperspace
	 * @return returns true if ship is on hyperspace, false otherwise.
	 */
	public boolean isHyperspace() {
		return hyperspace;
	}
	/**
	 * Setter for Ship on Hyperspace
	 * @param hyperspace - true for enter in hyperspace, false to exit.
	 */
	public void setHyperspace(boolean hyperspace) {
		this.hyperspace = hyperspace;
	}

	/**
	 * If ship is hyperspace this method is called to drop out of hyperspace after a while.
	 * {@link #time_on} {@link #start_trip } {@link #hyperspace}
	 */
	public void Hyperspace(){
		if(hyperspace){
			if(System.currentTimeMillis()-start_trip>time_on){
				Random a=new Random();
				spaceShip.setX(a.nextInt(width));
				spaceShip.setY(a.nextInt(height));
				hyperspace=false;

			}
		}

	}
	/**
	 * Getter for {@link #cooldown}
	 * @return {@link #cooldown}
	 */
	public boolean isCooldown() {
		return cooldown;
	}
	/**
	 * Setter for {@link #cooldown}
	 * @param {@link #cooldown}
	 */

	public void setCooldown(boolean cooldown) {
		this.cooldown = cooldown;
	}
	/**
	 * Getter for {@link #lives}
	 * @return {@link #lives}
	 */
	public int getLives() {
		return lives;
	}

	/**
	 * Respawn the player after a certain amount of time, if the middle of the screen is clear of asteroids
	 * {@link #killed} {@link #time_on}
	 */
	public void Revive(){
		if(lives>0){
			if(System.currentTimeMillis()-killed>(time_on*5)){
				//Random a=new Random();
				boolean placed=true;
				this.hyperspace=false;
				spaceShip=new SpaceShip(width/2, height/2, 1, 0);


				//System.out.println("Trying to place...");
				for (Asteroid v: asteroids) {

					if(v.getPoly().intersects(getSpaceShip().getRect2D())){
						placed=false;
						break;
					}
				}
				if(placed){
					cooldown=false;
				}
			}
		}

	}

	/**
	 * Place a new asteroid on the screen if the number of medium and big asteroids isn't bigger than {@link #num_maximo}
	 * 
	 * The asteroid is place on the limits of the screen with random direction and speed(limited by {@link #asteroids_max_speed})
	 */
	public void place_new_asteroid() {
		int numgrande=0;
		for(Asteroid a: asteroids){
			if(a.getType()>=Asteroid.Medio){
				numgrande++;
			}
		}
		if (numgrande >= num_maximo) {
			return;
		}
		Random randomizador = new Random();
		if (randomizador.nextBoolean()) {
			if (randomizador.nextBoolean()) {
				this.asteroids.add(new Asteroid(0, randomizador.nextFloat()
						* this.height, randomizador.nextFloat() * asteroids_max_speed,
						(float) (randomizador.nextFloat() * 2 * Math.PI),
						Asteroid.Grande));
			} else {
				this.asteroids.add(new Asteroid(this.width, randomizador
						.nextFloat() * this.height,
						randomizador.nextFloat() * asteroids_max_speed, (float) (randomizador
								.nextFloat() * 2 * Math.PI), Asteroid.Grande));
			}
		} else {
			if (randomizador.nextBoolean()) {
				this.asteroids.add(new Asteroid(randomizador.nextFloat()
						* this.width, 0, randomizador.nextFloat() * asteroids_max_speed,
						(float) (randomizador.nextFloat() * 2 * Math.PI),
						Asteroid.Grande));
			} else {
				this.asteroids.add(new Asteroid(randomizador.nextFloat()
						* this.width, this.height,
						randomizador.nextFloat() * asteroids_max_speed, (float) (randomizador
								.nextFloat() * 2 * Math.PI), Asteroid.Grande));
			}
		}
	}

	/**
	 * Makes ship enter in {@link #hyperspace}
	 */
	public void enterhyper(){
		hyperspace=true;
		start_trip=System.currentTimeMillis();
	}


	/**
	 * Setter for difficulty level.
	 * <br>
	 * 1  -
	 *- {@link #asteroids_max_speed} =  3
	 *- {@link #num_maximo} = 6
	 *- {@link #b_max} = 20
	 *- {@link #drag} = 0.04
	 * <br>
	 *
	 *2  -
	 *- {@link #asteroids_max_speed} =  4
	 *- {@link #num_maximo} = 10
	 *- {@link #b_max} = 15
	 *- {@link #drag} = 0.035
	 * <br>
	 *3  -
	 *
	 *- {@link #asteroids_max_speed} =  5
	 *- {@link #num_maximo} = 15
	 *- {@link #b_max} = 10
	 *- {@link #drag} = 0.03
	 * <br>
	 *4  -
	 *- {@link #asteroids_max_speed} =  7
	 *- {@link #num_maximo} = 18
	 *- {@link #b_max} = 8
	 *- {@link #drag} = 0.025
	 * <br>
	 *5  -
	 *- {@link #asteroids_max_speed} =  10
	 *- {@link #num_maximo} = 24
	 *- {@link #b_max} = 6
	 *- {@link #drag} = 0.015
	 * <br>
	 * @param mode
	 */
	public void setmode(int mode){
		System.out.println("Modo changed to: "+mode);
		if(mode==0){
			num_maximo = 6;
			bmax=20;
			asteroids_max_speed=3;
			drag=0.04;
		}
		if(mode==1){
			num_maximo = 10;
			bmax=15;
			asteroids_max_speed=4;
			drag=0.035;
		}
		if(mode==2){
			num_maximo = 15;
			bmax=10;
			asteroids_max_speed=5;
			drag=0.03;
		}
		if(mode==3){
			num_maximo = 18;
			bmax=8;
			asteroids_max_speed=7;
			drag=0.025;
		}
		if(mode>=4){
			num_maximo = 24;
			bmax=6;
			asteroids_max_speed=10;
			drag=0.015;
		}

	}

	/**
	 *  Place a new missile on the screen
	 *  
	 * @param x - shoot start x's position
	 * @param y- shoot start y's position
	 * @return Returns true if missile is placed, false if cannot be placed
	 * @see Bullet
	 */
	public boolean shoot(float x, float y) {
		//System.out.println("Shooted");
		if(!hyperspace && !cooldown)
			if(System.currentTimeMillis()-last_shoot>time_between_shoots){
				last_shoot=System.currentTimeMillis();
				if(bullets.size()<bmax){
					this.bullets.add(new Bullet(x, y, default_bullet_acc, spaceShip
							.getRotation()));
					return true;
				}
			}
		return false;
	}
	/**
	 * Increases the velocity of the ship according to rotation of the ship and aceleration.
	 * @see SpaceShip
	 */
	public void acceleration() {

		if(hyperspace && !cooldown)
			return;
		spaceShip.setVx((float) (spaceShip.getVx() + spaceShip.getAceleration()
				* Math.sin((double) spaceShip.getRotation())));
		spaceShip.setVy((float) (spaceShip.getVy() + spaceShip.getAceleration()
				* Math.cos((double) spaceShip.getRotation())));
		/*System.out.println("vx: " + spaceShip.getVx() + " vy: "
				+ spaceShip.getVy());*/

	}


	/**
	 * Method called for the timer. 
	 * 
	 * This method updates scores, ship, bullets and asteroids
	 * location on the screen according to their velocities. 
	 * Also in this method collisions are detected killing the player or 
	 * destroying asteroids and bullets.
	 * 
	 * @see SpaceShip
	 * @see Bullet
	 * @see Asteroid
	 * @see #Hyperspace()
	 * @see #Revive()
	 * @see #pontuacao
	 * 
	 */
	public void update() {
		Hyperspace();
		if(cooldown){
			Revive();
		}
		this.spaceShip.setVx((float)(this.spaceShip.getVx()*(1-this.drag)));
		this.spaceShip.setVy((float)(this.spaceShip.getVy()*(1-this.drag)));

		this.spaceShip.setX((float) (spaceShip.getX() + spaceShip.getVx()));
		this.spaceShip.setY((float) (spaceShip.getY() - spaceShip.getVy()));
		if (spaceShip.getX() > width) {
			spaceShip.setX(0);
		} else if (spaceShip.getX() < 0) {
			spaceShip.setX(width);
		}
		if (spaceShip.getY() > height) {
			spaceShip.setY(0);
		} else if (spaceShip.getY() < 0) {
			spaceShip.setY(height);
		}

		for (int i=0;i< bullets.size();i++) {
			Bullet v=bullets.get(i);
			v.setPos_x((float) (v.getPos_x() + v.getAceleration()
					* Math.sin(v.getAngle())));
			v.setPos_y((float) (v.getPos_y() - v.getAceleration()
					* Math.cos(v.getAngle())));


			if (v.getPos_x() > width || v.getPos_x() < 0 || v.getPos_y() > height || v.getPos_y() < 0) {
				bullets.remove(i);
			}

			for (int j=0;j< asteroids.size();j++) {
				Asteroid a=asteroids.get(j);
				if(a.getPoly().intersects(new Rectangle((int)v.getPos_x(),(int)v.getPos_y(),5,5))){
					//if(Math.abs(v.getPos_x()-a.getX())<=10*a.getType() && Math.abs(v.getPos_y()-a.getY())<=10*a.getType()){
					//System.out.println("Embateu!");
					if(bullets.size()!=0){
						bullets.remove(i);
					}
					if(a.getType()==1){
						pontuacao=pontuacao+pequeno;
					}
					if(a.getType()==2){
						pontuacao=pontuacao+medio;
					}
					if(a.getType()>=3){
						pontuacao=pontuacao+grande;
					}
					if(pontuacao>=75*ultima ){
						lives++;
						ultima++;
						//			ultima_pont_vida=pontuacao;
					}
					//pontuacao++;
					i=-1;
					if(soundeffects){
						this.explosion.stop();
						this.explosion.setMicrosecondPosition(0);
						this.explosion.start();
					}
					//j=asteroids.size();
					if(a.getType()!=Asteroid.Pequeno){
						asteroids.add(new Asteroid(a.getX(), a.getY(), a.getAceleration(), (float)(v.getAngle()+0.5*/*Math.PI)+0.1**/a.getRotation()), a.getType()-1));
						asteroids.add(new Asteroid(a.getX(), a.getY(), a.getAceleration(), (float)(v.getAngle()-0.5*/*Math.PI)+0.1**/a.getRotation()), a.getType()-1));
					}
					asteroids.remove(a);
					break;
				}

			}


		}
		for (int i=0;i< asteroids.size();i++) {
			Asteroid v=asteroids.get(i);



			//if(Math.abs(v.getX()-getSpaceShip().getX())<2 && Math.abs(v.getY()-getSpaceShip().getY())<2){
			if(!hyperspace && !cooldown)
				if(v.getPoly().intersects(getSpaceShip().getRect2D())){
					if(--lives==0){
						setVivo(false);
					}else{
						killed=System.currentTimeMillis();
						cooldown=true;
						if(soundeffects){
							ship_explosion.stop();
							ship_explosion.setMicrosecondPosition(0);
							ship_explosion.start();
						}

					}

					System.out.println("morreu");
					//	addScore();

					//return;
				}


			v.setX((float) (v.getX() + v.getAceleration()
					* Math.sin(v.getRotation())));
			v.setY((float) (v.getY() - v.getAceleration()
					* Math.cos(v.getRotation())));

			if (v.getX() > width) {
				v.setX(0);
			} else if (v.getX() < 0) {
				v.setX(width);
			}
			if (v.getY() > height) {
				v.setY(0);
			} else if (v.getY() < 0) {
				v.setY(height);
			}

		}

	}

	/* CONSTRUCTOR */

	/**
	 * Constructor for Engine class
	 * 
	 * This method places the ship on the center of space and initialize all the array lists.
	 * Load the sound clips. 
	 * 
	 * @param height - height of space field
	 * @param width - width of space field
	 */
	public Engine(int height, int width) {
		this.height = height;
		this.width = width;
		this.spaceShip = new SpaceShip(width / 2, height / 2, 0, 0);
		this.asteroids = new ArrayList<Asteroid>();
		this.bullets = new ArrayList<Bullet>();
		this.lives=MAX_LIVES;

		try {
			// Open an audio input stream.

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("/logic/explosion.wav"));
			// Get a sound clip resource.
			this.explosion = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			this.explosion.open(audioIn);
			// clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}


		try {
			// Open an audio input stream.

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("/logic/explosion_s.wav"));
			// Get a sound clip resource.
			this.ship_explosion = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			this.ship_explosion.open(audioIn);
			// clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}
}
