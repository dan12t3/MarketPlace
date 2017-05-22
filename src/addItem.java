import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class addItem extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public addItem(userSession user, marketSession market) {
		setTitle("Add Item");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 139);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addItem ref = this;
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(301, 11, 123, 14);
		contentPane.add(lblPrice);
		
		textField = new JTextField();
		textField.setBounds(10, 36, 281, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(301, 36, 123, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(!textField.getText().trim().isEmpty() && !textField_1.getText().trim().isEmpty()){
					Item inventoryItem = new Item(textField.getText().trim(), Double.parseDouble(textField_1.getText().trim()), user);
					transaction x = new transaction(user, market, inventoryItem);
					
					passToNetwork(x);
					
					ref.dispose();
				}else{
					System.out.println("Please fill in the required field");
				}
				//transaction between user and marketplace
				
				
				
				
				
			}
		});
		btnAddItem.setBounds(301, 67, 123, 23);
		contentPane.add(btnAddItem);
	}
	
	public void passToNetwork(transaction x){
		
		Network.getInstance().addTransaction(x);
		
		
	}

}
