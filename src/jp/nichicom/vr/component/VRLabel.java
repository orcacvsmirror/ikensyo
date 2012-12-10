/** TODO <HEAD> */
package jp.nichicom.vr.component;

import javax.swing.Icon;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * バインド機構を実装したラベルです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRLabel
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public class VRLabel extends AbstractVRLabel {

    /**
     * Creates a <code>JLabel</code> instance with 
     * no image and with an empty string for the title.
     * The label is centered vertically 
     * in its display area.
     * The label's contents, once set, will be displayed on the leading edge 
     * of the label's display area.
     */
    public VRLabel() {
        super();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image. The
     * label is centered vertically and horizontally in its display area.
     * 
     * @param image The image to be displayed by the label.
     */
    public VRLabel(Icon image) {
        super(image);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public VRLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text. The
     * label is aligned against the leading edge of its display area, and
     * centered vertically.
     * 
     * @param text The text to be displayed by the label.
     */
    public VRLabel(String text) {
        super(text);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text, image,
     * and horizontal alignment. The label is centered vertically in its display
     * area. The text is on the trailing edge of the image.
     * 
     * @param text The text to be displayed by the label.
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public VRLabel(String text, Icon image, int horizontalAlignment) {
        super(text, image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param text The text to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public VRLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }
}