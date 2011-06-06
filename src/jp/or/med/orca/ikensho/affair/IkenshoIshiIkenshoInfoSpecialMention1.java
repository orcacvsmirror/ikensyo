package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/**
 * 
 * IkenshoIshIkenshoInfoSpecialMention1です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/11
 */
public class IkenshoIshiIkenshoInfoSpecialMention1 extends
        IkenshoIkenshoInfoMentionH18 {
    /**
     * コンストラクタです。
     */
    public IkenshoIshiIkenshoInfoSpecialMention1() {
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
        // タイトル設定
        getTitle().setText("５．その他特記すべき事項");
        // ラベル文字列変更
        getTokkiAbstraction1()
                .setText(
                        "障害程度区分認定やサービス利用計画作成に必要な医学的なご意見等をご記載して下さい。なお、専門医等に別途意見を求めた場合はその内容、結果も記載して下さい。（情報提供書や身体障害者申請診断書の写し等を添付して頂いても結構です。）");
        getMentionTokkiMoreAbstraction().setText("");
        
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        getMentionTokki().setColumns(92);
        getMentionTokki().setMaxRows(9);
        getMentionTokki().setRows(10);
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        
        // パネル追加への定義
        addComponent();
    }
    
    /**
     * overrideして特記事項グループへの追加順序を定義します。
     */
    protected void addMentionTokkiGroupComponent(){
        getMentionTokkiGroup().add(getMentionTokkiAbstractions(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkis(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkiMoreAbstractions(), VRLayout.NORTH);
    }
    
    /**
     * overrideしてタブパネルへの追加を定義します。
     */
    protected void addComponent(){
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getMentionTokkiGroup(), VRLayout.NORTH);
        
    }
    
    /**
     * 医療機関登録画面を表示します。
     */
    public void showRegistHokensya() {
        // 処理を行わない。
    }
    
    /**
     * 検査点数入力画面を表示します。
     */
    protected void showInputTestPoint() {
        // 処理を行わない。
    }
    
    /** 
     * overrideして処理を行わない
     */
    public boolean isTestPointCheckWarning() {
        return true;
    }
    
    /**
     * overrideして処理を行わない
     */
    public boolean noControlWarning() {
        return true;
    }
    
    /**
     * 保険者名の変更を処理します。
     * 
     * @param e イベント情報
     */
    protected void changeInsurerName(ItemEvent e) {
        // 処理を行わせない。
    }

    
    protected IkenshoExtraSpecialNoteDialog createMentionTeikeibunKubun(){
        // [ID:0000514][Tozo TANAKA] 2009/09/24 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        return new IkenshoExtraSpecialNoteDialog(
//                "その他特記すべき事項",
//                IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, 400, 100, 8,
//                50);
        return new IkenshoExtraSpecialNoteDialog(
                "その他特記すべき事項",
                IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, getMentionTokki().getMaxLength(), getMentionTokki().getColumns(), getMentionTokki().getMaxRows(),
                50);
        // [ID:0000514][Tozo TANAKA] 2009/09/24 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    }

}
