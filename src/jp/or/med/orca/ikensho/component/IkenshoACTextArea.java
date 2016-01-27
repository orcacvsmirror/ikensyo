package jp.or.med.orca.ikensho.component;

import java.awt.Color;
import java.awt.Dimension;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start 入力画面と印刷の表示不一致対応
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

// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start 入力画面と印刷の表示不一致対応
import jp.nichicom.ac.ACOSInfo;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.container.AbstractACScrollPane;
// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start 入力画面と印刷の表示不一致対応
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
 * スクロールペイン一体型のテキストエリアです。
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
     * コンストラクタです。
     */
    public IkenshoACTextArea() {
        super();
// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start 入力画面と印刷の表示不一致対応
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End
    }

    /**
     * コンストラクタです。
     * 
     * @param doc 入力制御ドキュメント
     * @param text テキスト値
     * @param rows 行数
     * @param columns 列数
     */
    public IkenshoACTextArea(Document doc, String text, int rows, int columns) {
        this();
        getMainContent().setDocument(doc);
        getMainContent().setText(text);
        getMainContent().setRows(rows);
        getMainContent().setColumns(columns);
        
    }

    /**
     * コンストラクタです。
     * 
     * @param rows 行数
     * @param columns 列数
     */
    public IkenshoACTextArea(int rows, int columns) {
        this();
        getMainContent().setRows(rows);
        getMainContent().setColumns(columns);
    }

    /**
     * コンストラクタです。
     * 
     * @param text テキスト値
     */
    public IkenshoACTextArea(String text) {
        this();
        getMainContent().setText(text);
    }

    /**
     * コンストラクタです。
     * 
     * @param text テキスト値
     * @param rows 行数
     * @param columns 列数
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

// [ID:0000793][Satoshi Tokusari] 2014/10 add-Start 入力画面と印刷の表示不一致対応
    /**
     * コンポーネントを初期化します。
     *
     * @throws Exception 初期化例外
     */
    private void jbInit() throws Exception {
        // Windowsの場合は、MSゴシックの使用を強制し、LineSpaceを詰める
        if (!ACOSInfo.isMac()) {
            Font oldFont = getFont();
            if (oldFont == null) {
                getMainContent().setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
            }
            else {
            	ACFrame frame = ACFrame.getInstance();
                getMainContent().setFont(new Font("ＭＳ ゴシック", oldFont.getStyle(), frame.isMiddle() ? 14 : oldFont.getSize()));
            }
        }
    }
