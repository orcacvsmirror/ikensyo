package jp.nichicom.ac.component.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

/**
 * 基底データ搬送クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACTransferHandler extends TransferHandler {

    /**
     * Constructs a transfer handler that can transfer a Java Bean property from
     * one component to another via the clipboard or a drag and drop operation.
     * 
     * @param property the name of the property to transfer; this can be
     *            <code>null</code> if there is no property associated with
     *            the transfer handler (a subclass that performs some other kind
     *            of transfer, for example)
     */
    public ACTransferHandler(String property) {
        super(property);
    }

    /**
     * Convenience constructor for subclasses.
     */
    public ACTransferHandler() {
        super();
    }

    public boolean canImport(JComponent comp, DataFlavor flavors[]) {
        // XXX just testing
        return true;
    }

    public boolean importData(JComponent comp, Transferable t) {
        // XXX
        return true;
    }

//    protected void exportDone(JComponent source, Transferable data, int action) {
//
//        super.exportDone(source, data, action);
//    }
//
//    public Icon getVisualRepresentation(Transferable t) {
//
//        return super.getVisualRepresentation(t);
//    }

    protected Transferable createTransferable(JComponent c) {
        return getSupportedTransferData(c);
    }

    /**
     * サポートしている搬送元コンポーネントごとに同名メソッドをoverloadして値を返します。
     * <p>
     * 該当するoverloadがない場合、nullを返します。
     * </p>
     * 
     * @param c 搬送元コンポーネント
     * @return 搬送体
     * @throws UnsupportedFlavorException サポート対象外例外
     */
    protected Transferable getSupportedTransferData(JComponent c){
        return null;
    }

    /**
     * サポートしている搬送元コンポーネントごとに同名メソッドをoverloadして値を返します。
     * <p>
     * JList用の搬送処理です。
     * </p>
     * 
     * @param c 搬送元コンポーネント
     * @return 搬送体
     * @throws UnsupportedFlavorException サポート対象外例外
     */
    protected Transferable getSupportedTransferData(JList c){
        Object[] values = c.getSelectedValues();
        if (values == null || values.length == 0) {
            return null;
        }
        return createTransferable(values);
    }
    
//    /**
//     * XXX the move/copy decision depends on the role of the list view
//     */
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }

    /**
     * データ搬送体を生成して返します。
     * @param values 搬送データ
     * @return 搬送体
     */
    protected Transferable createTransferable(Object[] values){
        return new StringSelection(String.valueOf(values));
    }
}
