package jp.nichicom.ac.text;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import jp.nichicom.vr.text.VRDateFormat;

import org.xml.sax.SAXException;

/**
 * 日付フォーマットです。
 * <p>
 * 渡されたデータが空の場合は何もしません。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRDateFormat
 */

public class ACDateFormat extends VRDateFormat {
    public ACDateFormat() {
        super();
    }

    public ACDateFormat(String format) {
        super(format);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if ("".equals(obj)) {
            return toAppendTo;
        }
        return super.format(obj, toAppendTo, pos);

    }

    public Date parse(String text) throws ParseException, SAXException,
            IOException, ParserConfigurationException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        if ((text == null) || ("".equals(text))) {
            return null;
        }
        return super.parse(text);
    }
}
