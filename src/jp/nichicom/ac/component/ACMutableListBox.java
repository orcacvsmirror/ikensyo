package jp.nichicom.ac.component;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRListBox;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.adapter.VRBindSourceAdapter;

/**
 * ���f���v�f���ԐړI�ɑ���\�ȃ��X�g�{�b�N�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACMutableListBox extends VRListBox {
    private int columns = 0;
    private int columnWidth;

    /**
     * Returns the number of columns in this <code>TextField</code>.
     * 
     * @return the number of columns >= 0
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Sets the number of columns in this <code>TextField</code>, and then
     * invalidate the layout.
     * 
     * @param columns the number of columns >= 0
     * @exception IllegalArgumentException if <code>columns</code> is less
     *                than 0
     * @beaninfo description: the number of columns preferred for display
     */
    public void setColumns(int columns) {
        int oldVal = this.columns;
        if (columns < 0) {
            throw new IllegalArgumentException("columns less than zero.");
        }
        if (columns != oldVal) {
            this.columns = columns;
            invalidate();
        }
    }
    
    public Object getSelectedValue() {
        int i = getMinSelectionIndex();
        if(i == -1){
            return null;
        }
        //��������`�F�b�N����B
        if(getModel().getSize()<=i){
            return null;
        }
        return (i == -1) ? null : getModel().getElementAt(i);
    }
    
    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code> where both
     * horizontal and vertical scrollbars appear when needed.
     */
    public ACMutableListBox() {
        super();
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified, non-<code>null</code> model. All <code>JList</code>
     * constructors delegate to this one.
     * 
     * @param dataModel the data model for this list
     * @exception IllegalArgumentException if <code>dataModel</code> is
     *                <code>null</code>
     */
    public ACMutableListBox(ListModel dataModel) {
        super(dataModel);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified array. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the array of Objects to be loaded into the data model
     */

    public ACMutableListBox(Object[] listData) {
        super(listData);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified <code>Vector</code>. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the <code>Vector</code> to be loaded into the data
     *            model
     */
    public ACMutableListBox(Vector listData) {
        super(listData);
    }

    /**
     * ���ڂ�ǉ����܂��B
     * 
     * @param item �ǉ�����
     */
    public void addItem(Object item) {
        Object mdl = getModel();
        while (mdl != null) {
            if (mdl instanceof DefaultListModel) {
                ((DefaultListModel) mdl).addElement(item);
                return;
            } else if (mdl instanceof VRBindSource) {
                ((VRBindSource) mdl).addData(item);
                return;
            } else if (mdl instanceof List) {
                ((List) mdl).add(item);
                return;
            } else if (mdl instanceof VRBindSourceAdapter) {
                mdl = ((VRBindSourceAdapter) mdl).getAdaptee();
            } else {
                return;
            }
        }
    }

    /**
     * ���ڂ����ׂč폜���܂��B
     */
    public void clearItem() {
        Object mdl = getModel();
        while (mdl != null) {
            if (mdl instanceof DefaultListModel) {
                ((DefaultListModel) mdl).removeAllElements();
                return;
            } else if (mdl instanceof VRBindSource) {
                ((VRBindSource) mdl).clearData();
                return;
            } else if (mdl instanceof List) {
                ((List) mdl).clear();
                return;
            } else if (mdl instanceof VRBindSourceAdapter) {
                mdl = ((VRBindSourceAdapter) mdl).getAdaptee();
            } else {
                return;
            }
        }
    }

    /**
     * �w��ʒu�̍��ڂ�Ԃ��܂��B
     * 
     * @param index ���ڈʒu
     * @return �w��ʒu�̍���
     */
    public Object getItemAt(int index) {
        return getModel().getElementAt(index);
    }

    /**
     * ���ڐ���Ԃ��܂��B
     * 
     * @return ���ڐ�
     */
    public int getItemCount() {
        return getModel().getSize();
    }

    /**
     * �o�C���h�\�[�X�`���Ń��X�g���f����Ԃ��܂��B
     * <p>
     * ���f�����o�C���h�\�[�X�`������\������Ă��Ȃ��ꍇ�Anull��Ԃ��܂��B
     * </p>
     * 
     * @return ���f��
     */
    public VRBindSource getModelAtBindSource() {
        Object mdl = getModel();
        while (mdl != null) {
            if (mdl instanceof VRBindSource) {
                return (VRBindSource) mdl;
            } else if (mdl instanceof VRBindSourceAdapter) {
                mdl = ((VRBindSourceAdapter) mdl).getAdaptee();
            } else {
                return null;
            }
        }
        return null;
    }

    /**
     * ���ڂ��폜���܂��B
     * 
     * @param index �폜�ʒu
     */
    public void removeItemAt(int index) {
        Object mdl = getModel();
        while (mdl != null) {
            if (mdl instanceof DefaultListModel) {
                ((DefaultListModel) mdl).removeElementAt(index);
                return;
            } else if (mdl instanceof VRBindSource) {
                ((VRBindSource) mdl).removeData(index);
                return;
            } else if (mdl instanceof List) {
                ((List) mdl).remove(index);
                return;
            } else if (mdl instanceof VRBindSourceAdapter) {
                mdl = ((VRBindSourceAdapter) mdl).getAdaptee();
            } else {
                return;
            }
        }
    }

    /**
     * �A�_�v�^�N���X�𐶐����AList���R���{���f���Ƃ��Đݒ肵�܂��B
     * 
     * @param model ���f��
     */
    public void setModel(List model) {
        setModel(new VRArrayList(model));
    }

    /**
     * �A�_�v�^�N���X�𐶐����A�z����R���{���f���Ƃ��Đݒ肵�܂��B
     * 
     * @param model ���f��
     */
    public void setModel(Object[] model) {
        setModel(Arrays.asList(model));
    }

    /**
     * �A�_�v�^�N���X�𐶐����AVRList���R���{���f���Ƃ��Đݒ肵�܂��B
     * 
     * @param model ���f��
     */
    public void setModel(VRList model) {
        setModel(new ACListModelAdapter(model));
    }

    protected void initComponent() {
        super.initComponent();
        setModel(new VRArrayList());
    }

    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = super.getPreferredScrollableViewportSize();
        if (getColumns() > 0) {
            Insets insets = getInsets();
            size.width = getColumns() * getColumnWidth() + insets.left
                    + insets.right;
        }
        return size;
    }

    /**
     * Returns the column width. The meaning of what a column is can be
     * considered a fairly weak notion for some fonts. This method is used to
     * define the width of a column. By default this is defined to be the width
     * of the character <em>m</em> for the font used. This method can be
     * redefined to be some alternative amount
     * 
     * @return the column width >= 1
     */
    protected int getColumnWidth() {
        if (columnWidth == 0) {
            FontMetrics metrics = getFontMetrics(getFont());
            columnWidth = metrics.charWidth('m');
        }
        // �S�p�������l�����ĕ��v�Z�����邽�߁A1.1�{���ĕԂ�
        return (int) (columnWidth * 1.1);
    }

//    public void removeNotify(){
//        if((getModel()!=null)&&(getModel().getSize()>0)){
//            setModel(new DefaultListModel());
//        }
//    }
}
