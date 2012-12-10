package jp.nichicom.ac.component.dnd;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureListener;

/**
 * ドラッグ可能であることをあらわすインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see Transferable
 * @see DragGestureListener
 */
public interface ACDraggable extends DragGestureListener, Transferable {
    /**
     * ドラッグを許容するデータ型 を返します。
     * <p>
     * デフォルトでは<code>DataFlavor.stringFlavor</code>が指定されます。
     * </p>
     * 
     * @return ドラッグを許容するデータ型
     */
    public DataFlavor[] getAllowdDataFlavors();

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
    public int getAllowedDragAction();

    /**
     * ドラッグ時のカーソル を返します。
     * <p>
     * デフォルトでは<code>DragSource.DefaultCopyDrop</code>が指定されます。
     * </p>
     * 
     * @return ドラッグ時のカーソル
     */
    public Cursor getDragCursor();

    /**
     * ドラッグを許容するデータ型 を設定します。
     * <p>
     * デフォルトでは<code>DataFlavor.stringFlavor</code>が指定されます。
     * </p>
     * 
     * @param allowdDataFlavor ドラッグを許容するデータ型
     */
    public void setAllowdDataFlavors(DataFlavor[] allowdDataFlavor);

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
    public void setAllowedDragAction(int actions);

    /**
     * ドラッグ時のカーソル を設定します。
     * <p>
     * デフォルトでは<code>DragSource.DefaultCopyDrop</code>が指定されます。
     * </p>
     * 
     * @param dragCursor ドラッグ時のカーソル
     */
    public void setDragCursor(Cursor dragCursor);

}
