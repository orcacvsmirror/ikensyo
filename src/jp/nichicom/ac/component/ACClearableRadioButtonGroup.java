package jp.nichicom.ac.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import javax.swing.JRadioButton;
import javax.swing.event.ListSelectionEvent;
/**
 * �N���A�{�^���Ή��̃��W�I�O���[�v�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractACClearableRadioButtonGroup
 */
public class ACClearableRadioButtonGroup extends AbstractACClearableRadioButtonGroup {
  protected int offset = -1;
  protected int noSelectIndex = 0;
  private transient ArrayList actionListeners;

  /**
   * �R���X�g���N�^�ł��B
   */
  public ACClearableRadioButtonGroup(){
    super();
  }

  /**
   * �C���f�b�N�X�I�t�Z�b�g ��Ԃ��܂��B
   *
   * @return �C���f�b�N�X�I�t�Z�b�g
   */
  public int getOffset() {
      return offset;
  }

  /**
   * �C���f�b�N�X�I�t�Z�b�g ��ݒ肵�܂��B
   *
   * @param offset �C���f�b�N�X�I�t�Z�b�g
   */
  public void setOffset(int offset) {
      this.offset = offset;
  }

  public int getSelectedIndex() {
    int index = super.getSelectedIndex();
    if(index==-1){
      return getNoSelectIndex();
    }else{
      return index - getOffset();
    }
  }

  public void itemStateChanged(ItemEvent e) {
      int idx = radioButtons.indexOf(e.getSource());
      if (idx - getOffset() == getSelectedIndex()) {
          fireValueChanged(new ListSelectionEvent(this, idx, idx, false));
          fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                                          "itemStateChanged"));
      }
  }
  public JRadioButton getButton(int index){
    if (index == getNoSelectIndex()) {
      index = -1;
    }
    else {
      index += getOffset();
    }
    return super.getButton(index);
  }

  public void setSelectedIndex(int selectedIndex) {
    if(selectedIndex==getNoSelectIndex()){
      selectedIndex = -1;
    }else{
      selectedIndex += getOffset();
    }
    super.setSelectedIndex(selectedIndex);
  }

  /**
   * ��I�����̃C���f�b�N�X��Ԃ��܂��B
   * @return ��I�����̃C���f�b�N�X
   */
  public int getNoSelectIndex() {
    return noSelectIndex;
  }
  /**
   * ��I�����̃C���f�b�N�X��ݒ肵�܂��B
   * @param noSelectIndex ��I�����̃C���f�b�N�X
   */
  public void setNoSelectIndex(int noSelectIndex) {
    this.noSelectIndex = noSelectIndex;
  }

  /**
   * �A�N�V�������X�i�����O���܂��B
   * @param l �A�N�V�������X�i
   */
  public synchronized void removeActionListener(ActionListener l) {
    if (actionListeners != null && actionListeners.contains(l)) {
      ArrayList v = (ArrayList) actionListeners.clone();
      v.remove(l);
      actionListeners = v;
    }
  }
  /**
   * �A�N�V�������X�i��ǉ����܂��B
   * @param l �A�N�V�������X�i
   */
  public synchronized void addActionListener(ActionListener l) {
    ArrayList v = actionListeners == null ? new ArrayList(2) : (ArrayList) actionListeners.clone();
    if (!v.contains(l)) {
      v.add(l);
      actionListeners = v;
    }
  }
  /**
   * �A�N�V�����C�x���g�𔭍s���܂��B
   * @param e �A�N�V�����C�x���g���
   */
  protected void fireActionPerformed(ActionEvent e) {
    if (actionListeners != null) {
      ArrayList listeners = actionListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        ((ActionListener) listeners.get(i)).actionPerformed(e);
      }
    }
  }
}
