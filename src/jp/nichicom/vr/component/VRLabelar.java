/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.Component;
import java.text.Format;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.plaf.LabelUI;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEventListener;

/**
 * バインド機構を実装したラベルインターフェースです。
 * <p>
 * Format指定による入力値のフォーマット変換処理を実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JLabel
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see Format
 * @see VRFormatEventListener
 */
public interface VRLabelar extends VRJComponentar, VRBindable {

    /**
     * フォーマットイベントリスナを追加します。
     * 
     * @param listener フォーマットイベントリスナ
     */
    public void addFormatEventListener(VRFormatEventListener listener);

    /**
     * Returns the value of the disabledIcon property if it's been set, If it
     * hasn't been set and the value of the icon property is an ImageIcon, we
     * compute a "grayed out" version of the icon and update the disabledIcon
     * property with that.
     * 
     * @return The value of the disabledIcon property.
     * @see #setDisabledIcon
     * @see ImageIcon
     */
    public Icon getDisabledIcon();

    /**
     * Return the keycode that indicates a mnemonic key. This property is used
     * when the label is part of a larger component. If the labelFor property of
     * the label is not null, the label will call the requestFocus method of the
     * component specified by the labelFor property when the mnemonic is
     * activated.
     * 
     * @return int value for the mnemonic key
     * @see #getLabelFor
     * @see #setLabelFor
     */
    public int getDisplayedMnemonic();

    /**
     * Returns the character, as an index, that the look and feel should provide
     * decoration for as representing the mnemonic character.
     * 
     * @since 1.4
     * @return index representing mnemonic character
     * @see #setDisplayedMnemonicIndex
     */
    public int getDisplayedMnemonicIndex();

    /**
     * フォーマットを返します。
     * 
     * @return フォーマット
     */
    public Format getFormat();

    /**
     * Returns the alignment of the label's contents along the X axis.
     * 
     * @return The value of the horizontalAlignment property, one of the
     *         following constants defined in <code>SwingConstants</code>:
     *         <code>LEFT</code>, <code>CENTER</code>, <code>RIGHT</code>,
     *         <code>LEADING</code> or <code>TRAILING</code>.
     * @see #setHorizontalAlignment
     * @see SwingConstants
     */
    public int getHorizontalAlignment();

    /**
     * Returns the horizontal position of the label's text, relative to its
     * image.
     * 
     * @return One of the following constants defined in
     *         <code>SwingConstants</code>: <code>LEFT</code>,
     *         <code>CENTER</code>, <code>RIGHT</code>,
     *         <code>LEADING</code> or <code>TRAILING</code>.
     * @see SwingConstants
     */
    public int getHorizontalTextPosition();

    /**
     * Returns the graphic image (glyph, icon) that the label displays.
     * 
     * @return an Icon
     * @see #setIcon
     */
    public Icon getIcon();

    /**
     * Returns the amount of space between the text and the icon displayed in
     * this label.
     * 
     * @return an int equal to the number of pixels between the text and the
     *         icon.
     * @see #setIconTextGap
     */
    public int getIconTextGap();

    /**
     * Get the component this is labelling.
     * 
     * @return the Component this is labelling. Can be null if this does not
     *         label a Component. If the displayedMnemonic property is set and
     *         the labelFor property is also set, the label will call the
     *         requestFocus method of the component specified by the labelFor
     *         property when the mnemonic is activated.
     * @see #getDisplayedMnemonic
     * @see #setDisplayedMnemonic
     */
    public Component getLabelFor();

    /**
     * モデルデータを返します。
     * 
     * @return モデルデータ
     */
    public Object getModel();

    /**
     * Returns the text string that the label displays.
     * 
     * @return a String
     * @see #setText
     */
    public String getText();

    /**
     * Returns the alignment of the label's contents along the Y axis.
     * 
     * @return The value of the verticalAlignment property, one of the following
     *         constants defined in <code>SwingConstants</code>:
     *         <code>TOP</code>, <code>CENTER</code>, or
     *         <code>BOTTOM</code>.
     * @see SwingConstants
     * @see #setVerticalAlignment
     */
    public int getVerticalAlignment();

    /**
     * Returns the vertical position of the label's text, relative to its image.
     * 
     * @return One of the following constants defined in
     *         <code>SwingConstants</code>: <code>TOP</code>,
     *         <code>CENTER</code>, or <code>BOTTOM</code>.
     * @see #setVerticalTextPosition
     * @see SwingConstants
     */
    public int getVerticalTextPosition();

    /**
     * フォーマットイベントリスナを削除します。
     * 
     * @param listener フォーマットイベントリスナ
     */
    public void removeFormatEventListener(VRFormatEventListener listener);

    /**
     * Set the icon to be displayed if this JLabel is "disabled"
     * (JLabel.setEnabled(false)).
     * <p>
     * The default value of this property is null.
     * 
     * @param disabledIcon the Icon to display when the component is disabled
     * @see #getDisabledIcon
     * @see #setEnabled
     */
    public void setDisabledIcon(Icon disabledIcon);

