package jp.nichicom.ac.component.event;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.container.VRLabelContainer;

/**
 * �t�H�[�}�b�g�̃G���[�L���ɉ����ď�ʂ̃R���e�i�𒅐F����t�H�[�}�b�g�C�x���g���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRFormatEventListener
 */
public class ACFollowContainerFormatEventListener implements
        VRFormatEventListener {
    private boolean allowedBlank = true;
    private VRLabelContainer changedContainer;
    private int parentFindCount = 3;
    private boolean valid = true;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACFollowContainerFormatEventListener() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param allowedBlank �����͂������邩
     */
    public ACFollowContainerFormatEventListener(boolean allowedBlank) {
        super();
        setAllowedBlank(allowedBlank);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param parentFindCount �G���[���Ɍ�������e�K�w
     */
    public ACFollowContainerFormatEventListener(int parentFindCount) {
        super();
        setParentFindCount(parentFindCount);
    }

    /**
     * �R���e�i�𖳌���ԕ\���ɂ��܂��B
     */
    public void changeInvalidContainer() {
        if (changedContainer != null) {
            changedContainer.setLabelFilled(true);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            Color fore;
            Color back;
            if (processer == null) {
                fore = Color.white;
                back = Color.red;
            } else {
                fore = processer.getContainerErrorForeground();
                back = processer.getContainerErrorBackground();
            }
            changedContainer.setForeground(fore);
            changedContainer.setBackground(back);

            setValid(false);
        }
    }

    /**
     * �R���e�i���x����ԕ\���ɂ��܂��B
     */
    public void changeWarningContainer() {
        if (changedContainer != null) {
            changedContainer.setLabelFilled(true);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            Color fore;
            Color back;
            if (processer == null) {
                fore = Color.black;
                back = Color.yellow;
            } else {
                fore = processer.getContainerWarningForeground();
                back = processer.getContainerWarningBackground();
            }
            changedContainer.setForeground(fore);
            changedContainer.setBackground(back);

            setValid(false);
        }
    }

    /**
     * �R���e�i��L����ԕ\���ɂ��܂��B
     */
    public void changeValidContainer() {
        if (changedContainer != null) {
            changedContainer.setLabelFilled(false);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            Color fore;
            Color back;
            if (processer == null) {
                fore = Color.black;
                back = Color.black;
            } else {
                fore = processer.getContainerDefaultForeground();
                back = processer.getContainerDefaultBackground();
            }
            changedContainer.setForeground(fore);
            changedContainer.setBackground(back);

            setValid(true);
        }
    }

    public void formatInvalid(VRFormatEvent e) {
        if (isAllowedBlank()) {
            Object parse = e.getParseValue();
            if ((parse == null) || "".equals(parse)) {
                // �����͂͋��e����
                changeValidContainer();
                return;
            }
        }
        Object source = e.getSource();
        if (source instanceof Component) {
            // �e���x���R���e�i������
            changedContainer = findParent(((Component) source).getParent(), 0);
            changeInvalidContainer();
        }
    }
    /**
     * �t�H�[�}�b�g�x���C�x���g���������܂��B
     * @param e �C�x���g���
     */
    public void formatWarning(VRFormatEvent e) {
        if (isAllowedBlank()) {
            Object parse = e.getParseValue();
            if ((parse == null) || "".equals(parse)) {
                // �����͂͋��e����
                changeValidContainer();
                return;
            }
        }
        Object source = e.getSource();
        if (source instanceof Component) {
            // �e���x���R���e�i������
            changedContainer = findParent(((Component) source).getParent(), 0);
            changeWarningContainer();
        }
    }
    public void formatValid(VRFormatEvent e) {
        if (changedContainer == null) {
            // �e���x���R���e�i���ݒ肳��Ă��Ȃ���Ό���
            Object source = e.getSource();
            if (source instanceof Component) {
                // �e���x���R���e�i������
                changedContainer = findParent(((Component) source).getParent(),
                        0);
            }
        }
        
        if (!isAllowedBlank()) {
            if ((e.getParseValue() == null) || ("".equals(e.getParseValue()))) {
                // �󕶎���F�߂Ȃ��ݒ�
                changeInvalidContainer();
                return;
            }
        }
        changeValidContainer();
    }

    /**
     * �G���[���Ɍ�������e�K�w�̐���Ԃ��܂��B
     * 
     * @return �G���[���Ɍ�������e�K�w
     */
    public int getParentFindCount() {
        return parentFindCount;
    }

    /**
     * �����͂������邩 ��Ԃ��܂��B
     * 
     * @return �����͂������邩
     */
    public boolean isAllowedBlank() {
        return allowedBlank;
    }

    /**
     * �e�R���e�i���L����ԕ\���ł��邩��Ԃ��܂��B
     * 
     * @return �e�R���e�i���L����ԕ\���ł��邩
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * �����͂������邩 ��ݒ肵�܂��B
     * 
     * @param allowedBlank �����͂������邩
     */
    public void setAllowedBlank(boolean allowedBlank) {
        this.allowedBlank = allowedBlank;
    }

    /**
     * �G���[���Ɍ�������e�K�w�̐���ݒ肵�܂��B
     * 
     * @param parentFindCount �G���[���Ɍ�������e�K�w
     */
    public void setParentFindCount(int parentFindCount) {
        this.parentFindCount = parentFindCount;
    }

    /**
     * �e�R���e�i���ċA�������A���F���܂��B
     * 
     * @param parent �e�R���e�i
     * @param depth �ċA�[�x
     */
    protected VRLabelContainer findParent(Container parent, int depth) {
        // ���̓G���[���ɂ̓R���e�i�ɃG���[�F��ݒ�
        if (parent == null) {
            return null;
        }
        if (parent instanceof VRLabelContainer) {
            return (VRLabelContainer) parent;
        }
        if (depth < parentFindCount) {
            // �e�ւ����オ��
            return findParent(parent.getParent(), depth + 1);
        }
        return null;
    }

    /**
     * �e�R���e�i���L����ԕ\���ł��邩 ��ݒ肵�܂��B
     * 
     * @param valid �e�R���e�i���L����ԕ\���ł��邩
     */
    protected void setValid(boolean valid) {
        this.valid = valid;
    }

}
