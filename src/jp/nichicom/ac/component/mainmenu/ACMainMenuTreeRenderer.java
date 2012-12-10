package jp.nichicom.ac.component.mainmenu;

import java.awt.Component;
import java.text.ParseException;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;

import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;

/**
 * ���C�����j���[�p�̃c���[�����_���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see TreeNode
 */

public class ACMainMenuTreeRenderer extends DefaultTreeCellRenderer {
    /**
     * ����̃L���v�V�����p�̃o�C���h�p�X��\���萔�ł��B
     */
    public static final String DEFAULT_CAPTION_BINDPATH = "CAPTION";
    /**
     * ����̃A�C�R���p�̃o�C���h�p�X��\���o�C���h�p�X�萔�ł��B
     */
    public static final String DEFAULT_ICON_BINDPATH = "ICON";
    /**
     * ����̃c�[���`�b�v�p�̃o�C���h�p�X��\���o�C���h�p�X�萔�ł��B
     */
    public static final String DEFAULT_TOOLTIP_BINDPATH = "TOOLTIP_TEXT";

    private String captionBindPath = DEFAULT_CAPTION_BINDPATH;
    private String iconBindPath = DEFAULT_ICON_BINDPATH;
    private String nodeIconPath;
    private String toolTipBindPath = DEFAULT_TOOLTIP_BINDPATH;

    /**
     * �L���v�V�����p�̃o�C���h�p�X ��Ԃ��܂��B
     * 
     * @return �L���v�V�����p�̃o�C���h�p�X
     */
    public String getCaptionBindPath() {
        return captionBindPath;
    }

    /**
     * �A�C�R���p�X�p�̃o�C���h�p�X ��Ԃ��܂��B
     * 
     * @return �A�C�R���p�X�p�̃o�C���h�p�X
     */
    public String getIconBindPath() {
        return iconBindPath;
    }

    /**
     * �m�[�h�p�̉摜��\���A�C�R���p�X ��Ԃ��܂��B
     * 
     * @return �m�[�h��\���A�C�R���p�X
     */
    public String getNodeIconPath() {
        return nodeIconPath;
    }

    /**
     * �c�[���`�b�v�p�̃o�C���h�p�X ��Ԃ��܂��B
     * 
     * @return �c�[���`�b�v�p�̃o�C���h�p�X
     */
    public String getToolTipBindPath() {
        return toolTipBindPath;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        String toolTip = "";
        Icon icon = null;
        if (value instanceof DefaultMutableTreeNode) {
            Object node = ((DefaultMutableTreeNode) value).getUserObject();
            if (node instanceof VRBindSource) {
                VRBindSource data = (VRBindSource) node;
                try {
                    value = VRBindPathParser.get("CAPTION", data);

                    Object obj = VRBindPathParser.get("ICON", data);
                    if (obj != null) {
                        icon = ACResourceIconPooler.getInstance().getImage(
                                String.valueOf(obj));
                    }
                    obj = VRBindPathParser.get("TOOLTIP_TEXT", data);
                    if (obj != null) {
                        toolTip = String.valueOf(obj);
                    }
                } catch (ParseException ex) {
                }
            }
        }
        Component cmp = super.getTreeCellRendererComponent(tree, value,
                selected, expanded, leaf, row, hasFocus);
        if (cmp instanceof JLabel) {
            ((JLabel) cmp).setIcon(icon);
            ((JLabel) cmp).setToolTipText(toolTip);
        }

        return cmp;
    }

    /**
     * �L���v�V�����p�̃o�C���h�p�X ��ݒ肵�܂��B
     * 
     * @param captionBindPath �L���v�V�����p�̃o�C���h�p�X
     */
    public void setCaptionBindPath(String captionBindPath) {
        this.captionBindPath = captionBindPath;
    }

    /**
     * �A�C�R���p�X�p�̃o�C���h�p�X ��ݒ肵�܂��B
     * 
     * @param iconBindPath �A�C�R���p�X�p�̃o�C���h�p�X
     */
    public void setIconBindPath(String iconBindPath) {
        this.iconBindPath = iconBindPath;
    }

    /**
     * �m�[�h�p�̉摜��\���A�C�R���p�X ��ݒ肵�܂��B
     * 
     * @param nodeIconPath �m�[�h��\���A�C�R���p�X
     */
    public void setNodeIconPath(String nodeIconPath) {
        this.nodeIconPath = nodeIconPath;
    }

    /**
     * �c�[���`�b�v�p�̃o�C���h�p�X ��ݒ肵�܂��B
     * 
     * @param toolTipBindPath �c�[���`�b�v�p�̃o�C���h�p�X
     */
    public void setToolTipBindPath(String toolTipBindPath) {
        this.toolTipBindPath = toolTipBindPath;
    }

}
