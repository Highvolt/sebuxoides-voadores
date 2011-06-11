package logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private screen_packet screen=null;
	private Socket server=null;
	private Client_data data=null;
	private ObjectInputStream in=null;
	private ObjectOutputStream out=null;
	Thread reader;
	
	
	public screen_packet getScreen() {
		return screen;
	}

	public void setScreen(screen_packet screen) {
		this.screen = screen;
	}

	Runnable read=new Runnable() {
		public void run() {
			while(true){
				try {
					screen=(screen_packet) in.readObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					System.out.println("thread de leitura ardeu!!");
					try {
						in.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						out.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					in=null;
					out=null;
					return;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("screenread");
			}
		}
	};

	public void senddata(){
		if(out==null){
			return;
		}
		try {
			out.reset();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out.writeObject(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Client_data getData() {
		return data;
	}

	public void setData(Client_data data) {
		this.data = data;
	}

	public Client(String name,String ip, int port){
		data=new Client_data(name);
		try {
			server=new Socket(ip,port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Server not found at "+ip+" : "+port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in=new ObjectInputStream(server.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out= new ObjectOutputStream(server.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		reader=new Thread(read);
		reader.start();
		senddata();

	}

	public static void main(String[] args) {
		Client p1=new Client("sapinho", "localhost",1234);
		Scanner s=new Scanner(System.in);
		s.next();
		Client_data d=p1.getData();
		d.setName("Pato");
		p1.setData(d);

		s.next();
		System.out.println("Nome enviado"+p1.getData().getName());
		p1.senddata();

		s.next();
		System.out.println("exit");

	}


}
