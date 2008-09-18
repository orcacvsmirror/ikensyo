package jp.or.med.orca.ikensho.component;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * �ꗗ�ҏW�\�R���{�{�b�N�X���f���f�R���[�^�[�N���X <br />
 * �ꗗ�ҏW�\�R���{�{�b�N�X���f���N���X���f�R���[�g���A�ҏW���ڂ�ǉ��������f���N���X�ł��B <br />
 *
 * �쐬�� 2007/10/11 <br />
 * �X�V�� <br />
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved. <br />
 *
 * @author Masahiko Higuchi
 * @version 1.0
 */
public class IkenshoOptionComboBoxModelDecorator extends AbstractListModel
        implements ComboBoxModel, ListDataListener {

    private ComboBoxModel originalModel;

    private ArrayList options = new ArrayList();

    private Object selectedItem;

    public IkenshoOptionComboBoxModelDecorator(ComboBoxModel originalModel) {
        super();
        setOriginalModel(originalModel);
    }

    /**
     * originalModel ��Ԃ��܂��B
     *
     * @return originalModel
     */
    public ComboBoxModel getOriginalModel() {
        return originalModel;
    }

    /**
     * originalModel ��ݒ肵�܂�
     *
     * @param originalModel originalModel
     */
    public void setOriginalModel(ComboBoxModel originalModel) {

        if (this.originalModel != null) {
            this.originalModel.removeListDataListener(this);
        }
        this.originalModel = originalModel;
        this.originalModel.addListDataListener(this);

    }

    public Object getSelectedItem() {
        return originalModel.getSelectedItem();

    }

    public void setSelectedItem(Object anItem) {
        originalModel.setSelectedItem(anItem);
    }

    public Object getElementAt(int index) {
        if (index >= originalModel.getSize()) {
            return options.get(index - originalModel.getSize());
        } else {
            return originalModel.getElementAt(index);
        }
    }

    public int getSize() {
        return originalModel.getSize() + options.size();
    }

    //�C�x���g�|��
    public void contentsChanged(ListDataEvent e) {
        fireContentsChanged(this, e.getIndex0(), e.getIndex1());
    }

    //�C�x���g�|��
    public void intervalAdded(ListDataEvent e) {
        fireIntervalAdded(this, e.getIndex0(), e.getIndex1());
    }

    //�C�x���g�|��
    public void intervalRemoved(ListDataEvent e) {
        fireIntervalRemoved(this, e.getIndex0(), e.getIndex1());

    }

    /**
     * customizable ��Ԃ��܂��B
     *
     * @return customizable
     */
    public ArrayList getOptions() {
        return options;
    }

}
