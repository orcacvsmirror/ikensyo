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
 * IkenshoIshiIkenshoInfoActionAndMind1です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/07
 */
public class IkenshoIshiIkenshoInfoActionAndMind1 extends IkenshoTabbableChildAffairContainer {
	
    private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
    // 行動上の障害の有無グループ(統括)
    private ACGroupBox koudouSyogaiGroup = new ACGroupBox();
    //精神・神経症状の有無グループ(統括)
    //private ACGroupBox seishinShinkeiGroup = new ACGroupBox();
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
    
    // 行動障害 文字数説明ラベル
    private ACLabel koudouShougaiSonotaExplanation;
    // （）パネル
    private ACParentHesesPanelContainer koudouShogaiSonotaHesesPanel = new ACParentHesesPanelContainer(); 
    // 行動上の障害の有無ラジオグループ
    private ACClearableRadioButtonGroup koudouShogaiRadioGroup = new ACClearableRadioButtonGroup();
    
    
    // -----------------------------------------------------------------
    // 精神症状・能力障害二軸評価
    // 生活障害評価
    // 精神障害の機能評価ラベル
    private ACLabelContainer spiritShogaiLabelCotainar;
    // ○精神症状・能力障害二軸評価ラベル
    private ACLabelContainer spiritAndAbilityLabel;
    // （）精神症状・能力障害二軸評価Hesesパネル
    private ACParentHesesPanelContainer spiritAndAbilityHesesPanel;
    // 精神症状グループ
    private ACGroupBox spiritShogaiGroup;
    // 精神症状コンボコンテナ
    private IkenshoInnerComboBoxContainar spiritInnerComboBox;
    // 能力障害コンボコンテナ
    private IkenshoInnerComboBoxContainar abilityInnerContainar;
    // 精神症状・判定時期
    private ACLabelContainer spiritJudgmentTimeContainar;
    // 精神症状・日付
    private IkenshoEraDateTextField spiritEraDate;
    // 障害グループレイアウト
    private VRLayout shogaiGroupLayout;
    //精神症状コンテナレイアウト
    private VRLayout spiritContainarLayout;
    // ------------------------------------------------------------------
    // 生活障害コンテナ
    private ACLabelContainer seikatsuShogaiContainar;
    // 生活障害評価（）ラベル開始
    private ACLabel seikatsuShogaiHesesStart;
    // 生活障害評価（）ラベル終了
    private ACLabel seikatsuShogaiHesesEnd;
    // Heses用レイアウト
    private VRLayout seikatsuShogaiLayout;
    // 食事
    private IkenshoInnerComboBoxContainar seikatsuShokujiInnerContainar;
    // 生活リズム
    private IkenshoInnerComboBoxContainar seikatsuSeikatsuRizumuInnerContainar;
    // 保清
    private IkenshoInnerComboBoxContainar seikatsuHoseiInnerContainar;
    // 金銭管理
    private IkenshoInnerComboBoxContainar seikatsuKinsenInnerContainar;
    // 服薬管理
    private IkenshoInnerComboBoxContainar seikatsuFukuyakuInnerContainar;
    // 対人関係
    private IkenshoInnerComboBoxContainar seikatsuTaijinInnerContainar;
    // 社会的適応
    private IkenshoInnerComboBoxContainar seikatsuSyakaiTekiouInnerContainar;
    // 生活障害・判断時期
    private ACLabelContainer seikatsuTimeContainar;
    // 生活障害・日付
    private IkenshoEraDateTextField seikatsuEraDate;
    
    private ACLabelContainer seikatsuDateContainar;
    
