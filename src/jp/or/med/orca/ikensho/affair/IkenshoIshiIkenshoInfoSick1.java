package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoSickH18です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/06
 */
public class IkenshoIshiIkenshoInfoSick1 extends IkenshoIkenshoInfoSickH18 {
    // 入院歴ラベル
    private ACLabel nyuinrekiLabel;
    // 入院歴1 - 開始
    private IkenshoEraDateTextField nyuinrekiStartDate1;
    // 入院歴2 - 開始
    private IkenshoEraDateTextField nyuinrekiStartDate2;
    // 入院歴1 - 終了
    private IkenshoEraDateTextField nyuinrekiEndDate1;
    // 入院歴2 - 終了
    private IkenshoEraDateTextField nyuinrekiEndDate2;    
    // 入院歴1Hesesパネル
    private ACParentHesesPanelContainer nyuinrekiHesesPanel1; 
    // 入院歴2Hesesパネル
    private ACParentHesesPanelContainer nyuinrekiHesesPanel2;
    // 傷病名1ラベルコンテナ
    private ACBackLabelContainer nyuinrekiLabelContainer1;
    // 傷病名2ラベルコンテナ
    private ACBackLabelContainer nyuinrekiLabelContainer2;
//  2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
    // 傷病名1コンボボックス
    private IkenshoOptionComboBox nyuinrekiSickCombo1;
    // 傷病名2コンボボックス
    private IkenshoOptionComboBox nyuinrekiSickCombo2;
//  2007/10/18 [Masahiko Higuchi] Replace - end
    // 傷病名コンボ用ラベルコンテナ1
    private ACLabelContainer nyuinrekiSickCombo1LabelContainer;
    // 傷病名コンボ用ラベルコンテナ2
    private ACLabelContainer nyuinrekiSickCombo2LabelContainer;
    // 入院歴日付用ラベルコンテナ1
    private ACLabelContainer  nyuinrekiDateLabelContainer1;
    // 入院歴日付用ラベルコンテナ2
    private ACLabelContainer  nyuinrekiDateLabelContainer2;
    // 入院歴日付1「から」
    private ACLabel nyuinrekiDateLabel1;
    // 入院歴日付2「から」
    private ACLabel nyuinrekiDateLabel2;
    // 入院歴日付レイアウト１
    private VRLayout nyuinrekiDateLayout1;
    // 入院歴日付レイアウト２
    private VRLayout nyuinrekiDateLayout2;
    // 出生時チェック１
    private ACIntegerCheckBox shusseiCheck1;
    // 出生時チェック２
    private ACIntegerCheckBox shusseiCheck2;
    // 出生時チェック３
    private ACIntegerCheckBox shusseiCheck3;
    
