package jp.nichicom.ac.core;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import jp.nichicom.ac.container.ACPanel;

/**
 * 業務ダイアログです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JDialog
 */

public class ACAffairDialog extends JDialog {

    /**
     * コンストラクタです。
     * <p>
     * システム規定のフレームを親にしたモーダルダイアログとして生成します。
     * </p>
     * 
     * @throws HeadlessException 処理例外
     */
    public ACAffairDialog() throws HeadlessException {
        super(ACFrame.getInstance(), true);
        initComponent();
    }

    /**
     * Creates a non-modal dialog without a title with the specified
     * <code>Dialog</code> as its owner.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     *            displayed
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Dialog owner) throws HeadlessException {
        super(owner);
        initComponent();
    }

    /**
     * Creates a modal or non-modal dialog without a title and with the
     * specified owner dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     *            displayed
     * @param modal true for a modal dialog, false for one that allows other
     *            windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Dialog owner, boolean modal) throws HeadlessException {
        super(owner, modal);
        initComponent();
    }

    /**
     * Creates a non-modal dialog without a title with the specified
     * <code>Frame</code> as its owner. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of
     * the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Frame owner) throws HeadlessException {
        super(owner);
        initComponent();
    }

    /**
     * Creates a modal or non-modal dialog without a title and with the
     * specified owner <code>Frame</code>. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of
     * the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param modal true for a modal dialog, false for one that allows others
     *            windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Frame owner, boolean modal) throws HeadlessException {
        super(owner, modal);
        initComponent();
    }

    /**
     * Creates a non-modal dialog with the specified title and with the
     * specified owner dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     *            displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Dialog owner, String title) throws HeadlessException {
        super(owner, title);
        initComponent();
    }

    /**
     * Creates a modal or non-modal dialog with the specified title and the
     * specified owner frame.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the non-null <code>Dialog</code> from which the dialog is
     *            displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @param modal true for a modal dialog, false for one that allows other
     *            windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Dialog owner, String title, boolean modal)
            throws HeadlessException {
        super(owner, title, modal);
        initComponent();
    }

    /**
     * Creates a non-modal dialog with the specified title and with the
     * specified owner frame. If <code>owner</code> is <code>null</code>, a
     * shared, hidden frame will be set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Frame owner, String title) throws HeadlessException {
        super(owner, title);
        initComponent();
    }

    /**
     * Creates a modal or non-modal dialog with the specified title and the
     * specified owner <code>Frame</code>. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of
     * this dialog. All constructors defer to this one.
     * <p>
     * NOTE: Any popup components (<code>JComboBox</code>,
     * <code>JPopupMenu</code>, <code>JMenuBar</code>) created within a
     * modal dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @param modal true for a modal dialog, false for one that allows other
     *            windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public ACAffairDialog(Frame owner, String title, boolean modal)
            throws HeadlessException {
        super(owner, title, modal);
        initComponent();
    }

    /**
     * Creates a modal or non-modal dialog with the specified title, owner
     * <code>Dialog</code>, and <code>GraphicsConfiguration</code>.
     * <p>
     * NOTE: Any popup components (<code>JComboBox</code>,
     * <code>JPopupMenu</code>, <code>JMenuBar</code>) created within a
     * modal dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Dialog</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @param modal true for a modal dialog, false for one that allows other
     *            windows to be active at the same time
     * @param gc the <code>GraphicsConfiguration</code> of the target screen
     *            device. If <code>gc</code> is <code>null</code>, the same
     *            <code>GraphicsConfiguration</code> as the owning Dialog is
     *            used.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless()
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale returns true.
     * @since 1.4
     */
    public ACAffairDialog(Dialog owner, String title, boolean modal,
            GraphicsConfiguration p3) throws HeadlessException {
        super(owner, title, modal, p3);
        initComponent();
    }

    /**
     * Creates a modal or non-modal dialog with the specified title, owner
     * <code>Frame</code>, and <code>GraphicsConfiguration</code>.
     * <p>
     * NOTE: Any popup components (<code>JComboBox</code>,
     * <code>JPopupMenu</code>, <code>JMenuBar</code>) created within a
     * modal dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @param modal true for a modal dialog, false for one that allows other
     *            windows to be active at the same time
     * @param gc the <code>GraphicsConfiguration</code> of the target screen
     *            device. If <code>gc</code> is <code>null</code>, the same
     *            <code>GraphicsConfiguration</code> as the owning Frame is
     *            used.
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     * @since 1.4
     */
    public ACAffairDialog(Frame owner, String title, boolean modal,
            GraphicsConfiguration p3) {
        super(owner, title, modal, p3);
        initComponent();
    }

    /**
     * ウィンドウタイトルを設定します。
     * 
     * @param title ウィンドウタイトル
     */
    public void setWindowTitle(String title) {
        setTitle(title);
    }

    /**
     * ウィンドウタイトルを返します。
     * 
     * @return ウィンドウタイトル
     */
    public String getWindowTitle() {
        return getTitle();
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

    public void dispose() {
        ACDialogChaine.getInstance().remove(this);
        prepareFinalize();
        super.dispose();
    }

    /**
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                try{
                getContentPane().invalidate();
                getContentPane().validate();
                Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(
                        new ComponentEvent(getContentPane(),
                                ComponentEvent.COMPONENT_RESIZED));
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        ACDialogChaine.getInstance().add(this);
    }
}