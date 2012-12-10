package jp.nichicom.bridge.pdf;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.geom.Point2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.EventListenerList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jp.nichicom.vr.io.VRBase64;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PRAcroForm;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;

/**
 * iTextによるPDF出力ライブラリです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 */
public class BridgeSimplePDF {

	private static final int FONT_STYLE_PLAIN = Font.PLAIN;

	private static final int FONT_STYLE_BOLD = Font.BOLD;

	private static final int FONT_STYLE_ITALIC = Font.ITALIC;

	private static final int FONT_STYLE_UNDERLINE = 4;

	private static final int FONT_STYLE_STRIKE = 8;

	private static final String MEDIASIZE_A5 = "A5";

	private static final String MEDIASIZE_A4 = "A4";

	private static final String MEDIASIZE_A3 = "A3";

	private static final String MEDIASIZE_A2 = "A2";

	private static final String MEDIASIZE_A1 = "A1";

	private static final String MEDIASIZE_A0 = "A0";

	private static final String MEDIASIZE_B5 = "B5";

	private static final String MEDIASIZE_B4 = "B4";

	private static final String MEDIASIZE_B3 = "B3";

	private static final String MEDIASIZE_B2 = "B2";

	private static final String MEDIASIZE_B1 = "B1";

	private static final String MEDIASIZE_B0 = "B0";

	private static final String ORIENTATION_PORTRAIT = "PORTRAIT";

	private static final String ORIENTATION_LANDSCAPE = "LANDSCAPE";

	private static final String TAG_PAGENO = "%PAGENO%";

	//private static final String TAG_PAGECOUNT = "%PAGECOUNT%";

	private static final String TAG_DATE = "%DATE%";

	private static final String TAG_DATETIME = "%DATETIME%";

	private static final String TAG_DOWNLINE = "%DOWNLINE%";

	private static final String TAG_RISELINE = "%RISELINE%";

	private static final String FONT_NAME_M = "明朝";

	private static final String FONT_NAME_G = "ゴシック";

	private static final String[] PHYSICAL_FONTNAME_MINCHO = { "ＭＳ 明朝", "HiraMinPro-W3", "平成明朝" };

	private static final String[] PHYSICAL_FONTNAME_GOTHIC = { "ＭＳ ゴシック", "HiraKakuPro-W3", "平成角ゴシック" };

	private Font[] localFonts;
    
    private int separatePrintPageSize = 30;

	/**
	 * 既定のフォントを表す定数です。
	 */
	public static final Font DEFAULT_FONT = new Font("ＭＳ 明朝", Font.PLAIN, 12);

	/**
	 * PDFファイルを出力します。
	 * 
	 * @param inputString 入力XMLString
	 * @param outputFilePath 出力PDFファイルパス
	 * @throws IOException
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void write(String inputString, String outputFilePath) throws IOException, DocumentException, SAXException, ParserConfigurationException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(inputString.getBytes("UTF-8"));
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		write(is, outputFilePath);
	}

	/**
	 * PDFファイルを出力します。
	 * 
	 * @param inputFile 入力XMLファイル
	 * @param outputFilePath 出力PDFファイルパス
	 * @throws IOException
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void write(File inputFile, String outputFilePath) throws IOException, DocumentException, SAXException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		DataXmlReader dataReader = new DataXmlReader();
        DataXmlEventAdapter adapt = new DataXmlEventAdapter(outputFilePath);
        dataReader.addDataXmlEventListener(adapt);
		parser.parse(inputFile, dataReader);
//        dataReader.removeDataXmlEventListener(adapt);
//        parser = null;
//        factory = null;
//		pdfWrite(dataReader, outputFilePath);
	}

	/**
	 * PDFファイルを出力します。
	 * 
	 * @param inputStream 入力XMLStream
	 * @param outputFilePath 出力PDFファイルパス
	 * @throws IOException
	 * @throws DocumentException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void write(InputStream inputStream, String outputFilePath) throws IOException, DocumentException, SAXException, ParserConfigurationException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();
		DataXmlReader dataReader = new DataXmlReader();
        dataReader.addDataXmlEventListener(new DataXmlEventAdapter(outputFilePath));
		parser.parse(inputStream, dataReader);
//		pdfWrite(dataReader, outputFilePath);
	}
    
    private class DataXmlEventAdapter implements DataXmlEventListener{
        private JapaneseFontMapper mp;
        
        private int pageNo = 1;
        
        private String outputFilePath;
        
        private PdfWriter writer;
        private Document doc;

        private int fileCount = -1; 
        private String currentFilePath;
        
        public DataXmlEventAdapter(String outputFilePath){
            this.outputFilePath = outputFilePath;
            // ローカルフォントの一覧を生成
            localFonts = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getAllFonts();
            try{
            mp = new JapaneseFontMapper(
                    PHYSICAL_FONTNAME_MINCHO, PHYSICAL_FONTNAME_GOTHIC);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        public void startParse(DataXmlReader source) {
            fileCount = -1;
            pageNo = 1;
        }

        public void startPage(DataXmlReader source) {
            
        }

        public void endPage(DataXmlReader source) {
            try{
            DataXml dataXml = source.getDataXml();
            ArrayList dataList = dataXml.getDataList();
            int pageCount = dataList.size();

            if(fileCount<0){
                if(doc!=null){
                    doc.close();
                }
                doc = new Document();
                currentFilePath = getOutputFilePath(0);
                fileCount = 1;
                writer = PdfWriter.getInstance(doc, new FileOutputStream(currentFilePath));
            }

            for (int i = 0; i < pageCount; i++) {

                DataPageObject pageObj = (DataPageObject) dataList.get(i);
                FormatXml formatXml = dataXml.getFormatListValue(pageObj
                        .getFormatId());
                FormatObject formatPaperObj = formatXml.getFormatPaperObject();

                doc.newPage();
                doc.setPageSize(calcPageSize(formatPaperObj));

                if (!doc.isOpen()) {
                    doc.open();
                }

                PdfContentByte cb = writer.getDirectContent();

                Iterator rectItems = formatXml.getFormatValues();
                while (rectItems.hasNext()) {
                    FormatObject item = (FormatObject) rectItems.next();
                    if (item instanceof FormatLabelObject) {
                        writeLabelObject(doc, cb, (FormatLabelObject) item,
                                pageObj.getDataValues(), pageCount, pageNo, mp);
                    } else if (item instanceof FormatSeparateObject) {
                        writeSeparateObject(doc, cb,
                                (FormatSeparateObject) item, pageObj
                                        .getDataValues(), pageCount, pageNo, mp);
                    } else if (item instanceof FormatTableObject) {
                        writeTableObject(doc, cb, (FormatTableObject) item,
                                pageObj.getDataValues(), pageCount, pageNo, mp);
                    }
                }

                pageNo++;

            }

            }catch(Exception ex){
                ex.printStackTrace();
                return;
            }
        }
        /**
         * ファイル番号に応じた出力ファイル名を返します。
         * @param fileIndex ファイル番号
         * @return ファイル名
         */
        protected String getOutputFilePath(int fileIndex){
            return outputFilePath;// + fileIndex+".pdf";
        }

