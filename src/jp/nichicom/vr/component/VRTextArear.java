/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.TextComponent;
import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.text.AbstractVRTextDocument;
import jp.nichicom.vr.text.VRCharType;

/**
 * バインド機構を実装したテキストエリアインターフェースです。
 * <p>
 * AbstractVRTextDocumentの導入によって入力可能な文字種別や最小・最大文字列長を制限する機能を実装しています。
 * </p>
 * <p>
 * InputSubset指定によるIMEモード制御を実装しています。
 * </p>
 * <p>
 * Format指定による入力値のフォーマット変換処理を実装しています。
 * </p>
 * <p>
 * タブ文字の入力可否制御を実装しています。
 * </p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JTextArea
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see InputSubset
 * @see Format
 * @see VRFormatEventListener
 * @see VRCharType
 * @see AbstractVRTextDocument
 */
public interface VRTextArear extends VRJTextComponentar, VRBindable, VRFormatable {
    /**
     * フォーマットイベントリスナを追加します。
     * 
     * @param listener フォーマットイベントリスナ
     */
    public void addFormatEventListener(VRFormatEventListener listener);

    /**
     * Appends the given text to the end of the document. Does nothing if the
     * model is null or the string is null or empty.
     * <p>
     * This method is thread safe, although most Swing methods are not. Please
     * see <A
     * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     * 
     * @param str the text to insert
     * @see #insert
     */
    public void append(String str);

    /**
     * 文字種制を返します。
     * 
     * @return 文字種制限
     */
    public VRCharType getCharType();

    /**
     * Returns the number of columns in the TextArea.
     * 
     * @return number of columns >= 0
     */
    public int getColumns();

    /**
     * フォーマットを返します。
     * 
     * @return フォーマット
     */
    public Format getFormat();

    /**
     * Determines the number of lines contained in the area.
     * 
     * @return the number of lines > 0
     */
    public int getLineCount();

    /**
     * Determines the offset of the end of the given line.
     * 
     * @param line the line >= 0
     * @return the offset >= 0
     * @exception BadLocationException Thrown if the line is less than zero or
     *                greater or equal to the number of lines contained in the
     *                document (as reported by getLineCount).
     */
    public int getLineEndOffset(int line) throws BadLocationException;

    /**
     * Translates an offset into the components text to a line number.
     * 
     * @param offset the offset >= 0
     * @return the line number >= 0
     * @exception BadLocationException thrown if the offset is less than zero or
     *                greater than the document length.
     */
    public int getLineOfOffset(int offset) throws BadLocationException;

    /**
     * Determines the offset of the start of the given line.
     * 
     * @param line the line number to translate >= 0
     * @return the offset >= 0
     * @exception BadLocationException thrown if the line is less than zero or
     *                greater or equal to the number of lines contained in the
     *                document (as reported by getLineCount).
     */
    public int getLineStartOffset(int line) throws BadLocationException;

    /**
     * Gets the line-wrapping policy of the text area. If set to true the lines
     * will be wrapped if they are too long to fit within the allocated width.
     * If set to false, the lines will always be unwrapped.
     * 
     * @return if lines will be wrapped
     */
    public boolean getLineWrap();

    /**
     * 最大文字数を返します。
     * 
     * @return 最大文字数
     */
    public int getMaxLength();

    /**
     * 最小文字数を返します。
     * 
     * @return 最小文字数
     */
    public int getMinLength();

    /**
     * モデルデータを返します。
     * 
     * @return モデルデータ
     */
    public Object getModel();

    /**
     * Returns the number of rows in the TextArea.
     * 
     * @return the number of rows >= 0
     */
    public int getRows();

    /**
     * Gets the number of characters used to expand tabs. If the document is
     * null or doesn't have a tab setting, return a default of 8.
     * 
     * @return the number of characters
     */
    public int getTabSize();

    /**
     * Gets the style of wrapping used if the text area is wrapping lines. If
     * set to true the lines will be wrapped at word boundaries (ie whitespace)
     * if they are too long to fit within the allocated width. If set to false,
     * the lines will be wrapped at character boundaries.
     * 
     * @return if the wrap style should be word boundaries instead of character
     *         boundaries
     * @see #setWrapStyleWord
     */
    public boolean getWrapStyleWord();

    /**
     * Inserts the specified text at the specified position. Does nothing if the
     * model is null or if the text is null or empty.
     * <p>
     * This method is thread safe, although most Swing methods are not. Please
     * see <A
     * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     * 
     * @param str the text to insert
     * @param pos the position at which to insert >= 0
     * @exception IllegalArgumentException if pos is an invalid position in the
     *                model
     * @see TextComponent#setText
     * @see #replaceRange
     */
    public void insert(String str, int pos);

