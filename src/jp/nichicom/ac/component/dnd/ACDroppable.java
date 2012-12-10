package jp.nichicom.ac.component.dnd;

import jp.nichicom.ac.component.dnd.event.ACDroppableListener;

/**
 * ドロップ可能であることをあらわすインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACDroppableListener
 */

public interface ACDroppable {
    /**
     * ドロップイベントリスナを追加します。
     * 
     * @param l ドロップイベントリスナ
     */
    public void addDroppableListener(ACDroppableListener l);

    /**
     * ドロップイベントリスナを削除します。
     * 
     * @param l ドロップイベントリスナ
     */
    public void removeDroppableListener(ACDroppableListener l);
    
}
