package jp.nichicom.ac;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;

import jp.nichicom.ac.bind.ACBindUtilities;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.sql.ACSQLUtilities;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;

/**
 * AC基盤の共通関数です。
 * <p>
 * 各機能をクラス単位に分割した中継ぎクラスです。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACCommon {
    private static ACCommon singleton;

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACCommon getInstance() {
        if (singleton == null) {
            singleton = new ACCommon();
        }
        return singleton;
    }

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ACCommon() {
    }

    /**
     * チェック項目をXML出力します。
     * 
     * @param pd 出力クラス
     * @param data データソース
     * @param key データキー
     * @param target タグ名
     * @param checkValue チェックとみなす値
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public boolean addCheck(ACChotarouXMLWriter pd, VRMap data, String key,
            String target, int checkValue) throws Exception {
        return ACChotarouXMLUtilities.addCheck(pd, data, key, target,
                checkValue);
    }

    /**
     * 指定日数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算日数
     * @return 加算結果
     */
    public Date addDay(Date x, int count) {
        return ACDateUtilities.getInstance().addDay(x, count);
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
    public void addFollowCheckNumberInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckNumberInsert(sb, map, checkNumberKey,
                followNumberKeys, addCheckValue);
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
    public void addFollowCheckNumberUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckNumberUpdate(sb, map, checkNumberKey,
                followNumberKeys, addCheckValue);
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
    public void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextInsert(sb, map, checkNumberKey,
                followTextKeys, addCheckValue);
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
    public void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextInsert(sb, map, checkNumberKey,
                followTextKeys, selectedIndex, addCheckValue);
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
    public void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextInsert(sb, map, checkNumberKey,
                followTextKeys, followNumberKeys, textToNumber, addCheckValue);
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
    public void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextUpdate(sb, map, checkNumberKey,
                followTextKeys, addCheckValue);
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
    public void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextUpdate(sb, map, checkNumberKey,
                followTextKeys, selectedIndex, addCheckValue);
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
    public void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextUpdate(sb, map, checkNumberKey,
                followTextKeys, followNumberKeys, textToNumber, addCheckValue);
    }

    /**
     * 指定時間数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算時間数
     * @return 加算結果
     */
    public Date addHourOfDay(Date x, int count) {
        return ACDateUtilities.getInstance().addHour(x, count);
    }

    /**
     * 指定分数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算分数
     * @return 加算結果
     */
    public Date addMinute(Date x, int count) {
        return ACDateUtilities.getInstance().addMinute(x, count);
    }

    /**
     * 指定月数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算月数
     * @return 加算結果
     */
    public Date addMonth(Date x, int count) {
        return ACDateUtilities.getInstance().addMonth(x, count);
    }

    /**
     * 指定秒数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算秒数
     * @return 加算結果
     */
    public Date addSecond(Date x, int count) {
        return ACDateUtilities.getInstance().addSecond(x, count);
    }

    /**
     * 指定年数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算年数
     * @return 加算結果
     */
    public Date addYear(Date x, int count) {
        return ACDateUtilities.getInstance().addYear(x, count);
    }

    /**
     * 日付を日単位で比較します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 比較結果
     */
    public int compareOnDay(Date x, Date y) {
        return ACDateUtilities.getInstance().compareOnDay(x, y);
    }

    /**
     * 日付を月単位で比較します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 比較結果
     */
    public int compareOnMonth(Date x, Date y) {
        return ACDateUtilities.getInstance().compareOnMonth(x, y);
    }

    /**
     * 日付を年単位で比較します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 比較結果
     */
    public int compareOnYear(Date x, Date y) {
        return ACDateUtilities.getInstance().compareOnYear(x, y);
    }

    /**
     * バインドパスfromKeyの値をバインドパスtoKeyの値として転記します。
     * 
     * @param array 操作対象
     * @param fromKey 複写元のバンドパス
     * @param toKey 転記先のバインドパス
     * @throws Exception 処理例外
     */
    public void copyBindPath(VRList array, String fromKey, String toKey)
            throws Exception {
        ACBindUtilities.copyBindPath(array, fromKey, toKey);
    }

    /**
     * バインドパスfromKeyの値をバインドパスtoKeyの値として転記します。
     * <p>
     * 移動後にバインドパスfromKeyのキーおよび値は除去されます。
     * </p>
     * 
     * @param array 操作対象
     * @param fromKeys 移動元のバンドパス配列
     * @param toKeys 移動先のバインドパス配列
     * @throws Exception 処理例外
     */
    public void copyBindPath(VRList array, String[] fromKeys, String[] toKeys)
            throws Exception {
        ACBindUtilities.copyBindPath(array, fromKeys, toKeys);
    }

    /**
     * 指定年の日付を生成します。
     * <p>
     * 1月1日付けで生成されます。
     * </p>
     * 
     * @param year 年
     * @return 日付
     */
    public Date createDate(int year) {
        return ACDateUtilities.getInstance().createDate(year);
    }

    /**
     * 指定年月の日付を生成します。
     * <p>
     * 1日付けで生成されます。
     * </p>
     * 
     * @param year 年
     * @param month 月
     * @return 日付
     */
    public Date createDate(int year, int month) {
        return ACDateUtilities.getInstance().createDate(year, month);
    }

    /**
     * 指定年月日の日付を生成します。
     * <p>
     * Java標準のMonthは0が1月ですが、この引数では1を1月とみなします。
     * </p>
     * 
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 日付
     */
    public Date createDate(int year, int month, int day) {
        return ACDateUtilities.getInstance().createDate(year, month, day);
    }

    /**
     * 指定時刻の日付を生成します。
     * <p>
     * 年月日は保証されません。
     * </p>
     * 
     * @param hour 時
     * @param minute 分
     * @return 日付
     */
    public Date createTime(int hour, int minute) {
        return ACDateUtilities.getInstance().createTime(hour, minute);
    }

    /**
     * 指定時刻の日付を生成します。
     * <p>
     * 年月日は保証されません。
     * </p>
     * 
     * @param hour 時
     * @param minute 分
     * @param second 秒
     * @return 日付
     */
    public Date createTime(int hour, int minute, int second) {
        return ACDateUtilities.getInstance().createTime(hour, minute, second);
    }

    /**
     * 指定フィールドと一致するインデックスデータをコンボに設定します。
     * <p>
     * 表示データがユニークキーではなく、実データと表示データが一致しないコンボのデータ割り当てに使用します。
     * </p>
     * 
     * @param array コンボに関連付けられたデータ集合
     * @param field チェックフィールド名
     * @param source 現在のデータ列
     * @param combo コンボ
     * @throws ParseException 解析例外
     * @return 該当するデータが見つかったか。
     */
    public boolean followComboIndex(VRList array, String field, VRMap source,
            JComboBox combo) throws ParseException {
        if (array != null) {
            Object nowNo = VRBindPathParser.get(field, source);
            if (!isNullText(nowNo)) {
                int end = array.getDataSize();
                for (int i = 0; i < end; i++) {
                    VRMap row = (VRMap) array.getData(i);
                    Object no = VRBindPathParser.get(field, row);
                    if (nowNo.equals(no)) {
                        if (combo.getSelectedItem() != row) {
                            combo.setSelectedItem(row);
                        }
                        return true;
                    }
                }
            }
        }
        combo.setSelectedItem(null);
        return false;
    }

    /**
     * SQLで取得したレコードの任意の列の値をVRHashMapArrayToConstKeyArrayAdapterとして返します。
     * 
     * @param dbm DMManager
     * @param field 対象のフィールド名
     * @param table 対象のテーブル名
     * @param codeField WHERE区に用いるフィールド名
     * @param code WHERE区に用いるフィールド値
     * @param orderField ソート基準となるフィールド名
     * @return VRHashMapArrayToConstKeyArrayAdapter
     * @throws SQLException SQL例外
     */
    public VRHashMapArrayToConstKeyArrayAdapter getArrayAdapter(
            ACDBManager dbm, String field, String table, String codeField,
            int code, String orderField) throws SQLException {
        return getFieldRows(dbm, field, table, codeField, code, orderField);
    }

    /**
     * 当月内の日にちを返します。
     * 
     * @param x 日付
     * @return 当月内の日にち
     */
    public int getDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().getDayOfMonth(x);
    }

    /**
     * 曜日を返します。
     * <p>
     * 日曜：<code>Calendar.SUNDAY</code></br> 月曜：<code>Calendar.MONDAY</code></br>
     * 火曜：<code>Calendar.TUESDAY</code></br> 水曜：<code>Calendar.WEDNESDAY</code></br>
     * 木曜：<code>Calendar.THURSDAY</code></br> 金曜：<code>Calendar.FRIDAY</code></br>
     * 土曜：<code>Calendar.SATURDAY</code></br>
     * </p>
     * 
     * @param x 日付
     * @return 曜日
     */
    public int getDayOfWeek(Date x) {
        return ACDateUtilities.getInstance().getDayOfWeek(x);
    }

    /**
     * 省略無しの曜日を返します。
     * <p>
     * "月曜日"や"金曜日"などです。
     * </p>
     * 
     * @param x 日付
     * @return 省略無しの曜日
     */
    public String getDayOfWeekFull(Date x) {
        return ACDateUtilities.getInstance().getDayOfWeekFull(x);
    }

    /**
     * 省略表記の曜日を返します。
     * <p>
     * "月"や"金"などです。
     * </p>
     * 
     * @param x 日付
     * @return 省略表記の曜日
     */
    public String getDayOfWeekShort(Date x) {
        return ACDateUtilities.getInstance().getDayOfWeekShort(x);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な日付文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public String getDBSafeDate(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeDate(key, source);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な数値文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public String getDBSafeNumber(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeNumber(key, source);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public String getDBSafeString(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeString(key, source);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な日時文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    public String getDBSafeTime(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeTime(key, source);
    }

    /**
     * 日付の差を日単位で返します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/13, 2000/10/10) → 50203<br />
     * (2000/02/11, 2000/02/22) → -11<br />
     * (2000/02/11, 2010/02/22) → -100011
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 月差
     */
    public int getDifferenceOnDay(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnDay(x, y);
    }

    /**
     * 日付の差を年月単位で返します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/23, 2000/10/12) → 502<br />
     * (2000/02/11, 2000/02/22) → 0<br />
     * (2000/02/11, 2010/02/22) → -1000
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 月差
     */
    public int getDifferenceOnMonth(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnMonth(x, y);
    }

    /**
     * 日付の差を純粋な総日数で返します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/13, 2000/10/10) → 1890<br />
     * (2000/02/11, 2000/02/22) → -11<br />
     * (2000/02/11, 2010/02/22) → -3662
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 日付差
     */
    public int getDifferenceOnTotalDay(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnTotalDay(x, y);
    }

    /**
     * 日付の差を年単位で返します。
     * <p>
     * 返り値：<br />
     * 日付1 < 日付2 → 0より小さい値<br />
     * 日付1 > 日付2 → 0より大きい値<br />
     * 日付1 = 日付2 → 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/23, 2000/10/12) → 5<br />
     * (2000/02/11, 2000/02/22) → 0<br />
     * (2000/02/11, 2010/02/22) → -10
     * </p>
     * 
     * @param x 日付1
     * @param y 日付2
     * @return 年差
     */
    public int getDifferenceOnYear(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnYear(x, y);
    }

    /**
     * アルファベット省略表記の元号を返します。
     * <p>
     * "H"や"S"などです。
     * </p>
     * 
     * @param x 日付
     * @throws Exception 処理例外
     * @return アルファベット省略表記の元号
     */
    public String getEraAlphabet(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraAlphabet(x);
    }

    /**
     * 省略無しの元号を返します。
     * <p>
     * "平成"や"昭和"などです。
     * </p>
     * 
     * @param x 日付
     * @throws Exception 処理例外
     * @return 省略無しの元号
     */
    public String getEraFull(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraFull(x);
    }

    /**
     * 省略表記の元号を返します。
     * <p>
     * "平"や"昭"などです。
     * </p>
     * 
     * @param x 日付
     * @throws Exception 処理例外
     * @return 省略表記の元号
     */
    public String getEraShort(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraShort(x);
    }

    /**
     * 和暦年を返します。
     * 
     * @param x 日付
     * @throws Exception 処理例外
     * @return 和暦年
     */
    public int getEraYear(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraYear(x);
    }

    /**
     * SQLで取得したレコードの任意の列の値集合を返します。
     * 
     * @param dbm DMManager
     * @param field 対象のフィールド名
     * @param table 対象のテーブル名
     * @param codeField WHERE区に用いるフィールド名
     * @param code WHERE区に用いるフィールド値
     * @param orderField ソート基準となるフィールド名
     * @throws SQLException SQL例外
     * @return 任意の列の値集合
     */
    public VRHashMapArrayToConstKeyArrayAdapter getFieldRows(
            ACDBManager dbm, String field, String table, String codeField,
            int code, String orderField) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(field);
        sb.append(" FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(codeField);
        sb.append("=");
        sb.append(code);
        if ((orderField != null) && (!"".equals(orderField))) {
            sb.append(" ORDER BY ");
            sb.append(orderField);
        }

        return new VRHashMapArrayToConstKeyArrayAdapter(dbm.executeQuery(sb
                .toString()), field);
    }

    /**
     * その月の初日(1日)を返します。
     * 
     * @param x 日付
     * @return 当月内の日にち
     */
    public int getFirstDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().getFirstDayOfMonth(x);
    }

    /**
     * その月の末日を返します。
     * 
     * @param x 日付
     * @return 当月内の日にち
     */
    public int getLastDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().getLastDayOfMonth(x);
    }

    /**
     * マスタデータを一括して取得します。
     * 
     * @param dbm DBManager
     * @param dest 結果代入先
     * @param nameField 対象のフィールド名
     * @param table 対象のテーブル名
     * @param codeField WHERE句に用いるフィールド名
     * @param orderField ORDER BY句に用いるフィールド名
     * @throws SQLException SQL例外
     * @throws ParseException 解析例外
     */
    public void getMasterTable(ACDBManager dbm, HashMap dest,
            String nameField, String table, String codeField, String orderField)
            throws SQLException, ParseException {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(" *");
        sb.append(" FROM ");
        sb.append(table);
        sb.append(" ORDER BY ");
        sb.append(orderField);

        VRList fullArray = dbm.executeQuery(sb.toString());
        int end = fullArray.size();
        if (end > 0) {
            HashMap arrayMap = new HashMap();
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) fullArray.getData(i);
                Integer code = (Integer) VRBindPathParser.get(codeField, row);
                Object obj = arrayMap.get(code);
                if (obj instanceof VRList) {
                    ((VRList) obj).addData(row);
                } else {
                    VRList array = new VRArrayList();
                    array.addData(row);
                    arrayMap.put(code, array);
                }
            }

            Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                dest.put(((Map.Entry) obj).getKey(),
                        new VRHashMapArrayToConstKeyArrayAdapter(
                                (VRList) (((Map.Entry) obj).getValue()),
                                nameField));
            }
        }
    }

    /**
     * 指定キーフィールドが一致する行番号を返します。
     * 
     * @param array データ集合
     * @param fieldName 比較フィールド名
     * @param source 比較元ソース
     * @throws ParseException 解析例外
     * @return 指定キーフィールドが一致する行番号
     */
    public int getMatchIndexFromMap(VRList array, String fieldName, VRMap source)
            throws ParseException {
        return ACBindUtilities.getMatchIndexFromMap(array, fieldName, source);
    }

    /**
     * 指定キーフィールドが一致する行番号を返します。
     * 
     * @param array データ集合
     * @param fieldName 比較フィールド名
     * @param source 比較元ソース
     * @throws ParseException 解析例外
     * @return 指定キーフィールドが一致する行番号
     */
    public int getMatchIndexFromValue(VRList array, String fieldName,
            Object source) throws ParseException {
        return ACBindUtilities.getMatchIndexFromValue(array, fieldName, source);
    }

    // /**
    // * SQLで取得したレコードの任意の列の値ComboBoxに設定します。
    // *
    // * @param combo VRComboBox
    // * @param sql String
    // * @param field String
    // */
    // public void setComboModel(JComboBox combo, String sql, String field) {
    // try {
    // NCFrameEventProcessable processer = NCFrame.getInstance()
    // .getFrameEventProcesser();
    // if (processer instanceof NCFrameEventProcessable) {
    // NCDBManagerable dbm = processer.getDBManager();
    // if (dbm instanceof NCDBManagerable) {
    // VRList tbl = (VRList) dbm.executeQuery(sql);
    // if (tbl.size() > 0) {
    // applyComboModel(combo,
    // new VRHashMapArrayToConstKeyArrayAdapter(tbl,
    // field));
    // }
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // /**
    // * SQLで取得したレコードの任意の列の値ComboBoxに設定します。
    // *
    // * @param combo 対象のコンボ
    // * @param field 対象のフィールド名
    // * @param table 対象のテーブル名
    // * @param codeField WHERE区に用いるフィールド名
    // * @param code WHERE区に用いるフィールド値
    // * @param orderField ソート基準となるフィールド名
    // * @throws Exception 処理例外
    // */
    // public void setComboModel(JComboBox combo, String field, String table,
    // String codeField, int code, String orderField) throws Exception {
    // NCFrameEventProcessable processer = NCFrame.getInstance()
    // .getFrameEventProcesser();
    // if (processer instanceof NCFrameEventProcessable) {
    // NCDBManagerable dbm = processer.getDBManager();
    // if (dbm instanceof NCDBManagerable) {
    // setComboModel(dbm, combo, field, table, codeField, code,
    // orderField);
    // }
    // }
    //
    // }
    //
    // /**
    // * SQLで取得したレコードの任意の列の値ComboBoxに設定します。
    // *
    // * @param dbm DMManager
    // * @param combo 対象のコンボ
    // * @param field 対象のフィールド名
    // * @param table 対象のテーブル名
    // * @param codeField WHERE区に用いるフィールド名
    // * @param code WHERE区に用いるフィールド値
    // * @param orderField ソート基準となるフィールド名
    // * @throws SQLException SQL例外
    // */
    // public void setComboModel(NCDBManagerable dbm, JComboBox combo,
    // String field, String table, String codeField, int code,
    // String orderField) throws SQLException {
    // VRHashMapArrayToConstKeyArrayAdapter adapter = geArrayAdapter(dbm,
    // field, table, codeField, code, orderField);
    // if ((adapter != null) && (adapter.getDataSize() > 0)) {
    // applyComboModel(combo, adapter);
    // }
    // }

    /**
     * 指定キーフィールドが一致する行マップを返します。
     * 
     * @param array データ集合
     * @param fieldName 比較フィールド名
     * @param source 比較元ソース
     * @throws ParseException 解析例外
     * @return 指定キーフィールドが一致する行マップ
     */
    public VRMap getMatchRowFromMap(VRList array, String fieldName, VRMap source)
            throws ParseException {
        return ACBindUtilities.getMatchRowFromMap(array, fieldName, source);
    }

    /**
     * 指定キーフィールドが一致する行マップを返します。
     * 
     * @param array データ集合
     * @param fieldName 比較フィールド名
     * @param source 比較元ソース
     * @throws ParseException 解析例外
     * @return 指定キーフィールドが一致する行マップ
     */
    public VRMap getMatchRowFromValue(VRList array, String fieldName,
            Object source) throws ParseException {
        return ACBindUtilities.getMatchRowFromValue(array, fieldName, source);
    }

    /**
     * 月を返します。
     * <p>
     * JavaのDateは1月を0で表現しますが、この関数の戻り値は1月を1とします。
     * </p>
     * 
     * @param x 日付
     * @return 月
     */
    public int getMonth(Date x) {
        return ACDateUtilities.getInstance().getMonth(x);
    }

    /**
     * キーと値の関係で直列化された列を持つMapからなるListに対し、キー列と値列に並列化したMapの集合を返します。
     * 
     * @param src 変換元
     * @param groupingConditionFieldNames グループ化条件列名
     * @param keyFieldName キーにあたるデータを格納する列名
     * @param valueFieldName 値にあたるデータを格納する列名
     * @param keyFieldValues キー列の値として想定される、直列化対象の列名
     * @param valueFieldDefaultValue 直列化対象の列に適用する初期値
     * @throws Exception 処理例外
     * @return 変換結果
     */
    public VRList getParalleledKeyList(VRList src,
            final String[] groupingConditionFieldNames,
            final String keyFieldName, final String valueFieldName,
            final String[] keyFieldValues, final Object valueFieldDefaultValue)
            throws Exception {
        return ACBindUtilities.getParalleledKeyList(src,
                groupingConditionFieldNames, keyFieldName, valueFieldName,
                keyFieldValues, valueFieldDefaultValue);
    }

    /**
     * キーと値の関係にある2つの列を持つMapを集めて並列化されたListに対し、キー列の値をキーに値列の値を値として直列化したMapの集合を返します。
     * 
     * @param src 変換元
     * @param groupingConditionFieldNames グループ化条件列名
     * @param keyFieldName キーにあたるデータを格納する列名
     * @param valueFieldName 値にあたるデータを格納する列名
     * @param keyFieldValues キー列の値として想定される、直列化対象の列名
     * @param valueFieldDefaultValue 直列化対象の列に適用する初期値
     * @throws Exception 処理例外
     * @return 変換結果
     */
    public VRList getSeriesedKeyList(VRList src,
            final String[] groupingConditionFieldNames,
            final String keyFieldName, final String valueFieldName,
            final String[] keyFieldValues, final Object valueFieldDefaultValue)
            throws Exception {
        return ACBindUtilities.getSeriesedKeyList(src,
                groupingConditionFieldNames, keyFieldName, valueFieldName,
                keyFieldValues, valueFieldDefaultValue);
    }

    /**
     * 年を返します。
     * 
     * @param x 日付
     * @return 年
     */
    public int getYear(Date x) {
        return ACDateUtilities.getInstance().getYear(x);
    }

    /**
     * 祝祭日であるかを返します。
     * 
     * @param x 日付
     * @return 祝祭日であるか
     */
    public boolean isHolyday(Date x) {
        return ACDateUtilities.getInstance().isHolyday(x);
    }

    /**
     * 文字列がNullまたは空文字であるかを返します。
     * 
     * @param obj 評価文字列
     * @return 文字列がNullまたは空文字であるか
     */
    public boolean isNullText(Object obj) {
        return ACTextUtilities.isNullText(obj);
    }

    /**
     * 土曜日であるかを返します。
     * 
     * @param x 日付
     * @return 土曜日であるか
     */
    public boolean isSaturday(Date x) {
        return ACDateUtilities.getInstance().isSaturday(x);
    }

    /**
     * 日曜日であるかを返します。
     * 
     * @param x 日付
     * @return 日曜日であるか
     */
    public boolean isSunday(Date x) {
        return ACDateUtilities.getInstance().isSunday(x);
    }

    /**
     * 日曜もしくは祝祭日であるかを返します。
     * 
     * @param x 日付
     * @return 日曜もしくは祝祭日であるか
     */
    public boolean isSundayOrHolyday(Date x) {
        return ACDateUtilities.getInstance().isSundayOrHolyday(x);
    }

    /**
     * 文字単位で、改行文字と折り返し文字数を基準に切り分けた配列を返します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @return 切り分けた配列
     */
    public String[] lineWrapOnChar(String text, int columns) {

        return ACTextUtilities.separateLineWrapOnChar(text, columns);
    }

    /**
     * バインドパスfromKeyの値をバインドパスtoKeyの値として移動します。
     * <p>
     * 移動後にバインドパスfromKeyのキーおよび値は除去されます。
     * </p>
     * 
     * @param array 操作対象
     * @param fromKey 移動元のバンドパス
     * @param toKey 移動先のバインドパス
     * @throws Exception 処理例外
     */
    public void moveBindPath(VRList array, String fromKey, String toKey)
            throws Exception {
        ACBindUtilities.moveBindPath(array, fromKey, toKey);
    }

    /**
     * バインドパスfromKeyの値をバインドパスtoKeyの値として移動します。
     * <p>
     * 移動後にバインドパスfromKeyのキーおよび値は除去されます。
     * </p>
     * 
     * @param array 操作対象
     * @param fromKeys 移動元のバンドパス配列
     * @param toKeys 移動先のバインドパス配列
     * @throws Exception 処理例外
     */
    public void moveBindPath(VRList array, String[] fromKeys, String[] toKeys)
            throws Exception {
        ACBindUtilities.moveBindPath(array, fromKeys, toKeys);
    }

    /**
     * PDFファイルを生成し、生成したPDFファイルを開きます。
     * 
     * @param pd 印刷データ
     * @throws Exception
     */
    public void openPDF(ACChotarouXMLWriter pd) throws Exception {
        ACChotarouXMLUtilities.openPDF(pd);
    }

    /**
     * 指定日に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定日
     * @return 設定結果
     */
    public Date setDayOfMonth(Date x, int value) {
        return ACDateUtilities.getInstance().setDayOfMonth(x, value);
    }

    /**
     * 指定時間に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定時間
     * @return 設定結果
     */
    public Date setHourOfDay(Date x, int value) {
        return ACDateUtilities.getInstance().setHourOfDay(x, value);
    }

    /**
     * 帳票定義対抗目を非表示にします。
     * 
     * @param pd 出力クラス
     * @param shape Visible制御対象
     * @throws Exception 処理例外
     */
    public void setInvisible(ACChotarouXMLWriter pd, String shape)
            throws Exception {
        ACChotarouXMLUtilities.setInvisible(pd, shape);
    }

    /**
     * DBから取得した配列(ResultSet)を、[キー:key, 値:レコード]にしたHashMapとして設定します。
     * 
     * @param src DBから取得した配列(ResultSet)
     * @param dest 設定先のMap
     * @param key キーとなるフィールド名
     * @throws Exception 処理例外
     */
    public void setMapFromArray(VRList src, VRMap dest, String key)
            throws Exception {
        ACBindUtilities.setMapFromArray(src, dest, key);
    }

    /**
     * DBから取得した配列(ResultSet)を、[キー:srcKey,
     * 値:レコードのdestKeyフィールド値]にしたHashMapとして設定します。
     * 
     * @param src DBから取得した配列(ResultSet)
     * @param dest 設定先のMap
     * @param srckey キーとなるフィールド名
     * @param destKey 抜き出すフィールド名
     * @throws Exception 処理例外
     */
    public void setMapFromArray(VRList src, VRMap dest, String srckey,
            String destKey) throws Exception {
        ACBindUtilities.setMapFromArray(src, dest, srckey, destKey);
    }

    /**
     * 指定分に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定分
     * @return 設定結果
     */
    public Date setMinute(Date x, int value) {
        return ACDateUtilities.getInstance().setMinute(x, value);
    }

    /**
     * 指定月に設定した日付を返します。
     * <p>
     * Java標準のMonthは0が1月ですが、この引数では1を1月とみなします。
     * </p>
     * 
     * @param x 日付
     * @param value 設定月
     * @return 設定結果
     */
    public Date setMonth(Date x, int value) {
        return ACDateUtilities.getInstance().setMonth(x, value);
    }

    /**
     * 指定秒に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定秒
     * @return 設定結果
     */
    public Date setSecond(Date x, int value) {
        return ACDateUtilities.getInstance().setSecond(x, value);
    }

    /**
     * 選択型項目をXML出力します。
     * 
     * @param pd 出力クラス
     * @param data データソース
     * @param key データキー
     * @param shapes Visible制御対象群
     * @param offset 値と配列添え字とのオフセット
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public boolean setSelection(ACChotarouXMLWriter pd, VRMap data, String key,
            String[] shapes, int offset) throws Exception {
        return ACChotarouXMLUtilities.setSelection(pd, data, key, shapes,
                offset);
    }

    /**
     * 選択型項目をXML出力します。
     * 
     * @param pd 出力クラス
     * @param data データソース
     * @param key データキー
     * @param shapes Visible制御対象群
     * @param offset 値と配列添え字とのオフセット
     * @param optionKey 連動して出力する文字列キー
     * @param optionTarget 連動して出力する文字列出力位置
     * @param useOptionIndex 連動して出力する文字列の出力条件となる選択番号
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public boolean setSelection(ACChotarouXMLWriter pd, VRMap data, String key,
            String[] shapes, int offset, String optionKey, String optionTarget,
            int useOptionIndex) throws Exception {
        return ACChotarouXMLUtilities.setSelection(pd, data, key, shapes,
                offset, optionKey, optionTarget, useOptionIndex);
    }

    /**
     * 文字項目をXML出力します。
     * 
     * @param pd 出力クラス
     * @param target タグ名
     * @param value 出力値
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public boolean setValue(ACChotarouXMLWriter pd, String target, Object value)
            throws Exception {
        return ACChotarouXMLUtilities.setValue(pd, target, value);
    }

    /**
     * 文字項目をXML出力します。
     * 
     * @param pd 出力クラス
     * @param data データソース
     * @param key データキー
     * @param target タグ名
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public boolean setValue(ACChotarouXMLWriter pd, VRMap data, String key,
            String target) throws Exception {
        return ACChotarouXMLUtilities.setValue(pd, data, key, target);
    }

    /**
     * 文字項目をXML出力します。
     * 
     * @param pd 出力クラス
     * @param data データソース
     * @param key データキー
     * @param target タグ名
     * @param head 文字項目値の前に連結して出力する文字列
     * @throws Exception 処理例外
     * @return 出力したか
     */
    public boolean setValue(ACChotarouXMLWriter pd, VRMap data, String key,
            String target, String head) throws Exception {
        return ACChotarouXMLUtilities.setValue(pd, data, key, target, head);
    }

    /**
     * 指定年に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定年
     * @return 設定結果
     */
    public Date setYear(Date x, int value) {
        return ACDateUtilities.getInstance().setYear(x, value);
    }

    /**
     * 例外メッセージを表示します。
     * 
     * @param ex 例外
     */
    public void showExceptionMessage(Throwable ex) {
        ACFrame.getInstance().showExceptionMessage(ex);
    }
    
    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public Date toDate(Calendar obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public Date toDate(Date obj) throws Exception {
        return ACCastUtilities.toDate(obj);
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
    public Date toDate(int obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public Date toDate(Object obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public Date toDate(String obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * double型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public double toDouble(Object obj) throws Exception {
        return ACCastUtilities.toDouble(obj);
    }

    /**
     * 日にちだけをその月の1日にして返します。
     * <p>
     * (2005/2/8) → 2005/2/8<br />
     * (2005/3/1) → 2005/3/1
     * </p>
     * 
     * @param x 日付
     * @return 1日付けに変換した日付
     */
    public Date toFirstDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().toFirstDayOfMonth(x);
    }

    /**
     * float型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public float toFloat(Object obj) throws Exception {
        return ACCastUtilities.toFloat(obj);
    }

    /**
     * /** Date型にキャストします。
     * <p>
     * Date(2004/11/05) → 20041105
     * </p>
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public int toInt(Date obj) throws Exception {
        return ACCastUtilities.toInt(obj);
    }

    /**
     * int型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public int toInt(Object obj) throws Exception {
        return ACCastUtilities.toInt(obj);
    }

    /**
     * 日にちだけをその月の末日にして返します。
     * <p>
     * (2005/2/8) → 2005/2/28<br />
     * (2005/3/1) → 2005/3/31
     * </p>
     * 
     * @param x 日付
     * @return 1日付けに変換した日付
     */
    public Date toLastDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().toLastDayOfMonth(x);
    }

    /**
     * long型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public long toLong(Object obj) throws Exception {
        return ACCastUtilities.toLong(obj);
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public String toString(double obj) throws Exception {
        return ACCastUtilities.toString(obj);
    }

    /**
     * String型にキャストします。
     * 
     * @param obj 変換元
     * @return 変換結果
     * @throws Exception 処理例外
     */
    public String toString(int obj) throws Exception {
        return ACCastUtilities.toString(obj);
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
    public String toString(Object obj) throws Exception {
        return ACCastUtilities.toString(obj);
    }
}
