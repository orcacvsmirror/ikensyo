/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.TableModel;

/**
 * SQL��Order By��`���Ń\�[�g�w��\�ȃ\�[�g����N���X�ł��B
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
     * �\�[�g�����s���܂��B
     * 
     * @param model �Ώۂ̃e�[�u�����f��
     * @param columnIndex �J�����ԍ�
     * @param order �\�[�g����
     * @param first �J�n�ʒu
     * @param end �I���ʒu
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
                            //����
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
                            //�~��
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
     * �w��J�����̃\�[�g���ʂ��i�[���܂��B
     * @param model �Ώۂ̃e�[�u�����f��
     * @param columnIndex �J�����ԍ�
     * @return �\�[�g����
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
     * <code>VRTableOrderBySorter</code> �p�̃\�[�g���ʊi�[�N���X�ł��B
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
         * �I���ʒu��Ԃ��܂��B
         * @return �I���ʒu
         */
        public int getEnd() {
            return end;
        }

        /**
         * �J�n�ʒu��Ԃ��܂��B
         * @return �J�n�ʒu
         */
        public int getFirst() {
            return first;
        }

        /**
         * �J�n�ʒu��ݒ肵�܂��B
         * @param first �J�n�ʒu
         */
        public void setFirst(int first) {
            this.first = first;
        }

        /**
         * �I���ʒu��ݒ肵�܂��B
         * @param end �I���ʒu
         */
        public void setEnd(int end) {
            this.end = end;
        }
    }

}