package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import logic.Highscores;
import javax.swing.JTextPane;

import network.Socketus;
/**
 * Class containing the windows of the game.
 * 
 * This class holds the actionlistenners and the windows for communication between user and logic.
 * 
 * @author Margarida Pereira
 * @author Pedro Borges
 */
public class Asteroids {
	private boolean paused=false;
	private JFrame jFrame = null; // @jve:decl-index=0:visual-constraint="20,15"
	private JPanel jContentPane = null;
	private GraphicalEngine jPanel = null;
	private JPanel jPanel1 = null;
	private JButton OK = null;
	private JButton Sair = null;
	private int ciclos=0;
	private int counter=0;
	private JFrame jFrame1 = null;  //  @jve:decl-index=0:visual-constraint="546,21"
	private JPanel jContentPane1 = null;
	private JPanel jPanel2 = null;
	private JTabbedPane jTabbedPane = null;
	private FundoOptions jPanel3 = null;
	private FundoOptions jPanel4 = null;
	private JPanel jPanel5 = null;
	private JButton UP = null;
	private JButton Left = null;
	private JButton Right = null;
	private JCheckBox Som = null;
	private JLabel Somlabel = null;
	private JLabel Difficultyl = null;
	private JComboBox niveisdificuldade = null;
	private JButton Config = null;
	private JPanel botoesconfig = null;
	private JButton Ok_config = null;
	private JFrame nome = null;  //  @jve:decl-index=0:visual-constraint="1132,161"
	private FundoOptions Nome = null;
	private JButton Submit = null;
	private JTextField Name = null;
	private boolean fullscreen=false;
	private boolean wereinfull=false;
	private JFrame janfull = null;  //  @jve:decl-index=0:visual-constraint="1174,33"
	private GraphicalEngine panelfull = null;
	/**
	 * Keys being pressed at the moment.
	 */
	private HashSet<Integer> pressed = new HashSet<Integer>();
	private GraphicalEngine active=null;
	private JButton Fire = null;
	private JButton Hyperspace = null;
	private int up_k=38;
	private int right_k=39;
	private int left_k=37;
	private int fire_k=32;
	private int hyper_k=16;
	private JTextPane jTextPane = null;
	private JButton Two_Players = null;
	private JFrame jFrame2 = null;  //  @jve:decl-index=0:visual-constraint="1161,322"
	private JPanel Net = null;
	private JPanel server = null;
	private JButton bserver = null;
	private JPanel jPanel6 = null;
	private JTextField ip = null;
	private JButton Connect = null;
	private Socketus net=null;

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(466, 400));
			jFrame.setContentPane(getJContentPane());
			active=jPanel;

			//jFrame.setDefaultCloseOperation(JFrame.);
			jFrame.addWindowListener(new java.awt.event.WindowListener() {
				public void windowClosed(java.awt.event.WindowEvent e) {
					//System.out.println("windowClosed()"); // TODO Auto-generated Event stub windowClosed()
					try {
						getJPanel().saveScores();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);

				}
				public void windowOpened(java.awt.event.WindowEvent e) {
					try {
						getJPanel().loadScores();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
						System.out.println("File don't exist");
					}
				}
				public void windowClosing(java.awt.event.WindowEvent e) {
					try {
						getJPanel().saveScores();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
				public void windowIconified(java.awt.event.WindowEvent e) {
				}
				public void windowDeiconified(java.awt.event.WindowEvent e) {
				}
				public void windowActivated(java.awt.event.WindowEvent e) {
				}
				public void windowDeactivated(java.awt.event.WindowEvent e) {
				}
			});
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if(pressed.size()!=0 && !(getNome().isVisible())){
						paused=false;
					}
					if(!paused){
						keyanalizer();
						//active.requestFocus();
						counter++;
						ciclos++;
						active.update();
						active.repaint();

						if(ciclos>=15){
							active.setFiring(false);
							ciclos=0;
						}
						if(counter>=40 && active.getGame()!=null){
							active.getGame().place_new_asteroid();
							counter=0;
						}
					}
				}
			});
			timer.start();
			jFrame.setSize(new Dimension(850, 600));
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), BorderLayout.SOUTH);
		}
		return jContentPane;
	}

