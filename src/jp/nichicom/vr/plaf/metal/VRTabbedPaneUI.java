/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * VR Look&FeelにおけるタブペインUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRTabbedPaneUI extends BasicTabbedPaneUI {
    private Color selectedColor;

    public static ComponentUI createUI(JComponent c) {
        return new VRTabbedPaneUI();
    }

    protected void installDefaults() {
        super.installDefaults();
        selectedColor = UIManager.getColor("TabbedPane.selected");
    }

    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    }

    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {

        Color c;
        Color fc;
        if (!isSelected || selectedColor == null) {
            fc = tabPane.getBackgroundAt(tabIndex);
            c = VRDraw.darker(fc, 0.8);
        } else {
            c = selectedColor;
            fc = selectedColor.brighter();
        }

        Color f = VRLookAndFeel.getFocusColor();

        Graphics2D g2 = (Graphics2D) g;
        Rectangle r;
        r = new Rectangle(x, y, w, h);

        Polygon p = new Polygon();

        switch (tabPlacement) {
        case LEFT:
            r.width += 4;
            VRDraw.drawThmbBase(g, fc, c, r, 8, 8, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_DOWN);
            break;
        case RIGHT:
            r.x -= 4;
            r.width += 4;
            VRDraw.drawThmbBase(g, fc, c, r, 8, 8, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_DOWN);
            break;
        case BOTTOM:
            r.y -= 4;
            r.height += 4;
            VRDraw.drawThmbBase(g, c, fc, r, 8, 8, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_DOWN);
            break;
        case TOP:
        default:
            r.height += 4;
            VRDraw.drawThmbBase(g, fc, c, r, 8, 8, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_DOWN);

        }
    }

    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        Rectangle r = (Rectangle) rects[tabIndex];
        if (tabPane.hasFocus() & isSelected) {
            Graphics2D g2 = (Graphics2D) g;
            r = new Rectangle(r.x + 2, r.y + r.height / 2 - 4, 7, 7);
            VRDraw.setGradientPaint(g2, focus.brighter(), focus, VRDraw.DRAW_DIRECTION_DOWN, r);
            g2.fill(r);
        }
    }

}