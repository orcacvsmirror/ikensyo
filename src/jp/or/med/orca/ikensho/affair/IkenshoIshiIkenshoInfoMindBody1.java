package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACRadioButtonItem;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoHashableComboFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoMindBody1�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 */
public class IkenshoIshiIkenshoInfoMindBody1 extends IkenshoTabbableChildAffairContainer {
    
    private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
    // �s����̏�Q�̗L���O���[�v(����)
    private ACGroupBox koudouSyogaiGroup = new ACGroupBox();
    //���_�E�_�o�Ǐ�̗L���O���[�v(����)
    private ACGroupBox seishinShinkeiGroup = new ACGroupBox();
    // �I�������X�g
    private VRListModelAdapter existEmptyListModel = new VRListModelAdapter(new
            VRArrayList(Arrays.asList(new String[] {"�L", "��"})));
    //�s����̏�Q�̗L���㕔�p�l��
    private ACPanel koudouShogaiPanelTop = new ACPanel();
    //�s����̏�Q�̗L�������p�l��
    private ACPanel koudouShogaiPanelDown = new ACPanel();
    // ��Q�̗L�������p�l����
    private ACPanel koudouShogaiPanelDownLeftPane = new ACPanel();
    // ��Q�̗L�������p�l���E
    private ACPanel koudouShogaiPanelDownRightPane = new ACPanel();
    // ���̑������p�p�l��
    private ACPanel koudouShogaiSonotaPanel = new ACPanel(); 
    // �s����̏��Q�̗L�����x���R���e�i
    private ACLabelContainer koudouShogaiLabelContainer = new ACLabelContainer();
    // �s����̏�Q���C�A�E�g
    private VRLayout koudouShogaiLayout;
    // �`�F�b�N�{�b�N�X�S -----------------------------------------
    private ACIntegerCheckBox tyuyaGyakuten = new ACIntegerCheckBox();
    private ACIntegerCheckBox bougen = new ACIntegerCheckBox();
    private ACIntegerCheckBox boukou = new ACIntegerCheckBox();
    private ACIntegerCheckBox kaigoTeikou = new ACIntegerCheckBox();
    private ACIntegerCheckBox haikai = new ACIntegerCheckBox();
    private ACIntegerCheckBox hinoFushimatsu = new ACIntegerCheckBox();
    private ACIntegerCheckBox fuketsuKoui = new ACIntegerCheckBox();
    private ACIntegerCheckBox isyoku = new ACIntegerCheckBox();
    private ACIntegerCheckBox seitekiMondai = new ACIntegerCheckBox();
    private ACIntegerCheckBox sonota = new ACIntegerCheckBox();
    // -----------------------------------------------------------------
    
    private ACTextField sonotaText = new ACTextField();
    
    // �����f�̗L���@�������������x��
    private ACLabel senmoniExplanation;
    // �s����Q �������������x��
    private ACLabel koudouShougaiSonotaExplanation;
    // ���_ �Ǐ󖼃R���{�������������x��
    private ACLabel seishinShoujyomeiExplanation;
    
