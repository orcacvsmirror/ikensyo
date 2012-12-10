/** TODO <HEAD> */
package jp.nichicom.vr.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JComponent;

/**
 * 上下左右中央へのドッキングの他、列挙や整列をサポートするレイアウトです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see LayoutManager2
 */
public class VRLayout implements LayoutManager2 {

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

    /**
     * 配置を「フロー」にする定数です。
     */
    public static final String FLOW = "Flow";

    /**
     * 配置を「フロー改行」にする定数です。
     */
    public static final String FLOW_RETURN = "Return";

    /**
     * 配置を「フローインセット」にする定数です。
     */
    public static final String FLOW_INSETLINE = "Inset";

    /**
     * 配置を「フローインセット改行」にする定数です。
     */
    public static final String FLOW_INSETLINE_RETURN = "InsetReturn";

    /**
     * 配置を「二重フローインセット」にする定数です。
     */
    public static final String FLOW_DOUBLEINSETLINE = "DoubleInset";

    /**
     * 配置を「二重フローインセット改行」にする定数です。
     */
    public static final String FLOW_DOUBLEINSETLINE_RETURN = "DoubleInsetReturn";

    /**
     * 配置を「フローLEFT」にする定数です。
     */
    public static final String FLOW_LEFT = "Left";

    /** 配置を「左端」に設定する定数です。 */
    public static final int LEFT = 0;

    /** 配置を「中央」に設定する定数です。 */
    public static final int CENTER = 1;

    /** 配置を「右端」に設定する定数です。 */
    public static final int RIGHT = 2;

    /** 配置を「先頭」に設定する定数です。 */
    public static final int LEADING = 3;

    /** 配置を「末尾」に設定する定数です。 */
    public static final int TRAILING = 4;

    /** 配置を「上端」に設定する定数です。 */
    public static final int TOP = 0;

    /** 配置を「下端」に設定する定数です。 */
    public static final int BOTTOM = 2;

    /** 上下左右に配置 */
    private LinkedHashMap sideDocks = new LinkedHashMap();

    /** センター配置 */
    private ArrayList centerDocks = new ArrayList();

    /** 自動配置 */
    private LinkedHashMap flows = new LinkedHashMap();

    /** 配置方法(LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか) */
    private int alignment;

    private int vAlignment;

    /** 水平方向に空ける余白サイズ */
    private int hgap;

    /** 垂直方向に空ける余白サイズ */
    private int vgap;

    /** 水平方向のグリッドの幅 */
    private int vgrid;

    /** 垂直方向のグリッドの高さ */
    private int hgrid;

    /** 水平方向いっぱいに幅を広げるか */
    private boolean fitHGrid;

    /** 垂直方向いっぱいに高さを広げるか */
    private boolean fitVGrid;

    /** 水平方向いっぱいに幅を広げるか */
    private boolean fitHLast;

    /** 垂直方向いっぱいに高さを広げるか */
    private boolean fitVLast;

    /** 自動で改行するか */
    private boolean autoWrap;

    /** 行毎の垂直配置方向 */
    private int vLineAlign;

    /** キャプションをオーバーさせる設定 */
    private boolean labelOverwrap;

    /** ラベルのマージン */
    private int labelMargin;

    /** 自動伸縮させるか */
    private boolean stretch;

    /** 水平分割させるか */
    private boolean horizontalDivide;

    /**
     * パラメータを全て自動設定し、オーバーロードコンストラクタを呼び出します。
     * <p>
     * 【自動設定値】 <br>
     * align - 配置方向：LEFT <br>
     * hgap - 親水平方向に空ける余白サイズ：2 <br>
     * vgap - 垂直方向に空ける余白サイズ：2
     * </p>
     */
    public VRLayout() {
        this(LEFT, 20, 2);
    }

    /**
     * 指定なしのパラメータを自動設定し、オーバーロードコンストラクタを呼び出します。
     * <p>
     * 【自動設定値】 <br>
     * hgap - 親水平方向に空ける余白サイズ：2 <br>
     * vgap - 垂直方向に空ける余白サイズ：2
     * </p>
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     */
    public VRLayout(int align) {
        this(align, 20, 2);
    }

    /**
     * 指定なしのパラメータを自動設定し、オーバーロードコンストラクタを呼び出します。
     * <p>
     * 【自動設定値】 <br>
     * hgrid - 水平方向のグリッドの幅：120 <br>
     * vgrid - 垂直方向のグリッドの高さ：1 <br>
     * fitHgrid - 水平方向に最大まで幅を広げる場合はtrue：false <br>
     * fitVgrid - 垂直方向に最大まで高さを広げる場合はtrue：false
     * </p>
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     */
    public VRLayout(int align, int hgap, int vgap) {
        this(align, hgap, vgap, 60, 1, false, false);
    }

