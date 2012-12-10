package jp.nichicom.ac.component.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * 基底のフレーバ処理クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACTransfer implements Transferable {
    private DataFlavor[] supportedFlavors;
    private Object value;
    /**
     * コンストラクタです。
     */
    public ACTransfer() {
        super();
    }

    /**
     * コンストラクタです。
     * @param value 搬送データ
     */
    public ACTransfer(Object value) {
        setValue(value);
    }


    public DataFlavor[] getTransferDataFlavors() {
        if (supportedFlavors == null) {
            try {
                supportedFlavors = createSupportFlavors();
            } catch (ClassNotFoundException cnfe) {
                supportedFlavors = new DataFlavor[] {};
            }
        }
        return supportedFlavors;
    }

    /**
     * 搬送データ を返します。
     * 
     * @return value 搬送データ
     */
    public Object getValue() {
        return value;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors = getTransferDataFlavors();
        if ((flavor != null) && (flavors != null)) {
            int end = flavors.length;
            for (int i = 0; i < end; i++) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 搬送データ を設定します。
     * 
     * @param value 搬送データ
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * サポートしているフレーバクラスごとに同名メソッドをoverrideして値を返します。
     * <p>
     * 該当するoverloadがない場合、UnsupportedFlavorExceptionを発行します。
     * </p>
     * 
     * @param flavor フレーバ
     * @return データ
     * @throws UnsupportedFlavorException サポート対象外例外
     */
    protected Object getSupportedTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {
        return value;
    }

    public Object getTransferData(DataFlavor flavor) throws IOException,
            UnsupportedFlavorException {
        return getSupportedTransferData(flavor);
    }
    
    /**
     * サポート対象のフレーバクラス配列を返します。
     * 
     * @return フレーバクラス配列
     * @throws ClassNotFoundException クラス生成例外
     */
    protected DataFlavor[] createSupportFlavors() throws ClassNotFoundException {
        return new DataFlavor[] {  };
    }

}
