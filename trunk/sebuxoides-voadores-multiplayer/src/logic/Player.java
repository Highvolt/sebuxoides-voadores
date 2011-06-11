package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import network.Socketus;

public class Player {
	private SpaceShip spaceShip;
	private String name;
	private long score;
	private ArrayList<Bullet> bullets;
	private int lives;
	private boolean hyperspace;
	private boolean playing;
	private boolean respawning;
	private boolean remote;
	//private Socketus net;
	private long timeofdead;
	private long timeofhyperspace;
	private long last_shoot;
	private int ultima;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Thread reader;
	private Client_data data;
	private boolean run=true;

	private Runnable leitura=new Runnable() {
		public void run() {
			while(run){
				if(!socket.isConnected() || socket.isOutputShutdown()){
					System.out.println("Socket dead");
					playing=false;
					run=false;
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
				try {
					data=(Client_data) in.readObject();
					
					//System.out.println("data");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//System.out.println("Excepcao");
					playing=false;
					run=false;
					return;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					playing=false;
					run=false;
					return;
				}
				if(data!=null){
					//System.out.println("nome - "+data.getName());
					name=data.getName();
					}
			}
		}
	};

	public void stopthread(){
		run=false;
		reader.stop();
	}

	public void send_screen(screen_packet screen){
		try {
			out.reset();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out.writeObject(screen);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Ecrã enviado");
	}

	public Client_data getData() {
		return data;
	}

	public void setData(Client_data data) {
		this.data = data;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public int getUltima() {
		return ultima;
	}

	public void setUltima(int ultima) {
		this.ultima = ultima;
	}

	public SpaceShip getSpaceShip() {
		return spaceShip;
	}

	public void setSpaceShip(SpaceShip spaceShip) {
		this.spaceShip = spaceShip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean isHyperspace() {
		return hyperspace;
	}

	public void setHyperspace(boolean hyperspace) {
		this.hyperspace = hyperspace;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}

	public boolean isRespawning() {
		return respawning;
	}

	public void setRespawning(boolean respawning) {
		this.respawning = respawning;
	}

	public boolean isRemote() {
		return remote;
	}

	public void setRemote(boolean remote) {
		this.remote = remote;
	}

	//	public Socketus getNet() {
	//		return net;
	//	}
	//
	//	public void setNet(Socketus net) {
	//		this.net = net;
	//	}

	public long getTimeofdead() {
		return timeofdead;
	}

	public void setTimeofdead(long timeofdead) {
		this.timeofdead = timeofdead;
	}

	public long getTimeofhyperspace() {
		return timeofhyperspace;
	}

	public void setTimeofhyperspace(long timeofhyperspace) {
		this.timeofhyperspace = timeofhyperspace;
	}



	public long getLast_shoot() {
		return last_shoot;
	}

	public void setLast_shoot(long last_shoot) {
		this.last_shoot = last_shoot;
	}

	public void die(){
		timeofdead=System.currentTimeMillis();
		if(--lives==0){
			playing=false;
		}else{
			respawning=true;
		}
	}

	public void shoot(float x, float y, float speed) {

		this.bullets.add(new Bullet(x, y, speed, spaceShip
				.getRotation()));


	}

	private void init(){
		spaceShip=null;
		name="";
		score=0;
		bullets=new ArrayList<Bullet>();
		lives=0;
		hyperspace=false;
		playing=false;
		respawning=false;
		remote=false;
		socket=null;
		//net=null;
		timeofdead=0;
		timeofhyperspace=0;
		last_shoot=0;
		ultima=1;
		reader=new Thread(leitura);

	}

	Player(String name, int lives, float posx, float posy,float acc, float rotation){
		init();
		spaceShip=new SpaceShip(posx, posy, acc, rotation);
		this.name=name;
		this.lives=lives;
		this.playing=true;
	}

	Player(String name, int lives, float posx, float posy,float acc, float rotation, Socket net){
		this(name, lives, posx, posy, acc, rotation);
		this.remote=true;
		this.socket=net;
		try {
			in= new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.start();

	}

	Player( int lives, float posx, float posy,float acc, float rotation, Socket net){

		init();
		spaceShip=new SpaceShip(posx, posy, acc, rotation);
		this.lives=lives;
		this.playing=true;
		this.remote=true;
		this.socket=net;
	
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			in= new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader.start();
	}


}
