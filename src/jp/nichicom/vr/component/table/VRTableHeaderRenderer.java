/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import jp.nichicom.vr.image.VRArrowIcon;

/**
 * ソート方向を描画するテーブルヘッダ用のレンダラです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 * @see DefaultTableCellRenderer
 * @see JTable
 * @see VRTableSortable
 * @see VRArrowIcon
 */
public class VRTableHeaderRenderer extends DefaultTableCellRenderer {
    private VRTableHeaderRendererAssistant assistant=new VRTableHeaderRendererAssistant();
    
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                row, column);
        assistant.applyRenderer(this, table, value, isSelected, hasFocus, row, column);
//        if (table != null) {
//            JTableHeader header = table.getTableHeader();
//            if (header != null) {
//                setForeground(header.getForeground());
//                setBackground(header.getBackground());
//                setFont(header.getFont());
//            }
//        }
//
//        setIcon(null);
//        
//        boolean sortableColumn = true;
//        TableColumn columnObj = table.getColumnModel().getColumn(column);
//        if(columnObj instanceof VRTableColumnar){
//            //ソートを許容するカラムであるか
//            sortableColumn = ((VRTableColumnar)columnObj).isSortable();
//        }
//        
//        if ((sortableColumn)&&(table instanceof VRTablar) ) {
//            //VRTablarインターフェースを実装しているか
//            VRTablar vTable = (VRTablar) table;
//            HashMap sequence = vTable.getSortSequence();
//            String sequenceValue = (String) sequence.get(vTable
//                    .getColumnName(column));
//            if (sequenceValue != null) {
//                if (sequenceValue.equals(VRTableSortable.ORDER_ASC_VALUE)) {
//                    setIcon(new VRArrowIcon(UIManager
//                            .getColor("TableHeader.foreground"), VRArrowIcon.UP));
//                } else if (sequenceValue
//                        .equals(VRTableSortable.ORDER_DESC_VALUE)) {
//                    setIcon(new VRArrowIcon(UIManager
//                            .getColor("TableHeader.foreground"),
//                            VRArrowIcon.DOWN));
//                }
//            }
//        }
//
//        setText((value == null) ? "" : value.toString());
//        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        return this;
    }

}