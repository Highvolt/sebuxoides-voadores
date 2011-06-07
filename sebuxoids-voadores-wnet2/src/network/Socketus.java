package network;

import gui.GraphicalEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import logic.Asteroid;
import logic.Bullet;
/**
 * Socket class
 * 
 * @author Pedro Borges
 * @author Margarida Pereira
 *
 */
public class Socketus extends Thread{
	GraphicalEngine actual;

	public GraphicalEngine getActual() {
		return actual;
	}

	public void setActual(GraphicalEngine actual) {
		this.actual = actual;
		//actual.newGame();
	}

	public void run() {
		if(isserver){
			System.out.println("Thread Server");
			String line="";
			while(true){
				try{
					line = in.readLine();
					System.out.println("data received: "+line);
					//Send data back to client
					//out.println(line);
					if(actual.getGame()!=null){
						if(line.equals("not") || line.equals("play") || line.equals("done")){
							actual.setState(line);
						}
						else{
							actual.setSocket_data(line);
						}}
				} catch (IOException e) {
					System.out.println("Read failed");
					System.exit(-1);
				}
			}
		}else{
			System.out.println("Thread Client");
			while(true){
				try{

					String line = in.readLine();
					System.out.println("data received: "+line);
					//Send data back to client
					//out.println(line);
					if(actual!=null){
						if(actual.getGame()!=null){
							if(line.equals("not") || line.equals("play") || line.equals("done")){
								actual.setState(line);
							}
							else{
								actual.setSocket_data(line);
							}
						}}

					//					
					//					if(s[0].equals("-2") && s[1].equals("-2")){
					//						if(s.length>=2){
					//							in.readLine();
					//							//System.out.println("Text received: " + line);
					//							s=line.split(":");
					//							System.out.println("x: "+s[0]);
					//							System.out.println("y: "+s[1]);
					//							actual.getGame().getSpaceShip().setX(Float.parseFloat(s[0]));
					//							actual.getGame().getSpaceShip().setY(Float.parseFloat(s[1]));
					//							actual.repaint();
					//							out.println("read");
					//						}
					//					}
					//					if(s[0].equals("-3") && s[1].equals("-3")){
					//						/*	line = in.readLine();
					//					System.out.println("Text received: " + line);
					//					s=line.split(":");
					//					if(s.length>=2){
					//						if(s[0]=="-1" && s[1]=="-1"){*/
					//						ArrayList<Asteroid> a=new ArrayList<Asteroid>();
					//						out.println("read");
					//						while(true){
					//							line = in.readLine();
					//							System.out.println("Text received: " + line);
					//							s=line.split(":");
					//							if(s[0].equals("-1") && s[1].equals("-1")){
					//								out.println("read");
					//								break;
					//							}
					//
					//							a.add(new Asteroid(Float.parseFloat(s[0]), Float.parseFloat(s[1]), 0, 0, Integer.parseInt(s[2])));
					//							out.println("read");
					//						}
					//						actual.getGame().setAsteroids(a);
					//					}
					//					if(s[0].equals("-4") && s[1].equals("-4")){
					//						out.println("read");
					//						ArrayList<Bullet> b=new ArrayList<Bullet>();
					//						while(true){
					//							line = in.readLine();
					//							System.out.println("Text received: " + line);
					//							s=line.split(":");
					//							if(s[0].equals("-1") && s[1].equals("-1")){
					//								out.println("read");
					//								break;
					//							}
					//
					//							b.add(new Bullet(Float.parseFloat(s[0]), Float.parseFloat(s[1]), 0, 0));
					//							out.println("read");
					//						}
					//						actual.getGame().setBullets(b);
					//					}
					//				
					//				/*System.out.println("x: "+s[0]);
					//				System.out.println("y: "+s[1]);
					//				actual.getGame().getSpaceShip().setX(Float.parseFloat(s[0]));
					//				actual.getGame().getSpaceShip().setY(Float.parseFloat(s[1]));*/
					//				actual.repaint();
					//		
				}
				catch (IOException e){
					System.out.println("Read failed");
					System.exit(1);
				}
			}

		}
	}

	public boolean isIsserver() {
		return isserver;
	}

	public void setIsserver(boolean isserver) {
		this.isserver = isserver;
	}

	PrintWriter out = null;
	BufferedReader in = null;
	Socket client=null;
	private static ServerSocket server;
	boolean isserver;
	Thread t;


	public Socketus(int port, boolean server){
		isserver=server;
		if(server){
			createServer( port);
			waitforclient();
		}else{
			createClient(port,"localhost");
		}
	}

	public Socketus(int port, boolean server,String ip){
		isserver=server;
		if(server){
			createServer( port);
			waitforclient();
		}else{
			createClient(port,ip);
		}
	}

	private void waitforclient() {
		System.out.println("Server Waiting"); 
		try{
			client = server.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: " );
			System.exit(-1);
		}

		System.out.println("Client in");

		in = null ;
		out = null ;

		try{
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), 
					true);
		} catch (IOException e) {
			System.out.println("Read failed");
			System.exit(-1);
		}

		//Thread a=new Thread("Listener");
		t=new Thread(this);
		t.start();

	}

	public void senddata(String data){
		out.println(data);
		String line="";
		/*while(!line.equals("read")){
	try {
		line = in.readLine();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}*/


	}

	private void createClient(int port,String ip) {
		try{
			client = new Socket(ip, port);
			out = new PrintWriter(client.getOutputStream(), 
					true);
			in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: kq6py");
			System.exit(1);
		} catch  (IOException e) {
			System.out.println("No I/O");
			System.exit(1);
		}
		t=new Thread(this);
		t.start();

	}



	private void createServer(int port) {

		try{
			server = new ServerSocket(port); 
		} catch (IOException e) {
			System.out.println("Could not listen on port "+port);
			System.exit(-1);
		}

	}

}
