package logic;

import java.awt.Rectangle;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Engine2 {

	/* VARIABLES */

	
	private static Instrumentation inst;
	
	private ArrayList<Player> players;

	Thread listener;
	private ServerSocket socas;
	private int up_k=38;
	private int right_k=39;
	private int left_k=37;
	private int fire_k=32;
	private int hyper_k=16;

	/**
	 * Array List of on-screen asteroids.
	 * 
	 * @see Asteroid
	 */
	private ArrayList<Asteroid> asteroids;



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
	 * Minimum time between shoots in millisecs.
	 */
	private long time_between_shoots=200;



	/**
	 * Time of the travel in hyperspace.
	 */
	private long time_on=500;

	/**
	 * Initial player's lives
	 */
	final private int MAX_LIVES=3;


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


	private Runnable waiter= new Runnable() {
		synchronized public void run() {
			while(true){
				Socket client=null;
				try {
					client=socas.accept();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(client!=null){
					Player p1=new Player(MAX_LIVES, width/2, height/2, 1,0, client);
					players.add(p1);
				}

			}
		}
	};


	/**
	 * Returns the state of the player
	 * 
	 * @return Returns true if ship still have lifes left
	 */


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
	 * getter for array list of the asteroids on the screen
	 * @return Array list of asteroids on the screen
	 * @see Asteroid
	 */
	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
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
	 * Setter of array list of Asteroids on the screen
	 * @param asteroids
	 * @see Asteroid
	 */
	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	//	/**
	//	 * add a new bullet on ship's position.
	//	 * @see Bullet
	//	 * @deprecated
	//	 */
	//	public void shoot() {
	//		this.bullets.add(new Bullet(spaceShip.getX(), spaceShip.getY(),
	//				default_bullet_acc, spaceShip.getRotation()));
	//	}



	/**
	 * If ship is hyperspace this method is called to drop out of hyperspace after a while.
	 * {@link #time_on} {@link #start_trip } {@link #hyperspace}
	 */
	public void Hyperspace(){
		for(Player p: players){
			if(p.isHyperspace()){
				if(System.currentTimeMillis()-p.getTimeofhyperspace()>time_on){
					Random a=new Random();
					p.getSpaceShip().setX(a.nextInt(width));
					p.getSpaceShip().setY(a.nextInt(height));
					p.setHyperspace(false);

				}
			}
		}
	}


	public int getLives(Player p) {
		return p.getLives();
	}

	/**
	 * Respawn the player after a certain amount of time, if the middle of the screen is clear of asteroids
	 * {@link #killed} {@link #time_on}
	 */
	public void Revive(){
		for(Player p: players){
			if(p.isRespawning()){
				if(p.getLives()>0){
					if(System.currentTimeMillis()-p.getTimeofdead()>(time_on*5)){
						//Random a=new Random();
						boolean placed=true;
						p.setHyperspace(false);
						p.setSpaceShip(new SpaceShip(width/2, height/2, 1, 0));


						//System.out.println("Trying to place...");
						for (Asteroid v: asteroids) {

							if(v.getPoly().intersects(p.getSpaceShip().getRect2D())){
								placed=false;
								break;
							}
						}
						if(placed){
							p.setRespawning(false);
						}
					}
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
	public void enterhyper(Player p){
		p.setHyperspace(true);
		p.setTimeofdead(System.currentTimeMillis());
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
	public boolean shoot(Player p,float x, float y) {
		//System.out.println("Shooted");
		if(!p.isHyperspace()&& !p.isRespawning())
			if(System.currentTimeMillis()-p.getLast_shoot()>time_between_shoots){
				p.setLast_shoot(System.currentTimeMillis());
				if(p.getBullets().size()<bmax){
					p.shoot(x, y, default_bullet_acc);
					return true;
				}
			}
		return false;
	}
	/**
	 * Increases the velocity of the ship according to rotation of the ship and aceleration.
	 * @see SpaceShip
	 */
	public void acceleration(Player p) {

		if(p.isHyperspace() || p.isRespawning())
			return;
		p.getSpaceShip().setVx((float) (p.getSpaceShip().getVx() + p.getSpaceShip().getAceleration()
				* Math.sin((double) p.getSpaceShip().getRotation())));
		p.getSpaceShip().setVy((float) (p.getSpaceShip().getVy() + p.getSpaceShip().getAceleration()
				* Math.cos((double) p.getSpaceShip().getRotation())));
		/*System.out.println("vx: " + spaceShip.getVx() + " vy: "
				+ spaceShip.getVy());*/

	}



	public ArrayList<Player> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}


	public void keyanalizer(){
		for(Player p: players){
			if(p.isPlaying() && p.getData()!=null){
				for(int a: p.getData().getKeyboard()){
					if(a==hyper_k){
						enterhyper(p);
					}
					if (a == left_k) {
						//getPanelfull().setFiring(false);
						System.out.println(p.getName()+"left");
						p.getSpaceShip().setRotation((float)(p.getSpaceShip().getRotation()-0.2));
					}
					if (a == right_k) {
						//getPanelfull().setFiring(false);
						System.out.println(p.getName()+"right");
						p.getSpaceShip().setRotation((float)(p.getSpaceShip().getRotation()+0.2));
						System.out.println(p.getSpaceShip().getRotation());
					}
					if(a==up_k){
						System.out.println(p.getName()+"acc actual: "+p.getSpaceShip().getVx()+" , "+p.getSpaceShip().getVy());
						acceleration(p);
						System.out.println(p.getName()+"acc depois: "+p.getSpaceShip().getVx()+" , "+p.getSpaceShip().getVy());
					}
					if(a==fire_k){
						//getPanelfull().setFiring(false);
						shoot(p,(int)p.getSpaceShip().getX() + 30 / 2, (int) p.getSpaceShip().getY() + 36 / 2);
					}
				}
			}
		}
	}

	public void sendscreen(){
		ArrayList<Bullet> b=new ArrayList<Bullet>();
		ArrayList<SpaceShip> s=new ArrayList<SpaceShip>();
		ArrayList<String> n= new ArrayList<String>();
		ArrayList<Long> po= new ArrayList<Long>();
		for (Player p: players) {
			b.addAll(p.getBullets());
			if(!p.isRespawning() && !p.isHyperspace()){
				s.add(p.getSpaceShip());
				n.add(p.getName());
				po.add(p.getScore());
			}
		}
		screen_packet scr=new screen_packet(s, b, asteroids,width,height);
		scr.setN(n);
		scr.setP(po);
	//	System.out.println("Size of the screen:" + inst.getObjectSize(scr));
		for (Player p: players) {
			p.send_screen(scr);
		}
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
	synchronized public void update() {
		place_new_asteroid();
		keyanalizer();
		Hyperspace();
		Revive();

		for(Player p: players){
			if(p.isPlaying()){
				//System.out.println("Player: "+p.getName());
				p.getSpaceShip().setVx((float)(p.getSpaceShip().getVx()*(1-this.drag)));
				p.getSpaceShip().setVy((float)(p.getSpaceShip().getVy()*(1-this.drag)));

				p.getSpaceShip().setX((float) (p.getSpaceShip().getX() + p.getSpaceShip().getVx()));
				p.getSpaceShip().setY((float) (p.getSpaceShip().getY() - p.getSpaceShip().getVy()));
				if (p.getSpaceShip().getX() > width) {
					p.getSpaceShip().setX(0);
				} else if (p.getSpaceShip().getX() < 0) {
					p.getSpaceShip().setX(width);
				}
				if (p.getSpaceShip().getY() > height) {
					p.getSpaceShip().setY(0);
				} else if (p.getSpaceShip().getY() < 0) {
					p.getSpaceShip().setY(height);
				}

				for (int i=0;i< p.getBullets().size();i++) {
					Bullet v=p.getBullets().get(i);
					v.setPos_x((float) (v.getPos_x() + v.getAceleration()
							* Math.sin(v.getAngle())));
					v.setPos_y((float) (v.getPos_y() - v.getAceleration()
							* Math.cos(v.getAngle())));


					if (v.getPos_x() > width || v.getPos_x() < 0 || v.getPos_y() > height || v.getPos_y() < 0) {
						p.getBullets().remove(i);
					}

					for (int j=0;j< asteroids.size();j++) {
						Asteroid a=asteroids.get(j);
						if(a.getPoly().intersects(new Rectangle((int)v.getPos_x(),(int)v.getPos_y(),5,5))){
							//if(Math.abs(v.getPos_x()-a.getX())<=10*a.getType() && Math.abs(v.getPos_y()-a.getY())<=10*a.getType()){
							//System.out.println("Embateu!");
							if(p.getBullets().size()!=0){
								p.getBullets().remove(i);
							}
							if(a.getType()==1){
								p.setScore(p.getScore()+pequeno);
							}
							if(a.getType()==2){
								p.setScore(p.getScore()+medio);
							}
							if(a.getType()>=3){
								p.setScore(p.getScore()+grande);
							}
							if(p.getScore()>=75*p.getUltima() ){
								p.setLives(p.getLives()+1);
								p.setUltima(p.getUltima()+1);
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
					if(!p.isHyperspace() && !p.isRespawning())
						if(v.getPoly().intersects(p.getSpaceShip().getRect2D())){
							/*if(--lives==0){
							setVivo(false);
						}else{
							killed=System.currentTimeMillis();
							cooldown=true;
							if(soundeffects){
								ship_explosion.stop();
								ship_explosion.setMicrosecondPosition(0);
								ship_explosion.start();
							}



						}*/
							p.die();
							System.out.println("morreu");
							//	addScore();

							//return;
						}


//					v.setX((float) (v.getX() + v.getAceleration()
//							* Math.sin(v.getRotation())));
//					v.setY((float) (v.getY() - v.getAceleration()
//							* Math.cos(v.getRotation())));
//
//					if (v.getX() > width) {
//						v.setX(0);
//					} else if (v.getX() < 0) {
//						v.setX(width);
//					}
//					if (v.getY() > height) {
//						v.setY(0);
//					} else if (v.getY() < 0) {
//						v.setY(height);
//					}

				}
			}else{
				players.remove(p);
				update();
				break;
			}
		}
		
		for (int i=0;i< asteroids.size();i++) {
			Asteroid v=asteroids.get(i);

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
		
		
		sendscreen();
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
	public Engine2(int height, int width,int port) {
		this.height = height;
		this.width = width;
		//this.spaceShip = new SpaceShip(width / 2, height / 2, 0, 0);
		this.asteroids = new ArrayList<Asteroid>();
		this.players=new ArrayList<Player>();
		try {
			this.socas=new ServerSocket(port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//this.bullets = new ArrayList<Bullet>();
		//this.lives=MAX_LIVES;
		/*	try {
			this.socas=new ServerSocket(port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		}*/
		this.soundeffects=false;
		listener=new Thread(waiter);
		listener.start();

	}

	public static void main(String[] args) {
		Engine2 m= new Engine2(400, 400, 1234);
		Scanner s=new Scanner(System.in);
		while(true){
			s.next();
			m.update();
		}
	}

}
