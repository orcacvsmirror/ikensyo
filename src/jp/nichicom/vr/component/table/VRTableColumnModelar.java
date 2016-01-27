package jp.nichicom.vr.component.table;

import java.util.EventListener;

import javax.swing.JTable;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumnModel;

/**
 * テーブルカラムモデルインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see TableColumnModel
 */
public interface VRTableColumnModelar extends TableColumnModel {

    /**
     * Returns an array of all the column model listeners registered on this
     * model.
     * 
     * @return all of this default table column model's
     *         <code>ColumnModelListener</code>s or an empty array if no
     *         column model listeners are currently registered
     * @see #addColumnModelListener
     * @see #removeColumnModelListener
     * @since 1.4
     */
    public TableColumnModelListener[] getColumnModelListeners();

    /**
     * Returns an array of all the objects currently registered as
     * <code><em>Foo</em>Listener</code>s upon this model.
     * <code><em>Foo</em>Listener</code>s are registered using the
     * <code>add<em>Foo</em>Listener</code> method.
     * <p>
     * You can specify the <code>listenerType</code> argument with a class
     * literal, such as <code><em>Foo</em>Listener.class</code>. For
     * example, you can query a
     * <code>DefaultTableColumnModel</code> <code>m</code> for its column
     * model listeners with the following code:
     * 
     * <pre>
     * ColumnModelListener[] cmls = (ColumnModelListener[]) (m
     *         .getListeners(ColumnModelListener.class));
     * </pre>
     * 
     * If no such listeners exist, this method returns an empty array.
     * 
     * @param listenerType the type of listeners requested; this parameter
     *            should specify an interface that descends from
     *            <code>java.util.EventListener</code>
     * @return an array of all objects registered as
     *         <code><em>Foo</em>Listener</code>s on this model, or an
     *         empty array if no such listeners have been added
     * @exception ClassCastException if <code>listenerType</code> doesn't
     *                specify a class or interface that implements
     *                <code>java.util.EventListener</code>
     * @see #getColumnModelListeners
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
     * 親テーブルを設定します。
     * 
     * @param table 親テーブル
     */
    public void setTable(JTable table);

}
