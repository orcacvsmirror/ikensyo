package jp.nichicom.ac.component;

import javax.swing.text.Document;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.text.ACOneDecimalDoubleFormat;

/**
 * 小数点第1位までのDouble型で値を管理するテキストフィールドです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACTextField
 */
public class ACOneDecimalDoubleTextField extends ACTextField {
    public ACOneDecimalDoubleTextField() {
        super();
    }

    public ACOneDecimalDoubleTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public ACOneDecimalDoubleTextField(int columns) {
        super(columns);
    }

    public ACOneDecimalDoubleTextField(String text) {
        super(text);
    }

    public ACOneDecimalDoubleTextField(String text, int columns) {
        super(text, columns);
    }

    protected void initComponent() {
        super.initComponent();

        setCharType(ACConstants.CHAR_TYPE_ONE_DECIMAL_DOUBLE);
        setFormat(ACOneDecimalDoubleFormat.getInstance());
    }

    public Object createSource() {
        return new Double(0);
    }
}
