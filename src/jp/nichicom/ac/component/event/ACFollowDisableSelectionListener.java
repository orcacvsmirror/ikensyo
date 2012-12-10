package jp.nichicom.ac.component.event;

import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * �C�x���g�������̑I����Ԃɒǐ����Ċ֘A�R���|�[�l���g�̗L����Ԃ�ύX���郊�X�g�I�����X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ListSelectionListener
 */
public class ACFollowDisableSelectionListener implements ListSelectionListener {
    private JComponent[] disableTargets;
    private int enabledTriggerIndex;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param disableTargets �C���f�b�N�XenabledTriggerIndex�Ԃ�I�������Ƃ��ɖ����ɂ���R���|�[�l���g
     */
    public ACFollowDisableSelectionListener(JComponent[] disableTargets) {
        this.disableTargets = disableTargets;
        enabledTriggerIndex = 0;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param disableTargets �C���f�b�N�XenabledTriggerIndex�Ԃ�I�������Ƃ��ɗL���ɂ���R���|�[�l���g
     * @param enabledTriggerIndex �L���g���K�ƂȂ�C���f�b�N�X
     */
    public ACFollowDisableSelectionListener(JComponent[] disableTargets,
            int enabledTriggerIndex) {
        this.disableTargets = disableTargets;
        this.enabledTriggerIndex = enabledTriggerIndex;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        if (disableTargets != null) {
            boolean enabled = e.getFirstIndex() == enabledTriggerIndex;
            int end = disableTargets.length;
            for (int i = 0; i < end; i++) {
                disableTargets[i].setEnabled(enabled);
            }
        }
    }

}
