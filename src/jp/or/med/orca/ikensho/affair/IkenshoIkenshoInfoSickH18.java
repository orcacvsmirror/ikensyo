package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSickH18 extends IkenshoIkenshoInfoSick {
  private ACLabelContainer notStableStateContainer;
  private ACComboBox noteStableState;

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);
    applyPoolTeikeibun(noteStableState, IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME);
  }

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoSickH18() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 不安定時の具体的な状況コンテナを返します。
   * @return 不安定時の具体的な状況コンテナ
   */
  protected ACLabelContainer getNotStableStateContainer() {
    if(notStableStateContainer==null){
      notStableStateContainer = new ACLabelContainer();
    }
    return notStableStateContainer;
  }

  /**
   * 不安定時の具体的な状況を返します。
   * @return 不安定時の具体的な状況
   */
  protected ACComboBox getNotStableState() {
    if(noteStableState==null){
      noteStableState = new ACComboBox();
    }
    return noteStableState;
  }

  protected void addSickStableGroup(){
    super.addSickStableGroup();
    getSickStableGroup().add(getNotStableStateContainer(), VRLayout.FLOW);
  }

  protected void addSickStableAndOutlook(){
    getStableAndOutlook().add(getSickStableGroup(), VRLayout.CLIENT);
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    if(getSickStable().getSelectedIndex()!=2){
      getNotStableState().setText("");
    }
  }

  private void jbInit() throws Exception {
//    getOutlookGroup().setVisible(false);
//    getOutlook().setBindPath("");
    getSickStable().addListSelectionListener(new
        ACFollowDisableSelectionListener(new JComponent[] {
                                         getNotStableState()}
                                         , 1));

    getNotStableStateContainer().add(getNotStableState());
    getNotStableStateContainer().setText("具体的な状況");
    //不安定における具体的な状況のバインドパス
    getNotStableState().setBindPath("INSECURE_CONDITION");

    getSickProgresss().setText("傷病または" + IkenshoConstants.LINE_SEPARATOR +
                               "特定疾病の経過" + IkenshoConstants.LINE_SEPARATOR +
                               "（250文字" + IkenshoConstants.LINE_SEPARATOR +
                               "または5行以内）");

    getNotStableState().setPreferredSize(new Dimension(400,20));
    getNotStableState().setMaxLength(30);
    getNotStableState().setIMEMode(InputSubset.KANJI);
    getSickNameGroup().setText("診断名（特定疾病または生活機能低下の直接の原因となっている傷病名については１．に記入）及び発症年月日");
    getProgressGroup().setText("生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容");
//    getSickDate1().setEraRange(2);
//    getSickDate2().setEraRange(2);
//    getSickDate3().setEraRange(2);
//    getSickDate1().setAllowedUnknown(false);
//    getSickDate2().setAllowedUnknown(false);
//    getSickDate3().setAllowedUnknown(false);
  }

}
