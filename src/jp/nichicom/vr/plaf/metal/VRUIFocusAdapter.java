/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JComponent;

/**
 * VR Look&Feelで使用するフォーカス制御アダプタです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */public class VRUIFocusAdapter extends FocusAdapter {
    private static final VRUIFocusAdapter FOCUSADAPTER = new VRUIFocusAdapter();

    /**
     * シングルトンのインスタンスを返します。
     * @return フォーカスアダプター
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