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
 * フレーム(枠)を描画するボーダークラスです。
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
     * コンストラクタ
     */
    public VRFrameBorder() {
        super();
        initComponent();
    }

    /**
     * コンストラクタ
     * 
     * @param text テキスト
     */
    public VRFrameBorder(String text) {
        super();
        this.text = text;
        initComponent();
    }

    /**
     * コンストラクタ
     * 
     * @param text テキスト
     * @param fillColor 背景色
     */
    public VRFrameBorder(String text, Color fillColor) {
        super();
        this.text = text;
        this.fillColor = fillColor;
        initComponent();
    }

    /**
     * コンストラクタ
     * 
     * @param component コンポーネント
     */
    public VRFrameBorder(JComponent component) {
        super();
        this.component = component;
        initComponent();
    }

    /**
     * コンストラクタ
     * 
     * @param component コンポーネント
     * @param fillColor 背景色
     */
    public VRFrameBorder(JComponent component, Color fillColor) {
        super();
        this.fillColor = fillColor;
        this.component = component;
        initComponent();
    }

    /**
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        setDrawComponent(true);
    }

    /**
     * テキストを返します。
     * 
     * @return テキスト
     */
    public String getText() {
        return text;
    }

    /**
     * テキストを設定します
     * 
     * @param text テキスト
     */
    public void setText(String text) {
        this.text = text;
        borderInsets = null;
    }

    /**
     * レンダリングに用いるコンポーネントを返します。
     * 
     * @return レンダリングに用いるコンポーネント
     */
    public JComponent getComponent() {
        return component;
    }

    /**
     * レンダリングに用いるコンポーネントを設定します。
     * 
     * @param component レンダリングに用いるコンポーネント
     */
    public void setComponent(JComponent component) {
        this.component = component;
    }

    /**
     * 背景色を返します。
     * 
     * @return 背景色
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * 背景色を設定します
     * 
     * @param fillColor 背景色
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
     * コンポーネント描画を行なうかを設定します。
     * 
     * @param drawComponent コンポーネント描画を行なうか
     */
    public void setDrawComponent(boolean drawComponent) {
        this.drawComponent = drawComponent;
    }

    /**
     * コンポーネント描画を行なうかを返します。
     * 
     * @return コンポーネント描画を行なうか
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
     * ボーダーの余白を返します。
     * 
     * @param c コンポーネント
     * @param newInsets 代入先
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
     * 描画領域に溝つきのフレームを描画します。
     * 
     * @param g2 グラフィックス2D
     * @param hilight ハイライト色
     * @param shadow シャドウ色
     * @param x 描画矩形のX座標
     * @param y 描画矩形のY座標
     * @param width 描画矩形の幅
     * @param height 描画矩形の高さ
     * @param round 角丸径
     * @param captionSpaceLeft テキスト描画開始位置
     * @param captionSpaceWidth テキスト描画幅
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