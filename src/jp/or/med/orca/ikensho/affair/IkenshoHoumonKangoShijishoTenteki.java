package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoTenteki
    extends IkenshoTabbableChildAffairContainer {
  private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
  private ACGroupBox group = new ACGroupBox();
  private IkenshoHoumonKangoShijishoInstructContainer tenteki = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer tokki = new
      IkenshoHoumonKangoShijishoInstructContainer();

  public IkenshoHoumonKangoShijishoTenteki() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    tenteki.setShowSelectMnemonic('I');
    tenteki.setShowSelectText("�I��(I)");
    tenteki.setCaption("�ݑ�ҖK��_�H���˂Ɋւ���w��");
    tenteki.setTextBindPath("TENTEKI_SIJI");
    tenteki.setTitle("�ݑ�ҖK��_�H���˂Ɋւ���w���i�S����200����/4�s�ȓ��j");
    tenteki.setCheckVisible(false);
    tenteki.setCode(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA);
    tenteki.setMaxLength(200);
//    tenteki.setUseMaxRows(true);
    tenteki.setRows(4);
    tenteki.setMaxRows(tenteki.getRows());
    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
    tokki.setMaxLength(200);
//    tokki.setUseMaxRows(true);
    tokki.setRows(4);
    tokki.setMaxRows(tokki.getRows());
    tokki.setShowSelectMnemonic('J');
    tokki.setShowSelectText("�I��(J)");
    tokki.setCaption("���L���ׂ����ӎ���");
    tokki.setTextBindPath("SIJI_TOKKI");
    tokki.setTitle("���L���ׂ����ӎ����i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��Ă��������B�j");
    tokki.setCheckVisible(false);
    title.setText("�K��_�H���ˁE���L���ׂ����ӎ���");
    group.setLayout(new VRLayout());
    group.add(tenteki, VRLayout.NORTH);
    group.add(tokki, VRLayout.NORTH);
    this.add(title, VRLayout.NORTH);
    this.add(group, VRLayout.CLIENT);
  }

}
