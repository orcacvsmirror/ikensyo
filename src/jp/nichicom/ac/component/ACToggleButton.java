package jp.nichicom.ac.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.io.ACResourceIconPooler;

/**
 * �g�O���{�^���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */
public class ACToggleButton extends JToggleButton {
    private boolean toggleState;
    private String iconPathPushed;
    private String iconPathUnPushed;
    private String textPushed;
    private String textUnPushed;
    private char mnemonicPushed;
    private char mnemonicUnPushed;

    /**
     * �R���X�g���N�^
     */
    public ACToggleButton() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ������Ԃ𔽓]���܂��B
     */
    public void swapPushed(){
      setPushed(!isPushed()); //�t���O�̔��]
    }

    /**
     * ������
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        this.setIconTextGap(0);
        this.setMargin(new java.awt.Insets(0, 4, 0, 4));
        this.setContentAreaFilled(true);

        this.addActionListener(new ActionListener() {
          /**
           * �{�^���������̓���
           * @param e ActionEvent
           */
          public void actionPerformed(ActionEvent e) {
            swapPushed();
          }
        });
        toggleState = false;
        setButtonProperty();
    }

    /**
     * �g�O���̏�Ԃɂ��A���ꂼ��̃v���p�e�B��ݒ肷��
     */
    private void setButtonProperty() {
        if (isPushed()) {
            this.setText(textPushed);
            this.setIconPath(iconPathPushed);
            this.setMnemonic(mnemonicPushed);
        }
        else {
            this.setText(textUnPushed);
            this.setIconPath(iconPathUnPushed);
            this.setMnemonic(mnemonicUnPushed);
        }
    }


    /**
     * ������Ԃ�ݒ肵�܂��B
     * @param pushed true:�����Afalse:�����Ȃ�
     */
    public void setPushed(boolean pushed) {
        toggleState = pushed;
        setButtonProperty();
    }

    /**
     * ������Ԏ��̕\���������ݒ肵�܂��B
     * @param textPushed ������Ԏ��̕\��������
     */
    public void setTextPushed(String textPushed) {
        this.textPushed = textPushed;
        setButtonProperty();
    }

    /**
     * �ʏ��Ԏ��̕\���������ݒ肵�܂��B
     * @param textUnPushed �ʏ��Ԏ��̕\��������
     */
    public void setTextUnPushed(String textUnPushed) {
        this.textUnPushed = textUnPushed;
        setButtonProperty();
    }

    /**
     * �A�C�R���^�C�v��ݒ肵�܂��B
     * @param iconPath �A�C�R���^�C�v
     */
    public void setIconPath(String iconPath) {
        if ( (iconPath == null) || ("".equals(iconPath))) {
            setIcon(null);
        }
        else {
            try {
                setIcon(ACResourceIconPooler.getInstance().getImage(iconPath));
            }
            catch (Exception ex) {
              ACCommon.getInstance().showExceptionMessage(ex);
                setIcon(null);
            }
        }
    }

    /**
     * ������Ԏ��̃A�C�R���̃��\�[�X�p�X��ݒ肵�܂��B
     * @param iconPathPushed ������Ԏ��̃A�C�R���̃��\�[�X�p�X
     */
    public void setIconPathPushed(String iconPathPushed) {
        this.iconPathPushed = iconPathPushed;
        setButtonProperty();
    }

    /**
     * �ʏ��Ԏ��̃A�C�R���̃��\�[�X�p�X��ݒ肵�܂��B
     * @param iconPathUnPushed �ʏ��Ԏ��̃A�C�R���̃��\�[�X�p�X
     */
    public void setIconPathUnPushed(String iconPathUnPushed) {
        this.iconPathUnPushed = iconPathUnPushed;
        setButtonProperty();
    }

    /**
     * �ʏ��Ԏ��̃j�[���j�b�N��ݒ肵�܂��B
     * @param mnemonicPushed �ʏ��Ԏ��̃j�[���j�b�N
     */
    public void setMnemonicPushed(char mnemonicPushed) {
        this.mnemonicPushed = mnemonicPushed;
        setButtonProperty();
    }

    /**
     * ������Ԏ��̃j�[���j�b�N��ݒ肵�܂��B
     * @param mnemonicUnPushed ������Ԏ��̃j�[���j�b�N
     */
    public void setMnemonicUnPushed(char mnemonicUnPushed) {
        this.mnemonicUnPushed = mnemonicUnPushed;
        setButtonProperty();
    }

    /**
     * ������Ԃ��擾���܂��B
     * @return true:������Ă���Afalse:������Ă��Ȃ�
     */
    public boolean isPushed() {
        return toggleState;
    }

    /**
     * ������Ԏ��̕\����������擾���܂��B
     * @return ������Ԏ��̕\��������
     */
    public String getTextPushed() {
        return textPushed;
    }
    /**
     * �ʏ��Ԏ��̕\����������擾���܂��B
     * @return �ʏ��Ԏ��̕\��������
     */
    public String getTextUnPushed() {
        return textUnPushed;
    }

    /**
     * ������Ԏ��̃A�C�R���̃��\�[�X�p�X���擾���܂��B
     * @return ������Ԏ��̃A�C�R���̃��\�[�X�p�X
     */
    public String getIconPathPushed() {
        return iconPathPushed;
    }
    /**
     * �ʏ��Ԏ��̃A�C�R���̃��\�[�X�p�X���擾���܂��B
     * @return �ʏ��Ԏ��̃A�C�R���̃��\�[�X�p�X
     */
    public String getIconPathUnPushed() {
        return iconPathUnPushed;
    }

    /**
     * ������Ԏ��̃j�[���j�b�N���擾���܂��B
     * @return ������Ԏ��̃j�[���j�b�N
     */
    public char getMnemonicPushed() {
        return mnemonicPushed;
    }

    /**
     * �ʏ��Ԏ��̃j�[���j�b�N���擾���܂��B
     * @return �ʏ��Ԏ��̃j�[���j�b�N
     */
    public char getMnemonicUnPushed() {
        return mnemonicPushed;
    }
}
