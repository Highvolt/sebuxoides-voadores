package gui;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import logic.Asteroid;

public class FundoOptions extends JPanel {
	BufferedImage asteroid;  //  @jve:decl-index=0:
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
	 * This method initializes this
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
	}
	
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
		for (int i=0;i<ast.size();i++) {
			arg0.drawImage(Toolkit.getDefaultToolkit().createImage(asteroid.getSource()), (int )ast.get(i).getX(),(int)ast.get(i).getY(),10*ast.get(i).getType(),10*ast.get(i).getType(),null);
		}
		
	}

}
