package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.layout.VRLayout;
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
 */
public class IkenshoIshiIkenshoInfoMindBody2 extends
        IkenshoIkenshoInfoMindBody2H18 {
    
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
    private VRLayout kousyukuLayout = new VRLayout();
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
    /**
     * 
     * コンストラクタです。
     */
    public IkenshoIshiIkenshoInfoMindBody2() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 画面構成に関する処理を行います。
     */
    private void jbInit(){
        getMindBody2Title().setText("３．心身の状態に関する意見（続き１）");
        // 医師意見書では項目が全て表示できないので関節の痛みまででとめとく
        addContaints();
        // イベントリスナ定義
        setEvent();
        
    }

    /**
     * overrideして身体の状態グループへの追加を定義します。
     */
    protected void addContaints(){
        // 四肢欠損の部位を表示
        getShishiKesson().setRankVisible(true);
        // 麻痺コンテナ表示順並び替え
        addMahiContainar();
        // 関節の拘縮グループ生成
        buildKansetuKousyukuGroup();
        // 画面配置
        getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.CENTER);
        getMindBodyStatusContainer().add(getShishiKesson(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMahiContainer(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMahi(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getMastleDown(), VRLayout.FLOW_RETURN);
        getMindBodyStatusContainer().add(getConnectKousyuku(), VRLayout.FLOW_RETURN);

    }
    
    /**
     * overrideして麻痺コンテナの並び順を変更します。
     */
    protected void addMahiContainar(){
        // チェックボックスの要求サイズを最低にする
        getMahiLegLeftUp().getCheckBox().setPreferredSize(null);
        getMahiLegLeftDown().getCheckBox().setPreferredSize(null);
        getMahiLegRightUp().getCheckBox().setPreferredSize(null);
        getMahiLegRightDown().getCheckBox().setPreferredSize(null);
        // 並び替え
        // 左上肢　左下肢
        // 右上肢　右下肢
        getMahis().add(getMahiLegLeftUp(), VRLayout.FLOW);
        getMahis().add(getMahiLegLeftDown(), VRLayout.FLOW_RETURN);
        getMahis().add(getMahiLegRightUp(), VRLayout.FLOW);
        getMahis().add(getMahiLegRightDown(), VRLayout.FLOW_RETURN);
        getMahis().add(getMahiOther(), VRLayout.FLOW_RETURN);
    }
    
    /**
     * 関節の拘縮グループを生成します。
     */
    protected void buildKansetuKousyukuGroup(){
        // レイアウト設定を行う。
        getKousyukuLayout().setAutoWrap(false);
        getKousyukuLayout().setHgap(0);
        getKousyukuLayout().setVgap(0);
        
        VRLayout kataLayout = new VRLayout();
        VRLayout mataLayout = new VRLayout();
        VRLayout hijiLayout = new VRLayout();
        VRLayout hizaLayout = new VRLayout();
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
        
        // 股関節グループ ------------------------------------------------------
        getMataCheck().setText("股関節");
        ikenshoBodyComponentSetting(getMataKousyukuRight(),"右");
        ikenshoBodyComponentSetting(getMataKousyukuLeft(),"左");
        getMataBackLabelContainar().add(getMataCheck(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuRight(),VRLayout.FLOW);
        getMataBackLabelContainar().add(getMataKousyukuLeft(),VRLayout.FLOW);
        // 股関節グループ ------------------------------------------------------
        
        // 肘関節グループ ------------------------------------------------------
        getHijiCheck().setText("肘関節");
        ikenshoBodyComponentSetting(getHijiKousyukuRight(),"右");
        ikenshoBodyComponentSetting(getHijiKousyukuLeft(),"左");
        getHijiBackLabelContainar().add(getHijiCheck(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuRight(),VRLayout.FLOW);
        getHijiBackLabelContainar().add(getHijiKousyukuLeft(),VRLayout.FLOW);
        // 肘関節グループ ------------------------------------------------------        
        
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
        getConnectKousyuku().add(getMataBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHijiBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getHizaBackLabelContainar(),VRLayout.FLOW_RETURN);
        getConnectKousyuku().add(getSonota(),VRLayout.FLOW_RETURN);
    }


    /**
     * イベントリスナを定義します。
     */
    protected void setEvent(){
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
     * 
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        // 拘縮チェック
        getKousyukuCheck().setSelected(
                getKataCheck().isSelected() || getMataCheck().isSelected()
                        || getHijiCheck().isSelected()
                        || getHizaCheck().isSelected()
                        || getSonota().getCheckBox().isSelected());
        
        kousyukuChangeState();
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
            sonota.setRankVisible(false);
            sonota.getCheckBox().setText("その他");
            sonota.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            sonota.setCheckBindPath("KOUSHU_ETC");
            sonota.setPosBindPath("KOUSHU_ETC_BUI");
        }
        return sonota;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        applyPoolTeikeibun(getSonota().getComboBox(), IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME);
      }

}
