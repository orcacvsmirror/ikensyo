package jp.nichicom.ac.io;

import java.util.Calendar;
import java.util.Date;

/**
 * 年齢計算ライブラリです。
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
   * インスタンスを返します。
   * @return インスタンス
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
   * 生年月日と本日日付を比較して、年齢を算出する
   * @param birth 生年月日
   * @return 本日日付時点での年齢
   */
  public int toAge(Calendar birth) {
    //例外処理
    if (birth == null) {
      throw new NullPointerException("生年月日(birth)にnullは使用できません。");
    }

    Calendar base = Calendar.getInstance();
    return toAge(birth, base);
  }

  /**
   * 生年月日と基準日を比較して、年齢を算出する
   * @param birth 生年月日
   * @param base 基準日
   * @return 基準日時点での年齢
   */
  public int toAge(Calendar birth, Calendar base) {
    //例外処理
    if (base == null) {
      throw new NullPointerException("基準日(base)にnullは使用できません。");
    }
    if (birth == null) {
      throw new NullPointerException("生年月日(birth)にnullは使用できません。");
    }

    return toAge(birth.get(Calendar.YEAR),
                 birth.get(Calendar.MONTH),
                 birth.get(Calendar.DAY_OF_MONTH),
                 base.get(Calendar.YEAR),
                 base.get(Calendar.MONTH),
                 base.get(Calendar.DAY_OF_MONTH));
  }

  /**
   * 生年月日と本日日付を比較して、年齢を算出する
   * @param birth 生年月日
   * @return 本日日付時点での年齢
   */
  public int toAge(Date birth) {
    //例外処理
    if (birth == null) {
      throw new NullPointerException("生年月日(birth)にnullは使用できません。");
    }

    Calendar calBase = Calendar.getInstance();
    Date base = calBase.getTime();
    return toAge(birth, base);
  }

  /**
   * 生年月日と基準日を比較して、年齢を算出する
   * @param birth 生年月日
   * @param base 基準日
   * @return 基準日時点での年齢
   */
  public int toAge(Date birth, Date base) {
    //例外処理
    if (base == null) {
      throw new NullPointerException("基準日(base)にnullは使用できません。");
    }
    if (birth == null) {
      throw new NullPointerException("生年月日(birth)にnullは使用できません。");
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
   * 生年月日と本日日付を比較して、年齢を算出する
   * @param birthYear 生年月日：年
   * @param birthMonth 生年月日：月(0-11)
   * @param birthDay 生年月日：日
   * @return 本日日付時点での年齢
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
   * 生年月日と基準日を比較して、年齢を算出する
   * @param birthYear 生年月日：年
   * @param birthMonth 生年月日：月(0-11)
   * @param birthDay 生年月日：日
   * @param baseYear 基準日：年
   * @param baseMonth 基準日：月(0-11)
   * @param baseDay 基準日：日
   * @return 基準日時点での年齢
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
