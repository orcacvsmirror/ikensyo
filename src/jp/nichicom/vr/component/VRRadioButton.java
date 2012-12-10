/** TODO <HEAD> */
package jp.nichicom.vr.component;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * バインド機構を実装したラジオボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRRadioButton
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public class VRRadioButton extends AbstractVRRadioButton {

    public VRRadioButton() {
        super();
    }

    public VRRadioButton(Action a) {
        super(a);
    }

    public VRRadioButton(Icon icon) {
        super(icon);
    }

    public VRRadioButton(Icon icon, boolean selected) {
        super(icon, selected);
    }

    public VRRadioButton(String text) {
        super(text);
    }

    public VRRadioButton(String text, boolean selected) {
        super(text, selected);
    }

    public VRRadioButton(String text, Icon icon) {
        super(text, icon);
    }

    public VRRadioButton(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }
}