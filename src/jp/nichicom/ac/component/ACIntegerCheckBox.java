package jp.nichicom.ac.component;

import java.text.ParseException;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.event.VRBindEvent;

/**
 * �l��Integer�ŊǗ�����`�F�b�N�{�b�N�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACCheckBox
 */
public class ACIntegerCheckBox extends ACCheckBox {
    protected int selectValue = 1;
    protected int unSelectValue = 0;

    /**
     * Creates an initially unselected check box button with no text, no icon.
     */
    public ACIntegerCheckBox() {
        super();
    }

    /**
     * Creates a check box where properties are taken from the Action supplied.
     * 
     * @since 1.3
     */
    public ACIntegerCheckBox(Action a) {
        super(a);
    }

    /**
     * Creates an initially unselected check box with an icon.
     * 
     * @param icon the Icon image to display
     */
    public ACIntegerCheckBox(Icon icon) {
        super(icon);
    }

    /**
     * Creates a check box with an icon and specifies whether or not it is
     * initially selected.
     * 
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */

    public ACIntegerCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * Creates an initially unselected check box with text.
     * 
     * @param text the text of the check box.
     */

    public ACIntegerCheckBox(String text) {
        super(text);
    }

    /**
     * Creates a check box with text and specifies whether or not it is
     * initially selected.
     * 
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public ACIntegerCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * Creates an initially unselected check box with the specified text and
     * icon.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     */
    public ACIntegerCheckBox(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a check box with text and icon, and specifies whether or not it
     * is initially selected.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public ACIntegerCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    public void applySource() throws ParseException {
        Integer val = new Integer(getValue());

        if (VRBindPathParser.set(getBindPath(), getSource(), val)) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindSource() throws ParseException {
        Object obj = VRBindPathParser.get(getBindPath(), source);

        int val;
        if (obj == null) {
            return;
        } else if (obj instanceof Integer) {
            val = ((Integer) obj).intValue();
        } else {
            val = Integer.valueOf(String.valueOf(obj)).intValue();
        }
        setValue(val);
        fireBindSource(new VRBindEvent(this));
    }

    public Object createSource() {
        return new Integer(0);
    }

    /**
     * �I����Ԃ�\���l��Ԃ��܂��B
     * 
     * @return �I����Ԃ�\���l
     */
    public int getSelectValue() {
        return selectValue;
    }

    /**
     * ���I����Ԃ�\���l��Ԃ��܂��B
     * 
     * @return ���I����Ԃ�\���l
     */
    public int getUnSelectValue() {
        return unSelectValue;
    }

    /**
     * �I����Ԃ�\���l��ݒ肵�܂��B
     * 
     * @param selectValue �I����Ԃ�\���l
     */
    public void setSelectValue(int selectValue) {
        this.selectValue = selectValue;
    }

    /**
     * ���I����Ԃ�\���l��ݒ肵�܂��B
     * 
     * @param unSelectValue ���I����Ԃ�\���l
     */
    public void setUnSelectValue(int unSelectValue) {
        this.unSelectValue = unSelectValue;
    }

    /**
     * ���݂̑I����ԂɑΉ����������l��Ԃ��܂��B
     * <p>
     * �I����ԂȂ��<code>getSelectValue</code>�A���I����ԂȂ��<code>getUnSelectValue</code>���Ԃ�܂��B
     * </p>
     * 
     * @return ���݂̑I����ԂɑΉ����������l
     */
    public int getValue() {
        if (super.isSelected()) {
            return getSelectValue();
        } else {
            return getUnSelectValue();
        }
    }

    /**
     * �����l�ɑΉ������I����Ԃɐݒ肵�܂��B
     * <p>
     * <code>getSelectValue</code>�Ɠ����Ȃ�ΑI����ԁA����ȊO�͖��I����ԂɂȂ�܂��B
     * </p>
     * 
     * @param value �I����Ԃ����ʂ��鐮���l
     */
    public void setValue(int value) {
        super.setSelected(value == getSelectValue());
    }

}
