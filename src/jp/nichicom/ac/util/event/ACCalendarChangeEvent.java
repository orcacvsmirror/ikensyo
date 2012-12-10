package jp.nichicom.ac.util.event;

import java.util.Calendar;
import java.util.EventObject;

/**
 * カレンダー選択情報更新イベントです。
 * <p>
 * カレンダーコンポーネントが日付の選択情報を更新した時に呼び出されるイベント情報を格納するクラスです。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendarChangeEvent extends EventObject {

    private int month;
    private int year;
    private Calendar calendar;
    private Calendar endCalendar;

    /**
     * メインコンストラクタ - 継承元のコンストラクタを呼び出した後、コンポーネントの初期化を行います。
     * 
     * @param source イベント発生元オブジェクト
     * @param year 表示年
     * @param month 表示月
     * @param calendar 選択開始日
     * @param endCalendar 選択終了日
     */
    public ACCalendarChangeEvent(Object source, int year, int month,
            Calendar calendar, Calendar endCalendar) {
        super(source);
        this.month = month;
        this.year = year;
        this.calendar = calendar;
        this.endCalendar = endCalendar;
    }

    /**
     * 表示月を返します。
     * 
     * @return 表示月
     */
    public int getMonth() {
        return month;
    }

    /**
     * 表示年を返します。
     * 
     * @return 表示年
     */
    public int getYear() {
        return year;
    }

    /**
     * 選択開始日を返します。
     * 
     * @return 選択開始日
     */
    public Calendar getBeginCalendar() {
        return calendar;
    }

    /**
     * 選択終了日を返します。
     * 
     * @return 選択終了日
     */
    public Calendar getEndCalendar() {
        return endCalendar;
    }

}
