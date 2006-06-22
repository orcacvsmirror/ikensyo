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
    tenteki.setShowSelectText("選択(I)");
    tenteki.setCaption("在宅患者訪問点滴注射に関する指示");
    tenteki.setTextBindPath("TENTEKI_SIJI");
    tenteki.setTitle("在宅患者訪問点滴注射に関する指示（全項目200文字/4行以内）");
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
    tokki.setShowSelectText("選択(J)");
    tokki.setCaption("特記すべき留意事項");
    tokki.setTextBindPath("SIJI_TOKKI");
    tokki.setTitle("特記すべき留意事項（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載してください。）");
    tokki.setCheckVisible(false);
    title.setText("訪問点滴注射・特記すべき留意事項");
    group.setLayout(new VRLayout());
    group.add(tenteki, VRLayout.NORTH);
    group.add(tokki, VRLayout.NORTH);
    this.add(title, VRLayout.NORTH);
    this.add(group, VRLayout.CLIENT);
  }

}
