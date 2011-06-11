package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import logic.Player;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Client {

	private JFrame Cliente = null;  //  @jve:decl-index=0:visual-constraint="65,21"
	private JPanel jContentPane = null;
	private logic.Client cliente=null;  //  @jve:decl-index=0:
	private HashSet<Integer> keys=new HashSet<Integer>();  //  @jve:decl-index=0:
	private screener jPanel = null;
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="586,79"
	private JPanel jContentPane1 = null;
	private JTextField jTextField = null;
	private JButton jButton = null;
	private JTextField jTextField1 = null;
	/**
	 * This method initializes Cliente	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getCliente() {
		if (Cliente == null) {
			
			Cliente = new JFrame();
			Cliente.setSize(new Dimension(484, 447));
			Cliente.setContentPane(getJContentPane());
			Cliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					if(jPanel.getScreen()!=null){
						Cliente.setSize(new Dimension(jPanel.getScreen().getWidth(),jPanel.getScreen().getHeight()));
					}
					cliente.getData().setKeyboard(keys);
					cliente.senddata();
					
					jPanel.setScreen(cliente.getScreen());
					jPanel.repaint();
				}
			});
			timer.start();
		}
		return Cliente;
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
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private screener getJPanel() {
		if (jPanel == null) {
			jPanel = new screener();
			jPanel.setFocusable(true);
			jPanel.setLayout(new GridBagLayout());
			jPanel.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					cliente=new logic.Client(getJTextField1().getText(), getJTextField().getText(), 1234);
					System.out.println("mouseClicked()"); // TODO Auto-generated Event stub mouseClicked()
					jPanel.requestFocus();
				}
			});
			jPanel.addKeyListener(new java.awt.event.KeyListener() {
				public void keyPressed(java.awt.event.KeyEvent e) {
					System.out.println("keyPressed()"); // TODO Auto-generated Event stub keyPressed()
				keys.add(e.getKeyCode());
				}
				public void keyTyped(java.awt.event.KeyEvent e) {
				}
				public void keyReleased(java.awt.event.KeyEvent e) {
					keys.remove(e.getKeyCode());
				}
			});
		}
		return jPanel;
	}

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(190, 94));
			jFrame.setContentPane(getJContentPane1());
		}
		return jFrame;
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
			jContentPane1.add(getJTextField(), BorderLayout.WEST);
			jContentPane1.add(getJButton(), BorderLayout.EAST);
			jContentPane1.add(getJTextField1(), BorderLayout.NORTH);
		}
		return jContentPane1;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setPreferredSize(new Dimension(100, 19));
		}
		return jTextField;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setText("connect");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					jFrame.setVisible(false);
					cliente=new logic.Client(getJTextField1().getText(), getJTextField().getText(), 1234);
					getCliente().setVisible(true);
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new JTextField();
		}
		return jTextField1;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client ja=new Client();
		ja.getJFrame().setVisible(true);
		
	}

}
