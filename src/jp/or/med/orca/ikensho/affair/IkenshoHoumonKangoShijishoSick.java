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
    getProgressGroup().setText("���a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
    getTitle().setText("���݂̏��");
    getSickNames1().setText("");
    getSickNames2().setText("");
    getSickNames3().setText("");
    getSickNameGroup().setText("�傽�鏝�a���i�u���ǔN�����v�͈������܂���v�j");
  }

}
