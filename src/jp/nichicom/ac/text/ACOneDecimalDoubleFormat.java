package jp.nichicom.ac.text;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * �����_��1�ʂ܂ł̏����ɕϊ�����t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACOneDecimalDoubleFormat extends Format {
    protected static ACOneDecimalDoubleFormat singleton;
    private boolean underZeroCut = true;

    /**
     * �����_��1��0�̏ꍇ�A�������܂ł̕\���� ��Ԃ��܂��B
     * 
     * @return �����_��1��0�̏ꍇ�A�������܂ł̕\����
     */
    public boolean isUnderZeroCut() {
        return underZeroCut;
    }

    /**
     * �����_��1��0�̏ꍇ�A�������܂ł̕\���Ƃ��邩��ݒ肵�܂��B
     * 
     * @param underZeroCut �����_��1��0�̏ꍇ�A�������܂ł̕\����
     */
    public void setUnderZeroCut(boolean underZeroCut) {
        this.underZeroCut = underZeroCut;
    }

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @return �C���X�^���X
     */
    public static ACOneDecimalDoubleFormat getInstance() {
        if (singleton == null) {
            singleton = new ACOneDecimalDoubleFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if ("".equals(source)) {
            pos.setIndex(-1);
            return null;
        }

        pos.setIndex(source.length());
        return Double.valueOf(source);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj == null) {
            return toAppendTo;
        }
        String valText = String.valueOf(obj);
        if ("".equals(valText)) {
            return toAppendTo;
        }

        double dblValue = Double.parseDouble(valText);
        if (isUnderZeroCut()) {
            int intValue = (int) dblValue;
            double dblTmp = Double.parseDouble(String.valueOf(intValue));

            if (dblValue == dblTmp) {
                toAppendTo.append(new DecimalFormat("#").format(dblValue));
            } else {
                toAppendTo.append(new DecimalFormat("#.#").format(dblValue));
            }
        } else {
            toAppendTo.append(new DecimalFormat("#.#").format(dblValue));
        }

        return toAppendTo;
    }
}
