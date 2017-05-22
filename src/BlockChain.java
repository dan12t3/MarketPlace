import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class BlockChain {
	
	ArrayList<Block> chain;
	
	public BlockChain(){
		chain = new ArrayList<Block>();
		
	}
	
	public void addBlock(Block next, int index){
		
		
		if(chain.size()!=0){
			//get previous hash
			Block prevBlock = chain.get(chain.size()-1);
			prevBlock.setNext(next);
			
		}
		
		//solve for new block
		

		next.calculateHash();
		next.setIndex(chain.size());
		
		//add to new block
		
		
		
		chain.add(next);

		GridBagConstraints constraints = new GridBagConstraints();
		constraints.insets = new Insets(0, 0, 5, 5);
		constraints.gridx = chain.size()+1;
		constraints.gridy = index;
		
		JPanel display = Network.getInstance().getDisplay();
		
		display.add(next, constraints);
		

		
		display.revalidate();
		
		
		
		
		
		
		
	}
	
	public String verifyBlock(int index){
		
		return chain.get(index).calculateHash();
		
	}
	
	public String getHash(){
		if(chain.size()!=0){
			return chain.get(chain.size()-1).getHash();
		}else{
			return "";
		}
		
	}
	
	public void updateUser(userSession x, int update){
		
		if(update==0){
			for(int i=0; i<chain.size();i++){
				chain.get(i).userTransactions(x,update);
				
			}
			
		}else{
			chain.get(chain.size()-1).userTransactions(x,update);
			
		}
		
		
		
	
	}
	
	public void updateMarket(int update){
		
		if(update ==0){
			for(int i=0; i<chain.size();i++){
				chain.get(i).marketTransaction();
			}
		}else{
			chain.get(chain.size()-1).marketTransaction();
		}
		
		
		
	}
	
	public boolean verifyTran(transaction x){
		
		return chain.get(chain.size()-1).verifyTran(x);
	}
	
	public String toString(){
		
		String toReturn = "";
		
		for(int i=0; i<chain.size();i++){
			toReturn = toReturn + chain.get(i) + "\r\n";
			
		}
		
		return toReturn;
	}
	
	
	
	
	
	

}
