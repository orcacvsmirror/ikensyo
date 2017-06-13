package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoTokubetsuHoumonKangoShijishoRyuiShiji extends
        IkenshoHoumonKangoShijishoRyuiShiji {
    protected ACGroupBox tokubetsuGroup;
    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuRyuijiko;

    public IkenshoTokubetsuHoumonKangoShijishoRyuiShiji() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit()throws Exception{
        
        getRyuuiShijiTitle().setText("���ӎ����y�юw������");
        getRyuiShijiGrp().setVisible(false);
        
        
        getTokubetsuRyuijiko().setColumns(100);
        getTokubetsuRyuijiko().setRows(14);
        getTokubetsuRyuijiko().setMaxRows(14);
        getTokubetsuRyuijiko().setMaxLength(700);
        getTokubetsuRyuijiko().fitTextArea();
        getTokubetsuRyuijiko().setShowSelectVisible(false);
        getTokubetsuRyuijiko().setCheckVisible(false);
        getTokubetsuRyuijiko().setTextBindPath("TOKUBETSU_RYUI");
        getTokubetsuRyuijiko().setCaption("���ӎ����y�юw������");
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace begin �y��Q�z���ʎw�����̒��[�󎚕����Ɍ뎚  
//        getTokubetsuRyuijiko().setTitle(
//                "���ӎ����y�юw�������i700����/14�s�ȓ��j" + IkenshoConstants.LINE_SEPARATOR
//                        + "�i���F�_�H���˖�̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_������΋L�ڂ��Ă��������B�j");
        getTokubetsuRyuijiko().setTitle(
                "���ӎ����y�юw�������i700����/14�s�ȓ��j" + IkenshoConstants.LINE_SEPARATOR
                        + "�i���F�_�H���˖�̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_������΋L�ڂ��Ă��������B�j");
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace end �y��Q�z���ʎw�����̒��[�󎚕����Ɍ뎚  
        
        getTokubetsuGroup().add(getTokubetsuRyuijiko(), VRLayout.NORTH);
        this.add(getTokubetsuGroup(), VRLayout.CLIENT);
    }

    /**
     * tokubetsuShusoGroup ��Ԃ��܂��B
     * @return tokubetsuShusoGroup
     */
    protected ACGroupBox getTokubetsuGroup() {
        if(tokubetsuGroup==null){
            tokubetsuGroup = new ACGroupBox();
        }
        return tokubetsuGroup;
    }

    /**
     * tokubetsuRyuijiko ��Ԃ��܂��B
     * @return tokubetsuRyuijiko
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuRyuijiko() {
        if(tokubetsuRyuijiko==null){
            tokubetsuRyuijiko = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuRyuijiko;
    }
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuRyuijiko().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "SIJI_TOKKI",
                "0",
                "�Ǎ�(L)",
                'L',
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//                "�ŐV�̖K��Ō�w�����ɓo�^�����u���L���ׂ����ӎ����v��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                "�ŐV�́i���_�ȁj�K��Ō�w�����ɓo�^�����u���L���ׂ����ӎ����v��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start
                        + "��낵���ł����H", true);
        
    }
    
}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
