package jp.or.med.orca.ikensho.affair;





/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoMindBody1 extends IkenshoDocumentAffairMindBody1 {
  /**
   * �R���X�g���N�^�ł��B
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
    getJiritsuGroup().setText("���퐶�������x");
    getTitle().setText("���퐶�������x");
    getTitle().setVisible(true);
    getMondaiGroup().setVisible(false);
    getShinkeiGroup().setVisible(false);
  }
}
