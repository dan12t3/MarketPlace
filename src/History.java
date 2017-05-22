
public class History {
	
	private String from;
	private String to;
	private double amount;
	private double balance;
	private String itemName;
	private String remark;
	
	public History(String from, String to, double amount,String itemName, double balance, String remark){
		
		this.from = from;
		this.to = to;
		this.amount = amount;
		this.balance = balance;
		this.itemName = itemName;
		this.remark = remark;
		
	}
	
	public History(String from, double amount, String itemName){
		this.from = from;
		this.amount = amount;
		this.itemName = itemName;
	}
	
	public String getFrom(){
		return from;
	}
	
	public String getTo(){
		return to;
	}
	
	public double getAmount(){
		return amount;
	}
	
	public String getItemName(){
		return itemName;
	}
	
	public double getBalance(){
		return balance;
	}
	
	public String getRemark(){
		return remark;
	}

}
