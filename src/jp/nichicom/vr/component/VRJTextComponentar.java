package jp.nichicom.vr.component;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.Action;
import javax.swing.TransferHandler;
import javax.swing.event.CaretListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.NavigationFilter;
import javax.swing.text.PlainDocument;

/**
 * テキストコンポーネントインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JTextComponent
 * @see VRJComponentar
 */
public interface VRJTextComponentar extends VRJComponentar {

    /**
     * Adds a caret listener for notification of any changes to the caret.
     * 
     * @param listener the listener to be added
     * @see javax.swing.event.CaretEvent
     */
    public void addCaretListener(CaretListener listener);

    /**
     * Transfers the currently selected range in the associated text model to
     * the system clipboard, leaving the contents in the text model. The current
     * selection remains intact. Does nothing for <code>null</code>
     * selections.
     * 
     * @see java.awt.Toolkit#getSystemClipboard
     * @see java.awt.datatransfer.Clipboard
     */
    public void copy();

    /**
     * Transfers the currently selected range in the associated text model to
     * the system clipboard, removing the contents from the model. The current
     * selection is reset. Does nothing for <code>null</code> selections.
     * 
     * @see java.awt.Toolkit#getSystemClipboard
     * @see java.awt.datatransfer.Clipboard
     */
    public void cut();

    /**
     * Fetches the command list for the editor. This is the list of commands
     * supported by the plugged-in UI augmented by the collection of commands
     * that the editor itself supports. These are useful for binding to events,
     * such as in a keymap.
     * 
     * @return the command list
     */
    public Action[] getActions();

    /**
     * Fetches the caret that allows text-oriented navigation over the view.
     * 
     * @return the caret
     */
    public Caret getCaret();

    /**
     * Fetches the current color used to render the caret.
     * 
     * @return the color
     */
    public Color getCaretColor();

    /**
     * Returns an array of all the caret listeners registered on this text
     * component.
     * 
     * @return all of this component's <code>CaretListener</code>s or an
     *         empty array if no caret listeners are currently registered
     * @see #addCaretListener
     * @see #removeCaretListener
     * @since 1.4
     */
    public CaretListener[] getCaretListeners();

    /**
     * Returns the position of the text insertion caret for the text component.
     * 
     * @return the position of the text insertion caret for the text component >=
     *         0
     */
    public int getCaretPosition();

    /**
     * Fetches the current color used to render the selected text.
     * 
     * @return the color
     */
    public Color getDisabledTextColor();

    /**
     * Fetches the model associated with the editor. This is primarily for the
     * UI to get at the minimal amount of state required to be a text editor.
     * Subclasses will return the actual type of the model which will typically
     * be something that extends Document.
     * 
     * @return the model
     */
    public Document getDocument();

    /**
     * Gets the <code>dragEnabled</code> property.
     * 
     * @return the value of the <code>dragEnabled</code> property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled();

    /**
     * Returns the key accelerator that will cause the receiving text component
     * to get the focus. Return '\0' if no focus accelerator has been set.
     * 
     * @return the key
     */
    public char getFocusAccelerator();

    /**
     * Fetches the object responsible for making highlights.
     * 
     * @return the highlighter
     */
    public Highlighter getHighlighter();

    /**
     * Fetches the keymap currently active in this text component.
     * 
     * @return the keymap
     */
    public Keymap getKeymap();

    /**
     * Returns the margin between the text component's border and its text.
     * 
     * @return the margin
     */
    public Insets getMargin();

    /**
     * Returns the <code>NavigationFilter</code>.
     * <code>NavigationFilter</code> is used by <code>DefaultCaret</code>
     * and the default cursor movement actions as a way to restrict the cursor
     * movement. A null return value implies the cursor movement and selection
     * should not be restricted.
     * 
     * @since 1.4
     * @return the NavigationFilter
     */
    public NavigationFilter getNavigationFilter();

    /**
     * Returns the selected text contained in this <code>TextComponent</code>.
     * If the selection is <code>null</code> or the document empty, returns
     * <code>null</code>.
     * 
     * @return the text
     * @exception IllegalArgumentException if the selection doesn't have a valid
     *                mapping into the document for some reason
     * @see #setText
     */
    public String getSelectedText();

    /**
     * Fetches the current color used to render the selected text.
     * 
     * @return the color
     */
    public Color getSelectedTextColor();

    /**
     * Fetches the current color used to render the selection.
     * 
     * @return the color
     */
    public Color getSelectionColor();

