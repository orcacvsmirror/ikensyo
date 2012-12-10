/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import javax.swing.AbstractListModel;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEvent;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * バインドソース機構を実装したListModelのアダプタクラスです。
 * <p>
 * <code>VRBindSource</code> 形式のオブジェクトをListModelとして透過的に扱います。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractListModel
 * @see VRBindSource
 * @see VRBindSourceEventListener
 * @see VRBindSourceAdapter
 */
public class VRListModelAdapter extends AbstractListModel implements
        VRBindSource, VRBindSourceEventListener, VRBindSourceAdapter {
    protected VRBindSource adaptee;

    /**
     * コンストラクタです。
     */
    public VRListModelAdapter() {
    }

    /**
     * コンストラクタです。
     * 
     * @param adaptee アダプティーとなるバインドソース
     */
    public VRListModelAdapter(VRBindSource adaptee) {
        setAdaptee(adaptee);
    }

    public void addBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().addBindSourceEventListener(listener);
    }

    public void addData(Object data) {
        getAdaptee().addData(data);
    }

    public void addSource(VRBindSourceEvent e) {
//        int index = e.getIndex();
        int size = getSize();
        super.fireIntervalAdded(this, size-1, size);
    }

    public void changeSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        super.fireContentsChanged(this, index - 1, index);
    }

    public void clearData() {
        getAdaptee().clearData();
    }

    public void clearSource(VRBindSourceEvent e) {
        super.fireIntervalRemoved(this, 0, 0);
    }

    /**
     * アダプティーとなるバインドソースを返します。
     * 
     * @return アダプティー
     */
    public VRBindSource getAdaptee() {
        return adaptee;
    }

    public Object getData() {
        return getAdaptee().getData();
    }

    public Object getData(int index) {
        return getAdaptee().getData(index);
    }

    public Object getData(Object key) {
        return getAdaptee().getData(key);
    }

    public int getDataSize() {
        return getAdaptee().getDataSize();
    }

    public Object getElementAt(int index) {
        return getData(index);
    }

    public int getSize() {
        return getDataSize();
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

    public void removeSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        if (index >= 0) {
            super.fireIntervalRemoved(this, index, index);
        }
    }

    public void setData(int index, Object data) {
        getAdaptee().setData(index, data);
    }

    public void setData(Object data) {
        getAdaptee().setData(data);
    }

    public void setData(Object key, Object data) {
        setData(parseKey(key), data);
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
     * @param adaptee アダプティー
     */
    public void setAdaptee(VRBindSource adaptee) {
        if (this.adaptee != null) {
            //自身をソースのリスナとして登録
            this.adaptee.removeBindSourceEventListener(this);
        }
        this.adaptee = adaptee;
        if (adaptee != null) {
            //自身をソースのリスナとして登録
            adaptee.addBindSourceEventListener(this);
        }
    }
}