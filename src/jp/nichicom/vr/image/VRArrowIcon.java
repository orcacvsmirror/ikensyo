/** TODO <HEAD> */
package jp.nichicom.vr.image;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.Icon;
import javax.swing.plaf.UIResource;

import jp.nichicom.vr.component.table.VRTableHeaderRenderer;

/**
 * �O�p���^�̐}�`��`�悷��[���A�C�R���N���X�ł��B�\�[�g���ꂽ�e�[�u���̃w�b�_�̈��`�悷��ۂɎg�p���܂��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see Icon
 * @see VRTableHeaderRenderer
 */
public class VRArrowIcon implements Icon, UIResource {
    /**
     * �������̌`���\���`��萔�ł��B
     */
    public static final int DOWN = 0;

    /**
     * ������̌`���\���`��萔�ł��B
     */
    public static final int UP = 1;

    /**
     * �������̌`���\���`��萔�ł��B
     */
    public static final int LEFT = 2;

    /**
     * �E�����̌`���\���`��萔�ł��B
     */
    public static final int RIGHT = 3;

    private Color arrowColor;

    private int direction;

    private int size;

    /**
     * �R���X�g���N�^
     * 
     * @param arrowColor �A�C�R���J���[
     * @param direction �A�C�R���̌���
     */
    public VRArrowIcon(Color arrowColor, int direction) {
        this(arrowColor, direction, 9);
    }

    /**
     * �R���X�g���N�^
     * 
     * @param arrowColor �A�C�R���J���[
     * @param direction �A�C�R���̌���
     * @param size �A�C�R���̃T�C�Y
     */
    public VRArrowIcon(Color arrowColor, int direction, int size) {
        this.arrowColor = arrowColor;
        this.direction = direction;
        this.size = size;
    }

    /**
     * �A�C�R���J���[��Ԃ��܂��B
     * 
     * @return arrowColor ��Ԃ��܂��B
     */
    public Color getArrowColor() {
        return arrowColor;
    }

    /**
     * �A�C�R���J���[��ݒ肵�܂��B
     * 
     * @param arrowColor �A�C�R���J���[
     */
    public void setArrowColor(Color arrowColor) {
        this.arrowColor = arrowColor;
    }

    /**
     * �A�C�R���̌�����Ԃ��܂��B
     * 
     * @return direction �A�C�R���̌���
     */
    public int getDirection() {
        return direction;
    }

    /**
     * �A�C�R���̌�����ݒ肵�܂��B
     * 
     * @param direction �A�C�R���̌���
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {

        g.translate(x, y);

        Color b = arrowColor;
        if (!c.isEnabled()) {
            b = c.getBackground().darker();
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Polygon p = new Polygon();
        switch (direction) {
        case DOWN:
            p.addPoint(0, 0);
            p.addPoint(size - 1, 0);
            p.addPoint((size - 1) / 2, size - 1);
            break;
        case UP:
            p.addPoint(0, size - 1);
            p.addPoint(size - 1, size - 1);
            p.addPoint((size - 1) / 2, 0);
            break;
        case LEFT:
            p.addPoint(size - 1, 0);
            p.addPoint(size - 1, size - 1);
            p.addPoint(0, (size - 1) / 2);
            break;
        case RIGHT:
            p.addPoint(0, 0);
            p.addPoint(0, size - 1);
            p.addPoint(size - 1, (size - 1) / 2);
            break;

        }
        g2.setColor(b);
        g2.fill(p);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF);
        g.translate(-x, -y);
    }

    public int getIconWidth() {
        return size;
    }

    public int getIconHeight() {
        return size;
    }

}