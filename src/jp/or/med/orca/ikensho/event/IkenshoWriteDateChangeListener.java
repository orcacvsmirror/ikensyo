package jp.or.med.orca.ikensho.event;

import java.util.EventObject;


/** TODO <HEAD_IKENSYO> */
public interface IkenshoWriteDateChangeListener {
  /**
   * �L�������ύX���ꂽ�ۂɌĂ΂��C�x���g�ł��B
   * @param e �C�x���g���
   */
  void writeDataChanged(EventObject e);
}
