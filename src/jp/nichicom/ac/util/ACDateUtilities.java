package jp.nichicom.ac.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.text.parsers.VRDateParserHolyday;

/**
 * ���t�֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACDateUtilities {
    /**
     * ����1�̊J�n�Ȍ�Ɗ���2�̏I���ȑO���d�����邱�Ƃ�����킷���ԏd���萔�ł��B
     */
    public static final int DUPLICATE_FIRST_BEGIN_AND_SECOND_END = 2;

    /**
     * ����1�̏I���ȑO�Ɗ���2�̊J�n�Ȍオ�d�����邱�Ƃ�����킷���ԏd���萔�ł��B
     */
    public static final int DUPLICATE_FIRST_END_AND_SECOND_BEGIN = 1;

    /**
     * ����1�Ɗ���2���S���������Ƃ�����킷���ԏd���萔�ł��B
     */
    public static final int DUPLICATE_FIRST_EQUALS_SECOND = 5;
    /**
     * ����1������2���܂��邱�Ƃ�����킷���ԏd���萔�ł��B
     */
    public static final int DUPLICATE_FIRST_INCLUDE_SECOND = 3;
    /**
     * �d������������킷���ԏd���萔�ł��B
     */
    public static final int DUPLICATE_NONE = 0;
    /**
     * ����1������2���܂��邱�Ƃ�����킷���ԏd���萔�ł��B
     */
    public static final int DUPLICATE_SECOND_INCLUDE_FIRST = 4;

    private final static String[] fullWeeks = { "���j��", "���j��", "�Ηj��", "���j��", "�ؗj��", "���j��",
            "�y�j��" };

    private final static String[] shortWeeks = { "��", "��", "��", "��", "��", "��", "�y" };

    private static ACDateUtilities singleton;

    /**
     * �w��������Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z����
     * @return ���Z����
     */
    public static Date addDay(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.DAY_OF_MONTH, count);
        return getCalX().getTime();
    }

    /**
     * �w�莞�Ԑ����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z���Ԑ�
     * @return ���Z����
     */
    public static Date addHour(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.HOUR_OF_DAY, count);
        return getCalX().getTime();
    }

    /**
     * �w�蕪�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z����
     * @return ���Z����
     */
    public static Date addMinute(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.MINUTE, count);
        return getCalX().getTime();
    }

    /**
     * �w�茎�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z����
     * @return ���Z����
     */
    public static Date addMonth(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.MONTH, count);
        return getCalX().getTime();
    }
    /**
     * �w��b�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z�b��
     * @return ���Z����
     */
    public static Date addSecond(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.SECOND, count);
        return getCalX().getTime();
    }

    /**
     * �w��N�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z�N��
     * @return ���Z����
     */
    public static Date addYear(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.YEAR, count);
        return getCalX().getTime();
    }

    /**
     * ���t����P�ʂŔ�r���܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ��r����
     */
    public static int compareOnDay(Date x, Date y) {
        return toSimpleDifference(getDifferenceOnDay(x, y));
    }

    /**
     * ���t�����P�ʂŔ�r���܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ��r����
     */
    public static int compareOnMonth(Date x, Date y) {
        return toSimpleDifference(getDifferenceOnMonth(x, y));
    }

    /**
     * ���t��N�P�ʂŔ�r���܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ��r����
     */
    public static int compareOnYear(Date x, Date y) {
        return toSimpleDifference(getDifferenceOnYear(x, y));
    }

    /**
     * �w��N�̓��t�𐶐����܂��B
     * <p>
     * 1��1���t���Ő�������܂��B
     * </p>
     * 
     * @param year �N
     * @return ���t
     */
    public static Date createDate(int year) {
        return createDate(year, 1, 1);
    }

    /**
     * �w��N���̓��t�𐶐����܂��B
     * <p>
     * 1���t���Ő�������܂��B
     * </p>
     * 
     * @param year �N
     * @param month ��
     * @return ���t
     */
    public static Date createDate(int year, int month) {
        return createDate(year, month, 1);
    }

    /**
     * �w��N�����̓��t�𐶐����܂��B
     * <p>
     * Java�W����Month��0��1���ł����A���̈����ł�1��1���Ƃ݂Ȃ��܂��B
     * </p>
     * 
     * @param year �N
     * @param month ��
     * @param day ��
     * @return ���t
     */
    public static Date createDate(int year, int month, int day) {
        getCalX().clear();
        getCalX().set(Calendar.YEAR, year);
        getCalX().set(Calendar.MONTH, month - 1);
        getCalX().set(Calendar.DAY_OF_MONTH, day);
        return getCalX().getTime();
    }

    /**
     * �w�莞���̓��t�𐶐����܂��B
     * <p>
     * �N�����͕ۏ؂���܂���B
     * </p>
     * 
     * @param hour ��
     * @param minute ��
     * @return ���t
     */
    public static Date createTime(int hour, int minute) {
        return createTime(hour, minute, 0);
    }
    /**
     * �w�莞���̓��t�𐶐����܂��B
     * <p>
     * �N�����͕ۏ؂���܂���B
     * </p>
     * 
     * @param hour ��
     * @param minute ��
     * @param second �b
     * @return ���t
     */
    public static Date createTime(int hour, int minute, int second) {
        getCalX().clear();
        getCalX().set(Calendar.HOUR_OF_DAY, hour);
        getCalX().set(Calendar.MINUTE, minute);
        getCalX().set(Calendar.SECOND, second);
        return getCalX().getTime();
    }

    /**
     * �������̓��ɂ���Ԃ��܂��B
     * 
     * @param x ���t
     * @return �������̓��ɂ�
     */
    public static int getDayOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * �j����Ԃ��܂��B
     * <p>
     * ���j�F<code>Calendar.SUNDAY</code><br/> ���j�F<code>Calendar.MONDAY</code><br/>
     * �Ηj�F<code>Calendar.TUESDAY</code><br/> ���j�F<code>Calendar.WEDNESDAY</code><br/>
     * �ؗj�F<code>Calendar.THURSDAY</code><br/> ���j�F<code>Calendar.FRIDAY</code><br/>
     * �y�j�F<code>Calendar.SATURDAY</code><br/>
     * </p>
     * 
     * @param x ���t
     * @return �j��
     */
    public static int getDayOfWeek(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * �ȗ������̗j����Ԃ��܂��B
     * <p>
     * "���j��"��"���j��"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @return �ȗ������̗j��
     */
    public static String getDayOfWeekFull(Date x) {
        int idx = getDayOfWeek(x)-1;
        if(idx<0){
            return "";
        }
        return fullWeeks[idx];
    }

    /**
     * �ȗ��\�L�̗j����Ԃ��܂��B
     * <p>
     * "��"��"��"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @return �ȗ��\�L�̗j��
     */
    public static String getDayOfWeekShort(Date x) {
        int idx = getDayOfWeek(x)-1;
        if(idx<0){
            return "";
        }
        return shortWeeks[idx];
    }

    /**
     * ���t�̍���N�����P�ʂŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/13, 2000/10/10) �� 50203<br />
     * (2000/02/11, 2000/02/22) �� -11<br />
     * (2000/02/11, 2010/02/22) �� -100011
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ���t��
     */
    public static int getDifferenceOnDay(Date x, Date y) {
        getCalX().setTime(x);
        getCalY().setTime(y);

        int xD = getCalX().get(Calendar.YEAR) * 10000 + getCalX().get(Calendar.MONTH)
                * 100 + getCalX().get(Calendar.DAY_OF_MONTH);
        int yD = getCalY().get(Calendar.YEAR) * 10000 + getCalY().get(Calendar.MONTH)
                * 100 + getCalY().get(Calendar.DAY_OF_MONTH);

        return xD - yD;
    }

    /**
     * ���t�̍���N���P�ʂŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/23, 2000/10/12) �� 502<br />
     * (2000/02/11, 2000/02/22) �� 0<br />
     * (2000/02/11, 2010/02/22) �� -1000
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ���t��
     */
    public static int getDifferenceOnMonth(Date x, Date y) {
        getCalX().setTime(x);
        getCalY().setTime(y);

        int xD = getCalX().get(Calendar.YEAR) * 100 + getCalX().get(Calendar.MONTH);
        int yD = getCalY().get(Calendar.YEAR) * 100 + getCalY().get(Calendar.MONTH);

        return xD - yD;
    }

    /**
     * ���t�̍��������ȑ������ŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/13, 2000/10/10) �� 1890<br />
     * (2000/02/11, 2000/02/22) �� -11<br />
     * (2000/02/11, 2010/02/22) �� -3662
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ���t��
     */
    public static int getDifferenceOnTotalDay(Date x, Date y) {
        getCalX().setTime(x);
        getCalY().setTime(y);
        int xY = getCalX().get(Calendar.YEAR);
        int yY = getCalY().get(Calendar.YEAR);
        int xD = getCalX().get(Calendar.DAY_OF_YEAR);
        int yD = getCalY().get(Calendar.DAY_OF_YEAR);
        int count = 0;
        Calendar cal = Calendar.getInstance();
        for (int year = yY; year < xY; year++) {
            // ���̔N�̓��������Z
            cal.set(Calendar.YEAR, year);
            count += cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        for (int year = xY; year < yY; year++) {
            // ���̔N�̓��������Z
            cal.set(Calendar.YEAR, year);
            count -= cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        // �����̍��������Z
        count += xD - yD;
        return count;
    }

    /**
     * ���t�̍���N�P�ʂŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/23, 2000/10/12) �� 5<br />
     * (2000/02/11, 2000/02/22) �� 0<br />
     * (2000/02/11, 2010/02/22) �� -10
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ���t��
     */
    public static int getDifferenceOnYear(Date x, Date y) {
        getCalX().setTime(x);
        getCalY().setTime(y);

        int xD = getCalX().get(Calendar.YEAR);
        int yD = getCalY().get(Calendar.YEAR);

        return xD - yD;
    }

    /**
     * ���Ԃ̏d�����`�F�b�N���܂��B
     * 
     * @param span1Begin ����1�̊J�n
     * @param span1End ����1�̏I��
     * @param span2Begin ����2�̊J�n
     * @param span2End ����2�̏I��
     * @return �d����
     * @see DUPLICATE_NONE
     * @see DUPLICATE_FIRST_END_AND_SECOND_BEGIN
     * @see DUPLICATE_FIRST_BEGIN_AND_SECOND_END
     * @see DUPLICATE_FIRST_INCLUDE_SECOND
     * @see DUPLICATE_SECOND_INCLUDE_FIRST
     */
    public static int getDuplicateTermCheck(Date span1Begin, Date span1End,
            Date span2Begin, Date span2End) {
        int beginDif = getDifferenceOnDay(span1Begin, span2Begin);
        if (beginDif > 0) {
            // ����1�̊J�n������2�̊J�n
            if (getDifferenceOnDay(span1End, span2End) <= 0) {
                // ����1�̏I��������2�̏I��
                return DUPLICATE_SECOND_INCLUDE_FIRST;
            }
            if (getDifferenceOnDay(span1Begin, span2End) <= 0) {
                // ����1�̊J�n������2�̏I��
                return DUPLICATE_FIRST_BEGIN_AND_SECOND_END;
            }
        } else if (beginDif < 0) {
            // ����1�̊J�n �� ����2�̊J�n
            if (getDifferenceOnDay(span1End, span2End) >= 0) {
                // ����1�̏I��������2�̏I��
                return DUPLICATE_FIRST_INCLUDE_SECOND;
            }
            if (getDifferenceOnDay(span1End, span2Begin) >= 0) {
                // ����1�̏I��������2�̊J�n
                return DUPLICATE_FIRST_END_AND_SECOND_BEGIN;
            }
        } else {
            // ����1�̊J�n������2�̊J�n
            if (getDifferenceOnDay(span1End, span2End) > 0) {
                // ����1�̏I��������2�̏I��
                return DUPLICATE_FIRST_INCLUDE_SECOND;
            }
            if (getDifferenceOnDay(span1End, span2End) < 0) {
                // ����1�̏I��������2�̏I��
                return DUPLICATE_SECOND_INCLUDE_FIRST;
            }
            if (getDifferenceOnDay(span1End, span2End) == 0) {
                // ����1�̏I��������2�̏I��
                return DUPLICATE_FIRST_EQUALS_SECOND;
            }
        }
        return DUPLICATE_NONE;
    }

    /**
     * �A���t�@�x�b�g�ȗ��\�L�̌�����Ԃ��܂��B
     * <p>
     * "H"��"S"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �A���t�@�x�b�g�ȗ��\�L�̌���
     */
    public static String getEraAlphabet(Date x) throws Exception {
        return VRDateParser.getEra(x).getAbbreviation(1);
    }

    /**
     * �ȗ������̌�����Ԃ��܂��B
     * <p>
     * "����"��"���a"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �ȗ������̌���
     */
    public static String getEraFull(Date x) throws Exception {
        return VRDateParser.getEra(x).getAbbreviation(3);
    }

    /**
     * �ȗ��\�L�̌�����Ԃ��܂��B
     * <p>
     * "��"��"��"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �ȗ��\�L�̌���
     */
    public static String getEraShort(Date x) throws Exception {
        return VRDateParser.getEra(x).getAbbreviation(2);
    }

    /**
     * �a��N��Ԃ��܂��B
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �a��N
     */
    public static int getEraYear(Date x) throws Exception {
        return Integer.parseInt(VRDateParser.format(x, "e"));
    }

    /**
     * ���̌��̏���(1��)��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �������̓��ɂ�
     */
    public static int getFirstDayOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    /**
     * �����̎��Ԃ�Ԃ��܂��B
     * 
     * @param x ���t
     * @return �����̎���
     */
    public static int getHourOfDay(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @return �C���X�^���X
     */
    public static ACDateUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACDateUtilities();
        }
        return singleton;
    }

    /**
     * ���̌��̖�����Ԃ��܂��B
     * 
     * @param x ���t
     * @return �������̓��ɂ�
     */
    public static int getLastDayOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * �����̕���Ԃ��܂��B
     * 
     * @param x ���t
     * @return �����̕�
     */
    public static int getMinute(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.MINUTE);
    }

    /**
     * ����Ԃ��܂��B
     * <p>
     * Java��Date��1����0�ŕ\�����܂����A���̊֐��̖߂�l��1����1�Ƃ��܂��B
     * </p>
     * 
     * @param x ���t
     * @return ��
     */
    public static int getMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.MONTH) + 1;
    }

    /**
     * �����̕b��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �����̕b
     */
    public static int getSecond(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.SECOND);
    }

    /**
     * �扽�T�ڂ���Ԃ��܂��B
     * 
     * @param x ���t
     * @return �T��
     */
    public static int getWeekOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * �N��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �N
     */
    public static int getYear(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.YEAR);
    }

    /**
     * AM(�ߑO)�ł��邩��Ԃ��܂��B
     * 
     * @param x ����
     * @return AM(�ߑO��)�ł��邩
     */
    public static boolean isAM(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.AM_PM) == Calendar.AM;
    }

    /**
     * �j�Փ��ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �j�Փ��ł��邩
     */
    public static boolean isHolyday(Date x) {
        try {
            return !VRDateParser.getHolydays(x).isEmpty();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * PM(�ߌ�)�ł��邩��Ԃ��܂��B
     * 
     * @param x ����
     * @return PM(�ߌ�)�ł��邩
     */
    public static boolean isPM(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.AM_PM) == Calendar.PM;
    }

    /**
     * �y�j���ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �y�j���ł��邩
     */
    public static boolean isSaturday(Date x) {
        return getDayOfWeek(x) == Calendar.SATURDAY;
    }

    /**
     * ���j���ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return ���j���ł��邩
     */
    public static boolean isSunday(Date x) {
        return getDayOfWeek(x) == Calendar.SUNDAY;
    }

    /**
     * ���j�������͏j�Փ��ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return ���j�������͏j�Փ��ł��邩
     */
    public static boolean isSundayOrHolyday(Date x) {
        return isSunday(x) || isHolyday(x);
    }

    /**
     * �w����ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ��
     * @return �ݒ茋��
     */
    public static Date setDayOfMonth(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_MONTH, value);
        return getCalX().getTime();
    }

    /**
     * �w��j���ɐݒ肵�����t��Ԃ��܂��B
     * <p>
     * ���j�F<code>Calendar.SUNDAY</code><br/> ���j�F<code>Calendar.MONDAY</code><br/>
     * �Ηj�F<code>Calendar.TUESDAY</code><br/> ���j�F<code>Calendar.WEDNESDAY</code><br/>
     * �ؗj�F<code>Calendar.THURSDAY</code><br/> ���j�F<code>Calendar.FRIDAY</code><br/>
     * �y�j�F<code>Calendar.SATURDAY</code><br/>
     * </p>
     * 
     * @param x ���t
     * @param value �ݒ�j��
     * @return �ݒ茋��
     */
    public static Date setDayOfWeek(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_WEEK, value);
        return getCalX().getTime();
    }

    /**
     * �w�莞�Ԃɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ莞��
     * @return �ݒ茋��
     */
    public static Date setHourOfDay(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.HOUR_OF_DAY, value);
        return getCalX().getTime();
    }

    /**
     * �w�蕪�ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ蕪
     * @return �ݒ茋��
     */
    public static Date setMinute(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.MINUTE, value);
        return getCalX().getTime();
    }

    /**
     * �w�茎�ɐݒ肵�����t��Ԃ��܂��B
     * <p>
     * Java�W����Month��0��1���ł����A���̈����ł�1��1���Ƃ݂Ȃ��܂��B
     * </p>
     * 
     * @param x ���t
     * @param value �ݒ茎
     * @return �ݒ茋��
     */
    public static Date setMonth(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.MONTH, value - 1);
        return getCalX().getTime();
    }

    /**
     * �w��b�ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ�b
     * @return �ݒ茋��
     */
    public static Date setSecond(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.SECOND, value);
        return getCalX().getTime();
    }

    /**
     * �w��T�ڂɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ�T��
     * @return �ݒ茋��
     */
    public static Date setWeekOfMonth(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.WEEK_OF_MONTH, value);
        return getCalX().getTime();
    }

    /**
     * �w��N�ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ�N
     * @return �ݒ茋��
     */
    public static Date setYear(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.YEAR, value);
        return getCalX().getTime();
    }

    /**
     * ���ɂ����������̌���1���ɂ��ĕԂ��܂��B
     * <p>
     * (2005/2/8) �� 2005/2/8<br />
     * (2005/3/1) �� 2005/3/1
     * </p>
     * 
     * @param x ���t
     * @return 1���t���ɕϊ��������t
     */
    public static Date toFirstDayOfMonth(Date x) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_MONTH, getCalX()
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        return getCalX().getTime();
    }

    /**
     * ���ɂ����������̌��̖����ɂ��ĕԂ��܂��B
     * <p>
     * (2005/2/8) �� 2005/2/28<br />
     * (2005/3/1) �� 2005/3/31
     * </p>
     * 
     * @param x ���t
     * @return 1���t���ɕϊ��������t
     */
    public static Date toLastDayOfMonth(Date x) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_MONTH, getCalX()
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return getCalX().getTime();
    }


    /**
     * �����v�Z�p�̃J�����_1 ��Ԃ��܂��B
     * @return �����v�Z�p�̃J�����_1
     */
    protected static Calendar getCalX() {
        return getInstance().calX;
    }

    /**
     * �����v�Z�p�̃J�����_2 ��Ԃ��܂��B
     * @return �����v�Z�p�̃J�����_2
     */
    protected static Calendar getCalY() {
        return getInstance().calY;
    }

    /**
     * �l��0,-1,1�ɒP�������܂��B
     * 
     * @param diff �ϊ���
     * @return �P��������
     */
    protected static int toSimpleDifference(int diff) {
        if (diff == 0) {
            return 0;
        }
        if (diff < 0) {
            return -1;
        } else {
            return 1;
        }
    }

    private Calendar calX = Calendar.getInstance();

    private Calendar calY = Calendar.getInstance();

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACDateUtilities() {
    }

    /**
     * �j�Փ�����Ԃ��܂��B
     * 
     * @param x ���t
     * @return �j�Փ���
     */
    public static String getHolydayNames(Date x) {
        try {
            Iterator it = VRDateParser.getHolydays(x).iterator();
            if(it.hasNext()){
                StringBuffer sb = new StringBuffer();
                VRDateParserHolyday h = (VRDateParserHolyday)it.next();
                sb.append(h.getId());
                while(it.hasNext()){
                    h = (VRDateParserHolyday)it.next();
                    sb.append(", ");
                    sb.append(h.getId());
                }
                return sb.toString();
            }
        } catch (Exception ex) {
        }
        return "";
    }

}
