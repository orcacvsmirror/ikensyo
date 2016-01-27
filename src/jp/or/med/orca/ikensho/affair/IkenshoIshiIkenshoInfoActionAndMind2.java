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
 * IkenshoIshiIkenshoInfoActionAndMind2です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 */
public class IkenshoIshiIkenshoInfoActionAndMind2 extends IkenshoTabbableChildAffairContainer {
	
    private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
    
    // -------------------------------------------------------------------------------------
    // 精神・神経症状の有無グループ(統括)
    private ACGroupBox seishinShinkeiGroup = new ACGroupBox();
    // 選択肢リスト
    private VRListModelAdapter existEmptyListModel = new VRListModelAdapter(new
            VRArrayList(Arrays.asList(new String[] {"有", "無"})));

    
    // 専門医受診の有無　文字数説明ラベル
    private ACLabel senmoniExplanation;
    // 行動障害 文字数説明ラベル
    private ACLabel koudouShougaiSonotaExplanation;
    
    // 精神・神経障害の有無ラジオグループ
    private ACClearableRadioButtonGroup seishinShinkeiRadioGroup = new ACClearableRadioButtonGroup();
    // 精神・神経症状 - 有の場合　ラベルコンテナ
    private ACLabelContainer seishinShinkeiShojyoLabelConainer;
    // 精神・神経症状パネル上部 - 右
    private ACPanel seishinShinkeiShojyomeiPanelTopRightPanel = new ACPanel();
    // 精神・神経症状パネル下部
    private ACPanel seishinShinkeiShojyomeiPanelDownPanel = new ACPanel();
    // 精神・神経症状パネル中部
    private ACPanel seishinShinkeiShojyomeiPanelMiddlePanel = new ACPanel();
    // 精神・神経症状チェックボックス郡------------------------------------------------------
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
    // その他用Heses
    private ACParentHesesPanelContainer seishinShinkeiSonotaHesesPanel = new ACParentHesesPanelContainer();
    // その他統合用パネル
    private ACPanel seishinShinkeiSonotaPanel = new ACPanel();
    // その他テキストフィールド
    private ACTextField seihinShinkeiSonotaText = new ACTextField();
    // その他登録可能文字数表示ラベル
    private ACLabel seishinShinkeiSonotaExplanation;
    // 専門医受診の有無
    private ACClearableRadioButtonGroup seishinShinkeiSenmoniRadioGroup = new ACClearableRadioButtonGroup();
    // 専門医受診の有無Heses
    private ACParentHesesPanelContainer seishinShinkeiSenmoniHesesPanel = new ACParentHesesPanelContainer();
    // 専門医受診の有無コンボ
    private ACComboBox seishinShinkeiSenmoniComboBox = new ACComboBox();
    // 専門医受診の有無ラベルコンテナ
    private ACLabelContainer seishinShinkeiSenmoniLabelContainer;
    
    
	// -------------------------------------------------------------------------------------
    // てんかんグループ
    private ACGroupBox tenkanGroup = new ACGroupBox();
    // てんかんラベル
    //private ACLabel seishinShinkeiTenkanTitleLabel = new ACLabel();
    // 精神・神経 - 有の場合ラベルコンテナ
    private ACLabelContainer seishinShinkeiTenkanLabelContainer;
    // てんかん有無ラジオグループ
    private ACClearableRadioButtonGroup seishinShinkeiTenkanRadioGroup = new ACClearableRadioButtonGroup();
    // 頻度チェック
    private ACLabel seishinShinkeiHindoLabel = new ACLabel();
    // 頻度ラジオグループ
    private ACClearableRadioButtonGroup seishinShinkeiHindoRadioGroup = new ACClearableRadioButtonGroup();
    // 選択肢リスト
    private VRListModelAdapter hindEmptyListModel = new VRListModelAdapter(new
            VRArrayList(Arrays.asList(new String[] {"週１回以上", "月１回以上","年１回以上"})));
    // 頻度Hesesパネル
    private ACParentHesesPanelContainer seishinShinkeiHindoHesesPanel = new ACParentHesesPanelContainer();
    

    
    public IkenshoIshiIkenshoInfoActionAndMind2() {
        try {
            // 画面構成処理
            jbInit();
            // イベントリスナ定義
            setEvent();
 
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    
    /**
     * 画面構成に関する処理を行います。
     */
    private void jbInit(){

        // 精神・神経障害グループ生成
        // 各パネルは個別に設定
        buildSeishinShinkeiPanel();
        buildSenmoniJyushinPanel();
        buildTenkanPanel();
        
        // 配置関連
        this.setLayout(new VRLayout());
        title.setText("３．行動及び精神等の状態に関する意見（続き）");
        this.add(title,VRLayout.NORTH);
        this.add(seishinShinkeiGroup,VRLayout.NORTH);
        this.add(tenkanGroup,VRLayout.NORTH);
        
        // 操作対象として追加する
        addInnerBindComponent(seishinShinkeiSenmoniComboBox);
    }
    
    /**
     * 精神・神経障害の有無グループ生成します。
     * @return
     */
    protected void buildSeishinShinkeiPanel(){
        // 精神・神経有無ラジオグループ
        seishinShinkeiRadioGroup.setModel(existEmptyListModel);
        // クリアボタンを消去する
        seishinShinkeiRadioGroup.setUseClearButton(false);
        // 文字列設定
        seishinShinkeiGroup.setText("精神・神経症状の有無");
        
        genshiGenkaku.setText("幻覚");
        mousou.setText("妄想");
        ninchiShogai.setText("その他の認知機能障害");
        kiokuShogai.setText("記憶障害");
        chuiShogai.setText("注意障害");
        suikoukinouShogai.setText("遂行機能障害");
        shakaitekikoudouShogai.setText("社会的行動障害");
        seishinSonota.setText("その他");
        
        ishikiShogai.setText("意識障害");
        kibunShogai.setText("気分障害(抑うつ気分、軽躁/躁状態)");
        suiminShogai.setText("睡眠障害");
        

        
        genshiGenkaku.setBindPath("SS_GNS_GNC");
        mousou.setBindPath("SS_MOUSOU");
        ninchiShogai.setBindPath("SS_NINCHI_SHOGAI");
        kiokuShogai.setBindPath("SS_KIOKU_SHOGAI");
        chuiShogai.setBindPath("SS_CHUI_SHOGAI");
        suikoukinouShogai.setBindPath("SS_SUIKOU_KINO_SHOGAI");
        shakaitekikoudouShogai.setBindPath("SS_SHAKAITEKI_KODO_SHOGAI");
        seishinSonota.setBindPath("SS_OTHER");
        seihinShinkeiSonotaText.setBindPath("SS_OTHER_NM");
        // 分岐用フラグ
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
        
        // 精神神経症状の有無チェックボックス郡グループ 
        getSeishinShinkeiSyojyoLabelConainer().add(seishinShinkeiShojyomeiPanelTopRightPanel,VRLayout.CLIENT);
        
        // グループに追加する。
        seishinShinkeiGroup.add(seishinShinkeiRadioGroup,VRLayout.NORTH);
        seishinShinkeiGroup.add(getSeishinShinkeiSyojyoLabelConainer(),VRLayout.NORTH);
        
    }
    
    /**
     * 専門医受診の有無パネルを生成します、
     */
    protected void buildSenmoniJyushinPanel(){
        // ラジオグループを設定する。
        seishinShinkeiSenmoniRadioGroup.setModel(existEmptyListModel);
        seishinShinkeiSenmoniRadioGroup.setUseClearButton(false);

        // バインドパスを設定する。
        seishinShinkeiSenmoniRadioGroup.setBindPath("SENMONI");
        seishinShinkeiSenmoniComboBox.setBindPath("SENMONI_NM");
        
        seishinShinkeiSenmoniComboBox.setRenderer(new ACBindListCellRenderer("SINRYOUKA"));
        seishinShinkeiSenmoniComboBox.setIMEMode(InputSubset.KANJI);
        seishinShinkeiSenmoniComboBox.setMaxLength(30);
        seishinShinkeiSenmoniComboBox.setColumns(30);
        
        // （）で囲んだコンボを作成する。
        seishinShinkeiSenmoniHesesPanel.add(seishinShinkeiSenmoniComboBox,VRLayout.FLOW);
        // ラジオグループにコンボを挿入する。
        if(seishinShinkeiSenmoniRadioGroup.getLayout() instanceof FlowLayout){
            ((FlowLayout)seishinShinkeiRadioGroup.getLayout()).setHgap(0);
        }
        seishinShinkeiSenmoniRadioGroup.add(seishinShinkeiSenmoniHesesPanel,1);
        seishinShinkeiSenmoniRadioGroup.add(getSenmoniExplanation(),2);
        
        // 専門医受診パネル設定
        seishinShinkeiShojyomeiPanelMiddlePanel.setAutoWrap(false);
        seishinShinkeiShojyomeiPanelMiddlePanel.setHgap(0);
        // ラベルコンテナに追加
        getSeishinShinkeiSenmoniLabelContainer().add(seishinShinkeiSenmoniRadioGroup,VRLayout.CLIENT);
        // パネルに追加
        seishinShinkeiShojyomeiPanelMiddlePanel.add(getSeishinShinkeiSenmoniLabelContainer(),VRLayout.CLIENT);
        // グループに追加
        seishinShinkeiGroup.add(seishinShinkeiShojyomeiPanelMiddlePanel,VRLayout.NORTH);
    }
    
    /**
     * ＜てんかん＞パネルを生成します。
     */
    protected void buildTenkanPanel(){
        // 文字列設定
        seishinShinkeiHindoLabel.setText("（有の場合）→　頻度");
        
        // ラジオに有無を設定
        seishinShinkeiTenkanRadioGroup.setModel(existEmptyListModel);
        seishinShinkeiTenkanRadioGroup.setUseClearButton(false);
        // バインドパス設定
        seishinShinkeiTenkanRadioGroup.setBindPath("TENKAN");
        seishinShinkeiHindoRadioGroup.setBindPath("TENKAN_HINDO");
        // 頻度選択肢設定
        seishinShinkeiHindoRadioGroup.setModel(hindEmptyListModel);
        // Hesesパネル
        seishinShinkeiHindoHesesPanel.setHgap(0);
        seishinShinkeiHindoHesesPanel.add(seishinShinkeiHindoRadioGroup,VRLayout.FLOW);
        
        // 画面構成
        getSeishinShinkeiTenkanLabelContainer().add(seishinShinkeiHindoLabel,VRLayout.WEST);
        getSeishinShinkeiTenkanLabelContainer().add(seishinShinkeiHindoHesesPanel,VRLayout.CLIENT);
     
        seishinShinkeiShojyomeiPanelDownPanel.setHgap(0);
        seishinShinkeiShojyomeiPanelDownPanel.add(seishinShinkeiTenkanRadioGroup,VRLayout.FLOW);
        seishinShinkeiShojyomeiPanelDownPanel.add(getSeishinShinkeiTenkanLabelContainer(),VRLayout.FLOW);
        // グループに追加する。
        tenkanGroup.setText("てんかん");
        tenkanGroup.setHgap(0);
        tenkanGroup.add(seishinShinkeiShojyomeiPanelDownPanel,VRLayout.NORTH);

    }
    
    
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        //精神・神経症状の有無ラジオグループ
        if (seishinShinkeiSenmoniRadioGroup.getSelectedIndex() == 0) {
        	seishinShinkeiRadioGroup.setSelectedIndex(2);
        }
        
        // 専門医受診の有無
        if(seishinShinkeiSenmoniRadioGroup.getSelectedIndex() != 1){
            seishinShinkeiSenmoniRadioGroup.setSelectedIndex(2);
        }

        // てんかん未選択の場合
        if(seishinShinkeiTenkanRadioGroup.getSelectedIndex() == 0){
            seishinShinkeiTenkanRadioGroup.setSelectedIndex(2);
        }

        
        // 過去データ補正
        
        // 記憶障害-短期、記憶障害-長期は未使用項目
        VRMap map =  (VRMap)getMasterSource();
        map.put("SS_KIOKU_SHOGAI_TANKI", null);
        map.put("SS_KIOKU_SHOGAI_CHOUKI", null);
        
        // 「てんかん」で「１年以上みられない」が選択されていた場合
        // てんかん無に変更
        if (ACCastUtilities.toInt(map.get("TENKAN_HINDO"), 0) == 4) {
        	seishinShinkeiTenkanRadioGroup.setSelectedIndex(2);
        }
        
        

    }
    
    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
        /*
         * 精神・神経症状の有無ラジオグループ
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
         * てんかんの有無ラジオグループ
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
         * 専門医受診の有無ラジオグループ
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
         * 精神・神経症状-その他チェック選択時
         */
        seishinSonota.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                seishinShinkeiSonotaChangeState();
            }
        });
    }
    
    
    /**
     * 精神・神経症状の有無ラジオグループ選択時の画面制御です。
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
        
        //その他個別処理
        seishinShinkeiSonotaChangeState();
        seishinShinkeiSenmoniChangeAction();
        
        ishikiShogai.setEnabled(enable);
        kibunShogai.setEnabled(enable);
        suiminShogai.setEnabled(enable);
    }
    
    /**
     * 精神・神経症状-その他コンボ状態制御
     * @param enable
     */
    protected void seishinShinkeiSonotaChangeState(){
        
        boolean enable;
        // その他有効、その他選択時にその他コンボを有効にする。
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
     * てんかんの有無ラジオグループ変更の処理です。
     * @param enable
     */
    protected void seishinShinkeiTenkanRadioGroupChangeAction(boolean enable){
        // Enable制御
        seishinShinkeiHindoRadioGroup.setEnabled(enable);
        seishinShinkeiHindoRadioGroup.getClearButton().setEnabled(enable);
        seishinShinkeiHindoHesesPanel.setEnabled(enable);
        seishinShinkeiHindoLabel.setEnabled(enable);
        getSeishinShinkeiTenkanLabelContainer().setEnabled(enable);
    }
    
    /**
     * 専門医受診コンボボックスの状態制御を行います。
     */
    protected void seishinShinkeiSenmoniChangeAction(){
        
        boolean enable;
        // 精神・神経症状有り尚且つ専門医受診のEnableがTrueの場合
        if (seishinShinkeiSenmoniRadioGroup.getSelectedIndex() == 1
                && getSeishinShinkeiSenmoniLabelContainer().isEnabled()) {
            enable = true;
        }else{
            enable = false;
        }
        seishinShinkeiSenmoniComboBox.setEnabled(enable);
    }
    
    /**
     * 専門医受診の有無ラジオグループ変更時の処理です。
     * @param enable
     */
    protected void seishinShinkeiSenmoniRadioGroupChangeAction(boolean enable){
        seishinShinkeiSenmoniHesesPanel.setEnabled(enable);
        seishinShinkeiSenmoniComboBox.setEnabled(enable);
    }
    
    /**
     * 精神・神経チェックグループを返します。
     * @return
     */
    protected ACGroupBox getSeishinShinkeiGroup() {
        return seishinShinkeiGroup;
    }

    protected ACLabelContainer getSeishinShinkeiSenmoniLabelContainer() {
        if(seishinShinkeiSenmoniLabelContainer == null){
            seishinShinkeiSenmoniLabelContainer = new ACLabelContainer();
            seishinShinkeiSenmoniLabelContainer.setText("・専門科受診の有無");
        }
        return seishinShinkeiSenmoniLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiTenkanLabelContainer() {
        if(seishinShinkeiTenkanLabelContainer == null){
            seishinShinkeiTenkanLabelContainer = new ACLabelContainer();
            //seishinShinkeiTenkanLabelContainer.setText("（有の場合）→");
        }
        return seishinShinkeiTenkanLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiSyojyoLabelConainer() {
        if(seishinShinkeiShojyoLabelConainer == null){
            seishinShinkeiShojyoLabelConainer = new ACLabelContainer();
            seishinShinkeiShojyoLabelConainer.setText("（有の場合）→");
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
        // 精神・神経症状エラーチェック
        if (seishinShinkeiRadioGroup.getSelectedIndex() == 1) {
        	
        	// チェック項目が存在するか
        	if (!isCheckSeishinShinkei()) {
                ACMessageBox.showExclamation("「精神・神経症状」で未記入があります。");
                seishinShinkeiRadioGroup.requestChildFocus();
                return false;
        	}
        	
            // その他チェック有効
            if(seishinSonota.isSelected()){
                if (IkenshoCommon.isNullText(seihinShinkeiSonotaText.getText())) {
                    ACMessageBox.showExclamation("「精神・神経症状（その他）」で未記入があります。");
                    seishinShinkeiRadioGroup.requestChildFocus();
                    return false;
                }
            }
        }

      return true;
    }
    
    
    // 精神・神経症状の項目で、チェックがつけられているものが存在するか
    private boolean isCheckSeishinShinkei() throws Exception {
    	
    	
    	if (ishikiShogai.isSelected() // 意識障害
    		|| kiokuShogai.isSelected() //記憶障害
    		|| chuiShogai.isSelected() //注意障害
    		|| suikoukinouShogai.isSelected() //遂行機能障害
    		|| shakaitekikoudouShogai.isSelected() //社会的行動障害
    		|| ninchiShogai.isSelected() //その他の認知機能障害
    		|| kibunShogai.isSelected() //気分障害
    		|| suiminShogai.isSelected() //睡眠障害
    		|| genshiGenkaku.isSelected() //幻覚
    		|| mousou.isSelected() //妄想
    		|| seishinSonota.isSelected() //その他
    			) {
    		return true;
    	}
    	
    	// 専門家受診の有無
    	if (seishinShinkeiSenmoniRadioGroup.getSelectedIndex() == 1) {
    		return true;
    	}
    	
    	return false;
    }
    
    

    /**
     * その他行数説明ラベルを返します。
     * @return
     */
    protected ACLabel getKoudouShougaiSonotaExplanation() {
        if(koudouShougaiSonotaExplanation == null){
            koudouShougaiSonotaExplanation = new ACLabel();
            koudouShougaiSonotaExplanation.setText("（30文字以内）");
            koudouShougaiSonotaExplanation
                    .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return koudouShougaiSonotaExplanation;
    }
    
    /**
     * 専門医行数説明ラベルを返します。
     * @return
     */
    protected ACLabel getSenmoniExplanation() {
        if(senmoniExplanation == null){
            senmoniExplanation = new ACLabel();
            senmoniExplanation.setText("（30文字以内）");
            senmoniExplanation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return senmoniExplanation;
    }

    /**
     * 精神・神経 - その他文字数説明ラベルを返します。
     * @return
     */
    protected ACLabel getSeishinShinkeiSonotaExplanation() {
        if(seishinShinkeiSonotaExplanation == null){
            seishinShinkeiSonotaExplanation = new ACLabel();
            seishinShinkeiSonotaExplanation.setText("30文字以内");
            seishinShinkeiSonotaExplanation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return seishinShinkeiSonotaExplanation;
    }

}
