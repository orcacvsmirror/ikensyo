package jp.or.med.orca.ikensho.affair;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACBitCheckBoxGroup;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoApplicant
    extends IkenshoDocumentAffairApplicant {

  private ACGroupBox applicantAgreeGroup = new ACGroupBox();
  private VRPanel applicantSub = new VRPanel();
  private ACGroupBox createCountGroup = new ACGroupBox();
  private ACGroupBox otherDepartmentGroup = new ACGroupBox();

  private ACComboBox otherDepartmentOther = new ACComboBox();
  private ACLabelContainer otherDepartmentUseCaption = new ACLabelContainer();
  private VRLabel otherDepartmentOtherTailMessage = new VRLabel();
  private ACLabelContainer createCountCaption = new ACLabelContainer();
  private ACClearableRadioButtonGroup otherDepartment = new ACClearableRadioButtonGroup();
  private VRLayout otherDepartmentLayout = new VRLayout();
  private ACClearableRadioButtonGroup applicantAgree = new ACClearableRadioButtonGroup();
  private IkenshoOtherDepartmentUseCheckBoxGroup otherDepartmentUse = new
      IkenshoOtherDepartmentUseCheckBoxGroup();
  private ACLabelContainer lastExaminationDateCaption = new ACLabelContainer();
  private ACGroupBox writeDateGroup = new ACGroupBox();
  private ACIntegerCheckBox otherDepartmentOtherUse = new
      ACIntegerCheckBox();
  private VRPanel otherDepartmentOtherPanel = new VRPanel();
  private ACLabelContainer otherDepartmentCaption = new ACLabelContainer();
  private ACGroupBox lastExaminationDateGroup = new ACGroupBox();
  private ACClearableRadioButtonGroup createCount = new ACClearableRadioButtonGroup();
  private ACParentHesesPanelContainer otherDepartmentOthers = new
      ACParentHesesPanelContainer();
  private IkenshoEraDateTextField lastExaminationDate = new IkenshoEraDateTextField();
  private VRLayout applicantSubLayout = new VRLayout();
  private FlowLayout otherDepartmentOtherPanelLayout = new FlowLayout();
  private VRLayout otherDepartmentUseCaptionLayout = new VRLayout();

  /**
   * 医師の同意グループを返します。
   * @return 医師の同意グループ
   */
  protected ACGroupBox getAgreeGroup(){
    return applicantAgreeGroup;
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    IkenshoCommon.setMSinryouka(dbm, otherDepartmentOther,
                                IkenshoCommon.DEPARTMENT_OTHER);

  }


  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoApplicant() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    otherDepartmentOtherUse.addItemListener(new
                                            ACFollowDisabledItemListener(new
        JComponent[] {otherDepartmentOther, otherDepartmentOthers, }));

    otherDepartment.addListSelectionListener(new
        ACFollowDisableSelectionListener(new JComponent[] {
                                              otherDepartmentUseCaption,
                                              otherDepartmentOthers,
                                              otherDepartmentOther,
                                              otherDepartmentOtherUse, }));
    otherDepartment.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
          return;
        }
        boolean used = e.getFirstIndex() == 0;
        if (used) {
          boolean selected = otherDepartmentOtherUse.isSelected();
          otherDepartmentOther.setEnabled(selected);
          otherDepartmentOthers.setEnabled(selected);
        }
        int end = otherDepartmentUse.getCheckBoxCount();
        for (int i = 0; i < end; i++) {
          otherDepartmentUse.getCheckBox(i).setEnabled(used);
        }
      }
    });

    int end = otherDepartmentUse.getCheckBoxCount();
    for (int i = 0; i < end; i++) {
      otherDepartmentUse.getCheckBox(i).setEnabled(false);
    }

  }

  private void jbInit() throws Exception {
    //Model
    otherDepartmentUse.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new String[] {"内科", "精神科", "外科", "整形外科",
                                  "脳神経外科", "皮膚科", "泌尿器科", "婦人科", "眼科", "耳鼻咽喉科",
                                  "リハビリテーション科", "歯科", }))));
    applicantAgreeGroup.setText("本意見書が介護サービス計画作成に利用されることに");
    otherDepartment.setBindPath("TAKA_FLAG");
    otherDepartmentOtherUse.setBindPath("TAKA_OTHER_FLAG");
    applicantEasts.add(applicantAgreeGroup, null);
    applicantAgreeGroup.add(applicantAgree, null);

    applicantSub.setLayout(applicantSubLayout);
    applicantSub.add(lastExaminationDateGroup, VRLayout.CLIENT);
    applicantSub.add(writeDateGroup, VRLayout.CLIENT);
    createCountGroup.setLayout(new VRLayout());
    createCountGroup.add(createCountCaption, VRLayout.FLOW);
    createCountCaption.add(createCount, null);

    otherDepartmentGroup.setLayout(otherDepartmentLayout);
    otherDepartmentGroup.add(otherDepartmentCaption,
                             VRLayout.NORTH);
    otherDepartmentGroup.add(otherDepartmentUseCaption,
                             VRLayout.CLIENT);
