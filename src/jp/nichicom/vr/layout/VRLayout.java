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
 * �㉺���E�����ւ̃h�b�L���O�̑��A�񋓂␮����T�|�[�g���郌�C�A�E�g�ł��B
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

    /**
     * �z�u���u�t���[�v�ɂ���萔�ł��B
     */
    public static final String FLOW = "Flow";

    /**
     * �z�u���u�t���[���s�v�ɂ���萔�ł��B
     */
    public static final String FLOW_RETURN = "Return";

    /**
     * �z�u���u�t���[�C���Z�b�g�v�ɂ���萔�ł��B
     */
    public static final String FLOW_INSETLINE = "Inset";

    /**
     * �z�u���u�t���[�C���Z�b�g���s�v�ɂ���萔�ł��B
     */
    public static final String FLOW_INSETLINE_RETURN = "InsetReturn";

    /**
     * �z�u���u��d�t���[�C���Z�b�g�v�ɂ���萔�ł��B
     */
    public static final String FLOW_DOUBLEINSETLINE = "DoubleInset";

    /**
     * �z�u���u��d�t���[�C���Z�b�g���s�v�ɂ���萔�ł��B
     */
    public static final String FLOW_DOUBLEINSETLINE_RETURN = "DoubleInsetReturn";

    /**
     * �z�u���u�t���[LEFT�v�ɂ���萔�ł��B
     */
    public static final String FLOW_LEFT = "Left";

    /** �z�u���u���[�v�ɐݒ肷��萔�ł��B */
    public static final int LEFT = 0;

    /** �z�u���u�����v�ɐݒ肷��萔�ł��B */
    public static final int CENTER = 1;

    /** �z�u���u�E�[�v�ɐݒ肷��萔�ł��B */
    public static final int RIGHT = 2;

    /** �z�u���u�擪�v�ɐݒ肷��萔�ł��B */
    public static final int LEADING = 3;

    /** �z�u���u�����v�ɐݒ肷��萔�ł��B */
    public static final int TRAILING = 4;

    /** �z�u���u��[�v�ɐݒ肷��萔�ł��B */
    public static final int TOP = 0;

    /** �z�u���u���[�v�ɐݒ肷��萔�ł��B */
    public static final int BOTTOM = 2;

    /** �㉺���E�ɔz�u */
    private LinkedHashMap sideDocks = new LinkedHashMap();

    /** �Z���^�[�z�u */
    private ArrayList centerDocks = new ArrayList();

    /** �����z�u */
    private LinkedHashMap flows = new LinkedHashMap();

    /** �z�u���@(LEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ) */
    private int alignment;

    private int vAlignment;

    /** ���������ɋ󂯂�]���T�C�Y */
    private int hgap;

    /** ���������ɋ󂯂�]���T�C�Y */
    private int vgap;

    /** ���������̃O���b�h�̕� */
    private int vgrid;

    /** ���������̃O���b�h�̍��� */
    private int hgrid;

    /** �������������ς��ɕ����L���邩 */
    private boolean fitHGrid;

    /** �������������ς��ɍ������L���邩 */
    private boolean fitVGrid;

    /** �������������ς��ɕ����L���邩 */
    private boolean fitHLast;

    /** �������������ς��ɍ������L���邩 */
    private boolean fitVLast;

    /** �����ŉ��s���邩 */
    private boolean autoWrap;

    /** �s���̐����z�u���� */
    private int vLineAlign;

    /** �L���v�V�������I�[�o�[������ݒ� */
    private boolean labelOverwrap;

    /** ���x���̃}�[�W�� */
    private int labelMargin;

    /** �����L�k�����邩 */
    private boolean stretch;

    /** �������������邩 */
    private boolean horizontalDivide;

    /**
     * �p�����[�^��S�Ď����ݒ肵�A�I�[�o�[���[�h�R���X�g���N�^���Ăяo���܂��B
     * <p>
     * �y�����ݒ�l�z <br>
     * align - �z�u�����FLEFT <br>
     * hgap - �e���������ɋ󂯂�]���T�C�Y�F2 <br>
     * vgap - ���������ɋ󂯂�]���T�C�Y�F2
     * </p>
     */
    public VRLayout() {
        this(LEFT, 20, 2);
    }

    /**
     * �w��Ȃ��̃p�����[�^�������ݒ肵�A�I�[�o�[���[�h�R���X�g���N�^���Ăяo���܂��B
     * <p>
     * �y�����ݒ�l�z <br>
     * hgap - �e���������ɋ󂯂�]���T�C�Y�F2 <br>
     * vgap - ���������ɋ󂯂�]���T�C�Y�F2
     * </p>
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     */
    public VRLayout(int align) {
        this(align, 20, 2);
    }

    /**
     * �w��Ȃ��̃p�����[�^�������ݒ肵�A�I�[�o�[���[�h�R���X�g���N�^���Ăяo���܂��B
     * <p>
     * �y�����ݒ�l�z <br>
     * hgrid - ���������̃O���b�h�̕��F120 <br>
     * vgrid - ���������̃O���b�h�̍����F1 <br>
     * fitHgrid - ���������ɍő�܂ŕ����L����ꍇ��true�Ffalse <br>
     * fitVgrid - ���������ɍő�܂ō������L����ꍇ��true�Ffalse
     * </p>
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     */
    public VRLayout(int align, int hgap, int vgap) {
        this(align, hgap, vgap, 60, 1, false, false);
    }

    /**
     * �w��Ȃ��̃p�����[�^�������ݒ肵�A�I�[�o�[���[�h�R���X�g���N�^���Ăяo���܂��B
     * <p>
     * �y�����ݒ�l�z <br>
     * vLineAlign - �s�̐����z�u�����FTOP
     * </p>
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param hgrid ���������̃O���b�h�̕�
     * @param vgrid ���������̃O���b�h�̍���
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid) {

        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, TOP);
    }

    /**
     * �w��Ȃ��̃p�����[�^�������ݒ肵�A���C���R���X�g���N�^���Ăяo���܂��B
     * <p>
     * �y�����ݒ�l�z <br>
     * autoWrap - �������s����ꍇ��true�Ffalse
     * </p>
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param hgrid ���������̃O���b�h�̕�
     * @param vgrid ���������̃O���b�h�̍���
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     * @param vLineAlign �s�̐����z�u����
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                true);
    }

    /**
     * �w��Ȃ��̃p�����[�^�������ݒ肵�A�I�[�o�[���[�h�R���X�g���N�^���Ăяo���܂��B
     * <p>
     * �y�����ݒ�l�z <br>
     * labelOverwrap - ���񃉃x���̃I�[�o�[���b�v��������ꍇ��true�Ffalse <br>
     * labelMargin - ���x���}�[�W���F100 <br>
     * stretch - �X�g���b�`����ꍇ��true�Ftrue
     * </p>
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param hgrid ���������̃O���b�h�̕�
     * @param vgrid ���������̃O���b�h�̍���
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     * @param vLineAlign �s�̐����z�u����
     * @param autoWrap �������s����ꍇ��true
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign, boolean autoWrap) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                autoWrap, true, 60, false);
    }

    /**
     * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param hgrid ���������̃O���b�h�̕�
     * @param vgrid ���������̃O���b�h�̍���
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     * @param vLineAlign �s�̐����z�u����
     * @param autoWrap �������s����ꍇ��true
     * @param labelOverwrap �I�[�o�[���b�v�ݒ���ɘa����ꍇ��true
     * @param labelMargin ���x���}�[�W��
     * @param stretch �X�g���b�`����ꍇ��true
     */
    public VRLayout(int align, int hgap, int vgap, int hgrid, int vgrid,
            boolean fitHgrid, boolean fitVgrid, int vLineAlign,
            boolean autoWrap, boolean labelOverwrap, int labelMargin,
            boolean stretch) {
        this(align, hgap, vgap, hgrid, vgrid, fitHgrid, fitVgrid, vLineAlign,
                autoWrap, labelOverwrap, labelMargin, stretch, TOP);

    }

    /**
     * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param hgrid ���������̃O���b�h�̕�
     * @param vgrid ���������̃O���b�h�̍���
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     * @param vLineAlign �s�̐����z�u����
     * @param autoWrap �������s����ꍇ��true
     * @param labelOverwrap �I�[�o�[���b�v�ݒ���ɘa����ꍇ��true
     * @param labelMargin ���x���}�[�W��
     * @param stretch �X�g���b�`����ꍇ��true
     * @param vAlign �z�u�����iTOP, CENTER, BOTTOM, LEADING, TRAILING�̂����ꂩ�j
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
     * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
     * 
     * @param align �z�u�����iLEFT, CENTER, RIGHT, LEADING, TRAILING�̂����ꂩ�j
     * @param hgap ���������ɋ󂯂�]���T�C�Y
     * @param vgap ���������ɋ󂯂�]���T�C�Y
     * @param hgrid ���������̃O���b�h�̕�
     * @param vgrid ���������̃O���b�h�̍���
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     * @param vLineAlign �s�̐����z�u����
     * @param autoWrap �������s����ꍇ��true
     * @param labelOverwrap �I�[�o�[���b�v�ݒ���ɘa����ꍇ��true
     * @param labelMargin ���x���}�[�W��
     * @param stretch �X�g���b�`����ꍇ��true
     * @param vAlign �z�u�����iTOP, CENTER, BOTTOM, LEADING, TRAILING�̂����ꂩ�j
     * @param horizontalDivide �N���C�A���g�̈�𐅕������ɕ�������ꍇ��true
     * @param fitHLast ���������ɍŌ�܂ŕ����L����ꍇ��true
     * @param fitVLast ���������ɍŌ�܂ō������L����ꍇ��true
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
     * �R���e�i�ɃR���|�[�l���g��ǉ����鎞�ɔ�������C�x���g�ł��B
     * 
     * @param comp �ǉ��R���|�[�l���g
     * @param constraints �ǉ��ݒ�
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
     * �R���e�i����R���|�[�l���g���폜���鎞�ɔ�������C�x���g�ł��B
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
     * �}�b�v����R���|�[�l���g���L�[�Ƃ���G���g�����폜���܂��B
     * 
     * @param map �폜��
     * @param comp ��r�Ώ�
     * @return �폜������
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
     * �R���e�i���̃R���|�[�l���g�Ɋ�Â��āA���̃��C�A�E�g�}�l�[�W�����g�p���邽�߂̃R���e�i�̐����T�C�Y��Ԃ��܂��B
     * 
     * @param target �z�u���s����R���e�i
     * @return �w�肳�ꂽ�R���e�i�̎q�R���|�[�l���g��z�u���邽�߂ɕK�v�Ȑ����T�C�Y
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
                    //Y�����̃A���C�����g��D�悵�čs���i���̌�X�����̃A���C�����g�j
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
                //���������邽�߂̌v�Z
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

            //��_�̃M���b�v�ݒ�
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

            //�������s�Ȃ�

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

                    //�ŏ��ɁA����̃R���g���[����ǉ����邱�Ƃɂ����s���������邩���m�F���A
                    //���s����������ꍇ�A�s�̕��v�Z������O�ɉ��s������
                    if (autoWrap
                            && (lmw + ((stretch) ? md.width : d.width) + hgap > maxwidth && lmw > 0)) {

                        //�ŏ��l�̌��E�𒴂����ꍇ
                        y += (int) Math.max(rowh + vgap, vgrid);
                        roww = (int) Math.max(x, roww);

                        //�s������
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

                    //�s�������킹��v�Z���s��

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
                        //���x���R���e�i�֘A
                        if (lmw + isl < firstMargin) {
                            lmw = firstMargin - isl;
                        }

                        if (x + isl < firstMargin) {
                            x = firstMargin - isl;
                        }

                    } else {
                        //���x���R���e�i�ȊO�ŁA���s���̏ꍇ�A�M���b�v��ǉ�����
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

                    //�s�̕���ǉ�����
                    x += d.width + hgap;
                    lmw += ((stretch) ? md.width : d.width) + hgap;

                    //���̃R���g���[�������s�@�\�t�ł���ꍇ�A���s����
                    if (FLOW_RETURN.equals(key)
                            || FLOW_INSETLINE_RETURN.equals(key)
                            || FLOW_DOUBLEINSETLINE_RETURN.equals(key)) {
                        y += (int) Math.max(rowh + vgap, vgrid);
                        roww = (int) Math.max(x, roww);

                        //�s������
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

            //�����ʒu�̒���
            if (fitVGrid) {
                y = (int) (Math.ceil((double) y / (double) vgrid) * vgrid)
                        - vgap;
            } else {
                y -= vgap;
            }

            //�S�̂̍������擾
            y += ((int) Math.max(cy, insets.top + vgap) + (int) Math.max(
                    insets.bottom + vgap, cb));

            //�Œ�m�ۍ��̐ݒ�
            y = (int) Math.max(y, keepheight);

            //���ʒu�̒���
            if (fitHGrid) {
                roww += hgap;
                roww = (int) (Math.ceil((double) roww / (double) hgrid) * hgrid)
                        - hgap;
            }

            //�S�̂̕��ݒ�
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
            int cx = insets.left;
            int cy = insets.top;
            int cr = insets.right;
            int cb = insets.bottom;

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

            //�����z�u�R���g���[��

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

            //���s����

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
                        //�ŏ��l�̌��E�𒴂����ꍇ
                        lhp = new ArrayList();
                        lst.add(lhp);
                        ny += (int) Math.max(rh + vgap, vgrid);

                        //�s������
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

                        //�s������
                        ny = (int) Math.max(shiftEndY, ny);
                        shiftX = 0;
                        shiftEndY = 0;

                        rh = 0;
                        lmw = 0;
                    }

                }
            }

            //�ŏI�s������Ȃ���Ώ���
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

            //�c�z�u
            switch (vAlignment) {
            case CENTER:
                cy += (h - (cy + cb) - ny) / 2;
                break;
            case BOTTOM:
            case TRAILING:
                cy += h - (cy + cb) - ny;
                break;
            }

            //�Ĕz�u
            int[] xs = new int[lst.size()];

            int min = 0;
            int max = 0;

            for (int i = 0; i <= lst.size() - 1; i++) {
                xs[i] = -1;
            }

            //�������킹��
            for (int i = 0; i <= lst.size() - 1; i++) {
                ArrayList lh = (ArrayList) lst.get(i);
                if (lh.size() > 0) {
                    if (xs[i] < lh.size() - 1) {
                        CompoInfo ci1 = (CompoInfo) lh.get(0);
                        int mxcal = ci1.rect.x;
                        boolean isleadercheck = true;
                        for (int j = xs[i] + 1; j <= lh.size() - 1; j++) {
                            CompoInfo ci2 = (CompoInfo) lh.get(j);
                            //�������x���R���e�i�ɂȂ�܂Ō����J�n�ʒu���ړ�����
                            if (ci2.leader == 0) {
                                if (isleadercheck) {
                                    xs[i] = j;
                                }
                                //�������x���R���e�i�ȊO�̏ꍇ�J�cLeft�w��ȊO�̏ꍇ
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

            //�z�u�v�Z
            int minpos = 0;

            while (minpos >= 0) {

                min = 0;
                max = 0;

                //�ŏ��ʒu������
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

                //����ʒu����

                if (minpos == -1) {
                    break;
                }

                int linemax = 0;
                int minS = minpos;
                int minE = minpos;
                max = min;

                //�ŏ��ʒu����̑ΏۃR���g���[��������
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
                        //�󔒂��͂��񂾏ꍇ�A����ΏۂƂȂ�
                        if (lh.size() > 0) {
                            CompoInfo ci = (CompoInfo) lh.get(lh.size() - 1);
                            if (ci.rect.x + ci.rect.width <= min) {
                                continue;
                            }
                        }
                    }
                    break;
                }

                //�ŏ��ʒu���O�̑ΏۃR���g���[��������
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
                        //�󔒂��͂��񂾏ꍇ�A����ΏۂƂȂ�
                        if (lh.size() > 0) {
                            CompoInfo ci = (CompoInfo) lh.get(lh.size() - 1);
                            if (ci.rect.x + ci.rect.width <= min) {
                                continue;
                            }
                        }
                    }
                    break;
                }
                //�I�[�o�[���b�v�ݒ���ɘa����
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

                //���񏈗����s��

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

            //�z�u
            for (int i = 0; i < lst.size(); i++) {
                ArrayList lh = (ArrayList) lst.get(i);
                if (lh.size() > 0) {
                    CompoInfo ci = (CompoInfo) lh.get(lh.size() - 1);
                    CompoInfo ci2;
                    int mgn = ci.rect.x + ci.rect.width - maxwidth;
                    if (mgn > 0) {
                        //���Ԗ��ߏ���
                        for (int j = lh.size() - 1; j >= 0; j--) {
                            ci = (CompoInfo) lh.get(j);
                            int mm = ci.rect.x;
                            if (j > 0) {
                                ci2 = (CompoInfo) lh.get(j - 1);
                                mm -= (ci2.rect.x + ci2.rect.width);
                            }
                            if (mm > mgn) {
                                //�X�y�[�X�ɗ]�T������ꍇ
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
                        //���Ԗ��߂̌��E�𒴂����ꍇ�A�k������
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

                    //���̈ʒu�v�Z
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

                    //�ő�l�擾
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
     * ���̃��C�A�E�g�̏�Ԃ̕�����\����Ԃ��܂��B
     * 
     * @return ���̃��C�A�E�g�̏�Ԃ̕�����\��
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
     * �ێ��R���|�[�l���g�̔z�u������Ԃ��܂��B
     * 
     * @return �z�u����
     */
    public int getAlignment() {
        return alignment;
    }

    /**
     * �ێ��R���|�[�l���g�̔z�u������ݒ肵�܂��B
     * 
     * @param align �z�u����
     */
    public void setAlignment(int align) {
        this.alignment = align;
    }

    /**
     * ���������ɍő�܂ō������L���邩��Ԃ��܂��B
     * 
     * @return ���������ɍő�܂ō������L����ꍇ��true
     */
    public boolean getFitVGrid() {
        return fitVGrid;
    }

    /**
     * ���������ɍő�܂ō������L���邩��ݒ肵�܂��B
     * 
     * @param fitVgrid ���������ɍő�܂ō������L����ꍇ��true
     */
    public void setFitVGrid(boolean fitVgrid) {
        this.fitVGrid = fitVgrid;
    }

    /**
     * ���������ɍő�܂ŕ����L���邩��Ԃ��܂��B
     * 
     * @return ���������ɍő�܂ŕ����L����ꍇ��true
     */
    public boolean getFitHGrid() {
        return fitHGrid;
    }

    /**
     * ���������ɍő�܂ŕ����L���邩��ݒ肵�܂��B
     * 
     * @param fitHgrid ���������ɍő�܂ŕ����L����ꍇ��true
     */
    public void setFitHGrid(boolean fitHgrid) {
        this.fitHGrid = fitHgrid;
    }

    /**
     * ���������ɍő�܂ō������L���邩��Ԃ��܂��B
     * 
     * @return ���������ɍő�܂ō������L����ꍇ��true
     */
    public boolean getFitVLast() {
        return fitVLast;
    }

    /**
     * ���������ɍő�܂ō������L���邩��ݒ肵�܂��B
     * 
     * @param fitVLast ���������ɍő�܂ō������L����ꍇ��true
     */
    public void setFitVLast(boolean fitVLast) {
        this.fitVLast = fitVLast;
    }

    /**
     * ���������ɍő�܂ŕ����L���邩��Ԃ��܂��B
     * 
     * @return ���������ɍő�܂ŕ����L����ꍇ��true
     */
    public boolean getFitHLast() {
        return fitHLast;
    }

    /**
     * ���������ɍő�܂ŕ����L���邩��ݒ肵�܂��B
     * 
     * @param fitHLast ���������ɍő�܂ŕ����L����ꍇ��true
     */
    public void setFitHLast(boolean fitHLast) {
        this.fitHLast = fitHLast;
    }

    /**
     * ���������̃O���b�h�̕���Ԃ��܂��B
     * 
     * @return ���������̃O���b�h�̕�
     */
    public int getHgrid() {
        return hgrid;
    }

    /**
     * ���������̃O���b�h�̕���ݒ肵�܂��B
     * 
     * @param hgrid ���������̃O���b�h�̕�
     */
    public void setHgrid(int hgrid) {
        this.hgrid = hgrid;
    }

    /**
     * ���������̃O���b�h�̍�����Ԃ��܂��B
     * 
     * @return ���������̃O���b�h�̍���
     */
    public int getVgrid() {
        return vgrid;
    }

    /**
     * ���������̃O���b�h�̍�����ݒ肵�܂��B
     * 
     * @param vgrid ���������̃O���b�h�̍���
     */
    public void setVgrid(int vgrid) {
        this.vgrid = vgrid;
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��Ԃ��܂��B
     * 
     * @return �]���T�C�Y
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
     * �s���̏c�A���C�����g��Ԃ��܂��B
     * 
     * @return �z�u
     */
    public int getVLineAlign() {
        return vLineAlign;
    }

    /**
     * �s���̏c�A���C�����g��ݒ肵�܂��B
     * 
     * @param vLineAlign �z�u
     */
    public void setVLineAlign(int vLineAlign) {
        this.vLineAlign = vLineAlign;
        this.vLineAlign = vLineAlign;
    }

    /**
     * �������s���邩��Ԃ��܂��B
     * 
     * @return �������s����ꍇ��true
     */
    public boolean isAutoWrap() {
        return autoWrap;
    }

    /**
     * �������s���邩��ݒ肵�܂��B
     * 
     * @param autoWrap �������s����ꍇ��true
     */
    public void setAutoWrap(boolean autoWrap) {
        this.autoWrap = autoWrap;
    }

    /**
     * ���񃉃x���̃I�[�o�[���b�v�������邩��Ԃ��܂��B
     * 
     * @return �I�[�o�[���b�v��������ꍇ��True
     */
    public boolean isLabelOverwrap() {
        return labelOverwrap;
    }

    /**
     * ���񃉃x���̃I�[�o�[���b�v�������邩��ݒ肵�܂��B
     * 
     * @param labelOverwrap �I�[�o�[���b�v��������ꍇ��True
     */
    public void setLabelOverwrap(boolean labelOverwrap) {
        this.labelOverwrap = labelOverwrap;
    }

    /**
     * ���x���̃}�[�W����Ԃ��܂��B
     * 
     * @return ���x���̃}�[�W��
     */
    public int getLabelMargin() {
        return labelMargin;
    }

    /**
     * ���x���̃}�[�W����ݒ肵�܂��B
     * 
     * @param labelMargin ���x���̃}�[�W��
     */
    public void setLabelMargin(int labelMargin) {
        this.labelMargin = labelMargin;
    }

    /**
     * �X�g���b�`���邩��Ԃ��܂��B
     * 
     * @return �X�g���b�`����ꍇ��True
     */
    public boolean isStretch() {
        return stretch;
    }

    /**
     * �X�g���b�`���邩��ݒ肵�܂��B
     * 
     * @param stretch �X�g���b�`����ꍇ��True
     */
    public void setStretch(boolean stretch) {
        this.stretch = stretch;
    }

    /**
     * �c�A���C�����g��Ԃ��܂��B
     * 
     * @return int �c�A���C�����g
     */
    public int getVAlignment() {
        return vAlignment;
    }

    /**
     * �c�A���C�����g��ݒ肵�܂��B
     * 
     * @param vAlignment int �c�A���C�����g
     */
    public void setVAlignment(int vAlignment) {
        this.vAlignment = vAlignment;
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

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {

    }
}