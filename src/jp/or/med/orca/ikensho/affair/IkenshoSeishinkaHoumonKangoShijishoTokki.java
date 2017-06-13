package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoTokki extends IkenshoHoumonKangoShijishoTenteki {

    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoTokki() {
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
        // タイトル変更
        getTitle().setText("特記すべき留意事項");
        getTokki().setTitle("特記すべき留意事項(現在 {2}文字 {3}行)");
        // 在宅患者訪問点滴注射に関する指示の非表示
        getTenteki().setVisible(false);
    }

    /**
     * 改頁項目設定処理
     * @return 改頁項目設定結果
     */
    public boolean noControlWarning() throws Exception {

        if (getMasterAffair() == null) {
            return true;
        }

        if (getMasterAffair().getCanUpdateCheckStatus() != IkenshoTabbableAffairContainer.CAN_UPDATE_CHECK_STATUS_PRINT) {
            return true;
        }

        if (!(getMasterAffair() instanceof IkenshoHoumonKangoShijisho)) {
            return true;
        }

        IkenshoHoumonKangoShijisho owner = (IkenshoHoumonKangoShijisho)getMasterAffair();
        setWarningMessageText(owner, getTokki(), "特記すべき留意事項");
        return true;
    }

    /**
     * 改頁項目設定処理（個別）
     */
    private void setWarningMessageText(
        IkenshoHoumonKangoShijisho owner,
        IkenshoHoumonKangoShijishoInstructContainer target,
        String contentName) {
        if (target.isPageBreak()) {
            owner.setWarningMessage(contentName);
        }
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End