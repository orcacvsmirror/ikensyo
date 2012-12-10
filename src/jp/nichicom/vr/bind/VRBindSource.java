/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;

/**
 * バインド用のデータインターフェースです。
 * <p>
 * バインド機構におけるデータ格納クラスが実装します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindPathParser
 * @see VRBindEventListener
 * @see VRArrayList
 * @see VRHashMap
 */
public interface VRBindSource {

    /**
     * バインドソースイベントリスナを追加します。
     * 
     * @param listener バインドソースイベントリスナ
     */
    public void addBindSourceEventListener(VRBindSourceEventListener listener);

    /**
     * 要素を追加します。
     * 
     * @param data 要素
     */
    public void addData(Object data);

    /**
     * 保持要素集合をクリアします。
     */
    public void clearData();

    /**
     * 主要素を返します。
     * 
     * @return 要素
     */
    public Object getData();

    /**
     * 指定番号の要素を返します。
     * 
     * @param index 要素番号
     * @return 要素
     */
    public Object getData(int index);

    /**
     * 指定キーの要素を返します。
     * 
     * @param key 要素キー
     * @return 要素
     */
    public Object getData(Object key);

    /**
     * 保持要素数を返します。
     * 
     * @return 保持要素数
     */
    public int getDataSize();

    /**
     * バインドソースイベントリスナを削除します。
     * 
     * @param listener バインドソースイベントリスナ
     */
    public void removeBindSourceEventListener(VRBindSourceEventListener listener);

    /**
     * 指定番号の要素を削除します。
     * 
     * @param index 削除対象の要素番号
     */
    public void removeData(int index);

    /**
     * 指定キーの要素を削除します。
     * 
     * @param key 削除対象の要素キー
     */
    public void removeData(Object key);

    /**
     * 指定番号の要素を設定します。
     * 
     * @param index 要素番号
     * @param data 要素
     */
    public void setData(int index, Object data);

    /**
     * 主要素を設定します。
     * 
     * @param data 要素
     */
    public void setData(Object data);

    /**
     * 指定キーの要素を設定します。
     * 
     * @param key 要素キー
     * @param data 要素
     */
    public void setData(Object key, Object data);

}