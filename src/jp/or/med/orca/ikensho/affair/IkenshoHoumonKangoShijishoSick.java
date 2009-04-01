package jp.or.med.orca.ikensho.affair;

import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;



/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoSick extends IkenshoDocumentAffairSick {
  public IkenshoHoumonKangoShijishoSick() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    getStableAndOutlook().setVisible(false);
    getProgressGroup().setText("���a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
    getTitle().setText("���݂̏��");
    getSickNames1().setText("");
    getSickNames2().setText("");
    getSickNames3().setText("");
    getSickNameGroup().setText("�傽�鏝�a���i�u���ǔN�����v�͈������܂���v�j");
  }

  /**
   * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
   * 
   * @param dbm DBManager
   * @throws Exception ������O
   * @since 3.0.5
   * @author Masahiko Higuchi
   */
  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      
      super.initDBCopmponent(dbm);
      
        getSickName1().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName2(), getSickName3() });
        getSickName2().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName3() });
        getSickName3().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_SICK_NAME, 30);
        getSickName3().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2() });

        // �ݒ肷��l�������ւ���
        getSickSpecial1().setUnpressedModel(getSickName1().getOriginalModel());
        getSickSpecial2().setUnpressedModel(getSickName2().getOriginalModel());
        getSickSpecial3().setUnpressedModel(getSickName3().getOriginalModel());

        getSickMedicineName(0).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(1).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(2).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(3).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(4).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(5).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        //2009/01/06 [Tozo Tanaka] Add - end

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
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(1).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(2).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(3).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(4).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(5).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
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
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(1).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(2).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(3).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(4).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(5).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        //2009/01/06 [Tozo Tanaka] Add - begin
        getSickMedicineUsage(6).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(7).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
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
    }
  
  //2009/01/08 [Tozo Tanaka] Add - begin
  protected String getSickProgressName(){
      return "���a�̌o��";
  }
  
  protected void setSickProgressContaierText(int maxLength){
      getSickProgresss().setText(
              getSickProgressName() + IkenshoConstants.LINE_SEPARATOR + "�i" + maxLength
                        + "����" + IkenshoConstants.LINE_SEPARATOR + "�܂���"
                        + IkenshoConstants.LINE_SEPARATOR + "5�s�ȓ��j");
  }
  //2009/01/08 [Tozo Tanaka] Add - end
  
}
