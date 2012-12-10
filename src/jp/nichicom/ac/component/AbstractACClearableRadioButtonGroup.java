package jp.nichicom.ac.component;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRRadioButtonGroup;

/**
 * �N���A�{�^���Ή��̊�ꃉ�W�I�O���[�v�ł��B
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
   * �R���X�g���N�^�ł��B
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
   * �N���A�{�^�����R���e�i�ł��鎩�����g�ɒǉ����܂��B
   * <p>
   * template method pattern
   * </p>
   *
   * @param button �ǉ�����N���A�{�^��
   */
  protected void addClearButton(JButton button) {
    this.add(button, null);
  }

  /**
   * �N���A�{�^���̃c�[���`�b�v ��Ԃ��܂��B
   *
   * @return �N���A�{�^���̃c�[���`�b�v
   */
  public String getClearButtonToolTipText() {
    if(getClearButton()==null){
      return "";
    }
     return getClearButton().getToolTipText();
  }

  /**
   * �N���A�{�^���̃c�[���`�b�v ��ݒ肵�܂��B
   *
   * @param text �N���A�{�^���̃c�[���`�b�v
   */
  public void setClearButtonToolTipText(String text) {
    if(getClearButton()!=null){
      getClearButton().setToolTipText(text);
    }
  }

  /**
   * �N���A�{�^�����g�p���邩��Ԃ��܂��B
   * @return �N���A�{�^�����g�p���邩
   */
  public boolean isUseClearButton() {
    return useClearButton;
  }
  /**
   * �N���A�{�^�����g�p���邩��ݒ肵�܂��B
   * @param useClearButton �N���A�{�^�����g�p���邩
   */
  public void setUseClearButton(boolean useClearButton) {
    this.useClearButton = useClearButton;
    refreshRadioButton();
  }
  /**
   * �N���A�{�^����Ԃ��܂��B
   * @return �N���A�{�^��
   */
  public VRButton getClearButton() {
    if(clear==null){
      clear = new VRButton();
      clear.setMargin(new Insets(0, 0, 0, 0));
      clear.setText("�N���A");
      clear.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          setSelectedIndex( -1);
        }
      });
    }
    return clear;
  }


  /**
   * �ŏ��̎q�R���g���[���Ƀt�H�[�J�X���ڂ��܂��B
   */
  public void requestChildFocus(){
    if(getButtonCount()>0){
      super.getButton(0).requestFocus();
    }
  }

}
