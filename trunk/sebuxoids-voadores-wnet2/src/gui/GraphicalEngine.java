package gui;

import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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


/**
 * Interface between the logic and GUI.
 * 
 * In this class the elements of the game (logic) are used to place sprites on graphical mode. The method
 * update of this class is responsible for calling the right mode of game.
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 *
 */
public class GraphicalEngine extends JPanel {

	boolean dualplayer=false;
	long espera=-1;
	String state="not";
	String mystate="not";
	int scores=0;
	private static final long serialVersionUID = 1L;
	//private double a = 0;
	/**
	 * Logic class instance.
	 * @see Engine
	 */
	Engine game;
	/**
	 * The ship is with its engines on fire
	 */
	boolean firing;
	/**
	 * Sprite of the ship
	 */
	BufferedImage ship;
	/**
	 * Sprite of the ship with the reactors working
	 */
	BufferedImage ship_fired;
	/**
	 * Sprite of an asteroid (used for highscores)
	 */
	BufferedImage asteroid;
	/**
	 * Background image.
	 */
	BufferedImage bg;
	/**
	 * Highscores background image
	 */
	BufferedImage hbg;
	/**
	 * Backgroud image, resized to actual frame size.
	 */
	BufferedImage bg_actual=null;
	/**
	 * Ship's sprite resize to be used as a life counter
	 */
	BufferedImage life=null;
	/**
	 * Resized highscore asteroids to be used as highscore asteroids.
	 */
	BufferedImage hasteroids=null;
	/**
	 * shoot sound effect.
	 */
	Clip shootfx;
	/**
	 * Sound efects enabled status
	 */
	boolean soundeffects=true;
	/**
	 * @deprecated
	 * Debug mode for a mode of game
	 */
	boolean cheatao=false;
	/**
	 * Array list with the six higher scores.
	 */
	ArrayList<Highscores> pont=new ArrayList<Highscores>();
	/**
	 * Current player's name.
	 */
	private String currentname;
	/**
	 * Used to blink the initial text.
	 */
	private boolean blink=true;
	/**
	 * Time in milisec of the last blink
	 */
	long tempo_ini=-1;
	/**
	 * true if is the first run of this class at this time.
	 */
	boolean first=true;
	/**
	 * actual radius of the ship explosion
	 */
	int radius=0;
	/**
	 * X's position of the last ship known position.
	 */
	int shipinhax=0;
	/**
	 * Y's position of the last ship known position.
	 */
	int shipinhay=0;
	private String socket_data="";



