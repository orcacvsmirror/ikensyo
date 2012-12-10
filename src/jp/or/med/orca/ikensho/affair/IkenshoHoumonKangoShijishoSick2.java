package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoACTextArea;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.component.IkenshoShijishoFieldLoadButton;
import jp.or.med.orca.ikensho.component.IkenshoTextFieldDocumentLimit;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * ���a�Q�^�u
 * 
 * @since V3.0.9
 * @author Masahiko Higuchi
 */
public class IkenshoHoumonKangoShijishoSick2 extends IkenshoDocumentAffairSick {
    // [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
    protected IkenshoShijishoFieldLoadButton recentLoad = new IkenshoShijishoFieldLoadButton();
    // [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\

    private IkenshoTextFieldDocumentLimit limitArea;
    
    protected IkenshoACTextArea getSickProgress(){
        if(limitArea == null) {
            limitArea = new IkenshoTextFieldDocumentLimit();
            limitArea.setColumns(100);
            limitArea.setLineWrap(true);
            limitArea.setRows(11);
            limitArea.setMaxRows(10);
            limitArea.setBindPath("MT_STS");
            limitArea.setMaxLength(500);
            limitArea.setPageBreakLimitLength(251);
            limitArea.setPageBreakLimitRow(6);
            limitArea.setIMEMode(InputSubset.KANJI);
            getSickMedicineValueWarning().setText(getSickProgressName() + "�� {0}�����ȏ�^{1}�s�ȏ�̓��͂ł́A���[��2���ň������܂�(���� {2}���� {3}�s)");
            limitArea.setAlertLabel(getSickMedicineValueWarning());
        }
        return (IkenshoACTextArea)limitArea;
    }
    
    /**
     * �e�L�X�g�̕\���͕ʃR���|�[�l���g�ɐ؂�ւ��邽�߃I�[�o�[���C�h
     */
    protected int getSickMedicineTotalLineCount(){
        return 0;
    }
    
    /**
     * �e�L�X�g�̕\���͕ʃR���|�[�l���g�ɐ؂�ւ��邽�߃I�[�o�[���C�h
     */
    protected void updateSickMedicineValueWarningText(int totalLineCount){
        
    }
    
    /**
     * �w�����ł͖�܂̌v�Z�͕s�v�Ȃ̂ŃI�[�o�[���C�h���Ēׂ�
     */
    protected void initSickMedicineDocument() {
        
    }
    
    
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        String sickText = ACCastUtilities.toString(getMasterSource().getData("MT_STS"),"");
        getSickProgress().setText(sickText);
        
    }
    
    
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
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        getSickProgresss().setText("���a�̌o��");
//        getSickMedicineValueWarning().setText("���a�̌o�߂� 250���� �܂��� 5�s�ȓ� �����������܂���B");
        getSickProgresss().setText(
                "�Ǐ�E" + ACConstants.LINE_SEPARATOR + "����"
                        + ACConstants.LINE_SEPARATOR + "���");
        getSickMedicineValueWarning().setText(
                getSickProgressName() + "�� 250���� �܂��� 5�s�ȓ� �����������܂���B");
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

        // [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
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
        getProgressGroup().setText(getSickProgressName()+"�y�ѓ��^���̖�܂̗p�@�E�p��");
        // [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
        
        
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

        // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012�N�x�Ή� ��ܖ����ڂ̓��͕������g��
//        getSickMedicineName(0).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(1).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(2).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(3).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(4).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(5).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
//        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
//                IkenshoCommon.TEIKEI_MEDICINE_NAME, 12);
        
        getSickMedicineName(0).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(1).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(2).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(3).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(4).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(5).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(6).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        getSickMedicineName(7).setOptionComboBoxParameters("��ܖ�",
                IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH);
        
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
        
        
//      [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

        recentLoad.setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TOKUBETSU_SHOJO_SHUSO",
                "1",
                "�Ǎ�(L)",
                'L',
                "�ŐV�̓��ʖK��Ō�w�����ɓo�^�����u�Ǐ�E��i�v��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                        + "��낵���ł����H");
        
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        
    }

    protected String getSickProgressName() {
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        return "���a�̌o��";
        return "�Ǐ�E���Ï��";
//      [ID:0000514][Tozo TANAKA] 2009/09/11 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
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
        	
        	/* [ID:0000635][Shin Fujihara] 2011/02/28 edit begin �y2010�N�x�v�]�Ή��z
            //������̂݃`�F�b�N(�ۑ����͌x���ΏۊO)
          int maxLen = getSickProgressMaxLengthWhenPrint();
          if (maxLen < 0) {
              // �x��
              showAlertOnSickProgressLengthOverWhenSaveOrPrint();
          }
          */
          
        	//�ꊇ�Ōx����\������悤�C��
	      	if (getMasterAffair() instanceof IkenshoHoumonKangoShijisho) {
	      		if(limitArea.isPageBreak()) {
	      			((IkenshoHoumonKangoShijisho)getMasterAffair()).setWarningMessage(getSickProgressName());
	      		}
	    	}
	      	//[ID:0000635][Shin Fujihara] 2011/02/28 edit end �y2010�N�x�v�]�Ή��z
        }
      return true;
    }

    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 del begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
//    protected String getSickMedicineValueWarningText(int inputedCharCount, int inputedLineCount){
////      [ID:0000514][Tozo TANAKA] 2009/09/11 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
////        return "���a�̌o�߂� 250���� �܂��� 5�s�ȓ� �����������܂���B";
//        return getSickProgressName() + "�� 250���� �܂��� 5�s�ȓ� �����������܂���B";
////      [ID:0000514][Tozo TANAKA] 2009/09/11 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    }
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 del end

    // [ID:0000438][Tozo TANAKA] 2009/06/02 add end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�

}
