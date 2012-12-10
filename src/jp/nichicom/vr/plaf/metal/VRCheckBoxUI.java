/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;

/**
 * VR Look&FeelにおけるチェックボックスUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRCheckBoxUI extends BasicCheckBoxUI {
    private static final VRCheckBoxUI vrCheckBoxUI = new VRCheckBoxUI();

    public static ComponentUI createUI(JComponent c) {
        return vrCheckBoxUI;
    }

    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setRolloverEnabled(true);
        b.setOpaque(false);

    }

}