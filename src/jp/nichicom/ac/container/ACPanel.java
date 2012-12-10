package jp.nichicom.ac.container;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * VRLayout��ݒ肵���p�l���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRPanel
 * @see ACChildEnabledFollowable
 */

public class ACPanel extends VRPanel implements ACChildEnabledFollowable {
    private boolean followChildEnabled = false;

    /**
     * Creates a new <code>JPanel</code> with a double buffer and a flow
     * layout.
     */
    public ACPanel() {
        super();
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code> and
     * the specified buffering strategy. If <code>isDoubleBuffered</code> is
     * true, the <code>JPanel</code> will use a double buffer.
     * 
     * @param isDoubleBuffered a boolean, true for double-buffering, which uses
     *            additional memory space to achieve fast, flicker-free updates
     */
    public ACPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     * 
     * @param layout the LayoutManager to use
     */
    public ACPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     * 
     * @param layout the LayoutManager to use
     * @param isDoubleBuffered a boolean, true for double-buffering, which uses
     *            additional memory space to achieve fast, flicker-free updates
     */
    public ACPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��ݒ肵�܂��B
     * 
     * @return �]���T�C�Y
     */
    public int getHgap() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getHgap();
        }
        return 0;
    }

    /**
     * ���������̃O���b�h�̕���ݒ肵�܂��B
     * 
     * @return ���������̃O���b�h�̕�
     */
    public int getHgrid() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getHgrid();
        }
        return 0;
    }

    /**
     * ���x���̃}�[�W����ݒ肵�܂��B
     * 
     * @return ���x���̃}�[�W��
     */
    public int getLabelMargin() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getLabelMargin();
        }
        return 0;
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��ݒ肵�܂��B
     * 
     * @return �]���T�C�Y
     */
    public int getVgap() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getVgap();
        }
        return 0;
    }

    /**
     * �������s���邩��Ԃ��܂��B
     * 
     * @return �������s����ꍇ��true
     */
    public boolean isAutoWrap() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).isAutoWrap();
        }
        return false;
    }

    public boolean isFollowChildEnabled() {
        return followChildEnabled;
    }

    /**
     * �������s���邩��ݒ肵�܂��B
     * 
     * @param autoWrap �������s����ꍇ��true
     */
    public void setAutoWrap(boolean autoWrap) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setAutoWrap(autoWrap);
        }
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (isFollowChildEnabled()) {
            // �q���A������
            followChildEnabled(this, isEnabled());
        }
    }

    /**
     * �p�l����Enabled�ɘA�����ē���ڂ�Enabled��ݒ肷�邩 ��ݒ肵�܂��B
     * 
     * @param followChildEnabled �p�l����Enabled�ɘA�����ē���ڂ�Enabled��ݒ肷�邩
     */
    public void setFollowChildEnabled(boolean followChildEnabled) {
        boolean old = isFollowChildEnabled();
        this.followChildEnabled = followChildEnabled;
        if ((!old) && followChildEnabled) {
            // �V���ɘA�����邱�Ƃɂ���
            setEnabled(isEnabled());
        }
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��ݒ肵�܂��B
     * 
     * @param hgap �]���T�C�Y
     */
    public void setHgap(int hgap) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setHgap(hgap);
        }
    }

    /**
     * ���������̃O���b�h�̕���ݒ肵�܂��B
     * 
     * @param hgrid ���������̃O���b�h�̕�
     */
    public void setHgrid(int hgrid) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setHgrid(hgrid);
        }
    }

    /**
     * ���x���̃}�[�W����ݒ肵�܂��B
     * 
     * @param labelMargin ���x���̃}�[�W��
     */
    public void setLabelMargin(int labelMargin) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setLabelMargin(labelMargin);
        }
    }

    /**
     * ���������ɋ󂯂�]���T�C�Y��ݒ肵�܂��B
     * 
     * @param vgap �]���T�C�Y
     */
    public void setVgap(int vgap) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setVgap(vgap);
        }
    }

    /**
     * ����������ݒ肵�܂��B
     * 
     * @param align ��������
     */
    public void setAlignment(int align) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setAlignment(align);
        }
    }

    /**
     * ����������Ԃ��܂��B
     * 
     * @return ��������
     */
    public int getAlignment() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getAlignment();
        }
        return 0;
    }

    /**
     * ����������ݒ肵�܂��B
     * 
     * @param align ��������
     */
    public void setVAlignment(int align) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setVAlignment(align);
        }
    }

    /**
     * ����������Ԃ��܂��B
     * 
     * @return ��������
     */
    public int getVAlignment() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getVAlignment();
        }
        return 0;
    }

    /**
     * �s���̏c�A���C�����g��ݒ肵�܂��B
     * 
     * @param vLineAlign �z�u
     */
    public void setVLineAlignment(int align) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setVLineAlign(align);
        }
    }

    /**
     * �s���̏c�A���C�����g��Ԃ��܂��B
     * 
     * @return �s���̏c�A���C�����g
     */
    public int getVLineAlign() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getVLineAlign();
        }
        return 0;
    }

    /**
     * �q�̗L����Ԃ�A�����܂��B
     * 
     * @param container �e�R���e�i
     * @param enabled �L�����
     */
    protected void followChildEnabled(Container container, boolean enabled) {
        int compSize = container.getComponentCount();
        for (int i = 0; i < compSize; i++) {

            Component comp = container.getComponent(i);
            comp.setEnabled(enabled);
            if (comp instanceof ACChildEnabledFollowable) {
                // �q�A���ݒ�\�ȃR���|�[�l���g�̏ꍇ�͏������Ϗ��ς�
                continue;
            }
            if (comp instanceof Container) {
                followChildEnabled((Container) comp, enabled);
            }
        }
    }

    protected void initComponent() {
        super.initComponent();
        this.setLayout(new VRLayout());
    }

    /**
     * �t�@�C�i���C�Y�ɔ����ăf�b�h���b�N�h�~���������s���܂��B
     */
    public void prepareFinalize() {
        enumPrepareFinalize(this);
    }

    /**
     * �t�@�C�i���C�Y�ɔ����čċA�I�Ƀf�b�h���b�N�h�~���������s���܂��B
     * 
     * @param comp �R���|�[�l���g
     */
    private void enumPrepareFinalize(Component comp) {
        if (comp instanceof JComboBox) {
            ((JComboBox) comp).setModel(new DefaultComboBoxModel());
        } else if (comp instanceof JList) {
            ((JList) comp).setModel(new DefaultListModel());
        } else if (comp instanceof JTable) {
            ((JTable) comp).setModel(new DefaultTableModel());
        } else if (comp instanceof JTree) {
            ((JTree) comp).setModel(new DefaultTreeModel(null));
        }
        if (comp instanceof Container) {
            Container parent = (Container) comp;
            int end = parent.getComponentCount();
            for (int i = 0; i < end; i++) {
                comp = parent.getComponent(i);
                if (comp instanceof ACPanel) {
                    ((ACPanel) comp).prepareFinalize();
                } else {
                    enumPrepareFinalize(comp);
                }
            }
        }
    }
    
    public void removeNotify(){
        prepareFinalize();
        super.removeNotify();
    }
}
