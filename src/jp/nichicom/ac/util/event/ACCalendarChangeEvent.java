package jp.nichicom.ac.util.event;

import java.util.Calendar;
import java.util.EventObject;

/**
 * �J�����_�[�I�����X�V�C�x���g�ł��B
 * <p>
 * �J�����_�[�R���|�[�l���g�����t�̑I�������X�V�������ɌĂяo�����C�x���g�����i�[����N���X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendarChangeEvent extends EventObject {

    private int month;
    private int year;
    private Calendar calendar;
    private Calendar endCalendar;

    /**
     * ���C���R���X�g���N�^ - �p�����̃R���X�g���N�^���Ăяo������A�R���|�[�l���g�̏��������s���܂��B
     * 
     * @param source �C�x���g�������I�u�W�F�N�g
     * @param year �\���N
     * @param month �\����
     * @param calendar �I���J�n��
     * @param endCalendar �I���I����
     */
    public ACCalendarChangeEvent(Object source, int year, int month,
            Calendar calendar, Calendar endCalendar) {
        super(source);
        this.month = month;
        this.year = year;
        this.calendar = calendar;
        this.endCalendar = endCalendar;
    }

    /**
     * �\������Ԃ��܂��B
     * 
     * @return �\����
     */
    public int getMonth() {
        return month;
    }

    /**
     * �\���N��Ԃ��܂��B
     * 
     * @return �\���N
     */
    public int getYear() {
        return year;
    }

    /**
     * �I���J�n����Ԃ��܂��B
     * 
     * @return �I���J�n��
     */
    public Calendar getBeginCalendar() {
        return calendar;
    }

    /**
     * �I���I������Ԃ��܂��B
     * 
     * @return �I���I����
     */
    public Calendar getEndCalendar() {
        return endCalendar;
    }

}
