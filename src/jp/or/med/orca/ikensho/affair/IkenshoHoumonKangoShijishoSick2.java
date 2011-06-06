package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 傷病２タブ
 * 
 * @since V3.0.9
 * @author Masahiko Higuchi
 */
public class IkenshoHoumonKangoShijishoSick2 extends IkenshoDocumentAffairSick {

    public IkenshoHoumonKangoShijishoSick2() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 画面構築処理
     * @throws Exception
     */
    private void jbInit() throws Exception {
        getTitle().setText("現在の状態（続き）");
        getSickProgresss().setText("傷病の経過");
        getSickMedicineValueWarning().setText("傷病の経過は 250文字 または 5行以内 しか印刷されません。");
    }

    /**
     * 画面項目制御
     */
    protected void addThisComponent() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getProgressGroup(), VRLayout.NORTH);
    }

    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     * @since 3.0.9
     * @author Masahiko Higuchi
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(6).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(7).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);

        // コンボ連動設定
        getSickMedicineName(0).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(1),
                        getSickMedicineName(2), getSickMedicineName(3),
                        getSickMedicineName(4), getSickMedicineName(5),
                        getSickMedicineName(6), getSickMedicineName(7) });
        getSickMedicineName(1).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(2), getSickMedicineName(3),
                        getSickMedicineName(4), getSickMedicineName(5),
                        getSickMedicineName(6), getSickMedicineName(7) });
        getSickMedicineName(2).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(3),
                        getSickMedicineName(4), getSickMedicineName(5),
                        getSickMedicineName(6), getSickMedicineName(7) });
        getSickMedicineName(3).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(4), getSickMedicineName(5),
                        getSickMedicineName(6), getSickMedicineName(7) });
        getSickMedicineName(4).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(3), getSickMedicineName(5),
                        getSickMedicineName(6), getSickMedicineName(7) });
        getSickMedicineName(5).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(3), getSickMedicineName(4),
                        getSickMedicineName(6), getSickMedicineName(7) });
        getSickMedicineName(6).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(3), getSickMedicineName(4),
                        getSickMedicineName(5), getSickMedicineName(7) });
        getSickMedicineName(7).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(3), getSickMedicineName(4),
                        getSickMedicineName(5), getSickMedicineName(6) });

        getSickMedicineDosageUnit(0).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(1).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(2).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(3).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(4).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(5).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);

        // 連動コンボの登録
        getSickMedicineDosageUnit(0).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(6),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(1).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(6),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(2).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(6),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(3).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(6),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(4).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(6),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(5).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(6),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(6).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(7) });
        getSickMedicineDosageUnit(7).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5),
                        getSickMedicineDosageUnit(6) });

        getSickMedicineUsage(0).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(1).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(2).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(3).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(4).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(5).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(6).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(7).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);

        // 連動コンボの登録
        getSickMedicineUsage(0).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(1),
                        getSickMedicineUsage(2), getSickMedicineUsage(3),
                        getSickMedicineUsage(4), getSickMedicineUsage(5),
                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
        getSickMedicineUsage(1).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(2), getSickMedicineUsage(3),
                        getSickMedicineUsage(4), getSickMedicineUsage(5),
                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
        getSickMedicineUsage(2).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(3),
                        getSickMedicineUsage(4), getSickMedicineUsage(5),
                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
        getSickMedicineUsage(3).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(4), getSickMedicineUsage(5),
                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
        getSickMedicineUsage(4).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(3), getSickMedicineUsage(5),
                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
        getSickMedicineUsage(5).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(3), getSickMedicineUsage(4),
                        getSickMedicineUsage(6), getSickMedicineUsage(7) });
        getSickMedicineUsage(6).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(3), getSickMedicineUsage(4),
                        getSickMedicineUsage(5), getSickMedicineUsage(7) });
        getSickMedicineUsage(7).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(3), getSickMedicineUsage(4),
                        getSickMedicineUsage(5), getSickMedicineUsage(6) });
    }

    protected String getSickProgressName() {
        return "傷病の経過";
    }

    /**
     * 傷病テキスト見出し
     */
    protected void setSickProgressContaierText(int maxLength) {
        getSickProgresss().setText(getSickProgressName());
    }
    
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//    public boolean noControlWarning() {
//        if (!noControlWarningOfSickProgress()) {
//            return false;
//        }
//        return true;
//    }
//
//    protected boolean noControlWarningOfSickProgress() {
//        String text = getSickProgress().getText();
//        int lines = ACTextUtilities.separateLineWrap(text).length;
//        int totalChars = text.replaceAll("[\r\n]","").length();
//        if(lines > 6 || totalChars > 300){
//            // 傷病テキストが5行もしくは300文字を超えている場合
//            if (ACMessageBox.showOkCancel(getSickProgressName()
//                    + "がトータルで300文字または6行を超えています。"
//                    + IkenshoConstants.LINE_SEPARATOR+getSickProgressName()
//                    + "は300文字または6行目までしか印刷されません。") != ACMessageBox.RESULT_OK) {
//                getSickProgress().requestFocus();
//                return false;
//            }
//        }
//
//        return true;
//    }
//    protected void showAlertOnSickProgressLengthOver(){
//        //フォーカスロストではエラーを出さない
//    }
    protected void showAlertOnSickProgressLengthOverWhenSaveOrPrint(){
        ACMessageBox.show(getSickProgressName() + "がトータルで250文字または5行を超えています。"
                + IkenshoConstants.LINE_SEPARATOR + getSickProgressName()
                + "は250文字または5行目までしか印刷されません。");
    }
    protected int getSickProgressMaxLengthWhenPrint(){
        String text = getSickProgress().getText();
        int lines = ACTextUtilities.separateLineWrapOnByte(text,100).length;
        int totalByteCount = text.replaceAll("[\r\n]","").getBytes().length;
        if(lines > 5 || totalByteCount > 500){
            return -totalByteCount;
        }
        return 500;
    }    
    public boolean noControlWarning() throws Exception {
        if (getMasterAffair() != null
                && getMasterAffair().getCanUpdateCheckStatus() == IkenshoTabbableAffairContainer.CAN_UPDATE_CHECK_STATUS_PRINT) {
            //印刷時のみチェック(保存時は警告対象外)
          int maxLen = getSickProgressMaxLengthWhenPrint();
          if (maxLen < 0) {
              // 警告
              showAlertOnSickProgressLengthOverWhenSaveOrPrint();
          }
        }
      return true;
    }

    protected String getSickMedicineValueWarningText(int inputedCharCount, int inputedLineCount){
        return "傷病の経過は 250文字 または 5行以内 しか印刷されません。";
    }

    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end 【主治医医見書・医師医見書】薬剤名テキストの追加

}
