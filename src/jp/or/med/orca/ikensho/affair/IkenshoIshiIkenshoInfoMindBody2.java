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
 * IkenshoIshiIkenshoInfoMindBody2です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 * @version 2.0 2014/02/14
 */
public class IkenshoIshiIkenshoInfoMindBody2 extends IkenshoTabbableChildAffairContainer {
	
	
	//タイトル
	private IkenshoDocumentTabTitleLabel mindBody2Title = new IkenshoDocumentTabTitleLabel();
	
	//メインパネル
	private ACGroupBox mindBody2Group;
	private VRPanel mindBody2Pos;
	
	
	// 関節の痛み
	private IkenshoBodyStatusContainer connectPain;
	private ACParentHesesPanelContainer connectPainChanges;
	private ACClearableRadioButtonGroup connectPainChange;
	
	
	// 失調・不随意運動
	private ACLabelContainer mindBody2DownFuzuiis;
	private ACIntegerCheckBox mindBody2DownFuzuii;
    // 上肢コンテナ-右
    private IkenshoBodyStatusContainer jyoshiRight;
    // 上肢コンテナ-左
    private IkenshoBodyStatusContainer jyoshiLeft;
    // 体幹コンテナ
    private IkenshoBodyStatusContainer taikan;
    // 下肢コンテナ-右
    private IkenshoBodyStatusContainer kashiRight;
    // 下肢コンテナ-左
    private IkenshoBodyStatusContainer kashiLeft;
    // 上肢バックラベルコンテナ
    private ACBackLabelContainer jyoshiContainar;
    // 体幹バックラベルコンテナ
    private ACBackLabelContainer taikanContainar;
    // 下肢バックラベルコンテナ
    private ACBackLabelContainer kashiContainar;
    
    
    // 褥瘡
    private IkenshoBodyStatusContainer jyokusou;
    
    // その他の皮膚疾患
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
		
		mindBody2Title.setText("２．心身の状態に関する意見（続き）");
		
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
	 * overrideして心身の状態の追加順序を定義します。
	 */
	protected void addContaints() {

		getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);
		
		//関節の痛み
		getMindBodyStatusContainer().add(getConnectPain(), VRLayout.FLOW_RETURN);
		
		// 失調・不随意運動
		getMindBodyStatusContainer().add(getDownFuzuiis(), VRLayout.FLOW_RETURN);
		
		// 褥瘡
		getMindBodyStatusContainer().add(getJyokusou(), VRLayout.FLOW_RETURN);
		
