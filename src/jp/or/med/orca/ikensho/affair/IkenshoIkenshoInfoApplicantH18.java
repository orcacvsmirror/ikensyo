package jp.or.med.orca.ikensho.affair;



/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoApplicantH18 extends IkenshoIkenshoInfoApplicant {
  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoApplicantH18() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    getAgreeGroup().setText("主治医として、本意見書が介護サービス計画作成に利用されることに");
  }

}
