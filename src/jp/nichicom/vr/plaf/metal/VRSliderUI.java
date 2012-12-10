/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * VR Look&FeelにおけるスライダーUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRSliderUI extends BasicSliderUI {
    protected static Color thumbColor;

    /**
     * コンストラクタ
     * @param b スライダー
     */
    public VRSliderUI(JSlider b) {
        super(b);
    }

    public static ComponentUI createUI(JComponent b) {
        return new VRSliderUI((JSlider) b);
    }

    public void installUI(JComponent c) {
        super.installUI(c);
        thumbColor = UIManager.getColor("Slider.thumb");
    }

    protected void installDefaults(JSlider slider) {
        super.installDefaults(slider);
        slider.setOpaque(false);
    }

    public void paintFocus(Graphics g) {
    }

    public void paintThumb(Graphics g) {
        Rectangle knobBounds = thumbRect;
        int w = knobBounds.width;
        int h = knobBounds.height;

        g.translate(knobBounds.x, knobBounds.y);
        Color thumbc = slider.getBackground();
        if (slider.hasFocus()) {
            thumbc = thumbColor;
        }
        Color thumbbc = thumbc.darker();

        if (!slider.isEnabled()) {
            thumbbc = slider.getBackground();
        }

        int r = (w + h) / 4;
        if (!slider.getPaintTicks()) {

            VRDraw.drawThmbBase(g, thumbc, thumbbc, new Rectangle(0, 0, w, h), r, r, VRDraw.BUTTON_SHAPE_ROUND_RECT, VRDraw.DRAW_DIRECTION_RIGHT);

        } else if (slider.getOrientation() == JSlider.HORIZONTAL) {
            //下矢印
            int cw = w / 2;
            VRDraw.drawThmbBase(g, thumbc, thumbbc, new Rectangle(0, 0, w, h), r, r, VRDraw.BUTTON_SHAPE_POINTED_DOWN, VRDraw.DRAW_DIRECTION_RIGHT);
        } else { // vertical
            int cw = h / 2;
            if (slider.getComponentOrientation().isLeftToRight()) {

                //右向き
                VRDraw.drawThmbBase(g, thumbc, thumbbc, new Rectangle(0, 0, w, h), r, r, VRDraw.BUTTON_SHAPE_POINTED_RIGHT, VRDraw.DRAW_DIRECTION_DOWN);

            } else {

                //左向き
                VRDraw.drawThmbBase(g, thumbc, thumbbc, new Rectangle(0, 0, w, h), r, r, VRDraw.BUTTON_SHAPE_POINTED_LEFT, VRDraw.DRAW_DIRECTION_DOWN);
            }
        }

        g.translate(-knobBounds.x, -knobBounds.y);
    }

    public void paintTrack(Graphics g) {
        int cx, cy, cw, ch;
        int pad;

        Rectangle trackBounds = trackRect;

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            pad = trackBuffer;
            cx = pad;
            cy = (trackBounds.height / 2) - 2;
            cw = trackBounds.width;

            g.translate(trackBounds.x, 0);

            if (slider.hasFocus()) {
                g.setColor(VRDraw.blend(VRLookAndFeel.getTextFocusBackground(), slider.getBackground(), 0.5));
            } else {
                g.setColor(slider.getBackground());
            }
            g.fillRect(0, cy, cw, 4);
            if (slider.isEnabled()) {
                VRDraw.drawFlush3DBorder(g, 0, cy, cw, 4);
            } else {
                VRDraw.drawDisabledBorder(g, 0, cy, cw, 4);
            }

            g.translate(-trackBounds.x, 0);
        } else {
            pad = trackBuffer;
            cx = (trackBounds.width / 2) - 2;
            cy = pad;
            ch = trackBounds.height;

            g.translate(0, trackBounds.y);
            if (slider.hasFocus()) {
                g.setColor(VRLookAndFeel.getTextFocusBackground());
            } else {
                g.setColor(slider.getBackground());
            }
            g.fillRect(cx, 0, 4, ch);

            if (slider.isEnabled()) {
                VRDraw.drawFlush3DBorder(g, cx, 0, 4, ch);
            } else {
                VRDraw.drawDisabledBorder(g, cx, 0, 4, ch);
            }

            g.translate(0, -trackBounds.y);
        }
    }

    public void paintTicks(Graphics g) {
        Rectangle tickBounds = tickRect;
        int i;
        int maj, min, max;
        int w = tickBounds.width;
        int h = tickBounds.height;
        int centerEffect, tickHeight;

        g.setColor(Color.black);

        maj = slider.getMajorTickSpacing();
        min = slider.getMinorTickSpacing();

        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            g.translate(0, tickBounds.y);

            int value = slider.getMinimum();
            int xPos = 0;

            if (slider.getMinorTickSpacing() > 0) {
                while (value <= slider.getMaximum()) {
                    xPos = xPositionForValue(value);
                    paintMinorTickForHorizSlider(g, tickBounds, xPos);
                    value += slider.getMinorTickSpacing();
                }
            }

            if (slider.getMajorTickSpacing() > 0) {
                value = slider.getMinimum();

                while (value <= slider.getMaximum()) {
                    xPos = xPositionForValue(value);
                    paintMajorTickForHorizSlider(g, tickBounds, xPos);
                    value += slider.getMajorTickSpacing();
                }
            }

            g.translate(0, -tickBounds.y);
        } else {
            g.translate(tickBounds.x, 0);

            int value = slider.getMinimum();
            int yPos = 0;

            if (slider.getMinorTickSpacing() > 0) {
                int offset = 0;
                if (!slider.getComponentOrientation().isLeftToRight()) {
                    offset = tickBounds.width - tickBounds.width / 2;
                    g.translate(offset, 0);
                }

                while (value <= slider.getMaximum()) {
                    yPos = yPositionForValue(value);
                    paintMinorTickForVertSlider(g, tickBounds, yPos);
                    value += slider.getMinorTickSpacing();
                }

                if (!slider.getComponentOrientation().isLeftToRight()) {
                    g.translate(-offset, 0);
                }
            }

            if (slider.getMajorTickSpacing() > 0) {
                value = slider.getMinimum();
                if (!slider.getComponentOrientation().isLeftToRight()) {
                    g.translate(2, 0);
                }

                while (value <= slider.getMaximum()) {
                    yPos = yPositionForValue(value);
                    paintMajorTickForVertSlider(g, tickBounds, yPos);
                    value += slider.getMajorTickSpacing();
                }

                if (!slider.getComponentOrientation().isLeftToRight()) {
                    g.translate(-2, 0);
                }
            }
            g.translate(-tickBounds.x, 0);
        }
    }

}