package jp.nichicom.ac.util.event;

import java.awt.Color;
import java.util.Calendar;
import java.util.EventObject;

/**
 * カレンダー描画イベントです。
 * <p>
 * カレンダーコンポーネントが描画される時に呼び出されるイベント情報を格納するクラスです。
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
   * メインコンストラクタ - パラメータでメンバ変数を初期化します。
   *
   * @param source イベント発生元
   * @param calendar 描画対象日
   * @param subText 日付名称
   * @param selectedColor 選択時の図形描画色
   * @param dateNoColor 日付の描画色
   * @param subTextColor 日付名称の描画色
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
   * 日付名称を返します。
   *
   * @return 日付名称
   */
  public String getSubText() {
    return subText;
  }

  /**
   * 日付名称を設定します。
   *
   * @param pstrSubText 日付名称
   */
  public void setSubText(String pstrSubText) {
    if (pstrSubText == null) {
      return;
    }
    this.subText = pstrSubText;
  }

  /**
   * 選択時の図形描画色を返します。
   *
   * @return 選択時の図形描画色
   */
  public Color getSelectedColor() {
    return selectedColor;
  }

  /**
   * 選択時の図形描画色を設定します。
   *
   * @param pclsSelectedColor 選択時の図形描画色
   */
  public void setSelectedColor(Color pclsSelectedColor) {
    if (pclsSelectedColor == null) {
      return;
    }
    this.selectedColor = pclsSelectedColor;
  }

  /**
   * 日付の描画色を返します。
   *
   * @return 日付の描画色
   */
  public Color getDateNoColor() {
    return dateNoColor;
  }

  /**
   * 日付の描画色を設定します。
   *
   * @param pclsDateNoColor 日付の描画色
   */
  public void setDateNoColor(Color pclsDateNoColor) {
    if (pclsDateNoColor == null) {
      return;
    }
    this.dateNoColor = pclsDateNoColor;
  }

  /**
   * 日付名称の描画色を返します。
   *
   * @return 日付名称の描画色
   */
  public Color getSubTextColor() {
    return subTextColor;
  }

  /**
   * 日付名称の描画色を設定します。
   *
   * @param pclsSubTextColor 日付名称の描画色
   */
  public void setSubTextColor(Color pclsSubTextColor) {
    if (pclsSubTextColor == null) {
      return;
    }
    this.subTextColor = pclsSubTextColor;
  }

  /**
   * 描画対象の日付を返します。
   *
   * @return 描画対象の日付
   */
  public Calendar getCalendar() {
    return calendar;
  }

  /**
   * 描画対象の日付を設定します。
   *
   * @param pclsCalendar 描画対象の日付
   */
  public void setCalendar(Calendar pclsCalendar) {
    if (pclsCalendar == null) {
      return;
    }
    this.calendar = pclsCalendar;
  }
}
