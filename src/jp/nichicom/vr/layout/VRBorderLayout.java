/** TODO <HEAD> */
package jp.nichicom.vr.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 上下左右中央へのドッキングをサポートするレイアウトです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see LayoutManager2
 */
public class VRBorderLayout implements LayoutManager2 {

    public float getLayoutAlignmentX(Container target) {
        return 0.5f;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0.5f;
    }

    public void invalidateLayout(Container target) {
        target.repaint();
    }

    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 配置を「ドッキング上」にする定数です。
     */
    public static final String NORTH = "North";

    /**
     * 配置を「ドッキング下」にする定数です。
     */
    public static final String SOUTH = "South";

    /**
     * 配置を「ドッキング右」にする定数です。
     */
    public static final String EAST = "East";

    /**
     * 配置を「ドッキング左」にする定数です。
     */
    public static final String WEST = "West";

    /**
     * 配置を「ドッキング中央」にする定数です。
     */
    public static final String CLIENT = "Client";

    /** 上下左右に配置 */
    private LinkedHashMap sideDocks = new LinkedHashMap();

    /** センター配置 */
    private ArrayList centerDocks = new ArrayList();

    /** 水平方向に空ける余白サイズ */
    private int hgap;

    /** 垂直方向に空ける余白サイズ */
    private int vgap;

    private boolean horizontalDivide;

    public VRBorderLayout() {
        this(0, 0);

    }

    /**
     * メインコンストラクタ - パラメータでメンバ変数を初期化します。
     * 
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     */
    public VRBorderLayout(int hgap, int vgap) {
        this(hgap, vgap, true);

    }

    /**
     * メインコンストラクタ - パラメータでメンバ変数を初期化します。
     * 
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param horizontalDivide クライアント領域を垂直に割る設定
     */
    public VRBorderLayout(int hgap, int vgap, boolean horizontalDivide) {
        this.hgap = hgap;
        this.vgap = vgap;
        this.horizontalDivide = horizontalDivide;

    }

    /**
     * コンテナにコンポーネントを追加する時に発生するイベントです。
     * 
     * @param comp 追加コンポーネント
     * @param constraints 追加方法設定
     */
    public void addLayoutComponent(Component comp, Object constraints) {
        synchronized (comp.getTreeLock()) {
            if ((constraints == null) || (constraints instanceof String)) {
                this.addLayoutComponent((String) constraints, comp);
            } else {
                throw new IllegalArgumentException(
                        "cannot add to layout: constraint must be a string (or null)");
            }
        }
    }

    /**
     * @deprecated addLayoutComponent(Component comp, Object constraints)を使用します。
     */
    public void addLayoutComponent(String name, Component comp) {
        synchronized (comp.getTreeLock()) {

            /*
             * レイアウト追加
             */
            if (name == null) {
                name = CLIENT;
            }

            if (CLIENT.equals(name)) {
                centerDocks.add(comp);
            } else if (NORTH.equals(name)) {
                sideDocks.put(comp, NORTH);
            } else if (SOUTH.equals(name)) {
                sideDocks.put(comp, SOUTH);
            } else if (EAST.equals(name)) {
                sideDocks.put(comp, EAST);
            } else if (WEST.equals(name)) {
                sideDocks.put(comp, WEST);
            } else {
                throw new IllegalArgumentException(
                        "cannot add to layout: unknown constraint: " + name);
            }

        }

    }

    /**
     * コンテナからコンポーネントを削除する時に発生するイベントです。
     * 
     * @param comp 削除コンポーネント
     */
    public void removeLayoutComponent(Component comp) {
        if (sideDocks.get(comp) != null) {
            sideDocks.remove(comp);
            return;
        }

        if (centerDocks.indexOf(comp) >= 0) {
            centerDocks.remove(comp);
            return;
        }

    }

