package jp.or.med.orca.ikensho.affair;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMentionH18 extends IkenshoIkenshoInfoMention {
  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoMentionH18() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    getTitle().setText("５．特記すべき事項");
    getTokkiAbstraction1().setText("要介護認定及び介護サービス計画作成時に必要な医学的なご意見等を記載して下さい。なお、専門医等に別途意見を求めた場合はその内容、結果も記載して下さい。");
//    getTokkiAbstraction2().setText("");
  }

}
