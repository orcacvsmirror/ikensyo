package jp.nichicom.ac.container;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JComponent;

import jp.nichicom.vr.border.VRFrameBorder;
import jp.nichicom.vr.component.VRLabel;

/**
 * �g���ň͂ރO���[�v�{�b�N�X�ł��B
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
     * �R���X�g���N�^�ł��B
     */
    public ACGroupBox() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param component ���E�ɕ\������R���|�[�l���g
     */
    public ACGroupBox(JComponent component) {
        super();
        setComponent(component);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param text ���E�̃e�L�X�g
     */
    public ACGroupBox(String text) {
        super();
        setText(text);
    }

    /**
     * �R���|�[�l���g�����������܂��B
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
     * ���E�̃e�L�X�g��Ԃ��܂��B
     * 
     * @return ���E�̃e�L�X�g
     */
    public String getText() {
        return border.getText();
    }

    /**
     * ���E�̃e�L�X�g��ݒ肵�܂��B
     * 
     * @param text ���E�̃e�L�X�g
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
     * ���E�ɕ\������R���|�[�l���g��Ԃ��܂��B
     * 
     * @return ���E�ɕ\������R���|�[�l���g
     */
    public JComponent getComponent() {
        return border.getComponent();
    }

    /**
     * ���E�ɕ\������R���|�[�l���g��ݒ肵�܂��B
     * 
     * @param component ���E�ɕ\������R���|�[�l���g
     */
    public void setComponent(JComponent component) {
        border.setComponent(component);
    }

    /**
     * �h��Ԃ��F��ݒ肵�܂��B
     * 
     * @param fillColor �h��Ԃ��F
     */
    public void setFillColor(Color fillColor) {
        border.setFillColor(fillColor);
    }

    /**
     * �h��Ԃ��F��Ԃ��܂��B
     * 
     * @return �h��Ԃ��F
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
