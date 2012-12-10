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
 * 三角矢印型の図形を描画する擬似アイコンクラスです。ソートされたテーブルのヘッダ領域を描画する際に使用します。
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
     * 下向きの形状を表す描画定数です。
     */
    public static final int DOWN = 0;

    /**
     * 上向きの形状を表す描画定数です。
     */
    public static final int UP = 1;

    /**
     * 左向きの形状を表す描画定数です。
     */
    public static final int LEFT = 2;

    /**
     * 右向きの形状を表す描画定数です。
     */
    public static final int RIGHT = 3;

    private Color arrowColor;

    private int direction;

    private int size;

    /**
     * コンストラクタ
     * 
     * @param arrowColor アイコンカラー
     * @param direction アイコンの向き
     */
    public VRArrowIcon(Color arrowColor, int direction) {
        this(arrowColor, direction, 9);
    }

    /**
     * コンストラクタ
     * 
     * @param arrowColor アイコンカラー
     * @param direction アイコンの向き
     * @param size アイコンのサイズ
     */
    public VRArrowIcon(Color arrowColor, int direction, int size) {
        this.arrowColor = arrowColor;
        this.direction = direction;
        this.size = size;
    }

    /**
     * アイコンカラーを返します。
     * 
     * @return arrowColor を返します。
     */
    public Color getArrowColor() {
        return arrowColor;
    }

    /**
     * アイコンカラーを設定します。
     * 
     * @param arrowColor アイコンカラー
     */
    public void setArrowColor(Color arrowColor) {
        this.arrowColor = arrowColor;
    }

    /**
     * アイコンの向きを返します。
     * 
     * @return direction アイコンの向き
     */
    public int getDirection() {
        return direction;
    }

    /**
     * アイコンの向きを設定します。
     * 
     * @param direction アイコンの向き
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