
public class transaction {
	
	private String from = null;
	private String to = null;
	
	private double amount = 0;
	private String remark;
	
	private Item what = null;
	
	public transaction(userSession from, marketSession to, Item item){
		
		this.from = from.getUserName();
		this.to = "Market";
		what = item;
		remark = "Added Item";
		
	}
	
	public transaction(String from, String to, double amount, String itemName){
		this.from = from;
		this.to= to;
		
		this.amount = amount;
		remark = itemName;
		
		
	}
	
	public transaction(marketSession from, userSession to, double amount){
		this.from = "Market";
		this.to= to.getUserName();
		
		this.amount = amount;
		
		remark = "Bought Coins";
		
	}
	
	public transaction(String from, Item x){
		this.from = from;
		this.to = "Market";
		this.what = x;
		this.remark ="Remove";
		 
	}
	
	public String toString(){
		
		String object;
		
		if(what==null) object = "$"+amount;
		else object = "%"+what.toString();
		
		String party = from + "-->"+object+"-->" + to + "-->" + remark;
		
		
		
		return party;
		
	}
	
	
	public String getFrom(){
		return from;
		
	}
	
	public double getAmount(){
		return amount;
	}
	
	public Item getItem(){
		return what;
	}

}
