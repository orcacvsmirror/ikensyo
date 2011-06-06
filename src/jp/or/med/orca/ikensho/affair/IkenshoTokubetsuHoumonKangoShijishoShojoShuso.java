package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

//[ID:0000514][Masahiko Higuchi] 2009/09/08 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
public class IkenshoTokubetsuHoumonKangoShijishoShojoShuso extends
        IkenshoTabbableChildAffairContainer {

    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuShuso;
    protected ACGroupBox tokubetsuShusoGroup;
    protected IkenshoDocumentTabTitleLabel shojoShusoTitle;
    
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * ��ʍ\�z����
     * 
     * @throws Exception
     */
    private void jbInit()throws Exception{
        this.setLayout(new VRLayout());
        getShojoShusoTitle().setText("�Ǐ�E��i");
        
        
        getTokubetsuShuso().setColumns(100);
        getTokubetsuShuso().setRows(15);
        getTokubetsuShuso().setMaxRows(14);
        getTokubetsuShuso().setMaxLength(700);
        getTokubetsuShuso().setShowSelectVisible(false);
        getTokubetsuShuso().setCheckVisible(false);
        getTokubetsuShuso().setTextBindPath("TOKUBETSU_SHOJO_SHUSO");
        getTokubetsuShuso().setCaption("�Ǐ�E��i");
        getTokubetsuShuso().setTitle("�Ǐ�E��i�i700����/14�s�ȓ��j");
        
        getTokubetsuShusoGroup().add(getTokubetsuShuso(), VRLayout.NORTH);
        this.add(getShojoShusoTitle(), VRLayout.NORTH);
        this.add(getTokubetsuShusoGroup(), VRLayout.CLIENT);
    }
    
    /**
     * tokubetsuShuso ��Ԃ��܂��B
     * @return tokubetsuShuso
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuShuso() {
        if(tokubetsuShuso==null){
            tokubetsuShuso = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuShuso;
    }
    
    /**
     * tokubetsuShusoGroup ��Ԃ��܂��B
     * @return tokubetsuShusoGroup
     */
    protected ACGroupBox getTokubetsuShusoGroup() {
        if(tokubetsuShusoGroup==null){
            tokubetsuShusoGroup = new ACGroupBox();
        }
        return tokubetsuShusoGroup;
    }
    
    /**
     * ryuuiShijiTitle ��Ԃ��܂��B
     * 
     * @return ryuuiShijiTitle
     */
    protected IkenshoDocumentTabTitleLabel getShojoShusoTitle() {
        if (shojoShusoTitle == null) {
            shojoShusoTitle = new IkenshoDocumentTabTitleLabel();
        }
        return shojoShusoTitle;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuShuso().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "MT_STS",
                "0",
                "�Ǎ�(L)",
                'L',
                "�ŐV�̖K��Ō�w�����ɓo�^�����u�Ǐ�E���Ï�ԁv��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                        + "��낵���ł����H", true);
        
    }

}
//[ID:0000514][Masahiko Higuchi] 2009/09/08 add end