/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 * VR Look&FeelにおけるスピナーUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRSpinnerUI extends BasicSpinnerUI {

    /**
     * コンストラクタ
     * 
     * @param c スピナー
     */
    public VRSpinnerUI(JSpinner c) {
        super();
    }

    public static ComponentUI createUI(JComponent c) {
        return new VRSpinnerUI((JSpinner) c);
    }

    protected Component createPreviousButton() {
        AbstractButton classicButton = (AbstractButton) super
                .createPreviousButton();

        JButton xpButton = new VRArrowButton(SwingConstants.SOUTH);
        xpButton.setRequestFocusEnabled(false);
        xpButton.addActionListener((ActionListener) getUIResource(classicButton
                .getActionListeners()));
        xpButton.addMouseListener((MouseListener) getUIResource(classicButton
                .getMouseListeners()));
        return xpButton;
    }

    protected Component createNextButton() {
        AbstractButton classicButton = (AbstractButton) super
                .createNextButton();

        JButton xpButton = new VRArrowButton(SwingConstants.NORTH);
        xpButton.setRequestFocusEnabled(false);
        xpButton.addActionListener((ActionListener) getUIResource(classicButton
                .getActionListeners()));
        xpButton.addMouseListener((MouseListener) getUIResource(classicButton
                .getMouseListeners()));
        return xpButton;
    }

    /**
     * リソース取得用
     * 
     * @param listeners リスナー
     * @return リソース
     */
    private UIResource getUIResource(Object[] listeners) {
        for (int counter = 0; counter < listeners.length; counter++) {
            if (listeners[counter] instanceof UIResource) {
                return (UIResource) listeners[counter];
            }
        }
        return null;
    }
}