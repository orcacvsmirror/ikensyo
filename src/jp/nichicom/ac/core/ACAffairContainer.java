package jp.nichicom.ac.core;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACStatusBar;
import jp.nichicom.vr.layout.VRLayout;

/**
 * �Ɩ��N���X�ł��B
 * <p>
 * ��Ղɂ���đJ�ډ\�ȃN���X�Ƃ���ɂ́A�����<code>NCAffairble</code>����������K�v������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACConstants
 * @see ACAffairable
 */

public class ACAffairContainer extends ACPanel implements ACConstants {
    protected ACStatusBar statusBar = new ACStatusBar();

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACAffairContainer() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * �X�e�[�^�X�o�[�̃^�C�}�[���~�����܂��B
     * <p>
     * �J�����Ɏ����ŌĂ΂�Ȃ��̂ŁA�����I�ɌĂт܂��B
     * </p>
     */
    public void cancelTimer() {
        ACStatusBar statusBar = getStatusBar();
        if (statusBar instanceof ACStatusBar) {
            statusBar.cancelTimer();
        }
    }

    public boolean canClose() throws Exception {
        return true;
    }

    /**
     * �X�e�[�^�X�o�[��Ԃ��܂��B
     * 
     * @return �X�e�[�^�X�o�[
     */
    public ACStatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * �E�B���h�E�^�C�g����Ԃ��܂��B
     * 
     * @return �E�B���h�E�^�C�g��
     */
    public String getTitle() {
        return ACFrame.getInstance().getTitle();
    }

    /**
     * �E�B���h�E�^�C�g����ݒ肵�܂��B
     * 
     * @param title �E�B���h�E�^�C�g��
     */
    public void setTitle(String title) {
        ACFrame.getInstance().setTitle(title);
    }

    /**
     * �R���|�[�l���g��ݒ肵�܂��B
     * 
     * @throws Exception �ݒ��O
     */
    private void jbInit() throws Exception{
        statusBar.setForeground(java.awt.Color.white);
        statusBar.setBackground(new java.awt.Color(0, 51, 153));
        this.add(statusBar, VRLayout.SOUTH);
        this.setBackground(new java.awt.Color(0, 51, 153));

    }

    /**
     * �X�e�[�^�X�o�[�̕������Ԃ��܂��B
     * 
     * @return �X�e�[�^�X�o�[�̕�����
     */
    protected String getStatusText() {
        ACStatusBar statusBar = getStatusBar();
        if (statusBar instanceof ACStatusBar) {
            statusBar.getText();
        }
        return "";
    }

    /**
     * �X�e�[�^�X�o�[�ɕ������ݒ肵�܂��B
     * 
     * @param text �X�e�[�^�X�o�[�̕�����
     */
    protected void setStatusText(String text) {
        ACStatusBar statusBar = getStatusBar();
        if (statusBar instanceof ACStatusBar) {
            statusBar.setText(text);
        }
    }
}