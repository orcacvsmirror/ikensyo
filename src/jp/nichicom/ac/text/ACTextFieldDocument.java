package jp.nichicom.ac.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.text.VRTextFieldDocument;

/**
 * テキストフィールド用のドキュメントクラスです。
 * <p>
 * 改行文字を自動除去します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTextFieldDocument
 */

public class ACTextFieldDocument extends VRTextFieldDocument {
    /**
     * コンストラクタです。
     * 
     * @param textField 検査対象のテキストフィールド
     */
    public ACTextFieldDocument(AbstractVRTextField textField) {
        super(textField);
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
        // 2006/02/08[Tozo Tanaka] : replace begin
        // super.insertString(offset, str.replaceAll("\n", ""), attr);
        if(str!=null){
            //VT(垂直タブ)と改行は除去
            str = str.replaceAll("\u000b", "").replaceAll(
                    "\n", "");
        }
        super.insertString(offset, str, attr);
        // 2006/02/08[Tozo Tanaka] : replace end
    }

}
