package jp.nichicom.ac.util.event;

import java.awt.Color;
import java.util.Calendar;
import java.util.EventObject;

/**
 * �J�����_�[�`��C�x���g�ł��B
 * <p>
 * �J�����_�[�R���|�[�l���g���`�悳��鎞�ɌĂяo�����C�x���g�����i�[����N���X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendarDrawEvent
    extends EventObject {

  private String subText;
  private Color selectedColor;
  private Color dateNoColor;
  private Color subTextColor;
  private Calendar calendar;

  /**
   * ���C���R���X�g���N�^ - �p�����[�^�Ń����o�ϐ������������܂��B
   *
   * @param source �C�x���g������
   * @param calendar �`��Ώۓ�
   * @param subText ���t����
   * @param selectedColor �I�����̐}�`�`��F
   * @param dateNoColor ���t�̕`��F
   * @param subTextColor ���t���̂̕`��F
   */
  public ACCalendarDrawEvent(Object source, Calendar calendar, String subText,
                              Color selectedColor, Color dateNoColor,
                              Color subTextColor) {
    super(source);
    setCalendar(calendar);
    setSubText(subText);
    setSelectedColor(selectedColor);
    setDateNoColor(dateNoColor);
    setSubTextColor(subTextColor);

  }

  /**
   * ���t���̂�Ԃ��܂��B
   *
   * @return ���t����
   */
  public String getSubText() {
    return subText;
  }

  /**
   * ���t���̂�ݒ肵�܂��B
   *
   * @param pstrSubText ���t����
   */
  public void setSubText(String pstrSubText) {
    if (pstrSubText == null) {
      return;
    }
    this.subText = pstrSubText;
  }

  /**
   * �I�����̐}�`�`��F��Ԃ��܂��B
   *
   * @return �I�����̐}�`�`��F
   */
  public Color getSelectedColor() {
    return selectedColor;
  }

  /**
   * �I�����̐}�`�`��F��ݒ肵�܂��B
   *
   * @param pclsSelectedColor �I�����̐}�`�`��F
   */
  public void setSelectedColor(Color pclsSelectedColor) {
    if (pclsSelectedColor == null) {
      return;
    }
    this.selectedColor = pclsSelectedColor;
  }

  /**
   * ���t�̕`��F��Ԃ��܂��B
   *
   * @return ���t�̕`��F
   */
  public Color getDateNoColor() {
    return dateNoColor;
  }

  /**
   * ���t�̕`��F��ݒ肵�܂��B
   *
   * @param pclsDateNoColor ���t�̕`��F
   */
  public void setDateNoColor(Color pclsDateNoColor) {
    if (pclsDateNoColor == null) {
      return;
    }
    this.dateNoColor = pclsDateNoColor;
  }

  /**
   * ���t���̂̕`��F��Ԃ��܂��B
   *
   * @return ���t���̂̕`��F
   */
  public Color getSubTextColor() {
    return subTextColor;
  }

  /**
   * ���t���̂̕`��F��ݒ肵�܂��B
   *
   * @param pclsSubTextColor ���t���̂̕`��F
   */
  public void setSubTextColor(Color pclsSubTextColor) {
    if (pclsSubTextColor == null) {
      return;
    }
    this.subTextColor = pclsSubTextColor;
  }

  /**
   * �`��Ώۂ̓��t��Ԃ��܂��B
   *
   * @return �`��Ώۂ̓��t
   */
  public Calendar getCalendar() {
    return calendar;
  }

  /**
   * �`��Ώۂ̓��t��ݒ肵�܂��B
   *
   * @param pclsCalendar �`��Ώۂ̓��t
   */
  public void setCalendar(Calendar pclsCalendar) {
    if (pclsCalendar == null) {
      return;
    }
    this.calendar = pclsCalendar;
  }
}
