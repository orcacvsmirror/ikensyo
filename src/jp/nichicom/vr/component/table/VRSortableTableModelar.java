/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * ソートに対応したテーブルモデルインターフェースです。
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
     * 列位置 column のビューに表示される列のインデックスを返します。
     * 
     * @param columnName 列の名前
     * @return 列のインデックス
     */
    public int getColumnIndex(String columnName);

    /**
     * 元データのIndexから見た目上のIndexを返します。
     * 
     * @param dataIndex 元データのIndex
     * @return 見た目上のIndex
     */
    public int getReverseTranslateIndex(int dataIndex);

    /**
     * 指定行番号の行データを返します。
     * <p>
     * Adapteeとなる内部モデルがVRBindSourceの場合にのみ有効です。
     * </p>
     * 
     * @return 行データ
     * @throws Exception 処理例外
     */
    public Object getValueAt(int index) throws Exception;

    /**
     * ソートオブジェクトを返します。
     * 
     * @return ソートオブジェクト
     */
    public VRTableSortable getSortable();

    /**
     * 元データのIndexを返します。
     * 
     * @param rowIndex 行インデックス
     * @return 元データの行インデックス
     */
    public int getTranslateIndex(int rowIndex);

    /**
     * 行の移動を行います。
     * 
     * @param from 移動元行
     * @param to 移動先行
     */
    public void moveRow(int from, int to);

    /**
     * 最後にソートしたときと同条件でソート処理を行います。
     */
    public void resort();

    /**
     * ソートオブジェクトを設定します。
     * 
     * @param sortable ソートオブジェクト
     */
    public void setSorter(VRTableSortable sortable);

    /**
     * ソート処理を行います。
     * 
     * @param columnIndexs 列インデックス配列
     * @param orders ソート順序配列
     */
    public void sort(int[] columnIndexs, int[] orders);

    /**
     * ソート処理を行います。
     * 
     * @param condition ソート条件式
     */
    public void sort(String condition);
}
