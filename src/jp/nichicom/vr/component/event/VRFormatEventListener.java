/** TODO <HEAD> */
package jp.nichicom.vr.component.event;

import java.util.EventListener;


/**
 * フォーマットに関連するイベントリスナです。
 * <p>
 * 入力値のフォーマットに対応したコントロールが関連イベントを発生させます。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRFormatEvent
 */
public interface VRFormatEventListener extends EventListener{

    /**
     * フォーマットに失敗した際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void formatInvalid(VRFormatEvent e);

    /**
     * フォーマットに成功した際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void formatValid(VRFormatEvent e);
}