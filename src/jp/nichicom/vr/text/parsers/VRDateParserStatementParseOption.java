/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * VRDateParserが使用する構文解析状況クラスです。
 * <p>
 * VRDateParserStatementableを実装した構文解析クラスに解析を実行させる際の条件および結果を格納します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/02/14
 */
public class VRDateParserStatementParseOption {
    private int day = -1;
    private int era = 0;
    private ArrayList eras;
    private int hour = -1;
    private Locale locale;
    private VRDateParserEra machedEra = null;
    private int minute = -1;
    private int month = -1;
    private Calendar now;
    private int parseBeginIndex = 0;
    private int parseEndIndex = 0;
    private int second = -1;

    private String target;
    private boolean useDay = false;
    private boolean useEra = false;
    private boolean useHour = false;
    private boolean useMinute = false;
    private boolean useMonth = false;
    private boolean useSecond = false;
    private boolean useYear = false;
    private int year = -1;

    /**
     * コンストラクタです。
     */
    public VRDateParserStatementParseOption() {

    }

    /**
     * コンストラクタです。
     * 
     * @param target 解析対象
     * @param eras 元号集合
     * @param locale ロケール
     */
    public VRDateParserStatementParseOption(String target, ArrayList eras,
            Locale locale, Calendar now) {
        setTarget(target);
        setEras(eras);
        setLocale(locale);
        setNow(now);
    }

    /**
     * 解析結果の日 を返します。
     * 
     * @return 解析結果の日
     */
    public int getDay() {
        return day;
    }

    /**
     * 解析結果の元号年 を返します。
     * 
     * @return 解析結果の元号年
     */
    public int getEra() {
        return era;
    }

    /**
     * 解析に利用する元号情報集合 を返します。
     * 
     * @return 解析に利用する元号情報集合
     */
    public ArrayList getEras() {
        return eras;
    }

    /**
     * 解析結果の時 を返します。
     * @return 解析結果の時
     */
    public int getHour() {
        return hour;
    }

    /**
     * 解析に利用する言語ロケール を返します。
     * 
     * @return 解析に利用する言語ロケール
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 解析結果の元号情報 を返します。
     * 
     * @return 解析結果の元号情報
     */
    public VRDateParserEra getMachedEra() {
        return machedEra;
    }

    /**
     * 解析結果の分 を返します。
     * @return 解析結果の分
     */
    public int getMinute() {
        return minute;
    }

    /**
     * 解析結果の月 を返します。
     * 
     * @return 解析結果の月
     */
    public int getMonth() {
        return month;
    }

    /**
     * 解析に利用する現在日時 を返します。
     * 
     * @return 解析に利用する現在日時
     */
    public Calendar getNow() {
        return now;
    }

    /**
     * 解析に利用する解析開始位置 を返します。
     * 
     * @return 解析に利用する解析開始位置
     */
    public int getParseBeginIndex() {
        return parseBeginIndex;
    }

    /**
     * 解析結果の解析終了位置 を返します。
     * 
     * @return 解析結果の解析終了位置
     */
    public int getParseEndIndex() {
        return parseEndIndex;
    }

    /**
     * 解析結果の秒 を返します。
     * @return 解析結果の秒
     */
    public int getSecond() {
        return second;
    }

    /**
     * 解析に利用する解析対象 を返します。
     * 
     * @return 解析に利用する解析対象
     */
    public String getTarget() {
        return target;
    }

    /**
     * 解析結果の年 を返します。
     * 
     * @return 解析結果の年
     */
    public int getYear() {
        return year;
    }

    /**
     * 解析結果として日を設定したか を返します。
     * 
     * @return 解析結果として日を設定したか
     */
    public boolean isUseDay() {
        return useDay;
    }

    /**
     * 解析結果として元号年を設定したか を返します。
     * 
     * @return 解析結果として元号年を設定したか
     */
    public boolean isUseEra() {
        return useEra;
    }

    /**
     * 解析結果として時を設定したか を返します。
     * @return 解析結果として時を設定したか
     */
    public boolean isUseHour() {
        return useHour;
    }

    /**
     * 解析結果として分を設定したか を返します。
     * @return 解析結果として分を設定したか
     */
    public boolean isUseMinute() {
        return useMinute;
    }

