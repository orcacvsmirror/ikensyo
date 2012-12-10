package jp.nichicom.ac.component;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyleConstants;

import jp.nichicom.ac.text.ACTextFieldDocument;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.text.VRTextFieldDocument;


/**
 * かな入力を自動転記するテキストフィールドです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 *
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 * @see ACTextField
 */
public class ACKanaSendTextField extends ACTextField {
    protected AbstractVRTextField kanaField;

    public ACKanaSendTextField() {
        super();
    }

    public ACKanaSendTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public ACKanaSendTextField(int columns) {
        super(columns);
    }

    public ACKanaSendTextField(String text) {
        super(text);
    }

    public ACKanaSendTextField(String text, int columns) {
        super(text, columns);
    }

    protected Document createDocument() {
        return new NCKanaSendTextFieldDocument(this);
    }

    /**
     * 仮名送信先のテキストフィールドを返します。
     * @return 仮名送信先のテキストフィールド
     */
    public AbstractVRTextField getKanaField() {
        return kanaField;
    }

    /**
     * 仮名送信先のテキストフィールドを設定します。
     * @param kanaField 仮名送信先のテキストフィールド
     */
    public void setKanaField(AbstractVRTextField kanaField) {
        this.kanaField = kanaField;
    }

    /**
     * かな入力のみを転記するドキュメントクラスです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     *
     * @author Mizuki Tsutsumi
     * @version 1.0 2005/10/31
     * @see ACTextFieldDocument
     */
    protected class NCKanaSendTextFieldDocument extends ACTextFieldDocument {

        private final int[] HALF_KANA_ARRAY = new int[] {
            0x3002, //｡。
            0x300c, //｢「
            0x300d, //｣」
            0x3001, //､、
            0x30fb, //･・
            0x3092, //ｦヲ
            0x3041, //ｧァ
            0x3043, //ｨィ
            0x3045, //ｩゥ
            0x3047, //ｪェ
            0x3049, //ｫォ
            0x3083, //ｬャ
            0x3085, //ｭュ
            0x3087, //ｮョ
            0x3063, //ｯッ
            0x30fc, //ｰー
            0x3042, //ｱア
            0x3044, //ｲイ
            0x3046, //ｳウ
            0x3048, //ｴエ
            0x304a, //ｵオ
            0x304b, //ｶカ
            0x304d, //ｷキ
            0x304f, //ｸク
            0x3051, //ｹケ
            0x3053, //ｺコ
            0x3055, //ｻサ
            0x3057, //ｼシ
            0x3059, //ｽス
            0x305b, //ｾセ
            0x305d, //ｿソ
            0x305f, //ﾀタ
            0x3061, //ﾁチ
            0x3064, //ﾂツ
            0x3066, //ﾃテ
            0x3068, //ﾄト
            0x306a, //ﾅナ
            0x306b, //ﾆニ
            0x306c, //ﾇヌ
            0x306d, //ﾈネ
            0x306e, //ﾉノ
            0x306f, //ﾊハ
            0x3072, //ﾋヒ
            0x3075, //ﾌフ
            0x3078, //ﾍヘ
            0x307b, //ﾎホ
            0x307e, //ﾏマ
            0x307f, //ﾐミ
            0x3080, //ﾑム
            0x3081, //ﾒメ
            0x3082, //ﾓモ
            0x3084, //ﾔヤ
            0x3086, //ﾕユ
            0x3088, //ﾖヨ
            0x3089, //ﾗラ
            0x308a, //ﾘリ
            0x308b, //ﾙル
            0x308c, //ﾚレ
            0x308d, //ﾛロ
            0x308f, //ﾜワ
            0x3093, //ﾝン
            0x309b, //ﾞ゛
            0x309c, //ﾟ゜

        };

        /**
         * コンストラクタです。
         *
         * @param textField 検査対象のテキストフィールド
         */
        public NCKanaSendTextFieldDocument(AbstractVRTextField textField) {
            super(textField);

        }

