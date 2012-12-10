/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.event.FocusEvent;
import java.awt.event.InputMethodEvent;
import java.awt.font.TextHitInfo;
import java.awt.im.InputSubset;
import java.text.Format;
import java.text.ParseException;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.text.AbstractVRTextDocument;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.text.VRTextFieldDocument;

/**
 * �o�C���h�@�\�����������e�L�X�g�t�B�[���h�̊��N���X�ł��B
 * <p>
 * AbstractVRTextDocument�̓����ɂ���ē��͉\�ȕ�����ʂ�ŏ��E�ő啶���񒷂𐧌�����@�\���������Ă��܂��B
 * </p>
 * <p>
 * InputSubset�w��ɂ��IME���[�h������������Ă��܂��B
 * </p>
 * <p>
 * Format�w��ɂ����͒l�̃t�H�[�}�b�g�ϊ��������������Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see JTextField
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see InputSubset
 * @see Format
 * @see VRFormatEventListener
 * @see VRCharType
 * @see AbstractVRTextDocument
 */
public abstract class AbstractVRTextField extends JTextField implements
        VRTextFieldar {

    private boolean autoApplySource = false;

    private String bindPath;

    private boolean borderless;

    private boolean byteMaxLength = false;

    private VRCharType charType;

    private boolean focusSelcetAll = false;

    private Format format;

    private int maxLength;

    private int minLength;

    private Object model = "";

    private Border oldBorder;

    private VRBindSource source;

    /**
     * Constructs a new <code>TextField</code>. A default model is created,
     * the initial string is <code>null</code>, and the number of columns is
     * set to 0.
     */
    public AbstractVRTextField() {
        super();
        initComponent();
    }

    /**
     * Constructs a new <code>JTextField</code> that uses the given text
     * storage model and the given number of columns. This is the constructor
     * through which the other constructors feed. If the document is
     * <code>null</code>, a default model is created.
     * 
     * @param doc the text storage to use; if this is <code>null</code>, a
     *            default will be provided by calling the
     *            <code>createDefaultModel</code> method
     * @param text the initial string to display, or <code>null</code>
     * @param columns the number of columns to use to calculate the preferred
     *            width >= 0; if <code>columns</code> is set to zero, the
     *            preferred width will be whatever naturally results from the
     *            component implementation
     * @exception IllegalArgumentException if <code>columns</code> < 0
     */
    public AbstractVRTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        initComponent();
    }

    /**
     * Constructs a new empty <code>TextField</code> with the specified number
     * of columns. A default model is created and the initial string is set to
     * <code>null</code>.
     * 
     * @param columns the number of columns to use to calculate the preferred
     *            width; if columns is set to zero, the preferred width will be
     *            whatever naturally results from the component implementation
     */
    public AbstractVRTextField(int columns) {
        super(columns);
        initComponent();
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the specified
     * text. A default model is created and the number of columns is 0.
     * 
     * @param text the text to be displayed, or <code>null</code>
     */
    public AbstractVRTextField(String text) {
        super(text);
        initComponent();
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the specified
     * text and columns. A default model is created.
     * 
     * @param text the text to be displayed, or <code>null</code>
     * @param columns the number of columns to use to calculate the preferred
     *            width; if columns is set to zero, the preferred width will be
     *            whatever naturally results from the component implementation
     */
    public AbstractVRTextField(String text, int columns) {
        super(text, columns);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
     */
    public void addFormatEventListener(VRFormatEventListener listener) {
        listenerList.add(VRFormatEventListener.class, listener);
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), getModel())) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindSource() throws ParseException {
        if(!VRBindPathParser.has(getBindPath(), getSource())){
            return;
        }
        Object obj = VRBindPathParser.get(getBindPath(), getSource());

        Document doc = getDocument();
        if (doc instanceof AbstractVRTextDocument) {
            // ���͐�������
            ((AbstractVRTextDocument) doc).setAbsoluteEditable(true);
        }

        if (obj == null) {
            super.setText("");
            setModel(null);
        } else {

            Format fmt = getFormat();
            if (fmt != null) {
                super.setText(fmt.format(obj));
                setModel(obj);
            } else {
                String text = String.valueOf(obj);
                super.setText(text);
                setModel(text);
            }

        }
        if (doc instanceof AbstractVRTextDocument) {
            // ���͐������A
            ((AbstractVRTextDocument) doc).setAbsoluteEditable(false);
        }

        fireBindSource(new VRBindEvent(this));
    }

    public Object createSource() {
        if (getFormat() == null) {
            return "";
        }
        return null;
    }

    /**
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    public String getBindPath() {
        return bindPath;
    }

    /**
     * �����퐧��Ԃ��܂��B
     * 
     * @return �����퐧��
     */
    public VRCharType getCharType() {
        return this.charType;
    }

    /**
     * �t�H�[�}�b�g��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
     */
    public Format getFormat() {
        return this.format;
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g�C�x���g���X�i
     */
    public synchronized VRFormatEventListener[] getFormatEventListeners() {
        return (VRFormatEventListener[]) (getListeners(VRFormatEventListener.class));
    }

    /**
     * �ő啶������Ԃ��܂��B
     * 
     * @return �ő啶����
     */
    public int getMaxLength() {
        return this.maxLength;
    }

    /**
     * �ŏ���������Ԃ��܂��B
     * 
     * @return �ŏ�������
     */
    public int getMinLength() {
        return this.minLength;
    }

    /**
     * ���f���f�[�^��Ԃ��܂��B
     * 
     * @return ���f���f�[�^
     */
    public Object getModel() {
        return model;
    }

    public VRBindSource getSource() {
        return source;
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    /**
     * �{�[�_�[�̐ݒ�������邩 ��Ԃ��܂��B
     * 
     * @return �{�[�_�[�̐ݒ�������邩
     */
    public boolean isBorderless() {
        return borderless;
    }

    /**
     * �ő啶���񒷂𕶎����ł͂Ȃ��o�C�g���Ŕ��f���邩 ��Ԃ��܂��B
     * 
     * @return �ő啶���񒷂��o�C�g���Ŕ��f���邩
     */
    public boolean isByteMaxLength() {
        return byteMaxLength;
    }

    /**
     * �t�H�[�J�X�擾���ɕ������S�I�����邩 ��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���ɕ������S�I�����邩
     */
    public boolean isFocusSelcetAll() {
        return focusSelcetAll;
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
     */
    public void removeFormatEventListener(VRFormatEventListener listener) {
        listenerList.remove(VRFormatEventListener.class, listener);
    }

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

    public void setBorder(Border border) {
        oldBorder = border;
        if ((border == null) || (!isBorderless())) {
            super.setBorder(border);
        }
    }

    /**
     * �{�[�_�[�̐ݒ�������邩 ��ݒ肵�܂��B
     * 
     * @param borderless �{�[�_�[�̐ݒ�������邩
     */
    public void setBorderless(boolean borderless) {
        if (this.borderless == borderless) {
            return;
        }
        if (this.borderless) {
            // �ύX�O���{�[�_���X�ŐV���Ƀ{�[�_�[�������� �� �{�[�_�[�𕜋A����
            this.borderless = borderless;
            super.setBorder(oldBorder);
        } else {
            // �ύX�O�̓{�[�_�[������A�V���Ƀ{�[�_���X�ɂ���
            this.borderless = borderless;
            super.setBorder(null);
        }
    }

    /**
     * �ő啶���񒷂𕶎����ł͂Ȃ��o�C�g���Ŕ��f���邩 ��ݒ肵�܂��B
     * 
     * @param byteMaxLength �ő啶���񒷂��o�C�g���Ŕ��f���邩
     */
    public void setByteMaxLength(boolean byteMaxLength) {
        this.byteMaxLength = byteMaxLength;
    }

    /**
     * �����퐧��ݒ肵�܂��B
     * 
     * @param charType �����퐧��
     */
    public void setCharType(VRCharType charType) {
        this.charType = charType;
    }

    /**
     * �t�H�[�J�X�擾���ɕ������S�I�����邩 ��ݒ肵�܂��B
     * 
     * @param focusSelcetAll �t�H�[�J�X�擾���ɕ������S�I�����邩
     */
    public void setFocusSelcetAll(boolean focusSelcetAll) {
        this.focusSelcetAll = focusSelcetAll;
    }

    /**
     * �t�H�[�}�b�g��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * �ő啶������ݒ肵�܂��B
     * 
     * @param maxLength �ő啶����
     */
    public void setMaxLength(int maxLength) {
        if (maxLength < getMinLength()) {
            return;
        }
        this.maxLength = maxLength;
    }

    /**
     * �ŏ���������ݒ肵�܂��B
     * 
     * @param minLength �ŏ�������
     */
    public void setMinLength(int minLength) {
        if (minLength > getMaxLength()) {
            return;
        }
        if (minLength < 0) {
            return;
        }
        this.minLength = minLength;
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    public void setText(String text) {

        Document doc = getDocument();
        if (doc instanceof AbstractVRTextDocument) {
            // ���͐�������
            ((AbstractVRTextDocument) doc).setAbsoluteEditable(true);
        }

        Format fmt = getFormat();
        if (fmt != null) {
            setModelValueOnFormat(text);
        } else {
            setModel(text);
            super.setText(text);
        }

        if (doc instanceof AbstractVRTextDocument) {
            // ���͐������A
            ((AbstractVRTextDocument) doc).setAbsoluteEditable(false);
        }

        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ���̓`�F�b�N�Ɏg�p����h�L�������g�𐶐����܂��B <br />
     * Factory Method Pattern
     * 
     * @return �h�L�������g
     */
    protected Document createDocument() {
        return new VRTextFieldDocument(this);
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
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindSource(e);
        }
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��S��������formatError�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireFormatInvalid(VRFormatEvent e) {
        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatInvalid(e);
        }
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��S��������formatValid�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireFormatValid(VRFormatEvent e) {
        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatValid(e);
        }
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        setDocument(createDocument());
    }

    protected void processFocusEvent(FocusEvent e) {

        int id = e.getID();
        switch (id) {
        case FocusEvent.FOCUS_GAINED: {
            processFocusGained(e);
            break;
        }
        case FocusEvent.FOCUS_LOST: {
            processFocusLost(e);
            break;
        }
        }
        super.processFocusEvent(e);

    }

    /**
     * �t�H�[�J�X�擾���̒ǉ��������s�Ȃ��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void processFocusGained(FocusEvent e) {
        if (isFocusSelcetAll()) {
            selectAll();
        }
    }

    /**
     * �t�H�[�J�X�r�����̒ǉ��������s�Ȃ��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void processFocusLost(FocusEvent e) {
        removeUndecidedCharacters();

        // �ω����������ꍇ
        String text = getText();
        setText(text);

        // �G���[���ɖ߂��ꍇ
        Format fmt = getFormat();
        if (fmt != null) {
            // �ω����Ȃ���return
            if (text.equals(getText())) {
                // �Ō�ɓK���Ƃ��ꂽ��Ԃɖ߂�
                Object mdl = getModel();
                if (mdl == null) {
                    super.setText("");
                } else {
                    super.setText(fmt.format(mdl));
                }
            }
        }
    }

    /**
     * IME�̖��m�蕶������폜���܂��B
     */
    protected void removeUndecidedCharacters() {
        if (!(getDocument() instanceof AbstractVRTextDocument)) {
            return;
        }
        AbstractVRTextDocument doc = (AbstractVRTextDocument) getDocument();

        int begin = -1; // �폜�Ώې擪�ʒu
        int len = 0; // �폜���镶����

        int end = doc.getLength();
        // ������̑������ꕶ�������ׂ�
        for (int i = 0; i < end; i++) {
            Element elem = doc.getCharacterElement(i);
            if (elem.getAttributes().isDefined(
                    StyleConstants.ComposedTextAttribute)) { // IME�̖��m�蕶���񂾂����ꍇ
                begin = i;
                break;
            }
        }
        if (begin >= 0) {
            len = 1;
            for (int i = begin + 1; i < end; i++) {
                Element elem = doc.getCharacterElement(i);
                if (elem.getAttributes().isDefined(
                        StyleConstants.ComposedTextAttribute)) { // IME�̖��m�蕶���񂾂����ꍇ
                    len++;
                } else {
                    break;
                }
            }
        }

        if (len != 0) {
            try {
                // IME�̖��m�蕶������폜����
                doc.superRemove(begin, len);

                // �ˋ��InputEvent�𔭐������āA�L�����b�g��ݒ肷��
                processInputMethodEvent(new InputMethodEvent(this,
                        InputMethodEvent.INPUT_METHOD_TEXT_CHANGED, null, 0,
                        TextHitInfo.beforeOffset(0), null));

            } catch (Exception ex) {

            }
        }
    }

    /**
     * ���f���f�[�^��ݒ肵�܂��B
     * 
     * @param model ���f���f�[�^
     */
    protected void setModel(Object model) {
        this.model = model;
    }

    /**
     * ���f���Ƀt�H�[�}�b�^��ʂ��ăe�L�X�g��ݒ肵�܂��B
     * 
     * @param text �ݒ�l
     */
    protected void setModelValueOnFormat(String text) {
        Format fmt = getFormat();
        String val = text.trim();
        try {
            Object mdl = fmt.parseObject(val);
            String formated = fmt.format(mdl);

            // FormatValid�C�x���g����
            Object old = getModel();
            setModel(mdl);

            super.setText(formated);
            fireFormatValid(new VRFormatEvent(this, old, val));
        } catch (ParseException e) {
            // FormatError�C�x���g����
            Object old = getModel();
            fireFormatInvalid(new VRFormatEvent(this, old, val));
        }
    }
}