/** TODO <HEAD> */
package jp.nichicom.vr.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;

/**
 * �t���[��(�g)��`�悷��{�[�_�[�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see AbstractBorder
 */
public class VRFrameBorder extends AbstractBorder {
    private Insets borderInsets;
    private String text;
    private Color fillColor;
    private JComponent component;
    private boolean drawComponent;

    /**
     * �R���X�g���N�^
     */
    public VRFrameBorder() {
        super();
        initComponent();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param text �e�L�X�g
     */
    public VRFrameBorder(String text) {
        super();
        this.text = text;
        initComponent();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param text �e�L�X�g
     * @param fillColor �w�i�F
     */
    public VRFrameBorder(String text, Color fillColor) {
        super();
        this.text = text;
        this.fillColor = fillColor;
        initComponent();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param component �R���|�[�l���g
     */
    public VRFrameBorder(JComponent component) {
        super();
        this.component = component;
        initComponent();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param component �R���|�[�l���g
     * @param fillColor �w�i�F
     */
    public VRFrameBorder(JComponent component, Color fillColor) {
        super();
        this.fillColor = fillColor;
        this.component = component;
        initComponent();
    }

    /**
     * �R���|�[�l���g�����������܂��B
     */
    protected void initComponent() {
        setDrawComponent(true);
    }

    /**
     * �e�L�X�g��Ԃ��܂��B
     * 
     * @return �e�L�X�g
     */
    public String getText() {
        return text;
    }

    /**
     * �e�L�X�g��ݒ肵�܂�
     * 
     * @param text �e�L�X�g
     */
    public void setText(String text) {
        this.text = text;
        borderInsets = null;
    }

    /**
     * �����_�����O�ɗp����R���|�[�l���g��Ԃ��܂��B
     * 
     * @return �����_�����O�ɗp����R���|�[�l���g
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * �����_�����O�ɗp����R���|�[�l���g��ݒ肵�܂��B
     * 
     * @param component �����_�����O�ɗp����R���|�[�l���g
     */
    public void setComponent(JComponent component) {
        this.component = component;
    }

    /**
     * �w�i�F��Ԃ��܂��B
     * 
     * @return �w�i�F
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * �w�i�F��ݒ肵�܂�
     * 
     * @param fillColor �w�i�F
     */
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Insets is = getBorderInsets(c);
        Color b = c.getBackground().brighter();
        Color d = c.getBackground().darker();
        Graphics2D g2 = (Graphics2D) g;
        int r = (is.left + is.right) / 2;
        if (fillColor != null) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fillColor);
            g2.fill(new RoundRectangle2D.Double(x, y, w, h, r, r));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        JComponent cmp = getComponent();
        if ((text != null && text.length() > 0)
                || ((cmp != null) && isDrawComponent())) {
            int ts = 0;
            int tl = 0;

            if (cmp == null) {
                FontMetrics fm = c.getFontMetrics(c.getFont());
                ts = fm.getAscent();
                tl = fm.stringWidth(text) + 8;
                if (c.isEnabled()) {
                    g.setColor(c.getForeground());
                } else {
                    g.setColor(UIManager.getColor("Label.disabledForeground"));
                }
                g.drawString(text, x + is.left / 2 + r / 2 + ts / 2 + 4, ts);
            } else {
                Dimension dm = cmp.getPreferredSize();
                ts = dm.height;
                tl = dm.width + 8;
                cmp.setBounds(0, 0, dm.width, dm.height);
                g.translate(x + is.left / 2 + r / 2 + ts / 2 + 4, 0);
                Shape bk = g.getClip();
                g.setClip(cmp.getBounds());
                cmp.paint(g);
                g.setClip(bk);
                g.translate(-(x + is.left / 2 + r / 2 + ts / 2 + 4), 0);
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (fillColor == null) {
                int rx = x + is.left / 2;
                int ry = y + is.top / 2;
                int rw = w - is.left / 2 - is.right / 2;
                int rh = h - is.top / 2 - is.bottom / 2;
                roundBorder3D(g2, b, d, rx, ry, rw, rh, r, ts / 2, tl);
                roundBorder3D(g2, d, b, rx - 1, ry - 1, rw + 2, rh + 2, r + 1,
                        ts / 2, tl);
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        } else {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            if (fillColor == null) {
                int rx = x + is.left / 2;
                int ry = y + is.top / 2;
                int rw = w - is.left / 2 - is.right / 2;
                int rh = h - is.top / 2 - is.bottom / 2;
                roundBorder3D(g2, b, d, rx, ry, rw, rh, r, 0, 0);
                roundBorder3D(g2, d, b, rx - 1, ry - 1, rw + 2, rh + 2, r + 1,
                        0, 0);
            }
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }

    /**
     * �R���|�[�l���g�`����s�Ȃ�����ݒ肵�܂��B
     * 
     * @param drawComponent �R���|�[�l���g�`����s�Ȃ���
     */
    public void setDrawComponent(boolean drawComponent) {
        this.drawComponent = drawComponent;
    }

    /**
     * �R���|�[�l���g�`����s�Ȃ�����Ԃ��܂��B
     * 
     * @return �R���|�[�l���g�`����s�Ȃ���
     */
    public boolean isDrawComponent() {
        return drawComponent;
    }

    public Insets getBorderInsets(Component c) {
        if (borderInsets == null) {
            int h = 8;
            if (text != null && text.length() > 0) {
                FontMetrics fm = c.getFontMetrics(c.getFont());
                h = fm.getAscent() + 4;
            }
            if ((component != null) && isDrawComponent()) {
                h = component.getPreferredSize().height;
            }
            borderInsets = new Insets(h, 6, 6, 6);
        }
        return borderInsets;
    }

    /**
     * �{�[�_�[�̗]����Ԃ��܂��B
     * 
     * @param c �R���|�[�l���g
     * @param newInsets �����
     */
    public Insets getBorderInsets(Component c, Insets newInsets) {
        Insets is = getBorderInsets(c);
        newInsets.top = is.top;
        newInsets.left = is.left;
        newInsets.bottom = is.bottom;
        newInsets.right = is.right;
        return newInsets;
    }

    /**
     * �`��̈�ɍa���̃t���[����`�悵�܂��B
     * 
     * @param g2 �O���t�B�b�N�X2D
     * @param hilight �n�C���C�g�F
     * @param shadow �V���h�E�F
     * @param x �`���`��X���W
     * @param y �`���`��Y���W
     * @param width �`���`�̕�
     * @param height �`���`�̍���
     * @param round �p�یa
     * @param captionSpaceLeft �e�L�X�g�`��J�n�ʒu
     * @param captionSpaceWidth �e�L�X�g�`�敝
     */
    protected void roundBorder3D(Graphics2D g2, Color hilight, Color shadow,
            int x, int y, int width, int height, int round,
            int captionSpaceLeft, int captionSpaceWidth) {
        if (round > 0) {
            g2.setColor(hilight);
            g2.drawArc(x, y, round, round, 90, 90);
            if (captionSpaceWidth > 0) {
                g2.drawLine(x + round / 2, y, x + round / 2 + captionSpaceLeft
                        - 1, y);
                if (round / 2 + captionSpaceLeft + captionSpaceWidth < width
                        - round / 2 - 1) {
                    g2.drawLine(x + round / 2 + captionSpaceLeft
                            + captionSpaceWidth, y, x + width - round / 2 - 1,
                            y);
                }
            } else {
                g2.drawLine(x + round / 2, y, x + width - round / 2 - 1, y);
            }
            g2.drawLine(x, y + round / 2, x, y + height - round / 2 - 1);
            g2.setPaint(new GradientPaint(0f, y, hilight, 0f, y + round / 2,
                    shadow));
            g2.drawArc(x + width - round - 1, y, round, round, 0, 90);
            g2.setPaint(new GradientPaint(0f, y + height - round / 2 - 1,
                    hilight, 0f, y + height - 1, shadow));
            g2.drawArc(x, y + height - round - 1, round, round, 180, 90);
            g2.setColor(shadow);
            g2.drawLine(x + round / 2, y + height - 1, x + width - round / 2
                    - 1, y + height - 1);
            g2.drawLine(x + width - 1, y + round / 2, x + width - 1, y + height
                    - round / 2 - 1);
            g2.drawArc(x + width - round - 1, y + height - round - 1, round,
                    round, 270, 90);
        } else {
            g2.setColor(hilight);
            if (captionSpaceWidth > 0) {
                g2.drawLine(x, y, x + captionSpaceLeft - 1, y);
                g2.drawLine(x + captionSpaceLeft + captionSpaceWidth, y, x
                        + width - 1, y);
            } else {
                g2.drawLine(x, y, x + width - 1, y);
            }
            g2.drawLine(x, y, x, y + height - 1);
            g2.setColor(shadow);
            g2.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
            g2.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        }
    }
}