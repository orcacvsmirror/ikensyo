package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoCareStatusContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoCare1H18
    extends IkenshoIkenshoInfoCare1 {
  private IkenshoCareStatusContainer moveDown;
  private IkenshoCareStatusContainer tojikomori;
  private IkenshoCareStatusContainer iyokuDown;
  private IkenshoCareStatusContainer lowEnergy;
  private IkenshoCareStatusContainer engeDown;
  private IkenshoCareStatusContainer toutsu;

  /**
   * 移動能力の低下コンテナを返します。
   * @return 移動能力の低下コンテナ
   */
  protected IkenshoCareStatusContainer getMoveDown() {
    if (moveDown == null) {
      moveDown = new IkenshoCareStatusContainer();
    }
    return moveDown;
  }

  /**
   * 閉じこもりコンテナを返します。
   * @return 閉じこもりコンテナ
   */
  protected IkenshoCareStatusContainer getTojikomori() {
    if (tojikomori == null) {
      tojikomori = new IkenshoCareStatusContainer();
    }
    return tojikomori;
  }

  /**
   * 意欲低下コンテナを返します。
   * @return 意欲低下コンテナ
   */
  protected IkenshoCareStatusContainer getIyokuDown() {
    if (iyokuDown == null) {
      iyokuDown = new IkenshoCareStatusContainer();
    }
    return iyokuDown;
  }

  /**
   * 低栄養コンテナを返します。
   * @return 低栄養コンテナ
   */
  protected IkenshoCareStatusContainer getLowEnergy() {
    if (lowEnergy == null) {
      lowEnergy = new IkenshoCareStatusContainer();
    }
    return lowEnergy;
  }

  /**
   * 摂食・嚥下機能低下コンテナを返します。
   * @return 摂食・嚥下機能低下コンテナ
   */
  protected IkenshoCareStatusContainer getEngeDown() {
    if (engeDown == null) {
      engeDown = new IkenshoCareStatusContainer();
    }
    return engeDown;
  }

  /**
   * がん等による疼痛コンテナを返します。
   * @return がん等による疼痛コンテナ
   */
  protected IkenshoCareStatusContainer getToutsu() {
    if (toutsu == null) {
      toutsu = new IkenshoCareStatusContainer();
    }
    return toutsu;
  }
  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    super.initDBCopmponent(dbm);

    applyPoolTeikeibun(moveDown.getComboBox(), IkenshoCommon.TEIKEI_MOVILITY_DOWN_NAME);
    applyPoolTeikeibun(tojikomori.getComboBox(), IkenshoCommon.TEIKEI_TOJIKOMORI_NAME);
    applyPoolTeikeibun(iyokuDown.getComboBox(), IkenshoCommon.TEIKEI_IYOKU_DOWN_NAME);
    applyPoolTeikeibun(lowEnergy.getComboBox(), IkenshoCommon.TEIKEI_LOW_ENERGY_NAME);
    applyPoolTeikeibun(engeDown.getComboBox(), IkenshoCommon.TEIKEI_SESSHOKU_ENGE_DOWN_NAME);
    applyPoolTeikeibun(toutsu.getComboBox(), IkenshoCommon.TEIKEI_GAN_TOTSU_NAME);
  }

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoCare1H18() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    getMoveDown().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getTojikomori().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getIyokuDown().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getLowEnergy().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getEngeDown().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
    getToutsu().getComboBox().getEditor().getEditorComponent().addFocusListener(
        getTotalLengthCheckListener());
  }

  private void jbInit() throws Exception {
    getTitle().setText("４．生活機能とサービスに関する意見（続き１）");
    getAbstractionTail().setText("トータルでは45文字しか印刷されません。");
    getStateAndTaisyoGroup().setText("現在あるかまたは今後発生の可能性の高い状態とその対処方針");
    moveDown.setValueBindPath("IDOUTEIKA_TAISHO_HOUSIN");
    moveDown.setCheckBindPath("IDOUTEIKA");
    getMoveDown().setCheckText("移動能力の低下");
    tojikomori.setCheckBindPath("TOJIKOMORI");
    getTojikomori().setCheckText("閉じこもり");
    tojikomori.setValueBindPath("TOJIKOMORI_TAISHO_HOUSIN");
    iyokuDown.setCheckBindPath("IYOKUTEIKA");
    getIyokuDown().setCheckText("意欲低下");
    iyokuDown.setValueBindPath("IYOKUTEIKA_TAISHO_HOUSIN");
    lowEnergy.setCheckBindPath("TEIEIYOU");
    getLowEnergy().setCheckText("低栄養");
    lowEnergy.setValueBindPath("TEIEIYOU_TAISHO_HOUSIN");
    engeDown.setCheckBindPath("SESSYOKUENGE");
    getEngeDown().setCheckText("摂食・嚥下機能低下");
    engeDown.setValueBindPath("SESSYOKUENGE_TAISHO_HOUSIN");
    toutsu.setCheckBindPath("GAN_TOUTU");
    getToutsu().setCheckText("がん等による疼痛");
    toutsu.setValueBindPath("GAN_TOUTU_TAISHO_HOUSIN");

    getHaien().setVisible(false);
    getChouheisoku().setVisible(false);
    getPain().setVisible(false);

  }

  protected int getAllowedMaxLength() {
    return 45;
  }

  protected void showMaxLengthError() {
    ACMessageBox.show("対処方針はトータルで45文字までしか印刷されません。");
  }

  protected int getInputedLength() {
    int total = 0;
    if (getShikkin().isChecked()) {
      total += getShikkin().getInputedLength();
    }
    if (getTentou().isChecked()) {
      total += getTentou().getInputedLength();
    }
    if (getMoveDown().isChecked()) {
      total += getMoveDown().getInputedLength();
    }
    if (getJyokusou().isChecked()) {
      total += getJyokusou().getInputedLength();
    }
    if (getShinpaiDown().isChecked()) {
      total += getShinpaiDown().getInputedLength();
    }
    if (getTojikomori().isChecked()) {
      total += getTojikomori().getInputedLength();
    }
    if (getIyokuDown().isChecked()) {
      total += getIyokuDown().getInputedLength();
    }
    if (getHaikai().isChecked()) {
      total += getHaikai().getInputedLength();
    }
    if (getLowEnergy().isChecked()) {
      total += getLowEnergy().getInputedLength();
    }
    if (getEngeDown().isChecked()) {
      total += getEngeDown().getInputedLength();
    }
    if (getDassui().isChecked()) {
      total += getDassui().getInputedLength();
    }
    if (getEkikan().isChecked()) {
      total += getEkikan().getInputedLength();
    }
    if (getToutsu().isChecked()) {
      total += getToutsu().getInputedLength();
    }
    if (getOther().isSelected()) {
      total += String.valueOf(getOtherTaisyo().getEditor().getItem()).length();
    }
    return total;
  }

  protected void addStateTaisyos() {
    getStateAndTaisyoGroup().add(getShikkin(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getTentou(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getMoveDown(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getJyokusou(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getShinpaiDown(),
                                 VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getTojikomori(),
                                 VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getIyokuDown(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getHaikai(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getLowEnergy(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getEngeDown(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getDassui(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getEkikan(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getToutsu(), VRLayout.FLOW_INSETLINE_RETURN);

    getStateAndTaisyoGroup().add(getOthers(), VRLayout.FLOW_INSETLINE_RETURN);

    //移行
    getStateAndTaisyoGroup().add(getHaien(), VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getChouheisoku(),
                                 VRLayout.FLOW_INSETLINE_RETURN);
    getStateAndTaisyoGroup().add(getPain(), VRLayout.FLOW_INSETLINE_RETURN);

  }

}
