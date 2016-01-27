package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
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
 * IkenshoIshiIkenshoInfoMindBody2�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 * @version 2.0 2014/02/14
 */
public class IkenshoIshiIkenshoInfoMindBody2 extends IkenshoTabbableChildAffairContainer {
	
	
	//�^�C�g��
	private IkenshoDocumentTabTitleLabel mindBody2Title = new IkenshoDocumentTabTitleLabel();
	
	//���C���p�l��
	private ACGroupBox mindBody2Group;
	private VRPanel mindBody2Pos;
	
	
	// �֐߂̒ɂ�
	private IkenshoBodyStatusContainer connectPain;
	private ACParentHesesPanelContainer connectPainChanges;
	private ACClearableRadioButtonGroup connectPainChange;
	
	
	// �����E�s���Ӊ^��
	private ACLabelContainer mindBody2DownFuzuiis;
	private ACIntegerCheckBox mindBody2DownFuzuii;
    // �㎈�R���e�i-�E
    private IkenshoBodyStatusContainer jyoshiRight;
    // �㎈�R���e�i-��
    private IkenshoBodyStatusContainer jyoshiLeft;
    // �̊��R���e�i
    private IkenshoBodyStatusContainer taikan;
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
    
    
    // ���
    private IkenshoBodyStatusContainer jyokusou;
    
    // ���̑��̔畆����
    private IkenshoBodyStatusContainer mindBodyStatusOthers;
    
	