    /**
     * Returns the selected text's end position. Return 0 if the document is
     * empty, or the value of dot if there is no selection.
     * 
     * @return the end position >= 0
     */
    public int getSelectionEnd();

    /**
     * Returns the selected text's start position. Return 0 for an empty
     * document, or the value of dot if no selection.
     * 
     * @return the start position >= 0
     */
    public int getSelectionStart();

    /**
     * Returns the text contained in this <code>TextComponent</code>. If the
     * underlying document is <code>null</code>, will give a
     * <code>NullPointerException</code>.
     * 
     * @return the text
     * @exception NullPointerException if the document is <code>null</code>
     * @see #setText
     */
    public String getText();

    /**
     * Fetches a portion of the text represented by the component. Returns an
     * empty string if length is 0.
     * 
     * @param offs the offset >= 0
     * @param len the length >= 0
     * @return the text
     * @exception BadLocationException if the offset or length are invalid
     */
    public String getText(int offs, int len) throws BadLocationException;

    /**
     * Returns the boolean indicating whether this <code>TextComponent</code>
     * is editable or not.
     * 
     * @return the boolean value
     * @see #setEditable
     */
    public boolean isEditable();

    /**
     * Converts the given location in the model to a place in the view
     * coordinate system. The component must have a positive size for this
     * translation to be computed (i.e. layout cannot be computed until the
     * component has been sized). The component does not have to be visible or
     * painted.
     * 
     * @param pos the position >= 0
     * @return the coordinates as a rectangle, with (r.x, r.y) as the location
     *         in the coordinate system, or null if the component does not yet
     *         have a positive size.
     * @exception BadLocationException if the given position does not represent
     *                a valid location in the associated document
     * @see TextUI#modelToView
     */
    public Rectangle modelToView(int pos) throws BadLocationException;

    /**
     * Moves the caret to a new position, leaving behind a mark defined by the
     * last time <code>setCaretPosition</code> was called. This forms a
     * selection. If the document is <code>null</code>, does nothing. The
     * position must be between 0 and the length of the component's text or else
     * an exception is thrown.
     * 
     * @param pos the position
     * @exception IllegalArgumentException if the value supplied for
     *                <code>position</code> is less than zero or greater than
     *                the component's text length
     * @see #setCaretPosition
     */
    public void moveCaretPosition(int pos);

    /**
     * Transfers the contents of the system clipboard into the associated text
     * model. If there is a selection in the associated view, it is replaced
     * with the contents of the clipboard. If there is no selection, the
     * clipboard contents are inserted in front of the current insert position
     * in the associated view. If the clipboard is empty, does nothing.
     * 
     * @see #replaceSelection
     * @see java.awt.Toolkit#getSystemClipboard
     * @see java.awt.datatransfer.Clipboard
     */
    public void paste();

    /**
     * Initializes from a stream. This creates a model of the type appropriate
     * for the component and initializes the model from the stream. By default
     * this will load the model as plain text. Previous contents of the model
     * are discarded.
     * 
     * @param in the stream to read from
     * @param desc an object describing the stream; this might be a string, a
     *            File, a URL, etc. Some kinds of documents (such as html for
     *            example) might be able to make use of this information; if
     *            non-<code>null</code>, it is added as a property of the
     *            document
     * @exception IOException as thrown by the stream being used to initialize
     * @see EditorKit#createDefaultDocument
     * @see #setDocument
     * @see PlainDocument
     */
    public void read(Reader in, Object desc) throws IOException;

    /**
     * Removes a caret listener.
     * 
     * @param listener the listener to be removed
     * @see javax.swing.event.CaretEvent
     */
    public void removeCaretListener(CaretListener listener);

    public void removeNotify();

    /**
     * Replaces the currently selected content with new content represented by
     * the given string. If there is no selection this amounts to an insert of
     * the given text. If there is no replacement text this amounts to a removal
     * of the current selection.
     * <p>
     * This is the method that is used by the default implementation of the
     * action for inserting content that gets bound to the keymap actions.
     * <p>
     * This method is thread safe, although most Swing methods are not. Please
     * see <A
     * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     * 
     * @param content the content to replace the selection with
     */
    public void replaceSelection(String content);

