package jp.nichicom.ac.component.dnd.event;

import java.awt.dnd.DragGestureEvent;
import java.util.EventListener;

/**
 * ドラッグイベントリスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public interface ACDraggableListener extends EventListener{
    /**
     * ドラッグ開始イベントです。
     * @param event イベント情報
     */
    public void beginDrag(DragGestureEvent event);
    
    
}
