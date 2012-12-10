package jp.nichicom.ac.container;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JComponent;

import jp.nichicom.vr.border.VRFrameBorder;
import jp.nichicom.vr.component.VRLabel;

/**
 * 枠線で囲むグループボックスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 * @see ACPanel
 */

public class ACGroupBox extends ACPanel {
    private VRFrameBorder border;
    private VRLabel textRenderer;

    /**
     * コンストラクタです。
     */
    public ACGroupBox() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param component 境界に表示するコンポーネント
     */
    public ACGroupBox(JComponent component) {
        super();
        setComponent(component);
    }

    /**
     * コンストラクタです。
     * 
     * @param text 境界のテキスト
     */
    public ACGroupBox(String text) {
        super();
        setText(text);
    }

    /**
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        super.initComponent();
        textRenderer = new VRLabel();
        border = new VRFrameBorder();
        border.setDrawComponent(false);
        border.setComponent(textRenderer);
        setBorder(border);
    }

    /**
     * 境界のテキストを返します。
     * 
     * @return 境界のテキスト
     */
    public String getText() {
        return border.getText();
    }

    /**
     * 境界のテキストを設定します。
     * 
     * @param text 境界のテキスト
     */
    public void setText(String text) {
        border.setText(text);
        if (textRenderer != null) {
            textRenderer.setText(text);
            if ((text == null) || ("".equals(text))) {
                border.setDrawComponent(false);
            } else {
                border.setDrawComponent(true);
            }
        }
        repaint();
    }

    public void setForeground(Color foreground) {
        if (textRenderer != null) {
            textRenderer.setForeground(foreground);
        }
        super.setForeground(foreground);
    }

    /**
     * 境界に表示するコンポーネントを返します。
     * 
     * @return 境界に表示するコンポーネント
     */
    public JComponent getComponent() {
        return border.getComponent();
    }

    /**
     * 境界に表示するコンポーネントを設定します。
     * 
     * @param component 境界に表示するコンポーネント
     */
    public void setComponent(JComponent component) {
        border.setComponent(component);
    }

    /**
     * 塗りつぶし色を設定します。
     * 
     * @param fillColor 塗りつぶし色
     */
    public void setFillColor(Color fillColor) {
        border.setFillColor(fillColor);
    }

    /**
     * 塗りつぶし色を返します。
     * 
     * @return 塗りつぶし色
     */
    public Color getFillColor() {
        return border.getFillColor();
    }

    public Dimension getPreferredSize() {
        Dimension dm = super.getPreferredSize();

        if (getComponent() != null) {
            FontMetrics fm = getFontMetrics(getFont());
            int w = (int) getComponent().getPreferredSize().getWidth()
                    + fm.getHeight() * 2;
            if (dm.width < w) {
                dm.width = w;
            }

        } else if (getText() != null && getText().length() > 0) {

            FontMetrics fm = getFontMetrics(getFont());

            int w = fm.stringWidth(getText()) + fm.getHeight() * 2;
            if (dm.width < w) {
                dm.width = w;
            }
        }

        return dm;

    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (textRenderer != null) {
            textRenderer.setEnabled(enabled);
        }
    }
}
