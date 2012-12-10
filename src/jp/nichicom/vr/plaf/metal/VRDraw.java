/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;

/**
 * VR�f�U�C���p�̕`��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRDraw {

    /**
     * �l�p��\���`��`��萔�ł��B
     */
    public static final int BUTTON_SHAPE_RECT = 0;

    /**
     * �l�����ۂ߂��l�p��\���`��`��萔�ł��B
     */
    public static final int BUTTON_SHAPE_ROUND_RECT = 1;

    /**
     * �������ɐ�����l�p��\���`��`��萔�ł��B
     */
    public static final int BUTTON_SHAPE_POINTED_DOWN = 2;

    /**
     * ������ɐ�����l�p��\���`��`��萔�ł��B
     */
    public static final int BUTTON_SHAPE_POINTED_UP = 3;

    /**
     * �������ɐ�����l�p��\���`��`��萔�ł��B
     */
    public static final int BUTTON_SHAPE_POINTED_LEFT = 4;

    /**
     * �E�����ɐ�����l�p��\���`��`��萔�ł��B
     */
    public static final int BUTTON_SHAPE_POINTED_RIGHT = 5;

    /**
     * ��������\���`������萔�ł��B
     */
    public static final int DRAW_DIRECTION_DOWN = 0;

    /**
     * �������\���`������萔�ł��B
     */
    public static final int DRAW_DIRECTION_UP = 1;

    /**
     * ��������\���`������萔�ł��B
     */
    public static final int DRAW_DIRECTION_LEFT = 2;

    /**
     * �E������\���`������萔�ł��B
     */
    public static final int DRAW_DIRECTION_RIGHT = 3;

    /**
     * �񐶐��R���X�g���N�^
     */
    private VRDraw() {
    }

    /**
     * �w��F�𖾂邭�����F��߂��܂��B
     * 
     * @param color �w��F
     * @param factor ���邳�W��
     * @return ���ʐF
     */
    public static Color brighter(Color color, double factor) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        double mf = 1 - factor;

        return new Color(r + (int) ((255d - r) * mf), g
                + (int) ((255d - g) * mf), b + (int) ((255d - b) * mf));
    }

    /**
     * �w��F���Â������F��Ԃ��܂��B
     * 
     * @param color �w��F
     * @param factor �Â��W��
     * @return ���ʐF
     */
    public static Color darker(Color color, double factor) {
        return new Color(Math.max((int) (factor * color.getRed()), 0), Math
                .max((int) (factor * color.getGreen()), 0), Math.max(
                (int) (factor * color.getBlue()), 0));
    }

    /**
     * AB�w��F�̍�����Ԃ��܂��B
     * 
     * @param colorA �w��FA
     * @param colorB �w��FB
     * @param factor �����W��
     * @return ���ʐF
     */
    public static Color blend(Color colorA, Color colorB, double factor) {
        return new Color((int) (factor * colorA.getRed() + (1 - factor)
                * colorB.getRed()),
                (int) (factor * colorA.getGreen() + (1 - factor)
                        * colorB.getGreen()),
                (int) (factor * colorA.getBlue() + (1 - factor)
                        * colorB.getBlue()));
    }

    /**
     * �{�^���̌`���Ԃ��܂��B
     * 
     * @param rect �{�^����`
     * @param buttonShape �{�^���`��
     * @return �{�^���V�F�[�v
     */
    public static Shape buttonStyle(Rectangle rect, int rw, int rh,
            int buttonShape) {
        switch (buttonShape) {
        case BUTTON_SHAPE_ROUND_RECT:
            return new RoundRectangle2D.Double(rect.x, rect.y, rect.width,
                    rect.height, rw, rh);
        case BUTTON_SHAPE_POINTED_DOWN: {
            Polygon p = new Polygon();
            p.addPoint(rect.x, rect.y);
            p.addPoint(rect.x + rect.width, rect.y);
            p.addPoint(rect.x + rect.width, rect.y + rect.height - rh);
            p.addPoint(rect.x + rect.width / 2, rect.y + rect.height);
            p.addPoint(rect.x, rect.y + rect.height - rh);
            return p;
        }
        case BUTTON_SHAPE_POINTED_UP: {
            Polygon p = new Polygon();
            p.addPoint(rect.x, rect.y + rh);
            p.addPoint(rect.x + rect.width / 2, rect.y);
            p.addPoint(rect.x + rect.width, rect.y + rh);
            p.addPoint(rect.x + rect.width, rect.y + rect.height);
            p.addPoint(rect.x, rect.y + rect.height);
            return p;
        }
        case BUTTON_SHAPE_POINTED_LEFT: {
            Polygon p = new Polygon();
            p.addPoint(rect.x + rw, rect.y);
            p.addPoint(rect.x + rect.width, rect.y);
            p.addPoint(rect.x + rect.width, rect.y + rect.height);
            p.addPoint(rect.x + rw, rect.y + rect.height);
            p.addPoint(rect.x, rect.y + rect.height / 2);
            return p;
        }
        case BUTTON_SHAPE_POINTED_RIGHT: {
            Polygon p = new Polygon();
            p.addPoint(rect.x + rect.width - rw, rect.y);
            p.addPoint(rect.x + rect.width, rect.y + rect.height / 2);
            p.addPoint(rect.x + rect.width - rw, rect.y + rect.height);
            p.addPoint(rect.x, rect.y + rect.height);
            p.addPoint(rect.x, rect.y);
            return p;
        }
        }

        return new Rectangle2D.Double(rect.x, rect.y, rect.width, rect.height);
    }

    /**
     * �{�^���`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param background �w�i�F
     * @param focus �t�H�[�J�X�F
     * @param hover �z�o�[�F
     * @param rect �{�^����`
     * @param roundWidth �p�ۂ̉���
     * @param roundHeight �p�ۂ̏c��
     * @param buttonShape �{�^���`��
     * @param b �{�^��
     */
    public static void drawButtonBase(Graphics g, Color background,
            Color focus, Color hover, Rectangle rect, int roundWidth,
            int roundHeight, int buttonShape, AbstractButton b) {

        ButtonModel model = b.getModel();

        boolean pressed = (model.isArmed() && model.isPressed())
                || model.isSelected();
        boolean focused = (b.isFocusPainted() && b.hasFocus());
        boolean hovered = model.isRollover();
        boolean enabled = b.isEnabled();

        Color dark;
        Color bright;
        Color back = background;

        if (pressed) {
            if (hovered) {
                back = hover;
            } else {
                back = focus;

            }
        }

        dark = back.darker();
        bright = back.brighter();

        if (!enabled) {

            back = new Color(back.getRed(), back.getGreen(), back.getGreen(),
                    128);
            dark = new Color(dark.getRed(), dark.getGreen(), dark.getGreen(),
                    128);
            bright = new Color(bright.getRed(), bright.getGreen(), bright
                    .getGreen(), 128);

            //            if(!pressed){
            //                if(border!=null){
            //                    back=border;
            //                }
            //           }
            //            
            //            dark=VRDraw.darker(back,0.9);
            //            bright = VRDraw.brighter(back,0.8);

        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setStroke(new BasicStroke(1.4f));

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int rw = roundWidth;
        int rh = roundHeight;

        Rectangle r = new Rectangle(rect.x + 1, rect.y + 1, rect.width - 2,
                rect.height - 2);
        Shape sp;

        sp = buttonStyle(r, rw, rh, buttonShape);

        //�a�`��
        if (b.isBorderPainted()) {
            Color bdark;
            Color bhigh;

            if (!enabled) {
                bdark = new Color(0, 0, 0, 32);
                bhigh = new Color(255, 255, 255, 32);
                //                bdark= VRDraw.darker(border,0.9);
                //                bhigh = VRDraw.brighter(border,0.9);;

            } else {
                bdark = new Color(0, 0, 0, 64);
                bhigh = new Color(255, 255, 255, 64);
                //                bdark=border.darker();
                //                bhigh = border.brighter();

            }

            setGradientPaint(g2, bdark, bhigh, DRAW_DIRECTION_DOWN, r);
            g2.draw(sp);

            r.grow(-1, -1);
            rw--;
            rh--;

        }

        if (b.isContentAreaFilled() || pressed) {
            r.grow(-1, -1);
            rw--;
            rh--;
            sp = buttonStyle(r, rw, rh, buttonShape);

            //�\�ʕ`��
            if (pressed) {
                setGradientPaint(g2, dark, back, DRAW_DIRECTION_DOWN, r);
            } else {
                setGradientPaint(g2, bright, back, DRAW_DIRECTION_DOWN, r);
            }
            g2.fill(sp);

            g2.setStroke(new BasicStroke(2f));

            if (pressed) {
                setGradientPaint(g2, dark, dark, DRAW_DIRECTION_DOWN, r);

            } else {

                setGradientPaint(g2, bright, dark, DRAW_DIRECTION_DOWN, r);
            }
            g2.draw(sp);

            r.grow(1, 1);
            rw += 1;
            rh += 1;
            g2.setStroke(new BasicStroke(1.4f));

        }

        //�t�H�[�J�X�t���[���`��
        if (focused || hovered) {
            sp = buttonStyle(r, rw, rh, buttonShape);

            if (hovered) {
                g2.setPaint(hover);
            } else {
                g2.setPaint(focus);
            }

            g2.draw(sp);

        }

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

        g2.setStroke(new BasicStroke(1f));
    }

    /**
     * �{�^���`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param background �w�i�F
     * @param focus �t�H�[�J�X�F
     * @param hover �z�o�[�F
     * @param rect �{�^����`
     * @param roundWidth �p�ۂ̉���
     * @param roundHeight �p�ۂ̏c��
     * @param buttonShape �{�^���`��
     * @param enabled �L�����
     * @param pressed �������
     * @param focused �t�H�[�J�X���
     * @param hovered �z�o�[���
     */
    public static void drawButtonBase2(Graphics g, Color background,
            Color border, Color focus, Color hover, Rectangle rect,
            int roundWidth, int roundHeight, int buttonShape, boolean enabled,
            boolean pressed, boolean focused, boolean hovered) {

        Color dark;
        Color bright;
        Color back = background;

        if (pressed) {
            if (hovered) {
                back = hover;
            } else {
                back = focus;

            }
        }

        if (enabled) {

            dark = back.darker();
            bright = back.brighter();

        } else {
            if (!pressed) {
                back = border;
            }

            dark = VRDraw.darker(back, 0.9);
            bright = VRDraw.brighter(back, 0.8);

        }

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int rw = roundWidth;
        int rh = roundHeight;

        Rectangle r = rect;
        Shape sp;

        //�a�`��
        if (border != null) {
            Color bdark;
            Color bhigh;

            if (!enabled) {
                bdark = VRDraw.darker(border, 0.9);
                bhigh = VRDraw.brighter(border, 0.9);
                ;

            } else {
                bdark = border.darker();
                bhigh = border.brighter();

            }

            setGradientPaint(g2, bdark, bhigh, DRAW_DIRECTION_DOWN, r);
            sp = buttonStyle(r, rw, rh, buttonShape);
            g2.fill(sp);

            r.grow(-1, -1);
            rw--;
            rh--;

        }

        //�t�H�[�J�X�t���[���`��
        if (focused || hovered) {

            if (hovered) {
                g2.setPaint(hover);
            } else {
                g2.setPaint(focus);
            }

            sp = buttonStyle(r, rw, rh, buttonShape);
            g2.fill(sp);

        }

        r.grow(-1, -1);
        rw -= 1;
        rh -= 1;

        //�g�`��
        if (pressed) {
            setGradientPaint(g2, dark, dark.darker(), DRAW_DIRECTION_DOWN, r);

        } else {

            setGradientPaint(g2, bright.brighter(), dark.darker(),
                    DRAW_DIRECTION_DOWN, r);
        }
        sp = buttonStyle(r, rw, rh, buttonShape);
        g2.fill(sp);

        r.grow(-1, -1);
        rw -= 1;
        rh -= 1;

        //�\�ʕ`��
        if (pressed) {
            setGradientPaint(g2, dark, back, DRAW_DIRECTION_DOWN, r);
        } else {
            setGradientPaint(g2, bright, back, DRAW_DIRECTION_DOWN, r);
        }

        sp = buttonStyle(r, rw, rh, buttonShape);
        g2.fill(sp);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

    }

    /**
     * �T���l�C���`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param background �w�i�F
     * @param backgroundDark �w�i�Õ��F
     * @param rect �{�^����`
     * @param roundWidth �p�ۂ̉���
     * @param roundHeight �p�ۂ̏c��
     * @param buttonShape �{�^���`��
     * @param direction �`�����
     */

    public static void drawThmbBase(Graphics g, Color background,
            Color backgroundDark, Rectangle rect, int roundWidth,
            int roundHeight, int buttonShape, int direction) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int rw = roundWidth;
        int rh = roundHeight;

        Rectangle r = rect;
        Shape sp;

        r.grow(-1, -1);
        rw -= 1;
        rh -= 1;

        setGradientPaint(g2, backgroundDark, backgroundDark.darker(),
                direction, rect);
        sp = buttonStyle(r, rw, rh, buttonShape);
        g2.fill(sp);

        r.grow(-1, -1);
        rw--;
        rh--;

        setGradientPaint(g2, background, backgroundDark, direction, rect);
        sp = buttonStyle(r, rw, rh, buttonShape);
        g2.fill(sp);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);

    }

    /**
     * �ʏ�g�`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param x X���W
     * @param y Y���W
     * @param width ��
     * @param height ����
     */
    public static void drawFlush3DBorder(Graphics g, int x, int y, int width,
            int height) {

        g.setColor(VRLookAndFeel.getControlShadow());
        g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
        g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);

        g.setColor(VRLookAndFeel.getControlDarkShadow());
        g.drawLine(x, y, x + width - 1, y);
        g.drawLine(x, y, x, y + height - 1);

    }

    /**
     * �ʏ�g�����`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param x X���W
     * @param y Y���W
     * @param width ��
     * @param height ����
     */
    public static void drawDisabledBorder(Graphics g, int x, int y, int width,
            int height) {

        g.setColor(VRLookAndFeel.getControlHighlight());
        g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
        g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);

        g.setColor(VRLookAndFeel.getControlDisabled());
        g.drawLine(x, y, x + width - 1, y);
        g.drawLine(x, y, x, y + height - 1);

    }

    /**
     * �{�^���g������ԕ`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param x X���W
     * @param y Y���W
     * @param width ��
     * @param height ����
     */
    public static void drawPressedButtonBorder(Graphics g, int x, int y,
            int width, int height) {

        g.setColor(VRLookAndFeel.getControlHighlight());
        g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
        g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);

        g.setColor(VRLookAndFeel.getControlDarkShadow());
        g.drawLine(x, y, x + width - 1, y);
        g.drawLine(x, y, x, y + height - 1);

    }

    /**
     * �{�^���g�`�悵�܂��B
     * 
     * @param g �O���t�B�b�N�X
     * @param x X���W
     * @param y Y���W
     * @param width ��
     * @param height ����
     */
    public static void drawButtonBorder(Graphics g, int x, int y, int width,
            int height) {

        g.setColor(VRLookAndFeel.getControlDarkShadow());
        g.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
        g.drawLine(x + width - 1, y, x + width - 1, y + height - 1);

        g.setColor(VRLookAndFeel.getControlHighlight());
        g.drawLine(x, y, x + width - 1, y);
        g.drawLine(x, y, x, y + height - 1);

    }

    /**
     * �O���f�[�V�����ݒ肵�܂��B
     * 
     * @param g2 �O���t�B�b�N�X2D
     * @param c1 ���F
     * @param c2 ���F
     * @param direction �O���f�[�V��������
     * @param rect �͈͋�`
     */
    public static void setGradientPaint(Graphics2D g2, Color c1, Color c2,
            int direction, Rectangle rect) {
        setGradientPaint(g2, c1, c2, direction, rect.x, rect.y, rect.width,
                rect.height);
    }

    /**
     * �O���f�[�V�����ݒ肵�܂��B
     * 
     * @param g2 �O���t�B�b�N�X2D
     * @param c1 ���F
     * @param c2 ���F
     * @param direction �O���f�[�V��������
     * @param x X���W
     * @param y Y���W
     * @param width ��
     * @param height ����
     */
    public static void setGradientPaint(Graphics2D g2, Color c1, Color c2,
            int direction, int x, int y, int width, int height) {

        switch (direction) {
        case DRAW_DIRECTION_DOWN:
            g2.setPaint(new GradientPaint(0, y, c1, 0, y + height, c2));
            break;
        case DRAW_DIRECTION_UP:
            g2.setPaint(new GradientPaint(0, y + height, c1, 0, y, c2));
            break;
        case DRAW_DIRECTION_LEFT:
            g2.setPaint(new GradientPaint(x + width, 0, c1, x, 0, c2));
            break;
        case DRAW_DIRECTION_RIGHT:
            g2.setPaint(new GradientPaint(x, 0, c1, x + width, 0, c2));
            break;
        }

    }

}