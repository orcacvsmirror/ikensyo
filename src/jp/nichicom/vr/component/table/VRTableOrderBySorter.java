/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.TableModel;

/**
 * SQLのOrder By句形式でソート指定可能なソート制御クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 * @see VRTableSortable
 * @see VRTableModelar
 */
public class VRTableOrderBySorter implements VRTableSortable {

    public void sort(VRSortableTableModelar model, int[] columnIndexs, int[] orders) {
        List sortIndexs = null;
        for (int i = 0; i < columnIndexs.length; i++) {
            int columnIndex = columnIndexs[i];
            int order = orders[i];
            if (sortIndexs != null) {
                Iterator itSortIndex = sortIndexs.iterator();
                while (itSortIndex.hasNext()) {
                    VRTableOrderBySortIndexItem sortIndex = (VRTableOrderBySortIndexItem) itSortIndex
                            .next();
                    selectSort(model, columnIndex, order, sortIndex.getFirst(),
                            sortIndex.getEnd());
                }
            } else {
                selectSort(model, columnIndex, order, 0, model.getRowCount());
            }
            sortIndexs = getSortIndexItem(model, columnIndex);
        }

    }

    /**
     * ソートを実行します。
     * 
     * @param model 対象のテーブルモデル
     * @param columnIndex カラム番号
     * @param order ソート方向
     * @param first 開始位置
     * @param end 終了位置
     */
    protected void selectSort(VRSortableTableModelar model, int columnIndex, int order,
            int first, int end) {
        if (ORDER_ASC == order) {
            for (int i = first; i < end - 1; i++) {
                for (int j = end - 1; j > i; j--) {

                    Object objX = model.getValueAt(j, columnIndex);
                    Object objY = model.getValueAt(j - 1, columnIndex);

                    if (!(objX instanceof Component)
                            && !(objY instanceof Component)) {

                        if (objX == null) {
                            model.moveRow(j, j - 1);
                        } else if (!(objX instanceof Comparable)) {
                            model.moveRow(j, j - 1);
                        } else if (objY != null && objY instanceof Comparable) {
                            //昇順
                            if (((Comparable) objX).compareTo(objY) < 0) {
                                model.moveRow(j, j - 1);
                            }
                        }

                    }

                }
            }

        } else if (ORDER_DESC == order) {
            for (int i = first; i < end - 1; i++) {
                for (int j = end - 1; j > i; j--) {

                    Object objX = model.getValueAt(j, columnIndex);
                    Object objY = model.getValueAt(j - 1, columnIndex);

                    if (!(objX instanceof Component)
                            && !(objY instanceof Component)) {
                        if (objY == null) {
                            model.moveRow(j, j - 1);
                        } else if (!(objY instanceof Comparable)) {
                            model.moveRow(j, j - 1);
                        } else if (objX != null && objX instanceof Comparable) {
                            //降順
                            if (((Comparable) objX).compareTo(objY) > 0) {
                                model.moveRow(j, j - 1);
                            }
                        }

                    }

                }
            }

        }

    }

    /**
     * 指定カラムのソート結果を格納します。
     * @param model 対象のテーブルモデル
     * @param columnIndex カラム番号
     * @return ソート結果
     */
    protected List getSortIndexItem(TableModel model, int columnIndex) {
        List sortIndexs = new ArrayList();
        int count = 0;
        for (int i = 0; i < model.getRowCount() - 1; i++) {
            VRTableOrderBySortIndexItem item = new VRTableOrderBySortIndexItem();
            Object obj = model.getValueAt(i, columnIndex);
            Object obj2 = model.getValueAt(i + 1, columnIndex);
            if (obj == null || !obj.equals(obj2)) {
                item.setFirst(count);
                item.setEnd(i + 1);
                count = i + 1;
                sortIndexs.add(item);
            }
        }
        VRTableOrderBySortIndexItem item = new VRTableOrderBySortIndexItem();
        item.setFirst(count);
        item.setEnd(model.getRowCount());
        sortIndexs.add(item);
        return sortIndexs;
    }

    /**
     * <code>VRTableOrderBySorter</code> 用のソート結果格納クラスです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Yosuke Takemoto
     * @version 1.0 2005/10/31
     * @see VRTableSortable
     * @see VRTableModelar
     */
    protected class VRTableOrderBySortIndexItem {
        private int first;

        private int end;

        /**
         * 終了位置を返します。
         * @return 終了位置
         */
        public int getEnd() {
            return end;
        }

        /**
         * 開始位置を返します。
         * @return 開始位置
         */
        public int getFirst() {
            return first;
        }

        /**
         * 開始位置を設定します。
         * @param first 開始位置
         */
        public void setFirst(int first) {
            this.first = first;
        }

        /**
         * 終了位置を設定します。
         * @param end 終了位置
         */
        public void setEnd(int end) {
            this.end = end;
        }
    }

}