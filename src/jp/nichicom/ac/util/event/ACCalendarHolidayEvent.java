package jp.nichicom.ac.util.event;

import java.util.Calendar;
import java.util.EventObject;

/**
 * カレンダー祝日情報取得イベントです。
 * <p>
 * カレンダーコンポーネントが祝日情報を取得した時に呼び出されるイベント情報を格納するクラスです。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendarHolidayEvent extends EventObject {

    private Calendar calendar;
    private String holidayText;
    private boolean holiday;

    /**
     * メインコンストラクタ - パラメータでメンバ変数を初期化します。
     * 
     * @param source イベント発生元
     * @param calendar 祝日情報を取得する日付
     * @param holidayText 祝日名称
     */
    public ACCalendarHolidayEvent(Object source, Calendar calendar,
            String holidayText) {
        super(source);
        setCalendar(calendar);
        setHolidayText(holidayText);
        setHoliday(false);
    }

    /**
     * 祝日情報を取得する日付を返します。
     * 
     * @return 祝日情報を取得する日付
     */
    public Calendar getCalendar() {
        return calendar;
    }

    /**
     * 祝日情報を取得する日付を設定します。
     * 
     * @param calendar 祝日情報を取得する日付
     */
    public void setCalendar(Calendar calendar) {
        if (calendar == null) {
            return;
        }
        this.calendar = calendar;
    }

    /**
     * 祝日名称を返します。
     * 
     * @return 祝日名称
     */
    public String getHolidayText() {
        return holidayText;
    }

    /**
     * 祝日名称を設定します。
     * 
     * @param holidayText 祝日名称
     */
    public void setHolidayText(String holidayText) {
        if (holidayText == null) {
            return;
        }
        this.holidayText = holidayText;
    }

    /**
     * 祝日であるかを返します。
     * 
     * @return 祝日の場合はtrue
     */
    public boolean isHoliday() {
        return holiday;
    }

    /**
     * 祝日であるかを設定します。
     * 
     * @param holiday 祝日の場合はtrue
     */
    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

}
