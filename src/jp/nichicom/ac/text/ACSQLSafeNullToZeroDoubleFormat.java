package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * SQL�̍\�z��z�肵�������l�t�H�[�}�b�g�ł��B
 * <p>
 * �f�[�^��null�܂��͋󕶎��̂Ƃ���"0"���o�͂��܂��B<br />
 * �f�[�^��null�łȂ���΁A�����񉻂������l���o�͂��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see Format
 */
public class ACSQLSafeNullToZeroDoubleFormat extends Format {
    protected static ACSQLSafeNullToZeroDoubleFormat singleton;

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected ACSQLSafeNullToZeroDoubleFormat() {
        super();
    }

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @return �C���X�^���X
     */
    public static ACSQLSafeNullToZeroDoubleFormat getInstance() {
        if (singleton == null) {
            singleton = new ACSQLSafeNullToZeroDoubleFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if ((source == null) || ("".equals(source))) {
            return new Double(0);
        }
        return Double.valueOf(source);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if ((obj == null) || ("".equals(obj))) {
            toAppendTo.append("0");
        } else {
            toAppendTo.append(String.valueOf(obj));
        }
        return toAppendTo;

    }
}
