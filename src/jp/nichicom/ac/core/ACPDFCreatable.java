package jp.nichicom.ac.core;

import jp.nichicom.ac.pdf.ACChotarouXMLWriter;

/**
 * PDF出力インターフェースです。
 * <p>
 * 主として、NCFrameEventProcessableインターフェースとあわせてシステムイベント処理クラスに実装します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACFrameEventProcesser
 */
public interface ACPDFCreatable {
    /**
     * 印刷可能な文字列に置換して返します。
     * 
     * @param text 置換対象
     * @return 置換結果
     */
    String toPrintable(String text);

    /**
     * 指定データでPDFを固定パスに出力します。
     * 
     * @param data 元データ
     * @throws Exception 処理例外
     * @return 出力ファイルパス
     */
    String writePDF(ACChotarouXMLWriter data) throws Exception;

    /**
     * 出力された任意パスのPDFファイルを開きます。
     * 
     * @param pdfPath PDFファイルのパス
     * @throws Exception
     */
    void openPDF(String pdfPath) throws Exception;

    /**
     * PDFの出力フォルダを返します。
     * 
     * @return PDFの出力フォルダ
     */
    String getPrintPDFDirectory();

    /**
     * 帳票定義体フォルダを返します。
     * 
     * @return 帳票定義体フォルダ
     */
    String getPrintFormatDirectory();

}
