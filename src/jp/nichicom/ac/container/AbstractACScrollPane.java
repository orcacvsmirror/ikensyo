package jp.nichicom.ac.container;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;

/**
 * 投影項目をラップするスクロールペインです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/05/08
 * @see javax.swing.JScrollPane
 */
public abstract class AbstractACScrollPane extends JScrollPane implements
        FocusListener, MouseWheelListener, MouseListener, MouseMotionListener,
        InputMethodListener, KeyListener {

    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code> where both
     * horizontal and vertical scrollbars appear when needed.
     */
    public AbstractACScrollPane() {
        super();
        initComponent();
    }

    /**
     * Creates a <code>JScrollPane</code> that displays the contents of the
     * specified component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     * 
     * @see #setViewportView
     * @param view the component to display in the scrollpane's viewport
     */
    public AbstractACScrollPane(Component view) {
        super(view);
        initComponent();
    }

    /**
     * Creates a <code>JScrollPane</code> that displays the view component in
     * a viewport whose view position can be controlled with a pair of
     * scrollbars. The scrollbar policies specify when the scrollbars are
     * displayed, For example, if <code>vsbPolicy</code> is
     * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code> then the vertical scrollbar
     * only appears if the view doesn't fit vertically. The available policy
     * settings are listed at {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     * 
     * @see #setViewportView
     * @param view the component to display in the scrollpanes viewport
     * @param vsbPolicy an integer that specifies the vertical scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal scrollbar
     *            policy
     */
    public AbstractACScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        initComponent();
    }

    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code> with
     * specified scrollbar policies. The available policy settings are listed at
     * {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     * 
     * @see #setViewportView
     * @param vsbPolicy an integer that specifies the vertical scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal scrollbar
     *            policy
     */
    public AbstractACScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
        initComponent();
    }

    public void caretPositionChanged(InputMethodEvent event) {
        processInputMethodEvent(event);
    }

    public void focusGained(FocusEvent e) {
        processFocusEvent(e);
    }

    public void focusLost(FocusEvent e) {
        processFocusEvent(e);
    }

    /**
     * 投影項目の背景色を返します。
     * 
     * @return 投影項目の背景色
     */
    public Color getMainContentBackground() {
        return getJView().getBackground();
    }

    /**
     * 投影項目の前景色を返します。
     * 
     * @return 投影項目の前景色
     */
    public Color getMainContentForeground() {
        return getJView().getForeground();
    }

    public Dimension getPreferredScrollableViewportSize() {
        if (getJView() instanceof Scrollable) {
            return ((Scrollable) getJView())
                    .getPreferredScrollableViewportSize();
        }
        return null;
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        if (getJView() instanceof Scrollable) {
            return ((Scrollable) getJView()).getScrollableBlockIncrement(
                    visibleRect, orientation, direction);
        }
        return 0;
    }

    public boolean getScrollableTracksViewportHeight() {
        if (getJView() instanceof Scrollable) {
            return ((Scrollable) getJView())
                    .getScrollableTracksViewportHeight();
        }
        return false;
    }

    public boolean getScrollableTracksViewportWidth() {
        if (getJView() instanceof Scrollable) {
            return ((Scrollable) getJView()).getScrollableTracksViewportWidth();
        }
        return false;
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return ((Scrollable) getJView()).getScrollableUnitIncrement(
                visibleRect, orientation, direction);
    }

    public Point getToolTipLocation(MouseEvent event) {
        JViewport vp = getViewport();
        if (vp != null) {
            Point pt = event.getPoint();
            if (pt != null) {
                Point vpt = vp.getViewPosition();
                pt.x = pt.x - vpt.x + vp.getX();
                pt.y = pt.y - vpt.y + vp.getY() + 20;
                return pt;
            }
        }
        return super.getToolTipLocation(event);
    }

    public String getToolTipText(MouseEvent event) {
        JComponent v = getJView();
        if (v != null) {
            return v.getToolTipText(event);
        }
        return super.getToolTipText(event);
    }

    public void inputMethodTextChanged(InputMethodEvent event) {
        processInputMethodEvent(event);
    }

    /**
     * 投影項目の不透明性を返します。
     * 
     * @return 投影項目の不透明性
     */
    public boolean isMainContentOpaque() {
        return getJView().isOpaque();
    }

    public void keyPressed(KeyEvent e) {
        processKeyEvent(e);
    }

    public void keyReleased(KeyEvent e) {
        processKeyEvent(e);
    }

    public void keyTyped(KeyEvent e) {
        processKeyEvent(e);
    }

    public void mouseClicked(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseDragged(MouseEvent e) {
        processMouseMotionEvent(e);
    }

    public void mouseEntered(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseExited(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseMoved(MouseEvent e) {
        processMouseMotionEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        processMouseWheelEvent(e);
    }

    /**
     * 投影項目の背景色を設定します。
     * 
     * @param bg 投影項目の背景色
     */
    public void setMainContentBackground(Color bg) {
        getJView().setBackground(bg);
    }

    /**
     * 投影項目の前景色を設定します。
     * 
     * @param fg 投影項目の前景色
     */
    public void setMainContentForeground(Color fg) {
        getJView().setForeground(fg);
    }

    /**
     * 投影項目の不透明性を設定します。
     * 
     * @param isOpaque 投影項目の不透明性
     */
    public void setMainContentOpaque(boolean isOpaque) {
        getJView().setOpaque(isOpaque);
    }

    /**
     * 投影項目にラップリスナを登録します。
     * 
     * @param view 投影項目
     */
    protected void addJViewListeners(JComponent view) {
        if (view != null) {
            view.addFocusListener(this);
            view.addInputMethodListener(this);
            view.addKeyListener(this);
            view.addMouseListener(this);
            view.addMouseMotionListener(this);
            view.addMouseWheelListener(this);
        }
    }

    /**
     * 投影項目を生成して返します。
     * 
     * @return 投影項目
     */
    protected abstract JComponent createJView();

    /**
     * 投影項目を返します。
     * 
     * @return 投影項目
     */
    protected JComponent getJView() {
        JViewport vp = getViewport();
        if (vp != null) {
            Component cmp = vp.getView();
            if (cmp instanceof JComponent) {
                return (JComponent) cmp;
            }
        }
        return null;
    }

    /**
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        JComponent v = createJView();
        if (v != null) {
            addJViewListeners(v);
            this.getViewport().add(v, null);
        }
        if (getHorizontalScrollBar() != null) {
            getHorizontalScrollBar().setFocusable(false);
        }
        if (getVerticalScrollBar() != null) {
            getVerticalScrollBar().setFocusable(false);
        }
    }

    protected void processFocusEvent(FocusEvent e) {
        if (e.getSource() == getJView()) {
            // 子のイベントを置換
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new FocusEvent(this,e.getID());
            // 2007/10/09 [Masahiko Higuchi] Replace - end
        }
        super.processFocusEvent(e);
    }

    protected void processInputMethodEvent(InputMethodEvent e) {
        if (e.getSource() == getJView()) {
            // 子のイベントを置換
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new InputMethodEvent(this, e.getID(), e.getText(), e
                    .getCommittedCharacterCount(), e.getCaret(), e
                    .getVisiblePosition());
            // 2007/10/09 [Masahiko Higuchi] Replace - end
        }
        super.processInputMethodEvent(e);
    }

    protected void processKeyEvent(KeyEvent e) {
        if (e.getSource() == getJView()) {
            // 子のイベントを間接的に発行
            e = new KeyEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e
                    .getKeyCode(), e.getKeyChar(), e.getKeyLocation());
        }
        super.processKeyEvent(e);
    }

    protected void processMouseEvent(MouseEvent e) {
        if (e.getSource() == getJView()) {
            // 子のイベントを間接的に発行
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
                    e.getX(), e.getY(), e.getClickCount(), false);
            // 2007/10/09 [Masahiko Higuchi] Replace - end
            
        }
        super.processMouseEvent(e);
    }

    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.getSource() == getJView()) {
            // 子のイベントを間接的に発行
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
                    e.getX(), e.getY(), e.getClickCount(), false);
            // 2007/10/09 [Masahiko Higuchi] Replace - end
        }
        super.processMouseMotionEvent(e);
    }

    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (e.getSource() == getJView()) {
            // 子のイベントを置換
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new MouseWheelEvent(this, e.getID(), e.getWhen(), e
                    .getModifiers(), e.getX(), e.getY(), e.getClickCount(),
                    false, e.getScrollType(), e.getScrollAmount(), e
                            .getWheelRotation());
            // 2007/10/09 [Masahiko Higuchi] Replace - end
        }
        super.processMouseWheelEvent(e);
    }

    /**
     * 投影項目からラップリスナを除外します。
     * 
     * @param view 投影項目
     */
    protected void removeJViewListeners(JComponent view) {
        if (view != null) {
            view.removeFocusListener(this);
            view.removeInputMethodListener(this);
            view.removeKeyListener(this);
            view.removeMouseListener(this);
            view.removeMouseMotionListener(this);
            view.removeMouseWheelListener(this);
        }
    }

    public void requestFocus() {
        if (getJView() != null) {
            getJView().requestFocus();
        }else{
            super.requestFocus();
        }
    }

    public void revalidate() {
        super.revalidate();
        if (getJView() != null) {
            getJView().revalidate();
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (getJView() != null) {
            getJView().setEnabled(enabled);
        }
    }

    public boolean isEnabled() {
        if (super.isEnabled()) {
            if (getJView() != null) {
                return getJView().isEnabled();
            }
            return true;
        }
        return false;
    }

}
