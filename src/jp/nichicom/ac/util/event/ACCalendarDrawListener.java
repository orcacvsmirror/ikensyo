package jp.nichicom.ac.util.event;

import java.util.EventListener;

/**
 * カレンダー描画情報 更新イベントリスナーインタフェースです。
 * <p>
 * カレンダーコンポーネントが描画される時に呼び出されるイベントを受け取るリスナーインターフェースです。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public interface ACCalendarDrawListener extends EventListener {

    /**
     * カレンダーコンポーネントが描画される時に呼び出されるイベントです。
     * 
     * @param e カレンダー描画イベント情報
     */
    public void calendarDrawing(ACCalendarDrawEvent e);

}
