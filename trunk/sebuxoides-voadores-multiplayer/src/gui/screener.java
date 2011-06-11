package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.SpaceShip;
import logic.screen_packet;

public class screener extends JPanel {

	private static final long serialVersionUID = 1L;
	private screen_packet screen=null;  //  @jve:decl-index=0:
	private BufferedImage ship;

	public screen_packet getScreen() {
		return screen;
	}

	public void setScreen(screen_packet screen) {
		this.screen = screen;
	}

	/**
	 * This is the default constructor
	 */
	public screener() {
		super();
		initialize();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ship=scale(ship,36,30);
	}


	public void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		Graphics2D g = (Graphics2D) arg0;
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		// g.drawRect(0, 0, this.getWidth(), this.getHeight());
		if (screen != null) {
			
			this.setSize(new Dimension(getScreen().getWidth(),getScreen().getHeight()));
			g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.WHITE);
			for(int i=0;i<screen.getB().size();i++){

				g.drawRect((int)screen.getB().get(i).getPos_x(),(int)screen.getB().get(i).getPos_y(),5,5);
				//g.drawOval((int)game.getBullets().get(i).getPos_x(),(int)game.getBullets().get(i).getPos_y(),10,10);
			}
			Color original=g.getColor();

			BasicStroke dashed = new BasicStroke(3.0f);
			Stroke saves=g.getStroke();
			g.setStroke(dashed);

			for(int i=0;i<screen.getA().size();i++){
				//g.translate(game.getAsteroids().get(i).getX(), game.getAsteroids().get(i).getY());
				g.setColor(screen.getA().get(i).getColor());
				g.drawPolygon(screen.getA().get(i).getPoly());

			}
			g.setColor(original);
			g.setStroke(saves);
			g.setColor(Color.orange);
			for(int i=0; i<screen.getS().size();i++){
				SpaceShip s= screen.getS().get(i);
				//g.draw(s.getRect());
			
				g.rotate(s.getRotation(), (int)s.getX() + ship.getWidth() / 2, (int) s.getY() + ship.getHeight() / 2);
				g.drawImage(ship, null, (int) s.getX(), (int) s.getY());
				
				g.rotate(-s.getRotation(), (int)s.getX() + ship.getWidth() / 2, (int) s.getY() + ship.getHeight() / 2);	
				g.drawString(screen.getN().get(i)+ " "+ screen.getP().get(i), (int) s.getX(), (int) s.getY()+ship.getWidth());
			}

		}			
	}
	
	BufferedImage scale(BufferedImage ori, int width, int height){

		BufferedImage ret=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2=(Graphics2D) ret.getGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(ori, 0, 0,width, height, 0, 0, ori.getWidth(),ori.getHeight(), null);
		g2.dispose();
		return ret;
	}
}