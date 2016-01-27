package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoCare1�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoCare1 extends
		IkenshoTabbableChildAffairContainer {
	
	
	// �^�C�g��
	private IkenshoDocumentTabTitleLabel care1Title = new IkenshoDocumentTabTitleLabel();
	
	// --------------------------------------------------------------------
	// ���݁A�����̉\���������a�ԂƂ��̑Ώ����j
	private ACGroupBox care1Group;
	
	private ACIntegerCheckBox care1Shikkin = new ACIntegerCheckBox(); //�A����
	private ACIntegerCheckBox care1Tentou = new ACIntegerCheckBox(); //�]�|�E����
	private ACIntegerCheckBox care1Haikai = new ACIntegerCheckBox(); //�p�j
	private ACIntegerCheckBox care1Jyokusou = new ACIntegerCheckBox(); //���
	private ACIntegerCheckBox care1Haien = new ACIntegerCheckBox(); //�������x��
	private ACIntegerCheckBox care1Chouheisoku = new ACIntegerCheckBox(); //����
	private ACIntegerCheckBox care1Ekikan = new ACIntegerCheckBox(); //�Պ�����
	private ACIntegerCheckBox care1ShinpaiDown = new ACIntegerCheckBox(); //�S�x�@�\�̒ቺ
	private ACIntegerCheckBox care1Pain = new ACIntegerCheckBox(); //�u��
	private ACIntegerCheckBox care1Dassui = new ACIntegerCheckBox(); //�E��
	
	private ACIntegerCheckBox care1Koudou = new ACIntegerCheckBox(); //�s����Q
	private ACIntegerCheckBox care1Seishin = new ACIntegerCheckBox(); //���_�Ǐ�̑���
	private ACIntegerCheckBox care1Keiren = new ACIntegerCheckBox(); //������񔭍�
	
	// ���̑�
	private ACLabelContainer care1Others = new ACLabelContainer();
	private ACIntegerCheckBox care1Other = new ACIntegerCheckBox();
	private IkenshoOptionComboBox care1OtherName = new IkenshoOptionComboBox();
	private ACParentHesesPanelContainer care1OtherNameHeses = new ACParentHesesPanelContainer();
	private ACLabelContainer care1OtherNames = new ACLabelContainer();
	
	//�Ώ����j
	private ACLabelContainer care1OtherTaisyos = new ACLabelContainer();
	private VRLabel care1AbstractionMiddleMessage = new VRLabel();
	private IkenshoOptionComboBox care1OtherTaisyo = new IkenshoOptionComboBox();
	
	
	// --------------------------------------------------------------------
	// ��Q�����T�[�r�X�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ���
	private ACGroupBox care2Group;
	
	// ����
	private ACLabelContainer care2Ketsuattsus = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Ketsuattsu = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2KetsuattsuHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2KetsuattsuValue = new IkenshoOptionComboBox();
	
	// ����
	private ACLabelContainer care2Enges = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Enge = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2EngeHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2EngeValue = new IkenshoOptionComboBox();
	
	// �ېH
	private ACLabelContainer care2Sesshokus = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Sesshoku = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2SesshokuHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2SesshokuValue = new IkenshoOptionComboBox();
	
	// �ړ�
	private ACLabelContainer care2Moves = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Move = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2MoveHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2MoveValue = new IkenshoOptionComboBox();
	
	// �s��
	private ACLabelContainer care2Actions = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Action = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2ActionHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2ActionValue = new IkenshoOptionComboBox();
	
	// ���_
	private ACLabelContainer care2Minds = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Mind = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2MindHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2MindValue = new IkenshoOptionComboBox();
	
	// ���̑�
	private ACLabelContainer care2ServiceOthers = new ACLabelContainer();
	private ACParentHesesPanelContainer care2ServiceOtherHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2ServiceOtherValue = new IkenshoOptionComboBox();
	
	
	// --------------------------------------------------------------------
	// �����ǂ̗L��(�L�̏ꍇ�͋�̓I�ɋL��)
	private ACGroupBox care3Group;
	private ACLabelContainer care3Kansens = new ACLabelContainer();
	private ACValueArrayRadioButtonGroup care3Kansen = new ACValueArrayRadioButtonGroup();
	private IkenshoOptionComboBox care3KansenName = new IkenshoOptionComboBox();
	

	
	
	public IkenshoIshiIkenshoInfoCare1() {
		try {
			//��ʍ\������
			jbInit();
            // �C�x���g���X�i��`
            setEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private void jbInit() throws Exception {
		
		care1Title.setText("�T�D�T�[�r�X���p�Ɋւ���ӌ�");
		
		VRLayout layout = new VRLayout();
		layout.setFitHLast(true);
		layout.setFitVLast(true);
		setLayout(layout);
		
		
		buildCare1Group();
		buildCare2Group();
		buildCare3Group();
		
		
		this.add(care1Title, VRLayout.NORTH);
		this.add(getCare1Group(), VRLayout.NORTH);
		this.add(getCare2Group(), VRLayout.NORTH);
		this.add(getCare3Group(), VRLayout.NORTH);

	}
	
	// ���݁A�����̉\���������a�ԂƂ��̑Ώ����j
	private void buildCare1Group() {
		
		
		care1Shikkin.setBindPath("NYOUSIKKIN");
		care1Shikkin.setText("�A����");
		//care1Shikkin.setValueBindPath("NYOUSIKKIN_TAISHO_HOUSIN");
		
		care1Tentou.setBindPath("TENTOU_KOSSETU");
		care1Tentou.setText("�]�|�E����");
		//care1Tentou.setValueBindPath("TENTOU_KOSSETU_TAISHO_HOUSIN");
		
		care1Haikai.setBindPath("HAIKAI_KANOUSEI");
		care1Haikai.setText("�p�j");
		//care1Haikai.setValueBindPath("HAIKAI_KANOUSEI_TAISHO_HOUSIN");
		
		care1Jyokusou.setBindPath("JOKUSOU_KANOUSEI");
		care1Jyokusou.setText("���");
		//care1Jyokusou.setValueBindPath("JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
		
		care1Haien.setBindPath("ENGESEIHAIEN");
		care1Haien.setText("�������x��");
		//care1Haien.setValueBindPath("ENGESEIHAIEN_TAISHO_HOUSIN");
		
		care1Chouheisoku.setBindPath("CHOUHEISOKU");
		care1Chouheisoku.setText("����");
		//care1Chouheisoku.setValueBindPath("CHOUHEISOKU_TAISHO_HOUSIN");
		
		care1Ekikan.setBindPath("EKIKANKANSEN");
		care1Ekikan.setText("�Պ�����");
		//care1Ekikan.setValueBindPath("EKIKANKANSEN_TAISHO_HOUSIN");
		
		care1ShinpaiDown.setBindPath("SINPAIKINOUTEIKA");
		care1ShinpaiDown.setText("�S�x�@�\�̒ቺ");
		//care1ShinpaiDown.setValueBindPath("SINPAIKINOUTEIKA_TAISHO_HOUSIN");
		
		care1Pain.setBindPath("ITAMI");
		care1Pain.setText("�u��");
		//care1Pain.setValueBindPath("ITAMI_TAISHO_HOUSIN");
		
		care1Dassui.setBindPath("DASSUI");
		care1Dassui.setText("�E��");
		//care1Dassui.setValueBindPath("DASSUI_TAISHO_HOUSIN");
		
		care1Koudou.setBindPath("KOUDO_SHOGAI");
		care1Koudou.setText("�s����Q");
		
		care1Seishin.setBindPath("SEISIN_ZOAKU");
		care1Seishin.setText("���_�Ǐ�̑���");
		
		
		care1Keiren.setBindPath("KEIREN_HOSSA");
		care1Keiren.setText("������񔭍�");
		
		care1Other.setBindPath("BYOUTAITA");
		care1Other.setActionCommand("���̑�");
		care1Other.setText("���̑�");
		
		
		care1OtherName.setColumns(10);
		care1OtherName.setMaxLength(15);
		care1OtherName.setIMEMode(InputSubset.KANJI);
		care1OtherNameHeses.setOpaque(false);
		care1OtherNameHeses.setEnabled(false);
		care1OtherName.setBindPath("BYOUTAITA_NM");
		
		care1OtherNames.setLayout(new VRLayout());
		care1OtherNames.add(care1OtherName, VRLayout.CLIENT);
		
		care1OtherNameHeses.add(care1OtherNames, VRLayout.CLIENT);
		
		VRLayout layout = new VRLayout();
		layout.setFitHLast(true);
		layout.setAutoWrap(false);
		layout.setLabelMargin(0);
		care1Others.setLayout(layout);
		care1Others.setContentAreaFilled(true);
		care1Others.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
		care1Others.add(care1Other, VRLayout.WEST);
		care1Others.add(care1OtherNameHeses, VRLayout.WEST);
		
		
		
		
		//�Ώ����j
		care1AbstractionMiddleMessage.setText("���@�Ώ����j");
		care1OtherTaisyo.setIMEMode(InputSubset.KANJI);
		care1OtherTaisyo.setMaxLength(45);
		care1OtherTaisyo.setColumns(45);
		care1OtherTaisyo.setBindPath("TAISHO_HOUSIN");
		
		care1OtherTaisyos.setLayout(new VRLayout());
		care1OtherTaisyos.add(care1AbstractionMiddleMessage, VRLayout.WEST);
		care1OtherTaisyos.add(care1OtherTaisyo, VRLayout.CLIENT);
		
		
		
		
		getCare1Group().add(care1Shikkin, VRLayout.FLOW);
		getCare1Group().add(care1Tentou, VRLayout.FLOW);
		getCare1Group().add(care1Haikai, VRLayout.FLOW);
		getCare1Group().add(care1Jyokusou, VRLayout.FLOW);
		getCare1Group().add(care1Haien, VRLayout.FLOW);
		getCare1Group().add(care1Chouheisoku, VRLayout.FLOW_INSETLINE_RETURN);
		getCare1Group().add(care1Ekikan, VRLayout.FLOW);
		getCare1Group().add(care1ShinpaiDown, VRLayout.FLOW);
		getCare1Group().add(care1Pain, VRLayout.FLOW);
		getCare1Group().add(care1Dassui, VRLayout.FLOW);
		getCare1Group().add(care1Koudou, VRLayout.FLOW);
		getCare1Group().add(care1Seishin, VRLayout.FLOW_INSETLINE_RETURN);
		getCare1Group().add(care1Keiren, VRLayout.FLOW);
		getCare1Group().add(care1Others, VRLayout.FLOW);
		getCare1Group().add(care1OtherTaisyos, VRLayout.FLOW_INSETLINE_RETURN);
	}
	
	
	// ��Q�����T�[�r�X�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ���
	private void buildCare2Group() {
		
		VRListModelAdapter adp = new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] { "���ɂȂ��@", "�L" })));
		
		
		// ����
        care2Ketsuattsu.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2KetsuattsuValue, care2KetsuattsuHeses }, 1));
		
		care2Ketsuattsus.setText("�E�����ɂ��ā@�@");
		
		care2Ketsuattsu.setBindPath("KETUATU");
		care2Ketsuattsu.setModel(adp);
		care2Ketsuattsu.setUseClearButton(false);

		care2KetsuattsuValue.setEnabled(false);
		care2KetsuattsuValue.setPreferredSize(new Dimension(380, 19));
		care2KetsuattsuValue.setIMEMode(InputSubset.KANJI);
		care2KetsuattsuValue.setMaxLength(30);
		care2KetsuattsuValue.setColumns(30);
		care2KetsuattsuValue.setBindPath("KETUATU_RYUIJIKOU");
		
		care2KetsuattsuHeses.add(care2KetsuattsuValue, VRLayout.CLIENT);
		care2Ketsuattsu.add(care2KetsuattsuHeses, VRLayout.CLIENT);
		care2Ketsuattsus.add(care2Ketsuattsu);
		
		
		// ����
		care2Enge.addListSelectionListener(
				new ACFollowDisableSelectionListener(
						new JComponent[] { care2EngeValue, care2EngeHeses }, 1));
		
		care2Enges.setText("�E�����ɂ��ā@�@");
		
		care2Enge.setBindPath("ENGE");
		care2Enge.setModel(adp);
		care2Enge.setUseClearButton(false);
		
		care2EngeValue.setEnabled(false);
		care2EngeValue.setPreferredSize(new Dimension(380, 19));
		care2EngeValue.setIMEMode(InputSubset.KANJI);
		care2EngeValue.setMaxLength(30);
		care2EngeValue.setColumns(30);
		care2EngeValue.setBindPath("ENGE_RYUIJIKOU");
		
		care2EngeHeses.add(care2EngeValue, VRLayout.CLIENT);
		care2Enge.add(care2EngeHeses, VRLayout.CLIENT);
		care2Enges.add(care2Enge);


		// �ېH
		care2Sesshoku.addListSelectionListener(
				new ACFollowDisableSelectionListener(
					new JComponent[] { care2SesshokuValue, care2SesshokuHeses }, 1));
		
		care2Sesshokus.setText("�E�ېH�ɂ��ā@�@");
		
		care2Sesshoku.setBindPath("SESHOKU");
		care2Sesshoku.setModel(adp);
		care2Sesshoku.setUseClearButton(false);
		
		care2SesshokuValue.setEnabled(false);
		care2SesshokuValue.setPreferredSize(new Dimension(380, 19));
		care2SesshokuValue.setIMEMode(InputSubset.KANJI);
		care2SesshokuValue.setMaxLength(30);
		care2SesshokuValue.setColumns(30);
		care2SesshokuValue.setBindPath("SESHOKU_RYUIJIKOU");
		
		care2SesshokuHeses.add(care2SesshokuValue, VRLayout.CLIENT);
		care2Sesshoku.add(care2SesshokuHeses, VRLayout.CLIENT);
		care2Sesshokus.add(care2Sesshoku);

		
		// �ړ�
        care2Move.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2MoveValue, care2MoveHeses }, 1));

        care2Moves.setText("�E�ړ��ɂ��ā@�@");

        care2Move.setModel(adp);
        care2Move.setUseClearButton(false);
        care2Move.setBindPath("IDOU");

        care2MoveValue.setEnabled(false);
        care2MoveValue.setPreferredSize(new Dimension(380, 19));
        care2MoveValue.setIMEMode(InputSubset.KANJI);
        care2MoveValue.setMaxLength(30);
        care2MoveValue.setColumns(30);
        care2MoveValue.setBindPath("IDOU_RYUIJIKOU");

        care2MoveHeses.add(care2MoveValue, VRLayout.CLIENT);
        care2Move.add(care2MoveHeses, VRLayout.CLIENT);
        care2Moves.add(care2Move);
		
		
		// �s����Q
        care2Action.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2ActionValue, care2ActionHeses }, 1));

        care2Actions.setText("�E�s����Q�ɂ���");

        care2Action.setModel(adp);
        care2Action.setUseClearButton(false);
        care2Action.setBindPath("SFS_KOUDO");

        care2ActionValue.setEnabled(false);
        care2ActionValue.setPreferredSize(new Dimension(380, 19));
        care2ActionValue.setIMEMode(InputSubset.KANJI);
        care2ActionValue.setMaxLength(30);
        care2ActionValue.setColumns(30);
        care2ActionValue.setBindPath("SFS_KOUDO_RYUIJIKOU");

        care2ActionHeses.add(care2ActionValue, VRLayout.CLIENT);
        care2Action.add(care2ActionHeses, VRLayout.CLIENT);
        care2Actions.add(care2Action);
        
        
		// ���_�Ǐ�
        care2Mind.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2MindValue, care2MindHeses }, 1));

        care2Minds.setText("�E���_�Ǐ�ɂ���");

        care2Mind.setModel(adp);
        care2Mind.setUseClearButton(false);
        care2Mind.setBindPath("SFS_SEISIN");

        care2MindValue.setEnabled(false);
        care2MindValue.setPreferredSize(new Dimension(380, 19));
        care2MindValue.setIMEMode(InputSubset.KANJI);
        care2MindValue.setMaxLength(30);
        care2MindValue.setColumns(30);
        care2MindValue.setBindPath("SFS_SEISIN_RYUIJIKOU");

        care2MindHeses.add(care2MindValue, VRLayout.CLIENT);
        care2Mind.add(care2MindHeses, VRLayout.CLIENT);
        care2Minds.add(care2Mind);
        
		
		
        //���̑�
        care2ServiceOthers.setText("�E���̑�");

        care2ServiceOtherValue.setIMEMode(InputSubset.KANJI);
        care2ServiceOtherValue.setMaxLength(50);
        care2ServiceOtherValue.setColumns(50);
        care2ServiceOtherValue.setBindPath("KAIGO_OTHER");

        care2ServiceOtherHeses.add(care2ServiceOtherValue, VRLayout.CLIENT);
        care2ServiceOthers.add(care2ServiceOtherHeses);
        
		
        addInnerBindComponent(care2KetsuattsuValue);
        addInnerBindComponent(care2EngeValue);
        addInnerBindComponent(care2SesshokuValue);
        addInnerBindComponent(care2MoveValue);
        addInnerBindComponent(care2ActionValue);
        addInnerBindComponent(care2MindValue);
		
		getCare2Group().add(care2Ketsuattsus, VRLayout.FLOW_INSETLINE_RETURN);
		getCare2Group().add(care2Enges, VRLayout.FLOW_INSETLINE_RETURN);
		getCare2Group().add(care2Sesshokus, VRLayout.FLOW_INSETLINE_RETURN);
		getCare2Group().add(care2Moves, VRLayout.FLOW_INSETLINE_RETURN);
		getCare2Group().add(care2Actions, VRLayout.FLOW_INSETLINE_RETURN);
		getCare2Group().add(care2Minds, VRLayout.FLOW_INSETLINE_RETURN);
		getCare2Group().add(care2ServiceOthers, VRLayout.FLOW_RETURN);
	}
	
	
	// �����ǂ̗L��
	private void buildCare3Group() {
		
		VRListModelAdapter adp = new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] { "�L", "��", "�s��" })));
		
		care3Kansen.setModel(adp);
		care3Kansen.setValues(new int[] { 1, 2, 3 });
		care3Kansen.setUseClearButton(false);
		care3Kansen.setBindPath("KANSENSHOU");
		care3Kansen.addListSelectionListener(
                new ACFollowDisableSelectionListener(
                        new JComponent[] { care3KansenName }, 0));
		
		
		care3KansenName.setIMEMode(InputSubset.KANJI);
		care3KansenName.setMaxLength(30);
		care3KansenName.setColumns(30);
		care3KansenName.setBindPath("KANSENSHOU_NM");
		care3KansenName.setEnabled(false);
		care3KansenName.setPreferredSize(new Dimension(400, 19));
		
		care3Kansen.add(care3KansenName, null, 1);
		care3Kansens.add(care3Kansen, null);
		
		addInnerBindComponent(care3KansenName);
		
		getCare3Group().add(care3Kansens, VRLayout.FLOW_INSETLINE_RETURN);
		
		
	}
	
	
	
	
	
	protected ACGroupBox getCare1Group() {
		if (care1Group == null) {
			care1Group = new ACGroupBox();
			care1Group.setText("���݁A�����̉\���������a�ԂƂ��̑Ώ����j");
		}
		return care1Group;
	}
	
	
	protected ACGroupBox getCare2Group() {
		if (care2Group == null) {
			care2Group = new ACGroupBox();
			care2Group.setText("��Q�����T�[�r�X�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ���");
			
	        VRLayout layout = new VRLayout();
	        layout.setFitHLast(true);
	        layout.setHgap(0);
	        layout.setLabelMargin(0);
	        
	        care2Group.setLayout(layout);
			
		}
		return care2Group;
	}
	
	protected ACGroupBox getCare3Group() {
		if (care3Group == null) {
			care3Group = new ACGroupBox();
			care3Group.setText("�����ǂ̗L���i�L�̏ꍇ�͋�̓I�ɋL���j�i30�����ȓ��j");
			
		}
		return care3Group;
	}


	
	
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
    	// ���̑��`�F�b�N�Ɠ��̘͂A��
		care1Other.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				changeSonotaState();
			}
		});
		
		
		// �`�F�b�N�ƘA�����āA�e�L�X�g�̓��͂�������
		Care1GroupCheckItemListener listener = new Care1GroupCheckItemListener();
		
		//�A����
		care1Shikkin.addItemListener(listener);
		//�]�|�E����
		care1Tentou.addItemListener(listener);
		//�p�j
		care1Haikai.addItemListener(listener);
		//���
		care1Jyokusou.addItemListener(listener);
		//�������x��
		care1Haien.addItemListener(listener);
		//����
		care1Chouheisoku.addItemListener(listener);
		//�Պ�����
		care1Ekikan.addItemListener(listener);
		//�S�x�@�\�̒ቺ
		care1ShinpaiDown.addItemListener(listener);
		//�u��
		care1Pain.addItemListener(listener);
		//�E��
		care1Dassui.addItemListener(listener);
		//�s����Q
		care1Koudou.addItemListener(listener);
		//���_�Ǐ�̑���
		care1Seishin.addItemListener(listener);
		//������񔭍�
		care1Keiren.addItemListener(listener);
		//���̑�
		care1Other.addItemListener(listener);
		
    }
	

	public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
		
		// --- �R���{�{�b�N�X�̓��e�ݒ�
		// �����̉\���������a�� - ���̑�
		applyPoolTeikeibun(care1OtherName, IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME);
		// �����̉\���������a�� - �Ώ����j
		applyPoolTeikeibun(care1OtherTaisyo, IkenshoCommon.TEIKEI_TAISHO_HOUSIN_NAME);
		
		// ���ӎ��� - ����
        applyPoolTeikeibun(care2KetsuattsuValue, IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME);
        // ���ӎ��� - ����
        applyPoolTeikeibun(care2EngeValue, IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME);
        // ���ӎ��� - �ېH
        applyPoolTeikeibun(care2SesshokuValue, IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME);
        // ���ӎ��� - �ړ�
        applyPoolTeikeibun(care2MoveValue, IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME);
        // ���ӎ��� - �s����Q
        applyPoolTeikeibun(care2ActionValue, IkenshoCommon.TEIKEI_CARE_SERVICE_ACTION_NAME);
        // ���ӎ��� - ���_�Ǐ�
        applyPoolTeikeibun(care2MindValue, IkenshoCommon.TEIKEI_CARE_SERVICE_MIND_NAME);
        // ���ӎ��� - ���̑�
        applyPoolTeikeibun(care2ServiceOtherValue, IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME);
        
        // �����ǂ̗L��
        applyPoolTeikeibun(care3KansenName, IkenshoCommon.TEIKEI_INFECTION_NAME);
        
        
        // ...�ҏW���g�p�\��
        // �����̉\���������a�� - ���̑�
        care1OtherName.setOptionComboBoxParameters("���̑�", IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15);
        // �����̉\���������a�� - �Ώ����j
        care1OtherTaisyo.setOptionComboBoxParameters("�Ώ����j", IkenshoCommon.TEIKEI_TAISHO_HOUSIN_NAME, 45);
        
        // ���ӎ��� - ����
        care2KetsuattsuValue.setOptionComboBoxParameters("����", IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30);
        // ���ӎ��� - ����
        care2EngeValue.setOptionComboBoxParameters("����", IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30);
        // ���ӎ��� - �ېH
        care2SesshokuValue.setOptionComboBoxParameters("�ېH", IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30);
        // ���ӎ��� - �ړ�
        care2MoveValue.setOptionComboBoxParameters("�ړ�", IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30);
        // ���ӎ��� - �s����Q
        care2ActionValue.setOptionComboBoxParameters("�s����Q", IkenshoCommon.TEIKEI_CARE_SERVICE_ACTION_NAME, 30);
        // ���ӎ��� - ���_�Ǐ�
        care2MindValue.setOptionComboBoxParameters("���_�Ǐ�", IkenshoCommon.TEIKEI_CARE_SERVICE_MIND_NAME, 30);
        // ���ӎ��� - ���̑�
        care2ServiceOtherValue.setOptionComboBoxParameters("���̑�", IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50);
        
        // �����ǂ̗L��
        care3KansenName.setOptionComboBoxParameters("�L�̏ꍇ", IkenshoCommon.TEIKEI_INFECTION_NAME, 30);
        
        
        
        if(getMasterAffair() instanceof IkenshoIkenshoInfo){
            // �X�i�b�v�V���b�g�ɂ���
            // 2006/06/21
            // Addition - [Masahiko Higuchi]
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2KetsuattsuValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2EngeValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2SesshokuValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2MoveValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2ActionValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2MindValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().addComponent(care2ServiceOtherValue);
            ((IkenshoIkenshoInfo)getMasterAffair()).getSimpleSnap().simpleSnapshot();
            
        }
		
	}
	
	
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        
    	// ���̑��`�F�b�N�Ɠ��̘͂A��
        changeSonotaState();
        
		// �`�F�b�N����ł��L��΁A�Ώ����j���͉�
        changeTaisyoState();
        
        
        // �厡��ӌ�������W�J�������A�l���Ȃ��̂Őݒ�
        // ���ӎ��� - �s����Q
        if (care2Action.getSelectedIndex() == 0) {
        	care2Action.setSelectedIndex(1);
        }

        // ���ӎ��� - ���_�Ǐ�
        if (care2Mind.getSelectedIndex() == 0) {
        	care2Mind.setSelectedIndex(1);
        }
        
        // �ߋ��f�[�^���p��
    	VRMap map =  (VRMap)getMasterSource();
    	
    	// �Ώ����j���󔒂Ȃ�A�쐬�����݂�
    	if ("".equals(ACCastUtilities.toString(map.get("TAISHO_HOUSIN")))) {
    		care1OtherTaisyo.setText(createTaisho(map));
    	}
    	
    	// �A����
    	map.put("NYOUSIKKIN_TAISHO_HOUSIN", null);
    	// �]�|�E����
    	map.put("TENTOU_KOSSETU_TAISHO_HOUSIN", null);
    	// �p�j
    	map.put("HAIKAI_KANOUSEI_TAISHO_HOUSIN", null);
    	// ���
    	map.put("JOKUSOU_KANOUSEI_TAISHO_HOUSIN", null);
    	// �������x��
    	map.put("ENGESEIHAIEN_TAISHO_HOUSIN", null);
    	// ����
    	map.put("CHOUHEISOKU_TAISHO_HOUSIN", null);
    	// �Պ�����
    	map.put("EKIKANKANSEN_TAISHO_HOUSIN", null);
    	// �S�x�@�\�̒ቺ
    	map.put("SINPAIKINOUTEIKA_TAISHO_HOUSIN", null);
    	// �u��
    	map.put("ITAMI_TAISHO_HOUSIN", null);
    	// �E��
    	map.put("DASSUI_TAISHO_HOUSIN", null);
    	// ���̑�
    	map.put("BYOUTAITA_TAISHO_HOUSIN", null);
    }
    
    
    private String createTaisho(VRMap map) throws Exception {
    	
    	StringBuffer sb = new StringBuffer();
    	
    	// �A����
    	sb.append(getTaisho(map, "NYOUSIKKIN_TAISHO_HOUSIN"));
    	// �]�|�E����
    	sb.append(getTaisho(map, "TENTOU_KOSSETU_TAISHO_HOUSIN"));
    	// �p�j
    	sb.append(getTaisho(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
    	// ���
    	sb.append(getTaisho(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
    	// �������x��
    	sb.append(getTaisho(map, "ENGESEIHAIEN_TAISHO_HOUSIN"));
    	// ����
    	sb.append(getTaisho(map, "CHOUHEISOKU_TAISHO_HOUSIN"));
    	// �Պ�����
    	sb.append(getTaisho(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
    	// �S�x�@�\�̒ቺ
    	sb.append(getTaisho(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
    	// �u��
    	sb.append(getTaisho(map, "ITAMI_TAISHO_HOUSIN"));
    	// �E��
    	sb.append(getTaisho(map, "DASSUI_TAISHO_HOUSIN"));
    	// ���̑�
    	sb.append(getTaisho(map, "BYOUTAITA_TAISHO_HOUSIN"));
    	
    	if (sb.toString().endsWith("�A")) {
    		int len = sb.length();
    		sb.replace(len - 1, len, "�B");
    	}
    	
    	if (sb.length() <= 45) {
    		return sb.toString();
    	}
    	
    	return sb.substring(0, 45);
    	
    }
    
    private String getTaisho(VRMap map, String key) throws Exception {
    	
    	String value = ACCastUtilities.toString(map.get(key), "");
    	if (IkenshoCommon.isNullText(value)) {
    		return "";
    	}
    	
    	if (value.endsWith("�A") || value.endsWith("�B")) {
    		return value;
    	}
    	
    	return value + "�A";
    	
    }
    
    
    // ���̑��`�F�b�N�Ƃ��̑����̘͂A��
    private void changeSonotaState() {
        boolean select =  care1Other.isSelected();
        care1OtherName.setEnabled(select);
        care1OtherNameHeses.setEnabled(select);
    }
    
    
    // �Ώ����j���͂�Enabled����
    private void changeTaisyoState() {
		// �`�F�b�N����ł��L��΁A�Ώ����j���͉�
		boolean selected = isCare1GroupChecked();
		care1OtherTaisyo.setEnabled(selected);
		care1AbstractionMiddleMessage.setEnabled(selected);
    }
    
    
    
    private boolean isCare1GroupChecked() {
		if (care1Shikkin.isSelected() //�A����
				|| care1Tentou.isSelected() //�]�|�E����
				|| care1Haikai.isSelected() //�p�j
				|| care1Jyokusou.isSelected() //���
				|| care1Haien.isSelected() //�������x��
				|| care1Chouheisoku.isSelected() //����
				|| care1Ekikan.isSelected() //�Պ�����
				|| care1ShinpaiDown.isSelected() //�S�x�@�\�̒ቺ
				|| care1Pain.isSelected() //�u��
				|| care1Dassui.isSelected() //�E��
				|| care1Koudou.isSelected() //�s����Q
				|| care1Seishin.isSelected() //���_�Ǐ�̑���
				|| care1Keiren.isSelected() //������񔭍�
				|| care1Other.isSelected() //���̑�
			){
			
			return true;
		}
		
		return false;
    }
    
    
    
    
    public boolean noControlError() throws Exception {
        if(!super.noControlError()){
            return false;
        }

        // ���̑��`�F�b�N���L���ł���ꍇ
        if(care1Other.isSelected()){
            if (IkenshoCommon.isNullText(care1OtherName.getText())) {
                ACMessageBox.showExclamation("�u���݁A�����̉\���������a�ԂƂ��̑Ώ����j�i���̑��j�v�Ŗ��L��������܂��B");
                care1Other.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    
    

	// �I�[�o�[���C�h���āA�����l��ݒ�
    public VRMap createSourceInnerBindComponent() {
        VRMap map = super.createSourceInnerBindComponent();
        
        map.setData("KETUATU", new Integer(1)); //����
        map.setData("ENGE", new Integer(1)); //����
        map.setData("SESHOKU", new Integer(1)); //�ېH
        map.setData("IDOU", new Integer(1)); //�ړ�
        map.setData("SFS_KOUDO", new Integer(1)); //�s����Q
        map.setData("SFS_SEISIN", new Integer(1)); //���_�Ǐ�

        map.setData("KANSENSHOU", new Integer(2)); //�����ǂ̗L��
        
        return map;
    }
    
    
    // �Ώ����j��Enable����̂��߁A�`�F�b�N�C�x���g
    private class Care1GroupCheckItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			changeTaisyoState();
		}
    }

}