    /**
     * コンテナ内のコンポーネントに基づいて、このレイアウトマネージャを使用するためのコンテナの推奨サイズを返します。
     * 
     * @param target 配置が行われるコンテナ
     * @return 指定されたコンテナの子コンポーネントを配置するために必要な推奨サイズを返します。
     */
    public Dimension preferredLayoutSize(Container target) {

        synchronized (target.getTreeLock()) {

            Insets insets = target.getInsets();

            int nmembers = target.getComponentCount();
            int y = 0;
            int w = target.getWidth();

            int cx = insets.left + hgap;
            int cy = insets.top + vgap;
            int cr = insets.right + hgap;
            int cb = insets.bottom + vgap;

            boolean ft = true;
            boolean fb = true;

            int keepheight = 0;
            int keepwidth = 0;

            Iterator it = sideDocks.keySet().iterator();

            while (it.hasNext()) {
                Component c = (Component) it.next();

                if (c.isVisible()) {

                    String key = (String) sideDocks.get(c);
                    //Y方向のアライメントを優先して行う（その後X方向のアライメント）
                    if (NORTH.equals(key)) {
                        Dimension cd = c.getPreferredSize();
                        //                        if (cd.width != w - cx - cr) {
                        //                            Dimension ors = c.getSize();
                        //                            c.setSize(w - cx - cr, cd.height);
                        //                            cd = c.getPreferredSize();
                        //                            c.setSize(ors);
                        //
                        //                        }

                        cy += cd.height + vgap;

                        ft = false;
                        keepwidth = (int) Math.max(keepwidth, cx + cr
                                + cd.width);
                    } else if (SOUTH.equals(key)) {

                        Dimension cd = c.getPreferredSize();
                        //                        if (cd.width != w - cx - cr) {
                        //                            Dimension ors = c.getSize();
                        //                            c.setSize(w - cx - cr, cd.height);
                        //                            cd = c.getPreferredSize();
                        //                            c.setSize(ors);
                        //                        }
                        cb = cb + cd.height + vgap;

                        fb = false;
                        keepwidth = (int) Math.max(keepwidth, cx + cr
                                + cd.width);
                    } else if (WEST.equals(key)) {
                        //                        Dimension ors = c.getSize();
                        //                        c.setSize(1, 1);
                        Dimension cd = c.getPreferredSize();
                        //                        c.setSize(ors);
                        cx += cd.width + hgap;
                        keepheight = (int) Math.max(cd.height + cy + cb,
                                keepheight);
                    } else if (EAST.equals(key)) {
                        //                        Dimension ors = c.getSize();
                        //                        c.setSize(1, 1);
                        Dimension cd = c.getPreferredSize();
                        //                        c.setSize(ors);
                        cr += cd.width + hgap;
                        keepheight = (int) Math.max(cd.height + cy + cb,
                                keepheight);
                    }

                }

            }

            int cWidth = 0;
            int cHeight = 0;

            if (centerDocks.size() > 0) {

                int cliw = w - cx - cr;
                if (isHorizontalDivide()) {
                } else {
                    cliw = cliw / centerDocks.size();
                }

                for (int i = 0; i <= centerDocks.size() - 1; i++) {

                    Component c = (Component) centerDocks.get(i);

                    Dimension cd = c.getPreferredSize();
                    if (cd.width != cliw) {
                        Dimension ors = c.getSize();
                        c.setSize(cliw, cd.height);
                        cd = c.getPreferredSize();
                        c.setSize(ors);

                    }

                    cHeight = (int) Math.max(cd.height, cHeight);
                    cWidth = (int) Math.max(cd.width, cWidth);

                }
                //等分割するための計算
                if (isHorizontalDivide()) {
                    cHeight = cHeight * centerDocks.size() + vgap
                            * (centerDocks.size() - 1);
                } else {
                    cWidth = cWidth * centerDocks.size() + hgap
                            * (centerDocks.size() - 1);
                }

                keepwidth = (int) Math.max(keepwidth, cx + cr + cWidth);

                keepheight = (int) Math.max(cHeight + cy + cb, keepheight);

            } else {
                keepwidth = (int) Math.max(keepwidth, cx + cr);

                keepheight = (int) Math.max(keepheight, cy + cb);

            }

            return new Dimension(keepwidth, keepheight);

        }

    }

    /**
     * コンテナ内のコンポーネントに基づいて、このレイアウトマネージャを使用するためのコンテナの最小サイズを返します。
     * 
     * @param target 配置が行われるコンテナ
     * @return 指定されたコンテナの子コンポーネントを配置するために必要な最小サイズ
     */
    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = new Dimension(0, 0);
            int nmembers = target.getComponentCount();

            int lw = 0;
            int lh = 0;
            int inw = 0;

            Iterator it = sideDocks.keySet().iterator();

            while (it.hasNext()) {
                Component m = (Component) it.next();
                if (m.isVisible()) {
                    Dimension d = m.getMinimumSize();
                    String key = (String) sideDocks.get(m);
                    if (NORTH.equals(key) || SOUTH.equals(key)) {
                        dim.height += d.height;
                    } else if (WEST.equals(key) || EAST.equals(key)) {
                        dim.width += d.width;
                    }
                }

            }

