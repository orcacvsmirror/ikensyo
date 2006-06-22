package jp.or.med.orca.ikensho.component;

import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSpecialSijiContainer extends VRPanel {
  private VRLayout layout = new VRLayout();
  public IkenshoSpecialSijiContainer() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    layout.setHgap(0);
    layout.setVgap(0);
    layout.setAutoWrap(false);
    this.setLayout(layout);
    this.setBackground(IkenshoConstants.COLOR_RANGE_PANEL_BACKGROUND);
  }

}
