package jp.nichicom.ac.util.event;

import java.util.EventListener;

/**
 * �J�����_�[�`���� �X�V�C�x���g���X�i�[�C���^�t�F�[�X�ł��B
 * <p>
 * �J�����_�[�R���|�[�l���g���`�悳��鎞�ɌĂяo�����C�x���g���󂯎�郊�X�i�[�C���^�[�t�F�[�X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public interface ACCalendarDrawListener extends EventListener {

    /**
     * �J�����_�[�R���|�[�l���g���`�悳��鎞�ɌĂяo�����C�x���g�ł��B
     * 
     * @param e �J�����_�[�`��C�x���g���
     */
    public void calendarDrawing(ACCalendarDrawEvent e);

}
