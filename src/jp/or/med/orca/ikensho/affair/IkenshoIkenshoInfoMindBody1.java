package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMindBody1
    extends IkenshoDocumentAffairMindBody1 {

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoMindBody1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    getSyougaiJiritsuHelp().setFollowHideComponents(new JComponent[] {
        getRikaiKiokuGroup(), getMondaiGroup(), getShinkeiGroup()});
    getChihouJiritsuHelp().setFollowHideComponents(new JComponent[] {
        getRikaiKiokuGroup(), getMondaiGroup(), getShinkeiGroup()});
  }

  private void jbInit() throws Exception {
      getMindBody1SyougaiJiritsu().setModel(new VRListModelAdapter(new
              VRArrayList(Arrays.asList(new
                                        String[] {"����", "�i�P", "�i�Q", "�`�P", "�`�Q", "�a�P",
                                        "�a�Q", "�b�P", "�b�Q"}))));
      getMindBody1ChihouJiritsu().setModel(new VRListModelAdapter(new
              VRArrayList(Arrays.asList(new
                                        String[] {"����", "I", "II��", "II��", "III��", "III��",
                                        "IV", "�l"}))));
    getRikaiKiokuGroup().setVisible(true);
    getJiritsuGroup().setVisible(true);
    getJiritsuGroup().setText("���퐶���̎����x���ɂ���");
    getTitle().setVisible(true);
    getMondaiGroup().setVisible(true);
    getShinkeiGroup().setVisible(true);
  }

}
