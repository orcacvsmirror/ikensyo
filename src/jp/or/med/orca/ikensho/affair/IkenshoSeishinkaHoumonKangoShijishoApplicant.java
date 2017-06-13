package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoApplicant extends IkenshoHoumonKangoShijishoApplicant {

    // 施設名
    protected ACGroupBox sisetuNameGrp = new ACGroupBox();
    private ACLabelContainer sisetuNameContainer = new ACLabelContainer();
    private IkenshoOptionComboBox sisetuName = new IkenshoOptionComboBox();

    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoApplicant() {
        super();
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初期処理
     */
    private void jbInit() throws Exception {
        // 指示書種類、点滴指示期間、要介護認定状況を非表示
        getShijishoKind().setVisible(false);
        getTenteki().setVisible(false);
        youkaigoJoukyouGrp.setVisible(false);
        //患者
        this.add(sisetuNameGrp, VRLayout.NORTH);
        // 指示期間
        sijiKikanContainer.setText(getSijiKikanContainerText());
        // 施設名
        sisetuName.setMaxLength(30);
        sisetuName.setColumns(30);
        sisetuName.setIMEMode(InputSubset.KANJI);
        sisetuName.setBindPath("SISETU_NM");
        addInnerBindComponent(sisetuName);
        sisetuNameContainer.setText("施設名");
        sisetuNameContainer.add(sisetuName, VRLayout.CLIENT);
        sisetuNameGrp.add(sisetuNameContainer, VRLayout.FLOW_INSETLINE_RETURN);
    }

    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * @param dbm DBManager
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        applyPoolTeikeibun(sisetuName, IkenshoCommon.TEIKEI_FACILITY_NAME);
        sisetuName.setOptionComboBoxParameters("施設名", IkenshoCommon.TEIKEI_FACILITY_NAME, 30);
    }

    /**
     * エラーチェック処理
     */
    /* (非 Javadoc)
     * @see jp.or.med.orca.ikensho.affair.IkenshoHoumonKangoShijishoApplicant#noControlError()
     */
    public boolean noControlError() throws Exception {
        // 氏名必須チェック
        if (isNullText(getApplicantName().getText())) {
          ACMessageBox.show("氏名を入力してください。");
          getApplicantName().requestFocus();
          return false;
        }
        // 性別必須チェック
        if (getApplicantSex().getSelectedIndex() == getApplicantSex().getNoSelectIndex()) {
          ACMessageBox.show("性別を選択してください。");
          getApplicantSex().requestChildFocus();
          return false;
        }
        // 生年月日チェック
        switch (getApplicantBirth().getInputStatus()) {
          case IkenshoEraDateTextField.STATE_VALID:
            break;
          case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            getApplicantBirth().requestChildFocus();
            return false;
          default:
            ACMessageBox.showExclamation("日付に誤りがあります。");
            getApplicantBirth().requestChildFocus();
            getApplicantBirth().setParentColor(false);
            return false;
        }
        // 記入日チェック
        switch (writeDate.getInputStatus()) {
          case IkenshoEraDateTextField.STATE_VALID:
          case IkenshoEraDateTextField.STATE_FUTURE:
            break;
          default:
            ACMessageBox.showExclamation("日付に誤りがあります。");
            writeDate.requestChildFocus();
            return false;
        }
        // 指示期間（開始日）チェック
        sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(sijiKikanFrom.getEra())&& !"".equals(sijiKikanFrom.getYear())){
            sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            switch (sijiKikanFrom.getInputStatus()) {
              case IkenshoEraDateTextField.STATE_EMPTY:
                  sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                break;
              case IkenshoEraDateTextField.STATE_VALID:
                sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
                if (sijiKikanTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
                  if (sijiKikanFrom.getDate().compareTo(sijiKikanTo.getDate()) >= 0) {
                    ACMessageBox.showExclamation("指示期間「開始日付」と「終了日付」の範囲に誤りがあります。");
                    sijiKikanFrom.requestChildFocus();
                    sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                    sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                    return false;
                  }
                }
                sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                break;
              default:
                ACMessageBox.show("日付に誤りがあります。");
                sijiKikanFrom.requestChildFocus();
                sijiKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }
        // 指示期間（終了日）チェック
        sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(sijiKikanTo.getEra())&& !"".equals(sijiKikanTo.getYear())){
            sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            switch (sijiKikanTo.getInputStatus()) {
              case IkenshoEraDateTextField.STATE_EMPTY:
              case IkenshoEraDateTextField.STATE_VALID:
                  sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                break;
              default:
                ACMessageBox.show("日付に誤りがあります。");
                sijiKikanTo.requestChildFocus();
                sijiKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }
        return true;
    }

    /**
     * sijiKikanContainerに設定するTextを返します。
     * @return sijiKikanContainerに設定するText
     */
    protected String getSijiKikanContainerText() {
        return "指示期間";
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End