    private IkenshoDocumentTabTitleLabel titleLabel;
    // 説明文表示用パネル
    private IkenshoHintContainer hintPanel;
    // 説明文パネル用グループ
    private ACGroupBox hintGroup;
    // 機能評価コンボ候補
    private static String[] MAXIMUM_VALUE5 = new String[] {" ","1", "2", "3",
            "4", "5" };
    private static Integer[] MAPS_MAXIMUM_VALUE5 = new Integer[] {
             new Integer(0), new Integer(1), new Integer(2), new Integer(3),
            new Integer(4),new Integer(5)};
    // 機能評価コンボ候補
    private static String[] MAXIMUM_VALUE6 = new String[] {" ","1", "2", "3",
            "4", "5", "6" };
    private static Integer[] MAPS_MAXIMUM_VALUE6 = new Integer[] {
            new Integer(0),new Integer(1), new Integer(2), new Integer(3),
            new Integer(4), new Integer(5),new Integer(6)};
    
    
    //ヒント表示領域
    private ACGroupBox hiddenParameters = new ACGroupBox();
    
    public IkenshoIshiIkenshoInfoActionAndMind1() {
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
        jisyou.setText("自傷");
        boukou.setText("他害");
        kaigoTeikou.setText("支援への抵抗");
        haikai.setText("徘徊");
        hinoFushimatsu.setText("危険の認識が困難");
        fuketsuKoui.setText("不潔行為");
        isyoku.setText("異食");
        seitekiMondai.setText("性的逸脱行動");
        sonota.setText("その他");
        
        // バインドパス設定
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
        sonotaText.setColumns(30);
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
        
        // 精神症状・能力障害二軸評価
        buildSpiritSyogaiGroup();
        // 生活障害評価
        buildSeikatsuSyogaiGroup();
        
        // 配置関連
        this.setLayout(new VRLayout());
        title.setText("３．行動及び精神等の状態に関する意見");
        this.add(title,VRLayout.NORTH);
        this.add(koudouSyogaiGroup,VRLayout.NORTH);
        
        // 精神障害の機能評価グループ
        this.add(getSpiritShogaiGroup(),VRLayout.NORTH);
        // 隠しパラメータグループ
        this.add(getHiddenParameters(),VRLayout.NORTH);
        // ヒント領域
        this.add(getHintGroup(),VRLayout.CLIENT);
        
        getHiddenParameters().setVisible(false);
        getHintGroup().setVisible(false);
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
         * その他チェック選択時
         */
        sonota.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                koudouSyogaiSonotaChangeState();
            }
        });
        
        
        
        // 他コンポーネントの表示切替設定
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

        // 押し下げ状態制御
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
        jisyou.setEnabled(enable);
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
                    || seitekiMondai.isSelected() || sonota.isSelected()
                    || jisyou.isSelected())) {
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
        
        // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
        // チェックする対象期間の初期値を設定
    	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(getSpiritEraDate().getEra()) && !"".equals(getSpiritEraDate().getYear())){
        	// チェック用の対象期間設定に変更する。
        	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        // 2008/01/15 [Masahiko Higuchi] add - end
    	
            // 精神症状-判定時期チェック
            switch (getSpiritEraDate().getInputStatus()) {
            case IkenshoEraDateTextField.STATE_EMPTY:
            case IkenshoEraDateTextField.STATE_VALID:
	            // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                break;
            case IkenshoEraDateTextField.STATE_FUTURE:
                ACMessageBox.showExclamation("未来の日付です。");
                getSpiritEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            default:
                ACMessageBox.show("日付に誤りがあります。");
                getSpiritEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            	getSpiritEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            }
        // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
        }
        // 2008/01/15 [Masahiko Higuchi] add - end
        
        
        // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
        // チェックする対象期間の初期値を設定
        getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        if(!"".equals(getSeikatsuEraDate().getEra()) && !"".equals(getSeikatsuEraDate().getYear())){
        	// チェック用の対象期間設定に変更する。
        	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        // 2008/01/15 [Masahiko Higuchi] add - end
        
            // 生活障害-判断時期チェック
            switch (getSeikatsuEraDate().getInputStatus()) {
            case IkenshoEraDateTextField.STATE_EMPTY:
            case IkenshoEraDateTextField.STATE_VALID:
	            // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                break;
            case IkenshoEraDateTextField.STATE_FUTURE:
                ACMessageBox.showExclamation("未来の日付です。");
                getSeikatsuEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            default:
                ACMessageBox.show("日付に誤りがあります。");
                getSeikatsuEraDate().requestChildFocus();
	            // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            	getSeikatsuEraDate().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
	            // 2008/01/15 [Masahiko Higuchi] add - end
                return false;
            }

	    // 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
        }
        // 2008/01/15 [Masahiko Higuchi] add - end
        

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
     * 
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        // ヒント領域を登録します
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
     * ヒントボタンと連動して無効になるコントロール集合を設定します。
     *
     * @param components ヒントボタンと連動して無効になるコントロール集合
     * @throws Exception 処理例外
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
     * 精神障害の評価機能グループを生成します。
     */
    protected void buildSpiritSyogaiGroup(){
        
        getSpiritShogaiGroup().setText("精神障害の機能評価");
        // レイアウト適用
        getSpiritShogaiGroup().setLayout(getShogaiGroupLayout());
        getSpiritShogaiLabelContainar().setLayout(getSpiritContainarLayout());
        
        // 背面色塗り設定
        getSpiritShogaiLabelContainar().setContentAreaFilled(true);
        
        // 文字色・背景色を設定する。
        getSpiritShogaiLabelContainar().setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        getSpiritAndAbilityHesesPanel().setForeground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        getSpiritShogaiLabelContainar().setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        
        // 文字列設定
        getSpiritShogaiLabelContainar().setText("精神症状・能力障害二軸評価");

        // （）パネルに追加
        getSpiritAndAbilityHesesPanel().add(getSpiritInnerComboBox(),VRLayout.CLIENT);
        getSpiritAndAbilityHesesPanel().add(getAbilityInnerContainar(),VRLayout.CLIENT);
        
        // 判定時期コンテナに追加
        getSpiritJudgmentTimeContainar().add(getSpiritEraDate(),VRLayout.FLOW);
        
        // 精神症状コンテナ追加
        getSpiritShogaiLabelContainar().add(getSpiritAndAbilityHesesPanel(),VRLayout.FLOW_RETURN);
        getSpiritShogaiLabelContainar().add(getSpiritJudgmentTimeContainar(),VRLayout.FLOW);
        // 精神障害グループボックスに追加
        getSpiritShogaiGroup().add(getSpiritShogaiLabelContainar(),VRLayout.NORTH);
        
    }
    
    /**
     * 生活障害評価グループを生成します。
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
        // 精神障害グループボックスに追加
        getSpiritShogaiGroup().add(getSeikatsuShogaiContainar(),VRLayout.NORTH);
        
    }



    
    /***
     * 精神・能力障害Hesesパネルコンテナを返します。
     * @return
     */
    protected ACParentHesesPanelContainer getSpiritAndAbilityHesesPanel() {
        if(spiritAndAbilityHesesPanel == null){
            spiritAndAbilityHesesPanel = new ACParentHesesPanelContainer();
        }
        return spiritAndAbilityHesesPanel;
    }
    /**
     * 精神症状・能力障害二軸評価ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getSpiritAndAbilityLabel() {
        if(spiritAndAbilityLabel == null){
            spiritAndAbilityLabel = new ACLabelContainer();
        }
        return spiritAndAbilityLabel;
    }
    /**
     * 精神障害ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getSpiritShogaiLabelContainar() {
        if(spiritShogaiLabelCotainar == null){
            spiritShogaiLabelCotainar = new ACLabelContainer();
        }
        return spiritShogaiLabelCotainar;
    }
    /**
     * 精神障害グループを返します。
     * @return
     */
    protected ACGroupBox getSpiritShogaiGroup() {
        if(spiritShogaiGroup == null){
            spiritShogaiGroup = new ACGroupBox();
        }
        return spiritShogaiGroup;
    }
    /**
     * 能力障害コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getAbilityInnerContainar() {
        if(abilityInnerContainar == null){
            abilityInnerContainar = new IkenshoInnerComboBoxContainar();
            abilityInnerContainar.setText("能力障害評価");
            abilityInnerContainar.setInnerComboModel("SK_NIJIKU_NORYOKU",
                    "RENDER_SK_NIJIKU_NORYOKU",MAPS_MAXIMUM_VALUE5 ,
                    MAXIMUM_VALUE5);
            abilityInnerContainar.setComboBindPath("SK_NIJIKU_NORYOKU");
            abilityInnerContainar.setComboRenderBindPath("RENDER_SK_NIJIKU_NORYOKU");
            // ヒント表示領域を設定する。
            abilityInnerContainar.getHintButton().setHintArea(getHintPanel());
            abilityInnerContainar.getHintButton().setHintContainer(getHintGroup());
        }
        return abilityInnerContainar;
    }
    /**
     * 精神症状コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSpiritInnerComboBox() {
        if(spiritInnerComboBox == null){
            spiritInnerComboBox = new IkenshoInnerComboBoxContainar();
            spiritInnerComboBox.setText("精神症状評価");
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
     * 判定時期コンテナを返します。
     * @return
     */
    protected IkenshoEraDateTextField getSpiritEraDate() {
        if(spiritEraDate == null){
            spiritEraDate = new IkenshoEraDateTextField();
            // 日付コンポーネントの設定
            spiritEraDate.setAgeVisible(false);
            spiritEraDate.setDayVisible(false);
            spiritEraDate.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            spiritEraDate.setBindPath("SK_NIJIKU_DT");
            // 表示領域を平成と空白のみに設定
            spiritEraDate.setEraRange(3);
            
    		// 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            spiritEraDate.setEraPreclusion("平成");
            spiritEraDate.setDefaultCheckRange(IkenshoEraDateTextField.RNG_YEAR);
            spiritEraDate.setDateTypeConv(false);
    		// 2008/01/15 [Masahiko Higuchi] add - end
        }
        return spiritEraDate;
    }


    /**
     * 判定時期ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getSpiritJudgmentTimeContainar() {
        if(spiritJudgmentTimeContainar == null){
            spiritJudgmentTimeContainar = new ACLabelContainer();
            spiritJudgmentTimeContainar.setText("判定時期");
        }
        return spiritJudgmentTimeContainar;
    }
    /**
     * 精神障害グループレイアウトを返します。
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
     * 精神コンテナレイアウトを返します。
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
     * 生活障害評価 - 判断時期コンポーネントを返します。
     * @return
     */
    protected IkenshoEraDateTextField getSeikatsuEraDate() {
        if(seikatsuEraDate == null){
            seikatsuEraDate = new IkenshoEraDateTextField();
            // 設定関係
            seikatsuEraDate.setDayVisible(false);
            seikatsuEraDate.setAgeVisible(false);
            seikatsuEraDate.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            seikatsuEraDate.setBindPath("SK_SEIKATSU_DT");
            // 表示領域を平成と空白に限定
            seikatsuEraDate.setEraRange(3);
            
    		// 2008/01/15 [Masahiko Higuchi] add - begin 平成デフォルト表示対応
            seikatsuEraDate.setEraPreclusion("平成");
            seikatsuEraDate.setDefaultCheckRange(IkenshoEraDateTextField.RNG_YEAR);
            seikatsuEraDate.setDateTypeConv(false);
    		// 2008/01/15 [Masahiko Higuchi] add - end
        }
        return seikatsuEraDate;
    }
    /**
     * 服薬管理コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuFukuyakuInnerContainar() {
        if(seikatsuFukuyakuInnerContainar == null){
            seikatsuFukuyakuInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuFukuyakuInnerContainar.setText("服薬管理");
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
     * 保清コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuHoseiInnerContainar() {
        if(seikatsuHoseiInnerContainar == null){
            seikatsuHoseiInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuHoseiInnerContainar.setText("保清");
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
     * 金銭管理コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuKinsenInnerContainar() {
        if(seikatsuKinsenInnerContainar == null){
            seikatsuKinsenInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuKinsenInnerContainar.setText("金銭管理");
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
     * 生活リズムコンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuSeikatsuRizumuInnerContainar() {
        if(seikatsuSeikatsuRizumuInnerContainar == null){
            seikatsuSeikatsuRizumuInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuSeikatsuRizumuInnerContainar.setText("生活リズム");
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
     * 生活障害評価ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getSeikatsuShogaiContainar() {
        if(seikatsuShogaiContainar == null){
            seikatsuShogaiContainar = new ACLabelContainer();
            seikatsuShogaiContainar.setText("生活障害評価");
            // レイアウト適用
            seikatsuShogaiContainar.setLayout(getSeikatsuShogaiLayout());
            seikatsuShogaiContainar.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
            seikatsuShogaiContainar.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            seikatsuShogaiContainar.setContentAreaFilled(true);
        }
        return seikatsuShogaiContainar;
    }
    /**
     * 生活グループ「）」ラベルを返します。
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
     * 生活グループ「（」ラベルを返します。
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
     * 食事コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuShokujiInnerContainar() {
        if(seikatsuShokujiInnerContainar == null){
            seikatsuShokujiInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuShokujiInnerContainar.setText("食事");
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
     * 対人関係コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuTaijinInnerContainar() {
        if(seikatsuTaijinInnerContainar == null){
            seikatsuTaijinInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuTaijinInnerContainar.setText("対人関係");
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
     * 生活障害・判断時期ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getSeikatsuTimeContainar() {
        if(seikatsuTimeContainar == null){
            seikatsuTimeContainar = new ACLabelContainer();
        }
        return seikatsuTimeContainar;
    }
    /**
     * 生活障害コンテナ用のレイアウトを返します。
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
     * 社会的適応を妨げる行動コンボコンテナを返します。
     * @return
     */
    protected IkenshoInnerComboBoxContainar getSeikatsuSyakaiTekiouInnerContainar() {
        if(seikatsuSyakaiTekiouInnerContainar == null){
            seikatsuSyakaiTekiouInnerContainar = new IkenshoInnerComboBoxContainar();
            seikatsuSyakaiTekiouInnerContainar.setText("社会的適応を妨げる行動");
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
     * タブタイトルラベルを返します。
     * @return
     */
    protected IkenshoDocumentTabTitleLabel getTitleLabel() {
        if(titleLabel == null){
            titleLabel = new IkenshoDocumentTabTitleLabel();
            titleLabel.setText("５．その他特記すべき事項（続き）");
        }
        return titleLabel;
    }
    
// [ID:0000785][Satoshi Tokusari] 2014/10 del-Start 医師意見書の保険者表示障害対応
//    protected int getAllowedInsurerType(){
//        //2:医師意見書のみの保険者は許可する
//        return 2;
//    }
// [ID:0000785][Satoshi Tokusari] 2014/10 del-End
    
    /**
     * ヒントグループを返します。
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
     * ヒントコンテナを返します。
     * @return
     */
    protected IkenshoHintContainer getHintPanel() {
        if(hintPanel == null){
            hintPanel = new IkenshoHintContainer();
        }
        return hintPanel;
    }
    /**
     * overrideして説明取得のための法改正対応区分を返します。
     * @return 説明取得のための法改正対応区分
     */
    protected int getFomratKubun(){
      return 2;
    }
    
    // [ID:0000555][Tozo TANAKA] 2009/09/14 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
    protected String getInsuredNoText(){
        return "受給者番号";
    }
    // [ID:0000555][Tozo TANAKA] 2009/09/14 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応
    
    protected ACLabelContainer getSeikatsuDateContainar() {
        if(seikatsuDateContainar == null){
            seikatsuDateContainar = new ACLabelContainer();
            seikatsuDateContainar.setText("判断時期"); 
        }
        return seikatsuDateContainar;
    }
    
    /**
     * ヒント表示領域
     * @return
     */
    protected ACGroupBox getHiddenParameters() {
        if(hiddenParameters == null){
            hiddenParameters = new ACGroupBox();
        }
        return hiddenParameters;
    }
    

}
