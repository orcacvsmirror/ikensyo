package jp.nichicom.vr.component;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindModelEventListener;

/**
 * バインド機構を実装したリストボックスです。
 * <p>
 * モデルバインド機構も実装しています。
 * </p>
 * <p>
 * 選択モードがListSelectionModel.SINGLE_SELECTIONの場合は選択している単一オジェクトがbind対象となり、それ以外の選択モードならば選択しているオブジェクトの配列がbind対象となります。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see AbstractVRListBox
 */
public class VRListBox extends AbstractVRListBox {

    /**
     * Constructs a <code>JList</code> with an empty model.
     */
    public VRListBox() {
        super();
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified array. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the array of Objects to be loaded into the data model
     */
    public VRListBox(Object[] listData) {
        super(listData);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified <code>Vector</code>. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the <code>Vector</code> to be loaded into the data
     *            model
     */
    public VRListBox(Vector listData) {
        super(listData);
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified, non-<code>null</code> model. All <code>JList</code>
     * constructors delegate to this one.
     * 
     * @param dataModel the data model for this list
     * @exception IllegalArgumentException if <code>dataModel</code> is
     *                <code>null</code>
     */
    public VRListBox(ListModel dataModel) {
        super(dataModel);
    }
}
