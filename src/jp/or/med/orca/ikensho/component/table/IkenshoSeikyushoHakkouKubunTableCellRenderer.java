package jp.or.med.orca.ikensho.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;

import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;

/** <IKENSYO_HEAD> */
public class IkenshoSeikyushoHakkouKubunTableCellRenderer extends
        ACTableCellViewer {

    private VRArrayList data;
    private String comp1;
    private String comp2;
    private int currentRow = 0;
    private boolean changeFlg = false;

    // コンストラクタ
    public IkenshoSeikyushoHakkouKubunTableCellRenderer(String comp1,
            String comp2) {
        this.comp1 = comp1;
        this.comp2 = comp2;
    }

    public void setData(VRArrayList data) {
        this.data = data;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component cmp = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        if ((currentRow != row) || ((row == 0) && (column == 0))) {
            currentRow = row;
            changeFlg = isChangeColor(((VRSortableTableModelar) table
                    .getModel()).getTranslateIndex(row));
        }

        if (changeFlg) {
            if (isSelected) {
                cmp.setForeground(Color.white);
            } else {
                cmp.setForeground(Color.blue);
            }
            cmp.setFont(new Font(cmp.getFont().getName(), Font.ITALIC, cmp
                    .getFont().getSize()));
            // setFont(new Font(getFont().getName(), Font.ITALIC,
            // getFont().getSize()));
        } else {
            cmp.setFont(new Font(cmp.getFont().getName(), Font.PLAIN, cmp
                    .getFont().getSize()));
        }
        return cmp;
        // return this;
    }

    private boolean isChangeColor(int row) {
        if (data.size() - 1 < row) {
            return false;
        }
        return !((VRMap) data.getData(row)).getData(comp1).toString()
                .equals(
                        ((VRMap) data.getData(row)).getData(comp2)
                                .toString());
    }
}
