package jp.or.med.orca.ikensho.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.text.Document;

import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.text.ACTextAreaDocument;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRTextArea;

/**
 * 行数制限に対応したテキストエリアです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTextArea
 */

public class IkenshoACRowMaximumableTextArea extends ACRowMaximumableTextArea {

    private boolean runComposition;
    protected InputSubset imeMode;

    protected int maxRows = 0;

    /**
     * Constructs a new TextArea.  A default model is set, the initial string
     * is null, and rows/columns are set to 0.
     */
    public IkenshoACRowMaximumableTextArea() {
        super();
    }

    /**
     * Constructs a new TextArea with the specified text displayed.
     * A default model is created and rows/columns are set to 0.
     *
     * @param text the text to be displayed, or null
     */
   public IkenshoACRowMaximumableTextArea(Document doc, String text, int rows,
            int columns) {
        super(doc, text, rows, columns);
    }

   /**
    * Constructs a new empty TextArea with the specified number of
    * rows and columns.  A default model is created, and the initial
    * string is null.
    *
    * @param rows the number of rows >= 0
    * @param columns the number of columns >= 0
    * @exception IllegalArgumentException if the rows or columns
    *  arguments are negative.
    */
    public IkenshoACRowMaximumableTextArea(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Constructs a new TextArea with the specified text displayed.
     * A default model is created and rows/columns are set to 0.
     *
     * @param text the text to be displayed, or null
     */
    public IkenshoACRowMaximumableTextArea(String text) {
        super(text);
    }

    /**
     * Constructs a new TextArea with the specified text and number
     * of rows and columns.  A default model is created.
     *
     * @param text the text to be displayed, or null
     * @param rows the number of rows >= 0
     * @param columns the number of columns >= 0
     * @exception IllegalArgumentException if the rows or columns
     *  arguments are negative.
     */
    public IkenshoACRowMaximumableTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
    }

    public void bindSource() throws ParseException {
        super.bindSource();
        if ((getModel() == null) && (getFormat() == null)) {
            setText("");
        } else {
            setSelectionStart(0);
            setSelectionEnd(0);
        }
    }

    /**
     * IMEモードを返します。
     * 
     * @return フォーカス取得時に自動設定するIMEモード
     */
    public InputSubset getIMEMode() {
        return this.imeMode;
    }

    /**
     * 最大入力行数 を返します。
     * 
     * @return 最大入力行数
     */
    public int getMaxRows() {
        return maxRows;
    }
    
    public Dimension getPreferredScrollableViewportSize() {
        Dimension size = super.getPreferredScrollableViewportSize();
        if (size == null) {
            size = new Dimension(400, 400);
        }

        int w = 0, h = 0;
        if (getColumns() != 0) {
            
            Insets margin = getInsets();
            if (margin != null) {
                // [ID:0000438][Tozo TANAKA] 2009/06/08 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
                //w += margin.left + margin.right;
                w += margin.left + margin.right-1;
                // [ID:0000438][Tozo TANAKA] 2009/06/08 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
            }
            
            double per=1.0;
            if (!runComposition) {
                //Macの補正率は0.55
                per = 0.55;
                // [ID:0000438][Tozo TANAKA] 2009/06/08 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
                w -= 2;
                // [ID:0000438][Tozo TANAKA] 2009/06/08 add end 【主治医医見書・医師医見書】薬剤名テキストの追加
            }
            size.width = (int)Math.max(0, getColumnWidth()*(getColumns()*per));
        }
        if (getRows() != 0) {
            Insets margin = getInsets();
            if (margin != null) {
                h += margin.top + margin.bottom;
            }
            size.height = getRows() * getRowHeight();
        }
        size.width += w;
        size.height += h;

        return size;
    }


    /**
     * IMEモードを設定します。
     * 
     * @param imeMode フォーカス取得時に自動設定するIMEモード
     */
    public void setIMEMode(InputSubset imeMode) {
        this.imeMode = imeMode;
    }

