package jp.nichicom.ac.component;

import java.text.ParseException;

import javax.swing.Icon;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.event.VRBindEvent;

/**
 * ��ԃA�C�R�����x���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACStateIconLabel extends ACLabel {
    /**
     * ����(�ύX����)������킷��Ԓ萔�ł��B
     */
    public static final int STATE_NONE = 0;
    /**
     * �V�K�ǉ�������킷��Ԓ萔�ł��B
     */
    public static final int STATE_INSERT = 1;
    /**
     * �����̍X�V������킷��Ԓ萔�ł��B
     */
    public static final int STATE_UPDATE = 2;
    /**
     * �����̍폜������킷��Ԓ萔�ł��B
     */
    public static final int STATE_DELETE = 4;
    /**
     * �V�K�ǉ���̍X�V������킷��Ԓ萔�ł��B
     */
    public static final int STATE_INSERTED_UPDATE = STATE_INSERT | STATE_UPDATE;
    /**
     * �����X�V��̍폜������킷��Ԓ萔�ł��B
     */
    public static final int STATE_UPDATED_DELETE = STATE_UPDATE | STATE_DELETE;

    private int state;
    private String insertIconPath = ACConstants.ICON_PATH_STATE_INSERT_16;
    private String updateIconPath = ACConstants.ICON_PATH_STATE_UPDATE_16;
    private String deleteIconPath = ACConstants.ICON_PATH_STATE_DELETE_16;

    /**
     * ��� ��Ԃ��܂��B
     * 
     * @return ���
     * @see STATE_NONE
     * @see STATE_INSERT
     * @see STATE_UPDATE
     * @see STATE_DELETE
     * @see STATE_INSERTED_UPDATE
     * @see STATE_UPDATED_DELETE
     */
    public int getState() {
        return state;
    }

    /**
     * ��� ��ݒ肵�܂��B
     * 
     * @param state ���
     * @see STATE_NONE
     * @see STATE_INSERT
     * @see STATE_UPDATE
     * @see STATE_DELETE
     * @see STATE_INSERTED_UPDATE
     * @see STATE_UPDATED_DELETE
     */
    public void setState(int state) {
        this.state = state;
        switch (state) {
        case STATE_NONE:
            setIconPath(null);
            break;
        case STATE_INSERT:
        case STATE_INSERTED_UPDATE:
            setIconPath(getInsertIconPath());
            break;
        case STATE_UPDATE:
            setIconPath(getUpdateIconPath());
            break;
        case STATE_DELETE:
        case STATE_UPDATED_DELETE:
            setIconPath(getDeleteIconPath());
            break;
        default:
            setIconPath(null);
            break;
        }
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * Creates a <code>JLabel</code> instance with no image and with an empty
     * string for the title. The label is centered vertically in its display
     * area. The label's contents, once set, will be displayed on the leading
     * edge of the label's display area.
     */
    public ACStateIconLabel() {
        super();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image. The
     * label is centered vertically and horizontally in its display area.
     * 
     * @param image The image to be displayed by the label.
     */
    public ACStateIconLabel(Icon image) {
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
    public ACStateIconLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text. The
     * label is aligned against the leading edge of its display area, and
     * centered vertically.
     * 
     * @param text The text to be displayed by the label.
     */
    public ACStateIconLabel(String text) {
        super(text);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text, image,
     * and horizontal alignment. The label is centered vertically in its display
     * area. The text is on the trailing edge of the image.
     * 
     * @param text The text to be displayed by the label.
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACStateIconLabel(String text, Icon image, int horizontalAlignment) {
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
    public ACStateIconLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    /**
     * �폜��Ԃ�\���A�C�R���p�X ��Ԃ��܂��B
     * 
     * @return �폜��Ԃ�\���A�C�R���p�X
     */
    public String getDeleteIconPath() {
        return deleteIconPath;
    }

    /**
     * �폜��Ԃ�\���A�C�R���p�X ��ݒ肵�܂��B
     * 
     * @param deleteIconPath �폜��Ԃ�\���A�C�R���p�X
     */
    public void setDeleteIconPath(String deleteIconPath) {
        this.deleteIconPath = deleteIconPath;
    }

    /**
     * �ǉ���Ԃ�\���A�C�R���p�X ��Ԃ��܂��B
     * 
     * @return �ǉ���Ԃ�\���A�C�R���p�X
     */
    public String getInsertIconPath() {
        return insertIconPath;
    }

    /**
     * �ǉ���Ԃ�\���A�C�R���p�X ��ݒ肵�܂��B
     * 
     * @param insertIconPath �ǉ���Ԃ�\���A�C�R���p�X
     */
    public void setInsertIconPath(String insertIconPath) {
        this.insertIconPath = insertIconPath;
    }

    /**
     * �X�V��Ԃ�\���A�C�R���p�X ��Ԃ��܂��B
     * 
     * @return �X�V��Ԃ�\���A�C�R���p�X
     */
    public String getUpdateIconPath() {
        return updateIconPath;
    }

    /**
     * �X�V��Ԃ�\���A�C�R���p�X ��ݒ肵�܂��B
     * 
     * @param updateIconPath �X�V��Ԃ�\���A�C�R���p�X
     */
    public void setUpdateIconPath(String updateIconPath) {
        this.updateIconPath = updateIconPath;
    }

    public Object createSource() {
        return new Integer(STATE_NONE);
    }

    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), getSource())) {
            return;
        }
        Object obj = VRBindPathParser.get(getBindPath(), getSource());
        int value;
        if (obj instanceof Integer) {
            value = ((Integer) obj).intValue();
        } else if(obj==null) {
            value = STATE_NONE;
        } else {
            value = Integer.parseInt(String.valueOf(obj));
        }
        setState(value);
        fireBindSource(new VRBindEvent(this));
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), new Integer(
                getState()))) {
            fireApplySource(new VRBindEvent(this));
        }
    }
}
