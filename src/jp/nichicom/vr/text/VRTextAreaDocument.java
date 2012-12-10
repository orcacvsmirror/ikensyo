/** TODO <HEAD> */
package jp.nichicom.vr.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.component.AbstractVRTextArea;

/**
 * テキストエリア用の入力編集ドキュメントです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRTextDocument
 * @see AbstractVRTextArea
 */
public class VRTextAreaDocument extends AbstractVRTextDocument {

    /**
     * コンストラクタです。
     * 
     * @param textArea 検査対象のテキストフィールド
     */
    public VRTextAreaDocument(AbstractVRTextArea textArea) {
        super();
        setTextComponent(textArea);
    }

    /**
     * 関連付けされたテキストフィールドを返します。
     * 
     * @return 関連付けされたテキストフィールド
     */
    public AbstractVRTextArea getTextArea() {
        return (AbstractVRTextArea) getTextComponent();
    }

    /**
     * 文字列挿入処理です。
     * 
     * @param offset オフセット
     * @param str 文字列
     * @param attr 属性
     * @throws BadLocationException 処理例外
     */
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (isInsertProcessDisabled(offset, str, attr)) {
            return;
        }

        AbstractVRTextArea area = getTextArea();
        if (area instanceof AbstractVRTextArea) {

            //最大文字数チェック
            str = getMaxLengthCheckedText(str, area.getMaxLength(), area
                    .getText(), area.isByteMaxLength());
            if (str == null) {
                return;
            }

            //文字種別チェック
            if (isErrorCharType(offset, str, area.getCharType(), area.getText())) {
                return;
            }
        }
        super.insertString(offset, str, attr);
    }

    /**
     * 文字列削除処理です。
     * 
     * @param offset オフセット
     * @param length 文字数
     */
    public void remove(int offset, int length) throws BadLocationException {
        if (!isAbsoluteEditable()) {
            if (!getTextArea().hasFocus()) {
                return;
            }
        }

        //最小文字数チェック
        if (isMinimumLengthError(offset, length)) {
            return;
        }
        super.remove(offset, length);
    }

    /**
     * 最小文字数制限に違反しているかを返しますします。
     * 
     * @param offset オフセット
     * @param length 文字数
     * @return 最小文字数制限に違反しているか
     */
    protected boolean isMinimumLengthError(int offset, int length) {
        int len = getTextArea().getMinLength();
        if ((len > 0) && (len >= getTextArea().getText().length())) {
            return true;
        }
        return false;
    }

}