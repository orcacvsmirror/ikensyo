/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.io.Serializable;
import java.util.EventListener;

import javax.swing.JTable;
import javax.swing.event.EventListenerList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * テーブルモデルインターフェースです。
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
public interface VRTableModelar extends TableModel, Serializable {
    /**
     * Returns a column given its name. Implementation is naive so this should
     * be overridden if this method is to be called often. This method is not in
     * the <code>TableModel</code> interface and is not used by the
     * <code>JTable</code>.
     * 
     * @param columnName string containing name of column to be located
     * @return the column with <code>columnName</code>, or -1 if not found
     */
    public int findColumn(String columnName);

    /**
     * Notifies all listeners that the value of the cell at
     * <code>[row, column]</code> has been updated.
     * 
     * @param row row of cell which has been updated
     * @param column column of cell which has been updated
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableCellUpdated(int row, int column);

    /**
     * Forwards the given notification event to all
     * <code>TableModelListeners</code> that registered themselves as
     * listeners for this table model.
     * 
     * @param e the event to be forwarded
     * @see #addTableModelListener
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableChanged(TableModelEvent e);

    /**
     * Notifies all listeners that all cell values in the table's rows may have
     * changed. The number of rows may also have changed and the
     * <code>JTable</code> should redraw the table from scratch. The structure
     * of the table (as in the order of the columns) is assumed to be the same.
     * 
     * @see TableModelEvent
     * @see EventListenerList
     * @see javax.swing.JTable#tableChanged(TableModelEvent)
     */
    public void fireTableDataChanged();

    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been deleted.
     * 
     * @param firstRow the first row
     * @param lastRow the last row
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsDeleted(int firstRow, int lastRow);

    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been inserted.
     * 
     * @param firstRow the first row
     * @param lastRow the last row
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsInserted(int firstRow, int lastRow);

    /**
     * Notifies all listeners that rows in the range
     * <code>[firstRow, lastRow]</code>, inclusive, have been updated.
     * 
     * @param firstRow the first row
     * @param lastRow the last row
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableRowsUpdated(int firstRow, int lastRow);

    /**
     * Notifies all listeners that the table's structure has changed. The number
     * of columns in the table, and the names and types of the new columns may
     * be different from the previous state. If the <code>JTable</code>
     * receives this event and its <code>autoCreateColumnsFromModel</code>
     * flag is set it discards any table columns that it had and reallocates
     * default columns in the order they appear in the model. This is the same
     * as calling <code>setModel(TableModel)</code> on the <code>JTable</code>.
     * 
     * @see TableModelEvent
     * @see EventListenerList
     */
    public void fireTableStructureChanged();

    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s upon this
     * <code>AbstractTableModel</code>. <code><em>Foo</em>Listener</code>s
     * are registered using the <code>add<em>Foo</em>Listener</code>
     * method.
     * <p>
     * You can specify the <code>listenerType</code> argument with a class
     * literal, such as <code><em>Foo</em>Listener.class</code>. For
     * example, you can query a model <code>m</code> for its table model
     * listeners with the following code:
     * 
     * <pre>
     * TableModelListener[] tmls = (TableModelListener[]) (m
     *         .getListeners(TableModelListener.class));
     * </pre>
     * 
     * If no such listeners exist, this method returns an empty array.
     * 
     * @param listenerType the type of listeners requested; this parameter
     *            should specify an interface that descends from
     *            <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *         <code><em>Foo</em>Listener</code>s on this component, or an
     *         empty array if no such listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     *                specify a class or interface that implements
     *                <code>java.util.EventListener</code>
     * @see #getTableModelListeners
     * @since 1.3
     */
//    public EventListener[] getListeners(Class listenerType);

    /**
     * 親テーブルを返します。
     * 
     * @return 親テーブル
     */
    public JTable getTable();

    /**
     * Returns an array of all the table model listeners registered on this
     * model.
     * 
     * @return all of this model's <code>TableModelListener</code>s or an
     *         empty array if no table model listeners are currently registered
     * @see #addTableModelListener
     * @see #removeTableModelListener
     * @since 1.4
     */
    public TableModelListener[] getTableModelListeners();

    /**
     * 親テーブルを設定します。
     * 
     * @param table 親テーブル
     */
    public void setTable(JTable table);

}
