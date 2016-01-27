/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * <code>VRTableColumn</code>�ɑΉ������e�[�u���J�������f���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see DefaultTableColumnModel
 * @see JTable
 * @see VRTableColumn
 * @see VRTableColumnModelar
 */
public class VRTableColumnModel extends DefaultTableColumnModel implements
        VRTableColumnModelar {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;

    /**
     * �R���X�g���N�^�ł��B
     */
    public VRTableColumnModel() {
        super();
        tableColumns = new VRTableColumnModelVector();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param columns �ǉ�����J�����z��
     */
    public VRTableColumnModel(String[] columns) {
        super();
        tableColumns = new VRTableColumnModelVector();
        int end = columns.length;
        for (int i = 0; i < end; i++) {
            TableColumn column = createTableColumn();
            column.setHeaderValue(columns[i]);
            column.setModelIndex(i);
            addColumn(column);
        }
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param columns �ǉ�����J�����z��
     */
    public VRTableColumnModel(TableColumn[] columns) {
        super();
        tableColumns = new VRTableColumnModelVector();
        int end = columns.length;
        for (int i = 0; i < end; i++) {
            addColumn(columns[i]);
        }
    }

    public void addColumn(TableColumn column) {
        if (column instanceof VRTableColumnar) {
            ((VRTableColumnar) column).setTable(getTable());
        }
        super.addColumn(column);

    }

    /**
     * �e�e�[�u����Ԃ��܂��B
     * 
     * @return �e�e�[�u��
     */
    public JTable getTable() {
        return table;
    }

    /**
     * �e�e�[�u����ݒ肵�܂��B
     * 
     * @param table �e�e�[�u��
     */
    public void setTable(JTable table) {
        this.table = table;

        int end = getColumnCount();
        for (int i = 0; i < end; i++) {
            TableColumn column = getColumn(i);
            if (column instanceof VRTableColumnar) {
                ((VRTableColumnar) column).setTable(table);
            }
        }
    }

    /**
     * �e�[�u���J�����𐶐����܂��B
     * 
     * @return �e�[�u���J����
     */
    protected TableColumn createTableColumn() {
        return new VRTableColumn();
    }

    /**
     * �ێ�����<code>VRTableColumn</code>�̕\����Ԃɉ����ėv�f���𐧌䂷��g��Vector�ł��B
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2006/03/31
     * @see Vector
     * @see VRTableColumn
     */
    protected class VRTableColumnModelVector extends Vector {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public synchronized Object elementAt(int index) {
            return super.elementAt(toRealColumnCount(index));
        }

        public Enumeration elements() {
            return new NCTableColumnModelVectorEnumeration();
        }

        public synchronized int indexOf(Object elem, int index) {
            return super.indexOf(elem, toVisibleColumnIndex(index));
        }

        public synchronized void insertElementAt(Object obj, int index) {
            super.insertElementAt(obj, toVisibleColumnIndex(index));
        }

        public synchronized Object realElementAt(int index) {
            return super.elementAt(index);
        }

        public Enumeration realElements() {
            return super.elements();
        }

        public synchronized int realIndexOf(Object elem, int index) {
            return super.indexOf(elem, index);
        }

        public synchronized void realInsertElementAt(Object obj, int index) {
            super.insertElementAt(obj, index);
        }

        public synchronized void realRemoveElementAt(int index) {
            super.removeElementAt(index);
        }

        public synchronized int realSize() {
            return super.size();
        }

        public synchronized void removeElementAt(int index) {
            super.removeElementAt(toVisibleColumnIndex(index));
        }

        public synchronized int size() {
            return toVisibleColumnIndex(elementCount);
        }

        /**
         * ���C���f�b�N�X���\���J�����v�Z���s�Ȃ������z�C���f�b�N�X�Ƃ��ĕԂ��܂��B
         * 
         * @param index ���C���f�b�N�X
         * @return ���z�C���f�b�N�X
         */
        public int toVisibleColumnIndex(int index) {
            if (index == 0) {
                return 0;
            }
            int to = 0;
            for (int i = 0; i < index; i++) {
                if (elementData[i] instanceof VRTableColumnar) {
                    if (!((VRTableColumnar) elementData[i]).isVisible()) {
                        // ��\���Ȃ���Z���Ȃ�
                        continue;
                    }
                }
                to++;
            }
            return to;
        }

        /**
         * ��\���J�����v�Z���s�Ȃ������z�C���f�b�N�X�����C���f�b�N�X�Ƃ��ĕԂ��܂��B
         * 
         * @param index ���z�C���f�b�N�X
         * @return ���C���f�b�N�X
         */
        public int toRealColumnCount(int index) {
           
            int end = realSize();
            int last = index;
            for (int i = 0; i < end; i++) {
                if (elementData[i] instanceof VRTableColumnar) {
                    if (!((VRTableColumnar) elementData[i]).isVisible()) {
                        // ��\���Ȃ�ǉ����Z
                        last++;
                    }
                }
                
                if (last<=i) {
                    break;
                }
            }
            return last;
        }

        /**
         * <code>NCTableColumnModelVector</code>�p��Enumeration�N���X�ł��B
         * <p>
         * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
         * </p>
         * 
         * @author Tozo Tanaka
         * @version 1.0 2005/12/01
         */
        protected class NCTableColumnModelVectorEnumeration implements
                Enumeration {
            int count = 0;

            int maxCount = 0;

            public NCTableColumnModelVectorEnumeration() {
                maxCount = size();
            }

            public boolean hasMoreElements() {
                return count < maxCount;
            }

            public Object nextElement() {
                synchronized (this) {
                    if (count < maxCount) {
                        return elementAt(count++);
                    }
                }
                throw new NoSuchElementException("Vector Enumeration");
            }
        }

    }
}