package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * ������n���ꂽ�ꍇ�ɁA���ݓ����Ɣ�r�����N��ɕϊ�����t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACNowAgeFormat extends Format {
    private Date baseDate;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACNowAgeFormat() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param baseDate ���ݓ���������킷��N����
     */
    public ACNowAgeFormat(Date baseDate) {
        super();
        setBaseDate(baseDate);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {

        Calendar target;
        if (obj instanceof Date) {
            target = Calendar.getInstance();
            target.setTime((Date) obj);
        } else if (obj instanceof Calendar) {
            target = ((Calendar) obj);
        } else {
            return toAppendTo;
        }

        Calendar now = Calendar.getInstance();
        if (getBaseDate() != null) {
            now.setTime(getBaseDate());
        }
        now.setLenient(true);
        now.add(Calendar.YEAR, 10);
        now.add(Calendar.DAY_OF_YEAR, -target.get(Calendar.DAY_OF_YEAR) + 1);
        now.add(Calendar.YEAR, -target.get(Calendar.YEAR));

        toAppendTo.append(now.get(Calendar.YEAR) - 10);
        return toAppendTo;
    }

    /**
     * ���ݓ���������킷��N���� ��Ԃ��܂��B
     * <p>
     * null�Ȃ�΃��A���^�C���ɍX�V����錻�ݓ����ƂȂ�܂��B
     * </p>
     * 
     * @return ���ݓ���������킷��N����
     */
    public Date getBaseDate() {
        return baseDate;
    }

    public Object parseObject(String source, ParsePosition pos) {
        Calendar now = Calendar.getInstance();
        if (getBaseDate() != null) {
            now.setTime(getBaseDate());
        }
        now.add(Calendar.YEAR, -Integer.valueOf(source).intValue());
        return now.getTime();
    }

    /**
     * ���ݓ���������킷��N���� ��ݒ肵�܂��B
     * <p>
     * null�Ȃ�΃��A���^�C���ɍX�V����錻�ݓ����ƂȂ�܂��B
     * </p>
     * 
     * @param baseDate ���ݓ���������킷��N����
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }
}
