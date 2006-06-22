package jp.or.med.orca.ikensho.affair;

import java.util.Date;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoPrintParameter {
  /** ���쐬 */
  public static final int CSV_OUTPUT_TYPE_UNKNOWN = -1;

  /** �s�� */
  public static final int CSV_OUTPUT_TYPE_NONE = 0;

  /** �o�͑Ώ� */
  public static final int CSV_OUTPUT_TYPE_TARGET = 1;

  /** �o�͍ς� */
  public static final int CSV_OUTPUT_TYPE_OUTPUTED = 2;

  /** ���쐬 */
  public static final int BILL_HAKKOU_TYPE_UNKNOWN = -1;

  /** �s�� */
  public static final int BILL_HAKKOU_TYPE_NONE = 0;

  /** �����s */
  public static final int BILL_HAKKOU_TYPE_NEVER_PRINT = 1;

  /** ���s�ς� */
  public static final int BILL_HAKKOU_TYPE_PRINTED = 2;

  /** �ۗ� */
  public static final int BILL_HAKKOU_TYPE_RESERVE = 3;

  /** �쐬���E������(1��) */
  public static final int BILL_PRINT_CREATE_AND_TEST = 1;

  /** �쐬��(1��)�E������(1��) */
  public static final int BILL_PRINT_CREATE_OR_TEST = 2;

  /** �쐬���̂� */
  public static final int BILL_PRINT_ONLY_CREATE = 3;

  /** �������̂� */
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
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoPrintParameter() {
  }

  /**
   * CSV�o�͓�����Ԃ��܂��B
   * @return CSV�o�͓���
   */
  public Date getCsvOutputTime() {
    return csvOutputTime;
  }

  /**
   * CSV�o�͏�Ԃ�Ԃ��܂��B
   * @return CSV�o�͏��
   */
  public int getCsvOutputType() {
    return csvOutputType;
  }

  /**
   * ���������s��Ԃ�Ԃ��܂��B
   * @return ���������s���
   */
  public int getHakkouType() {
    return hakkouType;
  }

  //2006/02/12[Tozo Tanaka] : add begin
  /**
   * CSV�o�͑Ώۂł��邩��Ԃ��܂��B
   * @return CSV�o�͑Ώۂł��邩
   */
  public boolean isCsvTarget() {
    return csvTarget;
  }
  //2006/02/12[Tozo Tanaka] : add end

  /**
   * CSV�o�͍ς݂ł��邩��Ԃ��܂��B
   * @return CSV�o�͍ς݂ł��邩
   */
  public boolean isCsvSubmited() {
    return csvSubmited;
  }

  /**
   * ���ۑ��ł��邩��Ԃ��܂��B
   * @return ���ۑ��ł��邩
   */
  public boolean isNeverSaved() {
    return neverSaved;
  }

  /**
   * ���݂̏������[�h��Ԃ��܂��B
   * @return ���݂̏������[�h
   */
  public String getNowMode() {
    return nowMode;
  }

  /**
   * ���݂̏������[�h��ݒ肵�܂��B
   * @param nowMode ���݂̏������[�h
   */
  public void setNowMode(String nowMode) {
    this.nowMode = nowMode;
  }

  /**
   * ���ۑ��ł��邩��ݒ肵�܂��B
   * @param neverSaved ���ۑ��ł��邩
   */
  public void setNeverSaved(boolean neverSaved) {
    this.neverSaved = neverSaved;
  }

  /**
   * ���������s��Ԃ�ݒ肵�܂��B
   * @param hakkouType ���������s���
   */
  public void setHakkouType(int hakkouType) {
    this.hakkouType = hakkouType;
  }

  //2006/02/12[Tozo Tanaka] : add end
  /**
   * CSV�o�͑Ώۂł��邩��ݒ肵�܂��B
   * @param csvTarget CSV�o�͑Ώۂł��邩
   */
  public void setCsvTarget(boolean csvTarget) {
    this.csvTarget = csvTarget;
  }
  //2006/02/12[Tozo Tanaka] : add begin

  /**
   * CSV�o�͍ς݂ł��邩��ݒ肵�܂��B
   * @param csvSubmited CSV�o�͑Ώۂł��邩
   */
  public void setCsvSubmited(boolean csvSubmited) {
    this.csvSubmited = csvSubmited;
  }

  /**
   * CSV�o�͏�Ԃł��邩��ݒ肵�܂��B
   * @param csvOutputType CSV�o�͏�Ԃł��邩
   */
  public void setCsvOutputType(int csvOutputType) {
    this.csvOutputType = csvOutputType;
  }

  /**
   * CSV�o�͓����ł��邩��ݒ肵�܂��B
   * @param csvOutputTime CSV�o�͓����ł��邩
   */
  public void setCsvOutputTime(Date csvOutputTime) {
    this.csvOutputTime = csvOutputTime;
  }

  /**
   * �o�͋敪���ϓ���������Ԃ��܂��B
   * @return �o�͋敪���ϓ�������
   */
  public boolean isTypeChanged() {
    return typeChanged;
  }

  /**
   * �o�͋敪���ϓ���������ݒ肵�܂��B
   * @param typeChanged �o�͋敪���ϓ�������
   */
  public void setTypeChanged(boolean typeChanged) {
    this.typeChanged = typeChanged;
  }

  /**
   * �ŐV�ȊO�̈ӌ����ł��邩��Ԃ��܂��B
   * @return �ŐV�ȊO�̈ӌ����ł��邩
   */
  public boolean isNotMostNewDocument() {
    return notMostNewDocument;
  }

  /**
   * �ŐV�ȊO�̈ӌ����ł��邩��ݒ肵�܂��B
   * @param notMostNewDocument �ŐV�ȊO�̈ӌ����ł��邩
   */
  public void setNotMostNewDocument(boolean notMostNewDocument) {
    this.notMostNewDocument = notMostNewDocument;
  }

}
