/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;

/**
 * VR Look&Feel�Ŏg�p����t�H�[�J�X����A�_�v�^�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */public class VRUIFocusAdapter extends FocusAdapter {
    private static final VRUIFocusAdapter FOCUSADAPTER = new VRUIFocusAdapter();

    /**
     * �V���O���g���̃C���X�^���X��Ԃ��܂��B
     * @return �t�H�[�J�X�A�_�v�^�[
     */
    public static VRUIFocusAdapter getInstance() {
        return FOCUSADAPTER;
    }

    public void focusGained(FocusEvent e) {
        ((JComponent) e.getSource()).repaint();
    }

    public void focusLost(FocusEvent e) {
        ((JComponent) e.getSource()).repaint();
    }

}