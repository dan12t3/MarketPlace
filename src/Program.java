import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

import java.awt.Font;
import javax.swing.JButton;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.awt.event.ActionEvent;

public class Program {

	private JFrame frmBmarketplace;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblMarketplaceLogin;
	private static Network nodeNet;
	
	private Map<String,String> userDatabase = new HashMap<String,String>(); 

	Connection con;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					
					
					Program window = new Program();
					window.frmBmarketplace.setVisible(true);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		Thread t = new Thread(Network.getInstance());
		t.run();
		
		
	}

	/**
	 * Create the application.
	 */
	public Program() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		
		loadUsers();
	
		Network.getInstance().initialize();
		
		
		frmBmarketplace = new JFrame();
		frmBmarketplace.setTitle("MarketPlace Login");
		frmBmarketplace.setBounds(100, 100, 292, 300);
		frmBmarketplace.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBmarketplace.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(144, 96, 86, 20);
		frmBmarketplace.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JPasswordField();
		textField_1.setBounds(144, 140, 86, 20);
		frmBmarketplace.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(38, 99, 96, 14);
		frmBmarketplace.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(38, 143, 96, 14);
		frmBmarketplace.getContentPane().add(lblPassword);
		
		lblMarketplaceLogin = new JLabel("MarketPlace Login");
		lblMarketplaceLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMarketplaceLogin.setBounds(58, 38, 163, 28);
		frmBmarketplace.getContentPane().add(lblMarketplaceLogin);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
					


					if(textField_1.getText().equals(userDatabase.get(textField.getText()))){
						System.out.println("User confirmed. Logging In...");
						
						userSession user = new userSession(textField.getText());
						
						
						
						marketSession market = null;
						marketSession.getInstance().setUser(user);
						
						synchronized(user){
							
							Network.getInstance().setUserSession(user);
							
							
						}
						
						
						
						
						
						Zone userZone = new Zone(user, market);
						userZone.setVisible(true);
						
						frmBmarketplace.setVisible(false);
						setClose(userZone);
						
						
						
					}else{
						System.out.println("Invalid User or Incorrect Password");
					}
					
			
					
			
				
				textField.setText(null);
				textField_1.setText(null);
				
				
			}
		});
		btnLogin.setBounds(100, 197, 89, 23);
		frmBmarketplace.getContentPane().add(btnLogin);
		
		frmBmarketplace.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				
				//System.out.println("Closing");
				
				Network.getInstance().saveBlockChain();
				
				
			}
		});

	}
	/*
	 * Loads username and passwords from the text file
	 */
	public void loadUsers(){
		
		
		
		try {
			Scanner userDB = new Scanner(new FileReader("UserDatabase.txt"));
			
			while(userDB.hasNextLine()){
				String[] line = userDB.nextLine().split(",");
				userDatabase.put(line[0], line[1]);
			}
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("User Database not found!");
			System.exit(0);
		}
		

			
	} 
	
	public void setClose(JFrame x){
		
		x.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
            	frmBmarketplace.setVisible(true);
            }
        });
		
		
		
	}
	
}


