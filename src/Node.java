import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Node extends JButton{
	
	//holds blockchain
	BlockChain chain = null;
	GridBagConstraints constraints;
	int serverIndex;
	private double tempBalance;

	
	
	
	
	public Node(int index){
		super("Server # "+index);
		
		serverIndex = index;
		
		constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 5, 5);
		constraints.gridx = 1;
		constraints.gridy = index;
		
		
	}
	
	public GridBagConstraints getConstraints(){
		return constraints;
	}

	

	public boolean verify(transaction x, userSession user){
		
		//return user.verifyTran(tran);
		if(chain == null){
			return true;
		}
		else{
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
		//verify double spending

	}
	
	public void updateBalance(userSession x){
		tempBalance = x.getBalance();
	}
	
	public void addBlock(Block next){
		
		if(chain == null) chain = new BlockChain();
		
		chain.addBlock(next,serverIndex);
		
		
		
		
		
	}
	
	public void saveBlockChain(int index){
		
		if(chain!=null){
			try{
				PrintWriter save = new PrintWriter("BlockChain/Server-"+index+".txt","UTF-8");
				save.print(chain);
				save.close();
			}catch(IOException e){
				System.out.println("Couldn't save the BlockChain for Server-" + index );
			}
			
			
		}
		
		
		
	}
	
	public String verifyBlock(int index){
		
		return chain.verifyBlock(index);
		
	}
	
	public String getHash(){
		
		if(chain==null) return "";
		else return chain.getHash();
		
		
	}
	
	public void updateUser(userSession x, int update){
		
		if(chain != null){
			chain.updateUser(x, update);
		}
		
		
	}
	
	public void updateMarket(int update){
		
		if(chain != null) chain.updateMarket(update);
	}
	
	
	
	
	
	
	

}
