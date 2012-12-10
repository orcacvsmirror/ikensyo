package jp.nichicom.ac.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;

/**
 * リストモデル用のアダプタです。
 * <p>
 * コンテナのaddメソッドを擬態します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRListModelAdapter
 */
public class ACListModelAdapter extends VRListModelAdapter {
    /**
     * コンストラクタです。
     */
    public ACListModelAdapter() {
        super(new VRArrayList());
    }

    /**
     * コンストラクタです。
     * 
     * @param adaptee アダプティーとなるバインドソース
     */
    public ACListModelAdapter(VRBindSource adaptee) {
        super(adaptee);
    }

    /**
     * 項目を追加します。
     * 
     * @param data 項目
     * @param dummy ダミー引数
     */
    public void add(Object data, Object dummy) {
        add(data);
    }

    /**
     * 項目を追加します。
     * 
     * @param data 項目
     */
    public void add(Object data) {
        super.addData(data);
    }

}
