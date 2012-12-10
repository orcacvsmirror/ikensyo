/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import javax.swing.ComboBoxModel;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * バインドソース機構を実装したComboBoxModelのアダプタクラスです。
 * <p>
 * <code>VRBindSource</code> 形式のオブジェクトをComboBoxModelとして透過的に扱います。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRListModelAdapter
 * @see VRBindSource
 * @see VRBindSourceEventListener
 */
public class VRComboBoxModelAdapter extends VRListModelAdapter implements
        ComboBoxModel {

    protected Object selectedItem;

    /**
     * コンストラクタです。
     * 
     * @param adaptee アダプティーとなるバインドソース
     */
    public VRComboBoxModelAdapter(VRBindSource adaptee) {
        super(adaptee);
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object anItem) {
        if ((selectedItem != null && !selectedItem.equals(anItem))
                || selectedItem == null && anItem != null) {
            selectedItem = anItem;
            fireContentsChanged(this, -1, -1);
        }
    }

}