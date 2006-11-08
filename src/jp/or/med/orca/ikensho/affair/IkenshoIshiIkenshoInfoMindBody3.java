package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoBodyStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;

/**
 * 
 * IkenshoIshiIkenshoInfoMindBody3です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoMindBody3 extends
IkenshoIshiIkenshoInfoMindBody2 {

    // 上肢コンテナ-右
    private IkenshoBodyStatusContainer jyoshiRight;
    // 上肢コンテナ-左
    private IkenshoBodyStatusContainer jyoshiLeft;
    // 体幹コンテナ-右
    private IkenshoBodyStatusContainer taikanRight;
    // 体幹コンテナ-左
    private IkenshoBodyStatusContainer taikanLeft;
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
    
    /**
     * 
     * コンストラクタです。
     */
    public IkenshoIshiIkenshoInfoMindBody3() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 画面構成処理を行います。
     */
    private void jbInit(){
        // タブタイトルを設定します。
        getMindBody2Title().setText("３．心身の状態に関する意見（続き２）");
        // 画面構成
        addContaints();
        // イベントリスナ定義
        setEvent();
    }
    
    /**
     * overrideして身体の状態グループへの追加を定義します。
     */
    protected void addContaints(){
        addDownFuzuii();
        // パネル内に配置
        getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);
        
        getMindBodyStatusContainer().add(getConnectPain(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getDownFuzuiis(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getJyokusou(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMindBodyStatusOthers(), VRLayout.FLOW_RETURN);
    
        getMindBodyConnectContainer().add(getConnectKoushukus(), null);
        getMindBodyClientContainer().add(getHumanPicture(), BorderLayout.CENTER);
        getMindBodyClientContainer().add(getMindBodyConnectContainer(), BorderLayout.WEST);
    }
    
    /**
     * overrideして失調・不随意運動の追加順序を定義します。
     */
    protected void addDownFuzuii(){
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
        getTaikanContainar().setText("・体幹");
        ikenshoBodyComponentSetting(getTaikanRight(),"右");
        ikenshoBodyComponentSetting(getTaikanLeft(),"左");
        getTaikanContainar().add(getTaikanRight(),VRLayout.FLOW);
        getTaikanContainar().add(getTaikanLeft(),VRLayout.FLOW);        
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
    
    /**
     * 
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        getDownFuzuii().setSelected(
                getJyoshiLeft().getCheckBox().isSelected()
                        || getJyoshiRight().getCheckBox().isSelected()
                        || getTaikanLeft().getCheckBox().isSelected()
                        || getTaikanRight().getCheckBox().isSelected()
                        || getKashiLeft().getCheckBox().isSelected()
                        || getKashiRight().getCheckBox().isSelected());
        
        downFuzuiiChangeState();
    }
        
    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
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
        getTaikanRight().getCheckBox().setEnabled(enable);
        getTaikanLeft().getCheckBox().setEnabled(enable);
        getKashiRight().getCheckBox().setEnabled(enable);
        getKashiLeft().getCheckBox().setEnabled(enable);
        // 子を制御
        getJyoshiRight().followParentEnabled(enable);
        getJyoshiLeft().followParentEnabled(enable);
        getTaikanRight().followParentEnabled(enable);
        getTaikanLeft().followParentEnabled(enable);
        getKashiRight().followParentEnabled(enable);
        getKashiLeft().followParentEnabled(enable);
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
     * 体幹コンテナ-左を返します。
     * @return
     */
    protected IkenshoBodyStatusContainer getTaikanLeft() {
        if(taikanLeft == null){
            taikanLeft = new IkenshoBodyStatusContainer();
            taikanLeft.setRankBindPath("TAIKAN_SICCHOU_HIDARI_TEIDO");
            taikanLeft.setCheckBindPath("TAIKAN_SICCHOU_HIDARI");
        }
        return taikanLeft;
    }
    /**
     * 体幹コンテナ-右を返します。
     * @return
     */
    protected IkenshoBodyStatusContainer getTaikanRight() {
        if(taikanRight == null){
            taikanRight = new IkenshoBodyStatusContainer();
            taikanRight.setRankBindPath("TAIKAN_SICCHOU_MIGI_TEIDO");
            taikanRight.setCheckBindPath("TAIKAN_SICCHOU_MIGI");
        }
        return taikanRight;
    }
}
