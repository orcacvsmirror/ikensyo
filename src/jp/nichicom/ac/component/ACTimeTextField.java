package jp.nichicom.ac.component;

import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.event.ACFollowContainerFormatEventListener;
import jp.nichicom.ac.text.ACTimeFormat;
import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * �����p�ɐݒ肵���e�L�X�g�t�B�[���h�ł��B
 * <p>
 * �����Ƃ��ĕs�K�؂Ȓl����͂����ꍇ�A�e�R���e�i�𑖍����Ē��F���܂��B
 * </p>
 * <p>
 * ���E���̓��͂�ΏۂƂ��Ă���A�N������b�̒l�͕ۏ؂��܂���B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACTextField
 * @see ACTimeFormat
 */
public class ACTimeTextField extends ACTextField {
    private ACTimeFormat timeFormat = new ACTimeFormat();
    private ACFollowContainerFormatEventListener followFormatListener = new ACFollowContainerFormatEventListener();

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACTimeTextField() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.addFormatEventListener(followFormatListener);
    }

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ������O
     */
    private void jbInit() throws Exception {
        this.setColumns(3);
        this.setMaxLength(6);
        this.setFormat(timeFormat);
        this.setCharType(ACConstants.CHAR_TYPE_TIME_HOUR_MINUTE);
    }

    /**
     * �G���[���Ɍ�������e�K�w�̐���Ԃ��܂��B
     * 
     * @return �G���[���Ɍ�������e�K�w
     */
    public int getParentFindCount() {
        return followFormatListener.getParentFindCount();
    }

    /**
     * �G���[���Ɍ�������e�K�w�̐���ݒ肵�܂��B
     * 
     * @param parentFindCount �G���[���Ɍ�������e�K�w
     */
    public void setParentFindCount(int parentFindCount) {
        followFormatListener.setParentFindCount(parentFindCount);
    }

    /**
     * ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t
     */
    public Date getBaseDate() {
        return timeFormat.getBaseDate();
    }

    /**
     * �o�͏��� ��Ԃ��܂��B
     * 
     * @return �o�͏���
     */
    public String getFormatedFormat() {
        return timeFormat.getFormatedFormat();
    }

    /**
     * ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏��� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏���
     */
    public String getPargedFormat() {
        return timeFormat.getPargedFormat();
    }

    /**
     * ��͌��ʂ̌^ ��Ԃ��܂��B
     * <p>
     * VALUE_TYPE_STRING : ������<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @return ��͌��ʂ̌^
     */
    public int getParsedValueType() {
        return timeFormat.getParsedValueType();
    }

    /**
     * ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t ��ݒ肵�܂��B
     * 
     * @param baseDate ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t
     */
    public void setBaseDate(Date baseDate) {
        timeFormat.setBaseDate(baseDate);
    }

    /**
     * �o�͏��� ��ݒ肵�܂��B
     * 
     * @param formatedFormat �o�͏���
     */
    public void setFormatedFormat(String formatedFormat) {
        timeFormat.setFormatedFormat(formatedFormat);
    }

    /**
     * ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏��� ��ݒ肵�܂��B
     * 
     * @param pargedFormat ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏���
     */
    public void setPargedFormat(String pargedFormat) {
        timeFormat.setPargedFormat(pargedFormat);
    }

    /**
     * ��͌��ʂ̌^ ��ݒ肵�܂��B
     * <p>
     * VALUE_TYPE_STRING : ������<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @param parsedValueType ��͌��ʂ̌^
     */
    public void setParsedValueType(int parsedValueType) {
        timeFormat.setParsedValueType(parsedValueType);
    }

    /**
     * �L���ȓ��t�����͂���Ă��邩��Ԃ��܂��B
     * 
     * @return �L���ȓ��t�����͂���Ă��邩
     */
    public boolean isValidDate() {
        return followFormatListener.isValid();
    }

    /**
     * ���͒l��Date�^�Ŏ擾���܂��B
     * 
     * @return ���͒l
     * @throws Exception ������O
     */
    public Date getDate() throws Exception {
        return (Date)timeFormat.parseObject(getText());
//        return VRDateParser.parse(getText());
    }

    /**
     * ���͒l��Date�^�Őݒ肵�܂��B
     * 
     * @param value ���͒l
     * @throws Exception ������O
     */
    public void setDate(Date value) throws Exception {
        setText(VRDateParser.format(value, "HH:mm"));
    }

    /**
     * �����͂������邩 ��Ԃ��܂��B
     * 
     * @return �����͂������邩
     */
    public boolean isAllowedBlank() {
        return followFormatListener.isAllowedBlank();
    }

    /**
     * �����͂������邩 ��ݒ肵�܂��B
     * 
     * @param allowedBlank �����͂������邩
     */
    public void setAllowedBlank(boolean allowedBlank) {
        followFormatListener.setAllowedBlank(allowedBlank);
    }

}
