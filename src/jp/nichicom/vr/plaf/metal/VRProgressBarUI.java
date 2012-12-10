/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JProgressBar;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;

/**
 * VR Look&FeelにおけるプログレスバーUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRProgressBarUI extends BasicProgressBarUI {

    private Rectangle boxRect;

    public static ComponentUI createUI(JComponent c) {
        return new VRProgressBarUI();
    }

    protected void paintIndeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        Graphics2D g2 = (Graphics2D) g;

        // Paint the bouncing box.
        boxRect = getBox(boxRect);
        if (boxRect != null) {
            VRDraw.setGradientPaint(g2, progressBar.getForeground(), progressBar.getForeground().darker(), VRDraw.DRAW_DIRECTION_DOWN, boxRect);
            g2.fill(boxRect);
        }

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
                paintString(g2, b.left, b.top, barRectWidth, barRectHeight, 0, b);

            } else {
                paintString(g2, b.left, b.top, barRectWidth, barRectHeight, 0, b);
            }
        }
    }

    protected void paintDeterminate(Graphics g, JComponent c) {
        if (!(g instanceof Graphics2D)) {
            return;
        }

        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);

        int cellLength = getCellLength();
        int cellSpacing = getCellSpacing();
        // amount of progress to draw
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        Graphics2D g2 = (Graphics2D) g;
        VRDraw.setGradientPaint(g2, progressBar.getForeground(), progressBar.getForeground().darker(), VRDraw.DRAW_DIRECTION_DOWN, 0, b.top, 0, barRectHeight);

        //        g2.setColor(progressBar.getForeground());

        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL) {
            // draw the cells
            if (cellSpacing == 0 && amountFull > 0) {
                // draw one big Rect because there is no space between cells

                g2.setStroke(new BasicStroke((float) barRectHeight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            } else {
                // draw each individual cell
                g2.setStroke(new BasicStroke((float) barRectHeight, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0.f, new float[] { cellLength, cellSpacing }, 0.f));
            }

            if (c.getComponentOrientation().isLeftToRight()) {
                g2.drawLine(b.left, (barRectHeight / 2) + b.top, amountFull + b.left, (barRectHeight / 2) + b.top);
            } else {
                g2.drawLine((barRectWidth + b.left), (barRectHeight / 2) + b.top, barRectWidth + b.left - amountFull, (barRectHeight / 2) + b.top);
            }

        } else { // VERTICAL
            // draw the cells
            if (cellSpacing == 0 && amountFull > 0) {
                // draw one big Rect because there is no space between cells
                g2.setStroke(new BasicStroke((float) barRectWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
            } else {
                // draw each individual cell
                g2.setStroke(new BasicStroke((float) barRectWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0f, new float[] { cellLength, cellSpacing }, 0f));
            }

            g2.drawLine(barRectWidth / 2 + b.left, b.top + barRectHeight, barRectWidth / 2 + b.left, b.top + barRectHeight - amountFull);
        }

        // Deal with possible text painting
        if (progressBar.isStringPainted()) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
        }

    }

}