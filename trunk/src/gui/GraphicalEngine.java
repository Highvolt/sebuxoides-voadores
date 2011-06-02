package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import logic.Asteroid;
import logic.Engine;
import logic.Highscores;

public class GraphicalEngine extends JPanel {

	private static final long serialVersionUID = 1L;
	//private double a = 0;
	Engine game;
	boolean firing;
	BufferedImage ship;
	BufferedImage ship_fired;
	BufferedImage asteroid;
	Clip shootfx;
	boolean soundeffects=true;
	boolean cheatao=false;
	ArrayList<Highscores> pont=new ArrayList<Highscores>();
	private String currentname;
	private boolean blink=true;
	long tempo_ini=-1;


	public GraphicalEngine clone(){
		GraphicalEngine g=new GraphicalEngine();
		g.game=game;
		g.firing=firing;
		g.ship=ship;
		g.ship_fired=ship;
		g.asteroid=asteroid;
		g.shootfx=shootfx;
		g.soundeffects=soundeffects;
		g.cheatao=cheatao;
		g.pont=pont;
		g.currentname=currentname;
		g.blink=blink;
		g.tempo_ini=tempo_ini;
		return g;
	}
	public void setter(GraphicalEngine g){

		g.game=game;
		g.firing=firing;
		g.ship=ship;
		g.ship_fired=ship;
		g.asteroid=asteroid;
		g.shootfx=shootfx;
		g.soundeffects=soundeffects;
		g.cheatao=cheatao;
		g.pont=pont;
		g.currentname=currentname;
		g.blink=blink;
		g.tempo_ini=tempo_ini;
		//return g;
	}

	public String getCurrentname() {
		return currentname;
	}

	public void setCurrentname(String currentname) {
		this.currentname = currentname;
	}

	public boolean isSoundeffects() {
		return soundeffects;
	}

	public void setSoundeffects(boolean soundeffects) {
		this.soundeffects = soundeffects;
		if(game!=null){
			game.setSoundeffects(soundeffects);
		}
	}

