package gui;

import javax.swing.ComponentInputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import logic.Engine2;
import logic.Player;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class server {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="62,14"
	private JPanel jContentPane = null;
	private Engine2 dados=null;  //  @jve:decl-index=0:
	private JTextField jTextField = null;
	private JFrame Config = null;  //  @jve:decl-index=0:visual-constraint="578,21"
	private JPanel Configs = null;
	private JPanel jPanel = null;
	private JLabel jLabel = null;
	private JLabel jLabel1 = null;
	private JTextField width = null;
	private JTextField Height = null;
	private JPanel jPanel1 = null;
	private JLabel Dificulty = null;
	private JComboBox Modo = null;
	private JButton Ok = null;

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(484, 441));
			jFrame.setTitle("Server");
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setContentPane(getJContentPane());
			Timer timer = new Timer(30, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					dados.update();
					ArrayList<Player> p=dados.getPlayers();
					getJTextField().setText("");
					for(Player p1:p){
						getJTextField().setText(getJTextField().getText()+p1.getName()+" "+p1.getLives()+ " Score:"+p1.getScore() + " |||        ");
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
			jContentPane.add(getJTextField(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
		}
		return jTextField;
	}

	/**
	 * This method initializes Config	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getConfig() {
		if (Config == null) {
			Config = new JFrame();
			Config.setSize(new Dimension(292, 389));
			Config.setContentPane(getConfigs());
		}
		return Config;
	}

	/**
	 * This method initializes Configs	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getConfigs() {
		if (Configs == null) {
			Configs = new JPanel();
			Configs.setLayout(new BorderLayout());
			Configs.add(getJPanel(), BorderLayout.NORTH);
			Configs.add(getJPanel1(), BorderLayout.CENTER);
		}
		return Configs;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.gridy = 2;
			gridBagConstraints3.weightx = 1.0;
			gridBagConstraints3.gridx = 1;
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = 2;
			jLabel1 = new JLabel();
			jLabel1.setText("Height");
			jLabel = new JLabel();
			jLabel.setText("Width");
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
			jPanel.add(jLabel, gridBagConstraints2);
			jPanel.add(jLabel1, gridBagConstraints);
			jPanel.add(getWidth(), gridBagConstraints1);
			jPanel.add(getHeight(), gridBagConstraints3);
		}
		return jPanel;
	}

	/**
	 * This method initializes width	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getWidth() {
		if (width == null) {
			width = new JTextField();
			width.setPreferredSize(new Dimension(100, 28));
			width.setText("1280");
		}
		return width;
	}

	/**
	 * This method initializes Height	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getHeight() {
		if (Height == null) {
			Height = new JTextField();
			Height.setPreferredSize(new Dimension(100, 28));
			Height.setText("800");
		}
		return Height;
	}

	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 0;
			gridBagConstraints6.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints5.gridy = 0;
			gridBagConstraints5.weightx = 1.0;
			gridBagConstraints5.gridx = 1;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 0;
			gridBagConstraints4.gridy = 0;
			Dificulty = new JLabel();
			Dificulty.setText("Dificulty");
			jPanel1 = new JPanel();
			jPanel1.setLayout(new GridBagLayout());
			jPanel1.add(Dificulty, gridBagConstraints4);
			jPanel1.add(getModo(), gridBagConstraints5);
			jPanel1.add(getOk(), gridBagConstraints6);
		}
		return jPanel1;
	}

	/**
	 * This method initializes Modo	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getModo() {
		if (Modo == null) {
			String labels[] = { "Baby","Easy", "Medium", "Hard", "Insane"};
			Modo = new JComboBox(labels);
		}
		return Modo;
	}

	/**
	 * This method initializes Ok	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getOk() {
		if (Ok == null) {
			Ok = new JButton();
			Ok.setText("OK");
			Ok.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					dados=new Engine2(Integer.parseInt(getHeight().getText()), Integer.parseInt(getWidth().getText()), 1234);
					dados.setmode(getModo().getSelectedIndex());
					getConfig().setVisible(false);
					getJFrame().setVisible(true);
				
				}
			});
		}
		return Ok;
	}

	public static void main(String[] args) {
		server j=new server();
		//j.getJFrame().setVisible(true);
		j.getConfig().setVisible(true);
	}
}
