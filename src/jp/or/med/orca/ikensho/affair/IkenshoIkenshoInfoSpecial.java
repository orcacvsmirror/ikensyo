package jp.or.med.orca.ikensho.affair;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSpecial extends IkenshoDocumentAffairSpecial {

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoSpecial() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    specialTitle.setText("２．特別な医療（過去14日間以内に受けた医療のすべてにチェック）");
    specialMessage1.setVisible(true);
    specialMessage2.setVisible(true);
 }

}
