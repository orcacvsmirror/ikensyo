/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

/**
 * VR Look&FeelにおけるボタンUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRButtonUI extends BasicButtonUI {
    private static VRButtonUI VRBUTTONUI = new VRButtonUI();

    private Border defaultBorder = null;

    public VRButtonUI() {
        super();
        defaultBorder = UIManager.getBorder(getPropertyPrefix() + "border");
    }

    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false);
        b.setRolloverEnabled(true);

    }

    public static ComponentUI createUI(JComponent c) {
        AbstractButton b = (AbstractButton) c;

        return VRBUTTONUI;
    }

    private static Rectangle viewRect = new Rectangle();

    private static Rectangle textRect = new Rectangle();

    private static Rectangle iconRect = new Rectangle();

    private static Rectangle backRect = new Rectangle();

    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();

        FontMetrics fm = g.getFontMetrics();

        Insets i = c.getInsets();
        Insets ib = c.getBorder().getBorderInsets(null);

        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = b.getWidth() - (i.right + viewRect.x);
        viewRect.height = b.getHeight() - (i.bottom + viewRect.y);

        backRect.x = 0;
        backRect.y = 0;
        backRect.width = b.getWidth();
        backRect.height = b.getHeight();

        int rw = ib.right + ib.left;
        int rh = ib.top + ib.bottom;

        textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;

        Font f = c.getFont();
        g.setFont(f);

        // layout the text and icon
        String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b
                .getIcon(), b.getVerticalAlignment(), b
                .getHorizontalAlignment(), b.getVerticalTextPosition(), b
                .getHorizontalTextPosition(), viewRect, iconRect, textRect, b
                .getText() == null ? 0 : b.getIconTextGap());

        clearTextShiftOffset();

        boolean ispress = (model.isArmed() && model.isPressed());
        boolean isfocus = (b.isFocusPainted() && b.hasFocus());
        boolean ishover = model.isRollover();

        if (b.getBorder().equals(defaultBorder)) {

            VRDraw.drawButtonBase(g, b.getBackground(), VRLookAndFeel
                    .getFocusColor(), VRLookAndFeel.getHoverColor(), backRect,
                    rw, rh * 4, VRDraw.BUTTON_SHAPE_ROUND_RECT, b);

        } else {
            Graphics2D g2 = (Graphics2D) g;
            //表面描画
            if (ispress) {
                VRDraw.setGradientPaint(g2, b.getBackground().darker(), b
                        .getBackground(), VRDraw.DRAW_DIRECTION_DOWN, viewRect);

            } else {
                VRDraw.setGradientPaint(g2, b.getBackground().brighter(), b
                        .getBackground(), VRDraw.DRAW_DIRECTION_DOWN, viewRect);
            }

            g2.fill(viewRect);
        }

        // perform UI specific press action, e.g. Windows L&F shifts text
        if (model.isArmed() && model.isPressed()) {
            textRect.x++;
            textRect.y++;
            iconRect.x++;
            iconRect.y++;

            //            paintButtonPressed(g,b);
        }

        // Paint the Icon
        if (b.getIcon() != null) {
            paintIcon(g, c, iconRect);
        }

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
     * テキスト描画を行ないます。
     * 
     * @param g グラフィクス
     * @param c コンポーネント
     * @param textRect テキスト矩形
     * @param text 描画テキスト
     */
    protected void paintText(Graphics g, JComponent c, Rectangle textRect,
            String text) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        FontMetrics fm = g.getFontMetrics();
        int mnemonicIndex = b.getDisplayedMnemonicIndex();

        /* Draw the Text */
        if (model.isEnabled()) {
            /** * paint the text normally */
            g.setColor(b.getForeground());
            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text,
                    mnemonicIndex, textRect.x + getTextShiftOffset(),
                    textRect.y + fm.getAscent() + getTextShiftOffset());

        } else {
            /** * paint the text disabled ** */
            g.setColor(new Color(255, 255, 255, 128));
            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text,
                    mnemonicIndex, textRect.x, textRect.y + fm.getAscent());
            g.setColor(new Color(0, 0, 0, 128));
            BasicGraphicsUtils.drawStringUnderlineCharAt(g, text,
                    mnemonicIndex, textRect.x - 1, textRect.y + fm.getAscent()
                            - 1);
        }
    }

}