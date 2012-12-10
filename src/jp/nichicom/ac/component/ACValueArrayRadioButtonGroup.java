package jp.nichicom.ac.component;

import java.awt.event.ItemEvent;
import java.util.List;

import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;

/**
 * �{�^�����Ƃɒl��ݒ�ł��郉�W�I�O���[�v�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractACClearableRadioButtonGroup
 */

public class ACValueArrayRadioButtonGroup extends
        AbstractACClearableRadioButtonGroup {
    protected int[] values;
    protected int noSelectIndex = 0;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACValueArrayRadioButtonGroup() {
        super();
    }

    /**
     * �l�W�� ��Ԃ��܂��B
     * 
     * @return �l�W��
     */
    public int[] getValues() {
        return values;
    }

    /**
     * �l�W�� ��ݒ肵�܂��B
     * 
     * @param values �l�W��
     */
    public void setValues(int[] values) {
        this.values = values;
    }

    /**
     * �l�W�� ��ݒ肵�܂��B
     * 
     * @param values �l�W��
     */
    public void setValues(Integer[] values) {
        int end = values.length;
        int[] array = new int[end];
        for (int i = 0; i < end; i++) {
            array[i] = values[i].intValue();
        }
        setValues(array);
    }

    /**
     * �l�W�� ��ݒ肵�܂��B
     * 
     * @param values �l�W��
     */
    public void setValues(List values) {
        int end = values.size();
        int[] array = new int[end];
        for (int i = 0; i < end; i++) {
            array[i] = Integer.parseInt(String.valueOf(values.get(i)));
        }
        setValues(array);
    }

    /**
     * ��I�����̃C���f�b�N�X��Ԃ��܂��B
     * 
     * @return ��I�����̃C���f�b�N�X
     */
    public int getNoSelectIndex() {
        return noSelectIndex;
    }

    /**
     * ��I�����̃C���f�b�N�X��ݒ肵�܂��B
     * 
     * @param noSelectIndex ��I�����̃C���f�b�N�X
     */
    public void setNoSelectIndex(int noSelectIndex) {
        this.noSelectIndex = noSelectIndex;
    }

    public int getSelectedIndex() {
        int index = super.getSelectedIndex();

        int[] vals = getValues();
        if (vals != null) {
            if ((index >= 0) && (index < getValues().length)) {
                return getValues()[index];
            }
        }
        return getNoSelectIndex();
    }

    public void itemStateChanged(ItemEvent e) {
        int index = radioButtons.indexOf(e.getSource());
        if ((index < 0) || (index >= getValues().length)) {
            return;
        }
        if (getValues()[index] == getSelectedIndex()) {
            fireValueChanged(new ListSelectionEvent(this, index, index, false));
        }
    }

    public JRadioButton getButton(int index) {
        return super.getButton(toNaturalIndex(index));
    }

    public void setSelectedIndex(int selectedIndex) {
        if (selectedIndex == getNoSelectIndex()) {
            selectedIndex = -1;
        } else {
            selectedIndex = toNaturalIndex(selectedIndex);
        }
        super.setSelectedIndex(selectedIndex);
    }

    /**
     * �l����C���f�b�N�X��Ԃ��܂��B
     * 
     * @param value ��r�l
     * @return �C���f�b�N�X
     */
    protected int toNaturalIndex(int value) {
        int[] vals = getValues();
        if (vals != null) {
            int end = vals.length;
            for (int i = 0; i < end; i++) {
                if (vals[i] == value) {
                    return i;
                }
            }
        }
        return -1;
    }
}