        /**
         * 文字列削除処理です。
         *
         * @param offset オフセット
         * @param length 文字数
         * @throws BadLocationException
         */
        public void remove(int offset, int length) throws BadLocationException {
            super.remove(offset, length);

            //TextFieldクリア時、KanaFieldもクリアする
            if (getTextField().hasFocus()) {
                if (getTextField().getText().length() == 0) {
                  if (getKanaField() != null) {
                    VRTextFieldDocument doc = null;
                    if (getKanaField().getDocument() instanceof VRTextFieldDocument) {
                      doc = (VRTextFieldDocument) getKanaField().getDocument();
                      doc.setAbsoluteEditable(true);
                    }
                    getKanaField().setText("");
                    if (doc != null) {
                      doc.setAbsoluteEditable(false);
                    }
                  }
                }
            }
        }

        private String imeString = new String();
        private String strOld = "";
        public String inputString = new String();
        private String missingString = new String();
        boolean hasKanji = false;
        boolean hasKanjiOld = false;

        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (isInsertDisabled(offset, str, attr)) {
                return;
            }

            //IME変換中チェック
            if ( (attr != null) &&
                (attr.isDefined(StyleConstants.ComposedTextAttribute))) {
                super.insertString(offset, str, attr);

                //退避するかどうかの判定
                hasKanji = checkIncludedKanji(str);
                if (!hasKanji) {
                    imeString = str;
                }

                //ひらがなから漢字への変換のタイミングではチェックしない
                boolean chkFlg = true;
                if (hasKanji) {
                    if (!hasKanjiOld) {
                        chkFlg = false;
                    }
                }

                //文字が削られたときの処理
                int pos = -1;
                String tmp = imeString;
                if (chkFlg) {
                    pos = checkMissingKana(str, inputString);

                    //edit sta 2005/11/19 tsutsumi
                    //削除部位より前の部分の文字列の変化をチェック
                    if (pos > 0) {
                      if (str.substring(0, pos).equals(strOld.substring(0, pos))) {
                          //削除部位よりも前の部分が変化していない場合、削除されたとする
                          tmp = deleteMissingKana(imeString, missingString, pos);
                      }
                      else {
                          //削除部位よりも前の部分が変化している場合、変換によって別の文字になった(送り仮名があるものから、送り仮名がないものになった、など)
                      }
                    }
//                    tmp = deleteMissingKana(imeString, missingString, pos);
                    //edit end 2005/11/19 tsutsumi
                }

                //一時変数更新
                strOld = str;
                imeString = tmp;
                inputString = str;
                hasKanjiOld = hasKanji;

                return;
            }

            //最大文字数チェック
            int len = getTextField().getMaxLength();
            if (len > 0) {
                int txtLen = getTextField().getText().length();
                if (len < txtLen) {
                    return;
                }
                if (len < txtLen + str.length()) {
                    //間引く
                    str = str.substring(0, len - txtLen);
                }
            }

            //文字種別チェック
            VRCharType chrType = getTextField().getCharType();
            if ( (chrType != null) && (!chrType.isMatch(str))) {
                return;
            }

            //全角スペース対応
            if (str.equals("　")) {
                getKanaField().setText(getKanaField().getText() + str);

                imeString = "";
            }

            //ひらがなをかなTextFieldに追加する & リセット
            if (getKanaField() != null) {
                if (!imeString.equals("")) {
                    if (getTextField().hasFocus()) {
                        getKanaField().setText(getKanaField().getText() +
                                               convToHiragana(imeString));

                        imeString = "";
                        inputString = "";
                        hasKanjiOld = false;
                    }
                }
            }

            super.insertString(offset, str, attr);
        }

        /**
         * 漢字が含まれているかチェック
         * 文字の種類 ブロックの範囲（16進表記）
         * ひらがな   3040 ～ 309F
         * カタカナ   30A0 ～ 30FF
         * 漢字      4E00 ～ 9FFF
         * @param str String
         * @return boolean
         */
        private boolean checkIncludedKanji(String str) {
            int len = str.length();
            for (int i = 0; i < len; i++ ) {
               if (str.charAt(i) >= 0x4E00 && str.charAt(i) <= 0x9FFF) {
                   return true;
               }
            }
            return false;
        }

