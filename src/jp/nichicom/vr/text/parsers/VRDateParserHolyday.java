/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.HashMap;


/**
 * VRDateParserが使用する祝祭日定義クラスです。
 * <p>
 * 祝祭日名たるIDの他に、自由に定義可能なパラメタHashMapを保持しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 */
public class VRDateParserHolyday {
    private String id;

    private HashMap parameters;

    /**
     * コンストラクタ
     */
    public VRDateParserHolyday() {
        parameters = new HashMap();
    }

    /**
     * コンストラクタ
     */
    public VRDateParserHolyday(String id) {
        this();
        setId(id);
    }

    /**
     * パラメタをすべて削除します。
     */
    public void clearParameter() {
        getParameters().clear();
    }

    /**
     * 既存のパラメタキーであるかを返します。
     * 
     * @param key キー
     * @return 既存のパラメタキーであるか
     */
    public boolean containsParameter(String key) {
        return getParameters().containsKey(key);
    }

    /**
     * 祝日名 を返します。
     * 
     * @return 祝日名
     */
    public String getId() {
        return id;
    }

    /**
     * キーに対応するパラメタを返します。
     * 
     * @param key キー
     * @return パラメタ。該当しなければnull
     */
    public String getParameter(String key) {
        return (String) getParameters().get(key);
    }

    /**
     * パラメタ集合 を返します。
     * 
     * @return パラメタ集合
     */
    public HashMap getParameters() {
        return parameters;
    }

    /**
     * キーに該当するパラメタを削除します。
     * 
     * @param key キー
     */
    public void removeParameter(String key) {
        getParameters().remove(key);
    }

    /**
     * 祝日名 を設定します。
     * 
     * @param id 祝日名
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * パラメタ集合 を設定します。
     * 
     * @param parameters パラメタ集合
     */
    public void setParametars(HashMap parameters) {
        this.parameters = parameters;
    }

    /**
     * パラメタを設定します。 <br />
     * 既存のキーを指定した場合、値を上書きします。
     * 
     * @param key キー
     * @param val 値
     */
    public void setParameter(String key, String val) {
        getParameters().put(key, val);
    }

}