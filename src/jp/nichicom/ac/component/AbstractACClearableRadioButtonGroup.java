package jp.nichicom.ac.component;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRRadioButtonGroup;

/**
 * クリアボタン対応の基底ラジオグループです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRRadioButtonGroup
 */
public class AbstractACClearableRadioButtonGroup extends VRRadioButtonGroup {
  protected boolean useClearButton = true;
  private VRButton clear;

  /**
   * コンストラクタです。
   */
  public AbstractACClearableRadioButtonGroup(){
    super();
  }

  public void refreshRadioButton() {
    super.refreshRadioButton();
    if(getClearButton()!=null){
      if(isUseClearButton()){
        addClearButton(getClearButton());
      }else{
        this.remove(getClearButton());
      }
      revalidate();
    }
  }

  /**
   * クリアボタンをコンテナである自分自身に追加します。
   * <p>
   * template method pattern
   * </p>
   *
   * @param button 追加するクリアボタン
   */
  protected void addClearButton(JButton button) {
    this.add(button, null);
  }

  /**
   * クリアボタンのツールチップ を返します。
   *
   * @return クリアボタンのツールチップ
   */
  public String getClearButtonToolTipText() {
    if(getClearButton()==null){
      return "";
    }
     return getClearButton().getToolTipText();
  }

  /**
   * クリアボタンのツールチップ を設定します。
   *
   * @param text クリアボタンのツールチップ
   */
  public void setClearButtonToolTipText(String text) {
    if(getClearButton()!=null){
      getClearButton().setToolTipText(text);
    }
  }

  /**
   * クリアボタンを使用するかを返します。
   * @return クリアボタンを使用するか
   */
  public boolean isUseClearButton() {
    return useClearButton;
  }
  /**
   * クリアボタンを使用するかを設定します。
   * @param useClearButton クリアボタンを使用するか
   */
  public void setUseClearButton(boolean useClearButton) {
    this.useClearButton = useClearButton;
    refreshRadioButton();
  }
  /**
   * クリアボタンを返します。
   * @return クリアボタン
   */
  public VRButton getClearButton() {
    if(clear==null){
      clear = new VRButton();
      clear.setMargin(new Insets(0, 0, 0, 0));
      clear.setText("クリア");
      clear.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          setSelectedIndex( -1);
        }
      });
    }
    return clear;
  }


  /**
   * 最初の子コントロールにフォーカスを移します。
   */
  public void requestChildFocus(){
    if(getButtonCount()>0){
      super.getButton(0).requestFocus();
    }
  }

}
