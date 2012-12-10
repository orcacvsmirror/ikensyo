package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * NULL���󕶎��ɕϊ�����t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACNullToBlankFormat extends Format {
    protected static ACNullToBlankFormat singleton;

    /**
     * �C���X�^���X���擾���܂��B
     */
    public static ACNullToBlankFormat getInstance() {
        if (singleton == null) {
            singleton = new ACNullToBlankFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        return "";
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj != null) {
            toAppendTo.append(obj);
        }
        return toAppendTo;
    }
}