//    otherDepartmentGroup.add(otherDepartmentUseCaption,
//                             VRLayout.NORTH);
//    otherDepartmentGroup.add(otherDepartmentCaption,
//                             VRLayout.FLOW_INSETLINE_RETURN);
//    otherDepartmentGroup.add(otherDepartmentUseCaption,
//                             VRLayout.FLOW_INSETLINE_RETURN);
//    otherDepartmentGroup.add(otherDepartmentCaption,
//                             BorderLayout.NORTH);
//    otherDepartmentGroup.add(otherDepartmentUseCaption,
//                             BorderLayout.CENTER);

    this.add(applicantSub, VRLayout.NORTH);
    this.add(createCountGroup, VRLayout.NORTH);
//    this.add(otherDepartmentGroup, VRLayout.NORTH);
    this.add(otherDepartmentGroup, VRLayout.CLIENT);


    createCount.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"初回", "２回目以上"}))));
    applicantAgree.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"同意する", "同意しない"}))));
    otherDepartment.setModel(new VRListModelAdapter(new
      VRArrayList(Arrays.asList(new String[] {"有", "無"}))));

    otherDepartmentOtherPanelLayout.setHgap(0);
    otherDepartmentOtherPanel.setLayout(otherDepartmentOtherPanelLayout);
    applicantSubLayout.setAutoWrap(false);
    applicantSubLayout.setFitHLast(true);
    otherDepartmentUseCaption.setEnabled(false);
    otherDepartmentUseCaption.setText("（有の場合）→");
    otherDepartmentUseCaption.setLayout(otherDepartmentUseCaptionLayout);
    otherDepartmentOther.setMaxLength(6);
    otherDepartmentOther.setBindPath("TAKA_OTHER");
    otherDepartmentOther.setEnabled(false);
    otherDepartmentOther.setIMEMode(InputSubset.KANJI);
    otherDepartmentOtherTailMessage.setText("（6文字以内）");
    otherDepartmentOtherTailMessage.setForeground(IkenshoConstants.
                                                  COLOR_MESSAGE_TEXT_FOREGROUND);
    createCountCaption.setText("作成回数");
    otherDepartment.setUseClearButton(false);
    otherDepartment.setSelectedIndex(2);
    writeDate.setBindPath("KINYU_DT");
    writeDate.setAgeVisible(false);
    writeDateCaption.setText("記入日：");
