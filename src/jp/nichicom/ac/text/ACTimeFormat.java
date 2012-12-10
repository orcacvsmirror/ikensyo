package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * �����`���ɑΉ������t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */
public class ACTimeFormat extends Format {
    /**
     * Date�^������킷��͌��ʒ萔�ł��B
     */
    public static final int VALUE_TYPE_DATE = 1;
    /**
     * �����������킷��͌��ʒ萔�ł��B
     */
    public static final int VALUE_TYPE_STRING = 0;
    private Date baseDate = new Date();
    private SimpleDateFormat formatBaseFormater;
    private String formatedFormat;
    private String pargedFormat;
    private SimpleDateFormat parseBaseFormater;
    private int parsedValueType = ACTimeFormat.VALUE_TYPE_DATE;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACTimeFormat() {
        this("HH:mm", "HH:mm");
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param formatedFormat �o�̓t�H�[�}�b�g
     */
    public ACTimeFormat(String formatedFormat) {
        this(formatedFormat, "HH:mm");
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param formatedFormat �o�̓t�H�[�}�b�g
     * @param parsedFormat �o�̓t�H�[�}�b�g
     */
    public ACTimeFormat(String formatedFormat, String parsedFormat) {
        super();
        setFormatedFormat(formatedFormat);
        setPargedFormat(parsedFormat);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (formatBaseFormater != null) {
            Date target = null;
            if (obj instanceof Calendar) {
                target = ((Calendar) obj).getTime();
            } else if (obj instanceof Date) {
                target = (Date) obj;
            } else if(obj!=null){
                try {
                    target = toDate(String.valueOf(obj));
                } catch (ParseException e) {
                    // �������s
                    return toAppendTo;
                }
            }
            if (target == null) {
                return toAppendTo;
            }
            return formatBaseFormater.format(target, toAppendTo, pos);
        }
        return toAppendTo;
    }

    /**
     * ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t
     */
    public Date getBaseDate() {
        return baseDate;
    }

    /**
     * �o�͏��� ��Ԃ��܂��B
     * 
     * @return �o�͏���
     */
    public String getFormatedFormat() {
        return formatedFormat;
    }

    /**
     * ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏��� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏���
     */
    public String getPargedFormat() {
        return pargedFormat;
    }

    /**
     * ��͌��ʂ̌^ ��Ԃ��܂��B
     * <p>
     * VALUE_TYPE_STRING : ������<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @return ��͌��ʂ̌^
     */
    public int getParsedValueType() {
        return parsedValueType;
    }

    public Object parseObject(String source, ParsePosition pos) {
        
        Date target;
        try {
            target = toDate(source);
        } catch (ParseException e) {
            return null;
        }
        if (target != null) {
            pos.setIndex(source.length());
        }else{
            //��͎��s�ł��󕶎��Ȃ�G���[�ɂ��Ȃ�
            pos.setIndex(1);
        }

        switch (getParsedValueType()) {
        case ACTimeFormat.VALUE_TYPE_DATE:
            return target;
        case ACTimeFormat.VALUE_TYPE_STRING:
            return parseBaseFormater.format(target);
        }
        return null;
    }

    /**
     * ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t ��ݒ肵�܂��B
     * 
     * @param baseDate ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * �o�͏��� ��ݒ肵�܂��B
     * 
     * @param formatedFormat �o�͏���
     */
    public void setFormatedFormat(String formatedFormat) {
        this.formatedFormat = formatedFormat;
        formatBaseFormater = new SimpleDateFormat(formatedFormat);
    }

    /**
     * ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏��� ��ݒ肵�܂��B
     * 
     * @param pargedFormat ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏���
     */
    public void setPargedFormat(String pargedFormat) {
        this.pargedFormat = pargedFormat;
        parseBaseFormater = new SimpleDateFormat(pargedFormat);
    }

    /**
     * ��͌��ʂ̌^ ��ݒ肵�܂��B
     * <p>
     * VALUE_TYPE_STRING : ������<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @param parsedValueType ��͌��ʂ̌^
     */
    public void setParsedValueType(int parsedValueType) {
        this.parsedValueType = parsedValueType;
    }

    /**
     * �����`���̕������Date�^�ɕϊ����܂��B
     * 
     * @param source �ϊ���
     * @return �ϊ�����
     * @throws ParseException ������O
     */
    protected Date toDate(String source) throws ParseException {
        if (source == null) {
            return null;
        }
        int sourcceLength = source.length();
        if (sourcceLength == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();

        // "��"��":"�ɒu�����A�Z�p���[�^�𓝈ꂷ��
        source = source.replaceAll("��", ":");

        if (source.charAt(0) == ':') {
            // �擪��������؂�Ŏn�܂遨���̂�

            // "��"���������Đ��l�̂�
            source = source.replaceAll("��", "");
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(1)));
            return cal.getTime();
        }

        int minuteUnitPos = source.indexOf("��");
        if (minuteUnitPos == 0) {
            // �擪�ɕ��P�ʂ͉��ߕs�\
            throw new ParseException(source, 0);
        }
        boolean useMinuteUnit = minuteUnitPos > 0;

        int hourUnitPos = source.indexOf(":");
        if (hourUnitPos == 0) {
            // �擪�Ɏ��P�ʂ͉��ߕs�\
            throw new ParseException(source, 0);
        }
        boolean useHourUnit = hourUnitPos > 0;

        if (useHourUnit) {
            // h��*
            // �����P�ʂ̊֌W���t�̏ꍇ�A��͗�O�ƂȂ�
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(source.substring(0,
                    hourUnitPos)));
            if (useMinuteUnit) {
                // h��m��
                cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(
                        hourUnitPos + 1, minuteUnitPos)));
            } else if (hourUnitPos + 1 < source.length()) {
                // h��m
                cal.set(Calendar.MINUTE, Integer.parseInt(source
                        .substring(hourUnitPos + 1)));
            } else {
                cal.set(Calendar.MINUTE, 0);
            }
            return cal.getTime();
        }
        if (useMinuteUnit) {
            // m��
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(0,
                    minuteUnitPos)));
            return cal.getTime();
        }

        // �����P�ʂȂ�
        switch (source.length()) {
        case 1:
        case 2: {
            // h
            int h = Integer.parseInt(source);
            if (h > 23) {
                throw new ParseException(source, 0);
            }
            cal.set(Calendar.HOUR_OF_DAY, h);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        case 3:
            // hmm
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(source.substring(0, 1)));
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(1)));
            return cal.getTime();
        case 4:
            // hhmm
            int h = Integer.parseInt(source.substring(0, 2));
            if (h > 23) {
                throw new ParseException(source, 0);
            }
            cal.set(Calendar.HOUR_OF_DAY, h);
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(2)));
            return cal.getTime();
        }
        return null;
    }

}
