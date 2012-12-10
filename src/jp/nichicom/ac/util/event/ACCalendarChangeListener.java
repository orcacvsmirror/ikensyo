package jp.nichicom.ac.util.event;

import java.util.EventListener;

/**
 * カレンダー選択情報 更新イベントリスナーインタフェースです。
 * <p>
 * カレンダーコンポーネントが日付の選択情報を更新した時に呼び出されるイベントを受け取るリスナーインターフェースです。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public interface ACCalendarChangeListener extends EventListener {

    /**
     * カレンダーコンポーネントが日付の選択情報を更新した時に呼び出されるイベントです。
     * 
     * @param e カレンダー選択情報更新イベント情報
     */
    public void selectedChanged(ACCalendarChangeEvent e);

    /**
     * カレンダーコンポーネントが表示対象の月を更新した時に呼び出されるイベントです。
     * 
     * @param e カレンダー表示対象月更新イベント情報
     */
    public void displayMonthChanged(ACCalendarChangeEvent e);
}
