/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * VRDateParser���g�p����N�������ɂ����鐔�l�p�̗�\���N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserStatementable
 * @see VRDateParserStatementParseOption
 */
public class VRDateParserStatementNumber implements VRDateParserStatementable {
    public static final char DAY = 'd';

    public static final char ERA = 'e';

    public static final char MONTH = 'M';

    public static final char YEAR = 'y';

    public static final char HOUR = 'h';

    public static final char MINUTE = 'm';

    public static final char SECOND = 's';

    private int length; //��(0�Ȃ疳��)

    private char type; //y,M,d,e

    private static Pattern variableLegthNumPattern;

    /**
     * ���l�Ƃ��ėL���Ȍ`���𔻒f���鐳�K�\���p�^�[����Ԃ��܂��B
     * 
     * @return ���l�Ƃ��ėL���Ȍ`���𔻒f���鐳�K�\���p�^�[��
     */
    public Pattern getVariableLegthNumPattern() {
        if (variableLegthNumPattern == null) {
            //�x������
            variableLegthNumPattern = Pattern.compile("[0-9]+");
        }
        return variableLegthNumPattern;
    }

    /**
     * �R���X�g���N�^
     * 
     * @param len ������
     */
    public VRDateParserStatementNumber(char type, int len) {
        setType(type);
        setLength(len);
    }

    /**
     * ��������Ԃ��܂��B
     * 
     * @return ������
     */
    public int getLength() {
        return length;
    }

    /**
     * �`����Ԃ��܂��B
     * 
     * @return �`��
     */
    public char getType() {
        return type;
    }

    /**
     * ��������ݒ肵�܂��B
     * 
     * @param length ������
     */
    public void setLength(int length) {
        if (length <= 0) {
            return;
        }
        this.length = length;
    }

    /**
     * �`����ݒ肵�܂��B
     * 
     * @param type �`��
     */
    public void setType(char type) {
        this.type = type;
    }

    public boolean isMatched(VRDateParserStatementParseOption option) {
        String target = option.getTarget();
        int i = option.getParseBeginIndex();
        int len = target.length();

        int txtLen = this.getLength();
        if (i + txtLen > len) {
            //�����w�蕪�̒������c���Ă��Ȃ�
            return false;
        }

        Matcher matcher;
        if (txtLen > 1) {
            //�����w��
            matcher = Pattern.compile("[0-9]{1," + txtLen + "}").matcher(
                    target.substring(i, i + txtLen));
        } else {
            //��������
            matcher = getVariableLegthNumPattern().matcher(target.substring(i));
        }
        if ((!matcher.lookingAt())) {
            //�}�b�`���Ȃ�����
            return false;
        }
        String find = matcher.group();
        int findData = Integer.parseInt(find);

        switch (this.getType()) {
        case VRDateParserStatementNumber.YEAR:
            if (txtLen > 1) {
                //�����w��
                int digit = (int) Math.pow(10, txtLen);
                option.setYear(option.getNow().get(Calendar.YEAR) / digit * digit
                        + findData);
            } else {
                //��������
                option.setYear(findData);
            }
            option.setUseYear(true);
            break;
        case VRDateParserStatementNumber.MONTH:
            option.setMonth(findData);
            option.setUseMonth(true);
            break;
        case VRDateParserStatementNumber.DAY:
            option.setDay(findData);
            option.setUseDay(true);
            break;
        case VRDateParserStatementNumber.ERA:
            option.setEra(findData);
            option.setUseEra(true);
            break;
        case VRDateParserStatementNumber.HOUR:
            option.setHour(findData);
            option.setUseHour(true);
            break;
        case VRDateParserStatementNumber.MINUTE:
            option.setMinute(findData);
            option.setUseMinute(true);
            break;
        case VRDateParserStatementNumber.SECOND:
            option.setSecond(findData);
            option.setUseSecond(true);
            break;
        }

        i += find.length();

        option.setParseEndIndex(i);
        return true;
    }
}