package jp.or.med.orca.ikensho.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.w3c.dom.views.AbstractView;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACListBox;
import jp.nichicom.ac.component.ACListItem;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.mainmenu.ACMainMenuButton;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRListBox;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.affair.IkenshoTeikeibunEdit;
import jp.or.med.orca.ikensho.affair.IkenshoOptionTeikeibunEdit;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoOptionComboBoxです。
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public class IkenshoOptionComboBox extends ACComboBox {

    public static final String CLEAR = "クリア";

    public static final String EDIT = "編集...";

    protected IkenshoOptionComboBoxModelDecorator modelDecorator;
    // 編集時タイトル
    private String editComboTitle = "";
    // 定型文区分
    private int teikeibunKbn = 0;
    // 最大文字数
    private int fontMaxLength = 0;
    
    private boolean isSelectEvent = false;
    
    private boolean optionMode = true;

    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴いサイズ指定を可能とする。
    private int optionSize = 0;
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    
    // 連動対象となるコンボボックス群
    private ArrayList interlockComboComponents;

    /**
     * コンストラクタ
     *
     */
    public IkenshoOptionComboBox() {
        this(new DefaultComboBoxModel());
    }

    /**
     * コンストラクタ
     *
     * @param items
     */
    public IkenshoOptionComboBox(Object[] items) {
        this(new DefaultComboBoxModel(items));
    }

    /**
     * コンストラクタ
     *
     * @param items
     */
    public IkenshoOptionComboBox(Vector items) {
        this(new DefaultComboBoxModel(items));
    }

    /**
     * コンストラクタ
     *
     * @param aModel
     */
    public IkenshoOptionComboBox(ComboBoxModel aModel) {
        super();
        setEditable(true);
        this.setModel(aModel);
        this.setRenderer(new ACBindListCellRenderer());
    }

    /**
     * 初期構築
     */
    protected void initComponent() {
        super.initComponent();
    }
    
    /**
     * オプションコンポーネント追加後のモデルを取得します。
     */
    public ComboBoxModel getModel() {
        if(isOptionMode()){
            
            return modelDecorator;
            
        }else{
            
            return modelDecorator.getOriginalModel();
            
        }
    }
    
    /**
     * オプションコンポーネント設定前のモデルを取得します。
     * @return
     */
    public ComboBoxModel getOriginalModel(){
        return modelDecorator.getOriginalModel();
    }

    /**
     * モデル設定を行います。
     */
    public void setModel(ComboBoxModel aModel) {
        //新規にモデルを設定した場合、モデルデコレーターも新規作成する
        modelDecorator = new IkenshoOptionComboBoxModelDecorator(aModel);
        if(isOptionMode()){
            modelDecorator.getOptions().add(" ");
            modelDecorator.getOptions().add(EDIT);
        }
        
        super.setModel(modelDecorator);
        

    }
    
    /**
     * customizable を返します。
     *
     * @return customizable
     */
    public ArrayList getOptions() {
        return modelDecorator.getOptions();
    }

    /**
     * Selects the item at index <code>anIndex</code>.
     *
     * @param anIndex an integer specifying the list item to select,
     *          where 0 specifies the first item in the list and -1 indicates no selection
     * @exception IllegalArgumentException if <code>anIndex</code> < -1 or
     *          <code>anIndex</code> is greater than or equal to size
     * @beaninfo
     *   preferred: true
     *  description: The item at index is selected.
     */
    public void setSelectedIndex(int index) {
        if (index < modelDecorator.getOriginalModel().getSize()) {
            super.setSelectedIndex(index);
        } else {
            if(!isSelectEvent){
                // 連続イベント対策
                isSelectEvent = true;
                //イベント発生
                fireVROptionSelected(new IkenshoOptionComboBoxEvent(this, getOptions().get(index - modelDecorator.getOriginalModel().getSize())));
                isSelectEvent = false;
            }
        }
    }

    /**
     * オプション項目選択時の処理を行います。
     * @param e イベントソース
     */
    protected void fireVROptionSelected(IkenshoOptionComboBoxEvent e) {
        
        // 空白は処理しない
        if(" ".equals(e.getOptionItem())){
            super.clearSelection();
            return;
        }
        
        try{
            // 定型文ダイアログの生成
            IkenshoOptionTeikeibunEdit editDlg = new IkenshoOptionTeikeibunEdit(
                    getEditComboTitle(), IkenshoTeikeibunEdit.TEIKEIBUN,
                    getTeikeibunKbn(), getFontMaxLength(),this.getText());
            
            int otherDocKbn = -1;
            int otherDocType = -1;
            switch(getTeikeibunKbn()){
            case IkenshoCommon.TEIKEI_SICK_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_SICK_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_SICK_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_SICK_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case IkenshoCommon.TEIKEI_MEDICINE_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT:
                otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case IkenshoCommon.TEIKEI_MEDICINE_USAGE:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE:
                otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_USAGE;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            }
            
            // コンボ種類によるキャプション名称の変更
            switch (otherDocType) {
            case IkenshoConstants.IKENSHO_LOW_DEFAULT:
            case IkenshoConstants.IKENSHO_LOW_H18:
                editDlg.setAllowedAddOtherDocument(otherDocKbn, "主治医意見書");
                break;
            case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
                editDlg.setAllowedAddOtherDocument(otherDocKbn, "医師意見書");
                break;
            }
            
            editDlg.setVisible(true);
            
            VRMap param = new VRHashMap();
            param.setData("RETURN_TYPE",new Integer(editDlg.getReturnType()));
            param.setData("RETURN_VALUE",editDlg.getReturnValue());
            // コンボ項目の再設定処理
            resettingComboItem(param);
            
        }catch(Exception er){
            er.printStackTrace();
            ACMessageBox.show("定型文編集に必要なパラメーターが不足しています。");
            return;
        }
        
    }
    

    /**
     * 編集コンボ名称を設定します。
     * @param name
     */
    public void setEditComboTitle(String name){
        editComboTitle = name;
    }
    
    /**
     * 編集コンボ名称を設定します。
     * @param name
     */
    public String getEditComboTitle(){
        return editComboTitle;
    }
    
    /**
     * 定型文呼び出し用の区分番号を取得します。
     * @return
     */
    public int getTeikeibunKbn() {
        return teikeibunKbn;
    }
    
    /**
     * 定型文呼び出し用の区分番号を設定します。
     * @param teikeibunKbn
     */
    public void setTeikeibunKbn(int index) {
        this.teikeibunKbn = index;
    }
    
    /**
     * 最大文字数を取得します。
     * @return
     */
    public int getFontMaxLength() {
        return fontMaxLength;
    }
    
    /**
     * 最大文字数を設定します。
     * @param fontMaxLength
     */
    public void setFontMaxLength(int maxLength) {
        this.fontMaxLength = maxLength;
    }
    
    /**
     * オプションコンボボックスに必須となる項目を設定します。
     * @param editComboTitle
     * @param teikeibunKbn
     * @param fontMaxLength
     */
    public void setOptionComboBoxParameters(String editComboTitle,
            int teikeibunKbn, int fontMaxLength) {
        // タイトル
        setEditComboTitle(editComboTitle);
        // 定型文区分
        setTeikeibunKbn(teikeibunKbn);
        // 最大文字数
        setFontMaxLength(fontMaxLength);
    }

    /**
     * 編集後の設定処理を行います。
     * 
     * @param param パラメーター群
     * @throws Exception
     */
    protected void resettingComboItem(VRMap param) throws Exception{
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        HashMap teikeibunMap = new HashMap();
        // 定型文の再取得
        IkenshoCommon.getMasterTable(dbm, teikeibunMap, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", "TKB_CD");
        Object obj = teikeibunMap.get(new Integer(getTeikeibunKbn()));
        // 自分自身に再設定
        if (obj instanceof VRHashMapArrayToConstKeyArrayAdapter) {
          IkenshoCommon.applyComboModel(this,
                                        (VRHashMapArrayToConstKeyArrayAdapter)
                                        obj);
              Object objItem = new Object();
              // 連動コンボにも同一設定
            if (getInterlockComboComponents() != null) {
                for (int i = 0; i < getInterlockComboComponents().size(); i++) {
                    // オプションコンボボックスである事は保障する
                    if (getInterlockComboComponents().get(i) instanceof IkenshoOptionComboBox) {
                        // ローカル変数に取得
                        IkenshoOptionComboBox combo = (IkenshoOptionComboBox) getInterlockComboComponents()
                                .get(i);
                        // 値は一旦退避しておく
                        objItem = combo.getSelectedItem();
                        // Nullが来た場合
                        if(objItem == null){
                            // テキストから文字列を取得する
                            objItem = combo.getText();
                        }
                        // オプションコンボモードである場合
                        if (combo.isOptionMode()) {
                            // モデル再設定
                            IkenshoCommon.applyComboModel(combo,
                                    (VRHashMapArrayToConstKeyArrayAdapter) obj);
                        }
                        // 値の差し戻し
                        combo.setSelectedItem(objItem);
                    }
                }
            }
          
        }else{
            this.setModel(new ACComboBoxModelAdapter());
        }
        
        // 戻り時のボタンにより処理制御
        switch(ACCastUtilities.toInt(param.getData("RETURN_TYPE"))){
            case IkenshoOptionTeikeibunEdit.CLOSE_RETURN:
                // 閉じる
                // 値の差し戻し
                this.setSelectedItem(param.getData("RETURN_VALUE"));
                break;
            case IkenshoOptionTeikeibunEdit.SELECT_RETURN:
                // 選択有
                // 選択した値を設定する。
                this.setSelectedItem(param.getData("RETURN_VALUE"));
                break;
            default:
                
                break;
        }
        
    }
    
    /**
     * オプションモードを返却します。
     * @return
     */
    public boolean isOptionMode() {
        return optionMode;
    }

    /**
     * オプションモードを設定します。
     * @param optionMode
     */
    public void setOptionMode(boolean optionMode) {
        this.optionMode = optionMode;

    }

    /**
     * モデルの連動するコンボ群を取得します。
     * 
     * @return 選択肢の連動するコンボ群
     */
    public ArrayList getInterlockComboComponents() {
        return interlockComboComponents;
        
    }

    /**
     * モデルの連動するコンボ群を設定します。
     * @param 選択肢が連動するコンボ群
     */
    public void addInterlockComboComponents(
            IkenshoOptionComboBox[] interlockComboComponents) {
        if(this.interlockComboComponents == null){
            this.interlockComboComponents = new ArrayList();
        }
        
        for(int i=0;i<interlockComboComponents.length; i++){
            this.interlockComboComponents.add(interlockComboComponents[i]);
        }
    }
    
    /**
     * 定型文をコンボボックスモデル形式で取得します。
     * @throws Exception
     */
    public ComboBoxModel getTeikeibunComboBoxModel() throws Exception {

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        HashMap teikeibunMap = new HashMap();
        // 定型文の再取得
        IkenshoCommon.getMasterTable(dbm, teikeibunMap, "TEIKEIBUN",
                "TEIKEIBUN", "TKB_KBN", "TKB_CD");
        Object obj = teikeibunMap.get(new Integer(getTeikeibunKbn()));
        if (obj instanceof VRHashMapArrayToConstKeyArrayAdapter) {
            return (ComboBoxModel) IkenshoCommon
                    .createComboAdapter((VRHashMapArrayToConstKeyArrayAdapter) obj);
        } else {

            return (ComboBoxModel) new ACComboBoxModelAdapter();
        }
    }
 
    
    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin 画面調整に伴いサイズ指定を可能とする。
    
    /**
     * コンボボックスの指定サイズを返却します。 
     */
    public int getOptionSize() {
        return optionSize;
    }

    /**
     * コンボボックスの指定サイズを設定します。
     * @param optionColumnSize
     */
    public void setOptionSize(int optionColumnSize) {
        this.optionSize = optionColumnSize;
    }
    
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if(getMaxLength()>0 && d != null){
            //最大幅で制限する
            d.width += 6;
            // オプションサイズが0を超えている場合
            if(getOptionSize() > 0) {
                // カラムサイズを採用する
                d.width = getOptionSize();
            }
            
        }
        return d;
    }
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    
    
    // [ID:0000438][Tozo TANAKA] 2009/06/10 delete begin 【主治医医見書・医師医見書】薬剤名テキストの追加
    protected AbstractVRTextField createEditorField() {
        return new IkenshoACTextField();
    }
    // [ID:0000438][Tozo TANAKA] 2009/06/10 delete end 【主治医医見書・医師医見書】薬剤名テキストの追加
    

}

