/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * VR Look&FeelにおけるスクロールバーUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRScrollBarUI extends BasicScrollBarUI {
    protected VRArrowButton decButton;

    protected VRArrowButton incButton;

    public static ComponentUI createUI(JComponent c) {
        return new VRScrollBarUI();
    }

    protected JButton createDecreaseButton(int orientation) {

        decButton = new VRArrowButton(orientation);
        return decButton;
    }

    protected JButton createIncreaseButton(int orientation) {
        incButton = new VRArrowButton(orientation);
        return incButton;
    }

    protected void paintDecreaseHighlight(Graphics g) {
        Insets insets = scrollbar.getInsets();
        Rectangle thumbR = getThumbBounds();
        g.setColor(trackColor);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            int x = insets.left;
            int y = decrButton.getY() + decrButton.getHeight();
            int w = scrollbar.getWidth() - (insets.left + insets.right);
            int h = thumbR.y - y;
            g.fillRect(x, y, w, h);
        } else {
            int x, w;
            if (scrollbar.getComponentOrientation().isLeftToRight()) {
                x = decrButton.getX() + decrButton.getWidth();
                w = thumbR.x - x;
            } else {
                x = thumbR.x + thumbR.width;
                w = decrButton.getX() - x;
            }
            int y = insets.top;
            int h = scrollbar.getHeight() - (insets.top + insets.bottom);
            g.fillRect(x, y, w, h);
        }
    }

    protected void paintIncreaseHighlight(Graphics g) {
        Insets insets = scrollbar.getInsets();
        Rectangle thumbR = getThumbBounds();
        g.setColor(trackColor);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            int x = insets.left;
            int y = thumbR.y + thumbR.height;
            int w = scrollbar.getWidth() - (insets.left + insets.right);
            int h = incrButton.getY() - y;
            g.fillRect(x, y, w, h);
        } else {
            int x, w;
            if (scrollbar.getComponentOrientation().isLeftToRight()) {
                x = thumbR.x + thumbR.width;
                w = incrButton.getX() - x;
            } else {
                x = incrButton.getX() + incrButton.getWidth();
                w = thumbR.x - x;
            }
            int y = insets.top;
            int h = scrollbar.getHeight() - (insets.top + insets.bottom);
            g.fillRect(x, y, w, h);
        }
    }

    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            VRDraw.setGradientPaint(g2, trackColor, trackColor.brighter(), VRDraw.DRAW_DIRECTION_RIGHT, trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        } else {
            VRDraw.setGradientPaint(g2, trackColor, trackColor.brighter(), VRDraw.DRAW_DIRECTION_DOWN, trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

        }
        g2.fill(new Rectangle(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height));
        if (trackHighlight == DECREASE_HIGHLIGHT) {
            paintDecreaseHighlight(g);
        } else if (trackHighlight == INCREASE_HIGHLIGHT) {
            paintIncreaseHighlight(g);
        }
    }

    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            return;
        }

        int w = thumbBounds.width;
        int h = thumbBounds.height;

        g.translate(thumbBounds.x, thumbBounds.y);

        int r;
        if (w < h) {
            r = w / 4 * 3;
        } else {
            r = h / 4 * 3;

        }

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {

            VRDraw.drawThmbBase(g, thumbColor, thumbColor.darker(), new Rectangle(0, 0, w, h), r, r, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_RIGHT);

        } else {
            VRDraw.drawThmbBase(g, thumbColor, thumbColor.darker(), new Rectangle(0, 0, w, h), r, r, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_DOWN);

        }
        g.translate(-thumbBounds.x, -thumbBounds.y);
    }

}