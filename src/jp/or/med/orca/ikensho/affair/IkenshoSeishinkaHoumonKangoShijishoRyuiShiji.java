package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
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
     * �R���X�g���N�^
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
     * ��������
     */
    private void jbInit() throws Exception {
        // ���_�K��Ō�Ɋւ��闯�ӎ����y�юw������
        this.setLayout(new VRLayout());
        this.add(getRyuuiShijiTitle(), VRLayout.NORTH);
        this.add(getRyuiShijiGrp(), VRLayout.CLIENT);
        getRyuuiShijiTitle().setText("���_�K��Ō�Ɋւ��闯�ӎ����y�юw������");
        getRyuiShijiGrp().setLayout(new VRLayout());
        getRyuiShijiGrp().add(seikatuRizumu, VRLayout.NORTH);
        getRyuiShijiGrp().add(kajiNouryoku, VRLayout.NORTH);
        getRyuiShijiGrp().add(taijinKankei, VRLayout.NORTH);
        getRyuiShijiGrp().add(syakaiShigen, VRLayout.NORTH);
        getRyuiShijiGrp().add(yakubutuRyouhou, VRLayout.NORTH);
        // �������Y���̊m��
        seikatuRizumu.setCheckBindPath("SEIKATU_RIZUMU_UMU");
        seikatuRizumu.setCheckText("�P�D�������Y���̊m��(���� {2}���� {3}�s)");
        seikatuRizumu.setPageBreakLimitProperty(151, 4);
        seikatuRizumu.setMaxLength(250);
        seikatuRizumu.setMaxRows(5);
        seikatuRizumu.setColumns(100);
        seikatuRizumu.setCode(IkenshoCommon.TEIKEI_SEIKATU_RIZUMU);
        seikatuRizumu.setShowSelectMnemonic('C');
        seikatuRizumu.setShowSelectText("�I��(C)");
        seikatuRizumu.setCaption("�������Y���̊m��");
        seikatuRizumu.setTextBindPath("SEIKATU_RIZUMU");
        seikatuRizumu.setRows(4);
        seikatuRizumu.fitTextArea();
        // �Ǝ��\�́A�Љ�Z�\���̊l��
        kajiNouryoku.setCheckBindPath("KAJI_NOURYOKU_UMU");
        kajiNouryoku.setCheckText("�Q�D�Ǝ��\�́A�Љ�Z�\���̊l��(���� {2}���� {3}�s)");
        kajiNouryoku.setPageBreakLimitProperty(151, 4);
        kajiNouryoku.setMaxLength(250);
        kajiNouryoku.setMaxRows(5);
        kajiNouryoku.setColumns(100);
        kajiNouryoku.setCode(IkenshoCommon.TEIKEI_KAJI_NOURYOKU);
        kajiNouryoku.setShowSelectMnemonic('D');
        kajiNouryoku.setShowSelectText("�I��(D)");
        kajiNouryoku.setCaption("�Ǝ��\�́A�Љ�Z�\���̊l��");
        kajiNouryoku.setTextBindPath("KAJI_NOURYOKU");
        kajiNouryoku.setRows(4);
        kajiNouryoku.fitTextArea();
        // �ΐl�֌W�̉��P�i�Ƒ��܂ށj
        taijinKankei.setCheckBindPath("TAIJIN_KANKEI_UMU");
        taijinKankei.setCheckText("�R�D�ΐl�֌W�̉��P�i�Ƒ��܂ށj(���� {2}���� {3}�s)");
        taijinKankei.setPageBreakLimitProperty(151, 4);
        taijinKankei.setMaxLength(250);
        taijinKankei.setMaxRows(5);
        taijinKankei.setColumns(100);
        taijinKankei.setCode(IkenshoCommon.TEIKEI_TAIJIN_KANKEI);
        taijinKankei.setShowSelectMnemonic('E');
        taijinKankei.setShowSelectText("�I��(E)");
        taijinKankei.setCaption("�ΐl�֌W�̉��P�i�Ƒ��܂ށj");
        taijinKankei.setTextBindPath("TAIJIN_KANKEI");
        taijinKankei.setRows(4);
        taijinKankei.fitTextArea();
        // �Љ�����p�̎x��
        syakaiShigen.setCheckBindPath("SYAKAI_SHIGEN_UMU");
        syakaiShigen.setCheckText("�S�D�Љ�����p�̎x��(���� {2}���� {3}�s)");
        syakaiShigen.setPageBreakLimitProperty(151, 4);
        syakaiShigen.setMaxLength(250);
        syakaiShigen.setMaxRows(5);
        syakaiShigen.setColumns(100);
        syakaiShigen.setCode(IkenshoCommon.TEIKEI_SYAKAI_SHIGEN);
        syakaiShigen.setShowSelectMnemonic('G');
        syakaiShigen.setShowSelectText("�I��(G)");
        syakaiShigen.setCaption("�Љ�����p�̎x��");
        syakaiShigen.setTextBindPath("SYAKAI_SHIGEN");
        syakaiShigen.setRows(4);
        syakaiShigen.fitTextArea();
        // �򕨗Ö@�p���ւ̉���
        yakubutuRyouhou.setCheckBindPath("YAKUBUTU_RYOUHOU_UMU");
        yakubutuRyouhou.setCheckText("�T�D�򕨗Ö@�p���ւ̉���(���� {2}���� {3}�s)");
        yakubutuRyouhou.setPageBreakLimitProperty(151, 4);
        yakubutuRyouhou.setMaxLength(250);
        yakubutuRyouhou.setMaxRows(5);
        yakubutuRyouhou.setColumns(100);
        yakubutuRyouhou.setCode(IkenshoCommon.TEIKEI_YAKUBUTU_RYOUHOU);
        yakubutuRyouhou.setShowSelectMnemonic('H');
        yakubutuRyouhou.setShowSelectText("�I��(H)");
        yakubutuRyouhou.setCaption("�򕨗Ö@�p���ւ̉���");
        yakubutuRyouhou.setTextBindPath("YAKUBUTU_RYOUHOU");
        yakubutuRyouhou.setRows(4);
        yakubutuRyouhou.fitTextArea();
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
        setWarningMessageText(owner, seikatuRizumu, "�������Y���̊m��");
        setWarningMessageText(owner, kajiNouryoku, "�Ǝ��\�́A�Љ�Z�\���̊l��");
        setWarningMessageText(owner, taijinKankei, "�ΐl�֌W�̉��P�i�Ƒ��܂ށj");
        setWarningMessageText(owner, syakaiShigen, "�Љ�����p�̎x��");
        setWarningMessageText(owner, yakubutuRyouhou, "�򕨗Ö@�p���ւ̉���");
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