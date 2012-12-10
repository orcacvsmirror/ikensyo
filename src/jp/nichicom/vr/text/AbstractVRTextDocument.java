/** TODO <HEAD> */
package jp.nichicom.vr.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;

/**
 * テキスト入力編集ドキュメントの基底クラスです。
 * <p>
 * 入力可能な文字種別や最大文字列長を制限する機能を実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRCharType
 * @see DefaultStyledDocument
 */
public abstract class AbstractVRTextDocument extends DefaultStyledDocument {

    private JTextComponent textComponent;
    private boolean absoluteEditable = false;

    /**
     * 関連付けされたテキストコンポーネント を返します。
     * 
     * @return 関連付けされたテキストコンポーネント
     */
    protected JTextComponent getTextComponent() {
        return textComponent;
    }

    /**
     * 関連付けされたテキストコンポーネント を設定します。
     * 
     * @param textComponent 関連付けされたテキストコンポーネント
     */
    protected void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    /**
     * Documentの入力制限処理を無視して編集させるか を返します。
     * 
     * @return Documentの入力制限処理を無視して編集させるか
     */
    public boolean isAbsoluteEditable() {
        return absoluteEditable;
    }

    /**
     * Documentの入力制限処理を無視して編集させるか を設定します。
     * 
     * @param absoluteEditable Documentの入力制限処理を無視して編集させるか
     */
    public void setAbsoluteEditable(boolean absoluteEditable) {
        this.absoluteEditable = absoluteEditable;
    }

    /**
     * 文字列の挿入処理を禁止しているかを返します。
     * 
     * @param offset オフセット
     * @param str 文字列
     * @param attr 属性
     * @return 文字列の挿入処理を禁止しているか
     */
    public boolean isInsertDisabled(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (!isAbsoluteEditable()) {
            //文字列が入ってきていない状態でメソッドが呼ばれた場合、無視する
            if (str == null) {
                return true;
            }

            //フォーカスが無い状態で呼び出された場合、無視する
            if (!textComponent.hasFocus()) {
                return true;
            }

        }
        return false;
    }

    /**
     * 親クラスの文字列削除処理です。
     * 
     * @param offset オフセット
     * @param length 文字数
     */
    public void superRemove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
    }

    /**
     * 入力文字の処理を禁止するかを返します。
     * 
     * @param offset オフセット
     * @param str 文字列
     * @param attr 属性
     * @return 入力文字の処理を禁止するか
     * @throws BadLocationException 処理例外
     */
    protected boolean isInsertProcessDisabled(int offset, String str,
            AttributeSet attr) throws BadLocationException {
        if (isInsertDisabled(offset, str, attr)) {
            return true;
        }

        //IME変換中チェック
        if ((attr != null)
                && (attr.isDefined(StyleConstants.ComposedTextAttribute))) {
            super.insertString(offset, str, attr);
            return true;

        }
        return false;
    }

    /**
     * 最大文字数をチェックした結果、間引き補正込みの文字列を返します。
     * 
     * @param str 文字列
     * @param len 最大文字数
     * @param inputedText 入力済みの文字
     * @param isByteMaxLength 最大文字列長を文字数ではなくバイト数で判断するか
     * @return 間引き補正込みの文字列。入力済みの文字がすでに最大文字数を超えていればnull。
     */
    protected String getMaxLengthCheckedText(String str, int len,
            String inputedText, boolean isByteMaxLength) {
        //nullチェック
        if ((str == null) || (inputedText == null)) {
            return str;
        }

        //最大文字数チェック
        if (len > 0) {
            if (isByteMaxLength) {
                int byteCount = 0;
                int txtLen = inputedText.length();
                for (int i = 0; i < txtLen; i++) {
                    //バイト単位で最大文字列長を超えるまで繰り返す
                    byteCount += inputedText.substring(i, i + 1).getBytes().length;
                    if (len < byteCount) {
                        return str.substring(0, i);
                    }
                }
            } else {
                int txtLen = inputedText.length();
                if (len < txtLen) {
                    return null;
                }
                if (len < txtLen + str.length()) {
                    //間引く
                    return str.substring(0, len - txtLen);
                }
            }
        }
        return str;
    }

    /**
     * 入力禁止対象の文字を含むかを返します。
     * 
     * @param offset オフセット
     * @param str 文字列
     * @param chrType 許可文字種別
     * @param inputedText 入力済みの文字
     * @return 入力禁止対象の文字を含むか
     */
    protected boolean isErrorCharType(int offset, String str,
            VRCharType chrType, String inputedText) {
        //文字種別チェック
        if (chrType != null) {
            if (!chrType.isMatch(inputedText.substring(0, offset) + str
                    + inputedText.substring(offset))) {
                return true;
            }
        }
        return false;
    }
}