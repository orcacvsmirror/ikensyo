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
 * �h���b�O�\�ȃ��x���ł��B
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
        // �h���b�O�̃W�F�X�`�����F�����ꂽ�B

        // DragSourceActio�̃A�N�V�����Ȃ�h���b�O���J�n����
        if ((e.getDragAction() & getAllowedDragAction()) != 0) {
            e.startDrag(getDragCursor(), this);
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

}
