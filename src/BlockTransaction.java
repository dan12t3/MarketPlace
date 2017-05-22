import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class BlockTransaction extends JFrame {

	private JPanel contentPane;
	private static BlockTransaction instance = null;
	DefaultTableModel dtmHistory;
	
	private JLabel lblPreviousHash;
	private JLabel lblCurrentHash;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	protected BlockTransaction() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 377);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		//setVisible(true);
		
		JTable transHistory = new JTable();
		dtmHistory = new DefaultTableModel(0,0);
		
		String headerHistory[] = new String[] {"From","To","Amount","Item","Remarks"};
		dtmHistory.setColumnIdentifiers(headerHistory);
		
		
		transHistory.setModel(dtmHistory);
		

		
		JScrollPane tranScroll = new JScrollPane(transHistory);
		tranScroll.setBounds(0, 0, 434, 278);
		
		contentPane.add(tranScroll);
		
		lblPreviousHash = new JLabel("Previous Hash: ");
		lblPreviousHash.setBounds(10, 289, 414, 14);
		contentPane.add(lblPreviousHash);
		
		lblCurrentHash = new JLabel("Current Hash: ");
		lblCurrentHash.setBounds(10, 314, 414, 14);
		contentPane.add(lblCurrentHash);
		contentPane.repaint();
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				
				instance.setVisible(false);
				
			}
		});
		
		
			
		
	}
	
	public void displayTransactions(String transactions, String old, String current, int index){
		
		dtmHistory.setRowCount(0);
		
		this.setTitle("Block # " + index);
		lblPreviousHash.setText("Previous Hash: " + old);
		lblCurrentHash.setText("Current Hash: " + current);
		
		String[] tran = transactions.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		
		for(int i=0; i<tran.length;i++){
			
			String[] temp = tran[i].split("-->");
			
			if(temp[1].charAt(0) == "$".charAt(0)){
				
				//System.out.println(temp[1].replaceAll("$",""));
				
				String amount = temp[1].replaceAll("\\$", "");
				
				
				
				dtmHistory.addRow(new String[]{temp[0],temp[2],amount,"Currency",temp[3]});
				
			
				
				
				
			}else if(temp[1].charAt(0) == "%".charAt(0)){
				
				
				String[] tempItem = temp[1].replaceAll("\\%", "").split("\\|"); 
				
				dtmHistory.addRow(new String[]{temp[0],temp[2],tempItem[1],tempItem[0],temp[3]});
				
				
			}
			
			
			
		}
		
	}
	
	public static BlockTransaction getInstance(){
		
		if(instance == null) instance = new BlockTransaction();
		
		instance.setVisible(true);
		return instance;
		
	}

}
