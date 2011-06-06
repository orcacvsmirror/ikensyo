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
 * ���a�Q�^�u
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
     * ��ʍ\�z����
     * @throws Exception
     */
    private void jbInit() throws Exception {
        getTitle().setText("���݂̏�ԁi�����j");
        getSickProgresss().setText("���a�̌o��");
        getSickMedicineValueWarning().setText("���a�̌o�߂� 250���� �܂��� 5�s�ȓ� �����������܂���B");
    }

    /**
     * ��ʍ��ڐ���
     */
    protected void addThisComponent() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getProgressGroup(), VRLayout.NORTH);
    }

    /**
     * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     * @since 3.0.9
     * @author Masahiko Higuchi
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

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
        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);

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
        getSickMedicineDosageUnit(6).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);
        getSickMedicineDosageUnit(7).setOptionComboBoxParameters("�p�ʒP��",
                IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT, 4);

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
        getSickMedicineUsage(6).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);
        getSickMedicineUsage(7).setOptionComboBoxParameters("�p�@",
                IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10);

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
        return "���a�̌o��";
    }

    /**
     * ���a�e�L�X�g���o��
     */
    protected void setSickProgressContaierText(int maxLength) {
        getSickProgresss().setText(getSickProgressName());
    }
    
    // [ID:0000438][Tozo TANAKA] 2009/06/02 add begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
//            // ���a�e�L�X�g��5�s��������300�����𒴂��Ă���ꍇ
//            if (ACMessageBox.showOkCancel(getSickProgressName()
//                    + "���g�[�^����300�����܂���6�s�𒴂��Ă��܂��B"
//                    + IkenshoConstants.LINE_SEPARATOR+getSickProgressName()
//                    + "��300�����܂���6�s�ڂ܂ł����������܂���B") != ACMessageBox.RESULT_OK) {
//                getSickProgress().requestFocus();
//                return false;
//            }
//        }
//
//        return true;
//    }
//    protected void showAlertOnSickProgressLengthOver(){
//        //�t�H�[�J�X���X�g�ł̓G���[���o���Ȃ�
//    }
    protected void showAlertOnSickProgressLengthOverWhenSaveOrPrint(){
        ACMessageBox.show(getSickProgressName() + "���g�[�^����250�����܂���5�s�𒴂��Ă��܂��B"
                + IkenshoConstants.LINE_SEPARATOR + getSickProgressName()
                + "��250�����܂���5�s�ڂ܂ł����������܂���B");
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
            //������̂݃`�F�b�N(�ۑ����͌x���ΏۊO)
          int maxLen = getSickProgressMaxLengthWhenPrint();
          if (maxLen < 0) {
              // �x��
              showAlertOnSickProgressLengthOverWhenSaveOrPrint();
          }
        }
      return true;
    }

    protected String getSickMedicineValueWarningText(int inputedCharCount, int inputedLineCount){
        return "���a�̌o�߂� 250���� �܂��� 5�s�ȓ� �����������܂���B";
    }

    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�

}
