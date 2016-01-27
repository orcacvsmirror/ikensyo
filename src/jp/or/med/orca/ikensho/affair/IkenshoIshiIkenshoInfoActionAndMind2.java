package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoHashableComboFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoActionAndMind2�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 */
public class IkenshoIshiIkenshoInfoActionAndMind2 extends IkenshoTabbableChildAffairContainer {
	
    private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
    
    // -------------------------------------------------------------------------------------
    // ���_�E�_�o�Ǐ�̗L���O���[�v(����)
    private ACGroupBox seishinShinkeiGroup = new ACGroupBox();
    // �I�������X�g
    private VRListModelAdapter existEmptyListModel = new VRListModelAdapter(new
            VRArrayList(Arrays.asList(new String[] {"�L", "��"})));

    
    // �����f�̗L���@�������������x��
    private ACLabel senmoniExplanation;
    // �s����Q �������������x��
    private ACLabel koudouShougaiSonotaExplanation;
    
    // ���_�E�_�o��Q�̗L�����W�I�O���[�v
    private ACClearableRadioButtonGroup seishinShinkeiRadioGroup = new ACClearableRadioButtonGroup();
    // ���_�E�_�o�Ǐ� - �L�̏ꍇ�@���x���R���e�i
    private ACLabelContainer seishinShinkeiShojyoLabelConainer;
    // ���_�E�_�o�Ǐ�p�l���㕔 - �E
    private ACPanel seishinShinkeiShojyomeiPanelTopRightPanel = new ACPanel();
    // ���_�E�_�o�Ǐ�p�l������
    private ACPanel seishinShinkeiShojyomeiPanelDownPanel = new ACPanel();
    // ���_�E�_�o�Ǐ�p�l������
    private ACPanel seishinShinkeiShojyomeiPanelMiddlePanel = new ACPanel();
    // ���_�E�_�o�Ǐ�`�F�b�N�{�b�N�X�S------------------------------------------------------
    private ACIntegerCheckBox genshiGenkaku = new ACIntegerCheckBox();
    private ACIntegerCheckBox mousou = new ACIntegerCheckBox();
    private ACIntegerCheckBox ninchiShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox kiokuShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox chuiShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox suikoukinouShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox shakaitekikoudouShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox seishinSonota = new ACIntegerCheckBox();
    
    private ACIntegerCheckBox ishikiShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox kibunShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox suiminShogai = new ACIntegerCheckBox();
    
