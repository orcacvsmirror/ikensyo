/*
 * Project code name "ORCA"
 * 主治医意見書作成ソフト ITACHI（JMA IKENSYO software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: ITACHI
 * 開発者: 田中統蔵
 * 作成日: 2005/12/01  日本コンピュータ株式会社 田中統蔵 新規作成
 * 更新日: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Iterator;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHashableComboFormat extends Format {
    protected VRArrayList array;
    protected String fieldName;

    /**
     * コンストラクタです。
     * 
     * @param array 選択候補集合
     * @param fieldName 表示するフィールド名
     */
    public IkenshoHashableComboFormat(VRArrayList array, String fieldName) {
        this.array = array;
        this.fieldName = fieldName;
    }

    public Object parseObject(String source, ParsePosition pos) {
        Object result = source;
        if (array != null) {
            if (source != null) {
                Iterator it = array.iterator();
                while (it.hasNext()) {
                    VRMap row = (VRMap) it.next();
                    try {
                        if (String.valueOf(row).equals(source)) {
                            result = row;
                            break;
                        }
                        Object obj = VRBindPathParser.get(fieldName, row);
                        if (source.equals(obj)) {
                            result = row;
                            break;
                        }
                    } catch (ParseException ex) {
                    }
                }
            }
        }
        pos.setIndex(source.length() + 1);
        return result;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj instanceof VRMap) {
            try {
                if (VRBindPathParser.has(fieldName, (VRMap) obj)) {
                    obj = VRBindPathParser.get(fieldName, (VRMap) obj);
                }
            } catch (ParseException ex) {
                return toAppendTo;
            }
        }

        if (obj != null) {
            toAppendTo.append(obj);
        }
        return toAppendTo;
    }

}
