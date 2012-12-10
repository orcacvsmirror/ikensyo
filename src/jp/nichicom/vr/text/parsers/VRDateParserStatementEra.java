/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Iterator;


/**
 * VRDateParserが使用する元号用の暦構文クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserStatementable
 * @see VRDateParserStatementParseOption
 */
public class VRDateParserStatementEra implements VRDateParserStatementable {
    public static final int G = 1;

    public static final int GG = 2;

    public static final int GGG = 3;

    private int type;

    /**
     * コンストラクタ
     * 
     * @param type 形式
     */
    public VRDateParserStatementEra(int type) {
        setType(type);
    }

    /**
     * 形式を返します。
     * 
     * @return 形式
     */
    public int getType() {
        return type;
    }

    /**
     * 形式を設定します。
     * 
     * @param type 形式
     */
    public void setType(int type) {
        this.type = type;
    }

    public boolean isMatched(VRDateParserStatementParseOption option) {
        String target = option.getTarget();
        int i = option.getParseBeginIndex();
        int len = target.length();
        int matchEraLen = 0;
        Iterator eraIt = option.getEras().iterator();
        while (eraIt.hasNext()) {
            VRDateParserEra eraItem = (VRDateParserEra) eraIt.next();

            String txt = eraItem.getAbbreviation(this.getType());
            if (txt == null) {
                //指定の略称を定義していない
                continue;
            }
            int txtLen = txt.length();
            if (i + txtLen > len) {
                //リテラル文字列分の長さが残っていない
                continue;
            }
            if (!target.substring(i, txtLen).equals(txt)) {
                //リテラル文字列が一致しない
                continue;
            }
            if (matchEraLen < txtLen) {
                //更新
                matchEraLen = txtLen;
                option.setMachedEra(eraItem);
            }
        }

        if (matchEraLen > 0) {
            i += matchEraLen;
        } else {
            //該当する元号が存在しない
            return false;
        }
        option.setParseEndIndex(i);
        return true;
    }

}