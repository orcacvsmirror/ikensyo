package jp.nichicom.ac.util.event;

import java.util.EventListener;

/**
 * �J�����_�[�I����� �X�V�C�x���g���X�i�[�C���^�t�F�[�X�ł��B
 * <p>
 * �J�����_�[�R���|�[�l���g�����t�̑I�������X�V�������ɌĂяo�����C�x���g���󂯎�郊�X�i�[�C���^�[�t�F�[�X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public interface ACCalendarChangeListener extends EventListener {

    /**
     * �J�����_�[�R���|�[�l���g�����t�̑I�������X�V�������ɌĂяo�����C�x���g�ł��B
     * 
     * @param e �J�����_�[�I�����X�V�C�x���g���
     */
    public void selectedChanged(ACCalendarChangeEvent e);

    /**
     * �J�����_�[�R���|�[�l���g���\���Ώۂ̌����X�V�������ɌĂяo�����C�x���g�ł��B
     * 
     * @param e �J�����_�[�\���Ώی��X�V�C�x���g���
     */
    public void displayMonthChanged(ACCalendarChangeEvent e);
}
