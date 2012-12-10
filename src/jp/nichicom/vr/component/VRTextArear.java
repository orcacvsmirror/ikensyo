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
 * �o�C���h�@�\�����������e�L�X�g�G���A�C���^�[�t�F�[�X�ł��B
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
 * �^�u�����̓��͉ې�����������Ă��܂��B
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
     * �t�H�[�}�b�g�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
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
     * �����퐧��Ԃ��܂��B
     * 
     * @return �����퐧��
     */
    public VRCharType getCharType();

    /**
     * Returns the number of columns in the TextArea.
     * 
     * @return number of columns >= 0
     */
    public int getColumns();

    /**
     * �t�H�[�}�b�g��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
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
     * �ő啶������Ԃ��܂��B
     * 
     * @return �ő啶����
     */
    public int getMaxLength();

    /**
     * �ŏ���������Ԃ��܂��B
     * 
     * @return �ŏ�������
     */
    public int getMinLength();

    /**
     * ���f���f�[�^��Ԃ��܂��B
     * 
     * @return ���f���f�[�^
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
     * �ő啶���񒷂𕶎����ł͂Ȃ��o�C�g���Ŕ��f���邩 ��Ԃ��܂��B
     * 
     * @return �ő啶���񒷂��o�C�g���Ŕ��f���邩
     */
    public boolean isByteMaxLength();

    /**
     * �t�H�[�J�X�擾���ɕ������S�I�����邩 ��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���ɕ������S�I�����邩
     */
    public boolean isFocusSelcetAll();

    /**
     * �^�u�����̑}���������邩��Ԃ��܂��B
     * 
     * @return �^�u�����̑}���������邩
     */
    public boolean isInsertTab();

    /**
     * �t�H�[�}�b�g�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
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
     * �o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     */
    public void setBindPath(String bindPath);

    /**
     * �ő啶���񒷂𕶎����ł͂Ȃ��o�C�g���Ŕ��f���邩 ��ݒ肵�܂��B
     * 
     * @param byteMaxLength �ő啶���񒷂��o�C�g���Ŕ��f���邩
     */
    public void setByteMaxLength(boolean byteMaxLength);

    /**
     * �����퐧��ݒ肵�܂��B
     * 
     * @param charType �����퐧��
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
     * �t�H�[�J�X�擾���ɕ������S�I�����邩 ��ݒ肵�܂��B
     * 
     * @param focusSelcetAll �t�H�[�J�X�擾���ɕ������S�I�����邩
     */
    public void setFocusSelcetAll(boolean focusSelcetAll);

    /**
     * �t�H�[�}�b�g��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format);

    /**
     * �^�u�����̑}���������邩��ݒ肵�܂��B
     * 
     * @param insertTab �^�u�����̑}���������邩
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
     * �ő啶������ݒ肵�܂��B
     * 
     * @param maxLength �ő啶����
     */
    public void setMaxLength(int maxLength);

    /**
     * �ŏ���������ݒ肵�܂��B
     * 
     * @param minLength �ŏ�������
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
