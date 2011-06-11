package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.Asteroid;
/**
 * Animated background jpanel.
 * 
 * This class was created for eye candy, giving a asteroidish look to all the windows.
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 *
 */
public class FundoOptions extends JPanel {
	/**
	 * medium asteroid sprite
	 */
	BufferedImage asteroid;  //  @jve:decl-index=0:
	/**
	 * small asteroid sprite
	 */
	BufferedImage sasteroid; 
	/**
	 * big asteroid sprite
	 */
	BufferedImage masteroid; 
	/**
	 * Array list of all asteroids on the screen
	 */
	ArrayList<Asteroid> ast=new ArrayList<Asteroid>();  //  @jve:decl-index=0:

	private static final long serialVersionUID = 1L;

	/**
	 * This is the default constructor
	 */
	public FundoOptions() {
		super();
		initialize();
	}

	/**
	 * This method initializes this.
	 * Loads and resize sprites
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setOpaque(false);
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		try{
			this.asteroid = ImageIO.read(getClass().getResource("/gui/asteroid.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Random ale=new Random();
		for(int i=0;i<40;i++){
			ast.add(new Asteroid(ale.nextFloat()*this.getWidth(), ale.nextFloat()*this.getHeight(), ale.nextFloat() * 5,  (float) (ale.nextFloat() * 2 * Math.PI), ale.nextInt(5)));
		}
		sasteroid=scale(asteroid, 10);
		masteroid=scale(asteroid,20);
		asteroid=scale(asteroid,30);
	}
	
	/**
	 * Method called by timer to update asteroid postions and repaint
	 */
	public void updatef(){
		for (int i=0;i<ast.size();i++) {
			Asteroid v=ast.get(i);

			v.setX((float) (v.getX() + v.getAceleration()
					* Math.sin(v.getRotation())));
			v.setY((float) (v.getY() - v.getAceleration()
					* Math.cos(v.getRotation())));

			if (v.getX() > this.getWidth()) {
				v.setX(0);
			} else if (v.getX() < 0) {
				v.setX(this.getWidth());
			}
			if (v.getY() > this.getHeight()) {
				v.setY(0);
			} else if (v.getY() < 0) {
				v.setY(getHeight());
			}

		}
	}
	
	@Override
	public void paintComponent(Graphics arg0) {
		super.paintComponent(arg0);
		Graphics2D g=(Graphics2D) arg0;
		for (int i=0;i<ast.size();i++) {
			BufferedImage a;
			if(ast.get(i).getType()>=Asteroid.Grande){
				a=asteroid;
			}else if(ast.get(i).getType()==Asteroid.Medio){
				a=masteroid;
			}else{
				a=sasteroid;
			}
			g.drawImage(a,null, (int )ast.get(i).getX(), (int )ast.get(i).getY());
			//arg0.drawImage(Toolkit.getDefaultToolkit().createImage(asteroid.getSource()), (int )ast.get(i).getX(),(int)ast.get(i).getY(),10*ast.get(i).getType(),10*ast.get(i).getType(),null);
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

}
