package jp.nichicom.ac.util.event;

import java.util.EventListener;

/**
 * �J�����_�[�j�����擾�C�x���g���X�i�[�C���^�t�F�[�X�ł��B
 * <p>
 * �J�����_�[�R���|�[�l���g���j�������擾�������ɌĂяo�����C�x���g���󂯎�郊�X�i�[�C���^�[�t�F�[�X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public interface ACCalendarHolidayListener extends EventListener {

    /**
     * �J�����_�[�R���|�[�l���g���j�������擾�������ɌĂяo�����C�x���g�ł��B
     * 
     * @param e �J�����_�[�j�����擾�C�x���g���
     */
    public void ownerHoliday(ACCalendarHolidayEvent e);
}
