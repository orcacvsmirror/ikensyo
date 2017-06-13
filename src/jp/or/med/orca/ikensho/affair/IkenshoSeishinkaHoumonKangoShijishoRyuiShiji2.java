package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoRyuiShiji2 extends IkenshoHoumonKangoShijishoRyuiShiji {
    
    private IkenshoHoumonKangoShijishoInstructContainer shintaiGappei = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    private IkenshoHoumonKangoShijishoInstructContainer other = 
            new IkenshoHoumonKangoShijishoInstructContainer();
    
    /**
     * �R���X�g���N�^
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
     * ��������
     */
    private void jbInit() throws Exception {
        // ���_�K��Ō�Ɋւ��闯�ӎ����y�юw�������i�����j
        this.setLayout(new VRLayout());
        this.add(getRyuuiShijiTitle(), VRLayout.NORTH);
        this.add(getRyuiShijiGrp(), VRLayout.CLIENT); 
        getRyuuiShijiTitle().setText("���_�K��Ō�Ɋւ��闯�ӎ����y�юw�������i�����j");
        getRyuiShijiGrp().setLayout(new VRLayout());
        getRyuiShijiGrp().add(shintaiGappei, VRLayout.NORTH);
        getRyuiShijiGrp().add(other, VRLayout.NORTH);
        // �g�̍����ǂ̔��ǁE�����̖h�~
        shintaiGappei.setCheckBindPath("SHINTAI_GAPPEISYO_UMU");
        shintaiGappei.setCheckText("�U�D�g�̍����ǂ̔��ǁE�����̖h�~(���� {2}���� {3}�s)");
        shintaiGappei.setPageBreakLimitProperty(151, 4);
        shintaiGappei.setMaxLength(250);
        shintaiGappei.setMaxRows(5);
        shintaiGappei.setColumns(100);
        shintaiGappei.setCode(IkenshoCommon.TEIKEI_SHINTAI_GAPPEISYO);
        shintaiGappei.setShowSelectMnemonic('C');
        shintaiGappei.setShowSelectText("�I��(C)");
        shintaiGappei.setCaption("�g�̍����ǂ̔��ǁE�����̖h�~");
        shintaiGappei.setTextBindPath("SHINTAI_GAPPEISYO");
        shintaiGappei.setRows(4);
        shintaiGappei.fitTextArea();
        // ���̑�
        other.setCheckBindPath("SEISHIN_OTHER_UMU");
        other.setCheckText("�V�D���̑�(���� {2}���� {3}�s)");
        other.setPageBreakLimitProperty(151, 4);
        other.setMaxLength(250);
        other.setMaxRows(5);
        other.setColumns(100);
        other.setCode(IkenshoCommon.TEIKEI_SEISHIN_OTHER);
        other.setShowSelectMnemonic('D');
        other.setShowSelectText("�I��(D)");
        other.setCaption("���̑�");
        other.setTextBindPath("SEISHIN_OTHER");
        other.setRows(4);
        other.fitTextArea();
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
        setWarningMessageText(owner, shintaiGappei, "�g�̍����ǂ̔��ǁE�����̖h�~");
        setWarningMessageText(owner, other, "���̑�");
        
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