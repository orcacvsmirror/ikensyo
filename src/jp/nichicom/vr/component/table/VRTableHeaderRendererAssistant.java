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
 * �e�[�u���w�b�_�p�̃����_���⏕�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/25
 */
public class VRTableHeaderRendererAssistant {
    /**
     * �R���X�g���N�^�ł��B
     */
    public VRTableHeaderRendererAssistant() {
        super();
    }

    /**
     * �����_���̕\�����ڂ�ݒ肵�܂��B
     * 
     * @param renderer �����_��
     * @param table �e�[�u��
     * @param value �l
     * @param isSelected �I�����
     * @param hasFocus �t�H�[�J�X�̗L��
     * @param row �s�ԍ�
     * @param column ��ԍ�
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
            // �\�[�g�����e����J�����ł��邩
            sortableColumn = ((VRTableColumnar) columnObj).isSortable();
        }
        Icon oldIcon = renderer.getIcon();
        Icon newIcon = null;
        if ((sortableColumn) && (table instanceof VRTablar)) {
            // VRTablar�C���^�[�t�F�[�X���������Ă��邩
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
            // �ݒ肷�ׂ��A�C�R���ɕω����������ꍇ�̓A�C�R����ύX
            renderer.setIcon(newIcon);
        }

        renderer.setOpaque(true);
        renderer.setText((value == null) ? "" : value.toString());
        renderer.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }
}
