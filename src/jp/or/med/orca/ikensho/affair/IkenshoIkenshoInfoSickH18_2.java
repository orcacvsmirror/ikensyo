package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 傷病タブ２
 * @since V3.0.9
 * @author Masahiko Higuchi
 */
public class IkenshoIkenshoInfoSickH18_2 extends IkenshoIkenshoInfoSick {
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
//        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(6).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(7).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        
        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(6).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(7).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        
        // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張

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
    
    /**
     * コンストラクタ
     */
    public IkenshoIkenshoInfoSickH18_2() {
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
        getTitle().setText("１．傷病に関する意見（続き）");
        getProgressGroup().setText("障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容");
        this.add(getTitle(),VRLayout.NORTH);
        this.add(getProgressGroup(), VRLayout.NORTH);
    }
    
    /**
     * 画面構成制御処理
     */
    protected void addThisComponent(){
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getProgressGroup(), VRLayout.NORTH);
    }
    
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();

    }
    
    /**
     * 傷病の経過テキストの見出し設定
     */
    protected void setSickProgressContaierText(int maxLength){
        getSickProgresss().setText(
                  "傷病または" + IkenshoConstants.LINE_SEPARATOR + "特定疾病の経過");
    }
    
   
}
