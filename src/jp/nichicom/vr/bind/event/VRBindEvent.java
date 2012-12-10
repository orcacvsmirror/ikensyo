/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventObject;

import jp.nichicom.vr.bind.VRBindable;

/**
 * バインドコントロールに関連するイベントオブジェクトです。
 * <p>
 * バインドコントロールに対して行なった操作に対応するイベント情報を格納します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindable
 * @see VRBindEventListener
 */
public class VRBindEvent extends EventObject {

    /**
     * コンストラクタです。
     * 
     * @param source イベント発生元
     */
    public VRBindEvent(Object source) {
        super(source);
    }

    /**
     * イベント発生元を設定します。
     * 
     * @param source イベント発生元
     */
    public void setSource(Object source) {
        this.source = source;
    }

}
