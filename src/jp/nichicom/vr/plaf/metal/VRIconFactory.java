/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.plaf.UIResource;

/**
 * VR Look&Feel用のアイコンファクトリです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRIconFactory {
    private static Icon radioButtonIcon;

    private static Icon checkBoxIcon;

    /**
     * ラジオボタンアイコンを返します。
     * 
     * @return ラジオボタンアイコン
     */
    public static Icon getRadioButtonIcon() {
        if (radioButtonIcon == null) {
            radioButtonIcon = new RadioButtonIcon();
        }
        return radioButtonIcon;
    }

    /**
     * チェックボックスアイコンを返します。
     * 
     * @return チェックボックスアイコン
     */
    public static Icon getCheckBoxIcon() {
        if (checkBoxIcon == null) {
            checkBoxIcon = new CheckBoxIcon();
        }
        return checkBoxIcon;
    }

    /**
     * VR Look&Feel用のチェックボックスアイコン描画クラスです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Susumu Nakahara
     * @version 1.0 2005/10/31
     */
    protected static class CheckBoxIcon implements Icon, UIResource,
            Serializable {

        public void paintIcon(Component c, Graphics g, int x, int y) {

            AbstractButton cb = (AbstractButton) c;
            ButtonModel model = cb.getModel();

            Color background = c.getBackground();
            Color foreground = c.getForeground();
            Color shadow = VRLookAndFeel.getControlShadow();
            Color focus = VRLookAndFeel.getFocusColor();
            Color hover = VRLookAndFeel.getHoverColor();
            Color check = VRLookAndFeel.getCheckColor();
            Color darkCircle = VRLookAndFeel.getControlDarkShadow();
            Color hight = VRLookAndFeel.getControlHighlight();
            Color interiorColor = VRLookAndFeel.getWhite();

            // Set up colors per RadioButtonModel condition
            if (!model.isEnabled()) {
                hight = background;
                foreground = darkCircle = check = shadow;
            } else if (model.isPressed() && model.isArmed()) {
                hight = interiorColor = shadow;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.translate(x, y);

            //外枠
            g2.setPaint(new GradientPaint(0, 0, darkCircle, 0, 13, hight));
            g2.fill(new Rectangle(0, 0, 13, 13));

            //内枠

            if (cb.hasFocus() || model.isRollover()) {
                if (model.isRollover()) {
                    g2.setColor(hover);

                } else {
                    g2.setColor(focus);
                }

                g2.fill(new Rectangle(1, 1, 11, 11));
                g2.setColor(interiorColor);
                g2.fill(new Rectangle(2, 2, 9, 9));

            } else {
                g2.setColor(interiorColor);
                g2.fill(new Rectangle(1, 1, 11, 11));

            }

            if (model.isSelected()) {
                g2.setPaint(new GradientPaint(0, 0, check, 0, 13, foreground));
                g2.fill(new Polygon(new int[] { 1, 4, 13, 5 }, new int[] { 3,
                        6, 0, 10 }, 4));
            }

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

            g.translate(-x, -y);

        }

        public int getIconWidth() {
            return 13;
        }

        public int getIconHeight() {
            return 13;
        }
    } // End class CheckBoxIcon

    /**
     * VR Look&Feel用のラジオボタンアイコン描画クラスです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Susumu Nakahara
     * @version 1.0 2005/10/31
     */
    protected static class RadioButtonIcon implements Icon, UIResource,
            Serializable {
        public void paintIcon(Component c, Graphics g, int x, int y) {
            AbstractButton rb = (AbstractButton) c;
            ButtonModel model = rb.getModel();
            boolean drawDot = model.isSelected();

            Color background = c.getBackground();
            Color foreground = c.getForeground();

            Color shadow = VRLookAndFeel.getControlShadow();
            Color focus = VRLookAndFeel.getFocusColor();
            Color hover = VRLookAndFeel.getHoverColor();
            Color check = VRLookAndFeel.getCheckColor();
            Color darkCircle = VRLookAndFeel.getControlDarkShadow();
            Color hight = VRLookAndFeel.getControlHighlight();
            Color interiorColor = VRLookAndFeel.getWhite();

            // Set up colors per RadioButtonModel condition
            if (!model.isEnabled()) {
                hight = background;
                foreground = darkCircle = check = shadow;
            } else if (model.isPressed() && model.isArmed()) {
                hight = interiorColor = shadow;
            }

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.translate(x, y);

            //外枠
            g2.setPaint(new GradientPaint(0, 0, darkCircle, 0, 13, hight));
            g2.fill(new Ellipse2D.Double(0, 0, 13, 13));

            //内枠

            if (rb.hasFocus() || model.isRollover()) {
                if (model.isRollover()) {
                    g2.setColor(hover);

                } else {
                    g2.setColor(focus);
                }

                g2.fill(new Ellipse2D.Double(1, 1, 11, 11));
                g2.setColor(interiorColor);
                g2.fill(new Ellipse2D.Double(2, 2, 9, 9));

            } else {
                g2.setColor(interiorColor);
                g2.fill(new Ellipse2D.Double(1, 1, 11, 11));

            }

            if (drawDot) {
                g2.setPaint(new GradientPaint(0, 0, check, 0, 13, foreground));
                g2.fill(new Ellipse2D.Double(3, 3, 7, 7));
            }

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);

            g.translate(-x, -y);
        }

        public int getIconWidth() {
            return 13;
        }

        public int getIconHeight() {
            return 13;
        }
    } // End class RadioButtonIcon

}