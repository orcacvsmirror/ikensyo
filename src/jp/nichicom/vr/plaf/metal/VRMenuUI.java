/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * VR Look&FeelÇ…Ç®ÇØÇÈÉÅÉjÉÖÅ[UIÇ≈Ç∑ÅB
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRMenuUI extends BasicMenuUI {

    public static ComponentUI createUI(JComponent x) {
        return new VRMenuUI();
    }

    protected void paintBackground(Graphics g, JMenuItem menuItem, Color bgColor) {
        ButtonModel model = menuItem.getModel();
        Color oldColor = g.getColor();
        int menuWidth = menuItem.getWidth();
        int menuHeight = menuItem.getHeight();

        if (menuItem.isOpaque()) {
            Color c = menuItem.getBackground();
            if (model.isArmed() || (menuItem instanceof JMenu && model.isSelected())) {
                c = bgColor;
            }

            Graphics2D g2d = (Graphics2D) g;

            g2d.setPaint(new GradientPaint(0.0f, menuItem.getY(), c.brighter(), 0.0f, menuItem.getY() + menuHeight, c));

            g2d.fill(g.getClipBounds());

            g.setColor(oldColor);
        }
    }
}