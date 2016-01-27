package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoHintButton;
import jp.or.med.orca.ikensho.component.IkenshoHintContainer;
import jp.or.med.orca.ikensho.component.IkenshoInnerComboBoxContainar;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoActionAndMind1�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 */
public class IkenshoIshiIkenshoInfoActionAndMind1 extends IkenshoTabbableChildAffairContainer {
	
    private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
    // �s����̏�Q�̗L���O���[�v(����)
    private ACGroupBox koudouSyogaiGroup = new ACGroupBox();
    //���_�E�_�o�Ǐ�̗L���O���[�v(����)
    //private ACGroupBox seishinShinkeiGroup = new ACGroupBox();
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
    private ACIntegerCheckBox jisyou = new ACIntegerCheckBox();
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
    
    // �s����Q �������������x��
    private ACLabel koudouShougaiSonotaExplanation;
    // �i�j�p�l��
    private ACParentHesesPanelContainer koudouShogaiSonotaHesesPanel = new ACParentHesesPanelContainer(); 
    // �s����̏�Q�̗L�����W�I�O���[�v
    private ACClearableRadioButtonGroup koudouShogaiRadioGroup = new ACClearableRadioButtonGroup();
    
    
    // -----------------------------------------------------------------
    // ���_�Ǐ�E�\�͏�Q�񎲕]��
    // ������Q�]��
    // ���_��Q�̋@�\�]�����x��
    private ACLabelContainer spiritShogaiLabelCotainar;
    // �����_�Ǐ�E�\�͏�Q�񎲕]�����x��
    private ACLabelContainer spiritAndAbilityLabel;
    // �i�j���_�Ǐ�E�\�͏�Q�񎲕]��Heses�p�l��
    private ACParentHesesPanelContainer spiritAndAbilityHesesPanel;
    // ���_�Ǐ�O���[�v
    private ACGroupBox spiritShogaiGroup;
    // ���_�Ǐ�R���{�R���e�i
    private IkenshoInnerComboBoxContainar spiritInnerComboBox;
    // �\�͏�Q�R���{�R���e�i
    private IkenshoInnerComboBoxContainar abilityInnerContainar;
    // ���_�Ǐ�E���莞��
    private ACLabelContainer spiritJudgmentTimeContainar;
    // ���_�Ǐ�E���t
    private IkenshoEraDateTextField spiritEraDate;
    // ��Q�O���[�v���C�A�E�g
    private VRLayout shogaiGroupLayout;
    //���_�Ǐ�R���e�i���C�A�E�g
    private VRLayout spiritContainarLayout;
    // ------------------------------------------------------------------
    // ������Q�R���e�i
    private ACLabelContainer seikatsuShogaiContainar;
    // ������Q�]���i�j���x���J�n
    private ACLabel seikatsuShogaiHesesStart;
    // ������Q�]���i�j���x���I��
    private ACLabel seikatsuShogaiHesesEnd;
    // Heses�p���C�A�E�g
    private VRLayout seikatsuShogaiLayout;
    // �H��
    private IkenshoInnerComboBoxContainar seikatsuShokujiInnerContainar;
    // �������Y��
    private IkenshoInnerComboBoxContainar seikatsuSeikatsuRizumuInnerContainar;
    // �ې�
    private IkenshoInnerComboBoxContainar seikatsuHoseiInnerContainar;
    // ���K�Ǘ�
    private IkenshoInnerComboBoxContainar seikatsuKinsenInnerContainar;
    // ����Ǘ�
    private IkenshoInnerComboBoxContainar seikatsuFukuyakuInnerContainar;
    // �ΐl�֌W
    private IkenshoInnerComboBoxContainar seikatsuTaijinInnerContainar;
    // �Љ�I�K��
    private IkenshoInnerComboBoxContainar seikatsuSyakaiTekiouInnerContainar;
    // ������Q�E���f����
    private ACLabelContainer seikatsuTimeContainar;
    // ������Q�E���t
    private IkenshoEraDateTextField seikatsuEraDate;
    
    private ACLabelContainer seikatsuDateContainar;
    
