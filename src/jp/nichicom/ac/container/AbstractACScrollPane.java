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
 * ���e���ڂ����b�v����X�N���[���y�C���ł��B
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
     * ���e���ڂ̔w�i�F��Ԃ��܂��B
     * 
     * @return ���e���ڂ̔w�i�F
     */
    public Color getMainContentBackground() {
        return getJView().getBackground();
    }

    /**
     * ���e���ڂ̑O�i�F��Ԃ��܂��B
     * 
     * @return ���e���ڂ̑O�i�F
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
     * ���e���ڂ̕s��������Ԃ��܂��B
     * 
     * @return ���e���ڂ̕s������
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
     * ���e���ڂ̔w�i�F��ݒ肵�܂��B
     * 
     * @param bg ���e���ڂ̔w�i�F
     */
    public void setMainContentBackground(Color bg) {
        getJView().setBackground(bg);
    }

    /**
     * ���e���ڂ̑O�i�F��ݒ肵�܂��B
     * 
     * @param fg ���e���ڂ̑O�i�F
     */
    public void setMainContentForeground(Color fg) {
        getJView().setForeground(fg);
    }

    /**
     * ���e���ڂ̕s��������ݒ肵�܂��B
     * 
     * @param isOpaque ���e���ڂ̕s������
     */
    public void setMainContentOpaque(boolean isOpaque) {
        getJView().setOpaque(isOpaque);
    }

    /**
     * ���e���ڂɃ��b�v���X�i��o�^���܂��B
     * 
     * @param view ���e����
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
     * ���e���ڂ𐶐����ĕԂ��܂��B
     * 
     * @return ���e����
     */
    protected abstract JComponent createJView();

    /**
     * ���e���ڂ�Ԃ��܂��B
     * 
     * @return ���e����
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
     * �R���|�[�l���g�����������܂��B
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
            // �q�̃C�x���g��u��
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new FocusEvent(this,e.getID());
            // 2007/10/09 [Masahiko Higuchi] Replace - end
        }
        super.processFocusEvent(e);
    }

    protected void processInputMethodEvent(InputMethodEvent e) {
        if (e.getSource() == getJView()) {
            // �q�̃C�x���g��u��
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
            // �q�̃C�x���g���ԐړI�ɔ��s
            e = new KeyEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e
                    .getKeyCode(), e.getKeyChar(), e.getKeyLocation());
        }
        super.processKeyEvent(e);
    }

    protected void processMouseEvent(MouseEvent e) {
        if (e.getSource() == getJView()) {
            // �q�̃C�x���g���ԐړI�ɔ��s
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
                    e.getX(), e.getY(), e.getClickCount(), false);
            // 2007/10/09 [Masahiko Higuchi] Replace - end
            
        }
        super.processMouseEvent(e);
    }

    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.getSource() == getJView()) {
            // �q�̃C�x���g���ԐړI�ɔ��s
            // 2007/10/09 [Masahiko Higuchi] Replace - begin
            e = new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
                    e.getX(), e.getY(), e.getClickCount(), false);
            // 2007/10/09 [Masahiko Higuchi] Replace - end
        }
        super.processMouseMotionEvent(e);
    }

    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (e.getSource() == getJView()) {
            // �q�̃C�x���g��u��
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
     * ���e���ڂ��烉�b�v���X�i�����O���܂��B
     * 
     * @param view ���e����
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
