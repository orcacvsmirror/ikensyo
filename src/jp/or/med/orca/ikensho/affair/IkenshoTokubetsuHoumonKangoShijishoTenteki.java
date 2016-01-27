package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoTokubetsuHoumonKangoShijishoTenteki extends
        IkenshoHoumonKangoShijishoTenteki {
    protected ACGroupBox tokubetsuGroup;
    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuTenteki;

    public IkenshoTokubetsuHoumonKangoShijishoTenteki() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void jbInit()throws Exception{
        
        getTitle().setText("�_�H���ˎw�����e�i���^��܁E���^�ʁE���^���@���j");
        getGroup().setVisible(false);
        
        
        getTokubetsuTenteki().setColumns(100);
        getTokubetsuTenteki().setRows(14);
        getTokubetsuTenteki().setMaxRows(14);
        getTokubetsuTenteki().setMaxLength(700);
        getTokubetsuTenteki().setShowSelectVisible(false);
        getTokubetsuTenteki().setCheckVisible(false);
        getTokubetsuTenteki().setTextBindPath("TOKUBETSU_CHUSHA_SHIJI");
        getTokubetsuTenteki().setCaption("�_�H���ˎw�����e");
        getTokubetsuTenteki().setTitle("�_�H���ˎw�����e�i���^��܁E���^�ʁE���^���@���j�i700����/14�s�ȓ��j");
        getTokubetsuTenteki().fitTextArea();
        getTokubetsuGroup().add(getTokubetsuTenteki(), VRLayout.NORTH);
        this.add(getTokubetsuGroup(), VRLayout.CLIENT);
    }

    /**
     * tokubetsuGroup ��Ԃ��܂��B
     * @return tokubetsuGroup
     */
    protected ACGroupBox getTokubetsuGroup() {
        if(tokubetsuGroup==null){
            tokubetsuGroup = new ACGroupBox();
        }
        return tokubetsuGroup;
    }

    /**
     * tokubetsuTenteki ��Ԃ��܂��B
     * @return tokubetsuTenteki
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuTenteki() {
        if(tokubetsuTenteki==null){
            tokubetsuTenteki = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuTenteki;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuTenteki().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TENTEKI_SIJI",
                "0",
                "�Ǎ�(L)",
                'L',
                "�ŐV�̖K��Ō�w�����ɓo�^�����u�ݑ�ҖK��_�H���˂Ɋւ���w���v��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                        + "��낵���ł����H", true);
        
    }
}
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
