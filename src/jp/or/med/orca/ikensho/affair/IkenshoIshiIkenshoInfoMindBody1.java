package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoBodyStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoMindBody1�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 * @version 2.0 2014/02/03
 */
public class IkenshoIshiIkenshoInfoMindBody1 extends
		IkenshoTabbableChildAffairContainer {
	
	// ���C���p�l��
	private ACGroupBox mindBody2Group;
	
	// �����r �̏d �g��
	private VRPanel mindBody2Connect;
	//(2)�l������ (3)��� (4)�ؗ͂̒ቺ (5)�֐�
	private VRPanel mindBody2Pos;
	
	// �^�C�g��
	private IkenshoDocumentTabTitleLabel mindBody2Title = new IkenshoDocumentTabTitleLabel();
	
	// �����r
	private ACClearableRadioButtonGroup mindBody2ConnectHand = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer mindBody2ConnectHandHeses = new ACParentHesesPanelContainer();
	private ACLabelContainer mindBody2ConnectHandPanel = new ACLabelContainer();
	
	//�g��
	private ACLabelContainer mindBody2ConnectHeightPanel = new ACLabelContainer();
	private ACTextField mindBody2ConnectHeight = new ACTextField();
	private VRLabel mindBody2ConnectHeightUnit = new VRLabel();
	
	//�̏d
	private ACLabelContainer mindBody2ConnectWeightPanel = new ACLabelContainer();
	private ACTextField mindBody2ConnectWeight = new ACTextField();
	private VRLabel mindBody2ConnectWeightUnit = new VRLabel();
	
	//�̏d�̕ω�
	private ACParentHesesPanelContainer weightChanges= new ACParentHesesPanelContainer();
	private ACClearableRadioButtonGroup weightChange = new ACClearableRadioButtonGroup();
	
	// �l������
	private IkenshoBodyStatusContainer mindBody2Pos1 = new IkenshoBodyStatusContainer();
	
	// ���
	private ACLabelContainer mahiContainer = new ACLabelContainer();
	private ACIntegerCheckBox mahi = new ACIntegerCheckBox();
	private IkenshoBodyStatusContainer mahiLegLeftUp = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiOther = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiLegRightDown = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiLegLeftDown = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiLegRightUp = new IkenshoBodyStatusContainer();
	private ACLabelContainer mahis = new ACLabelContainer();

	//�ؗ͂̒ቺ
	private IkenshoBodyStatusContainer mindBody2Pos3 = new IkenshoBodyStatusContainer();
	private ACParentHesesPanelContainer muscleChanges  = new ACParentHesesPanelContainer();
	private ACClearableRadioButtonGroup muscleChange = new ACClearableRadioButtonGroup();
	
	//�֐߂̍S�k
	private IkenshoBodyStatusContainer connectKousyuku;
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
    private VRLayout kousyukuLayout;
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
    

	
	
	public IkenshoIshiIkenshoInfoMindBody1() {
		try {
			jbInit();
			setEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void jbInit() throws Exception {
		
		VRLayout layout = new VRLayout();
		layout.setFitHLast(true);
		layout.setFitVLast(true);
		setLayout(layout);
		
		
		mindBody2Title.setText("�Q�D�g�̂̏�ԂɊւ���ӌ�");
		
		// �����r
		VRListModelAdapter rightLeftListModel = new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] { "�E", "��" })));
		
		mindBody2ConnectHand.setModel(rightLeftListModel);
		mindBody2ConnectHand.setBindPath("KIKIUDE");
		
		mindBody2ConnectHandHeses.add(mindBody2ConnectHand);
		mindBody2ConnectHandPanel.setText("�����r");
		mindBody2ConnectHandPanel.add(mindBody2ConnectHandHeses);
		
		//�g���̏d
		mindBody2ConnectHeight.setMaxLength(5);
		mindBody2ConnectHeight.setColumns(6);
		mindBody2ConnectHeight.setHorizontalAlignment(SwingConstants.RIGHT);
		mindBody2ConnectHeight.setBindPath("HEIGHT");
		
		mindBody2ConnectHeightUnit.setText("cm");
		
		mindBody2ConnectHeightPanel.setText("�g��=");
		mindBody2ConnectHeightPanel.add(mindBody2ConnectHeight);
		mindBody2ConnectHeightPanel.add(mindBody2ConnectHeightUnit);
		
		
		mindBody2ConnectWeight.setColumns(6);
		mindBody2ConnectWeight.setHorizontalAlignment(SwingConstants.RIGHT);
		mindBody2ConnectWeight.setBindPath("WEIGHT");
		mindBody2ConnectWeight.setMaxLength(5);
		
		mindBody2ConnectWeightUnit.setText("kg");
		
		mindBody2ConnectWeightPanel.setBackground(Color.pink);
		mindBody2ConnectWeightPanel.setText("�̏d=");
		mindBody2ConnectWeightPanel.add(mindBody2ConnectWeight);
		mindBody2ConnectWeightPanel.add(mindBody2ConnectWeightUnit);
		
		
	    getWeightChange().setModel(new VRListModelAdapter(new
                VRArrayList(Arrays.asList(new
                		String[] {"����", "�ێ�", "����"}))));
	    
	    getWeightChange().setBindPath("WEIGHT_CHANGE");
	    getWeightChanges().add(getWeightChange());
	    getWeightChanges().setBeginText("�i�ߋ�6�����̑̏d�̕ω�");
	    mindBody2ConnectWeightPanel.add(getWeightChanges());
		
		
		
		// �l������
		mindBody2Pos1.setCheckText("�l������");
		mindBody2Pos1.setCheckBindPath("SISIKESSON");
		mindBody2Pos1.setPosBindPath("SISIKESSON_BUI");
		
		// �l�������̒��x�폜
		//mindBody2Pos1.setRankBindPath("SISIKESSON_TEIDO");
		mindBody2Pos1.setRankVisible(false);

		// ���
		mahiContainer.setContentAreaFilled(true);
		mahiContainer.setBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
		mahiContainer.setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
		mahiContainer.setContentAreaFilled(true);
		mahiContainer.setLayout(new VRLayout(VRLayout.LEFT, 0, 0));
		mahiContainer.add(mahi, VRLayout.FLOW_RETURN);
		mahiContainer.add(mahis, VRLayout.FLOW_RETURN);
		
	    mahiOther.setCheckBindPath("MAHI_ETC");
	    mahiOther.setPosBindPath("MAHI_ETC_BUI");
	    mahiOther.setRankBindPath("MAHI_ETC_TEIDO");
	    mahiLegLeftUp.setCheckBindPath("MAHI_LEFTARM");
	    mahiLegLeftUp.setRankBindPath("MAHI_LEFTARM_TEIDO");
	    mahiLegRightUp.setCheckBindPath("MAHI_RIGHTARM");
	    mahiLegRightUp.setRankBindPath("MAHI_RIGHTARM_TEIDO");
	    mahiLegLeftDown.setCheckBindPath("MAHI_LOWERLEFTLIMB");
	    mahiLegLeftDown.setRankBindPath("MAHI_LOWERLEFTLIMB_TEIDO");
	    mahiLegRightDown.setCheckBindPath("MAHI_LOWERRIGHTLIMB");
	    mahiLegRightDown.setRankBindPath("MAHI_LOWERRIGHTLIMB_TEIDO");
	    mahi.setSelected(true);
	    mahi.setBindPath("MAHI_FLAG");
	    
	    VRLayout lay = new VRLayout(VRLayout.LEFT, 0, 0);
	    lay.setAutoWrap(false);
	    mahis.setLayout(lay);
	    
	    mahiLegLeftUp.setCheckText("���㎈");
	    mahiLegRightUp.setCheckText("�E�㎈");
	    mahiLegLeftDown.setCheckText("������");
	    mahiLegRightDown.setCheckText("�E����");
	    mahiOther.setCheckText("���̑�");
	    mahiOther.getComboBox().setColumns(12);
	    mahis.setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
	    mahi.setText("���");
	    mahiLegLeftUp.setPosVisible(false);
	    mahiLegRightDown.setPosVisible(false);
	    mahiLegLeftDown.setPosVisible(false);
	    mahiLegRightUp.setPosVisible(false);
	    
        mahiLegRightUp.getCheckBox().setPreferredSize(null);
        mahiLegLeftUp.getCheckBox().setPreferredSize(null);
        mahiLegRightDown.getCheckBox().setPreferredSize(null);
        mahiLegLeftDown.getCheckBox().setPreferredSize(null);
        mahis.add(mahiLegRightUp, VRLayout.FLOW);
        mahis.add(mahiLegLeftUp, VRLayout.FLOW_RETURN);
        mahis.add(mahiLegRightDown, VRLayout.FLOW);
        mahis.add(mahiLegLeftDown, VRLayout.FLOW_RETURN);
        mahis.add(mahiOther, VRLayout.FLOW_RETURN);
        
        
        
        // �ؗ͂̒ቺ
	    mindBody2Pos3.setCheckText("�ؗ͂̒ቺ");
		mindBody2Pos3.setCheckBindPath("KINRYOKU_TEIKA");
		mindBody2Pos3.setPosBindPath("KINRYOKU_TEIKA_BUI");
		mindBody2Pos3.setRankBindPath("KINRYOKU_TEIKA_TEIDO");
		
		ACPanel p = new ACPanel(new VRLayout());
		p.setOpaque(false);
		mindBody2Pos3.add(p, BorderLayout.SOUTH);
		
		muscleChange.setModel(new VRListModelAdapter(new
                VRArrayList(Arrays.asList(new
                		String[] {"���P", "�ێ�", "����"}))));
		
		muscleChange.setBindPath("KINRYOKU_TEIKA_CHANGE");
		muscleChanges.add(muscleChange);
		muscleChanges.setBeginText("�i�ߋ�6�����̏Ǐ�̕ϓ�");
		p.add(muscleChanges, VRLayout.EAST);
		
		
		
		
		//�֐�
		buildKansetuKousyukuGroup();


		addContaints();
		this.add(mindBody2Title, VRLayout.NORTH);
		this.add(getMindBody2Group(), VRLayout.CLIENT);
		
	}
	
    /**
     * �֐߂̍S�k�O���[�v�𐶐����܂��B
     */
    protected void buildKansetuKousyukuGroup(){
        // ���C�A�E�g�ݒ���s���B
        getKousyukuLayout().setAutoWrap(false);
        getKousyukuLayout().setHgap(0);
        getKousyukuLayout().setVgap(0);
        
        VRLayout kataLayout = new VRLayout(VRLayout.LEFT, 0, 0);
        VRLayout mataLayout = new VRLayout(VRLayout.LEFT, 0, 0);
        VRLayout hijiLayout = new VRLayout(VRLayout.LEFT, 0, 0);
        VRLayout hizaLayout = new VRLayout(VRLayout.LEFT, 0, 0);
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
        
        // �I�֐߃O���[�v ------------------------------------------------------
        getHijiCheck().setText("�I�֐�");
        ikenshoBodyComponentSetting(getHijiKousyukuRight(),"�E");
        ikenshoBodyComponentSetting(getHijiKousyukuLeft(),"��");
        getHijiBackLabelContainar().add(getHijiCheck(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuRight(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuLeft(),VRLayout.FLOW);
        // �I�֐߃O���[�v ------------------------------------------------------
        
        // �Ҋ֐߃O���[�v ------------------------------------------------------
        getMataCheck().setText("�Ҋ֐�");
        ikenshoBodyComponentSetting(getMataKousyukuRight(),"�E");
        ikenshoBodyComponentSetting(getMataKousyukuLeft(),"��");
        getMataBackLabelContainar().add(getMataCheck(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuRight(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuLeft(),VRLayout.FLOW);
        // �Ҋ֐߃O���[�v ------------------------------------------------------        
        
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
        getConnectKousyuku().add(getHijiBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getMataBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHizaBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getSonota(),VRLayout.FLOW_RETURN);
    }
	
	
    
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
    	
		// ��Ⴣ`�F�b�N�̘A��
		mahi.addItemListener(new ItemListener() {
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
				mahiLegLeftUp.followParentEnabled(select);
				mahiLegRightUp.followParentEnabled(select);
				mahiLegLeftDown.followParentEnabled(select);
				mahiLegRightDown.followParentEnabled(select);
				mahiOther.followParentEnabled(select);
			}

		});
    	
		// �ؗ͂̒ቺ�`�F�b�N�A��
		mindBody2Pos3.getCheckBox().addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				boolean select = (e.getStateChange() == ItemEvent.SELECTED);
				muscleChanges.setEnabled(select);
				muscleChange.setEnabled(select);
				muscleChange.getClearButton().setEnabled(select);
			}
		});
		
		
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
            //sonota.setRankVisible(false);
            sonota.getCheckBox().setText("���̑�");
            sonota.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            sonota.setCheckBindPath("KOUSHU_ETC");
            sonota.setPosBindPath("KOUSHU_ETC_BUI");
            sonota.setRankBindPath("KOUSHU_ETC_BUI_TEIDO");
        }
        return sonota;
    }
    
    
    /**
     * �֐߂̍S�k�R���e�i��Ԃ��܂��B
     * @return �֐߂̍S�k�R���e�i
     */
    protected IkenshoBodyStatusContainer getConnectKousyuku(){
      if(connectKousyuku==null){
        connectKousyuku = new IkenshoBodyStatusContainer();
      }
      return connectKousyuku;
    }
    
    /**
     * �̏d�̕ω��R���e�i��Ԃ��܂��B
     * @return �̏d�̕ω��R���e�i
     */
    protected ACParentHesesPanelContainer getWeightChanges(){
      if(weightChanges==null){
        weightChanges = new ACParentHesesPanelContainer();
      }
      return weightChanges;
    }
    /**
     * �̏d�̕ω���Ԃ��܂��B
     * @return �̏d�̕ω�
     */
    protected ACClearableRadioButtonGroup getWeightChange(){
      if(weightChange==null){
        weightChange = new ACClearableRadioButtonGroup();
      }
      return weightChange;
    }
    
    
    protected ACGroupBox getMindBody2Group() {
    	if (mindBody2Group == null) {
    		// ���C���p�l��
    		mindBody2Group = new ACGroupBox();
    		mindBody2Group.setText("�g�̂̏��");
    		mindBody2Group.setVgap(0);
    	}
    	return mindBody2Group;
    }



	/**
	 * �S�g�̏��(����)�O���[�v��Ԃ��܂��B
	 * 
	 * @return �S�g�̏��(����)�O���[�v
	 */
	protected VRPanel getMindBodyStatusContainer() {
		
		if (mindBody2Pos == null) {
			mindBody2Pos = new VRPanel();
			VRLayout layout = new VRLayout();
			layout.setVgap(0);
			layout.setAutoWrap(false);
			layout.setLabelMargin(0);
			layout.setAlignment(VRLayout.LEFT);
			layout.setHgap(0);
			
			mindBody2Pos.setLayout(layout);
			mindBody2Pos.setOpaque(true);
		}
		
		return mindBody2Pos;
	}


	/**
	 * �S�g�̏��(�֐ߓ�)�O���[�v��Ԃ��܂��B
	 * 
	 * @return �S�g�̏��(�֐ߓ�)�O���[�v
	 */
	protected VRPanel getMindBodyConnectContainer() {
		
		if (mindBody2Connect == null) {
			mindBody2Connect = new VRPanel();
			
			VRLayout layout = new VRLayout();
			//layout.setHgrid(20);
			layout.setHgrid(0);
			layout.setHgap(0);
			layout.setVgap(0);
			layout.setAutoWrap(false);
			layout.setLabelMargin(0);
			mindBody2Connect.setLayout(layout);
		}
		
		return mindBody2Connect;
	}

	/**
	 * override���ĐS�g�̏�Ԃ̒ǉ��������`���܂��B
	 */
	protected void addContaints() {

		getMindBodyConnectContainer().add(mindBody2ConnectHandPanel, VRLayout.FLOW_INSETLINE_RETURN); //�����r
		getMindBodyConnectContainer().add(mindBody2ConnectHeightPanel, VRLayout.FLOW_INSETLINE); //�g��
		getMindBodyConnectContainer().add(mindBody2ConnectWeightPanel, VRLayout.FLOW_INSETLINE); //�̏d
		
		getMindBodyStatusContainer().add(mindBody2Pos1, VRLayout.FLOW_INSETLINE_RETURN); //(2)�l������
		getMindBodyStatusContainer().add(mahiContainer, VRLayout.FLOW_INSETLINE_RETURN); //(3)���
		getMindBodyStatusContainer().add(mindBody2Pos3, VRLayout.FLOW_INSETLINE_RETURN); //(4)�ؗ͂̒ቺ
		
		getMindBodyStatusContainer().add(getConnectKousyuku(), VRLayout.FLOW_INSETLINE_RETURN); //(5)�֐�
		
		getMindBody2Group().add(getMindBodyConnectContainer(), VRLayout.FLOW_RETURN);
		getMindBody2Group().add(getMindBodyStatusContainer(), VRLayout.FLOW_RETURN);
	}


	public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

		//--- ��^�����̂̐ݒ�
		//�l������
		applyPoolTeikeibun(mindBody2Pos1.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME);
		//���
		applyPoolTeikeibun(mahiOther.getComboBox(), IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME);
		//�ؗ͂̒ቺ
		applyPoolTeikeibun(mindBody2Pos3.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME);
		//�֐߂̍S�k
		applyPoolTeikeibun(sonota.getComboBox(), IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME);
		
		
		//...�ҏW�̋@�\���g�p�\��
		//�l������
		mindBody2Pos1.getComboBox().setOptionComboBoxParameters("�l�������E����",IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME,10);
		//���
		mahiOther.getComboBox().setOptionComboBoxParameters("���(���̑�)�E����",IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME,10);
		//�ؗ͂̒ቺ
		mindBody2Pos3.getComboBox().setOptionComboBoxParameters("�ؗ͂̒ቺ�E����",IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME,10);
		//�֐߂̍S�k
		sonota.getComboBox().setOptionComboBoxParameters("�֐߂̍S�k�E����",IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME,10);
	}

	protected void bindSourceInnerBindComponent() throws Exception {
		super.bindSourceInnerBindComponent();
		
		// ��Ⴣ`�F�b�N
	    mahi.setSelected(mahiLegLeftUp.isChecked() || mahiLegRightUp.isChecked() ||
                mahiLegLeftDown.isChecked() || mahiLegRightDown.isChecked() ||
                mahiOther.isChecked());
		
	    // �ؗ͂̒ቺ�`�F�b�N
	    if (!mindBody2Pos3.getCheckBox().isSelected()) {
			muscleChanges.setEnabled(false);
			muscleChange.setEnabled(false);
			muscleChange.getClearButton().setEnabled(false);
	    }
	    
	    
        // �S�k�`�F�b�N
        getKousyukuCheck().setSelected(
                getKataCheck().isSelected() || getMataCheck().isSelected()
                        || getHijiCheck().isSelected()
                        || getHizaCheck().isSelected()
                        || getSonota().getCheckBox().isSelected());
        
        kousyukuChangeState();
        
        
		// �ߋ��f�[�^���p��
    	VRMap map =  (VRMap)getMasterSource();
        
    	// �l�������̒��x�͍��㖢�g�p��
    	map.put("SISIKESSON_TEIDO", null);

	}



}
