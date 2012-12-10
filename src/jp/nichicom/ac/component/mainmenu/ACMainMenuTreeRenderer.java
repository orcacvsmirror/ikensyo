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
 * メインメニュー用のツリーレンダラです。
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
     * 既定のキャプション用のバインドパスを表す定数です。
     */
    public static final String DEFAULT_CAPTION_BINDPATH = "CAPTION";
    /**
     * 既定のアイコン用のバインドパスを表すバインドパス定数です。
     */
    public static final String DEFAULT_ICON_BINDPATH = "ICON";
    /**
     * 既定のツールチップ用のバインドパスを表すバインドパス定数です。
     */
    public static final String DEFAULT_TOOLTIP_BINDPATH = "TOOLTIP_TEXT";

    private String captionBindPath = DEFAULT_CAPTION_BINDPATH;
    private String iconBindPath = DEFAULT_ICON_BINDPATH;
    private String nodeIconPath;
    private String toolTipBindPath = DEFAULT_TOOLTIP_BINDPATH;

    /**
     * キャプション用のバインドパス を返します。
     * 
     * @return キャプション用のバインドパス
     */
    public String getCaptionBindPath() {
        return captionBindPath;
    }

    /**
     * アイコンパス用のバインドパス を返します。
     * 
     * @return アイコンパス用のバインドパス
     */
    public String getIconBindPath() {
        return iconBindPath;
    }

    /**
     * ノード用の画像を表すアイコンパス を返します。
     * 
     * @return ノードを表すアイコンパス
     */
    public String getNodeIconPath() {
        return nodeIconPath;
    }

    /**
     * ツールチップ用のバインドパス を返します。
     * 
     * @return ツールチップ用のバインドパス
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
     * キャプション用のバインドパス を設定します。
     * 
     * @param captionBindPath キャプション用のバインドパス
     */
    public void setCaptionBindPath(String captionBindPath) {
        this.captionBindPath = captionBindPath;
    }

    /**
     * アイコンパス用のバインドパス を設定します。
     * 
     * @param iconBindPath アイコンパス用のバインドパス
     */
    public void setIconBindPath(String iconBindPath) {
        this.iconBindPath = iconBindPath;
    }

    /**
     * ノード用の画像を表すアイコンパス を設定します。
     * 
     * @param nodeIconPath ノードを表すアイコンパス
     */
    public void setNodeIconPath(String nodeIconPath) {
        this.nodeIconPath = nodeIconPath;
    }

    /**
     * ツールチップ用のバインドパス を設定します。
     * 
     * @param toolTipBindPath ツールチップ用のバインドパス
     */
    public void setToolTipBindPath(String toolTipBindPath) {
        this.toolTipBindPath = toolTipBindPath;
    }

}
