package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoTokki extends IkenshoHoumonKangoShijishoTenteki {

    /**
     * �R���X�g���N�^
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
     * ��������
     */
    private void jbInit() throws Exception {
        // �^�C�g���ύX
        getTitle().setText("���L���ׂ����ӎ���");
        getTokki().setTitle("���L���ׂ����ӎ���(���� {2}���� {3}�s)");
        // �ݑ�ҖK��_�H���˂Ɋւ���w���̔�\��
        getTenteki().setVisible(false);
    }

    /**
     * ���ō��ڐݒ菈��
     * @return ���ō��ڐݒ茋��
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
        setWarningMessageText(owner, getTokki(), "���L���ׂ����ӎ���");
        return true;
    }

    /**
     * ���ō��ڐݒ菈���i�ʁj
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