		// その他の皮膚疾患
		getMindBodyStatusContainer().add(getMindBodyStatusOthers(), VRLayout.FLOW_RETURN);

	}
	
	/**
	 * overrideして失調・不随意運動の追加順序を定義します。
	 */
	protected void addDownFuzuii() {
		
        // 文言設定
        getDownFuzuii().setText("失調・不随意運動");
        
        // ・上肢コンテナ設定 --------------------------------------------------------------------
        getJyoshiContainar().setText("・上肢");
        ikenshoBodyComponentSetting(getJyoshiRight(),"右");
        ikenshoBodyComponentSetting(getJyoshiLeft(),"左");
        getJyoshiContainar().add(getJyoshiRight(),VRLayout.FLOW);
        getJyoshiContainar().add(getJyoshiLeft(),VRLayout.FLOW);
        // ---------------------------------------------------------------------------------------
        
        // ・体幹コンテナ設定 --------------------------------------------------------------------
        // TODO 微調整
        getTaikanContainar().setText("・体幹");
        ikenshoBodyComponentSetting(getTaikan(),"　");
        getTaikanContainar().add(getTaikan(),VRLayout.FLOW);        
        // ---------------------------------------------------------------------------------------
        
        // ・下肢コンテナ設定 --------------------------------------------------------------------
        getKashiContainar().setText("・下肢");
        ikenshoBodyComponentSetting(getKashiRight(),"右");
        ikenshoBodyComponentSetting(getKashiLeft(),"左");
        getKashiContainar().add(getKashiRight(),VRLayout.FLOW);
        getKashiContainar().add(getKashiLeft(),VRLayout.FLOW);        
        // ---------------------------------------------------------------------------------------
        
        // 背景色設定
        getDownFuzuiis().setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
        // 配置関係
        getDownFuzuiis().add(getDownFuzuii(),VRLayout.FLOW_RETURN);
        getDownFuzuiis().add(getJyoshiContainar(),VRLayout.FLOW_RETURN);
        getDownFuzuiis().add(getTaikanContainar(),VRLayout.FLOW_RETURN);
        getDownFuzuiis().add(getKashiContainar(),VRLayout.FLOW_RETURN);
		
	}
	
	

	
	// --- 関節の痛み
	protected IkenshoBodyStatusContainer getConnectPain() {
		if (connectPain == null) {
			
			connectPain = new IkenshoBodyStatusContainer();
			connectPain.setRankBindPath("KANSETU_ITAMI_TEIDO");
			connectPain.setPosBindPath("KANSETU_ITAMI_BUI");
			connectPain.setCheckText("関節の痛み");
			connectPain.setCheckBindPath("KANSETU_ITAMI");
			
			
		    connectPainChange = new ACClearableRadioButtonGroup();
		    connectPainChange.setModel(new VRListModelAdapter(new
	                VRArrayList(Arrays.asList(new
	                		String[] {"改善", "維持", "増悪"}))));
			
		    connectPainChanges = new ACParentHesesPanelContainer();
		    
		    connectPainChange.setBindPath("KANSETU_ITAMI_CHANGE");
		    connectPainChanges.add(connectPainChange);
		    connectPainChanges.setBeginText("（過去6ヶ月の症状の変動");
		    
		    ACPanel p = new ACPanel(new VRLayout());
		    p.setOpaque(false);
		    connectPain.add(p, BorderLayout.SOUTH);
		    
		    p.add(connectPainChanges, VRLayout.EAST);
		    
			
		}
		return connectPain;
	}
	
	
    /**
     * 上肢バックラベルコンテナを返します。
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
     * 上肢コンテナ-左を返します。
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
     * 上肢コンテナ-右を返します。 
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
     * 下肢バックラベルコンテナを返します。
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
     * 下肢コンテナ-左を返します。
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
     * 下肢コンテナ-右を返します。
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
     * 体幹バックラベルコンテナを返します。
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
     * 体幹コンテナを返します。
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
     * IkenshoBodyStatusContainerComponentの共通設定を行います。
     * @param comp
     * @param checkText
     */
    protected void ikenshoBodyComponentSetting(Component comp,String checkText){
        if(comp instanceof IkenshoBodyStatusContainer){
            //  要求サイズ0
            ((IkenshoBodyStatusContainer)comp).getCheckBox().setPreferredSize(null);
            ((IkenshoBodyStatusContainer)comp).setPosVisible(false);
            ((IkenshoBodyStatusContainer)comp).setCheckText(checkText);
        }
    }
	
	

	/**
	 * 褥瘡コンテナを返します。
	 * 
	 * @return 褥瘡コンテナ
	 */
	protected IkenshoBodyStatusContainer getJyokusou() {
		
		if (jyokusou == null) {
			jyokusou = new IkenshoBodyStatusContainer();
			
			jyokusou.setCheckBindPath("JOKUSOU");
			jyokusou.setCheckText("褥瘡");
			jyokusou.setPosBindPath("JOKUSOU_BUI");
			jyokusou.setRankBindPath("JOKUSOU_TEIDO");
		}
		
		return jyokusou;
	}
	
	
	/**
	 * その他の皮膚疾患コンテナ
	 * 
	 * @return その他の心身の状態コンテナ
	 */
	protected IkenshoBodyStatusContainer getMindBodyStatusOthers() {
		
		if (mindBodyStatusOthers == null) {
			mindBodyStatusOthers = new IkenshoBodyStatusContainer();
			
			mindBodyStatusOthers.setCheckBindPath("HIFUSIKKAN");
			mindBodyStatusOthers.setCheckText("その他の皮膚疾患");
			mindBodyStatusOthers.setPosBindPath("HIFUSIKKAN_BUI");
			mindBodyStatusOthers.setRankBindPath("HIFUSIKKAN_TEIDO");
			
		}
		
		return mindBodyStatusOthers;
	}

	


	/**
	 * 失調・不随意運動コンテナを返します。
	 * 
	 * @return 失調・不随意運動コンテナ
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
	 * 失調・不随意運動(チェック)を返します。
	 * 
	 * @return 失調・不随意運動(チェック)
	 */
	protected ACIntegerCheckBox getDownFuzuii() {
		
		if (mindBody2DownFuzuii == null) {
			mindBody2DownFuzuii = new ACIntegerCheckBox();
		}
		
		return mindBody2DownFuzuii;
	}






	/**
	 * 基盤グループを返します。
	 * 
	 * @return 基盤グループ
	 */
	protected ACGroupBox getContaintsGroup() {
		if (mindBody2Group == null) {
			mindBody2Group = new ACGroupBox();
			
			mindBody2Group.setLayout(new BorderLayout());
			mindBody2Group.setText("身体の状態");
			mindBody2Group.setVgap(20);
		}
		return mindBody2Group;
	}

	/**
	 * 心身の状態(部位)グループを返します。
	 * 
	 * @return 心身の状態(部位)グループ
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
		
		// 業務遷移コンボ
		connectPain.getComboBox().setOptionComboBoxParameters("関節の痛み・部位",IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME,10);
	    getJyokusou().getComboBox().setOptionComboBoxParameters("褥瘡・部位",IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME,10);
	    getMindBodyStatusOthers().getComboBox().setOptionComboBoxParameters("その他の皮膚疾患・部位",IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME,10);
		
	}

	protected void bindSourceInnerBindComponent() throws Exception {
		
		super.bindSourceInnerBindComponent();
		
		// 関節の痛み
    	//関節の痛み
    	if (!connectPain.getCheckBox().isSelected()) {
			connectPainChanges.setEnabled(false);
			connectPainChange.setEnabled(false);
			connectPainChange.getClearButton().setEnabled(false);
		};

		
		// 過去データ引継ぎ
    	VRMap map =  (VRMap)getMasterSource();
    	
    	// 体幹-右または、体幹-左にチェックありの場合は、体幹有効
    	if ((ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_MIGI"), 0) == 1)
    		|| (ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_HIDARI"), 0) == 1)) {
    		
    		// 程度は重い方を採用
        	int teido = Math.max(
        			ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_MIGI_TEIDO"), 0),
        			ACCastUtilities.toInt(map.get("TAIKAN_SICCHOU_HIDARI_TEIDO"), 0));
        	
        	
        	getTaikan().getCheckBox().setSelected(true);
        	getTaikan().getRadioGroup().setSelectedIndex(teido);
        	
        	// 左の項目は今後未使用に
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
     * イベントリスナを定義します。
     */
    protected void setEvent(){
    	
    	//関節の痛み
    	connectPain.getCheckBox().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				boolean select = (e.getStateChange() == ItemEvent.SELECTED);
				connectPainChanges.setEnabled(select);
				connectPainChange.setEnabled(select);
				connectPainChange.getClearButton().setEnabled(select);
			}
		});
    	
    	
    	// 失調・不随意運動
        getDownFuzuii().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                downFuzuiiChangeState();
            }
        });
    }
	
	
    /**
     * 失調・不随意運動に関する処理です。
     */
    protected void downFuzuiiChangeState(){
        boolean enable;
        
        if(getDownFuzuii().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // チェックボックスを制御
        getJyoshiRight().getCheckBox().setEnabled(enable);
        getJyoshiLeft().getCheckBox().setEnabled(enable);
        getTaikan().getCheckBox().setEnabled(enable);
        getKashiRight().getCheckBox().setEnabled(enable);
        getKashiLeft().getCheckBox().setEnabled(enable);
        // 子を制御
        getJyoshiRight().followParentEnabled(enable);
        getJyoshiLeft().followParentEnabled(enable);
        getTaikan().followParentEnabled(enable);
        getKashiRight().followParentEnabled(enable);
        getKashiLeft().followParentEnabled(enable);
    }


}
