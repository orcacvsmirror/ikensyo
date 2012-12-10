/** TODO <HEAD> */
package jp.nichicom.vr.component;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * バインド機構を実装したチェックボックスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRCheckBox
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public class VRCheckBox extends AbstractVRCheckBox {

    /**
     * Creates an initially unselected check box button with no text, no icon.
     */
    public VRCheckBox() {
        super();
    }

    /**
     * Creates a check box where properties are taken from the Action supplied.
     * 
     * @since 1.3
     */
    public VRCheckBox(Action a) {
        super(a);
    }

    /**
     * Creates an initially unselected check box with an icon.
     * 
     * @param icon the Icon image to display
     */
    public VRCheckBox(Icon icon) {
        super(icon);
    }

    /**
     * Creates a check box with an icon and specifies whether or not it is
     * initially selected.
     * 
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public VRCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * Creates an initially unselected check box with text.
     * 
     * @param text the text of the check box.
     */

    public VRCheckBox(String text) {
        super(text);
    }

    /**
     * Creates a check box with text and specifies whether or not it is
     * initially selected.
     * 
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public VRCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * Creates an initially unselected check box with the specified text and
     * icon.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     */
    public VRCheckBox(String text, Icon icon) {
        super(text, icon);
    }

    /**
     * Creates a check box with text and icon, and specifies whether or not it
     * is initially selected.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public VRCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }
}