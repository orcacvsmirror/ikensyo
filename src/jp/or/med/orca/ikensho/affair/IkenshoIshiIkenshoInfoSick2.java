package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoIshiIkenshoInfoSick2 extends IkenshoIkenshoInfoSickH18 {
    // �^�C�g���R���|�[�l���g
    private IkenshoDocumentTabTitleLabel titleLabel;
    
    public IkenshoIshiIkenshoInfoSick2() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * ��ʍ\���Ɋւ��鏈�����s���܁B
     */
    private void jbInit(){
        // �R���|�[�l���g�ǉ�
        addThisComponent();
    }
    
    /**
     * override���ă^�u�ɒǉ����鍀�ڂ��`���܂��B
     */
    protected void addThisComponent(){
        getProgressGroup().setText("��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
        getSickProgresss().setText(
                "���a�̌o��" + IkenshoConstants.LINE_SEPARATOR +
                "�i250����" + IkenshoConstants.LINE_SEPARATOR +
                "�܂���5�s�ȓ��j");
        this.add(getTitleLabel(),VRLayout.NORTH);
        this.add(getProgressGroup(), VRLayout.NORTH);
    }
    
    /**
     * �^�u���̃��x����Ԃ��܂��B
     * @return
     */
    protected IkenshoDocumentTabTitleLabel getTitleLabel() {
        if(titleLabel == null){
            titleLabel = new IkenshoDocumentTabTitleLabel();
            titleLabel.setText("�P�D���a�Ɋւ���ӌ��i�����j");
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
