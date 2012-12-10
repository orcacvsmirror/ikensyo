/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * バインドソース機構を実装したHashMapのArrayList化アダプタクラスです。
 * <p>
 * <code>VRHshMap</code> 形式のオブジェクトを固定キーに基づき <code>VRArrayList</code>
 * として透過的に扱います。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 * @see VRBindSourceEventListener
 * @see VRBindSourceAdapter
 */
public class VRHashMapArrayToConstKeyArrayAdapter implements VRBindSource,
        VRBindSourceAdapter {

    protected VRBindSource adaptee;

    protected Object key;

    /**
     * コンストラクタです。
     * 
     * @param adaptee アダプティーとなるバインドソース
     */
    public VRHashMapArrayToConstKeyArrayAdapter(VRBindSource adaptee, Object key) {
        setAdaptee(adaptee);
        setKey(key);
    }

    public void addBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().addBindSourceEventListener(listener);
    }

    public void addData(Object data) {
        getAdaptee().addData(data);
    }

    public void clearData() {
        getAdaptee().clearData();
    }

    /**
     * アダプティーとなるバインドソースを返します。
     * 
     * @return adaptee
     */
    public VRBindSource getAdaptee() {
        return adaptee;
    }

    public Object getData() {
        return ((VRBindSource) getAdaptee().getData()).getData(getKey());
    }

    public Object getData(int index) {
        return ((VRBindSource) getAdaptee().getData(index)).getData(getKey());
    }

    public Object getData(Object key) {
        return ((VRBindSource) getAdaptee().getData(key)).getData(getKey());
    }

    public int getDataSize() {
        return getAdaptee().getDataSize();
    }

    public Object getKey() {
        return key;
    }

    public void removeBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().removeBindSourceEventListener(listener);
    }

    public void removeData(int index) {
        getAdaptee().removeData(index);
    }

    public void removeData(Object key) {
        removeData(parseKey(key));
    }

    public void setData(int index, Object data) {
        ((VRBindSource) getAdaptee().getData(index)).setData(getKey(), data);
    }

    public void setData(Object data) {
        ((VRBindSource) getAdaptee().getData()).setData(getKey(), data);
    }

    public void setData(Object key, Object data) {
        ((VRBindSource) getAdaptee().getData(parseKey(key))).setData(getKey(),
                data);
    }

    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * キーをintとして解釈して返します。
     * 
     * @param key キー
     * @return 解釈結果
     */
    protected int parseKey(Object key) {
        if (key instanceof Integer) {
            return ((Integer) key).intValue();
        } else {
            return Integer.parseInt(String.valueOf(key));
        }
    }

    /**
     * アダプティーとなるバインドソースを設定します。
     * 
     * @param adaptee adaptee
     */
    protected void setAdaptee(VRBindSource adaptee) {
        this.adaptee = adaptee;
    }
}