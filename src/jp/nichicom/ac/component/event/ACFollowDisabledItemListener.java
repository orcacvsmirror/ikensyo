package jp.nichicom.ac.component.event;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;

/**
 * �C�x���g�������̑I����Ԃɒǐ����Ċ֘A�R���|�[�l���g�̗L����Ԃ�ύX����A�C�e�����X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ItemListener
 */

public class ACFollowDisabledItemListener implements ItemListener {
    private JComponent[] disableTargets;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param disableTargets �I�������������Ƃ��ɖ����ɂ���R���|�[�l���g
     */
    public ACFollowDisabledItemListener(JComponent[] disableTargets) {
        this.disableTargets = disableTargets;
    }

    public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED) {
            select = true;
        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            select = false;
        } else {
            return;
        }
        if (disableTargets != null) {
            int end = disableTargets.length;
            for (int i = 0; i < end; i++) {
                disableTargets[i].setEnabled(select);
            }
        }
    }

}
