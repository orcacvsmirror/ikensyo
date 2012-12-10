package jp.nichicom.ac.component.dnd.event;

import java.awt.dnd.DragGestureEvent;
import java.util.EventListener;

/**
 * �h���b�O�C�x���g���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public interface ACDraggableListener extends EventListener{
    /**
     * �h���b�O�J�n�C�x���g�ł��B
     * @param event �C�x���g���
     */
    public void beginDrag(DragGestureEvent event);
    
    
}
