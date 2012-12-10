package jp.nichicom.ac.component.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;

import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;

/**
 * 列の状態に応じて前景色を変更するセルビューワです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2005/12/01
 * @see ACTableCellViewer
 */

public class ACFollowConditionForegroundTableCellRenderer extends
        ACTableCellViewer {
    private VRArrayList data = new VRArrayList();
    private String[] conditionColNms;
    private String[] conditionVals;
    private Color[] colors;

    /**
     * コンストラクタ
     * 
     * @param conditionColNms チェック対象列
     * @param conditionVals 色を変化させる値(各列につき1つずつ)
     */
    public ACFollowConditionForegroundTableCellRenderer(
            String[] conditionColNms, String[] conditionVals, Color[] colors) {
        this.conditionColNms = conditionColNms;
        this.conditionVals = conditionVals;
        this.colors = colors;
    }

    /**
     * 中身のArrayListを設定します。
     * 
     * @param data VRArrayList
     */
    public void setData(VRArrayList data) {
        this.data = data;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component cmp = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);

        Color defaultForeground;
        if (isSelected) {
            cmp.setForeground(UIManager.getColor("Table.selectionForeground"));
        } else {
            cmp.setForeground(UIManager.getColor("Table.foreground"));
        }

        boolean changeFlg = false;
        boolean fontForegroundChangeFlg = false;

        for (int i = 0; i < conditionColNms.length; i++) {
            // 条件判定
            changeFlg = hasNeedToChangeColor(((VRSortableTableModelar) table
                    .getModel()).getTranslateIndex(row), i);
            if (changeFlg) {
                if (!isSelected) {
                    fontForegroundChangeFlg = true;
                }

                // 色変更処理
                if (fontForegroundChangeFlg) {
                    cmp.setForeground(colors[i]);
                }
            }
        }

        return cmp;
        // return this;
    }

    /**
     * 色変更判定
     * 
     * @param row 何行目か
     * @return true : 変更有, false : 変更無
     */
    private boolean hasNeedToChangeColor(int row, int index) {
        // データの存在チェック
        if (data.size() <= 0) {
            return false;
        }

        // 対象行の存在チェック
        if (data.size() - 1 < row) {
            return false;
        }

        // 色変更判定
        return ((VRMap) data.getData(row)).getData(conditionColNms[index])
                .toString().equals(conditionVals[index]);
    }
}
