package jp.or.med.orca.ikensho.component.table;

import java.awt.Component;
import java.text.Format;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import jp.nichicom.vr.component.table.VRTable;
import jp.nichicom.vr.component.table.VRTableColumn;

/** TODO <HEAD_IKENSYO> */
public class IkenshoCheckBoxTableCellRenderer extends JCheckBox implements TableCellRenderer{
  public Component getTableCellRendererComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 boolean hasFocus, int row,
                                                 int column) {

    TableColumn defaultColumn = table.getColumnModel().getColumn(column);
    if (defaultColumn instanceof VRTableColumn) {
        VRTableColumn clm = (VRTableColumn) defaultColumn;

        Format fmt = clm.getFormat();
        if (fmt != null) {
            value = fmt.format(value);
        }

        setHorizontalAlignment(clm.getHorizontalAlignment());
    }

    if(value instanceof Boolean){
      setSelected(((Boolean)value).booleanValue());
    }else{
      setSelected(Boolean.valueOf(String.valueOf(value)).booleanValue());
    }

    if (table instanceof VRTable) {
        VRTable vTable = (VRTable) table;
        if (isSelected) {
            if (table.hasFocus()) {
                setBackground(UIManager
                        .getColor("Table.selectionBackground"));
                setForeground(UIManager
                        .getColor("Table.selectionForeground"));
            } else {
                setBackground(UIManager.getColor(
                        "Table.selectionBackground").brighter());
                setForeground(UIManager
                        .getColor("Table.selectionForeground"));
            }
        } else {
            setForeground(UIManager.getColor("Table.foreground"));
            if (vTable.isStripe()) {
                if (row % 2 != 0) {
                    if (vTable.getStripeColor() != null) {
                        setBackground(vTable.getStripeColor());
                    } else {
                        setBackground(UIManager
                                .getColor("Table.background"));
                    }
                } else {
                    setBackground(UIManager.getColor("Table.background"));
                }
            } else {
                setBackground(UIManager.getColor("Table.background"));
            }
        }
        setOpaque(false);

    }
    return this;
  }

}