    /**
     * 最大入力行数 を設定します。
     * 
     * @param maxRows 最大入力行数
     */
    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    protected Document createDocument() {
        return new ACTextAreaDocument(this);
    }

    protected void initComponent() {
        super.initComponent();
        String osName = System.getProperty("os.name");

        Font nowFont = getFont();
        if ((osName == null) || (osName.indexOf("Mac") < 0)) {
            // Mac以外は"ＭＳ ゴシック"
            if (nowFont == null) {
                setFont(new Font("ＭＳ ゴシック", Font.PLAIN, 12));
            } else {
                setFont(new Font("ＭＳ ゴシック", nowFont.getStyle(), nowFont
                        .getSize()));
            }
            runComposition = true;
        } else {
            // Mac
            String ver = System.getProperty("os.version", "");
            if("10.4.0".compareTo(ver)>=0){
                // 10.4未満は"Osaka"
                if (nowFont == null) {
                    setFont(new Font("Osaka", Font.PLAIN, 12));
                } else {
                    setFont(new Font("Osaka", nowFont.getStyle(), nowFont.getSize()));
                }
            }else{
                //10.4以上は何もしない
            }
            runComposition = false;
        }

        setInsertTab(true);
//        new ACTextComponentPopupMenu().addInvoker(this);
    }

    /**
     * フォーカス取得時の追加処理を行ないます。
     * 
     * @param e イベント情報
     */
    protected void processFocusGained(FocusEvent e) {
        InputSubset ime = this.getIMEMode();
        if (ime == null) {
            return;
        }
        if (this.getInputContext() != null) {
            // ひらがなの場合だけはコンポジションを有効にする
            if (ime == InputSubset.KANJI) {
                // Mac対応
                if (runComposition) {
                    this.getInputContext().setCompositionEnabled(true);
                }
            }
            this.getInputContext().setCharacterSubsets(
                    new Character.Subset[] { ime });
        }

        super.processFocusGained(e);
    }

    /**
     * フォーカス喪失時の追加処理を行ないます。
     * 
     * @param e イベント情報
     */
    protected void processFocusLost(FocusEvent e) {
        super.processFocusLost(e);
        if (this.getInputContext() != null) {
            // IMEモードを戻す
            getInputContext().setCharacterSubsets(null);
        }
    }

//    /**
//     * 変換対象の文字種別 を設定します。
//     * @param fromCharacter 変換対象の文字種別
//     */
//    public void setConvertFromCharacter(int fromCharacter) {
//        if(getDocument() instanceof ACTextAreaDocument){
//            ((ACTextAreaDocument)getDocument()).setFromCharacter(fromCharacter);
//        }
//    }
//    
//    /**
//     * 変換結果の文字種別 を設定します。
//     * @param toCharacter 変換結果の文字種別
//     */
//    public void setConvertToCharacter(int toCharacter) {
//        if(getDocument() instanceof ACTextAreaDocument){
//            ((ACTextAreaDocument)getDocument()).setToCharacter(toCharacter);
//        }
//    }
//
//    /**
//     * 変換対象の文字種別 を返します。
//     * @return 変換対象の文字種別
//     */
//    public int getConvertFromCharacter() {
//        if(getDocument() instanceof ACTextAreaDocument){
//            return ((ACTextAreaDocument)getDocument()).getFromCharacter();
//        }
//        return 0;
//    }
//
//    /**
//     * 変換結果の文字種別 を返します。
//     * @return 変換結果の文字種別
//     */
//    public int getConvertToCharacter() {
//        if(getDocument() instanceof ACTextAreaDocument){
//            return ((ACTextAreaDocument)getDocument()).getToCharacter();
//        }
//        return 0;
//    }
    
    // [ID:0000438][Tozo TANAKA] 2009/06/10 delete begin 【主治医医見書・医師医見書】薬剤名テキストの追加
    public void setModelForce(Object model) {
        setModel(model);
    }
    // [ID:0000438][Tozo TANAKA] 2009/06/10 delete end 【主治医医見書・医師医見書】薬剤名テキストの追加

}