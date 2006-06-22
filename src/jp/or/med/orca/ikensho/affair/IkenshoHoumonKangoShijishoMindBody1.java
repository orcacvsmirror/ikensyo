package jp.or.med.orca.ikensho.affair;





/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoMindBody1 extends IkenshoDocumentAffairMindBody1 {
  /**
   * コンストラクタです。
   */
  public IkenshoHoumonKangoShijishoMindBody1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    getRikaiKiokuGroup().setVisible(false);
    getJiritsuGroup().setVisible(true);
    getJiritsuGroup().setText("日常生活自立度");
    getTitle().setText("日常生活自立度");
    getTitle().setVisible(true);
    getMondaiGroup().setVisible(false);
    getShinkeiGroup().setVisible(false);
  }
}
