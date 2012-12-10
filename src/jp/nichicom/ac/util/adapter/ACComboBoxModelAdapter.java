package jp.nichicom.ac.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRComboBoxModelAdapter;

/**
 * �R���{�{�b�N�X���f���p�̃A�_�v�^�ł��B
 * <p>
 * ��s�̎����ǉ��ɑΉ����Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRComboBoxModelAdapter
 */

public class ACComboBoxModelAdapter
    extends VRComboBoxModelAdapter {
  private Object blankItem;
  private boolean blankable = false;

  /**
   * �R���X�g���N�^�ł��B
   */
  public ACComboBoxModelAdapter() {
    super(new VRArrayList());
  }

  /**
   * �R���X�g���N�^�ł��B
   *
   * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
   */
  public ACComboBoxModelAdapter(VRBindSource adaptee) {
    super(adaptee);
  }

  /**
   * ���ڂ�ǉ����܂��B
   * @param data ����
   * @param dummy �_�~�[����
   */
  public void add(Object data, Object dummy) {
    add(data);
  }

  /**
   * ���ڂ�ǉ����܂��B
   * @param data ����
   */
  public void add(Object data) {
    super.addData(data);
  }

  public Object getElementAt(int index) {
    Object obj = super.getElementAt(index);
    if (obj==null) {
        return "";
    }
    return obj;
  }

  /**
   * ���I����Ԃ�\���ԍ��ł��邩��Ԃ��܂��B
   * @param index ��r�Ώ�
   * @return ���I����Ԃ�\���ԍ��ł��邩
   */
  public boolean isDeselectIndex(int index) {
    if (isBlankable()) {
      if (index <= 0) {
        return true;
      }
    }
    if (index < 0) {
      return true;
    }
    return false;
  }

  /**
   * ��I�����ڂ̗L�����l���������z�ԍ��ɕϊ����ĕԂ��܂��B
   * @param index �ϊ���
   * @return int ���z�ԍ�
   */
  public int toVirtualIndex(int index) {
    if (isBlankable()) {
      index++;
    }
    return index;
  }

  /**
   * ��I�����ڂ̗L�����l���������z���I�����ڂ�Ԃ��܂��B
   * @return ���z���I������
   */
  public Object getVirtualBlankItem() {
    if (isBlankable()) {
      return getBlankItem();
    }
    return null;
  }

  /**
   * ��I�����ڂ̗L�����l���������z�ԍ������f�[�^�ԍ��ɕϊ����ĕԂ��܂��B
   * @param index ���z�ԍ�
   * @return int ���f�[�^�ԍ�
   */
  public int toRealIndex(int index) {
    if (isBlankable()) {
      index--;
    }
    return index;
  }

  public Object getData(int index) {
    if (isDeselectIndex(index)) {
      return getVirtualBlankItem();
    }
    return super.getData(toRealIndex(index));
  }

  public int getDataSize() {
    return toVirtualIndex(super.getDataSize());
  }

  public void setData(int index, Object data) {
    super.setData(toVirtualIndex(index), data);
  }

  /**
   * ��I����\�����ڂ�Ԃ��܂��B
   * @return ��I����\������
   */
  public Object getBlankItem() {
    return blankItem;
  }

  /**
   * ��I����\�����ڂ�ݒ肵�܂��B
   * @param blankable ��I����\������
   */
  public void setBlankItem(Object blankable) {
    this.blankItem = blankable;
  }

  /**
   * ��I���������邩��Ԃ��܂��B
   * @param blankable ��I���������邩
   */
  public void setBlankable(boolean blankable) {
    this.blankable = blankable;
  }

  /**
   * ��I���������邩��ݒ肵�܂��B
   * @return ��I���������邩
   */
  public boolean isBlankable() {
    return blankable;
  }

}
