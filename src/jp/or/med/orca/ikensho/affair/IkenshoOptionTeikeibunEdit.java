package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/**
 * IkenshoOptionTeikeibunEditです。
 * 
 * コンボ遷移後の定型文編集業務です。
 * 
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public class IkenshoOptionTeikeibunEdit extends IkenshoTeikeibunEdit {

    // 閉じるボタン押下後戻り
    public static final int CLOSE_RETURN = 0;
    // 選択して戻り
    public static final int SELECT_RETURN = 1;
    // 選択して戻るボタン
    private ACButton selectReturn;
    // 選択して戻る押下時の値
    private String returnValue = "";
    // 戻り時の状態を特定するフラグ
    public int returnType = 99;
    
    /**
     * @deprecated 推奨されないコンストラクタです。
     * @param title
     * @param tableNo
     * @param kubun
     */
    public IkenshoOptionTeikeibunEdit(String title, int tableNo, int kubun) {
        super(title, tableNo, kubun);
    }

    /**
     * 
     * コンストラクタです。
     * @param title
     * @param tableNo
     * @param kubun
     * @param length
     */
    public IkenshoOptionTeikeibunEdit(String title, int tableNo, int kubun, int length) {
        super(title, tableNo, kubun, length);
    }
    /**
     * 
     * コンストラクタです。
     * @param title
     * @param tableNo
     * @param kubun
     * @param length
     * @param comboValue 遷移時のコンボの値を設定。
     */
    public IkenshoOptionTeikeibunEdit(String title, int tableNo, int kubun, int length,String comboValue) {
        super(title, tableNo, kubun, length);
        // 値を退避する。
        returnValue = comboValue;
    }
    
    /**
     * 
     * コンストラクタです。
     */
    public IkenshoOptionTeikeibunEdit() {
        super();
    }

    /**
     * 画面生成をoverride
     */
    protected void jbInit() throws Exception {
        super.jbInit();
    }
    
    /**
     * オプションコンボ画面変更処理用。
     * @return
     */
    protected void jbInitCustom() {

    }
    
    /**
     * ボタン領域の生成を行います。
     */
    protected void buildButtonsPanel(){
        getButtons().add(getOtherAdd(),null);
        getButtons().add(getAdd(),null);
        getButtons().add(getEdit(),null);
        getButtons().add(getDelete(),null);
        getButtons().add(getComit(),null);
        getButtons().add(getSelectReturn(),null);
        getButtons().add(getClose(),null);
    }
    
    
    /**
     * イベントの追加を行います。
     */
    protected void addEvents() {
        
        // 登録されているイベントの削除
        getComit().removeActionListener(getComit().getActionListeners()[0]);
        
        // 登録時のイベント再定義
        getComit().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // DB更新
                try {
                    updateData();
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }
                ACMessageBox.show("更新しました。");
                // テーブルの再取得処理
                doSelect();
            }
            
        });
        
        // 選択して戻る選択時のイベント 
        getSelectReturn().addActionListener(new ActionListener(){
            // イベント処理
            public void actionPerformed(ActionEvent e){
                
                try {
                    // リスト項目に変更がある場合（未登録）
                    if(isModified()){
                        if (ACMessageBox
                                .showOkCancel("リスト候補が変更されています。変更内容を登録しますか？") == ACMessageBox.RESULT_OK) {
                            updateData();
                            ACMessageBox.show("更新しました。");
                        }
                        // 一旦処理を終える。
                        return;
                    }
                    
                    String selectValue = getReturnValue();
                    // 選択されているか
                    if(getTable().isSelected()){
                        selectValue = ACCastUtilities.toString(((VRMap)getTable().getSelectedModelRowValue()).getData("TEIKEIBUN"));
                        // 値に差がある、なおかつ値が空白以外の場合
                        if(!getReturnValue().equals(selectValue)&&!"".equals(getReturnValue())){
                            int msgResult = ACMessageBox.showOkCancel("既存データを置き換えてよろしいですか？");
                            // キャンセルだった場合
                            if(msgResult == ACMessageBox.RESULT_CANCEL){
                                // 処理終了
                                return;
                            }
                        }
                    }
                    // 値を設定
                    setReturnValue(selectValue);
                    
                } catch (Exception err) {
                    err.printStackTrace();
                }
                // ダイアログを閉じる
                closeWindow();
                // 戻り判定フラグ設定
                returnType = SELECT_RETURN;
            }
        });
    }
    
    
    /**
     * ウィンドウを閉じます。override
     */
    protected void closeWindow() {
        super.closeWindow();
        returnType = CLOSE_RETURN;
    }
    
    /**
     * 戻り時の情報を取得します。
     * @return
     */
    public int getReturnType(){
        return this.returnType;
    }
    
    /**
     * 選択して戻るボタンを取得します。
     * @return
     */
    protected ACButton getSelectReturn() {
        if(selectReturn == null){
            selectReturn = new ACButton();
            selectReturn.setText("選択して戻る(E)");
            selectReturn.setMnemonic('E');
            selectReturn.setToolTipText("選択されている項目を採用し元の画面に戻ります。");
        }
        return selectReturn;
    }

    /**
     * 戻り時の値を取得します。
     * @return
     */
    public String getReturnValue() {
        return returnValue;
    }
    
    /**
     * 戻り時の値を退避します。
     * @param selectReturnValue
     */
    public void setReturnValue(String selectReturnValue) {
        this.returnValue = selectReturnValue;
    }
    
    /**
     * 変更ボタン押下時の追加処理
     * 
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected void addChangeAfter() throws Exception{
    	if(table != null){
    		// 再設定処理を走らせる
    		table.setSelectedModelRow(editRow);
    	}
    }
    
}
