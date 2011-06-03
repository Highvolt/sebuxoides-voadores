package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import logic.Highscores;

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
	private JButton Down = null;
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
	private HashSet<Integer> pressed = new HashSet<Integer>();
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
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if(!paused){
						keyanalizer();
						//getJPanel().requestFocus();
						counter++;
						ciclos++;
						getJPanel().update();
						getJFrame().repaint();
						
						if(ciclos>=15){
							getPanelfull().setFiring(false);
							ciclos=0;
						}
						if(counter>=40 && getJPanel().getGame()!=null){
							getJPanel().getGame().place_new_asteroid();
							counter=0;
						}
					}
				}
			});
			timer.start();
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

	
	private void keyanalizer(){
		if(getPanelfull().getGame()!=null && getPanelfull().getGame().isVivo()){
			for(int a:pressed){
				if (a == 37) {
					//getPanelfull().setFiring(false);
					getPanelfull().getGame().getSpaceShip().setRotation((float)(getPanelfull().getGame().getSpaceShip().getRotation()-0.2));
				}
				if (a == 39) {
					//getPanelfull().setFiring(false);
					getPanelfull().getGame().getSpaceShip().setRotation((float)(getPanelfull().getGame().getSpaceShip().getRotation()+0.2));
				}
				if(a==38){
					ciclos=0;
					getPanelfull().setFiring(true);
					getPanelfull().getGame().acceleration();

				}
				if(a==32){
					//getPanelfull().setFiring(false);
					getPanelfull().shoot();
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
					

					//keyanalizer();
				}
				public void keyTyped(java.awt.event.KeyEvent e) {
				}
				public void keyReleased(java.awt.event.KeyEvent e) {
					System.out.println("Released "+e.getKeyCode());
					
					pressed.remove((Integer)e.getKeyCode());
				}
			});
			jPanel.setFocusable(true);
		}
		return jPanel;
	}


	private void changeScreen(){
		if(!fullscreen){
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
		}
		return jPanel1;
	}

	private void restart(){
		paused=false;
		

			getJPanel().setCurrentname(getName().getText());
			System.out.println(getName().getText());
			getJPanel().newGame();
			getJPanel().setSoundeffects(getSom().isSelected());
			getJPanel().requestFocus();
			getJPanel().setter(getPanelfull());
			if(getJanfull().isVisible()){
				getPanelfull().requestFocus();
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
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 2;
			gridBagConstraints4.gridy = 1;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 0;
			gridBagConstraints3.gridy = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 1;
			gridBagConstraints2.gridy = 2;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 1;
			gridBagConstraints1.gridy = 0;
			jPanel3 = new FundoOptions();
			jPanel3.setLayout(new GridBagLayout());
			jPanel3.setName("Keys");
			jPanel3.add(getUP(), gridBagConstraints1);
			jPanel3.add(getDown(), gridBagConstraints2);
			jPanel3.add(getLeft(), gridBagConstraints3);
			jPanel3.add(getRight(), gridBagConstraints4);
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
			jPanel5 = new JPanel();
			jPanel5.setLayout(new GridBagLayout());
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
			UP.setText("UP");
		}
		return UP;
	}

	/**
	 * This method initializes Down	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getDown() {
		if (Down == null) {
			Down = new JButton();
			Down.setText("Down");
		}
		return Down;
	}

	/**
	 * This method initializes Left	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getLeft() {
		if (Left == null) {
			Left = new JButton();
			Left.setText("left");
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
			Right.setText("Right");
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
			Ok_config.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					paused=false;
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
						getPanelfull().update();
						getPanelfull() .repaint();
						//System.out.println("Timer..");

						if(ciclos>=15){
							getPanelfull().setFiring(false);
							ciclos=0;
						}
						if(counter>=40 && getPanelfull().getGame()!=null){
							getPanelfull().getGame().place_new_asteroid();
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
