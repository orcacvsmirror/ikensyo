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
import java.io.IOException;

import javax.swing.Icon;

import jp.nichicom.vr.component.VRLabel;

/**
 * ドラッグ可能なラベルです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRLabel
 * @see Transferable
 * @see DragGestureListener
 */
public class ACDraggableLabel extends VRLabel implements ACDraggable {
    private DataFlavor[] allowdDataFlavor;
    private Cursor dragCursor;
    private DragGestureRecognizer dragGestureRecognizer;

    /**
     * Creates a <code>JLabel</code> instance with no image and with an empty
     * string for the title. The label is centered vertically in its display
     * area. The label's contents, once set, will be displayed on the leading
     * edge of the label's display area.
     */
    public ACDraggableLabel() {
        super();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image. The
     * label is centered vertically and horizontally in its display area.
     * 
     * @param image The image to be displayed by the label.
     */
    public ACDraggableLabel(Icon image) {
        super(image);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACDraggableLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text. The
     * label is aligned against the leading edge of its display area, and
     * centered vertically.
     * 
     * @param text The text to be displayed by the label.
     */
    public ACDraggableLabel(String text) {
        super(text);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text, image,
     * and horizontal alignment. The label is centered vertically in its display
     * area. The text is on the trailing edge of the image.
     * 
     * @param text The text to be displayed by the label.
     * @param icon The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACDraggableLabel(String text, Icon image, int horizontalAlignment) {
        super(text, image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param text The text to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACDraggableLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public void dragGestureRecognized(DragGestureEvent e) {
        // ドラッグのジェスチャが認識された。

        // DragSourceActioのアクションならドラッグを開始する
        if ((e.getDragAction() & getAllowedDragAction()) != 0) {
            e.startDrag(getDragCursor(), this);
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
        return getModel();
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

}
