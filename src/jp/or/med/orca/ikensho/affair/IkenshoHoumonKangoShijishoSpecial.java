package jp.or.med.orca.ikensho.affair;




/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoSpecial extends IkenshoDocumentAffairSpecial {
  public IkenshoHoumonKangoShijishoSpecial() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    specialTitle.setText("���ʂȈ��");
    specialMessage1.setVisible(false);
    specialMessage2.setVisible(false);
  }

}
