package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoCare2�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoCare2 extends IkenshoIkenshoInfoCare2H18 {

    public IkenshoIshiIkenshoInfoCare2() {
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
        // �^�C�g�����x���ݒ�
        getTitle().setText("�S�D�T�[�r�X���p�Ɋւ���ӌ��i�����j");
        
        getKansenGroup().setText("�����ǂ̗L���i�L�̏ꍇ�͋�̓I�ɋL�����ĉ������j�i30�����ȓ��j");
        getServiceGroup().setText("���T�[�r�X�i�z�[���w���v�T�[�r�X���j�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ����i30�����ȓ��B�u���̑��v��50�����j");
        getCare2Kansen().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] {"�L", "��", "�s��" }))));
        getCare2Kansen().setValues(new int[] { 1, 2, 3 });

        // ��ʍ��ڂ��`
        addGroup();
    }
    
    /**
     * override���ăp�l���֒ǉ����鍀�ڂ��`���܂��B
     */
    protected void addGroup() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getServiceGroup(), VRLayout.NORTH);
        this.add(getKansenGroup(), VRLayout.NORTH);
    }
    
    /**
     * override���Ĉ�w�I�ϓ_����̗��ӎ����̒ǉ��������`���܂��B
     */
    protected void addRyuijiko() {
        getServiceGroup().add(getKetsuattsus(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getEnges(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getSesshokus(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getMoves(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getServiceOthers(), VRLayout.FLOW_RETURN);
    }
    

    protected void setCare2KanseEventListener() {
        getCare2Kansen().addListSelectionListener(
                new ACFollowDisableSelectionListener(
                        new JComponent[] { getCare2KansenName() }, 0));
    }

    protected void addCare2Kansen() {
        getCare2Kansen().add(getCare2KansenName(), null, 1);
    }


    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        
        applyPoolTeikeibun(getCare2KetsuattsuValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME);
        applyPoolTeikeibun(getCare2EngeValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME);
        applyPoolTeikeibun(getCare2SesshokuValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_EAT_NAME);
        applyPoolTeikeibun(getCare2MoveValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME);
        applyPoolTeikeibun(getCare2ServiceOtherValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME);
        applyPoolTeikeibun(getCare2KansenName(),
                IkenshoCommon.TEIKEI_ISHI_INFECTION_NAME);
        

        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        getCare2KetsuattsuValue().setOptionComboBoxParameters("����",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30);
        getCare2EngeValue().setOptionComboBoxParameters("����",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME, 30);
        getCare2SesshokuValue().setOptionComboBoxParameters("�ېH",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_EAT_NAME, 30);
        getCare2MoveValue().setOptionComboBoxParameters("�ړ�",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME, 30);
        getCare2ServiceOtherValue().setOptionComboBoxParameters("���̑�",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME, 50);
        getCare2KansenName().setOptionComboBoxParameters("�L�̏ꍇ",
                IkenshoCommon.TEIKEI_ISHI_INFECTION_NAME, 30);
        //      2007/10/18 [Masahiko Higuchi] Addition - end
        
        
        
      }

}
