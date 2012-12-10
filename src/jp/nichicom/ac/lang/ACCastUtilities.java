package jp.nichicom.ac.lang;

import java.util.Calendar;
import java.util.Date;

import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * �^�ϊ��֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACCastUtilities {
    private static ACCastUtilities singleton;

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @deprecated ����static���\�b�h���Ă�ł��������B
     * @return �C���X�^���X
     */
    public static ACCastUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACCastUtilities();
        }
        return singleton;
    }

    /**
     * boolaen�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static boolean toBoolean(Object obj) throws Exception {
        return Boolean.valueOf(toString(obj).trim()).booleanValue();
    }

    /**
     * boolaen�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static boolean toBoolean(Object obj, boolean defaultValue) {
        try {
            return toBoolean(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(Calendar obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return obj.getTime();
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(Calendar obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(Date obj) throws Exception {
        return obj;
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(Date obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * <p>
     * 20041105 �� Date(2004/11/05)
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(int obj) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(obj / 10000, obj / 100 % 100 - 1, obj % 100);
        return cal.getTime();
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * <p>
     * 20041105 �� Date(2004/11/05)
     * </p>
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(int obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(Object obj) throws Exception {
        if (obj instanceof Date) {
            return toDate((Date) obj);
        }
        return VRDateParser.parse(toString(obj));
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(Object obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(String obj) throws Exception {
        return VRDateParser.parse(obj);
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Date toDate(String obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * double�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static double toDouble(Object obj) throws Exception {
        return Double.parseDouble(toString(obj).trim());
    }

    /**
     * double�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static double toDouble(Object obj, double defaultValue) {
        try {
            return toDouble(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * float�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static float toFloat(Object obj) throws Exception {
        return Float.parseFloat(toString(obj).trim());
    }

    /**
     * float�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static float toFloat(Object obj, float defaultValue) {
        try {
            return toFloat(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^��int�\���ɃL���X�g���܂��B
     * <p>
     * Date(2004/11/05) �� 20041105
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static int toInt(Date obj) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(obj);
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1)
                * 100 + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Date�^��int�\���ɃL���X�g���܂��B
     * <p>
     * Date(2004/11/05) �� 20041105
     * </p>
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static int toInt(Date obj, int defaultValue) {
        try {
            return toInt(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * int�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static int toInt(Object obj) throws Exception {
        return Integer.parseInt(toString(obj).trim());
    }

    /**
     * int�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static int toInt(Object obj, int defaultValue) {
        try {
            return toInt(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date�^��Integer�\���ɃL���X�g���܂��B
     * <p>
     * Date(2004/11/05) �� 20041105
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Integer toInteger(Date obj) throws Exception {
        return new Integer(toInt(obj));
    }

    /**
     * Date�^��Integer�\���ɃL���X�g���܂��B
     * <p>
     * Date(2004/11/05) �� 20041105
     * </p>
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Integer toInteger(Date obj, int defaultValue) {
        return new Integer(toInt(obj, defaultValue));
    }

    /**
     * Integer�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     */
    public static Integer toInteger(int obj) {
        return new Integer(obj);
    }

    /**
     * Integer�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Integer toInteger(Object obj) throws Exception {
        return new Integer(toInt(obj));
    }

    /**
     * Integer�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Integer toInteger(Object obj, int defaultValue) {
        return new Integer(toInt(obj, defaultValue));
    }

    /**
     * Integer�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        try {
            return toInteger(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * long�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static long toLong(Object obj) throws Exception {
        return Long.parseLong(toString(obj).trim());
    }

    /**
     * long�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static long toLong(Object obj, long defaultValue) {
        try {
            return toLong(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(double obj) throws Exception {
        return Double.toString(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(double obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(int obj) throws Exception {
        return Integer.toString(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(int obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(long obj) throws Exception {
        return Long.toString(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(long obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * <p>
     * null�̏ꍇ�͋󕶎�("")��Ԃ��܂��B
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(Object obj) throws Exception {
        if (obj == null) {
            return "";
        }
        return String.valueOf(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @param defaultValue �L���X�g���s���ɕԂ��l
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public static String toString(Object obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACCastUtilities() {

    }
}
