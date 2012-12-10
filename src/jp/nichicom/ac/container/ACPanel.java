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
 * VRLayoutを設定したパネルです。
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
     * 水平方向に空ける余白サイズを設定します。
     * 
     * @return 余白サイズ
     */
    public int getHgap() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getHgap();
        }
        return 0;
    }

    /**
     * 水平方向のグリッドの幅を設定します。
     * 
     * @return 水平方向のグリッドの幅
     */
    public int getHgrid() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getHgrid();
        }
        return 0;
    }

    /**
     * ラベルのマージンを設定します。
     * 
     * @return ラベルのマージン
     */
    public int getLabelMargin() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getLabelMargin();
        }
        return 0;
    }

    /**
     * 垂直方向に空ける余白サイズを設定します。
     * 
     * @return 余白サイズ
     */
    public int getVgap() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getVgap();
        }
        return 0;
    }

    /**
     * 自動改行するかを返します。
     * 
     * @return 自動改行する場合はtrue
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
     * 自動改行するかを設定します。
     * 
     * @param autoWrap 自動改行する場合はtrue
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
            // 子も連動する
            followChildEnabled(this, isEnabled());
        }
    }

    /**
     * パネルのEnabledに連動して内包項目のEnabledを設定するか を設定します。
     * 
     * @param followChildEnabled パネルのEnabledに連動して内包項目のEnabledを設定するか
     */
    public void setFollowChildEnabled(boolean followChildEnabled) {
        boolean old = isFollowChildEnabled();
        this.followChildEnabled = followChildEnabled;
        if ((!old) && followChildEnabled) {
            // 新たに連動することにした
            setEnabled(isEnabled());
        }
    }

    /**
     * 水平方向に空ける余白サイズを設定します。
     * 
     * @param hgap 余白サイズ
     */
    public void setHgap(int hgap) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setHgap(hgap);
        }
    }

    /**
     * 水平方向のグリッドの幅を設定します。
     * 
     * @param hgrid 水平方向のグリッドの幅
     */
    public void setHgrid(int hgrid) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setHgrid(hgrid);
        }
    }

    /**
     * ラベルのマージンを設定します。
     * 
     * @param labelMargin ラベルのマージン
     */
    public void setLabelMargin(int labelMargin) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setLabelMargin(labelMargin);
        }
    }

    /**
     * 垂直方向に空ける余白サイズを設定します。
     * 
     * @param vgap 余白サイズ
     */
    public void setVgap(int vgap) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setVgap(vgap);
        }
    }

    /**
     * 水平揃えを設定します。
     * 
     * @param align 水平揃え
     */
    public void setAlignment(int align) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setAlignment(align);
        }
    }

    /**
     * 水平揃えを返します。
     * 
     * @return 水平揃え
     */
    public int getAlignment() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getAlignment();
        }
        return 0;
    }

    /**
     * 垂直揃えを設定します。
     * 
     * @param align 垂直揃え
     */
    public void setVAlignment(int align) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setVAlignment(align);
        }
    }

    /**
     * 垂直揃えを返します。
     * 
     * @return 垂直揃え
     */
    public int getVAlignment() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getVAlignment();
        }
        return 0;
    }

    /**
     * 行内の縦アライメントを設定します。
     * 
     * @param vLineAlign 配置
     */
    public void setVLineAlignment(int align) {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            ((VRLayout) lay).setVLineAlign(align);
        }
    }

    /**
     * 行内の縦アライメントを返します。
     * 
     * @return 行内の縦アライメント
     */
    public int getVLineAlign() {
        LayoutManager lay = getLayout();
        if (lay instanceof VRLayout) {
            return ((VRLayout) lay).getVLineAlign();
        }
        return 0;
    }

    /**
     * 子の有効状態を連動します。
     * 
     * @param container 親コンテナ
     * @param enabled 有効状態
     */
    protected void followChildEnabled(Container container, boolean enabled) {
        int compSize = container.getComponentCount();
        for (int i = 0; i < compSize; i++) {

            Component comp = container.getComponent(i);
            comp.setEnabled(enabled);
            if (comp instanceof ACChildEnabledFollowable) {
                // 子連動設定可能なコンポーネントの場合は処理を委譲済み
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
     * ファイナライズに備えてデッドロック防止処理を実行します。
     */
    public void prepareFinalize() {
        enumPrepareFinalize(this);
    }

    /**
     * ファイナライズに備えて再帰的にデッドロック防止処理を実行します。
     * 
     * @param comp コンポーネント
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
