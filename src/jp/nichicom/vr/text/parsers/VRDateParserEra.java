/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * VRDateParser���g�p���錳���N���X�ł��B
 * <p>
 * �����͈̔͂╶��"g"�̌��œ��肳��闪�̂��`�ł��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see Locale
 */
public class VRDateParserEra {
    private static final String NAME_ABBREVIATION = "ggg";

    private static final int NAME_ABBREVIATION_TYPE = NAME_ABBREVIATION
            .length();

    private HashMap abbreviation;

    private Calendar begin;

    private Calendar end;

    /**
     * �R���X�g���N�^
     * @param locale ���P�[��
     */
    public VRDateParserEra(Locale locale) {
        this.begin = Calendar.getInstance(locale);
        this.begin.set(1, 0, 1, 0, 0, 0);
        this.begin.set(Calendar.MILLISECOND, 0);
        this.end = Calendar.getInstance(locale);
        this.end.set(9999, 11, 31, 23, 59, 59);
        this.end.set(Calendar.MILLISECOND, 0);
        this.abbreviation = new HashMap();
    }

    /**
     * ���̂�Ԃ��܂��B
     * 
     * @param length �Ή�����������
     */
    public String getAbbreviation(int length) {
        return (String) abbreviation.get(new Integer(length));
    }

    /**
     * �J�n����Ԃ��܂��B
     * 
     * @return �J�n��
     */
    public Calendar getBegin() {
        return begin;
    }

    /**
     * �I������Ԃ��܂��B
     * 
     * @return �I����
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * ���̂�ݒ肵�܂��B
     * 
     * @param type �Ή�����
     * @param value ����
     */
    public void setAbbreviation(String type, String value) {
        abbreviation.put(new Integer(type.length()), value);
    }

    /**
     * �J�n����ݒ肵�܂��B
     * 
     * @param begin �J�n��
     */
    public void setBegin(Calendar begin) {
        this.begin.set(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH),
                begin.get(Calendar.DATE), 0, 0, 0);
    }

    /**
     * �I������ݒ肵�܂��B
     * 
     * @param end �I����
     */
    public void setEnd(Calendar end) {
        this.end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end
                .get(Calendar.DATE), 23, 59, 59);
    }

}