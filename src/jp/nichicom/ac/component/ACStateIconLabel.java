package jp.nichicom.ac.component;

import java.text.ParseException;

import javax.swing.Icon;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.event.VRBindEvent;

/**
 * 状態アイコンラベルです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACStateIconLabel extends ACLabel {
    /**
     * 既存(変更無し)をあらわす状態定数です。
     */
    public static final int STATE_NONE = 0;
    /**
     * 新規追加をあらわす状態定数です。
     */
    public static final int STATE_INSERT = 1;
    /**
     * 既存の更新をあらわす状態定数です。
     */
    public static final int STATE_UPDATE = 2;
    /**
     * 既存の削除をあらわす状態定数です。
     */
    public static final int STATE_DELETE = 4;
    /**
     * 新規追加後の更新をあらわす状態定数です。
     */
    public static final int STATE_INSERTED_UPDATE = STATE_INSERT | STATE_UPDATE;
    /**
     * 既存更新後の削除をあらわす状態定数です。
     */
    public static final int STATE_UPDATED_DELETE = STATE_UPDATE | STATE_DELETE;

    private int state;
    private String insertIconPath = ACConstants.ICON_PATH_STATE_INSERT_16;
    private String updateIconPath = ACConstants.ICON_PATH_STATE_UPDATE_16;
    private String deleteIconPath = ACConstants.ICON_PATH_STATE_DELETE_16;

    /**
     * 状態 を返します。
     * 
     * @return 状態
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
     * 状態 を設定します。
     * 
     * @param state 状態
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
     * 削除状態を表すアイコンパス を返します。
     * 
     * @return 削除状態を表すアイコンパス
     */
    public String getDeleteIconPath() {
        return deleteIconPath;
    }

    /**
     * 削除状態を表すアイコンパス を設定します。
     * 
     * @param deleteIconPath 削除状態を表すアイコンパス
     */
    public void setDeleteIconPath(String deleteIconPath) {
        this.deleteIconPath = deleteIconPath;
    }

    /**
     * 追加状態を表すアイコンパス を返します。
     * 
     * @return 追加状態を表すアイコンパス
     */
    public String getInsertIconPath() {
        return insertIconPath;
    }

    /**
     * 追加状態を表すアイコンパス を設定します。
     * 
     * @param insertIconPath 追加状態を表すアイコンパス
     */
    public void setInsertIconPath(String insertIconPath) {
        this.insertIconPath = insertIconPath;
    }

    /**
     * 更新状態を表すアイコンパス を返します。
     * 
     * @return 更新状態を表すアイコンパス
     */
    public String getUpdateIconPath() {
        return updateIconPath;
    }

    /**
     * 更新状態を表すアイコンパス を設定します。
     * 
     * @param updateIconPath 更新状態を表すアイコンパス
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
