package jp.or.med.orca.ikensho.component;

import java.awt.Color;
import java.awt.Dimension;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start ���͉�ʂƈ���̕\���s��v�Ή�
import java.awt.Font;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.im.InputSubset;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.Format;
import java.text.ParseException;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.Keymap;
import javax.swing.text.NavigationFilter;

// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start ���͉�ʂƈ���̕\���s��v�Ή�
import jp.nichicom.ac.ACOSInfo;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.container.AbstractACScrollPane;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start ���͉�ʂƈ���̕\���s��v�Ή�
import jp.nichicom.ac.core.ACFrame;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.VRTextArear;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.text.VRCharType;

/**
 * �X�N���[���y�C����̌^�̃e�L�X�g�G���A�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractACScrollPane
 * @see ACRowMaximumableTextArea
 * @see VRTextArear
 */
public class IkenshoACTextArea extends AbstractACScrollPane implements VRTextArear, VRBindable,
        FocusListener, KeyListener, MouseMotionListener, MouseWheelListener,
        MouseListener, VRBindEventListener, InputMethodListener,
        VRFormatEventListener {
    private ACRowMaximumableTextArea mainContent;

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoACTextArea() {
        super();
// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start ���͉�ʂƈ���̕\���s��v�Ή�
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param doc ���͐���h�L�������g
     * @param text �e�L�X�g�l
     * @param rows �s��
     * @param columns ��
     */
    public IkenshoACTextArea(Document doc, String text, int rows, int columns) {
        this();
        getMainContent().setDocument(doc);
        getMainContent().setText(text);
        getMainContent().setRows(rows);
        getMainContent().setColumns(columns);
        
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param rows �s��
     * @param columns ��
     */
    public IkenshoACTextArea(int rows, int columns) {
        this();
        getMainContent().setRows(rows);
        getMainContent().setColumns(columns);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param text �e�L�X�g�l
     */
    public IkenshoACTextArea(String text) {
        this();
        getMainContent().setText(text);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param text �e�L�X�g�l
     * @param rows �s��
     * @param columns ��
     */
    public IkenshoACTextArea(String text, int rows, int columns) {
        this();
        getMainContent().setText(text);
        getMainContent().setRows(rows);
        getMainContent().setColumns(columns);
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    public void addCaretListener(CaretListener listener) {
        getMainContent().addCaretListener(listener);
    }

// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start ���͉�ʂƈ���̕\���s��v�Ή�
    /**
     * �R���|�[�l���g�����������܂��B
     *
     * @throws Exception ��������O
     */
    private void jbInit() throws Exception {
        // Windows�̏ꍇ�́AMS�S�V�b�N�̎g�p���������ALineSpace���l�߂�
        if (!ACOSInfo.isMac()) {
            Font oldFont = getFont();
            if (oldFont == null) {
                getMainContent().setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 12));
            }
            else {
            	ACFrame frame = ACFrame.getInstance();
                getMainContent().setFont(new Font("�l�r �S�V�b�N", oldFont.getStyle(), frame.isMiddle() ? 14 : oldFont.getSize()));
            }
        }
    }
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
     */
    public void addFormatEventListener(VRFormatEventListener listener) {
        listenerList.add(VRFormatEventListener.class, listener);
        // getTextArea().addFormatEventListener(listener);
    }

    public void append(String str) {
        getMainContent().append(str);
    }

    public void applySource() throws ParseException {
        getMainContent().applySource();
    }

    public void applySource(VRBindEvent e) {
        fireApplySource(new VRBindEvent(this));
    }

    public void bindSource() throws ParseException {
        getMainContent().bindSource();
    }

    public void bindSource(VRBindEvent e) {
        fireBindSource(new VRBindEvent(this));
    }

//    public void caretPositionChanged(InputMethodEvent event) {
//        processInputMethodEvent(event);
//    }

    public void copy() {
        getMainContent().copy();
    }

    public Object createSource() {
        return getMainContent().createSource();
    }

    public void cut() {
        getMainContent().cut();
    }

    public void formatInvalid(VRFormatEvent e) {
        fireFormatInvalid(e);
    }

    public void formatValid(VRFormatEvent e) {
        fireFormatValid(e);
    }

    public Action[] getActions() {
        return getMainContent().getActions();
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
        return getMainContent().getBindPath();
    }

    public Caret getCaret() {
        return getMainContent().getCaret();
    }

    public Color getCaretColor() {
        return getMainContent().getCaretColor();
    }

    public CaretListener[] getCaretListeners() {
        return getMainContent().getCaretListeners();
    }

    public int getCaretPosition() {
        return getMainContent().getCaretPosition();
    }

    /**
     * �����퐧��Ԃ��܂��B
     * 
     * @return �����퐧��
     */
    public VRCharType getCharType() {
        return getMainContent().getCharType();
    }

    public int getColumns() {
        return getMainContent().getColumns();
    }

    public Color getDisabledTextColor() {
        return getMainContent().getDisabledTextColor();
    }

    public Document getDocument() {
        return getMainContent().getDocument();
    }

    public boolean getDragEnabled() {
        return getMainContent().getDragEnabled();
    }

    public char getFocusAccelerator() {
        return getMainContent().getFocusAccelerator();
    }

    /**
     * �t�H�[�}�b�g��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
     */
    public Format getFormat() {
        return getMainContent().getFormat();
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g�C�x���g���X�i
     */
    public synchronized VRFormatEventListener[] getFormatEventListeners() {
        return (VRFormatEventListener[]) (getListeners(VRFormatEventListener.class));
    }

    public Highlighter getHighlighter() {
        return getMainContent().getHighlighter();
    }

    /**
     * IME���[�h��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
     */
    public InputSubset getIMEMode() {
        return getMainContent().getIMEMode();
    }

    public Keymap getKeymap() {
        return getMainContent().getKeymap();
    }

    public int getLineCount() {
        return getMainContent().getLineCount();
    }

    public int getLineEndOffset(int line) throws BadLocationException {
        return getMainContent().getLineEndOffset(line);
    }

    public int getLineOfOffset(int offset) throws BadLocationException {
        return getMainContent().getLineOfOffset(offset);
    }

    public int getLineStartOffset(int line) throws BadLocationException {
        return getMainContent().getLineStartOffset(line);
    }

    public boolean getLineWrap() {
        return getMainContent().getLineWrap();
    }

    public Insets getMargin() {
        return getMainContent().getMargin();
    }

    /**
     * �ő啶������Ԃ��܂��B
     * 
     * @return �ő啶����
     */
    public int getMaxLength() {
        return getMainContent().getMaxLength();
    }

    /**
     * �ő���͍s�� ��Ԃ��܂��B
     * 
     * @return �ő���͍s��
     */
    public int getMaxRows() {
        return getMainContent().getMaxRows();
    }

    /**
     * �ŏ���������Ԃ��܂��B
     * 
     * @return �ŏ�������
     */
    public int getMinLength() {
        return getMainContent().getMinLength();
    }

    /**
     * ���f���f�[�^��Ԃ��܂��B
     * 
     * @return ���f���f�[�^
     */
    public Object getModel() {
        return getMainContent().getModel();
    }

    public NavigationFilter getNavigationFilter() {
        return getMainContent().getNavigationFilter();
    }

    public int getRows() {
        return getMainContent().getRows();
    }

    public String getSelectedText() {
        return getMainContent().getSelectedText();
    }

    public Color getSelectedTextColor() {
        return getMainContent().getSelectedTextColor();
    }

    public Color getSelectionColor() {
        return getMainContent().getSelectionColor();
    }

    public int getSelectionEnd() {
        return getMainContent().getSelectionEnd();
    }

    public int getSelectionStart() {
        return getMainContent().getSelectionStart();
    }

    public VRBindSource getSource() {
        return getMainContent().getSource();
    }

    public int getTabSize() {
        return getMainContent().getTabSize();
    }

    /**
     * ���͒l��Ԃ��܂��B
     * 
     * @return ���͒l
     */
    public String getText() {
        return getMainContent().getText();
    }

    public String getText(int offs, int len) throws BadLocationException {
        return getMainContent().getText(offs, len);
    }

    public boolean getWrapStyleWord() {
        return getMainContent().getWrapStyleWord();
    }

//    public void inputMethodTextChanged(InputMethodEvent event) {
//        processInputMethodEvent(event);
//    }

    public void insert(String str, int pos) {
        getMainContent().insert(str, pos);
    }

    public boolean isAutoApplySource() {
        return getMainContent().isAutoApplySource();
    }

    public boolean isByteMaxLength() {

        return getMainContent().isByteMaxLength();
    }

    public boolean isEditable() {
        return getMainContent().isEditable();
    }

//    public boolean isEnabled() {
//        return super.isEnabled() && getMainContent().isEnabled();
//    }

    /**
     * �t�H�[�J�X�擾���ɕ������S�I�����邩 ��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���ɕ������S�I�����邩
     */
    public boolean isFocusSelcetAll() {
        return getMainContent().isFocusSelcetAll();
    }

    /**
     * �^�u�����̑}���������邩��Ԃ��܂��B
     * 
     * @return �^�u�����̑}���������邩
     */
    public boolean isInsertTab() {
        return getMainContent().isInsertTab();

    }

    public Rectangle modelToView(int pos) throws BadLocationException {
        return getMainContent().modelToView(pos);
    }


    public void moveCaretPosition(int pos) {
        getMainContent().moveCaretPosition(pos);
    }

    public void paste() {
        getMainContent().paste();
    }

    public void read(Reader in, Object desc) throws IOException {
        getMainContent().read(in, desc);
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
    }

    public void removeCaretListener(CaretListener listener) {
        getMainContent().removeCaretListener(listener);
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
     */
    public void removeFormatEventListener(VRFormatEventListener listener) {
        listenerList.remove(VRFormatEventListener.class, listener);
    }

    public void replaceRange(String str, int start, int end) {
        getMainContent().replaceRange(str, start, end);
    }

    public void replaceSelection(String content) {
        getMainContent().replaceSelection(content);
    }

    public void select(int selectionStart, int selectionEnd) {
        getMainContent().select(selectionStart, selectionEnd);
    }

    public void selectAll() {
        getMainContent().selectAll();
    }

    public void setAutoApplySource(boolean autoApplySource) {
        getMainContent().setAutoApplySource(autoApplySource);
    }

    public void setBindPath(String bindPath) {
        getMainContent().setBindPath(bindPath);
    }

    public void setByteMaxLength(boolean byteMaxLength) {
        getMainContent().setByteMaxLength(byteMaxLength);
    }

    public void setCaret(Caret c) {
        getMainContent().setCaret(c);
    }

    public void setCaretColor(Color c) {
        getMainContent().setCaretColor(c);
    }

    public void setCaretPosition(int position) {
        getMainContent().setCaretPosition(position);
    }

    /**
     * �����퐧��ݒ肵�܂��B
     * 
     * @param charType �����퐧��
     */
    public void setCharType(VRCharType charType) {
        getMainContent().setCharType(charType);
    }

    public void setColumns(int columns) {
        getMainContent().setColumns(columns);
    }

    public void setDisabledTextColor(Color c) {
        getMainContent().setDisabledTextColor(c);
    }

    public void setDocument(Document doc) {
        getMainContent().setDocument(doc);
    }

    public void setDragEnabled(boolean b) {
        getMainContent().setDragEnabled(b);
    }

    public void setEditable(boolean b) {
        getMainContent().setEditable(b);
    }

//    public void setEnabled(boolean enabled) {
//        super.setEnabled(enabled);
//        getMainContent().setEnabled(enabled);
//    }

    public void setFocusAccelerator(char aKey) {
        getMainContent().setFocusAccelerator(aKey);
    }

    /**
     * �t�H�[�J�X�擾���ɕ������S�I�����邩 ��ݒ肵�܂��B
     * 
     * @param focusSelcetAll �t�H�[�J�X�擾���ɕ������S�I�����邩
     */
    public void setFocusSelcetAll(boolean focusSelcetAll) {
        getMainContent().setFocusSelcetAll(focusSelcetAll);
    }

    /**
     * �t�H�[�}�b�g��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format) {
        getMainContent().setFormat(format);
    }

    public void setHighlighter(Highlighter h) {
        getMainContent().setHighlighter(h);
    }

    /**
     * IME���[�h��ݒ肵�܂��B
     * 
     * @param imeMode �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
     */
    public void setIMEMode(InputSubset imeMode) {
        getMainContent().setIMEMode(imeMode);
    }

    /**
     * �^�u�����̑}���������邩��ݒ肵�܂��B
     * 
     * @param insertTab �^�u�����̑}���������邩
     */
    public void setInsertTab(boolean insertTab) {
        getMainContent().setInsertTab(insertTab);
    }

    public void setKeymap(Keymap map) {
        getMainContent().setKeymap(map);
    }

    public void setLineWrap(boolean wrap) {
        getMainContent().setLineWrap(wrap);
    }

    public void setMargin(Insets m) {
        getMainContent().setMargin(m);
    }

    /**
     * �ő啶������ݒ肵�܂��B
     * 
     * @param maxLength �ő啶����
     */
    public void setMaxLength(int maxLength) {
        getMainContent().setMaxLength(maxLength);
    }

    /**
     * �ő���͍s�� ��ݒ肵�܂��B
     * 
     * @param maxRows �ő���͍s��
     */
    public void setMaxRows(int maxRows) {
        getMainContent().setMaxRows(maxRows);
    }

    /**
     * �ŏ���������ݒ肵�܂��B
     * 
     * @param minLength �ŏ�������
     */
    public void setMinLength(int minLength) {
        getMainContent().setMinLength(minLength);
    }

    public void setNavigationFilter(NavigationFilter filter) {
        getMainContent().setNavigationFilter(filter);
    }

    public void setOpaque(boolean isOpaque) {
        super.setOpaque(isOpaque);
        getMainContent().setOpaque(isOpaque);
    }

    public void setRows(int rows) {
        getMainContent().setRows(rows);
    }

    public void setSelectedTextColor(Color c) {
        getMainContent().setSelectedTextColor(c);
    }

    public void setSelectionColor(Color c) {
        getMainContent().setSelectionColor(c);
    }

    public void setSelectionEnd(int selectionEnd) {
        getMainContent().setSelectionEnd(selectionEnd);
    }

    public void setSelectionStart(int selectionStart) {
        getMainContent().setSelectionStart(selectionStart);
    }

    public void setSource(VRBindSource source) {
        getMainContent().setSource(source);
    }

    public void setTabSize(int size) {
        getMainContent().setTabSize(size);

    }

    /**
     * ���͒l��ݒ肵�܂��B
     * 
     * @param text ���͒l
     */
    public void setText(String text) {
        getMainContent().setText(text);
    }

    public void setWrapStyleWord(boolean word) {
        getMainContent().setWrapStyleWord(word);
    }

    public int viewToModel(Point pt) {
        return getMainContent().viewToModel(pt);
    }

    public void write(Writer out) throws IOException {
        getMainContent().write(out);
    }

    /**
     * �R���|�[�l���g�����������܂��B
     */
    protected void initComponent() {
        super.initComponent();
        //���������̃X�N���[���o�[�͏�ɕ\��
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * ������區�� �𐶐����ĕԂ��܂��B
     * 
     * @return ������區��
     */
    protected ACRowMaximumableTextArea createMainContent() {
        return new IkenshoACRowMaximumableTextArea();
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     */
    protected void fireApplySource(VRBindEvent e) {
        if (e.getSource() == getMainContent()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applySource(e);
        }
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������bindSource�C�x���g���Ăяo���܂��B
     */
    protected void fireBindSource(VRBindEvent e) {
        if (e.getSource() == getMainContent()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
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
        if (e.getSource() == getMainContent()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
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
        if (e.getSource() == getMainContent()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }

        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatValid(e);
        }
    }

    /**
     * ������區�� ��Ԃ��܂��B
     * 
     * @return ������區��
     */
    protected ACRowMaximumableTextArea getMainContent() {
        if (mainContent == null) {
            mainContent = createMainContent();
            mainContent.addFormatEventListener(this);
            mainContent.addBindEventListener(this);
        }
        return mainContent;
    }

    protected JComponent createJView() {
        return getMainContent();
    }
    
    
    
    /**
     * �w�肳�ꂽFont�ARow,Col,�Ƀ}�b�`����悤�T�C�Y��ύX
     * �E
     */
    public void fitTextArea() {
    	
    	int width = getColumns();
    	int height = getRows();
    	
// [ID:0000793][Satoshi Tokusari] 2014/10 edit-Start ���͉�ʂƈ���̕\���s��v�Ή�
//    	FontMetrics fo = getFontMetrics(getFont());
//    	setPreferredSize(new Dimension(fo.charWidth('m') *	(width / 2) + 76, fo.getHeight() * height + 6));
        if (ACOSInfo.isMac()) {
            FontMetrics fo = getFontMetrics(getFont());
            setPreferredSize(new Dimension(fo.charWidth('m') *	(width / 2) + 76, fo.getHeight() * height + 6));
            return;
        }
        FontMetrics fo = getFontMetrics(getMainContent().getFont());
        setPreferredSize(new Dimension(fo.charWidth('m') *	width + 26, fo.getHeight() * height + 6));
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
    }
    
    
    
//    /**
//     * �ϊ��Ώۂ̕������ ��Ԃ��܂��B
//     * @return �ϊ��Ώۂ̕������
//     */
//    public int getConvertFromCharacter() {
//            return getMainContent().getConvertFromCharacter();
//    }
//
//    /**
//     * �ϊ����ʂ̕������ ��Ԃ��܂��B
//     * @return �ϊ����ʂ̕������
//     */
//    public int getConvertToCharacter() {
//        return getMainContent().getConvertToCharacter();
//    }
//
//    /**
//     * �ϊ��Ώۂ̕������ ��ݒ肵�܂��B
//     * @param fromCharacter �ϊ��Ώۂ̕������
//     */
//    public void setConvertFromCharacter(int fromCharacter) {
//        getMainContent().setConvertFromCharacter(fromCharacter);
//    }
//    
//    /**
//     * �ϊ����ʂ̕������ ��ݒ肵�܂��B
//     * @param toCharacter �ϊ����ʂ̕������
//     */
//    public void setConvertToCharacter(int toCharacter) {
//        getMainContent().setConvertToCharacter(toCharacter);
//    }

}
