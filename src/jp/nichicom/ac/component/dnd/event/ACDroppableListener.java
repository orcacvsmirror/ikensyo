package jp.nichicom.ac.component.dnd.event;

import java.util.EventListener;

/**
 * �h���b�v�C�x���g���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/01
 */
public interface ACDroppableListener extends EventListener {
    /**
     * �h���b�v�����C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void dropSuccess(ACDropEvent e);

    /**
     * �h���b�v���s�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void dropReject(ACDropEvent e);

}
