import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PurchaseCoins extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public PurchaseCoins(userSession u, marketSession mar) {
		
		PurchaseCoins ref = this;
		
		setTitle("Purchase Coins");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 279, 84);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 11, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnPurchaseCoins = new JButton("Purchase Coins");
		btnPurchaseCoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					double amount = Double.parseDouble(textField.getText().trim());
					
					if(amount <=0) {
						System.out.println("Please enter an appropriate amount");
					}
					else{
						transaction tran = new transaction(mar,u,amount);
						passToNetwork(tran);
					}
					
					ref.dispose();
					
				}catch(NumberFormatException e1){
					System.out.println("Please enter an amount");
				}
				
				
				
				
				
				
			}
		});
		btnPurchaseCoins.setBounds(106, 10, 149, 23);
		contentPane.add(btnPurchaseCoins);
	}
	
	public void passToNetwork(transaction x){
		
		//NodeNetwork.getInstance().addTransaction(x);
		Network.getInstance().addTransaction(x);
		
		
	}
}