	/**
	 * This is the default constructor
	 */
	public GraphicalEngine() {
		super();
		initialize();
	}
	public GraphicalEngine(int i) {
		super();
		//initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		try {
			this.ship = ImageIO.read(getClass().getResource("/gui/nave.png"));
			this.ship_fired=ImageIO.read(getClass().getResource("/gui/nave_f.png"));
			this.asteroid = ImageIO.read(getClass().getResource("/gui/asteroid.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// Open an audio input stream.

			AudioInputStream audioIn = AudioSystem.getAudioInputStream(this.getClass().getResource("/gui/shoot.wav"));
			// Get a sound clip resource.
			shootfx = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			shootfx.open(audioIn);
			// clip.start();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		//	this.pont.add(new Highscores("Guida", 301));
		//	this.pont.add(new Highscores("Lodo", 10));
		//	this.pont.add(new Highscores("Pedro", 101));
		//	this.pont.add(new Highscores("burro", 1));
		Collections.sort(pont);




	}

	//mudar para a logica
	private void addScore(){
		Highscores h=new Highscores(game.getPlayername(), game.getPontuacao());
		pont.add(h);
		Collections.sort(pont);
		if(pont.size()>10){
			while(pont.size()>10){
				pont.remove(10);
			}
			Collections.sort(pont);
		}
	}

	public void newGame() {
		this.game = new Engine(this.getHeight(), this.getWidth());
		this.game.getSpaceShip().setAceleration((float)1);
		firing=false;
		for(Highscores a:pont){
			a.reset();
		}
		this.game.setPlayername(currentname);
	}

	public void update() {
		if (game != null) {
			if(game.isVivo()){
				this.game.update();
			}else{

				game.ship_explosion.stop();
				game.ship_explosion.setMicrosecondPosition(0);
				game.ship_explosion.start();

				addScore();


				game=null;
			}
		}else{
			if(Math.abs(System.currentTimeMillis()-this.tempo_ini)>1000){
				if(blink){
					blink=false;
					this.tempo_ini=System.currentTimeMillis();
				}else{
					blink=true;
					this.tempo_ini=System.currentTimeMillis();
				}		

			}
			for(int i=0;i<this.pont.size();i++){
				Highscores a=pont.get(i);
				if(a.getAst()==null){
					a.setX(this.getWidth());
					a.setY(this.getHeight());
				}
				a.update(i+1);
				if(!(a.isDone())){
					break;
				}
			}
			this.repaint();	
		}
	}

	public Engine getGame() {
		return game;
	}

	public void setGame(Engine game) {
		this.game = game;
	}

	public boolean isFiring() {
		return firing;
	}

	public void setFiring(boolean firing) {
		this.firing = firing;
	}

	public void shoot(){

		if(game.shoot(game
				.getSpaceShip().getX() + ship.getWidth() / 8-5, game
				.getSpaceShip().getY() + ship.getHeight() / 8-5))
			if(soundeffects){
				shootfx.stop();
				shootfx.setMicrosecondPosition(0);
				shootfx.start();
			}

	}

	@Override
	public void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		Graphics2D g = (Graphics2D) arg0;
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		// g.drawRect(0, 0, this.getWidth(), this.getHeight());
		if (game != null) {
			if(game.isVivo()){
				if(cheatao){
					g.rotate(game.getSpaceShip().getRotation(), (int) game
							.getSpaceShip().getX() + ship.getWidth() / 8, (int) game
							.getSpaceShip().getY() + ship.getHeight() / 8);
					if (ship != null && ship_fired!=null) {
						if(!(firing)){
							g.drawImage(Toolkit.getDefaultToolkit().createImage(ship.getSource()),(int) game.getSpaceShip().getX(),
									(int) game.getSpaceShip().getY(),ship.getWidth()/4,ship.getHeight()/4,null);
						}else{
							g.drawImage(Toolkit.getDefaultToolkit().createImage(ship_fired.getSource()),(int) game.getSpaceShip().getX(),
									(int) game.getSpaceShip().getY(),ship_fired.getWidth()/4,ship_fired.getHeight()/4,null);
							/*
							g.drawImage(ship_fired, null, (int) game.getSpaceShip().getX(),
									(int) game.getSpaceShip().getY());*/
						}


					}
				}
				game.setHeight(this.getHeight());
				game.setWidth(this.getWidth());
				Color c=new Color(255, 255, 255);
				g.setColor(c);

				g.drawString(game.getPlayername() + "  "+Integer.toString(game.getPontuacao()), 10, 10);
				for(int i=0;i<game.getBullets().size();i++){

					g.drawRect((int)game.getBullets().get(i).getPos_x(),(int)game.getBullets().get(i).getPos_y(),5,5);
					//g.drawOval((int)game.getBullets().get(i).getPos_x(),(int)game.getBullets().get(i).getPos_y(),10,10);
				}
				Color original=g.getColor();
				 
					BasicStroke dashed = new BasicStroke(3.0f);
					Stroke saves=g.getStroke();
					g.setStroke(dashed);
				
				for(int i=0;i<game.getAsteroids().size();i++){
					//g.translate(game.getAsteroids().get(i).getX(), game.getAsteroids().get(i).getY());
					g.setColor(game.getAsteroids().get(i).getColor());
					g.drawPolygon(game.getAsteroids().get(i).getPoly());

					//g.drawRect((int)game.getAsteroids().get(i).getX(),(int)game.getAsteroids().get(i).getY(),10*game.getAsteroids().get(i).getType(),10*game.getAsteroids().get(i).getType());
					//g.drawImage(Toolkit.getDefaultToolkit().createImage(asteroid.getSource()), (int)game.getAsteroids().get(i).getX(),(int)game.getAsteroids().get(i).getY(),10*game.getAsteroids().get(i).getType(),10*game.getAsteroids().get(i).getType(),null);
					//if(Math.abs(game.getAsteroids().get(i).getX()-game.getSpaceShip().getX())<ship.getWidth()/8 && Math.abs(game.getAsteroids().get(i).getY()-game.getSpaceShip().getY())<ship.getHeight()/8){
					/*
					if(game.getAsteroids().get(i).getPoly().intersects(game.getSpaceShip().getRect2D())){	
					game.ship_explosion.stop();
						game.ship_explosion.setMicrosecondPosition(0);
						game.ship_explosion.start();
						if(game.isVivo()){
							addScore();
						}
						game.setVivo(false);


					}
					 */
					//g.translate(-game.getAsteroids().get(i).getX(), -game.getAsteroids().get(i).getY());

				}
				g.setColor(original);
				g.setStroke(saves);
				if(!cheatao){
					g.rotate(game.getSpaceShip().getRotation(), (int) game
							.getSpaceShip().getX() + ship.getWidth() / 8, (int) game
							.getSpaceShip().getY() + ship.getHeight() / 8);
					//System.out.println(ship.getWidth() / 8+" "+ship.getHeight() / 8);
					/*	g.drawRect(game
							.getSpaceShip().getRect().x, game
							.getSpaceShip().getRect().y, game
							.getSpaceShip().getRect().width, game
							.getSpaceShip().getRect().height);*/
					if (ship != null && ship_fired!=null) {
						if(!(firing)){
							g.drawImage(Toolkit.getDefaultToolkit().createImage(ship.getSource()),(int) game.getSpaceShip().getX(),
									(int) game.getSpaceShip().getY(),ship.getWidth()/4,ship.getHeight()/4,null);
						}else{
							g.drawImage(Toolkit.getDefaultToolkit().createImage(ship_fired.getSource()),(int) game.getSpaceShip().getX(),
									(int) game.getSpaceShip().getY(),ship_fired.getWidth()/4,ship_fired.getHeight()/4,null);
							/*
						g.drawImage(ship_fired, null, (int) game.getSpaceShip().getX(),
								(int) game.getSpaceShip().getY());*/
						}
					}
				}

			}
			else{
				paintHighscores(g);
				/*
				Color c=new Color(255, 255, 255);
				g.setColor(c);
				g.drawString("FIM!!", this.getWidth()/2, this.getHeight()/2);*/
			}
		}else{
			paintHighscores(g);
			if(blink){
				Font f=new Font("Comic Sans MS",Font.PLAIN,20);
				g.setFont(f);
				g.setColor(Color.WHITE);
				g.drawString("Press Enter to Continue", this.getWidth()/2-g.getFontMetrics(f).stringWidth("Press Enter to Continue")/2, this.getHeight()/2-g.getFontMetrics(f).getHeight()/2);
				g.drawString("Press ESC to change name", this.getWidth()/2-g.getFontMetrics(f).stringWidth("Press ESC to change name")/2, this.getHeight()/2+g.getFontMetrics(f).getHeight()/2);
			}

		}
	}

	void paintHighscores(Graphics2D g){
		Color white=new Color(255, 255, 255);
		Color black=new Color(0, 0, 0);
		g.setBackground(black);
		Font f=new Font("Comic Sans MS",Font.PLAIN,20);
		g.setFont(f);
		g.setColor(white);
		for(Highscores a: this.pont){
			a.setLetras(g.getFontMetrics(f));
			g.drawString(a.getName()+ "  "+a.getScore(), (float) a.getX(), (float) a.getY());
			if(a.getAst()!=null){
				for(Asteroid ast: a.getAst()){
					g.drawImage(Toolkit.getDefaultToolkit().createImage(asteroid.getSource()), (int)(ast.getX()+a.getX()),(int)ast.getY(),10*ast.getType(),10*ast.getType(),null);
				}
			}
			if(!(a.isDone())){
				break;
			}
		}
	}
}
