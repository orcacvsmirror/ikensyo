/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import javax.swing.text.View;

/**
 * VR Look&FeelにおけるトグルボタンUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRToggleButtonUI extends BasicToggleButtonUI {
    private final static VRToggleButtonUI toggleButtonUI = new VRToggleButtonUI();

    private static Rectangle backRect = new Rectangle();

    public static ComponentUI createUI(JComponent b) {
        return toggleButtonUI;
    }

    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false);
        b.setRolloverEnabled(true);

    }

    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        Dimension size = b.getSize();
        FontMetrics fm = g.getFontMetrics();

        Insets i = c.getInsets();
        Insets ib = c.getBorder().getBorderInsets(null);

        Rectangle viewRect = new Rectangle(size);

        viewRect.x += i.left;
        viewRect.y += i.top;
        viewRect.width -= (i.right + viewRect.x);
        viewRect.height -= (i.bottom + viewRect.y);

        Rectangle iconRect = new Rectangle();
        Rectangle textRect = new Rectangle();

        backRect.x = 0;
        backRect.y = 0;
        backRect.width = b.getWidth();
        backRect.height = b.getHeight();

        int rw = ib.right + ib.left;
        int rh = ib.top + ib.bottom;

        Font f = c.getFont();
        g.setFont(f);

        // layout the text and icon
        String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b.getIcon(), b.getVerticalAlignment(), b.getHorizontalAlignment(), b.getVerticalTextPosition(), b.getHorizontalTextPosition(), viewRect, iconRect, textRect, b.getText() == null ? 0 : b.getIconTextGap());

        boolean ispress = (model.isArmed() && model.isPressed() || model.isSelected());
        boolean isfocus = (b.isFocusPainted() && b.hasFocus());
        boolean ishover = model.isRollover();
        if (b.isContentAreaFilled() || ispress || isfocus || ishover) {
            Color bk = null;
            if (b.isBorderPainted()) {
                Container pare = b.getParent();
                while (pare != null) {
                    if (pare.isOpaque()) {
                        bk = pare.getBackground();
                        break;
                    }

                    pare = pare.getParent();
                }
            }

            VRDraw.drawButtonBase(g, b.getBackground(), VRLookAndFeel.getFocusColor(), VRLookAndFeel.getHoverColor(), backRect, rw, rh * 4, VRDraw.BUTTON_SHAPE_ROUND_RECT, b);
        }

        // perform UI specific press action, e.g. Windows L&F shifts text
        if (model.isArmed() && model.isPressed()) {
            textRect.x++;
            textRect.y++;
            iconRect.x++;
            iconRect.y++;

        }

        // Paint the Icon
        if (b.getIcon() != null) {
            paintIcon(g, b, iconRect);
        }

        // Draw the Text
        if (text != null && !text.equals("")) {
            View v = (View) c.getClientProperty(BasicHTML.propertyKey);
            if (v != null) {
                v.paint(g, textRect);
            } else {
                paintText(g, b, textRect, text);
            }
        }

    }

    /**
     * As of Java 2 platform v 1.4 this method should not be used or overriden. Use the paintText method which takes the AbstractButton argument.
     */
    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        FontMetrics fm = g.getFontMetrics();
        int mnemonicIndex = b.getDisplayedMnemonicIndex();

        /* Draw the Text */
        if (model.isEnabled()) {
            /** * paint the text normally */
            if (model.isSelected() && (!model.isRollover())) {
                g.setColor(b.getBackground().brighter());
            } else {
                g.setColor(b.getForeground());
            }
            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x + getTextShiftOffset(), textRect.y + fm.getAscent() + getTextShiftOffset());

        } else {
            /** * paint the text disabled ** */
            g.setColor(b.getBackground().brighter());
            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
            g.setColor(b.getBackground().darker());
            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, mnemonicIndex, textRect.x - 1, textRect.y + fm.getAscent() - 1);
        }
    }

}