package jp.or.med.orca.ikensho.component;

import jp.nichicom.vr.component.VRLabel;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoDocumentTabTitleLabel extends VRLabel {
  public IkenshoDocumentTabTitleLabel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setForeground(IkenshoConstants.COLOR_PANEL_TITLE_FOREGROUND);
    this.setBackground(IkenshoConstants.COLOR_PANEL_TITLE_BACKGROUND);
    this.setOpaque(true);
  }

}
