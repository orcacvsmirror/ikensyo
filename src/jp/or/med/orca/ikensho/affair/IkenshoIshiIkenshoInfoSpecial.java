package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.util.Arrays;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoIshiIkenshoInfoSpecial extends IkenshoIkenshoInfoSpecial {

    // 吸引処置コンテナ
    protected ACLabelContainer specialKyuinShochiContainer;
    protected ACPanel kyuinShochiPanel;
    // ()付きのパネル
    protected ACParentHesesPanelContainer specialKyuinShochiHases;
    // 回数
    protected ACTextField specialKyuinShochiCount;
    // 吸引処置メインチェック
    protected ACIntegerCheckBox specialKyuinShochiMainCheck;
    // 吸引処置回数ラベル
    protected ACLabel specialKyuinShochiCountDayComment;
    // 吸引処置ラジオグループ
    protected ACClearableRadioButtonGroup kyuinShochiStatesRadio;
    
    private VRListModelAdapter kyuinShochiListModel;
    
    public IkenshoIshiIkenshoInfoSpecial() {
        try{
            // イベント定義
            setEvent();
            // 画面再構築
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
        
    /**
     * 画面構成に関する処理を行います。
     * @throws Exception
     */
    private void jbInit() throws Exception {
        specialTitle.setText("２．特別な医療（現在、定期的に、あるいは頻回に受けている医療）");
        
        // 項目設定
        getSpecialKyuinShochiMainCheck().setText("吸引処置");
        getSpecialKyuinShochiCountDayComment().setText("回／日,");
        getKyuinShochiPanel().setOpaque(false);
        getSpecialKeikanEiyou().setText("経管栄養(胃ろう)");
        getSpecialMessage2().setText("「医師意見書」では印刷されません。");
        getSpecialKyuinShochiHases().setBeginText("（回数");
        
        // サイズ設定
        getSpecialKyuinShochiMainCheck().setPreferredSize(new Dimension(140, 5));
        // 自動折り返しなし
        getSpecialKyuinShochiHases().setAutoWrap(false);
        
        // 画面構成
        getSpecialKyuinShochiHases().add(getSpecialKyuinShochiCountText(),VRLayout.FLOW);
        getSpecialKyuinShochiHases().add(getSpecialKyuinShochiCountDayComment(),VRLayout.FLOW);
        getSpecialKyuinShochiHases().add(getKyuinShochiStatesRadio(),VRLayout.FLOW);
        getKyuinShochiPanel().add(getSpecialKyuinShochiHases(),VRLayout.FLOW);
        getSpecialKyuinShochiContainer().add(getSpecialKyuinShochiMainCheck(),VRLayout.WEST);
        getSpecialKyuinShochiContainer().add(getKyuinShochiPanel(),VRLayout.WEST);
        
        // チェックボックス状態変更処理
        kyuinShochiCheckAction(getSpecialKyuinShochiMainCheck().isSelected());

    }
 
    /**
     * 処理内容の並び順をoverrideして変更します。
     */
    protected void addProcess() {
        super.addProcess();
        getProcesss().add(getSpecialKyuinShochiContainer(),VRLayout.FLOW_RETURN);
    }
    
    /**
     * イベントリスナーを定義します。
     */
    protected void setEvent(){
        getSpecialKyuinShochiMainCheck().addItemListener(new ItemListener(){
            /*
             * 吸引処置チェックボックスに関するイベント
             */
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
                kyuinShochiCheckAction(select);
            }
        });
    }
    /**
     * 吸引処置をチェックした際の処理です。
     */
    private void kyuinShochiCheckAction(boolean enable){
        // Enable制御
        getSpecialKyuinShochiCountText().setEnabled(enable);
        getKyuinShochiStatesRadio().setEnabled(enable);
        getSpecialKyuinShochiHases().setEnabled(enable);
        getSpecialKyuinShochiCountDayComment().setEnabled(enable);
        getKyuinShochiStatesRadio().getClearButton().setEnabled(enable);
    }
    /**
     * 吸引処置回数コンボボックスを返します。
     * @return
     */
    protected ACTextField getSpecialKyuinShochiCountText() {
        if(specialKyuinShochiCount == null){
            specialKyuinShochiCount = new ACTextField();
            specialKyuinShochiCount.setBindPath("KYUIN_SHOCHI_CNT");
            specialKyuinShochiCount.setIMEMode(InputSubset.KANJI);
            specialKyuinShochiCount.setMaxLength(5);
            specialKyuinShochiCount.setColumns(5);
        }
        return specialKyuinShochiCount;
    }
    /**
     * 吸引処置ラベルコンテナを返します。
     * @return
     */
    protected ACLabelContainer getSpecialKyuinShochiContainer(){
        if(specialKyuinShochiContainer == null){
            specialKyuinShochiContainer = new ACLabelContainer();
        }
        return specialKyuinShochiContainer;
    }
    /**
     * 吸引処置Hesesパネルを返します。
     * @return
     */
    protected ACParentHesesPanelContainer getSpecialKyuinShochiHases() {
        if (specialKyuinShochiHases == null){
            specialKyuinShochiHases = new ACParentHesesPanelContainer();
        }
        return specialKyuinShochiHases;
    }
    /**
     * 吸引処置メインチェックボックスを返します。
     * @return
     */
    protected ACCheckBox getSpecialKyuinShochiMainCheck() {
        if(specialKyuinShochiMainCheck == null){
            specialKyuinShochiMainCheck = new ACIntegerCheckBox();
            specialKyuinShochiMainCheck.setBindPath("KYUIN_SHOCHI");
        }
        return specialKyuinShochiMainCheck;
    }
    /**
     * 吸引処置回数ラベルします。
     * @return
     */
    protected ACLabel getSpecialKyuinShochiCountDayComment() {
        if(specialKyuinShochiCountDayComment == null){
            specialKyuinShochiCountDayComment = new ACLabel();
        }
        return specialKyuinShochiCountDayComment;
    }
    /**
     * 吸引処置パネルを返します。
     * @return
     */
    protected ACPanel getKyuinShochiPanel() {
        if (kyuinShochiPanel == null){
            kyuinShochiPanel = new ACPanel();
        }
        return kyuinShochiPanel;
    }
    /**
     * 吸引障害のリストモデルを返します。
     * @return
     */
    protected VRListModelAdapter getKyuinShochiListModel() {
        if(kyuinShochiListModel == null){
            kyuinShochiListModel = new VRListModelAdapter(new VRArrayList(
                    Arrays.asList(new String[] { "一時的", "継続的" })));
        }

        return kyuinShochiListModel;
    }
    
    /**
     * 吸引処置ラジオグループを返します。
     * @return
     */
    protected ACClearableRadioButtonGroup getKyuinShochiStatesRadio() {
        if(kyuinShochiStatesRadio == null){
            kyuinShochiStatesRadio = new ACClearableRadioButtonGroup();
            // モデル設定
            kyuinShochiStatesRadio.setModel(getKyuinShochiListModel());
            kyuinShochiStatesRadio.setBindPath("KYUIN_SHOCHI_JIKI");
        }
        return kyuinShochiStatesRadio;
    }
    
//    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
//        super.initDBCopmponent(dbm);
//        // applyPoolTeikeibun(getSpecialKyuinShochiCountText(), IkenshoCommon.TEIKEI_ISHI_SUCK_COUNT);
//    }
    
}