/**
 * key analizer.
 * 
 * This method uses {@link #pressed} to call the correct methods. Called everytime the timer runs.
 * 
 */
	private void keyanalizer(){
		if(getPanelfull().getGame()!=null && getPanelfull().getGame().isVivo()){
			if(paused && pressed.size()>0){
				paused=false;
			}
			for(int a:pressed){
				if(a==hyper_k){
					active.getGame().enterhyper();
				}
				if (a == left_k) {
					//getPanelfull().setFiring(false);
					active.getGame().getSpaceShip().setRotation((float)(getPanelfull().getGame().getSpaceShip().getRotation()-0.2));
				}
				if (a == right_k) {
					//getPanelfull().setFiring(false);
					active.getGame().getSpaceShip().setRotation((float)(getPanelfull().getGame().getSpaceShip().getRotation()+0.2));
				}
				if(a==up_k){
					ciclos=0;
					active.setFiring(true);
					active.getGame().acceleration();

				}
				if(a==fire_k){
					//getPanelfull().setFiring(false);
					active.shoot();
				}
				if(a==27){
					if(fullscreen){
						pressed.clear();
						changeScreen();
						wereinfull=true;
					}else{
						wereinfull=false;
					}
					getJFrame().setEnabled(false);
					getNome().setVisible(true);
					//pressed.remove(27);
					pressed.clear();
					paused=true;
				}
				if(a==70){/*
					Toolkit tk = Toolkit.getDefaultToolkit();  
					int xSize = ((int) tk.getScreenSize().getWidth());  
					int ySize = ((int) tk.getScreenSize().getHeight());
					getJFrame().setSize(xSize,ySize);  
					getJFrame().setVisible(false);
					getJFrame().setUndecorated(true);
					getJFrame().setVisible(true);
					getJFrame().repaint();
				 */
					pressed.remove(70);
					changeScreen();


				}

			}
		}else{
			for(int a:pressed){
				if(a==10){
					pressed.clear();
					restart();
				}
				if(a==27){
					if(fullscreen){
						pressed.clear();
						changeScreen();
					}
					getJFrame().setEnabled(false);
					getNome().setVisible(true);
					//pressed.remove(27);
					pressed.clear();
					paused=true;

				}
				if(a==70){
					//pressed.remove(70);
					pressed.clear();
					changeScreen();


				}
			}
		}
		if(net!=null && active.getGame()!=null && active.isDualplayer()){
			datasend();
		}
	}

	private void datasend() {
		net.setActual(active);
		net.senddata(active.getMystate());
		net.senddata(active.getGame().getPlayername()+":"+active.getGame().getPontuacao());
	
}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private GraphicalEngine getJPanel() {
		if (jPanel == null) {
			jPanel = new GraphicalEngine();
			
			jPanel.setSize(jFrame.getWidth(), jFrame.getHeight());
			jPanel.setLayout(new GridBagLayout());
			/*jPanel.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					System.out.println("keyPressed() " + e.getKeyCode()); // TODO
					// Auto-generated
					// Event
					// stub
					// keyPressed()
					// 39 direita
					// 37 esquerda
					// 38 cima
					// 40 baixo
					// 32 spacebar
					if (e.getKeyCode() == 37) {
						getJPanel().setFiring(false);
						getJPanel().getGame().getSpaceShip().setRotation((float)(getJPanel().getGame().getSpaceShip().getRotation()-0.1));
					}
					if (e.getKeyCode() == 39) {
						getJPanel().setFiring(false);
						getJPanel().getGame().getSpaceShip().setRotation((float)(getJPanel().getGame().getSpaceShip().getRotation()+0.1));
					}
					if(e.getKeyCode()==38){
						ciclos=0;
						getJPanel().setFiring(true);
						getJPanel().getGame().acceleration();

					}
					if(e.getKeyCode()==32){
						getJPanel().setFiring(false);
						getJPanel().shoot();
					}

				}
			});*/
			jPanel.addKeyListener(new java.awt.event.KeyListener() {

				public void keyPressed(java.awt.event.KeyEvent e) {
					//System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					pressed.remove((Integer)e.getKeyCode());
					pressed.add(e.getKeyCode());
					//System.out.println("keyPressed() " + e.getKeyCode()); // TODO
					// Auto-generated
					// Event
					// stub
					// keyPressed()
					// 39 direita
					// 37 esquerda
					// 38 cima
					// 40 baixo
					// 32 spacebar


					//keyanalizer();
				}
				public void keyTyped(java.awt.event.KeyEvent e) {
				}
				public void keyReleased(java.awt.event.KeyEvent e) {
					//System.out.println("Released "+e.getKeyCode());

					pressed.remove((Integer)e.getKeyCode());
				}
			});
			jPanel.setFocusable(true);
			active=jPanel;
		}
		return jPanel;
	}


	private void changeScreen(){
		if(!fullscreen){
			active=panelfull;
			getJanfull().setVisible(true);
			getJFrame().setVisible(false);
			getJanfull().setExtendedState(JFrame.MAXIMIZED_BOTH); 
			//getJFrame().setBounds(getJFrame().getGraphicsConfiguration().getBounds());

			getJanfull().setSize(getJFrame().getGraphicsConfiguration().getBounds().getSize());
			getJanfull().getGraphicsConfiguration().getDevice().setFullScreenWindow(getJanfull());
			fullscreen=true;

			getJPanel().setter(panelfull);
			//getJPanel().setPont(null);
			//panelfull.(getJPanel().getGame());
			getPanelfull().requestFocus();
		}else{
			fullscreen=false;
			active=jPanel;
			getJanfull().getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
			//getJFrame().setSize(600,600);
			getJanfull().setVisible(false);
			getJFrame().setVisible(true);
			getJPanel().requestFocus();
			getPanelfull().setter(jPanel);
			//getPanelfull().setPont(null);
			/*jPanel=null;
			jPanel=getPanelfull();
			jPanel.repaint();*/
		}

	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new FlowLayout());
			jPanel1.add(getOK(), null);
			jPanel1.add(getConfig(), null);
			jPanel1.add(getSair(), null);
			jPanel1.add(getTwo_Players(), null);
		}
		return jPanel1;
	}