    public IkenshoIshiIkenshoInfoSick1() {
        try{
            // 画面コンポーネント生成
            buildNyuinrekiGroup();
            // 画面構築
            jbInit();
            // イベントリスナを定義します。
            setEvent();
            // 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 画面項目を変更します。
     */
    private void jbInit(){
        getProgressGroup().setText("障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容");
        getSickNameGroup().setText("診断名（障害の直接の原因となっている傷病名については１．に記入）及び発症年月日");
        // 特定疾病ボタンを消す
        getSickSpecial1().setVisible(false);
        getSickSpecial2().setVisible(false);
        getSickSpecial3().setVisible(false);
    }

    
    /**
     * 入院歴コンポーネントを生成します。
     */
    protected void buildNyuinrekiGroup(){
        // ラベルコンテナにコンボを追加
        getNyuinrekiSickCombo1LabelContainer().add(getNyuinrekiSickCombo1(),VRLayout.CLIENT);
        // Hesesパネルにラベルコンテナ追加
        getNyuinrekiHesesPanel1().add(getNyuinrekiSickCombo1LabelContainer(),VRLayout.CLIENT);
        // 日付ラベルコンテナにHesesパネル追加
        getNyuinrekiDateLabelContainer1().setLayout(getNyuinrekiDateLayout2());
        getNyuinrekiDateLabelContainer1().add(getNyuinrekiStartDate1(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer1().add(getNyuinrekiDateLabel1(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer1().add(getNyuinrekiEndDate1(),VRLayout.FLOW);
        // 入院歴1ラベルコンテナ
        getNyuinrekiLabelContainer1().add(getNyuinrekiDateLabelContainer1(),VRLayout.WEST);
        getNyuinrekiLabelContainer1().add(getNyuinrekiHesesPanel1(),VRLayout.CLIENT);
        
        // ラベルコンテナにコンボを追加
        getNyuinrekiSickCombo2LabelContainer().add(getNyuinrekiSickCombo2(),VRLayout.CLIENT);
        // Hesesパネルにラベルコンテナ追加
        getNyuinrekiHesesPanel2().add(getNyuinrekiSickCombo2LabelContainer(),VRLayout.CLIENT);
        // 日付ラベルコンテナにHesesパネル追加
        getNyuinrekiDateLabelContainer2().setLayout(getNyuinrekiDateLayout1());
        getNyuinrekiDateLabelContainer2().add(getNyuinrekiStartDate2(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer2().add(getNyuinrekiDateLabel2(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer2().add(getNyuinrekiEndDate2(),VRLayout.FLOW);
        // 入院歴2ラベルコンテナ
        getNyuinrekiLabelContainer2().add(getNyuinrekiDateLabelContainer2(),VRLayout.WEST);
        getNyuinrekiLabelContainer2().add(getNyuinrekiHesesPanel2(),VRLayout.CLIENT);
    }
    
    /**
     * 傷病名グループに追加する項目を定義します。
     */
    protected void addSickGroupComponent(){
        // 傷病名グループを通常追加
        super.addSickGroupComponent();
        // 追加処理
        getSickNames1().add(getShusseiCheck1(),VRLayout.EAST);
        getSickNames2().add(getShusseiCheck2(),VRLayout.EAST);
        getSickNames3().add(getShusseiCheck3(),VRLayout.EAST);
        // 新規項目
        getSickNameGroup().setHgap(0);
        getSickNameGroup().add(getNyuinrekiLabel(),VRLayout.FLOW_RETURN);
        getSickNameGroup().add(getNyuinrekiLabelContainer1(),VRLayout.FLOW_RETURN);
        getSickNameGroup().add(getNyuinrekiLabelContainer2(),VRLayout.FLOW_RETURN);
    }
    
    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
        // 出生時チェックボックス選択イベント
        getShusseiCheck1().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                boolean enable;
                switch(e.getStateChange()){
                case ItemEvent.SELECTED:
                    enable = false;
                    break;
                case ItemEvent.DESELECTED:
                    enable = true;
                    break;
                default:
                    return;
                }
                shusseiCheck1Event(enable);
            }
        });
        // 出生時チェックボックス選択イベント
        getShusseiCheck2().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                boolean enable;
                switch(e.getStateChange()){
                case ItemEvent.SELECTED:
                    enable = false;
                    break;
                case ItemEvent.DESELECTED:
                    enable = true;
                    break;
                default:
                    return;
                }
                shusseiCheck2Event(enable);
            }
        });
        // 出生時チェックボックス選択イベント
        getShusseiCheck3().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                boolean enable;
                switch(e.getStateChange()){
                case ItemEvent.SELECTED:
                    enable = false;
                    break;
                case ItemEvent.DESELECTED:
                    enable = true;
                    break;
                default:
                    return;
                }
                shusseiCheck3Event(enable);
            }
        });
    }
    
    /**
     * 出生時チェックボックス１選択時のイベントです。
     */
    protected void shusseiCheck1Event(boolean enable){
        getSickDate1().setEnabled(enable);
        // 発症年月日のEnableがfalseである場合はバインドパス等を削る
        if(!enable){
            getSickDate1().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }else{
            getSickDate1().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            // 再度状態をチェックし設定しなおす。
            getSickDate1().checkState();
        }
        // 不詳の場合のみEnableを差し戻す
        if(getSickDate1().isDateUnknown()){
            getSickDate1().setEraUnknownState();
        }
    }
    
    /**
     * 出生時チェックボックス２選択時のイベントです。
     */
    protected void shusseiCheck2Event(boolean enable){
        getSickDate2().setEnabled(enable);
        // 発症年月日のEnableがfalseである場合はバインドパス等を削る
        if(!enable){
            getSickDate2().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }else{
            getSickDate2().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            // 再度状態をチェックし設定しなおす。
            getSickDate2().checkState();
        }
        // 不詳の場合のみEnableを差し戻す
        if(getSickDate2().isDateUnknown()){
            getSickDate2().setEraUnknownState();
        }

    }
    
