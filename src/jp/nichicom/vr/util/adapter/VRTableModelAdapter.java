/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEvent;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;
import jp.nichicom.vr.component.table.VRTableModelar;

/**
 * �o�C���h�\�[�X�@�\�����������e�[�u�����f���̃A�_�v�^�N���X�ł��B
 * <p>
 * <code>VRBindSource</code> �`���̃I�u�W�F�N�g��TableModel�Ƃ��ē��ߓI�Ɉ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractTableModel
 * @see VRBindSource
 * @see VRBindSourceEventListener
 * @see VRBindSourceAdapter
 */
public class VRTableModelAdapter extends AbstractTableModel implements
        VRBindSource, VRBindSourceEventListener, VRTableModelar, VRBindSourceAdapter {
    private VRBindSource adaptee;

    private String[] columns;
    private JTable table;

    /**
     * �R���X�g���N�^�ł��B
     */
    public VRTableModelAdapter() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param columns �J������`
     */
    public VRTableModelAdapter(String[] columns) {
        super();
        setColumns(columns);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
     * @param columns �J������`
     */
    public VRTableModelAdapter(VRBindSource adaptee, String[] columns) {
        super();
        setAdaptee(adaptee);
        setColumns(columns);
    }

    public void addBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().addBindSourceEventListener(listener);
    }

    public void addData(Object data) {
        getAdaptee().addData(data);
    }

    public void addSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        super.fireTableRowsInserted(index, index);
    }

    public void changeSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        super.fireTableRowsUpdated(index, index);
    }

    public void clearData() {
        getAdaptee().clearData();
    }

    public void clearSource(VRBindSourceEvent e) {
        super.fireTableDataChanged();
    }

    /**
     * �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X��Ԃ��܂��B
     * 
     * @return adaptee
     */
    public VRBindSource getAdaptee() {
        return adaptee;
    }

    public int getColumnCount() {
        if (getColumns() == null) {
            return 0;
        }
        return getColumns().length;
    }

    public String getColumnName(int column) {
        return getColumns()[column];
    }

    /**
     * �J�������z�� ��Ԃ��܂��B
     * 
     * @return �J�������z��
     */
    public String[] getColumns() {
        return columns;
    }

    public Object getData() {
        return getAdaptee().getData();
    }

    public Object getData(int index) {
        return getAdaptee().getData(index);
    }

    public Object getData(Object key) {
        return getAdaptee().getData(key);
    }

    public int getDataSize() {
        return getAdaptee().getDataSize();
    }

    public int getRowCount() {
        return this.getDataSize();
    }

    public JTable getTable() {
        return table;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = getData(rowIndex);
        if (obj instanceof VRBindSource) {
            return ((VRBindSource) obj).getData(getColumnName(columnIndex));
        }
        if (obj instanceof List) {
            return ((List) obj).get(columnIndex);
        }
        throw new ArrayIndexOutOfBoundsException(columnIndex + " >= "
                + getColumnCount());
    }

    public void removeBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().removeBindSourceEventListener(listener);
    }

    public void removeData(int index) {
        getAdaptee().removeData(index);
    }

    public void removeData(Object key) {
        getAdaptee().removeData(key);
    }

    public void removeSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        if (index >= 0) {
            super.fireTableRowsDeleted(index, index + 1);
        }
    }

    /**
     * �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X��ݒ肵�܂��B
     * 
     * @param adaptee adaptee
     */
    public void setAdaptee(VRBindSource adaptee) {
        if (this.adaptee != null) {
            // ���g���\�[�X�̃��X�i�Ƃ��ēo�^
            this.adaptee.removeBindSourceEventListener(this);
        }
        this.adaptee = adaptee;
        if (adaptee != null) {
            // ���g���\�[�X�̃��X�i�Ƃ��ēo�^
            adaptee.addBindSourceEventListener(this);
        }
        // �č\��
        super.fireTableStructureChanged();
    }

    /**
     * �J�������z�� ��ݒ肵�܂��B
     * 
     * @param columns �J�������z��
     */
    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public void setData(int index, Object data) {
        getAdaptee().setData(index, data);
    }

    public void setData(Object data) {
        getAdaptee().setData(data);
    }

    public void setData(Object key, Object data) {
        setData(parseKey(key), data);
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Object obj = getData(rowIndex);
        if (obj instanceof VRBindSource) {
            ((VRBindSource) obj).setData(getColumnName(columnIndex), aValue);
        } else if (obj instanceof List) {
            ((List) obj).set(columnIndex, aValue);
        } else {
            throw new ArrayIndexOutOfBoundsException(columnIndex + " >= "
                    + getColumnCount());
        }
    }

    /**
     * �t�@�C�i���C�U�ł��B
     */
    protected void finalize() {
        if (adaptee != null) {
            adaptee.removeBindSourceEventListener(this);
        }
    }

    /**
     * �L�[��int�Ƃ��ĉ��߂��ĕԂ��܂��B
     * 
     * @param key �L�[
     * @return ���ߌ���
     */
    protected int parseKey(Object key) {
        if (key instanceof Integer) {
            return ((Integer) key).intValue();
        } else {
            return Integer.parseInt(String.valueOf(key));
        }
    }

}