package jp.nichicom.ac.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.text.parsers.VRDateParserHolyday;

/**
 * 日付関連の汎用メソッドを集めたクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACDateUtilities {
    /**
     * 期間1の開始以後と期間2の終了以前が重複することをあらわす期間重複定数です。
     */
    public static final int DUPLICATE_FIRST_BEGIN_AND_SECOND_END = 2;

    /**
     * 期間1の終了以前と期間2の開始以後が重複することをあらわす期間重複定数です。
     */
    public static final int DUPLICATE_FIRST_END_AND_SECOND_BEGIN = 1;

    /**
     * 期間1と期間2が全く同じことをあらわす期間重複定数です。
     */
    public static final int DUPLICATE_FIRST_EQUALS_SECOND = 5;
    /**
     * 期間1が期間2を包含することをあらわす期間重複定数です。
     */
    public static final int DUPLICATE_FIRST_INCLUDE_SECOND = 3;
    /**
     * 重複無しをあらわす期間重複定数です。
     */
    public static final int DUPLICATE_NONE = 0;
    /**
     * 期間1が期間2を包含することをあらわす期間重複定数です。
     */
    public static final int DUPLICATE_SECOND_INCLUDE_FIRST = 4;

    private final static String[] fullWeeks = { "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日",
            "土曜日" };

    private final static String[] shortWeeks = { "日", "月", "火", "水", "木", "金", "土" };

    private static ACDateUtilities singleton;

    /**
     * 指定日数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算日数
     * @return 加算結果
     */
    public static Date addDay(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.DAY_OF_MONTH, count);
        return getCalX().getTime();
    }

    /**
     * 指定時間数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算時間数
     * @return 加算結果
     */
    public static Date addHour(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.HOUR_OF_DAY, count);
        return getCalX().getTime();
    }

    /**
     * 指定分数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算分数
     * @return 加算結果
     */
    public static Date addMinute(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.MINUTE, count);
        return getCalX().getTime();
    }

    /**
     * 指定月数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算月数
     * @return 加算結果
     */
    public static Date addMonth(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.MONTH, count);
        return getCalX().getTime();
    }
    /**
     * 指定秒数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算秒数
     * @return 加算結果
     */
    public static Date addSecond(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.SECOND, count);
        return getCalX().getTime();
    }

    /**
     * 指定年数加算した日付を返します。
     * 
     * @param x 日付
     * @param count 加算年数
     * @return 加算結果
     */
    public static Date addYear(Date x, int count) {
        getCalX().setTime(x);
        getCalX().add(Calendar.YEAR, count);
        return getCalX().getTime();
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
    public static int compareOnDay(Date x, Date y) {
        return toSimpleDifference(getDifferenceOnDay(x, y));
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
    public static int compareOnMonth(Date x, Date y) {
        return toSimpleDifference(getDifferenceOnMonth(x, y));
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
    public static int compareOnYear(Date x, Date y) {
        return toSimpleDifference(getDifferenceOnYear(x, y));
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
    public static Date createDate(int year) {
        return createDate(year, 1, 1);
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
    public static Date createDate(int year, int month) {
        return createDate(year, month, 1);
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
    public static Date createDate(int year, int month, int day) {
        getCalX().clear();
        getCalX().set(Calendar.YEAR, year);
        getCalX().set(Calendar.MONTH, month - 1);
        getCalX().set(Calendar.DAY_OF_MONTH, day);
        return getCalX().getTime();
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
    public static Date createTime(int hour, int minute) {
        return createTime(hour, minute, 0);
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
    public static Date createTime(int hour, int minute, int second) {
        getCalX().clear();
        getCalX().set(Calendar.HOUR_OF_DAY, hour);
        getCalX().set(Calendar.MINUTE, minute);
        getCalX().set(Calendar.SECOND, second);
        return getCalX().getTime();
    }

    /**
     * 当月内の日にちを返します。
     * 
     * @param x 日付
     * @return 当月内の日にち
     */
    public static int getDayOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 曜日を返します。
     * <p>
     * 日曜：<code>Calendar.SUNDAY</code><br/> 月曜：<code>Calendar.MONDAY</code><br/>
     * 火曜：<code>Calendar.TUESDAY</code><br/> 水曜：<code>Calendar.WEDNESDAY</code><br/>
     * 木曜：<code>Calendar.THURSDAY</code><br/> 金曜：<code>Calendar.FRIDAY</code><br/>
     * 土曜：<code>Calendar.SATURDAY</code><br/>
     * </p>
     * 
     * @param x 日付
     * @return 曜日
     */
    public static int getDayOfWeek(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.DAY_OF_WEEK);
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
    public static String getDayOfWeekFull(Date x) {
        int idx = getDayOfWeek(x)-1;
        if(idx<0){
            return "";
        }
        return fullWeeks[idx];
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
    public static String getDayOfWeekShort(Date x) {
        int idx = getDayOfWeek(x)-1;
        if(idx<0){
            return "";
        }
        return shortWeeks[idx];
    }

    /**
     * 日付の差を年月日単位で返します。
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
     * @return 日付差
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
     * @return 日付差
     */
    public static int getDifferenceOnMonth(Date x, Date y) {
        getCalX().setTime(x);
        getCalY().setTime(y);

        int xD = getCalX().get(Calendar.YEAR) * 100 + getCalX().get(Calendar.MONTH);
        int yD = getCalY().get(Calendar.YEAR) * 100 + getCalY().get(Calendar.MONTH);

        return xD - yD;
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
            // その年の日数を加算
            cal.set(Calendar.YEAR, year);
            count += cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        for (int year = xY; year < yY; year++) {
            // その年の日数を減算
            cal.set(Calendar.YEAR, year);
            count -= cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        }
        // 日数の差分を加算
        count += xD - yD;
        return count;
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
     * @return 日付差
     */
    public static int getDifferenceOnYear(Date x, Date y) {
        getCalX().setTime(x);
        getCalY().setTime(y);

        int xD = getCalX().get(Calendar.YEAR);
        int yD = getCalY().get(Calendar.YEAR);

        return xD - yD;
    }

    /**
     * 期間の重複をチェックします。
     * 
     * @param span1Begin 期間1の開始
     * @param span1End 期間1の終了
     * @param span2Begin 期間2の開始
     * @param span2End 期間2の終了
     * @return 重複状況
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
            // 期間1の開始＞期間2の開始
            if (getDifferenceOnDay(span1End, span2End) <= 0) {
                // 期間1の終了≦期間2の終了
                return DUPLICATE_SECOND_INCLUDE_FIRST;
            }
            if (getDifferenceOnDay(span1Begin, span2End) <= 0) {
                // 期間1の開始≦期間2の終了
                return DUPLICATE_FIRST_BEGIN_AND_SECOND_END;
            }
        } else if (beginDif < 0) {
            // 期間1の開始 ＜ 期間2の開始
            if (getDifferenceOnDay(span1End, span2End) >= 0) {
                // 期間1の終了≧期間2の終了
                return DUPLICATE_FIRST_INCLUDE_SECOND;
            }
            if (getDifferenceOnDay(span1End, span2Begin) >= 0) {
                // 期間1の終了≧期間2の開始
                return DUPLICATE_FIRST_END_AND_SECOND_BEGIN;
            }
        } else {
            // 期間1の開始＝期間2の開始
            if (getDifferenceOnDay(span1End, span2End) > 0) {
                // 期間1の終了＞期間2の終了
                return DUPLICATE_FIRST_INCLUDE_SECOND;
            }
            if (getDifferenceOnDay(span1End, span2End) < 0) {
                // 期間1の終了＜期間2の終了
                return DUPLICATE_SECOND_INCLUDE_FIRST;
            }
            if (getDifferenceOnDay(span1End, span2End) == 0) {
                // 期間1の終了＝期間2の終了
                return DUPLICATE_FIRST_EQUALS_SECOND;
            }
        }
        return DUPLICATE_NONE;
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
    public static String getEraAlphabet(Date x) throws Exception {
        return VRDateParser.getEra(x).getAbbreviation(1);
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
    public static String getEraFull(Date x) throws Exception {
        return VRDateParser.getEra(x).getAbbreviation(3);
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
    public static String getEraShort(Date x) throws Exception {
        return VRDateParser.getEra(x).getAbbreviation(2);
    }

    /**
     * 和暦年を返します。
     * 
     * @param x 日付
     * @throws Exception 処理例外
     * @return 和暦年
     */
    public static int getEraYear(Date x) throws Exception {
        return Integer.parseInt(VRDateParser.format(x, "e"));
    }

    /**
     * その月の初日(1日)を返します。
     * 
     * @param x 日付
     * @return 当月内の日にち
     */
    public static int getFirstDayOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().getActualMinimum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当日の時間を返します。
     * 
     * @param x 日付
     * @return 当日の時間
     */
    public static int getHourOfDay(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACDateUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACDateUtilities();
        }
        return singleton;
    }

    /**
     * その月の末日を返します。
     * 
     * @param x 日付
     * @return 当月内の日にち
     */
    public static int getLastDayOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 当日の分を返します。
     * 
     * @param x 日付
     * @return 当日の分
     */
    public static int getMinute(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.MINUTE);
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
    public static int getMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.MONTH) + 1;
    }

    /**
     * 当日の秒を返します。
     * 
     * @param x 日付
     * @return 当日の秒
     */
    public static int getSecond(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.SECOND);
    }

    /**
     * 第何週目かを返します。
     * 
     * @param x 日付
     * @return 週数
     */
    public static int getWeekOfMonth(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 年を返します。
     * 
     * @param x 日付
     * @return 年
     */
    public static int getYear(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.YEAR);
    }

    /**
     * AM(午前)であるかを返します。
     * 
     * @param x 時刻
     * @return AM(午前中)であるか
     */
    public static boolean isAM(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.AM_PM) == Calendar.AM;
    }

    /**
     * 祝祭日であるかを返します。
     * 
     * @param x 日付
     * @return 祝祭日であるか
     */
    public static boolean isHolyday(Date x) {
        try {
            return !VRDateParser.getHolydays(x).isEmpty();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * PM(午後)であるかを返します。
     * 
     * @param x 時刻
     * @return PM(午後)であるか
     */
    public static boolean isPM(Date x) {
        getCalX().setTime(x);
        return getCalX().get(Calendar.AM_PM) == Calendar.PM;
    }

    /**
     * 土曜日であるかを返します。
     * 
     * @param x 日付
     * @return 土曜日であるか
     */
    public static boolean isSaturday(Date x) {
        return getDayOfWeek(x) == Calendar.SATURDAY;
    }

    /**
     * 日曜日であるかを返します。
     * 
     * @param x 日付
     * @return 日曜日であるか
     */
    public static boolean isSunday(Date x) {
        return getDayOfWeek(x) == Calendar.SUNDAY;
    }

    /**
     * 日曜もしくは祝祭日であるかを返します。
     * 
     * @param x 日付
     * @return 日曜もしくは祝祭日であるか
     */
    public static boolean isSundayOrHolyday(Date x) {
        return isSunday(x) || isHolyday(x);
    }

    /**
     * 指定日に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定日
     * @return 設定結果
     */
    public static Date setDayOfMonth(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_MONTH, value);
        return getCalX().getTime();
    }

    /**
     * 指定曜日に設定した日付を返します。
     * <p>
     * 日曜：<code>Calendar.SUNDAY</code><br/> 月曜：<code>Calendar.MONDAY</code><br/>
     * 火曜：<code>Calendar.TUESDAY</code><br/> 水曜：<code>Calendar.WEDNESDAY</code><br/>
     * 木曜：<code>Calendar.THURSDAY</code><br/> 金曜：<code>Calendar.FRIDAY</code><br/>
     * 土曜：<code>Calendar.SATURDAY</code><br/>
     * </p>
     * 
     * @param x 日付
     * @param value 設定曜日
     * @return 設定結果
     */
    public static Date setDayOfWeek(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_WEEK, value);
        return getCalX().getTime();
    }

    /**
     * 指定時間に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定時間
     * @return 設定結果
     */
    public static Date setHourOfDay(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.HOUR_OF_DAY, value);
        return getCalX().getTime();
    }

    /**
     * 指定分に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定分
     * @return 設定結果
     */
    public static Date setMinute(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.MINUTE, value);
        return getCalX().getTime();
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
    public static Date setMonth(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.MONTH, value - 1);
        return getCalX().getTime();
    }

    /**
     * 指定秒に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定秒
     * @return 設定結果
     */
    public static Date setSecond(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.SECOND, value);
        return getCalX().getTime();
    }

    /**
     * 指定週目に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定週数
     * @return 設定結果
     */
    public static Date setWeekOfMonth(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.WEEK_OF_MONTH, value);
        return getCalX().getTime();
    }

    /**
     * 指定年に設定した日付を返します。
     * 
     * @param x 日付
     * @param value 設定年
     * @return 設定結果
     */
    public static Date setYear(Date x, int value) {
        getCalX().setTime(x);
        getCalX().set(Calendar.YEAR, value);
        return getCalX().getTime();
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
    public static Date toFirstDayOfMonth(Date x) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_MONTH, getCalX()
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        return getCalX().getTime();
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
    public static Date toLastDayOfMonth(Date x) {
        getCalX().setTime(x);
        getCalX().set(Calendar.DAY_OF_MONTH, getCalX()
                .getActualMaximum(Calendar.DAY_OF_MONTH));
        return getCalX().getTime();
    }


    /**
     * 内部計算用のカレンダ1 を返します。
     * @return 内部計算用のカレンダ1
     */
    protected static Calendar getCalX() {
        return getInstance().calX;
    }

    /**
     * 内部計算用のカレンダ2 を返します。
     * @return 内部計算用のカレンダ2
     */
    protected static Calendar getCalY() {
        return getInstance().calY;
    }

    /**
     * 値を0,-1,1に単純化します。
     * 
     * @param diff 変換元
     * @return 単純化結果
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
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACDateUtilities() {
    }

    /**
     * 祝祭日名を返します。
     * 
     * @param x 日付
     * @return 祝祭日名
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
