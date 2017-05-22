
public class Item {
	
	private String name;
	private double price;
	
	private userSession owner;
	
	public Item(String name, double price, userSession owner){
		this.name = name;
		this.price = price;

		
		this.owner = owner;
	}
	
	public Item(String name, double price){
		this.name = name;
		this.price = price;

		
	
	}
	
	public String toString(){
		return name + "|" + price;
	}

}
