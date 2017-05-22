import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;


//http://www.java2s.com/Code/Java/Swing-Components/ButtonTableExample.htm
class ButtonRenderer extends JButton implements TableCellRenderer {
	
	private boolean condition;

  public ButtonRenderer() {
    setOpaque(true);
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
	  
	  String[] temp = value.toString().split(",");
	  
	  if(temp[4].equals("0")) condition = true;
	  else condition = false;
	  
	  
    if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(UIManager.getColor("Button.background"));
    }
    //setText((value == null) ? "" : value.toString());
    if(condition) setText("Buy");
    else setText("Delete");
    return this;
  }


}