	public String getMystate() {
		return mystate;
	}
	public void setMystate(String mystate) {
		this.mystate = mystate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isDualplayer() {
		return dualplayer;
	}
	public void setDualplayer(boolean dualplayer) {
		this.dualplayer = dualplayer;
	}
	/**
	 * Sync two instances of this class.
	 * 
	 * Used to sync the fullscreen and window panel.
	 * 
	 * @param g - Instance to be sync
	 */
	public void setter(GraphicalEngine g){

		g.game=game;
		g.firing=firing;
		g.ship=ship;
		g.ship_fired=ship_fired;
		g.asteroid=asteroid;
		g.shootfx=shootfx;
		g.soundeffects=soundeffects;
		g.cheatao=cheatao;
		g.pont=pont;
		g.currentname=currentname;
		g.blink=blink;
		g.tempo_ini=tempo_ini;
		g.bg=bg;
		g.bg_actual=null;
		g.life=life;
		g.hasteroids=hasteroids;
		g.first=first;
		g.hbg=hbg;
		g.dualplayer=dualplayer;
		//return g;
	}
	/**
	 * getter for {@link #pont}
	 * @return {@link #pont}
	 */
	public ArrayList<Highscores> getPont() {
		return pont;
	}
	/**
	 * setter for {@link #pont}
	 * @param pont -  {@link #pont}
	 */
	public void setPont(ArrayList<Highscores> pont) {
		this.pont = pont;
	}
	/**
	 * getter for {@link #currentname}
	 * @return {@link #currentname}
	 */
	public String getCurrentname() {
		return currentname;
	}
	/**
	 * setter for {@link #currentname}
	 * @param currentname - {@link #currentname}
	 */
	public void setCurrentname(String currentname) {
		this.currentname = currentname;
	}
	/**
	 * getter for {@link #soundeffects}
	 * @return {@link #soundeffects}
	 */
	public boolean isSoundeffects() {
		return soundeffects;
	}
	/**
	 * setter for {@link #soundeffects}
	 * @param soundeffects - {@link #soundeffects}
	 */
	public void setSoundeffects(boolean soundeffects) {
		this.soundeffects = soundeffects;
		if(game!=null){
			game.setSoundeffects(soundeffects);
		}
	}

	/**
	 * This is the default constructor
	 * {@link #initialize()}
	 */
	public GraphicalEngine() {
		super();
		initialize();
	}
	/**
	 * creates a empty {@link GraphicalEngine} class
	 * @param i - dummy int
	 */
	public GraphicalEngine(int i) {
		super();
		//initialize();
	}

	/**
	 * This method initializes this.
	 * 
	 * Load images and sounds.
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		try {
			this.ship = ImageIO.read(getClass().getResource("/gui/nave.png"));
			this.ship_fired=ImageIO.read(getClass().getResource("/gui/nave_f.png"));
			this.asteroid = ImageIO.read(getClass().getResource("/gui/asteroid.png"));
			this.bg = ImageIO.read(getClass().getResource("/gui/bg.png"));
			this.hbg = ImageIO.read(getClass().getResource("/gui/hbg.png"));
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

		life=scale(ship, 20);
		hasteroids=scale(asteroid,20);
		ship=scale(ship,36,30);
		ship_fired=scale(ship_fired,36,30);
		Collections.sort(pont);
		for(Highscores a:pont){
			a.reset();
			a.setX(this.getWidth());
			a.setY(this.getHeight());
		}



	}

	/**
	 * Responsible for add highscores to the {@link #pont}. 
	 * 
	 * This method only let {@link #pont} hold the 6 higher scores.
	 * 
	 * @see Highscores
	 */
	//mudar para a logica
	private void addScore(){



		Highscores h=new Highscores(game.getPlayername(), game.getPontuacao());
		/*
		for (Highscores a : pont) {
			if(a.equals(h)){
				System.out.println("duplicado evitado!");
				return;
			}
		}
		 */
		pont.add(h);
		System.out.println(game.getPlayername()+" - "+game.getPontuacao()+" Added");
		Collections.sort(pont);
		if(pont.size()>6){
			while(pont.size()>6){
				pont.remove(6);
			}
			Collections.sort(pont);
		}
	}
	/**
	 * Method used to start a new game replacing the old {@link #game}.
	 * 
	 * see {@link Engine}
	 */
	public void newGame() {
		mystate="play";
		if(first){
			bg_actual=null;
		}
		first=false;
		this.game = new Engine(this.getHeight(), this.getWidth());
		this.game.getSpaceShip().setAceleration((float)0.5);
		firing=false;

		for(Highscores a:pont){
			a.reset();
			a.setX(this.getWidth());
			a.setY(this.getHeight());
		}
		this.game.setPlayername(currentname);
	}
	/**
	 * Update method.
	 * 
	 * This method selects the current game state to represent and recall it's update method and repaint.
	 */
	public void update() {
		if (game != null) {
			if(game.isVivo()){
				mystate="play";
				this.game.update();
			}else{
				mystate="done";
				game.ship_explosion.stop();
				game.ship_explosion.setMicrosecondPosition(0);
				game.ship_explosion.start();


				if(dualplayer && mystate.equals("done") && state.equals("play")){
					this.repaint();
				}else{
					if(dualplayer && state.equals("done") ){
						if(espera==-1)
							espera=System.currentTimeMillis();
						else{
							if(System.currentTimeMillis()-espera>5000){
								scores=game.getPontuacao();
								addScore();
								dualplayer=false;
								game=null;
								espera=-1;
							}
						}
						this.repaint();
					}else{
						/*this.repaint();
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}*/
						scores=game.getPontuacao();
						addScore();
						dualplayer=false;
						game=null;
					}
				}
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
			if(!first){
				for(int i=0;i<this.pont.size();i++){
					Highscores a=pont.get(i);
					if(a.getAst()==null){
						//System.out.println("Width: "+this.getWidth()+" Height:"+this.getHeight());
						a.setX(this.getWidth());
						a.setY(this.getHeight());
					}
					a.update(i+1);
					//System.out.println("indice " + i +" de "+pont.size() );
					if(!(a.isDone())){
						break;
					}
				}
			}
			this.repaint();	
		}
	}
	/**
	 * getter for {@link #game}
	 * @return {@link #game}
	 */
	public Engine getGame() {
		return game;
	}
	/**
	 * setter for {@link #game}
	 * @param game - {@link #game}
	 */
	public void setGame(Engine game) {
		this.game = game;
	}
	/**
	 * setter for {@link #firing}
	 * @return {@link #firing} state
	 */
	public boolean isFiring() {
		return firing;
	}
	/**
	 * setter for {@link #firing}
	 * @param firing - {@link #firing}
	 */
	public void setFiring(boolean firing) {
		this.firing = firing;
	}
	/**
	 * Calls {@link Engine} method shoot(float,float) to if possible add a missile and play the sound effect
	 * 
	 * @see Engine
	 * @see Clip
	 */
	public void shoot(){

		if(game.shoot(game
				.getSpaceShip().getX() + ship.getWidth() / 2-5, game
				.getSpaceShip().getY() + ship.getHeight() / 2-5))
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

		if(dualplayer && !mystate.equals("play") && game!=null && !state.equals("done")){
			waiting_screen(g);
			return;
		}else{
			if(state.equals("done") && mystate.equals("done") && game!=null){
				dualwin(g);
			}
		}
		if (game != null) {
			if(game.isVivo()){
				game.setHeight(this.getHeight());
				game.setWidth(this.getWidth());
				Color c=new Color(255, 255, 255);
				g.setColor(c);

				//Explosion
				if(game.isCooldown()){
					drawExplosion(g,shipinhax,shipinhay);
				}else{
					shipinhax=(int) game.getSpaceShip().getX();
					shipinhay=(int) game.getSpaceShip().getY();
					radius=0;
				}


				String pontuacao=game.getPlayername() + "  "+Integer.toString(game.getPontuacao());
				g.drawString(pontuacao, 10, 10);
				if(dualplayer){
					g.drawString(socket_data, 10, 10+g.getFontMetrics().getHeight());
				}
				int tampont=g.getFontMetrics().stringWidth(pontuacao);
				for(int i=0; i<game.getLives();i++){
					g.drawImage(life, null, tampont+20+i*life.getWidth(), 0);
				}
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

				}
				g.setColor(original);
				g.setStroke(saves);
				if(!game.isHyperspace() && !game.isCooldown()){
					/*g.rotate(game.getSpaceShip().getRotation(), (int) game
							.getSpaceShip().getX() + ship.getWidth() / 8, (int) game
							.getSpaceShip().getY() + ship.getHeight() / 8);

					 */
					g.rotate(game.getSpaceShip().getRotation(), game
							.getSpaceShip().getX() + ship.getWidth() / 2, (int) game
							.getSpaceShip().getY() + ship.getHeight() / 2);
					if (ship != null && ship_fired!=null) {
						//g.draw(game.getSpaceShip().getRect2D());
						if(!(firing)){
							/*
							g.drawImage(Toolkit.getDefaultToolkit().createImage(ship.getSource()),(int) game.getSpaceShip().getX(),
									(int) game.getSpaceShip().getY(),ship.getWidth()/4,ship.getHeight()/4,null);

							 */
							g.drawImage(ship, null, (int) game.getSpaceShip().getX(), (int) game.getSpaceShip().getY());

						}else{

							g.drawImage(ship_fired, null, (int) game.getSpaceShip().getX(), (int) game.getSpaceShip().getY());
							//System.out.println("acc");
							//							g.drawImage(Toolkit.getDefaultToolkit().createImage(ship_fired.getSource()),(int) game.getSpaceShip().getX(),
							//									(int) game.getSpaceShip().getY(),ship_fired.getWidth()/4,ship_fired.getHeight()/4,null);
							/*
						g.drawImage(ship_fired, null, (int) game.getSpaceShip().getX(),
								(int) game.getSpaceShip().getY());*/
						}
					}

				}
			}
			else{

				/*
				Color c=new Color(255, 255, 255);
				g.setColor(c);
				g.drawString("FIM!!", this.getWidth()/2, this.getHeight()/2);*/
			}
		}else{
			if(bg_actual==null || bg_actual.getWidth()!=this.getWidth()){
				if(first){
					double ratio=(double)bg.getHeight()/(double)bg.getWidth();
					//System.out.println("width: "+bg.getWidth()+" Height "+ bg.getHeight() + "ratio: "+ratio );
					//g.drawImage(bg,null, 0, 0);
					bg_actual=new BufferedImage(this.getWidth(), (int)(ratio*this.getWidth()), BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2=(Graphics2D) bg_actual.getGraphics();
					//g2.drawImage(Toolkit.getDefaultToolkit().createImage(bg.getSource()),(int) 0,
					//			0,this.getWidth(),(int)(ratio*this.getWidth()),null);
					g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2.drawImage(bg, 0, 0, this.getWidth(), (int)(ratio*this.getWidth()), 0, 0, bg.getWidth(),bg.getHeight(), null);
					g2.dispose();
				}else
				{
					double ratio=(double)hbg.getHeight()/(double)hbg.getWidth();
					//System.out.println("width: "+hbg.getWidth()+" Height "+ hbg.getHeight() + "ratio: "+ratio );
					//g.drawImage(hbg,null, 0, 0);
					bg_actual=new BufferedImage(this.getWidth(), (int)(ratio*this.getWidth()), BufferedImage.TYPE_INT_ARGB);
					Graphics2D g2=(Graphics2D) bg_actual.getGraphics();
					//g2.drawImage(Toolkit.getDefaultToolkit().createImage(hbg.getSource()),(int) 0,
					//			0,this.getWidth(),(int)(ratio*this.getWidth()),null);
					g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					g2.drawImage(hbg, 0, 0, this.getWidth(), (int)(ratio*this.getWidth()), 0, 0, hbg.getWidth(),hbg.getHeight(), null);
					g2.dispose();
				}

			}
			g.drawImage(bg_actual,null,0,0);
			if(!first){
				paintHighscores(g);
			}
			if(blink){
				Font f=new Font("Comic Sans MS",Font.PLAIN,20);
				g.setFont(f);
				g.setColor(Color.WHITE);
				g.drawString("Press Enter to Continue", this.getWidth()/2-g.getFontMetrics(f).stringWidth("Press Enter to Continue")/2, this.getHeight()/2-g.getFontMetrics(f).getHeight()/2);
				g.drawString("Press ESC to change name", this.getWidth()/2-g.getFontMetrics(f).stringWidth("Press ESC to change name")/2, this.getHeight()/2+g.getFontMetrics(f).getHeight()/2);
				String lastline="Press F to enter or exit Fullscreen";
				g.drawString(lastline, this.getWidth()/2-g.getFontMetrics(f).stringWidth(lastline)/2, this.getHeight()/2+3*g.getFontMetrics(f).getHeight()/2);

			}

		}
	}
	private void dualwin(Graphics2D g) {

		g.setBackground(Color.black);
		g.setColor(Color.white);
		Font f=new Font("Comic Sans MS",Font.PLAIN,32);
		g.setFont(f);
		String[] data= socket_data.split(":");
		System.out.println("rede: "+Integer.parseInt(data[1])+ " Tu: "+game.getPontuacao());
		if(Integer.parseInt(data[1])>game.getPontuacao()){
			g.drawString(data[0]+" WON!!!", 0, g.getFontMetrics(f).getHeight()*2);
		}else{
			g.drawString(currentname+" WON!!!", 0, g.getFontMetrics(f).getHeight()*2);
		}


	}
	/**
	 * Resizes a buffered image keeping the ratio
	 * @param ori - source image
	 * @param width - desired width
	 * @return resized image.
	 */
	BufferedImage scale(BufferedImage ori, int width){
		int height=(int)((double)ori.getHeight()/(double)ori.getWidth()*width);
		BufferedImage ret=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=(Graphics2D) ret.getGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(ori, 0, 0,width, height, 0, 0, ori.getWidth(),ori.getHeight(), null);
		g2.dispose();
		return ret;
	}
	/**
	 * Resizes a buffered image
	 * @param ori - source image
	 * @param width - desired width
	 * @param height - desired height
	 * @return resized image.
	 */
	BufferedImage scale(BufferedImage ori, int width, int height){

		BufferedImage ret=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=(Graphics2D) ret.getGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(ori, 0, 0,width, height, 0, 0, ori.getWidth(),ori.getHeight(), null);
		g2.dispose();
		return ret;
	}
	/**
	 * This method is called by {@link #paintComponent(Graphics)} when the game is displaying the highscores
	 * @param g - Screen Graphics
	 */
	void paintHighscores(Graphics2D g){
		Color white=new Color(255, 255, 255);
		Color black=new Color(0, 0, 0);
		g.setBackground(black);
		Font f=new Font("Comic Sans MS",Font.PLAIN,20);
		int alturamin= (int)((double) bg_actual.getHeight()/(double) hbg.getHeight() *210);
		g.setFont(f);
		g.setColor(white);
		for(Highscores a: this.pont){
			a.setLetras(g.getFontMetrics(f));
			g.drawString(a.getName()+ "  "+a.getScore(), (float) a.getX(), (float) a.getY()+alturamin);
			//RenderingHints h=g.getRenderingHints();
			if(a.getAst()!=null){
				for(Asteroid ast: a.getAst()){
					g.drawImage(hasteroids,null, (int)(ast.getX()+a.getX()) , (int)ast.getY()+alturamin);
					//g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
					//g.drawImage(asteroid, (int)(ast.getX()+a.getX()), (int)(ast.getY()),10*ast.getType(), 10*ast.getType(),  0,0, asteroid.getWidth(),asteroid.getHeight(), null);
					//g.drawImage(Toolkit.getDefaultToolkit().createImage(asteroid.getSource()), (int)(ast.getX()+a.getX()),(int)ast.getY(),10*ast.getType(),10*ast.getType(),null);
				}
			}
			//g.setRenderingHints(h);
			if(!(a.isDone())){
				break;
			}
		}
	}

	/**
	 * Saves {@link #pont} to 'highscores.dat'.
	 * @throws IOException
	 */
	public void saveScores() throws IOException {
		File hfile = new File("highscores.dat");
		ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream(hfile));
		out.writeObject(pont);
		out.close();

	}
	/**
	 * Loads {@link #pont} from 'highscores.dat'
	 * @throws IOException
	 */
	public void loadScores() throws IOException {
		File hfile = new File("highscores.dat");
		if(hfile!=null){
			ObjectInputStream out = new ObjectInputStream(
					new FileInputStream(hfile));
			try {
				ArrayList<Highscores> readObject = (ArrayList<Highscores>) out.readObject();
				if(readObject!=null){
					pont=readObject;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			out.close();
		}

	}

	/**
	 * this method is called by {@link #paintComponent(Graphics)}.
	 * 
	 * Draws a circle of explosion particles.
	 * 
	 * @param g - screen graphics
	 * @param x - initial explosion position
	 * @param y - initial explosion position
	 */
	public void drawExplosion(Graphics2D g,int x, int y){
		Color saved=g.getColor();
		radius++;
		Random rand=new Random();
		for (int i = 0; i < 30; i++){
			g.setColor(new Color(rand.nextInt(256),rand.nextInt(256), rand.nextInt(256)));
			//g.drawRect((int) (x +radius* Math.cos(i * 2 * Math.PI / (30))),(int) (y +radius* Math.sin(i * 2 * Math.PI / (30))),3,3);
			g.fillRect((int) (x +radius* Math.cos(i * 2 * Math.PI / (30))),(int) (y +radius* Math.sin(i * 2 * Math.PI / (30))),4,4);
			//g.draw((Shape) p);

		}
		g.setColor(saved);
	}
	public void setSocket_data(String line) {
		// TODO Auto-generated method stub
		this.socket_data=line;

	}


	public void waiting_screen(Graphics2D g){
		g.setBackground(Color.black);
		g.setColor(Color.white);
		Font f=new Font("Comic Sans MS",Font.PLAIN,32);
		g.setFont(f);
		g.drawString(game.getPlayername()+ "  "+ game.getPontuacao(), 0,  g.getFontMetrics(f).getHeight());
		g.drawString(this.socket_data, 0, g.getFontMetrics(f).getHeight()*2);

	}

}
