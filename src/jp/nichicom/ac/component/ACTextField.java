package jp.nichicom.ac.component;

import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.text.Document;

import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.text.ACTextFieldDocument;
import jp.nichicom.vr.component.VRTextField;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * �e�L�X�g�t�B�[���h�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACParentHesesPanelContainer
 */

public class ACTextField extends VRTextField {
    private InputSubset imeMode;

    private boolean runComposition;

    /**
     * Constructs a new <code>TextField</code>. A default model is created,
     * the initial string is <code>null</code>, and the number of columns is
     * set to 0.
     */
    public ACTextField() {
        super();
    }

    /**
     * Constructs a new <code>JTextField</code> that uses the given text
     * storage model and the given number of columns. This is the constructor
     * through which the other constructors feed. If the document is
     * <code>null</code>, a default model is created.
     * 
     * @param doc the text storage to use; if this is <code>null</code>, a
     *            default will be provided by calling the
     *            <code>createDefaultModel</code> method
     * @param text the initial string to display, or <code>null</code>
     * @param columns the number of columns to use to calculate the preferred
     *            width >= 0; if <code>columns</code> is set to zero, the
     *            preferred width will be whatever naturally results from the
     *            component implementation
     * @exception IllegalArgumentException if <code>columns</code> < 0
     */
    public ACTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    /**
     * Constructs a new empty <code>TextField</code> with the specified number
     * of columns. A default model is created and the initial string is set to
     * <code>null</code>.
     * 
     * @param columns the number of columns to use to calculate the preferred
     *            width; if columns is set to zero, the preferred width will be
     *            whatever naturally results from the component implementation
     */
    public ACTextField(int columns) {
        super(columns);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the specified
     * text. A default model is created and the number of columns is 0.
     * 
     * @param text the text to be displayed, or <code>null</code>
     */
    public ACTextField(String text) {
        super(text);
    }

    /**
     * Constructs a new <code>TextField</code> initialized with the specified
     * text and columns. A default model is created.
     * 
     * @param text the text to be displayed, or <code>null</code>
     * @param columns the number of columns to use to calculate the preferred
     *            width; if columns is set to zero, the preferred width will be
     *            whatever naturally results from the component implementation
     */

    public ACTextField(String text, int columns) {
        super(text, columns);
    }

    public void bindSource() throws ParseException {
        super.bindSource();
        if ((getModel() == null) && (getFormat() == null)) {
            setText("");
        } else {
            setSelectionStart(0);
            setSelectionEnd(0);
        }
        getPreferredSize();
    }

    /**
     * IME���[�h��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
     */
    public InputSubset getIMEMode() {
        return this.imeMode;
    }

    public void setEditable(boolean editable) {
        // Editable=false��VB�ł����Ƃ����Lock�ƈʒu�t���A�t�H�[�J�X�����ĂȂ�
        super.setEditable(editable);
        super.setFocusable(editable);
    }

    /**
     * IME���[�h��ݒ肵�܂��B
     * 
     * @param imeMode �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
     */
    public void setIMEMode(InputSubset imeMode) {
        this.imeMode = imeMode;
    }

    protected Document createDocument() {
        return new ACTextFieldDocument(this);
    }

    protected int getColumnWidth() {
        // �S�p�������l�����ĕ��v�Z�����邽�߁A1.1�{���ĕԂ�
        return (int) (super.getColumnWidth() * 1.1);
    }

    protected void initComponent() {
        super.initComponent();

        // Mac�Ή�
        runComposition = !System.getProperty("os.name").startsWith("Mac");

        setFocusSelcetAll(true);

        String osName = System.getProperty("os.name","").toLowerCase();
        if ((osName != null) && (osName.indexOf("mac") >= 0)) {
            // Mac

            String ver = System.getProperty("os.version", "");
            if("10.4.0".compareTo(ver)>=0){
                // 10.4������"Osaka"
                Font nowFont = getFont();
                if (nowFont == null) {
                    setFont(new Font("Osaka", Font.PLAIN, 12));
                } else {
                    setFont(new Font("Osaka", nowFont.getStyle(), nowFont.getSize()));
                }
            }else{
                //10.4�ȏ�͉������Ȃ�
            }
        }
        
        new ACTextComponentPopupMenu().addInvoker(this);
    }

    protected void processFocusGained(FocusEvent e) {
        InputSubset ime = this.getIMEMode();
        if (ime != null) {
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
        }

        super.processFocusGained(e);
    }

    protected void processFocusLost(FocusEvent e) {
        super.processFocusLost(e);
        if (this.getInputContext() != null) {
            // IME���[�h��߂�
            getInputContext().setCharacterSubsets(null);
        }

//        setSelectionStart(0);
//        setSelectionEnd(0);
    }

}
