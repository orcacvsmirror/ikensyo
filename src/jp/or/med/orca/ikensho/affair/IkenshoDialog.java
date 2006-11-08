package jp.or.med.orca.ikensho.affair;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JComponent;
import jp.nichicom.ac.core.ACAffairDialog;

/**
 * 規定ダイアログクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/07/10
 */
public class IkenshoDialog extends ACAffairDialog {// JDialog {

    /**
     * Creates a non-modal dialog without a title and without a specified
     * <code>Frame</code> owner. A shared, hidden frame will be set as the
     * owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public IkenshoDialog() throws HeadlessException {
        super();
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
    public IkenshoDialog(Dialog owner) throws HeadlessException {
        super(owner);
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
    public IkenshoDialog(Dialog owner, boolean modal) throws HeadlessException {
        super(owner, modal);
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
    public IkenshoDialog(Frame owner) throws HeadlessException {
        super(owner);
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
    public IkenshoDialog(Frame owner, boolean modal) throws HeadlessException {
        super(owner, modal);
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
    public IkenshoDialog(Dialog owner, String title) throws HeadlessException {
        super(owner, title);
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
    public IkenshoDialog(Dialog owner, String title, boolean modal)
            throws HeadlessException {
        super(owner, title, modal);
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
    public IkenshoDialog(Frame owner, String title) throws HeadlessException {
        super(owner, title);
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
    public IkenshoDialog(Frame owner, String title, boolean modal)
            throws HeadlessException {
        super(owner, title, modal);
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
    public IkenshoDialog(Dialog owner, String title, boolean modal,
            GraphicsConfiguration gc) throws HeadlessException {
        super(owner, title, modal, gc);
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
    public IkenshoDialog(Frame owner, String title, boolean modal,
            GraphicsConfiguration gc) {
        super(owner, title, modal, gc);
    }
}
