package jp.nichicom.ac.io;

import java.util.Calendar;
import java.util.Date;

/**
 * �N��v�Z���C�u�����ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */
public class ACAgeEncorder {
  private static ACAgeEncorder singleton;
  /**
   * �C���X�^���X��Ԃ��܂��B
   * @return �C���X�^���X
   */
  public static ACAgeEncorder getInstance() {
    if (singleton == null) {
      singleton = new ACAgeEncorder();
    }
    return singleton;
  }

  protected ACAgeEncorder() {
  }

  /**
   * ���N�����Ɩ{�����t���r���āA�N����Z�o����
   * @param birth ���N����
   * @return �{�����t���_�ł̔N��
   */
  public int toAge(Calendar birth) {
    //��O����
    if (birth == null) {
      throw new NullPointerException("���N����(birth)��null�͎g�p�ł��܂���B");
    }

    Calendar base = Calendar.getInstance();
    return toAge(birth, base);
  }

  /**
   * ���N�����Ɗ�����r���āA�N����Z�o����
   * @param birth ���N����
   * @param base ���
   * @return ������_�ł̔N��
   */
  public int toAge(Calendar birth, Calendar base) {
    //��O����
    if (base == null) {
      throw new NullPointerException("���(base)��null�͎g�p�ł��܂���B");
    }
    if (birth == null) {
      throw new NullPointerException("���N����(birth)��null�͎g�p�ł��܂���B");
    }

    return toAge(birth.get(Calendar.YEAR),
                 birth.get(Calendar.MONTH),
                 birth.get(Calendar.DAY_OF_MONTH),
                 base.get(Calendar.YEAR),
                 base.get(Calendar.MONTH),
                 base.get(Calendar.DAY_OF_MONTH));
  }

  /**
   * ���N�����Ɩ{�����t���r���āA�N����Z�o����
   * @param birth ���N����
   * @return �{�����t���_�ł̔N��
   */
  public int toAge(Date birth) {
    //��O����
    if (birth == null) {
      throw new NullPointerException("���N����(birth)��null�͎g�p�ł��܂���B");
    }

    Calendar calBase = Calendar.getInstance();
    Date base = calBase.getTime();
    return toAge(birth, base);
  }

  /**
   * ���N�����Ɗ�����r���āA�N����Z�o����
   * @param birth ���N����
   * @param base ���
   * @return ������_�ł̔N��
   */
  public int toAge(Date birth, Date base) {
    //��O����
    if (base == null) {
      throw new NullPointerException("���(base)��null�͎g�p�ł��܂���B");
    }
    if (birth == null) {
      throw new NullPointerException("���N����(birth)��null�͎g�p�ł��܂���B");
    }

    Calendar calBirth = Calendar.getInstance();
    calBirth.setTime(birth);
    Calendar calBase = Calendar.getInstance();
    calBase.setTime(base);

    return toAge(calBirth.get(Calendar.YEAR),
                 calBirth.get(Calendar.MONTH),
                 calBirth.get(Calendar.DAY_OF_MONTH),
                 calBase.get(Calendar.YEAR),
                 calBase.get(Calendar.MONTH),
                 calBase.get(Calendar.DAY_OF_MONTH));
  }

  /**
   * ���N�����Ɩ{�����t���r���āA�N����Z�o����
   * @param birthYear ���N�����F�N
   * @param birthMonth ���N�����F��(0-11)
   * @param birthDay ���N�����F��
   * @return �{�����t���_�ł̔N��
   */
  public int toAge(int birthYear, int birthMonth, int birthDay) {
    Calendar base = Calendar.getInstance();
    return toAge(birthYear,
                 birthMonth,
                 birthDay,
                 base.get(Calendar.YEAR),
                 base.get(Calendar.MONTH),
                 base.get(Calendar.DAY_OF_MONTH));
  }

  /**
   * ���N�����Ɗ�����r���āA�N����Z�o����
   * @param birthYear ���N�����F�N
   * @param birthMonth ���N�����F��(0-11)
   * @param birthDay ���N�����F��
   * @param baseYear ����F�N
   * @param baseMonth ����F��(0-11)
   * @param baseDay ����F��
   * @return ������_�ł̔N��
   */
  public int toAge(int birthYear, int birthMonth, int birthDay, int baseYear,
                   int baseMonth, int baseDay) {
    int age = baseYear - birthYear;
    if (baseMonth < birthMonth) {
      age--;
    }
    else if (baseMonth == birthMonth) {
      if (baseDay < birthDay) {
        age--;
      }
    }
    return age;
  }

}
