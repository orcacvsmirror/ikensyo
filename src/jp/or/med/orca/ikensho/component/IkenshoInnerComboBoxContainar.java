package jp.or.med.orca.ikensho.component;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

/**
 * 
 * IkenshoInnerComboBoxContainarです。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/11
 */
public class IkenshoInnerComboBoxContainar extends ACLabelContainer {

    private ACComboBox combo = new ACComboBox();
    private IkenshoHintButton hintButton = new IkenshoHintButton();
    private VRMap maps = new VRHashMap();
    private ArrayList listArray = new ArrayList();
    
    /**
     * コンストラクタです。
     */
    public IkenshoInnerComboBoxContainar() {
        super();
        try{
            if(combo == null){
                combo = new ACComboBox();
            }
            // コンボ追加
            this.add(combo,VRLayout.FLOW);
            // ヒントボタン追加
            this.add(hintButton,VRLayout.FLOW);
            // 初期化処理
            initialize();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * コンストラクタです。
     * @param text
     */
    public IkenshoInnerComboBoxContainar(String text) {
        super(text);
    }

    /**
     * 初期化処理
     */
    public void initialize(){
        // 今回のコンボのサイズを規定
        combo.setPreferredSize(new Dimension(50,19));
        combo.setEditable(false);
        combo.setBlankable(false);
    }
    
    /**
     * コンボの選択肢を設定します。
     */
    public void setInnerComboModel(String bindPath, String renderBintPath,
            Object[] bindChoice, Object[] renderChoice) {
        for (int i = 0; i < Math.min(bindChoice.length, renderChoice.length); i++) {
            maps = new VRHashMap();
            maps.setData(bindPath, bindChoice[i]);
            maps.setData(renderBintPath, renderChoice[i]);
            listArray.add(maps);
        }
        combo.setModel(listArray);
// combo.setModel(maps);
    }
    
    
    /**
     * コンボのサイズを変更します
     */
    public void setComboSize(int width,int height){
        combo.setPreferredSize(new Dimension(width,height));
    }
    
    /**
     * 内部に設定しているコンボボックスを返します。
     * @return
     */
    public ACComboBox getInnerComboBox(){
        return combo;
    }

    /**
     * 内部に設定しているヒントボタンを返します。
     * @return
     */
    public IkenshoHintButton getHintButton() {
        return hintButton;
    }
    
    /**
     * 内部コンボのEditableを設定します。
     * @param editable
     */
    public void setInnerComboEditable(boolean editable){
        combo.setEditable(editable);
    }
    
    /**
     * 内部コンボにバインドパスを設定します。
     * @param bindPath
     */
    public void setComboBindPath(String bindPath){
        combo.setBindPath(bindPath);
    }

    /**
     * 内部コンボに設定されているバインドパスを返します。
     * @return
     */
    public String getComboBindPath(){
        return combo.getBindPath();
    }

    /**
     * 内部コンボのレンダーバインドパスを設定します。
     * @param renderPath
     */
    public void setComboRenderBindPath(String renderPath){
        combo.setRenderBindPath(renderPath);
    }
    
    /**
     * 内部コンボのレンダーバインドパスを返します。
     * @return
     */
    public String getComboRenderBindPath(){
        return combo.getRenderBindPath();
    }
    
}
