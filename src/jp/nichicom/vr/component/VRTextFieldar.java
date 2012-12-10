package jp.nichicom.vr.component;

import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.JTextField;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.text.AbstractVRTextDocument;
import jp.nichicom.vr.text.VRCharType;

/**
 * バインド機構を実装したテキストフィールドインターフェースです。
 * <p>
 * AbstractVRTextDocumentの導入によって入力可能な文字種別や最小・最大文字列長を制限する機能を実装しています。
 * </p>
 * <p>
 * InputSubset指定によるIMEモード制御を実装しています。
 * </p>
 * <p>
 * Format指定による入力値のフォーマット変換処理を実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JTextField
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see InputSubset
 * @see Format
 * @see VRFormatEventListener
 * @see VRCharType
 * @see AbstractVRTextDocument
 */
public interface VRTextFieldar extends VRJTextComponentar, VRBindable,
        VRFormatable {

    /**
     * Returns the horizontal alignment of the text. Valid keys are:
     * <ul>
     * <li><code>JTextField.LEFT</code>
     * <li><code>JTextField.CENTER</code>
     * <li><code>JTextField.RIGHT</code>
     * <li><code>JTextField.LEADING</code>
     * <li><code>JTextField.TRAILING</code>
     * </ul>
     * 
     * @return the horizontal alignment
     */
    int getHorizontalAlignment();

    /**
     * Sets the horizontal alignment of the text. Valid keys are:
     * <ul>
     * <li><code>JTextField.LEFT</code>
     * <li><code>JTextField.CENTER</code>
     * <li><code>JTextField.RIGHT</code>
     * <li><code>JTextField.LEADING</code>
     * <li><code>JTextField.TRAILING</code>
     * </ul>
     * <code>invalidate</code> and <code>repaint</code> are called when the
     * alignment is set, and a <code>PropertyChange</code> event
     * ("horizontalAlignment") is fired.
     * 
     * @param alignment the alignment
     * @exception IllegalArgumentException if <code>alignment</code> is not a
     *                valid key
     */
    void setHorizontalAlignment(int alignment);

    /**
     * Returns the number of columns in this <code>TextField</code>.
     * 
     * @return the number of columns >= 0
     */
    int getColumns();

    /**
     * Sets the number of columns in this <code>TextField</code>, and then
     * invalidate the layout.
     * 
     * @param columns the number of columns >= 0
     * @exception IllegalArgumentException if <code>columns</code> is less
     *                than 0
     */
    void setColumns(int columns);

    /**
     * Sets the command string used for action events.
     * 
     * @param command the command string
     */
    void setActionCommand(String command);

    /**
     * Sets the <code>Action</code> for the <code>ActionEvent</code> source.
     * The new <code>Action</code> replaces any previously set
     * <code>Action</code> but does not affect <code>ActionListeners</code>
     * independently added with <code>addActionListener</code>. If the
     * <code>Action</code> is already a registered <code>ActionListener</code>
     * for the <code>ActionEvent</code> source, it is not re-registered. A
     * side-effect of setting the <code>Action</code> is that the
     * <code>ActionEvent</code> source's properties are immediately set from
     * the values in the <code>Action</code> (performed by the method
     * <code>configurePropertiesFromAction</code>) and subsequently updated
     * as the <code>Action</code>'s properties change (via a
     * <code>PropertyChangeListener</code> created by the method
     * <code>createActionPropertyChangeListener</code>.
     * 
     * @param a the <code>Action</code> for the <code>JTextField</code>, or
     *            <code>null</code>
     * @since 1.3
     * @see Action
     * @see #getAction
     */
    void setAction(Action a);

    /**
     * Returns the currently set <code>Action</code> for this
     * <code>ActionEvent</code> source, or <code>null</code> if no
     * <code>Action</code> is set.
     * 
     * @return the <code>Action</code> for this <code>ActionEvent</code>
     *         source, or <code>null</code>
     * @since 1.3
     * @see Action
     * @see #setAction
     */
    Action getAction();

    /**
     * Processes action events occurring on this textfield by dispatching them
     * to any registered <code>ActionListener</code> objects. This is normally
     * called by the controller registered with textfield.
     */
    void postActionEvent();

    /**
     * Gets the visibility of the text field. This can be adjusted to change the
     * location of the visible area if the size of the field is greater than the
     * area that was allocated to the field.
     * <p>
     * The fields look-and-feel implementation manages the values of the
     * minimum, maximum, and extent properties on the
     * <code>BoundedRangeModel</code>.
     * 
     * @return the visibility
     * @see BoundedRangeModel
     */
    BoundedRangeModel getHorizontalVisibility();

    /**
     * Gets the scroll offset, in pixels.
     * 
     * @return the offset >= 0
     */
    int getScrollOffset();

    /**
     * Sets the scroll offset, in pixels.
     * 
     * @param scrollOffset the offset >= 0
     */
    void setScrollOffset(int scrollOffset);

}
