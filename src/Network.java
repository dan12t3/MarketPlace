import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Network extends JFrame implements Runnable {

	private JPanel contentPane;
	
	
	private int networkSize = 4;
	private userSession user = null;
	
	
	private Node[] servers;
	private ArrayList<transaction> allTransactions = new ArrayList<transaction>();
	private ArrayList<transaction> verifiedTransactions = new ArrayList<transaction>();
	
	private static Network instance = null;
	
	private long prev = System.currentTimeMillis();
	
	JPanel panel;
	


	/**
	 * Create the frame.
	 */
	protected Network() {
		this.setTitle("Block Chain Network");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 600, 414);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setBounds(0,0,581,376);
		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		
		panel.setBounds(0, 0, 434, 262);
		contentPane.add(scroll);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		//JButton btnNewButton = new JButton("New button");
		
		
		//panel.add(btnNewButton, gbc_btnNewButton);
		
		this.setVisible(true);
		
		servers = new Node[networkSize];
		
		for(int i=0; i<servers.length;i++){
			servers[i] = new Node(i);
			panel.add(servers[i],servers[i].getConstraints());
			
		}
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				
				//System.out.println("Closing");
				
				saveBlockChain();
				
				
			}
		});

		
		
	}
	
	public static synchronized Network getInstance(){
		
		if(instance == null){
			instance = new Network();
		}
		
		return instance;
	}
	
	public void initialize(){

		for(int i=0;i<servers.length;i++){
			
			try {
				Scanner serverFile = new Scanner(new FileReader("BlockChain/Server-"+i+".txt"));
				
				while(serverFile.hasNextLine()){
					
					String oldHash = servers[i].getHash();
					servers[i].addBlock(new Block(serverFile.nextLine().replaceAll("\\s+", ""),oldHash));
					
				}
				
				
				
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				break;
				
			}
			
			//verify blockchain
			
			
			
			//update market

		}
		
		servers[0].updateMarket(0);
		
		
		
		
		
		
		
		
	}
	
	public void refresh(){
		this.repaint();
	}
	
	public JPanel getDisplay(){
		return panel;
		
	}

	
	public boolean addTransaction(transaction tran){
		
		boolean properSpend = true;
		
		for(int i=0;i<servers.length;i++){
			
			
			if(!servers[i].verify(tran, user)){//verifying if user can spend
				properSpend = false;
			}else{
				System.out.println(i);
			}

		}
		
		if(properSpend) {
			verifiedTransactions.add(tran);
		}
		
		return properSpend;
		
		//allTransactions.add(tran);
		
				
	}
	
	public void updateChain(Block next){
		for(int i=0;i<servers.length;i++){
			servers[i].addBlock(next);
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
		
		while(true){
			

			
			String oldHash = servers[0].getHash();

			long currentTime = System.currentTimeMillis();
			
			if((currentTime - prev > 10000) && !verifiedTransactions.isEmpty()){
				System.out.println("Adding transaction to Block");
				prev = currentTime;
				
				
				
				
				//make block
				String trans = verifiedTransactions.toString();
				trans = trans.replaceAll("\\s+", "");
				int nonce = mine(sha256(trans),oldHash);
				
				
				for(int i=0; i<servers.length;i++){
					servers[i].addBlock(new Block(trans, oldHash, nonce));
				}
				
				if(user != null){
					//user.updateBalance();
					servers[0].updateUser(user,1);
					servers[0].updateMarket(1);
					for(int j=0; j<servers.length;j++){
						servers[j].updateBalance(user);
					}
					
					
				}
				
				
				verifiedTransactions.clear();
				
	
			}else{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			
		}
		
	}
	

	
	public int mine(String trans, String old){
		
		
		
		int i =0;
		while(true){
			String hashInput = i + trans + old;
			String output = sha256(hashInput);
			
			if(output.substring(0,4).equals("0000")){
				
				System.out.println(i);
				System.out.println(output);
				break;
			}
			i++;
			
		}
		
		try(
				FileWriter write = new FileWriter("minerLog.txt", true);
				BufferedWriter buf = new BufferedWriter(write);
				PrintWriter print = new PrintWriter(buf);
				){
			
			print.println("Nonce: " + i);
			print.println("Content: " + trans);
			print.println("previous Hash: " + old);
			print.println();
			
		}catch(IOException e1){
			System.out.println("Miner log not found");
		}
		
		
		
		return i;
	}
	
	public void setUserSession(userSession x){
		user = x;
		servers[0].updateUser(user,0);
		
		for(int j=0; j<servers.length;j++){
			servers[j].updateBalance(user);
		}
		
		
	}
	
	public String sha256(String base) {
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }

	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}
	
	public void saveBlockChain(){
		for(int i=0; i<servers.length;i++){
			servers[i].saveBlockChain(i);
		}
		
	}
}
