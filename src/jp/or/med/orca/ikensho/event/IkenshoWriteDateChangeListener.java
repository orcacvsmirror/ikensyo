package jp.or.med.orca.ikensho.event;

import java.util.EventObject;


/** TODO <HEAD_IKENSYO> */
public interface IkenshoWriteDateChangeListener {
  /**
   * 記入日が変更された際に呼ばれるイベントです。
   * @param e イベント情報
   */
  void writeDataChanged(EventObject e);
}