	public IkenshoIshiIkenshoInfoMindBody2() {
		try {
			jbInit();
			setEvent();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void jbInit() throws Exception {
		
		mindBody2Title.setText("�Q�D�S�g�̏�ԂɊւ���ӌ��i�����j");
		
		// Model

		// Layout
		VRLayout layout = new VRLayout();
		layout.setFitHLast(true);
		layout.setFitVLast(true);
		setLayout(layout);
		

		addDownFuzuii();

		addContaints();
		
		this.add(mindBody2Title, VRLayout.NORTH);
		this.add(getContaintsGroup(), VRLayout.CLIENT);
		
	}
	
	
	/**
	 * override���ĐS�g�̏�Ԃ̒ǉ��������`���܂��B
	 */
	protected void addContaints() {

		getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);
		
		//�֐߂̒ɂ�
		getMindBodyStatusContainer().add(getConnectPain(), VRLayout.FLOW_RETURN);
		
		// �����E�s���Ӊ^��
		getMindBodyStatusContainer().add(getDownFuzuiis(), VRLayout.FLOW_RETURN);
		
		// ���
		getMindBodyStatusContainer().add(getJyokusou(), VRLayout.FLOW_RETURN);
		
		// ���̑��̔畆����
		getMindBodyStatusContainer().add(getMindBodyStatusOthers(), VRLayout.FLOW_RETURN);

	}
	
	/**
	 * override���Ď����E�s���Ӊ^���̒ǉ��������`���܂��B
	 */
	protected void addDownFuzuii() {
		
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
        // TODO ������
        getTaikanContainar().setText("�E�̊�");
        ikenshoBodyComponentSetting(getTaikan(),"�@");
        getTaikanContainar().add(getTaikan(),VRLayout.FLOW);        
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
	
	

	
	// --- �֐߂̒ɂ�
	protected IkenshoBodyStatusContainer getConnectPain() {
		if (connectPain == null) {
			
			connectPain = new IkenshoBodyStatusContainer();
			connectPain.setRankBindPath("KANSETU_ITAMI_TEIDO");
			connectPain.setPosBindPath("KANSETU_ITAMI_BUI");
			connectPain.setCheckText("�֐߂̒ɂ�");
			connectPain.setCheckBindPath("KANSETU_ITAMI");
			
			
		    connectPainChange = new ACClearableRadioButtonGroup();
		    connectPainChange.setModel(new VRListModelAdapter(new
	                VRArrayList(Arrays.asList(new
	                		String[] {"���P", "�ێ�", "����"}))));
			
		    connectPainChanges = new ACParentHesesPanelContainer();
		    
		    connectPainChange.setBindPath("KANSETU_ITAMI_CHANGE");
		    connectPainChanges.add(connectPainChange);
		    connectPainChanges.setBeginText("�i�ߋ�6�����̏Ǐ�̕ϓ�");
		    
		    ACPanel p = new ACPanel(new VRLayout());
		    p.setOpaque(false);
		    connectPain.add(p, BorderLayout.SOUTH);
		    
		    p.add(connectPainChanges, VRLayout.EAST);
		    
			
		}
		return connectPain;
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
     * �̊��R���e�i��Ԃ��܂��B
     * @return
     */
    protected IkenshoBodyStatusContainer getTaikan() {
        if(taikan == null){
            taikan = new IkenshoBodyStatusContainer();
            taikan.setRankBindPath("TAIKAN_SICCHOU_MIGI_TEIDO");
            taikan.setCheckBindPath("TAIKAN_SICCHOU_MIGI");
        }
        return taikan;
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
	 * ��ጃR���e�i��Ԃ��܂��B
	 * 
	 * @return ��ጃR���e�i
	 */
	protected IkenshoBodyStatusContainer getJyokusou() {
		
		if (jyokusou == null) {
			jyokusou = new IkenshoBodyStatusContainer();
			
			jyokusou.setCheckBindPath("JOKUSOU");
			jyokusou.setCheckText("���");
			jyokusou.setPosBindPath("JOKUSOU_BUI");
			jyokusou.setRankBindPath("JOKUSOU_TEIDO");
		}
		
		return jyokusou;
	}
	
	
	/**
	 * ���̑��̔畆�����R���e�i
	 * 
	 * @return ���̑��̐S�g�̏�ԃR���e�i
	 */
	protected IkenshoBodyStatusContainer getMindBodyStatusOthers() {
		
		if (mindBodyStatusOthers == null) {
			mindBodyStatusOthers = new IkenshoBodyStatusContainer();
			
			mindBodyStatusOthers.setCheckBindPath("HIFUSIKKAN");
			mindBodyStatusOthers.setCheckText("���̑��̔畆����");
			mindBodyStatusOthers.setPosBindPath("HIFUSIKKAN_BUI");
			mindBodyStatusOthers.setRankBindPath("HIFUSIKKAN_TEIDO");
			
		}
		
		return mindBodyStatusOthers;
	}

	


	/**
	 * �����E�s���Ӊ^���R���e�i��Ԃ��܂��B
	 * 
	 * @return �����E�s���Ӊ^���R���e�i
	 */
	protected ACLabelContainer getDownFuzuiis() {
		
		if (mindBody2DownFuzuiis == null) {
			mindBody2DownFuzuiis = new ACLabelContainer();
			
			mindBody2DownFuzuiis.setLayout(new VRLayout(VRLayout.LEFT, 0, 0));
			mindBody2DownFuzuiis.setContentAreaFilled(true);
			mindBody2DownFuzuiis.setFocusBackground(new Color(204, 204, 255));
			
			mindBody2DownFuzuii.setBindPath("SICCHOU_FLAG");
		}

		return mindBody2DownFuzuiis;
	}

	/**
	 * �����E�s���Ӊ^��(�`�F�b�N)��Ԃ��܂��B
	 * 
	 * @return �����E�s���Ӊ^��(�`�F�b�N)
	 */
	protected ACIntegerCheckBox getDownFuzuii() {
		
		if (mindBody2DownFuzuii == null) {
			mindBody2DownFuzuii = new ACIntegerCheckBox();
		}
		
		return mindBody2DownFuzuii;
	}






	/**
	 * ��ՃO���[�v��Ԃ��܂��B
	 * 
	 * @return ��ՃO���[�v
	 */
	protected ACGroupBox getContaintsGroup() {
		if (mindBody2Group == null) {
			mindBody2Group = new ACGroupBox();
			
			mindBody2Group.setLayout(new BorderLayout());
			mindBody2Group.setText("�g�̂̏��");
			mindBody2Group.setVgap(20);
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
			
			VRLayout layout = new VRLayout();
			
			layout.setVgap(0);
			layout.setHgap(0);
			layout.setAutoWrap(false);
			layout.setLabelMargin(0);
			layout.setAlignment(VRLayout.LEFT);
			
			mindBody2Pos = new VRPanel();
			mindBody2Pos.setLayout(layout);
			mindBody2Pos.setOpaque(true);
		}
		
		return mindBody2Pos;
	}







	public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
		super.initDBCopmponent(dbm);
		
		applyPoolTeikeibun(connectPain.getComboBox(), IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME);
		applyPoolTeikeibun(getJyokusou().getComboBox(), IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME);
		applyPoolTeikeibun(getMindBodyStatusOthers().getComboBox(), IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME);
		
		// �Ɩ��J�ڃR���{
		connectPain.getComboBox().setOptionComboBoxParameters("�֐߂̒ɂ݁E����",IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME,10);
	    getJyokusou().getComboBox().setOptionComboBoxParameters("��ጁE����",IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME,10);
	    getMindBodyStatusOthers().getComboBox().setOptionComboBoxParameters("���̑��̔畆�����E����",IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME,10);
		
	}

	protected void bindSourceInnerBindComponent() throws Exception {
		
		super.bindSourceInnerBindComponent();
		
		// �֐߂̒ɂ�
    	//�֐߂̒ɂ�
    	if (!connectPain.getCheckBox().isSelected()) {
			connectPainChanges.setEnabled(false);
			connectPainChange.setEnabled(false);
			connectPainChange.getClearButton().setEnabled(false);
		};

		
		// �ߋ��f�[�^���p��
    	VRMap map =  (VRMap)getMasterSource();
    	
    	// �̊�-�E�܂��́A�̊�-���Ƀ`�F�b�N����̏ꍇ�́A�̊��L��
    	if ((ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_MIGI"), 0) == 1)
    		|| (ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_HIDARI"), 0) == 1)) {
    		
    		// ���x�͏d�������̗p
        	int teido = Math.max(
        			ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_MIGI_TEIDO"), 0),
        			ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_HIDARI_TEIDO"), 0));
        	
        	
        	getTaikan().getCheckBox().setSelected(true);
        	getTaikan().getRadioGroup().setSelectedIndex(teido);
        	
        	// ���̍��ڂ͍��㖢�g�p��
        	map.put("TAIKAN_SICCHOU_HIDARI", null);
        	map.put("TAIKAN_SICCHOU_HIDARI_TEIDO", null);
    	}
		
		
		
        getDownFuzuii().setSelected(
                getJyoshiLeft().getCheckBox().isSelected()
                        || getJyoshiRight().getCheckBox().isSelected()
                        || getTaikan().getCheckBox().isSelected()
                        || getKashiLeft().getCheckBox().isSelected()
                        || getKashiRight().getCheckBox().isSelected());
        
        downFuzuiiChangeState();

	}
	
	
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
    	
    	//�֐߂̒ɂ�
    	connectPain.getCheckBox().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean select = (e.getStateChange() == ItemEvent.SELECTED);
				connectPainChanges.setEnabled(select);
				connectPainChange.setEnabled(select);
				connectPainChange.getClearButton().setEnabled(select);
			}
		});
    	
    	
    	// �����E�s���Ӊ^��
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
        getTaikan().getCheckBox().setEnabled(enable);
        getKashiRight().getCheckBox().setEnabled(enable);
        getKashiLeft().getCheckBox().setEnabled(enable);
        // �q�𐧌�
        getJyoshiRight().followParentEnabled(enable);
        getJyoshiLeft().followParentEnabled(enable);
        getTaikan().followParentEnabled(enable);
        getKashiRight().followParentEnabled(enable);
        getKashiLeft().followParentEnabled(enable);
    }


}
