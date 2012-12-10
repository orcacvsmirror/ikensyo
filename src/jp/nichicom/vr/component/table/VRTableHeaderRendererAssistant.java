package jp.nichicom.vr.component.table;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import jp.nichicom.vr.image.VRArrowIcon;

/**
 * テーブルヘッダ用のレンダラ補助クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/25
 */
public class VRTableHeaderRendererAssistant {
    /**
     * コンストラクタです。
     */
    public VRTableHeaderRendererAssistant() {
        super();
    }

    /**
     * レンダラの表示項目を設定します。
     * 
     * @param renderer レンダラ
     * @param table テーブル
     * @param value 値
     * @param isSelected 選択状態
     * @param hasFocus フォーカスの有無
     * @param row 行番号
     * @param column 列番号
     */
    public void applyRenderer(JLabel renderer, JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (renderer == null) {
            return;
        }
        if (table != null) {
            JTableHeader header = table.getTableHeader();
            if (header != null) {
                renderer.setForeground(header.getForeground());
                renderer.setBackground(header.getBackground());
                renderer.setFont(header.getFont());
            }
        }

        boolean sortableColumn = true;
        TableColumn columnObj = table.getColumnModel().getColumn(column);
        if (columnObj instanceof VRTableColumnar) {
            // ソートを許容するカラムであるか
            sortableColumn = ((VRTableColumnar) columnObj).isSortable();
        }
        Icon oldIcon = renderer.getIcon();
        Icon newIcon = null;
        if ((sortableColumn) && (table instanceof VRTablar)) {
            // VRTablarインターフェースを実装しているか
            VRTablar vTable = (VRTablar) table;
            HashMap sequence = vTable.getSortSequence();
            String sequenceValue = (String) sequence.get(vTable
                    .getColumnName(column));
            if (sequenceValue != null) {
                if (sequenceValue.equals(VRTableSortable.ORDER_ASC_VALUE)) {
                    newIcon = new VRArrowIcon(UIManager
                            .getColor("TableHeader.foreground"), VRArrowIcon.UP);
                } else if (sequenceValue
                        .equals(VRTableSortable.ORDER_DESC_VALUE)) {
                    newIcon = new VRArrowIcon(UIManager
                            .getColor("TableHeader.foreground"),
                            VRArrowIcon.DOWN);
                }
            }
        }
        if (oldIcon != newIcon) {
            // 設定すべきアイコンに変化があった場合はアイコンを変更
            renderer.setIcon(newIcon);
        }

        renderer.setOpaque(true);
        renderer.setText((value == null) ? "" : value.toString());
        renderer.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }
}
