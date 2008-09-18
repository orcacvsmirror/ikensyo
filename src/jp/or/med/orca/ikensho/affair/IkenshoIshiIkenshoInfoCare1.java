package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoCare1�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoCare1 extends IkenshoIkenshoInfoCare1H18 {
    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIshiIkenshoInfoCare1() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * ��ʍ\���Ɋւ��鏈�����s���܂��B
     */
    private void jbInit(){
        // �^�C�g���ݒ�
        getTitle().setText("�S�D�T�[�r�X���p�Ɋւ���ӌ�");
        // �ǉ�������`
        // �O���[�v���ږ��ݒ�
        getStateAndTaisyoGroup().setText("���݁A�����̉\���������a�ԂƂ��̑Ώ����j");
        getHaien().setVisible(true);
        getChouheisoku().setVisible(true);
        getPain().setVisible(true);
        
    }
    
    /**
     * ��ԂƑΏ����j�̒ǉ��������`���܂��B
     */
    protected void addStateTaisyos() {
        // �A����
        getStateAndTaisyoGroup().add(getShikkin(), VRLayout.FLOW_INSETLINE_RETURN);
        // �]�|�E����
        getStateAndTaisyoGroup().add(getTentou(), VRLayout.FLOW_INSETLINE_RETURN);
        // �p�j
        getStateAndTaisyoGroup().add(getHaikai(), VRLayout.FLOW_INSETLINE_RETURN);
        // ���
        getStateAndTaisyoGroup().add(getJyokusou(), VRLayout.FLOW_INSETLINE_RETURN);
        // �������x��
        getStateAndTaisyoGroup().add(getHaien(), VRLayout.FLOW_INSETLINE_RETURN);
        // ����
        getStateAndTaisyoGroup().add(getChouheisoku(),VRLayout.FLOW_INSETLINE_RETURN);
        // �Պ�����
        getStateAndTaisyoGroup().add(getEkikan(), VRLayout.FLOW_INSETLINE_RETURN);
        // �S�x�@�\�̒ቺ
        getStateAndTaisyoGroup().add(getShinpaiDown(),VRLayout.FLOW_INSETLINE_RETURN);
        // �ɂ�
        getStateAndTaisyoGroup().add(getPain(), VRLayout.FLOW_INSETLINE_RETURN);
        // �E��
        getStateAndTaisyoGroup().add(getDassui(), VRLayout.FLOW_INSETLINE_RETURN);
        // ���̑�
        getStateAndTaisyoGroup().add(getOthers(), VRLayout.FLOW_INSETLINE_RETURN);

      }
    
    protected int getInputedLength() {
        int total = 0;
        if (getShikkin().isChecked()) {
            total += getShikkin().getInputedLength();
        }
        if (getTentou().isChecked()) {
            total += getTentou().getInputedLength();
        }
        if (getHaikai().isChecked()) {
            total += getHaikai().getInputedLength();
        }
        if (getJyokusou().isChecked()) {
            total += getJyokusou().getInputedLength();
        }
        if (getHaien().isChecked()) {
            total += getHaien().getInputedLength();
        }
        if (getChouheisoku().isChecked()) {
            total += getChouheisoku().getInputedLength();
        }
        if (getEkikan().isChecked()) {
            total += getEkikan().getInputedLength();
        }
        if (getShinpaiDown().isChecked()) {
            total += getShinpaiDown().getInputedLength();
        }
        if (getPain().isChecked()) {
            total += getPain().getInputedLength();
        }
        if (getDassui().isChecked()) {
            total += getDassui().getInputedLength();
        }
        if (getOther().isSelected()) {
            total += String.valueOf(getOtherTaisyo().getEditor().getItem())
                    .length();
        }
        return total;
    }
    
    

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      super.initDBCopmponent(dbm);

      applyPoolTeikeibun(getCare1Shikkin().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_URINE_NAME);
      applyPoolTeikeibun(getCare1Tentou().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_FACTURE_NAME);
      applyPoolTeikeibun(getCare1Haikai().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PROWL_NAME);
      applyPoolTeikeibun(getCare1Jyokusou().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_JYOKUSOU_NAME);
      applyPoolTeikeibun(getCare1Haien().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME);
      applyPoolTeikeibun(getCare1Chouheisoku().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME);
      applyPoolTeikeibun(getCare1Ekikan().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INFECTION_NAME);
      applyPoolTeikeibun(getCare1ShinpaiDown().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_HEART_LUNG_NAME);
      applyPoolTeikeibun(getCare1Pain().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PAIN_NAME);
      applyPoolTeikeibun(getCare1Dassui().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_DEHYDRATION_NAME);
      applyPoolTeikeibun(getCare1OtherTaisyo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_OTHER_NAME);
      applyPoolTeikeibun(getCare1OtherName(), IkenshoCommon.TEIKEI_ISHI_SICK_TYPE_OTHER_NAME);
      

        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        getCare1Shikkin().getComboBox().setOptionComboBoxParameters("�A����",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_URINE_NAME, 30);
        getCare1Tentou().getComboBox().setOptionComboBoxParameters("�]�|�E����",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_FACTURE_NAME, 30);
        getCare1Haikai().getComboBox().setOptionComboBoxParameters("�p�j",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PROWL_NAME, 30);
        getCare1Jyokusou().getComboBox().setOptionComboBoxParameters("���",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_JYOKUSOU_NAME, 30);
        getCare1Haien().getComboBox().setOptionComboBoxParameters("�������x��",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME, 30);
        getCare1Chouheisoku().getComboBox().setOptionComboBoxParameters("����",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME, 30);
        getCare1Ekikan().getComboBox().setOptionComboBoxParameters("�Պ�����",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INFECTION_NAME, 30);
        getCare1ShinpaiDown().getComboBox().setOptionComboBoxParameters(
                "�S�x�@�\�̒ቺ", IkenshoCommon.TEIKEI_ISHI_SICK_COPE_HEART_LUNG_NAME,
                30);
        getCare1Pain().getComboBox().setOptionComboBoxParameters("�ɂ�",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PAIN_NAME, 30);
        getCare1Dassui().getComboBox().setOptionComboBoxParameters("�E��",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_DEHYDRATION_NAME, 30);
        getCare1OtherTaisyo().setOptionComboBoxParameters("���̑��E�ڍ�",
                IkenshoCommon.TEIKEI_ISHI_SICK_COPE_OTHER_NAME, 30);
        getCare1OtherName().setOptionComboBoxParameters("���̑��E���ږ�",
                IkenshoCommon.TEIKEI_ISHI_SICK_TYPE_OTHER_NAME, 15);
        //    2007/10/18 [Masahiko Higuchi] Addition - end
      
    }

}
