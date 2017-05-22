import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class userSession {
	
	private String username;
	private double balance = 0;
	private double tempBalance = 0;
	private Zone userZone;
	
	private ArrayList<History> transactions = new ArrayList<History>();
	DefaultTableModel table;
	
	public userSession(String username){
		
		this.username = username;
		
	}
	
	public String getUserName(){
		return username;
	}
	
	public double getBalance(){
		//Network.something
		return balance;
	}
	

	
	public void addToHistory(String transaction, int update){
		
		String[] temp = transaction.split("-->");
		
		if(temp[1].charAt(0) == "$".charAt(0)){
			
			//System.out.println(temp[1].replaceAll("$",""));
			
			
			
			double amount = Double.parseDouble(temp[1].replaceAll("\\$",""));
			
			//dollar exchange
			if(temp[0].equals(username)){
				//subtract from balance
				balance = balance - amount;
				
			}else if(temp[2].equals(username)){
				//add to balance
				balance = balance + amount;
				
			}
			
			
			tempBalance = balance;
			//System.out.println("temp: " + tempBalance);
			
			if(update==0){
				transactions.add(new History(temp[0], temp[2], amount,"Currency", balance, temp[3]));
				//send item to market to display
				
			}else{
				
				
				//send item to singleton market to display
				//update user inventory
				
				
				
				table.addRow(new String[]{temp[0],temp[2],amount+"","Currency",""+balance, temp[3]});
				userZone.updateBalance();
			}
			
			
			
		}else if(temp[1].charAt(0) == "%".charAt(0)){
			
			
			String[] tempItem = temp[1].replaceAll("\\%", "").split("\\|"); 
			
			if(update==0){
				
				//send item to market to display
				//udpate user inventory
				transactions.add(new History(temp[0], temp[2], Double.parseDouble(tempItem[1]), tempItem[0], balance, temp[3]));
			}else{
				
				
				//send item to market to display
				if(!temp[3].equals("Remove")){
					marketSession.getInstance().addRow(temp[0], tempItem[0],tempItem[1]);
				}
				
				table.addRow(new String[]{temp[0], temp[2], tempItem[1], tempItem[0], ""+balance, temp[3]});
				
			}
			
			
		}
		
		
		
	}
	

	
	
	public void addTable(DefaultTableModel table){
		this.table = table;
		
		for(int i=0; i<transactions.size();i++){
			History temp = transactions.get(i);
			
			
			table.addRow(new String[]{temp.getFrom(),temp.getTo(),temp.getAmount()+"", temp.getItemName(),""+temp.getBalance(),temp.getRemark()});
			
			
			
		}
		
		userZone.updateBalance();
			
		
	}
	

	
	public void setZone(Zone x){
		userZone = x;
	}

}
