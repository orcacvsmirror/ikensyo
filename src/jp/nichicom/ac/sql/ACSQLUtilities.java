package jp.nichicom.ac.sql;

import java.text.ParseException;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRMap;

/**
 * SQL関連の汎用メソッドを集めたクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public class ACSQLUtilities {
    private static ACSQLUtilities singleton;

    /**
     * インスタンスを取得します。
     * 
     * @deprecated 直接staticメソッドを呼んでください。
     * @return インスタンス
     */
    public static ACSQLUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACSQLUtilities();
        }
        return singleton;
    }

    /**
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACSQLUtilities() {

    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followNumberKeys 数値型チェック連動テキスト項目キー集合
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckNumberInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followNumberKeys.length;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(getDBSafeNumber(followNumberKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",0");
            }
        }
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followNumberKeys 数値型チェック連動テキスト項目キー集合
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckNumberUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(checkNumberKey);
            sb.append(" = ");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followNumberKeys.length;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followNumberKeys[i]);
                sb.append(" = ");
                sb.append(getDBSafeNumber(followNumberKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followNumberKeys[i]);
                sb.append(" = 0");
            }
        }
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followTextKeys 文字列型チェック連動テキスト項目キー集合
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        addFollowCheckTextInsert(sb, map, checkNumberKey, followTextKeys, 1,
                addCheckValue);
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followTextKeys 文字列型チェック連動テキスト項目キー集合
     * @param selectedIndex 選択扱いとみなすチェック項目値
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followTextKeys.length;
        if ((obj instanceof Integer)
                && (((Integer) obj).intValue() == selectedIndex)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(getDBSafeString(followTextKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",''");
            }
        }
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followTextKeys 文字列型チェック連動テキスト項目キー集合
     * @param followNumberKeys 数値型チェック連動テキスト項目キー集合
     * @param textToNumber テキスト出力が先か
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            if (textToNumber) {
                // 文字出力が先
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
            } else {
                // 数値出力が先
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
            }
        } else {
            if (textToNumber) {
                // 文字出力が先
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",''");
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",0");
                }
            } else {
                // 数値出力が先
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",0");
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",''");
                }
            }
        }
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followTextKeys 文字列型チェック連動テキスト項目キー集合
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        addFollowCheckTextUpdate(sb, map, checkNumberKey, followTextKeys, 1,
                addCheckValue);
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followTextKeys 文字列型チェック連動テキスト項目キー集合
     * @param selectedIndex 選択扱いとみなすチェック項目値
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(checkNumberKey);
            sb.append(" = ");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followTextKeys.length;
        if ((obj instanceof Integer)
                && (((Integer) obj).intValue() == selectedIndex)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followTextKeys[i]);
                sb.append(" = ");
                sb.append(getDBSafeString(followTextKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followTextKeys[i]);
                sb.append(" = ");
                sb.append("''");
            }
        }
    }

    /**
     * チェック値と連動して出力有無を切り替えて出力します。
     * 
     * @param sb 追加先バッファ
     * @param map 値取得元
     * @param checkNumberKey 数値型チェック項目キー
     * @param followTextKeys 文字列型チェック連動テキスト項目キー集合
     * @param followNumberKeys 数値型チェック連動テキスト項目キー集合
     * @param textToNumber テキスト出力が先か
     * @param addCheckValue チェック項目も出力するか
     * @throws ParseException 解析例外
     */
    public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(checkNumberKey);
            sb.append(" = ");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            if (textToNumber) {
                // 文字出力が先
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
            } else {
                // 数値出力が先
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
            }
        } else {
            if (textToNumber) {
                // 文字出力が先
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append("''");
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = 0");
                }
            } else {
                // 数値出力が先
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = 0");
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append("''");
                }
            }
        }
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な日付文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public static String getDBSafeDate(String key, VRBindSource source)
            throws ParseException {
        return ACConstants.FORMAT_SQL_FULL_YMD.format(VRBindPathParser.get(
                key, source));
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な数値文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public static String getDBSafeNumber(String key, VRBindSource source)
            throws ParseException {
        Object obj = VRBindPathParser.get(key, source);
        if (obj == null) {
            return "NULL";
        }
        return String.valueOf(obj);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public static String getDBSafeString(String key, VRBindSource source)
            throws ParseException {
        return ACConstants.FORMAT_SQL_STRING.format(VRBindPathParser.get(
                key, source));
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な日時文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public static String getDBSafeTime(String key, VRBindSource source)
            throws ParseException {
        return ACConstants.FORMAT_SQL_FULL_YMD_HMS.format(VRBindPathParser
                .get(key, source));
    }
}
