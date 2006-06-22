package jp.or.med.orca.ikensho.affair;



/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSick
    extends IkenshoDocumentAffairSick {

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoSick() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    getSickNames1().setText("１．");
    getSickNames2().setText("２．");
    getSickNames3().setText("３．");
    getTitle().setText("１．傷病に関する意見");
    getProgressGroup().setText("障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容");
    getSickNameGroup().setText("診断名（特定疾病または障害の直接の原因となっている傷病名については１．に記入）及び発症年月日");
    getStableAndOutlook().setVisible(true);
  }

}

