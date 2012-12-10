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
 * ��������E�B���h�E�̃T�C�Y���h���b�O�Ŋg��k��������N���X�ł��B
 * <p>
 * AlignmentX�����AlignmentY�v���p�e�B�Ŋg��k��������ݒ肵�܂��B
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

    //�I�[�i�[�E�B���h�E
    private Window ownerWindow = null;

    //�h���b�O�J�n���W
    private int dragStartX;
    private int dragStartY;

    private Insets insets;

    /**
     * ���C���R���X�g���N�^�ł��B
     */
    public VRWindowExpander() {
        super();
        initComponent();
    }

    /**
     * �R���|�[�l���g�̏��������s���܂��B
     */
    protected void initComponent() {
        //�I�[�i�[�E�B���h�E
        ownerWindow = null;
        //�f�t�H���g�͉E��
        setAlignmentX(RIGHT_ALIGNMENT);
        setAlignmentY(BOTTOM_ALIGNMENT);
        setInsets(new Insets(1, 1, 1, 1));
        //�h���b�O�J�n���W
        dragStartX = 0;
        dragStartY = 0;
        //�O�i�F
        setForeground(new Color(0xB0, 0xB0, 0xB0));
        //�w�i�F
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
     * ���̃R���|�[�l���g��`�悵�܂��B
     * 
     * @param g �y�C���g�Ɏg�p����O���t�B�b�N�X�R���e�L�X�g
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

        //�h���b�O�\�ȕ����ɓ_��`�悷��
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
        //�����ɓ_��`�悷��
        paint(g2, 4, 4);

        if ((leftDiff != 0) || (topDiff != 0)) {
            g2.translate(-leftDiff, -topDiff);
        }
    }

    /**
     * ���̃R���|�[�l���g��`�悵�܂��B
     * 
     * @param g2 �y�C���g�Ɏg�p����O���t�B�b�N�X�R���e�L�X�g
     * @param dX �_�̕`��X���W
     * @param dY �_�̕`��Y���W
     */
    protected void paint(Graphics2D g2, int dX, int dY) {
        g2.setColor(getBackground());
        g2.fillRect(dX, dY, 3, 3);
        g2.setColor(getForeground());
        g2.fillRect(dX, dY, 2, 2);
    }

    /**
     * �I�[�i�[�t�H�[����Ԃ��܂��B
     * 
     * @return �I�[�i�[�t�H�[��
     */
    protected Window getOwnerWindow() {
        return getOwnerWindowEnum(this);
    }

    /**
     * �I�[�i�[�t�H�[�����ċA�I�Ɍ������ĕԂ��܂��B
     * 
     * @param c �q�R���|�[�l���g
     * @return �I�[�i�[�t�H�[��
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
     * �������փh���b�O�\�ł��邩��Ԃ��܂��B
     * 
     * @return �������փh���b�O�\�ȏꍇ��true
     */
    protected boolean isLeftDraggable() {
        return getAlignmentX() == LEFT_ALIGNMENT;
    }

    /**
     * �E�����փh���b�O�\�ł��邩��Ԃ��܂��B
     * 
     * @return �E�����փh���b�O�\�ȏꍇ��true
     */
    protected boolean isRightDraggable() {
        return getAlignmentX() == RIGHT_ALIGNMENT;
    }

    /**
     * ������փh���b�O�\�ł��邩��Ԃ��܂��B
     * 
     * @return ������փh���b�O�\�ȏꍇ��true
     */
    protected boolean isTopDraggable() {
        return getAlignmentY() == TOP_ALIGNMENT;
    }

    /**
     * �������փh���b�O�\�ł��邩��Ԃ��܂��B
     * 
     * @return �������փh���b�O�\�ȏꍇ��true
     */
    protected boolean isBottomDraggable() {
        return getAlignmentY() == BOTTOM_ALIGNMENT;
    }

    /**
     * �R���|�[�l���g��Ń}�E�X���h���b�O�������ɌĂяo�����C�x���g�ł��B <br>
     * �e�E�B���h�E�̃T�C�Y�ƈʒu��ύX���܂��B
     * 
     * @param e �}�E�X�C�x���g���
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
     * �����̍��W�Ƀ}�E�X�J�[�\��������Ƃ��A�h���b�O�͉\����Ԃ��܂��B
     * 
     * @param x �R���g���[����X���W
     * @param y �R���g���[����Y���W
     * @return �h���b�O�͉\��
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
     * �R���|�[�l���g��Ń}�E�X�ړ����ɌĂяo�����C�x���g�ł��B <br>
     * �h���b�O�\�����ɂ��킹�āA�}�E�X�J�[�\����ω������܂��B
     * 
     * @param e �}�E�X�C�x���g���
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
     * �}�E�X�N���b�N���ɌĂяo�����C�x���g�ł��B
     * 
     * @deprecated �������܂���B
     * @param e �}�E�X�C�x���g���
     */
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * �R���|�[�l���g��Ń}�E�X�{�^���������ꂽ���ɌĂяo�����C�x���g�ł��B <br>
     * ���������ʒu���h���b�O�J�n�ʒu�Ƃ��ċL�����܂��B
     * 
     * @param e �}�E�X�C�x���g���
     */
    public void mousePressed(MouseEvent e) {
        if (canDraggablePos(e.getX(), e.getY())) {
            ownerWindow = getOwnerWindow();
            dragStartX = this.getWidth() - (int) e.getX() + 2;
            dragStartY = this.getHeight() - (int) e.getY() + 2;
        }
    }

    /**
     * �R���|�[�l���g��Ń}�E�X�{�^���������ꂽ���ɌĂяo�����C�x���g�ł��B <br>
     * �h���b�O�J�n�ʒu�������������܂��B
     * 
     * @param e �}�E�X�C�x���g���
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
     * �R���|�[�l���g��Ƀ}�E�X�����������ɌĂяo�����C�x���g�ł��B <br>
     * 
     * @deprecated WelExtWindowExpander�ł͉������܂���B
     * @param e �}�E�X�C�x���g���
     */
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * �R���|�[�l���g�ォ��}�E�X���o�����ɌĂяo�����C�x���g�ł��B <br>
     * �}�E�X�J�[�\����ʏ�̃J�[�\���ɖ߂��܂��B
     * 
     * @param e �}�E�X�C�x���g���
     */
    public void mouseExited(MouseEvent e) {
        this.setCursor(Cursor.getDefaultCursor());
    }

}