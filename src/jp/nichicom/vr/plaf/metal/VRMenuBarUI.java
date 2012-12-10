/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 * VR Look&FeelにおけるメニューバーUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRMenuBarUI extends BasicMenuBarUI {

    public static ComponentUI createUI(JComponent c) {
        return new VRMenuBarUI();
    }

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(new GradientPaint(0.0f, 0, c.getBackground().brighter(), 0.0f, c.getHeight(), c.getBackground()));

        g2d.fill(g.getClipBounds());

    }
}