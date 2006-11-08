package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoCareStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoUnderlineFormatableLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoCare1
    extends IkenshoTabbableChildAffairContainer {
  private FocusListener totalLengthCheckListener;
  private VRLayout care1GroupLayout = new VRLayout();
  private VRPanel care1Abstractions = new VRPanel();
  private ACLabelContainer care1OtherNames = new ACLabelContainer();
  private VRLabel care1AbstractionHead = new VRLabel();
  private ACGroupBox care1Group = new ACGroupBox();
  private VRLayout care1Layout = new VRLayout();
  private ACIntegerCheckBox care1Other = new ACIntegerCheckBox();
  private IkenshoDocumentTabTitleLabel care1Title = new
      IkenshoDocumentTabTitleLabel();
  private VRLabel care1AbstractionMiddleMessage = new VRLabel();
  private VRLabel care1AbstractionMiddle = new VRLabel();
  private ACLabelContainer care1Others = new ACLabelContainer();
  private IkenshoUnderlineFormatableLabel care1AbstractionTail = new
      IkenshoUnderlineFormatableLabel();
  private ACLabelContainer care1OtherTaisyos = new ACLabelContainer();
  private VRLayout care1OthersLayout = new VRLayout();
  private VRLayout care1AbstractionsLayout = new VRLayout();
  private ACComboBox care1OtherName = new ACComboBox();
  private ACComboBox care1OtherTaisyo = new ACComboBox();
  private IkenshoCareStatusContainer care1Shikkin = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Jyokusou = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Haikai = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Tentou = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Haien = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Chouheisoku = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Ekikan = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1ShinpaiDown = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Pain = new IkenshoCareStatusContainer();
  private IkenshoCareStatusContainer care1Dassui = new IkenshoCareStatusContainer();
  private ACParentHesesPanelContainer care1OtherNameHeses = new
      ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer care1OtherTaisyoHeses = new
      ACParentHesesPanelContainer();

  /**
   * 総文字数チェックリスナを返します。
   * @return 総文字数チェックリスナ
   */
  protected FocusListener getTotalLengthCheckListener(){
    return totalLengthCheckListener;
  }
  /**
   * タイトル表示ラベルを返します。
   * @return タイトル表示ラベル
   */
  protected VRLabel getTitle() {
    return care1Title;
  }

  /**
   * 最大文字数表示ラベルを返します。
   * @return 最大文字数表示ラベル
   */
  protected VRLabel getAbstractionTail() {
    return care1AbstractionTail;
  }

  /**
   * 状態と対処方針グループを返します。
   * @return 状態と対処方針グループ
   */
  protected ACGroupBox getStateAndTaisyoGroup() {
    return care1Group;
  }

  /**
   * 尿失禁コンテナを返します。
   * @return 尿失禁コンテナ
   */
  protected IkenshoCareStatusContainer getShikkin() {
    return care1Shikkin;
  }

  /**
   * 転倒骨折コンテナを返します。
   * @return 転倒骨折コンテナ
   */
  protected IkenshoCareStatusContainer getTentou() {
    return care1Tentou;
  }

  /**
   * 徘徊コンテナを返します。
   * @return 徘徊コンテナ
   */
  protected IkenshoCareStatusContainer getHaikai() {
    return care1Haikai;
  }

  /**
   * 褥瘡コンテナを返します。
   * @return 褥瘡コンテナ
   */
  protected IkenshoCareStatusContainer getJyokusou() {
    return care1Jyokusou;
  }

  /**
   * 嚥下性肺炎コンテナを返します。
   * @return 嚥下性肺炎コンテナ
   */
  protected IkenshoCareStatusContainer getHaien() {
    return care1Haien;
  }

  /**
   * 腸閉塞コンテナを返します。
   * @return 腸閉塞コンテナ
   */
  protected IkenshoCareStatusContainer getChouheisoku() {
    return care1Chouheisoku;
  }

  /**
   * 易感染性コンテナを返します。
   * @return 易感染性コンテナ
   */
  protected IkenshoCareStatusContainer getEkikan() {
    return care1Ekikan;
  }

  /**
   * 心肺機能の低下コンテナを返します。
   * @return 心肺機能の低下コンテナ
   */
  protected IkenshoCareStatusContainer getShinpaiDown() {
    return care1ShinpaiDown;
  }

  /**
   * 痛みコンテナを返します。
   * @return 痛みコンテナ
   */
  protected IkenshoCareStatusContainer getPain() {
    return care1Pain;
  }

  /**
   * 脱水コンテナを返します。
   * @return 脱水コンテナ
   */
  protected IkenshoCareStatusContainer getDassui() {
    return care1Dassui;
  }


  /**
   * その他コンテナを返します。
   * @return その他コンテナ
   */
  protected ACLabelContainer getOthers() {
    return care1Others;
  }

  /**
   * その他を返します。
   * @return その他
   */
  protected ACCheckBox getOther(){
    return care1Other;
  }

  /**
   * その他-対処法を返します。
   * @return その他-対処法
   */
  protected ACComboBox getOtherTaisyo(){
    return care1OtherTaisyo;
  }

  /**
   * overrideして状態と対処方針の追加順序を定義します。
   */
  protected void addStateTaisyos() {
    care1Group.add(care1Shikkin, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Tentou, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Haikai, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Jyokusou, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Haien, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Chouheisoku, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Ekikan, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1ShinpaiDown, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Pain, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Dassui, VRLayout.FLOW_INSETLINE_RETURN);
    care1Group.add(care1Others, VRLayout.FLOW_INSETLINE_RETURN);

  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    applyPoolTeikeibun(care1Shikkin.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME);
    applyPoolTeikeibun(care1Tentou.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME);
    applyPoolTeikeibun(care1Haikai.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME);
    applyPoolTeikeibun(care1Jyokusou.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME);
    applyPoolTeikeibun(care1Haien.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_PNEUMONIA_NAME);
    applyPoolTeikeibun(care1Chouheisoku.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_INTESTINES_NAME);
    applyPoolTeikeibun(care1Ekikan.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME);
    applyPoolTeikeibun(care1ShinpaiDown.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME);
    applyPoolTeikeibun(care1Pain.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_PAIN_NAME);
    applyPoolTeikeibun(care1Dassui.getValueCombo(), IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME);
    applyPoolTeikeibun(care1OtherTaisyo, IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME);
    applyPoolTeikeibun(care1OtherName, IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME);


  }

  public IkenshoIkenshoInfoCare1() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    care1Other.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED) {
          select = true;
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED) {
          select = false;
        }
        else {
          return;
        }
        care1OtherName.setEnabled(select);
        care1OtherTaisyo.setEnabled(select);
        care1OtherNameHeses.setEnabled(select);
        care1OtherTaisyoHeses.setEnabled(select);
      }
    });

    totalLengthCheckListener = new FocusAdapter() {
      private boolean lockFlag = false;
      public void focusLost(FocusEvent e) {
        if (lockFlag) {
          return;
        }
        int total = getInputedLength();
        if (total > getAllowedMaxLength()) {
          //フォーカス移動先でもこのリスナを使用していた場合、メッセージボックスへの
          //フォーカス遷移で再度フォーカスロストが発生してしまうため、フラグで排他をかける。
          lockFlag = true;
          showMaxLengthError();
          lockFlag = false;
        }
      }
    };
    care1Shikkin.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Tentou.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Haikai.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Jyokusou.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Haien.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Chouheisoku.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Ekikan.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1ShinpaiDown.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1Pain.getValueCombo().getEditor().getEditorComponent().addFocusListener(
        totalLengthCheckListener);
    care1Dassui.getValueCombo().getEditor().getEditorComponent().
        addFocusListener(totalLengthCheckListener);
    care1OtherTaisyo.getEditor().getEditorComponent().addFocusListener(
        totalLengthCheckListener);

  }

  /**
   * 許容する項目総文字数を返します。
   * @return 許容する項目文字総数
   */
  protected int getAllowedMaxLength() {
    return 129;
  }

  /**
   * 対処方針の入力最大長警告メッセージを表示します。
   */
  protected void showMaxLengthError() {
    ACMessageBox.show("対処方針はトータルで140文字までしか印刷されません。");
  }

  /**
   * 入力済みの項目総文字数を返します。
   * @return 入力済みの項目文字数
   */
  protected int getInputedLength() {
    int total = 0;
    total += getShikkin().getInputedLength();
    total += getTentou().getInputedLength();
    total += getHaikai().getInputedLength();
    total += getJyokusou().getInputedLength();
    total += getHaien().getInputedLength();
    total += getChouheisoku().getInputedLength();
    total += getEkikan().getInputedLength();
    total += getShinpaiDown().getInputedLength();
    total += getPain().getInputedLength();
    total += getDassui().getInputedLength();
    total += String.valueOf(getOtherTaisyo().getEditor().getItem()).length();
    return total;
  }

  private void jbInit() throws Exception {
    care1Layout.setFitHLast(true);
    care1Layout.setFitVLast(true);
    setLayout(care1Layout);
    care1OtherTaisyos.setLayout(new VRLayout());
    care1OtherNames.setLayout(new VRLayout());
    care1GroupLayout.setFitHLast(true);
    care1GroupLayout.setLabelMargin(0);
    care1Abstractions.setLayout(care1AbstractionsLayout);
    care1AbstractionHead.setText("病態");
    care1Group.setLayout(care1GroupLayout);
    care1Group.setText("現在、発生の可能性が高い病態とその対処方針");
    care1Other.setBindPath("BYOUTAITA");
    care1Other.setActionCommand("その他");
    care1Other.setText("その他");
    care1Title.setText("４．介護に関する意見");
    care1AbstractionMiddleMessage.setText("→具体的な病態および対処方針");
    care1AbstractionMiddle.setForeground(IkenshoConstants.
                                         COLOR_MESSAGE_TEXT_FOREGROUND);
    care1AbstractionMiddle.setText("（各30文字以内）");
    care1Others.setLayout(care1OthersLayout);
    care1AbstractionTail.setText("トータルでは140文字しか印刷されません。");
    care1AbstractionTail.setForeground(IkenshoConstants.
                                       COLOR_MESSAGE_WARNING_TEXT_FOREGROUND);
    care1OthersLayout.setFitHLast(true);
    care1OthersLayout.setAutoWrap(false);
    care1OthersLayout.setLabelMargin(0);
    care1AbstractionsLayout.setAutoWrap(false);
    care1OtherName.setEnabled(false);
    care1OtherName.setPreferredSize(new Dimension(210, 19));
    care1OtherName.setIMEMode(InputSubset.KANJI);
    care1OtherName.setMaxLength(15);
    care1OtherName.setBindPath("BYOUTAITA_NM");
    care1OtherTaisyo.setEnabled(false);
    care1OtherTaisyo.setIMEMode(InputSubset.KANJI);
    care1OtherTaisyo.setMaxLength(30);
    care1OtherTaisyo.setBindPath("BYOUTAITA_TAISHO_HOUSIN");
    care1Others.setContentAreaFilled(true);
    care1Others.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    care1Shikkin.setCheckBindPath("NYOUSIKKIN");
    care1Shikkin.setCheckText("尿失禁");
    care1Shikkin.setValueBindPath("NYOUSIKKIN_TAISHO_HOUSIN");
    care1Jyokusou.setCheckBindPath("JOKUSOU_KANOUSEI");
    care1Jyokusou.setCheckText("褥瘡");
    care1Jyokusou.setValueBindPath("JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
    care1Haikai.setCheckBindPath("HAIKAI_KANOUSEI");
    care1Haikai.setCheckText("徘徊");
    care1Haikai.setValueBindPath("HAIKAI_KANOUSEI_TAISHO_HOUSIN");
    care1Tentou.setCheckBindPath("TENTOU_KOSSETU");
    care1Tentou.setCheckText("転倒・骨折");
    care1Tentou.setValueBindPath("TENTOU_KOSSETU_TAISHO_HOUSIN");
    care1Haien.setCheckBindPath("ENGESEIHAIEN");
    care1Haien.setCheckText("嚥下性肺炎");
    care1Haien.setValueBindPath("ENGESEIHAIEN_TAISHO_HOUSIN");
    care1Ekikan.setCheckBindPath("EKIKANKANSEN");
    care1Ekikan.setCheckText("易感染性");
    care1Ekikan.setValueBindPath("EKIKANKANSEN_TAISHO_HOUSIN");
    care1Chouheisoku.setCheckBindPath("CHOUHEISOKU");
    care1Chouheisoku.setCheckText("腸閉塞");
    care1Chouheisoku.setValueBindPath("CHOUHEISOKU_TAISHO_HOUSIN");
    care1ShinpaiDown.setValueBindPath("SINPAIKINOUTEIKA_TAISHO_HOUSIN");
    care1Dassui.setCheckBindPath("DASSUI");
    care1Dassui.setCheckText("脱水");
    care1Dassui.setValueBindPath("DASSUI_TAISHO_HOUSIN");
    care1OtherNameHeses.setOpaque(false);
    care1OtherNameHeses.setEnabled(false);
    care1OtherTaisyoHeses.setOpaque(false);
    care1OtherTaisyoHeses.setEnabled(false);
    care1Abstractions.add(care1AbstractionHead, VRLayout.WEST);
    care1Abstractions.add(care1AbstractionTail, VRLayout.EAST);
    care1Abstractions.add(care1AbstractionMiddle, VRLayout.EAST);
    care1Abstractions.add(care1AbstractionMiddleMessage, VRLayout.EAST);
    care1Others.add(care1Other, VRLayout.WEST);
    care1Others.add(care1OtherNameHeses, VRLayout.WEST);
    care1OtherNameHeses.add(care1OtherNames, VRLayout.CLIENT);
    care1OtherNames.add(care1OtherName, VRLayout.CLIENT);
    care1Others.add(care1OtherTaisyoHeses, VRLayout.CLIENT);
    care1OtherTaisyoHeses.add(care1OtherTaisyos, VRLayout.CLIENT);
    care1OtherTaisyos.add(care1OtherTaisyo, VRLayout.CLIENT);

    care1Group.add(care1Abstractions, VRLayout.FLOW_INSETLINE_RETURN);
    addStateTaisyos();
    this.add(care1Title, VRLayout.NORTH);
    this.add(care1Group, VRLayout.CLIENT);
    care1ShinpaiDown.setCheckText("心肺機能の低下");
    care1ShinpaiDown.setCheckBindPath("SINPAIKINOUTEIKA");
    care1Pain.setCheckText("痛み");
    care1Pain.setCheckBindPath("ITAMI");
    care1Pain.setValueBindPath("ITAMI_TAISHO_HOUSIN");
  }

  /**
   * 尿失禁への対処方針を返します。
   * @return 尿失禁への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Shikkin(){
      return care1Shikkin;
  }
  /**
   * 転倒骨折への対処方針を返します。
   * @return 転倒骨折への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Tentou(){
      return care1Tentou;
  }
  /**
   * 徘徊への対処方針を返します。
   * @return 徘徊への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Haikai(){
      return care1Haikai;
  }
  /**
   * 褥瘡への対処方針を返します。
   * @return 褥瘡への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Jyokusou(){
      return care1Jyokusou;
  }
  /**
   * 嚥下性肺炎への対処方針を返します。
   * @return 嚥下性肺炎への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Haien(){
      return care1Haien;
  }
  /**
   * 腸閉塞への対処方針を返します。
   * @return 腸閉塞への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Chouheisoku(){
      return care1Chouheisoku;
  }
  /**
   * 易感染性への対処方針を返します。
   * @return 易感染性への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Ekikan(){
      return care1Ekikan;
  }
  /**
   * 心肺機能の低下への対処方針を返します。
   * @return 心肺機能の低下への対処方針
   */
  protected IkenshoCareStatusContainer getCare1ShinpaiDown(){
      return care1ShinpaiDown;
  }
  /**
   * 痛みへの対処方針を返します。
   * @return 痛みへの対処方針
   */
  protected IkenshoCareStatusContainer getCare1Pain(){
      return care1Pain;
  }
  /**
   * 脱水への対処方針を返します。
   * @return 脱水への対処方針
   */
  protected IkenshoCareStatusContainer getCare1Dassui(){
      return care1Dassui;
  }
  /**
   * その他の対処方針を返します。
   * @return その他の対処方針
   */
  protected ACComboBox getCare1OtherTaisyo(){
      return care1OtherTaisyo;
  }

  /**
   * その他の名称を返します。
   * @return その他の名称
   */
  protected ACComboBox getCare1OtherName(){
      return care1OtherName;
  }
}
