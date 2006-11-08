package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoBodyStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoMindBody2�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoMindBody2 extends
        IkenshoIkenshoInfoMindBody2H18 {
    
    // ���֐�-�E
    private IkenshoBodyStatusContainer kataKousyukuRight;
    // ���֐�-��
    private IkenshoBodyStatusContainer kataKousyukuLeft;
    // �Ҋ֐�-�E
    private IkenshoBodyStatusContainer mataKousyukuRight;
    // �Ҋ֐�-��
    private IkenshoBodyStatusContainer mataKousyukuLeft;
    // �I�֐�-�E
    private IkenshoBodyStatusContainer hijiKousyukuRight;
    // �Ҋ֐�-��
    private IkenshoBodyStatusContainer hijiKousyukuLeft;
    // �G�֐�-�E
    private IkenshoBodyStatusContainer hizaKousyukuRight;
    // �G�֐�-��
    private IkenshoBodyStatusContainer hizaKousyukuLeft;
    // ���̑�
    private IkenshoBodyStatusContainer sonota;
    // ���֐߃`�F�b�N�{�b�N�X
    private ACIntegerCheckBox kataCheck;
    // �Ҋ֐߃`�F�b�N�{�b�N�X
    private ACIntegerCheckBox mataCheck;
    // �I�֐߃`�F�b�N�{�b�N�X
    private ACIntegerCheckBox hijiCheck;
    // �G�֐߃`�F�b�N�{�b�N�X
    private ACIntegerCheckBox hizaCheck;
    // �֐߂̍S�k���C�A�E�g
    private VRLayout kousyukuLayout = new VRLayout();
    // �֐߂̍S�k�`�F�b�N�{�b�N�X
    private ACIntegerCheckBox kousyukuCheck;
    // ���֐߃o�b�N���x���R���e�i
    private ACBackLabelContainer kataBackLabelContainar;
    // �Ҋ֐߃o�b�N���x���R���e�i
    private ACBackLabelContainer mataBackLabelContainar;
    // �I�֐߃o�b�N���x���R���e�i
    private ACBackLabelContainer hijiBackLabelContainar;
    // �G�֐߃o�b�N���x���R���e�i
    private ACBackLabelContainer hizaBackLabelContainar;
    /**
     * 
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIshiIkenshoInfoMindBody2() {
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
        getMindBody2Title().setText("�R�D�S�g�̏�ԂɊւ���ӌ��i�����P�j");
        // ��t�ӌ����ł͍��ڂ��S�ĕ\���ł��Ȃ��̂Ŋ֐߂̒ɂ݂܂łłƂ߂Ƃ�
        addContaints();
        // �C�x���g���X�i��`
        setEvent();
        
    }

    /**
     * override���Đg�̂̏�ԃO���[�v�ւ̒ǉ����`���܂��B
     */
    protected void addContaints(){
        // �l�������̕��ʂ�\��
        getShishiKesson().setRankVisible(true);
        // ��ჃR���e�i�\�������ёւ�
        addMahiContainar();
        // �֐߂̍S�k�O���[�v����
        buildKansetuKousyukuGroup();
        // ��ʔz�u
        getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);
        getMindBodyStatusContainer().add(getShishiKesson(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMahiContainer(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMahi(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMastleDown(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getConnectKousyuku(), VRLayout.FLOW_RETURN);

    }
    
    /**
     * override���Ė�ჃR���e�i�̕��я���ύX���܂��B
     */
    protected void addMahiContainar(){
        // �`�F�b�N�{�b�N�X�̗v���T�C�Y���Œ�ɂ���
        getMahiLegLeftUp().getCheckBox().setPreferredSize(null);
        getMahiLegLeftDown().getCheckBox().setPreferredSize(null);
        getMahiLegRightUp().getCheckBox().setPreferredSize(null);
        getMahiLegRightDown().getCheckBox().setPreferredSize(null);
        // ���ёւ�
        // ���㎈�@������
        // �E�㎈�@�E����
        getMahis().add(getMahiLegLeftUp(), VRLayout.FLOW);
        getMahis().add(getMahiLegLeftDown(), VRLayout.FLOW_RETURN);
        getMahis().add(getMahiLegRightUp(), VRLayout.FLOW);
        getMahis().add(getMahiLegRightDown(), VRLayout.FLOW_RETURN);
        getMahis().add(getMahiOther(), VRLayout.FLOW_RETURN);
    }
    
    /**
     * �֐߂̍S�k�O���[�v�𐶐����܂��B
     */
    protected void buildKansetuKousyukuGroup(){
        // ���C�A�E�g�ݒ���s���B
        getKousyukuLayout().setAutoWrap(false);
        getKousyukuLayout().setHgap(0);
        getKousyukuLayout().setVgap(0);
        
        VRLayout kataLayout = new VRLayout();
        VRLayout mataLayout = new VRLayout();
        VRLayout hijiLayout = new VRLayout();
        VRLayout hizaLayout = new VRLayout();
        kataLayout.setAutoWrap(false);
        mataLayout.setAutoWrap(false);
        hijiLayout.setAutoWrap(false);
        hizaLayout.setAutoWrap(false);
        
        // ���C�A�E�g�K�p
        getConnectKousyuku().setLayout(getKousyukuLayout());
        getKataBackLabelContainar().setLayout(kataLayout);
        getMataBackLabelContainar().setLayout(mataLayout);
        getHijiBackLabelContainar().setLayout(hijiLayout);
        getHizaBackLabelContainar().setLayout(hizaLayout);
        
        getKousyukuCheck().setText("�֐߂̍S�k");
        // ���֐߃O���[�v ------------------------------------------------------
        getKataCheck().setText("���֐�");
        ikenshoBodyComponentSetting(getKataKousyukuRight(),"�E");
        ikenshoBodyComponentSetting(getKataKousyukuLeft(),"��");
        getKataBackLabelContainar().add(getKataCheck(),VRLayout.FLOW);
        getKataBackLabelContainar().add(getKataKousyukuRight(),VRLayout.FLOW);
        getKataBackLabelContainar().add(getKataKousyukuLeft(),VRLayout.FLOW);
        // ���֐߃O���[�v ------------------------------------------------------
        
        // �Ҋ֐߃O���[�v ------------------------------------------------------
        getMataCheck().setText("�Ҋ֐�");
        ikenshoBodyComponentSetting(getMataKousyukuRight(),"�E");
        ikenshoBodyComponentSetting(getMataKousyukuLeft(),"��");
        getMataBackLabelContainar().add(getMataCheck(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuRight(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuLeft(),VRLayout.FLOW);
        // �Ҋ֐߃O���[�v ------------------------------------------------------
        
        // �I�֐߃O���[�v ------------------------------------------------------
        getHijiCheck().setText("�I�֐�");
        ikenshoBodyComponentSetting(getHijiKousyukuRight(),"�E");
        ikenshoBodyComponentSetting(getHijiKousyukuLeft(),"��");
        getHijiBackLabelContainar().add(getHijiCheck(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuRight(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuLeft(),VRLayout.FLOW);
        // �I�֐߃O���[�v ------------------------------------------------------        
        
        // �G�֐߃O���[�v ------------------------------------------------------ 
        getHizaCheck().setText("�G�֐�");
        ikenshoBodyComponentSetting(getHizaKousyukuRight(),"�E");
        ikenshoBodyComponentSetting(getHizaKousyukuLeft(),"��");
        getHizaBackLabelContainar().add(getHizaCheck(),VRLayout.FLOW);
        getHizaBackLabelContainar().add(getHizaKousyukuRight(),VRLayout.FLOW);
        getHizaBackLabelContainar().add(getHizaKousyukuLeft(),VRLayout.FLOW);
        // �G�֐߃O���[�v ------------------------------------------------------

        
        // �w�i�F�ݒ�
        getConnectKousyuku().setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
        // �z�u�֘A
        getConnectKousyuku().add(getKousyukuCheck(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getKataBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getMataBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHijiBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHizaBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getSonota(),VRLayout.FLOW_RETURN);
    }


    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
        /*
         * �֐߂̍S�k�`�F�b�N�C�x���g
         */
        getKousyukuCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                kousyukuChangeState();
            }
        });
        /*
         * ���֐߃`�F�b�N�C�x���g
         */
        getKataCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                kataKousyukuChangeState();
            }

        });
        /*
         * �Ҋ֐߃`�F�b�N�C�x���g
         */
        getMataCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                mataKousyukuChangeState();
            }

        });
        /*
         * �G�֐߃`�F�b�N�C�x���g
         */
        getHizaCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                hizaKousyukuChangeState();
            }

        });
        /*
         * �I�֐߃`�F�b�N�C�x���g
         */
        getHijiCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                hijiKousyukuChangeState();
            }

        });
    }
    
    /**
     * �֐߂̍S�k���`�F�b�N�����ꍇ�̏����ł��B
     */
    protected void kousyukuChangeState(){
        boolean enable;
        if(getKousyukuCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        
        getKataCheck().setEnabled(enable);
        getMataCheck().setEnabled(enable);
        getHijiCheck().setEnabled(enable);
        getHizaCheck().setEnabled(enable);
        getSonota().getCheckBox().setEnabled(enable);
        // �q�R���|�[�l���g��A��
        getKataKousyukuLeft().followParentEnabled(enable);
        getKataKousyukuRight().followParentEnabled(enable);
        getMataKousyukuLeft().followParentEnabled(enable);
        getMataKousyukuRight().followParentEnabled(enable);
        getHijiKousyukuLeft().followParentEnabled(enable);
        getHijiKousyukuRight().followParentEnabled(enable);
        getHizaKousyukuLeft().followParentEnabled(enable);
        getHizaKousyukuRight().followParentEnabled(enable);
        getSonota().followParentEnabled(enable);
        // ����Ɏq�����ĘA������
        kataKousyukuChangeState();
        mataKousyukuChangeState();
        hizaKousyukuChangeState();
        hijiKousyukuChangeState();
    }
    
    /**
     * ���֐߃`�F�b�N��I�������ꍇ�̏����ł��B
     */
    protected void kataKousyukuChangeState(){
        boolean enable;
        if(getKataCheck().isEnabled() && getKataCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // �e
        getKataKousyukuLeft().getCheckBox().setEnabled(enable);
        getKataKousyukuRight().getCheckBox().setEnabled(enable);
        // �q
        getKataKousyukuLeft().followParentEnabled(enable);
        getKataKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * �Ҋ֐߂��`�F�b�N��I�������ꍇ�̏����ł��B
     */
    protected void mataKousyukuChangeState(){
        boolean enable;
        if(getMataCheck().isEnabled() && getMataCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // �e
        getMataKousyukuLeft().getCheckBox().setEnabled(enable);
        getMataKousyukuRight().getCheckBox().setEnabled(enable);
        // �q
        getMataKousyukuLeft().followParentEnabled(enable);
        getMataKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * �G�֐߃`�F�b�N��I�������ꍇ�̏����ł��B
     */
    protected void hizaKousyukuChangeState(){
        boolean enable;
        if(getHizaCheck().isEnabled() && getHizaCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // �e
        getHizaKousyukuLeft().getCheckBox().setEnabled(enable);
        getHizaKousyukuRight().getCheckBox().setEnabled(enable);
        // �q
        getHizaKousyukuLeft().followParentEnabled(enable);
        getHizaKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * �I�֐߂�I�������ꍇ�̏����ł��B
     */
    protected void hijiKousyukuChangeState(){
        boolean enable;
        if(getHijiCheck().isEnabled() && getHijiCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // �e
        getHijiKousyukuLeft().getCheckBox().setEnabled(enable);
        getHijiKousyukuRight().getCheckBox().setEnabled(enable);
        // �q
        getHijiKousyukuLeft().followParentEnabled(enable);
        getHijiKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * 
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        // �S�k�`�F�b�N
        getKousyukuCheck().setSelected(
                getKataCheck().isSelected() || getMataCheck().isSelected()
                        || getHijiCheck().isSelected()
                        || getHizaCheck().isSelected()
                        || getSonota().getCheckBox().isSelected());
        
        kousyukuChangeState();
    }
    
    /**
     * IkenshoBodyStatusContainerComponent�̋��ʐݒ���s���܂��B
     * @param comp
     * @param checkText
     */
    protected void ikenshoBodyComponentSetting(Component comp,String checkText){
        if(comp instanceof IkenshoBodyStatusContainer){
            //  �v���T�C�Y0
            ((IkenshoBodyStatusContainer)comp).getCheckBox().setPreferredSize(null);
            ((IkenshoBodyStatusContainer)comp).setPosVisible(false);
            ((IkenshoBodyStatusContainer)comp).setCheckText(checkText);
        }
    }
    /**
     * �I�֐߃R���e�i-�E��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getHijiKousyukuRight() {
        if(hijiKousyukuRight == null){
            hijiKousyukuRight = new IkenshoBodyStatusContainer();
            hijiKousyukuRight.setCheckBindPath("HIJI_KOUSHU_MIGI");
            hijiKousyukuRight.setRankBindPath("HIJI_KOUSHU_MIGI_TEIDO");
        }
        return hijiKousyukuRight;
    }
    /**
     * �I�֐߃R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getHijiKousyukuLeft() {
        if(hijiKousyukuLeft == null){
            hijiKousyukuLeft = new IkenshoBodyStatusContainer();
            hijiKousyukuLeft.setCheckBindPath("HIJI_KOUSHU_HIDARI");
            hijiKousyukuLeft.setRankBindPath("HIJI_KOUSHU_HIDARI_TEIDO");
        }
        return hijiKousyukuLeft;
    }
    /**
     * �G�֐߃R���e�i-�E��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getHizaKousyukuRight() {
        if(hizaKousyukuRight == null){
            hizaKousyukuRight = new IkenshoBodyStatusContainer();
            hizaKousyukuRight.setCheckBindPath("HIZA_KOUSHU_MIGI");
            hizaKousyukuRight.setRankBindPath("HIZA_KOUSHU_MIGI_TEIDO");
        }
        return hizaKousyukuRight;
    }
    /**
     * �G�֐߃R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getHizaKousyukuLeft() {
        if(hizaKousyukuLeft == null){
            hizaKousyukuLeft = new IkenshoBodyStatusContainer();
            hizaKousyukuLeft.setCheckBindPath("HIZA_KOUSHU_HIDARI");
            hizaKousyukuLeft.setRankBindPath("HIZA_KOUSHU_HIDARI_TEIDO");
        }
        return hizaKousyukuLeft;
    }
    /**
     * ���֐߃R���e�i-�E��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getKataKousyukuRight() {
        if(kataKousyukuRight == null){
            kataKousyukuRight = new IkenshoBodyStatusContainer();
            kataKousyukuRight.setCheckBindPath("KATA_KOUSHU_MIGI");
            kataKousyukuRight.setRankBindPath("KATA_KOUSHU_MIGI_TEIDO");
        }
        return kataKousyukuRight;
    }
    /**
     * ���֐߃R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getKataKousyukuLeft() {
        if(kataKousyukuLeft == null){
            kataKousyukuLeft = new IkenshoBodyStatusContainer();
            kataKousyukuLeft.setCheckBindPath("KATA_KOUSHU_HIDARI");
            kataKousyukuLeft.setRankBindPath("KATA_KOUSHU_HIDARI_TEIDO");
        }
        return kataKousyukuLeft;
    }
    /**
     * �Ҋ֐߃R���e�i-�E��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMataKousyukuRight() {
        if(mataKousyukuRight == null){
            mataKousyukuRight = new IkenshoBodyStatusContainer();
            mataKousyukuRight.setCheckBindPath("MATA_KOUSHU_MIGI");
            mataKousyukuRight.setRankBindPath("MATA_KOUSHU_MIGI_TEIDO");
        }
        return mataKousyukuRight;
    }
    /**
     * �Ҋ֐߃R���e�i-����Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getMataKousyukuLeft() {
        if(mataKousyukuLeft == null){
            mataKousyukuLeft = new IkenshoBodyStatusContainer();
            mataKousyukuLeft.setCheckBindPath("MATA_KOUSHU_HIDARI");
            mataKousyukuLeft.setRankBindPath("MATA_KOUSHU_HIDARI_TEIDO");
        }
        return mataKousyukuLeft;
    }
    /**
     * �I�֐߃`�F�b�N��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getHijiCheck() {
        if(hijiCheck == null){
            hijiCheck = new ACIntegerCheckBox();
            hijiCheck.setBindPath("HIJI_KOUSHU");
        }
        return hijiCheck;
    }
    /**
     * �G�֐߃`�F�b�N��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getHizaCheck() {
        if(hizaCheck == null){
            hizaCheck = new ACIntegerCheckBox();
            hizaCheck.setBindPath("HIZA_KOUSHU");
        }
        return hizaCheck;
    }
    /**
     * ���֐߃`�F�b�N��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getKataCheck() {
        if(kataCheck == null){
            kataCheck = new ACIntegerCheckBox();
            kataCheck.setBindPath("KATA_KOUSHU");
        }
        return kataCheck;
    }
    /**
     * �Ҋ֐߃`�F�b�N��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getMataCheck() {
        if(mataCheck == null){
            mataCheck = new ACIntegerCheckBox();
            mataCheck.setBindPath("MATA_KOUSHU");
        }
        return mataCheck;
    }
    /**
     * �֐߂̍S�k���C�A�E�g��Ԃ��܂��B
     * @return
     */
    protected VRLayout getKousyukuLayout() {
        if(kousyukuLayout == null){
            kousyukuLayout = new VRLayout();
        }
        return kousyukuLayout;
    }
    /**
     * �֐߂̍S�k�`�F�b�N�{�b�N�X��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getKousyukuCheck() {
        if(kousyukuCheck == null){
            kousyukuCheck = new ACIntegerCheckBox();
            kousyukuCheck.setBindPath("KOUSHU");
        }
        return kousyukuCheck;
    }
    /**
     * �I�֐߃o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getHijiBackLabelContainar() {
        if(hijiBackLabelContainar == null){
            hijiBackLabelContainar = new ACBackLabelContainer();
        }
        return hijiBackLabelContainar;
    }
    /**
     * �G�֐߃o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getHizaBackLabelContainar() {
        if(hizaBackLabelContainar == null){
            hizaBackLabelContainar = new ACBackLabelContainer();
        }
        return hizaBackLabelContainar;
    }
    /**
     * ���֐߃o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getKataBackLabelContainar() {
        if(kataBackLabelContainar == null){
            kataBackLabelContainar = new ACBackLabelContainer();
        }
        return kataBackLabelContainar;
    }
    /**
     * �Ҋ֐߃o�b�N���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getMataBackLabelContainar() {
        if(mataBackLabelContainar == null){
            mataBackLabelContainar = new ACBackLabelContainer();
        }
        return mataBackLabelContainar;
    }
    /**
     * ���̑��R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getSonota() {
        if(sonota == null){
            sonota = new IkenshoBodyStatusContainer();
            sonota.setRankVisible(false);
            sonota.getCheckBox().setText("���̑�");
            sonota.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            sonota.setCheckBindPath("KOUSHU_ETC");
            sonota.setPosBindPath("KOUSHU_ETC_BUI");
        }
        return sonota;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        applyPoolTeikeibun(getSonota().getComboBox(), IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME);
      }

}