    /**
     * Selects the text between the specified start and end positions.
     * <p>
     * This method sets the start and end positions of the selected text,
     * enforcing the restriction that the start position must be greater than or
     * equal to zero. The end position must be greater than or equal to the
     * start position, and less than or equal to the length of the text
     * component's text.
     * <p>
     * If the caller supplies values that are inconsistent or out of bounds, the
     * method enforces these constraints silently, and without failure.
     * Specifically, if the start position or end position is greater than the
     * length of the text, it is reset to equal the text length. If the start
     * position is less than zero, it is reset to zero, and if the end position
     * is less than the start position, it is reset to the start position.
     * <p>
     * This call is provided for backward compatibility. It is routed to a call
     * to <code>setCaretPosition</code> followed by a call to
     * <code>moveCaretPosition</code>. The preferred way to manage selection
     * is by calling those methods directly.
     * 
     * @param selectionStart the start position of the text
     * @param selectionEnd the end position of the text
     * @see #setCaretPosition
     * @see #moveCaretPosition
     */
    public void select(int selectionStart, int selectionEnd);

    /**
     * Selects all the text in the <code>TextComponent</code>. Does nothing
     * on a <code>null</code> or empty document.
     */
    public void selectAll();

    /**
     * Sets the caret to be used. By default this will be set by the UI that
     * gets installed. This can be changed to a custom caret if desired. Setting
     * the caret results in a PropertyChange event ("caret") being fired.
     * 
     * @param c the caret
     * @see #getCaret
     */
    public void setCaret(Caret c);

    /**
     * Sets the current color used to render the caret. Setting to
     * <code>null</code> effectively restores the default color. Setting the
     * color results in a PropertyChange event ("caretColor") being fired.
     * 
     * @param c the color
     * @see #getCaretColor
     */
    public void setCaretColor(Color c);

    /**
     * Sets the position of the text insertion caret for the
     * <code>TextComponent</code>. Note that the caret tracks change, so this
     * may move if the underlying text of the component is changed. If the
     * document is <code>null</code>, does nothing. The position must be
     * between 0 and the length of the component's text or else an exception is
     * thrown.
     * 
     * @param position the position
     * @exception IllegalArgumentException if the value supplied for
     *                <code>position</code> is less than zero or greater than
     *                the component's text length
     */
    public void setCaretPosition(int position);

    /**
     * Sets the current color used to render the disabled text. Setting the
     * color fires off a PropertyChange event ("disabledTextColor").
     * 
     * @param c the color
     * @see #getDisabledTextColor
     */
    public void setDisabledTextColor(Color c);

    /**
     * Associates the editor with a text document. The currently registered
     * factory is used to build a view for the document, which gets displayed by
     * the editor after revalidation. A PropertyChange event ("document") is
     * propagated to each listener.
     * 
     * @param doc the document to display/edit
     * @see #getDocument
     */
    public void setDocument(Document doc);

    /**
     * Sets the <code>dragEnabled</code> property, which must be
     * <code>true</code> to enable automatic drag handling (the first part of
     * drag and drop) on this component. The <code>transferHandler</code>
     * property needs to be set to a non-<code>null</code> value for the drag
     * to do anything. The default value of the <code>dragEnabled</code>
     * property is <code>false</code>.
     * <p>
     * When automatic drag handling is enabled, most look and feels begin a
     * drag-and-drop operation whenever the user presses the mouse button over a
     * selection and then moves the mouse a few pixels. Setting this property to
     * <code>true</code> can therefore have a subtle effect on how selections
     * behave.
     * <p>
     * Some look and feels might not support automatic drag and drop; they will
     * ignore this property. You can work around such look and feels by
     * modifying the component to directly call the <code>exportAsDrag</code>
     * method of a <code>TransferHandler</code>.
     * 
     * @param b the value to set the <code>dragEnabled</code> property to
     * @exception HeadlessException if <code>b</code> is <code>true</code>
     *                and <code>GraphicsEnvironment.isHeadless()</code>
     *                returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.4
     */
    public void setDragEnabled(boolean b);

    /**
     * Sets the specified boolean to indicate whether or not this
     * <code>TextComponent</code> should be editable. A PropertyChange event
     * ("editable") is fired when the state is changed.
     * 
     * @param b the boolean to be set
     * @see #isEditable
     */
    public void setEditable(boolean b);

    /**
     * Sets the key accelerator that will cause the receiving text component to
     * get the focus. The accelerator will be the key combination of the
     * <em>alt</em> key and the character given (converted to upper case). By
     * default, there is no focus accelerator key. Any previous key accelerator
     * setting will be superseded. A '\0' key setting will be registered, and
     * has the effect of turning off the focus accelerator. When the new key is
     * set, a PropertyChange event (FOCUS_ACCELERATOR_KEY) will be fired.
     * 
     * @param aKey the key
     * @see #getFocusAccelerator
     */
    public void setFocusAccelerator(char aKey);

