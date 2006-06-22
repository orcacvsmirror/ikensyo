package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMindBody1
    extends IkenshoDocumentAffairMindBody1 {

  /**
   * ÉRÉìÉXÉgÉâÉNÉ^Ç≈Ç∑ÅB
   */
  public IkenshoIkenshoInfoMindBody1() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    getSyougaiJiritsuHelp().setFollowHideComponents(new JComponent[] {
        getRikaiKiokuGroup(), getMondaiGroup(), getShinkeiGroup()});
    getChihouJiritsuHelp().setFollowHideComponents(new JComponent[] {
        getRikaiKiokuGroup(), getMondaiGroup(), getShinkeiGroup()});
  }

  private void jbInit() throws Exception {
      getMindBody1SyougaiJiritsu().setModel(new VRListModelAdapter(new
              VRArrayList(Arrays.asList(new
                                        String[] {"é©óß", "ÇiÇP", "ÇiÇQ", "Ç`ÇP", "Ç`ÇQ", "ÇaÇP",
                                        "ÇaÇQ", "ÇbÇP", "ÇbÇQ"}))));
      getMindBody1ChihouJiritsu().setModel(new VRListModelAdapter(new
              VRArrayList(Arrays.asList(new
                                        String[] {"é©óß", "I", "IIÇÅ", "IIÇÇ", "IIIÇÅ", "IIIÇÇ",
                                        "IV", "Çl"}))));
    getRikaiKiokuGroup().setVisible(true);
    getJiritsuGroup().setVisible(true);
    getJiritsuGroup().setText("ì˙èÌê∂äàÇÃé©óßìxìôÇ…Ç¬Ç¢Çƒ");
    getTitle().setVisible(true);
    getMondaiGroup().setVisible(true);
    getShinkeiGroup().setVisible(true);
  }

}
