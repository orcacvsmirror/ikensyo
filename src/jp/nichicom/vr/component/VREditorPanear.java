package jp.nichicom.vr.component;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.accessibility.AccessibleContext;
import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.event.EventListenerList;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.EditorKit;
import javax.swing.text.JTextComponent;

import jp.nichicom.vr.bind.VRBindable;

/**
 * 
 * VREditorPanearÇ≈Ç∑ÅB
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @see jp.nichicom.vr.bind.VRBindable
 * @see jp.nichicom.vr.component.VRComponentar
 * @see jp.nichicom.vr.component.VRJTextComponentar
 * @see javax.swing.event.HyperlinkListener
 * @version 1.0 2007/10/09
 */
public interface VREditorPanear extends VRBindable, VRJComponentar,
        VRJTextComponentar, HyperlinkListener {
    
    /**
     * Adds a hyperlink listener for notification of any changes, for example
     * when a link is selected and entered.
     *
     * @param listener the listener
     */
    public void addHyperlinkListener(HyperlinkListener listener);
    
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  This is normally called
     * by the currently installed <code>EditorKit</code> if a content type
     * that supports hyperlinks is currently active and there
     * was activity with a link.  The listener list is processed
     * last to first.
     *
     * @param e the event
     * @see EventListenerList
     */
    public void fireHyperlinkUpdate(HyperlinkEvent e);
    
    /**
     * Gets the AccessibleContext associated with this JEditorPane. 
     * For editor panes, the AccessibleContext takes the form of an 
     * AccessibleJEditorPane. 
     * A new AccessibleJEditorPane instance is created if necessary.
     *
     * @return an AccessibleJEditorPane that serves as the 
     *         AccessibleContext of this JEditorPane
     */
    public AccessibleContext getAccessibleContext();
    
    /**
     * Gets the type of content that this editor 
     * is currently set to deal with.  This is 
     * defined to be the type associated with the
     * currently installed <code>EditorKit</code>.
     *
     * @return the content type, <code>null</code> if no editor kit set
     */
    public String getContentType();
    
    /**
     * Fetches the currently installed kit for handling content.
     * <code>createDefaultEditorKit</code> is called to set up a default
     * if necessary.
     *
     * @return the editor kit
     */
    public EditorKit getEditorKit();
    
    /**
     * Fetches the editor kit to use for the given type
     * of content.  This is called when a type is requested
     * that doesn't match the currently installed type.
     * If the component doesn't have an <code>EditorKit</code> registered
     * for the given type, it will try to create an 
     * <code>EditorKit</code> from the default <code>EditorKit</code> registry.
     * If that fails, a <code>PlainEditorKit</code> is used on the
     * assumption that all text documents can be represented
     * as plain text.
     * <p>
     * This method can be reimplemented to use some
     * other kind of type registry.  This can
     * be reimplemented to use the Java Activation
     * Framework, for example.
     *
     * @param type the non-</code>null</code> content type
     * @return the editor kit
     */  
    public EditorKit getEditorKitForContentType(String type);
    
    /**
     * Returns an array of all the <code>HyperLinkListener</code>s added
     * to this JEditorPane with addHyperlinkListener().
     *
     * @return all of the <code>HyperLinkListener</code>s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public HyperlinkListener[] getHyperlinkListeners();
    
    /**
     * Gets the current URL being displayed.  If a URL was 
     * not specified in the creation of the document, this
     * will return <code>null</code>, and relative URL's will not be 
     * resolved.
     *
     * @return the URL, or <code>null</code> if none
     */
    public URL getPage();
    
    /**
     * Returns the preferred size for the <code>JEditorPane</code>.
     * The preferred size for <code>JEditorPane</code> is slightly altered
     * from the preferred size of the superclass.  If the size
     * of the viewport has become smaller than the minimum size
     * of the component, the scrollable definition for tracking
     * width or height will turn to false.  The default viewport
     * layout will give the preferred size, and that is not desired
     * in the case where the scrollable is tracking.  In that case
     * the <em>normal</em> preferred size is adjusted to the
     * minimum size.  This allows things like HTML tables to
     * shrink down to their minimum size and then be laid out at
     * their minimum size, refusing to shrink any further.
     *
     * @return a <code>Dimension</code> containing the preferred size
     */
    public Dimension getPreferredSize();
    
    /**
     * Returns true if a viewport should always force the height of this 
     * <code>Scrollable</code> to match the height of the viewport.  
     * 
     * @return true if a viewport should force the
     *      <code>Scrollable</code>'s height to match its own,
     *      false otherwise
     */
    public boolean getScrollableTracksViewportHeight();
    
    /**
     * Returns true if a viewport should always force the width of this 
     * <code>Scrollable</code> to match the width of the viewport.  
     * 
     * @return true if a viewport should force the Scrollables width to
     * match its own, false otherwise
     */
    public boolean getScrollableTracksViewportWidth();
    
    /**
     * Returns the text contained in this <code>TextComponent</code>
     * in terms of the
     * content type of this editor.  If an exception is thrown while
     * attempting to retrieve the text, <code>null</code> will be returned.
     * This is implemented to call <code>JTextComponent.write</code> with
     * a <code>StringWriter</code>.
     *
     * @return the text
     * @see #setText
     */
    public String getText();
    
    /**
     * Gets the class ID for the UI.
     *
     * @return the string "EditorPaneUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID();
    
    /**
     * This method initializes from a stream.  If the kit is
     * set to be of type <code>HTMLEditorKit</code>, and the
     * <code>desc</code> parameter is an <code>HTMLDocument</code>,
     * then it invokes the <code>HTMLEditorKit</code> to initiate
     * the read. Otherwise it calls the superclass
     * method which loads the model as plain text.
     *
     * @param in the stream from which to read
     * @param desc an object describing the stream
     * @exception IOException as thrown by the stream being
     *      used to initialize
     * @see JTextComponent#read
     * @see #setDocument
     */
    public void read(InputStream in, Object desc) throws IOException;
    
    /**
     * Removes a hyperlink listener.
     *
     * @param listener the listener
     */
    public void removeHyperlinkListener(HyperlinkListener listener);
    
    /**
     * Replaces the currently selected content with new content
     * represented by the given string.  If there is no selection
     * this amounts to an insert of the given text.  If there
     * is no replacement text (i.e. the content string is empty
     * or <code>null</code>) this amounts to a removal of the
     * current selection.  The replacement text will have the
     * attributes currently defined for input.  If the component is not
     * editable, beep and return.  
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see 
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.     
     *
     * @param content  the content to replace the selection with.  This
     *   value can be <code>null</code>
     */
    public void replaceSelection(String content);
    
    /**
     * Scrolls the view to the given reference location
     * (that is, the value returned by the <code>UL.getRef</code>
     * method for the URL being displayed).  By default, this
     * method only knows how to locate a reference in an
     * HTMLDocument.  The implementation calls the
     * <code>scrollRectToVisible</code> method to
     * accomplish the actual scrolling.  If scrolling to a
     * reference location is needed for document types other
     * than HTML, this method should be reimplemented.
     * This method will have no effect if the component
     * is not visible.
     * 
     * @param reference the named location to scroll to
     */
    public void scrollToReference(String reference);
    
    /**
     * Sets the type of content that this editor
     * handles.  This calls <code>getEditorKitForContentType</code>,
     * and then <code>setEditorKit</code> if an editor kit can
     * be successfully located.  This is mostly convenience method
     * that can be used as an alternative to calling 
     * <code>setEditorKit</code> directly.
     * <p>
     * If there is a charset definition specified as a parameter
     * of the content type specification, it will be used when
     * loading input streams using the associated <code>EditorKit</code>.
     * For example if the type is specified as 
     * <code>text/html; charset=EUC-JP</code> the content
     * will be loaded using the <code>EditorKit</code> registered for
     * <code>text/html</code> and the Reader provided to
     * the <code>EditorKit</code> to load unicode into the document will
     * use the <code>EUC-JP</code> charset for translating
     * to unicode.  If the type is not recognized, the content
     * will be loaded using the <code>EditorKit</code> registered
     * for plain text, <code>text/plain</code>.
     * 
     * @param type the non-<code>null</code> mime type for the content editing
     *   support
     * @see #getContentType
     * @beaninfo
     *  description: the type of content
     * @throws NullPointerException if the <code>type</code> parameter
     *      is <code>null</code>
     */
    public void setContentType(String type);
    
    /**
     * Sets the currently installed kit for handling
     * content.  This is the bound property that
     * establishes the content type of the editor.
     * Any old kit is first deinstalled, then if kit is
     * non-<code>null</code>,
     * the new kit is installed, and a default document created for it.
     * A <code>PropertyChange</code> event ("editorKit") is always fired when
     * <code>setEditorKit</code> is called.
     * <p>
     * <em>NOTE: This has the side effect of changing the model,
     * because the <code>EditorKit</code> is the source of how a
     * particular type
     * of content is modeled.  This method will cause <code>setDocument</code>
     * to be called on behalf of the caller to ensure integrity
     * of the internal state.</em>
     * 
     * @param kit the desired editor behavior
     * @see #getEditorKit
     * @beaninfo
     *  description: the currently installed kit for handling content
     *        bound: true
     *       expert: true
     */
    public void setEditorKit(EditorKit kit);
    
    /**
     * Sets the current URL being displayed.
     *
     * @param url the URL for display
     * @exception IOException for a <code>null</code> or invalid URL
     *      specification
     */
    public void setPage(String url) throws IOException;
    
    /**
     * Sets the current URL being displayed.  The content type of the
     * pane is set, and if the editor kit for the pane is
     * non-<code>null</code>, then
     * a new default document is created and the URL is read into it.
     * If the URL contains and reference location, the location will
     * be scrolled to by calling the <code>scrollToReference</code> 
     * method.  If the desired URL is not the one currently being
     * displayed, the <code>getStream</code> method is called to
     * give subclasses control over the stream provided.
     * <p>
     * This may load either synchronously or asynchronously
     * depending upon the document returned by the <code>EditorKit</code>.
     * If the <code>Document</code> is of type
     * <code>AbstractDocument</code> and has a value returned by 
     * <code>AbstractDocument.getAsynchronousLoadPriority</code>
     * that is greater than or equal to zero, the page will be
     * loaded on a separate thread using that priority.
     * <p>
     * If the document is loaded synchronously, it will be
     * filled in with the stream prior to being installed into
     * the editor with a call to <code>setDocument</code>, which
     * is bound and will fire a property change event.  If an
     * <code>IOException</code> is thrown the partially loaded
     * document will
     * be discarded and neither the document or page property
     * change events will be fired.  If the document is 
     * successfully loaded and installed, a view will be
     * built for it by the UI which will then be scrolled if 
     * necessary, and then the page property change event
     * will be fired.
     * <p>
     * If the document is loaded asynchronously, the document
     * will be installed into the editor immediately using a
     * call to <code>setDocument</code> which will fire a 
     * document property change event, then a thread will be
     * created which will begin doing the actual loading.  
     * In this case, the page property change event will not be 
     * fired by the call to this method directly, but rather will be 
     * fired when the thread doing the loading has finished.
     * It will also be fired on the event-dispatch thread.
     * Since the calling thread can not throw an <code>IOException</code>
     * in the event of failure on the other thread, the page 
     * property change event will be fired when the other 
     * thread is done whether the load was successful or not.
     * 
     * @param page the URL of the page
     * @exception IOException for a <code>null</code> or invalid
     *      page specification, or exception from the stream being read
     * @see #getPage
     * @beaninfo
     *  description: the URL used to set content
     *        bound: true
     *       expert: true
     */
    public void setPage(URL page) throws IOException;
    
    /**
     * Sets the text of this <code>TextComponent</code> to the specified
     * content,
     * which is expected to be in the format of the content type of
     * this editor.  For example, if the type is set to <code>text/html</code>
     * the string should be specified in terms of HTML.  
     * <p>
     * This is implemented to remove the contents of the current document,
     * and replace them by parsing the given string using the current
     * <code>EditorKit</code>.  This gives the semantics of the
     * superclass by not changing
     * out the model, while supporting the content type currently set on
     * this component.  The assumption is that the previous content is
     * relatively
     * small, and that the previous content doesn't have side effects.
     * Both of those assumptions can be violated and cause undesirable results.
     * To avoid this, create a new document,
     * <code>getEditorKit().createDefaultDocument()</code>, and replace the
     * existing <code>Document</code> with the new one. You are then assured the
     * previous <code>Document</code> won't have any lingering state.
     * <ol>
     * <li>
     * Leaving the existing model in place means that the old view will be
     * torn down, and a new view created, where replacing the document would
     * avoid the tear down of the old view.
     * <li>
     * Some formats (such as HTML) can install things into the document that
     * can influence future contents.  HTML can have style information embedded
     * that would influence the next content installed unexpectedly.
     * </ol>
     * <p>
     * An alternative way to load this component with a string would be to
     * create a StringReader and call the read method.  In this case the model
     * would be replaced after it was initialized with the contents of the
     * string.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see 
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.     
     *
     * @param t the new text to be set
     * @see #getText
     * @beaninfo
     * description: the text of this component
     */
    public void setText(String t);
    
    /**
     * Directly sets the editor kit to use for the given type.  A 
     * look-and-feel implementation might use this in conjunction
     * with <code>createEditorKitForContentType</code> to install handlers for
     * content types with a look-and-feel bias.
     *
     * @param type the non-<code>null</code> content type
     * @param k the editor kit to be set
     */
    public void setEditorKitForContentType(String type, EditorKit k);
    
}