    /**
     * 出生時チェックボックス３選択時のイベントです。
     */
    protected void shusseiCheck3Event(boolean enable){
        getSickDate3().setEnabled(enable);
        // 発症年月日のEnableがfalseである場合はバインドパス等を削る
        if(!enable){
            getSickDate3().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }else{
            getSickDate3().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            // 再度状態をチェックし設定しなおす。
            getSickDate3().checkState();
        }
        // 不詳の場合のみEnableを差し戻す
        if(getSickDate3().isDateUnknown()){
            getSickDate3().setEraUnknownState();
        }
    }
    
    /**
     * overrideしてタブに追加する項目を定義します。
     */
    protected void addThisComponent(){
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getSickNameGroup(), VRLayout.NORTH);
        this.add(getStableAndOutlook(), VRLayout.NORTH);
//        this.add(getProgressGroup(), VRLayout.NORTH);
    }
    /**
     * 入院歴 - 終了日付１を返します。
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiEndDate1() {
        if(nyuinrekiEndDate1 == null){
            nyuinrekiEndDate1 = new IkenshoEraDateTextField();
            nyuinrekiEndDate1.setAgeVisible(false);
            nyuinrekiEndDate1.setDayVisible(false);
            nyuinrekiEndDate1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiEndDate1.setBindPath("NYUIN_DT_END1");
        }
        return nyuinrekiEndDate1;
    }
    /**
     * 入院歴 - 終了日付２を返します。
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiEndDate2() {
        if(nyuinrekiEndDate2 == null){
            nyuinrekiEndDate2 = new IkenshoEraDateTextField();
            nyuinrekiEndDate2.setAgeVisible(false);
            nyuinrekiEndDate2.setDayVisible(false);
            nyuinrekiEndDate2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiEndDate2.setBindPath("NYUIN_DT_END2");
        }
        return nyuinrekiEndDate2;
    }
    /**
     * 入院歴Hesesパネルコンテナ１を返します。
     * @return
     */
    protected ACParentHesesPanelContainer getNyuinrekiHesesPanel1() {
        if(nyuinrekiHesesPanel1 == null){
            nyuinrekiHesesPanel1 = new ACParentHesesPanelContainer();
        }
        return nyuinrekiHesesPanel1;
    }
    /**
     * 入院歴Hesesパネルコンテナ２を返します。
     * @return
     */
    protected ACParentHesesPanelContainer getNyuinrekiHesesPanel2() {
        if(nyuinrekiHesesPanel2 == null){
            nyuinrekiHesesPanel2 = new ACParentHesesPanelContainer();
        }
        return nyuinrekiHesesPanel2;
    }
    /**
     * 入院歴タイトルラベルを返します。
     * @return
     */
    protected ACLabel getNyuinrekiLabel() {
        if(nyuinrekiLabel == null){
            nyuinrekiLabel = new ACLabel();
            nyuinrekiLabel.setText("入院歴（直近の入院歴を記入）");
        }
        return nyuinrekiLabel;
    }
    /**
     * 入院歴バックラベルコンテナ１を返します。
     * @return
     */
    protected ACBackLabelContainer getNyuinrekiLabelContainer1() {
        if(nyuinrekiLabelContainer1 == null){
            nyuinrekiLabelContainer1 = new ACBackLabelContainer();
            nyuinrekiLabelContainer1.setLayout(new VRLayout());
            nyuinrekiLabelContainer1.setHgap(0);
            nyuinrekiLabelContainer1.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        }
        return nyuinrekiLabelContainer1;
    }
    /**
     * 入院歴バックラベルコンテナ２を返します。
     * @return
     */
    protected ACBackLabelContainer getNyuinrekiLabelContainer2() {
        if(nyuinrekiLabelContainer2 == null){
            nyuinrekiLabelContainer2 = new ACBackLabelContainer();
            nyuinrekiLabelContainer2.setLayout(new VRLayout());
            nyuinrekiLabelContainer2.setHgap(0);
            nyuinrekiLabelContainer2.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            
        }
        return nyuinrekiLabelContainer2;
    }
    /**
     * 入院歴-傷病名コンボ１を返します。
     * @return
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected IkenshoOptionComboBox getNyuinrekiSickCombo1() {
        if(nyuinrekiSickCombo1 == null){
            nyuinrekiSickCombo1 = new IkenshoOptionComboBox();
            nyuinrekiSickCombo1.setBindPath("NYUIN_NM1");
            nyuinrekiSickCombo1.setMaxLength(30);
            nyuinrekiSickCombo1.setColumns(25);
            nyuinrekiSickCombo1.setIMEMode(InputSubset.KANJI);
        }
        return nyuinrekiSickCombo1;
    }
    /**
     * 入院歴コンボ用ラベルコンテナ１を返します。
     * @return
     */
    protected ACLabelContainer getNyuinrekiSickCombo1LabelContainer() {
        if(nyuinrekiSickCombo1LabelContainer == null){
            nyuinrekiSickCombo1LabelContainer = new ACLabelContainer();
            nyuinrekiSickCombo1LabelContainer.setText("傷病名");
        }
        return nyuinrekiSickCombo1LabelContainer;
    }
    /**
     * 入院歴-傷病名コンボ２を返します。
     * @return
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected IkenshoOptionComboBox getNyuinrekiSickCombo2() {
        if(nyuinrekiSickCombo2 == null){
            nyuinrekiSickCombo2 = new IkenshoOptionComboBox();
            nyuinrekiSickCombo2.setBindPath("NYUIN_NM2");
            nyuinrekiSickCombo2.setMaxLength(30);
            nyuinrekiSickCombo2.setColumns(25);
            nyuinrekiSickCombo2.setIMEMode(InputSubset.KANJI);
        }
        return nyuinrekiSickCombo2;
    }
    /**
     * 入院歴コンボ用ラベルコンテナ２を返します。
     * @return
     */
    protected ACLabelContainer getNyuinrekiSickCombo2LabelContainer() {
        if(nyuinrekiSickCombo2LabelContainer == null){
            nyuinrekiSickCombo2LabelContainer = new ACLabelContainer();
            nyuinrekiSickCombo2LabelContainer.setText("傷病名");
        }
        return nyuinrekiSickCombo2LabelContainer;
    }
    /**
     * 入院歴 - 開始日付１を返します。
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiStartDate1() {
        if(nyuinrekiStartDate1 == null){
            nyuinrekiStartDate1 = new IkenshoEraDateTextField();
            nyuinrekiStartDate1.setAgeVisible(false);
            nyuinrekiStartDate1.setDayVisible(false);
            nyuinrekiStartDate1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiStartDate1.setBindPath("NYUIN_DT_STA1");
        }
        return nyuinrekiStartDate1;
    }
    /**
     * 入院歴 - 開始日付２を返します。
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiStartDate2() {
        if(nyuinrekiStartDate2 == null){
            nyuinrekiStartDate2 = new IkenshoEraDateTextField();
            nyuinrekiStartDate2.setAgeVisible(false);
            nyuinrekiStartDate2.setDayVisible(false);
            nyuinrekiStartDate2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiStartDate2.setBindPath("NYUIN_DT_STA2");
        }
        return nyuinrekiStartDate2;
    }
    /**
     * 入院歴日付用ラベルコンテナ１を返します。
     * @return
     */
    protected ACLabelContainer getNyuinrekiDateLabelContainer1() {
        if(nyuinrekiDateLabelContainer1 == null){
            nyuinrekiDateLabelContainer1 = new ACLabelContainer();
            nyuinrekiDateLabelContainer1.setText("１．");
        }
        return nyuinrekiDateLabelContainer1;
    }
    /**
     * 入院歴日付用ラベルコンテナ２を返します。
     * @return
     */
    protected ACLabelContainer getNyuinrekiDateLabelContainer2() {
        if(nyuinrekiDateLabelContainer2 == null){
            nyuinrekiDateLabelContainer2 = new ACLabelContainer();
            nyuinrekiDateLabelContainer2.setText("２．");
        }
        return nyuinrekiDateLabelContainer2;
    }

    protected ACLabel getNyuinrekiDateLabel1() {
        if(nyuinrekiDateLabel1 == null){
            nyuinrekiDateLabel1 = new ACLabel();
            nyuinrekiDateLabel1.setText(" から ");
        }
        return nyuinrekiDateLabel1;
    }

    protected ACLabel getNyuinrekiDateLabel2() {
        if(nyuinrekiDateLabel2 == null){
            nyuinrekiDateLabel2 = new ACLabel();
            nyuinrekiDateLabel2.setText(" から ");
        }
        return nyuinrekiDateLabel2;
    }

    protected VRLayout getNyuinrekiDateLayout1() {
        if(nyuinrekiDateLayout1 == null){
            nyuinrekiDateLayout1 = new VRLayout();
            nyuinrekiDateLayout1.setHgap(0);
            nyuinrekiDateLayout1.setAutoWrap(false);
        }
        return nyuinrekiDateLayout1;
    }

    protected VRLayout getNyuinrekiDateLayout2() {
        if(nyuinrekiDateLayout2 == null){
            nyuinrekiDateLayout2 = new VRLayout();
            nyuinrekiDateLayout2.setHgap(0);
            nyuinrekiDateLayout2.setAutoWrap(false);
        }
        return nyuinrekiDateLayout2;
    }
    /**
     * 出生時チェックボックス１を返します。
     * @return
     */
    protected ACIntegerCheckBox getShusseiCheck1() {
        if(shusseiCheck1 == null){
            shusseiCheck1 = new ACIntegerCheckBox();
            shusseiCheck1.setText("出生時");
            shusseiCheck1.setBindPath("SHUSSEI1");
        }
        return shusseiCheck1;
    }
    /**
     * 出生時チェックボックス２を返します。
     * @return
     */
    protected ACIntegerCheckBox getShusseiCheck2() {
        if(shusseiCheck2 == null){
            shusseiCheck2 = new ACIntegerCheckBox();
            shusseiCheck2.setText("出生時");
            shusseiCheck2.setBindPath("SHUSSEI2");
        }
        return shusseiCheck2;
    }
    /**
     * 出生時チェックボックス３を返します。
     * @return
     */
    protected ACIntegerCheckBox getShusseiCheck3() {
        if(shusseiCheck3 == null){
            shusseiCheck3 = new ACIntegerCheckBox();
            shusseiCheck3.setText("出生時");
            shusseiCheck3.setBindPath("SHUSSEI3");
        }
        return shusseiCheck3;
    }
    
    /**
     * コントロールとして更新処理が可能かを返します。<br />
     * apply前に呼ばれます。
     * 
     * @throws Exception 処理例外
     * @return 更新処理が可能か
     */
    public boolean noControlError() {
        if(super.noControlError()){
            // 入院歴日付をチェック
            IkenshoEraDateTextField[] sicks = new IkenshoEraDateTextField[] {
                    nyuinrekiStartDate1, nyuinrekiEndDate1, nyuinrekiStartDate2,
                    nyuinrekiEndDate2 };
            int end = sicks.length;
            for (int i = 0; i < end; i++) {
                switch (sicks[i].getInputStatus()) {
                case IkenshoEraDateTextField.STATE_EMPTY:
                case IkenshoEraDateTextField.STATE_VALID:
                    break;
                case IkenshoEraDateTextField.STATE_FUTURE:
                    ACMessageBox.showExclamation("未来の日付です。");
                    sicks[i].requestChildFocus();
                    return false;
                default:
                    ACMessageBox.show("日付に誤りがあります。");
                    sicks[i].requestChildFocus();
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }
    
    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      super.initDBCopmponent(dbm);
      
      getSickSpecial1().setPressedModel(new ACComboBoxModelAdapter());
      getSickSpecial2().setPressedModel(new ACComboBoxModelAdapter());
      getSickSpecial3().setPressedModel(new ACComboBoxModelAdapter());
      
      applyPoolTeikeibun(getSickName1(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getSickName2(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getSickName3(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getNyuinrekiSickCombo1(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getNyuinrekiSickCombo2(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getNotStableState(), IkenshoCommon.TEIKEI_ISHI_INSECURE_CONDITION_NAME);
            

        // 2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応
        // ACComboBox⇒IkenshoOptionComboBox
        getSickName1().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getSickName2().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getSickName3().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getNyuinrekiSickCombo1().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getNyuinrekiSickCombo2().setOptionComboBoxParameters("疾病名",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getNotStableState().setOptionComboBoxParameters("「不安定」とした場合の具体的状況",
                IkenshoCommon.TEIKEI_ISHI_INSECURE_CONDITION_NAME, 30);

        // コンボの連動を設定
        getSickName1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName2(), getSickName3(),
                        getNyuinrekiSickCombo1(), getNyuinrekiSickCombo2() });
        getSickName2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName3(),
                        getNyuinrekiSickCombo1(), getNyuinrekiSickCombo2() });
        getSickName3().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2(),
                        getNyuinrekiSickCombo1(), getNyuinrekiSickCombo2() });
        getNyuinrekiSickCombo1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2(),
                        getSickName3(), getNyuinrekiSickCombo2() });
        getNyuinrekiSickCombo2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2(),
                        getSickName3(), getNyuinrekiSickCombo1() });

        // 設定する値を差し替える
        getSickSpecial1().setUnpressedModel(getSickName1().getOriginalModel());
        getSickSpecial2().setUnpressedModel(getSickName2().getOriginalModel());
        getSickSpecial3().setUnpressedModel(getSickName3().getOriginalModel());
        //    2007/10/18 [Masahiko Higuchi] Addition - end
      
    }
    
    /**
     * 追加走査対象のコンポーネントに参照先ソースの値を流し込みます。
     * 
     * @throws Exception 解析例外
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        // スーパークラスのメソッドを呼ぶ前にしよう
        // 一律押していないことにする
        getSickSpecial1().setPushed(true);
        getSickSpecial2().setPushed(true);
        getSickSpecial3().setPushed(true);
        getSickSpecial1().refreshCombo();
        getSickSpecial2().refreshCombo();
        getSickSpecial3().refreshCombo();
        // ボタンステータス設定後にリバインド
        applyPoolTeikeibun(getSickName1(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
        applyPoolTeikeibun(getSickName2(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
        applyPoolTeikeibun(getSickName3(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
        getSickSpecial1().setUnpressedModel( getSickName1().getModel() );
        getSickSpecial2().setUnpressedModel( getSickName2().getModel() );
        getSickSpecial3().setUnpressedModel( getSickName3().getModel() );
        
        
        super.bindSourceInnerBindComponent();
        // 出生時チェックボックス1選択イベント
        if(getShusseiCheck1().isSelected()){
            shusseiCheck1Event(false);
        }else{
            shusseiCheck1Event(true);
        }
        
        // 出生時チェックボックス2選択イベント
        if(getShusseiCheck2().isSelected()){
            shusseiCheck2Event(false);
        }else{
            shusseiCheck2Event(true);
        }
        
        // 出生時チェックボックス3選択イベント
        if(getShusseiCheck3().isSelected()){
            shusseiCheck3Event(false);
        }else{
            shusseiCheck3Event(true);
        }
        
    }
    
    /**
     * 追加走査対象のコンポーネントの値を参照先ソースに適用します。
     * 
     * @throws Exception 解析例外
     */
    protected void applySourceInnerBindComponent() throws Exception {
        // 出生時チェックボックス1選択イベント
        if(getShusseiCheck1().isSelected()){
            shusseiCheck1Event(false);
            // データを初期値で登録する
            if(getSickDate1().getSource() != null){
                getSickDate1().getSource().setData("HASHOU_DT1","0000年00月00日");
            }
        }else{
            shusseiCheck1Event(true);
        }
        
        // 出生時チェックボックス2選択イベント
        if(getShusseiCheck2().isSelected()){
            shusseiCheck2Event(false);
            // データを初期値で登録する
            if(getSickDate2().getSource() != null){
                getSickDate2().getSource().setData("HASHOU_DT2","0000年00月00日");
            }
        }else{
            shusseiCheck2Event(true);
        }
        
        // 出生時チェックボックス3選択イベント
        if(getShusseiCheck3().isSelected()){
            shusseiCheck3Event(false);
            // データを初期値で登録する
            if(getSickDates3().getSource() != null){
                getSickDate3().getSource().setData("HASHOU_DT3","0000年00月00日");
            }
        }else{
            shusseiCheck3Event(true);
        }
    }
    
}
