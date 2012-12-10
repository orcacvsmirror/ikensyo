package jp.nichicom.ac.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRComboBoxModelAdapter;

/**
 * コンボボックスモデル用のアダプタです。
 * <p>
 * 空行の自動追加に対応しています。
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
   * コンストラクタです。
   */
  public ACComboBoxModelAdapter() {
    super(new VRArrayList());
  }

  /**
   * コンストラクタです。
   *
   * @param adaptee アダプティーとなるバインドソース
   */
  public ACComboBoxModelAdapter(VRBindSource adaptee) {
    super(adaptee);
  }

  /**
   * 項目を追加します。
   * @param data 項目
   * @param dummy ダミー引数
   */
  public void add(Object data, Object dummy) {
    add(data);
  }

  /**
   * 項目を追加します。
   * @param data 項目
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
   * 未選択状態を表す番号であるかを返します。
   * @param index 比較対象
   * @return 未選択状態を表す番号であるか
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
   * 空選択項目の有無を考慮した仮想番号に変換して返します。
   * @param index 変換元
   * @return int 仮想番号
   */
  public int toVirtualIndex(int index) {
    if (isBlankable()) {
      index++;
    }
    return index;
  }

  /**
   * 空選択項目の有無を考慮した仮想未選択項目を返します。
   * @return 仮想未選択項目
   */
  public Object getVirtualBlankItem() {
    if (isBlankable()) {
      return getBlankItem();
    }
    return null;
  }

  /**
   * 空選択項目の有無を考慮した仮想番号を実データ番号に変換して返します。
   * @param index 仮想番号
   * @return int 実データ番号
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
   * 空選択を表す項目を返します。
   * @return 空選択を表す項目
   */
  public Object getBlankItem() {
    return blankItem;
  }

  /**
   * 空選択を表す項目を設定します。
   * @param blankable 空選択を表す項目
   */
  public void setBlankItem(Object blankable) {
    this.blankItem = blankable;
  }

  /**
   * 空選択を許可するかを返します。
   * @param blankable 空選択を許可するか
   */
  public void setBlankable(boolean blankable) {
    this.blankable = blankable;
  }

  /**
   * 空選択を許可するかを設定します。
   * @return 空選択を許可するか
   */
  public boolean isBlankable() {
    return blankable;
  }

}
