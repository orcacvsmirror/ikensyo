package jp.nichicom.ac.component.mainmenu;

import javax.swing.tree.TreeNode;

/**
 * メインメニューツリーのノード表示を受け入れるインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see TreeNode
 */
public interface ACMainMenuNodeContainerable {
    public void nodeSelected(TreeNode node);
}
