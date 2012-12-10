/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * VRDateParserHolydays���g�p����j�Փ�������`�N���X�ł��B
 * <p>
 * �j�Փ��̏��������ԂŒ�`���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 * @see VRDateParserHolydays
 * @see VRDateParserHolyday
 */
public class VRDateParserHolydayTerm implements VRDateParserHolydayCalculatable {
    private static final ArrayList EMPTY_ARRAY = new ArrayList();

    private Calendar begin;

    private Calendar end;

    private VRDateParserHolyday holyday;

    /**
     * �R���X�g���N�^
     */
    public VRDateParserHolydayTerm() {
        begin = Calendar.getInstance();
        begin.setTimeInMillis(Long.MIN_VALUE);
        end = Calendar.getInstance();
        end.setTimeInMillis(Long.MAX_VALUE);
        holyday = new VRDateParserHolyday();
    }

    /**
     * �L�������̊J�n�� ��Ԃ��܂��B
     * 
     * @return �L�������̊J�n��
     */
    public Calendar getBegin() {
        return begin;
    }

    /**
     * �L�������̏I���� ��Ԃ��܂��B
     * 
     * @return �L�������̏I����
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * �j�Փ���` ��Ԃ��܂��B
     * 
     * @return �j�Փ���`
     */
    public VRDateParserHolyday getHolyday() {
        return holyday;
    }

    /**
     * �L�������̊J�n�� ��ݒ肵�܂��B
     * 
     * @param begin �L�������̊J�n��
     */
    public void setBegin(Calendar begin) {
        this.begin = begin;
    }

    /**
     * �L�������̏I���� ��ݒ肵�܂��B
     * 
     * @param end �L�������̏I����
     */
    public void setEnd(Calendar end) {
        this.end = end;
    }

    /**
     * �j�Փ���` ��ݒ肵�܂��B
     * 
     * @param holyday �j�Փ���`
     */
    public void setHolyday(VRDateParserHolyday holyday) {
        this.holyday = holyday;
    }

    public void stockHolyday(Calendar cal, List holydays, Object parameter) {
        if (cal != null) {
            //�������ł���ΗL��
            if (cal.before(getEnd()) && cal.after(getBegin())) {
                holydays.add(holyday);
            }
        }
    }
}