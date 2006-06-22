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
    //留意事項および指示事項
    this.setLayout(new VRLayout());
    seikatsuShidouRyuijikou.setCode(IkenshoCommon.
                                    TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU);
    seikatsuShidouRyuijikou.setShowSelectMnemonic('C');
    seikatsuShidouRyuijikou.setShowSelectText("選択(C)");
    seikatsuShidouRyuijikou.setCaption("療養生活指導上の留意事項");
    seikatsuShidouRyuijikou.setTextBindPath("RSS_RYUIJIKOU");
    seikatsuShidouRyuijikou.setTitle(
        " I  療養生活指導上の留意事項（全項目120文字/3行以内）");
    seikatsuShidouRyuijikou.setCheckVisible(false);
    rehabilitation.setCheckBindPath("REHA_SIJI_UMU");
    rehabilitation.setCheckText("１．リハビリテーション");
    rehabilitation.setCode(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION);
    rehabilitation.setShowSelectMnemonic('D');
    rehabilitation.setShowSelectText("選択(D)");
    rehabilitation.setCaption("リハビリテーション");
    rehabilitation.setTextBindPath("REHA_SIJI");
    rehabilitation.setTitle("II  ");
    jyokusou.setCheckBindPath("JOKUSOU_SIJI_UMU");
    jyokusou.setCheckText("２．褥瘡の処置等");
    jyokusou.setCode(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU);
    jyokusou.setShowSelectMnemonic('E');
    jyokusou.setShowSelectText("選択(E)");
    jyokusou.setCaption("褥瘡の処置等");
    jyokusou.setTextBindPath("JOKUSOU_SIJI");
    jyokusou.setTitle("　");
    kikiSousaShien.setCheckBindPath("SOUCHAKU_SIJI_UMU");
    kikiSousaShien.setCheckText("３．装着・使用医療機器等の操作援助・管理");
    kikiSousaShien.setCode(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO);
    kikiSousaShien.setShowSelectMnemonic('G');
    kikiSousaShien.setShowSelectText("選択(G)");
    kikiSousaShien.setCaption("装着・使用医療機器等の操作援助・管理");
    kikiSousaShien.setText("");
    kikiSousaShien.setTextBindPath("SOUCHAKU_SIJI");
    kikiSousaShien.setTitle("　");
    other.setCheckBindPath("RYUI_SIJI_UMU");
    other.setCheckText("４．その他");
    other.setCode(IkenshoCommon.TEIKEI_HOUMON_SONOTA);
    other.setShowSelectMnemonic('H');
    other.setShowSelectText("選択(H)");
    other.setCaption("その他");
    other.setTextBindPath("RYUI_SIJI");
    other.setTitle("　");
//    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
//    tokki.setMaxLength(200);
//    tokki.setMaxRows(4);
//    tokki.setRows(5);
//    tokki.setShowSelectMnemonic('I');
//    tokki.setShowSelectText("選択(I)");
//    tokki.setCaption("特記すべき留意事項");
//    tokki.setTextBindPath("SIJI_TOKKI");
//    tokki.setTitle("特記すべき留意事項（注：薬の相互作用・副作用についての留意点、薬物アレルギーの既往等あれば記載してください。）");
//    tokki.setCheckVisible(false);
    ryuuiShijiTitle.setText("留意事項及び指示事項");
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
