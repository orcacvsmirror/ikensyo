/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import javax.swing.ComboBoxModel;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * �o�C���h�\�[�X�@�\����������ComboBoxModel�̃A�_�v�^�N���X�ł��B
 * <p>
 * <code>VRBindSource</code> �`���̃I�u�W�F�N�g��ComboBoxModel�Ƃ��ē��ߓI�Ɉ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRListModelAdapter
 * @see VRBindSource
 * @see VRBindSourceEventListener
 */
public class VRComboBoxModelAdapter extends VRListModelAdapter implements
        ComboBoxModel {

    protected Object selectedItem;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
     */
    public VRComboBoxModelAdapter(VRBindSource adaptee) {
        super(adaptee);
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object anItem) {
        if ((selectedItem != null && !selectedItem.equals(anItem))
                || selectedItem == null && anItem != null) {
            selectedItem = anItem;
            fireContentsChanged(this, -1, -1);
        }
    }

}