        /**
         * oldの中から平仮名1文字を削った結果がnewだと仮定して、</br>
         * 無くなった平仮名1文字が何なのかを見つける
         * @param newStr 新しい。後。
         * @param oldStr 古い。前。元々。
         * @return 無くなった平仮名の位置。
         */
        private int checkMissingKana(String newStr, String oldStr) {
            missingString = "";
            String miss;
            int codeLower = 0x3000;
            int codeUpper = 0x30FF;

            //ひらがな削除 = 1文字減少
            int lenNew = newStr.length();
            int lenOld = oldStr.length();
            if (lenOld - lenNew != 1) {
                return -1;
            }

            //無くなった文字の種類と位置を探す
            for (int i = 0; i < lenNew; i++) {
                if (newStr.charAt(i) != oldStr.charAt(i)) {
                    miss = oldStr.substring(i, i+1);
                    if (miss.charAt(0) >= codeLower && miss.charAt(0) <= codeUpper) {
                        missingString = miss;
                        return i;
                    }
                }
            }

            miss = oldStr.substring(lenNew, lenNew+1);
            if (miss.charAt(0) >= codeLower && miss.charAt(0) <= codeUpper) {
                missingString = miss;
                return lenNew;
            }

            return -1;
        }

        /**
         * 平仮名文字列kanaのpos文字目以前の平仮名1文字missingを削除する。
         * @param kana String
         * @param missing String
         * @param pos int
         * @return String
         */
        private String deleteMissingKana(String kana, String missing, int pos) {
            if (pos < 0) {
                return kana;
            }
            if (pos > kana.length() - 1) {
                return kana;
            }
            if (missing.equals("")) {
                return kana;
            }

            int point = -1;
            for (int i = pos; i < kana.length(); i++ ) {
                if (kana.substring(i, i+1).equals(missing)) {
                    point = i;
                    break;
                }
            }
            if (point < 0) {
                return kana;
            }

            //文字列をStringBufferに格納
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < kana.length(); i++ ) {
                if (i != point) {
                    sb.append(kana.substring(i, i+1));
                }
            }

            return sb.toString();
        }

        /**
         * カタカナや半角文字などを、全角ひらがなに置換する
         * @param str 入力
         * @return 全角ひらがな
         */
        private String convToHiragana(String str) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length(); i++ ) {
                char tgt = str.charAt(i);
                char fst = 0;
                char snd = 0;

                if ((tgt >= 0x3000) && (tgt <= 0x303F)) {
                    fst = tgt;
                }
                //全角かな
                else if ((tgt >= 0x3041) && (tgt <= 0x309F)) {
                    fst = tgt;
                }
                //全角カナ
                else if ( (tgt >= 0x30A1) && (tgt <= 0x30FF)) {
                    fst = (char)((int)tgt - 0x0060);

                    //例外
                    if (tgt == 0x30F4) { //ヴ (0x3094はやめとく)
                        fst = 0x3046; //う
                        snd = 0x309B; //゛
                    }
                    else if (tgt == 0x30F7) { //ワ゛
                        fst = 0x308F; //わ
                        snd = 0x309B; //゛
                    }
                    else if (tgt == 0x30F8) { //ヰ゛
                        fst = 0x3090; //ゐ
                        snd = 0x309B; //゛
                    }
                    else if (tgt == 0x30F9) { //ヱ゛
                        fst = 0x3091; //ゑ
                        snd = 0x309B; //゛
                    }
                    else if (tgt == 0x30FA) { //ヲ゛
                        fst = 0x3092; //を
                        snd = 0x309B; //゛
                    }
                    else if (tgt == 0x30FB) { //・
                        fst = tgt; //・
                    }
                    else if (tgt == 0x30FC) { //ー
                        fst = tgt; //ー
                    }
                }
                //半角空白
                else if (tgt == 0x0020) {
                    fst = '　';
                }
                //半角英数
                else if ( (tgt >= 0x0021) && (tgt <= 0x007F)) {
                    fst = (char)((int)tgt + 0xFEE0);
                }
                //半角カナ
                else if ( (tgt >= 0xFF61) && (tgt <= 0xFF9F)) {
                    fst = (char) HALF_KANA_ARRAY[(int)tgt - 0xFF61];
                }
                //その他
                else {
                    fst = tgt;
                }

                //バッファに追加
                if (fst != 0) {
                    sb.append(fst);
                }
                if (snd != 0) {
                    sb.append(snd);
                }
            }

            return sb.toString();
        }
    }
}
