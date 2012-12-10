/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;


import org.w3c.dom.Document;

/**
 * VRDateParserが使用する暦定義へのアクセスドキュメントインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see Document
 * @see VRDateParser
 */
public interface VRDateParserDocumentCreatable {

    /**
     * XML文書オブジェクトを取得します。
     * 
     * @return XML文書オブジェクト
     */
    public Document getDefinedDocument();
}