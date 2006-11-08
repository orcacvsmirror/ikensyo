package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.util.Arrays;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoIshiIkenshoInfoSpecial extends IkenshoIkenshoInfoSpecial {

    // �z�����u�R���e�i
    protected ACLabelContainer specialKyuinShochiContainer;
    protected ACPanel kyuinShochiPanel;
    // ()�t���̃p�l��
    protected ACParentHesesPanelContainer specialKyuinShochiHases;
    // ��
    protected ACTextField specialKyuinShochiCount;
    // �z�����u���C���`�F�b�N
    protected ACIntegerCheckBox specialKyuinShochiMainCheck;
    // �z�����u�񐔃��x��
    protected ACLabel specialKyuinShochiCountDayComment;
    // �z�����u���W�I�O���[�v
    protected ACClearableRadioButtonGroup kyuinShochiStatesRadio;
    
    private VRListModelAdapter kyuinShochiListModel;
    
    public IkenshoIshiIkenshoInfoSpecial() {
        try{
            // �C�x���g��`
            setEvent();
            // ��ʍč\�z
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    /**
     * ��ʍ\���Ɋւ��鏈�����s���܂��B
     * @throws Exception
     */
    private void jbInit() throws Exception {
        specialTitle.setText("�Q�D���ʂȈ�Ái���݁A����I�ɁA���邢�͕p��Ɏ󂯂Ă����Áj");
        
        // ���ڐݒ�
        getSpecialKyuinShochiMainCheck().setText("�z�����u");
        getSpecialKyuinShochiCountDayComment().setText("��^��,");
        getKyuinShochiPanel().setOpaque(false);
        getSpecialKeikanEiyou().setText("�o�ǉh�{(�݂낤)");
        getSpecialMessage2().setText("�u��t�ӌ����v�ł͈������܂���B");
        getSpecialKyuinShochiHases().setBeginText("�i��");
        
        // �T�C�Y�ݒ�
        getSpecialKyuinShochiMainCheck().setPreferredSize(new Dimension(140, 5));
        // �����܂�Ԃ��Ȃ�
        getSpecialKyuinShochiHases().setAutoWrap(false);
        
        // ��ʍ\��
        getSpecialKyuinShochiHases().add(getSpecialKyuinShochiCountText(),VRLayout.FLOW);
        getSpecialKyuinShochiHases().add(getSpecialKyuinShochiCountDayComment(),VRLayout.FLOW);
        getSpecialKyuinShochiHases().add(getKyuinShochiStatesRadio(),VRLayout.FLOW);
        getKyuinShochiPanel().add(getSpecialKyuinShochiHases(),VRLayout.FLOW);
        getSpecialKyuinShochiContainer().add(getSpecialKyuinShochiMainCheck(),VRLayout.WEST);
        getSpecialKyuinShochiContainer().add(getKyuinShochiPanel(),VRLayout.WEST);
        
        // �`�F�b�N�{�b�N�X��ԕύX����
        kyuinShochiCheckAction(getSpecialKyuinShochiMainCheck().isSelected());

    }
 
    /**
     * �������e�̕��я���override���ĕύX���܂��B
     */
    protected void addProcess() {
        super.addProcess();
        getProcesss().add(getSpecialKyuinShochiContainer(),VRLayout.FLOW_RETURN);
    }
    
    /**
     * �C�x���g���X�i�[���`���܂��B
     */
    protected void setEvent(){
        getSpecialKyuinShochiMainCheck().addItemListener(new ItemListener(){
            /*
             * �z�����u�`�F�b�N�{�b�N�X�Ɋւ���C�x���g
             */
            public void itemStateChanged(ItemEvent e) {
                boolean select;
                switch (e.getStateChange()) {
                case ItemEvent.SELECTED:
                    select = true;
                    break;
                case ItemEvent.DESELECTED:
                    select = false;
                    break;
                default:
                    return;
                }
                kyuinShochiCheckAction(select);
            }
        });
    }
    /**
     * �z�����u���`�F�b�N�����ۂ̏����ł��B
     */
    private void kyuinShochiCheckAction(boolean enable){
        // Enable����
        getSpecialKyuinShochiCountText().setEnabled(enable);
        getKyuinShochiStatesRadio().setEnabled(enable);
        getSpecialKyuinShochiHases().setEnabled(enable);
        getSpecialKyuinShochiCountDayComment().setEnabled(enable);
        getKyuinShochiStatesRadio().getClearButton().setEnabled(enable);
    }
    /**
     * �z�����u�񐔃R���{�{�b�N�X��Ԃ��܂��B
     * @return
     */
    protected ACTextField getSpecialKyuinShochiCountText() {
        if(specialKyuinShochiCount == null){
            specialKyuinShochiCount = new ACTextField();
            specialKyuinShochiCount.setBindPath("KYUIN_SHOCHI_CNT");
            specialKyuinShochiCount.setIMEMode(InputSubset.KANJI);
            specialKyuinShochiCount.setMaxLength(5);
            specialKyuinShochiCount.setColumns(5);
        }
        return specialKyuinShochiCount;
    }
    /**
     * �z�����u���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getSpecialKyuinShochiContainer(){
        if(specialKyuinShochiContainer == null){
            specialKyuinShochiContainer = new ACLabelContainer();
        }
        return specialKyuinShochiContainer;
    }
    /**
     * �z�����uHeses�p�l����Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getSpecialKyuinShochiHases() {
        if (specialKyuinShochiHases == null){
            specialKyuinShochiHases = new ACParentHesesPanelContainer();
        }
        return specialKyuinShochiHases;
    }
    /**
     * �z�����u���C���`�F�b�N�{�b�N�X��Ԃ��܂��B
     * @return
     */
    protected ACCheckBox getSpecialKyuinShochiMainCheck() {
        if(specialKyuinShochiMainCheck == null){
            specialKyuinShochiMainCheck = new ACIntegerCheckBox();
            specialKyuinShochiMainCheck.setBindPath("KYUIN_SHOCHI");
        }
        return specialKyuinShochiMainCheck;
    }
    /**
     * �z�����u�񐔃��x�����܂��B
     * @return
     */
    protected ACLabel getSpecialKyuinShochiCountDayComment() {
        if(specialKyuinShochiCountDayComment == null){
            specialKyuinShochiCountDayComment = new ACLabel();
        }
        return specialKyuinShochiCountDayComment;
    }
    /**
     * �z�����u�p�l����Ԃ��܂��B
     * @return
     */
    protected ACPanel getKyuinShochiPanel() {
        if (kyuinShochiPanel == null){
            kyuinShochiPanel = new ACPanel();
        }
        return kyuinShochiPanel;
    }
    /**
     * �z����Q�̃��X�g���f����Ԃ��܂��B
     * @return
     */
    protected VRListModelAdapter getKyuinShochiListModel() {
        if(kyuinShochiListModel == null){
            kyuinShochiListModel = new VRListModelAdapter(new VRArrayList(
                    Arrays.asList(new String[] { "�ꎞ�I", "�p���I" })));
        }

        return kyuinShochiListModel;
    }
    
    /**
     * �z�����u���W�I�O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACClearableRadioButtonGroup getKyuinShochiStatesRadio() {
        if(kyuinShochiStatesRadio == null){
            kyuinShochiStatesRadio = new ACClearableRadioButtonGroup();
            // ���f���ݒ�
            kyuinShochiStatesRadio.setModel(getKyuinShochiListModel());
            kyuinShochiStatesRadio.setBindPath("KYUIN_SHOCHI_JIKI");
        }
        return kyuinShochiStatesRadio;
    }
    
//    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
//        super.initDBCopmponent(dbm);
//        // applyPoolTeikeibun(getSpecialKyuinShochiCountText(), IkenshoCommon.TEIKEI_ISHI_SUCK_COUNT);
//    }
    
}
