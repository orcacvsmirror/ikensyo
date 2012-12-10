package jp.nichicom.ac.component;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.component.VRButton;

/**
 * �A�C�R�������\�[�X�p�X�Ŏw��\�ȃ{�^���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRButton
 */
public class ACButton extends VRButton {
    private boolean environmentChecked = false;
    private boolean colorIgnore = false;
    protected String iconType = "";

    /**
     * ���`�F�b�N������������ ��Ԃ��܂��B
     * 
     * @return ���`�F�b�N������������
     */
    protected boolean isEnvironmentChecked() {
        return environmentChecked;
    }

    /**
     * ���`�F�b�N������������ ��ݒ肵�܂��B
     * 
     * @param environmentChecked ���`�F�b�N������������
     */
    protected void setEnvironmentChecked(boolean environmentChecked) {
        this.environmentChecked = environmentChecked;
    }

    public ACButton() {
        super();
    }

    public ACButton(Action a) {
        super(a);
    }

    public ACButton(Icon icon) {
        super(icon);
    }

    public ACButton(String text) {
        super(text);
    }

    public ACButton(String text, Icon icon) {
        super(text, icon);
    }

    protected void initComponent() {
        super.initComponent();
        checkEnvironment();
    }

    /**
     * ���Ɉˑ�����ݒ�ύX���s�Ȃ��܂��B
     */
    protected void checkEnvironment() {
        if (isEnvironmentChecked()) {
            // ���`�F�b�N�ςȂ�΂Ȃɂ����Ȃ�
            return;
        }
        setEnvironmentChecked(true);
        String osName = System.getProperty("os.name");
        if ((osName == null) || (osName.indexOf("Mac") < 0)) {
            // Mac�ȊO�͐F�ύX������
            setColorIgnore(false);
        } else {
            // Mac�͐F�ύX�𖳎�����
            setColorIgnore(true);
        }
    }

    public void setForeground(Color foreground) {
        if (isColorIgnore()) {
            return;
        }
        super.setForeground(foreground);
    }

    public void setBackground(Color background) {
        if (isColorIgnore()) {
            return;
        }
        super.setBackground(background);
    }

    /**
     * �F�ݒ�𖳎����邩��Ԃ��܂��B
     * 
     * @return �F�ݒ�𖳎����邩
     */
    public boolean isColorIgnore() {
        // ���`�F�b�N�K�{
        checkEnvironment();
        return colorIgnore;
    }

    /**
     * �F�ݒ�𖳎����邩��ݒ肵�܂��B
     * 
     * @param colorIgnore �F�ݒ�𖳎����邩
     */
    public void setColorIgnore(boolean colorIgnore) {
        this.colorIgnore = colorIgnore;
        if (colorIgnore) {
            super.setForeground(null);
            super.setBackground(null);
        }
    }

    /**
     * �A�C�R���̃��\�[�X�p�X��Ԃ��܂��B
     * 
     * @return �A�C�R���̃��\�[�X�p�X
     */
    public String getIconPath() {
        return iconType;
    }

    /**
     * �A�C�R���̃��\�[�X�p�X��ݒ肵�܂��B
     * 
     * @param iconPath �A�C�R���̃��\�[�X�p�X
     */
    public void setIconPath(String iconPath) {
        this.iconType = iconPath;
        if ((iconPath == null) || ("".equals(iconPath))) {
            setIcon(null);
        } else {
            try {
                setIcon(ACResourceIconPooler.getInstance().getImage(iconPath));
            } catch (Exception ex) {
                ex.printStackTrace();
                setIcon(null);
                this.iconType = "";
            }
        }
    }

}
