package jp.nichicom.ac.component;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.vr.component.VRCheckBox;

/**
 * チェックボックスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRCheckBox
 */
public class ACCheckBox extends VRCheckBox {
    /**
     * Creates an initially unselected check box button with no text, no icon.
     */
    public ACCheckBox() {
        super();
    }

    /**
     * Creates an initially unselected check box with an icon.
     * 
     * @param icon the Icon image to display
     */
    public ACCheckBox(Icon icon) {
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

    public ACCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
    }

    /**
     * Creates an initially unselected check box with text.
     * 
     * @param text the text of the check box.
     */

    public ACCheckBox(String text) {
        super(text);
    }

    /**
     * Creates a check box where properties are taken from the Action supplied.
     * 
     * @since 1.3
     */
    public ACCheckBox(Action a) {
        super(a);
    }

    /**
     * Creates a check box with text and specifies whether or not it is
     * initially selected.
     * 
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public ACCheckBox(String text, boolean selected) {
        super(text, selected);
    }

    /**
     * Creates an initially unselected check box with the specified text and
     * icon.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     */
    public ACCheckBox(String text, Icon icon) {
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
    public ACCheckBox(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
    }

    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        if ((size == null) || (size.getHeight() <= 3)) {
            return size;
        }
        // CheckBoxの高さは、標準のTextFieldより3ピクセルほど高いため間引く
        return new Dimension((int) size.getWidth(), (int) size.getHeight() - 3);
    }

    public Dimension getMinimumSize() {
        Dimension size = super.getMinimumSize();
        if ((size == null) || (size.getHeight() <= 3)) {
            return size;
        }
        // CheckBoxの高さは、標準のTextFieldより3ピクセルほど高いため間引く
        return new Dimension((int) size.getWidth(), (int) size.getHeight() - 3);
    }

    public Insets getMargin() {
        Insets margin = super.getMargin();
        if (margin == null) {
            return margin;
        }
        int top = margin.top;
        if (top < 1) {
            top = 0;
        } else {
            top -= 1;
        }
        int bottom = margin.bottom;
        if (bottom < 2) {
            bottom = 0;
        } else {
            bottom -= 2;
        }

        // CheckBoxの高さは、標準のTextFieldより3ピクセルほど高いため間引く
        return new Insets(top, margin.left, bottom, margin.right);
    }

}
