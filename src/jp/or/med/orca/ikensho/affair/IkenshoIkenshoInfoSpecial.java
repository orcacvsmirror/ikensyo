package jp.or.med.orca.ikensho.affair;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSpecial extends IkenshoDocumentAffairSpecial {

  /**
   * �R���X�g���N�^�ł��B
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
    specialTitle.setText("�Q�D���ʂȈ�Ái�ߋ�14���Ԉȓ��Ɏ󂯂���Â̂��ׂĂɃ`�F�b�N�j");
    specialMessage1.setVisible(true);
    specialMessage2.setVisible(true);
 }

}
