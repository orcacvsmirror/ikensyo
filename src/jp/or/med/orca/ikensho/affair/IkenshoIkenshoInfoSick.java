package jp.or.med.orca.ikensho.affair;



/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSick
    extends IkenshoDocumentAffairSick {

  /**
   * �R���X�g���N�^�ł��B
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
    getSickNames1().setText("�P�D");
    getSickNames2().setText("�Q�D");
    getSickNames3().setText("�R�D");
    getTitle().setText("�P�D���a�Ɋւ���ӌ�");
    getProgressGroup().setText("��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
    getSickNameGroup().setText("�f�f���i���莾�a�܂��͏�Q�̒��ڂ̌����ƂȂ��Ă��鏝�a���ɂ��Ă͂P�D�ɋL���j�y�є��ǔN����");
    getStableAndOutlook().setVisible(true);
  }

}

