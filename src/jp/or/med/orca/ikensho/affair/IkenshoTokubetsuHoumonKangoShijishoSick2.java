package jp.or.med.orca.ikensho.affair;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
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
     * ��ʍ\�z����
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
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
