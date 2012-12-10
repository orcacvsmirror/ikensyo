package jp.nichicom.ac.component.dnd;

import jp.nichicom.ac.component.dnd.event.ACDroppableListener;

/**
 * �h���b�v�\�ł��邱�Ƃ�����킷�C���^�[�t�F�[�X�ł��B
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
     * �h���b�v�C�x���g���X�i��ǉ����܂��B
     * 
     * @param l �h���b�v�C�x���g���X�i
     */
    public void addDroppableListener(ACDroppableListener l);

    /**
     * �h���b�v�C�x���g���X�i���폜���܂��B
     * 
     * @param l �h���b�v�C�x���g���X�i
     */
    public void removeDroppableListener(ACDroppableListener l);
    
}