    /**
     * 指定なしのパラメータを自動設定し、オーバーロードコンストラクタを呼び出します。
     * <p>
     * 【自動設定値】 <br>
     * vLineAlign - 行の垂直配置方向：TOP
     * </p>
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param hgrid 水平方向のグリッドの幅
     * @param vgrid 垂直方向のグリッドの高さ
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid) {

        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, TOP);
    }

    /**
     * 指定なしのパラメータを自動設定し、メインコンストラクタを呼び出します。
     * <p>
     * 【自動設定値】 <br>
     * autoWrap - 自動改行する場合はtrue：false
     * </p>
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param hgrid 水平方向のグリッドの幅
     * @param vgrid 垂直方向のグリッドの高さ
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     * @param vLineAlign 行の垂直配置方向
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                true);
    }

    /**
     * 指定なしのパラメータを自動設定し、オーバーロードコンストラクタを呼び出します。
     * <p>
     * 【自動設定値】 <br>
     * labelOverwrap - 整列ラベルのオーバーラップを許可する場合はtrue：false <br>
     * labelMargin - ラベルマージン：100 <br>
     * stretch - ストレッチする場合はtrue：true
     * </p>
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param hgrid 水平方向のグリッドの幅
     * @param vgrid 垂直方向のグリッドの高さ
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     * @param vLineAlign 行の垂直配置方向
     * @param autoWrap 自動改行する場合はtrue
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign, boolean autoWrap) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                autoWrap, true, 60, false);
    }

    /**
     * メインコンストラクタ - パラメータでメンバ変数を初期化します。
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param hgrid 水平方向のグリッドの幅
     * @param vgrid 垂直方向のグリッドの高さ
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     * @param vLineAlign 行の垂直配置方向
     * @param autoWrap 自動改行する場合はtrue
     * @param labelOverwrap オーバーラップ設定を緩和する場合はtrue
     * @param labelMargin ラベルマージン
     * @param stretch ストレッチする場合はtrue
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign,
            boolean autoWrap, boolean labelOverwrap, int labelMargin,
            boolean stretch) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                autoWrap, labelOverwrap, labelMargin, stretch, TOP);

    }

    /**
     * メインコンストラクタ - パラメータでメンバ変数を初期化します。
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param hgrid 水平方向のグリッドの幅
     * @param vgrid 垂直方向のグリッドの高さ
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     * @param vLineAlign 行の垂直配置方向
     * @param autoWrap 自動改行する場合はtrue
     * @param labelOverwrap オーバーラップ設定を緩和する場合はtrue
     * @param labelMargin ラベルマージン
     * @param stretch ストレッチする場合はtrue
     * @param vAlign 配置方向（TOP, CENTER, BOTTOM, LEADING, TRAILINGのいずれか）
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign,
            boolean autoWrap, boolean labelOverwrap, int labelMargin,
            boolean stretch, int vAlign) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                autoWrap, labelOverwrap, labelMargin, stretch, vAlign, false,
                false, false);

    }

    /**
     * メインコンストラクタ - パラメータでメンバ変数を初期化します。
     * 
     * @param align 配置方向（LEFT, CENTER, RIGHT, LEADING, TRAILINGのいずれか）
     * @param hgap 水平方向に空ける余白サイズ
     * @param vgap 垂直方向に空ける余白サイズ
     * @param hgrid 水平方向のグリッドの幅
     * @param vgrid 垂直方向のグリッドの高さ
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     * @param vLineAlign 行の垂直配置方向
     * @param autoWrap 自動改行する場合はtrue
     * @param labelOverwrap オーバーラップ設定を緩和する場合はtrue
     * @param labelMargin ラベルマージン
     * @param stretch ストレッチする場合はtrue
     * @param vAlign 配置方向（TOP, CENTER, BOTTOM, LEADING, TRAILINGのいずれか）
     * @param horizontalDivide クライアント領域を水平方向に分割する場合はtrue
     * @param fitHLast 水平方向に最後まで幅を広げる場合はtrue
     * @param fitVLast 垂直方向に最後まで高さを広げる場合はtrue
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign,
            boolean autoWrap, boolean labelOverwrap, int labelMargin,
            boolean stretch, int vAlign, boolean horizontalDivide,
            boolean fitHLast, boolean fitVLast) {
        this.hgap = hgap;
        this.vgap = vgap;
        this.hgrid = hgrid;
        this.vgrid = vgrid;
        setFitHGrid(fitHgrid);
        setFitVGrid(fitVgrid);
        setAlignment(align);
        this.vLineAlign = vLineAlign;
        this.autoWrap = autoWrap;
        this.labelOverwrap = labelOverwrap;
        this.labelMargin = labelMargin;
        this.stretch = stretch;
        this.vAlignment = vAlign;
        this.horizontalDivide = horizontalDivide;
        this.fitHLast = fitHLast;
        this.fitVLast = fitVLast;

        initComponent();
    }

    /**
     * コンテナにコンポーネントを追加する時に発生するイベントです。
     * 
     * @param comp 追加コンポーネント
     * @param constraints 追加設定
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
                flows.put(comp, null);
            } else if (FLOW.equals(name)) {
                flows.put(comp, null);
            } else if (FLOW_RETURN.equals(name)) {
                flows.put(comp, FLOW_RETURN);
            } else if (FLOW_LEFT.equals(name)) {
                flows.put(comp, FLOW_LEFT);
            } else if (FLOW_INSETLINE.equals(name)) {
                flows.put(comp, (comp instanceof JComponent) ? FLOW_INSETLINE
                        : null);
            } else if (FLOW_INSETLINE_RETURN.equals(name)) {
                flows.put(comp,
                        (comp instanceof JComponent) ? FLOW_INSETLINE_RETURN
                                : FLOW_RETURN);
            } else if (FLOW_DOUBLEINSETLINE.equals(name)) {
                flows.put(comp,
                        (comp instanceof JComponent) ? FLOW_DOUBLEINSETLINE
                                : null);
            } else if (FLOW_DOUBLEINSETLINE_RETURN.equals(name)) {
                flows
                        .put(
                                comp,
                                (comp instanceof JComponent) ? FLOW_DOUBLEINSETLINE_RETURN
                                        : FLOW_RETURN);
            } else if (CLIENT.equals(name)) {
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
     * @param comp
     */
    public void removeLayoutComponent(Component comp) {
        if (removeComponent(sideDocks, comp)) {
            return;
        }

        if (centerDocks.indexOf(comp) >= 0) {
            centerDocks.remove(comp);
            return;
        }

        if (removeComponent(flows, comp)) {
            return;
        }
        //        if (flows.get(comp) != null) {
        //            flows.remove(comp);
        //            return;
        //        }

    }

