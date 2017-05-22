import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class marketSession {
	
	private static marketSession instance = null;
	private userSession user = null;
	
	private ArrayList<History> history = new ArrayList<History>();
	
	private DefaultTableModel table;
	private DefaultTableModel inventory;
	
	protected marketSession(){

		
	}
	
	public static marketSession getInstance(){
		
		
		if(instance ==null) instance = new marketSession();
		
		return instance;
		
	}
	
	public void setUser(userSession x){
		user = x;
		
	}
	
	public void addTable(DefaultTableModel t){
		table = t;

	}
	
	public void verifyItem(String tran){
		
		String[] temp = tran.split("-->"); 
		String[] tempItem = temp[1].replaceAll("\\%", "").split("\\|");
		
		
		if(temp[3].equals("Remove")){
			
			for(int i=0; i<history.size();i++){
				if(tempItem[0].equals(history.get(i).getItemName())){
					history.remove(i);
				}
			}
			
		}else{
			history.add(new History(temp[0],Double.parseDouble(tempItem[1]),tempItem[0]));
		}
		
	}
	
	public void addRow(String seller, String itemName, String price){
		
		//BuyButton buy = new BuyButton(seller, itemName, Double.parseDouble(price));
		//ButtonRenderer buy = new ButtonRenderer();
		String buy = user.getUserName() + "," + seller +"," + price + "," + itemName;
		
		int invCount = inventory.getRowCount();
		int tblCount = table.getRowCount();
		
		if(!user.getUserName().equals(seller)){
			table.addRow(new Object[]{itemName,price,buy+",0," + invCount});
		}else{
			inventory.addRow(new Object[]{itemName,price,buy+",1," + tblCount});
		}
		
		
		
	}
	
	public void addInventoryTable(DefaultTableModel itable){
		inventory = itable;
		
		//use history to update table
				//use addRow()
				
		for(int i=0; i<history.size();i++){
			addRow(history.get(i).getFrom(),history.get(i).getItemName(),history.get(i).getAmount()+"");
		}
	}
	
	public void deleteTableRow(int row){
		table.removeRow(row);
	}
	
	public void deleteInventoryRow(int row){
		inventory.removeRow(row);
	}
	
	
	
	
	
	
	
	
	
	
	

}
