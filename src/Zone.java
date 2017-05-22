import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;

public class Zone extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private userSession user;
	private marketSession marketPlace;
	private JLabel lblBalance;

	/**
	 * Create the frame.
	 */
	public Zone(userSession u, marketSession mar) {
		this.setTitle(u.getUserName() + "'s MarketPlace");
		
		this.user = u;
		marketPlace = mar;
		
		user.setZone(this);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 549);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUsername = new JLabel("Username: " + user.getUserName());
		lblUsername.setBounds(10, 11, 218, 14);
		contentPane.add(lblUsername);
		
		lblBalance = new JLabel("Balance: $" + user.getBalance());
		lblBalance.setBounds(10, 36, 218, 14);
		contentPane.add(lblBalance);
		
		JButton btnPurchaseCoins = new JButton("Purchase Coins");
		btnPurchaseCoins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				PurchaseCoins pc = new PurchaseCoins(u, mar);
				pc.setVisible(true);
				
				
			}
		});
		btnPurchaseCoins.setBounds(296, 11, 128, 23);
		contentPane.add(btnPurchaseCoins);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 61, 414, 439);
		
		JPanel market = new JPanel();
		JPanel inventory = new JPanel();
		JPanel history = new JPanel();
		
		inventory.setLayout(null);
		market.setLayout(null);
		history.setLayout(null);
		
		JScrollPane marScroll = new JScrollPane(market);
		JScrollPane invScroll = new JScrollPane(inventory);
		JScrollPane hisScroll = new JScrollPane(history);
		
		
		
		
		tabbedPane.addTab("MarketPlace", marScroll);
		tabbedPane.addTab("Inventory", invScroll);
		tabbedPane.addTab("History", hisScroll);
		//
		/////////
		JTable transHistory = new JTable();
		DefaultTableModel dtmHistory = new DefaultTableModel(0,0);
		
		String headerHistory[] = new String[] {"From","To","Amount","Item","Balance","Remarks"};
		dtmHistory.setColumnIdentifiers(headerHistory);
		
		
		transHistory.setModel(dtmHistory);
		
		
		
		JScrollPane tranScroll = new JScrollPane(transHistory);
		tranScroll.setBounds(0, 0, 407, 409);
		
		history.add(tranScroll);
		history.repaint();
		
		user.addTable(dtmHistory);
		
		///////////////
		
		JTable marketTable = new JTable();
		DefaultTableModel dtmMarket = new DefaultTableModel(0,0);
		
		String headerMarket[] = new String[]{"Item","Price",""};
		dtmMarket.setColumnIdentifiers(headerMarket);
		
		marketTable.setModel(dtmMarket);
		
		//dtmMarket.addRow(new Object[]{"Couch","1.5","button"});
		//dtmMarket.addRow(new Object[]{"Couch","1.5","button2"});
		
		marketTable.getColumn("").setCellRenderer(new ButtonRenderer());
	    marketTable.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
	 
		
		
		
		JScrollPane marketItemScroll = new JScrollPane(marketTable);
		marketItemScroll.setBounds(0,0,407,409);
		
		market.add(marketItemScroll);
		market.repaint();
		
		marketSession.getInstance().addTable(dtmMarket);
		
		
		
		///////////
		JTable inventoryTable = new JTable();
		DefaultTableModel dtmInventory = new DefaultTableModel(0,0);
		
		String headerInventory[] = new String[]{"Item","Price",""};
		
		dtmInventory.setColumnIdentifiers(headerInventory);
		
		inventoryTable.setModel(dtmInventory);
		
		inventoryTable.getColumn("").setCellRenderer(new ButtonRenderer());
	    inventoryTable.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
	    
	    JScrollPane inventoryItemScroll = new JScrollPane(inventoryTable);
	    inventoryItemScroll.setBounds(0,52,407,357);
	    
	    inventory.add(inventoryItemScroll);
	    inventory.repaint();
	    
	    marketSession.getInstance().addInventoryTable(dtmInventory);
		
		
		
		JButton btnAddItem = new JButton("Add Item");
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				addItem itemWindow = new addItem(user, marketPlace);
				itemWindow.setVisible(true);
				
				
				
				
				
				
				
			}
		});
		btnAddItem.setBounds(10, 11, 89, 23);
		inventory.add(btnAddItem);
		
		
		
		contentPane.add(tabbedPane);
		
		
	}
	
	public void updateBalance(){
		
		lblBalance.setText("Balance: $" + user.getBalance());
		
	}
}
