/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableHeaderUI;

/**
 * VR Look&FeelにおけるテーブルヘッダUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRTableHeaderUI extends BasicTableHeaderUI {

    public static ComponentUI createUI(JComponent c) {
        return new VRTableHeaderUI();
    }

    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        VRDraw.setGradientPaint(g2, header.getParent().getBackground(), header.getParent().getBackground().brighter(), VRDraw.DRAW_DIRECTION_UP, 0, 0, 0, c.getHeight());
        g2.fill(g.getClipBounds());
        super.paint(g, c);

    }

}