package jp.nichicom.ac.util.event;

import java.util.EventListener;

/**
 * カレンダー祝日情報取得イベントリスナーインタフェースです。
 * <p>
 * カレンダーコンポーネントが祝日情報を取得した時に呼び出されるイベントを受け取るリスナーインターフェースです。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public interface ACCalendarHolidayListener extends EventListener {

    /**
     * カレンダーコンポーネントが祝日情報を取得した時に呼び出されるイベントです。
     * 
     * @param e カレンダー祝日情報取得イベント情報
     */
    public void ownerHoliday(ACCalendarHolidayEvent e);
}
