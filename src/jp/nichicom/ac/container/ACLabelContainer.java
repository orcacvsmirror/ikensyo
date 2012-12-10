package jp.nichicom.ac.container;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;

import javax.swing.JLabel;

import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;

/**
 * �`���NCLabel���g�p���郉�x���R���e�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACPanel
 * @see ACChildEnabledFollowable
 */

public class ACLabelContainer extends VRLabelContainer implements
        ACChildEnabledFollowable {
    private boolean followChildEnabled = false;

    /**
     * �R���X�g���N�^
     */
    public ACLabelContainer() {
        super();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param text �L���v�V�����e�L�X�g
     */
    public ACLabelContainer(String text) {
        super(text);
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

    protected LayoutManager createLayout(){
        VRLayout lay = new VRLayout(VRLayout.LEFT, 0, 0);
        lay.setAutoWrap(false);
        return lay;
    }

    protected JLabel createTextRenderer() {
        return new ACLabel();
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

    protected int getColumnWidth() {
        return (int) (super.getColumnWidth() * 1.1);
    }
    
}
