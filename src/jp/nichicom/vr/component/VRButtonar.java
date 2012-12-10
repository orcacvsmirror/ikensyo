/** TODO <HEAD> */
package jp.nichicom.vr.component;

import javax.swing.JButton;
import javax.swing.JRootPane;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * バインド機構を実装したボタンインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JButton
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public interface VRButtonar extends VRJComponentar, VRBindable {

    /**
     * Gets the value of the <code>defaultButton</code> property, which if
     * <code>true</code> means that this button is the current default button
     * for its <code>JRootPane</code>. Most look and feels render the default
     * button differently, and may potentially provide bindings to access the
     * default button.
     * 
     * @return the value of the <code>defaultButton</code> property
     * @see JRootPane#setDefaultButton
     * @see #isDefaultCapable
     */
    public boolean isDefaultButton();

    /**
     * Gets the value of the <code>defaultCapable</code> property.
     * 
     * @return the value of the <code>defaultCapable</code> property
     * @see #setDefaultCapable
     * @see #isDefaultButton
     * @see JRootPane#setDefaultButton
     */
    public boolean isDefaultCapable();

    /**
     * Sets the <code>defaultCapable</code> property, which determines whether
     * this button can be made the default button for its root pane. The default
     * value of the <code>defaultCapable</code> property is <code>true</code>
     * unless otherwise specified by the look and feel.
     * 
     * @param defaultCapable <code>true</code> if this button will be capable
     *            of being the default button on the <code>RootPane</code>;
     *            otherwise <code>false</code>
     * @see #isDefaultCapable
     */
    public void setDefaultCapable(boolean defaultCapable);

}
