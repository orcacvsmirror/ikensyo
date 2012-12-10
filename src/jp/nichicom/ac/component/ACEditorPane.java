package jp.nichicom.ac.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.text.ParseException;

import javax.security.auth.Refreshable;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.TransferHandler;
import javax.swing.event.CaretListener;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.NavigationFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.html.HTMLDocument;

import jp.nichicom.ac.container.AbstractACScrollPane;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.VREditorPane;
import jp.nichicom.vr.component.VREditorPanear;
/**
 * 
 * ACEditorPaneです。
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 * @see jp.nichicom.ac.container.AbstractACScrollPane
 * @see jp.nichicom.vr.component.VREditorPanear
 * @see jp.nichicom.vr.bind.event.VRBindEventListener
 * @see java.awt.event.FocusListener
 * @see javax.swing.event.HyperlinkListener
 * @see java.awt.event.KeyListener
 * @see java.awt.event.MouseMotionListener
 * @see java.awt.event.MouseWheelListener
 * @see java.awt.event.MouseListener
 */
public class ACEditorPane extends AbstractACScrollPane implements
        VREditorPanear, VRBindEventListener, FocusListener, KeyListener,
        MouseMotionListener, MouseWheelListener, MouseListener {

    private VREditorPane mainContents;
    
    /**
     * Creates an empty (no viewport view) <code>JScrollPane</code> where both
     * horizontal and vertical scrollbars appear when needed.
     */
    public ACEditorPane() {
        super();
    }

    /**
     * Creates a <code>JScrollPane</code> that displays the contents of the
     * specified component, where both horizontal and vertical scrollbars appear
     * whenever the component's contents are larger than the view.
     * 
     * @see #setViewportView
     * @param view the component to display in the scrollpane's viewport
     */
    public ACEditorPane(Component view) {
        super(view);
    }

    /**
     * Creates a <code>JScrollPane</code> that displays the view component in
     * a viewport whose view position can be controlled with a pair of
     * scrollbars. The scrollbar policies specify when the scrollbars are
     * displayed, For example, if <code>vsbPolicy</code> is
     * <code>VERTICAL_SCROLLBAR_AS_NEEDED</code> then the vertical scrollbar
     * only appears if the view doesn't fit vertically. The available policy
     * settings are listed at {@link #setVerticalScrollBarPolicy} and
     * {@link #setHorizontalScrollBarPolicy}.
     * 
     * @see #setViewportView
     * @param view the component to display in the scrollpanes viewport
     * @param vsbPolicy an integer that specifies the vertical scrollbar policy
     * @param hsbPolicy an integer that specifies the horizontal scrollbar
     *            policy
     */
    public ACEditorPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
        
    }

    /**
     * Creates a <code>JEditorPane</code> based on a specified URL for input.
     *
     * @param initialPage the URL
     * @exception IOException if the URL is <code>null</code>
     *      or cannot be accessed
     */
    public ACEditorPane(String url) throws IOException {
        this();
        getMainContent().setPage(url);
        
    }

    /**
     * Creates a <code>JEditorPane</code> based on a string containing
     * a URL specification.
     *
     * @param url the URL
     * @exception IOException if the URL is <code>null</code> or
     *      cannot be accessed
     */
    public ACEditorPane(URL initialPage) throws IOException {
        this();
        getMainContent().setPage(initialPage);
    }

    /**
     * Creates a <code>JEditorPane</code> that has been initialized
     * to the given text.  This is a convenience constructor that calls the
     * <code>setContentType</code> and <code>setText</code> methods.
     *
     * @param type mime type of the given text
     * @param text the text to initialize with
     * @exception NullPointerException if the <code>type</code> parameter
     *      is <code>null</code>
     */
    public ACEditorPane(String type, String text) {
        this();
        getMainContent().setContentType(type);
        getMainContent().setText(text);
    }
    
    /**
     * 投影項目を生成して返します。
     * 
     * @return 投影項目
     */
    protected JComponent createJView() {
        return getMainContent();
    }

    /**
     * バインドイベントリスナを追加します。
     * 
     * @param listener バインドイベントリスナ
     */
    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    /**
     * 参照先ソースの値をコントロールに流し込みます。
     * 
     * @throws ParseException 解析例外
     */
    public void bindSource() throws ParseException {
        getMainContent().bindSource();
        
    }

    public Object createSource() {
        return getMainContent().createSource();
        
    }

    /**
     * バインドパスを返します。
     * 
     * @return バインドパス
     */
    public String getBindPath() {
        return getMainContent().getBindPath();
    }

    public VRBindSource getSource() {
        return getMainContent().getSource();
    }

    /**
     * 自動適用するかを返します。
     * 
     * @return 自動適用するか
     */
    public boolean isAutoApplySource() {
        return getMainContent().isAutoApplySource();
        
    }

    /**
     * バインドイベントリスナを削除します。
     * 
     * @param listener バインドイベントリスナ
     */
    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class,listener);
        
    }

    /**
     * 自動適用するかを設定します。
     * 
     * @param autoApplySource 自動適用するか
     */
    public void setAutoApplySource(boolean autoApplySource) {
        getMainContent().setAutoApplySource(autoApplySource);
        
    }
    
    /**
     * バインドパスを設定します。
     * 
     * @param bindPath バインドパス
     */
    public void setBindPath(String bindPath) {
        getMainContent().setBindPath(bindPath);
        
    }

    /**
     * 参照先ソースを設定します。
     * 
     * @param source 参照先ソース
     */
    public void setSource(VRBindSource source) {
        getMainContent().setSource(source);
        
    }

    /**
     * Called when a hypertext link is updated.
     *
     * @param e the event responsible for the update
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
        fireHyperlinkUpdate(e);
        
    }
    
    /**
     * ハイパーリンク関連のイベントです。
     * @param e
     */
    public void fireHyperlinkUpdate(HyperlinkEvent e){
        if(e.getSource() == getMainContent()){
            // 子のイベントを置換する
            e = new HyperlinkEvent(e.getSource(),e.getEventType(),e.getURL());
        }
        
        HyperlinkListener listeners[] = getHyperlinkListeners();
        for(int i = 0;i < listeners.length; i++){
            listeners[i].hyperlinkUpdate(e);
        }

    }
    
    /**
     * ハイパーリンクリスナーイベントを返します。
     * 
     * @return ハイパーリンクイベントリスナ
     */
    public synchronized HyperlinkListener[] getHyperlinkListeners() {
        return (HyperlinkListener[]) (getListeners(HyperlinkListener.class));
        
    }
    
    /**
     * Adds a hyperlink listener for notification of any changes, for example
     * when a link is selected and entered.
     *
     * @param listener the listener
     */
    public synchronized void addHyperlinkListener(HyperlinkListener listener) {
        getMainContent().addHyperlinkListener(listener);
    }
    
    /**
     * Removes a hyperlink listener.
     *
     * @param listener the listener
     */
    public synchronized void removeHyperlinkListener(HyperlinkListener listener) {
        getMainContent().removeHyperlinkListener(listener);
    }

    /**
     * 内包しているコンポーネントを取得します。
     * @return
     */
    protected VREditorPane getMainContent() {
        if(mainContents == null){
            mainContents = new VREditorPane();
            // 自分自身を登録してイベントを検知
            mainContents.addHyperlinkListener(this);
            mainContents.addBindEventListener(this);
        }
        return (VREditorPane)mainContents;
    }

    /**
     * コントロールの値を参照先ソースに適用します。
     * 
     * @throws ParseException 解析例外
     */
    public void applySource() throws ParseException {
        getMainContent().applySource();
        
    }

    /**
     * ソースをコントロールにbindした際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    public void bindSource(VRBindEvent e) {
        this.fireBindSource(new VRBindEvent(this));
        
    }

    /**
     * コントロールがソースに値を適用(apply)した際に呼ばれるイベントです。
     * 
     * @param e イベント情報
     */
    public void applySource(VRBindEvent e) {
        this.fireApplySource(new VRBindEvent(this));
        
    }
    
    /**
     * バインドイベントリスナを全走査してapplySourceイベントを呼び出します。
     * 
     * @param e イベント情報
     */
    protected void fireApplySource(VRBindEvent e) {
        if (e.getSource() == getMainContent()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applySource(e);
        }
    }
    /**
     * バインドイベントリスナを全走査してbindSourceイベントを呼び出します。
     */
    protected void fireBindSource(VRBindEvent e) {
        if (e.getSource() == getMainContent()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindSource(e);
        }
    }
    
    /**
     * バインドイベントリスナを返します。
     * 
     * @return バインドイベントリスナ
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    /**
     * HTMLページを表示します。
     * @param url
     * @throws Exception 
     * @throws IOException 
     */
    public void showHtmlPage(URL url) throws Exception {
        getMainContent().setContentType("text/html");
        getMainContent().setEditable(false);
        ((HTMLDocument)getMainContent().getDocument()).setBase(url);
        try {
            getMainContent().setPage(url);
        } catch (IOException e) {
            throw new Exception("不正なURLが設定されている、もしくはインターネット接続されていません。");
        }
    }
    
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
    public void setText(String text){
        getMainContent().setText(text);
    }
    
    
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
    public String getText() {
        return getMainContent().getText();
        
    }
    
    /**
     * Gets the current URL being displayed.  If a URL was 
     * not specified in the creation of the document, this
     * will return <code>null</code>, and relative URL's will not be 
     * resolved.
     *
     * @return the URL, or <code>null</code> if none
     */
    public URL getPage() {
        return getMainContent().getPage();
        
    }
    
    /**
     * Sets the current URL being displayed.
     *
     * @param url the URL for display
     * @exception IOException for a <code>null</code> or invalid URL
     *      specification
     */
    public void setPage(String url) throws IOException {
        getMainContent().setPage(url);
        
    }
    
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
    public void setPage(URL page) throws IOException {
        getMainContent().setPage(page);
        
    }
    
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
    public void read(InputStream in, Object desc) throws IOException {
        getMainContent().read(in,desc);
        
    }
    
    /**
     * Fetches the currently installed kit for handling content.
     * <code>createDefaultEditorKit</code> is called to set up a default
     * if necessary.
     *
     * @return the editor kit
     */
    public EditorKit getEditorKit() {
        return getMainContent().getEditorKit();
        
    }
    
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
    public void setEditorKit(EditorKit kit) {
        getMainContent().setEditorKit(kit);
        
    }

    /**
     * Fetches the model associated with the editor.  This is
     * primarily for the UI to get at the minimal amount of
     * state required to be a text editor.  Subclasses will
     * return the actual type of the model which will typically
     * be something that extends Document.
     *
     * @return the model
     */
    public Document getDocument() {
        return getMainContent().getDocument();
        
    }
 
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
    public final void setContentType(String type) {
        getMainContent().setContentType(type);
        
    }
    
    /**
     * Sets the specified boolean to indicate whether or not this
     * <code>TextComponent</code> should be editable.
     * A PropertyChange event ("editable") is fired when the
     * state is changed.
     *
     * @param b the boolean to be set
     * @see #isEditable
     * @beaninfo
     * description: specifies if the text can be edited
     *       bound: true
     */
    public void setEditable(boolean b) {
        getMainContent().setEditable(b);
        
    }
    
    /**
     * Returns the boolean indicating whether this 
     * <code>TextComponent</code> is editable or not.
     *
     * @return the boolean value
     * @see #setEditable
     */
    public boolean isEditable() {
        return getMainContent().isEditable();
        
    }
    
    /**
     * Gets the type of content that this editor 
     * is currently set to deal with.  This is 
     * defined to be the type associated with the
     * currently installed <code>EditorKit</code>.
     *
     * @return the content type, <code>null</code> if no editor kit set
     */
    public final String getContentType() {
        return getMainContent().getContentType();
        
    }

    /**
     * Adds a caret listener for notification of any changes to the caret.
     * 
     * @param listener the listener to be added
     * @see javax.swing.event.CaretEvent
     */
    public void addCaretListener(CaretListener listener) {
        getMainContent().addCaretListener(listener);
        
    }

    /**
     * Transfers the currently selected range in the associated text model to
     * the system clipboard, leaving the contents in the text model. The current
     * selection remains intact. Does nothing for <code>null</code>
     * selections.
     * 
     * @see java.awt.Toolkit#getSystemClipboard
     * @see java.awt.datatransfer.Clipboard
     */
    public void copy() {
        getMainContent().copy();
        
    }

    /**
     * Transfers the currently selected range in the associated text model to
     * the system clipboard, removing the contents from the model. The current
     * selection is reset. Does nothing for <code>null</code> selections.
     * 
     * @see java.awt.Toolkit#getSystemClipboard
     * @see java.awt.datatransfer.Clipboard
     */
    public void cut() {
        getMainContent().cut();
        
    }

    /**
     * Fetches the command list for the editor. This is the list of commands
     * supported by the plugged-in UI augmented by the collection of commands
     * that the editor itself supports. These are useful for binding to events,
     * such as in a keymap.
     * 
     * @return the command list
     */
    public Action[] getActions() {
        return getMainContent().getActions();
        
    }

    /**
     * Fetches the caret that allows text-oriented navigation over the view.
     * 
     * @return the caret
     */
    public Caret getCaret() {
        return getMainContent().getCaret();
        
    }

    /**
     * Fetches the current color used to render the caret.
     * 
     * @return the color
     */
    public Color getCaretColor() {
        return getMainContent().getCaretColor();
        
    }

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
    public CaretListener[] getCaretListeners() {
        return getMainContent().getCaretListeners();

    }

    /**
     * Returns the position of the text insertion caret for the text component.
     * 
     * @return the position of the text insertion caret for the text component >=
     *         0
     */
    public int getCaretPosition() {
        return getMainContent().getCaretPosition();

    }

    /**
     * Fetches the current color used to render the selected text.
     * 
     * @return the color
     */
    public Color getDisabledTextColor() {
        return getMainContent().getDisabledTextColor();
        
    }

    /**
     * Gets the <code>dragEnabled</code> property.
     * 
     * @return the value of the <code>dragEnabled</code> property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled() {
        return getMainContent().getDragEnabled();
        
    }

    /**
     * Returns the key accelerator that will cause the receiving text component
     * to get the focus. Return '\0' if no focus accelerator has been set.
     * 
     * @return the key
     */
    public char getFocusAccelerator() {
        return getMainContent().getFocusAccelerator();
        
    }

    /**
     * Fetches the object responsible for making highlights.
     * 
     * @return the highlighter
     */
    public Highlighter getHighlighter() {
        return getMainContent().getHighlighter();
        
    }

    /**
     * Fetches the keymap currently active in this text component.
     * 
     * @return the keymap
     */
    public Keymap getKeymap() {
        return getMainContent().getKeymap();
        
    }

    /**
     * Returns the margin between the text component's border and its text.
     * 
     * @return the margin
     */
    public Insets getMargin() {
        return getMainContent().getMargin();
        
    }

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
    public NavigationFilter getNavigationFilter() {
        return getMainContent().getNavigationFilter();

    }

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
    public String getSelectedText() {
        return getMainContent().getSelectedText();
        
    }

    /**
     * Fetches the current color used to render the selected text.
     * 
     * @return the color
     */
    public Color getSelectedTextColor() {
        return getMainContent().getSelectedTextColor();
        
    }

    /**
     * Fetches the current color used to render the selection.
     * 
     * @return the color
     */
    public Color getSelectionColor() {
        return getMainContent().getSelectionColor();
        
    }

    /**
     * Returns the selected text's end position. Return 0 if the document is
     * empty, or the value of dot if there is no selection.
     * 
     * @return the end position >= 0
     */
    public int getSelectionEnd() {
        return getMainContent().getSelectionEnd();
        
    }

    /**
     * Returns the selected text's start position. Return 0 for an empty
     * document, or the value of dot if no selection.
     * 
     * @return the start position >= 0
     */
    public int getSelectionStart() {
        return getMainContent().getSelectionStart();
        
    }

    /**
     * Fetches a portion of the text represented by the component. Returns an
     * empty string if length is 0.
     * 
     * @param offs the offset >= 0
     * @param len the length >= 0
     * @return the text
     * @exception BadLocationException if the offset or length are invalid
     */
    public String getText(int offs, int len) throws BadLocationException {
        return getMainContent().getText(offs,len);
        
    }

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
    public Rectangle modelToView(int pos) throws BadLocationException {
        return getMainContent().modelToView(pos);
        
    }

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
    public void moveCaretPosition(int pos) {
        getMainContent().moveCaretPosition(pos);
        
    }

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
    public void paste() {
        getMainContent().paste();
        
    }

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
    public void read(Reader in, Object desc) throws IOException {
        getMainContent().read(in,desc);
        
    }

    /**
     * Removes a caret listener.
     * 
     * @param listener the listener to be removed
     * @see javax.swing.event.CaretEvent
     */
    public void removeCaretListener(CaretListener listener) {
        getMainContent().removeCaretListener(listener);
        
    }

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
    public void replaceSelection(String content) {
        getMainContent().replaceSelection(content);
        
    }

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
    public void select(int selectionStart, int selectionEnd) {
        getMainContent().select(selectionStart,selectionEnd);
        
    }

    /**
     * Selects all the text in the <code>TextComponent</code>. Does nothing
     * on a <code>null</code> or empty document.
     */
    public void selectAll() {
        getMainContent().selectAll();
        
    }

    /**
     * Sets the caret to be used. By default this will be set by the UI that
     * gets installed. This can be changed to a custom caret if desired. Setting
     * the caret results in a PropertyChange event ("caret") being fired.
     * 
     * @param c the caret
     * @see #getCaret
     */
    public void setCaret(Caret c) {
        getMainContent().setCaret(c);
        
    }

    /**
     * Sets the current color used to render the caret. Setting to
     * <code>null</code> effectively restores the default color. Setting the
     * color results in a PropertyChange event ("caretColor") being fired.
     * 
     * @param c the color
     * @see #getCaretColor
     */
    public void setCaretColor(Color c) {
        getMainContent().setCaretColor(c);
        
    }

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
    public void setCaretPosition(int position) {
        getMainContent().setCaretPosition(position);
        
    }

    /**
     * Sets the current color used to render the disabled text. Setting the
     * color fires off a PropertyChange event ("disabledTextColor").
     * 
     * @param c the color
     * @see #getDisabledTextColor
     */
    public void setDisabledTextColor(Color c) {
        getMainContent().setDisabledTextColor(c);
        
    }

    /**
     * Associates the editor with a text document. The currently registered
     * factory is used to build a view for the document, which gets displayed by
     * the editor after revalidation. A PropertyChange event ("document") is
     * propagated to each listener.
     * 
     * @param doc the document to display/edit
     * @see #getDocument
     */
    public void setDocument(Document doc) {
        getMainContent().setDocument(doc);
        
    }

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
    public void setDragEnabled(boolean b) {
        getMainContent().setDragEnabled(b);
        
    }

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
    public void setFocusAccelerator(char aKey) {
        getMainContent().setFocusAccelerator(aKey);
        
    }

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
    public void setHighlighter(Highlighter h) {
        getMainContent().setHighlighter(h);
        
    }

    /**
     * Sets the keymap to use for binding events to actions. Setting to
     * <code>null</code> effectively disables keyboard input. A PropertyChange
     * event ("keymap") is fired when a new keymap is installed.
     * 
     * @param map the keymap
     * @see #getKeymap
     */
    public void setKeymap(Keymap map) {
        getMainContent().setKeymap(map);
        
    }

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
    public void setMargin(Insets m) {
        getMainContent().setMargin(m);
        
    }

    /**
     * Sets the <code>NavigationFilter</code>. <code>NavigationFilter</code>
     * is used by <code>DefaultCaret</code> and the default cursor movement
     * actions as a way to restrict the cursor movement.
     * 
     * @since 1.4
     */
    public void setNavigationFilter(NavigationFilter filter) {
        getMainContent().setNavigationFilter(filter);
        
    }

    /**
     * Sets the current color used to render the selected text. Setting the
     * color to <code>null</code> is the same as <code>Color.black</code>.
     * Setting the color results in a PropertyChange event ("selectedTextColor")
     * being fired.
     * 
     * @param c the color
     * @see #getSelectedTextColor
     */
    public void setSelectedTextColor(Color c) {
        getMainContent().setSelectedTextColor(c);
        
    }

    /**
     * Sets the current color used to render the selection. Setting the color to
     * <code>null</code> is the same as setting <code>Color.white</code>.
     * Setting the color results in a PropertyChange event ("selectionColor").
     * 
     * @param c the color
     * @see #getSelectionColor
     */
    public void setSelectionColor(Color c) {
        getMainContent().setSelectionColor(c);
        
    }

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
    public void setSelectionEnd(int selectionEnd) {
        getMainContent().setSelectionEnd(selectionEnd);
        
    }

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
    public void setSelectionStart(int selectionStart) {
        getMainContent().setSelectionStart(selectionStart);
        
    }

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
    public int viewToModel(Point pt) {
        return getMainContent().viewToModel(pt); 
        
    }

    /**
     * Stores the contents of the model into the given stream. By default this
     * will store the model as plain text.
     * 
     * @param out the output stream
     * @exception IOException on any I/O error
     */
    public void write(Writer out) throws IOException {
        getMainContent().write(out);
        
    }

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
    public EditorKit getEditorKitForContentType(String type) {
        return getMainContent().getEditorKitForContentType(type);

    }

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
    public void scrollToReference(String reference) {
        getMainContent().scrollToReference(reference);
        
    }

    /**
     * Directly sets the editor kit to use for the given type.  A 
     * look-and-feel implementation might use this in conjunction
     * with <code>createEditorKitForContentType</code> to install handlers for
     * content types with a look-and-feel bias.
     *
     * @param type the non-<code>null</code> content type
     * @param k the editor kit to be set
     */
    public void setEditorKitForContentType(String type, EditorKit k) {
        getMainContent().setEditorKitForContentType(type,k);
        
    }

    /**
     * Creates a handler for the given type from the default registry
     * of editor kits.  The registry is created if necessary.  If the
     * registered class has not yet been loaded, an attempt
     * is made to dynamically load the prototype of the kit for the
     * given type.  If the type was registered with a <code>ClassLoader</code>,
     * that <code>ClassLoader</code> will be used to load the prototype.
     * If there was no registered <code>ClassLoader</code>,
     * <code>Class.forName</code> will be used to load the prototype.
     * <p>
     * Once a prototype <code>EditorKit</code> instance is successfully
     * located, it is cloned and the clone is returned.  
     *
     * @param type the content type
     * @return the editor kit, or <code>null</code> if there is nothing
     *   registered for the given type
     */
    public EditorKit createEditorKitForContentType(String type) {
        return getMainContent().createEditorKitForContentType(type);
        
    }
    
    /**
     * Returns the currently registered <code>EditorKit</code>
     * class name for the type <code>type</code>.
     *
     * @param type  the non-<code>null</code> content type
     *
     * @since 1.3
     */
    public String getEditorKitClassNameForContentType(String type){
        return getMainContent().getEditorKitClassNameForContentType(type);
        
    }
    
    /**
     * Establishes the default bindings of <code>type</code> to
     * <code>classname</code>.  
     * The class will be dynamically loaded later when actually
     * needed, and can be safely changed before attempted uses
     * to avoid loading unwanted classes.  The prototype 
     * <code>EditorKit</code> will be loaded with <code>Class.forName</code>
     * when registered with this method.
     *
     * @param type the non-<code>null</code> content type
     * @param classname the class to load later
     */
    public void registerEditorKitForContentType(String type, String classname){
        getMainContent().registerEditorKitForContentType(type,classname);
        
    }
    
    /**
     * Establishes the default bindings of <code>type</code> to
     * <code>classname</code>.  
     * The class will be dynamically loaded later when actually
     * needed using the given <code>ClassLoader</code>,
     * and can be safely changed 
     * before attempted uses to avoid loading unwanted classes.
     *
     * @param type the non-</code>null</code> content type
     * @param classname the class to load later
     * @param loader the <code>ClassLoader</code> to use to load the name
     */
    public void registerEditorKitForContentType(String type, String classname, ClassLoader loader){
        getMainContent().registerEditorKitForContentType(type,classname,loader);
        
    }
    
}
