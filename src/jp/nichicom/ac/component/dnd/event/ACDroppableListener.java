package jp.nichicom.ac.component.dnd.event;

import java.util.EventListener;

/**
 * ドロップイベントリスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/01
 */
public interface ACDroppableListener extends EventListener {
    /**
     * ドロップ成功イベントです。
     * 
     * @param e イベント情報
     */
    public void dropSuccess(ACDropEvent e);

    /**
     * ドロップ失敗イベントです。
     * 
     * @param e イベント情報
     */
    public void dropReject(ACDropEvent e);

}
