/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * VRDateParserが使用する暦定義への標準アクセスドキュメントクラスです。
 * <p>
 * デフォルトではカレントフォルダ内の calendar.xml を定義ファイルとして使用します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParserDocumentCreatable
 * @see VRDateParser
 * @see Document
 */
public class VRDateParserDocumentFile implements VRDateParserDocumentCreatable {
    private String path;

    /**
     * コンストラクタ
     */
    public VRDateParserDocumentFile() {
        // デフォルトファイルパス
        this("calendar.xml");
    }

    /**
     * コンストラクタ
     * 
     * @param path ファイルパス
     */
    public VRDateParserDocumentFile(String path) {
        setPath(path);
    }

    public Document getDefinedDocument() {
        File f = new File(getPath());

        Document doc = null;
        if (f.exists()) {
            try {
                // ドキュメントビルダーファクトリを生成
                // ドキュメントビルダーを生成
                // パースを実行してDocumentオブジェクトを取得
                doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(f);
            } catch (SAXException ex) {
                ex.printStackTrace();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if (doc == null) {
            doc = createDefaultDocument();
        }
        return doc;
    }

    /**
     * ファイル解析失敗時のデフォルト暦定義を基にしたDocumentを返します。
     * 
     * @return デフォルトドキュメント
     */
    protected Document createDefaultDocument() {
        try {
            return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(
                            new InputSource(
                                    new StringReader(
                                            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><calendar><locales><locale id=\"JAPAN\" language=\"ja\" country=\"JP\"><eras><era><abbreviation type=\"g\">H</abbreviation><abbreviation type=\"gg\">平</abbreviation><abbreviation type=\"ggg\">平成</abbreviation><abbreviation type=\"gggg\">h</abbreviation><begin>1989-01-08</begin><end>9999-12-31</end></era><era><abbreviation type=\"g\">S</abbreviation><abbreviation type=\"gg\">昭</abbreviation><abbreviation type=\"ggg\">昭和</abbreviation><abbreviation type=\"gggg\">s</abbreviation><begin>1926-12-25</begin><end>1989-01-07</end></era><era><abbreviation type=\"g\">T</abbreviation><abbreviation type=\"gg\">大</abbreviation><abbreviation type=\"ggg\">大正</abbreviation><abbreviation type=\"gggg\">t</abbreviation><begin>1912-07-30</begin><end>1926-12-24</end></era><era><abbreviation type=\"g\">M</abbreviation><abbreviation type=\"gg\">明</abbreviation><abbreviation type=\"ggg\">明治</abbreviation><abbreviation type=\"gggg\">m</abbreviation><begin>1868-09-08</begin><end>1912-07-29</end></era><era><abbreviation type=\"g\"></abbreviation><abbreviation type=\"gg\"></abbreviation><abbreviation type=\"ggg\">01</abbreviation><begin>1000-01-01</begin><end>1868-09-08</end></era><era><abbreviation type=\"g\"></abbreviation><abbreviation type=\"gg\"></abbreviation><abbreviation type=\"ggg\">00</abbreviation><begin>0001-01-01</begin><end>999-12-31</end></era></eras><holydays><holyday id=\"元旦\"><begin>1949-01-01</begin><end>9999-12-31</end><term type=\"M\">1</term><term type=\"d\">1</term></holyday><holyday id=\"成人の日\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">1</term><term type=\"week\">2</term><term type=\"wday\">2</term></holyday><holyday id=\"建国記念の日\"><begin>1966-01-01</begin><end>9999-12-31</end><term type=\"M\">2</term><term type=\"d\">11</term></holyday><holyday id=\"みどりの日\"><begin>1989-01-01</begin><end>2006-12-31</end><term type=\"M\">4</term><term type=\"d\">29</term></holyday><holyday id=\"みどりの日\"><begin>2007-01-01</begin><end>9999-12-31</end><term type=\"M\">5</term><term type=\"d\">4</term></holyday><holyday id=\"昭和の日\"><begin>2007-01-01</begin><end>9999-12-31</end><term type=\"M\">4</term><term type=\"d\">29</term></holyday><holyday id=\"憲法記念日\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">5</term><term type=\"d\">3</term></holyday><holyday id=\"こどもの日\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">5</term><term type=\"d\">5</term></holyday><holyday id=\"海の日\"><begin>2003-01-01</begin><end>9999-12-31</end><term type=\"M\">7</term><term type=\"week\">3</term><term type=\"wday\">2</term></holyday><holyday id=\"敬老の日\"><begin>2003-01-01</begin><end>9999-12-31</end><term type=\"M\">9</term><term type=\"week\">3</term><term type=\"wday\">2</term></holyday><holyday id=\"体育の日\"><begin>1900-01-01</begin><end>1998-10-20</end><term type=\"M\">10</term><term type=\"d\">10</term></holyday><holyday id=\"体育の日\"><begin>1998-10-21</begin><end>9999-12-31</end><term type=\"M\">10</term><term type=\"week\">2</term><term type=\"wday\">2</term></holyday><holyday id=\"文化の日\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">11</term><term type=\"d\">3</term></holyday><holyday id=\"勤労感謝の日\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">11</term><term type=\"d\">23</term></holyday><holyday id=\"天皇誕生日\"><begin>1989-01-01</begin><end>9999-12-31</end><term type=\"M\">12</term><term type=\"d\">23</term></holyday><calcurate id=\"jp.nichicom.vr.text.parsers.VRDateParserHolydayCalculaterJAPAN\" /></holydays></locale> <locale id=\"UK\" language=\"en\" country=\"GB\" /><locale id=\"US\" language=\"en\" country=\"US\" /></locales><format><patterns><pattern>yy/M/d</pattern><pattern>yy.M.d</pattern><pattern>yy-M-d</pattern><pattern>y/M/d</pattern><pattern>y.M.d</pattern><pattern>y-M-d</pattern><pattern>yyyyMMdd</pattern><pattern>yyMMdd</pattern><pattern>MM/d</pattern><pattern>yyyy/M</pattern><pattern>M/d</pattern><pattern>M.d</pattern><pattern>M-d</pattern><pattern>yyyy</pattern><pattern>yy/M/d h:m:s</pattern><pattern>y/M/d h:m:s</pattern><pattern>yy.M.d h:m:s</pattern><pattern>y.M.d h:m:s</pattern><pattern>yy-M-d h:m:s</pattern><pattern>y-M-d h:m:s</pattern><pattern>yy/M/d h:m</pattern><pattern>y/M/d h:m</pattern><pattern>yy.M.d h:m</pattern><pattern>y.M.d h:m</pattern><pattern>yy-M-d h:m</pattern><pattern>y-M-d h:m</pattern><pattern>h:m:s</pattern><pattern>h:m</pattern></patterns><patterns locale=\"US\"><pattern>M/d/yy</pattern><pattern>M.d.yy</pattern><pattern>M-d-yy</pattern><pattern>M/d/y</pattern><pattern>M.d.y</pattern><pattern>M-d-y</pattern></patterns><patterns locale=\"UK\"><pattern>d/M/yy</pattern><pattern>d.M.yy</pattern><pattern>d-M-yy</pattern><pattern>d/M/y</pattern><pattern>d.M.y</pattern><pattern>d-M-y</pattern></patterns><patterns locale=\"JAPAN\"><pattern>gggge年M月d日</pattern><pattern>ggge年M月d日</pattern><pattern>gge年M月d日</pattern><pattern>ge年M月d日</pattern><pattern>gggge年M月d</pattern><pattern>ggge年M月d</pattern><pattern>gge年M月d</pattern><pattern>ge年M月d</pattern><pattern>gggge年M月</pattern><pattern>ggge年M月</pattern><pattern>gge年M月</pattern><pattern>ge年M月</pattern><pattern>gggge年</pattern><pattern>ggge年</pattern><pattern>gge年</pattern><pattern>ge年</pattern><pattern>gggge・M・d</pattern><pattern>ggge・M・d</pattern><pattern>gge・M・d</pattern><pattern>ge・M・d</pattern><pattern>gggge.M.d</pattern><pattern>ggge.M.d</pattern><pattern>gge.M.d</pattern><pattern>ge.M.d</pattern><pattern>yy年M月d日</pattern><pattern>y年M月d日</pattern><pattern>ggggeeMMdd</pattern><pattern>gggeeMMdd</pattern><pattern>ggeeMMdd</pattern><pattern>geeMMdd</pattern><pattern>gggge/M/d</pattern><pattern>ggge/M/d</pattern><pattern>gge/M/d</pattern><pattern>ge/M/d</pattern><pattern>gggge年M月d日 h時m分s秒</pattern><pattern>ggge年M月d日 h時m分s秒</pattern><pattern>gge年M月d日 h時m分s秒</pattern><pattern>ge年M月d日 h時m分s秒</pattern><pattern>gggge年M月d日 h時m分</pattern><pattern>ggge年M月d日 h時m分</pattern><pattern>gge年M月d日 h時m分</pattern><pattern>ge年M月d日 h時m分</pattern><pattern>yy年M月d日 h時m分s秒</pattern><pattern>y年M月d日 h時m分s秒</pattern><pattern>y年M月d日 h時m分</pattern><pattern>h時m分s秒</pattern><pattern>h時m分</pattern></patterns></format></calendar>")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 定義ファイルパスを返します。
     * 
     * @return 定義ファイルパス
     */
    public String getPath() {
        return path;
    }

    /**
     * 定義ファイルパスを設定します。
     * 
     * @param path 定義ファイルパス
     */
    public void setPath(String path) {
        this.path = path;
    }
}