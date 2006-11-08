package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
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
    }
    
}
