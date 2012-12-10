package jp.nichicom.ac.component.mainmenu;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;

/**
 * ���C�����j���[�c���[�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see TreeNode
 */

public class ACMainMenuTree extends JTree {
    /**
     * ����̎q�K�w��\���o�C���h�p�X�萔�ł��B
     */
    public static final String DEFAULT_CHILDREN_KEY = "CHILDREN";

    /**
     * �m�[�h�Ƃ��ׂĂ̗t���c���[��ɕ\�����邱�Ƃ�\���c���[���[�h�萔�ł��B
     */
    public final static int TREE_MODE_NODE_AND_ALL_LEAF = 0;

    /**
     * �m�[�h�ƒP��̗t���c���[��ɕ\�����邱�Ƃ�\���c���[���[�h�萔�ł��B
     */
    public final static int TREE_MODE_NODE_AND_SINGLE_LEAF = 1;

    /**
     * �m�[�h�������c���[��ɕ\�����邱�Ƃ�\���c���[���[�h�萔�ł��B
     */
    public final static int TREE_MODE_NODE_ONLY = 2;

    public String childrenKey = DEFAULT_CHILDREN_KEY;

    private List fullModel;

    private int treeMode;

    public ACMainMenuTree() {
        super();
        initComponent();
    }

    public ACMainMenuTree(Hashtable value) {
        super(value);
        initComponent();
    }

    public ACMainMenuTree(Object[] value) {
        super(value);
        initComponent();
    }

    public ACMainMenuTree(TreeModel newModel) {
        super(newModel);
        initComponent();
    }

    public ACMainMenuTree(TreeNode root) {
        super(root);
        initComponent();
    }

    public ACMainMenuTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
        initComponent();
    }

    public ACMainMenuTree(Vector value) {
        super(value);
        initComponent();
    }

    /**
     * �w��m�[�h�̎q�v�f�Ƃ��ăc���[��ǉ����܂��B
     * 
     * @param parent �Ώېe�m�[�h
     * @param children �c���[�f�[�^
     * @throws Exception ������O
     */
    public void addNode(DefaultMutableTreeNode parent, List children)
            throws Exception {
        if ((parent != null) && (children instanceof List)) {
            int end = children.size();
            for (int i = 0; i < end; i++) {
                Object child = children.get(i);
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(
                        child);
                parent.add(childNode);
                if (child instanceof VRBindSource) {
                    Object array = VRBindPathParser.get(childrenKey,
                            (VRBindSource) child);
                    if (array instanceof List) {
                        // ��
                        addNode(childNode, (List) array);
                    }
                }
            }
        }
    }

    /**
     * �q�K�w��\���o�C���h�p�X ��Ԃ��܂��B
     * 
     * @return �q�K�w��\���o�C���h�p�X
     */
    public String getChildrenKey() {
        return childrenKey;
    }

    /**
     * ���j���[�f�[�^�`���̃c���[�f�[�^��Ԃ��܂��B
     * 
     * @return ���j���[�f�[�^�`���̃c���[�f�[�^
     */
    public List getFullModel() {
        return fullModel;
    }

    /**
     * �c���[�\�����[�h��Ԃ��܂��B
     * 
     * @return �c���[�\�����[�h
     */
    public int getTreeMode() {
        return treeMode;
    }

    /**
     * �q�K�w��\���o�C���h�p�X ��ݒ肵�܂��B
     * 
     * @param childrenKey �q�K�w��\���o�C���h�p�X
     */
    public void setChildrenKey(String childrenKey) {
        this.childrenKey = childrenKey;
    }

    /**
     * �Ɩ��c���[���f�����\�z���܂��B
     * 
     * @param newModel �c���[�f�[�^
     * @throws Exception ������O
     */
    public void setModel(List newModel) throws Exception {
        setModel(newModel, null);
    }

    /**
     * �Ɩ��c���[���f�����\�z���܂��B
     * 
     * @param newModel �c���[�f�[�^
     * @param root ���[�g�m�[�h
     * @throws Exception ������O
     */
    public void setModel(List newModel, TreeNode root) throws Exception {
        fullModel = newModel;
        NCMainMenuTreeModel model = new NCMainMenuTreeModel();
        model.setTreeMode(getTreeMode());
        if (!(root instanceof DefaultMutableTreeNode)) {
            root = new NCMainMenuTreeNode(model);
        }
        model.setRoot(root);
        addNode((DefaultMutableTreeNode) root, newModel);
        setModel(model);
    }

    /**
     * �c���[�\�����[�h��ݒ肵�܂��B
     * 
     * @param treeMode �c���[�\�����[�h
     */
    public void setTreeMode(int treeMode) {
        this.treeMode = treeMode;
        if (getModel() instanceof NCMainMenuTreeModel) {
            ((NCMainMenuTreeModel) getModel()).setTreeMode(treeMode);
        }

    }

    /**
     * �R���|�[�l���g�����������܂��B
     */
    protected void initComponent() {
        setRootVisible(false);
        setShowsRootHandles(false);

    }

    /**
     * ���C�����j���[�c���[�p�̃c���[���f���ł��B
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2005/12/01
     * @see TreeNode
     */
    protected class NCMainMenuTreeModel extends DefaultTreeModel {
        private int treeMode;

        public NCMainMenuTreeModel() {
            super(null);
        }

        public NCMainMenuTreeModel(TreeNode root) {
            super(root);
        }

        public int getTreeMode() {
            return treeMode;
        }

        public void setTreeMode(int treeMode) {
            this.treeMode = treeMode;
        }

        protected void setModelNode(TreeNode node) {
            if (node instanceof NCMainMenuTreeNode) {
                ((NCMainMenuTreeNode) node).setModel(this);
            }
            int end = node.getChildCount();
            for (int i = 0; i < end; i++) {
                setModelNode(node.getChildAt(i));
            }
        }
    }

    /**
     * ���C�����j���[�c���[�p�̃m�[�h�f�[�^�ł��B
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2005/12/01
     * @see TreeNode
     */
    protected class NCMainMenuTreeNode extends DefaultMutableTreeNode {
        private NCMainMenuTreeModel model;

        public NCMainMenuTreeNode() {
            super();
        }

        public NCMainMenuTreeNode(NCMainMenuTreeModel model) {
            super();
            setModel(model);
        }

        public NCMainMenuTreeNode(TreeNode root, NCMainMenuTreeModel model) {
            super(root);
            setModel(model);
        }

        public int getChildCount() {
            //TODO SHOW_FLAG�̍l��
            switch (model.getTreeMode()) {
            case TREE_MODE_NODE_ONLY:
                return 0;
            case TREE_MODE_NODE_AND_SINGLE_LEAF: {
                int size = super.getChildCount();
                if (size <= 1) {
                    return 0;
                }
                return size;
            }
            }
            return super.getChildCount();
        }

        public Object getUserObject() {
            switch (model.getTreeMode()) {
            case TREE_MODE_NODE_AND_SINGLE_LEAF: {
                if (super.getChildCount() == 1) {
                    TreeNode child = getChildAt(0);
                    if (child instanceof DefaultMutableTreeNode) {
                        return ((DefaultMutableTreeNode) child).getUserObject();
                    }
                }
            }
            }
            return super.getUserObject();
        }

        public void setModel(NCMainMenuTreeModel model) {
            this.model = model;
        }
    }

}
