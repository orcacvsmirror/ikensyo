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
 * IkenshoIshiIkenshoMindBody1です。
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
	
	// メインパネル
	private ACGroupBox mindBody2Group;
	
	// 利き腕 体重 身長
	private VRPanel mindBody2Connect;
	//(2)四肢欠損 (3)麻痺 (4)筋力の低下 (5)関節
	private VRPanel mindBody2Pos;
	
	// タイトル
	private IkenshoDocumentTabTitleLabel mindBody2Title = new IkenshoDocumentTabTitleLabel();
	
	// 利き腕
	private ACClearableRadioButtonGroup mindBody2ConnectHand = new ACClearableRadioButtonGroup();
	private ACParentHesesPanelContainer mindBody2ConnectHandHeses = new ACParentHesesPanelContainer();
	private ACLabelContainer mindBody2ConnectHandPanel = new ACLabelContainer();
	
	//身長
	private ACLabelContainer mindBody2ConnectHeightPanel = new ACLabelContainer();
	private ACTextField mindBody2ConnectHeight = new ACTextField();
	private VRLabel mindBody2ConnectHeightUnit = new VRLabel();
	
	//体重
	private ACLabelContainer mindBody2ConnectWeightPanel = new ACLabelContainer();
	private ACTextField mindBody2ConnectWeight = new ACTextField();
	private VRLabel mindBody2ConnectWeightUnit = new VRLabel();
	
	//体重の変化
	private ACParentHesesPanelContainer weightChanges= new ACParentHesesPanelContainer();
	private ACClearableRadioButtonGroup weightChange = new ACClearableRadioButtonGroup();
	
	// 四肢欠損
	private IkenshoBodyStatusContainer mindBody2Pos1 = new IkenshoBodyStatusContainer();
	
	// 麻痺
	private ACLabelContainer mahiContainer = new ACLabelContainer();
	private ACIntegerCheckBox mahi = new ACIntegerCheckBox();
	private IkenshoBodyStatusContainer mahiLegLeftUp = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiOther = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiLegRightDown = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiLegLeftDown = new IkenshoBodyStatusContainer();
	private IkenshoBodyStatusContainer mahiLegRightUp = new IkenshoBodyStatusContainer();
	private ACLabelContainer mahis = new ACLabelContainer();

	//筋力の低下
	private IkenshoBodyStatusContainer mindBody2Pos3 = new IkenshoBodyStatusContainer();
	private ACParentHesesPanelContainer muscleChanges  = new ACParentHesesPanelContainer();
	private ACClearableRadioButtonGroup muscleChange = new ACClearableRadioButtonGroup();
	
	//関節の拘縮
	private IkenshoBodyStatusContainer connectKousyuku;
    // 肩関節-右
    private IkenshoBodyStatusContainer kataKousyukuRight;
    // 肩関節-左
    private IkenshoBodyStatusContainer kataKousyukuLeft;
    // 股関節-右
    private IkenshoBodyStatusContainer mataKousyukuRight;
    // 股関節-左
    private IkenshoBodyStatusContainer mataKousyukuLeft;
    // 肘関節-右
    private IkenshoBodyStatusContainer hijiKousyukuRight;
    // 股関節-左
    private IkenshoBodyStatusContainer hijiKousyukuLeft;
    // 膝関節-右
    private IkenshoBodyStatusContainer hizaKousyukuRight;
    // 膝関節-左
    private IkenshoBodyStatusContainer hizaKousyukuLeft;
    // その他
    private IkenshoBodyStatusContainer sonota;
    // 肩関節チェックボックス
    private ACIntegerCheckBox kataCheck;
    // 股関節チェックボックス
    private ACIntegerCheckBox mataCheck;
    // 肘関節チェックボックス
    private ACIntegerCheckBox hijiCheck;
    // 膝関節チェックボックス
    private ACIntegerCheckBox hizaCheck;
    // 関節の拘縮レイアウト
    private VRLayout kousyukuLayout;
    // 関節の拘縮チェックボックス
    private ACIntegerCheckBox kousyukuCheck;
    // 肩関節バックラベルコンテナ
    private ACBackLabelContainer kataBackLabelContainar;
    // 股関節バックラベルコンテナ
    private ACBackLabelContainer mataBackLabelContainar;
    // 肘関節バックラベルコンテナ
    private ACBackLabelContainer hijiBackLabelContainar;
    // 膝関節バックラベルコンテナ
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
		
		
		mindBody2Title.setText("２．身体の状態に関する意見");
		
		// 利き腕
		VRListModelAdapter rightLeftListModel = new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] { "右", "左" })));
		
		mindBody2ConnectHand.setModel(rightLeftListModel);
		mindBody2ConnectHand.setBindPath("KIKIUDE");
		
		mindBody2ConnectHandHeses.add(mindBody2ConnectHand);
		mindBody2ConnectHandPanel.setText("利き腕");
		mindBody2ConnectHandPanel.add(mindBody2ConnectHandHeses);
		
		//身長体重
		mindBody2ConnectHeight.setMaxLength(5);
		mindBody2ConnectHeight.setColumns(6);
		mindBody2ConnectHeight.setHorizontalAlignment(SwingConstants.RIGHT);
		mindBody2ConnectHeight.setBindPath("HEIGHT");
		
		mindBody2ConnectHeightUnit.setText("cm");
		
		mindBody2ConnectHeightPanel.setText("身長=");
		mindBody2ConnectHeightPanel.add(mindBody2ConnectHeight);
		mindBody2ConnectHeightPanel.add(mindBody2ConnectHeightUnit);
		
		
		mindBody2ConnectWeight.setColumns(6);
		mindBody2ConnectWeight.setHorizontalAlignment(SwingConstants.RIGHT);
		mindBody2ConnectWeight.setBindPath("WEIGHT");
		mindBody2ConnectWeight.setMaxLength(5);
		
		mindBody2ConnectWeightUnit.setText("kg");
		
		mindBody2ConnectWeightPanel.setBackground(Color.pink);
		mindBody2ConnectWeightPanel.setText("体重=");
		mindBody2ConnectWeightPanel.add(mindBody2ConnectWeight);
		mindBody2ConnectWeightPanel.add(mindBody2ConnectWeightUnit);
		
		
	    getWeightChange().setModel(new VRListModelAdapter(new
                VRArrayList(Arrays.asList(new
                		String[] {"増加", "維持", "減少"}))));
	    
	    getWeightChange().setBindPath("WEIGHT_CHANGE");
	    getWeightChanges().add(getWeightChange());
	    getWeightChanges().setBeginText("（過去6ヶ月の体重の変化");
	    mindBody2ConnectWeightPanel.add(getWeightChanges());
		
		
		
		// 四肢欠損
		mindBody2Pos1.setCheckText("四肢欠損");
		mindBody2Pos1.setCheckBindPath("SISIKESSON");
		mindBody2Pos1.setPosBindPath("SISIKESSON_BUI");
		
		// 四肢欠損の程度削除
		//mindBody2Pos1.setRankBindPath("SISIKESSON_TEIDO");
		mindBody2Pos1.setRankVisible(false);

		// 麻痺
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
	    
	    mahiLegLeftUp.setCheckText("左上肢");
	    mahiLegRightUp.setCheckText("右上肢");
	    mahiLegLeftDown.setCheckText("左下肢");
	    mahiLegRightDown.setCheckText("右下肢");
	    mahiOther.setCheckText("その他");
	    mahiOther.getComboBox().setColumns(12);
	    mahis.setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
	    mahi.setText("麻痺");
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
        
        
        
        // 筋力の低下
	    mindBody2Pos3.setCheckText("筋力の低下");
		mindBody2Pos3.setCheckBindPath("KINRYOKU_TEIKA");
		mindBody2Pos3.setPosBindPath("KINRYOKU_TEIKA_BUI");
		mindBody2Pos3.setRankBindPath("KINRYOKU_TEIKA_TEIDO");
		
		ACPanel p = new ACPanel(new VRLayout());
		p.setOpaque(false);
		mindBody2Pos3.add(p, BorderLayout.SOUTH);
		
		muscleChange.setModel(new VRListModelAdapter(new
                VRArrayList(Arrays.asList(new
                		String[] {"改善", "維持", "増悪"}))));
		
		muscleChange.setBindPath("KINRYOKU_TEIKA_CHANGE");
		muscleChanges.add(muscleChange);
		muscleChanges.setBeginText("（過去6ヶ月の症状の変動");
		p.add(muscleChanges, VRLayout.EAST);
		
		
		
		
		//関節
		buildKansetuKousyukuGroup();


		addContaints();
		this.add(mindBody2Title, VRLayout.NORTH);
		this.add(getMindBody2Group(), VRLayout.CLIENT);
		
	}
	
    /**
     * 関節の拘縮グループを生成します。
     */
    protected void buildKansetuKousyukuGroup(){
        // レイアウト設定を行う。
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
        
        // レイアウト適用
        getConnectKousyuku().setLayout(getKousyukuLayout());
        getKataBackLabelContainar().setLayout(kataLayout);
        getMataBackLabelContainar().setLayout(mataLayout);
        getHijiBackLabelContainar().setLayout(hijiLayout);
        getHizaBackLabelContainar().setLayout(hizaLayout);
        
        getKousyukuCheck().setText("関節の拘縮");
        // 肩関節グループ ------------------------------------------------------
        getKataCheck().setText("肩関節");
        ikenshoBodyComponentSetting(getKataKousyukuRight(),"右");
        ikenshoBodyComponentSetting(getKataKousyukuLeft(),"左");
        getKataBackLabelContainar().add(getKataCheck(),VRLayout.FLOW);
        getKataBackLabelContainar().add(getKataKousyukuRight(),VRLayout.FLOW);
        getKataBackLabelContainar().add(getKataKousyukuLeft(),VRLayout.FLOW);
        // 肩関節グループ ------------------------------------------------------
        
        // 肘関節グループ ------------------------------------------------------
        getHijiCheck().setText("肘関節");
        ikenshoBodyComponentSetting(getHijiKousyukuRight(),"右");
        ikenshoBodyComponentSetting(getHijiKousyukuLeft(),"左");
        getHijiBackLabelContainar().add(getHijiCheck(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuRight(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuLeft(),VRLayout.FLOW);
        // 肘関節グループ ------------------------------------------------------
        
        // 股関節グループ ------------------------------------------------------
        getMataCheck().setText("股関節");
        ikenshoBodyComponentSetting(getMataKousyukuRight(),"右");
        ikenshoBodyComponentSetting(getMataKousyukuLeft(),"左");
        getMataBackLabelContainar().add(getMataCheck(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuRight(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuLeft(),VRLayout.FLOW);
        // 股関節グループ ------------------------------------------------------        
        
        // 膝関節グループ ------------------------------------------------------ 
        getHizaCheck().setText("膝関節");
        ikenshoBodyComponentSetting(getHizaKousyukuRight(),"右");
        ikenshoBodyComponentSetting(getHizaKousyukuLeft(),"左");
        getHizaBackLabelContainar().add(getHizaCheck(),VRLayout.FLOW);
        getHizaBackLabelContainar().add(getHizaKousyukuRight(),VRLayout.FLOW);
        getHizaBackLabelContainar().add(getHizaKousyukuLeft(),VRLayout.FLOW);
        // 膝関節グループ ------------------------------------------------------

        
        // 背景色設定
        getConnectKousyuku().setFocusBackground(IkenshoConstants.COLOR_DOUBLE_BACK_PANEL_BACKGROUND);
        // 配置関連
        getConnectKousyuku().add(getKousyukuCheck(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getKataBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHijiBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getMataBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHizaBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getSonota(),VRLayout.FLOW_RETURN);
    }
	
	
    
    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
    	
		// 麻痺チェックの連動
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
    	
		// 筋力の低下チェック連動
		mindBody2Pos3.getCheckBox().addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				boolean select = (e.getStateChange() == ItemEvent.SELECTED);
				muscleChanges.setEnabled(select);
				muscleChange.setEnabled(select);
				muscleChange.getClearButton().setEnabled(select);
			}
		});
		
		
        /*
         * 関節の拘縮チェックイベント
         */
        getKousyukuCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                kousyukuChangeState();
            }
        });
        /*
         * 肩関節チェックイベント
         */
        getKataCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                kataKousyukuChangeState();
            }

        });
        /*
         * 股関節チェックイベント
         */
        getMataCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                mataKousyukuChangeState();
            }

        });
        /*
         * 膝関節チェックイベント
         */
        getHizaCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                hizaKousyukuChangeState();
            }

        });
        /*
         * 肘関節チェックイベント
         */
        getHijiCheck().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                hijiKousyukuChangeState();
            }

        });
    }
    
    
	
	
    /**
     * 関節の拘縮をチェックした場合の処理です。
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
        // 子コンポーネントを連動
        getKataKousyukuLeft().followParentEnabled(enable);
        getKataKousyukuRight().followParentEnabled(enable);
        getMataKousyukuLeft().followParentEnabled(enable);
        getMataKousyukuRight().followParentEnabled(enable);
        getHijiKousyukuLeft().followParentEnabled(enable);
        getHijiKousyukuRight().followParentEnabled(enable);
        getHizaKousyukuLeft().followParentEnabled(enable);
        getHizaKousyukuRight().followParentEnabled(enable);
        getSonota().followParentEnabled(enable);
        // さらに子を見て連動制御
        kataKousyukuChangeState();
        mataKousyukuChangeState();
        hizaKousyukuChangeState();
        hijiKousyukuChangeState();
    }
    
    /**
     * 肩関節チェックを選択した場合の処理です。
     */
    protected void kataKousyukuChangeState(){
        boolean enable;
        if(getKataCheck().isEnabled() && getKataCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // 親
        getKataKousyukuLeft().getCheckBox().setEnabled(enable);
        getKataKousyukuRight().getCheckBox().setEnabled(enable);
        // 子
        getKataKousyukuLeft().followParentEnabled(enable);
        getKataKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * 股関節をチェックを選択した場合の処理です。
     */
    protected void mataKousyukuChangeState(){
        boolean enable;
        if(getMataCheck().isEnabled() && getMataCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // 親
        getMataKousyukuLeft().getCheckBox().setEnabled(enable);
        getMataKousyukuRight().getCheckBox().setEnabled(enable);
        // 子
        getMataKousyukuLeft().followParentEnabled(enable);
        getMataKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * 膝関節チェックを選択した場合の処理です。
     */
    protected void hizaKousyukuChangeState(){
        boolean enable;
        if(getHizaCheck().isEnabled() && getHizaCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // 親
        getHizaKousyukuLeft().getCheckBox().setEnabled(enable);
        getHizaKousyukuRight().getCheckBox().setEnabled(enable);
        // 子
        getHizaKousyukuLeft().followParentEnabled(enable);
        getHizaKousyukuRight().followParentEnabled(enable);
        
    }
    
    /**
     * 肘関節を選択した場合の処理です。
     */
    protected void hijiKousyukuChangeState(){
        boolean enable;
        if(getHijiCheck().isEnabled() && getHijiCheck().isSelected()){
            enable = true;
        }else{
            enable = false;
        }
        // 親
        getHijiKousyukuLeft().getCheckBox().setEnabled(enable);
        getHijiKousyukuRight().getCheckBox().setEnabled(enable);
        // 子
        getHijiKousyukuLeft().followParentEnabled(enable);
        getHijiKousyukuRight().followParentEnabled(enable);
        
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
     * 肘関節コンテナ-右を返します。
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
     * 肘関節コンテナ-左を返します。
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
     * 膝関節コンテナ-右を返します。
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
     * 膝関節コンテナ-左を返します。
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
     * 肩関節コンテナ-右を返します。
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
     * 肩関節コンテナ-左を返します。
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
     * 股関節コンテナ-右を返します。
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
     * 股関節コンテナ-左を返します。
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
     * 肘関節チェックを返します。
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
     * 膝関節チェックを返します。
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
     * 肩関節チェックを返します。
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
     * 股関節チェックを返します。
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
     * 関節の拘縮レイアウトを返します。
     * @return
     */
    protected VRLayout getKousyukuLayout() {
        if(kousyukuLayout == null){
            kousyukuLayout = new VRLayout();
        }
        return kousyukuLayout;
    }
    /**
     * 関節の拘縮チェックボックスを返します。
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
     * 肘関節バックラベルコンテナを返します。
     * @return
     */
    protected ACBackLabelContainer getHijiBackLabelContainar() {
        if(hijiBackLabelContainar == null){
            hijiBackLabelContainar = new ACBackLabelContainer();
        }
        return hijiBackLabelContainar;
    }
    /**
     * 膝関節バックラベルコンテナを返します。
     * @return
     */
    protected ACBackLabelContainer getHizaBackLabelContainar() {
        if(hizaBackLabelContainar == null){
            hizaBackLabelContainar = new ACBackLabelContainer();
        }
        return hizaBackLabelContainar;
    }
    /**
     * 肩関節バックラベルコンテナを返します。
     * @return
     */
    protected ACBackLabelContainer getKataBackLabelContainar() {
        if(kataBackLabelContainar == null){
            kataBackLabelContainar = new ACBackLabelContainer();
        }
        return kataBackLabelContainar;
    }
    /**
     * 股関節バックラベルコンテナを返します。
     * @return
     */
    protected ACBackLabelContainer getMataBackLabelContainar() {
        if(mataBackLabelContainar == null){
            mataBackLabelContainar = new ACBackLabelContainer();
        }
        return mataBackLabelContainar;
    }
    /**
     * その他コンテナを返します。
     * @return
     */
    protected IkenshoBodyStatusContainer getSonota() {
        if(sonota == null){
            sonota = new IkenshoBodyStatusContainer();
            //sonota.setRankVisible(false);
            sonota.getCheckBox().setText("その他");
            sonota.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            sonota.setCheckBindPath("KOUSHU_ETC");
            sonota.setPosBindPath("KOUSHU_ETC_BUI");
            sonota.setRankBindPath("KOUSHU_ETC_BUI_TEIDO");
        }
        return sonota;
    }
    
    
    /**
     * 関節の拘縮コンテナを返します。
     * @return 関節の拘縮コンテナ
     */
    protected IkenshoBodyStatusContainer getConnectKousyuku(){
      if(connectKousyuku==null){
        connectKousyuku = new IkenshoBodyStatusContainer();
      }
      return connectKousyuku;
    }
    
    /**
     * 体重の変化コンテナを返します。
     * @return 体重の変化コンテナ
     */
    protected ACParentHesesPanelContainer getWeightChanges(){
      if(weightChanges==null){
        weightChanges = new ACParentHesesPanelContainer();
      }
      return weightChanges;
    }
    /**
     * 体重の変化を返します。
     * @return 体重の変化
     */
    protected ACClearableRadioButtonGroup getWeightChange(){
      if(weightChange==null){
        weightChange = new ACClearableRadioButtonGroup();
      }
      return weightChange;
    }
    
    
    protected ACGroupBox getMindBody2Group() {
    	if (mindBody2Group == null) {
    		// メインパネル
    		mindBody2Group = new ACGroupBox();
    		mindBody2Group.setText("身体の状態");
    		mindBody2Group.setVgap(0);
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
	 * 心身の状態(関節等)グループを返します。
	 * 
	 * @return 心身の状態(関節等)グループ
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
	 * overrideして心身の状態の追加順序を定義します。
	 */
	protected void addContaints() {

		getMindBodyConnectContainer().add(mindBody2ConnectHandPanel, VRLayout.FLOW_INSETLINE_RETURN); //利き腕
		getMindBodyConnectContainer().add(mindBody2ConnectHeightPanel, VRLayout.FLOW_INSETLINE); //身長
		getMindBodyConnectContainer().add(mindBody2ConnectWeightPanel, VRLayout.FLOW_INSETLINE); //体重
		
		getMindBodyStatusContainer().add(mindBody2Pos1, VRLayout.FLOW_INSETLINE_RETURN); //(2)四肢欠損
		getMindBodyStatusContainer().add(mahiContainer, VRLayout.FLOW_INSETLINE_RETURN); //(3)麻痺
		getMindBodyStatusContainer().add(mindBody2Pos3, VRLayout.FLOW_INSETLINE_RETURN); //(4)筋力の低下
		
		getMindBodyStatusContainer().add(getConnectKousyuku(), VRLayout.FLOW_INSETLINE_RETURN); //(5)関節
		
		getMindBody2Group().add(getMindBodyConnectContainer(), VRLayout.FLOW_RETURN);
		getMindBody2Group().add(getMindBodyStatusContainer(), VRLayout.FLOW_RETURN);
	}


	public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

		//--- 定型文名称の設定
		//四肢欠損
		applyPoolTeikeibun(mindBody2Pos1.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME);
		//麻痺
		applyPoolTeikeibun(mahiOther.getComboBox(), IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME);
		//筋力の低下
		applyPoolTeikeibun(mindBody2Pos3.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME);
		//関節の拘縮
		applyPoolTeikeibun(sonota.getComboBox(), IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME);
		
		
		//...編集の機能を使用可能に
		//四肢欠損
		mindBody2Pos1.getComboBox().setOptionComboBoxParameters("四肢欠損・部位",IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME,10);
		//麻痺
		mahiOther.getComboBox().setOptionComboBoxParameters("麻痺(その他)・部位",IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME,10);
		//筋力の低下
		mindBody2Pos3.getComboBox().setOptionComboBoxParameters("筋力の低下・部位",IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME,10);
		//関節の拘縮
		sonota.getComboBox().setOptionComboBoxParameters("関節の拘縮・部位",IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME,10);
	}

	protected void bindSourceInnerBindComponent() throws Exception {
		super.bindSourceInnerBindComponent();
		
		// 麻痺チェック
	    mahi.setSelected(mahiLegLeftUp.isChecked() || mahiLegRightUp.isChecked() ||
                mahiLegLeftDown.isChecked() || mahiLegRightDown.isChecked() ||
                mahiOther.isChecked());
		
	    // 筋力の低下チェック
	    if (!mindBody2Pos3.getCheckBox().isSelected()) {
			muscleChanges.setEnabled(false);
			muscleChange.setEnabled(false);
			muscleChange.getClearButton().setEnabled(false);
	    }
	    
	    
        // 拘縮チェック
        getKousyukuCheck().setSelected(
                getKataCheck().isSelected() || getMataCheck().isSelected()
                        || getHijiCheck().isSelected()
                        || getHizaCheck().isSelected()
                        || getSonota().getCheckBox().isSelected());
        
        kousyukuChangeState();
        
        
		// 過去データ引継ぎ
    	VRMap map =  (VRMap)getMasterSource();
        
    	// 四肢欠損の程度は今後未使用に
    	map.put("SISIKESSON_TEIDO", null);

	}



}
