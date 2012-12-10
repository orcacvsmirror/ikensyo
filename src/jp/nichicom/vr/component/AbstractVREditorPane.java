package jp.nichicom.vr.component;

import java.awt.Cursor;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
/**
 * 
 * AbstractVREditorPane�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/06
 * @see javax.swing.JEditorPane
 * @see jp.nichicom.vr.component.VREditorPanear
 */
public abstract class AbstractVREditorPane extends JEditorPane implements
        VREditorPanear {
    
    private boolean autoApplySource = false;

    private String bindPath;

    private VRBindSource source;

    /**
     * Creates a new <code>JEditorPane</code>.
     * The document model is set to <code>null</code>.
     */
    public AbstractVREditorPane() {
        super();
        initComponent();
    }

    /**
     * Creates a <code>JEditorPane</code> based on a specified URL for input.
     *
     * @param initialPage the URL
     * @exception IOException if the URL is <code>null</code>
     *      or cannot be accessed
     */
    public AbstractVREditorPane(String url) throws IOException {
        super(url);
        initComponent();
    }

    /**
     * Creates a <code>JEditorPane</code> based on a string containing
     * a URL specification.
     *
     * @param url the URL
     * @exception IOException if the URL is <code>null</code> or
     *      cannot be accessed
     */
    public AbstractVREditorPane(URL initialPage) throws IOException {
        super(initialPage);
        initComponent();
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
    public AbstractVREditorPane(String type, String text) {
        super(type, text);
        initComponent();
    }
    
    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        this.addHyperlinkListener(this);
        
    }

    /**
     * Called when a hypertext link is updated.
     *
     * @param e the event responsible for the update
     */
    public void hyperlinkUpdate(HyperlinkEvent e) {
        
        // �n�C�p�[�����N�N���b�N��
        if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
            URL url = e.getURL();
            // JavaScript����URL���擾�o���Ȃ��ꍇ�̑Ή�
            if(url == null)return;
            
            try{
                // �\������
                this.setPage(url);

            }catch(Exception err){
                err.printStackTrace();
                
            }
            
        // �����N�Ƀ}�E�X�J�[�\�����������ꍇ
        }else if(e.getEventType() == HyperlinkEvent.EventType.ENTERED){

        // �����N����}�E�X�J�[�\�����o���ꍇ
        }else if(e.getEventType() == HyperlinkEvent.EventType.EXITED){

        }else{
            return;
        }
        
    }
 
    /**
     * �o�C���h�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �o�C���h�C�x���g���X�i
     */
    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }
    
    /**
     * �R���g���[���̒l���Q�Ɛ�\�[�X�ɓK�p���܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(),getText())) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applySource(e);
        }
    }

    /**
     * �Q�Ɛ�\�[�X�̒l���R���g���[���ɗ������݂܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), getSource())) {
            return;
        }

        Object obj = VRBindPathParser.get(getBindPath(), getSource());
        if (obj == null) {
            return;
        }
        super.setText(String.valueOf(obj));
        fireBindSource(new VRBindEvent(this));
            
    }
    
    /**
     * �o�C���h�C�x���g���X�i��S��������bindSource�C�x���g���Ăяo���܂��B
     * @param e �C�x���g���
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindSource(e);
        }
    }
    
    /**
     * �f�t�H���g�f�[�^���i�[�����\�[�X�C���X�^���X�𐶐����܂��B
     * 
     * @return �q�v�f�C���X�^���X
     */
    public Object createSource() {
        return "";
    }
    
    /**
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }
    

    /**
     * �o�C���h�p�X��Ԃ��܂��B
     * 
     * @return �o�C���h�p�X
     */
    public String getBindPath() {
        return this.bindPath;
    }

    /**
     * �Q�Ɛ�\�[�X��Ԃ��܂��B
     * 
     * @return �Q�Ɛ�\�[�X
     */
    public VRBindSource getSource() {
        return this.source;
    }

    /**
     * �����K�p���邩��Ԃ��܂��B
     * 
     * @return �����K�p���邩
     */
    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }
    
    /**
     * �o�C���h�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �o�C���h�C�x���g���X�i
     */
    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
        
    }

    /**
     * �����K�p���邩��ݒ肵�܂��B
     * 
     * @param autoApplySource �����K�p���邩
     */
    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
        
    }
    
    /**
     * �o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     */
    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
        
    }

    /**
     * �Q�Ɛ�\�[�X��ݒ肵�܂��B
     * 
     * @param source �Q�Ɛ�\�[�X
     */
    public void setSource(VRBindSource source) {
        this.source = source;
        
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
        super.setText(text);
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    
}