    /**
     * Specifies the displayedMnemonic as a char value.
     * 
     * @param aChar a char specifying the mnemonic to display
     * @see #setDisplayedMnemonic(int)
     */
    public void setDisplayedMnemonic(char aChar);

    /**
     * Specify a keycode that indicates a mnemonic key. This property is used
     * when the label is part of a larger component. If the labelFor property of
     * the label is not null, the label will call the requestFocus method of the
     * component specified by the labelFor property when the mnemonic is
     * activated.
     * 
     * @see #getLabelFor
     * @see #setLabelFor
     */
    public void setDisplayedMnemonic(int key);

    /**
     * Provides a hint to the look and feel as to which character in the text
     * should be decorated to represent the mnemonic. Not all look and feels may
     * support this. A value of -1 indicates either there is no mnemonic, the
     * mnemonic character is not contained in the string, or the developer does
     * not wish the mnemonic to be displayed.
     * <p>
     * The value of this is updated as the properties relating to the mnemonic
     * change (such as the mnemonic itself, the text...). You should only ever
     * have to call this if you do not wish the default character to be
     * underlined. For example, if the text was 'Save As', with a mnemonic of
     * 'a', and you wanted the 'A' to be decorated, as 'Save <u>A</u>s', you
     * would have to invoke <code>setDisplayedMnemonicIndex(5)</code> after
     * invoking <code>setMnemonic(KeyEvent.VK_A)</code>.
     * 
     * @since 1.4
     * @param index Index into the String to underline
     * @exception IllegalArgumentException will be thrown if <code>index</code
     *            is >= length of the text, or < -1
     */
    public void setDisplayedMnemonicIndex(int index)
            throws IllegalArgumentException;

    /**
     * フォーマットを設定します。
     * 
     * @param format フォーマット
     */
    public void setFormat(Format format);

    /**
     * Sets the alignment of the label's contents along the X axis.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param alignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code> (the default for image-only labels),
     *            <code>RIGHT</code>, <code>LEADING</code> (the default for
     *            text-only labels) or <code>TRAILING</code>.
     * @see SwingConstants
     * @see #getHorizontalAlignment
     */
    public void setHorizontalAlignment(int alignment);

    /**
     * Sets the horizontal position of the label's text, relative to its image.
     * 
     * @param textPosition One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code>, or <code>TRAILING</code> (the
     *            default).
     * @exception IllegalArgumentException
     * @see SwingConstants
     */
    public void setHorizontalTextPosition(int textPosition);

    /**
     * Defines the icon this component will display. If the value of icon is
     * null, nothing is displayed.
     * <p>
     * The default value of this property is null.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @see #setVerticalTextPosition
     * @see #setHorizontalTextPosition
     * @see #getIcon
     */
    public void setIcon(Icon icon);

    /**
     * If both the icon and text properties are set, this property defines the
     * space between them.
     * <p>
     * The default value of this property is 4 pixels.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @see #getIconTextGap
     */
    public void setIconTextGap(int iconTextGap);

    /**
     * Set the component this is labelling. Can be null if this does not label a
     * Component. If the displayedMnemonic property is set and the labelFor
     * property is also set, the label will call the requestFocus method of the
     * component specified by the labelFor property when the mnemonic is
     * activated.
     * 
     * @param c the Component this label is for, or null if the label is not the
     *            label for a component
     * @see #getDisplayedMnemonic
     * @see #setDisplayedMnemonic
     */
    public void setLabelFor(Component c);

    /**
     * Defines the single line of text this component will display. If the value
     * of text is null or empty string, nothing is displayed.
     * <p>
     * The default value of this property is null.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @see #setVerticalTextPosition
     * @see #setHorizontalTextPosition
     * @see #setIcon
     */
    public void setText(String text);

    /**
     * Sets the L&F object that renders this component.
     * 
     * @param ui the LabelUI L&F object
     * @see UIDefaults#getUI
     */
    public void setUI(LabelUI ui);

    /**
     * Sets the alignment of the label's contents along the Y axis.
     * <p>
     * The default value of this property is CENTER.
     * 
     * @param alignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>TOP</code>,
     *            <code>CENTER</code> (the default), or <code>BOTTOM</code>.
     * @see SwingConstants
     * @see #getVerticalAlignment
     */
    public void setVerticalAlignment(int alignment);

    /**
     * Sets the vertical position of the label's text, relative to its image.
     * <p>
     * The default value of this property is CENTER.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param textPosition One of the following constants defined in
     *            <code>SwingConstants</code>: <code>TOP</code>,
     *            <code>CENTER</code> (the default), or <code>BOTTOM</code>.
     * @see SwingConstants
     * @see #getVerticalTextPosition
     */
    public void setVerticalTextPosition(int textPosition);

}