// [ID:0000793][Satoshi Tokusari] 2014/10 add-End

    /**
     * フォーマットイベントリスナを追加します。
     * 
     * @param listener フォーマットイベントリスナ
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
     * バインドイベントリスナを返します。
     * 
     * @return バインドイベントリスナ
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
     * 文字種制を返します。
     * 
     * @return 文字種制限
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
     * フォーマットを返します。
     * 
     * @return フォーマット
     */
    public Format getFormat() {
        return getMainContent().getFormat();
    }

    /**
     * フォーマットイベントリスナを返します。
     * 
     * @return フォーマットイベントリスナ
     */
    public synchronized VRFormatEventListener[] getFormatEventListeners() {
        return (VRFormatEventListener[]) (getListeners(VRFormatEventListener.class));
    }

    public Highlighter getHighlighter() {
        return getMainContent().getHighlighter();
    }

    /**
     * IMEモードを返します。
     * 
     * @return フォーカス取得時に自動設定するIMEモード
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
     * 最大文字数を返します。
     * 
     * @return 最大文字数
     */
    public int getMaxLength() {
        return getMainContent().getMaxLength();
    }

    /**
     * 最大入力行数 を返します。
     * 
     * @return 最大入力行数
     */
    public int getMaxRows() {
        return getMainContent().getMaxRows();
    }

    /**
     * 最小文字数を返します。
     * 
     * @return 最小文字数
     */
    public int getMinLength() {
        return getMainContent().getMinLength();
    }

    /**
     * モデルデータを返します。
     * 
     * @return モデルデータ
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
     * 入力値を返します。
     * 
     * @return 入力値
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
     * フォーカス取得時に文字列を全選択するか を返します。
     * 
     * @return フォーカス取得時に文字列を全選択するか
     */
    public boolean isFocusSelcetAll() {
        return getMainContent().isFocusSelcetAll();
    }

    /**
     * タブ文字の挿入を許可するかを返します。
     * 
     * @return タブ文字の挿入を許可するか
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
     * フォーマットイベントリスナを削除します。
     * 
     * @param listener フォーマットイベントリスナ
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
     * 文字種制を設定します。
     * 
     * @param charType 文字種制限
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
     * フォーカス取得時に文字列を全選択するか を設定します。
     * 
     * @param focusSelcetAll フォーカス取得時に文字列を全選択するか
     */
    public void setFocusSelcetAll(boolean focusSelcetAll) {
        getMainContent().setFocusSelcetAll(focusSelcetAll);
    }

    /**
     * フォーマットを設定します。
     * 
     * @param format フォーマット
     */
    public void setFormat(Format format) {
        getMainContent().setFormat(format);
    }

    public void setHighlighter(Highlighter h) {
        getMainContent().setHighlighter(h);
    }

    /**
     * IMEモードを設定します。
     * 
     * @param imeMode フォーカス取得時に自動設定するIMEモード
     */
    public void setIMEMode(InputSubset imeMode) {
        getMainContent().setIMEMode(imeMode);
    }

    /**
     * タブ文字の挿入を許可するかを設定します。
     * 
     * @param insertTab タブ文字の挿入を許可するか
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
     * 最大文字数を設定します。
     * 
     * @param maxLength 最大文字数
     */
    public void setMaxLength(int maxLength) {
        getMainContent().setMaxLength(maxLength);
    }

    /**
     * 最大入力行数 を設定します。
     * 
     * @param maxRows 最大入力行数
     */
    public void setMaxRows(int maxRows) {
        getMainContent().setMaxRows(maxRows);
    }

    /**
     * 最小文字数を設定します。
     * 
     * @param minLength 最小文字数
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
     * 入力値を設定します。
     * 
     * @param text 入力値
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
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        super.initComponent();
        //垂直方向のスクロールバーは常に表示
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    }

    /**
     * 内包した主項目 を生成して返します。
     * 
     * @return 内包した主項目
     */
    protected ACRowMaximumableTextArea createMainContent() {
        return new IkenshoACRowMaximumableTextArea();
    }

    /**
     * バインドイベントリスナを全走査してapplySourceイベントを呼び出します。
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
     * フォーマットイベントリスナを全走査してformatErrorイベントを発火します。
     * 
     * @param e イベント情報
     */
    protected void fireFormatInvalid(VRFormatEvent e) {
        if (e.getSource() == getMainContent()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatInvalid(e);
        }
    }

    /**
     * フォーマットイベントリスナを全走査してformatValidイベントを発火します。
     * 
     * @param e イベント情報
     */
    protected void fireFormatValid(VRFormatEvent e) {
        if (e.getSource() == getMainContent()) {
            // 子のイベントを置換
            e.setSource(this);
        }

        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatValid(e);
        }
    }

    /**
     * 内包した主項目 を返します。
     * 
     * @return 内包した主項目
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
     * 指定されたFont、Row,Col,にマッチするようサイズを変更
     * ・
     */
    public void fitTextArea() {
    	
    	int width = getColumns();
    	int height = getRows();
    	
// [ID:0000793][Satoshi Tokusari] 2014/10 edit-Start 入力画面と印刷の表示不一致対応
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
//     * 変換対象の文字種別 を返します。
//     * @return 変換対象の文字種別
//     */
//    public int getConvertFromCharacter() {
//            return getMainContent().getConvertFromCharacter();
//    }
//
//    /**
//     * 変換結果の文字種別 を返します。
//     * @return 変換結果の文字種別
//     */
//    public int getConvertToCharacter() {
//        return getMainContent().getConvertToCharacter();
//    }
//
//    /**
//     * 変換対象の文字種別 を設定します。
//     * @param fromCharacter 変換対象の文字種別
//     */
//    public void setConvertFromCharacter(int fromCharacter) {
//        getMainContent().setConvertFromCharacter(fromCharacter);
//    }
//    
//    /**
//     * 変換結果の文字種別 を設定します。
//     * @param toCharacter 変換結果の文字種別
//     */
//    public void setConvertToCharacter(int toCharacter) {
//        getMainContent().setConvertToCharacter(toCharacter);
//    }

}