/**
 * Starts a new game.
 * 
 * @see GraphicalEngine
 * @see Engine
 */
	private void restart(){

		if(!paused){

			active.setCurrentname(getName().getText());
			System.out.println(getName().getText());
			active.newGame();
			active.getGame().setmode(getNiveisdificuldade().getSelectedIndex());
			active.setSoundeffects(getSom().isSelected());
			active.requestFocus();
			active.setter(getPanelfull());
			if(getJanfull().isVisible()){
				getPanelfull().requestFocus();
			}
		}else{
			active.setGame(null);
			paused=false;
		}


	}
	/**
	 * This method initializes OK
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getOK() {
		if (OK == null) {
			OK = new JButton();
			OK.setText("New");
			OK.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					restart();
				}
			});
		}
		return OK;
	}

	/**
	 * This method initializes Sair
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getSair() {
		if (Sair == null) {
			Sair = new JButton();
			Sair.setText("Exit");
			Sair.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						getJPanel().saveScores();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}
			});
		}
		return Sair;
	}

	/**
	 * This method initializes jFrame1	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame1() {
		if (jFrame1 == null) {
			jFrame1 = new JFrame();
			jFrame1.setSize(new Dimension(575, 397));
			jFrame1.setContentPane(getJContentPane1());
			Timer timer = new Timer(33, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {

					if(jFrame1.isVisible()){
						if(getJPanel3().isVisible()){
							getJPanel3().updatef();
							getJPanel3().repaint();
							//System.out.println("3 update");
						}
						if(getJPanel4().isVisible()){
							getJPanel4().updatef();

							getJPanel4().repaint();
							//System.out.println("4 update");
						}
					}
				}

			});
			timer.start();
		}
		return jFrame1;
	}

	/**
	 * This method initializes jContentPane1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane1() {
		if (jContentPane1 == null) {
			jContentPane1 = new JPanel();
			jContentPane1.setLayout(new BorderLayout());
			jContentPane1.add(getJPanel2(), BorderLayout.CENTER);
			jContentPane1.add(getBotoesconfig(), BorderLayout.SOUTH);
		}
		return jContentPane1;
	}

	/**
	 * This method initializes jPanel2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel2 = new JPanel();
			jPanel2.setLayout(new GridBagLayout());
			jPanel2.add(getJTabbedPane(), gridBagConstraints);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Keys", null, getJPanel3(), null);
			jTabbedPane.addTab("Game definitions", null, getJPanel4(), null);
			jTabbedPane.addTab("Instructions", null, getJPanel5(), null);
		}
		return jTabbedPane;
	}

	/**
	 * This method initializes jPanel3	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private FundoOptions getJPanel3() {
		if (jPanel3 == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 2;
			gridBagConstraints11.gridy = 4;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.gridy = 4;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			//	gridBagConstraints2.gridx = 1;
			//gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			jPanel3 = new FundoOptions();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.setName("Keys");
			jPanel3.add(getUP(), gridBagConstraints1);
			jPanel3.add(getLeft(), gridBagConstraints3);
			jPanel3.add(getRight(), gridBagConstraints4);
			jPanel3.add(getFire(), gridBagConstraints10);
			jPanel3.add(getHyperspace(), gridBagConstraints11);
		}
		return jPanel3;
	}

	/**
	 * This method initializes jPanel4	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private FundoOptions getJPanel4() {
		if (jPanel4 == null) {
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints8.gridy = 1;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.gridx = 0;
			gridBagConstraints7.gridy = 1;
			Difficultyl = new JLabel();
			Difficultyl.setText("Difficulty level");
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 0;
			Somlabel = new JLabel();
			Somlabel.setText("Sound effects");
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 1;
			gridBagConstraints5.gridy = 0;
			jPanel4 = new FundoOptions();
			jPanel4.setLayout(new GridBagLayout());
			jPanel4.add(getSom(), gridBagConstraints5);
			jPanel4.add(Somlabel, gridBagConstraints6);
			jPanel4.add(Difficultyl, gridBagConstraints7);
			jPanel4.add(getNiveisdificuldade(), gridBagConstraints8);
		}
		return jPanel4;
	}

	/**
	 * This method initializes jPanel5	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel5() {
		if (jPanel5 == null) {

			String arrows="The "+KeyEvent.getKeyText(left_k)+" and "+KeyEvent.getKeyText(right_k)+" keys are used to turn. The "+KeyEvent.getKeyText(up_k)+" key pushes the ship forward. ";
			String fire="Use the "+KeyEvent.getKeyText(up_k)+" key to fire. ";
			String extra="Hit F to exit or enter fullscreen, ESC to change player's name, and "+KeyEvent.getKeyText(hyper_k)+" to enter hyperspace to a random location on the board.";
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.fill = GridBagConstraints.BOTH;
			gridBagConstraints2.gridy = 0;
			gridBagConstraints2.weightx = 1.0;
			gridBagConstraints2.weighty = 1.0;
			gridBagConstraints2.gridx = 0;
			String scoring= "\nScoring: Big asteroids are worth 1 points. Medium asteroids are worth 3 points. Small rocks are worth 5. You get an extra life every 75 points.";
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
			jPanel5.add(getJTextPane(), gridBagConstraints2);
			jPanel5.addPropertyChangeListener("visible",
					new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					//System.out.println("propertyChange(visible)"); // TODO Auto-generated property Event stub "visible" 
					getJTextPane().repaint();
				}
			});
		}
		return jPanel5;
	}

	/**
	 * This method initializes UP	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getUP() {
		if (UP == null) {
			UP = new JButton();
			UP.setText("UP " + KeyEvent.getKeyText(up_k));
			UP.setFocusable(true);
			UP.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					UP.requestFocus();
				}
			});
			UP.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//	System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					if(e.getKeyCode()!=up_k && e.getKeyCode()!=fire_k && e.getKeyCode()!=hyper_k && e.getKeyCode()!=left_k && e.getKeyCode()!=right_k){
						up_k=e.getKeyCode();
						
					}
					getOk_config().requestFocus();
					UP.setText("UP " + KeyEvent.getKeyText(up_k));
					getUP().repaint();
					getJTextPane().repaint();
				}
			});
		}
		return UP;
	}

	/**
	 * This method initializes Left	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLeft() {
		if (Left == null) {
			Left = new JButton();
			Left.setText("left" + KeyEvent.getKeyText(left_k));
			Left.setFocusable(true);
			Left.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					Left.requestFocus();
				}
			});
			Left.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//					System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					if(e.getKeyCode()!=up_k && e.getKeyCode()!=fire_k && e.getKeyCode()!=hyper_k && e.getKeyCode()!=left_k && e.getKeyCode()!=right_k){
						left_k=e.getKeyCode();
					}
					getOk_config().requestFocus();
					Left.setText("left" + KeyEvent.getKeyText(left_k));
					getLeft().repaint();
					getJTextPane().repaint();
				}
			});
		}
		return Left;
	}

	/**
	 * This method initializes Right	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getRight() {
		if (Right == null) {
			Right = new JButton();
			Right.setText("Right"+KeyEvent.getKeyText(right_k));
			Right.setFocusable(true);
			Right.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					Right.requestFocus();
				}
			});
			Right.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//					System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					if(e.getKeyCode()!=up_k && e.getKeyCode()!=fire_k && e.getKeyCode()!=hyper_k && e.getKeyCode()!=left_k && e.getKeyCode()!=right_k){
						right_k=e.getKeyCode();
					}
					getOk_config().requestFocus();
					Right.setText("Right"+KeyEvent.getKeyText(right_k));
					getRight().repaint();	
					getJTextPane().repaint();
				}
			});
		}
		return Right;
	}

	/**
	 * This method initializes Som	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getSom() {
		if (Som == null) {
			Som = new JCheckBox();
			Som.setSelected(true);
		}
		return Som;
	}

	/**
	 * This method initializes niveisdificuldade	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getNiveisdificuldade() {
		if (niveisdificuldade == null) {
			String labels[] = { "Baby","Easy", "Medium", "Hard", "Insane"};
			niveisdificuldade = new JComboBox(labels);

		}
		return niveisdificuldade;
	}

	/**
	 * This method initializes Config	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getConfig() {
		if (Config == null) {
			Config = new JButton();
			Config.setText("Config.");
			Config.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					paused=true;
					getJFrame1().setVisible(true);
				}
			});
		}
		return Config;
	}

	/**
	 * This method initializes botoesconfig	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getBotoesconfig() {
		if (botoesconfig == null) {
			botoesconfig = new JPanel();
			botoesconfig.setLayout(new FlowLayout());
			botoesconfig.add(getOk_config(), null);
		}
		return botoesconfig;
	}

	/**
	 * This method initializes Ok_config	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOk_config() {
		if (Ok_config == null) {
			Ok_config = new JButton();
			Ok_config.setText("OK");
			Ok_config.setFocusable(true);
			Ok_config.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					//paused=false;
					getJFrame1().setVisible(false);
					getJPanel().requestFocus();
				}
			});
		}
		return Ok_config;
	}

	/**
	 * This method initializes nome	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getNome() {
		if (nome == null) {
			nome = new JFrame();
			nome.setSize(new Dimension(300, 128));
			nome.setTitle("Name");
			nome.setContentPane(getNome2());
			nome.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					//System.out.println("windowClosing()"); // TODO Auto-generated Event stub windowClosing()
					getJPanel().setEnabled(true);
				}
			});
			Timer timer = new Timer(33, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {

					if(nome.isVisible()){
						getNome2().updatef();
						getNome2().repaint();

					}
				}

			});
			timer.start();

		}
		return nome;
	}

	/**
	 * This method initializes Nome	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private FundoOptions getNome2() {
		if (Nome == null) {
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints9.gridy = 0;
			gridBagConstraints9.weightx = 1.0;
			gridBagConstraints9.gridx = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 1;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 0;
			Nome = new FundoOptions();
			//Nome.setLayout(new BorderLayout());
			Nome.add(getSubmit(), gridBagConstraints3);
			Nome.add(getName(), gridBagConstraints9);
		}
		return Nome;
	}

	/**
	 * This method initializes Submit	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSubmit() {
		if (Submit == null) {
			Submit = new JButton();
			Submit.setText("OK");
			Submit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					getNome().setVisible(false);
					getJFrame().setEnabled(true);
					getJPanel().requestFocus();
					if (wereinfull) {
						changeScreen();
					}
					if(paused){
						restart();
					}
				}
			});


		}
		return Submit;
	}

	/**
	 * This method initializes Name	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getName() {
		if (Name == null) {
			Name = new JTextField();
			Name.setSize(100, 100);
			Name.setPreferredSize(new Dimension(200, 28));
		}
		return Name;
	}

	/**
	 * This method initializes janfull	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJanfull() {
		if (janfull == null) {
			janfull = new JFrame();
			janfull.setUndecorated(true);
			//janfull.setSize(new Dimension(233, 79));
			janfull.setContentPane(getPanelfull() );
			janfull.setVisible(false);
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {

					if(!paused){

						counter++;
						ciclos++;
						active.update();
						active .repaint();
						//System.out.println("Timer..");

						if(ciclos>=15){
							active.setFiring(false);
							ciclos=0;
						}
						if(counter>=40 && active.getGame()!=null){
							active.getGame().place_new_asteroid();
							counter=0;
						}
					}
				}
			});
			timer.start();

		}
		return janfull;
	}

	/**
	 * This method initializes panelfull	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private GraphicalEngine getPanelfull() {
		if (panelfull == null) {
			panelfull =new GraphicalEngine(1);
			getJPanel().setter(panelfull);
			//panelfull.setPont(null);
			//panelfull.setGame(getJPanel().getGame());
			//panelfull.setLayout(new BorderLayout());

			panelfull.addKeyListener(new java.awt.event.KeyListener() {
				//private ArrayList<Integer> pressed = new ArrayList<Integer>();
				public void keyPressed(java.awt.event.KeyEvent e) {
					//System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					pressed.remove((Integer)e.getKeyCode());
					pressed.add(e.getKeyCode());
					//System.out.println("keyPressed() " + e.getKeyCode()); // TODO
					// Auto-generated
					// Event
					// stub
					// keyPressed()
					// 39 direita
					// 37 esquerda
					// 38 cima
					// 40 baixo
					// 32 spacebar
					//keyanalizer();
				}
				public void keyTyped(java.awt.event.KeyEvent e) {
				}
				public void keyReleased(java.awt.event.KeyEvent e) {
					pressed.remove((Integer)e.getKeyCode());
				}
			});
		}
		return panelfull;
	}

	/**
	 * This method initializes Fire	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getFire() {
		if (Fire == null) {
			Fire = new JButton();
			Fire.setText("Fire" + KeyEvent.getKeyText(fire_k));
			Fire.setFocusable(true);
			Fire.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//	System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					Fire.requestFocus();
				}
			});
			Fire.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					//					System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					if(e.getKeyCode()!=up_k && e.getKeyCode()!=fire_k && e.getKeyCode()!=hyper_k && e.getKeyCode()!=left_k && e.getKeyCode()!=right_k){
						fire_k=e.getKeyCode();
					}
					getOk_config().requestFocus();
					Fire.setText("Fire" + KeyEvent.getKeyText(fire_k));
					getFire().repaint();
					getJTextPane().repaint();
				}
			});
		}
		return Fire;
	}

	/**
	 * This method initializes Hyperspace	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getHyperspace() {
		if (Hyperspace == null) {
			Hyperspace = new JButton();
			Hyperspace.setFocusable(true);
			Hyperspace.setText("Hyperspace"+ KeyEvent.getKeyText(hyper_k));
			Hyperspace.setFocusable(true);
			Hyperspace.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					//System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					Hyperspace.requestFocus();
				}
			});
			Hyperspace.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					//					System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
					if(e.getKeyCode()!=up_k && e.getKeyCode()!=fire_k && e.getKeyCode()!=hyper_k && e.getKeyCode()!=left_k && e.getKeyCode()!=right_k){
						hyper_k=e.getKeyCode();
					}
					getOk_config().requestFocus();
					Hyperspace.setText("Hyperspace"+ KeyEvent.getKeyText(hyper_k));
					getHyperspace().repaint();
					getJTextPane().repaint();
				}
			});
		}
		return Hyperspace;
	}

	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane() {
		String arrows="The "+KeyEvent.getKeyText(left_k)+" and "+KeyEvent.getKeyText(right_k)+" keys are used to turn. The "+KeyEvent.getKeyText(up_k)+" key pushes the ship forward. ";
		String fire="Use the "+KeyEvent.getKeyText(fire_k)+" key to fire. ";
		String extra="Hit F to exit or enter fullscreen, ESC to change player's name, and "+KeyEvent.getKeyText(hyper_k)+" to enter hyperspace to a random location on the board.";
		String scoring= "\n\nScoring: Big asteroids are worth 1 points. Medium asteroids are worth 3 points. Small asteroids are worth 5. You get an extra life every 75 points.";
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			Font f=jTextPane.getFont();
			Font f2=new Font(f.getFontName(),f.getStyle(),20);
			jTextPane.setFont(f2);
			jTextPane.setBackground(Color.BLUE);
			jTextPane.setForeground(Color.YELLOW);
			jTextPane.setText(arrows+fire+extra+scoring);
			jTextPane.setEditable(false);
		}
		jTextPane.setText(arrows+fire+extra+scoring);
		return jTextPane;
	}

	/**
	 * This method initializes Two_Players	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getTwo_Players() {
		if (Two_Players == null) {
			Two_Players = new JButton();
			Two_Players.setText("Two Players");
			Two_Players.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					getJFrame2().setVisible(true);
					active.setDualplayer(true);
				}
			});
		}
		return Two_Players;
	}

	/**
	 * This method initializes jFrame2	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame2() {
		if (jFrame2 == null) {
			jFrame2 = new JFrame();
			jFrame2.setSize(new Dimension(318, 227));
			jFrame2.setContentPane(getNet());
		}
		return jFrame2;
	}

	/**
	 * This method initializes Net	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getNet() {
		if (Net == null) {
			Net = new JPanel();
			Net.setLayout(new BorderLayout());
			Net.add(getServer(), BorderLayout.NORTH);
			Net.add(getJPanel6(), BorderLayout.SOUTH);
		}
		return Net;
	}

	/**
	 * This method initializes server	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getServer() {
		if (server == null) {
			server = new JPanel();
			server.setLayout(new GridBagLayout());
			server.add(getBserver(), new GridBagConstraints());
		}
		return server;
	}

	/**
	 * This method initializes bserver	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getBserver() {
		if (bserver == null) {
			bserver = new JButton();
			bserver.setText("server");
			bserver.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					net=new Socketus(1234, true);
					getJFrame2().setVisible(false);
				}
			});
		}
		return bserver;
	}

	/**
	 * This method initializes jPanel6	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel6() {
		if (jPanel6 == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints12.weightx = 1.0;
			jPanel6 = new JPanel();
			jPanel6.setLayout(new GridBagLayout());
			jPanel6.add(getIp(), gridBagConstraints12);
			jPanel6.add(getConnect(), new GridBagConstraints());
		}
		return jPanel6;
	}

	/**
	 * This method initializes ip	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getIp() {
		if (ip == null) {
			ip = new JTextField();
			ip.setPreferredSize(new Dimension(100, 28));
		}
		return ip;
	}

	/**
	 * This method initializes Connect	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getConnect() {
		if (Connect == null) {
			Connect = new JButton();
			Connect.setText("Client Connect");
			Connect.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					net=new Socketus(1234, false, getIp().getText());
					getJFrame2().setVisible(false);
				}
			});
		}
		return Connect;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Asteroids janelita = new Asteroids();
		//janelita.getNome().setVisible(true);

		janelita.getJFrame().setVisible(true);
		janelita.getJPanel().repaint();
		janelita.getJFrame().setEnabled(false);
		janelita.getNome().setVisible(true);


	}

}