            dim.height += lh + vgap;
            inw = (int) Math.max(inw, lw);

            dim.width += inw;

            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right + hgap;
            dim.height += insets.top + insets.bottom + vgap;
            return dim;
        }
    }

    /**
     * このレイアウトを使用してコンテナを配置します。
     * 
     * @param target 配置が行われるコンテナ
     */
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int nmembers = target.getComponentCount();
            int x = 0, y = 0;

            int rowh = 0, start = 0;
            int calcx = 0;

            int w = target.getWidth();
            int h = target.getHeight();
            int cx = insets.left + hgap;
            int cy = insets.top + vgap;
            int cr = insets.right + hgap;
            int cb = insets.bottom + vgap;

            boolean ft = true;
            boolean fb = true;

            //サイドドッキングコントロール

            Iterator it = sideDocks.keySet().iterator();

            while (it.hasNext()) {
                Component c = (Component) it.next();

                if (c.isVisible()) {
                    String key = (String) sideDocks.get(c);
                    if (NORTH.equals(key)) {
                        c.setSize(w - cr - cx, c.getHeight());
                        Dimension cd = c.getPreferredSize();
                        c.setBounds(cx, cy, w - cr - cx, cd.height);
                        cy += cd.height + vgap;

                        ft = false;
                    } else if (SOUTH.equals(key)) {
                        c.setSize(w - cr - cx, c.getHeight());
                        Dimension cd = c.getPreferredSize();

                        c.setBounds(cx, h - cb - cd.height, w - cr - cx,
                                cd.height);

                        cb += cd.height + vgap;
                        fb = false;
                    } else if (WEST.equals(key)) {
                        c.setSize(1, c.getHeight());
                        Dimension cd = c.getPreferredSize();
                        c.setBounds(cx, cy, cd.width, h - cb - cy);
                        cx = cx + cd.width + hgap;
                    } else if (EAST.equals(key)) {
                        c.setSize(1, c.getHeight());
                        Dimension cd = c.getPreferredSize();
                        c.setBounds(w - cr - cd.width, cy, cd.width, h - cb
                                - cy);
                        cr = cr + cd.width + hgap;
                    }

                }

            }

            //中央配置コントロール

            if (centerDocks.size() > 0) {
                int cWidth = w - cr - cx;
                int cHeight = h - cb - cy;

                if (isHorizontalDivide()) {
                    cHeight = (cHeight - vgap * (centerDocks.size() - 1))
                            / centerDocks.size();
                    for (int i = 0; i <= centerDocks.size() - 1; i++) {
                        Component c = (Component) centerDocks.get(i);
                        c.setBounds(cx, cy + (cHeight + vgap) * i, cWidth,
                                cHeight);
                    }
                } else {
                    cWidth = (cWidth - hgap * (centerDocks.size() - 1))
                            / centerDocks.size();
                    for (int i = 0; i <= centerDocks.size() - 1; i++) {
                        Component c = (Component) centerDocks.get(i);
                        c.setBounds(cx + (cWidth + hgap) * i, cy, cWidth,
                                cHeight);
                    }

                }
            }

        }
    }

    /**
     * このレイアウトの状態の文字列表現を返します。
     * 
     * @return このレイアウトの状態の文字列表現
     */
    public String toString() {

        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap
                + ",horizontalDivid=" + horizontalDivide + " ]";
    }

    /**
     * 水平方向に空ける余白サイズを返します。
     * 
     * @return 余白サイズを返します。
     */
    public int getHgap() {
        return hgap;
    }

    /**
     * 水平方向に空ける余白サイズを設定します。
     * 
     * @param hgap 余白サイズ
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    /**
     * 垂直方向に空ける余白サイズを返します。
     * 
     * @return 余白サイズ
     */
    public int getVgap() {
        return vgap;
    }

    /**
     * 垂直方向に空ける余白サイズを設定します。
     * 
     * @param vgap 余白サイズ
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    /**
     * 水平分割するかを返します。
     * 
     * @return horizontalDivide
     */
    public boolean isHorizontalDivide() {
        return horizontalDivide;
    }

    /**
     * 水平分割するかを設定します。
     * 
     * @param horizontalDivide 水平分割するか
     */
    public void setHorizontalDivide(boolean horizontalDivide) {
        this.horizontalDivide = horizontalDivide;
    }
}