/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * �\�[�g�ɑΉ������e�[�u�����f���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 * @see AbstractTableModel
 * @see VRTableModelar
 * @see VRTableColumn
 * @see VRTableSortable
 */
public class VRSortableTableModel extends AbstractTableModel implements
        VRSortableTableModelar, TableModelListener

{
    private int[] lastSortColumns;
    private int[] lastSortOrders;
    private ArrayList list;
    private TableModel model;
    private VRTableSortable sortable;

    private JTable table;

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * TableModel�I�u�W�F�N�g���f�R���[�g����VRTableModel�𐶐����܂��B
     * </p>
     * 
     * @param model TableModel�I�u�W�F�N�g
     */
    public VRSortableTableModel(TableModel model) {
        super();
        setModel(model);
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    protected VRSortableTableModel() {
        super();
        initComponent();
    }

    public Class getColumnClass(int columnIndex) {
        if (getModel() == null) {
            return super.getColumnClass(columnIndex);
        }
        return getModel().getColumnClass(columnIndex);
    }

    public int getColumnCount() {
        if (getModel() == null) {
            return 0;
        }
        return getModel().getColumnCount();
    }

    /**
     * ��ʒu column �̃r���[�ɕ\��������̃C���f�b�N�X��Ԃ��܂��B
     * 
     * @param columnName ��̖��O
     * @return ��̃C���f�b�N�X
     */
    public int getColumnIndex(String columnName) {
        int columnIndex = -1;
        for (int i = 0; i < getColumnCount(); i++) {
            if (getColumnName(i).equals(columnName)) {
                columnIndex = i;
                break;
            }
        }
        return columnIndex;
    }

    public String getColumnName(int column) {
        if (getModel() == null) {
            return super.getColumnName(column);
        }
        return getModel().getColumnName(column);
    }

    /**
     * ���f�[�^��Index���猩���ڏ��Index��Ԃ��܂��B
     * 
     * @param dataIndex ���f�[�^��Index
     * @return �����ڏ��Index
     */
    public int getReverseTranslateIndex(int dataIndex) {
        return list.indexOf(new Integer(dataIndex));
    }

    public int getRowCount() {
        if (getModel() == null) {
            return 0;
        }
        return getModel().getRowCount();
    }

    /**
     * �w��s�ԍ��̍s�f�[�^��Ԃ��܂��B
     * <p>
     * Adaptee�ƂȂ�������f����VRBindSource�̏ꍇ�ɂ̂ݗL���ł��B
     * </p>
     * 
     * @return �s�f�[�^
     * @throws Exception ������O
     */
    public Object getValueAt(int index) throws Exception {
        TableModel model = getModel();
        if (model instanceof VRBindSource) {
            return ((VRBindSource) model).getData(index);
        }
        if (model == null) {
            return null;
        }
        throw new IllegalAccessException("���Ή��̍s�f�[�^���f���ł��B");
    }

    /**
     * �\�[�g�I�u�W�F�N�g��Ԃ��܂��B
     * 
     * @return �\�[�g�I�u�W�F�N�g
     */
    public VRTableSortable getSortable() {
        return sortable;
    }

    /**
     * �e�e�[�u����Ԃ��܂��B
     * 
     * @return �e�e�[�u��
     */
    public JTable getTable() {
        if (getModel() instanceof VRTableModelar) {
            return ((VRTableModelar) getModel()).getTable();
        }
        return table;
    }

    /**
     * ���f�[�^��Index��Ԃ��܂��B
     * 
     * @param rowIndex �s�C���f�b�N�X
     * @return ���f�[�^�̍s�C���f�b�N�X
     */
    public int getTranslateIndex(int rowIndex) {
        return ((Integer) list.get(rowIndex)).intValue();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (getModel() == null) {
            return null;
        }
        int rowTranslateIndex = getTranslateIndex(rowIndex);
        return getModel().getValueAt(rowTranslateIndex, columnIndex);
    }

    public boolean isCellEditable(int row, int column) {
        if (getTable() != null) {
            TableColumnModel model = getTable().getColumnModel();
            if (model != null) {
                int end = model.getColumnCount();
                for (int i = 0; i < end; i++) {
                    TableColumn tableColumn = model.getColumn(i);
                    if (column == tableColumn.getModelIndex()) {
                        if (tableColumn instanceof VRTableColumnar) {
                            VRTableColumnar ncc = (VRTableColumnar) tableColumn;
                            return ncc.isVisible() & ncc.isEditable();
                        }
                    }
                }
            }
        }
        return super.isCellEditable(row, column);
    }

    /**
     * �s�̈ړ����s���܂��B
     * 
     * @param from �ړ����s
     * @param to �ړ���s
     */
    public void moveRow(int from, int to) {
        int fromIndex = getTranslateIndex(from);
        int toIndex = getTranslateIndex(to);
        list.set(from, new Integer(toIndex));
        list.set(to, new Integer(fromIndex));
    }

    /**
     * �Ō�Ƀ\�[�g�����Ƃ��Ɠ������Ń\�[�g�������s���܂��B
     */
    public void resort() {
        if (lastSortColumns != null) {
            sort(lastSortColumns, lastSortOrders);
        }
    }

    /**
     * �\�[�g�I�u�W�F�N�g��ݒ肵�܂��B
     * 
     * @param sortable �\�[�g�I�u�W�F�N�g
     */
    public void setSorter(VRTableSortable sortable) {
        this.sortable = sortable;
    }

    /**
     * �e�e�[�u����ݒ肵�܂��B
     * 
     * @param table �e�e�[�u��
     */
    public void setTable(JTable table) {
        if (getModel() instanceof VRTableModelar) {
            ((VRTableModelar) getModel()).setTable(table);
        }
        this.table = table;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (getModel() == null) {
            super.setValueAt(aValue, rowIndex, columnIndex);
            return;
        }
        int rowTranslateIndex = getTranslateIndex(rowIndex);
        getModel().setValueAt(aValue, rowTranslateIndex, columnIndex);
    }

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param columnIndexs ��C���f�b�N�X�z��
     * @param orders �\�[�g�����z��
     */
    public void sort(int[] columnIndexs, int[] orders) {
        if (getSortable() != null) {
            getSortable().sort(this, columnIndexs, orders);
            fireTableDataChanged();

            lastSortColumns = columnIndexs;
            lastSortOrders = orders;
        }
    }

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param condition �\�[�g������
     */
    public void sort(String condition) {
        int[][] items = analyzeSortCondition(condition);
        sort(items[0], items[1]);
    }

    public void tableChanged(TableModelEvent e) {
        int oldSize = list.size();
        int newSize = getRowCount();
        if ((oldSize > newSize)
                || ((e.getFirstRow() == 0) && (e.getLastRow() >= newSize - 1))) {
            initList();
            resort();
        } else if (oldSize < newSize) {
            for (int i = oldSize; i < newSize; i++) {
                list.add(new Integer(i));
            }
        }

        if ((e.getFirstRow() != e.getLastRow()) || e.getFirstRow() < 0
                || e.getLastRow() >= list.size()) {
            fireTableChanged(new TableModelEvent(this));
        } else {
            int index = list.indexOf(new Integer(e.getFirstRow()));
            fireTableChanged(new TableModelEvent(this, index, index));
        }

    }

    /**
     * �\�[�g�������̉�͂��s���܂��B
     * 
     * @param condition �\�[�g������
     * @return ��̓��X�g
     * @throws Exception
     */
    private int[][] analyzeSortCondition(String condition) {
        if (condition == null) {
            return null;
        }
        String[] conditions = condition.split(",");
        int[][] items = new int[2][conditions.length];
        for (int i = 0; i < conditions.length; i++) {
            String conditionString = conditions[i];
            int columnIndex = -1;
            int order = -1;
            if (conditionString.indexOf(VRTableSortable.ORDER_ASC_VALUE) != -1) {
                columnIndex = getColumnIndex(conditionString.replaceAll(
                        VRTableSortable.ORDER_ASC_VALUE, "").trim());
                order = VRTableSortable.ORDER_ASC;
            } else if (conditionString
                    .indexOf(VRTableSortable.ORDER_DESC_VALUE) != -1) {
                columnIndex = getColumnIndex(conditionString.replaceAll(
                        VRTableSortable.ORDER_DESC_VALUE, "").trim());
                order = VRTableSortable.ORDER_DESC;
            } else {
                columnIndex = getColumnIndex(conditionString.replaceAll(
                        VRTableSortable.ORDER_ASC_VALUE, "").trim());
                order = VRTableSortable.ORDER_ASC;
            }
            items[0][i] = columnIndex;
            items[1][i] = order;
        }
        return items;
    }

    /**
     * TableModel��Ԃ��܂��B
     * 
     * @return TableModel�I�u�W�F�N�g
     */
    protected TableModel getModel() {
        return model;
    }

    /**
     * �R���|�[�l���g�����������܂��B
     */
    protected void initComponent() {
        setSorter(new VRTableOrderBySorter());
    }

    /**
     * �|�󃊃X�g�̏�����
     */
    protected void initList() {
        list = new ArrayList();
        for (int i = 0; i < getRowCount(); i++) {
            list.add(new Integer(i));
        }
        // lastSortColumns = null;
        // lastSortOrders = null;
    }

    /**
     * TableModel��ݒ肵�܂��B
     * 
     * @param model TableModel�I�u�W�F�N�g
     */
    protected void setModel(TableModel model) {
        if (this.model != null) {
            this.model.removeTableModelListener(this);
        }
        this.model = model;
        if (this.model != null) {
            this.model.addTableModelListener(this);
            if (this.model instanceof VRTableModelar) {
                ((VRTableModelar) this.model).setTable(getTable());
            }
        }

        initList();
        resort();
        fireTableStructureChanged();
    }
}