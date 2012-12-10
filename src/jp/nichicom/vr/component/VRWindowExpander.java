/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

/**
 * 所属するウィンドウのサイズをドラッグで拡大縮小させるクラスです。
 * <p>
 * AlignmentXおよびAlignmentYプロパティで拡大縮小方向を設定します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 */
public class VRWindowExpander extends JComponent implements
        MouseMotionListener, MouseListener {

    //オーナーウィンドウ
    private Window ownerWindow = null;

    //ドラッグ開始座標
    private int dragStartX;
    private int dragStartY;

    private Insets insets;

    /**
     * メインコンストラクタです。
     */
    public VRWindowExpander() {
        super();
        initComponent();
    }

    /**
     * コンポーネントの初期化を行います。
     */
    protected void initComponent() {
        //オーナーウィンドウ
        ownerWindow = null;
        //デフォルトは右下
        setAlignmentX(RIGHT_ALIGNMENT);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setInsets(new Insets(1, 1, 1, 1));
        //ドラッグ開始座標
        dragStartX = 0;
        dragStartY = 0;
        //前景色
        setForeground(new Color(0xB0, 0xB0, 0xB0));
        //背景色
        setBackground(new Color(0xFF, 0xFF, 0xFF));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setInsets(Insets insets) {
        this.insets = insets;
    }

    public Insets getInsets() {
        return insets;
    }

    public Dimension getPreferredSize() {
        Dimension dm = super.getPreferredSize();
        if (dm == null) {
            dm = new Dimension(12, 12);
        } else {
            dm.width = Math.max(dm.width, 12);
            dm.height = Math.max(dm.height, 12);
        }
        Insets is = getInsets();
        if (is != null) {
            dm.height += is.top + is.bottom;
            dm.width += is.left + is.right;
        }

        return dm;
    }

    /**
     * このコンポーネントを描画します。
     * 
     * @param g ペイントに使用するグラフィックスコンテキスト
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Dimension dm = getSize();
        int leftDiff = 0;
        int topDiff = 0;
        if (isRightDraggable()) {
            leftDiff += (int) dm.getWidth() - 12;
        }
        if (isBottomDraggable()) {
            topDiff += (int) dm.getHeight() - 12;
        }

        Insets is = getInsets();
        if (is != null) {
            topDiff -= is.bottom;
            leftDiff -= is.right;
        }

        if ((leftDiff != 0) || (topDiff != 0)) {
            g2.translate(leftDiff, topDiff);
        }

        //ドラッグ可能な方向に点を描画する
        if (this.isLeftDraggable()) {
            for (int i = 0; i < 3; i++) {
                paint(g2, 0, i * 4);
            }
        }
        if (this.isRightDraggable()) {
            for (int i = 0; i < 3; i++) {
                paint(g2, 8, i * 4);
            }
        }
        if (this.isTopDraggable()) {
            for (int i = 0; i < 3; i++) {
                paint(g2, i * 4, 0);
            }
        }
        if (this.isBottomDraggable()) {
            for (int i = 0; i < 3; i++) {
                paint(g2, i * 4, 8);
            }
        }
        //中央に点を描画する
        paint(g2, 4, 4);

        if ((leftDiff != 0) || (topDiff != 0)) {
            g2.translate(-leftDiff, -topDiff);
        }
    }

    /**
     * このコンポーネントを描画します。
     * 
     * @param g2 ペイントに使用するグラフィックスコンテキスト
     * @param dX 点の描画X座標
     * @param dY 点の描画Y座標
     */
    protected void paint(Graphics2D g2, int dX, int dY) {
        g2.setColor(getBackground());
        g2.fillRect(dX, dY, 3, 3);
        g2.setColor(getForeground());
        g2.fillRect(dX, dY, 2, 2);
    }

    /**
     * オーナーフォームを返します。
     * 
     * @return オーナーフォーム
     */
    protected Window getOwnerWindow() {
        return getOwnerWindowEnum(this);
    }

    /**
     * オーナーフォームを再帰的に検索して返します。
     * 
     * @param c 子コンポーネント
     * @return オーナーフォーム
     */
    protected Window getOwnerWindowEnum(Component c) {
        if (c == null) {
            return null;
        }
        if (c instanceof Window) {
            return (Window) c;
        }
        return getOwnerWindowEnum(c.getParent());
    }

    /**
     * 左方向へドラッグ可能であるかを返します。
     * 
     * @return 左方向へドラッグ可能な場合はtrue
     */
    protected boolean isLeftDraggable() {
        return getAlignmentX() == LEFT_ALIGNMENT;
    }

    /**
     * 右方向へドラッグ可能であるかを返します。
     * 
     * @return 右方向へドラッグ可能な場合はtrue
     */
    protected boolean isRightDraggable() {
        return getAlignmentX() == RIGHT_ALIGNMENT;
    }

    /**
     * 上方向へドラッグ可能であるかを返します。
     * 
     * @return 上方向へドラッグ可能な場合はtrue
     */
    protected boolean isTopDraggable() {
        return getAlignmentY() == TOP_ALIGNMENT;
    }

    /**
     * 下方向へドラッグ可能であるかを返します。
     * 
     * @return 下方向へドラッグ可能な場合はtrue
     */
    protected boolean isBottomDraggable() {
        return getAlignmentY() == BOTTOM_ALIGNMENT;
    }

    /**
     * コンポーネント上でマウスをドラッグした時に呼び出されるイベントです。 <br>
     * 親ウィンドウのサイズと位置を変更します。
     * 
     * @param e マウスイベント情報
     */
    public void mouseDragged(MouseEvent e) {
        if (ownerWindow == null) {
            return;
        }

        Dimension nowDim = ownerWindow.getSize();
        Point windowLoc = ownerWindow.getLocationOnScreen();
        Point thisLoc = this.getLocationOnScreen();
        int thisX = (int) thisLoc.getX();
        int thisY = (int) thisLoc.getY();
        int windowX = (int) windowLoc.getX();
        int windowY = (int) windowLoc.getY();
        int mouseX = e.getX();
        int mouseY = e.getY();

        int newX = (int) windowLoc.getX();
        int newY = (int) windowLoc.getY();
        int newW = (int) nowDim.getWidth();
        int newH = (int) nowDim.getHeight();

        int x = e.getX();
        int y = e.getY();
        int w = ownerWindow.getWidth();
        int h = ownerWindow.getHeight();
        int area = 4;
        boolean sizeModified = false;
        boolean locateModified = false;
        if (isLeftDraggable()) {
            int addX = x - area;
            if ((newW - addX) > area) {
                newX += addX;
                newW -= addX;
                locateModified = true;
                sizeModified = true;
            }
        } else if (isRightDraggable()) {
            newW = thisX - windowX + mouseX + dragStartX;
            sizeModified = true;
        }

        if (isTopDraggable()) {
            int addY = y - area;
            if ((newH - addY) > area) {
                newY += addY;
                newH -= addY;
                locateModified = true;
                sizeModified = true;
            }
        } else if (isBottomDraggable()) {
            newH = thisY - windowY + mouseY + dragStartY;
            sizeModified = true;
        }

        if (sizeModified) {
            Dimension dm = ownerWindow.getMinimumSize();
            if ((dm != null) && (dm.getWidth() < newW)
                    && (dm.getHeight() < newH)) {
                ownerWindow.setSize(newW, newH);
            }
        }
        if (locateModified) {
            ownerWindow.setLocation(newX, newY);
        }

    }

    /**
     * 引数の座標にマウスカーソルがあるとき、ドラッグは可能かを返します。
     * 
     * @param x コントロール内X座標
     * @param y コントロール内Y座標
     * @return ドラッグは可能か
     */
    protected boolean canDraggablePos(int x, int y) {

        Dimension dm = getSize();
        if (dm == null) {
            return false;
        }
        if ((x >= dm.getWidth()) || (y >= dm.getHeight())) {
            return false;
        }
        int leftDiff = 0;
        int topDiff = 0;
        if (isRightDraggable()) {
            leftDiff = (int) dm.getWidth() - 12;
        }
        if (isBottomDraggable()) {
            topDiff = (int) dm.getHeight() - 12;
        }
        if ((x < leftDiff) || (y < topDiff)) {
            return false;
        }
        return true;
    }

    /**
     * コンポーネント上でマウス移動時に呼び出されるイベントです。 <br>
     * ドラッグ可能方向にあわせて、マウスカーソルを変化させます。
     * 
     * @param e マウスイベント情報
     */
    public void mouseMoved(MouseEvent e) {
        Cursor newCursor;
        if (canDraggablePos(e.getX(), e.getY())) {
            if (isLeftDraggable()) {
                if (isTopDraggable()) {
                    newCursor = Cursor
                            .getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                } else if (isBottomDraggable()) {
                    newCursor = Cursor
                            .getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                } else {
                    newCursor = Cursor
                            .getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                }
            } else if (isRightDraggable()) {
                if (isTopDraggable()) {
                    newCursor = Cursor
                            .getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                } else if (isBottomDraggable()) {
                    newCursor = Cursor
                            .getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                } else {
                    newCursor = Cursor
                            .getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                }
            } else if (isTopDraggable()) {
                newCursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
            } else if (isBottomDraggable()) {
                newCursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
            } else {
                newCursor = Cursor.getDefaultCursor();
            }

        } else {
            newCursor = Cursor.getDefaultCursor();
        }

        if (getCursor() != newCursor) {
            this.setCursor(newCursor);
        }
    }

    /**
     * マウスクリック時に呼び出されるイベントです。
     * 
     * @deprecated 何もしません。
     * @param e マウスイベント情報
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * コンポーネント上でマウスボタンが押された時に呼び出されるイベントです。 <br>
     * 押し下げ位置をドラッグ開始位置として記憶します。
     * 
     * @param e マウスイベント情報
     */
    public void mousePressed(MouseEvent e) {
        if (canDraggablePos(e.getX(), e.getY())) {
            ownerWindow = getOwnerWindow();
            dragStartX = this.getWidth() - (int) e.getX() + 2;
            dragStartY = this.getHeight() - (int) e.getY() + 2;
        }
    }

    /**
     * コンポーネント上でマウスボタンが離された時に呼び出されるイベントです。 <br>
     * ドラッグ開始位置情報を初期化します。
     * 
     * @param e マウスイベント情報
     */
    public void mouseReleased(MouseEvent e) {
        if (ownerWindow != null) {
            ownerWindow.validate();
            ownerWindow = null;
            dragStartX = 0;
            dragStartY = 0;
        }
    }

    /**
     * コンポーネント上にマウスが入った時に呼び出されるイベントです。 <br>
     * 
     * @deprecated WelExtWindowExpanderでは何もしません。
     * @param e マウスイベント情報
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * コンポーネント上からマウスが出た時に呼び出されるイベントです。 <br>
     * マウスカーソルを通常のカーソルに戻します。
     * 
     * @param e マウスイベント情報
     */
    public void mouseExited(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
    }

}