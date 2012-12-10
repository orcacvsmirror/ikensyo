package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class ACSimpleIntegerFormat extends Format {

    public ACSimpleIntegerFormat() {
        super();
    }

    public Object parseObject(String source, ParsePosition pos) {
        Integer val = Integer.valueOf(source);
        pos.setIndex(source.length());
        return val;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        Integer val;
        if (obj instanceof Integer) {
            val = (Integer) obj;
        } else if (obj instanceof Long) {
            val = new Integer(((Long) obj).intValue());
        } else {
            val = Integer.valueOf(String.valueOf(obj));
        }
        if (val != null) {
            toAppendTo.append(val.toString());
        }

        return toAppendTo;
    }

}
