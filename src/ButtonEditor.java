import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class ButtonEditor extends DefaultCellEditor {
  protected JButton button;

  
  private String from;
  private String to;
  private double amount;
  private String item;
  
  private JTable tab;
  private int row;
  private int otherRow;
  private boolean condition;
  
  private Object toReturn;
  
  private boolean toDelete = false;

  private boolean isPushed;

  public ButtonEditor(JCheckBox checkBox) {
    super(checkBox);
    button = new JButton();
    button.setOpaque(true);
    button.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
      }
    });
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
	  
	  toReturn = value;
	  String[] temp = value.toString().split(",");
	  
	  from = temp[0];
	  to = temp[1];
	  amount = Double.parseDouble(temp[2]);
	  item = temp[3];
	  
	  otherRow = Integer.parseInt(temp[5]);
	  
	  this.row = row;
	  this.tab = table;
	  
	  if(temp[4].equals("0")){
		  condition = true;
	  }else{
		  condition = false;
	  }
	  
	  
    if (isSelected) {
      button.setForeground(table.getSelectionForeground());
      button.setBackground(table.getSelectionBackground());
    } else {
      button.setForeground(table.getForeground());
      button.setBackground(table.getBackground());
    }
    //label = (value == null) ? "" : value.toString();
    if(condition)button.setText("Buy");
    else button.setText("Delete");
    
    isPushed = true;
    return button;
  }

  public Object getCellEditorValue() {
    if (isPushed) {
      // 
      // 
      //JOptionPane.showMessageDialog(button, label + ": Ouch!");
      // System.out.println(label + ": Ouch!");
    	
    	
    	//maybe switch to "to"
    	
    	if(condition){
    		toDelete = passToNetwork(new transaction(from, to, amount, item));
    		if(toDelete){
    			passToNetwork(new transaction(to, new Item(item, amount)));
    		}
    		
    	}else{
    		passToNetwork(new transaction(from, new Item(item, amount)));
    	}
    	
    	
    	
    	//another transaction to market and market (remove item)
    	
    	
    }
    isPushed = false;
    
    /*if(condition) return new String("Buy");
    else return new String("Delete");*/
    
    return new String(toReturn.toString());
  }

  public boolean stopCellEditing() {
    isPushed = false;
    return super.stopCellEditing();
  }

  protected void fireEditingStopped() {
    super.fireEditingStopped();
    
    DefaultTableModel model = (DefaultTableModel) tab.getModel();
    
    
    if(toDelete){
    	model.removeRow(row);
    }
	
	
	/*if(condition){
		marketSession.getInstance().deleteTableRow(otherRow);
	}else{
		
		marketSession.getInstance().deleteInventoryRow(otherRow);
	}*/
	
	
	
  }
  
  public boolean passToNetwork(transaction x){
		
		return Network.getInstance().addTransaction(x);
		
		
	}
}
