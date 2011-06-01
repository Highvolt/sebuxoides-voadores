package logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Engine {

	/* VARIABLES */
	private SpaceShip spaceShip;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Bullet> bullets;
	private int height;
	private int width;
	float default_bullet_acc = 10;
	boolean vivo=true;
	private int pontuacao=0;
	private static final int num_maximo = 15;
	private Clip explosion;
	public Clip ship_explosion;
	boolean soundeffects=true;
	private double drag=0.025;
	private String playername="";
	
	/* METHODS */

	
	public boolean isVivo() {
		return vivo;
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public boolean isSoundeffects() {
		return soundeffects;
	}

	public void setSoundeffects(boolean soundeffects) {
		this.soundeffects = soundeffects;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public SpaceShip getSpaceShip() {
		return spaceShip;
	}

	public ArrayList<Asteroid> getAsteroids() {
		return asteroids;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public void setAsteroids(ArrayList<Asteroid> asteroids) {
		this.asteroids = asteroids;
	}

	public void setSpaceShip(SpaceShip spaceShip) {
		this.spaceShip = spaceShip;
	}

	public void shoot() {
		this.bullets.add(new Bullet(spaceShip.getX(), spaceShip.getY(),
				default_bullet_acc, spaceShip.getRotation()));
	}

	public void place_new_asteroid() {
		if (asteroids.size() >= num_maximo) {
			return;
		}
		Random randomizador = new Random();
		if (randomizador.nextBoolean()) {
			if (randomizador.nextBoolean()) {
				this.asteroids.add(new Asteroid(0, randomizador.nextFloat()
						* this.height, randomizador.nextFloat() * 5,
						(float) (randomizador.nextFloat() * 2 * Math.PI),
						Asteroid.Grande));
			} else {
				this.asteroids.add(new Asteroid(this.width, randomizador
						.nextFloat() * this.height,
						randomizador.nextFloat() * 5, (float) (randomizador
								.nextFloat() * 2 * Math.PI), Asteroid.Grande));
			}
		} else {
			if (randomizador.nextBoolean()) {
				this.asteroids.add(new Asteroid(randomizador.nextFloat()
						* this.width, 0, randomizador.nextFloat() * 5,
						(float) (randomizador.nextFloat() * 2 * Math.PI),
						Asteroid.Grande));
			} else {
				this.asteroids.add(new Asteroid(randomizador.nextFloat()
						* this.width, this.height,
						randomizador.nextFloat() * 5, (float) (randomizador
								.nextFloat() * 2 * Math.PI), Asteroid.Grande));
			}
		}
	}

	public void shoot(float x, float y) {
		System.out.println("Shooted");
		this.bullets.add(new Bullet(x, y, default_bullet_acc, spaceShip
				.getRotation()));
	}

	public void acceleration() {
		/*
		 * if(Math.abs(spaceShip.getRotation()-spaceShip.getAccRotation())>Math.abs
		 * (this.spaceShip.getRotation()/10)){
		 * this.spaceShip.setAccRotation(this
		 * .spaceShip.getAccRotation()+this.spaceShip.getRotation()/10);}
		 * 
		 * 
		 * 
		 * System.out.println("dif: "+Math.abs(spaceShip.getRotation()-spaceShip.
		 * getAccRotation())+"valor actual: "+ spaceShip.getAccRotation());
		 */
		/*
		 * float modacc=(float)
		 * Math.sqrt((double)(Math.pow(spaceShip.getAceleration
		 * ()*Math.sin(spaceShip.getRotation()), 2)+
		 * Math.pow(spaceShip.getAceleration
		 * ()*Math.cos(spaceShip.getRotation()), 2))); float modaccrot=(float)
		 * Math
		 * .sqrt((double)(Math.pow(spaceShip.getAceleration()*Math.sin(spaceShip
		 * .getAccRotation()), 2)+
		 * Math.pow(spaceShip.getAceleration()*Math.cos(spaceShip
		 * .getAccRotation()), 2))); float
		 */

		spaceShip.setVx((float) (spaceShip.getVx() + spaceShip.getAceleration()
				* Math.sin((double) spaceShip.getRotation())));
		spaceShip.setVy((float) (spaceShip.getVy() + spaceShip.getAceleration()
				* Math.cos((double) spaceShip.getRotation())));
		System.out.println("vx: " + spaceShip.getVx() + " vy: "
				+ spaceShip.getVy());

	}

	public void update() {


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


			if (v.getPos_x() > width) {
				bullets.remove(i);
			} else if (v.getPos_x() < 0) {
				bullets.remove(i);
			}
			if (v.getPos_y() > height) {
				bullets.remove(i);
			} else if (v.getPos_y() < 0) {
				bullets.remove(i);
			}

			for (int j=0;j< asteroids.size();j++) {
				Asteroid a=asteroids.get(j);
				if(Math.abs(v.getPos_x()-a.getX())<=10*a.getType() && Math.abs(v.getPos_y()-a.getY())<=10*a.getType()){
					System.out.println("Embateu!");
					if(bullets.size()!=0){
						bullets.remove(i);
					}
					pontuacao++;
					i=-1;
					if(soundeffects){
						this.explosion.stop();
						this.explosion.setMicrosecondPosition(0);
						this.explosion.start();
					}
					//j=asteroids.size();
					if(a.getType()!=Asteroid.Pequeno){
						asteroids.add(new Asteroid(a.getX(), a.getY(), a.getAceleration(), (float)(a.getRotation()+0.5*Math.PI), a.getType()-1));
						asteroids.add(new Asteroid(a.getX(), a.getY(), a.getAceleration(), (float)(a.getRotation()-0.5*Math.PI), a.getType()-1));
					}
					asteroids.remove(a);
					break;
				}

			}


		}
		for (int i=0;i< asteroids.size();i++) {
			Asteroid v=asteroids.get(i);



			if(Math.abs(v.getX()-getSpaceShip().getX())<2 && Math.abs(v.getY()-getSpaceShip().getY())<2){
				setVivo(false);
				if(soundeffects){
					ship_explosion.stop();
					ship_explosion.setMicrosecondPosition(0);
					ship_explosion.start();
				}
				System.out.println("morreu");
				return;
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
	public Engine(int height, int width) {
		this.height = height;
		this.width = width;
		this.spaceShip = new SpaceShip(width / 2, height / 2, 0, 0);
		this.asteroids = new ArrayList<Asteroid>();
		this.bullets = new ArrayList<Bullet>();


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
