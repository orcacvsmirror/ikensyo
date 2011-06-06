package jp.or.med.orca.ikensho.affair;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
public class IkenshoTokubetsuHoumonKangoShijishoSick2 extends
        IkenshoHoumonKangoShijishoSick2 {

    public IkenshoTokubetsuHoumonKangoShijishoSick2() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 画面構築処理
     * @throws Exception
     */
    private void jbInit() throws Exception {
        getSickMedicineValueWarning().setText("");
    }
    protected void updateSickMedicineValueWarningText(int totalLineCount){
    }
    
    protected void showAlertOnSickProgressLengthOverWhenSaveOrPrint(){
    }
}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
