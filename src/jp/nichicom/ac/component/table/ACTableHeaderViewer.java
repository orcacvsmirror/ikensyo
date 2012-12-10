package jp.nichicom.ac.component.table;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import jp.nichicom.vr.component.table.VRTableHeaderRendererAssistant;

/**
 * �e�[�u���̃w�b�_�p�����_���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/27
 */
public class ACTableHeaderViewer extends ACTableCellViewer {
    private VRTableHeaderRendererAssistant assistant = new VRTableHeaderRendererAssistant();

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACTableHeaderViewer() {
        super();
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        Component cmp = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);
        if (cmp instanceof JLabel) {
            assistant.applyRenderer((JLabel) cmp, table, value, isSelected,
                    hasFocus, row, column);
        }
        return cmp;
    }
    
}
