package jp.nichicom.ac.component.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;

import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;

/**
 * ��̏�Ԃɉ����đO�i�F��ύX����Z���r���[���ł��B
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
     * �R���X�g���N�^
     * 
     * @param conditionColNms �`�F�b�N�Ώۗ�
     * @param conditionVals �F��ω�������l(�e��ɂ�1����)
     */
    public ACFollowConditionForegroundTableCellRenderer(
            String[] conditionColNms, String[] conditionVals, Color[] colors) {
        this.conditionColNms = conditionColNms;
        this.conditionVals = conditionVals;
        this.colors = colors;
    }

    /**
     * ���g��ArrayList��ݒ肵�܂��B
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
            // ��������
            changeFlg = hasNeedToChangeColor(((VRSortableTableModelar) table
                    .getModel()).getTranslateIndex(row), i);
            if (changeFlg) {
                if (!isSelected) {
                    fontForegroundChangeFlg = true;
                }

                // �F�ύX����
                if (fontForegroundChangeFlg) {
                    cmp.setForeground(colors[i]);
                }
            }
        }

        return cmp;
        // return this;
    }

    /**
     * �F�ύX����
     * 
     * @param row ���s�ڂ�
     * @return true : �ύX�L, false : �ύX��
     */
    private boolean hasNeedToChangeColor(int row, int index) {
        // �f�[�^�̑��݃`�F�b�N
        if (data.size() <= 0) {
            return false;
        }

        // �Ώۍs�̑��݃`�F�b�N
        if (data.size() - 1 < row) {
            return false;
        }

        // �F�ύX����
        return ((VRMap) data.getData(row)).getData(conditionColNms[index])
                .toString().equals(conditionVals[index]);
    }
}
