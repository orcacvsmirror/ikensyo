/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventObject;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * バインド用のデータに関連するイベントオブジェクトです。
 * <p>
 * バインド用のデータに対して行なった操作に対応するイベント情報を格納します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindSource
 * @see VRBindSourceEventListener
 */
public class VRBindSourceEvent extends EventObject {
    protected int index;

    protected Object key;

    /**
     * コンストラクタです。
     * 
     * @param source イベント発生元
     * @param index 要素番号
     * @param key 要素キー
     */
    public VRBindSourceEvent(Object source, int index, Object key) {
        super(source);

    }

    /**
     * 要素番号を返します。
     * 
     * @return 要素番号
     */
    public int getIndex() {
        return index;
    }

    /**
     * 要素キーを返します。
     * 
     * @return 要素キー
     */
    public Object getKey() {
        return key;
    }

    /**
     * 要素番号を設定します。
     * 
     * @param index 要素番号
     */
    protected void setIndex(int index) {
        this.index = index;
    }

    /**
     * 要素キーを設定します。
     * 
     * @param key 要素キー
     */
    protected void setKey(Object key) {
        this.key = key;
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