    /**
     * マップからコンポーネントをキーとするエントリを削除します。
     * 
     * @param map 削除元
     * @param comp 比較対象
     * @return 削除したか
     */
    protected boolean removeComponent(Map map, Component comp) {
        Object[] objs = map.keySet().toArray();
        if (comp != null) {
            for (int i = 0; i < objs.length; i++) {
                if (comp.equals(objs[i])) {
                    map.remove(comp);
                    return true;
                }
            }
        } else {
            if (map.get(null) != null) {
                map.remove(comp);
                return true;
            }
        }
        return false;
    }

    /**
     * コンテナ内のコンポーネントに基づいて、このレイアウトマネージャを使用するためのコンテナの推奨サイズを返します。
     * 
     * @param target 配置が行われるコンテナ
     * @return 指定されたコンテナの子コンポーネントを配置するために必要な推奨サイズ
     */
    public Dimension preferredLayoutSize(Container target) {

        synchronized (target.getTreeLock()) {

            Insets insets = target.getInsets();

            int nmembers = target.getComponentCount();
            int w = target.getWidth();

            int cx = insets.left;
            int cy = insets.top;
            int cr = insets.right;
            int cb = insets.bottom;

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
                        cy += cd.height;

                        ft = false;
                        keepwidth = (int) Math.max(keepwidth, cx + cr
                                + cd.width);
                    } else if (SOUTH.equals(key)) {
                        Dimension cd = c.getPreferredSize();
                        cb = cb + cd.height;

                        fb = false;
                        keepwidth = (int) Math.max(keepwidth, cx + cr
                                + cd.width);
                    } else if (WEST.equals(key)) {
                        Dimension cd = c.getPreferredSize();
                        cx += cd.width;
                        keepheight = (int) Math.max(cd.height + cy + cb,
                                keepheight);
                    } else if (EAST.equals(key)) {
                        Dimension cd = c.getPreferredSize();
                        cr += cd.width;
                        keepheight = (int) Math.max(cd.height + cy + cb,
                                keepheight);
                    }

                }

            }

            int cWidth = 0;
            int cHeight = 0;

            if (centerDocks.size() > 0) {

//                int cliw = w - cx - cr;
//                if (isHorizontalDivide()) {
//                } else {
//                    cliw = cliw / centerDocks.size();
//                }

                int visibleCount = 0;
                for (int i = 0; i <= centerDocks.size() - 1; i++) {

                    Component c = (Component) centerDocks.get(i);
                    if(c.isVisible()){
                        visibleCount++;
                        Dimension cd = c.getPreferredSize();
                        cHeight = (int) Math.max(cd.height, cHeight);
                        cWidth = (int) Math.max(cd.width, cWidth);
                    }
                }
                //等分割するための計算
//                if (isHorizontalDivide()) {
//                    cHeight = cHeight * centerDocks.size();
//                } else {
//                    cWidth = cWidth * centerDocks.size();
//                }
                if (isHorizontalDivide()) {
                    cHeight = cHeight * visibleCount;
                } else {
                    cWidth = cWidth * visibleCount;
                }

                keepwidth = (int) Math.max(keepwidth, cx + cr + cWidth);

                keepheight = (int) Math.max(cHeight + cy + cb, keepheight);

            }

            //基点のギャップ設定
            cy += vgap;
            cb += vgap;

            int x = 0;
            int y = 0;
            int rowh = 0;
            int roww = 0;
            int firstMargin = labelMargin - (cx - insets.left);
            if (firstMargin < 0) {
                firstMargin = 0;
            }

            int maxwidth = w - (cr + cx);

            //自動改行なし

            int lmw = 0;
            int isl = 0;

            int shiftX = 0;
            int shiftEndY = 0;

            it = flows.keySet().iterator();

            while (it.hasNext()) {
                Component m = (Component) it.next();

                if (m.isVisible()) {

                    String key = (String) flows.get(m);

                    Dimension d = m.getPreferredSize();
                    Dimension md = m.getMinimumSize();

                    //最初に、今回のコントロールを追加することにより改行が発生するかを確認し、
                    //改行が発生する場合、行の幅計算をする前に改行させる
                    if (autoWrap
                            && (lmw + ((stretch) ? md.width : d.width) + hgap > maxwidth && lmw > 0)) {

                        //最小値の限界を超えた場合
                        y += (int) Math.max(rowh + vgap, vgrid);
                        roww = (int) Math.max(x, roww);

                        //行頭調整
                        if (y + d.height + vgap > shiftEndY
                                || (shiftX + ((stretch) ? md.width : d.width)
                                        + hgap > maxwidth)) {
                            y = (int) Math.max(shiftEndY, y);
                            shiftX = 0;
                            shiftEndY = 0;
                        }

                        x = shiftX;
                        lmw = shiftX;
                        rowh = 0;

                    }

                    //行頭を合わせる計算を行う

                    isl = 0;
                    if (FLOW_INSETLINE.equals(key)
                            || FLOW_INSETLINE_RETURN.equals(key)) {
                        JComponent jc = (JComponent) m;
                        isl = jc.getInsets().left;
                    } else if (FLOW_DOUBLEINSETLINE.equals(key)
                            || FLOW_DOUBLEINSETLINE_RETURN.equals(key)) {
                        JComponent jc = (JComponent) m;
                        isl = getLeftInset(jc);
                    }

                    if (isl > 0) {
                        //ラベルコンテナ関連
                        if (lmw + isl < firstMargin) {
                            lmw = firstMargin - isl;
                        }

                        if (x + isl < firstMargin) {
                            x = firstMargin - isl;
                        }

                    } else {
                        //ラベルコンテナ以外で、かつ行頭の場合、ギャップを追加する
                        if (lmw == 0) {
                            x = hgap;
                            lmw = hgap;
                        }
                    }

                    if (FLOW_LEFT.equals(key)) {
                        shiftX += d.width + hgap;
                        shiftEndY = (int) Math.max(shiftEndY, y + d.height
                                + vgap);
                    } else {
                        rowh = (int) Math.max(rowh, d.height);

                    }

                    //行の幅を追加する
                    x += d.width + hgap;
                    lmw += ((stretch) ? md.width : d.width) + hgap;

                    //このコントロールが改行機能付である場合、改行する
                    if (FLOW_RETURN.equals(key)
                            || FLOW_INSETLINE_RETURN.equals(key)
                            || FLOW_DOUBLEINSETLINE_RETURN.equals(key)) {
                        y += (int) Math.max(rowh + vgap, vgrid);
                        roww = (int) Math.max(x, roww);

                        //行頭調整
                        y = (int) Math.max(shiftEndY, y);
                        shiftX = 0;
                        shiftEndY = 0;

                        x = 0;
                        rowh = 0;
                        lmw = 0;

                    }
                }
            }

            if (rowh > 0) {
                y += (int) Math.max(rowh + vgap, vgrid);
            }
            roww = (int) Math.max(x, roww);

            //垂直位置の調整
            if (fitVGrid) {
                y = (int) (Math.ceil((double) y / (double) vgrid) * vgrid)
                        - vgap;
            } else {
                y -= vgap;
            }

            //全体の高さを取得
            y += ((int) Math.max(cy, insets.top + vgap) + (int) Math.max(
                    insets.bottom + vgap, cb));

            //最低確保高の設定
            y = (int) Math.max(y, keepheight);

            //横位置の調整
            if (fitHGrid) {
                roww += hgap;
                roww = (int) (Math.ceil((double) roww / (double) hgrid) * hgrid)
                        - hgap;
            }

            //全体の幅設定
            roww += cx + cr;

            roww = (int) Math.max(keepwidth, roww);

            return new Dimension(roww, y);

        }

    }

    public int getLeftInset(JComponent c) {
        int l = c.getInsets().left;
        if (c.getComponentCount() > 0) {
            if (c.getComponent(0) instanceof JComponent) {
                JComponent jc = (JComponent) c.getComponent(0);
                l += jc.getInsets().left;
            }
        }
        return l;

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

            it = flows.keySet().iterator();

            while (it.hasNext()) {
                Component m = (Component) it.next();
                if (m.isVisible()) {
                    Dimension d = m.getMinimumSize();
                    String key = (String) flows.get(m);

                    lh = (int) Math.max(lh, d.height);
                    lw += d.width + hgap;
                    if (FLOW_RETURN.equals(key)
                            || FLOW_INSETLINE_RETURN.equals(key)
                            || FLOW_DOUBLEINSETLINE_RETURN.equals(key)) {
                        dim.height += lh + vgap;
                        inw = (int) Math.max(inw, lw);
                        lw = 0;
                        lh = 0;

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
            int cx = insets.left;
            int cy = insets.top;
            int cr = insets.right;
            int cb = insets.bottom;

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
                        cy += cd.height;

                        ft = false;
                    } else if (SOUTH.equals(key)) {
                        c.setSize(w - cr - cx, c.getHeight());
                        Dimension cd = c.getPreferredSize();

                        c.setBounds(cx, h - cb - cd.height, w - cr - cx,
                                cd.height);

                        cb += cd.height;
                        fb = false;
                    } else if (WEST.equals(key)) {
                        c.setSize(1, c.getHeight());
                        Dimension cd = c.getPreferredSize();
                        c.setBounds(cx, cy, cd.width, h - cb - cy);
                        cx = cx + cd.width;
                    } else if (EAST.equals(key)) {
                        c.setSize(1, c.getHeight());
                        Dimension cd = c.getPreferredSize();
                        c.setBounds(w - cr - cd.width, cy, cd.width, h - cb
                                - cy);
                        cr = cr + cd.width;
                    }

                }

            }

            //中央配置コントロール

            if (centerDocks.size() > 0) {
                int cWidth = w - cr - cx;
                int cHeight = h - cb - cy;

                int visibleCount = 0;
                for (int i = 0; i <= centerDocks.size() - 1; i++) {
                    Component c = (Component) centerDocks.get(i);
                    if(c.isVisible()){
                        visibleCount++;
                    }
                }
                
                if(visibleCount>0){
                if (isHorizontalDivide()) {
                    cHeight = cHeight / visibleCount;
                    int visibleIndex = 0;
                    for (int i = 0; i <= centerDocks.size() - 1; i++) {
                        Component c = (Component) centerDocks.get(i);
                        if(c.isVisible()){
                            c.setBounds(cx, cy + cHeight * visibleIndex++, cWidth, cHeight);
                        }
                    }
                } else {
                    cWidth = cWidth / visibleCount;
                    int visibleIndex = 0;
                    for (int i = 0; i <= centerDocks.size() - 1; i++) {
                        Component c = (Component) centerDocks.get(i);
                        if(c.isVisible()){
                            c.setBounds(cx + cWidth * visibleIndex++, cy, cWidth, cHeight);
                        }
                    }

                }
                }
//                if (isHorizontalDivide()) {
//                    cHeight = cHeight / centerDocks.size();
//                    for (int i = 0; i <= centerDocks.size() - 1; i++) {
//                        Component c = (Component) centerDocks.get(i);
//                        c.setBounds(cx, cy + cHeight * i, cWidth, cHeight);
//                    }
//                } else {
//                    cWidth = cWidth / centerDocks.size();
//                    for (int i = 0; i <= centerDocks.size() - 1; i++) {
//                        Component c = (Component) centerDocks.get(i);
//                        c.setBounds(cx + cWidth * i, cy, cWidth, cHeight);
//                    }
//
//                }
            }

            cy += vgap;
            cb += vgap;

            int maxwidth = w - (cx + cr);
            int maxheight = h - (cy + cb);
            boolean ltr = target.getComponentOrientation().isLeftToRight();

            //改行分割

            ArrayList lst = new ArrayList();
            ArrayList lhp = new ArrayList();
            lst.add(lhp);

            class CompoInfo {
                public Component compo;

                public Rectangle rect;

                public Dimension minSize;

                public int leader = 0;

                public boolean side = false;

                public CompoInfo(Component compo, Rectangle rect,
                        Dimension minSize, int leader, boolean side) {
                    this.compo = compo;
                    this.rect = rect;
                    this.minSize = minSize;
                    this.leader = leader;
                    this.side = side;

                }

            }

            int lmw = 0;
            int ny = 0;
            int rh = 0;

            int shiftX = 0;
            int shiftEndY = 0;

            int firstMargin = labelMargin - (cx - insets.left);
            if (firstMargin < 0) {
                firstMargin = 0;
            }

            it = flows.keySet().iterator();

            while (it.hasNext()) {
                Component m = (Component) it.next();

                if (m.isVisible()) {

                    String key = (String) flows.get(m);

                    Dimension d = m.getPreferredSize();
                    Dimension md = m.getMinimumSize();

                    if (autoWrap
                            && (lmw + ((stretch) ? md.width : d.width) + hgap > maxwidth && lmw > 0)) {
                        //最小値の限界を超えた場合
                        lhp = new ArrayList();
                        lst.add(lhp);
                        ny += (int) Math.max(rh + vgap, vgrid);

                        //行頭調整
                        if (ny + d.height + vgap > shiftEndY
                                || (shiftX + ((stretch) ? md.width : d.width)
                                        + hgap > maxwidth)) {
                            ny = (int) Math.max(shiftEndY, ny);
                            shiftX = 0;
                            shiftEndY = 0;
                        }

                        lmw = shiftX;
                        rh = 0;

                    }

                    int ll = 0;
                    if (FLOW_INSETLINE.equals(key)
                            || FLOW_INSETLINE_RETURN.equals(key)) {
                        JComponent jc = (JComponent) m;
                        ll = jc.getInsets().left;
                    } else if (FLOW_DOUBLEINSETLINE.equals(key)
                            || FLOW_DOUBLEINSETLINE_RETURN.equals(key)) {
                        JComponent jc = (JComponent) m;
                        ll = getLeftInset(jc);
                    }

                    CompoInfo ci = new CompoInfo(m, new Rectangle(shiftX, ny,
                            d.width, d.height), md, ll, FLOW_LEFT.equals(key));
                    lhp.add(ci);

                    if (ci.leader > 0) {
                        if (lmw + ci.leader < firstMargin) {
                            lmw = firstMargin - ci.leader;
                        }
                    } else {
                        if (lmw == 0) {
                            lmw = hgap;
                        }
                    }

                    if (FLOW_LEFT.equals(key)) {
                        shiftX += d.width + hgap;
                        shiftEndY = (int) Math.max(shiftEndY, y + d.height
                                + vgap);
                    } else {
                        rh = (int) Math.max(rh, d.height);
                    }

                    lmw += ((stretch) ? md.width : d.width) + hgap;

                    if (FLOW_RETURN.equals(key)
                            || FLOW_INSETLINE_RETURN.equals(key)
                            || FLOW_DOUBLEINSETLINE_RETURN.equals(key)) {
                        ny += (int) Math.max(rh + vgap, vgrid);
                        lhp = new ArrayList();
                        lst.add(lhp);

                        //行頭調整
                        ny = (int) Math.max(shiftEndY, ny);
                        shiftX = 0;
                        shiftEndY = 0;

                        rh = 0;
                        lmw = 0;
                    }

                }
            }

            //最終行がいらなければ消去
            if (lst.size() > 0) {
                if (((ArrayList) lst.get(lst.size() - 1)).size() == 0) {
                    lst.remove(lst.size() - 1);

                    if (ny > 0) {
                        ny -= vgap;
                    }

                } else {
                    ny += rh;
                }

            }

            //縦配置
            switch (vAlignment) {
            case CENTER:
                cy += (h - (cy + cb) - ny) / 2;
                break;
            case BOTTOM:
            case TRAILING:
                cy += h - (cy + cb) - ny;
                break;
            }

            //再配置
            int[] xs = new int[lst.size()];

            int min = 0;
            int max = 0;

            for (int i = 0; i <= lst.size() - 1; i++) {
                xs[i] = -1;
            }

            //頭をあわせる
            for (int i = 0; i <= lst.size() - 1; i++) {
                ArrayList lh = (ArrayList) lst.get(i);
                if (lh.size() > 0) {
                    if (xs[i] < lh.size() - 1) {
                        CompoInfo ci1 = (CompoInfo) lh.get(0);
                        int mxcal = ci1.rect.x;
                        boolean isleadercheck = true;
                        for (int j = xs[i] + 1; j <= lh.size() - 1; j++) {
                            CompoInfo ci2 = (CompoInfo) lh.get(j);
                            //頭がラベルコンテナになるまで検索開始位置を移動する
                            if (ci2.leader == 0) {
                                if (isleadercheck) {
                                    xs[i] = j;
                                }
                                //頭がラベルコンテナ以外の場合カツLeft指定以外の場合
                                if (!ci2.side) {
                                    mxcal += hgap;
                                }

                            } else {
                                isleadercheck = false;
                            }
                            ci2.rect.x = mxcal;
                            mxcal += ci2.rect.width;
                        }
                        if (mxcal > maxwidth) {
                            xs[i] = lh.size();
                        }

                    }
                }
            }

            //配置計算
            int minpos = 0;

            while (minpos >= 0) {

                min = 0;
                max = 0;

                //最小位置を検索
                minpos = -1;

                for (int i = 0; i <= lst.size() - 1; i++) {
                    ArrayList lh = (ArrayList) lst.get(i);
                    if (xs[i] < lh.size() - 1) {
                        //          isCheck = true;
                        CompoInfo ci2 = (CompoInfo) lh.get(xs[i] + 1);
                        int mins = ci2.leader;
                        if (xs[i] >= 0) {
                            CompoInfo ci = (CompoInfo) lh.get(xs[i]);
                            mins += ci.rect.x + ci.rect.width + hgap;
                        } else {
                            mins += ci2.rect.x;
                        }

                        if (min == 0) {
                            min = mins;
                            minpos = i;
                        } else {
                            if (mins < min) {
                                min = mins;
                                minpos = i;
                            }
                        }
                    }
                }

                //整列位置調査

                if (minpos == -1) {
                    break;
                }

                int linemax = 0;
                int minS = minpos;
                int minE = minpos;
                max = min;

                //最小位置より後の対象コントロールを検索
                for (int i = minpos + 1; i <= lst.size() - 1; i++) {
                    ArrayList lh = (ArrayList) lst.get(i);
                    if (xs[i] < lh.size() - 1) {

                        CompoInfo ci2 = (CompoInfo) lh.get(xs[i] + 1);
                        int minline = 0;
                        if (xs[i] >= 0) {
                            CompoInfo ci = (CompoInfo) lh.get(xs[i]);
                            minline = ci.rect.x + ci.rect.width + hgap;
                        } else {
                            minline = ci2.rect.x;
                        }
                        int mins = minline + ci2.leader;

                        if (min + hgrid > mins) {
                            max = (int) Math.max(mins, max);
                            linemax = (int) Math.max(linemax, minline);
                            minE = i;
                            continue;
                        }
                    } else {
                        //空白をはさんだ場合、整列対象となる
                        if (lh.size() > 0) {
                            CompoInfo ci = (CompoInfo) lh.get(lh.size() - 1);
                            if (ci.rect.x + ci.rect.width <= min) {
                                continue;
                            }
                        }
                    }
                    break;
                }

                //最小位置より前の対象コントロールを検索
                for (int i = minpos - 1; i >= 0; i--) {
                    ArrayList lh = (ArrayList) lst.get(i);
                    if (xs[i] < lh.size() - 1) {
                        CompoInfo ci2 = (CompoInfo) lh.get(xs[i] + 1);
                        int minline = 0;
                        if (xs[i] >= 0) {
                            CompoInfo ci = (CompoInfo) lh.get(xs[i]);
                            minline = ci.rect.x + ci.rect.width + hgap;
                        } else {
                            minline = ci2.rect.x;
                        }
                        int mins = minline + ci2.leader;

                        if (min + hgrid > mins) {
                            max = (int) Math.max(mins, max);
                            linemax = (int) Math.max(linemax, minline);
                            minS = i;
                            continue;
                        }
                    } else {
                        //空白をはさんだ場合、整列対象となる
                        if (lh.size() > 0) {
                            CompoInfo ci = (CompoInfo) lh.get(lh.size() - 1);
                            if (ci.rect.x + ci.rect.width <= min) {
                                continue;
                            }
                        }
                    }
                    break;
                }
                //オーバーラップ設定を緩和する
                if (!isLabelOverwrap()) {
                    int overlap = linemax;
                    for (int i = minS; i <= minE; i++) {
                        ArrayList lh = (ArrayList) lst.get(i);
                        if (xs[i] < lh.size() - 1) {
                            CompoInfo ci2 = (CompoInfo) lh.get(xs[i] + 1);
                            if (max + hgrid > linemax + ci2.leader) {
                                overlap = (int) Math.max(overlap, linemax
                                        + ci2.leader);
                            }
                        }
                    }

                    if (max < overlap) {
                        max = overlap;
                    }
                }

                //整列処理を行う

                if (max < firstMargin) {
                    max = firstMargin;
                }
                for (int i = minS; i <= minE; i++) {
                    ArrayList lh = (ArrayList) lst.get(i);
                    if (xs[i] < lh.size() - 1) {
                        xs[i]++;
                        CompoInfo ci = (CompoInfo) lh.get(xs[i]);
                        ci.rect.x = max - ci.leader;

                        int mxcal = ci.rect.x + ci.rect.width + hgap;
                        boolean isleadercheck = true;

                        for (int j = xs[i] + 1; j <= lh.size() - 1; j++) {
                            CompoInfo ci2 = (CompoInfo) lh.get(j);
                            ci2.rect.x = mxcal;
                            mxcal += ci2.rect.width + hgap;
                            if (ci2.leader == 0 && isleadercheck) {
                                xs[i] = j;
                            } else {
                                isleadercheck = false;
                            }
                        }
                        if (mxcal > maxwidth) {
                            xs[i] = lh.size();
                        }

                    }
                }
            }

            //配置
            for (int i = 0; i < lst.size(); i++) {
                ArrayList lh = (ArrayList) lst.get(i);
                if (lh.size() > 0) {
                    CompoInfo ci = (CompoInfo) lh.get(lh.size() - 1);
                    CompoInfo ci2;
                    int mgn = ci.rect.x + ci.rect.width - maxwidth;
                    if (mgn > 0) {
                        //隙間埋め処理
                        for (int j = lh.size() - 1; j >= 0; j--) {
                            ci = (CompoInfo) lh.get(j);
                            int mm = ci.rect.x;
                            if (j > 0) {
                                ci2 = (CompoInfo) lh.get(j - 1);
                                mm -= (ci2.rect.x + ci2.rect.width);
                            }
                            if (mm > mgn) {
                                //スペースに余裕がある場合
                                mm = mgn;
                            }
                            for (int k = j; k <= lh.size() - 1; k++) {
                                ci = (CompoInfo) lh.get(k);
                                ci.rect.x -= mm;
                            }

                            mgn -= mm;
                            if (mgn <= 0) {
                                break;
                            }

                        }
                        //隙間埋めの限界を超えた場合、縮小処理
                        if (mgn > 0) {
                            int resiz = 0;
                            for (int k = 0; k <= lh.size() - 1; k++) {
                                ci = (CompoInfo) lh.get(k);
                                resiz += ci.rect.width - ci.minSize.width;
                            }
                            double dd = (double) (resiz - mgn) / resiz;
                            for (int k = 0; k <= lh.size() - 1; k++) {
                                ci = (CompoInfo) lh.get(k);
                                ci.rect.width = ci.minSize.width
                                        + (int) (dd * (ci.rect.width - ci.minSize.width));
                            }
                            for (int k = 1; k <= lh.size() - 1; k++) {
                                ci = (CompoInfo) lh.get(k);
                                ci2 = (CompoInfo) lh.get(k - 1);
                                ci.rect.x = ci2.rect.x + ci2.rect.width;
                            }

                            mgn = 0;

                        }
                    }

                    //頭の位置計算
                    int lshift = 0;

                    switch (alignment) {
                    case LEFT:
                        lshift = ltr ? 0 : -mgn;
                        break;
                    case CENTER:
                        lshift = -mgn / 2;
                        break;
                    case RIGHT:
                        lshift = ltr ? -mgn : 0;
                        break;
                    case LEADING:
                        break;
                    case TRAILING:
                        lshift = -mgn;
                        break;
                    }

                    //最大値取得
                    int linemax = 0;
                    for (int j = 0; j <= lh.size() - 1; j++) {
                        ci = (CompoInfo) lh.get(j);
                        linemax = (int) Math.max(ci.rect.height, linemax);
                    }

                    for (int j = 0; j <= lh.size() - 1; j++) {
                        ci = (CompoInfo) lh.get(j);
                        if (fitVGrid) {
                            ci.rect.height = linemax;
                        } else {
                            switch (vLineAlign) {
                            case CENTER:
                                ci.rect.y += (linemax - ci.rect.height) / 2;
                                break;
                            case BOTTOM:
                                ci.rect.y += linemax - ci.rect.height;
                                break;
                            }
                        }

                        if (j < lh.size() - 1) {
                            if (mgn < 0) {
                                if (fitHGrid) {
                                    ci2 = (CompoInfo) lh.get(j + 1);
                                    ci.rect.width = ci2.rect.x - ci.rect.x
                                            - hgap;
                                }
                            }
                        } else if (fitHLast) {
                            if (alignment == LEFT) {
                                ci.rect.width = maxwidth - ci.rect.x - hgap;

                            }
                        }

                        if (fitVLast && (i == (lst.size() - 1))) {
                            int fith = maxheight - ci.rect.y - vgap;
                            if (0 < fith) {
                                //                        	if(ci.rect.height <fith){
                                ci.rect.height = fith;
                            }
                        }

                        ci.compo.setSize(ci.rect.width, ci.rect.height);
                        ci.compo.setLocation(lshift + ci.rect.x + cx, ci.rect.y
                                + cy);
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
        String str = "";
        switch (alignment) {
        case LEFT:
            str = ",align=left";
            break;
        case CENTER:
            str = ",align=center";
            break;
        case RIGHT:
            str = ",align=right";
            break;
        case LEADING:
            str = ",align=leading";
            break;
        case TRAILING:
            str = ",align=trailing";
            break;
        }
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + str
                + "]";
    }

    /**
     * 保持コンポーネントの配置方向を返します。
     * 
     * @return 配置方向
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * 保持コンポーネントの配置方向を設定します。
     * 
     * @param align 配置方向
     */
    public void setAlignment(int align) {
        this.alignment = align;
    }

    /**
     * 垂直方向に最大まで高さを広げるかを返します。
     * 
     * @return 垂直方向に最大まで高さを広げる場合はtrue
     */
    public boolean getFitVGrid() {
        return fitVGrid;
    }

    /**
     * 垂直方向に最大まで高さを広げるかを設定します。
     * 
     * @param fitVgrid 垂直方向に最大まで高さを広げる場合はtrue
     */
    public void setFitVGrid(boolean fitVgrid) {
        this.fitVGrid = fitVgrid;
    }

    /**
     * 水平方向に最大まで幅を広げるかを返します。
     * 
     * @return 水平方向に最大まで幅を広げる場合はtrue
     */
    public boolean getFitHGrid() {
        return fitHGrid;
    }

    /**
     * 水平方向に最大まで幅を広げるかを設定します。
     * 
     * @param fitHgrid 水平方向に最大まで幅を広げる場合はtrue
     */
    public void setFitHGrid(boolean fitHgrid) {
        this.fitHGrid = fitHgrid;
    }

    /**
     * 垂直方向に最大まで高さを広げるかを返します。
     * 
     * @return 垂直方向に最大まで高さを広げる場合はtrue
     */
    public boolean getFitVLast() {
        return fitVLast;
    }

    /**
     * 垂直方向に最大まで高さを広げるかを設定します。
     * 
     * @param fitVLast 垂直方向に最大まで高さを広げる場合はtrue
     */
    public void setFitVLast(boolean fitVLast) {
        this.fitVLast = fitVLast;
    }

    /**
     * 水平方向に最大まで幅を広げるかを返します。
     * 
     * @return 水平方向に最大まで幅を広げる場合はtrue
     */
    public boolean getFitHLast() {
        return fitHLast;
    }

    /**
     * 水平方向に最大まで幅を広げるかを設定します。
     * 
     * @param fitHLast 水平方向に最大まで幅を広げる場合はtrue
     */
    public void setFitHLast(boolean fitHLast) {
        this.fitHLast = fitHLast;
    }

    /**
     * 水平方向のグリッドの幅を返します。
     * 
     * @return 水平方向のグリッドの幅
     */
    public int getHgrid() {
        return hgrid;
    }

    /**
     * 水平方向のグリッドの幅を設定します。
     * 
     * @param hgrid 水平方向のグリッドの幅
     */
    public void setHgrid(int hgrid) {
        this.hgrid = hgrid;
    }

    /**
     * 垂直方向のグリッドの高さを返します。
     * 
     * @return 垂直方向のグリッドの高さ
     */
    public int getVgrid() {
        return vgrid;
    }

    /**
     * 垂直方向のグリッドの高さを設定します。
     * 
     * @param vgrid 垂直方向のグリッドの高さ
     */
    public void setVgrid(int vgrid) {
        this.vgrid = vgrid;
    }

    /**
     * 水平方向に空ける余白サイズを返します。
     * 
     * @return 余白サイズ
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
     * 行内の縦アライメントを返します。
     * 
     * @return 配置
     */
    public int getVLineAlign() {
        return vLineAlign;
    }

    /**
     * 行内の縦アライメントを設定します。
     * 
     * @param vLineAlign 配置
     */
    public void setVLineAlign(int vLineAlign) {
        this.vLineAlign = vLineAlign;
        this.vLineAlign = vLineAlign;
    }

    /**
     * 自動改行するかを返します。
     * 
     * @return 自動改行する場合はtrue
     */
    public boolean isAutoWrap() {
        return autoWrap;
    }

    /**
     * 自動改行するかを設定します。
     * 
     * @param autoWrap 自動改行する場合はtrue
     */
    public void setAutoWrap(boolean autoWrap) {
        this.autoWrap = autoWrap;
    }

    /**
     * 整列ラベルのオーバーラップを許可するかを返します。
     * 
     * @return オーバーラップを許可する場合はTrue
     */
    public boolean isLabelOverwrap() {
        return labelOverwrap;
    }

    /**
     * 整列ラベルのオーバーラップを許可するかを設定します。
     * 
     * @param labelOverwrap オーバーラップを許可する場合はTrue
     */
    public void setLabelOverwrap(boolean labelOverwrap) {
        this.labelOverwrap = labelOverwrap;
    }

    /**
     * ラベルのマージンを返します。
     * 
     * @return ラベルのマージン
     */
    public int getLabelMargin() {
        return labelMargin;
    }

    /**
     * ラベルのマージンを設定します。
     * 
     * @param labelMargin ラベルのマージン
     */
    public void setLabelMargin(int labelMargin) {
        this.labelMargin = labelMargin;
    }

    /**
     * ストレッチするかを返します。
     * 
     * @return ストレッチする場合はTrue
     */
    public boolean isStretch() {
        return stretch;
    }

    /**
     * ストレッチするかを設定します。
     * 
     * @param stretch ストレッチする場合はTrue
     */
    public void setStretch(boolean stretch) {
        this.stretch = stretch;
    }

    /**
     * 縦アライメントを返します。
     * 
     * @return int 縦アライメント
     */
    public int getVAlignment() {
        return vAlignment;
    }

    /**
     * 縦アライメントを設定します。
     * 
     * @param vAlignment int 縦アライメント
     */
    public void setVAlignment(int vAlignment) {
        this.vAlignment = vAlignment;
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

    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {

    }
}