/** TODO <HEAD> */
package jp.nichicom.vr.component;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * バインド機構を実装したボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRButton
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public class VRButton extends AbstractVRButton {

    /**
     * Creates a button with no set text or icon.
     */
    public VRButton() {
        super();
    }

    /**
     * Creates a button where properties are taken from the 
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public VRButton(Action a) {
        super(a);
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public VRButton(Icon icon) {
        super(icon);
    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public VRButton(String text) {
        super(text);
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public VRButton(String text, Icon icon) {
        super(text, icon);
    }
}