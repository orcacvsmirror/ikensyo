package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoRyuiShiji2 extends IkenshoHoumonKangoShijishoRyuiShiji {
    
    private IkenshoHoumonKangoShijishoInstructContainer shintaiGappei = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    private IkenshoHoumonKangoShijishoInstructContainer other = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    
    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoRyuiShiji2() {
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
        // 精神訪問看護に関する留意事項及び指示事項（続き）
        this.setLayout(new VRLayout());
        this.add(getRyuuiShijiTitle(), VRLayout.NORTH);
        this.add(getRyuiShijiGrp(), VRLayout.CLIENT); 
        getRyuuiShijiTitle().setText("精神訪問看護に関する留意事項及び指示事項（続き）");
        getRyuiShijiGrp().setLayout(new VRLayout());
        getRyuiShijiGrp().add(shintaiGappei, VRLayout.NORTH);
        getRyuiShijiGrp().add(other, VRLayout.NORTH);
        // 身体合併症の発症・悪化の防止
        shintaiGappei.setCheckBindPath("SHINTAI_GAPPEISYO_UMU");
        shintaiGappei.setCheckText("６．身体合併症の発症・悪化の防止(現在 {2}文字 {3}行)");
        shintaiGappei.setPageBreakLimitProperty(151, 4);
        shintaiGappei.setMaxLength(250);
        shintaiGappei.setMaxRows(5);
        shintaiGappei.setColumns(100);
        shintaiGappei.setCode(IkenshoCommon.TEIKEI_SHINTAI_GAPPEISYO);
        shintaiGappei.setShowSelectMnemonic('C');
        shintaiGappei.setShowSelectText("選択(C)");
        shintaiGappei.setCaption("身体合併症の発症・悪化の防止");
        shintaiGappei.setTextBindPath("SHINTAI_GAPPEISYO");
        shintaiGappei.setRows(4);
        shintaiGappei.fitTextArea();
        // その他
        other.setCheckBindPath("SEISHIN_OTHER_UMU");
        other.setCheckText("７．その他(現在 {2}文字 {3}行)");
        other.setPageBreakLimitProperty(151, 4);
        other.setMaxLength(250);
        other.setMaxRows(5);
        other.setColumns(100);
        other.setCode(IkenshoCommon.TEIKEI_SEISHIN_OTHER);
        other.setShowSelectMnemonic('D');
        other.setShowSelectText("選択(D)");
        other.setCaption("その他");
        other.setTextBindPath("SEISHIN_OTHER");
        other.setRows(4);
        other.fitTextArea();
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
        setWarningMessageText(owner, shintaiGappei, "身体合併症の発症・悪化の防止");
        setWarningMessageText(owner, other, "その他");
        
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