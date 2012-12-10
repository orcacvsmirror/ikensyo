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
 * �h���b�O�\�ȃ{�^���ł��B
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
     * �h���b�O�f�[�^ ��Ԃ��܂��B
     * @return �h���b�O�f�[�^
     */
    public Object getDragData() {
        return dragData;
    }

    /**
     * �h���b�O�f�[�^ ��ݒ肵�܂��B
     * @param dragData �h���b�O�f�[�^
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
        // �h���b�O�̃W�F�X�`�����F�����ꂽ�B

        // DragSourceActio�̃A�N�V�����Ȃ�h���b�O���J�n����
        if ((e.getDragAction() & getAllowedDragAction()) != 0) {
            fireBeginDrag(e);
            e.startDrag(getDragCursor(), this, this);
        }
    }

    /**
     * �h���b�O�����e����f�[�^�^ ��Ԃ��܂��B
     * <p>
     * �f�t�H���g�ł�<code>DataFlavor.javaJVMLocalObjectMimeType</code>���w�肳��܂��B
     * </p>
     * 
     * @return �h���b�O�����e����f�[�^�^
     */
    public DataFlavor[] getAllowdDataFlavors() {
        return allowdDataFlavor;
    }

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
    public int getAllowedDragAction() {
        if (getDragGestureRecognizer() != null) {
            return getDragGestureRecognizer().getSourceActions();
        }
        return DnDConstants.ACTION_NONE;
    }

    /**
     * �h���b�O���̃J�[�\�� ��Ԃ��܂��B
     * <p>
     * �f�t�H���g�ł�<code>DragSource.DefaultCopyDrop</code>���w�肳��܂��B
     * </p>
     * 
     * @return �h���b�O���̃J�[�\��
     */
    public Cursor getDragCursor() {
        return dragCursor;
    }

    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {
        // �f�[�^�̒��g��Transfer�C���^�[�t�F�[�X����ēn���B
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
     * �h���b�O�����e����f�[�^�^ ��ݒ肵�܂��B
     * <p>
     * �f�t�H���g�ł�<code>DataFlavor.javaJVMLocalObjectMimeType</code>���w�肳��܂��B
     * </p>
     * 
     * @param allowdDataFlavor �h���b�O�����e����f�[�^�^
     */
    public void setAllowdDataFlavors(DataFlavor[] allowdDataFlavor) {
        this.allowdDataFlavor = allowdDataFlavor;
    }

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
    public void setAllowedDragAction(int actions) {
        if (getDragGestureRecognizer() != null) {
            getDragGestureRecognizer().setSourceActions(actions);
        }
    }

    /**
     * �h���b�O���̃J�[�\�� ��ݒ肵�܂��B
     * <p>
     * �f�t�H���g�ł�<code>DragSource.DefaultCopyDrop</code>���w�肳��܂��B
     * </p>
     * 
     * @param dragCursor �h���b�O���̃J�[�\��
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
            // �h���b�O�\�[�X����h���b�O��F�����Ă����l�����B
            setDragGestureRecognizer(ds.createDefaultDragGestureRecognizer(
                    this, DnDConstants.ACTION_COPY_OR_MOVE, this));

        }
    }

    /**
     * �h���b�O�W�F�X�`�����X�i��ǉ����܂��B
     * 
     * @param dgl �h���b�O�W�F�X�`�����X�i
     * @throws TooManyListenersException ������O
     */
    public void addDragGestureListener(DragGestureListener dgl)
            throws TooManyListenersException {
        if (getDragGestureRecognizer() != null) {
            getDragGestureRecognizer().addDragGestureListener(dgl);
        }
    }

    /**
     * �h���b�O�W�F�X�`�����X�i���폜���܂��B
     * 
     * @param dgl �h���b�O�W�F�X�`�����X�i
     */
    public void removeDragGestureListener(DragGestureListener dgl) {
        if (getDragGestureRecognizer() != null) {
            getDragGestureRecognizer().removeDragGestureListener(dgl);
        }
    }

    /**
     * ���e����h���b�O�f�[�^�`���𐶐����ĕԂ��܂��B
     * 
     * @return ���e����h���b�O�f�[�^�`��
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
     * �h���b�O�Ǘ��N���X ��Ԃ��܂��B
     * 
     * @return �h���b�O�Ǘ��N���X
     */
    protected DragGestureRecognizer getDragGestureRecognizer() {
        return dragGestureRecognizer;
    }

    /**
     * �h���b�O�Ǘ��N���X ��ݒ肵�܂��B
     * 
     * @param dragGestureRecognizer �h���b�O�Ǘ��N���X
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
     * �h���b�O�C�x���g���X�i��ǉ����܂��B
     * 
     * @param l �h���b�O�C�x���g���X�i
     */
    public void addDraggableListener(ACDraggableListener l) {
        this.listenerList.add(ACDraggableListener.class, l);
    }

    /**
     * �h���b�O�C�x���g���X�i���폜���܂��B
     * 
     * @param l �h���b�O�C�x���g���X�i
     */
    public void removeDraggableListener(ACDraggableListener l) {
        this.listenerList.remove(ACDraggableListener.class, l);
    }

    /**
     * beginDrag�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireBeginDrag(DragGestureEvent e) {
        ACDraggableListener[] listeners = getDraggableEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].beginDrag(e);
        }
    }

    /**
     * �h���b�O�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �h���b�O�C�x���g���X�i
     */
    public synchronized ACDraggableListener[] getDraggableEventListeners() {
        return (ACDraggableListener[]) (getListeners(ACDraggableListener.class));
    }

}
