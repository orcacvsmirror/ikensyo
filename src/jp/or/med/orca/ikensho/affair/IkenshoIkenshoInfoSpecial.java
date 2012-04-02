package jp.or.med.orca.ikensho.affair;

import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSpecial extends IkenshoDocumentAffairSpecial {

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoSpecial() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    specialTitle.setText("�Q�D���ʂȈ�Ái�ߋ�14���Ԉȓ��Ɏ󂯂���Â̂��ׂĂɃ`�F�b�N�j");
    specialMessage1.setVisible(true);
    specialMessage2.setVisible(true);
 }

    // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
    // ACComboBox��IkenshoOptionComboBox
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

        getSpecialCannulaSize().setOptionComboBoxParameters("�C�ǃJ�j���[���T�C�Y",
                IkenshoCommon.TEIKEI_CANURE_SIZE, 5);
        getSpecialDorenPos().setOptionComboBoxParameters("�h���[������",
                IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10);
        getSpecialRyuchiCatheterChange().setOptionComboBoxParameters(
                "���u�J�e�[�e������", IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN, 5);
        getSpecialRyuchiCatheterSize().setOptionComboBoxParameters(
                "���u�J�e�[�e���T�C�Y", IkenshoCommon.TEIKEI_CATHETER_SIZE, 5);
        getSpecialKeikanEiyouSize().setOptionComboBoxParameters("�o�ǉh�{�T�C�Y",
                IkenshoCommon.TEIKEI_TUBE_SIZE, 5);
        getSpecialKeikanEiyouChange().setOptionComboBoxParameters("�o�ǉh�{����",
                IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN, 5);
        getSpecialJinkouKokyuHousiki().setOptionComboBoxParameters("�l�H�ċz�����",
                IkenshoCommon.TEIKEI_RESPIRATOR_TYPE, 5);
        getSpecialJinkouKokyuSettei().setOptionComboBoxParameters("�l�H�ċz��ݒ�",
                IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10);
        getSpecialKeikanEiyouMethod().setOptionComboBoxParameters("�o�ǉh�{���@",
                IkenshoCommon.TEIKEI_TUBE_TYPE, 5);
        
        //[ID:0000688][Shin Fujihara] 2012/03/12 Addition - start
        //���u�J�e�[�e���̕��ʃR���{�ǉ�
        getSpecialRyuchiCatheterPos().setOptionComboBoxParameters("���u�J�e�[�e������", IkenshoCommon.TEIKEI_CATHETER_POS_NAME, 10);
        //[ID:0000688][Shin Fujihara] 2012/03/12 Addition - end

    }
    // 2007/10/18 [Masahiko Higuchi] Addition - end
  
}