    /**
     * 解析結果として月を設定したか を返します。
     * 
     * @return 解析結果として月を設定したか
     */
    public boolean isUseMonth() {
        return useMonth;
    }

    /**
     * 解析結果として秒を設定したか を返します。
     * @return 解析結果として秒を設定したか
     */
    public boolean isUseSecond() {
        return useSecond;
    }

    /**
     * 解析結果として年を設定したか を返します。
     * 
     * @return 解析結果として年を設定したか
     */
    public boolean isUseYear() {
        return useYear;
    }

    /**
     * 解析結果の日 を設定します。
     * 
     * @param day 解析結果の日
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * 解析結果の元号年 を設定します。
     * 
     * @param era 解析結果の元号年
     */
    public void setEra(int era) {
        this.era = era;
    }

    /**
     * 解析に利用する元号情報集合 を設定します。
     * 
     * @param eras 解析に利用する元号情報集合
     */
    public void setEras(ArrayList eras) {
        this.eras = eras;
    }

    /**
     * 解析結果の時 を設定します。
     * @param hour 解析結果の時
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * 解析に利用する言語ロケール を設定します。
     * 
     * @param locale 解析に利用する言語ロケール
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * 解析結果の元号情報 を設定します。
     * 
     * @param machedEra 解析結果の元号情報
     */
    public void setMachedEra(VRDateParserEra machedEra) {
        this.machedEra = machedEra;
    }

    /**
     * 解析結果の分 を設定します。
     * @param minute 解析結果の分
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * 解析結果の月 を設定します。
     * 
     * @param month 解析結果の月
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * 解析に利用する現在日時 を設定します。
     * 
     * @param now 解析に利用する現在日時
     */
    public void setNow(Calendar now) {
        this.now = now;
    }

    /**
     * 解析に利用する解析開始位置 を設定します。
     * 
     * @param parseBeginIndex 解析に利用する解析開始位置
     */
    public void setParseBeginIndex(int parseBeginIndex) {
        this.parseBeginIndex = parseBeginIndex;
    }

    /**
     * 解析結果の解析終了位置 を設定します。
     * 
     * @param parseEndIndex 解析結果の解析終了位置
     */
    public void setParseEndIndex(int parseEndIndex) {
        this.parseEndIndex = parseEndIndex;
    }

    /**
     * 解析結果の秒 を設定します。
     * @param second 解析結果の秒
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * 解析に利用する解析対象 を設定します。
     * 
     * @param target 解析に利用する解析対象
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * 解析結果として日を設定したか を設定します。
     * 
     * @param useDay 解析結果として日を設定したか
     */
    public void setUseDay(boolean useDay) {
        this.useDay = useDay;
    }

    /**
     * 解析結果として元号年を設定したか を設定します。
     * 
     * @param useEra 解析結果として元号年を設定したか
     */
    public void setUseEra(boolean useEra) {
        this.useEra = useEra;
    }

    /**
     * 解析結果として時を設定したか を設定します。
     * @param useHour 解析結果として時を設定したか
     */
    public void setUseHour(boolean useHour) {
        this.useHour = useHour;
    }

    /**
     * 解析結果として分を設定したか を設定します。
     * @param useMinute 解析結果として分を設定したか
     */
    public void setUseMinute(boolean useMinute) {
        this.useMinute = useMinute;
    }

    /**
     * 解析結果として月を設定したか を設定します。
     * 
     * @param useMonth 解析結果として月を設定したか
     */
    public void setUseMonth(boolean useMonth) {
        this.useMonth = useMonth;
    }

    /**
     * 解析結果として秒を設定したか を設定します。
     * @param useSecond 解析結果として秒を設定したか
     */
    public void setUseSecond(boolean useSecond) {
        this.useSecond = useSecond;
    }

    /**
     * 解析結果として年を設定したか を設定します。
     * 
     * @param useYear 解析結果として年を設定したか
     */
    public void setUseYear(boolean useYear) {
        this.useYear = useYear;
    }

    /**
     * 解析結果の年 を設定します。
     * 
     * @param year 解析結果の年
     */
    public void setYear(int year) {
        this.year = year;
    }
}