    /**
     * Sets the highlighter to be used. By default this will be set by the UI
     * that gets installed. This can be changed to a custom highlighter if
     * desired. The highlighter can be set to <code>null</code> to disable it.
     * A PropertyChange event ("highlighter") is fired when a new highlighter is
     * installed.
     * 
     * @param h the highlighter
     * @see #getHighlighter
     */
    public void setHighlighter(Highlighter h);

    /**
     * Sets the keymap to use for binding events to actions. Setting to
     * <code>null</code> effectively disables keyboard input. A PropertyChange
     * event ("keymap") is fired when a new keymap is installed.
     * 
     * @param map the keymap
     * @see #getKeymap
     */
    public void setKeymap(Keymap map);

    /**
     * Sets margin space between the text component's border and its text. The
     * text component's default <code>Border</code> object will use this value
     * to create the proper margin. However, if a non-default border is set on
     * the text component, it is that <code>Border</code> object's
     * responsibility to create the appropriate margin space (else this property
     * will effectively be ignored). This causes a redraw of the component. A
     * PropertyChange event ("margin") is sent to all listeners.
     * 
     * @param m the space between the border and the text
     */
    public void setMargin(Insets m);

    /**
     * Sets the <code>NavigationFilter</code>. <code>NavigationFilter</code>
     * is used by <code>DefaultCaret</code> and the default cursor movement
     * actions as a way to restrict the cursor movement.
     * 
     * @since 1.4
     */
    public void setNavigationFilter(NavigationFilter filter);

    /**
     * Sets the current color used to render the selected text. Setting the
     * color to <code>null</code> is the same as <code>Color.black</code>.
     * Setting the color results in a PropertyChange event ("selectedTextColor")
     * being fired.
     * 
     * @param c the color
     * @see #getSelectedTextColor
     */
    public void setSelectedTextColor(Color c);

    /**
     * Sets the current color used to render the selection. Setting the color to
     * <code>null</code> is the same as setting <code>Color.white</code>.
     * Setting the color results in a PropertyChange event ("selectionColor").
     * 
     * @param c the color
     * @see #getSelectionColor
     */
    public void setSelectionColor(Color c);

    /**
     * Sets the selection end to the specified position. The new end point is
     * constrained to be at or after the current selection start.
     * <p>
     * This is available for backward compatibility to code that called this
     * method on <code>java.awt.TextComponent</code>. This is implemented to
     * forward to the <code>Caret</code> implementation which is where the
     * actual selection is maintained.
     * 
     * @param selectionEnd the end position of the text >= 0
     */
    public void setSelectionEnd(int selectionEnd);

    /**
     * Sets the selection start to the specified position. The new starting
     * point is constrained to be before or at the current selection end.
     * <p>
     * This is available for backward compatibility to code that called this
     * method on <code>java.awt.TextComponent</code>. This is implemented to
     * forward to the <code>Caret</code> implementation which is where the
     * actual selection is maintained.
     * 
     * @param selectionStart the start position of the text >= 0
     */
    public void setSelectionStart(int selectionStart);

    /**
     * Sets the text of this <code>TextComponent</code> to the specified text.
     * If the text is <code>null</code> or empty, has the effect of simply
     * deleting the old text. When text has been inserted, the resulting caret
     * location is determined by the implementation of the caret class.
     * <p>
     * This method is thread safe, although most Swing methods are not. Please
     * see <A
     * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     * 
     * @param t the new text to be set
     * @see #getText
     * @see DefaultCaret
     */
    public void setText(String t);

    /**
     * Converts the given place in the view coordinate system to the nearest
     * representative location in the model. The component must have a positive
     * size for this translation to be computed (i.e. layout cannot be computed
     * until the component has been sized). The component does not have to be
     * visible or painted.
     * 
     * @param pt the location in the view to translate
     * @return the offset >= 0 from the start of the document, or -1 if the
     *         component does not yet have a positive size.
     * @see TextUI#viewToModel
     */
    public int viewToModel(Point pt);

    /**
     * Stores the contents of the model into the given stream. By default this
     * will store the model as plain text.
     * 
     * @param out the output stream
     * @exception IOException on any I/O error
     */
    public void write(Writer out) throws IOException;
}
