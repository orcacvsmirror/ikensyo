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
 * IkenshoIshiIkenshoInfoCare1です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoCare1 extends
		IkenshoTabbableChildAffairContainer {
	
	
	// タイトル
	private IkenshoDocumentTabTitleLabel care1Title = new IkenshoDocumentTabTitleLabel();
	
	// --------------------------------------------------------------------
	// 現在、発生の可能性が高い病態とその対処方針
	private ACGroupBox care1Group;
	
	private ACIntegerCheckBox care1Shikkin = new ACIntegerCheckBox(); //尿失禁
	private ACIntegerCheckBox care1Tentou = new ACIntegerCheckBox(); //転倒・骨折
	private ACIntegerCheckBox care1Haikai = new ACIntegerCheckBox(); //徘徊
	private ACIntegerCheckBox care1Jyokusou = new ACIntegerCheckBox(); //褥瘡
	private ACIntegerCheckBox care1Haien = new ACIntegerCheckBox(); //嚥下性肺炎
	private ACIntegerCheckBox care1Chouheisoku = new ACIntegerCheckBox(); //腸閉塞
	private ACIntegerCheckBox care1Ekikan = new ACIntegerCheckBox(); //易感染性
	private ACIntegerCheckBox care1ShinpaiDown = new ACIntegerCheckBox(); //心肺機能の低下
	private ACIntegerCheckBox care1Pain = new ACIntegerCheckBox(); //疼痛
	private ACIntegerCheckBox care1Dassui = new ACIntegerCheckBox(); //脱水
	
	private ACIntegerCheckBox care1Koudou = new ACIntegerCheckBox(); //行動障害
	private ACIntegerCheckBox care1Seishin = new ACIntegerCheckBox(); //精神症状の増悪
	private ACIntegerCheckBox care1Keiren = new ACIntegerCheckBox(); //けいれん発作
	
	// その他
	private ACLabelContainer care1Others = new ACLabelContainer();
	private ACIntegerCheckBox care1Other = new ACIntegerCheckBox();
	private IkenshoOptionComboBox care1OtherName = new IkenshoOptionComboBox();
	private ACParentHesesPanelContainer care1OtherNameHeses = new ACParentHesesPanelContainer();
	private ACLabelContainer care1OtherNames = new ACLabelContainer();
	
	//対処方針
	private ACLabelContainer care1OtherTaisyos = new ACLabelContainer();
	private VRLabel care1AbstractionMiddleMessage = new VRLabel();
	private IkenshoOptionComboBox care1OtherTaisyo = new IkenshoOptionComboBox();
	
	
	// --------------------------------------------------------------------
	// 障害福祉サービスの利用時に関する医学的観点からの留意事項
	private ACGroupBox care2Group;
	
	// 血圧
	private ACLabelContainer care2Ketsuattsus = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Ketsuattsu = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2KetsuattsuHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2KetsuattsuValue = new IkenshoOptionComboBox();
	
	// 嚥下
	private ACLabelContainer care2Enges = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Enge = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2EngeHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2EngeValue = new IkenshoOptionComboBox();
	
	// 摂食
	private ACLabelContainer care2Sesshokus = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Sesshoku = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2SesshokuHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2SesshokuValue = new IkenshoOptionComboBox();
	
	// 移動
	private ACLabelContainer care2Moves = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Move = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2MoveHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2MoveValue = new IkenshoOptionComboBox();
	
	// 行動
	private ACLabelContainer care2Actions = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Action = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2ActionHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2ActionValue = new IkenshoOptionComboBox();
	
	// 精神
	private ACLabelContainer care2Minds = new ACLabelContainer();
	private ACClearableRadioButtonGroup care2Mind = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer care2MindHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2MindValue = new IkenshoOptionComboBox();
	
	// その他
	private ACLabelContainer care2ServiceOthers = new ACLabelContainer();
	private ACParentHesesPanelContainer care2ServiceOtherHeses = new ACParentHesesPanelContainer();
	private IkenshoOptionComboBox care2ServiceOtherValue = new IkenshoOptionComboBox();
	
	
	// --------------------------------------------------------------------
	// 感染症の有無(有の場合は具体的に記入)
	private ACGroupBox care3Group;
	private ACLabelContainer care3Kansens = new ACLabelContainer();
	private ACValueArrayRadioButtonGroup care3Kansen = new ACValueArrayRadioButtonGroup();
	private IkenshoOptionComboBox care3KansenName = new IkenshoOptionComboBox();
	

	
	
	public IkenshoIshiIkenshoInfoCare1() {
		try {
			//画面構成処理
			jbInit();
            // イベントリスナ定義
            setEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private void jbInit() throws Exception {
		
		care1Title.setText("５．サービス利用に関する意見");
		
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
	
	// 現在、発生の可能性が高い病態とその対処方針
	private void buildCare1Group() {
		
		
		care1Shikkin.setBindPath("NYOUSIKKIN");
		care1Shikkin.setText("尿失禁");
		//care1Shikkin.setValueBindPath("NYOUSIKKIN_TAISHO_HOUSIN");
		
		care1Tentou.setBindPath("TENTOU_KOSSETU");
		care1Tentou.setText("転倒・骨折");
		//care1Tentou.setValueBindPath("TENTOU_KOSSETU_TAISHO_HOUSIN");
		
		care1Haikai.setBindPath("HAIKAI_KANOUSEI");
		care1Haikai.setText("徘徊");
		//care1Haikai.setValueBindPath("HAIKAI_KANOUSEI_TAISHO_HOUSIN");
		
		care1Jyokusou.setBindPath("JOKUSOU_KANOUSEI");
		care1Jyokusou.setText("褥瘡");
		//care1Jyokusou.setValueBindPath("JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
		
		care1Haien.setBindPath("ENGESEIHAIEN");
		care1Haien.setText("嚥下性肺炎");
		//care1Haien.setValueBindPath("ENGESEIHAIEN_TAISHO_HOUSIN");
		
		care1Chouheisoku.setBindPath("CHOUHEISOKU");
		care1Chouheisoku.setText("腸閉塞");
		//care1Chouheisoku.setValueBindPath("CHOUHEISOKU_TAISHO_HOUSIN");
		
		care1Ekikan.setBindPath("EKIKANKANSEN");
		care1Ekikan.setText("易感染性");
		//care1Ekikan.setValueBindPath("EKIKANKANSEN_TAISHO_HOUSIN");
		
		care1ShinpaiDown.setBindPath("SINPAIKINOUTEIKA");
		care1ShinpaiDown.setText("心肺機能の低下");
		//care1ShinpaiDown.setValueBindPath("SINPAIKINOUTEIKA_TAISHO_HOUSIN");
		
		care1Pain.setBindPath("ITAMI");
		care1Pain.setText("疼痛");
		//care1Pain.setValueBindPath("ITAMI_TAISHO_HOUSIN");
		
		care1Dassui.setBindPath("DASSUI");
		care1Dassui.setText("脱水");
		//care1Dassui.setValueBindPath("DASSUI_TAISHO_HOUSIN");
		
		care1Koudou.setBindPath("KOUDO_SHOGAI");
		care1Koudou.setText("行動障害");
		
		care1Seishin.setBindPath("SEISIN_ZOAKU");
		care1Seishin.setText("精神症状の増悪");
		
		
		care1Keiren.setBindPath("KEIREN_HOSSA");
		care1Keiren.setText("けいれん発作");
		
		care1Other.setBindPath("BYOUTAITA");
		care1Other.setActionCommand("その他");
		care1Other.setText("その他");
		
		
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
		
		
		
		
		//対処方針
		care1AbstractionMiddleMessage.setText("→　対処方針");
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
	
	
	// 障害福祉サービスの利用時に関する医学的観点からの留意事項
	private void buildCare2Group() {
		
		VRListModelAdapter adp = new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] { "特になし　", "有" })));
		
		
		// 血圧
        care2Ketsuattsu.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2KetsuattsuValue, care2KetsuattsuHeses }, 1));
		
		care2Ketsuattsus.setText("・血圧について　　");
		
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
		
		
		// 嚥下
		care2Enge.addListSelectionListener(
				new ACFollowDisableSelectionListener(
						new JComponent[] { care2EngeValue, care2EngeHeses }, 1));
		
		care2Enges.setText("・嚥下について　　");
		
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


		// 摂食
		care2Sesshoku.addListSelectionListener(
				new ACFollowDisableSelectionListener(
					new JComponent[] { care2SesshokuValue, care2SesshokuHeses }, 1));
		
		care2Sesshokus.setText("・摂食について　　");
		
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

		
		// 移動
        care2Move.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2MoveValue, care2MoveHeses }, 1));

        care2Moves.setText("・移動について　　");

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
		
		
		// 行動障害
        care2Action.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2ActionValue, care2ActionHeses }, 1));

        care2Actions.setText("・行動障害について");

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
        
        
		// 精神症状
        care2Mind.addListSelectionListener(
        		new ACFollowDisableSelectionListener(
        				new JComponent[] { care2MindValue, care2MindHeses }, 1));

        care2Minds.setText("・精神症状について");

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
        
		
		
        //その他
        care2ServiceOthers.setText("・その他");

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
	
	
	// 感染症の有無
	private void buildCare3Group() {
		
		VRListModelAdapter adp = new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] { "有", "無", "不明" })));
		
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
			care1Group.setText("現在、発生の可能性が高い病態とその対処方針");
		}
		return care1Group;
	}
	
	
	protected ACGroupBox getCare2Group() {
		if (care2Group == null) {
			care2Group = new ACGroupBox();
			care2Group.setText("障害福祉サービスの利用時に関する医学的観点からの留意事項");
			
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
			care3Group.setText("感染症の有無（有の場合は具体的に記入）（30文字以内）");
			
		}
		return care3Group;
	}


	
	
    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
    	// その他チェックと入力の連動
		care1Other.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				changeSonotaState();
			}
		});
		
		
		// チェックと連動して、テキストの入力を許可する
		Care1GroupCheckItemListener listener = new Care1GroupCheckItemListener();
		
		//尿失禁
		care1Shikkin.addItemListener(listener);
		//転倒・骨折
		care1Tentou.addItemListener(listener);
		//徘徊
		care1Haikai.addItemListener(listener);
		//褥瘡
		care1Jyokusou.addItemListener(listener);
		//嚥下性肺炎
		care1Haien.addItemListener(listener);
		//腸閉塞
		care1Chouheisoku.addItemListener(listener);
		//易感染性
		care1Ekikan.addItemListener(listener);
		//心肺機能の低下
		care1ShinpaiDown.addItemListener(listener);
		//疼痛
		care1Pain.addItemListener(listener);
		//脱水
		care1Dassui.addItemListener(listener);
		//行動障害
		care1Koudou.addItemListener(listener);
		//精神症状の増悪
		care1Seishin.addItemListener(listener);
		//けいれん発作
		care1Keiren.addItemListener(listener);
		//その他
		care1Other.addItemListener(listener);
		
    }
	

	public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
		
		// --- コンボボックスの内容設定
		// 発生の可能性が高い病態 - その他
		applyPoolTeikeibun(care1OtherName, IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME);
		// 発生の可能性が高い病態 - 対処方針
		applyPoolTeikeibun(care1OtherTaisyo, IkenshoCommon.TEIKEI_TAISHO_HOUSIN_NAME);
		
		// 留意事項 - 血圧
        applyPoolTeikeibun(care2KetsuattsuValue, IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME);
        // 留意事項 - 嚥下
        applyPoolTeikeibun(care2EngeValue, IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME);
        // 留意事項 - 摂食
        applyPoolTeikeibun(care2SesshokuValue, IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME);
        // 留意事項 - 移動
        applyPoolTeikeibun(care2MoveValue, IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME);
        // 留意事項 - 行動障害
        applyPoolTeikeibun(care2ActionValue, IkenshoCommon.TEIKEI_CARE_SERVICE_ACTION_NAME);
        // 留意事項 - 精神症状
        applyPoolTeikeibun(care2MindValue, IkenshoCommon.TEIKEI_CARE_SERVICE_MIND_NAME);
        // 留意事項 - その他
        applyPoolTeikeibun(care2ServiceOtherValue, IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME);
        
        // 感染症の有無
        applyPoolTeikeibun(care3KansenName, IkenshoCommon.TEIKEI_INFECTION_NAME);
        
        
        // ...編集を使用可能に
        // 発生の可能性が高い病態 - その他
        care1OtherName.setOptionComboBoxParameters("その他", IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15);
        // 発生の可能性が高い病態 - 対処方針
        care1OtherTaisyo.setOptionComboBoxParameters("対処方針", IkenshoCommon.TEIKEI_TAISHO_HOUSIN_NAME, 45);
        
        // 留意事項 - 血圧
        care2KetsuattsuValue.setOptionComboBoxParameters("血圧", IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30);
        // 留意事項 - 嚥下
        care2EngeValue.setOptionComboBoxParameters("嚥下", IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30);
        // 留意事項 - 摂食
        care2SesshokuValue.setOptionComboBoxParameters("摂食", IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30);
        // 留意事項 - 移動
        care2MoveValue.setOptionComboBoxParameters("移動", IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30);
        // 留意事項 - 行動障害
        care2ActionValue.setOptionComboBoxParameters("行動障害", IkenshoCommon.TEIKEI_CARE_SERVICE_ACTION_NAME, 30);
        // 留意事項 - 精神症状
        care2MindValue.setOptionComboBoxParameters("精神症状", IkenshoCommon.TEIKEI_CARE_SERVICE_MIND_NAME, 30);
        // 留意事項 - その他
        care2ServiceOtherValue.setOptionComboBoxParameters("その他", IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50);
        
        // 感染症の有無
        care3KansenName.setOptionComboBoxParameters("有の場合", IkenshoCommon.TEIKEI_INFECTION_NAME, 30);
        
        
        
        if(getMasterAffair() instanceof IkenshoIkenshoInfo){
            // スナップショットについて
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
        
        
    	// その他チェックと入力の連動
        changeSonotaState();
        
		// チェックが一つでも有れば、対処方針入力化
        changeTaisyoState();
        
        
        // 主治医意見書から展開した時、値がないので設定
        // 留意事項 - 行動障害
        if (care2Action.getSelectedIndex() == 0) {
        	care2Action.setSelectedIndex(1);
        }

        // 留意事項 - 精神症状
        if (care2Mind.getSelectedIndex() == 0) {
        	care2Mind.setSelectedIndex(1);
        }
        
        // 過去データ引継ぎ
    	VRMap map =  (VRMap)getMasterSource();
    	
    	// 対処方針が空白なら、作成を試みる
    	if ("".equals(ACCastUtilities.toString(map.get("TAISHO_HOUSIN")))) {
    		care1OtherTaisyo.setText(createTaisho(map));
    	}
    	
    	// 尿失禁
    	map.put("NYOUSIKKIN_TAISHO_HOUSIN", null);
    	// 転倒・骨折
    	map.put("TENTOU_KOSSETU_TAISHO_HOUSIN", null);
    	// 徘徊
    	map.put("HAIKAI_KANOUSEI_TAISHO_HOUSIN", null);
    	// 褥瘡
    	map.put("JOKUSOU_KANOUSEI_TAISHO_HOUSIN", null);
    	// 嚥下性肺炎
    	map.put("ENGESEIHAIEN_TAISHO_HOUSIN", null);
    	// 腸閉塞
    	map.put("CHOUHEISOKU_TAISHO_HOUSIN", null);
    	// 易感染性
    	map.put("EKIKANKANSEN_TAISHO_HOUSIN", null);
    	// 心肺機能の低下
    	map.put("SINPAIKINOUTEIKA_TAISHO_HOUSIN", null);
    	// 疼痛
    	map.put("ITAMI_TAISHO_HOUSIN", null);
    	// 脱水
    	map.put("DASSUI_TAISHO_HOUSIN", null);
    	// その他
    	map.put("BYOUTAITA_TAISHO_HOUSIN", null);
    }
    
    
    private String createTaisho(VRMap map) throws Exception {
    	
    	StringBuffer sb = new StringBuffer();
    	
    	// 尿失禁
    	sb.append(getTaisho(map, "NYOUSIKKIN_TAISHO_HOUSIN"));
    	// 転倒・骨折
    	sb.append(getTaisho(map, "TENTOU_KOSSETU_TAISHO_HOUSIN"));
    	// 徘徊
    	sb.append(getTaisho(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
    	// 褥瘡
    	sb.append(getTaisho(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
    	// 嚥下性肺炎
    	sb.append(getTaisho(map, "ENGESEIHAIEN_TAISHO_HOUSIN"));
    	// 腸閉塞
    	sb.append(getTaisho(map, "CHOUHEISOKU_TAISHO_HOUSIN"));
    	// 易感染性
    	sb.append(getTaisho(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
    	// 心肺機能の低下
    	sb.append(getTaisho(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
    	// 疼痛
    	sb.append(getTaisho(map, "ITAMI_TAISHO_HOUSIN"));
    	// 脱水
    	sb.append(getTaisho(map, "DASSUI_TAISHO_HOUSIN"));
    	// その他
    	sb.append(getTaisho(map, "BYOUTAITA_TAISHO_HOUSIN"));
    	
    	if (sb.toString().endsWith("、")) {
    		int len = sb.length();
    		sb.replace(len - 1, len, "。");
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
    	
    	if (value.endsWith("、") || value.endsWith("。")) {
    		return value;
    	}
    	
    	return value + "、";
    	
    }
    
    
    // その他チェックとその他入力の連動
    private void changeSonotaState() {
        boolean select =  care1Other.isSelected();
        care1OtherName.setEnabled(select);
        care1OtherNameHeses.setEnabled(select);
    }
    
    
    // 対処方針入力のEnabled制御
    private void changeTaisyoState() {
		// チェックが一つでも有れば、対処方針入力化
		boolean selected = isCare1GroupChecked();
		care1OtherTaisyo.setEnabled(selected);
		care1AbstractionMiddleMessage.setEnabled(selected);
    }
    
    
    
    private boolean isCare1GroupChecked() {
		if (care1Shikkin.isSelected() //尿失禁
				|| care1Tentou.isSelected() //転倒・骨折
				|| care1Haikai.isSelected() //徘徊
				|| care1Jyokusou.isSelected() //褥瘡
				|| care1Haien.isSelected() //嚥下性肺炎
				|| care1Chouheisoku.isSelected() //腸閉塞
				|| care1Ekikan.isSelected() //易感染性
				|| care1ShinpaiDown.isSelected() //心肺機能の低下
				|| care1Pain.isSelected() //疼痛
				|| care1Dassui.isSelected() //脱水
				|| care1Koudou.isSelected() //行動障害
				|| care1Seishin.isSelected() //精神症状の増悪
				|| care1Keiren.isSelected() //けいれん発作
				|| care1Other.isSelected() //その他
			){
			
			return true;
		}
		
		return false;
    }
    
    
    
    
    public boolean noControlError() throws Exception {
        if(!super.noControlError()){
            return false;
        }

        // その他チェックが有効である場合
        if(care1Other.isSelected()){
            if (IkenshoCommon.isNullText(care1OtherName.getText())) {
                ACMessageBox.showExclamation("「現在、発生の可能性が高い病態とその対処方針（その他）」で未記入があります。");
                care1Other.requestFocus();
                return false;
            }
        }
        
        return true;
    }
    
    
    

	// オーバーライドして、初期値を設定
    public VRMap createSourceInnerBindComponent() {
        VRMap map = super.createSourceInnerBindComponent();
        
        map.setData("KETUATU", new Integer(1)); //血圧
        map.setData("ENGE", new Integer(1)); //嚥下
        map.setData("SESHOKU", new Integer(1)); //摂食
        map.setData("IDOU", new Integer(1)); //移動
        map.setData("SFS_KOUDO", new Integer(1)); //行動障害
        map.setData("SFS_SEISIN", new Integer(1)); //精神症状

        map.setData("KANSENSHOU", new Integer(2)); //感染症の有無
        
        return map;
    }
    
    
    // 対処方針のEnable制御のため、チェックイベント
    private class Care1GroupCheckItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			changeTaisyoState();
		}
    }

}
