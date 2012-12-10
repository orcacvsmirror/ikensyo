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
 * ソートに対応したテーブルモデルです。
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
     * コンストラクタです。
     * <p>
     * TableModelオブジェクトをデコレートしたVRTableModelを生成します。
     * </p>
     * 
     * @param model TableModelオブジェクト
     */
    public VRSortableTableModel(TableModel model) {
        super();
        setModel(model);
        initComponent();
    }

    /**
     * コンストラクタです。
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
     * 列位置 column のビューに表示される列のインデックスを返します。
     * 
     * @param columnName 列の名前
     * @return 列のインデックス
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
     * 元データのIndexから見た目上のIndexを返します。
     * 
     * @param dataIndex 元データのIndex
     * @return 見た目上のIndex
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
     * 指定行番号の行データを返します。
     * <p>
     * Adapteeとなる内部モデルがVRBindSourceの場合にのみ有効です。
     * </p>
     * 
     * @return 行データ
     * @throws Exception 処理例外
     */
    public Object getValueAt(int index) throws Exception {
        TableModel model = getModel();
        if (model instanceof VRBindSource) {
            return ((VRBindSource) model).getData(index);
        }
        if (model == null) {
            return null;
        }
        throw new IllegalAccessException("未対応の行データモデルです。");
    }

    /**
     * ソートオブジェクトを返します。
     * 
     * @return ソートオブジェクト
     */
    public VRTableSortable getSortable() {
        return sortable;
    }

    /**
     * 親テーブルを返します。
     * 
     * @return 親テーブル
     */
    public JTable getTable() {
        if (getModel() instanceof VRTableModelar) {
            return ((VRTableModelar) getModel()).getTable();
        }
        return table;
    }

    /**
     * 元データのIndexを返します。
     * 
     * @param rowIndex 行インデックス
     * @return 元データの行インデックス
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
     * 行の移動を行います。
     * 
     * @param from 移動元行
     * @param to 移動先行
     */
    public void moveRow(int from, int to) {
        int fromIndex = getTranslateIndex(from);
        int toIndex = getTranslateIndex(to);
        list.set(from, new Integer(toIndex));
        list.set(to, new Integer(fromIndex));
    }

    /**
     * 最後にソートしたときと同条件でソート処理を行います。
     */
    public void resort() {
        if (lastSortColumns != null) {
            sort(lastSortColumns, lastSortOrders);
        }
    }

    /**
     * ソートオブジェクトを設定します。
     * 
     * @param sortable ソートオブジェクト
     */
    public void setSorter(VRTableSortable sortable) {
        this.sortable = sortable;
    }

    /**
     * 親テーブルを設定します。
     * 
     * @param table 親テーブル
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
     * ソート処理を行います。
     * 
     * @param columnIndexs 列インデックス配列
     * @param orders ソート順序配列
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
     * ソート処理を行います。
     * 
     * @param condition ソート条件式
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
     * ソート条件式の解析を行います。
     * 
     * @param condition ソート条件式
     * @return 解析リスト
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
     * TableModelを返します。
     * 
     * @return TableModelオブジェクト
     */
    protected TableModel getModel() {
        return model;
    }

    /**
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        setSorter(new VRTableOrderBySorter());
    }

    /**
     * 翻訳リストの初期化
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
     * TableModelを設定します。
     * 
     * @param model TableModelオブジェクト
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