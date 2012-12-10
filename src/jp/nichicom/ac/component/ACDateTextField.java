package jp.nichicom.ac.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.event.ACFollowContainerFormatEventListener;
import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * ���t�p�ɐݒ肵���e�L�X�g�t�B�[���h�ł��B
 * <p>
 * ���t�Ƃ��ĕs�K�؂Ȓl����͂����ꍇ�A�e�R���e�i�𑖍����Ē��F���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACTextField
 */
public class ACDateTextField extends ACTextField {
    private boolean allowedFutureDate = true;
    private Date futureBaseDate;
    private ACFollowContainerFormatEventListener followFormatListener = new ACFollowContainerFormatEventListener(
            true);

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACDateTextField() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.addFormatEventListener(followFormatListener);

        this.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!isAllowedFutureDate()) {
                    // �������t�����e���Ȃ��ꍇ
                    if (isValid()) {
                        // ���t�Ƃ��Ă͓K���ł��邽�߁A�������t���`�F�b�N����
                        Calendar x = Calendar.getInstance();
                        Calendar y = Calendar.getInstance();
                        boolean future = false;
                        try {
                            x.setTime(getDate());
                            y.setTime(getFutureBaseDate());

                            int cmpY = x.get(Calendar.YEAR);
                            int baseY = y.get(Calendar.YEAR);
                            int cmpD = x.get(Calendar.DAY_OF_YEAR);
                            int baseD = y.get(Calendar.DAY_OF_YEAR);
                            if (cmpY == baseY) {
                                // ���N�Ȃ�ΔN���̓��ɂ��Ŕ�r
                                if (cmpD > baseD) {
                                    // �������̂ق����と����
                                    future = true;
                                }
                            } else {
                                // �������Ɗ����N�Ŕ�r
                                future = cmpY > baseY;
                            }
                        } catch (Exception e1) {
                            future = true;
                        }

                        if (future) {
                            // �����̏ꍇ�͖��������Ƃ���
                            followFormatListener.changeInvalidContainer();
                        }

                    }
                }
            }
        });
    }

    /**
     * ���͒l��Date�^�Ŏ擾���܂��B
     * 
     * @return ���͒l
     * @throws Exception ������O
     */
    public Date getDate() throws Exception {
        return VRDateParser.parse(getText());
    }

    /**
     * �������t����̊�ƂȂ錻�ݓ��� ��Ԃ��܂��B
     * <p>
     * ���A���^�C���Ȍ��ݓ������g���ꍇ��null���w�肵�܂��B
     * </p>
     * 
     * @return �������t����̊�ƂȂ錻�ݓ���
     */
    public Date getFutureBaseDate() {
        return futureBaseDate;
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
     * �����͂������邩 ��Ԃ��܂��B
     * 
     * @return �����͂������邩
     */
    public boolean isAllowedBlank() {
        return followFormatListener.isAllowedBlank();
    }

    /**
     * �������t�������邩 ��Ԃ��܂��B
     * 
     * @return �������t�������邩
     * @see #setFutureBaseDate(Date)
     */
    public boolean isAllowedFutureDate() {
        return allowedFutureDate;
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
     * �����͂������邩 ��ݒ肵�܂��B
     * 
     * @param allowedBlank �����͂������邩
     */
    public void setAllowedBlank(boolean allowedBlank) {
        followFormatListener.setAllowedBlank(allowedBlank);
    }

    /**
     * �������t�������邩 ��ݒ肵�܂��B
     * 
     * @param allowedFutureDate �������t�������邩
     */
    public void setAllowedFutureDate(boolean allowedFutureDate) {
        this.allowedFutureDate = allowedFutureDate;
    }

    /**
     * ���͒l��Date�^�Őݒ肵�܂��B
     * 
     * @param value ���͒l
     * @throws Exception ������O
     */
    public void setDate(Date value) throws Exception {
        setText(VRDateParser.format(value, "yyyy/MM/dd hh:mm:ss"));
    }

    /**
     * �������t����̊�ƂȂ錻�ݓ��� ��ݒ肵�܂��B
     * <p>
     * ���A���^�C���Ȍ��ݓ������g���ꍇ��null���w�肵�܂��B
     * </p>
     * 
     * @param futureBaseDate �������t����̊�ƂȂ錻�ݓ���
     */
    public void setFutureBaseDate(Date futureBaseDate) {
        this.futureBaseDate = futureBaseDate;
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
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ������O
     */
    private void jbInit() throws Exception {
        this.setColumns(11);
        this.setMaxLength(11);
        this.setFormat(ACConstants.FORMAT_FULL_ERA_YMD);
    }

}
