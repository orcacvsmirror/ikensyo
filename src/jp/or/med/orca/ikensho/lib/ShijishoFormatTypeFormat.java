package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
/**
 * �K��Ō�w�����敪�t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2009/09/07
 */
public class ShijishoFormatTypeFormat extends Format {
    protected static ShijishoFormatTypeFormat singleton;
    protected static final String TYPE_HOUMONKANGO_SHIJISHO = "0";
    protected static final String TYPE_TOKUBETSU_HOUMONKANGO_SHIJISHO = "1";
    protected static final String TEXT_HOUMONKANGO_SHIJISHO = "�K��Ō�w����";
    protected static final String TEXT_TOKUBETSU_HOUMONKANGO_SHIJISHO = "���ʖK��Ō�w����";

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected ShijishoFormatTypeFormat() {
        super();
    }

    /**
     * �C���X�^���X���擾���܂��B
     */
    public static ShijishoFormatTypeFormat getInstance() {
        if (singleton == null) {
            singleton = new ShijishoFormatTypeFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (TEXT_TOKUBETSU_HOUMONKANGO_SHIJISHO.equals(source)) {
            return new Integer(TYPE_TOKUBETSU_HOUMONKANGO_SHIJISHO);
        } else {
            return new Integer(TYPE_HOUMONKANGO_SHIJISHO);
        }

    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        String val = String.valueOf(obj);

        if (TYPE_TOKUBETSU_HOUMONKANGO_SHIJISHO.equals(val)) {
            toAppendTo.append(TEXT_TOKUBETSU_HOUMONKANGO_SHIJISHO);
        } else {
            toAppendTo.append(TEXT_HOUMONKANGO_SHIJISHO);
        }
        return toAppendTo;

    }
}
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
