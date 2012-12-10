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
 * ���ȓ��͂������]�L����e�L�X�g�t�B�[���h�ł��B
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
     * �������M��̃e�L�X�g�t�B�[���h��Ԃ��܂��B
     * @return �������M��̃e�L�X�g�t�B�[���h
     */
    public AbstractVRTextField getKanaField() {
        return kanaField;
    }

    /**
     * �������M��̃e�L�X�g�t�B�[���h��ݒ肵�܂��B
     * @param kanaField �������M��̃e�L�X�g�t�B�[���h
     */
    public void setKanaField(AbstractVRTextField kanaField) {
        this.kanaField = kanaField;
    }

    /**
     * ���ȓ��݂͂̂�]�L����h�L�������g�N���X�ł��B
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
            0x3002, //��B
            0x300c, //��u
            0x300d, //��v
            0x3001, //��A
            0x30fb, //��E
            0x3092, //���
            0x3041, //��@
            0x3043, //��B
            0x3045, //��D
            0x3047, //��F
            0x3049, //��H
            0x3083, //���
            0x3085, //���
            0x3087, //���
            0x3063, //��b
            0x30fc, //��[
            0x3042, //��A
            0x3044, //��C
            0x3046, //��E
            0x3048, //��G
            0x304a, //��I
            0x304b, //��J
            0x304d, //��L
            0x304f, //��N
            0x3051, //��P
            0x3053, //��R
            0x3055, //��T
            0x3057, //��V
            0x3059, //��X
            0x305b, //��Z
            0x305d, //��\
            0x305f, //��^
            0x3061, //��`
            0x3064, //c
            0x3066, //Ãe
            0x3068, //ăg
            0x306a, //Ńi
            0x306b, //ƃj
            0x306c, //ǃk
            0x306d, //ȃl
            0x306e, //Ƀm
            0x306f, //ʃn
            0x3072, //˃q
            0x3075, //̃t
            0x3078, //̓w
            0x307b, //΃z
            0x307e, //σ}
            0x307f, //Ѓ~
            0x3080, //у�
            0x3081, //҃�
            0x3082, //Ӄ�
            0x3084, //ԃ�
            0x3086, //Ճ�
            0x3088, //փ�
            0x3089, //׃�
            0x308a, //؃�
            0x308b, //ك�
            0x308c, //ڃ�
            0x308d, //ۃ�
            0x308f, //܃�
            0x3093, //݃�
            0x309b, //ށJ
            0x309c, //߁K

        };

        /**
         * �R���X�g���N�^�ł��B
         *
         * @param textField �����Ώۂ̃e�L�X�g�t�B�[���h
         */
        public NCKanaSendTextFieldDocument(AbstractVRTextField textField) {
            super(textField);

        }

        /**
         * ������폜�����ł��B
         *
         * @param offset �I�t�Z�b�g
         * @param length ������
         * @throws BadLocationException
         */
        public void remove(int offset, int length) throws BadLocationException {
            super.remove(offset, length);

            //TextField�N���A���AKanaField���N���A����
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

            //IME�ϊ����`�F�b�N
            if ( (attr != null) &&
                (attr.isDefined(StyleConstants.ComposedTextAttribute))) {
                super.insertString(offset, str, attr);

                //�ޔ����邩�ǂ����̔���
                hasKanji = checkIncludedKanji(str);
                if (!hasKanji) {
                    imeString = str;
                }

                //�Ђ炪�Ȃ��犿���ւ̕ϊ��̃^�C�~���O�ł̓`�F�b�N���Ȃ�
                boolean chkFlg = true;
                if (hasKanji) {
                    if (!hasKanjiOld) {
                        chkFlg = false;
                    }
                }

                //���������ꂽ�Ƃ��̏���
                int pos = -1;
                String tmp = imeString;
                if (chkFlg) {
                    pos = checkMissingKana(str, inputString);

                    //edit sta 2005/11/19 tsutsumi
                    //�폜���ʂ��O�̕����̕�����̕ω����`�F�b�N
                    if (pos > 0) {
                      if (str.substring(0, pos).equals(strOld.substring(0, pos))) {
                          //�폜���ʂ����O�̕������ω����Ă��Ȃ��ꍇ�A�폜���ꂽ�Ƃ���
                          tmp = deleteMissingKana(imeString, missingString, pos);
                      }
                      else {
                          //�폜���ʂ����O�̕������ω����Ă���ꍇ�A�ϊ��ɂ���ĕʂ̕����ɂȂ���(���艼����������̂���A���艼�����Ȃ����̂ɂȂ����A�Ȃ�)
                      }
                    }
//                    tmp = deleteMissingKana(imeString, missingString, pos);
                    //edit end 2005/11/19 tsutsumi
                }

                //�ꎞ�ϐ��X�V
                strOld = str;
                imeString = tmp;
                inputString = str;
                hasKanjiOld = hasKanji;

                return;
            }

            //�ő啶�����`�F�b�N
            int len = getTextField().getMaxLength();
            if (len > 0) {
                int txtLen = getTextField().getText().length();
                if (len < txtLen) {
                    return;
                }
                if (len < txtLen + str.length()) {
                    //�Ԉ���
                    str = str.substring(0, len - txtLen);
                }
            }

            //������ʃ`�F�b�N
            VRCharType chrType = getTextField().getCharType();
            if ( (chrType != null) && (!chrType.isMatch(str))) {
                return;
            }

            //�S�p�X�y�[�X�Ή�
            if (str.equals("�@")) {
                getKanaField().setText(getKanaField().getText() + str);

                imeString = "";
            }

            //�Ђ炪�Ȃ�����TextField�ɒǉ����� & ���Z�b�g
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
         * �������܂܂�Ă��邩�`�F�b�N
         * �����̎�� �u���b�N�͈̔́i16�i�\�L�j
         * �Ђ炪��   3040 �` 309F
         * �J�^�J�i   30A0 �` 30FF
         * ����      4E00 �` 9FFF
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
         * old�̒����畽����1��������������ʂ�new���Ɖ��肵�āA</br>
         * �����Ȃ���������1���������Ȃ̂���������
         * @param newStr �V�����B��B
         * @param oldStr �Â��B�O�B���X�B
         * @return �����Ȃ����������̈ʒu�B
         */
        private int checkMissingKana(String newStr, String oldStr) {
            missingString = "";
            String miss;
            int codeLower = 0x3000;
            int codeUpper = 0x30FF;

            //�Ђ炪�ȍ폜 = 1��������
            int lenNew = newStr.length();
            int lenOld = oldStr.length();
            if (lenOld - lenNew != 1) {
                return -1;
            }

            //�����Ȃ��������̎�ނƈʒu��T��
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
         * ������������kana��pos�����ڈȑO�̕�����1����missing���폜����B
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

            //�������StringBuffer�Ɋi�[
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < kana.length(); i++ ) {
                if (i != point) {
                    sb.append(kana.substring(i, i+1));
                }
            }

            return sb.toString();
        }

        /**
         * �J�^�J�i�┼�p�����Ȃǂ��A�S�p�Ђ炪�Ȃɒu������
         * @param str ����
         * @return �S�p�Ђ炪��
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
                //�S�p����
                else if ((tgt >= 0x3041) && (tgt <= 0x309F)) {
                    fst = tgt;
                }
                //�S�p�J�i
                else if ( (tgt >= 0x30A1) && (tgt <= 0x30FF)) {
                    fst = (char)((int)tgt - 0x0060);

                    //��O
                    if (tgt == 0x30F4) { //�� (0x3094�͂�߂Ƃ�)
                        fst = 0x3046; //��
                        snd = 0x309B; //�J
                    }
                    else if (tgt == 0x30F7) { //���J
                        fst = 0x308F; //��
                        snd = 0x309B; //�J
                    }
                    else if (tgt == 0x30F8) { //���J
                        fst = 0x3090; //��
                        snd = 0x309B; //�J
                    }
                    else if (tgt == 0x30F9) { //���J
                        fst = 0x3091; //��
                        snd = 0x309B; //�J
                    }
                    else if (tgt == 0x30FA) { //���J
                        fst = 0x3092; //��
                        snd = 0x309B; //�J
                    }
                    else if (tgt == 0x30FB) { //�E
                        fst = tgt; //�E
                    }
                    else if (tgt == 0x30FC) { //�[
                        fst = tgt; //�[
                    }
                }
                //���p��
                else if (tgt == 0x0020) {
                    fst = '�@';
                }
                //���p�p��
                else if ( (tgt >= 0x0021) && (tgt <= 0x007F)) {
                    fst = (char)((int)tgt + 0xFEE0);
                }
                //���p�J�i
                else if ( (tgt >= 0xFF61) && (tgt <= 0xFF9F)) {
                    fst = (char) HALF_KANA_ARRAY[(int)tgt - 0xFF61];
                }
                //���̑�
                else {
                    fst = tgt;
                }

                //�o�b�t�@�ɒǉ�
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
