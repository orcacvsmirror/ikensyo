/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Iterator;


/**
 * VRDateParser���g�p���錳���p�̗�\���N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserStatementable
 * @see VRDateParserStatementParseOption
 */
public class VRDateParserStatementEra implements VRDateParserStatementable {
    public static final int G = 1;

    public static final int GG = 2;

    public static final int GGG = 3;

    private int type;

    /**
     * �R���X�g���N�^
     * 
     * @param type �`��
     */
    public VRDateParserStatementEra(int type) {
        setType(type);
    }

    /**
     * �`����Ԃ��܂��B
     * 
     * @return �`��
     */
    public int getType() {
        return type;
    }

    /**
     * �`����ݒ肵�܂��B
     * 
     * @param type �`��
     */
    public void setType(int type) {
        this.type = type;
    }

    public boolean isMatched(VRDateParserStatementParseOption option) {
        String target = option.getTarget();
        int i = option.getParseBeginIndex();
        int len = target.length();
        int matchEraLen = 0;
        Iterator eraIt = option.getEras().iterator();
        while (eraIt.hasNext()) {
            VRDateParserEra eraItem = (VRDateParserEra) eraIt.next();

            String txt = eraItem.getAbbreviation(this.getType());
            if (txt == null) {
                //�w��̗��̂��`���Ă��Ȃ�
                continue;
            }
            int txtLen = txt.length();
            if (i + txtLen > len) {
                //���e���������񕪂̒������c���Ă��Ȃ�
                continue;
            }
            if (!target.substring(i, txtLen).equals(txt)) {
                //���e���������񂪈�v���Ȃ�
                continue;
            }
            if (matchEraLen < txtLen) {
                //�X�V
                matchEraLen = txtLen;
                option.setMachedEra(eraItem);
            }
        }

        if (matchEraLen > 0) {
            i += matchEraLen;
        } else {
            //�Y�����錳�������݂��Ȃ�
            return false;
        }
        option.setParseEndIndex(i);
        return true;
    }

}