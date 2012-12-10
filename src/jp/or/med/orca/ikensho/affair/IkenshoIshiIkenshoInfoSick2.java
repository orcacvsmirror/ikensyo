package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
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
		// [ID:0000509][Masahiko Higuchi] 2009/06 del begin ��ʒ����ɔ�������
		//getSickProgresss().setText(
        //        "���a�̌o��" + IkenshoConstants.LINE_SEPARATOR +
        //        "�i250����" + IkenshoConstants.LINE_SEPARATOR +
        //        "�܂���5�s�ȓ��j");
        //getSickProgresss().setText(getSickProgressName());
        //getSickMedicineValueWarning().setText("���a�̌o�� �� ��ܖ� �̓g�[�^����560���������������܂���B");
        // [ID:0000509][Masahiko Higuchi] 2009/06 del end
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
      //2009/01/06 [Tozo Tanaka] Add - begin
      applyPoolTeikeibun(getSickMedicineName(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineDosageUnit(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineUsage(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      applyPoolTeikeibun(getSickMedicineName(7), IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME);
      applyPoolTeikeibun(getSickMedicineDosageUnit(6), IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT);
      applyPoolTeikeibun(getSickMedicineUsage(7), IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE);
      //2009/01/06 [Tozo Tanaka] Add - end
      

        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012�N�x�Ή� ��ܖ����ڂ̓��͕������g��
//        getSickMedicineName(0).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(1).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(2).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(3).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(4).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(5).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - begin
//        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12);
//        //2009/01/06 [Tozo Tanaka] Add - end
      
        getSickMedicineName(0).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(1).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(2).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(3).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(4).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(5).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
              IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
      
        // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012�N�x�Ή� ��ܖ����ڂ̓��͕������g��

        // �R���{�A���ݒ�
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

        getSickMedicineDosageUnit(0).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(1).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(2).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(3).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(4).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(5).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT, 4);
        //2009/01/06 [Tozo Tanaka] Add - end

        // �A���R���{�̓o�^
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

        getSickMedicineUsage(0).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(1).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(2).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(3).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(4).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(5).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineUsage(6).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(7).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10);
        //2009/01/06 [Tozo Tanaka] Add - end

        // �A���R���{�̓o�^
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
        return "���a�̌o��";
    }
    

    protected void setSickProgressContaierText(int maxLength){
        // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin ��ʒ����ɔ�������
//        getSickProgresss().setText(
//                  getSickProgressName() + IkenshoConstants.LINE_SEPARATOR + "�i" + maxLength
//                          + "����" + IkenshoConstants.LINE_SEPARATOR + "�܂���5�s�ȓ��j");
      getSickProgresss().setText(getSickProgressName());
        // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
    }
    //2009/01/08 [Tozo Tanaka] Add - end
    
}
