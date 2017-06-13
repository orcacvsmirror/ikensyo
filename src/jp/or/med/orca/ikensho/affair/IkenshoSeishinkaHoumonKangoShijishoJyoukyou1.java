package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoJyoukyou1 extends IkenshoHoumonKangoShijishoSick2 {

    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoJyoukyou1() {
        super();
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初期処理
     */
    private void jbInit() throws Exception {
        getTitle().setText("現在の状況");
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End