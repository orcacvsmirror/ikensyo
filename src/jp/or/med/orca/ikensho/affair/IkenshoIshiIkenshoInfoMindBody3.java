package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoBodyStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;

/**
 * 
 * IkenshoIshiIkenshoInfoMindBody3�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoMindBody3 extends
IkenshoIshiIkenshoInfoMindBody2 {

    // �㎈�R���e�i-�E
    private IkenshoBodyStatusContainer jyoshiRight;
    // �㎈�R���e�i-��
    private IkenshoBodyStatusContainer jyoshiLeft;
    // �̊��R���e�i-�E
    private IkenshoBodyStatusContainer taikanRight;
    // �̊��R���e�i-��
    private IkenshoBodyStatusContainer taikanLeft;
    // �����R���e�i-�E
    private IkenshoBodyStatusContainer kashiRight;
    // �����R���e�i-��
    private IkenshoBodyStatusContainer kashiLeft;
    // �㎈�o�b�N���x���R���e�i
    private ACBackLabelContainer jyoshiContainar;
    // �̊��o�b�N���x���R���e�i
    private ACBackLabelContainer taikanContainar;
    // �����o�b�N���x���R���e�i
    private ACBackLabelContainer kashiContainar;
    
    /**
     * 
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIshiIkenshoInfoMindBody3() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * ��ʍ\���������s���܂��B
     */
    private void jbInit(){
        // �^�u�^�C�g����ݒ肵�܂��B
        getMindBody2Title().setText("�R�D�S�g�̏�ԂɊւ���ӌ��i�����Q�j");
        // ��ʍ\��
        addContaints();
        // �C�x���g���X�i��`
        setEvent();
    }
    
    /**
     * override���Đg�̂̏�ԃO���[�v�ւ̒ǉ����`���܂��B
     */
    protected void addContaints(){
        addDownFuzuii();
        // �p�l�����ɔz�u
        getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);
        
        getMindBodyStatusContainer().add(getConnectPain(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getDownFuzuiis(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getJyokusou(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMindBodyStatusOthers(), VRLayout.FLOW_RETURN);
    
        getMindBodyConnectContainer().add(getConnectKoushukus(), null);
        getMindBodyClientContainer().add(getHumanPicture(), BorderLayout.CENTER);
        getMindBodyClientContainer().add(getMindBodyConnectContainer(), BorderLayout.WEST);
    }
    
    /**
     * override���Ď����E�s���Ӊ^���̒ǉ��������`���܂��B
     */
    protected void addDownFuzuii(){
        // �����ݒ�
        getDownFuzuii().setText("�����E�s���Ӊ^��");
        
        // �E�㎈�R���e�i�ݒ� --------------------------------------------------------------------
        getJyoshiContainar().setText("�E�㎈");
        ikenshoBodyComponentSetting(getJyoshiRight(),"�E");
        ikenshoBodyComponentSetting(getJyoshiLeft(),"��");
        getJyoshiContainar().add(getJyoshiRight(),VRLayout.FLOW);
        getJyoshiContainar().add(getJyoshiLeft(),VRLayout.FLOW);
        // ---------------------------------------------------------------------------------------
        
        // �E�̊��R���e�i�ݒ� --------------------------------------------------------------------
        getTaikanContainar().setText("�E�̊�");
        ikenshoBodyComponentSetting(getTaikanRight(),"�E");
        ikenshoBodyComponentSetting(getTaikanLeft(),"��");
        getTaikanContainar().add(getTaikanRight(),VRLayout.FLOW);
        getTaikanContainar().add(getTaikanLeft(),VRLayout.FLOW);        
        // ---------------------------------------------------------------------------------------
        
        // �E�����R���e�i�ݒ� --------------------------------------------------------------------
        getKashiContainar().setText("�E����");
        ikenshoBodyComponentSetting(getKashiRight(),"�E");
        ikenshoBodyComponentSetting(getKashiLeft(),"��");
        getKashiContainar().add(getKashiRight(),VRLayout.FLOW);
        getKashiContainar().add(getKashiLeft(),VRLayout.FLOW);        
        // ---------------------------------------------------------------------------------------
        
        // �w�i�F�ݒ�
        getDownFuzuiis().setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
        // �z�u�֌W
        getDownFuzuiis().add(getDownFuzuii(),VRLayout.FLOW_RETURN);
        getDownFuzuiis().add(getJyoshiContainar(),VRLayout.FLOW_RETURN);
        getDownFuzuiis().add(getTaikanContainar(),VRLayout.FLOW_RETURN);
        getDownFuzuiis().add(getKashiContainar(),VRLayout.FLOW_RETURN);
    }
    
    /**
     * 
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        getDownFuzuii().setSelected(
                getJyoshiLeft().getCheckBox().isSelected()
                        || getJyoshiRight().getCheckBox().isSelected()
                        || getTaikanLeft().getCheckBox().isSelected()
                        || getTaikanRight().getCheckBox().isSelected()
                        || getKashiLeft().getCheckBox().isSelected()
                        || getKashiRight().getCheckBox().isSelected());
        
        downFuzuiiChangeState();
    }
        
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
        getDownFuzuii().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                downFuzuiiChangeState();
            }
        });
    }
    
    /**
     * �����E�s���Ӊ^���Ɋւ��鏈���ł��B
     */
    protected void downFuzuiiChangeState(){
        boolean enable;
        
        if(getDownFuzuii().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // �`�F�b�N�{�b�N�X�𐧌�
        getJyoshiRight().getCheckBox().setEnabled(enable);
        getJyoshiLeft().getCheckBox().setEnabled(enable);
        getTaikanRight().getCheckBox().setEnabled(enable);
        getTaikanLeft().getCheckBox().setEnabled(enable);
        getKashiRight().getCheckBox().setEnabled(enable);
        getKashiLeft().getCheckBox().setEnabled(enable);
        // �q�𐧌�
        getJyoshiRight().followParentEnabled(enable);
        getJyoshiLeft().followParentEnabled(enable);
        getTaikanRight().followParentEnabled(enable);
        getTaikanLeft().followParentEnabled(enable);
        getKashiRight().followParentEnabled(enable);
        getKashiLeft().followParentEnabled(enable);
    }
    
    /**
     * �㎈�o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getJyoshiContainar() {
        if(jyoshiContainar == null){
            jyoshiContainar = new ACBackLabelContainer();
            jyoshiContainar.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        }
        return jyoshiContainar;
    }
    /**
     * �㎈�R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getJyoshiLeft() {
        if(jyoshiLeft == null){
            jyoshiLeft = new IkenshoBodyStatusContainer();
            jyoshiLeft.setRankBindPath("JOUSI_SICCHOU_HIDARI_TEIDO");
            jyoshiLeft.setCheckBindPath("JOUSI_SICCHOU_HIDARI");
        }
        return jyoshiLeft;
    }
    /**
     * �㎈�R���e�i-�E��Ԃ��܂��B 
     * @return
     */
    protected IkenshoBodyStatusContainer getJyoshiRight() {
        if(jyoshiRight == null){
            jyoshiRight = new IkenshoBodyStatusContainer();
            jyoshiRight.setRankBindPath("JOUSI_SICCHOU_MIGI_TEIDO");
            jyoshiRight.setCheckBindPath("JOUSI_SICCHOU_MIGI");
        }
        return jyoshiRight;
    }
    /**
     * �����o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getKashiContainar() {
        if(kashiContainar == null){
            kashiContainar = new ACBackLabelContainer();
            kashiContainar.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        }
        return kashiContainar;
    }
    /**
     * �����R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getKashiLeft() {
        if(kashiLeft == null){
            kashiLeft = new IkenshoBodyStatusContainer();
            kashiLeft.setRankBindPath("KASI_SICCHOU_HIDARI_TEIDO");
            kashiLeft.setCheckBindPath("KASI_SICCHOU_HIDARI");
        }
        return kashiLeft;
    }
    /**
     * �����R���e�i-�E��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getKashiRight() {
        if(kashiRight == null){
            kashiRight = new IkenshoBodyStatusContainer();
            kashiRight.setRankBindPath("KASI_SICCHOU_MIGI_TEIDO");
            kashiRight.setCheckBindPath("KASI_SICCHOU_MIGI");
        }
        return kashiRight;
    }
    /**
     * �̊��o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getTaikanContainar() {
        if(taikanContainar == null){
            taikanContainar = new ACBackLabelContainer();
            taikanContainar.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        }
        return taikanContainar;
    }
    /**
     * �̊��R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getTaikanLeft() {
        if(taikanLeft == null){
            taikanLeft = new IkenshoBodyStatusContainer();
            taikanLeft.setRankBindPath("TAIKAN_SICCHOU_HIDARI_TEIDO");
            taikanLeft.setCheckBindPath("TAIKAN_SICCHOU_HIDARI");
        }
        return taikanLeft;
    }
    /**
     * �̊��R���e�i-�E��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getTaikanRight() {
        if(taikanRight == null){
            taikanRight = new IkenshoBodyStatusContainer();
            taikanRight.setRankBindPath("TAIKAN_SICCHOU_MIGI_TEIDO");
            taikanRight.setCheckBindPath("TAIKAN_SICCHOU_MIGI");
        }
        return taikanRight;
    }
}
