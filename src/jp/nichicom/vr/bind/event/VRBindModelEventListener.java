/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventListener;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindSource;

/**
 * モデルバインド機構に関連するイベントリスナです。
 * <p>
 * モデルバインド機構に対応したコントロールが関連イベントを発生させます。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindSource
 * @see VRBindModelable
 */
public interface VRBindModelEventListener extends EventListener {

    /**
     * コントロールがソースに値を適用(apply)した際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void applyModelSource(VRBindModelEvent e);

    /**
     * ソースをコントロールにbindした際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    void bindModelSource(VRBindModelEvent e);
}