    private IkenshoDocumentTabTitleLabel titleLabel;
    // �������\���p�p�l��
    private IkenshoHintContainer hintPanel;
    // �������p�l���p�O���[�v
    private ACGroupBox hintGroup;
    // �@�\�]���R���{���
    private static String[] MAXIMUM_VALUE5 = new String[] {" ","1", "2", "3",
            "4", "5" };
    private static Integer[] MAPS_MAXIMUM_VALUE5 = new Integer[] {
             new Integer(0), new Integer(1), new Integer(2), new Integer(3),
            new Integer(4),new Integer(5)};
    // �@�\�]���R���{���
    private static String[] MAXIMUM_VALUE6 = new String[] {" ","1", "2", "3",
            "4", "5", "6" };
    private static Integer[] MAPS_MAXIMUM_VALUE6 = new Integer[] {
            new Integer(0),new Integer(1), new Integer(2), new Integer(3),
            new Integer(4), new Integer(5),new Integer(6)};
    
    
    //�q���g�\���̈�
    private ACGroupBox hiddenParameters = new ACGroupBox();
    
    public IkenshoIshiIkenshoInfoActionAndMind1() {
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
        jisyou.setText("����");
        boukou.setText("���Q");
        kaigoTeikou.setText("�x���ւ̒�R");
        haikai.setText("�p�j");
        hinoFushimatsu.setText("�댯�̔F��������");
        fuketsuKoui.setText("�s���s��");
        isyoku.setText("�ِH");
        seitekiMondai.setText("���I��E�s��");
        sonota.setText("���̑�");
        
        // �o�C���h�p�X�ݒ�
        tyuyaGyakuten.setBindPath("KS_CHUYA");
        bougen.setBindPath("KS_BOUGEN");
        jisyou.setBindPath("KS_JISYOU");
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
        sonotaText.setColumns(30);
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
        koudouShogaiPanelDownRightPane.add(jisyou,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(boukou,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(kaigoTeikou,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(haikai,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(hinoFushimatsu,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(fuketsuKoui,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(isyoku,VRLayout.FLOW);
        koudouShogaiPanelDownRightPane.add(seitekiMondai,VRLayout.FLOW_RETURN);
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
        
        // ���_�Ǐ�E�\�͏�Q�񎲕]��
        buildSpiritSyogaiGroup();
        // ������Q�]��
        buildSeikatsuSyogaiGroup();
        
        // �z�u�֘A
        this.setLayout(new VRLayout());
        title.setText("�R�D�s���y�ѐ��_���̏�ԂɊւ���ӌ�");
        this.add(title,VRLayout.NORTH);
        this.add(koudouSyogaiGroup,VRLayout.NORTH);
        
        // ���_��Q�̋@�\�]���O���[�v
        this.add(getSpiritShogaiGroup(),VRLayout.NORTH);
        // �B���p�����[�^�O���[�v
        this.add(getHiddenParameters(),VRLayout.NORTH);
        // �q���g�̈�
        this.add(getHintGroup(),VRLayout.CLIENT);
        
        getHiddenParameters().setVisible(false);
        getHintGroup().setVisible(false);
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
         * ���̑��`�F�b�N�I����
         */
        sonota.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                koudouSyogaiSonotaChangeState();
            }
        });
        
        
        
        // ���R���|�[�l���g�̕\���ؑ֐ݒ�
        getSpiritInnerComboBox().getHintButton().setFollowHideComponents(
                new JComponent[] { koudouSyogaiGroup, getSeikatsuShogaiContainar() });
        
        getAbilityInnerContainar().getHintButton().setFollowHideComponents(
                new JComponent[] { koudouSyogaiGroup, getSeikatsuShogaiContainar() });
        
        getSeikatsuShokujiInnerContainar().getHintButton() .setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });
        
        getSeikatsuSeikatsuRizumuInnerContainar().getHintButton().setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });
        
        getSeikatsuHoseiInnerContainar().getHintButton().setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });
        
        getSeikatsuKinsenInnerContainar().getHintButton().setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });
        
        getSeikatsuFukuyakuInnerContainar().getHintButton().setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });
        
        getSeikatsuTaijinInnerContainar().getHintButton().setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });
        
        getSeikatsuSyakaiTekiouInnerContainar().getHintButton().setFollowHideComponents(
        		new JComponent[] { koudouSyogaiGroup, getSpiritShogaiLabelContainar() });

        // ����������Ԑ���
        getSpiritInnerComboBox().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton() });
        getAbilityInnerContainar().getHintButton().setFollowPressedButtons(
                new IkenshoHintButton[] {
                        getSpiritInnerComboBox().getHintButton(),
                        getSeikatsuShokujiInnerContainar().getHintButton(),
                        getSeikatsuSeikatsuRizumuInnerContainar()
                                .getHintButton(),
                        getSeikatsuHoseiInnerContainar().getHintButton(),
                        getSeikatsuKinsenInnerContainar().getHintButton(),
                        getSeikatsuFukuyakuInnerContainar().getHintButton(),
                        getSeikatsuTaijinInnerContainar().getHintButton(),
                        getSeikatsuSyakaiTekiouInnerContainar().getHintButton()

                });
        getSeikatsuShokujiInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton()
                        });
        getSeikatsuSeikatsuRizumuInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton()
                        });
        getSeikatsuHoseiInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton()
                        });
        getSeikatsuKinsenInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton()
                        });
        getSeikatsuFukuyakuInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton()
                        });
        getSeikatsuTaijinInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton()
                        });
        getSeikatsuSyakaiTekiouInnerContainar().getHintButton()
                .setFollowPressedButtons(
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton() });
        

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
        jisyou.setEnabled(enable);
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
                    || seitekiMondai.isSelected() || sonota.isSelected()
                    || jisyou.isSelected())) {
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
        
        // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
        // �`�F�b�N����Ώۊ��Ԃ̏����l��ݒ�
    	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(getSpiritEraDate().getEra()) && !"".equals(getSpiritEraDate().getYear())){
        	// �`�F�b�N�p�̑Ώۊ��Ԑݒ�ɕύX����B
        	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        // 2008/01/15 [Masahiko Higuchi] add - end
    	
            // ���_�Ǐ�-���莞���`�F�b�N
            switch (getSpiritEraDate().getInputStatus()) {
            case IkenshoEraDateTextField.STATE_EMPTY:
            case IkenshoEraDateTextField.STATE_VALID:
	            // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                break;
            case IkenshoEraDateTextField.STATE_FUTURE:
                ACMessageBox.showExclamation("�����̓��t�ł��B");
                getSpiritEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            default:
                ACMessageBox.show("���t�Ɍ�肪����܂��B");
                getSpiritEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            }
        // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
        }
        // 2008/01/15 [Masahiko Higuchi] add - end
        
        
        // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
        // �`�F�b�N����Ώۊ��Ԃ̏����l��ݒ�
        getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(getSeikatsuEraDate().getEra()) && !"".equals(getSeikatsuEraDate().getYear())){
        	// �`�F�b�N�p�̑Ώۊ��Ԑݒ�ɕύX����B
        	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        // 2008/01/15 [Masahiko Higuchi] add - end
        
            // ������Q-���f�����`�F�b�N
            switch (getSeikatsuEraDate().getInputStatus()) {
            case IkenshoEraDateTextField.STATE_EMPTY:
            case IkenshoEraDateTextField.STATE_VALID:
	            // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                break;
            case IkenshoEraDateTextField.STATE_FUTURE:
                ACMessageBox.showExclamation("�����̓��t�ł��B");
                getSeikatsuEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            default:
                ACMessageBox.show("���t�Ɍ�肪����܂��B");
                getSeikatsuEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            }

	    // 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
        }
        // 2008/01/15 [Masahiko Higuchi] add - end
        

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
     * 
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        // �q���g�̈��o�^���܂�
        IkenshoCommon
                .setHintButtons(dbm, new String[] { "1", "2", "3", "4", "5",
                        "6", "7", "8", "9" }, getFomratKubun(),
                        new IkenshoHintButton[] {
                                getSpiritInnerComboBox().getHintButton(),
                                getAbilityInnerContainar().getHintButton(),
                                getSeikatsuShokujiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSeikatsuRizumuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuHoseiInnerContainar()
                                        .getHintButton(),
                                getSeikatsuKinsenInnerContainar()
                                        .getHintButton(),
                                getSeikatsuFukuyakuInnerContainar()
                                        .getHintButton(),
                                getSeikatsuTaijinInnerContainar()
                                        .getHintButton(),
                                getSeikatsuSyakaiTekiouInnerContainar()
                                        .getHintButton() });
    }
    
    
    
    /**
     * �q���g�{�^���ƘA�����Ė����ɂȂ�R���g���[���W����ݒ肵�܂��B
     *
     * @param components �q���g�{�^���ƘA�����Ė����ɂȂ�R���g���[���W��
     * @throws Exception ������O
     */
    public void setFollowDisabledComponents(JComponent[] components) {
      getSpiritInnerComboBox().getHintButton().setFollowDisabledComponents(components);
      getAbilityInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuShokujiInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuSeikatsuRizumuInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuHoseiInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuHoseiInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuFukuyakuInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuTaijinInnerContainar().getHintButton().setFollowDisabledComponents(components);
      getSeikatsuSyakaiTekiouInnerContainar().getHintButton().setFollowDisabledComponents(components);
    }

    
    
    /**
     * ���_��Q�̕]���@�\�O���[�v�𐶐����܂��B
     */
    protected void buildSpiritSyogaiGroup(){
        
        getSpiritShogaiGroup().setText("���_��Q�̋@�\�]��");
        // ���C�A�E�g�K�p
        getSpiritShogaiGroup().setLayout(getShogaiGroupLayout());
        getSpiritShogaiLabelContainar().setLayout(getSpiritContainarLayout());
        
        // �w�ʐF�h��ݒ�
        getSpiritShogaiLabelContainar().setContentAreaFilled(true);
        
        // �����F�E�w�i�F��ݒ肷��B
        getSpiritShogaiLabelContainar().setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        getSpiritAndAbilityHesesPanel().setForeground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        getSpiritShogaiLabelContainar().setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        
        // ������ݒ�
        getSpiritShogaiLabelContainar().setText("���_�Ǐ�E�\�͏�Q�񎲕]��");

        // �i�j�p�l���ɒǉ�
        getSpiritAndAbilityHesesPanel().add(getSpiritInnerComboBox(),VRLayout.CLIENT);
        getSpiritAndAbilityHesesPanel().add(getAbilityInnerContainar(),VRLayout.CLIENT);
        
        // ���莞���R���e�i�ɒǉ�
        getSpiritJudgmentTimeContainar().add(getSpiritEraDate(),VRLayout.FLOW);
        
        // ���_�Ǐ�R���e�i�ǉ�
        getSpiritShogaiLabelContainar().add(getSpiritAndAbilityHesesPanel(),VRLayout.FLOW_RETURN);
        getSpiritShogaiLabelContainar().add(getSpiritJudgmentTimeContainar(),VRLayout.FLOW);
        // ���_��Q�O���[�v�{�b�N�X�ɒǉ�
        getSpiritShogaiGroup().add(getSpiritShogaiLabelContainar(),VRLayout.NORTH);
        
    }
    
    /**
     * ������Q�]���O���[�v�𐶐����܂��B
     */
    protected void buildSeikatsuSyogaiGroup(){
        
        getSeikatsuShogaiContainar().add(getSeikatsuShokujiInnerContainar(),VRLayout.FLOW_INSETLINE);
        getSeikatsuShogaiContainar().add(getSeikatsuSeikatsuRizumuInnerContainar(),VRLayout.FLOW_INSETLINE);
        getSeikatsuShogaiContainar().add(getSeikatsuHoseiInnerContainar(),VRLayout.FLOW_INSETLINE);
        getSeikatsuShogaiContainar().add(getSeikatsuKinsenInnerContainar(),VRLayout.FLOW_INSETLINE);
        getSeikatsuShogaiContainar().add(getSeikatsuFukuyakuInnerContainar(),VRLayout.FLOW_INSETLINE);
        getSeikatsuShogaiContainar().add(getSeikatsuTaijinInnerContainar(),VRLayout.FLOW_INSETLINE);
        getSeikatsuShogaiContainar().add(getSeikatsuSyakaiTekiouInnerContainar(),VRLayout.FLOW_INSETLINE);
        
        getSeikatsuDateContainar().add(getSeikatsuEraDate(),VRLayout.FLOW);
        getSeikatsuShogaiContainar().add(getSeikatsuDateContainar(),VRLayout.FLOW_INSETLINE);
        // ���_��Q�O���[�v�{�b�N�X�ɒǉ�
        getSpiritShogaiGroup().add(getSeikatsuShogaiContainar(),VRLayout.NORTH);
        
    }



    
    /***
     * ���_�E�\�͏�QHeses�p�l���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getSpiritAndAbilityHesesPanel() {
        if(spiritAndAbilityHesesPanel == null){
            spiritAndAbilityHesesPanel = new ACParentHesesPanelContainer();
        }
        return spiritAndAbilityHesesPanel;
    }
    /**
     * ���_�Ǐ�E�\�͏�Q�񎲕]�����x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getSpiritAndAbilityLabel() {
        if(spiritAndAbilityLabel == null){
            spiritAndAbilityLabel = new ACLabelContainer();
        }
        return spiritAndAbilityLabel;
    }
    /**
     * ���_��Q���x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getSpiritShogaiLabelContainar() {
        if(spiritShogaiLabelCotainar == null){
            spiritShogaiLabelCotainar = new ACLabelContainer();
        }
        return spiritShogaiLabelCotainar;
    }
    /**
     * ���_��Q�O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACGroupBox getSpiritShogaiGroup() {
        if(spiritShogaiGroup == null){
            spiritShogaiGroup = new ACGroupBox();
        }
        return spiritShogaiGroup;
    }
    /**
     * �\�͏�Q�R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getAbilityInnerContainar() {
        if(abilityInnerContainar == null){
            abilityInnerContainar = new IkenshoInnerComboBoxContainar();
            abilityInnerContainar.setText("�\�͏�Q�]��");
            abilityInnerContainar.setInnerComboModel("SK_NIJIKU_NORYOKU",
                    "RENDER_SK_NIJIKU_NORYOKU",MAPS_MAXIMUM_VALUE5 ,
                    MAXIMUM_VALUE5);
            abilityInnerContainar.setComboBindPath("SK_NIJIKU_NORYOKU");
            abilityInnerContainar.setComboRenderBindPath("RENDER_SK_NIJIKU_NORYOKU");
            // �q���g�\���̈��ݒ肷��B
            abilityInnerContainar.getHintButton().setHintArea(getHintPanel());
            abilityInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return abilityInnerContainar;
    }
    /**
     * ���_�Ǐ�R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSpiritInnerComboBox() {
        if(spiritInnerComboBox == null){
            spiritInnerComboBox = new IkenshoInnerComboBoxContainar();
            spiritInnerComboBox.setText("���_�Ǐ�]��");
            spiritInnerComboBox.setInnerComboModel("SK_NIJIKU_SEISHIN",
                    "RENDER_SK_NIJIKU_SEISHIN",  MAPS_MAXIMUM_VALUE6,
                   MAXIMUM_VALUE6);
            spiritInnerComboBox.setComboBindPath("SK_NIJIKU_SEISHIN");
            spiritInnerComboBox.setComboRenderBindPath("RENDER_SK_NIJIKU_SEISHIN");
            spiritInnerComboBox.getHintButton().setHintArea(getHintPanel());
            spiritInnerComboBox.getHintButton().setHintContainer(getHintGroup());
        }
        return spiritInnerComboBox;
    }
    /**
     * ���莞���R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoEraDateTextField getSpiritEraDate() {
        if(spiritEraDate == null){
            spiritEraDate = new IkenshoEraDateTextField();
            // ���t�R���|�[�l���g�̐ݒ�
            spiritEraDate.setAgeVisible(false);
            spiritEraDate.setDayVisible(false);
            spiritEraDate.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            spiritEraDate.setBindPath("SK_NIJIKU_DT");
            // �\���̈�𕽐��Ƌ󔒂݂̂ɐݒ�
            spiritEraDate.setEraRange(3);
            
    		// 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            spiritEraDate.setEraPreclusion("����");
            spiritEraDate.setDefaultCheckRange(IkenshoEraDateTextField.RNG_YEAR);
            spiritEraDate.setDateTypeConv(false);
    		// 2008/01/15 [Masahiko Higuchi] add - end
        }
        return spiritEraDate;
    }


    /**
     * ���莞�����x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getSpiritJudgmentTimeContainar() {
        if(spiritJudgmentTimeContainar == null){
            spiritJudgmentTimeContainar = new ACLabelContainer();
            spiritJudgmentTimeContainar.setText("���莞��");
        }
        return spiritJudgmentTimeContainar;
    }
    /**
     * ���_��Q�O���[�v���C�A�E�g��Ԃ��܂��B
     * @return
     */
    protected VRLayout getShogaiGroupLayout() {
        if(shogaiGroupLayout == null){
            shogaiGroupLayout = new VRLayout();
            shogaiGroupLayout.setAutoWrap(false);
            shogaiGroupLayout.setHgap(0);
        }
        return shogaiGroupLayout;
    }
    /**
     * ���_�R���e�i���C�A�E�g��Ԃ��܂��B
     * @return
     */
    protected VRLayout getSpiritContainarLayout() {
        if(spiritContainarLayout == null){    
            spiritContainarLayout = new VRLayout();
            spiritContainarLayout.setAutoWrap(false);
            spiritContainarLayout.setHgap(0);
        }
        return spiritContainarLayout;
    }
    /**
     * ������Q�]�� - ���f�����R���|�[�l���g��Ԃ��܂��B
     * @return
     */
    protected IkenshoEraDateTextField getSeikatsuEraDate() {
        if(seikatsuEraDate == null){
            seikatsuEraDate = new IkenshoEraDateTextField();
            // �ݒ�֌W
            seikatsuEraDate.setDayVisible(false);
            seikatsuEraDate.setAgeVisible(false);
            seikatsuEraDate.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            seikatsuEraDate.setBindPath("SK_SEIKATSU_DT");
            // �\���̈�𕽐��Ƌ󔒂Ɍ���
            seikatsuEraDate.setEraRange(3);
            
    		// 2008/01/15 [Masahiko Higuchi] add - begin �����f�t�H���g�\���Ή�
            seikatsuEraDate.setEraPreclusion("����");
            seikatsuEraDate.setDefaultCheckRange(IkenshoEraDateTextField.RNG_YEAR);
            seikatsuEraDate.setDateTypeConv(false);
    		// 2008/01/15 [Masahiko Higuchi] add - end
        }
        return seikatsuEraDate;
    }
    /**
     * ����Ǘ��R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuFukuyakuInnerContainar() {
        if(seikatsuFukuyakuInnerContainar == null){
            seikatsuFukuyakuInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuFukuyakuInnerContainar.setText("����Ǘ�");
            seikatsuFukuyakuInnerContainar.setInnerComboModel(
                    "SK_SEIKATSU_HUKUYAKU_KANRI",
                    "RENDER_SK_SEIKATSU_HUKUYAKU_KANRI",MAPS_MAXIMUM_VALUE5 ,
                    MAXIMUM_VALUE5);
            seikatsuFukuyakuInnerContainar.setComboBindPath("SK_SEIKATSU_HUKUYAKU_KANRI");
            seikatsuFukuyakuInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_HUKUYAKU_KANRI");
            seikatsuFukuyakuInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuFukuyakuInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuFukuyakuInnerContainar;
    }

    /**
     * �ې��R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuHoseiInnerContainar() {
        if(seikatsuHoseiInnerContainar == null){
            seikatsuHoseiInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuHoseiInnerContainar.setText("�ې�");
            seikatsuHoseiInnerContainar.setInnerComboModel("SK_SEIKATSU_HOSEI",
                    "RENDER_SK_SEIKATSU_HOSEI", MAPS_MAXIMUM_VALUE5,MAXIMUM_VALUE5
                    );
            seikatsuHoseiInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_HOSEI");
            seikatsuHoseiInnerContainar.setComboBindPath("SK_SEIKATSU_HOSEI");
            seikatsuHoseiInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuHoseiInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuHoseiInnerContainar;
    }
    /**
     * ���K�Ǘ��R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuKinsenInnerContainar() {
        if(seikatsuKinsenInnerContainar == null){
            seikatsuKinsenInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuKinsenInnerContainar.setText("���K�Ǘ�");
            seikatsuKinsenInnerContainar.setInnerComboModel(
                    "SK_SEIKATSU_KINSEN_KANRI",
                    "RENDER_SK_SEIKATSU_KINSEN_KANRI",MAPS_MAXIMUM_VALUE5 ,MAXIMUM_VALUE5
                    );
            seikatsuKinsenInnerContainar.setComboBindPath("SK_SEIKATSU_KINSEN_KANRI");
            seikatsuKinsenInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_KINSEN_KANRI");
            seikatsuKinsenInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuKinsenInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuKinsenInnerContainar;
    }
    /**
     * �������Y���R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuSeikatsuRizumuInnerContainar() {
        if(seikatsuSeikatsuRizumuInnerContainar == null){
            seikatsuSeikatsuRizumuInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuSeikatsuRizumuInnerContainar.setText("�������Y��");
            seikatsuSeikatsuRizumuInnerContainar.setInnerComboModel(
                    "SK_SEIKATSU_RHYTHM", "RENDER_SK_SEIKATSU_RHYTHM",MAPS_MAXIMUM_VALUE5
                    , MAXIMUM_VALUE5);
            seikatsuSeikatsuRizumuInnerContainar.setComboBindPath("SK_SEIKATSU_RHYTHM");
            seikatsuSeikatsuRizumuInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_RHYTHM");
            seikatsuSeikatsuRizumuInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuSeikatsuRizumuInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuSeikatsuRizumuInnerContainar;
    }
    /**
     * ������Q�]�����x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getSeikatsuShogaiContainar() {
        if(seikatsuShogaiContainar == null){
            seikatsuShogaiContainar = new ACLabelContainer();
            seikatsuShogaiContainar.setText("������Q�]��");
            // ���C�A�E�g�K�p
            seikatsuShogaiContainar.setLayout(getSeikatsuShogaiLayout());
            seikatsuShogaiContainar.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
            seikatsuShogaiContainar.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            seikatsuShogaiContainar.setContentAreaFilled(true);
        }
        return seikatsuShogaiContainar;
    }
    /**
     * �����O���[�v�u�j�v���x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getSeikatsuShogaiHesesEnd() {
        if(seikatsuShogaiHesesEnd == null){
            seikatsuShogaiHesesEnd = new ACLabel();
            seikatsuShogaiHesesEnd.setText(")");
        }
        return seikatsuShogaiHesesEnd;
    }
    /**
     * �����O���[�v�u�i�v���x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getSeikatsuShogaiHesesStart() {
        if(seikatsuShogaiHesesStart == null){
            seikatsuShogaiHesesStart = new ACLabel();
            seikatsuShogaiHesesStart.setText("(");
        }
        return seikatsuShogaiHesesStart;
    }
    /**
     * �H���R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuShokujiInnerContainar() {
        if(seikatsuShokujiInnerContainar == null){
            seikatsuShokujiInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuShokujiInnerContainar.setText("�H��");
            seikatsuShokujiInnerContainar.setInnerComboModel(
                    "SK_SEIKATSU_SHOKUJI", "RENDER_SK_SEIKATSU_SHOKUJI",MAPS_MAXIMUM_VALUE5
                    , MAXIMUM_VALUE5);
            seikatsuShokujiInnerContainar.setComboBindPath("SK_SEIKATSU_SHOKUJI");
            seikatsuShokujiInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_SHOKUJI");
            seikatsuShokujiInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuShokujiInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuShokujiInnerContainar;
    }
    /**
     * �ΐl�֌W�R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuTaijinInnerContainar() {
        if(seikatsuTaijinInnerContainar == null){
            seikatsuTaijinInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuTaijinInnerContainar.setText("�ΐl�֌W");
            seikatsuTaijinInnerContainar.setInnerComboModel(
                    "SK_SEIKATSU_TAIJIN_KANKEI", "RENDER_SK_SEIKATSU_TAIJIN_KANKEI",MAPS_MAXIMUM_VALUE5
                    ,MAXIMUM_VALUE5 );
            seikatsuTaijinInnerContainar.setComboBindPath("SK_SEIKATSU_TAIJIN_KANKEI");
            seikatsuTaijinInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_TAIJIN_KANKEI");
            seikatsuTaijinInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuTaijinInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuTaijinInnerContainar;
    }
    /**
     * ������Q�E���f�������x���R���e�i��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getSeikatsuTimeContainar() {
        if(seikatsuTimeContainar == null){
            seikatsuTimeContainar = new ACLabelContainer();
        }
        return seikatsuTimeContainar;
    }
    /**
     * ������Q�R���e�i�p�̃��C�A�E�g��Ԃ��܂��B
     * @return
     */
    protected VRLayout getSeikatsuShogaiLayout() {
        if(seikatsuShogaiLayout == null){
            seikatsuShogaiLayout = new VRLayout();
            seikatsuShogaiLayout.setHgap(0);
        }
        return seikatsuShogaiLayout;
    }
    /**
     * �Љ�I�K����W����s���R���{�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuSyakaiTekiouInnerContainar() {
        if(seikatsuSyakaiTekiouInnerContainar == null){
            seikatsuSyakaiTekiouInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuSyakaiTekiouInnerContainar.setText("�Љ�I�K����W����s��");
            seikatsuSyakaiTekiouInnerContainar.setInnerComboModel(
                    "SK_SEIKATSU_SHAKAI_TEKIOU",
                    "RENDER_SK_SEIKATSU_SHAKAI_TEKIOU",MAPS_MAXIMUM_VALUE5 ,
                    MAXIMUM_VALUE5);
            seikatsuSyakaiTekiouInnerContainar.setComboBindPath("SK_SEIKATSU_SHAKAI_TEKIOU");
            seikatsuSyakaiTekiouInnerContainar.setComboRenderBindPath("RENDER_SK_SEIKATSU_SHAKAI_TEKIOU");
            seikatsuSyakaiTekiouInnerContainar.getHintButton().setHintArea(getHintPanel());
            seikatsuSyakaiTekiouInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return seikatsuSyakaiTekiouInnerContainar;
    }
    /**
     * �^�u�^�C�g�����x����Ԃ��܂��B
     * @return
     */
    protected IkenshoDocumentTabTitleLabel getTitleLabel() {
        if(titleLabel == null){
            titleLabel = new IkenshoDocumentTabTitleLabel();
            titleLabel.setText("�T�D���̑����L���ׂ������i�����j");
        }
        return titleLabel;
    }
    

    protected int getAllowedInsurerType(){
        //2:��t�ӌ����݂̂̕ی��҂͋�����
        return 2;
    }
    
    /**
     * �q���g�O���[�v��Ԃ��܂��B
     * @return
     */
    protected ACGroupBox getHintGroup() {
        if(hintGroup == null){
            hintGroup = new ACGroupBox();
            hintGroup.add(getHintPanel(),VRLayout.CLIENT);
        }
        return hintGroup;
    }
    /**
     * �q���g�R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoHintContainer getHintPanel() {
        if(hintPanel == null){
            hintPanel = new IkenshoHintContainer();
        }
        return hintPanel;
    }
    /**
     * override���Đ����擾�̂��߂̖@�����Ή��敪��Ԃ��܂��B
     * @return �����擾�̂��߂̖@�����Ή��敪
     */
    protected int getFomratKubun(){
      return 2;
    }
    
    // [ID:0000555][Tozo TANAKA] 2009/09/14 add begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
    protected String getInsuredNoText(){
        return "�󋋎Ҕԍ�";
    }
    // [ID:0000555][Tozo TANAKA] 2009/09/14 add end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
    
    protected ACLabelContainer getSeikatsuDateContainar() {
        if(seikatsuDateContainar == null){
            seikatsuDateContainar = new ACLabelContainer();
            seikatsuDateContainar.setText("���f����"); 
        }
        return seikatsuDateContainar;
    }
    
    /**
     * �q���g�\���̈�
     * @return
     */
    protected ACGroupBox getHiddenParameters() {
        if(hiddenParameters == null){
            hiddenParameters = new ACGroupBox();
        }
        return hiddenParameters;
    }
    

}