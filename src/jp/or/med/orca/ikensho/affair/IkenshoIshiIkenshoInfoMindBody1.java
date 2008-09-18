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
 * IkenshoIshiIkenshoMindBody1です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 */
public class IkenshoIshiIkenshoInfoMindBody1 extends IkenshoTabbableChildAffairContainer {
    
    private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
    // 行動上の障害の有無グループ(統括)
    private ACGroupBox koudouSyogaiGroup = new ACGroupBox();
    //精神・神経症状の有無グループ(統括)
    private ACGroupBox seishinShinkeiGroup = new ACGroupBox();
    // 選択肢リスト
    private VRListModelAdapter existEmptyListModel = new VRListModelAdapter(new
            VRArrayList(Arrays.asList(new String[] {"有", "無"})));
    //行動上の障害の有無上部パネル
    private ACPanel koudouShogaiPanelTop = new ACPanel();
    //行動上の障害の有無下部パネル
    private ACPanel koudouShogaiPanelDown = new ACPanel();
    // 障害の有無下部パネル左
    private ACPanel koudouShogaiPanelDownLeftPane = new ACPanel();
    // 障害の有無下部パネル右
    private ACPanel koudouShogaiPanelDownRightPane = new ACPanel();
    // その他統合用パネル
    private ACPanel koudouShogaiSonotaPanel = new ACPanel(); 
    // 行動上の傷害の有無ラベルコンテナ
    private ACLabelContainer koudouShogaiLabelContainer = new ACLabelContainer();
    // 行動上の障害レイアウト
    private VRLayout koudouShogaiLayout;
    // チェックボックス郡 -----------------------------------------
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
    
    // 専門医受診の有無　文字数説明ラベル
    private ACLabel senmoniExplanation;
    // 行動障害 文字数説明ラベル
    private ACLabel koudouShougaiSonotaExplanation;
    // 精神 症状名コンボ文字数説明ラベル
    private ACLabel seishinShoujyomeiExplanation;
    
