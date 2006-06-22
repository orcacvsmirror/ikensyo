package jp.or.med.orca.ikensho.affair;



/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoSick extends IkenshoDocumentAffairSick {
  public IkenshoHoumonKangoShijishoSick() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    getStableAndOutlook().setVisible(false);
    getProgressGroup().setText("傷病の経過及び投薬内容を含む治療内容");
    getTitle().setText("現在の状態");
    getSickNames1().setText("");
    getSickNames2().setText("");
    getSickNames3().setText("");
    getSickNameGroup().setText("主たる傷病名（「発症年月日」は印刷されません」）");
  }

}
