package jp.nichicom.ac.component.dnd;

import java.awt.Cursor;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureListener;

/**
 * �h���b�O�\�ł��邱�Ƃ�����킷�C���^�[�t�F�[�X�ł��B
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
     * �h���b�O�����e����f�[�^�^ ��Ԃ��܂��B
     * <p>
     * �f�t�H���g�ł�<code>DataFlavor.stringFlavor</code>���w�肳��܂��B
     * </p>
     * 
     * @return �h���b�O�����e����f�[�^�^
     */
    public DataFlavor[] getAllowdDataFlavors();

    /**
     * �h���b�O���̃A�N�V������Ԃ��܂��B
     * <p>
     * �f�t�H���g�ł�<code>DnDConstants.ACTION_COPY_OR_MOVE</code>���w�肳��܂��B
     * </p>
     * <p>
     * <code>
     * DnDConstants.ACTION_COPY : ����</br>
     * DnDConstants.ACTION_COPY_OR_MOVE : ���ʂ������͈ړ�</br> 
     * DnDConstants.ACTION_LINK : �����N</br>
     * DnDConstants.ACTION_MOVE : �ړ�</br>
     * DnDConstants.ACTION_NONE : �Ȃ�</br>
     * DnDConstants.ACTION_REFERENCE : �Q��
     * </code>
     * </p>
     * 
     * @return �h���b�O���̃A�N�V����
     */
    public int getAllowedDragAction();

    /**
     * �h���b�O���̃J�[�\�� ��Ԃ��܂��B
     * <p>
     * �f�t�H���g�ł�<code>DragSource.DefaultCopyDrop</code>���w�肳��܂��B
     * </p>
     * 
     * @return �h���b�O���̃J�[�\��
     */
    public Cursor getDragCursor();

    /**
     * �h���b�O�����e����f�[�^�^ ��ݒ肵�܂��B
     * <p>
     * �f�t�H���g�ł�<code>DataFlavor.stringFlavor</code>���w�肳��܂��B
     * </p>
     * 
     * @param allowdDataFlavor �h���b�O�����e����f�[�^�^
     */
    public void setAllowdDataFlavors(DataFlavor[] allowdDataFlavor);

    /**
     * �h���b�O���̃A�N�V������ݒ肵�܂��B
     * <p>
     * �f�t�H���g�ł�<code>DnDConstants.ACTION_COPY_OR_MOVE</code>���w�肳��܂��B
     * </p>
     * <p>
     * <code>
     * DnDConstants.ACTION_COPY : ����</br>
     * DnDConstants.ACTION_COPY_OR_MOVE : ���ʂ������͈ړ�</br> 
     * DnDConstants.ACTION_LINK : �����N</br>
     * DnDConstants.ACTION_MOVE : �ړ�</br>
     * DnDConstants.ACTION_NONE : �Ȃ�</br>
     * DnDConstants.ACTION_REFERENCE : �Q��
     * </code>
     * </p>
     * 
     * @param actions �h���b�O���̃A�N�V����
     */
    public void setAllowedDragAction(int actions);

    /**
     * �h���b�O���̃J�[�\�� ��ݒ肵�܂��B
     * <p>
     * �f�t�H���g�ł�<code>DragSource.DefaultCopyDrop</code>���w�肳��܂��B
     * </p>
     * 
     * @param dragCursor �h���b�O���̃J�[�\��
     */
    public void setDragCursor(Cursor dragCursor);

}
