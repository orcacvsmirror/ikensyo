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
		// [ID:0000509][Masahiko Higuchi] 2009/06 del begin 画面調整に伴い調整
		//getSickProgresss().setText(
        //        "傷病の経過" + IkenshoConstants.LINE_SEPARATOR +
        //        "（250文字" + IkenshoConstants.LINE_SEPARATOR +
        //        "または5行以内）");
        //getSickProgresss().setText(getSickProgressName());
        //getSickMedicineValueWarning().setText("傷病の経過 と 薬剤名 はトータルで560文字しか印刷されません。");
        // [ID:0000509][Masahiko Higuchi] 2009/06 del end
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
      //2009/01/06 [Tozo Tanaka] Add - begin
      applyPoolTeikeibun(getSickMedicineName(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineDosageUnit(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineUsage(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineName(7), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineDosageUnit(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineUsage(7), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      //2009/01/06 [Tozo Tanaka] Add - end
      

        // 2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応
        // ACComboBox⇒IkenshoOptionComboBox
        // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
//        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineName(6).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(7).setOptionComboBoxParameters("薬剤名",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - end
      
        getSickMedicineName(0).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(1).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(2).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(3).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(4).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(5).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(6).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(7).setOptionComboBoxParameters("薬剤名",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
      
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
        //2009/01/06 [Tozo Tanaka] Add - begin
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
        //2009/01/06 [Tozo Tanaka] Add - end

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
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("用量単位",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        //2009/01/06 [Tozo Tanaka] Add - end

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
        //2009/01/06 [Tozo Tanaka] Add - begin
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
        //2009/01/06 [Tozo Tanaka] Add - end

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
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineUsage(6).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(7).setOptionComboBoxParameters("用法",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        //2009/01/06 [Tozo Tanaka] Add - end

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
        //2009/01/06 [Tozo Tanaka] Add - begin
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
        //2009/01/06 [Tozo Tanaka] Add - end
        // 2007/10/18 [Masahiko Higuchi] Addition - end
      
    }
    
    //2009/01/08 [Tozo Tanaka] Add - begin
    protected String getSickProgressName(){
        return "傷病の経過";
    }
    

    protected void setSickProgressContaierText(int maxLength){
        // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin 画面調整に伴い調整
//        getSickProgresss().setText(
//                  getSickProgressName() + IkenshoConstants.LINE_SEPARATOR + "（" + maxLength
//                          + "文字" + IkenshoConstants.LINE_SEPARATOR + "または5行以内）");
      getSickProgresss().setText(getSickProgressName());
        // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    }
    //2009/01/08 [Tozo Tanaka] Add - end
    
}
