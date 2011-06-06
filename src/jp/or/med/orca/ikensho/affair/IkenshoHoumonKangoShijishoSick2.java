package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.component.IkenshoShijishoFieldLoadButton;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 傷病２タブ
 * 
 * @since V3.0.9
 * @author Masahiko Higuchi
 */
public class IkenshoHoumonKangoShijishoSick2 extends IkenshoDocumentAffairSick {
    // [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
    protected IkenshoShijishoFieldLoadButton recentLoad = new IkenshoShijishoFieldLoadButton();
    // [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能

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
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        getSickProgresss().setText("傷病の経過");
//        getSickMedicineValueWarning().setText("傷病の経過は 250文字 または 5行以内 しか印刷されません。");
        getSickProgresss().setText(
                "症状・" + ACConstants.LINE_SEPARATOR + "治療"
                        + ACConstants.LINE_SEPARATOR + "状態");
        getSickMedicineValueWarning().setText(
                getSickProgressName() + "は 250文字 または 5行以内 しか印刷されません。");
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

        // [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
        recentLoad.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String data=recentLoad.getRecentData();
                if(data != null){
                    String oldText = getSickProgress().getText();
                    int max=getSickProgress().getMaxLength() - oldText.length();
                    if(max>0){
                        if(max<data.length()){
                            data = data.substring(0, max);
                        }
                        getSickProgress().setText(oldText+data);
                    }
                }
            }
        });
        getSickProgresss().setHgap(0);
        getSickProgresss().add(recentLoad);
        getProgressGroup().setText(getSickProgressName()+"及び投与中の薬剤の用法・用量");
        // [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能

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
        
        
//      [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

        recentLoad.setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TOKUBETSU_SHOJO_SHUSO",
                "1",
                "読込(L)",
                'L',
                "最新の特別訪問看護指示書に登録した「症状・主訴」を読み込みます。" + ACConstants.LINE_SEPARATOR
                        + "よろしいですか？");
        
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        
    }

    protected String getSickProgressName() {
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        return "傷病の経過";
        return "症状・治療状態";
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
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
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        return "傷病の経過は 250文字 または 5行以内 しか印刷されません。";
        return getSickProgressName() + "は 250文字 または 5行以内 しか印刷されません。";
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    }

    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end 【主治医医見書・医師医見書】薬剤名テキストの追加

}
