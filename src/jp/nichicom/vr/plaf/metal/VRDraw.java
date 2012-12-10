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
 * VRデザイン用の描画クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRDraw {

    /**
     * 四角を表す描画形状定数です。
     */
    public static final int BUTTON_SHAPE_RECT = 0;

    /**
     * 四隅を丸めた四角を表す描画形状定数です。
     */
    public static final int BUTTON_SHAPE_ROUND_RECT = 1;

    /**
     * 下方向に尖った四角を表す描画形状定数です。
     */
    public static final int BUTTON_SHAPE_POINTED_DOWN = 2;

    /**
     * 上方向に尖った四角を表す描画形状定数です。
     */
    public static final int BUTTON_SHAPE_POINTED_UP = 3;

    /**
     * 左方向に尖った四角を表す描画形状定数です。
     */
    public static final int BUTTON_SHAPE_POINTED_LEFT = 4;

    /**
     * 右方向に尖った四角を表す描画形状定数です。
     */
    public static final int BUTTON_SHAPE_POINTED_RIGHT = 5;

    /**
     * 下方向を表す描画方向定数です。
     */
    public static final int DRAW_DIRECTION_DOWN = 0;

    /**
     * 上方向を表す描画方向定数です。
     */
    public static final int DRAW_DIRECTION_UP = 1;

    /**
     * 左方向を表す描画方向定数です。
     */
    public static final int DRAW_DIRECTION_LEFT = 2;

    /**
     * 右方向を表す描画方向定数です。
     */
    public static final int DRAW_DIRECTION_RIGHT = 3;

    /**
     * 非生成コンストラクタ
     */
    private VRDraw() {
    }

    /**
     * 指定色を明るくした色を戻します。
     * 
     * @param color 指定色
     * @param factor 明るさ係数
     * @return 結果色
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
     * 指定色を暗くした色を返します。
     * 
     * @param color 指定色
     * @param factor 暗さ係数
     * @return 結果色
     */
    public static Color darker(Color color, double factor) {
        return new Color(Math.max((int) (factor * color.getRed()), 0), Math
                .max((int) (factor * color.getGreen()), 0), Math.max(
                (int) (factor * color.getBlue()), 0));
    }

    /**
     * AB指定色の合成を返します。
     * 
     * @param colorA 指定色A
     * @param colorB 指定色B
     * @param factor 合成係数
     * @return 結果色
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
     * ボタンの形状を返します。
     * 
     * @param rect ボタン矩形
     * @param buttonShape ボタン形状
     * @return ボタンシェープ
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
     * ボタン描画します。
     * 
     * @param g グラフィックス
     * @param background 背景色
     * @param focus フォーカス色
     * @param hover ホバー色
     * @param rect ボタン矩形
     * @param roundWidth 角丸の横幅
     * @param roundHeight 角丸の縦幅
     * @param buttonShape ボタン形状
     * @param b ボタン
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

        //溝描画
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

            //表面描画
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

        //フォーカスフレーム描画
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
     * ボタン描画します。
     * 
     * @param g グラフィックス
     * @param background 背景色
     * @param focus フォーカス色
     * @param hover ホバー色
     * @param rect ボタン矩形
     * @param roundWidth 角丸の横幅
     * @param roundHeight 角丸の縦幅
     * @param buttonShape ボタン形状
     * @param enabled 有効状態
     * @param pressed 押下状態
     * @param focused フォーカス状態
     * @param hovered ホバー状態
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

        //溝描画
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

        //フォーカスフレーム描画
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

        //枠描画
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

        //表面描画
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
     * サムネイル描画します。
     * 
     * @param g グラフィックス
     * @param background 背景色
     * @param backgroundDark 背景暗部色
     * @param rect ボタン矩形
     * @param roundWidth 角丸の横幅
     * @param roundHeight 角丸の縦幅
     * @param buttonShape ボタン形状
     * @param direction 描画方向
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
     * 通常枠描画します。
     * 
     * @param g グラフィックス
     * @param x X座標
     * @param y Y座標
     * @param width 幅
     * @param height 高さ
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
     * 通常枠無効描画します。
     * 
     * @param g グラフィックス
     * @param x X座標
     * @param y Y座標
     * @param width 幅
     * @param height 高さ
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
     * ボタン枠押下状態描画します。
     * 
     * @param g グラフィックス
     * @param x X座標
     * @param y Y座標
     * @param width 幅
     * @param height 高さ
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
     * ボタン枠描画します。
     * 
     * @param g グラフィックス
     * @param x X座標
     * @param y Y座標
     * @param width 幅
     * @param height 高さ
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
     * グラデーション設定します。
     * 
     * @param g2 グラフィックス2D
     * @param c1 第一色
     * @param c2 第二色
     * @param direction グラデーション方向
     * @param rect 範囲矩形
     */
    public static void setGradientPaint(Graphics2D g2, Color c1, Color c2,
            int direction, Rectangle rect) {
        setGradientPaint(g2, c1, c2, direction, rect.x, rect.y, rect.width,
                rect.height);
    }

    /**
     * グラデーション設定します。
     * 
     * @param g2 グラフィックス2D
     * @param c1 第一色
     * @param c2 第二色
     * @param direction グラデーション方向
     * @param x X座標
     * @param y Y座標
     * @param width 幅
     * @param height 高さ
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