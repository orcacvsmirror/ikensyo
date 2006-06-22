package jp.or.med.orca.ikensho.affair;

import java.util.Date;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoPrintParameter {
  /** 未作成 */
  public static final int CSV_OUTPUT_TYPE_UNKNOWN = -1;

  /** 不明 */
  public static final int CSV_OUTPUT_TYPE_NONE = 0;

  /** 出力対象 */
  public static final int CSV_OUTPUT_TYPE_TARGET = 1;

  /** 出力済み */
  public static final int CSV_OUTPUT_TYPE_OUTPUTED = 2;

  /** 未作成 */
  public static final int BILL_HAKKOU_TYPE_UNKNOWN = -1;

  /** 不明 */
  public static final int BILL_HAKKOU_TYPE_NONE = 0;

  /** 未発行 */
  public static final int BILL_HAKKOU_TYPE_NEVER_PRINT = 1;

  /** 発行済み */
  public static final int BILL_HAKKOU_TYPE_PRINTED = 2;

  /** 保留 */
  public static final int BILL_HAKKOU_TYPE_RESERVE = 3;

  /** 作成料・検査料(1枚) */
  public static final int BILL_PRINT_CREATE_AND_TEST = 1;

  /** 作成料(1枚)・検査料(1枚) */
  public static final int BILL_PRINT_CREATE_OR_TEST = 2;

  /** 作成料のみ */
  public static final int BILL_PRINT_ONLY_CREATE = 3;

  /** 検査料のみ */
  public static final int BILL_PRINT_ONLY_TEST = 4;


  private boolean csvSubmited;
  //2006/02/12[Tozo Tanaka] : add end
  private boolean csvTarget;
  //2006/02/12[Tozo Tanaka] : add end
  private int csvOutputType;
  private Date csvOutputTime;
  private int hakkouType;
  private boolean neverSaved;
  private String nowMode;
  private boolean typeChanged;
  private boolean notMostNewDocument;

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoPrintParameter() {
  }

  /**
   * CSV出力日時を返します。
   * @return CSV出力日時
   */
  public Date getCsvOutputTime() {
    return csvOutputTime;
  }

  /**
   * CSV出力状態を返します。
   * @return CSV出力状態
   */
  public int getCsvOutputType() {
    return csvOutputType;
  }

  /**
   * 請求書発行状態を返します。
   * @return 請求書発行状態
   */
  public int getHakkouType() {
    return hakkouType;
  }

  //2006/02/12[Tozo Tanaka] : add begin
  /**
   * CSV出力対象であるかを返します。
   * @return CSV出力対象であるか
   */
  public boolean isCsvTarget() {
    return csvTarget;
  }
  //2006/02/12[Tozo Tanaka] : add end

  /**
   * CSV出力済みであるかを返します。
   * @return CSV出力済みであるか
   */
  public boolean isCsvSubmited() {
    return csvSubmited;
  }

  /**
   * 未保存であるかを返します。
   * @return 未保存であるか
   */
  public boolean isNeverSaved() {
    return neverSaved;
  }

  /**
   * 現在の処理モードを返します。
   * @return 現在の処理モード
   */
  public String getNowMode() {
    return nowMode;
  }

  /**
   * 現在の処理モードを設定します。
   * @param nowMode 現在の処理モード
   */
  public void setNowMode(String nowMode) {
    this.nowMode = nowMode;
  }

  /**
   * 未保存であるかを設定します。
   * @param neverSaved 未保存であるか
   */
  public void setNeverSaved(boolean neverSaved) {
    this.neverSaved = neverSaved;
  }

  /**
   * 請求書発行状態を設定します。
   * @param hakkouType 請求書発行状態
   */
  public void setHakkouType(int hakkouType) {
    this.hakkouType = hakkouType;
  }

  //2006/02/12[Tozo Tanaka] : add end
  /**
   * CSV出力対象であるかを設定します。
   * @param csvTarget CSV出力対象であるか
   */
  public void setCsvTarget(boolean csvTarget) {
    this.csvTarget = csvTarget;
  }
  //2006/02/12[Tozo Tanaka] : add begin

  /**
   * CSV出力済みであるかを設定します。
   * @param csvSubmited CSV出力対象であるか
   */
  public void setCsvSubmited(boolean csvSubmited) {
    this.csvSubmited = csvSubmited;
  }

  /**
   * CSV出力状態であるかを設定します。
   * @param csvOutputType CSV出力状態であるか
   */
  public void setCsvOutputType(int csvOutputType) {
    this.csvOutputType = csvOutputType;
  }

  /**
   * CSV出力日時であるかを設定します。
   * @param csvOutputTime CSV出力日時であるか
   */
  public void setCsvOutputTime(Date csvOutputTime) {
    this.csvOutputTime = csvOutputTime;
  }

  /**
   * 出力区分が変動したかを返します。
   * @return 出力区分が変動したか
   */
  public boolean isTypeChanged() {
    return typeChanged;
  }

  /**
   * 出力区分が変動したかを設定します。
   * @param typeChanged 出力区分が変動したか
   */
  public void setTypeChanged(boolean typeChanged) {
    this.typeChanged = typeChanged;
  }

  /**
   * 最新以外の意見書であるかを返します。
   * @return 最新以外の意見書であるか
   */
  public boolean isNotMostNewDocument() {
    return notMostNewDocument;
  }

  /**
   * 最新以外の意見書であるかを設定します。
   * @param notMostNewDocument 最新以外の意見書であるか
   */
  public void setNotMostNewDocument(boolean notMostNewDocument) {
    this.notMostNewDocument = notMostNewDocument;
  }

}
