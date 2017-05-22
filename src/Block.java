import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Block extends JButton {
	
	private Block prev = null;
	private Block next = null;
	
	private String transactions;
	
	private int nonce;
	private String oldHash;
	
	private String currentHash;
	
	private double tempBalance;
	 
	
	private int index;
	
	public Block(String trans, String old, int n){
		super();
		
		this.transactions = trans;
		this.nonce = n;
		this.oldHash = old;
		
		String hashInput = n + sha256(trans) + old;
		
		currentHash = sha256(hashInput);
		
		setColor();
		
		//execute transactions
		
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BlockTransaction temp = BlockTransaction.getInstance(); 
				temp.displayTransactions(transactions, oldHash, currentHash, index);
				
				
				
			}
		});
	}
	
	public Block(String line, String old){
		
		String[] tempLine = line.split("\\&");
		
		this.transactions = tempLine[1];
		this.nonce = Integer.parseInt(tempLine[0]);
		this.oldHash = old;
		
		String hashInput = nonce + sha256(transactions) + this.oldHash;
		
		currentHash = sha256(hashInput);
		
		//execute transactions
		setColor();
		
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BlockTransaction.getInstance().displayTransactions(transactions, oldHash, currentHash, index);
				
				
				
			}
		});
		
		
		
	}
	
	public void setIndex(int i){
		index = i;
		this.setText("Block # " + index);
		
	}
	
	public String getHash(){
		return currentHash;
	}
	
	
	public String calculateHash(){
		
		
		
		//use transactions
		//use prev.getHash();
		
		//output current hash
		
		return currentHash;
		
		
		
	}
	
	public void setNext(Block x){
		next = x;
		x.setPrev(this);
		
		
	}
	
	public void setPrev(Block x){
		
		prev = x;
		
	}
	
	public void changeTransaction(){
		
		//change transaction
		//calculate hash
		
		updateBlock();
		
	}
	
	public void updateBlock(){
		
		calculateHash();
		if(next != null){
			next.calculateHash();
		}
		
		
	}
	
	//http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
	public static String sha256(String base) {
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
	
	public void userTransactions(userSession x, int update){
		//can use this for history
		
		String temp = transactions.replaceAll("\\[", "").replaceAll("\\]","");
		String[] tempTran = temp.split(",");
		
		for(int i=0; i<tempTran.length;i++){
			if(tempTran[i].contains(x.getUserName())){
				
				x.addToHistory(tempTran[i],update);
			}
		}
		
		tempBalance = x.getBalance();
		
		System.out.println("temp: " + tempBalance);
		//temp = temp.replaceAll("]", "");
	
	}
	
	public boolean verifyTran(transaction x){
		
		if(!x.getFrom().equals("Market")){
			
			//if(x.getFrom().equals(username)){
				if(tempBalance - x.getAmount() >= 0){
					tempBalance = tempBalance - x.getAmount();
					return true;
				}else{
					System.out.println("Invalid Transaction: " + tempBalance);
					return false;
				}
			//}
			
		}
		
		return true;
		
	}
	
	public void marketTransaction(){
		
		String temp = transactions.replaceAll("\\[", "").replaceAll("\\]","");
		String[] tempTran = temp.split(",");
		
		for(int i=0; i<tempTran.length;i++){
			
			String[] temp2 = tempTran[i].split("-->");
			
			if(temp2[2].contains("Market")){
				marketSession.getInstance().verifyItem(tempTran[i]);
			}
			
		}
		
	}
	
	public String toString(){
		
		return nonce + "&" + transactions;
	}
	
	public void setColor(){
		
		if(currentHash.substring(0,4).equals("0000")){
			
			this.setBackground(new Color(119,221,119));
			
		}else{
			this.setBackground(new Color(255,105,97));
		}
		
		this.setContentAreaFilled(false);
		this.setOpaque(true);
		
	}
	
	
	

	
	

}
