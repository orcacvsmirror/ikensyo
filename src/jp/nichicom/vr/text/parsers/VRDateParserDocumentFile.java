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
 * VRDateParser���g�p������`�ւ̕W���A�N�Z�X�h�L�������g�N���X�ł��B
 * <p>
 * �f�t�H���g�ł̓J�����g�t�H���_���� calendar.xml ���`�t�@�C���Ƃ��Ďg�p���܂��B
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
     * �R���X�g���N�^
     */
    public VRDateParserDocumentFile() {
        // �f�t�H���g�t�@�C���p�X
        this("calendar.xml");
    }

    /**
     * �R���X�g���N�^
     * 
     * @param path �t�@�C���p�X
     */
    public VRDateParserDocumentFile(String path) {
        setPath(path);
    }

    public Document getDefinedDocument() {
        File f = new File(getPath());

        Document doc = null;
        if (f.exists()) {
            try {
                // �h�L�������g�r���_�[�t�@�N�g���𐶐�
                // �h�L�������g�r���_�[�𐶐�
                // �p�[�X�����s����Document�I�u�W�F�N�g���擾
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
     * �t�@�C����͎��s���̃f�t�H���g���`����ɂ���Document��Ԃ��܂��B
     * 
     * @return �f�t�H���g�h�L�������g
     */
    protected Document createDefaultDocument() {
        try {
            return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(
                            new InputSource(
                                    new StringReader(
                                            "<?xml version=\"1.0\" encoding=\"UTF-8\"?><calendar><locales><locale id=\"JAPAN\" language=\"ja\" country=\"JP\"><eras><era><abbreviation type=\"g\">H</abbreviation><abbreviation type=\"gg\">��</abbreviation><abbreviation type=\"ggg\">����</abbreviation><abbreviation type=\"gggg\">h</abbreviation><begin>1989-01-08</begin><end>9999-12-31</end></era><era><abbreviation type=\"g\">S</abbreviation><abbreviation type=\"gg\">��</abbreviation><abbreviation type=\"ggg\">���a</abbreviation><abbreviation type=\"gggg\">s</abbreviation><begin>1926-12-25</begin><end>1989-01-07</end></era><era><abbreviation type=\"g\">T</abbreviation><abbreviation type=\"gg\">��</abbreviation><abbreviation type=\"ggg\">�吳</abbreviation><abbreviation type=\"gggg\">t</abbreviation><begin>1912-07-30</begin><end>1926-12-24</end></era><era><abbreviation type=\"g\">M</abbreviation><abbreviation type=\"gg\">��</abbreviation><abbreviation type=\"ggg\">����</abbreviation><abbreviation type=\"gggg\">m</abbreviation><begin>1868-09-08</begin><end>1912-07-29</end></era><era><abbreviation type=\"g\"></abbreviation><abbreviation type=\"gg\"></abbreviation><abbreviation type=\"ggg\">01</abbreviation><begin>1000-01-01</begin><end>1868-09-08</end></era><era><abbreviation type=\"g\"></abbreviation><abbreviation type=\"gg\"></abbreviation><abbreviation type=\"ggg\">00</abbreviation><begin>0001-01-01</begin><end>999-12-31</end></era></eras><holydays><holyday id=\"���U\"><begin>1949-01-01</begin><end>9999-12-31</end><term type=\"M\">1</term><term type=\"d\">1</term></holyday><holyday id=\"���l�̓�\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">1</term><term type=\"week\">2</term><term type=\"wday\">2</term></holyday><holyday id=\"�����L�O�̓�\"><begin>1966-01-01</begin><end>9999-12-31</end><term type=\"M\">2</term><term type=\"d\">11</term></holyday><holyday id=\"�݂ǂ�̓�\"><begin>1989-01-01</begin><end>2006-12-31</end><term type=\"M\">4</term><term type=\"d\">29</term></holyday><holyday id=\"�݂ǂ�̓�\"><begin>2007-01-01</begin><end>9999-12-31</end><term type=\"M\">5</term><term type=\"d\">4</term></holyday><holyday id=\"���a�̓�\"><begin>2007-01-01</begin><end>9999-12-31</end><term type=\"M\">4</term><term type=\"d\">29</term></holyday><holyday id=\"���@�L�O��\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">5</term><term type=\"d\">3</term></holyday><holyday id=\"���ǂ��̓�\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">5</term><term type=\"d\">5</term></holyday><holyday id=\"�C�̓�\"><begin>2003-01-01</begin><end>9999-12-31</end><term type=\"M\">7</term><term type=\"week\">3</term><term type=\"wday\">2</term></holyday><holyday id=\"�h�V�̓�\"><begin>2003-01-01</begin><end>9999-12-31</end><term type=\"M\">9</term><term type=\"week\">3</term><term type=\"wday\">2</term></holyday><holyday id=\"�̈�̓�\"><begin>1900-01-01</begin><end>1998-10-20</end><term type=\"M\">10</term><term type=\"d\">10</term></holyday><holyday id=\"�̈�̓�\"><begin>1998-10-21</begin><end>9999-12-31</end><term type=\"M\">10</term><term type=\"week\">2</term><term type=\"wday\">2</term></holyday><holyday id=\"�����̓�\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">11</term><term type=\"d\">3</term></holyday><holyday id=\"�ΘJ���ӂ̓�\"><begin>1948-01-01</begin><end>9999-12-31</end><term type=\"M\">11</term><term type=\"d\">23</term></holyday><holyday id=\"�V�c�a����\"><begin>1989-01-01</begin><end>9999-12-31</end><term type=\"M\">12</term><term type=\"d\">23</term></holyday><calcurate id=\"jp.nichicom.vr.text.parsers.VRDateParserHolydayCalculaterJAPAN\" /></holydays></locale> <locale id=\"UK\" language=\"en\" country=\"GB\" /><locale id=\"US\" language=\"en\" country=\"US\" /></locales><format><patterns><pattern>yy/M/d</pattern><pattern>yy.M.d</pattern><pattern>yy-M-d</pattern><pattern>y/M/d</pattern><pattern>y.M.d</pattern><pattern>y-M-d</pattern><pattern>yyyyMMdd</pattern><pattern>yyMMdd</pattern><pattern>MM/d</pattern><pattern>yyyy/M</pattern><pattern>M/d</pattern><pattern>M.d</pattern><pattern>M-d</pattern><pattern>yyyy</pattern><pattern>yy/M/d h:m:s</pattern><pattern>y/M/d h:m:s</pattern><pattern>yy.M.d h:m:s</pattern><pattern>y.M.d h:m:s</pattern><pattern>yy-M-d h:m:s</pattern><pattern>y-M-d h:m:s</pattern><pattern>yy/M/d h:m</pattern><pattern>y/M/d h:m</pattern><pattern>yy.M.d h:m</pattern><pattern>y.M.d h:m</pattern><pattern>yy-M-d h:m</pattern><pattern>y-M-d h:m</pattern><pattern>h:m:s</pattern><pattern>h:m</pattern></patterns><patterns locale=\"US\"><pattern>M/d/yy</pattern><pattern>M.d.yy</pattern><pattern>M-d-yy</pattern><pattern>M/d/y</pattern><pattern>M.d.y</pattern><pattern>M-d-y</pattern></patterns><patterns locale=\"UK\"><pattern>d/M/yy</pattern><pattern>d.M.yy</pattern><pattern>d-M-yy</pattern><pattern>d/M/y</pattern><pattern>d.M.y</pattern><pattern>d-M-y</pattern></patterns><patterns locale=\"JAPAN\"><pattern>gggge�NM��d��</pattern><pattern>ggge�NM��d��</pattern><pattern>gge�NM��d��</pattern><pattern>ge�NM��d��</pattern><pattern>gggge�NM��d</pattern><pattern>ggge�NM��d</pattern><pattern>gge�NM��d</pattern><pattern>ge�NM��d</pattern><pattern>gggge�NM��</pattern><pattern>ggge�NM��</pattern><pattern>gge�NM��</pattern><pattern>ge�NM��</pattern><pattern>gggge�N</pattern><pattern>ggge�N</pattern><pattern>gge�N</pattern><pattern>ge�N</pattern><pattern>gggge�EM�Ed</pattern><pattern>ggge�EM�Ed</pattern><pattern>gge�EM�Ed</pattern><pattern>ge�EM�Ed</pattern><pattern>gggge.M.d</pattern><pattern>ggge.M.d</pattern><pattern>gge.M.d</pattern><pattern>ge.M.d</pattern><pattern>yy�NM��d��</pattern><pattern>y�NM��d��</pattern><pattern>ggggeeMMdd</pattern><pattern>gggeeMMdd</pattern><pattern>ggeeMMdd</pattern><pattern>geeMMdd</pattern><pattern>gggge/M/d</pattern><pattern>ggge/M/d</pattern><pattern>gge/M/d</pattern><pattern>ge/M/d</pattern><pattern>gggge�NM��d�� h��m��s�b</pattern><pattern>ggge�NM��d�� h��m��s�b</pattern><pattern>gge�NM��d�� h��m��s�b</pattern><pattern>ge�NM��d�� h��m��s�b</pattern><pattern>gggge�NM��d�� h��m��</pattern><pattern>ggge�NM��d�� h��m��</pattern><pattern>gge�NM��d�� h��m��</pattern><pattern>ge�NM��d�� h��m��</pattern><pattern>yy�NM��d�� h��m��s�b</pattern><pattern>y�NM��d�� h��m��s�b</pattern><pattern>y�NM��d�� h��m��</pattern><pattern>h��m��s�b</pattern><pattern>h��m��</pattern></patterns></format></calendar>")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * ��`�t�@�C���p�X��Ԃ��܂��B
     * 
     * @return ��`�t�@�C���p�X
     */
    public String getPath() {
        return path;
    }

    /**
     * ��`�t�@�C���p�X��ݒ肵�܂��B
     * 
     * @param path ��`�t�@�C���p�X
     */
    public void setPath(String path) {
        this.path = path;
    }
}