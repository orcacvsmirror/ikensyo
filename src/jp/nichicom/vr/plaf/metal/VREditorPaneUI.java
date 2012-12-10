/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicEditorPaneUI;

/**
 * VR Look&FeelにおけるエディタペインUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VREditorPaneUI extends BasicEditorPaneUI {
    private JComponent textComp;

    /**
     * コンストラクタ
     * 
     * @param c コンポーネント
     */
    public VREditorPaneUI(JComponent c) {
        super();
        textComp = c;
    }

    public static ComponentUI createUI(JComponent c) {
        return new VREditorPaneUI(c);
    }

    protected void paintBackground(Graphics g) {
        super.paintBackground(g);

        Graphics2D g2d;
        GradientPaint gradient;
        Rectangle r = new Rectangle(0, 0, getComponent().getWidth(), getComponent().getHeight());

        Color b = textComp.getBackground();

        g2d = (Graphics2D) g;

        if (getComponent().hasFocus()) {
            b = VRDraw.blend(VRLookAndFeel.getTextFocusBackground(), b, 0.5);
            g2d.setColor(b);
            g2d.fill(r);
        }

        r.height = 4;
        g2d.setPaint(new GradientPaint(0, r.y, VRDraw.darker(b, 0.9d), 0, r.y + r.height, b));
        g2d.fill(r);

    }

    protected void installDefaults() {
        super.installDefaults();
        getComponent().addFocusListener(VRUIFocusAdapter.getInstance());

    }

    protected void uninstallDefaults() {
        super.uninstallDefaults();
        getComponent().removeFocusListener(VRUIFocusAdapter.getInstance());

    }
}