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
 * �㉺���E�����ւ̃h�b�L���O���T�|�[�g���郌�C�A�E�g�ł��B
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
     * �z�u���u�h�b�L���O��v�ɂ���萔�ł��B
     */
    public static final String NORTH = "North";

    /**
     * �z�u���u�h�b�L���O���v�ɂ���萔�ł��B
     */
    public static final String SOUTH = "South";

    /**
     * �z�u���u�h�b�L���O�E�v�ɂ���萔�ł��B
     */
    public static final String EAST = "East";

    /**
     * �z�u���u�h�b�L���O���v�ɂ���萔�ł��B
     */
    public static final String WEST = "West";

    /**
     * �z�u���u�h�b�L���O�����v�ɂ���萔�ł��B
     */
    public static final String CLIENT = "Client";

    /** �㉺���E�ɔz�u */
    private LinkedHashMap sideDocks = new LinkedHashMap();

    /** �Z���^�[�z�u */
    private ArrayList centerDocks = new ArrayList();

    /** ���������ɋ󂯂�]���T�C�Y */
    private int hgap;

    /** ���������ɋ󂯂�]���T�C�Y */
    private int vgap;

    private boolean horizontalDivide;

    public VRBorderLayout() {
        this(0, 0);

    }

    /**
     * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
     * 
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     */
    public VRBorderLayout(int hgap, int vgap) {
        this(hgap, vgap, true);

    }

    /**
     * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
     * 
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param horizontalDivide �N���C�A���g�̈�𐂒��Ɋ���ݒ�
     */
    public VRBorderLayout(int hgap, int vgap, boolean horizontalDivide) {
        this.hgap = hgap;
        this.vgap = vgap;
        this.horizontalDivide = horizontalDivide;

    }

    /**
     * �R���e�i�ɃR���|�[�l���g��ǉ����鎞�ɔ�������C�x���g�ł��B
     * 
     * @param comp �ǉ��R���|�[�l���g
     * @param constraints �ǉ����@�ݒ�
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
     * @deprecated addLayoutComponent(Component comp, Object constraints)���g�p���܂��B
     */
    public void addLayoutComponent(String name, Component comp) {
        synchronized (comp.getTreeLock()) {

            /*
             * ���C�A�E�g�ǉ�
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
     * �R���e�i����R���|�[�l���g���폜���鎞�ɔ�������C�x���g�ł��B
     * 
     * @param comp �폜�R���|�[�l���g
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
     * �R���e�i���̃R���|�[�l���g�Ɋ�Â��āA���̃��C�A�E�g�}�l�[�W�����g�p���邽�߂̃R���e�i�̐����T�C�Y��Ԃ��܂��B
     * 
     * @param target �z�u���s����R���e�i
     * @return �w�肳�ꂽ�R���e�i�̎q�R���|�[�l���g��z�u���邽�߂ɕK�v�Ȑ����T�C�Y��Ԃ��܂��B
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
                    //Y�����̃A���C�����g��D�悵�čs���i���̌�X�����̃A���C�����g�j
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
                //���������邽�߂̌v�Z
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
     * �R���e�i���̃R���|�[�l���g�Ɋ�Â��āA���̃��C�A�E�g�}�l�[�W�����g�p���邽�߂̃R���e�i�̍ŏ��T�C�Y��Ԃ��܂��B
     * 
     * @param target �z�u���s����R���e�i
     * @return �w�肳�ꂽ�R���e�i�̎q�R���|�[�l���g��z�u���邽�߂ɕK�v�ȍŏ��T�C�Y
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
     * ���̃��C�A�E�g���g�p���ăR���e�i��z�u���܂��B
     * 
     * @param target �z�u���s����R���e�i
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

            //�T�C�h�h�b�L���O�R���g���[��

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

            //�����z�u�R���g���[��

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
     * ���̃��C�A�E�g�̏�Ԃ̕�����\����Ԃ��܂��B
     * 
     * @return ���̃��C�A�E�g�̏�Ԃ̕�����\��
     */
    public String toString() {

        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap
                + ",horizontalDivid=" + horizontalDivide + " ]";
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��Ԃ��܂��B
     * 
     * @return �]���T�C�Y��Ԃ��܂��B
     */
    public int getHgap() {
        return hgap;
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��ݒ肵�܂��B
     * 
     * @param hgap �]���T�C�Y
     */
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��Ԃ��܂��B
     * 
     * @return �]���T�C�Y
     */
    public int getVgap() {
        return vgap;
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��ݒ肵�܂��B
     * 
     * @param vgap �]���T�C�Y
     */
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    /**
     * �����������邩��Ԃ��܂��B
     * 
     * @return horizontalDivide
     */
    public boolean isHorizontalDivide() {
        return horizontalDivide;
    }

    /**
     * �����������邩��ݒ肵�܂��B
     * 
     * @param horizontalDivide �����������邩
     */
    public void setHorizontalDivide(boolean horizontalDivide) {
        this.horizontalDivide = horizontalDivide;
    }
}