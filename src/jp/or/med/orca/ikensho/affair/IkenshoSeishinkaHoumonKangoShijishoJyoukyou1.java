package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoJyoukyou1 extends IkenshoHoumonKangoShijishoSick2 {

    /**
     * �R���X�g���N�^
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
     * ��������
     */
    private void jbInit() throws Exception {
        getTitle().setText("���݂̏�");
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End