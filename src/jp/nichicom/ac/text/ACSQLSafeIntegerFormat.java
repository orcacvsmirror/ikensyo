package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * SQL�̍\�z��z�肵�������t�H�[�}�b�g�ł��B
 * <p>
 * �f�[�^��null�̂Ƃ���"NULL"���o�͂��܂��B<br />
 * �f�[�^��null�łȂ���΁A���l�𕶎��񉻂��ďo�͂��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see Format
 */
public class ACSQLSafeIntegerFormat extends Format {
    protected static ACSQLSafeIntegerFormat singleton;

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected ACSQLSafeIntegerFormat() {
        super();
    }

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @return �C���X�^���X
     */
    public static ACSQLSafeIntegerFormat getInstance() {
        if (singleton == null) {
            singleton = new ACSQLSafeIntegerFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        return source;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if ((obj == null) || ("".equals(obj))) {
            toAppendTo.append("NULL");
        } else {
            toAppendTo.append(String.valueOf(obj));
        }
        return toAppendTo;

    }
}
