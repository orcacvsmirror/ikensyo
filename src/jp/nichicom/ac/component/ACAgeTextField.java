package jp.nichicom.ac.component;

import java.util.Date;

import javax.swing.JTextField;

import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.io.ACAgeEncorder;

/**
 * �N��e�L�X�g�t�B�[���h�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 * @see ACParentHesesPanelContainer
 */
public class ACAgeTextField extends ACParentHesesPanelContainer {
    public ACAgeTextField() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ACTextField age = new ACTextField();
    private Date birthday;
    private Date baseDate;
    private boolean enabled;

    private void jbInit() throws Exception {
        this.setBeginText("�N��");
        this.setEndText("��");

        age.setColumns(3);
        age.setEditable(false);
        age.setFocusable(false);
        age.setText("");
        age.setHorizontalAlignment(JTextField.RIGHT);
        this.setOpaque(false);
        this.add(age);
    }

    /**
     * ���N������ݒ肵�܂��B
     * @param birthday ���N����
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        calcAge();
    }

    /**
     * �N��v�Z�̂��߂ɐ��N�����Ɣ�r��������ݒ肵�܂��B
     * <p>
     * null��ݒ肵���ꍇ�A�N��͌��ݓ��t�Ɛ��N�������r���Čv�Z���܂��B
     * </p>
     * @param baseDate ���
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
        calcAge();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        age.setEnabled(enabled);
    }

    /**
     * �N���ݒ肵�܂��B
     * @param age �N��
     */
    public void setAge(String age) {
        this.age.setText(age);
    }

    /**
     * �ݒ肵�Ă��鐶�N�������擾���܂��B
     * @return ���N����
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * �ݒ肵�Ă��������擾���܂��B
     * <p>
     * ���ݓ��t�Ɣ�r����悤�ɐݒ肳��Ă���ꍇ�Anull�������Ă��܂��B
     * </p>
     * @return ���
     */
    public Date getBaseDate() {
        return baseDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * ���ݓ��t�Ɗ��(���ݓ��t)����N����Z�o���ATextBox�ɐݒ肵�܂��B
     */
    private void calcAge() {
        if (getBirthday() == null) {
            age.setText("");
        }
        else {
            if (getBaseDate() == null) {
                age.setText(String.valueOf(ACAgeEncorder.getInstance().toAge(getBirthday())));
            }
            else {
                age.setText(String.valueOf(ACAgeEncorder.getInstance().toAge(getBirthday(), getBaseDate())));
            }
        }
    }

    /**
     * TextBox�ɐݒ肳��Ă���N����擾���܂��B
     * @return TextBox�ɐݒ肳��Ă���N��
     */
    public String getAge() {
        String age = this.age.getText();
        if (age == null) {
            age = "";
        }
        return age.trim();
    }

}
