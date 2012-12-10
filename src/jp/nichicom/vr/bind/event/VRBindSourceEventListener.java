/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventListener;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * バインド用のデータに関連するイベントリスナです。
 * <p>
 * バインド用のデータに対して行なった操作に対応するイベントが通知されます。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 * @see VRBindSourceEvent
 */
public interface VRBindSourceEventListener extends EventListener{

    /**
     * データが追加された際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void addSource(VRBindSourceEvent e);

    /**
     * データに値が設定された際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void changeSource(VRBindSourceEvent e);

    /**
     * データに初期化された際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void clearSource(VRBindSourceEvent e);

    /**
     * データが削除された際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void removeSource(VRBindSourceEvent e);
}