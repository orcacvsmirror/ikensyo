package jp.or.med.orca.ikensho.component.table;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Format;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import jp.nichicom.vr.component.table.VRTable;
import jp.nichicom.vr.component.table.VRTableColumn;

/** TODO <HEAD_IKENSYO> */
public class IkenshoCheckBoxTableCellEditor extends JCheckBox implements TableCellEditor, MouseListener {
    private Object data;
    private JTable table;
    private int row;
    private int column;

    public IkenshoCheckBoxTableCellEditor() {
        super();
        addMouseListener(this);
        setOpaque(false);
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        data = value;
        this.table = table;
        this.row = row;
        this.column = column;

        TableColumn defaultColumn = table.getColumnModel().getColumn(column);
        if (defaultColumn instanceof VRTableColumn) {
            VRTableColumn clm = (VRTableColumn) defaultColumn;

            Format fmt = clm.getFormat();
            if (fmt != null) {
                value = fmt.format(value);
            }

            setHorizontalAlignment(clm.getHorizontalAlignment());
        }

        if (value instanceof Boolean) {
            setSelected( ( (Boolean) value).booleanValue());
        }
        else {
            setSelected(Boolean.valueOf(String.valueOf(value)).booleanValue());
        }

        if (table instanceof VRTable) {
            VRTable vTable = (VRTable) table;
            if (isSelected) {
                if (table.hasFocus()) {
                    setBackground(UIManager.getColor("Table.selectionBackground"));
                    setForeground(UIManager.getColor("Table.selectionForeground"));
                }
                else {
                    setBackground(UIManager.getColor("Table.selectionBackground").brighter());
                    setForeground(UIManager.getColor("Table.selectionForeground"));
                }
            }
            else {
                setForeground(UIManager.getColor("Table.foreground"));
                if (vTable.isStripe()) {
                    if (row % 2 != 0) {
                        if (vTable.getStripeColor() != null) {
                            setBackground(vTable.getStripeColor());
                        }
                        else {
                            setBackground(UIManager.getColor("Table.background"));
                        }
                    }
                    else {
                        setBackground(UIManager.getColor("Table.background"));
                    }
                }
                else {
                    setBackground(UIManager.getColor("Table.background"));
                }
            }
        }
        return this;
    }

    public void cancelCellEditing() {
        fireEditingCanceled();
    }

    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    public Object getCellEditorValue() {
        return data;
    }

    public boolean isCellEditable(EventObject anEvent) {
        setBackground(UIManager.getColor("Table.selectionBackground"));
        return true;
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    public void addCellEditorListener(CellEditorListener listener) {
        listenerList.add(CellEditorListener.class, listener);
    }

    public void removeCellEditorListener(CellEditorListener listener) {
        listenerList.remove(CellEditorListener.class, listener);
    }

    /**
     * 編集停止イベントを発生させます。
     */
    protected void fireEditingStopped() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingStopped(changeEvent);
            }
        }
    }

    /**
     * 編集キャンセルイベントを発生させます。
     */
    protected void fireEditingCanceled() {
        CellEditorListener listener;
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CellEditorListener.class) {
                listener = (CellEditorListener) listeners[i + 1];
                listener.editingCanceled(changeEvent);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
//    stopCellEditing();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    protected void fireItemStateChanged(ItemEvent e) {
      if((row>=0)||(column>=0)){
        data = new Boolean(isSelected());
        table.setValueAt(data, row, column);
      }
        super.fireItemStateChanged(e);
    }

    public void setData(Object data) {
        this.data = data;
    }
}
