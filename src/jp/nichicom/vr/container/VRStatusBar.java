/** TODO <HEAD> */
package jp.nichicom.vr.container;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.vr.layout.VRLayout;

/**
 * �R���g���[����ǉ��\�ȃX�e�[�^�X�o�[�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see JPanel
 */
public class VRStatusBar extends JPanel {

    private JLabel status;

    /**
     * �e�L�X�g�\���p�̃��x�� ��Ԃ��܂��B
     * 
     * @return �e�L�X�g�\���p�̃��x��
     */
    public JLabel getStatus() {
        if (status == null) {
            status = new JLabel();
            status
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));

        }
        return status;
    }

    /**
     * �e�L�X�g�\���p�̃��x�� ��ݒ肵�܂��B
     * 
     * @param textLabel �e�L�X�g�\���p�̃��x��
     */
    public void setStatus(JLabel textLabel) {
        this.status = textLabel;
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public VRStatusBar() {
        super();
        setOpaque(true);

        setLayout(new VRLayout());
        setBorder(javax.swing.BorderFactory
                .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        
        initComponent();

    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        add(getStatus(), VRLayout.CLIENT);
    }

    /**
     * �e�L�X�g�\���p�̃��x���̕������Ԃ��܂��B
     * 
     * @return ������
     */
    public String getText() {
        return getStatus().getText();
    }

    /**
     * �e�L�X�g�\���p�̃��x���ɕ������ݒ肵�܂��B
     * 
     * @param text �ݒ蕶����
     */
    public void setText(String text) {
        getStatus().setText(text);
        repaint();
    }

    /**
     * �e�L�X�g�\���p�̃��x����\�����邩��Ԃ��܂��B
     * 
     * @return �\�����邩
     */
    public boolean isStatusVisible() {
        return getStatus().isVisible();
    }

    /**
     * �e�L�X�g�\���p�̃��x����\�����邩��ݒ肵�܂��B
     * 
     * @param textVisible �\�����邩
     */
    public void setStatusVisible(boolean textVisible) {
        getStatus().setVisible(textVisible);
    }
    
    public void setForeground(Color foreground){
        super.setForeground(foreground);
        getStatus().setForeground(foreground);
    }
    
    public void setBackground(Color background){
        super.setBackground(background);
        getStatus().setBackground(background);
    }

}