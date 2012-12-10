/** TODO <HEAD> */
package jp.nichicom.vr.component.event;

import java.util.EventObject;

/**
 * フォーマットに関連するイベントオブジェクトです。
 * <p>
 * 入力値のフォーマット操作に対応するイベント情報を格納します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRFormatEventListener
 * @see EventObject
 */
public class VRFormatEvent extends EventObject {
    protected Object parseValue;
    protected Object oldModel;

    /**
     * コンストラクタです。
     * 
     * @param source イベント発生元
     * @param oldModel 変更前のモデル値
     * @param parseValue 設定しようとした値
     */
    public VRFormatEvent(Object source, Object oldModel, Object parseValue) {
        super(source);
        setOldModel(oldModel);
        setParseValue(parseValue);
    }

    /**
     * 設定しようとした値 を返します。
     * 
     * @return 設定しようとした値
     */
    public Object getParseValue() {
        return parseValue;
    }

    /**
     * 変更前のモデル値を返します。
     * 
     * @return 変更前のモデル値
     */
    public Object getOldModel() {
        return oldModel;
    }

    /**
     * 設定しようとした値 を設定します。
     * 
     * @param parseValue 設定しようとした値
     */
    public void setParseValue(Object parseValue) {
        this.parseValue = parseValue;
    }

    /**
     * イベント発生元を設定します。
     * 
     * @param source イベント発生元
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     * 変更前のモデル値を設定します。
     * 
     * @param oldModel 変更前のモデル値
     */
    protected void setOldModel(Object oldModel) {
        this.oldModel = oldModel;
    }
}