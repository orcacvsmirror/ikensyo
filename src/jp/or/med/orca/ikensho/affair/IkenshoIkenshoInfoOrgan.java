package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoOrgan extends IkenshoDocumentAffairOrgan {
    private VRArrayList jigyoushas;

    private ACTextField organBankAccountNo = new ACTextField();
    private ACTextField organDoctorNo = new ACTextField();
    private ACLabelContainer organBankAccountTypes = new ACLabelContainer();
    private ACTextField organBankUser = new ACTextField();
    private VRLayout organBankLayout = new VRLayout();
    private ACLabelContainer organBankAccountNos = new ACLabelContainer();
    private ACLabelContainer organBankUsers = new ACLabelContainer();
    private ACLabelContainer organOpeners = new ACLabelContainer();
    private ACTextField organBankAccountType = new ACTextField();
    // private VRComboBox organJigyoushoNo = new VRComboBox();
    private IkenshoReadOnlyCombo organJigyoushoNo = new IkenshoReadOnlyCombo();
    private ACGroupBox organBankGroup = new ACGroupBox();
    private ACTextField organBankOffice = new ACTextField();
    private ACTextField organOpener = new ACTextField();
    private ACLabelContainer organJigyoushoNos = new ACLabelContainer();
    private ACLabelContainer organBankNames = new ACLabelContainer();
    private VRLabel organJigyoushoNoAbstraction = new VRLabel();
    private VRLabel organBankOfficeNameSpacer = new VRLabel();
    private ACTextField organType = new ACTextField();
    private ACParentHesesPanelContainer organDoctorNos = new ACParentHesesPanelContainer();
    private ACLabelContainer organTypes = new ACLabelContainer();
    private ACLabelContainer organBankOffices = new ACLabelContainer();
    private VRLabel organTypeSpacer = new VRLabel();
    private ACTextField organBankName = new ACTextField();
    private VRLayout organJigyoushoNosLayout = new VRLayout();

    private Double defaultTax = new Double(0);
    private VRLabel organJigyoushoNoRegistAbstraction1 = new VRLabel();
    private VRPanel organJigyoushoNoAbstractions = new VRPanel();
    private VRLabel organJigyoushoNoRegistAbstraction2 = new VRLabel();

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" TAX");
        VRArrayList arrays = (VRArrayList) dbm.executeQuery(sb.toString());
        if (arrays.getDataSize() > 0) {
            defaultTax = Double.valueOf(String.valueOf(VRBindPathParser.get(
                    "TAX", (VRMap) arrays.getData())));
        }
        
        
        //2006/02/12[Tozo Tanaka] : add begin 
        getMasterAffair().getTabs().addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                getDoctorName().requestFocus();
            }
        });
        //2006/02/12[Tozo Tanaka] : add end
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIkenshoInfoOrgan() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        organJigyoushoNoAbstraction.setVisible(false);
        organJigyoushoNoRegistAbstraction1.setVisible(false);
        organJigyoushoNoRegistAbstraction2.setVisible(false);

    }

    protected boolean checkSelectedDoctor() {
        
        //2006/02/11[Tozo Tanaka] : replace begin
//      return setJigyoushas();
         boolean result = setJigyoushas();
        VRBindSource source = getMasterSource();
        if ((source != null) && (source instanceof VRMap)) {
            // �����̓]�L
            VRMap map = (VRMap) source;
            if (doctor != null) {
                map.setData("KOUZA_MEIGI", doctor.getData("FURIKOMI_MEIGI"));
                map.setData("KOUZA_NO", doctor.getData("BANK_KOUZA_NO"));
                map.setData("KOUZA_KIND", doctor.getData("BANK_KOUZA_KIND"));

                //2006/02/11[Tozo Tanaka] : add begin
                //TODO canChange?
                //��Ë@�ւ̎{�݋敪(MI_KBN)�𐿋����̏��f�Ώ�(SHOSIN)�ɓ]�L����
                map.setData("SHOSIN", doctor.getData("MI_KBN"));
                if(getMasterAffair() instanceof IkenshoIkenshoInfo){
                    ((IkenshoIkenshoInfo)getMasterAffair()).getBill().setShoshin(doctor.getData("MI_KBN"));
                }
                //2006/02/11[Tozo Tanaka] : add end
                            
            }
        }
        return result;

        //2006/02/11[Tozo Tanaka] : replace end
    }

    protected boolean setDefaultDoctorOnInsertInit() throws ParseException {
        boolean ret = super.setDefaultDoctorOnInsertInit();
        if (ret) {
            checkSelectedDoctor();
        }
        return ret;
    }

    protected void applySourceInnerBindComponent() throws Exception {
        super.applySourceInnerBindComponent();

        VRBindSource source = getMasterSource();
        if ((source != null) && (source instanceof VRMap)) {
            VRMap map = (VRMap) source;
            if (getSelectedJigyousha() != null) {
                map.putAll(getSelectedJigyousha());
            } else if (organJigyoushoNo.getEditor().getItem() != null) {
                map.put("JIGYOUSHA_NO", String.valueOf(organJigyoushoNo
                        .getEditor().getItem()));
            } else {
                map.put("JIGYOUSHA_NO", "");
            }

            Object shoshinObj = VRBindPathParser.get("MI_KBN", map);
            if (shoshinObj != null) {
                // �����ɂ������݂��Ȃ���Ë@�ւ�I�����Ă����ꍇ�AMI_KBN�͎擾�ł��Ȃ�
                // ���������������Ō����_�����ē��͂��邽�߂ɁA�L����MI_KBN��SHOSIN�ɓ]�L����
                VRBindPathParser.set("SHOSIN", map, shoshinObj);
            } else {
                // MI_KBN���擾�ł���SHOSIN���L���ł���΁ASHOSIN��MI_KBN�ɓ]�L����
                shoshinObj = VRBindPathParser.get("SHOSIN", map);
                if (shoshinObj != null) {
                    VRBindPathParser.set("MI_KBN", map, shoshinObj);
                }
            }
        }
    }

    protected void bindSourceInnerBindComponent() throws Exception {
        Object jigyoushaNo = null;
        VRBindSource source = getMasterSource();
        if ((source != null) && (source instanceof VRMap)) {
            jigyoushaNo = VRBindPathParser.get("JIGYOUSHA_NO", source);
        }

        // ��������ޔ�
        Object meigi = source.getData("KOUZA_MEIGI");
        Object no = source.getData("KOUZA_NO");
        Object kind = source.getData("KOUZA_KIND");

        super.bindSourceInnerBindComponent();

        if (kind != null) {
            // bind�ɂ���Ë@�ւ̍đI���ŗ�����������Ă��܂����߁A��bind
            source.setData("FURIKOMI_MEIGI", meigi);
            source.setData("BANK_KOUZA_NO", no);
            source.setData("BANK_KOUZA_KIND", kind);
            getFollowDoctorContainer().bindSource();
        }

        if (jigyoushaNo != null) {
            organJigyoushoNo.getEditor().setItem(jigyoushaNo);
            VRBindPathParser.set("JIGYOUSHA_NO", source, jigyoushaNo);
            checkOrganAbstractionVisible();
        }

    }

    /**
     * �I�𒆂̎��Ǝ҃f�[�^��Ԃ��܂��B
     * 
     * @return �I�𒆂̎��Ǝ҃f�[�^
     */
    public VRMap getSelectedJigyousha() {
        if ((organJigyoushoNo.getSelectedIndex() >= 0) && (jigyoushas != null)) {
            return (VRMap) jigyoushas.getData(organJigyoushoNo
                    .getSelectedIndex());
        }
        return null;
    }

    /**
     * ���Ǝ҈ꗗ���č\�����܂��B
     * 
     * @return �č\��������
     */
    protected boolean setJigyoushas() {

        try {

            Object selectItem = getDoctorName().getSelectedItem();
            if ((selectItem instanceof String)
                    || (selectItem instanceof Integer)) {
                // ��t�����R���{�̃t�H�[�J�X���X�g�ɂ���ċ����I�ɕ�����f�[�^��ݒ肳��Ă��܂����ꍇ
                if (doctor == defaultDoctor) {
                    return false;
                }
                // ���O�܂łɑI�����Ă�����t�Ƃ��ĉ��߂�����
                selectItem = doctor;
                // return false;
            }

            if ((doctors != null) && (selectItem instanceof VRMap)
                    && (selectItem != defaultDoctor)) {
                if (getMasterAffair() == null) {
                    return false;
                }
                IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
                if (info.getMention() == null) {
                    return false;
                }

                doctor = (VRMap) selectItem;
                VRMap hoken = info.getMention().getSelectedInsurer();
                if (hoken != null) {
                    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                    StringBuffer sb = new StringBuffer();
                    sb.append("SELECT");
                    sb.append(" *");
                    sb.append(" FROM");
                    sb.append(" JIGYOUSHA");
                    sb.append(" WHERE");
                    sb.append("(");
                    sb.append("DR_CD = ");
                    sb.append(IkenshoCommon.getDBSafeNumber("DR_CD", doctor));
                    sb.append(")AND(");
                    sb.append("INSURER_NO = ");
                    sb.append(IkenshoCommon
                            .getDBSafeString("INSURER_NO", hoken));
                    sb.append(")");
                    jigyoushas = (VRArrayList) dbm.executeQuery(sb.toString());
                    dbm.finalize();

                    IkenshoCommon.applyComboModel(organJigyoushoNo,
                            new VRHashMapArrayToConstKeyArrayAdapter(
                                    jigyoushas, "JIGYOUSHA_NO"));
                    if ((organJigyoushoNo.getItemCount() > 0)
                            && (organJigyoushoNo.getSelectedIndex() < 0)) {
                        Object val = organJigyoushoNo.getItemAt(0);
                        organJigyoushoNo.setSelectedItem(val);
                        doctor.setData("JIGYOUSHA_NO", String.valueOf(val));
                    }
                } else {
                    organJigyoushoNo.setModel(new DefaultComboBoxModel());
                }
            } else {
                doctor = defaultDoctor;
                organJigyoushoNo.setModel(new DefaultComboBoxModel());
            }

            checkOrganAbstractionVisible();

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }
        return true;
    }

    /**
     * ���Ə��ԍ��̕⑫�������̕\����Ԃ�؂�ւ��܂��B
     * 
     * @throws ParseException ��͗�O
     */
    protected void checkOrganAbstractionVisible() throws ParseException {

        if (organJigyoushoNo.getItemCount() > 0) {
            // ���ƎҔԍ���I���\�Ȃ�x������
            organJigyoushoNoAbstraction.setVisible(false);
            organJigyoushoNoRegistAbstraction1.setVisible(false);
            organJigyoushoNoRegistAbstraction2.setVisible(false);
        } else {

            if (IkenshoCommon.isBillSeted((VRMap) getMasterSource())) {
                // �����p���𖞂����Ă��邪���Ə����Ȃ���Ύ��Ə���񖳂��̌x��
                organJigyoushoNoAbstraction.setVisible(false);
                organJigyoushoNoRegistAbstraction1.setVisible(true);
                organJigyoushoNoRegistAbstraction2.setVisible(true);
            } else {
                IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
                VRMap insurer = info.getMention().getSelectedInsurer();

                Object obj = getDoctorName().getEditor().getItem();
                if ((insurer != null) && (obj != null) && (!"".equals(obj))) {
                    // ��t�����͑I������Ă��Ȃ����A�������Ƃ��ĕێ����Ă���Ȃ玖�Ə���񖳂��̌x��
                    organJigyoushoNoAbstraction.setVisible(false);
                    organJigyoushoNoRegistAbstraction1.setVisible(true);
                    organJigyoushoNoRegistAbstraction2.setVisible(true);
                } else {
                    // ��t�����̗������Ȃ���Εی��҂�I�Ԃ悤�x��
                    organJigyoushoNoAbstraction.setVisible(true);
                    organJigyoushoNoRegistAbstraction1.setVisible(false);
                    organJigyoushoNoRegistAbstraction2.setVisible(false);
                }
            }
        }

    }

    protected boolean canCallRegistDoctor() {
        if (ACMessageBox
                .show(
                        "�����̈�Ë@�ւ��X�V����ꍇ�i���Ə��ԍ���o�^����ꍇ�܂ށj\n[���C�����j���[]-[��Ë@�֏��o�^/�X�V]�ɂčs���ĉ������B\n\n�V�K�̈�Ë@�ւ̓o�^���s���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
            return false;
        }
        return true;
    }

    private void jbInit() throws Exception {

        organDoctorNo.setBindPath("DR_NO");
        organDoctorNo.setColumns(10);
        organDoctorNo.setEditable(false);
        organBankAccountNo.setBindPath("BANK_KOUZA_NO");
        organBankAccountNo.setColumns(25);
        organBankAccountNo.setEditable(false);
        organBankAccountTypes.setText("�������");
        organBankAccountType
                .setFormat(IkenshoConstants.FORMAT_BANK_ACCOUNT_TYPE);
        organBankUser.setEditable(false);
        organBankUser.setColumns(15);
        organBankUser.setBindPath("FURIKOMI_MEIGI");
        organBankLayout.setFitHLast(true);
        organBankLayout.setAutoWrap(false);
        organBankAccountNos.setText("�����ԍ�");
        organBankUsers.setText("���`�l");
        organOpeners.setText("�J�ݎҎ���");
        organBankAccountType.setEditable(false);
        organBankAccountType.setBindPath("BANK_KOUZA_KIND");
        organJigyoushoNo.setPreferredSize(new Dimension(130, 19));
        organJigyoushoNo.setEditable(true);
        organJigyoushoNo.setMaxLength(10);
        organJigyoushoNo.setBindPath("JIGYOUSHA_NO");
        organBankGroup.setLayout(organBankLayout);
        organBankGroup.setText("�U������");
        organBankOffice.setPreferredSize(new Dimension(131, 20));
        organBankOffice.setEditable(false);
        organBankOffice.setColumns(25);
        organBankOffice.setBindPath("BANK_SITEN_NM");
        organOpener.setEditable(false);
        organOpener.setColumns(20);
        organOpener.setBindPath("KAISETUSHA_NM");
        organJigyoushoNos.setText("���Ə��ԍ�");
        organJigyoushoNosLayout.setHgap(2);
        organJigyoushoNosLayout.setAutoWrap(false);
        organJigyoushoNos.setLayout(organJigyoushoNosLayout);
        organBankNames.setText("���Z�@�֖�");
        organJigyoushoNoAbstraction.setText("�����Ə��ԍ���I������ɂ͕ی��҂�I�����Ă��������B");
        organJigyoushoNoAbstraction
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organType.setEditable(false);
        organType.setColumns(20);
        organType.setBindPath("MI_KBN");
        organType.setFormat(IkenshoConstants.FORMAT_SHINRYOUJYO_TYPE);
        organDoctorNos.setBeginText("�i��t�ԍ�");
        organTypes.setText("�f�Ï��E�a�@�敪");
        organBankOffices.setText("�x�X��");
        organBankName.setEditable(false);
        organBankName.setColumns(25);
        organBankName.setBindPath("BANK_NM");
        organJigyoushoNoRegistAbstraction1
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoRegistAbstraction1
                .setText("���Y������ی��҂ɂ��Ă̎��Ə��ԍ����o�^����Ă��܂���B");
        organJigyoushoNoAbstractions.setLayout(new BorderLayout());
        organJigyoushoNoRegistAbstraction2
                .setText("[���C�����j���[]-[��Ë@�֏��o�^/�X�V]�ɂēo�^���Ă��������B");
        organJigyoushoNoRegistAbstraction2
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        getDoctorNameContainer().add(organDoctorNos, BorderLayout.EAST);
        organDoctorNos.add(organDoctorNo, null);

        organOpeners.add(organOpener, null);
        organTypes.add(organType, null);

        organBankGroup.add(organBankNames, VRLayout.FLOW_INSETLINE);
        organBankNames.add(organBankName, null);
        organBankGroup.add(organBankUsers, VRLayout.FLOW_INSETLINE_RETURN);
        organBankUsers.add(organBankUser, null);
        organBankGroup.add(organBankOffices, VRLayout.FLOW_INSETLINE);
        organBankOffices.add(organBankOffice, null);
        organBankGroup.add(organBankOfficeNameSpacer,
                VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankAccountNos, VRLayout.FLOW_INSETLINE);
        organBankAccountNos.add(organBankAccountNo, null);
        organBankGroup.add(organBankAccountTypes,
                VRLayout.FLOW_INSETLINE_RETURN);
        organBankAccountTypes.add(organBankAccountType, null);

        organJigyoushoNos.add(organJigyoushoNo, VRLayout.FLOW);
        organJigyoushoNos.add(organJigyoushoNoAbstractions, VRLayout.FLOW);
        organJigyoushoNoAbstractions.add(organJigyoushoNoAbstraction,
                BorderLayout.CENTER);
        organJigyoushoNoAbstractions.add(organJigyoushoNoRegistAbstraction1,
                BorderLayout.NORTH);
        organJigyoushoNoAbstractions.add(organJigyoushoNoRegistAbstraction2,
                BorderLayout.SOUTH);

        getFollowDoctorContainer().add(organOpeners, VRLayout.FLOW_INSETLINE);
        getFollowDoctorContainer().add(organTypes, VRLayout.FLOW_INSETLINE);
        getFollowDoctorContainer().add(organTypeSpacer,
                VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organJigyoushoNos,
                VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organBankGroup, VRLayout.FLOW_RETURN);

    }

}
