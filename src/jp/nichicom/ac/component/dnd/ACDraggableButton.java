package jp.nichicom.ac.component.dnd;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;
import java.util.TooManyListenersException;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.dnd.event.ACDraggableListener;
import jp.nichicom.vr.component.VRButton;

/**
 * ドラッグ可能なボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRButton
 * @see ACDraggable
 * @see Transferable
 * @see DragGestureListener
 */
public class ACDraggableButton extends ACButton implements ACDraggable,
        DragSourceListener {
    private DataFlavor[] allowdDataFlavor;
    private Cursor dragCursor;
    private DragGestureRecognizer dragGestureRecognizer;
    private Object dragData;

    /**
     * ドラッグデータ を返します。
     * @return ドラッグデータ
     */
    public Object getDragData() {
        return dragData;
    }

    /**
     * ドラッグデータ を設定します。
     * @param dragData ドラッグデータ
     */
    public void setDragData(Object dragData) {
        this.dragData = dragData;
    }

    /**
     * Creates a button with no set text or icon.
     */
    public ACDraggableButton() {
        super();
    }

    /**
     * Creates a button where properties are taken from the <code>Action</code>
     * supplied.
     * 
     * @param a the <code>Action</code> used to specify the new button
     * @since 1.3
     */
    public ACDraggableButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with an icon.
     * 
     * @param icon the Icon image to display on the button
     */
    public ACDraggableButton(Icon icon) {
        super(icon);
    }

    /**
     * Creates a button with text.
     * 
     * @param text the text of the button
     */
    public ACDraggableButton(String text) {
        super(text);
    }

    /**
     * Creates a button with initial text and an icon.
     * 
     * @param text the text of the button
     * @param icon the Icon image to display on the button
     */
    public ACDraggableButton(String text, Icon icon) {
        super(text, icon);
    }

    public void dragGestureRecognized(DragGestureEvent e) {
        // ドラッグのジェスチャが認識された。

        // DragSourceActioのアクションならドラッグを開始する
        if ((e.getDragAction() & getAllowedDragAction()) != 0) {
            fireBeginDrag(e);
            e.startDrag(getDragCursor(), this, this);
        }
    }

    /**
     * ドラッグを許容するデータ型 を返します。
     * <p>
     * デフォルトでは<code>DataFlavor.javaJVMLocalObjectMimeType</code>が指定されます。
     * </p>
     * 
     * @return ドラッグを許容するデータ型
     */
    public DataFlavor[] getAllowdDataFlavors() {
        return allowdDataFlavor;
    }

    /**
     * ドラッグ時のアクションを返します。
     * <p>
     * デフォルトでは<code>DnDConstants.ACTION_COPY_OR_MOVE</code>が指定されます。
     * </p>
     * <p>
     * <code>
     * DnDConstants.ACTION_COPY : 複写</br>
     * DnDConstants.ACTION_COPY_OR_MOVE : 複写もしくは移動</br> 
     * DnDConstants.ACTION_LINK : リンク</br>
     * DnDConstants.ACTION_MOVE : 移動</br>
     * DnDConstants.ACTION_NONE : なし</br>
     * DnDConstants.ACTION_REFERENCE : 参照
     * </code>
     * </p>
     * 
     * @return ドラッグ時のアクション
     */
    public int getAllowedDragAction() {
        if (getDragGestureRecognizer() != null) {
            return getDragGestureRecognizer().getSourceActions();
        }
        return DnDConstants.ACTION_NONE;
    }

    /**
     * ドラッグ時のカーソル を返します。
     * <p>
     * デフォルトでは<code>DragSource.DefaultCopyDrop</code>が指定されます。
     * </p>
     * 
     * @return ドラッグ時のカーソル
     */
    public Cursor getDragCursor() {
        return dragCursor;
    }

    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        // データの中身をTransferインターフェースを介して渡す。
        return getDragData();
    }

    public DataFlavor[] getTransferDataFlavors() {
        return getAllowdDataFlavors();
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors = getAllowdDataFlavors();
        if (flavors != null) {
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
     * ドラッグを許容するデータ型 を設定します。
     * <p>
     * デフォルトでは<code>DataFlavor.javaJVMLocalObjectMimeType</code>が指定されます。
     * </p>
     * 
     * @param allowdDataFlavor ドラッグを許容するデータ型
     */
    public void setAllowdDataFlavors(DataFlavor[] allowdDataFlavor) {
        this.allowdDataFlavor = allowdDataFlavor;
    }

    /**
     * ドラッグ時のアクションを設定します。
     * <p>
     * デフォルトでは<code>DnDConstants.ACTION_COPY_OR_MOVE</code>が指定されます。
     * </p>
     * <p>
     * <code>
     * DnDConstants.ACTION_COPY : 複写</br>
     * DnDConstants.ACTION_COPY_OR_MOVE : 複写もしくは移動</br> 
     * DnDConstants.ACTION_LINK : リンク</br>
     * DnDConstants.ACTION_MOVE : 移動</br>
     * DnDConstants.ACTION_NONE : なし</br>
     * DnDConstants.ACTION_REFERENCE : 参照
     * </code>
     * </p>
     * 
     * @param actions ドラッグ時のアクション
     */
    public void setAllowedDragAction(int actions) {
        if (getDragGestureRecognizer() != null) {
            getDragGestureRecognizer().setSourceActions(actions);
        }
    }

    /**
     * ドラッグ時のカーソル を設定します。
     * <p>
     * デフォルトでは<code>DragSource.DefaultCopyDrop</code>が指定されます。
     * </p>
     * 
     * @param dragCursor ドラッグ時のカーソル
     */
    public void setDragCursor(Cursor dragCursor) {
        this.dragCursor = dragCursor;
    }

    protected void initComponent() {
        super.initComponent();

        setAllowdDataFlavors(createAllowdDataFlavors());
        setDragCursor(DragSource.DefaultCopyDrop);

        DragSource ds = DragSource.getDefaultDragSource();
        if (ds != null) {
            // ドラッグソースからドラッグを認識してくれる人を作る。
            setDragGestureRecognizer(ds.createDefaultDragGestureRecognizer(
                    this, DnDConstants.ACTION_COPY_OR_MOVE, this));

        }
    }

    /**
     * ドラッグジェスチャリスナを追加します。
     * 
     * @param dgl ドラッグジェスチャリスナ
     * @throws TooManyListenersException 処理例外
     */
    public void addDragGestureListener(DragGestureListener dgl)
            throws TooManyListenersException {
        if (getDragGestureRecognizer() != null) {
            getDragGestureRecognizer().addDragGestureListener(dgl);
        }
    }

    /**
     * ドラッグジェスチャリスナを削除します。
     * 
     * @param dgl ドラッグジェスチャリスナ
     */
    public void removeDragGestureListener(DragGestureListener dgl) {
        if (getDragGestureRecognizer() != null) {
            getDragGestureRecognizer().removeDragGestureListener(dgl);
        }
    }

    /**
     * 許容するドラッグデータ形式を生成して返します。
     * 
     * @return 許容するドラッグデータ形式
     */
    protected DataFlavor[] createAllowdDataFlavors() {
        try {
            return new DataFlavor[] { new DataFlavor(
                    DataFlavor.javaJVMLocalObjectMimeType), };
        } catch (Exception ex) {
            return new DataFlavor[] {};
        }

    }

    /**
     * ドラッグ管理クラス を返します。
     * 
     * @return ドラッグ管理クラス
     */
    protected DragGestureRecognizer getDragGestureRecognizer() {
        return dragGestureRecognizer;
    }

    /**
     * ドラッグ管理クラス を設定します。
     * 
     * @param dragGestureRecognizer ドラッグ管理クラス
     */
    protected void setDragGestureRecognizer(
            DragGestureRecognizer dragGestureRecognizer) {
        this.dragGestureRecognizer = dragGestureRecognizer;
    }

    public void dragEnter(DragSourceDragEvent dsde) {
    }

    public void dragOver(DragSourceDragEvent dsde) {
    }

    public void dropActionChanged(DragSourceDragEvent dsde) {
    }

    public void dragDropEnd(DragSourceDropEvent dsde) {
        getModel().setPressed(false);
    }

    public void dragExit(DragSourceEvent dse) {
    }

    /**
     * ドラッグイベントリスナを追加します。
     * 
     * @param l ドラッグイベントリスナ
     */
    public void addDraggableListener(ACDraggableListener l) {
        this.listenerList.add(ACDraggableListener.class, l);
    }

    /**
     * ドラッグイベントリスナを削除します。
     * 
     * @param l ドラッグイベントリスナ
     */
    public void removeDraggableListener(ACDraggableListener l) {
        this.listenerList.remove(ACDraggableListener.class, l);
    }

    /**
     * beginDragイベントを発火します。
     * 
     * @param e イベント情報
     */
    protected void fireBeginDrag(DragGestureEvent e) {
        ACDraggableListener[] listeners = getDraggableEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].beginDrag(e);
        }
    }

    /**
     * ドラッグイベントリスナを返します。
     * 
     * @return ドラッグイベントリスナ
     */
    public synchronized ACDraggableListener[] getDraggableEventListeners() {
        return (ACDraggableListener[]) (getListeners(ACDraggableListener.class));
    }

}
