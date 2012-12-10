package jp.nichicom.ac.lang;

import java.util.Calendar;
import java.util.Date;

import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * 型変換関連の汎用メソッドを集めたクラスです。
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
     * インスタンスを取得します。
     * 
     * @deprecated 直接staticメソッドを呼んでください。
     * @return インスタンス
     */
    public static ACCastUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACCastUtilities();
        }
        return singleton;
    }

    /**
     * boolaen型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static boolean toBoolean(Object obj) throws Exception {
        return Boolean.valueOf(toString(obj).trim()).booleanValue();
    }

    /**
     * boolaen型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static boolean toBoolean(Object obj, boolean defaultValue) {
        try {
            return toBoolean(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(Calendar obj) throws Exception {
        if (obj == null) {
            return null;
        }
        return obj.getTime();
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(Calendar obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(Date obj) throws Exception {
        return obj;
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(Date obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型にキャストします。
     * <p>
     * 20041105 → Date(2004/11/05)
     * </p>
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(int obj) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(obj / 10000, obj / 100 % 100 - 1, obj % 100);
        return cal.getTime();
    }

    /**
     * Date型にキャストします。
     * <p>
     * 20041105 → Date(2004/11/05)
     * </p>
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(int obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(Object obj) throws Exception {
        if (obj instanceof Date) {
            return toDate((Date) obj);
        }
        return VRDateParser.parse(toString(obj));
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(Object obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(String obj) throws Exception {
        return VRDateParser.parse(obj);
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Date toDate(String obj, Date defaultValue) {
        try {
            return toDate(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * double型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static double toDouble(Object obj) throws Exception {
        return Double.parseDouble(toString(obj).trim());
    }

    /**
     * double型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static double toDouble(Object obj, double defaultValue) {
        try {
            return toDouble(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * float型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static float toFloat(Object obj) throws Exception {
        return Float.parseFloat(toString(obj).trim());
    }

    /**
     * float型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static float toFloat(Object obj, float defaultValue) {
        try {
            return toFloat(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型のint表現にキャストします。
     * <p>
     * Date(2004/11/05) → 20041105
     * </p>
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static int toInt(Date obj) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(obj);
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1)
                * 100 + cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Date型のint表現にキャストします。
     * <p>
     * Date(2004/11/05) → 20041105
     * </p>
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static int toInt(Date obj, int defaultValue) {
        try {
            return toInt(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * int型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static int toInt(Object obj) throws Exception {
        return Integer.parseInt(toString(obj).trim());
    }

    /**
     * int型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static int toInt(Object obj, int defaultValue) {
        try {
            return toInt(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * Date型のInteger表現にキャストします。
     * <p>
     * Date(2004/11/05) → 20041105
     * </p>
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Integer toInteger(Date obj) throws Exception {
        return new Integer(toInt(obj));
    }

    /**
     * Date型のInteger表現にキャストします。
     * <p>
     * Date(2004/11/05) → 20041105
     * </p>
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Integer toInteger(Date obj, int defaultValue) {
        return new Integer(toInt(obj, defaultValue));
    }

    /**
     * Integer型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     */
    public static Integer toInteger(int obj) {
        return new Integer(obj);
    }

    /**
     * Integer型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Integer toInteger(Object obj) throws Exception {
        return new Integer(toInt(obj));
    }

    /**
     * Integer型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Integer toInteger(Object obj, int defaultValue) {
        return new Integer(toInt(obj, defaultValue));
    }

    /**
     * Integer型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static Integer toInteger(Object obj, Integer defaultValue) {
        try {
            return toInteger(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * long型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static long toLong(Object obj) throws Exception {
        return Long.parseLong(toString(obj).trim());
    }

    /**
     * long型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static long toLong(Object obj, long defaultValue) {
        try {
            return toLong(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(double obj) throws Exception {
        return Double.toString(obj);
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(double obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(int obj) throws Exception {
        return Integer.toString(obj);
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(int obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(long obj) throws Exception {
        return Long.toString(obj);
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(long obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * String型にキャストします。
     * <p>
     * nullの場合は空文字("")を返します。
     * </p>
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(Object obj) throws Exception {
        if (obj == null) {
            return "";
        }
        return String.valueOf(obj);
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @param defaultValue キャスト失敗時に返す値
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public static String toString(Object obj, String defaultValue) {
        try {
            return toString(obj);
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    /**
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACCastUtilities() {

    }
}
