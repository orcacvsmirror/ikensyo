package jp.nichicom.ac.component;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.text.Document;

import jp.nichicom.ac.text.ACTextAreaDocument;
import jp.nichicom.vr.component.VRTextArea;

/**
 * �s�������ɑΉ������e�L�X�g�G���A�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTextArea
 */

public class ACRowMaximumableTextArea extends VRTextArea {

    private boolean runComposition;
    protected InputSubset imeMode;

    protected int maxRows = 0;

    /**
     * Constructs a new TextArea.  A default model is set, the initial string
     * is null, and rows/columns are set to 0.
     */
    public ACRowMaximumableTextArea() {
        super();
    }

    /**
     * Constructs a new TextArea with the specified text displayed.
     * A default model is created and rows/columns are set to 0.
     *
     * @param text the text to be displayed, or null
     */
   public ACRowMaximumableTextArea(Document doc, String text, int rows,
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
    public ACRowMaximumableTextArea(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Constructs a new TextArea with the specified text displayed.
     * A default model is created and rows/columns are set to 0.
     *
     * @param text the text to be displayed, or null
     */
    public ACRowMaximumableTextArea(String text) {
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
    public ACRowMaximumableTextArea(String text, int rows, int columns) {
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
     * IME���[�h��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
     */
    public InputSubset getIMEMode() {
        return this.imeMode;
    }

    /**
     * �ő���͍s�� ��Ԃ��܂��B
     * 
     * @return �ő���͍s��
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
                w += margin.left + margin.right;
            }
            margin = getBorder().getBorderInsets(this);
            if (margin != null) {
                w += margin.left + margin.right;
            }
            size.width = (getColumns() + 1) * getColumnWidth();
        }
        if (getRows() != 0) {
            Insets margin = getInsets();
            if (margin != null) {
                h += margin.top + margin.bottom;
            }
            margin = getBorder().getBorderInsets(this);
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
     * IME���[�h��ݒ肵�܂��B
     * 
     * @param imeMode �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
     */
    public void setIMEMode(InputSubset imeMode) {
        this.imeMode = imeMode;
    }

    /**
     * �ő���͍s�� ��ݒ肵�܂��B
     * 
     * @param maxRows �ő���͍s��
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
            // Mac�ȊO��"�l�r �S�V�b�N"
            if (nowFont == null) {
                setFont(new Font("�l�r �S�V�b�N", Font.PLAIN, 12));
            } else {
                setFont(new Font("�l�r �S�V�b�N", nowFont.getStyle(), nowFont
                        .getSize()));
            }
            runComposition = true;
        } else {
            // Mac
            String ver = System.getProperty("os.version", "");
            if("10.4.0".compareTo(ver)>=0){
                // 10.4������"Osaka"
                if (nowFont == null) {
                    setFont(new Font("Osaka", Font.PLAIN, 12));
                } else {
                    setFont(new Font("Osaka", nowFont.getStyle(), nowFont.getSize()));
                }
            }else{
                //10.4�ȏ�͉������Ȃ�
            }
            runComposition = false;
        }

        setInsertTab(true);
        new ACTextComponentPopupMenu().addInvoker(this);
    }

    /**
     * �t�H�[�J�X�擾���̒ǉ��������s�Ȃ��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void processFocusGained(FocusEvent e) {
        InputSubset ime = this.getIMEMode();
        if (ime == null) {
            return;
        }
        if (this.getInputContext() != null) {
            // �Ђ炪�Ȃ̏ꍇ�����̓R���|�W�V������L���ɂ���
            if (ime == InputSubset.KANJI) {
                // Mac�Ή�
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
     * �t�H�[�J�X�r�����̒ǉ��������s�Ȃ��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void processFocusLost(FocusEvent e) {
        super.processFocusLost(e);
        // IME���[�h��߂�
        getInputContext().setCharacterSubsets(null);
    }
}