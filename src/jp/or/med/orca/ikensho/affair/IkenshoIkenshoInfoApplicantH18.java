package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.component.ACIntegerTextField;



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
// [ID:0000794][Satoshi Tokusari] 2015/3 edit-Start 意見書様式変更対応
//    getAgreeGroup().setText("主治医として、本意見書が介護サービス計画作成に利用されることに");
    getAgreeGroup().setText("主治医として、本意見書が介護サービス計画作成等に利用されることに");
// [ID:0000794][Satoshi Tokusari] 2015/3 edit-End

  }
  

}
