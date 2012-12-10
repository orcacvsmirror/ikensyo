package jp.nichicom.ac.component;

import javax.swing.JRadioButton;

import jp.nichicom.vr.component.VRRadioButtonGroup;

/**
 * �Ή�����{�^���𐧌�\�ȃ��W�I���f���p�̍��ڃf�[�^�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRRadioButtonGroup
 */
public class ACRadioButtonItem {
    private int buttonIndex;
    private Object constraints;
    private VRRadioButtonGroup group;
    private Object text;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACRadioButtonItem() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param value �\������l
     */
    public ACRadioButtonItem(Object value) {
        super();
        setText(value);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param value �\������l
     * @param group �e�O���[�v
     * @param buttonIndex �Ή�����e�O���[�v���̃{�^���ԍ�
     */
    public ACRadioButtonItem(Object value, VRRadioButtonGroup group,
            int buttonIndex) {
        super();
        setText(value);
        setGroup(group);
        setButtonIndex(buttonIndex);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param group �e�O���[�v
     * @param buttonIndex �Ή�����e�O���[�v���̃{�^���ԍ�
     */
    public ACRadioButtonItem(VRRadioButtonGroup group, int buttonIndex) {
        super();
        setGroup(group);
        setButtonIndex(buttonIndex);
    }

    /**
     * �Ή�����e�O���[�v���̃{�^�� ��Ԃ��܂��B
     * 
     * @return button �Ή�����e�O���[�v���̃{�^��
     */
    public JRadioButton getButton() {
        if (getGroup() != null) {
            return getGroup().getButton(getButtonIndex());
        }
        return null;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���ԍ� ��Ԃ��܂��B
     * 
     * @return �Ή�����e�O���[�v���̃{�^���ԍ�
     */
    public int getButtonIndex() {
        return buttonIndex;
    }

    /**
     * �e���W�I�O���[�v�ւ̔z�u���@ ��Ԃ��܂��B
     * 
     * @return �e���W�I�O���[�v�ւ̔z�u���@
     */
    public Object getConstraints() {
        return constraints;
    }

    /**
     * �Ή�����e�O���[�v ��Ԃ��܂��B
     * 
     * @return �Ή�����e�O���[�v
     */
    public VRRadioButtonGroup getGroup() {
        return group;
    }

    /**
     * �\������l ��Ԃ��܂��B
     * 
     * @return �\������l
     */
    public Object getText() {
        return text;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���̗L����Ԃ�Ԃ��܂��B
     * 
     * @return �Ή�����e�O���[�v���̃{�^���̗L�����
     */
    public boolean isEnabled() {
        if (getButton() != null) {
            return getButton().isEnabled();
        }
        return true;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���̑I����Ԃ�Ԃ��܂��B
     * 
     * @return �Ή�����e�O���[�v���̃{�^���̑I�����
     */
    public boolean isSelected() {
        if (getButton() != null) {
            return getButton().isSelected();
        }
        return false;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���̕\���ۂ�Ԃ��܂��B
     * 
     * @return �Ή�����e�O���[�v���̃{�^���̕\����
     */
    public boolean isVisible() {
        if (getButton() != null) {
            return getButton().isVisible();
        }
        return true;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���ԍ� ��ݒ肵�܂��B
     * 
     * @param buttonIndex �Ή�����e�O���[�v���̃{�^���ԍ�
     */
    public void setButtonIndex(int buttonIndex) {
        this.buttonIndex = buttonIndex;
    }

    /**
     * �e���W�I�O���[�v�ւ̔z�u���@ ��ݒ肵�܂��B
     * 
     * @param constraints �e���W�I�O���[�v�ւ̔z�u���@
     */
    public void setConstraints(Object constraints) {
        this.constraints = constraints;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���̗L����Ԃ�ݒ肵�܂��B
     * 
     * @param enabled �Ή�����e�O���[�v���̃{�^���̗L�����
     */
    public void setEnabled(boolean enabled) {
        if (getButton() != null) {
            getButton().setEnabled(enabled);
        }
    }

    /**
     * �Ή�����e�O���[�v ��ݒ肵�܂��B
     * 
     * @param group �Ή�����e�O���[�v
     */
    public void setGroup(VRRadioButtonGroup group) {
        this.group = group;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���̑I����Ԃ�ݒ肵�܂��B
     * 
     * @param selected �Ή�����e�O���[�v���̃{�^���̑I�����
     */
    public void setSelected(boolean selected) {
        if (getButton() != null) {
            getButton().setSelected(selected);
        }
    }

    /**
     * �\������l ��ݒ肵�܂��B
     * 
     * @param value �\������l
     */
    public void setText(Object value) {
        this.text = value;
    }

    /**
     * �Ή�����e�O���[�v���̃{�^���̕\���ۂ�ݒ肵�܂��B
     * 
     * @param visible �Ή�����e�O���[�v���̃{�^���̕\����
     */
    public void setVisible(boolean visible) {
        if (getButton() != null) {
            getButton().setVisible(visible);
        }
    }

    public String toString() {
        if (getText() == null) {
            return null;
        }
        return getText().toString();
    }
}