    // ---------------------------------------------------------------------------------------
    // ���̑��pHeses
    private ACParentHesesPanelContainer seishinShinkeiSonotaHesesPanel = new ACParentHesesPanelContainer();
    // ���̑������p�p�l��
    private ACPanel seishinShinkeiSonotaPanel = new ACPanel();
    // ���̑��e�L�X�g�t�B�[���h
    private ACTextField seihinShinkeiSonotaText = new ACTextField();
    // ���̑��o�^�\�������\�����x��
    private ACLabel seishinShinkeiSonotaExplanation;
    // �����f�̗L��
    private ACClearableRadioButtonGroup seishinShinkeiSenmoniRadioGroup = new ACClearableRadioButtonGroup();
    // �����f�̗L��Heses
    private ACParentHesesPanelContainer seishinShinkeiSenmoniHesesPanel = new ACParentHesesPanelContainer();
    // �����f�̗L���R���{
    private ACComboBox seishinShinkeiSenmoniComboBox = new ACComboBox();
    // �����f�̗L�����x���R���e�i
    private ACLabelContainer seishinShinkeiSenmoniLabelContainer;
    
    
	// -------------------------------------------------------------------------------------
    // �Ă񂩂�O���[�v
    private ACGroupBox tenkanGroup = new ACGroupBox();
    // �Ă񂩂񃉃x��
    //private ACLabel seishinShinkeiTenkanTitleLabel = new ACLabel();
    // ���_�E�_�o - �L�̏ꍇ���x���R���e�i
    private ACLabelContainer seishinShinkeiTenkanLabelContainer;
    // �Ă񂩂�L�����W�I�O���[�v
    private ACClearableRadioButtonGroup seishinShinkeiTenkanRadioGroup = new ACClearableRadioButtonGroup();
    // �p�x�`�F�b�N
    private ACLabel seishinShinkeiHindoLabel = new ACLabel();
    // �p�x���W�I�O���[�v
    private ACClearableRadioButtonGroup seishinShinkeiHindoRadioGroup = new ACClearableRadioButtonGroup();
    // �I�������X�g
    private VRListModelAdapter hindEmptyListModel = new VRListModelAdapter(new
            VRArrayList(Arrays.asList(new String[] {"�T�P��ȏ�", "���P��ȏ�","�N�P��ȏ�"})));
    // �p�xHeses�p�l��
    private ACParentHesesPanelContainer seishinShinkeiHindoHesesPanel = new ACParentHesesPanelContainer();
    

    
    public IkenshoIshiIkenshoInfoActionAndMind2() {
        try {
            // ��ʍ\������
            jbInit();
            // �C�x���g���X�i��`
            setEvent();
 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    
    /**
     * ��ʍ\���Ɋւ��鏈�����s���܂��B
     */
    private void jbInit(){

        // ���_�E�_�o��Q�O���[�v����
        // �e�p�l���͌ʂɐݒ�
        buildSeishinShinkeiPanel();
        buildSenmoniJyushinPanel();
        buildTenkanPanel();
        
        // �z�u�֘A
        this.setLayout(new VRLayout());
        title.setText("�R�D�s���y�ѐ��_���̏�ԂɊւ���ӌ��i�����j");
        this.add(title,VRLayout.NORTH);
        this.add(seishinShinkeiGroup,VRLayout.NORTH);
        this.add(tenkanGroup,VRLayout.NORTH);
        
        // ����ΏۂƂ��Ēǉ�����
        addInnerBindComponent(seishinShinkeiSenmoniComboBox);
    }
    
    /**
     * ���_�E�_�o��Q�̗L���O���[�v�������܂��B
     * @return
     */
    protected void buildSeishinShinkeiPanel(){
        // ���_�E�_�o�L�����W�I�O���[�v
        seishinShinkeiRadioGroup.setModel(existEmptyListModel);
        // �N���A�{�^������������
        seishinShinkeiRadioGroup.setUseClearButton(false);
        // ������ݒ�
        seishinShinkeiGroup.setText("���_�E�_�o�Ǐ�̗L��");
        
        genshiGenkaku.setText("���o");
        mousou.setText("�ϑz");
        ninchiShogai.setText("���̑��̔F�m�@�\��Q");
        kiokuShogai.setText("�L����Q");
        chuiShogai.setText("���ӏ�Q");
        suikoukinouShogai.setText("���s�@�\��Q");
        shakaitekikoudouShogai.setText("�Љ�I�s����Q");
        seishinSonota.setText("���̑�");
        
        ishikiShogai.setText("�ӎ���Q");
        kibunShogai.setText("�C����Q(�}���C���A�y�N/�N���)");
        suiminShogai.setText("������Q");
        

        
        genshiGenkaku.setBindPath("SS_GNS_GNC");
        mousou.setBindPath("SS_MOUSOU");
        ninchiShogai.setBindPath("SS_NINCHI_SHOGAI");
        kiokuShogai.setBindPath("SS_KIOKU_SHOGAI");
        chuiShogai.setBindPath("SS_CHUI_SHOGAI");
        suikoukinouShogai.setBindPath("SS_SUIKOU_KINO_SHOGAI");
        shakaitekikoudouShogai.setBindPath("SS_SHAKAITEKI_KODO_SHOGAI");
        seishinSonota.setBindPath("SS_OTHER");
        seihinShinkeiSonotaText.setBindPath("SS_OTHER_NM");
        // ����p�t���O
        seishinShinkeiRadioGroup.setBindPath("SEISIN");
        
        ishikiShogai.setBindPath("SS_ISHIKI_SHOGAI");
        kibunShogai.setBindPath("SS_KIBUN_SHOGAI");
        suiminShogai.setBindPath("SS_SUIMIN_SHOGAI");
        
        
        seihinShinkeiSonotaText.setIMEMode(InputSubset.KANJI);
        seihinShinkeiSonotaText.setMaxLength(30);
        seihinShinkeiSonotaText.setColumns(30);
        
        
        seishinShinkeiSonotaPanel.setHgap(0);
        seishinShinkeiSonotaPanel.setAutoWrap(false);
        seishinShinkeiSonotaHesesPanel.add(seihinShinkeiSonotaText,VRLayout.FLOW);
        seishinShinkeiSonotaPanel.add(seishinSonota,VRLayout.FLOW);
        seishinShinkeiSonotaPanel.add(seishinShinkeiSonotaHesesPanel,VRLayout.FLOW);
        
        seishinShinkeiShojyomeiPanelTopRightPanel.add(ishikiShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(kiokuShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(chuiShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(suikoukinouShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(shakaitekikoudouShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(ninchiShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(kibunShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(suiminShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(genshiGenkaku,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(mousou,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(seishinShinkeiSonotaPanel,VRLayout.FLOW);
        
        // ���_�_�o�Ǐ�̗L���`�F�b�N�{�b�N�X�S�O���[�v 
        getSeishinShinkeiSyojyoLabelConainer().add(seishinShinkeiShojyomeiPanelTopRightPanel,VRLayout.CLIENT);
        
        // �O���[�v�ɒǉ�����B
        seishinShinkeiGroup.add(seishinShinkeiRadioGroup,VRLayout.NORTH);
        seishinShinkeiGroup.add(getSeishinShinkeiSyojyoLabelConainer(),VRLayout.NORTH);
        
    }
    
    /**
     * �����f�̗L���p�l���𐶐����܂��A
     */
    protected void buildSenmoniJyushinPanel(){
        // ���W�I�O���[�v��ݒ肷��B
        seishinShinkeiSenmoniRadioGroup.setModel(existEmptyListModel);
        seishinShinkeiSenmoniRadioGroup.setUseClearButton(false);

        // �o�C���h�p�X��ݒ肷��B
        seishinShinkeiSenmoniRadioGroup.setBindPath("SENMONI");
        seishinShinkeiSenmoniComboBox.setBindPath("SENMONI_NM");
        
        seishinShinkeiSenmoniComboBox.setRenderer(new ACBindListCellRenderer("SINRYOUKA"));
        seishinShinkeiSenmoniComboBox.setIMEMode(InputSubset.KANJI);
        seishinShinkeiSenmoniComboBox.setMaxLength(30);
        seishinShinkeiSenmoniComboBox.setColumns(30);
        
        // �i�j�ň͂񂾃R���{���쐬����B
        seishinShinkeiSenmoniHesesPanel.add(seishinShinkeiSenmoniComboBox,VRLayout.FLOW);
        // ���W�I�O���[�v�ɃR���{��}������B
        if(seishinShinkeiSenmoniRadioGroup.getLayout() instanceof FlowLayout){
            ((FlowLayout)seishinShinkeiRadioGroup.getLayout()).setHgap(0);
        }
        seishinShinkeiSenmoniRadioGroup.add(seishinShinkeiSenmoniHesesPanel,1);
        seishinShinkeiSenmoniRadioGroup.add(getSenmoniExplanation(),2);
        
        // �����f�p�l���ݒ�
        seishinShinkeiShojyomeiPanelMiddlePanel.setAutoWrap(false);
        seishinShinkeiShojyomeiPanelMiddlePanel.setHgap(0);
        // ���x���R���e�i�ɒǉ�
        getSeishinShinkeiSenmoniLabelContainer().add(seishinShinkeiSenmoniRadioGroup,VRLayout.CLIENT);
        // �p�l���ɒǉ�
        seishinShinkeiShojyomeiPanelMiddlePanel.add(getSeishinShinkeiSenmoniLabelContainer(),VRLayout.CLIENT);
        // �O���[�v�ɒǉ�
        seishinShinkeiGroup.add(seishinShinkeiShojyomeiPanelMiddlePanel,VRLayout.NORTH);
    }
    
    /**
     * ���Ă񂩂񁄃p�l���𐶐����܂��B
     */
    protected void buildTenkanPanel(){
        // ������ݒ�
        seishinShinkeiHindoLabel.setText("�i�L�̏ꍇ�j���@�p�x");
        
        // ���W�I�ɗL����ݒ�
        seishinShinkeiTenkanRadioGroup.setModel(existEmptyListModel);
        seishinShinkeiTenkanRadioGroup.setUseClearButton(false);
        // �o�C���h�p�X�ݒ�
        seishinShinkeiTenkanRadioGroup.setBindPath("TENKAN");
        seishinShinkeiHindoRadioGroup.setBindPath("TENKAN_HINDO");
        // �p�x�I�����ݒ�
        seishinShinkeiHindoRadioGroup.setModel(hindEmptyListModel);
        // Heses�p�l��
        seishinShinkeiHindoHesesPanel.setHgap(0);
        seishinShinkeiHindoHesesPanel.add(seishinShinkeiHindoRadioGroup,VRLayout.FLOW);
        
        // ��ʍ\��
        getSeishinShinkeiTenkanLabelContainer().add(seishinShinkeiHindoLabel,VRLayout.WEST);
        getSeishinShinkeiTenkanLabelContainer().add(seishinShinkeiHindoHesesPanel,VRLayout.CLIENT);
     
        seishinShinkeiShojyomeiPanelDownPanel.setHgap(0);
        seishinShinkeiShojyomeiPanelDownPanel.add(seishinShinkeiTenkanRadioGroup,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelDownPanel.add(getSeishinShinkeiTenkanLabelContainer(),VRLayout.FLOW);
        // �O���[�v�ɒǉ�����B
        tenkanGroup.setText("�Ă񂩂�");
        tenkanGroup.setHgap(0);
        tenkanGroup.add(seishinShinkeiShojyomeiPanelDownPanel,VRLayout.NORTH);

    }
    
    
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        //���_�E�_�o�Ǐ�̗L�����W�I�O���[�v
        if (seishinShinkeiSenmoniRadioGroup.getSelectedIndex() == 0) {
        	seishinShinkeiRadioGroup.setSelectedIndex(2);
        }
        
        // �����f�̗L��
        if(seishinShinkeiSenmoniRadioGroup.getSelectedIndex() != 1){
            seishinShinkeiSenmoniRadioGroup.setSelectedIndex(2);
        }

        // �Ă񂩂񖢑I���̏ꍇ
        if(seishinShinkeiTenkanRadioGroup.getSelectedIndex() == 0){
            seishinShinkeiTenkanRadioGroup.setSelectedIndex(2);
        }

        
        // �ߋ��f�[�^�␳
        
        // �L����Q-�Z���A�L����Q-�����͖��g�p����
        VRMap map =  (VRMap)getMasterSource();
        map.put("SS_KIOKU_SHOGAI_TANKI", null);
        map.put("SS_KIOKU_SHOGAI_CHOUKI", null);
        
        // �u�Ă񂩂�v�Łu�P�N�ȏ�݂��Ȃ��v���I������Ă����ꍇ
        // �Ă񂩂񖳂ɕύX
        if (ACCastUtilities.toInt(map.get("TENKAN_HINDO"), 0) == 4) {
        	seishinShinkeiTenkanRadioGroup.setSelectedIndex(2);
        }
        
        

    }
    
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
        /*
         * ���_�E�_�o�Ǐ�̗L�����W�I�O���[�v
         */
        seishinShinkeiRadioGroup.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    return;
                }
                boolean enable;
                
                int select = seishinShinkeiRadioGroup.getSelectedIndex();
                switch(select){
                case 1:
                    enable = true;
                    break;
                case 2:
                    enable = false;
                    break;
                default:
                    return;
                }
                seishinShinkeiRadioGroupChangeAction(enable);
            }
        });
        /*
         * �Ă񂩂�̗L�����W�I�O���[�v
         */
        seishinShinkeiTenkanRadioGroup.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    return;
                }
                boolean enable;
                
                int select = seishinShinkeiTenkanRadioGroup.getSelectedIndex();
                switch(select){
                case 1:
                    enable = true;
                    break;
                case 2:
                    enable = false;
                    break;
                default:
                    return;
                }
                seishinShinkeiTenkanRadioGroupChangeAction(enable);
            }
        });
        
        /*
         * �����f�̗L�����W�I�O���[�v
         */
        seishinShinkeiSenmoniRadioGroup.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    return;
                }

                seishinShinkeiSenmoniChangeAction();
            }
        });
        /*
         * ���_�E�_�o�Ǐ�-���̑��`�F�b�N�I����
         */
        seishinSonota.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                seishinShinkeiSonotaChangeState();
            }
        });
    }
    
    
    /**
     * ���_�E�_�o�Ǐ�̗L�����W�I�O���[�v�I�����̉�ʐ���ł��B
     * @param enable
     */
    protected void seishinShinkeiRadioGroupChangeAction(boolean enable){

        genshiGenkaku.setEnabled(enable);
        mousou.setEnabled(enable);
        ninchiShogai.setEnabled(enable);
        kiokuShogai.setEnabled(enable);
        chuiShogai.setEnabled(enable);
        suikoukinouShogai.setEnabled(enable);
        shakaitekikoudouShogai.setEnabled(enable);
        seishinSonota.setEnabled(enable);
        seishinSonota.setEnabled(enable);
        getSeishinShinkeiSyojyoLabelConainer().setEnabled(enable);
        
        getSeishinShinkeiSenmoniLabelContainer().setEnabled(enable);
        seishinShinkeiSenmoniRadioGroup.setEnabled(enable);
        seishinShinkeiSenmoniHesesPanel.setEnabled(enable);
        
        //���̑��ʏ���
        seishinShinkeiSonotaChangeState();
        seishinShinkeiSenmoniChangeAction();
        
        ishikiShogai.setEnabled(enable);
        kibunShogai.setEnabled(enable);
        suiminShogai.setEnabled(enable);
    }
    
    /**
     * ���_�E�_�o�Ǐ�-���̑��R���{��Ԑ���
     * @param enable
     */
    protected void seishinShinkeiSonotaChangeState(){
        
        boolean enable;
        // ���̑��L���A���̑��I�����ɂ��̑��R���{��L���ɂ���B
        if(seishinSonota.isEnabled()&&seishinSonota.isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        
        seishinShinkeiSonotaPanel.setEnabled(enable);
        seihinShinkeiSonotaText.setEnabled(enable);
        seishinShinkeiSonotaHesesPanel.setEnabled(enable);
    }
    
    /**
     * �Ă񂩂�̗L�����W�I�O���[�v�ύX�̏����ł��B
     * @param enable
     */
    protected void seishinShinkeiTenkanRadioGroupChangeAction(boolean enable){
        // Enable����
        seishinShinkeiHindoRadioGroup.setEnabled(enable);
        seishinShinkeiHindoRadioGroup.getClearButton().setEnabled(enable);
        seishinShinkeiHindoHesesPanel.setEnabled(enable);
        seishinShinkeiHindoLabel.setEnabled(enable);
        getSeishinShinkeiTenkanLabelContainer().setEnabled(enable);
    }
    
    /**
     * �����f�R���{�{�b�N�X�̏�Ԑ�����s���܂��B
     */
    protected void seishinShinkeiSenmoniChangeAction(){
        
        boolean enable;
        // ���_�E�_�o�Ǐ�L�菮�������f��Enable��True�̏ꍇ
        if (seishinShinkeiSenmoniRadioGroup.getSelectedIndex() == 1
                && getSeishinShinkeiSenmoniLabelContainer().isEnabled()) {
            enable = true;
        }else{
            enable = false;
        }
        seishinShinkeiSenmoniComboBox.setEnabled(enable);
    }
    
    /**
     * �����f�̗L�����W�I�O���[�v�ύX���̏����ł��B
     * @param enable
     */
    protected void seishinShinkeiSenmoniRadioGroupChangeAction(boolean enable){
        seishinShinkeiSenmoniHesesPanel.setEnabled(enable);
        seishinShinkeiSenmoniComboBox.setEnabled(enable);
    }
    
    /**
     * ���_�E�_�o�`�F�b�N�O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACGroupBox getSeishinShinkeiGroup() {
        return seishinShinkeiGroup;
    }

    protected ACLabelContainer getSeishinShinkeiSenmoniLabelContainer() {
        if(seishinShinkeiSenmoniLabelContainer == null){
            seishinShinkeiSenmoniLabelContainer = new ACLabelContainer();
            seishinShinkeiSenmoniLabelContainer.setText("�E���Ȏ�f�̗L��");
        }
        return seishinShinkeiSenmoniLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiTenkanLabelContainer() {
        if(seishinShinkeiTenkanLabelContainer == null){
            seishinShinkeiTenkanLabelContainer = new ACLabelContainer();
            //seishinShinkeiTenkanLabelContainer.setText("�i�L�̏ꍇ�j��");
        }
        return seishinShinkeiTenkanLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiSyojyoLabelConainer() {
        if(seishinShinkeiShojyoLabelConainer == null){
            seishinShinkeiShojyoLabelConainer = new ACLabelContainer();
            seishinShinkeiShojyoLabelConainer.setText("�i�L�̏ꍇ�j��");
            seishinShinkeiShojyoLabelConainer.setLayout(new VRLayout());
            seishinShinkeiShojyoLabelConainer.setPreferredSize(new Dimension(200,50));
        }
        return seishinShinkeiShojyoLabelConainer;
    }

    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" RENKEII");
        sb.append(" ORDER BY");
        sb.append(" RENKEII_CD");
        VRArrayList renkeiDoctors = (VRArrayList) dbm.executeQuery(sb.toString());
        seishinShinkeiSenmoniComboBox.setFormat(new IkenshoHashableComboFormat(renkeiDoctors, "SINRYOUKA"));
        IkenshoCommon.applyComboModel(seishinShinkeiSenmoniComboBox, renkeiDoctors);
        
    }

    protected void applySourceInnerBindComponent() throws Exception {
      super.applySourceInnerBindComponent();

      VRBindPathParser.set("SENMONI_NM", getMasterSource(),
              seishinShinkeiSenmoniComboBox.getEditor().getItem());

    }


    public boolean noControlError() throws Exception {
        if(!super.noControlError()){
            return false;
        }
        // ���_�E�_�o�Ǐ�G���[�`�F�b�N
        if (seishinShinkeiRadioGroup.getSelectedIndex() == 1) {
        	
        	// �`�F�b�N���ڂ����݂��邩
        	if (!isCheckSeishinShinkei()) {
                ACMessageBox.showExclamation("�u���_�E�_�o�Ǐ�v�Ŗ��L��������܂��B");
                seishinShinkeiRadioGroup.requestChildFocus();
                return false;
        	}
        	
            // ���̑��`�F�b�N�L��
            if(seishinSonota.isSelected()){
                if (IkenshoCommon.isNullText(seihinShinkeiSonotaText.getText())) {
                    ACMessageBox.showExclamation("�u���_�E�_�o�Ǐ�i���̑��j�v�Ŗ��L��������܂��B");
                    seishinShinkeiRadioGroup.requestChildFocus();
                    return false;
                }
            }
        }

      return true;
    }
    
    
    // ���_�E�_�o�Ǐ�̍��ڂŁA�`�F�b�N�������Ă�����̂����݂��邩
    private boolean isCheckSeishinShinkei() throws Exception {
    	
    	
    	if (ishikiShogai.isSelected() // �ӎ���Q
    		|| kiokuShogai.isSelected() //�L����Q
    		|| chuiShogai.isSelected() //���ӏ�Q
    		|| suikoukinouShogai.isSelected() //���s�@�\��Q
    		|| shakaitekikoudouShogai.isSelected() //�Љ�I�s����Q
    		|| ninchiShogai.isSelected() //���̑��̔F�m�@�\��Q
    		|| kibunShogai.isSelected() //�C����Q
    		|| suiminShogai.isSelected() //������Q
    		|| genshiGenkaku.isSelected() //���o
    		|| mousou.isSelected() //�ϑz
    		|| seishinSonota.isSelected() //���̑�
    			) {
    		return true;
    	}
    	
    	// ���Ǝ�f�̗L��
    	if (seishinShinkeiSenmoniRadioGroup.getSelectedIndex() == 1) {
    		return true;
    	}
    	
    	return false;
    }
    
    

    /**
     * ���̑��s���������x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getKoudouShougaiSonotaExplanation() {
        if(koudouShougaiSonotaExplanation == null){
            koudouShougaiSonotaExplanation = new ACLabel();
            koudouShougaiSonotaExplanation.setText("�i30�����ȓ��j");
            koudouShougaiSonotaExplanation
                    .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return koudouShougaiSonotaExplanation;
    }
    
    /**
     * ����s���������x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getSenmoniExplanation() {
        if(senmoniExplanation == null){
            senmoniExplanation = new ACLabel();
            senmoniExplanation.setText("�i30�����ȓ��j");
            senmoniExplanation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return senmoniExplanation;
    }

    /**
     * ���_�E�_�o - ���̑��������������x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getSeishinShinkeiSonotaExplanation() {
        if(seishinShinkeiSonotaExplanation == null){
            seishinShinkeiSonotaExplanation = new ACLabel();
            seishinShinkeiSonotaExplanation.setText("30�����ȓ�");
            seishinShinkeiSonotaExplanation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return seishinShinkeiSonotaExplanation;
    }

}
