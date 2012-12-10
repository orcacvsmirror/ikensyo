/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * �\�[�g�ɑΉ������e�[�u�����f���C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractTableModel
 * @see VRTableColumn
 * @see VRTableSortable
 */
public interface VRSortableTableModelar extends VRTableModelar {
    /**
     * ��ʒu column �̃r���[�ɕ\��������̃C���f�b�N�X��Ԃ��܂��B
     * 
     * @param columnName ��̖��O
     * @return ��̃C���f�b�N�X
     */
    public int getColumnIndex(String columnName);

    /**
     * ���f�[�^��Index���猩���ڏ��Index��Ԃ��܂��B
     * 
     * @param dataIndex ���f�[�^��Index
     * @return �����ڏ��Index
     */
    public int getReverseTranslateIndex(int dataIndex);

    /**
     * �w��s�ԍ��̍s�f�[�^��Ԃ��܂��B
     * <p>
     * Adaptee�ƂȂ�������f����VRBindSource�̏ꍇ�ɂ̂ݗL���ł��B
     * </p>
     * 
     * @return �s�f�[�^
     * @throws Exception ������O
     */
    public Object getValueAt(int index) throws Exception;

    /**
     * �\�[�g�I�u�W�F�N�g��Ԃ��܂��B
     * 
     * @return �\�[�g�I�u�W�F�N�g
     */
    public VRTableSortable getSortable();

    /**
     * ���f�[�^��Index��Ԃ��܂��B
     * 
     * @param rowIndex �s�C���f�b�N�X
     * @return ���f�[�^�̍s�C���f�b�N�X
     */
    public int getTranslateIndex(int rowIndex);

    /**
     * �s�̈ړ����s���܂��B
     * 
     * @param from �ړ����s
     * @param to �ړ���s
     */
    public void moveRow(int from, int to);

    /**
     * �Ō�Ƀ\�[�g�����Ƃ��Ɠ������Ń\�[�g�������s���܂��B
     */
    public void resort();

    /**
     * �\�[�g�I�u�W�F�N�g��ݒ肵�܂��B
     * 
     * @param sortable �\�[�g�I�u�W�F�N�g
     */
    public void setSorter(VRTableSortable sortable);

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param columnIndexs ��C���f�b�N�X�z��
     * @param orders �\�[�g�����z��
     */
    public void sort(int[] columnIndexs, int[] orders);

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param condition �\�[�g������
     */
    public void sort(String condition);
}
