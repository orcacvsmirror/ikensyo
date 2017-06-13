package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoRyuiShiji extends IkenshoHoumonKangoShijishoRyuiShiji {

    private IkenshoHoumonKangoShijishoInstructContainer seikatuRizumu = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    private IkenshoHoumonKangoShijishoInstructContainer kajiNouryoku = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    private IkenshoHoumonKangoShijishoInstructContainer taijinKankei = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    private IkenshoHoumonKangoShijishoInstructContainer syakaiShigen = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    private IkenshoHoumonKangoShijishoInstructContainer yakubutuRyouhou = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    
    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoRyuiShiji() {
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
        // 精神訪問看護に関する留意事項及び指示事項
        this.setLayout(new VRLayout());
        this.add(getRyuuiShijiTitle(), VRLayout.NORTH);
        this.add(getRyuiShijiGrp(), VRLayout.CLIENT);
        getRyuuiShijiTitle().setText("精神訪問看護に関する留意事項及び指示事項");
        getRyuiShijiGrp().setLayout(new VRLayout());
        getRyuiShijiGrp().add(seikatuRizumu, VRLayout.NORTH);
        getRyuiShijiGrp().add(kajiNouryoku, VRLayout.NORTH);
        getRyuiShijiGrp().add(taijinKankei, VRLayout.NORTH);
        getRyuiShijiGrp().add(syakaiShigen, VRLayout.NORTH);
        getRyuiShijiGrp().add(yakubutuRyouhou, VRLayout.NORTH);
        // 生活リズムの確立
        seikatuRizumu.setCheckBindPath("SEIKATU_RIZUMU_UMU");
        seikatuRizumu.setCheckText("１．生活リズムの確立(現在 {2}文字 {3}行)");
        seikatuRizumu.setPageBreakLimitProperty(151, 4);
        seikatuRizumu.setMaxLength(250);
        seikatuRizumu.setMaxRows(5);
        seikatuRizumu.setColumns(100);
        seikatuRizumu.setCode(IkenshoCommon.TEIKEI_SEIKATU_RIZUMU);
        seikatuRizumu.setShowSelectMnemonic('C');
        seikatuRizumu.setShowSelectText("選択(C)");
        seikatuRizumu.setCaption("生活リズムの確立");
        seikatuRizumu.setTextBindPath("SEIKATU_RIZUMU");
        seikatuRizumu.setRows(4);
        seikatuRizumu.fitTextArea();
        // 家事能力、社会技能等の獲得
        kajiNouryoku.setCheckBindPath("KAJI_NOURYOKU_UMU");
        kajiNouryoku.setCheckText("２．家事能力、社会技能等の獲得(現在 {2}文字 {3}行)");
        kajiNouryoku.setPageBreakLimitProperty(151, 4);
        kajiNouryoku.setMaxLength(250);
        kajiNouryoku.setMaxRows(5);
        kajiNouryoku.setColumns(100);
        kajiNouryoku.setCode(IkenshoCommon.TEIKEI_KAJI_NOURYOKU);
        kajiNouryoku.setShowSelectMnemonic('D');
        kajiNouryoku.setShowSelectText("選択(D)");
        kajiNouryoku.setCaption("家事能力、社会技能等の獲得");
        kajiNouryoku.setTextBindPath("KAJI_NOURYOKU");
        kajiNouryoku.setRows(4);
        kajiNouryoku.fitTextArea();
        // 対人関係の改善（家族含む）
        taijinKankei.setCheckBindPath("TAIJIN_KANKEI_UMU");
        taijinKankei.setCheckText("３．対人関係の改善（家族含む）(現在 {2}文字 {3}行)");
        taijinKankei.setPageBreakLimitProperty(151, 4);
        taijinKankei.setMaxLength(250);
        taijinKankei.setMaxRows(5);
        taijinKankei.setColumns(100);
        taijinKankei.setCode(IkenshoCommon.TEIKEI_TAIJIN_KANKEI);
        taijinKankei.setShowSelectMnemonic('E');
        taijinKankei.setShowSelectText("選択(E)");
        taijinKankei.setCaption("対人関係の改善（家族含む）");
        taijinKankei.setTextBindPath("TAIJIN_KANKEI");
        taijinKankei.setRows(4);
        taijinKankei.fitTextArea();
        // 社会資源活用の支援
        syakaiShigen.setCheckBindPath("SYAKAI_SHIGEN_UMU");
        syakaiShigen.setCheckText("４．社会資源活用の支援(現在 {2}文字 {3}行)");
        syakaiShigen.setPageBreakLimitProperty(151, 4);
        syakaiShigen.setMaxLength(250);
        syakaiShigen.setMaxRows(5);
        syakaiShigen.setColumns(100);
        syakaiShigen.setCode(IkenshoCommon.TEIKEI_SYAKAI_SHIGEN);
        syakaiShigen.setShowSelectMnemonic('G');
        syakaiShigen.setShowSelectText("選択(G)");
        syakaiShigen.setCaption("社会資源活用の支援");
        syakaiShigen.setTextBindPath("SYAKAI_SHIGEN");
        syakaiShigen.setRows(4);
        syakaiShigen.fitTextArea();
        // 薬物療法継続への援助
        yakubutuRyouhou.setCheckBindPath("YAKUBUTU_RYOUHOU_UMU");
        yakubutuRyouhou.setCheckText("５．薬物療法継続への援助(現在 {2}文字 {3}行)");
        yakubutuRyouhou.setPageBreakLimitProperty(151, 4);
        yakubutuRyouhou.setMaxLength(250);
        yakubutuRyouhou.setMaxRows(5);
        yakubutuRyouhou.setColumns(100);
        yakubutuRyouhou.setCode(IkenshoCommon.TEIKEI_YAKUBUTU_RYOUHOU);
        yakubutuRyouhou.setShowSelectMnemonic('H');
        yakubutuRyouhou.setShowSelectText("選択(H)");
        yakubutuRyouhou.setCaption("薬物療法継続への援助");
        yakubutuRyouhou.setTextBindPath("YAKUBUTU_RYOUHOU");
        yakubutuRyouhou.setRows(4);
        yakubutuRyouhou.fitTextArea();
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
        setWarningMessageText(owner, seikatuRizumu, "生活リズムの確立");
        setWarningMessageText(owner, kajiNouryoku, "家事能力、社会技能等の獲得");
        setWarningMessageText(owner, taijinKankei, "対人関係の改善（家族含む）");
        setWarningMessageText(owner, syakaiShigen, "社会資源活用の支援");
        setWarningMessageText(owner, yakubutuRyouhou, "薬物療法継続への援助");
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