    /**
     * 最大文字列長を文字数ではなくバイト数で判断するか を返します。
     * 
     * @return 最大文字列長をバイト数で判断するか
     */
    public boolean isByteMaxLength();

    /**
     * フォーカス取得時に文字列を全選択するか を返します。
     * 
     * @return フォーカス取得時に文字列を全選択するか
     */
    public boolean isFocusSelcetAll();

    /**
     * タブ文字の挿入を許可するかを返します。
     * 
     * @return タブ文字の挿入を許可するか
     */
    public boolean isInsertTab();

    /**
     * フォーマットイベントリスナを削除します。
     * 
     * @param listener フォーマットイベントリスナ
     */
    public void removeFormatEventListener(VRFormatEventListener listener);

    /**
     * Replaces text from the indicated start to end position with the new text
     * specified. Does nothing if the model is null. Simply does a delete if the
     * new string is null or empty.
     * <p>
     * This method is thread safe, although most Swing methods are not. Please
     * see <A
     * HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     * 
     * @param str the text to use as the replacement
     * @param start the start position >= 0
     * @param end the end position >= start
     * @exception IllegalArgumentException if part of the range is an invalid
     *                position in the model
     * @see #insert
     * @see #replaceRange
     */
    public void replaceRange(String str, int start, int end);

    /**
     * バインドパスを設定します。
     * 
     * @param bindPath バインドパス
     */
    public void setBindPath(String bindPath);

    /**
     * 最大文字列長を文字数ではなくバイト数で判断するか を設定します。
     * 
     * @param byteMaxLength 最大文字列長をバイト数で判断するか
     */
    public void setByteMaxLength(boolean byteMaxLength);

    /**
     * 文字種制を設定します。
     * 
     * @param charType 文字種制限
     */
    public void setCharType(VRCharType charType);

    /**
     * Sets the number of columns for this TextArea. Does an invalidate() after
     * setting the new value.
     * 
     * @param columns the number of columns >= 0
     * @exception IllegalArgumentException if columns is less than 0
     * @see #getColumns
     */
    public void setColumns(int columns);

    /**
     * フォーカス取得時に文字列を全選択するか を設定します。
     * 
     * @param focusSelcetAll フォーカス取得時に文字列を全選択するか
     */
    public void setFocusSelcetAll(boolean focusSelcetAll);

    /**
     * フォーマットを設定します。
     * 
     * @param format フォーマット
     */
    public void setFormat(Format format);

    /**
     * タブ文字の挿入を許可するかを設定します。
     * 
     * @param insertTab タブ文字の挿入を許可するか
     */
    public void setInsertTab(boolean insertTab);

    /**
     * Sets the line-wrapping policy of the text area. If set to true the lines
     * will be wrapped if they are too long to fit within the allocated width.
     * If set to false, the lines will always be unwrapped. A
     * <code>PropertyChange</code> event ("lineWrap") is fired when the policy
     * is changed. By default this property is false.
     * 
     * @param wrap indicates if lines should be wrapped
     * @see #getLineWrap
     */
    public void setLineWrap(boolean wrap);

    /**
     * 最大文字数を設定します。
     * 
     * @param maxLength 最大文字数
     */
    public void setMaxLength(int maxLength);

    /**
     * 最小文字数を設定します。
     * 
     * @param minLength 最小文字数
     */
    public void setMinLength(int minLength);

    /**
     * Sets the number of rows for this TextArea. Calls invalidate() after
     * setting the new value.
     * 
     * @param rows the number of rows >= 0
     * @exception IllegalArgumentException if rows is less than 0
     * @see #getRows
     */
    public void setRows(int rows);

    /**
     * Sets the number of characters to expand tabs to. This will be multiplied
     * by the maximum advance for variable width fonts. A PropertyChange event
     * ("tabSize") is fired when the tab size changes.
     * 
     * @param size number of characters to expand to
     * @see #getTabSize
     */
    public void setTabSize(int size);

    /**
     * Sets the style of wrapping used if the text area is wrapping lines. If
     * set to true the lines will be wrapped at word boundaries (whitespace) if
     * they are too long to fit within the allocated width. If set to false, the
     * lines will be wrapped at character boundaries. By default this property
     * is false.
     * 
     * @param word indicates if word boundaries should be used for line wrapping
     * @see #getWrapStyleWord
     */
    public void setWrapStyleWord(boolean word);

}
