/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventListener;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;

/**
 * バインド機構に関連するイベントリスナです。
 * <p>
 * バインド機構に対応したコントロールが関連イベントを発生させます。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 * @see VRBindable
 */
public interface VRBindEventListener extends EventListener{

    /**
     * コントロールがソースに値を適用(apply)した際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void applySource(VRBindEvent e);

    /**
     * ソースをコントロールにbindした際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void bindSource(VRBindEvent e);

}