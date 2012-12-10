/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

/**
 * テーブルのモデルのソート処理を実装するインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 * @see VRTableModelar
 */
public interface VRTableSortable {
    /**
     * <code>昇順を示す定数</code>
     */
    public static final int ORDER_ASC = 0;

    /**
     * <code>降順を示す定数</code>
     */
    public static final int ORDER_DESC = 1;

    /**
     * <code>昇順文字列を示す定数</code>
     */
    public static final String ORDER_ASC_VALUE = "ASC";

    /**
     * <code>降順文字列を示す定数</code>
     */
    public static final String ORDER_DESC_VALUE = "DESC";

    /**
     * ソート処理を行います。
     * 
     * @param model テーブルモデル
     * @param columnIndexs ソート優先順位インデックス
     * @throws Exception 処理例外
     */
    public void sort(VRSortableTableModelar model, int[] columnIndexs, int[] orders);
}