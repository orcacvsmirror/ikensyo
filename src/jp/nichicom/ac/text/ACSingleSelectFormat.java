package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * �e�[�u���J�����ɐݒ肳��邱�Ƃ�z�肵������f�[�^�F���t�H�[�}�b�g�ł��B
 * <p>
 * �f�[�^������f�[�^�̏ꍇ�͑I����Ԃ�\����������o�͂��܂��B<br />
 * �f�[�^������f�[�^�łȂ��ꍇ�́A��I����Ԃ�\����������o�͂��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACSingleSelectFormat extends Format {
    protected Object selectedData;
    protected String selectedText;
    protected String unselectedText;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param selectedText �I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ�����
     * @param unselectedText ��I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ�����
     */
    public ACSingleSelectFormat(String selectedText, String unselectedText) {
        setSelectedText(selectedText);
        setUnselectedText(unselectedText);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj == null) {
            if (getSelectedData() == null) {
                toAppendTo.append(getSelectedText());
            } else {
                toAppendTo.append(getUnselectedText());
            }
        } else {
            if (obj.equals(getSelectedData())) {
                toAppendTo.append(getSelectedText());
            } else {
                toAppendTo.append(getUnselectedText());
            }
        }
        return toAppendTo;
    }

    /**
     * �I�������Ƃ���f�[�^��Ԃ��܂��B
     * 
     * @return �I�������Ƃ���f�[�^
     */
    public Object getSelectedData() {
        return selectedData;
    }

    /**
     * �I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ������Ԃ��܂��B
     * 
     * @return �I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ�����
     */
    public String getSelectedText() {
        return selectedText;
    }

    /**
     * ��I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ������Ԃ��܂��B
     * 
     * @return ��I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ�����
     */
    public String getUnselectedText() {
        return unselectedText;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (source == null) {
            if (getSelectedText() == null) {
                return getSelectedData();
            }
        } else {
            if (source.equals(getSelectedText())) {
                return getSelectedData();
            }
        }
        return null;
    }

    /**
     * �I�������Ƃ���f�[�^��ݒ肵�܂��B
     * 
     * @param selectedData �I�������Ƃ���f�[�^
     */
    public void setSelectedData(Object selectedData) {
        this.selectedData = selectedData;
    }

    /**
     * �I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ������ݒ肵�܂��B
     * 
     * @param selectedText �I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ�����
     */
    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    /**
     * ��I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ������ݒ肵�܂��B
     * 
     * @param unselectedText ��I�������̃f�[�^�ɑΉ�����t�H�[�}�b�g���ʕ�����
     */
    public void setUnselectedText(String unselectedText) {
        this.unselectedText = unselectedText;
    }

}
