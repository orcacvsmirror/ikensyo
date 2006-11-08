package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
/**
 * �ӌ����敪�t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/07/10
 */
public class IkenshoFormatTypeFormat extends Format{
    protected static IkenshoFormatTypeFormat singleton;
    protected static final String TYPE_NONE = "0";
    protected static final String TYPE_SHUJII_IKENSHO = "1";
    protected static final String TYPE_ISHI_IKENSHO = "2";
    protected static final String TEXT_NONE = "";
    protected static final String TEXT_SHUJII_IKENSHO = "�厡��ӌ���";
    protected static final String TEXT_ISHI_IKENSHO = "��t�ӌ���";

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected IkenshoFormatTypeFormat() {
        super();
    }

    /**
     * �C���X�^���X���擾���܂��B
     */
    public static IkenshoFormatTypeFormat getInstance() {
        if (singleton == null) {
            singleton = new IkenshoFormatTypeFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (TEXT_ISHI_IKENSHO.equals(source)) {
            return new Integer(TYPE_ISHI_IKENSHO);
        } else {
            return new Integer(TYPE_SHUJII_IKENSHO);
        }

    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        String val = String.valueOf(obj);

        if (TYPE_ISHI_IKENSHO.equals(val)) {
            toAppendTo.append(TEXT_ISHI_IKENSHO);
        } else {
            toAppendTo.append(TEXT_SHUJII_IKENSHO);
        }
        return toAppendTo;

    }
}