    // �i�j�p�l��
    private ACParentHesesPanelContainer koudouShogaiSonotaHesesPanel = new ACParentHesesPanelContainer(); 
    // �s����̏�Q�̗L�����W�I�O���[�v
    private ACClearableRadioButtonGroup koudouShogaiRadioGroup = new ACClearableRadioButtonGroup();
    // ���_�E�_�o��Q�̗L�����W�I�O���[�v
    private ACClearableRadioButtonGroup seishinShinkeiRadioGroup = new ACClearableRadioButtonGroup();
    // �Ǐ󖼃��x��
    private ACLabel seishinShinkeiShojyomeiLabel = new ACLabel();
    // �Ǐ󖼃p�l��
    private ACParentHesesPanelContainer seishinShinkeiShogaiHesesPanel = new ACParentHesesPanelContainer();
    // ���_�E�_�o�Ǐ� - �L�̏ꍇ�@���x���R���e�i
    private ACLabelContainer seishinShinkeiShojyoLabelConainer;
//  2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
    // �Ǐ󖼃e�L�X�g
    private IkenshoOptionComboBox seishinShinkeiShojyomeiText = new IkenshoOptionComboBox();
//  2007/10/18 [Masahiko Higuchi] Replace - end
    // ���_�E�_�o�Ǐ�p�l���㕔 - �E
    private ACPanel seishinShinkeiShojyomeiPanelTopRightPanel = new ACPanel();
    // ���_�E�_�o�Ǐ�p�l������
    private ACPanel seishinShinkeiShojyomeiPanelDownPanel = new ACPanel();
    // ���_�E�_�o�Ǐ�p�l������
    private ACPanel seishinShinkeiShojyomeiPanelMiddlePanel = new ACPanel();
    // ���_�E�_�o�Ǐ�`�F�b�N�{�b�N�X�S------------------------------------------------------
    private ACIntegerCheckBox senmou = new ACIntegerCheckBox();
    private ACIntegerCheckBox keiminKeikou = new ACIntegerCheckBox();
    private ACIntegerCheckBox genshiGenkaku = new ACIntegerCheckBox();
    private ACIntegerCheckBox mousou = new ACIntegerCheckBox();
    private ACIntegerCheckBox shikken = new ACIntegerCheckBox();
    private ACIntegerCheckBox shitsunin = new ACIntegerCheckBox();
    private ACIntegerCheckBox shitsukou = new ACIntegerCheckBox();
    private ACIntegerCheckBox ninchiShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox kiokuShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox chuiShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox suikoukinouShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox shakaitekikoudouShogai = new ACIntegerCheckBox();
    private ACIntegerCheckBox seishinSonota = new ACIntegerCheckBox();
    // �Z���`�F�b�N�{�b�N�X
    private ACIntegerCheckBox tankiCheck = new ACIntegerCheckBox();
    // �����`�F�b�N�{�b�N�X
    private ACIntegerCheckBox choukiCheck = new ACIntegerCheckBox();
    // �Z���E����Heses�p�l��
    private ACParentHesesPanelContainer kiokuShogaiHesesPanel = new ACParentHesesPanelContainer();
    // �Z���E�����p�l��
    private ACPanel kiokuShogaiPanel = new ACPanel();
    // �Z���E�����p�l���pVRLayout
    private VRLayout kiokuShogaiPanelLayout = new VRLayout();
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
    // �Ă񂩂񃉃x��
    private ACLabel seishinShinkeiTenkanTitleLabel = new ACLabel();
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
            VRArrayList(Arrays.asList(new String[] {"�T�P��ȏ�", "���P��ȏ�","�N�P��ȏ�","�P�N�ȏ�݂��Ȃ�"})));
    // �p�xHeses�p�l��
    private ACParentHesesPanelContainer seishinShinkeiHindoHesesPanel = new ACParentHesesPanelContainer();
    
    // ---------------------------------------------------------------------------------------
    // �g�̃O���[�v
    private ACGroupBox shintaiGroup;
    // �����rHeses�p�l��
    private ACParentHesesPanelContainer shintaiKikiudeHesesPanel;
    // �����r���x���R���e�i
    private ACLabelContainer shintaiKikiudeLabelContainer;
    // �����r���W�I�O���[�v
    private ACClearableRadioButtonGroup shintaiKikiudeRadioGroup;
    // �����r���W�I���X�g
    private VRListModelAdapter kikiudeRadioListModel;
    // �g�����x���R���e�i
    private ACLabelContainer shintaiShinchouLabelContainer;
    // �g���e�L�X�g
    private ACTextField shintaiShinchouText;
    // �g�����x��
    private ACLabel shintaiShinchouLabel;
    // �̏d���x���R���e�i
    private ACLabelContainer shintaiTaijyuLabelContainer;
    // �̏d�e�L�X�g
    private ACTextField shintaiTaijyuText;
    // �̏d���x��
    private ACLabel shintaiTaijyuLabel;
    // �̏d�P�ʃ��x��
    private ACLabel shintaiTaijyuUnitLabel;
    // �̏dHeses�p�l��
    private ACParentHesesPanelContainer shintaiTaijyuHesesPanel;
    // �̏d���W�I�O���[�v
    private ACClearableRadioButtonGroup shintaiTaijyuRadioGroup;
    // �̏d���W�I���X�g
    private VRListModelAdapter taijyuRadioListModel;
    // ---------------------------------------------------------------------------------------
    
    public IkenshoIshiIkenshoInfoMindBody1() {
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
     * �s����Q�p�l���𐶐����܂��B
     */
    protected void buildKoudouShougaiPanel(){
        // �����܂�Ԃ��ݒ�
        koudouShogaiPanelTop.setAutoWrap(false);
        koudouShogaiPanelDown.setAutoWrap(true);
        koudouShogaiPanelDownLeftPane.setAutoWrap(true);
        koudouShogaiPanelDownRightPane.setAutoWrap(true);
        
        // ������ݒ�
        koudouSyogaiGroup.setText("�s����̏�Q�̗L���i�Y�����鍀�ڑS�ă`�F�b�N�j");
        tyuyaGyakuten.setText("����t�]");
        bougen.setText("�\��");
        boukou.setText("�\�s");
        kaigoTeikou.setText("���ւ̒�R");
        haikai.setText("�p�j");
        hinoFushimatsu.setText("�΂̕s�n��");
        fuketsuKoui.setText("�s���s��");
        isyoku.setText("�ِH");
        seitekiMondai.setText("���I�s����Q");
        sonota.setText("���̑�");
        
        // �o�C���h�p�X�ݒ�
        tyuyaGyakuten.setBindPath("KS_CHUYA");
        bougen.setBindPath("KS_BOUGEN");
        boukou.setBindPath("KS_BOUKOU");
        kaigoTeikou.setBindPath("KS_TEIKOU");
        haikai.setBindPath("KS_HAIKAI");
        hinoFushimatsu.setBindPath("KS_FUSIMATU");
        fuketsuKoui.setBindPath("KS_FUKETU");
        isyoku.setBindPath("KS_ISHOKU");
        seitekiMondai.setBindPath("KS_SEITEKI_MONDAI");
        sonota.setBindPath("KS_OTHER");
        sonotaText.setBindPath("KS_OTHER_NM");
        // �B���t���O
        koudouShogaiRadioGroup.setBindPath("MONDAI_FLAG");
        
        // �N���A�{�^������
        koudouShogaiRadioGroup.setUseClearButton(false);
        
        koudouShogaiRadioGroup.setModel(existEmptyListModel);
        // �s����̏�Q�̗L���㕔�p�l��
        koudouShogaiPanelTop.add(koudouShogaiRadioGroup,VRLayout.FLOW);
        
        //���̑��e�L�X�g�̐ݒ�
        sonotaText.setMaxLength(30);
        sonotaText.setEditable(true);
        sonotaText.setColumns(15);
        sonotaText.setIMEMode(InputSubset.KANJI);
        // ���̑��e�L�X�g���i�j�ň͂� 
        koudouShogaiSonotaHesesPanel.add(sonotaText,VRLayout.FLOW);

        // ���̑��e�L�X�g��ǉ�����B
        koudouShogaiSonotaPanel.setHgap(0);
        koudouShogaiSonotaPanel.setAutoWrap(false);
        koudouShogaiSonotaPanel.add(sonota,VRLayout.FLOW);
        koudouShogaiSonotaPanel.add(koudouShogaiSonotaHesesPanel,VRLayout.FLOW);
        koudouShogaiSonotaPanel.add(getKoudouShougaiSonotaExplanation(),VRLayout.FLOW);
        
        // �`�F�b�N�{�b�N�X�S��ǉ�-------------------------------------------------
        koudouShogaiPanelDownRightPane.add(tyuyaGyakuten,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(bougen,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(boukou,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(kaigoTeikou,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(haikai,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(hinoFushimatsu,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(fuketsuKoui,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(isyoku,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(seitekiMondai,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(koudouShogaiSonotaPanel,VRLayout.FLOW);
        //--------------------------------------------------------------------------

        // ���Q�̗L�������`�F�b�N�Q�p�l������
        koudouShogaiLabelContainer.setLayout(getKoudouShogaiLayout());
        koudouShogaiLabelContainer.setText("�i�L�̏ꍇ�j��");
        koudouShogaiLabelContainer.add(koudouShogaiPanelDownRightPane,VRLayout.CLIENT);
        
        koudouShogaiPanelDown.add(koudouShogaiLabelContainer,VRLayout.CLIENT);
        
        // �s����̏��Q�̗L���O���[�v�ɒǉ�
        koudouSyogaiGroup.add(koudouShogaiPanelTop,VRLayout.NORTH);
        koudouSyogaiGroup.add(koudouShogaiPanelDown,VRLayout.NORTH);
    }
    
    /**
     * ��ʍ\���Ɋւ��鏈�����s���܂��B
     */
    private void jbInit(){

        // ���_�E�_�o��Q�O���[�v����
        // �e�p�l���͌ʂɐݒ�
        buildKoudouShougaiPanel();
        buildSeishinShinkeiPanel();
        buildSenmoniJyushinPanel();
        buildTenkanPanel();
        buildShintaiGroup();
        
        // �z�u�֘A
        this.setLayout(new VRLayout());
        title.setText("�R�D�S�g�̏�ԂɊւ���ӌ�");
        this.add(title,VRLayout.NORTH);
        this.add(koudouSyogaiGroup,VRLayout.NORTH);
        this.add(seishinShinkeiGroup,VRLayout.NORTH);
        this.add(getShintaiGroup(),VRLayout.NORTH);
        
        // ����ΏۂƂ��Ēǉ�����
        addInnerBindComponent(seishinShinkeiShojyomeiText);
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
        seishinShinkeiShojyomeiLabel.setText("�Ǐ�");
        senmou.setText("�����");
        keiminKeikou.setText("�X���X��");
        genshiGenkaku.setText("�����E����");
        mousou.setText("�ϑz");
        shikken.setText("��������");
        shitsunin.setText("���F");
        shitsukou.setText("���s");
        ninchiShogai.setText("�F�m��Q");
        kiokuShogai.setText("�L����Q");
        tankiCheck.setText("�Z��");
        choukiCheck.setText("����");
        chuiShogai.setText("���ӏ�Q");
        suikoukinouShogai.setText("���s�@�\��Q");
        shakaitekikoudouShogai.setText("�Љ�I�s����Q");
        seishinSonota.setText("���̑�");
        
        // �o�C���h�p�X�ݒ�
        senmou.setBindPath("SS_SENMO");
        keiminKeikou.setBindPath("SS_KEMIN_KEIKO");
        genshiGenkaku.setBindPath("SS_GNS_GNC");
        mousou.setBindPath("SS_MOUSOU");
        shikken.setBindPath("SS_SHIKKEN_TOSHIKI");
        shitsunin.setBindPath("SS_SHITUNIN");
        shitsukou.setBindPath("SS_SHIKKO");
        ninchiShogai.setBindPath("SS_NINCHI_SHOGAI");
        kiokuShogai.setBindPath("SS_KIOKU_SHOGAI");
        tankiCheck.setBindPath("SS_KIOKU_SHOGAI_TANKI");
        choukiCheck.setBindPath("SS_KIOKU_SHOGAI_CHOUKI");
        chuiShogai.setBindPath("SS_CHUI_SHOGAI");
        suikoukinouShogai.setBindPath("SS_SUIKOU_KINO_SHOGAI");
        shakaitekikoudouShogai.setBindPath("SS_SHAKAITEKI_KODO_SHOGAI");
        seishinSonota.setBindPath("SS_OTHER");
        seihinShinkeiSonotaText.setBindPath("SS_OTHER_NM");
        seishinShinkeiShojyomeiText.setBindPath("SEISIN_NM");
        // ����p�t���O
        seishinShinkeiRadioGroup.setBindPath("SEISIN");
        
        // �ڍאݒ�
        seishinShinkeiShojyomeiText.setIMEMode(InputSubset.KANJI);
        seishinShinkeiShojyomeiText.setMaxLength(30);
        seishinShinkeiShojyomeiText.setColumns(30);
        
        seihinShinkeiSonotaText.setIMEMode(InputSubset.KANJI);
        seihinShinkeiSonotaText.setMaxLength(30);
        seihinShinkeiSonotaText.setColumns(30);
        
        // �L����Q
        kiokuShogaiHesesPanel.setAutoWrap(false);
        kiokuShogaiHesesPanel.setHgap(0);
        kiokuShogaiHesesPanel.add(tankiCheck,VRLayout.FLOW);
        kiokuShogaiHesesPanel.add(choukiCheck,VRLayout.FLOW);
        // �L����Q�p�l�����C�A�E�g�ݒ�
        kiokuShogaiPanelLayout.setAutoWrap(false);
        kiokuShogaiPanelLayout.setHgap(0);
        // �L����Q�p�l��
        kiokuShogaiPanel.setLayout(kiokuShogaiPanelLayout);
        kiokuShogaiPanel.add(kiokuShogai,VRLayout.FLOW);
        kiokuShogaiPanel.add(kiokuShogaiHesesPanel,VRLayout.FLOW);
        
        // �Ǐ󖼃R���{�{�b�N�X�Ɋւ���ݒ�
        seishinShinkeiShogaiHesesPanel.setAutoWrap(false);
        seishinShinkeiShogaiHesesPanel.setHgap(0);
        seishinShinkeiShogaiHesesPanel.add(seishinShinkeiShojyomeiLabel,VRLayout.FLOW);
        seishinShinkeiShogaiHesesPanel.add(seishinShinkeiShojyomeiText,VRLayout.FLOW);
        // ���W�I�O���[�v�̊ԂɃp�l����}������B

        if(seishinShinkeiRadioGroup.getLayout() instanceof FlowLayout){
            ((FlowLayout)seishinShinkeiRadioGroup.getLayout()).setHgap(0);
        }
        
        seishinShinkeiRadioGroup.add(seishinShinkeiShogaiHesesPanel,1);
        seishinShinkeiRadioGroup.add(getSeishinShoujyomeiExplanation(),2);
        
        seishinShinkeiSonotaPanel.setHgap(0);
        seishinShinkeiSonotaPanel.setAutoWrap(false);
        seishinShinkeiSonotaHesesPanel.add(seihinShinkeiSonotaText,VRLayout.FLOW);
        seishinShinkeiSonotaPanel.add(seishinSonota,VRLayout.FLOW);
        seishinShinkeiSonotaPanel.add(seishinShinkeiSonotaHesesPanel,VRLayout.FLOW);
        
        // �`�F�b�N�{�b�N�X�S�ǉ�
        seishinShinkeiShojyomeiPanelTopRightPanel.add(senmou,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(keiminKeikou,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(genshiGenkaku,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(mousou,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(shikken,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(shitsunin,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(shitsukou,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(ninchiShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(kiokuShogaiPanel,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(chuiShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(suikoukinouShogai,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelTopRightPanel.add(shakaitekikoudouShogai,VRLayout.FLOW);
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
        seishinShinkeiTenkanTitleLabel.setText("���Ă񂩂�");
        seishinShinkeiHindoLabel.setText("�p�x");
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
     
        seishinShinkeiShojyomeiPanelDownPanel.add(seishinShinkeiTenkanTitleLabel,VRLayout.FLOW_INSETLINE_RETURN);
        seishinShinkeiShojyomeiPanelDownPanel.add(seishinShinkeiTenkanRadioGroup,VRLayout.FLOW_INSETLINE_RETURN);
        seishinShinkeiShojyomeiPanelDownPanel.add(getSeishinShinkeiTenkanLabelContainer(),VRLayout.FLOW);
        // �O���[�v�ɒǉ�����B
        seishinShinkeiGroup.add(seishinShinkeiShojyomeiPanelDownPanel,VRLayout.NORTH);

    }
    
    /**
     * �g�̃O���[�v�𐶐����܂��B
     */
    protected void buildShintaiGroup(){
        // ���W�I�O���[�v���i�j�ň͂�
        getShintaiKikiudeHesesPanel().add(getShintaiKikiudeRadioGroup(),VRLayout.FLOW);
        // �̏d���x���R���e�i�쐬
        getShintaiKikiudeLabelContainer().add(getShintaiKikiudeHesesPanel(),VRLayout.FLOW);
        
        // �g�̃��x���R���e�i�쐬
        getShintaiShinchouLabelContainer().add(getShintaiShinchouText(),VRLayout.FLOW);
        getShintaiShinchouLabelContainer().add(getShintaiShinchouLabel(),VRLayout.FLOW);
        
        // �̏d���W�I�O���[�v���i�j�ň͂�
        getShintaiTaijyuHesesPanel().add(getShintaiTaijyuLabel(),VRLayout.FLOW);
        getShintaiTaijyuHesesPanel().add(getShintaiTaijyuRadioGroup(),VRLayout.FLOW);
        // �̏d���x���R���e�i�𐶐�
        getShintaiTaijyuLabelContainer().add(getShintaiTaijyuText(),VRLayout.FLOW);
        getShintaiTaijyuLabelContainer().add(getShintaiTaijyuUnitLabel(),VRLayout.FLOW);
        getShintaiTaijyuLabelContainer().add(getShintaiTaijyuHesesPanel(),VRLayout.FLOW);
        
        // �O���[�v���ɒǉ�
        getShintaiGroup().add(getShintaiKikiudeLabelContainer(),VRLayout.FLOW_RETURN);
        getShintaiGroup().add(getShintaiShinchouLabelContainer(),VRLayout.FLOW);
        getShintaiGroup().add(getShintaiTaijyuLabelContainer(),VRLayout.FLOW);
    }
    
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        // �s����Q�̗L�����W�I�O���[�v
        if (tyuyaGyakuten.isSelected() || bougen.isSelected()
                || boukou.isSelected() || kaigoTeikou.isSelected()
                || haikai.isSelected() || hinoFushimatsu.isSelected()
                || fuketsuKoui.isSelected() || isyoku.isSelected()
                || seitekiMondai.isSelected() || sonota.isSelected()) {
            koudouShogaiRadioGroup.setSelectedIndex(1);
        }else{
            koudouShogaiRadioGroup.setSelectedIndex(2);
        }
        // ���_�E�_�o�Ǐ�̗L��
        if (!ACTextUtilities.isNullText(seishinShinkeiShojyomeiText.getText())
                || senmou.isSelected() || keiminKeikou.isSelected()
                || genshiGenkaku.isSelected() || mousou.isSelected()
                || shikken.isSelected() || shitsunin.isSelected()
                || shitsukou.isSelected() || ninchiShogai.isSelected()
                || kiokuShogai.isSelected() || chuiShogai.isSelected()
                || suikoukinouShogai.isSelected()
                || shakaitekikoudouShogai.isSelected()
                || seishinSonota.isSelected()) {
            seishinShinkeiRadioGroup.setSelectedIndex(1);
        }else{
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

    }
    
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
        /*
         * �s����Q�̗L�����W�I�O���[�v
         */
        koudouShogaiRadioGroup.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    return;
                }
                boolean enable;

                int select = koudouShogaiRadioGroup.getSelectedIndex();
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
                koudouSyogaiRadioGroupChangeAction(enable);
            }       
        });
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
         * �����M�̗L�����W�I�O���[�v
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
         * ���̑��`�F�b�N�I����
         */
        sonota.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                koudouSyogaiSonotaChangeState();
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
        /*
         * �L����Q�`�F�b�N�I����
         */
        kiokuShogai.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                kiokuShogaiChangeState();
            }            
        });
    }
    
    /**
     * �s����Q���W�I�O���[�v�I�����̉�ʐ���
     */
    protected void koudouSyogaiRadioGroupChangeAction(boolean enable){
        // Enable����
        koudouShogaiPanelDownRightPane.setEnabled(enable);
        koudouShogaiPanelDown.setEnabled(enable);
        tyuyaGyakuten.setEnabled(enable);
        bougen.setEnabled(enable);
        boukou.setEnabled(enable);
        kaigoTeikou.setEnabled(enable);
        haikai.setEnabled(enable);
        hinoFushimatsu.setEnabled(enable);
        fuketsuKoui.setEnabled(enable);
        isyoku.setEnabled(enable);
        seitekiMondai.setEnabled(enable);
        sonota.setEnabled(enable);
        koudouShogaiLabelContainer.setEnabled(enable);
        // ���̑��e�L�X�g�̂ݐ���
        koudouSyogaiSonotaChangeState();
    }
    
    /**
     * �s����Q-���̑��R���{��Ԑ���
     * @param enable
     */
    protected void koudouSyogaiSonotaChangeState(){
        // ���̑��p�l�����L���A���̑��`�F�b�N�L���̏ꍇ�́A���̑��p�l����L���ɂ���B
        boolean enable;
        if(sonota.isSelected()&&sonota.isEnabled()){
            enable = true;
        }else{
            enable = false;
        }
        koudouShogaiSonotaPanel.setEnabled(enable);
        koudouShogaiSonotaHesesPanel.setEnabled(enable);
        sonotaText.setEnabled(enable);
    }
    
    /**
     * ���_�E�_�o�Ǐ�̗L�����W�I�O���[�v�I�����̉�ʐ���ł��B
     * @param enable
     */
    protected void seishinShinkeiRadioGroupChangeAction(boolean enable){
        senmou.setEnabled(enable);
        keiminKeikou.setEnabled(enable);
        genshiGenkaku.setEnabled(enable);
        mousou.setEnabled(enable);
        shikken.setEnabled(enable);
        shitsunin.setEnabled(enable);
        shitsukou.setEnabled(enable);
        ninchiShogai.setEnabled(enable);
        kiokuShogai.setEnabled(enable);
        chuiShogai.setEnabled(enable);
        suikoukinouShogai.setEnabled(enable);
        shakaitekikoudouShogai.setEnabled(enable);
        seishinSonota.setEnabled(enable);
        seishinShinkeiShogaiHesesPanel.setEnabled(enable);
        seishinShinkeiShojyomeiLabel.setEnabled(enable);
        seishinShinkeiShojyomeiText.setEnabled(enable);
        seishinSonota.setEnabled(enable);
        getSeishinShinkeiSyojyoLabelConainer().setEnabled(enable);
        
        getSeishinShinkeiSenmoniLabelContainer().setEnabled(enable);
        seishinShinkeiSenmoniRadioGroup.setEnabled(enable);
        
        //���̑��ʏ���
        seishinShinkeiSonotaChangeState();
        kiokuShogaiChangeState();
        seishinShinkeiSenmoniChangeAction();
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
     * �L����Q - ���W�I�O���[�v��Ԑ���
     */
    protected void kiokuShogaiChangeState(){
        boolean enable;
        // �L����Q�L�� + �`�F�b�N�L��̏ꍇ
        if(kiokuShogai.isEnabled() && kiokuShogai.isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        kiokuShogaiHesesPanel.setEnabled(enable);
        choukiCheck.setEnabled(enable);
        tankiCheck.setEnabled(enable);
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

    /**
     * �s����̏��Q�̗L���O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACGroupBox getKoudouSyogaiGroup() {
        return koudouSyogaiGroup;
    }

    protected VRLayout getKoudouShogaiLayout() {
        if(koudouShogaiLayout == null){
            koudouShogaiLayout = new VRLayout();
            koudouShogaiLayout.setAutoWrap(false);
        }
        return koudouShogaiLayout;
    }

    protected ACLabelContainer getSeishinShinkeiSenmoniLabelContainer() {
        if(seishinShinkeiSenmoniLabelContainer == null){
            seishinShinkeiSenmoniLabelContainer = new ACLabelContainer();
            seishinShinkeiSenmoniLabelContainer.setText("�E�����f�̗L��");
        }
        return seishinShinkeiSenmoniLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiTenkanLabelContainer() {
        if(seishinShinkeiTenkanLabelContainer == null){
            seishinShinkeiTenkanLabelContainer = new ACLabelContainer();
            seishinShinkeiTenkanLabelContainer.setText("�i�L�̏ꍇ�j��");
        }
        return seishinShinkeiTenkanLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiSyojyoLabelConainer() {
        if(seishinShinkeiShojyoLabelConainer == null){
            seishinShinkeiShojyoLabelConainer = new ACLabelContainer();
            seishinShinkeiShojyoLabelConainer.setText("�i�L�̏ꍇ�j��");
            seishinShinkeiShojyoLabelConainer.setLayout(new VRLayout());
        }
        return seishinShinkeiShojyoLabelConainer;
    }
    /**
     * �����r�R���{�����擾���܂��B
     * @return
     */
    protected VRListModelAdapter getKikiudeRadioListModel() {
        if(kikiudeRadioListModel == null){
            kikiudeRadioListModel = new VRListModelAdapter();
            kikiudeRadioListModel = new VRListModelAdapter(
                    new VRArrayList(Arrays.asList(new String[] { "�E", "��" })));
        }
        return kikiudeRadioListModel;
    }
    /**
     * �g�̂̏�ԃO���[�v�{�b�N�X��Ԃ��܂��B
     * @return
     */
    protected ACGroupBox getShintaiGroup() {
        if(shintaiGroup == null){
            shintaiGroup = new ACGroupBox();
            VRLayout shintaiLayout = new VRLayout();
            shintaiLayout.setAutoWrap(false);
            shintaiGroup.setText("�g�̂̏��");
            shintaiGroup.setLayout(shintaiLayout);
        }
        return shintaiGroup;
    }

    protected ACParentHesesPanelContainer getShintaiKikiudeHesesPanel() {
        if(shintaiKikiudeHesesPanel == null){
            shintaiKikiudeHesesPanel = new ACParentHesesPanelContainer();
        }
        return shintaiKikiudeHesesPanel;
    }
    /**
     * �����r���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getShintaiKikiudeLabelContainer() {
        if(shintaiKikiudeLabelContainer == null){
            shintaiKikiudeLabelContainer = new ACLabelContainer();
            shintaiKikiudeLabelContainer.setText("�����r");
        }
        return shintaiKikiudeLabelContainer;
    }
    /**
     * �����r���W�I�O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACClearableRadioButtonGroup getShintaiKikiudeRadioGroup() {
        if(shintaiKikiudeRadioGroup == null){
            shintaiKikiudeRadioGroup = new ACClearableRadioButtonGroup();
            shintaiKikiudeRadioGroup.setBindPath("KIKIUDE");
            // ���W�I�I������ǉ�
            getShintaiKikiudeRadioGroup().setModel(getKikiudeRadioListModel());
        }
        return shintaiKikiudeRadioGroup;
    }
    /**
     * �g���P�ʂ�Ԃ��܂��B
     * @return
     */
    protected ACLabel getShintaiShinchouLabel() {
        if(shintaiShinchouLabel == null){
            shintaiShinchouLabel = new ACLabel();
            shintaiShinchouLabel.setText("cm");
        }
        return shintaiShinchouLabel;
    }
    /**
     * �g�����x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getShintaiShinchouLabelContainer() {
        if(shintaiShinchouLabelContainer == null){
            shintaiShinchouLabelContainer = new ACLabelContainer();
            shintaiShinchouLabelContainer.setText("�g��=");
            shintaiShinchouLabelContainer.setHgap(0);
        }
        return shintaiShinchouLabelContainer;
    }
    /**
     * �g���e�L�X�g��Ԃ��܂��B
     * @return
     */
    protected ACTextField getShintaiShinchouText() {
        if(shintaiShinchouText == null){
            shintaiShinchouText = new ACTextField();
            shintaiShinchouText.setMaxLength(5);
            shintaiShinchouText.setColumns(6);
            shintaiShinchouText.setHorizontalAlignment(SwingConstants.RIGHT);
            shintaiShinchouText.setBindPath("HEIGHT");
        }
        return shintaiShinchouText;
    }
    /**
     * �̏dHeses�p�l����Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getShintaiTaijyuHesesPanel() {
        if(shintaiTaijyuHesesPanel == null){
            shintaiTaijyuHesesPanel = new ACParentHesesPanelContainer();
            shintaiTaijyuHesesPanel.setAutoWrap(false);
        }
        return shintaiTaijyuHesesPanel;
    }
    /**
     * �̏d�̕ω����x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getShintaiTaijyuLabel() {
        if(shintaiTaijyuLabel == null){
            shintaiTaijyuLabel = new ACLabel();
            shintaiTaijyuLabel.setText("�ߋ�6�����̑̏d�̕ω�");
        }
        return shintaiTaijyuLabel;
    }
    /**
     * �̏d���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getShintaiTaijyuLabelContainer() {
        if(shintaiTaijyuLabelContainer == null){
            shintaiTaijyuLabelContainer = new ACLabelContainer();
            shintaiTaijyuLabelContainer.setText("�̏d=");
            shintaiTaijyuLabelContainer.setAutoWrap(false);
            shintaiTaijyuLabelContainer.setHgap(0);
        }
        return shintaiTaijyuLabelContainer;
    }
    /**
     * �ߋ�6�����̑̏d�ω����W�I�O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACClearableRadioButtonGroup getShintaiTaijyuRadioGroup() {
        if(shintaiTaijyuRadioGroup == null){
            shintaiTaijyuRadioGroup = new ACClearableRadioButtonGroup();
            // �I�����ݒ�
            shintaiTaijyuRadioGroup.setModel(getTaijyuRadioListModel());
            shintaiTaijyuRadioGroup.setBindPath("WEIGHT_CHANGE");
        }
        return shintaiTaijyuRadioGroup;
    }
    /**
     * �̏d�e�L�X�g��Ԃ��܂��B
     * @return
     */
    protected ACTextField getShintaiTaijyuText() {
        if(shintaiTaijyuText == null){
            shintaiTaijyuText = new ACTextField();
            shintaiTaijyuText.setColumns(6);
            shintaiTaijyuText.setHorizontalAlignment(SwingConstants.RIGHT);
            shintaiTaijyuText.setBindPath("WEIGHT");
            shintaiTaijyuText.setMaxLength(5);
        }
        return shintaiTaijyuText;
    }
    /**
     * �ߋ�6�����̑̏d�ω����Ԃ��܂��B
     * @return
     */
    protected VRListModelAdapter getTaijyuRadioListModel() {
        if(taijyuRadioListModel == null){
            taijyuRadioListModel = new VRListModelAdapter();
            taijyuRadioListModel = new VRListModelAdapter(
                    new VRArrayList(Arrays.asList(new String[] { "����", "�ێ�","����" })));
        }
        return taijyuRadioListModel;
    }
    /**
     * �̏d-�P�ʃ��x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getShintaiTaijyuUnitLabel() {
        if(shintaiTaijyuUnitLabel == null){
            shintaiTaijyuUnitLabel = new ACLabel();
            shintaiTaijyuUnitLabel.setText("kg");
        }
        return shintaiTaijyuUnitLabel;
    }

    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        applyPoolTeikeibun(seishinShinkeiShojyomeiText, IkenshoCommon.TEIKEI_ISHI_MIND_SICK_NAME);
        // applyPoolTeikeibun(seihinShinkeiSonotaText, IkenshoCommon.TEIKEI_ISHI_MIND_SICK_OTHER_NAME);

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
        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        seishinShinkeiShojyomeiText.setOptionComboBoxParameters("���_�E�_�o�Ǐ�",
                IkenshoCommon.TEIKEI_ISHI_MIND_SICK_NAME, 30);
        // 2007/10/18 [Masahiko Higuchi] Addition - end
        
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
        // �s����̏��Q�̗L���`�F�b�N
        if (koudouShogaiRadioGroup.getSelectedIndex() == 1) {
            if (!(tyuyaGyakuten.isSelected() || bougen.isSelected()
                    || boukou.isSelected() || kaigoTeikou.isSelected()
                    || haikai.isSelected() || hinoFushimatsu.isSelected()
                    || fuketsuKoui.isSelected() || isyoku.isSelected()
                    || seitekiMondai.isSelected() || sonota.isSelected())) {
                ACMessageBox.showExclamation("�u�s����̏�Q�v�Ŗ��L��������܂��B");
                koudouShogaiRadioGroup.requestChildFocus();
                return false;            
            }
        
            // ���̑��`�F�b�N���L���ł���ꍇ
            if(sonota.isSelected()){
                if (IkenshoCommon.isNullText(sonotaText.getText())) {
                    ACMessageBox.showExclamation("�u�s����̏�Q�i���̑��j�v�Ŗ��L��������܂��B");
                    sonota.requestFocus();
                    return false;
                }
            }
        }
        // ���_�E�_�o�Ǐ�G���[�`�F�b�N
        if (seishinShinkeiRadioGroup.getSelectedIndex() == 1) {
            if (!(senmou.isSelected() || keiminKeikou.isSelected()
                    || genshiGenkaku.isSelected() || mousou.isSelected()
                    || shikken.isSelected() || shitsunin.isSelected()
                    || shitsukou.isSelected() || ninchiShogai.isSelected()
                    || kiokuShogai.isSelected() || chuiShogai.isSelected()
                    || suikoukinouShogai.isSelected()
                    || shakaitekikoudouShogai.isSelected() || seishinSonota
                    .isSelected())) {
                // �`�F�b�N�{�b�N�X���S�ċ󔒂Ŋ��e�L�X�g�����L���ł���ꍇ
                if(ACTextUtilities.isNullText(seishinShinkeiShojyomeiText.getText())){
                    ACMessageBox.showExclamation("�u���_�E�_�o�Ǐ�v�Ŗ��L��������܂��B");
                    seishinShinkeiRadioGroup.requestChildFocus();
                    return false;
                }
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
     * �Ǐ󖼍s���������x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getSeishinShoujyomeiExplanation() {
        if(seishinShoujyomeiExplanation == null){
            seishinShoujyomeiExplanation = new ACLabel();
            seishinShoujyomeiExplanation.setText("�i30�����ȓ��j");
            seishinShoujyomeiExplanation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return seishinShoujyomeiExplanation;
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
