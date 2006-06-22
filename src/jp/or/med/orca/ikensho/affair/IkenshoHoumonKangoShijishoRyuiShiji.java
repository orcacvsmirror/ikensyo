package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoRyuiShiji
    extends IkenshoTabbableChildAffairContainer {
  private IkenshoDocumentTabTitleLabel ryuuiShijiTitle = new
      IkenshoDocumentTabTitleLabel();
  private ACGroupBox ryuiShijiGrp = new ACGroupBox();

  private IkenshoHoumonKangoShijishoInstructContainer seikatsuShidouRyuijikou = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer rehabilitation = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer jyokusou = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer kikiSousaShien = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer other = new
      IkenshoHoumonKangoShijishoInstructContainer();
//  private IkenshoHoumonKangoShijishoInstructContainer tokki = new
//      IkenshoHoumonKangoShijishoInstructContainer();

  public IkenshoHoumonKangoShijishoRyuiShiji() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    //���ӎ�������юw������
    this.setLayout(new VRLayout());
    seikatsuShidouRyuijikou.setCode(IkenshoCommon.
                                    TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU);
    seikatsuShidouRyuijikou.setShowSelectMnemonic('C');
    seikatsuShidouRyuijikou.setShowSelectText("�I��(C)");
    seikatsuShidouRyuijikou.setCaption("�×{�����w����̗��ӎ���");
    seikatsuShidouRyuijikou.setTextBindPath("RSS_RYUIJIKOU");
    seikatsuShidouRyuijikou.setTitle(
        " I  �×{�����w����̗��ӎ����i�S����120����/3�s�ȓ��j");
    seikatsuShidouRyuijikou.setCheckVisible(false);
    rehabilitation.setCheckBindPath("REHA_SIJI_UMU");
    rehabilitation.setCheckText("�P�D���n�r���e�[�V����");
    rehabilitation.setCode(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION);
    rehabilitation.setShowSelectMnemonic('D');
    rehabilitation.setShowSelectText("�I��(D)");
    rehabilitation.setCaption("���n�r���e�[�V����");
    rehabilitation.setTextBindPath("REHA_SIJI");
    rehabilitation.setTitle("II  ");
    jyokusou.setCheckBindPath("JOKUSOU_SIJI_UMU");
    jyokusou.setCheckText("�Q�D��ጂ̏��u��");
    jyokusou.setCode(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU);
    jyokusou.setShowSelectMnemonic('E');
    jyokusou.setShowSelectText("�I��(E)");
    jyokusou.setCaption("��ጂ̏��u��");
    jyokusou.setTextBindPath("JOKUSOU_SIJI");
    jyokusou.setTitle("�@");
    kikiSousaShien.setCheckBindPath("SOUCHAKU_SIJI_UMU");
    kikiSousaShien.setCheckText("�R�D�����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�");
    kikiSousaShien.setCode(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO);
    kikiSousaShien.setShowSelectMnemonic('G');
    kikiSousaShien.setShowSelectText("�I��(G)");
    kikiSousaShien.setCaption("�����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�");
    kikiSousaShien.setText("");
    kikiSousaShien.setTextBindPath("SOUCHAKU_SIJI");
    kikiSousaShien.setTitle("�@");
    other.setCheckBindPath("RYUI_SIJI_UMU");
    other.setCheckText("�S�D���̑�");
    other.setCode(IkenshoCommon.TEIKEI_HOUMON_SONOTA);
    other.setShowSelectMnemonic('H');
    other.setShowSelectText("�I��(H)");
    other.setCaption("���̑�");
    other.setTextBindPath("RYUI_SIJI");
    other.setTitle("�@");
//    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
//    tokki.setMaxLength(200);
//    tokki.setMaxRows(4);
//    tokki.setRows(5);
//    tokki.setShowSelectMnemonic('I');
//    tokki.setShowSelectText("�I��(I)");
//    tokki.setCaption("���L���ׂ����ӎ���");
//    tokki.setTextBindPath("SIJI_TOKKI");
//    tokki.setTitle("���L���ׂ����ӎ����i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��Ă��������B�j");
//    tokki.setCheckVisible(false);
    ryuuiShijiTitle.setText("���ӎ����y�юw������");
    ryuiShijiGrp.setLayout(new VRLayout());
    ryuiShijiGrp.add(seikatsuShidouRyuijikou, VRLayout.NORTH);
    ryuiShijiGrp.add(rehabilitation, VRLayout.NORTH);
    ryuiShijiGrp.add(jyokusou, VRLayout.NORTH);
    ryuiShijiGrp.add(kikiSousaShien, VRLayout.NORTH);
    ryuiShijiGrp.add(other, VRLayout.NORTH);
//    ryuiShijiGrp.add(tokki, VRLayout.NORTH);
    this.add(ryuuiShijiTitle, VRLayout.NORTH);
    this.add(ryuiShijiGrp, VRLayout.CLIENT);
  }
}