    // （）パネル
    private ACParentHesesPanelContainer koudouShogaiSonotaHesesPanel = new ACParentHesesPanelContainer(); 
    // 行動上の障害の有無ラジオグループ
    private ACClearableRadioButtonGroup koudouShogaiRadioGroup = new ACClearableRadioButtonGroup();
    // 精神・神経障害の有無ラジオグループ
    private ACClearableRadioButtonGroup seishinShinkeiRadioGroup = new ACClearableRadioButtonGroup();
    // 症状名ラベル
    private ACLabel seishinShinkeiShojyomeiLabel = new ACLabel();
    // 症状名パネル
    private ACParentHesesPanelContainer seishinShinkeiShogaiHesesPanel = new ACParentHesesPanelContainer();
    // 精神・神経症状 - 有の場合　ラベルコンテナ
    private ACLabelContainer seishinShinkeiShojyoLabelConainer;
//  2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
    // 症状名テキスト
    private IkenshoOptionComboBox seishinShinkeiShojyomeiText = new IkenshoOptionComboBox();
//  2007/10/18 [Masahiko Higuchi] Replace - end
    // 精神・神経症状パネル上部 - 右
    private ACPanel seishinShinkeiShojyomeiPanelTopRightPanel = new ACPanel();
    // 精神・神経症状パネル下部
    private ACPanel seishinShinkeiShojyomeiPanelDownPanel = new ACPanel();
    // 精神・神経症状パネル中部
    private ACPanel seishinShinkeiShojyomeiPanelMiddlePanel = new ACPanel();
    // 精神・神経症状チェックボックス郡------------------------------------------------------
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
    // 短期チェックボックス
    private ACIntegerCheckBox tankiCheck = new ACIntegerCheckBox();
    // 長期チェックボックス
    private ACIntegerCheckBox choukiCheck = new ACIntegerCheckBox();
    // 短期・長期Hesesパネル
    private ACParentHesesPanelContainer kiokuShogaiHesesPanel = new ACParentHesesPanelContainer();
    // 短期・長期パネル
    private ACPanel kiokuShogaiPanel = new ACPanel();
    // 短期・長期パネル用VRLayout
    private VRLayout kiokuShogaiPanelLayout = new VRLayout();
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
    // てんかんラベル
    private ACLabel seishinShinkeiTenkanTitleLabel = new ACLabel();
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
            VRArrayList(Arrays.asList(new String[] {"週１回以上", "月１回以上","年１回以上","１年以上みられない"})));
    // 頻度Hesesパネル
    private ACParentHesesPanelContainer seishinShinkeiHindoHesesPanel = new ACParentHesesPanelContainer();
    
    // ---------------------------------------------------------------------------------------
    // 身体グループ
    private ACGroupBox shintaiGroup;
    // 利き腕Hesesパネル
    private ACParentHesesPanelContainer shintaiKikiudeHesesPanel;
    // 利き腕ラベルコンテナ
    private ACLabelContainer shintaiKikiudeLabelContainer;
    // 利き腕ラジオグループ
    private ACClearableRadioButtonGroup shintaiKikiudeRadioGroup;
    // 利き腕ラジオリスト
    private VRListModelAdapter kikiudeRadioListModel;
    // 身長ラベルコンテナ
    private ACLabelContainer shintaiShinchouLabelContainer;
    // 身長テキスト
    private ACTextField shintaiShinchouText;
    // 身長ラベル
    private ACLabel shintaiShinchouLabel;
    // 体重ラベルコンテナ
    private ACLabelContainer shintaiTaijyuLabelContainer;
    // 体重テキスト
    private ACTextField shintaiTaijyuText;
    // 体重ラベル
    private ACLabel shintaiTaijyuLabel;
    // 体重単位ラベル
    private ACLabel shintaiTaijyuUnitLabel;
    // 体重Hesesパネル
    private ACParentHesesPanelContainer shintaiTaijyuHesesPanel;
    // 体重ラジオグループ
    private ACClearableRadioButtonGroup shintaiTaijyuRadioGroup;
    // 体重ラジオリスト
    private VRListModelAdapter taijyuRadioListModel;
    // ---------------------------------------------------------------------------------------
    
    public IkenshoIshiIkenshoInfoMindBody1() {
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
     * 行動障害パネルを生成します。
     */
    protected void buildKoudouShougaiPanel(){
        // 自動折り返し設定
        koudouShogaiPanelTop.setAutoWrap(false);
        koudouShogaiPanelDown.setAutoWrap(true);
        koudouShogaiPanelDownLeftPane.setAutoWrap(true);
        koudouShogaiPanelDownRightPane.setAutoWrap(true);
        
        // 文字列設定
        koudouSyogaiGroup.setText("行動上の障害の有無（該当する項目全てチェック）");
        tyuyaGyakuten.setText("昼夜逆転");
        bougen.setText("暴言");
        boukou.setText("暴行");
        kaigoTeikou.setText("介護への抵抗");
        haikai.setText("徘徊");
        hinoFushimatsu.setText("火の不始末");
        fuketsuKoui.setText("不潔行為");
        isyoku.setText("異食");
        seitekiMondai.setText("性的行動障害");
        sonota.setText("その他");
        
        // バインドパス設定
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
        // 隠しフラグ
        koudouShogaiRadioGroup.setBindPath("MONDAI_FLAG");
        
        // クリアボタン消去
        koudouShogaiRadioGroup.setUseClearButton(false);
        
        koudouShogaiRadioGroup.setModel(existEmptyListModel);
        // 行動上の障害の有無上部パネル
        koudouShogaiPanelTop.add(koudouShogaiRadioGroup,VRLayout.FLOW);
        
        //その他テキストの設定
        sonotaText.setMaxLength(30);
        sonotaText.setEditable(true);
        sonotaText.setColumns(15);
        sonotaText.setIMEMode(InputSubset.KANJI);
        // その他テキストを（）で囲む 
        koudouShogaiSonotaHesesPanel.add(sonotaText,VRLayout.FLOW);

        // その他テキストを追加する。
        koudouShogaiSonotaPanel.setHgap(0);
        koudouShogaiSonotaPanel.setAutoWrap(false);
        koudouShogaiSonotaPanel.add(sonota,VRLayout.FLOW);
        koudouShogaiSonotaPanel.add(koudouShogaiSonotaHesesPanel,VRLayout.FLOW);
        koudouShogaiSonotaPanel.add(getKoudouShougaiSonotaExplanation(),VRLayout.FLOW);
        
        // チェックボックス郡を追加-------------------------------------------------
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

        // 傷害の有無下部チェック群パネル生成
        koudouShogaiLabelContainer.setLayout(getKoudouShogaiLayout());
        koudouShogaiLabelContainer.setText("（有の場合）→");
        koudouShogaiLabelContainer.add(koudouShogaiPanelDownRightPane,VRLayout.CLIENT);
        
        koudouShogaiPanelDown.add(koudouShogaiLabelContainer,VRLayout.CLIENT);
        
        // 行動上の傷害の有無グループに追加
        koudouSyogaiGroup.add(koudouShogaiPanelTop,VRLayout.NORTH);
        koudouSyogaiGroup.add(koudouShogaiPanelDown,VRLayout.NORTH);
    }
    
    /**
     * 画面構成に関する処理を行います。
     */
    private void jbInit(){

        // 精神・神経障害グループ生成
        // 各パネルは個別に設定
        buildKoudouShougaiPanel();
        buildSeishinShinkeiPanel();
        buildSenmoniJyushinPanel();
        buildTenkanPanel();
        buildShintaiGroup();
        
        // 配置関連
        this.setLayout(new VRLayout());
        title.setText("３．心身の状態に関する意見");
        this.add(title,VRLayout.NORTH);
        this.add(koudouSyogaiGroup,VRLayout.NORTH);
        this.add(seishinShinkeiGroup,VRLayout.NORTH);
        this.add(getShintaiGroup(),VRLayout.NORTH);
        
        // 操作対象として追加する
        addInnerBindComponent(seishinShinkeiShojyomeiText);
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
        seishinShinkeiShojyomeiLabel.setText("症状名");
        senmou.setText("せん妄");
        keiminKeikou.setText("傾眠傾向");
        genshiGenkaku.setText("幻視・幻聴");
        mousou.setText("妄想");
        shikken.setText("失見当識");
        shitsunin.setText("失認");
        shitsukou.setText("失行");
        ninchiShogai.setText("認知障害");
        kiokuShogai.setText("記憶障害");
        tankiCheck.setText("短期");
        choukiCheck.setText("長期");
        chuiShogai.setText("注意障害");
        suikoukinouShogai.setText("遂行機能障害");
        shakaitekikoudouShogai.setText("社会的行動障害");
        seishinSonota.setText("その他");
        
        // バインドパス設定
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
        // 分岐用フラグ
        seishinShinkeiRadioGroup.setBindPath("SEISIN");
        
        // 詳細設定
        seishinShinkeiShojyomeiText.setIMEMode(InputSubset.KANJI);
        seishinShinkeiShojyomeiText.setMaxLength(30);
        seishinShinkeiShojyomeiText.setColumns(30);
        
        seihinShinkeiSonotaText.setIMEMode(InputSubset.KANJI);
        seihinShinkeiSonotaText.setMaxLength(30);
        seihinShinkeiSonotaText.setColumns(30);
        
        // 記憶障害
        kiokuShogaiHesesPanel.setAutoWrap(false);
        kiokuShogaiHesesPanel.setHgap(0);
        kiokuShogaiHesesPanel.add(tankiCheck,VRLayout.FLOW);
        kiokuShogaiHesesPanel.add(choukiCheck,VRLayout.FLOW);
        // 記憶障害パネルレイアウト設定
        kiokuShogaiPanelLayout.setAutoWrap(false);
        kiokuShogaiPanelLayout.setHgap(0);
        // 記憶障害パネル
        kiokuShogaiPanel.setLayout(kiokuShogaiPanelLayout);
        kiokuShogaiPanel.add(kiokuShogai,VRLayout.FLOW);
        kiokuShogaiPanel.add(kiokuShogaiHesesPanel,VRLayout.FLOW);
        
        // 症状名コンボボックスに関する設定
        seishinShinkeiShogaiHesesPanel.setAutoWrap(false);
        seishinShinkeiShogaiHesesPanel.setHgap(0);
        seishinShinkeiShogaiHesesPanel.add(seishinShinkeiShojyomeiLabel,VRLayout.FLOW);
        seishinShinkeiShogaiHesesPanel.add(seishinShinkeiShojyomeiText,VRLayout.FLOW);
        // ラジオグループの間にパネルを挿入する。

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
        
        // チェックボックス郡追加
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
        seishinShinkeiTenkanTitleLabel.setText("＜てんかん＞");
        seishinShinkeiHindoLabel.setText("頻度");
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
     
        seishinShinkeiShojyomeiPanelDownPanel.add(seishinShinkeiTenkanTitleLabel,VRLayout.FLOW_INSETLINE_RETURN);
        seishinShinkeiShojyomeiPanelDownPanel.add(seishinShinkeiTenkanRadioGroup,VRLayout.FLOW_INSETLINE_RETURN);
        seishinShinkeiShojyomeiPanelDownPanel.add(getSeishinShinkeiTenkanLabelContainer(),VRLayout.FLOW);
        // グループに追加する。
        seishinShinkeiGroup.add(seishinShinkeiShojyomeiPanelDownPanel,VRLayout.NORTH);

    }
    
    /**
     * 身体グループを生成します。
     */
    protected void buildShintaiGroup(){
        // ラジオグループを（）で囲む
        getShintaiKikiudeHesesPanel().add(getShintaiKikiudeRadioGroup(),VRLayout.FLOW);
        // 体重ラベルコンテナ作成
        getShintaiKikiudeLabelContainer().add(getShintaiKikiudeHesesPanel(),VRLayout.FLOW);
        
        // 身体ラベルコンテナ作成
        getShintaiShinchouLabelContainer().add(getShintaiShinchouText(),VRLayout.FLOW);
        getShintaiShinchouLabelContainer().add(getShintaiShinchouLabel(),VRLayout.FLOW);
        
        // 体重ラジオグループを（）で囲む
        getShintaiTaijyuHesesPanel().add(getShintaiTaijyuLabel(),VRLayout.FLOW);
        getShintaiTaijyuHesesPanel().add(getShintaiTaijyuRadioGroup(),VRLayout.FLOW);
        // 体重ラベルコンテナを生成
        getShintaiTaijyuLabelContainer().add(getShintaiTaijyuText(),VRLayout.FLOW);
        getShintaiTaijyuLabelContainer().add(getShintaiTaijyuUnitLabel(),VRLayout.FLOW);
        getShintaiTaijyuLabelContainer().add(getShintaiTaijyuHesesPanel(),VRLayout.FLOW);
        
        // グループ内に追加
        getShintaiGroup().add(getShintaiKikiudeLabelContainer(),VRLayout.FLOW_RETURN);
        getShintaiGroup().add(getShintaiShinchouLabelContainer(),VRLayout.FLOW);
        getShintaiGroup().add(getShintaiTaijyuLabelContainer(),VRLayout.FLOW);
    }
    
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        // 行動障害の有無ラジオグループ
        if (tyuyaGyakuten.isSelected() || bougen.isSelected()
                || boukou.isSelected() || kaigoTeikou.isSelected()
                || haikai.isSelected() || hinoFushimatsu.isSelected()
                || fuketsuKoui.isSelected() || isyoku.isSelected()
                || seitekiMondai.isSelected() || sonota.isSelected()) {
            koudouShogaiRadioGroup.setSelectedIndex(1);
        }else{
            koudouShogaiRadioGroup.setSelectedIndex(2);
        }
        // 精神・神経症状の有無
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
        // 専門医受診の有無
        if(seishinShinkeiSenmoniRadioGroup.getSelectedIndex() != 1){
            seishinShinkeiSenmoniRadioGroup.setSelectedIndex(2);
        }

        // てんかん未選択の場合
        if(seishinShinkeiTenkanRadioGroup.getSelectedIndex() == 0){
            seishinShinkeiTenkanRadioGroup.setSelectedIndex(2);
        }

    }
    
    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
        /*
         * 行動障害の有無ラジオグループ
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
         * 専門医受信の有無ラジオグループ
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
         * その他チェック選択時
         */
        sonota.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                koudouSyogaiSonotaChangeState();
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
        /*
         * 記憶障害チェック選択時
         */
        kiokuShogai.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                kiokuShogaiChangeState();
            }            
        });
    }
    
    /**
     * 行動障害ラジオグループ選択時の画面制御
     */
    protected void koudouSyogaiRadioGroupChangeAction(boolean enable){
        // Enable制御
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
        // その他テキストのみ制御
        koudouSyogaiSonotaChangeState();
    }
    
    /**
     * 行動障害-その他コンボ状態制御
     * @param enable
     */
    protected void koudouSyogaiSonotaChangeState(){
        // その他パネルが有効、その他チェック有効の場合は、その他パネルを有効にする。
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
     * 精神・神経症状の有無ラジオグループ選択時の画面制御です。
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
        
        //その他個別処理
        seishinShinkeiSonotaChangeState();
        kiokuShogaiChangeState();
        seishinShinkeiSenmoniChangeAction();
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
     * 記憶障害 - ラジオグループ状態制御
     */
    protected void kiokuShogaiChangeState(){
        boolean enable;
        // 記憶障害有効 + チェック有りの場合
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

    /**
     * 行動上の傷害の有無グループを返します。
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
            seishinShinkeiSenmoniLabelContainer.setText("・専門医受診の有無");
        }
        return seishinShinkeiSenmoniLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiTenkanLabelContainer() {
        if(seishinShinkeiTenkanLabelContainer == null){
            seishinShinkeiTenkanLabelContainer = new ACLabelContainer();
            seishinShinkeiTenkanLabelContainer.setText("（有の場合）→");
        }
        return seishinShinkeiTenkanLabelContainer;
    }

    protected ACLabelContainer getSeishinShinkeiSyojyoLabelConainer() {
        if(seishinShinkeiShojyoLabelConainer == null){
            seishinShinkeiShojyoLabelConainer = new ACLabelContainer();
            seishinShinkeiShojyoLabelConainer.setText("（有の場合）→");
            seishinShinkeiShojyoLabelConainer.setLayout(new VRLayout());
        }
        return seishinShinkeiShojyoLabelConainer;
    }
    /**
     * 利き腕コンボ候補を取得します。
     * @return
     */
    protected VRListModelAdapter getKikiudeRadioListModel() {
        if(kikiudeRadioListModel == null){
            kikiudeRadioListModel = new VRListModelAdapter();
            kikiudeRadioListModel = new VRListModelAdapter(
                    new VRArrayList(Arrays.asList(new String[] { "右", "左" })));
        }
        return kikiudeRadioListModel;
    }
    /**
     * 身体の状態グループボックスを返します。
     * @return
     */
    protected ACGroupBox getShintaiGroup() {
        if(shintaiGroup == null){
            shintaiGroup = new ACGroupBox();
            VRLayout shintaiLayout = new VRLayout();
            shintaiLayout.setAutoWrap(false);
            shintaiGroup.setText("身体の状態");
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
     * 利き腕ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getShintaiKikiudeLabelContainer() {
        if(shintaiKikiudeLabelContainer == null){
            shintaiKikiudeLabelContainer = new ACLabelContainer();
            shintaiKikiudeLabelContainer.setText("利き腕");
        }
        return shintaiKikiudeLabelContainer;
    }
    /**
     * 利き腕ラジオグループを返します。
     * @return
     */
    protected ACClearableRadioButtonGroup getShintaiKikiudeRadioGroup() {
        if(shintaiKikiudeRadioGroup == null){
            shintaiKikiudeRadioGroup = new ACClearableRadioButtonGroup();
            shintaiKikiudeRadioGroup.setBindPath("KIKIUDE");
            // ラジオ選択肢を追加
            getShintaiKikiudeRadioGroup().setModel(getKikiudeRadioListModel());
        }
        return shintaiKikiudeRadioGroup;
    }
    /**
     * 身長単位を返します。
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
     * 身長ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getShintaiShinchouLabelContainer() {
        if(shintaiShinchouLabelContainer == null){
            shintaiShinchouLabelContainer = new ACLabelContainer();
            shintaiShinchouLabelContainer.setText("身長=");
            shintaiShinchouLabelContainer.setHgap(0);
        }
        return shintaiShinchouLabelContainer;
    }
    /**
     * 身長テキストを返します。
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
     * 体重Hesesパネルを返します。
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
     * 体重の変化ラベルを返します。
     * @return
     */
    protected ACLabel getShintaiTaijyuLabel() {
        if(shintaiTaijyuLabel == null){
            shintaiTaijyuLabel = new ACLabel();
            shintaiTaijyuLabel.setText("過去6ヶ月の体重の変化");
        }
        return shintaiTaijyuLabel;
    }
    /**
     * 体重ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getShintaiTaijyuLabelContainer() {
        if(shintaiTaijyuLabelContainer == null){
            shintaiTaijyuLabelContainer = new ACLabelContainer();
            shintaiTaijyuLabelContainer.setText("体重=");
            shintaiTaijyuLabelContainer.setAutoWrap(false);
            shintaiTaijyuLabelContainer.setHgap(0);
        }
        return shintaiTaijyuLabelContainer;
    }
    /**
     * 過去6ヶ月の体重変化ラジオグループを返します。
     * @return
     */
    protected ACClearableRadioButtonGroup getShintaiTaijyuRadioGroup() {
        if(shintaiTaijyuRadioGroup == null){
            shintaiTaijyuRadioGroup = new ACClearableRadioButtonGroup();
            // 選択肢設定
            shintaiTaijyuRadioGroup.setModel(getTaijyuRadioListModel());
            shintaiTaijyuRadioGroup.setBindPath("WEIGHT_CHANGE");
        }
        return shintaiTaijyuRadioGroup;
    }
    /**
     * 体重テキストを返します。
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
     * 過去6ヶ月の体重変化候補返します。
     * @return
     */
    protected VRListModelAdapter getTaijyuRadioListModel() {
        if(taijyuRadioListModel == null){
            taijyuRadioListModel = new VRListModelAdapter();
            taijyuRadioListModel = new VRListModelAdapter(
                    new VRArrayList(Arrays.asList(new String[] { "増加", "維持","減少" })));
        }
        return taijyuRadioListModel;
    }
    /**
     * 体重-単位ラベルを返します。
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
        // 2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応
        // ACComboBox⇒IkenshoOptionComboBox
        seishinShinkeiShojyomeiText.setOptionComboBoxParameters("精神・神経症状",
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
        // 行動上の傷害の有無チェック
        if (koudouShogaiRadioGroup.getSelectedIndex() == 1) {
            if (!(tyuyaGyakuten.isSelected() || bougen.isSelected()
                    || boukou.isSelected() || kaigoTeikou.isSelected()
                    || haikai.isSelected() || hinoFushimatsu.isSelected()
                    || fuketsuKoui.isSelected() || isyoku.isSelected()
                    || seitekiMondai.isSelected() || sonota.isSelected())) {
                ACMessageBox.showExclamation("「行動上の障害」で未記入があります。");
                koudouShogaiRadioGroup.requestChildFocus();
                return false;            
            }
        
            // その他チェックが有効である場合
            if(sonota.isSelected()){
                if (IkenshoCommon.isNullText(sonotaText.getText())) {
                    ACMessageBox.showExclamation("「行動上の障害（その他）」で未記入があります。");
                    sonota.requestFocus();
                    return false;
                }
            }
        }
        // 精神・神経症状エラーチェック
        if (seishinShinkeiRadioGroup.getSelectedIndex() == 1) {
            if (!(senmou.isSelected() || keiminKeikou.isSelected()
                    || genshiGenkaku.isSelected() || mousou.isSelected()
                    || shikken.isSelected() || shitsunin.isSelected()
                    || shitsukou.isSelected() || ninchiShogai.isSelected()
                    || kiokuShogai.isSelected() || chuiShogai.isSelected()
                    || suikoukinouShogai.isSelected()
                    || shakaitekikoudouShogai.isSelected() || seishinSonota
                    .isSelected())) {
                // チェックボックスが全て空白で且つテキストも未記入である場合
                if(ACTextUtilities.isNullText(seishinShinkeiShojyomeiText.getText())){
                    ACMessageBox.showExclamation("「精神・神経症状」で未記入があります。");
                    seishinShinkeiRadioGroup.requestChildFocus();
                    return false;
                }
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
     * 症状名行数説明ラベルを返します。
     * @return
     */
    protected ACLabel getSeishinShoujyomeiExplanation() {
        if(seishinShoujyomeiExplanation == null){
            seishinShoujyomeiExplanation = new ACLabel();
            seishinShoujyomeiExplanation.setText("（30文字以内）");
            seishinShoujyomeiExplanation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        }
        return seishinShoujyomeiExplanation;
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
