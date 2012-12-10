/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * VRDateParserが使用する祝祭日集合クラスです。
 * <p>
 * 指定日、指定曜日を祝祭日として特定可能です。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 * @see VRDateParserHolydayTerm
 */
public class VRDateParserHolydays implements VRDateParserHolydayCalculatable {
    private ArrayList calcHolydays;

    private ArrayList[][] dayHolydayTerms;

    private ArrayList[][][] weekHolydayTerms;

    /**
     * コンストラクタ
     */
    public VRDateParserHolydays() {
        clearHolydays();
    }

    /**
     * 祝祭日計算定義を追加します。
     * 
     * @param calcuable 祝祭日計算定義
     */
    public void addCalcHolyday(VRDateParserHolydayCalculatable calcuable) {
        getCalcHolydays().add(calcuable);
    }

    /**
     * 固定日祝祭日条件定義を追加します。
     * 
     * @param term 固定日祝祭日条件定義
     * @param month 月
     * @param day 日
     */
    public void addDayHolydayTerm(VRDateParserHolydayTerm term, int month,
            int day) {
        ArrayList terms = getDayHolydayTerms()[month - 1][day - 1];
        if (terms == null) {
            terms = new ArrayList();
            getDayHolydayTerms()[month - 1][day - 1] = terms;
        }
        terms.add(term);
    }

    /**
     * 固定週祝祭日条件定義を追加します。
     * 
     * @param term 固定週祝祭日条件定義
     * @param month 月
     * @param week 週
     * @param wday 曜日
     */
    public void addWeekHolydayTerm(VRDateParserHolydayTerm term, int month,
            int week, int wday) {
        ArrayList terms = getWeekHolydayTerms()[month - 1][week - 1][wday - 1];
        if (terms == null) {
            terms = new ArrayList();
            getWeekHolydayTerms()[month - 1][week - 1][wday - 1] = terms;
        }
        terms.add(term);
    }

    /**
     * 祝祭日計算定義集合を初期化します。
     */
    public void clearCalcHolydays() {
        getCalcHolydays().clear();
    }

    /**
     * 祝祭日定義を初期化します。
     */
    public void clearHolydays() {
        //12月6週7日
        weekHolydayTerms = new ArrayList[12][6][7];
        //12月31日
        dayHolydayTerms = new ArrayList[12][31];
        calcHolydays = new ArrayList();
    }

    /**
     * 祝祭日計算定義集合 を返します。
     * 
     * @return 祝祭日計算定義集合
     */
    public ArrayList getCalcHolydays() {
        return calcHolydays;
    }

    /**
     * 固定日祝祭日定義集合 を返します。
     * 
     * @return 固定日祝祭日条件定義集合
     */
    public ArrayList[][] getDayHolydayTerms() {
        return dayHolydayTerms;
    }

    /**
     * 固定日祝祭日条件定義集合を返します。
     * 
     * @param month 月
     * @param day 日
     * @return 固定日祝祭日条件定義集合
     */
    public ArrayList getDayHolydayTerms(int month, int day) {
        return getDayHolydayTerms()[month - 1][day - 1];
    }

    /**
     * 固定週祝祭日定義集合 を返します。
     * 
     * @return 固定週祝祭日条件定義集合
     */
    public ArrayList[][][] getWeekHolydayTerms() {
        return weekHolydayTerms;
    }

    /**
     * 固定週祝祭日条件定義集合を返します。
     * 
     * @param month 月
     * @param week 週
     * @param wday 曜日
     * @return 固定週祝祭日条件定義集合
     */
    public ArrayList getWeekHolydayTerms(int month, int week, int wday) {
        return getWeekHolydayTerms()[month - 1][week - 1][wday - 1];
    }

    /**
     * 祝祭日計算定義集合 を設定します。
     * 
     * @param calcHolydays 祝祭日計算定義集合
     */
    public void setCalcHolydays(ArrayList calcHolydays) {
        this.calcHolydays = calcHolydays;
    }

    /**
     * 固定日祝祭日条件定義集合 を設定します。
     * 
     * @param dayHolydayTerms 固定日祝祭日条件定義集合
     */
    public void setDayHolydayTerms(ArrayList[][] dayHolydayTerms) {
        this.dayHolydayTerms = dayHolydayTerms;
    }

    /**
     * 固定週祝祭日定義集合 を設定します。
     * 
     * @param weekHolydayTerms 固定週祝祭日条件定義集合
     */
    public void setWeekHolydayTerms(ArrayList[][][] weekHolydayTerms) {
        this.weekHolydayTerms = weekHolydayTerms;
    }

    /**
     * 初回呼び出し用の祝祭日取得関数です。
     * 
     * @param cal 対象日付
     * @param holydays 結果格納用の休日集合
     */
    public void stockHolyday(Calendar cal, List holydays) {
        stockHolyday(cal, holydays, null);
    }

    public void stockHolyday(Calendar cal, List holydays, Object parameter) {
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE) - 1;
        int week = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) - 1;
        int wday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        addMatchHoliday((List) getWeekHolydayTerms()[m][week][wday], cal,
                holydays, parameter);
        addMatchHoliday((List) getDayHolydayTerms()[m][d], cal, holydays,
                parameter);
        addMatchHoliday(getCalcHolydays(), cal, holydays, parameter);

    }

    /**
     * 祝祭日判定集合を走査して該当する祝祭日定義を追加します。
     * 
     * @param src チェック対象の祝祭日判定集合
     * @param cal 対象日
     * @param dest 祝祭日定義の追加先
     * @param parameter 循環参照防止等に利用する自由領域
     */
    protected void addMatchHoliday(List src, Calendar cal, List dest,
            Object parameter) {
        if (src == null) {
            return;
        }
        Iterator it = src.iterator();
        while (it.hasNext()) {
            VRDateParserHolydayCalculatable calc = (VRDateParserHolydayCalculatable) it
                    .next();
            calc.stockHolyday(cal, dest, parameter);
        }
    }
}