package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoIshiIkenshoInfoSick2 extends IkenshoIkenshoInfoSickH18 {
    // タイトルコンポーネント
    private IkenshoDocumentTabTitleLabel titleLabel;
    
    public IkenshoIshiIkenshoInfoSick2() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 画面構成に関する処理を行いま。
     */
    private void jbInit(){
        // コンポーネント追加
        addThisComponent();
    }
    
    /**
     * overrideしてタブに追加する項目を定義します。
     */
    protected void addThisComponent(){
        getProgressGroup().setText("障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容");
        getSickProgresss().setText(
                "傷病の経過" + IkenshoConstants.LINE_SEPARATOR +
                "（250文字" + IkenshoConstants.LINE_SEPARATOR +
                "または5行以内）");
        this.add(getTitleLabel(),VRLayout.NORTH);
        this.add(getProgressGroup(), VRLayout.NORTH);
    }
    
    /**
     * タブ名称ラベルを返します。
     * @return
     */
    protected IkenshoDocumentTabTitleLabel getTitleLabel() {
        if(titleLabel == null){
            titleLabel = new IkenshoDocumentTabTitleLabel();
            titleLabel.setText("１．傷病に関する意見（続き）");
        }
        return titleLabel;
    }



    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      super.initDBCopmponent(dbm);
      applyPoolTeikeibun(getSickMedicineName(0), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineName(1), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineName(2), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineName(3), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineName(4), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineName(5), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineDosageUnit(0), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineDosageUnit(1), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineDosageUnit(2), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineDosageUnit(3), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineDosageUnit(4), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineDosageUnit(5), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineUsage(0), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineUsage(1), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineUsage(2), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineUsage(3), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineUsage(4), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineUsage(5), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      

        // 2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応
        // ACComboBox⇒IkenshoOptionComboBox
        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);

        // コンボ連動設定
        getSickMedicineName(0).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(1),
                        getSickMedicineName(2), getSickMedicineName(3),
                        getSickMedicineName(4), getSickMedicineName(5) });
        getSickMedicineName(1).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(2), getSickMedicineName(3),
                        getSickMedicineName(4), getSickMedicineName(5) });
        getSickMedicineName(2).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(3),
                        getSickMedicineName(4), getSickMedicineName(5) });
        getSickMedicineName(3).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(4), getSickMedicineName(5) });
        getSickMedicineName(4).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(3), getSickMedicineName(5) });
        getSickMedicineName(5).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineName(0),
                        getSickMedicineName(1), getSickMedicineName(2),
                        getSickMedicineName(3), getSickMedicineName(4) });

        getSickMedicineDosageUnit(0).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(1).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(2).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(3).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(4).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(5).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);

        // 連動コンボの登録
        getSickMedicineDosageUnit(0).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5) });
        getSickMedicineDosageUnit(1).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5) });
        getSickMedicineDosageUnit(2).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5) });
        getSickMedicineDosageUnit(3).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(4),
                        getSickMedicineDosageUnit(5) });
        getSickMedicineDosageUnit(4).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(5) });
        getSickMedicineDosageUnit(5).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineDosageUnit(0),
                        getSickMedicineDosageUnit(1),
                        getSickMedicineDosageUnit(2),
                        getSickMedicineDosageUnit(3),
                        getSickMedicineDosageUnit(4) });

        getSickMedicineUsage(0).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(1).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(2).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(3).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(4).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(5).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        // 連動コンボの登録
        getSickMedicineUsage(0).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(1),
                        getSickMedicineUsage(2), getSickMedicineUsage(3),
                        getSickMedicineUsage(4), getSickMedicineUsage(5) });
        getSickMedicineUsage(1).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(2), getSickMedicineUsage(3),
                        getSickMedicineUsage(4), getSickMedicineUsage(5) });
        getSickMedicineUsage(2).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(3),
                        getSickMedicineUsage(4), getSickMedicineUsage(5) });
        getSickMedicineUsage(3).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(4), getSickMedicineUsage(5) });
        getSickMedicineUsage(4).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(3), getSickMedicineUsage(5) });
        getSickMedicineUsage(5).addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickMedicineUsage(0),
                        getSickMedicineUsage(1), getSickMedicineUsage(2),
                        getSickMedicineUsage(3), getSickMedicineUsage(4) });
        // 2007/10/18 [Masahiko Higuchi] Addition - end
      
    }
    
}
