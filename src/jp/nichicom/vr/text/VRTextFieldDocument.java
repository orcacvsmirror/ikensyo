/** TODO <HEAD> */
package jp.nichicom.vr.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.component.AbstractVRTextField;

/**
 * テキストフィールド用の入力編集ドキュメントです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRTextDocument
 * @see AbstractVRTextField
 */
public class VRTextFieldDocument extends AbstractVRTextDocument {

    /**
     * コンストラクタです。
     * 
     * @param textField 検査対象のテキストフィールド
     */
    public VRTextFieldDocument(AbstractVRTextField textField) {
        super();
        setTextComponent(textField);
    }

    /**
     * 関連付けされたテキストフィールドを返します。
     * 
     * @return 関連付けされたテキストフィールド
     */
    public AbstractVRTextField getTextField() {
        return (AbstractVRTextField) getTextComponent();
    }

    /**
     * 文字列挿入処理です。
     * 
     * @param offset オフセット
     * @param str 文字列
     * @param attr 属性
     */
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (isInsertProcessDisabled(offset, str, attr)) {
            return;
        }

        AbstractVRTextField field = getTextField();
        if (field instanceof AbstractVRTextField) {
            //最大文字数チェック
            str = getMaxLengthCheckedText(str, field.getMaxLength(), field
                    .getText(), field.isByteMaxLength());
            if (str == null) {
                return;
            }

            //文字種別チェック
            if (isErrorCharType(offset, str, field.getCharType(), field
                    .getText())) {
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
            if (!getTextField().hasFocus()) {
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
     * @param offset オフセット
     * @param length 文字数
     * @return 最小文字数制限に違反しているか
     */
    protected boolean isMinimumLengthError(int offset, int length){
        int len = getTextField().getMinLength();
        if ((len > 0) && (len >= getTextField().getText().length())) {
            return true;
        }
        return false;
    }
}