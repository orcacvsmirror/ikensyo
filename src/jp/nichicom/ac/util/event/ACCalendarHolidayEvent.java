package jp.nichicom.ac.util.event;

import java.util.Calendar;
import java.util.EventObject;

/**
 * �J�����_�[�j�����擾�C�x���g�ł��B
 * <p>
 * �J�����_�[�R���|�[�l���g���j�������擾�������ɌĂяo�����C�x���g�����i�[����N���X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendarHolidayEvent extends EventObject {

    private Calendar calendar;
    private String holidayText;
    private boolean holiday;

    /**
     * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
     * 
     * @param source �C�x���g������
     * @param calendar �j�������擾������t
     * @param holidayText �j������
     */
    public ACCalendarHolidayEvent(Object source, Calendar calendar,
            String holidayText) {
        super(source);
        setCalendar(calendar);
        setHolidayText(holidayText);
        setHoliday(false);
    }

    /**
     * �j�������擾������t��Ԃ��܂��B
     * 
     * @return �j�������擾������t
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * �j�������擾������t��ݒ肵�܂��B
     * 
     * @param calendar �j�������擾������t
     */
    public void setCalendar(Calendar calendar) {
        if (calendar == null) {
            return;
        }
        this.calendar = calendar;
    }

    /**
     * �j�����̂�Ԃ��܂��B
     * 
     * @return �j������
     */
    public String getHolidayText() {
        return holidayText;
    }

    /**
     * �j�����̂�ݒ肵�܂��B
     * 
     * @param holidayText �j������
     */
    public void setHolidayText(String holidayText) {
        if (holidayText == null) {
            return;
        }
        this.holidayText = holidayText;
    }

    /**
     * �j���ł��邩��Ԃ��܂��B
     * 
     * @return �j���̏ꍇ��true
     */
    public boolean isHoliday() {
        return holiday;
    }

    /**
     * �j���ł��邩��ݒ肵�܂��B
     * 
     * @param holiday �j���̏ꍇ��true
     */
    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

}