        public void endParse(DataXmlReader source) {
            try {
                if ((doc != null) && doc.isOpen()) {
                    doc.close();
                }

//                fileCount++;
//                if (fileCount <= 1) {
//                    if (fileCount == 1) {
//                        // 1ファイルのみなら変名
//                        File outFile = new File(outputFilePath);
//                        outFile.delete();
//                        new File(getOutputFilePath(0)).renameTo(outFile);
//                    }
//                } else {
//                    // 指定ページ以上なら結合する
//                    ArrayList files = new ArrayList();
//                    for (int f = 0; f < fileCount; f++) {
//                        files.add(getOutputFilePath(f));
//                    }
//                    concat(outputFilePath, files);
//                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }

        }
        
    }
    /**
     * PDFファイルを結合します。
     * @param outputFilePath 結合先ファイル名
     * @param files 結合元ファイル名集合 
     * @throws Exception 処理例外
     */
    public void concat(String outputFilePath, List files) throws Exception{
        int pageOffset = 0;
        ArrayList master = new ArrayList();
        Document document = null;
        PdfCopy writer = null;
        int fileCount = files.size();
        for (int f = 0; f < fileCount; f++) {
            // we create a reader for a certain document
            
            PdfReader reader = new PdfReader(String.valueOf(files.get(f)));
            reader.consolidateNamedDestinations();
            // we retrieve the total number of pages
            int n = reader.getNumberOfPages();
            List bookmarks = SimpleBookmark.getBookmark(reader);
            if (bookmarks != null) {
                if (pageOffset != 0) {
                    SimpleBookmark.shiftPageNumbers(bookmarks, pageOffset,
                            null);
                }
                master.addAll(bookmarks);
            }
            pageOffset += n;

            if (f == 0) {
                // step 1: creation of a document-object
                document = new Document(reader.getPageSizeWithRotation(1));
                // step 2: we create a writer that listens to the document
                writer = new PdfCopy(document, new FileOutputStream(
                        outputFilePath));
                // step 3: we open the document
                document.open();
            }
            // step 4: we add content
            PdfImportedPage page;
            for (int i = 0; i < n;) {
                ++i;
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            PRAcroForm form = reader.getAcroForm();
            if (form != null) {
                writer.copyAcroForm(reader);
            }
        }
        if (master.size() > 0) {
            writer.setOutlines(master);
        }
        // step 5: we close the document
        if(document!=null){
            document.close();
            document = null;
        }
        writer = null;


        // 分割ファイルを削除
        for (int f = 0; f < fileCount; f++) {
            new File(String.valueOf(files.get(f))).delete();
        }
        
    }

//	private void pdfWrite(DataXmlReader dataReader, String outputFilePath) throws IOException, DocumentException {
//
//		JapaneseFontMapper mp = new JapaneseFontMapper(PHYSICAL_FONTNAME_MINCHO, PHYSICAL_FONTNAME_GOTHIC);
//		DataXml dataXml = dataReader.getDataXml();
//		Document doc = new Document();
//		PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outputFilePath));
//
//		// ローカルフォントの一覧を生成
//		localFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
//
//		ArrayList dataList = dataXml.getDataList();
//		int pageNo = 1;
//		int pageCount = dataList.size();
//
//		for (int i = 0; i < pageCount; i++) {
//
//			DataPageObject pageObj = (DataPageObject) dataList.get(i);
//			FormatXml formatXml = dataXml.getFormatListValue(pageObj.getFormatId());
//			FormatObject formatPaperObj = formatXml.getFormatPaperObject();
//
//			doc.newPage();
//			doc.setPageSize(calcPageSize(formatPaperObj));
//
//			if (!doc.isOpen()) {
//				doc.open();
//			}
//
//			PdfContentByte cb = writer.getDirectContent();
//
//			Iterator rectItems = formatXml.getFormatValues();
//			while (rectItems.hasNext()) {
//				FormatObject item = (FormatObject) rectItems.next();
//				if (item instanceof FormatLabelObject) {
//					writeLabelObject(doc, cb, (FormatLabelObject) item, pageObj.getDataValues(), pageCount, pageNo, mp);
//				} else if (item instanceof FormatSeparateObject) {
//					writeSeparateObject(doc, cb, (FormatSeparateObject) item, pageObj.getDataValues(), pageCount, pageNo, mp);
//				} else if (item instanceof FormatTableObject) {
//					writeTableObject(doc, cb, (FormatTableObject) item, pageObj.getDataValues(), pageCount, pageNo, mp);
//				}
//			}
//
//			pageNo++;
//
//		}
//
//		doc.close();
//
//	}

	private void writeLabelObject(Document doc, PdfContentByte cb, FormatLabelObject item, HashMap dMap, int pageCount, int pageNo, JapaneseFontMapper mp) throws IOException, BadElementException, DocumentException, MalformedURLException {
		DataValueObject valueObject = (DataValueObject) dMap.get(item);
		String value = null;
		String imageValue = null;
		if (valueObject != null) {
			if (valueObject.isVisible()) {
				if (valueObject.getValue() != null) {
					value = valueObject.getValue();
				} else {
					value = item.getText();
				}
				if (valueObject.getImage() != null) {
					imageValue = valueObject.getImage();
				} else {
					imageValue = item.getImage();
				}
				drawFill(doc, cb, item, valueObject);
				drawLine(doc, cb, item);
				drawImage(cb, item, imageValue);
				drawString(doc, cb, item, item.getWidth(), item.getHeight(), value, pageCount, pageNo, mp);
			}
		} else {
			value = item.getText();
			imageValue = item.getImage();
			drawFill(doc, cb, item, valueObject);
			drawLine(doc, cb, item);
			drawImage(cb, item, imageValue);
			drawString(doc, cb, item, item.getWidth(), item.getHeight(), value, pageCount, pageNo, mp);
		}
	}

	private void writeSeparateObject(Document doc, PdfContentByte cb, FormatSeparateObject item, HashMap dMap, int pageCount, int pageNo, JapaneseFontMapper mp) {
		DataValueObject valueObject = (DataValueObject) dMap.get(item);
		String value = null;
		if (valueObject != null) {
			if (valueObject.isVisible()) {
				if (valueObject.getValue() != null) {
					value = valueObject.getValue();
				} else {
					value = item.getText();
				}
				drawSeparate(doc, cb, item, value, pageCount, pageNo, mp);
			}
		} else {
			value = item.getText();
			drawSeparate(doc, cb, item, value, pageCount, pageNo, mp);
		}
	}

	private void writeTableObject(Document doc, PdfContentByte cb, FormatTableObject item, HashMap dMap, int pageCount, int pageNo, JapaneseFontMapper mp) {
		DataValueObject valueObject = (DataValueObject) dMap.get(item);
		if (valueObject != null) {
			if (valueObject.isVisible()) {
				// テーブルの背景描画
				drawFill(doc, cb, item, valueObject);
				// セルの背景描画
				Iterator itFill = item.getCells();
				while (itFill.hasNext()) {
					FormatCellObject cell = (FormatCellObject) itFill.next();
					drawFill(doc, cb, cell, valueObject);
				}
				// セルの枠線・文字列描画
				Iterator itValues = item.getCells();
				while (itValues.hasNext()) {
					FormatCellObject cell = (FormatCellObject) itValues.next();
					writeCellObject(doc, cb, cell, dMap, pageCount, pageNo, mp);
				}
				// テーブルの枠線描画
				drawLine(doc, cb, item);
			}
		} else {
			// テーブルの背景描画
			drawFill(doc, cb, item, valueObject);
			// セルの背景描画
			Iterator itFill = item.getCells();
			while (itFill.hasNext()) {
				FormatCellObject cell = (FormatCellObject) itFill.next();
				drawFill(doc, cb, cell, valueObject);
			}
			// セルの枠線・文字列描画
			Iterator itValues = item.getCells();
			while (itValues.hasNext()) {
				FormatCellObject cell = (FormatCellObject) itValues.next();
				writeCellObject(doc, cb, cell, dMap, pageCount, pageNo, mp);
			}
			// テーブルの枠線描画
			drawLine(doc, cb, item);
		}
	}

	private void writeCellObject(Document doc, PdfContentByte cb, FormatCellObject item, HashMap dMap, int pageCount, int pageNo, JapaneseFontMapper mp) {
		DataValueObject valueObject = (DataValueObject) dMap.get(item);
		String value = null;
		if (valueObject != null) {
			if (valueObject.isVisible()) {
				if (valueObject.getValue() != null) {
					value = valueObject.getValue();
				} else {
					value = item.getText();
				}
				drawFill(doc, cb, item, valueObject);
				drawLineCell(doc, cb, item);
				drawString(doc, cb, item, item.getTextWidth(), item.getTextHeight(), value, pageCount, pageNo, mp);
			}
		} else {
			value = item.getText();
			drawLineCell(doc, cb, item);
			drawString(doc, cb, item, item.getTextWidth(), item.getTextHeight(), value, pageCount, pageNo, mp);
		}
	}

	private void drawString(Document doc, PdfContentByte cb, FormatObject item, float width, float height, String value, int pageCount, int pageNo, JapaneseFontMapper mp) {
		if (value == null) {
			return;
		}
		if (value.length() == 0) {
			return;
		}
		boolean equally = item.isEqually();
		String[] values = value.split(System.getProperty("line.separator"));
		String formatValue = item.getFormat().toUpperCase();
		Font f = createPdfFont(item.getFontName(), item.getFontStyle(), (int) (item.getFontSize() * 0.9));
		BaseFont bf = mp.awtToPdf(f);

		cb.setColorStroke(item.getForeColor());
		cb.setColorFill(item.getForeColor());

		if (equally) {
			if (FormatObject.FORMAT_NONE.equals(formatValue)) {
				drawStringEquallyValues(doc, bf, f, cb, item, width, height, values, mp);
			} else if (FormatObject.FORMAT_AUTOSIZE.equals(formatValue)) {
				Font rf = calcStringAutoSizeString(bf, f, item, width, height, values);
				drawStringEquallyValues(doc, bf, rf, cb, item, width, height, values, mp);
			}
		} else {
			if (FormatObject.FORMAT_NONE.equals(formatValue)) {
				drawStringValues(doc, bf, f, cb, item, width, height, values, pageCount, pageNo, mp);
			} else if (FormatObject.FORMAT_AUTOSIZE.equals(formatValue)) {
				Font rf = calcStringAutoSizeString(bf, f, item, width, height, values);
				drawStringValues(doc, bf, rf, cb, item, width, height, values, pageCount, pageNo, mp);
			} else if (FormatObject.FORMAT_AUTOFORMAT.equals(formatValue)) {
				AutoFormat breakValues = calcStringAutoFormatString(cb, bf, f, item, width, height, values, mp);
				drawStringValues(doc, bf, breakValues.getFont(), cb, item, width, height, breakValues.getValues(), pageCount, pageNo, mp);
			} else if (FormatObject.FORMAT_WORDBREAK.equals(formatValue)) {
				String[] breakValues = calcStringWordBreakValue(cb, f, item, width, height, values, mp);
				drawStringValues(doc, bf, f, cb, item, width, height, breakValues, pageCount, pageNo, mp);
			}
		}

	}

	private void drawStringValues(Document doc, BaseFont bf, Font f, PdfContentByte cb, FormatObject item, float width, float height, String[] values, int pageCount, int pageNo, JapaneseFontMapper mp) {
		float ph = doc.getPageSize().height();
		float as = bf.getFontDescriptor(BaseFont.ASCENT, f.getSize2D());
		float ds = bf.getFontDescriptor(BaseFont.DESCENT, f.getSize2D());
		float fh = as - ds;
		for (int i = 0; i < values.length; i++) {
			String drawValue = drawTag(doc, cb, item, width, height, values[i], pageCount, pageNo);
			if (drawValue != null) {
				float valueWidth = bf.getWidthPoint(drawValue, f.getSize2D());
				float valueHeight = calcStringHeight(bf, f, values);
				float fontHeight = fh * i;
				Point2D p = calcStringPoint(bf, f, item, width, height, valueWidth, valueHeight);
				drawStringFontStyle(bf, f, cb, item.getFontStyle(), valueWidth, (float) p.getX(), ph - (float) p.getY() - fontHeight);
				cb.beginText();
				cb.setTextMatrix((float) p.getX(), ph - (float) p.getY() - fontHeight);
				cb.setFontAndSize(bf, f.getSize2D());
				cb.showText(drawValue);
				cb.endText();
			}
		}
	}

	private void drawStringEquallyValues(Document doc, BaseFont bf, Font f, PdfContentByte cb, FormatObject item, float width, float height, String[] values, JapaneseFontMapper mp) {
		float ph = doc.getPageSize().height();
		float fh = bf.getFontDescriptor(BaseFont.ASCENT, f.getSize2D()) - bf.getFontDescriptor(BaseFont.DESCENT, f.getSize2D());
		float valueWidth = 0;
		float valueHeight = 0;

		for (int i = 0; i < values.length; i++) {
			valueWidth = (int) bf.getWidthPoint(values[i], f.getSize2D());
			valueHeight = calcStringHeight(bf, f, values);
			if (valueWidth <= width && values[i].length() > 1) {
				int totalWidth = 0;
				float equallyX = item.getX();
				float equallyWidth = (width - valueWidth) / (values[i].length() - 1);
				Point2D p = calcStringPoint(bf, f, item, width, height, valueWidth, valueHeight);
				for (int j = 0; j <= values[i].length() - 1; j++) {
					String s = values[i].substring(j, j + 1);
					cb.beginText();
					cb.setTextMatrix(equallyX, ph - (float) p.getY() - (fh * i));
					cb.setFontAndSize(bf, f.getSize2D());
					cb.showText(s);
					cb.endText();
					equallyX += bf.getWidthPoint(s, f.getSize2D()) + equallyWidth;
					totalWidth += equallyX;
				}
				drawStringFontStyle(bf, f, cb, item.getFontStyle(), totalWidth, item.getX(), (float) p.getY() + (fh * i));
			} else {
				valueWidth = (int) bf.getWidthPoint(values[i], f.getSize2D());
				valueHeight = calcStringHeight(bf, f, values);
				Point2D p = calcStringPoint(bf, f, item, width, height, valueWidth, valueHeight);
				cb.beginText();
				cb.setTextMatrix((float) p.getX(), ph - (float) p.getY() - (fh * i));
				cb.setFontAndSize(bf, f.getSize2D());
				cb.showText(values[i]);
				cb.endText();
			}
		}
	}

	private void drawStringFontStyle(BaseFont bf, Font f, PdfContentByte cb, int fontStyle, float width, float x, float y) {
		float as = bf.getFontDescriptor(BaseFont.ASCENT, f.getSize2D());
		float ds = bf.getFontDescriptor(BaseFont.DESCENT, f.getSize2D());
		if ((fontStyle & FONT_STYLE_UNDERLINE) == FONT_STYLE_UNDERLINE) {
			cb.moveTo(x, y + (ds / 2));
			cb.lineTo(x + width, y + (ds / 2));
			cb.stroke();
		}
		if ((fontStyle & FONT_STYLE_STRIKE) == FONT_STYLE_STRIKE) {
			cb.moveTo(x, y - (as / 2) + (ds / 2));
			cb.lineTo(x + width, y - (as / 2) + (ds / 2));
			cb.stroke();
		}
	}

	private void drawSeparate(Document doc, PdfContentByte cb, FormatSeparateObject item, String value, int pageCount, int pageNo, JapaneseFontMapper mp) {
		ArrayList list = new ArrayList();
		float ph = doc.getPageSize().height();
		double splitWidth = (double) item.getWidth() / item.getCols();
		double splitHeight = (double) item.getHeight() / item.getRows();
		double divideWidth = splitWidth;
		double divideHeight = splitHeight;
		if (item.getSlitWidth() > 0) {
			divideWidth -= item.getSlitWidth();
			divideHeight -= item.getSlitHeight();
		}
		ArrayList dValueList = new ArrayList();
		if (value != null) {
			String[] dValues = value.split(System.getProperty("line.separator"));
			for (int i = 0; i < dValues.length; i++) {
				String sValue = dValues[i];
				if (sValue.length() < item.getCols()) {
					dValueList.add(sValue);
				} else {
					int l = 0;
					int c = 0;
					for (int x = 0; x < sValue.length(); x++) {
						if (c > item.getCols() - 1) {
							dValueList.add(sValue.substring(l, l + c));
							c = 0;
							l = x;
						}
						c++;
					}
					dValueList.add(sValue.substring(l));
				}
			}
		}
		for (int x = 0; x < item.getCols(); x++) {
			for (int y = 0; y < item.getRows(); y++) {
				FormatSeparateObject divideObj = new FormatSeparateObject(null);
				divideObj.setX(item.getX() + (int) (splitWidth * x));
				divideObj.setY(item.getY() + (int) (splitHeight * y));
				divideObj.setShape(item.getShape());
				divideObj.setWidth((int) divideWidth);
				divideObj.setHeight((int) divideHeight);
				divideObj.setAlignment(item.getAlignment());
				divideObj.setForeColor(item.getForeColor());
				divideObj.setFormat(item.getFormat());
				divideObj.setFontName(item.getFontName());
				divideObj.setFontSize(item.getFontSize());
				divideObj.setFontStyle(item.getFontStyle());
				divideObj.setBorderColor(item.getBorderColor());
				divideObj.setBorderType(item.getBorderType());
				divideObj.setBorderWidth(item.getBorderWidth());
				divideObj.setBackColor(item.getBackColor());
				divideObj.setBackStyle(item.getBackStyle());
				divideObj.setSlitWidth(item.getSlitWidth());
				divideObj.setSlitHeight(item.getSlitHeight());
				divideObj.setLt(item.getLt());
				divideObj.setLb(item.getLb());
				divideObj.setRt(item.getRt());
				divideObj.setRb(item.getRb());
				if (dValueList.size() > y) {
					String divideValue = (String) dValueList.get(y);
					if (divideValue.length() > x) {
						divideObj.setText(Character.toString(divideValue.charAt(x)));
					}
				}
				list.add(divideObj);
			}
		}

		for (int i = 0; i < list.size(); i++) {
			FormatSeparateObject divideObj = (FormatSeparateObject) list.get(i);
			drawFill(doc, cb, divideObj, null);
			if (divideObj.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(divideObj.getBorderType().toUpperCase())) {
				cb.setColorStroke(divideObj.getBorderColor());
				createStroke(cb, divideObj.getBorderType(), divideObj.getBorderWidth());
				if (divideObj.getSlitWidth() > 0) {
					drawLine(doc, cb, divideObj);
				} else {
					cb.moveTo(divideObj.getX(), ph - divideObj.getBottom());
					cb.lineTo(divideObj.getX() + divideObj.getWidth(), ph - divideObj.getBottom());
					cb.stroke();
					cb.moveTo(divideObj.getX(), ph - divideObj.getBottom() + divideObj.getHeight());
					cb.lineTo(divideObj.getX() + divideObj.getWidth(), ph - divideObj.getBottom() + divideObj.getHeight());
					cb.stroke();
					cb.moveTo(divideObj.getX(), ph - divideObj.getBottom());
					cb.lineTo(divideObj.getX(), ph - divideObj.getBottom() + divideObj.getHeight());
					cb.stroke();
				}
			}
			if (divideObj.getText() != null) {
				drawString(doc, cb, divideObj, divideObj.getWidth(), divideObj.getHeight(), divideObj.getText(), pageCount, pageNo, mp);
			}
		}
	}

	private void drawLine(Document doc, PdfContentByte cb, FormatObject item) {
		if (item.getShape() == null) {
			return;
		}
		float ph = doc.getPageSize().height();
		String shapeValue = item.getShape().toUpperCase();
		if (FormatObject.SHAPE_NONE.equals(shapeValue)) {
			if (item.getTopLine() != null) {
				FormatBorderLineObject line = item.getTopLine();
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.setColorStroke(line.getBorderColor());
					createStroke(cb, line.getBorderType(), line.getBorderWidth());
					cb.moveTo(item.getX(), ph - item.getBottom() + item.getHeight());
					cb.lineTo(item.getX() + item.getWidth(), ph - item.getBottom() + item.getHeight());
					cb.stroke();
				}
			}
			if (item.getBottomLine() != null) {
				FormatBorderLineObject line = item.getBottomLine();
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.setColorStroke(line.getBorderColor());
					createStroke(cb, line.getBorderType(), line.getBorderWidth());
					cb.moveTo(item.getX(), ph - item.getBottom());
					cb.lineTo(item.getX() + item.getWidth(), ph - item.getBottom());
					cb.stroke();
				}
			}
			if (item.getLeftLine() != null) {
				FormatBorderLineObject line = item.getLeftLine();
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.setColorStroke(line.getBorderColor());
					createStroke(cb, line.getBorderType(), line.getBorderWidth());
					cb.moveTo(item.getX(), ph - item.getBottom());
					cb.lineTo(item.getX(), ph - item.getBottom() + item.getHeight());
					cb.stroke();
				}
			}
			if (item.getRightLine() != null) {
				FormatBorderLineObject line = item.getRightLine();
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.setColorStroke(line.getBorderColor());
					createStroke(cb, line.getBorderType(), line.getBorderWidth());
					cb.moveTo(item.getX() + item.getWidth(), ph - item.getBottom());
					cb.lineTo(item.getX() + item.getWidth(), ph - item.getBottom() + item.getHeight());
					cb.stroke();
				}
			}
			if (item.getRiseLine() != null) {
				FormatBorderLineObject line = item.getRiseLine();
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.setColorStroke(line.getBorderColor());
					createStroke(cb, line.getBorderType(), line.getBorderWidth());
                    cb.moveTo(item.getX(), ph - item.getBottom());
                    cb.lineTo(item.getX() + item.getWidth(), ph - item.getBottom() + item.getHeight());
					cb.stroke();
				}
			}
			if (item.getDownLine() != null) {
				FormatBorderLineObject line = item.getDownLine();
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.setColorStroke(line.getBorderColor());
					createStroke(cb, line.getBorderType(), line.getBorderWidth());
                    cb.moveTo(item.getX(), ph - item.getBottom() + item.getHeight());
                    cb.lineTo(item.getX() + item.getWidth(), ph - item.getBottom());
					cb.stroke();
				}
			}
		} else if (FormatObject.SHAPE_RECTANGLE.equals(shapeValue)) {
			if (item.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(item.getBorderType().toUpperCase())) {
				cb.setColorStroke(item.getBorderColor());
				createStroke(cb, item.getBorderType(), item.getBorderWidth());
				cb.rectangle(item.getX(), ph - item.getBottom(), item.getWidth(), item.getHeight());
				cb.stroke();
			}
		} else if (FormatObject.SHAPE_ELLIPSE.equals(shapeValue)) {
			if (item.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(item.getBorderType().toUpperCase())) {
				cb.setColorStroke(item.getBorderColor());
				createStroke(cb, item.getBorderType(), item.getBorderWidth());
				cb.ellipse(item.getX(), ph - item.getBottom(), item.getX() + item.getWidth(), ph - item.getBottom() + item.getHeight());
				cb.stroke();
			}
		} else if (FormatObject.SHAPE_ROUND.equals(shapeValue)) {
			if (item.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(item.getBorderType().toUpperCase())) {
				drawRoundRect(cb, item.getX(), ph - item.getBottom(), item.getX() + item.getWidth(), ph - item.getBottom() + item.getHeight(), item.getLt(), item.getLb(), item.getRt(), item.getRb());
				cb.stroke();
			}
		}
	}

	private Object wrapCast(FormatObject item, DataValueObject valueObject) {
		if (valueObject != null) {
			valueObject.setParent(item);
			return valueObject;
		} else {
			return item;
		}
	}

	private void drawFill(Document doc, PdfContentByte cb, FormatObject item, DataValueObject valueObject) {
		DrawFillParameter param = (DrawFillParameter) wrapCast(item, valueObject);
		if (param.getBackStyle() == null) {
			return;
		}
		int x = item.getX();
		int y = item.getY();
		int w = item.getWidth();
		int h = item.getHeight();

		float ph = doc.getPageSize().height();
		String backStyleValue = param.getBackStyle().toUpperCase();

		if (FormatObject.BACKSTYLE_CLEAR.equals(backStyleValue)) {
		} else if (FormatObject.BACKSTYLE_SOLID.equals(backStyleValue)) {
			cb.setColorFill(param.getBackColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			cb.rectangle(item.getX(), ph - item.getBottom(), item.getWidth(), item.getHeight());
			cb.fill();
		} else if (FormatObject.BACKSTYLE_HORIZONTAL.equals(backStyleValue)) {
			cb.setColorStroke(item.getBorderColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			for (int i = 0; i <= h - 1; i = i + 4) {
				cb.moveTo(x, y + i);
				cb.lineTo(x + w, y + i);
				cb.stroke();
			}
		} else if (FormatObject.BACKSTYLE_VERTICAL.equals(backStyleValue)) {
			cb.setColorStroke(item.getBorderColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			for (int i = 0; i <= w - 1; i = i + 4) {
				cb.moveTo(x + i, y);
				cb.lineTo(x + i, y + h);
				cb.stroke();
			}
		} else if (FormatObject.BACKSTYLE_FDIAGONAL.equals(backStyleValue)) {
			cb.setColorStroke(item.getBorderColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			for (int i = x - h; i <= x + w; i = i + 4) {
				int sx = i;
				int sy = y;
				int ex = i + h;
				int ey = y + h;
				if (sx < x) {
					sy = sy + (x - sx);
					sx = x;
				}
				if (ex > x + w) {
					ey = ey + (x + w - ex);
					ex = x + w;
				}
				cb.moveTo(sx, sy);
				cb.lineTo(ex, ey);
				cb.stroke();
			}
		} else if (FormatObject.BACKSTYLE_BDIAGONAL.equals(backStyleValue)) {
			cb.setColorStroke(item.getBorderColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			for (int i = x - h; i <= x + w; i = i + 4) {
				int sx = i;
				int sy = y + h;
				int ex = i + h;
				int ey = y;
				if (sx < x) {
					sy = sy - (x - sx);
					sx = x;
				}
				if (ex > x + w) {
					ey = ey - (x + w - ex);
					ex = x + w;
				}
				cb.moveTo(sx, sy);
				cb.lineTo(ex, ey);
				cb.stroke();
			}
		} else if (FormatObject.BACKSTYLE_CROSS.equals(backStyleValue)) {
			cb.setColorStroke(item.getBorderColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			for (int i = 0; i <= h - 1; i = i + 4) {
				cb.moveTo(x, y + i);
				cb.lineTo(x + w, y + i);
				cb.stroke();
			}
			for (int i = 0; i <= w - 1; i = i + 4) {
				cb.moveTo(x + i, y);
				cb.lineTo(x + i, y + h);
				cb.stroke();
			}
		} else if (FormatObject.BACKSTYLE_DIAGCROSS.equals(backStyleValue)) {
			cb.setColorStroke(item.getBorderColor());
			cb.setLineWidth(0.5F);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
			for (int i = x - h; i <= x + w; i = i + 4) {
				int sx = i;
				int sy = y;
				int ex = i + h;
				int ey = y + h;
				if (sx < x) {
					sy = y + (x - sx);
					sx = x;
				}
				if (ex > x + w) {
					ey = ey + (x + w - ex);
					ex = x + w;
				}
				cb.moveTo(sx, sy);
				cb.lineTo(ex, ey);
				cb.stroke();
			}
			for (int i = x - h; i <= x + w; i = i + 4) {
				int sx = i;
				int sy = y + h;
				int ex = i + h;
				int ey = y;
				if (sx < x) {
					sy = sy - (x - sx);
					sx = x;
				}
				if (ex > x + w) {
					ey = ey - (x + w - ex);
					ex = x + w;
				}
				cb.moveTo(sx, sy);
				cb.lineTo(ex, ey);
				cb.stroke();
			}
		}

	}

	private void drawLineCell(Document doc, PdfContentByte cb, FormatCellObject cell) {
		float ph = doc.getPageSize().height();
		// RowSpan計算
		if (cell.getRowIndex() < cell.getRowCount() - 1) {
			// 下枠線
			if (cell.getBottomLine() != null) {
				FormatBorderLineObject line = cell.getBottomLine();
				cb.setColorStroke(line.getBorderColor());
				createStroke(cb, line.getBorderType(), line.getBorderWidth());
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.moveTo(cell.getX(), ph - cell.getBottom());
					cb.lineTo(cell.getX() + cell.getWidth(), ph - cell.getBottom());
					cb.stroke();
				}
			} else {
				FormatBorderLineObject line = cell.getHorizontalLine();
				cb.setColorStroke(line.getBorderColor());
				createStroke(cb, line.getBorderType(), line.getBorderWidth());
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.moveTo(cell.getX(), ph - cell.getBottom());
					cb.lineTo(cell.getX() + cell.getWidth(), ph - cell.getBottom());
					cb.stroke();
				}
			}
		}
		// ColSpan計算
		if (cell.getColumnIndex() < cell.getColumnCount() - 1) {
			// 右枠線
			if (cell.getRightLine() != null) {
				FormatBorderLineObject line = cell.getRightLine();
				cb.setColorStroke(line.getBorderColor());
				createStroke(cb, line.getBorderType(), line.getBorderWidth());
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.moveTo(cell.getX() + cell.getWidth(), ph - cell.getBottom());
					cb.lineTo(cell.getX() + cell.getWidth(), ph - cell.getBottom() + cell.getHeight());
					cb.stroke();
				}
			} else {
				FormatBorderLineObject line = cell.getVerticalLine();
				cb.setColorStroke(line.getBorderColor());
				createStroke(cb, line.getBorderType(), line.getBorderWidth());
				if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
					cb.moveTo(cell.getX() + cell.getWidth(), ph - cell.getBottom());
					cb.lineTo(cell.getX() + cell.getWidth(), ph - cell.getBottom() + cell.getHeight());
					cb.stroke();
				}
			}
		}
		if (cell.getRiseLine() != null) {
			FormatBorderLineObject line = cell.getRiseLine();
			cb.setColorStroke(line.getBorderColor());
			createStroke(cb, line.getBorderType(), line.getBorderWidth());
			if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
				cb.moveTo(cell.getX(), ph - cell.getBottom() + cell.getHeight());
				cb.lineTo(cell.getX() + cell.getWidth(), ph - cell.getBottom());
				cb.stroke();
			}
		}
		if (cell.getDownLine() != null) {
			FormatBorderLineObject line = cell.getDownLine();
			cb.setColorStroke(line.getBorderColor());
			createStroke(cb, line.getBorderType(), line.getBorderWidth());
			if (line.getBorderType() != null && !FormatObject.BORDER_TYPE_CLEAR.equals(line.getBorderType().toUpperCase())) {
				cb.moveTo(cell.getX(), ph - cell.getBottom());
				cb.lineTo(cell.getX() + cell.getWidth(), ph - cell.getBottom() + cell.getHeight());
				cb.stroke();
			}
		}
	}

	private void drawImage(PdfContentByte cb, FormatObject item, String imageValue) throws IOException, BadElementException, DocumentException, MalformedURLException {
		if (imageValue == null) {
			return;
		}
		if (imageValue.length() == 0) {
			return;
		}
		byte[] b = VRBase64.decode(imageValue);
		if (b.length > 0) {
			com.lowagie.text.Image pdfImage = com.lowagie.text.Image.getInstance(b);
			cb.addImage(pdfImage, item.getWidth(), 0, 0, item.getHeight(), item.getX(), item.getY());
		}
	}

	private void drawRoundRect(PdfContentByte cb, float x1, float y1, float x2, float y2, float lt, float rt, float lb, float rb) {

		float width = x2 - x1;
		float height = y2 - y1;

		float mr;
		if (width > height) {
			mr = height / 2;
		} else {
			mr = width / 2;
		}
		lt = (lt < mr) ? lt : mr;
		rt = (rt < mr) ? rt : mr;
		lb = (lb < mr) ? lb : mr;
		rb = (rb < mr) ? rb : mr;

		cb.moveTo(x1, y1 + lt);

		if (lt > 0) {
			cb.curveTo(x1, y1, x1 + lt, y1);
		}
		cb.lineTo(x1 + width - rt, y1);

		if (rt > 0) {
			cb.curveTo(x1 + width, y1, x1 + width, y1 + rt);
		}
		cb.lineTo(x1 + width, y1 + height - rb);

		if (rb > 0) {
			cb.curveTo(x1 + width, y1 + height, x1 + width - rb, y1 + height);
		}
		cb.lineTo(x1 + lb, y1 + height);

		if (lb > 0) {
			cb.curveTo(x1, y1 + height, x1, y1 + height - lb);
		}

		cb.closePath();

	}

	private String drawTag(Document doc, PdfContentByte cb, FormatObject item, float width, float height, String value, int pageCount, int pageNo) {
		if (value == null) {
			return null;
		}
		if (value.length() == 0) {
			return null;
		}
		float ph = doc.getPageSize().height();
		String valueCase = value.toUpperCase();
		String tagValue = value;
		if (TAG_DATE.indexOf(valueCase) != -1) {
			DateFormat d = new SimpleDateFormat("yyyy/MM/dd");
			String date = d.format(Calendar.getInstance().getTime());
			tagValue = value.replaceAll(TAG_DATE, date);
		} else if (TAG_DATETIME.indexOf(valueCase) != -1) {
			DateFormat d = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			String date = d.format(Calendar.getInstance().getTime());
			tagValue = value.replaceAll(TAG_DATETIME, date);
//		} else if (TAG_PAGECOUNT.indexOf(valueCase) != -1) {
//			tagValue = value.replaceAll(TAG_PAGECOUNT, String.valueOf(pageCount));
		} else if (TAG_PAGENO.indexOf(valueCase) != -1) {
			tagValue = value.replaceAll(TAG_PAGENO, String.valueOf(pageNo));
        //2006.9.11 fujihara shin 判定式を修正　上記の判定も不正だが、実害はないため、
        //Riseline,Downlineのみ修正
        //} else if (TAG_RISELINE.indexOf(valueCase) != -1) {
        } else if (valueCase.indexOf(TAG_RISELINE) != -1) {
			tagValue = null;
			cb.setColorStroke(item.getForeColor());
			createStroke(cb, FormatObject.BORDER_TYPE_SOLID, 0.5F);
			cb.moveTo(item.getX(), ph - item.getY() - height);
			cb.lineTo(item.getX() + width, ph - item.getY());
			cb.stroke();
        //} else if (TAG_DOWNLINE.indexOf(valueCase) != -1) {
        } else if (valueCase.indexOf(TAG_DOWNLINE) != -1) {
			tagValue = null;
			cb.setColorStroke(item.getForeColor());
			createStroke(cb, FormatObject.BORDER_TYPE_SOLID, 0.5F);
			cb.moveTo(item.getX(), ph - item.getY());
			cb.lineTo(item.getX() + width, ph - item.getY() - height);
			cb.stroke();
		}
		return tagValue;
	}

	private Point2D calcStringPoint(BaseFont bf, Font f, FormatObject item, float width, float height, float valueWidth, float valueHeight) {
		Point2D p = new Point2D.Float();
		float as = bf.getFontDescriptor(BaseFont.ASCENT, f.getSize2D());
		float ds = bf.getFontDescriptor(BaseFont.DESCENT, f.getSize2D());
		if (item.getAlignment() != null) {
			String alignmentValue = item.getAlignment().toUpperCase();
			if (FormatObject.ALIGNMENT_LEFT_TOP.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + item.getMargin(), item.getY() + item.getMargin() + as);
			} else if (FormatObject.ALIGNMENT_LEFT_MIDDLE.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + item.getMargin(), item.getY() + (height / 2) + as - (valueHeight / 2));
			} else if (FormatObject.ALIGNMENT_LEFT_BOTTOM.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + item.getMargin(), item.getY() + height - valueHeight - item.getMargin() + as + ds);
			} else if (FormatObject.ALIGNMENT_CENTER_TOP.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + (width / 2) - (valueWidth / 2), item.getY() + item.getMargin() + as);
			} else if (FormatObject.ALIGNMENT_CENTER_MIDDLE.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + (width / 2) - (valueWidth / 2), item.getY() + (height / 2) + as - (valueHeight / 2));
			} else if (FormatObject.ALIGNMENT_CENTER_BOTTOM.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + (width / 2) - (valueWidth / 2), item.getY() + height - valueHeight - item.getMargin() + as + ds);
			} else if (FormatObject.ALIGNMENT_RIGHT_TOP.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + width - valueWidth - item.getMargin(), item.getY() + item.getMargin() + as);
			} else if (FormatObject.ALIGNMENT_RIGHT_MIDDLE.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + width - valueWidth - item.getMargin(), item.getY() + (height / 2) + as - (valueHeight / 2));
			} else if (FormatObject.ALIGNMENT_RIGHT_BOTTOM.equals(alignmentValue)) {
				p = new Point2D.Float(item.getX() + width - valueWidth - item.getMargin(), item.getY() + height - valueHeight - item.getMargin() + as + ds);
			} else {
				p = new Point2D.Float(item.getX() + item.getMargin(), item.getY() + item.getMargin() + as);
			}
		} else {
			p = new Point2D.Float(item.getX() + item.getMargin(), item.getY() + item.getMargin() + as);
		}
		return p;
	}

	private AutoFormat calcStringAutoFormatString(PdfContentByte cb, BaseFont bf, Font f, FormatObject item, float width, float height, String[] values, JapaneseFontMapper mp) {
		String[] rtnValues = null;
		Font rf = f;
		while (true) {
			String[] breakValues = calcStringWordBreakValue(cb, rf, item, width, height, values, mp);
			float cWidth = calcStringWidth(bf, rf, breakValues);
			float cHeight = calcStringHeight(bf, rf, breakValues);
			if ((width >= cWidth) && (height >= cHeight)) {
				rtnValues = breakValues;
				break;
			} else {
				rf = createPdfFont(item.getFontName(), item.getFontStyle(), rf.getSize() - 1);
			}
		}
		return new AutoFormat(rtnValues, rf);
	}

	private Font calcStringAutoSizeString(BaseFont bf, Font f, FormatObject item, float width, float height, String[] values) {
		float rWidth = width - item.getMargin();
		float rHeight = height - item.getMargin();
		float cWidth = calcStringWidth(bf, f, values);
		float cHeight = calcStringHeight(bf, f, values);
		Font rf = createPdfFont(item.getFontName(), item.getFontStyle(), f.getSize());
		while (true) {
			if ((rWidth >= cWidth) && (rHeight >= cHeight)) {
				break;
			}
			float rate = 0;
			float widthRate = 0;
			float heightRate = 0;
			widthRate = rWidth / cWidth;
			heightRate = rHeight / cHeight;
			rate = Math.min(widthRate, heightRate);
			rf = createPdfFont(item.getFontName(), item.getFontStyle(), (int) (rf.getSize2D() * rate));
			cWidth = calcStringWidth(bf, rf, values);
			cHeight = calcStringHeight(bf, rf, values);
		}
		return rf;
	}

	private String[] calcStringWordBreakValue(PdfContentByte cb, Font f, FormatObject item, float width, float height, String[] values, JapaneseFontMapper mp) {
		Graphics2D g2 = cb.createGraphics(width, height, mp);

		g2.setFont(f);
		Map attr = g2.getFont().getAttributes();
		FontRenderContext fontR = g2.getFontRenderContext();

		ArrayList list = new ArrayList();
		for (int i = 0; i < values.length; i++) {
			if (values[i] != null && values[i].length() > 0) {
				AttributedString attrString = new AttributedString(values[i], attr);
				AttributedCharacterIterator it = attrString.getIterator();
				LineBreakMeasurer lbm = new LineBreakMeasurer(it, fontR);
				lbm.setPosition(0);
				int oldpos = 0;
				int newpos = lbm.nextOffset((float) width - item.getMargin() - item.getBorderWidth());
				while (oldpos != newpos) {
					String s = values[i].substring(oldpos, newpos);
					int smax = s.length();
					for (int j = smax - 1; j > 0; j--) {
						if (s.charAt(j) == '　' || s.charAt(j) == ' ') {
							smax--;
						} else {
							break;
						}
					}
					if (smax < s.length()) {
						s = s.substring(0, smax);
					}
					lbm.setPosition(newpos);
					oldpos = newpos;
					newpos = lbm.nextOffset((float) width - item.getMargin() - item.getBorderWidth());
					list.add(s);
				}
			} else {
				list.add(values[i]);
			}
		}
		g2.dispose();
		String[] rtnValues = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			rtnValues[i] = (String) list.get(i);
		}
		return rtnValues;
	}

	private float calcStringWidth(BaseFont bf, Font f, String[] values) {
		float width = 0;
		for (int i = 0; i < values.length; i++) {
			width = Math.max(width, bf.getWidthPoint(values[i], f.getSize2D()));
		}
		return width;
	}

	private float calcStringHeight(BaseFont bf, Font f, String[] values) {
		float height = 0;
		float fh = bf.getFontDescriptor(BaseFont.ASCENT, f.getSize2D()) - bf.getFontDescriptor(BaseFont.DESCENT, f.getSize2D());
		for (int i = 0; i < values.length; i++) {
			height += fh;
		}
		return height;
	}

	private void createStroke(PdfContentByte cb, String type, float width) {
		String borderTypeValue = type.toUpperCase();
		if (FormatObject.BORDER_TYPE_SOLID.equals(borderTypeValue)) {
			cb.setLineDash(0f);
			cb.setLineWidth(width);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
		} else if (FormatObject.BORDER_TYPE_DOT.equals(borderTypeValue)) {
			float[] dash = new float[] { width * 2f, width * 2f };
			cb.setLineWidth(width);
			cb.setLineDash(dash, 0f);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
		} else if (FormatObject.BORDER_TYPE_DASH.equals(borderTypeValue)) {
			float[] dash = new float[] { width * 6, width * 2 };
			cb.setLineWidth(width);
			cb.setLineDash(dash, 0f);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
		} else if (FormatObject.BORDER_TYPE_DASHDOT.equals(borderTypeValue)) {
			float[] dash = new float[] { width * 6, width * 2, width * 2, width * 2 };
			cb.setLineWidth(width);
			cb.setLineDash(dash, 0f);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
		} else if (FormatObject.BORDER_TYPE_DASHDOTDOT.equals(borderTypeValue)) {
			float[] dash = new float[] { width * 6, width * 2, width * 2, width * 2, width * 2, width * 2 };
			cb.setLineWidth(width);
			cb.setLineDash(dash, 0f);
			cb.setLineCap(PdfContentByte.LINE_CAP_ROUND);
			cb.setLineJoin(PdfContentByte.LINE_JOIN_MITER);
		} else if (FormatObject.BORDER_TYPE_DOUBLE.equals(borderTypeValue)) {
			cb.setLineWidth(0f);
		} else {
			cb.setLineWidth(0f);
		}
	}

	private Rectangle calcPageSize(FormatObject paperObj) {
		if (paperObj.getMediaSizeName() == null) {
			return new Rectangle(0, 0, paperObj.getWidth(), paperObj.getHeight());
		}
		if (paperObj.getOrientation() == null) {
			return new Rectangle(0, 0, paperObj.getWidth(), paperObj.getHeight());
		}
		String mediaSizeValue = paperObj.getMediaSizeName().toUpperCase();
		String orientationValue = paperObj.getOrientation().toUpperCase();
		if (ORIENTATION_PORTRAIT.equals(orientationValue)) {
			if (MEDIASIZE_A5.equals(mediaSizeValue)) {
				return PageSize.A5;
			} else if (MEDIASIZE_A4.equals(mediaSizeValue)) {
				return PageSize.A4;
			} else if (MEDIASIZE_A3.equals(mediaSizeValue)) {
				return PageSize.A3;
			} else if (MEDIASIZE_A2.equals(mediaSizeValue)) {
				return PageSize.A2;
			} else if (MEDIASIZE_A1.equals(mediaSizeValue)) {
				return PageSize.A1;
			} else if (MEDIASIZE_A0.equals(mediaSizeValue)) {
				return PageSize.A0;
			} else if (MEDIASIZE_B5.equals(mediaSizeValue)) {
				return PageSize.B5;
			} else if (MEDIASIZE_B4.equals(mediaSizeValue)) {
				return PageSize.B4;
			} else if (MEDIASIZE_B3.equals(mediaSizeValue)) {
				return PageSize.B3;
			} else if (MEDIASIZE_B2.equals(mediaSizeValue)) {
				return PageSize.B2;
			} else if (MEDIASIZE_B1.equals(mediaSizeValue)) {
				return PageSize.B1;
			} else if (MEDIASIZE_B0.equals(mediaSizeValue)) {
				return PageSize.B0;
			}
		} else if (ORIENTATION_LANDSCAPE.equals(orientationValue)) {
			if (MEDIASIZE_A5.equals(mediaSizeValue)) {
				return PageSize.A5.rotate();
			} else if (MEDIASIZE_A4.equals(mediaSizeValue)) {
				return PageSize.A4.rotate();
			} else if (MEDIASIZE_A3.equals(mediaSizeValue)) {
				return PageSize.A3.rotate();
			} else if (MEDIASIZE_A2.equals(mediaSizeValue)) {
				return PageSize.A2.rotate();
			} else if (MEDIASIZE_A1.equals(mediaSizeValue)) {
				return PageSize.A1.rotate();
			} else if (MEDIASIZE_A0.equals(mediaSizeValue)) {
				return PageSize.A0.rotate();
			} else if (MEDIASIZE_B5.equals(mediaSizeValue)) {
				return PageSize.B5.rotate();
			} else if (MEDIASIZE_B4.equals(mediaSizeValue)) {
				return PageSize.B4.rotate();
			} else if (MEDIASIZE_B3.equals(mediaSizeValue)) {
				return PageSize.B3.rotate();
			} else if (MEDIASIZE_B2.equals(mediaSizeValue)) {
				return PageSize.B2.rotate();
			} else if (MEDIASIZE_B1.equals(mediaSizeValue)) {
				return PageSize.B1.rotate();
			} else if (MEDIASIZE_B0.equals(mediaSizeValue)) {
				return PageSize.B0.rotate();
			}
		}
		return new Rectangle(0, 0, paperObj.getWidth(), paperObj.getHeight());
	}

	private Font createPdfFont(String name, int style, int size) {
		Font f = DEFAULT_FONT;
		if (localFonts != null) {
			if (name.indexOf(FONT_NAME_M) != -1) {
				// 明朝体のフォントを検索
				for (int i = 0; i < PHYSICAL_FONTNAME_MINCHO.length; i++) {
					for (int x = 0; x < localFonts.length; x++) {
						if (localFonts[x].getFontName().equals(PHYSICAL_FONTNAME_MINCHO[i])) {
							return new Font(localFonts[x].getFontName(), style, size);
						}
					}
				}
			} else if (name.indexOf(FONT_NAME_G) != -1) {
				// ゴシック体のフォントを検索
				for (int i = 0; i < PHYSICAL_FONTNAME_GOTHIC.length; i++) {
					for (int x = 0; x < localFonts.length; x++) {
						if (localFonts[x].getFontName().equals(PHYSICAL_FONTNAME_GOTHIC[i])) {
							return new Font(localFonts[x].getFontName(), style, size);
						}
					}
				}
			}
		}
		return f;
	}

	private class AutoFormat {

		private String[] values;

		private Font font;

		public AutoFormat(String[] values, Font font) {
			this.values = values;
			this.font = font;
		}

		public String[] getValues() {
			return this.values;
		}

		public Font getFont() {
			return this.font;
		}

	}

	private class JapaneseFontMapper implements FontMapper {

		private BaseFont mFont;

		private BaseFont gFont;

		private String[] minchoNames;

		private String[] gothicNames;

		public JapaneseFontMapper(String[] minchoNames, String[] gothicNames) throws IOException, DocumentException {
			this.mFont = BaseFont.createFont("HeiseiMin-W3", "UniJIS-UCS2-HW-H", BaseFont.NOT_EMBEDDED);
			this.gFont = BaseFont.createFont("HeiseiKakuGo-W5", "UniJIS-UCS2-HW-H", BaseFont.NOT_EMBEDDED);
			this.minchoNames = minchoNames;
			this.gothicNames = gothicNames;
		}

		public BaseFont awtToPdf(Font font) {
			// 明朝体フォントを検索
			for (int i = 0; i < minchoNames.length; i++) {
				if (font.getFontName().equals(minchoNames[i])) {
					return mFont;
				}
			}
			// ゴシックフォントを検索
			for (int i = 0; i < gothicNames.length; i++) {
				if (font.getFontName().equals(gothicNames[i])) {
					return gFont;
				}
			}
			// 当てはまらない場合は一律ゴシック体を返す
			return gFont;
		}

		public Font pdfToAwt(BaseFont font, int size) {
			return null;
		}

	}
    
    private interface DataXmlEventListener extends EventListener{
        public void startParse(DataXmlReader source);
        public void startPage(DataXmlReader source);
        public void endPage(DataXmlReader source);
        public void endParse(DataXmlReader source);
    }

	private class DataXmlReader extends DefaultHandler {
        protected EventListenerList listenerList = new EventListenerList();

        public void addDataXmlEventListener(DataXmlEventListener listener) {
            listenerList.add(DataXmlEventListener.class, listener);
        }

        public void removeDataXmlEventListener(DataXmlEventListener listener) {
            listenerList.remove(DataXmlEventListener.class, listener);
        }

        public synchronized DataXmlEventListener[] getDataXmlEventListeners() {
            return (DataXmlEventListener[]) (listenerList.getListeners(DataXmlEventListener.class));
        }

        protected void fireStartParse() {
            DataXmlEventListener[] listeners = getDataXmlEventListeners();
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].startParse(this);
            }
        }

        protected void fireStartPage()  {
            DataXmlEventListener[] listeners = getDataXmlEventListeners();
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].startPage(this);
            }
        }

        protected void fireEndPage(){
            DataXmlEventListener[] listeners = getDataXmlEventListeners();
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].endPage(this);
            }
        }
        protected void fireEndParse() {
            DataXmlEventListener[] listeners = getDataXmlEventListeners();
            for (int i = 0; i < listeners.length; i++) {
                listeners[i].endParse(this);
            }
        }
		private static final String ELEMENT_PRINTDATA = "PRINTDATA";

		private static final String ELEMENT_FORMAT = "FORMAT";

		private static final String ELEMENT_PRINT = "PRINT";

		private static final String ELEMENT_PAGE = "PAGE";

		private static final String ELEMENT_DATA = "DATA";

		private static final String ELEMENT_TEXT = "TEXT";

		private static final String ELEMENT_IMAGE = "IMAGE";

		private static final String ELEMENT_BR = "BR";

		private String formatId;

		private StringBuffer valueBuff;

		private StringBuffer textBuff;

		private StringBuffer imageBuff;

		private DataXml dataXml;

		private DataPageObject dataPageObj;

		private DataValueObject dataValueObj;

		public DataXmlReader() {
			super();
			dataXml = new DataXml();
		}

		public DataXml getDataXml() {
			return dataXml;
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			if (valueBuff != null) {
				String value = new String(ch, start, length);
				if (value.trim().length() > 0) {
					valueBuff.append(value);
				}
			}
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			// 要素名を大文字に統一
			String qNameValue = null;
			if (qName != null) {
				qNameValue = qName.toUpperCase();
			}
			if (ELEMENT_PRINTDATA.equals(qNameValue)) {
                fireStartParse();
			} else if (ELEMENT_FORMAT.equals(qNameValue)) {
				try {
					// フォーマット解析
					FormatXmlReader formatReader = new FormatXmlReader();
					SAXParserFactory factory = SAXParserFactory.newInstance();
					SAXParser parser = factory.newSAXParser();
					parser.parse(attributes.getValue("Src"), formatReader);
					// フォーマットマップに退避
					formatId = attributes.getValue("Id");
					dataXml.addFormatListValue(formatId, formatReader.getFormatXml());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (ELEMENT_PAGE.equals(qNameValue)) {
				dataPageObj = new DataPageObject();
				dataPageObj.setFormatId(attributes.getValue("FormatId"));
                fireStartPage();
			} else if (ELEMENT_IMAGE.equals(qNameValue)) {
				if (textBuff != null) {
					textBuff.append(valueBuff.toString());
				}
				imageBuff = new StringBuffer();
				valueBuff = new StringBuffer();
			} else if (ELEMENT_PRINT.equals(qNameValue)) {
			} else if (ELEMENT_DATA.equals(qNameValue)) {
			} else if (ELEMENT_BR.equals(qNameValue)) {
			} else {
				dataValueObj = new DataValueObject();
				String val;
				// 表示可否
				val = attributes.getValue("Visible");
				if (val != null) {
					dataValueObj.setVisible(new Boolean(val).booleanValue());
				}
				// 背景形式
				val = attributes.getValue("BackStyle");
				if (val != null) {
					dataValueObj.setBackStyle(val);
				}
				// 背景色
				val = attributes.getValue("BackColor");
				if (val != null) {
					dataValueObj.setBackColor(stringToColor(val));
				}
				// テキスト領域初期化
				textBuff = new StringBuffer();
				// 文字列領域初期化
				valueBuff = new StringBuffer();
			}
		}

		private Color stringToColor(String s) {
			if (s == null) {
				return null;
			}
			try {
				return Color.decode(s);
			} catch (Exception e) {
				return null;
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			// 要素名を大文字に統一
			String qNameValue = null;
			if (qName != null) {
				qNameValue = qName.toUpperCase();
			}
			if (ELEMENT_PRINTDATA.equals(qNameValue)) {
			} else if (ELEMENT_FORMAT.equals(qNameValue)) {
			} else if (ELEMENT_PRINT.equals(qNameValue)) {
                fireEndParse();
			} else if (ELEMENT_PAGE.equals(qNameValue)) {
				dataXml.addDataListValue(dataPageObj);
                fireEndPage();
                //1ページ分のデータを破棄
                dataXml.getDataList().clear(); 
			} else if (ELEMENT_DATA.equals(qNameValue)) {
			} else if (ELEMENT_BR.equals(qNameValue)) {
				valueBuff.append(System.getProperty("line.separator"));
			} else if (ELEMENT_IMAGE.equals(qNameValue)) {
				imageBuff.append(valueBuff.toString());
				valueBuff = new StringBuffer();
			} else {
				if (textBuff != null) {
					textBuff.append(valueBuff.toString());
					dataValueObj.setValue(textBuff.toString());
				}
				// イメージ格納
				if (imageBuff != null) {
					dataValueObj.setImage(imageBuff.toString());
				}

				FormatXml formatXml = dataXml.getFormatListValue(dataPageObj.getFormatId());
				FormatObject formatValue = formatXml.findItem(qName);
				if (formatValue != null) {
					dataPageObj.addDataValue(formatValue, dataValueObj);
				}
			}
		}

	}

	private class DataXml {

		private ArrayList dataList;

		private LinkedHashMap formatList;

		public DataXml() {
			dataList = new ArrayList();
			formatList = new LinkedHashMap();
		}

		public ArrayList getDataList() {
			return dataList;
		}

		public LinkedHashMap getFormatList() {
			return formatList;
		}

		public void addDataListValue(DataPageObject page) {
			dataList.add(page);
		}

		public void addFormatListValue(String key, FormatXml formatXml) {
			formatList.put(key, formatXml);
		}

		public FormatXml getFormatListValue(String key) {
			return (FormatXml) formatList.get(key);
		}

	}

	private class DataPageObject {

		private String formatId;

		private LinkedHashMap dataValues;

		public DataPageObject() {
			dataValues = new LinkedHashMap();
		}

		public String getFormatId() {
			return formatId;
		}

		public void setFormatId(String formatId) {
			this.formatId = formatId;
		}

		public void addDataValue(Object key, DataValueObject dataValueObject) {
			dataValues.put(key, dataValueObject);
		}

		public void removeDataValue(String key) {
			dataValues.remove(key);
		}

		public LinkedHashMap getDataValues() {
			return dataValues;
		}

	}

	private interface DrawFillParameter {
		public Color getBackColor();

		public String getBackStyle();
	}

	private class DataValueObject implements DrawFillParameter {

		private String value;

		private String image;

		private boolean visible = true;

		private String backStyle;

		private Color backColor;

		private FormatObject parent;

		public FormatObject getParent() {
			return parent;
		}

		public void setParent(FormatObject parent) {
			this.parent = parent;
		}

		public String getValue() {
			return value;
		}

		public String getImage() {
			return image;
		}

		public boolean isVisible() {
			return visible;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public String getBackStyle() {
			if (backStyle != null) {
				return backStyle;
			} else {
				if (getParent() != null) {
					return getParent().getBackStyle();
				} else {
					return null;
				}
			}
		}

		public Color getBackColor() {
			if (backColor != null) {
				return backColor;
			} else {
				if (getParent() != null) {
					return getParent().getBackColor();
				} else {
					return null;
				}
			}
		}

		public void setBackStyle(String backStyle) {
			this.backStyle = backStyle;
		}

		public void setBackColor(Color backColor) {
			this.backColor = backColor;
		}

	}

	private class FormatXmlReader extends DefaultHandler {
		private static final String ELEMENT_PAPER = "PAPER";

		private static final String ELEMENT_TABLE = "TABLE";

		private static final String ELEMENT_SEPARATE = "SEPARATE";

		private static final String ELEMENT_LABEL = "LABEL";

		private static final String ELEMENT_BR = "BR";

		private static final String ELEMENT_IMAGE = "IMAGE";

		private static final String ELEMENT_TEXT = "TEXT";

		private static final String ELEMENT_FONT = "FONT";

		private static final String ELEMENT_VERTICALLINE = "VERTICALLINE";

		private static final String ELEMENT_HORIZONTALLINE = "HORIZONTALLINE";

		private static final String ELEMENT_TR = "TR";

		private static final String ELEMENT_TD = "TD";

		private static final String ELEMENT_TOPLLINE = "TOPLINE";

		private static final String ELEMENT_BOTTOMLINE = "BOTTOMLINE";

		private static final String ELEMENT_LEFTLINE = "LEFTLINE";

		private static final String ELEMENT_RIGHTLINE = "RIGHTLINE";

		private static final String ELEMENT_RISELINE = "RISELINE";

		private static final String ELEMENT_DOWNLINE = "DOWNLINE";

		private static final String ELEMENT_COL = "COL";

		private int columnCount;

		private StringBuffer buffer;

		private FormatXml formatXml;

		private FormatObject parsingObj;

		private FormatObject formatPaperObj;

		private FormatLabelObject formatLabelObject;

		private FormatSeparateObject formatSeparateObj;

		private FormatObject formatColumnObj;

		private FormatTableObject formatRowObj;

		private FormatTableObject formatTableObj;

		private FormatCellObject formatCellObj;

		private FormatBorderLineObject topLine;

		private FormatBorderLineObject bottomLine;

		private FormatBorderLineObject leftLine;

		private FormatBorderLineObject rightLine;

		private FormatBorderLineObject riseLine;

		private FormatBorderLineObject downLine;

		private FormatBorderLineObject verticalLine;

		private FormatBorderLineObject horizontalLine;

		public FormatXmlReader() {
			super();
			formatXml = new FormatXml();
		}

		public FormatXml getFormatXml() {
			return formatXml;
		}

		public void characters(char[] ch, int start, int length) throws SAXException {
			if (buffer != null) {
				String value = new String(ch, start, length);
				if (value.trim().length() > 0) {
					buffer.append(value);
				}
			}
		}

		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			String qNameValue = null;
			if (qName != null) {
				qNameValue = qName.toUpperCase();
			}
			if (ELEMENT_PAPER.equals(qNameValue)) {
				formatPaperObj = new FormatObject(null);
				formatPaperObj.setFormat(attributes.getValue("Format"));
				formatPaperObj.setRealScale(stringToInteger(attributes.getValue("RealScale")));
				formatPaperObj.setMediaSizeName(attributes.getValue("MediaSizeName"));
				formatPaperObj.setOrientation(attributes.getValue("Orientation"));
				formatPaperObj.setMargin(stringToInteger(attributes.getValue("Margin")));
				formatPaperObj.setWidth(stringToInteger(attributes.getValue("Width")));
				formatPaperObj.setHeight(stringToInteger(attributes.getValue("Height")));
			} else if (ELEMENT_SEPARATE.equals(qNameValue)) {
				formatSeparateObj = new FormatSeparateObject(formatPaperObj);
				formatSeparateObj.setCols(stringToInteger(attributes.getValue("Cols")));
				formatSeparateObj.setRows(stringToInteger(attributes.getValue("Rows")));
				formatSeparateObj.setSlitWidth(stringToFloat(attributes.getValue("SlitWidth")));
				formatSeparateObj.setSlitHeight(stringToFloat(attributes.getValue("SlitHeight")));
				setAttributes(formatSeparateObj, attributes);
				formatXml.addFormatValue(formatSeparateObj.getId(), formatSeparateObj);
				parsingObj = formatSeparateObj;
			} else if (ELEMENT_LABEL.equals(qNameValue)) {
				formatLabelObject = new FormatLabelObject(formatPaperObj);
				setAttributes(formatLabelObject, attributes);
				formatXml.addFormatValue(formatLabelObject.getId(), formatLabelObject);
				parsingObj = formatLabelObject;
			} else if (ELEMENT_TABLE.equals(qNameValue)) {
				formatTableObj = new FormatTableObject(formatPaperObj);
				setAttributes(formatTableObj, attributes);
				formatXml.addFormatValue(formatTableObj.getId(), formatTableObj);
				parsingObj = formatTableObj;
			} else if (ELEMENT_COL.equals(qNameValue)) {
				formatColumnObj = new FormatObject(formatTableObj);
				formatTableObj.addColumn(formatColumnObj);
				setAttributes(formatColumnObj, attributes);
			} else if (ELEMENT_TR.equals(qNameValue)) {
				columnCount = 0;
				formatRowObj = new FormatTableObject(formatTableObj);
				formatTableObj.addRow(formatRowObj);
				setAttributes(formatRowObj, attributes);
			} else if (ELEMENT_TD.equals(qNameValue)) {
				formatCellObj = new FormatCellObject(formatRowObj);
				formatCellObj.setColSpan(stringToInteger(attributes.getValue("ColSpan")));
				formatCellObj.setRowSpan(stringToInteger(attributes.getValue("RowSpan")));
				formatCellObj.setColumnIndex(columnCount);
				formatCellObj.setRowIndex(formatTableObj.getRowCount() - 1);
				formatTableObj.addCell(formatTableObj.getId() + "." + formatRowObj.getId() + "." + formatTableObj.getColumn(columnCount).getId(), formatCellObj);
				setAttributes(formatCellObj, attributes);
				parsingObj = formatCellObj;
				columnCount++;
			} else if (ELEMENT_IMAGE.equals(qNameValue)) {
				buffer = new StringBuffer();
			} else if (ELEMENT_TEXT.equals(qNameValue)) {
				buffer = new StringBuffer();
			} else if (ELEMENT_FONT.equals(qNameValue)) {
				String fontName = attributes.getValue("Name");
				int fontStyle = stringToInteger(attributes.getValue("Style"));
				int fontSize = stringToInteger(attributes.getValue("Size"));
				if (fontName != null) {
					parsingObj.setFontName(fontName);
				}
				if (fontStyle != -1) {
					parsingObj.setFontStyle(fontStyle);
				}
				if (fontSize != -1) {
					parsingObj.setFontSize(fontSize);
				}
			} else if (ELEMENT_TOPLLINE.equals(qNameValue)) {
				topLine = new FormatBorderLineObject(parsingObj);
				topLine.setBorderType(attributes.getValue("BorderType"));
				topLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				topLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_BOTTOMLINE.equals(qNameValue)) {
				bottomLine = new FormatBorderLineObject(parsingObj);
				bottomLine.setBorderType(attributes.getValue("BorderType"));
				bottomLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				bottomLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_LEFTLINE.equals(qNameValue)) {
				leftLine = new FormatBorderLineObject(parsingObj);
				leftLine.setBorderType(attributes.getValue("BorderType"));
				leftLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				leftLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_RIGHTLINE.equals(qNameValue)) {
				rightLine = new FormatBorderLineObject(parsingObj);
				rightLine.setBorderType(attributes.getValue("BorderType"));
				rightLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				rightLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_RISELINE.equals(qNameValue)) {
				riseLine = new FormatBorderLineObject(parsingObj);
				riseLine.setBorderType(attributes.getValue("BorderType"));
				riseLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				riseLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_DOWNLINE.equals(qNameValue)) {
				downLine = new FormatBorderLineObject(parsingObj);
				downLine.setBorderType(attributes.getValue("BorderType"));
				downLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				downLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_VERTICALLINE.equals(qNameValue)) {
				verticalLine = new FormatBorderLineObject(parsingObj);
				verticalLine.setBorderType(attributes.getValue("BorderType"));
				verticalLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				verticalLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			} else if (ELEMENT_HORIZONTALLINE.equals(qNameValue)) {
				horizontalLine = new FormatBorderLineObject(parsingObj);
				horizontalLine.setBorderType(attributes.getValue("BorderType"));
				horizontalLine.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
				horizontalLine.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			}
		}

		public void endElement(String uri, String localName, String qName) throws SAXException {
			String qNameValue = null;
			if (qName != null) {
				qNameValue = qName.toUpperCase();
			}
			if (ELEMENT_PAPER.equals(qNameValue)) {
				formatXml.setFormatPaperObject(formatPaperObj);
			} else if (ELEMENT_TABLE.equals(qNameValue)) {
				calcCellObjectSetting(formatTableObj);
			} else if (ELEMENT_BR.equals(qNameValue)) {
				buffer.append(System.getProperty("line.separator"));
			} else if (ELEMENT_IMAGE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setImage(buffer.toString());
				}
			} else if (ELEMENT_TEXT.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setText(buffer.toString());
				}
			} else if (ELEMENT_TOPLLINE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setTopLine(topLine);
				}
			} else if (ELEMENT_BOTTOMLINE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setBottomLine(bottomLine);
				}
			} else if (ELEMENT_LEFTLINE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setLeftLine(leftLine);
				}
			} else if (ELEMENT_RIGHTLINE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setRightLine(rightLine);
				}
			} else if (ELEMENT_RISELINE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setRiseLine(riseLine);
				}
			} else if (ELEMENT_DOWNLINE.equals(qNameValue)) {
				if (parsingObj != null && parsingObj instanceof FormatObject) {
					parsingObj.setDownLine(downLine);
				}
			} else if (ELEMENT_VERTICALLINE.equals(qNameValue)) {
				if (formatTableObj != null && formatTableObj instanceof FormatTableObject) {
					formatTableObj.setVerticalLine(verticalLine);
				}
			} else if (ELEMENT_HORIZONTALLINE.equals(qNameValue)) {
				if (formatTableObj != null && formatTableObj instanceof FormatTableObject) {
					formatTableObj.setHorizontalLine(horizontalLine);
				}
			}
		}

		private void setAttributes(FormatObject formatObj, Attributes attributes) {
			formatObj.setId(attributes.getValue("Id"));
			formatObj.setShape(attributes.getValue("Shape"));
			formatObj.setVertical(stringToInteger(attributes.getValue("Vertical")));
			formatObj.setAlignment(attributes.getValue("Alignment"));
			formatObj.setFormat(attributes.getValue("Format"));
			formatObj.setBackColor(stringToColor(attributes.getValue("BackColor")));
			formatObj.setBackStyle(attributes.getValue("BackStyle"));
			formatObj.setForeColor(stringToColor(attributes.getValue("ForeColor")));
			formatObj.setHeight(stringToInteger(attributes.getValue("Height")));
			formatObj.setWidth(stringToInteger(attributes.getValue("Width")));
			formatObj.setLineSpace(stringToInteger(attributes.getValue("LineSpace")));
			formatObj.setX(stringToInteger(attributes.getValue("X")));
			formatObj.setY(stringToInteger(attributes.getValue("Y")));
			formatObj.setBorderWidth(stringToFloat(attributes.getValue("BorderWidth")));
			formatObj.setBorderType(attributes.getValue("BorderType"));
			formatObj.setBorderColor(stringToColor(attributes.getValue("BorderColor")));
			formatObj.setEqually(stringToBoolean(attributes.getValue("Equally")));
			formatObj.setLt(stringToFloat(attributes.getValue("LT")));
			formatObj.setLb(stringToFloat(attributes.getValue("LB")));
			formatObj.setRt(stringToFloat(attributes.getValue("RT")));
			formatObj.setRb(stringToFloat(attributes.getValue("RB")));
		}

		private void calcCellObjectSetting(FormatTableObject table) {
			int[] width = new int[table.getColumnCount()];
			int[] height = new int[table.getRowCount()];
			// 列の幅退避
			for (int i = 0; i < table.getColumnCount(); i++) {
				width[i] = (table.getColumn(i)).getWidth();
			}
			// 行の高さを退避
			for (int i = 0; i < table.getRowCount(); i++) {
				height[i] = (table.getRow(i)).getHeight();
			}
			int columnWidth = 0;
			for (int x = 0; x < table.getColumnCount(); x++) {
				int rowHeight = 0;
				FormatObject column = table.getColumn(x);
				for (int y = 0; y < table.getRowCount(); y++) {
					FormatObject row = table.getRow(y);
					FormatCellObject cell = (FormatCellObject) formatXml.findItem(table.getId() + "." + row.getId() + "." + column.getId());
					cell.setX(table.getX() + columnWidth);
					cell.setY(table.getY() + rowHeight);
					cell.setWidth(width[x]);
					cell.setHeight(height[y]);

					// テキスト用Widthの設定
					if (cell.getColSpan() != -1) {
						int textWidth = 0;
						for (int i = 0; i < cell.getColSpan(); i++) {
							textWidth += width[i + x];
						}
						cell.setTextWidth(textWidth);
					} else {
						cell.setTextWidth(width[x]);
					}

					// テキスト用Heightの設定
					if (cell.getRowSpan() != -1) {
						int textHeight = 0;
						for (int i = 0; i < cell.getRowSpan(); i++) {
							textHeight += height[i + y];
						}
						cell.setTextHeight(textHeight);
					} else {
						cell.setTextHeight(height[y]);
					}

					rowHeight = rowHeight + row.getHeight();
				}
				columnWidth = columnWidth + column.getWidth();
			}

		}

		private int stringToInteger(String s) {
			if (s == null) {
				return -1;
			}
			try {
				return Integer.parseInt(s);
			} catch (Exception e) {
				return -1;
			}
		}

		private float stringToFloat(String s) {
			if (s == null) {
				return -1;
			}
			try {
				return Float.parseFloat(s);
			} catch (Exception e) {
				return -1;
			}
		}

		private Color stringToColor(String s) {
			if (s == null) {
				return null;
			}
			try {
				return Color.decode(s);
			} catch (Exception e) {
				return null;
			}
		}

		private boolean stringToBoolean(String s) {
			if (s == null) {
				return false;
			}
			try {
				if ("1".equals(s)) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				return false;
			}
		}

	}

	private interface FormatBackStyleConstants {

		public static final String BACKSTYLE_CLEAR = "CLEAR";

		public static final String BACKSTYLE_SOLID = "SOLID";

		public static final String BACKSTYLE_HORIZONTAL = "HORIZONTAL";

		public static final String BACKSTYLE_VERTICAL = "VERTICAL";

		public static final String BACKSTYLE_FDIAGONAL = "FDIAGONAL";

		public static final String BACKSTYLE_BDIAGONAL = "BDIAGONAL";

		public static final String BACKSTYLE_CROSS = "CROSS";

		public static final String BACKSTYLE_DIAGCROSS = "DIAGCROSS";

	}

	private interface FormatBodarLineConstants {

		public static final String BORDER_TYPE_CLEAR = "CLEAR";

		public static final String BORDER_TYPE_SOLID = "SOLID";

		public static final String BORDER_TYPE_DOT = "DOT";

		public static final String BORDER_TYPE_DASH = "DASH";

		public static final String BORDER_TYPE_DASHDOT = "DASHDOT";

		public static final String BORDER_TYPE_DASHDOTDOT = "DASHDOTDOT";

		public static final String BORDER_TYPE_DOUBLE = "DOUBLE";

		public float getBorderWidth();

		public String getBorderType();

		public Color getBorderColor();

		public void setBorderWidth(float borderWidth);

		public void setBorderType(String borderType);

		public void setBorderColor(Color borderColor);

	}

	private interface FormatFontConstants {

		public int getFontSize();

		public int getFontStyle();

		public String getFontName();

		public void setFontSize(int fontSize);

		public void setFontStyle(int fontStyle);

		public void setFontName(String fontName);

	}

	private interface FormatAlignmentConstants {
		public static final String ALIGNMENT_LEFT_TOP = "LEFTTOP";

		public static final String ALIGNMENT_LEFT_MIDDLE = "LEFTMIDDLE";

		public static final String ALIGNMENT_LEFT_BOTTOM = "LEFTBOTTOM";

		public static final String ALIGNMENT_CENTER_TOP = "CENTERTOP";

		public static final String ALIGNMENT_CENTER_MIDDLE = "CENTERMIDDLE";

		public static final String ALIGNMENT_CENTER_BOTTOM = "CENTERBOTTOM";

		public static final String ALIGNMENT_RIGHT_TOP = "RIGHTTOP";

		public static final String ALIGNMENT_RIGHT_MIDDLE = "RIGHTMIDDLE";

		public static final String ALIGNMENT_RIGHT_BOTTOM = "RIGHTBOTTOM";
	}

	private interface FormatFormatConstants {
		public static final String FORMAT_NONE = "NONE";

		public static final String FORMAT_AUTOSIZE = "AUTOSIZE";

		public static final String FORMAT_AUTOFORMAT = "AUTOFORMAT";

		public static final String FORMAT_WORDBREAK = "WORDBREAK";
	}

	private interface FormatShapeConstants {
		public static final String SHAPE_NONE = "NONE";

		public static final String SHAPE_RECTANGLE = "RECTANGLE";

		public static final String SHAPE_ELLIPSE = "ELLIPSE";

		public static final String SHAPE_ROUND = "ROUND";
	}

	private class FormatXml {
		private LinkedHashMap formatValues;

		private FormatObject formatPaperObject;

		public FormatXml() {
			formatValues = new LinkedHashMap();
			formatPaperObject = new FormatObject(null);
		}

		public FormatObject findItem(String key) {
			if (key == null) {
				return null;
			}
			// 通常の検索
			if (formatValues.get(key) != null) {
				return (FormatObject) formatValues.get(key);
			}
			// ID及びCELLの検索
			Iterator it = getFormatValues();
			while (it.hasNext()) {
				FormatObject item = (FormatObject) it.next();
				if (item instanceof FormatTableObject) {
					FormatTableObject tableItem = (FormatTableObject) item;
					if (tableItem.getCell(key) != null) {
						return tableItem.getCell(key);
					} else {
						Iterator ic = tableItem.getCells();
						while (ic.hasNext()) {
							FormatCellObject cell = (FormatCellObject) ic.next();
							if (key.equals(cell.getId())) {
								return cell;
							}
						}
					}
				} else {
					if (key.equals(item.getId())) {
						return item;
					}
				}
			}
			return null;
		}

		public void addFormatValue(String key, FormatObject formatObject) {
			formatValues.put(key, formatObject);
		}

		public Iterator getFormatValues() {
			return formatValues.values().iterator();
		}

		public FormatObject getFormatPaperObject() {
			return formatPaperObject;
		}

		public void setFormatPaperObject(FormatObject formatPaperObject) {
			this.formatPaperObject = formatPaperObject;
		}

	}

	private class FormatObject implements FormatBackStyleConstants, FormatBodarLineConstants, FormatFontConstants, FormatAlignmentConstants, FormatFormatConstants, FormatShapeConstants, DrawFillParameter {

		private int x = -1;

		private int y = -1;

		private int width = -1;

		private int height = -1;

		private int vertical = -1;

		private int lineSpace = -1;

		private int realScale = -1;

		private int margin = -1;

		private int fontSize = -1;

		private int fontStyle = -1;

		private float lt = -1;

		private float rt = -1;

		private float lb = -1;

		private float rb = -1;

		private float borderWidth = -1;

		private boolean equally;

		private String id;

		private String format;

		private String text;

		private String image;

		private String shape;

		private String alignment;

		private String orientation;

		private String mediaSizeName;

		private String borderType;

		private String backStyle;

		private String fontName;

		private Color borderColor;

		private Color backColor;

		private Color foreColor;

		private FormatBorderLineObject topLine;

		private FormatBorderLineObject bottomLine;

		private FormatBorderLineObject leftLine;

		private FormatBorderLineObject rightLine;

		private FormatBorderLineObject riseLine;

		private FormatBorderLineObject downLine;

		private FormatObject parent;

		public FormatObject(FormatObject parent) {
			this.parent = parent;
		}

		public FormatObject getParent() {
			return parent;
		}

		public String getId() {
			if (id != null) {
				return id;
			} else {
				return null;
			}
		}

		public int getX() {
			if (x != -1) {
				return x;
			} else {
				if (getParent() != null) {
					return getParent().getX();
				} else {
					return -1;
				}
			}
		}

		public int getY() {
			if (y != -1) {
				return y;
			} else {
				if (getParent() != null) {
					return getParent().getY();
				} else {
					return -1;
				}
			}
		}

		public int getBottom() {
			return getY() + getHeight();
		}

		public int getRight() {
			return getX() + getWidth();
		}

		public int getWidth() {
			if (width != -1) {
				return width;
			} else {
				if (getParent() != null) {
					return getParent().getWidth();
				} else {
					return -1;
				}
			}
		}

		public int getHeight() {
			if (height != -1) {
				return height;
			} else {
				if (getParent() != null) {
					return getParent().getHeight();
				} else {
					return -1;
				}
			}
		}

		public int getRealScale() {
			if (realScale != -1) {
				return realScale;
			} else {
				if (getParent() != null) {
					return getParent().getRealScale();
				} else {
					return -1;
				}
			}
		}

		public int getMargin() {
			if (margin != -1) {
				return margin;
			} else {
				if (getParent() != null) {
					return getParent().getMargin();
				} else {
					return -1;
				}
			}
		}

		public int getLineSpace() {
			if (lineSpace != -1) {
				return lineSpace;
			} else {
				if (getParent() != null) {
					return getParent().getLineSpace();
				} else {
					return -1;
				}
			}
		}

		public int getVertical() {
			if (vertical != -1) {
				return vertical;
			} else {
				if (getParent() != null) {
					return getParent().getVertical();
				} else {
					return -1;
				}
			}
		}

		public float getLt() {
			return lt;
		}

		public float getLb() {
			return lb;
		}

		public float getRt() {
			return rt;
		}

		public float getRb() {
			return rb;
		}

		public String getText() {
			if (text != null) {
				return text;
			} else {
				if (getParent() != null) {
					return getParent().getText();
				} else {
					return null;
				}
			}
		}

		public String getImage() {
			if (image != null) {
				return image;
			} else {
				if (getParent() != null) {
					return getParent().getImage();
				} else {
					return null;
				}
			}
		}

		public String getFormat() {
			if (format != null) {
				return format;
			} else {
				if (getParent() != null) {
					return getParent().getFormat();
				} else {
					return null;
				}
			}
		}

		public String getMediaSizeName() {
			if (mediaSizeName != null) {
				return mediaSizeName;
			} else {
				if (getParent() != null) {
					return getParent().getMediaSizeName();
				} else {
					return null;
				}
			}
		}

		public String getOrientation() {
			if (orientation != null) {
				return orientation;
			} else {
				if (getParent() != null) {
					return getParent().getOrientation();
				} else {
					return null;
				}
			}
		}

		public String getShape() {
			if (shape != null) {
				return shape;
			} else {
				if (getParent() != null) {
					return getParent().getShape();
				} else {
					return null;
				}
			}
		}

		public String getAlignment() {
			if (alignment != null) {
				return alignment;
			} else {
				if (getParent() != null) {
					return getParent().getAlignment();
				} else {
					return null;
				}
			}
		}

		public String getBackStyle() {
			if (backStyle != null) {
				return backStyle;
			} else {
				if (getParent() != null) {
					return getParent().getBackStyle();
				} else {
					return null;
				}
			}
		}

		public Color getBackColor() {
			if (backColor != null) {
				return backColor;
			} else {
				if (getParent() != null) {
					return getParent().getBackColor();
				} else {
					return null;
				}
			}
		}

		public Color getForeColor() {
			if (foreColor != null) {
				return foreColor;
			} else {
				if (getParent() != null) {
					return getParent().getForeColor();
				} else {
					return null;
				}
			}
		}

		public float getBorderWidth() {
			if (borderWidth != -1) {
				return borderWidth;
			} else {
				if (getParent() != null) {
					return getParent().getBorderWidth();
				} else {
					return -1;
				}
			}
		}

		public String getBorderType() {
			if (borderType != null) {
				return borderType;
			} else {
				if (getParent() != null) {
					return getParent().getBorderType();
				} else {
					return null;
				}
			}
		}

		public Color getBorderColor() {
			if (borderColor != null) {
				return borderColor;
			} else {
				if (getParent() != null) {
					return getParent().getBorderColor();
				} else {
					return Color.black;
				}
			}
		}

		public int getFontSize() {
			if (fontSize != -1) {
				return fontSize;
			} else {
				if (getParent() != null) {
					return getParent().getFontSize();
				} else {
					return -1;
				}
			}
		}

		public int getFontStyle() {
			if (fontStyle != -1) {
				return fontStyle;
			} else {
				if (getParent() != null) {
					return getParent().getFontStyle();
				} else {
					return -1;
				}
			}
		}

		public String getFontName() {
			if (fontName != null) {
				return fontName;
			} else {
				if (getParent() != null) {
					return getParent().getFontName();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getTopLine() {
			if (topLine != null) {
				return topLine;
			} else {
				if (getParent() != null) {
					return getParent().getTopLine();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getBottomLine() {
			if (bottomLine != null) {
				return bottomLine;
			} else {
				if (getParent() != null) {
					return getParent().getBottomLine();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getDownLine() {
			if (downLine != null) {
				return downLine;
			} else {
				if (getParent() != null) {
					return getParent().getDownLine();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getLeftLine() {
			if (leftLine != null) {
				return leftLine;
			} else {
				if (getParent() != null) {
					return getParent().getLeftLine();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getRightLine() {
			if (rightLine != null) {
				return rightLine;
			} else {
				if (getParent() != null) {
					return getParent().getRightLine();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getRiseLine() {
			if (riseLine != null) {
				return riseLine;
			} else {
				if (getParent() != null) {
					return getParent().getRiseLine();
				} else {
					return null;
				}
			}
		}

		public boolean isEqually() {
			return equally;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void setRealScale(int realScale) {
			this.realScale = realScale;
		}

		public void setMargin(int margin) {
			this.margin = margin;
		}

		public void setLineSpace(int lineSpace) {
			this.lineSpace = lineSpace;
		}

		public void setVertical(int vertical) {
			this.vertical = vertical;
		}

		public void setLt(float lt) {
			this.lt = lt;
		}

		public void setLb(float lb) {
			this.lb = lb;
		}

		public void setRt(float rt) {
			this.rt = rt;
		}

		public void setRb(float rb) {
			this.rb = rb;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		public void setText(String text) {
			this.text = text;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public void setMediaSizeName(String mediaSizeName) {
			this.mediaSizeName = mediaSizeName;
		}

		public void setOrientation(String orientation) {
			this.orientation = orientation;
		}

		public void setShape(String shape) {
			this.shape = shape;
		}

		public void setAlignment(String alignment) {
			this.alignment = alignment;
		}

		public void setBackStyle(String backStyle) {
			this.backStyle = backStyle;
		}

		public void setBackColor(Color backColor) {
			this.backColor = backColor;
		}

		public void setForeColor(Color foreColor) {
			this.foreColor = foreColor;
		}

		public void setBorderWidth(float borderWidth) {
			this.borderWidth = borderWidth;
		}

		public void setBorderType(String borderType) {
			this.borderType = borderType;
		}

		public void setBorderColor(Color borderColor) {
			this.borderColor = borderColor;
		}

		public void setFontSize(int fontSize) {
			this.fontSize = fontSize;
		}

		public void setFontStyle(int fontStyle) {
			this.fontStyle = fontStyle;
		}

		public void setFontName(String fontName) {
			this.fontName = fontName;
		}

		public void setTopLine(FormatBorderLineObject topLine) {
			this.topLine = topLine;
		}

		public void setBottomLine(FormatBorderLineObject bottomLine) {
			this.bottomLine = bottomLine;
		}

		public void setDownLine(FormatBorderLineObject downLine) {
			this.downLine = downLine;
		}

		public void setLeftLine(FormatBorderLineObject leftLine) {
			this.leftLine = leftLine;
		}

		public void setRightLine(FormatBorderLineObject rightLine) {
			this.rightLine = rightLine;
		}

		public void setRiseLine(FormatBorderLineObject riseLine) {
			this.riseLine = riseLine;
		}

		public void setEqually(boolean equally) {
			this.equally = equally;
		}

	}

	private class FormatLabelObject extends FormatObject {

		public FormatLabelObject(FormatObject parent) {
			super(parent);
		}

	}

	private class FormatSeparateObject extends FormatObject {

		public FormatSeparateObject(FormatObject parent) {
			super(parent);
		}

		private int cols;

		private int rows;

		private float slitWidth;

		private float slitHeight;

		public int getCols() {
			return cols;
		}

		public int getRows() {
			return rows;
		}

		public float getSlitWidth() {
			return slitWidth;
		}

		public float getSlitHeight() {
			return slitHeight;
		}

		public void setCols(int cols) {
			this.cols = cols;
		}

		public void setRows(int rows) {
			this.rows = rows;
		}

		public void setSlitWidth(float slitWidth) {
			this.slitWidth = slitWidth;
		}

		public void setSlitHeight(float slitHeight) {
			this.slitHeight = slitHeight;
		}

	}

	private class FormatTableObject extends FormatObject {

		private ArrayList rows;

		private ArrayList columns;

		private LinkedHashMap cells;

		private FormatBorderLineObject verticalLine;

		private FormatBorderLineObject horizontalLine;

		public FormatTableObject(FormatObject parent) {
			super(parent);
			rows = new ArrayList();
			columns = new ArrayList();
			cells = new LinkedHashMap();
		}

		public void addRow(FormatObject row) {
			rows.add(row);
		}

		public void addColumn(FormatObject column) {
			columns.add(column);
		}

		public void addCell(String key, FormatCellObject formatCellObject) {
			cells.put(key, formatCellObject);
		}

		public FormatCellObject getCell(String key) {
			return (FormatCellObject) cells.get(key);
		}

		public Iterator getCells() {
			return cells.values().iterator();
		}

		public FormatObject getRow(int index) {
			return (FormatObject) rows.get(index);
		}

		public int getRowIndex(String id) {
			int index = -1;
			for (int i = 0; i < getRowCount(); i++) {
				if (id.equals(getRow(i).getId())) {
					index = i;
					break;
				}
			}
			return index;
		}

		public FormatObject getColumn(int index) {
			return (FormatObject) columns.get(index);
		}

		public int getColumnIndex(String id) {
			int index = -1;
			for (int i = 0; i < getColumnCount(); i++) {
				if (id.equals(getColumn(i).getId())) {
					index = i;
					break;
				}
			}
			return index;
		}

		public int getRowCount() {
			if (rows != null && rows.size() != 0) {
				return rows.size();
			} else {
				if (getParent() != null && getParent() instanceof FormatTableObject) {
					return ((FormatTableObject) getParent()).getRowCount();
				} else {
					return -1;
				}
			}
		}

		public int getColumnCount() {
			if (columns != null && columns.size() != 0) {
				return columns.size();
			} else {
				if (getParent() != null && getParent() instanceof FormatTableObject) {
					return ((FormatTableObject) getParent()).getColumnCount();
				} else {
					return -1;
				}
			}
		}

		public FormatBorderLineObject getVerticalLine() {
			if (verticalLine != null) {
				return verticalLine;
			} else {
				if (getParent() != null && getParent() instanceof FormatTableObject) {
					return ((FormatTableObject) getParent()).getVerticalLine();
				} else {
					return null;
				}
			}
		}

		public FormatBorderLineObject getHorizontalLine() {
			if (horizontalLine != null) {
				return horizontalLine;
			} else {
				if (getParent() != null && getParent() instanceof FormatTableObject) {
					return ((FormatTableObject) getParent()).getHorizontalLine();
				} else {
					return null;
				}
			}
		}

		public void setVerticalLine(FormatBorderLineObject verticalLine) {
			this.verticalLine = verticalLine;
		}

		public void setHorizontalLine(FormatBorderLineObject horizontalLine) {
			this.horizontalLine = horizontalLine;
		}

	}

	private class FormatCellObject extends FormatTableObject {

		public FormatCellObject(FormatObject parent) {
			super(parent);
		}

		private int rowIndex;

		private int columnIndex;

		private int rowSpan;

		private int colSpan;

		private int textWidth;

		private int textHeight;

		private int getTextWidth() {
			return textWidth;
		}

		private int getTextHeight() {
			return textHeight;
		}

		private int getRowSpan() {
			return rowSpan;
		}

		private int getColSpan() {
			return colSpan;
		}

		private int getRowIndex() {
			return rowIndex;
		}

		private int getColumnIndex() {
			return columnIndex;
		}

		private void setTextWidth(int textWidth) {
			this.textWidth = textWidth;
		}

		private void setTextHeight(int textHeight) {
			this.textHeight = textHeight;
		}

		private void setRowSpan(int rowSpan) {
			this.rowSpan = rowSpan;
		}

		private void setColSpan(int colSpan) {
			this.colSpan = colSpan;
		}

		private void setRowIndex(int rowIndex) {
			this.rowIndex = rowIndex;
		}

		private void setColumnIndex(int columnIndex) {
			this.columnIndex = columnIndex;
		}

	}

	private class FormatBorderLineObject implements FormatBodarLineConstants {

		private FormatBodarLineConstants parent;

		private float borderWidth;

		private String borderType;

		private Color borderColor;

		public FormatBorderLineObject(FormatBodarLineConstants parent) {
			this.parent = parent;
		}

		public String getBorderType() {
			if (borderType != null) {
				return borderType;
			} else {
				if (parent != null) {
					return parent.getBorderType();
				} else {
					return null;
				}
			}
		}

		public float getBorderWidth() {
			if (borderWidth != -1) {
				return borderWidth;
			} else {
				if (parent != null) {
					return parent.getBorderWidth();
				} else {
					return 0;
				}
			}
		}

		public Color getBorderColor() {
			if (borderColor != null) {
				return borderColor;
			} else {
				if (parent != null) {
					return parent.getBorderColor();
				} else {
					return Color.black;
				}
			}
		}

		public void setBorderType(String borderType) {
			this.borderType = borderType;
		}

		public void setBorderWidth(float borderWidth) {
			this.borderWidth = borderWidth;
		}

		public void setBorderColor(Color borderColor) {
			this.borderColor = borderColor;
		}

	}

    /**
     * 分割出力を行なうページ数単位 を返します。
     * <p>初期状態は30ページです。</p>
     * @return 分割出力を行なうページ数単位
     */
    public int getSeparatePrintPageSize() {
        return separatePrintPageSize;
    }

    /**
     * 分割出力を行なうページ数単位 を設定します。
     * <p>初期状態は30ページです。</p>
     * @param separatePrintPageSize 分割出力を行なうページ数単位
     */
    public void setSeparatePrintPageSize(int separatePrintPageSize) {
        this.separatePrintPageSize = separatePrintPageSize;
    }
    
    /**
     * 同一のページをあらわす比較結果定数です。
     */
    public static final int COMPARE_EQUAL = 0;
    /**
     * 異なるページをあらわす比較結果定数です。
     */
    public static final int COMPARE_NOT_EQUAL = 1;
    /**
     * 比較元1にしかないページをあらわす比較結果定数です。
     */
    public static final int COMPARE_PAGE_OVER1 = -1;
    /**
     * 比較元2にしかないページをあらわす比較結果定数です。
     */
    public static final int COMPARE_PAGE_OVER2 = -2;
    /**
     * PDFの比較結果がすべて同一であったかを返します。
     * @param result PDFの比較結果
     * @return すべて同一であったか
     * @see #comparePages(PdfReader, PdfReader)
     */
    public static boolean comparePagesResult(int[] result){
        int end = result.length;
        for(int i=0; i<end; i++){
            if(result[i]!=COMPARE_EQUAL){
                return false;
            }
        }
        return true;
    }
    /**
     * 二つのPDFを比較します。
     * <p>
     * ページ単位で比較し、結果を配列で返します。
     * </p>
     * @param pdf1 比較元1
     * @param pdf2 比較元2
     * @return 比較結果
     * @throws IOException 入出力例外
     * @see COMPARE_EQUAL
     * @see COMPARE_NOT_EQUAL
     * @see COMPARE_PAGE_OVER1
     * @see COMPARE_PAGE_OVER2
     */
    public static int[] comparePages(PdfReader pdf1, PdfReader pdf2) throws IOException{
        //ページ数を取得
        int pages1=pdf1.getNumberOfPages();
        int pages2=pdf2.getNumberOfPages();
        int pageMax = Math.max(pages1, pages2);
        int pageMin = Math.min(pages1, pages2);

        //比較結果を生成
        int[] result = new int[pageMax];

        //1ページ目から比較元両方に存在するページまでを比較
        for(int i=1; i<=pageMin; i++){
            byte[] b1 = pdf1.getPageContent(i);
            byte[] b2 = pdf2.getPageContent(i);
            
            int res = COMPARE_EQUAL;
            if(!Arrays.equals(b1,b2)){
                res = COMPARE_NOT_EQUAL;
            }
            result[i-1] = res;
        }
        
        if (pages1 != pages2) {
            //片方だけページ数が多い場合
            int fill = COMPARE_PAGE_OVER1;
            if (pages1 < pages2) {
                fill = COMPARE_PAGE_OVER2;
            }
            //pdf1が多ければCOMPARE_PAGE_OVER1、pdf2が多ければCOMPARE_PAGE_OVER2で埋める
            for (int i = pageMin; i < pageMax; i++) {
                result[i] = fill;
            }
        }
        return result;
    }
    /**
     * 二つのPDFを比較します。
     * <p>
     * ページ単位で比較し、結果を配列で返します。
     * </p>
     * @param pdf1 比較元1
     * @param pdf2 比較元2
     * @return 比較結果
     * @throws IOException 入出力例外
     * @see COMPARE_EQUAL
     * @see COMPARE_NOT_EQUAL
     * @see COMPARE_PAGE_OVER1
     * @see COMPARE_PAGE_OVER2
     */
    public static int[] comparePages(InputStream pdf1, InputStream pdf2) throws IOException{
        return comparePages(new PdfReader(pdf1), new PdfReader(pdf2));
    }
    /**
     * 二つのPDFを比較します。
     * <p>
     * ページ単位で比較し、結果を配列で返します。
     * </p>
     * @param pdf1 比較元1
     * @param pdf2 比較元2
     * @return 比較結果
     * @throws IOException 入出力例外
     * @see COMPARE_EQUAL
     * @see COMPARE_NOT_EQUAL
     * @see COMPARE_PAGE_OVER1
     * @see COMPARE_PAGE_OVER2
     */
    public static int[] comparePages(String pdf1, String pdf2) throws IOException{
        return comparePages(new PdfReader(pdf1), new PdfReader(pdf2));
    }
}