//    otherDepartmentLayout.setFitHLast(true);
    otherDepartmentLayout.setHgap(0);
    applicantTitle.setText("申請者");
    applicantAgree.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"同意する", "同意しない"}))));
    applicantAgree.setBindPath("DR_CONSENT");
    applicantAgree.setUseClearButton(false);
    lastExaminationDateCaption.setText("最終診察日：");
    writeDateGroup.setLayout(new VRLayout());
    otherDepartmentOtherUse.setEnabled(false);
    otherDepartmentOtherUse.setText("その他");
    otherDepartmentCaption.setText("他科受診の有無");
    lastExaminationDateGroup.setLayout(new VRLayout());
    createCount.setModel(new VRListModelAdapter(new
                                                VRArrayList(Arrays.asList(new
        String[] {"初回", "２回目以上"}))));
    createCount.setUseClearButton(false);
    createCount.setBindPath("IKN_CREATE_CNT");
    createCount.setSelectedIndex(1);
    lastExaminationDate.setBindPath("LASTDAY");
    lastExaminationDate.setAgeVisible(false);
    otherDepartmentUse.setBindPath("TAKA");
    otherDepartmentOthers.setEnabled(false);
    lastExaminationDateGroup.add(lastExaminationDateCaption,
                                 VRLayout.FLOW_INSETLINE);
    lastExaminationDateCaption.add(lastExaminationDate, null);
    writeDateGroup.add(writeDateCaption, VRLayout.FLOW_INSETLINE);
    writeDateCaption.add(writeDate, null);
    otherDepartmentCaption.add(otherDepartment, null);
    otherDepartmentUseCaption.add(otherDepartmentUse, VRLayout.NORTH);
    otherDepartmentOtherPanel.add(otherDepartmentOtherUse, null);
    otherDepartmentOtherPanel.add(otherDepartmentOthers, null);
    otherDepartmentOtherPanel.add(otherDepartmentOtherTailMessage, null);
    otherDepartmentOthers.add(otherDepartmentOther, null);
    otherDepartmentUseCaption.add(otherDepartmentOtherPanel,
                                  VRLayout.FLOW_INSETLINE_RETURN);
  }

  public boolean noControlError() throws Exception {

    //エラーチェック
    if(!super.noControlError()){
      return false;
    }

    switch (writeDate.getInputStatus()) {
      case IkenshoEraDateTextField.STATE_VALID:
        break;
      case IkenshoEraDateTextField.STATE_FUTURE:
        ACMessageBox.showExclamation("未来の日付です。");
        writeDate.requestChildFocus();
        return false;
      default:
        ACMessageBox.showExclamation("日付に誤りがあります。");
        writeDate.requestChildFocus();
        return false;
    }


    switch (lastExaminationDate.getInputStatus()) {
      case IkenshoEraDateTextField.STATE_EMPTY:
        break;
      case IkenshoEraDateTextField.STATE_VALID:
        //有効ならば記入日との前後チェック
        if (lastExaminationDate.getDate().compareTo(writeDate.getDate())>0) {
          //記入日のほうが古い
          ACMessageBox.show("最終診察日は記入日以前でなければなりません。");
          lastExaminationDate.requestChildFocus();
          return false;
        }
        break;
      case IkenshoEraDateTextField.STATE_FUTURE:
        ACMessageBox.showExclamation("未来の日付です。");
        lastExaminationDate.requestChildFocus();
        return false;
      default:
        ACMessageBox.show("日付に誤りがあります。");
        lastExaminationDate.requestChildFocus();
        return false;
    }

    if (otherDepartment.getSelectedIndex() == 1) {
      //他科受診あり
      if (otherDepartmentOtherUse.isSelected()) {
        if (IkenshoCommon.isNullText(otherDepartmentOther.getEditor().getItem())) {
          ACMessageBox.showExclamation("「他科受診（その他）」で未記入があります。");
          otherDepartmentOtherUse.requestFocus();
          return false;
        }
      }else{
        if (otherDepartmentUse.getSelectedBits() == 0) {
          ACMessageBox.showExclamation("「他科受診」で未記入があります。");
          otherDepartment.requestChildFocus();
          return false;
        }
      }

    }

    return true;
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    otherDepartmentOtherUse.setSelected(!IkenshoCommon.isNullText(
        otherDepartmentOther.getEditor().getItem()));

    if ( (otherDepartmentUse.getSelectedBits() > 0) ||
        (otherDepartmentOtherUse.isSelected())) {
      otherDepartment.setSelectedIndex(1);
    }else{
      otherDepartment.setSelectedIndex(2);
    }
  }


  /** TODO <HEAD_IKENSYO> */
  private class IkenshoOtherDepartmentUseCheckBoxGroup
      extends ACBitCheckBoxGroup {
    public IkenshoOtherDepartmentUseCheckBoxGroup() {
      super();
      setLayout(new VRLayout());
    }

    protected void addItem(JCheckBox item) {
      if (this.getComponentCount() + 1 == getModel().getSize()) {
        this.add(item, VRLayout.FLOW_RETURN);
      }
      else {
        this.add(item, VRLayout.FLOW);
      }
    }
  }

  /**
   * 初診を選択しているかを返します。
   * @return 初診を選択しているか
   */
  public boolean isFirstCreate(){
    return createCount.getSelectedIndex()==1;
  }
}
