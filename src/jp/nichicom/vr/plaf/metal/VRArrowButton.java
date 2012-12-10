/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * VR Look&Feelにおける矢印ボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRArrowButton extends JButton implements SwingConstants {

    protected int direction;

    private Color shadow;

    private Color darkShadow;

    private Color highlight;

    private Dimension preferredSize;

    private Dimension minimumSize;

    /**
     * コンストラクタ
     * @param direction 方向
     * @param background 背景色
     * @param shadow シャドー色
     * @param darkShadow 暗いシャドー色
     * @param highlight ハイライト色
     */
    public VRArrowButton(int direction, Color background, Color shadow, Color darkShadow, Color highlight) {
        super();
        setRequestFocusEnabled(false);
        setDirection(direction);
        setBackground(background);
        setBorderPainted(false);
        this.shadow = shadow;
        this.darkShadow = darkShadow;
        this.highlight = highlight;
    }

    /**
     * コンストラクタ
     * @param direction 方向
     * @param width 幅
     */
    public VRArrowButton(int direction, int width) {
        this(direction, UIManager.getColor("control"), UIManager.getColor("controlShadow"), UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"));
        preferredSize = new Dimension(width, width);
        minimumSize = new Dimension(width, width);
    }

    /**
     * コンストラクタ
     * @param direction 方向
     */
    public VRArrowButton(int direction) {
        this(direction, UIManager.getColor("control"), UIManager.getColor("controlShadow"), UIManager.getColor("controlDkShadow"), UIManager.getColor("controlLtHighlight"));
    }

    /**
     * 方向を返す。
     * @return 方向
     */
    public int getDirection() {
        return direction;
    }

    /**
     * 方向を設定する
     * @param dir 方向
     */
    public void setDirection(int dir) {
        direction = dir;
    }

    public void paint(Graphics g) {
        Color origColor;
        boolean isPressed, isEnabled;
        int w, h, size;

        w = getSize().width;
        h = getSize().height;
        origColor = g.getColor();
        isPressed = getModel().isPressed();
        isEnabled = isEnabled();

        boolean isfocus = (isFocusPainted() && hasFocus());
        boolean ishover = model.isRollover();
        if (isContentAreaFilled() || isPressed || isfocus || ishover) {
            VRDraw.drawButtonBase(g, getBackground(), VRLookAndFeel.getFocusColor(), VRLookAndFeel.getHoverColor(), new Rectangle(0, 0, getWidth(), getHeight()), 0, 0, VRDraw.BUTTON_SHAPE_RECT, this);
        }

        // If there's no room to draw arrow, bail
        if (h < 5 || w < 5) {
            g.setColor(origColor);
            return;
        }

        if (isPressed) {
            g.translate(1, 1);
        }

        // Draw the arrow
        size = Math.min((h - 4) / 3, (w - 4) / 3);
        size = Math.max(size, 2);
        paintTriangle(g, (w - size) / 2, (h - size) / 2, size, direction, isEnabled);

        // Reset the Graphics back to it's original settings
        if (isPressed) {
            g.translate(-1, -1);
        }
        g.setColor(origColor);

    }

    public Dimension getPreferredSize() {
        if (preferredSize != null) {
            return preferredSize;
        } else {

            return new Dimension(16, 16);
        }
    }

    public Dimension getMinimumSize() {
        if (minimumSize != null) {
            return minimumSize;
        } else {

            return new Dimension(5, 5);
        }
    }

    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public boolean isFocusTraversable() {
        return false;
    }

    public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
        Color oldColor = g.getColor();
        int mid, i, j;

        j = 0;
        size = Math.max(size, 2);
        mid = (size / 2) - 1;

        g.translate(x, y);
        if (isEnabled)
            g.setColor(darkShadow);
        else
            g.setColor(shadow);

        switch (direction) {
        case NORTH:
            for (i = 0; i < size; i++) {
                g.drawLine(mid - i, i, mid + i, i);
            }
            if (!isEnabled) {
                g.setColor(highlight);
                g.drawLine(mid - i + 2, i, mid + i, i);
            }
            break;
        case SOUTH:
            if (!isEnabled) {
                g.translate(1, 1);
                g.setColor(highlight);
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(mid - i, j, mid + i, j);
                    j++;
                }
                g.translate(-1, -1);
                g.setColor(shadow);
            }

            j = 0;
            for (i = size - 1; i >= 0; i--) {
                g.drawLine(mid - i, j, mid + i, j);
                j++;
            }
            break;
        case WEST:
            for (i = 0; i < size; i++) {
                g.drawLine(i, mid - i, i, mid + i);
            }
            if (!isEnabled) {
                g.setColor(highlight);
                g.drawLine(i, mid - i + 2, i, mid + i);
            }
            break;
        case EAST:
            if (!isEnabled) {
                g.translate(1, 1);
                g.setColor(highlight);
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(j, mid - i, j, mid + i);
                    j++;
                }
                g.translate(-1, -1);
                g.setColor(shadow);
            }

            j = 0;
            for (i = size - 1; i >= 0; i--) {
                g.drawLine(j, mid - i, j, mid + i);
                j++;
            }
            break;
        }
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

}

