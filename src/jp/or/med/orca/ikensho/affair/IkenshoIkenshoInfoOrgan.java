package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.KeyboardFocusManager;
import java.awt.event.ItemEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACIntegerTextField;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
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
    //private VRLabel organBankOfficeNameSpacer = new VRLabel();
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
    
    private ACIntegerCheckBox doctorAddIT = new ACIntegerCheckBox();
    private ACIntegerTextField doctorCode = new ACIntegerTextField();

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
        
        
        //2006/09/08[Tozo Tanaka] : delete begin
//        //2006/02/12[Tozo Tanaka] : add begin 
//        getMasterAffair().getTabs().addChangeListener(new ChangeListener(){
//            public void stateChanged(ChangeEvent e) {
//              getDoctorName().requestFocus();
//            }
//        });
//        //2006/02/12[Tozo Tanaka] : add end
        //2006/09/08[Tozo Tanaka] : delete end
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
    
    private boolean organUserChanging = false;
    /**
     * ���[�U����ɂ���Ë@�ւ̕ύX���ł��邩��Ԃ��܂��B
     * @return ���[�U����ɂ���Ë@�ւ̕ύX���ł��邩
     */
    protected boolean isOrganUserChanging(){
        return organUserChanging ;
    }
    /**
     * ���[�U����ɂ���Ë@�ւ̕ύX���ł��邩��ݒ肵�܂��B
     * @param organUserChanging ���[�U����ɂ���Ë@�ւ̕ύX���ł��邩
     */
    protected void setOrganUserChanging(boolean organUserChanging ){
        this.organUserChanging  = organUserChanging;
    }

    protected boolean checkSelectedDoctor() {
        // 2006/09/21[Tozo Tanaka] : add begin
        setOrganUserChanging(true);
        // 2006/09/21[Tozo Tanaka] : add end
        
        // 2006/02/11[Tozo Tanaka] : replace begin
        // return setJigyoushas();
        boolean result = setJigyoushas();
        
        // 2006/09/21[Tozo Tanaka] : add begin
        setOrganUserChanging(false);
        // 2006/09/21[Tozo Tanaka] : add end

        
        // 2006/09/11[Tozo Tanaka] : add begin
        if ((getMasterAffair() != null)
                && getMasterAffair().isNowSelectingByUpdate()) {
            // �����������ł��X�V���[�h�̂Ƃ�
            //���㏑���]�L���Ȃ�
        }else{
            // 2006/09/11[Tozo Tanaka] : add end

            VRBindSource source = getMasterSource();
            if ((source != null) && (source instanceof VRMap)) {
                // �����̓]�L
                VRMap map = (VRMap) source;
                if (doctor != null) {
                    map
                            .setData("KOUZA_MEIGI", doctor
                                    .getData("FURIKOMI_MEIGI"));
                    map.setData("KOUZA_NO", doctor.getData("BANK_KOUZA_NO"));
                    map
                            .setData("KOUZA_KIND", doctor
                                    .getData("BANK_KOUZA_KIND"));

                    // 2006/02/11[Tozo Tanaka] : add begin
                    // ��Ë@�ւ̎{�݋敪(MI_KBN)�𐿋����̏��f�Ώ�(SHOSIN)�ɓ]�L����
                    map.setData("SHOSIN", doctor.getData("MI_KBN"));
                    if (getMasterAffair() instanceof IkenshoIkenshoInfo) {
                        ((IkenshoIkenshoInfo) getMasterAffair()).getBill()
                                .setShoshin(doctor.getData("MI_KBN"));
                    }
                    // 2006/02/11[Tozo Tanaka] : add end

                }
            }
            //2006/09/11[Tozo Tanaka] : add begin
        }
        //2006/09/11[Tozo Tanaka] : add end
        return result;

        //2006/02/11[Tozo Tanaka] : replace end
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

        // 2006/09/21[Tozo Tanaka] : add begin
        setJigyoushoNosOnSelectingByUpdate(jigyoushaNo);
        // 2006/09/21[Tozo Tanaka] : add end
        if (jigyoushaNo != null) {
            // 2006/09/20[Tozo Tanaka] : add begin
            //�I��������B
            organJigyoushoNo.setSelectedItem(jigyoushaNo);
            // 2006/09/20[Tozo Tanaka] : add end
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
     * ����\�����Ɏ��Ə��ԍ��̓��͌��𕜌����܂��B
     * 
     * @param jigyoushaNo �����̎��Ə��ԍ�
     */
    protected void setJigyoushoNosOnSelectingByUpdate(Object jigyoushaNo) {
        if ((getMasterAffair() != null)
                && getMasterAffair().isNowSelectingByUpdate()) {
            // ����\����
            if ((jigyoushaNo == null) || "".equals(jigyoushaNo)) {
                // ���Ə��ԍ����Ȃ��ꍇ
                // ���Ə��ԍ��R���{�̓��e����Ő�������B
                organJigyoushoNo.setModel(new DefaultComboBoxModel());
            } else {
                // ���Ə��ԍ���L����ꍇ
                // ���Ə��ԍ��R���{�̓��e�𗚗��̎��Ə��ԍ��̂ݒǉ����Đ�������B
                organJigyoushoNo.setModel(new DefaultComboBoxModel(
                        new Object[] { jigyoushaNo }));
            }
        }
    }

    /**
     * ���Ǝ҈ꗗ���č\�����܂��B
     * 
     * @return �č\��������
     */
    protected boolean setJigyoushas() {

        try {

            Object selectItem = getDoctorName().getSelectedItem();
            // 2006/09/21[Tozo Tanaka] : replace begin
            //if ((selectItem instanceof String)|| (selectItem instanceof Integer)) {
            if ((selectItem instanceof String)|| (selectItem instanceof Integer)||(selectItem==null)) {
            // 2006/09/21[Tozo Tanaka] : replace end
                // ��t�����R���{�̃t�H�[�J�X���X�g�ɂ���ċ����I�ɕ�����f�[�^��ݒ肳��Ă��܂����ꍇ
                if (doctor == defaultDoctor) {
                    
                    // 2006/09/21[Tozo Tanaka] : add begin
                    if (
                            // ����\���ɂ��bind�͏��O����B
                    (getMasterAffair() instanceof IkenshoIkenshoInfo)
                            && (!getMasterAffair().isNowSelectingByUpdate())
                            // ���[�U����ɂ���t�����ύX�͏��O����B
                            && (!isOrganUserChanging())
                    ) {
                        IkenshoIkenshoInfoMention mention = ((IkenshoIkenshoInfo) getMasterAffair())
                                .getMention();
                        if (mention != null) {
                            // �ی��Җ��Ƀt�H�[�J�X�����遨���[�U����
                            VRMap hoken = mention.getSelectedInsurer();
                            if (hoken != null) {
                                // �ی��ґI���ς�
                                IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                                StringBuffer sb = new StringBuffer();
                                String txt;
                                sb.append("SELECT");
                                sb.append(" *");
                                sb.append(" FROM");
                                sb.append(" JIGYOUSHA,DOCTOR,INSURER");
                                sb.append(" WHERE");
                                sb.append("(");
                                sb.append("DOCTOR.DR_NM = ");
                                txt = IkenshoConstants.FORMAT_PASSIVE_STRING
                                        .format(getDoctorName().getEditor()
                                                .getItem());
                                if("NULL".equals(txt)){
                                    txt = "''";
                                }
                                sb.append( txt);
                                sb.append(")AND(");
                                sb.append("DOCTOR.MI_NM = ");
                                txt = IkenshoConstants.FORMAT_PASSIVE_STRING
                                        .format(getOrganizationName().getText());
                                if("NULL".equals(txt)){
                                    txt = "''";
                                }
                                sb.append( txt);
                                sb.append(")AND(");
                                sb.append("INSURER.INSURER_NO = ");
                                sb.append(IkenshoCommon.getDBSafeString(
                                        "INSURER_NO", hoken));
                                sb.append(")AND(");
                                sb.append("INSURER.INSURER_TYPE = ");
                                sb.append(IkenshoCommon.getDBSafeNumber(
                                        "INSURER_TYPE", hoken));
                                sb.append(")AND(");
                                sb.append("JIGYOUSHA.DR_CD = DOCTOR.DR_CD");
                                sb.append(")AND(");
                                sb
                                        .append("JIGYOUSHA.INSURER_NO = INSURER.INSURER_NO");
                                sb.append(")");
                                jigyoushas = (VRArrayList) dbm.executeQuery(sb
                                        .toString());
                                dbm.finalize();

                                // �擾�������Ə��ԍ�����ݒ肷��
                                IkenshoCommon
                                        .applyComboModel(
                                                organJigyoushoNo,
                                                new VRHashMapArrayToConstKeyArrayAdapter(
                                                        jigyoushas,
                                                        "JIGYOUSHA_NO"));

                                if (organJigyoushoNo.getItemCount() > 0) {
                                    Object val = organJigyoushoNo.getItemAt(0);
                                    // doctor.setData("JIGYOUSHA_NO",
                                    // String.valueOf(val));
                                    organJigyoushoNo.setSelectedItem(val);
                                    getMasterSource().setData("JIGYOUSHA_NO",
                                            String.valueOf(val));
//                                } else if (getDoctorName().isFocusOwner()
//                                        || getDoctorName().getEditor()
//                                                .getEditorComponent()
//                                                .isFocusOwner()) {
//                                    //���Ə��ԍ��̌�₪0�����A��t�����̃R���{�Ƀt�H�[�J�X���������Ă���i���[�U����̏ꍇ�j�̂݁A�e�L�X�g���N���A����B
//                                    getMasterSource().setData("JIGYOUSHA_NO", "");
                                }

                            }else{
                                getMasterSource().setData("JIGYOUSHA_NO", "");
                                organJigyoushoNo.setModel(new DefaultComboBoxModel());
                            }
                            checkOrganAbstractionVisible();
                        }
                    }
                    // 2006/09/21[Tozo Tanaka] : add end
                    
                    
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
                    sb.append(")AND(");
                    sb.append("INSURER_TYPE = ");
                    sb.append(IkenshoCommon
                            .getDBSafeNumber("INSURER_TYPE", hoken));
                    sb.append(")");
                    jigyoushas = (VRArrayList) dbm.executeQuery(sb.toString());
                    dbm.finalize();

                    IkenshoCommon.applyComboModel(organJigyoushoNo,
                            new VRHashMapArrayToConstKeyArrayAdapter(
                                    jigyoushas, "JIGYOUSHA_NO"));
                    // 2006/09/20[Tozo Tanaka] : remove begin
//                    if ((organJigyoushoNo.getItemCount() > 0)
//                            && (organJigyoushoNo.getSelectedIndex() < 0)) {
//                        Object val = organJigyoushoNo.getItemAt(0);
//                        organJigyoushoNo.setSelectedItem(val);
//
//                        doctor.setData("JIGYOUSHA_NO", String.valueOf(val));
//                    }
                        // 2006/09/20[Tozo Tanaka] : remove end
                    // 2006/09/20[Tozo Tanaka] : add begin
                    if (organJigyoushoNo.getItemCount() > 0){
                        Object val = organJigyoushoNo.getItemAt(0);
                        doctor.setData("JIGYOUSHA_NO", String.valueOf(val));
                        organJigyoushoNo.setSelectedItem(val);
                        getMasterSource().setData("JIGYOUSHA_NO", String.valueOf(val));
                    }else if(getDoctorName().isFocusOwner()|| getDoctorName().getEditor().getEditorComponent().isFocusOwner()){
                        //���Ə��ԍ��̌�₪0�����A��t�����̃R���{�Ƀt�H�[�J�X���������Ă���i���[�U����̏ꍇ�j�̂݁A�e�L�X�g���N���A����B
                        getMasterSource().setData("JIGYOUSHA_NO", "");
                    }
                    // 2006/09/20[Tozo Tanaka] : add end
                } else {
                    // 2006/09/20[Tozo Tanaka] : add begin
                    getMasterSource().setData("JIGYOUSHA_NO", "");
                    // 2006/09/20[Tozo Tanaka] : add end
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
    	  // 2009/01/06 [Mizuki Tsutsumi] : edit begin
    	////////// �R���g���[�������� //////////
    	//��t�ԍ�
        organDoctorNos.setBeginText("�i��t�ԍ�");
        organDoctorNo.setBindPath("DR_NO");
        organDoctorNo.setColumns(10);
        organDoctorNo.setEditable(false);
        organDoctorNos.add(organDoctorNo, null);

        
        //�J�ݎҎ���
        organOpeners.setText("�J�ݎҎ���");
        organOpener.setEditable(false);
        organOpener.setColumns(20);
        organOpener.setBindPath("KAISETUSHA_NM");
        organOpeners.add(organOpener, null);
        
        //�f�Ï��E�a�@�敪
        organTypes.setText("�f�Ï��E�a�@�敪");
        organType.setEditable(false);
        organType.setColumns(20);
        organType.setBindPath("MI_KBN");
        organType.setFormat(IkenshoConstants.FORMAT_SHINRYOUJYO_TYPE);
        organTypes.add(organType, null);

        //���Ə��ԍ�
        organJigyoushoNosLayout.setHgap(2);
        organJigyoushoNosLayout.setAutoWrap(false);
        organJigyoushoNos.setText("���Ə��ԍ�");
        organJigyoushoNos.setLayout(organJigyoushoNosLayout);
        organJigyoushoNo.setPreferredSize(new Dimension(130, 19));
        organJigyoushoNo.setEditable(true);
        organJigyoushoNo.setMaxLength(10);
        organJigyoushoNo.setBindPath("JIGYOUSHA_NO");
        organJigyoushoNos.add(organJigyoushoNo, VRLayout.FLOW);
        
        organJigyoushoNoAbstractions.setLayout(new BorderLayout());
        organJigyoushoNoAbstraction.setText("�����Ə��ԍ���I������ɂ͕ی��҂�I�����Ă��������B");
        organJigyoushoNoAbstraction.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoRegistAbstraction1
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoRegistAbstraction1
                .setText("���Y������ی��҂ɂ��Ă̎��Ə��ԍ����o�^����Ă��܂���B");
        organJigyoushoNoRegistAbstraction2
                .setText("[���C�����j���[]-[��Ë@�֏��o�^/�X�V]�ɂēo�^���Ă��������B");
        organJigyoushoNoRegistAbstraction2
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        organJigyoushoNoAbstractions.add(organJigyoushoNoAbstraction, BorderLayout.CENTER);
        organJigyoushoNoAbstractions.add(organJigyoushoNoRegistAbstraction1, BorderLayout.NORTH);
        organJigyoushoNoAbstractions.add(organJigyoushoNoRegistAbstraction2, BorderLayout.SOUTH);
        organJigyoushoNos.add(organJigyoushoNoAbstractions, VRLayout.FLOW);

        
        //========== �U������ ==========
        organBankLayout.setFitHLast(true);
        organBankLayout.setAutoWrap(false);
        organBankGroup.setLayout(organBankLayout);
        organBankGroup.setText("�U������");

        //���Z�@�֖�
        organBankNames.setText("���Z�@�֖�");
        organBankName.setEditable(false);
        organBankName.setColumns(25);
        organBankName.setBindPath("BANK_NM");
        organBankNames.add(organBankName, null);

        //�x�X��
        organBankOffices.setText("�x�X��");
        organBankOffice.setPreferredSize(new Dimension(131, 20));
        organBankOffice.setEditable(false);
        organBankOffice.setColumns(25);
        organBankOffice.setBindPath("BANK_SITEN_NM");
        organBankOffices.add(organBankOffice, null);

        //���`�l
        organBankUsers.setText("���`�l");
        organBankUser.setEditable(false);
        organBankUser.setColumns(15);
        organBankUser.setBindPath("FURIKOMI_MEIGI");
        organBankUsers.add(organBankUser, null);

        //�����ԍ�
        organBankAccountNos.setText("�����ԍ�");
        organBankAccountNo.setBindPath("BANK_KOUZA_NO");
        organBankAccountNo.setColumns(25);
        organBankAccountNo.setEditable(false);
        organBankAccountNos.add(organBankAccountNo, null);
        
        //�������
        organBankAccountTypes.setText("�������");
        organBankAccountType.setFormat(IkenshoConstants.FORMAT_BANK_ACCOUNT_TYPE);
        organBankAccountType.setEditable(false);
        organBankAccountType.setBindPath("BANK_KOUZA_KIND");
//      [ID:0000514][Tozo TANAKA] 2009/09/11 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        organBankAccountType.setColumns(4);
//      [ID:0000514][Tozo TANAKA] 2009/09/11 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        organBankAccountTypes.add(organBankAccountType, null);
    	//==============================
        
        //////////// �R���g���[���̔z�u //////////
        getDoctorNameContainer().add(organDoctorNos, BorderLayout.EAST);

        getFollowDoctorContainer().add(organOpeners, VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organTypes, VRLayout.FLOW_INSETLINE);
        getFollowDoctorContainer().add(organTypeSpacer, VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organJigyoushoNos, VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(organBankGroup, VRLayout.FLOW_RETURN);

        //�U������
        organBankGroup.add(organBankNames, VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankOffices, VRLayout.FLOW_INSETLINE_RETURN);
        //organBankGroup.add(organBankOfficeNameSpacer, VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankUsers, VRLayout.FLOW_INSETLINE_RETURN);
        organBankGroup.add(organBankAccountNos, VRLayout.FLOW_INSETLINE);
        organBankGroup.add(organBankAccountTypes, VRLayout.FLOW_INSETLINE_RETURN);
        // 2009/01/06 [Mizuki Tsutsumi] : edit end
        
        //�d�q�����Z�Ώۂ̎��Ə��ł��邩
        doctorAddIT.setVisible(false);
        doctorAddIT.setBindPath("DR_ADD_IT");
        getFollowDoctorContainer().add(doctorAddIT, VRLayout.FLOW);

        //��Ë@�փR�[�h
        doctorCode.setVisible(false);
        doctorCode.setBindPath("DR_CD");
        getFollowDoctorContainer().add(doctorCode, VRLayout.FLOW);
    }

    protected boolean changeDoctor(ItemEvent e) {
        try {
            // ����܂őI�����Ă�����Ë@�ւ͓d�q�����Z�̑Ώۂł��邩
            
            //2006/09/07 [Tozo Tanaka] : replace begin
            //boolean oldDoctorIsAddIt = isDoctorAddIT();
            IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
            if (info.getMention() == null) {
                return false;
            }
            int formatKubun = ACCastUtilities.toInt(info.getFormatKubun(), 0);

            boolean oldDoctorIsAddIt = IkenshoCommon.canAddIT(formatKubun,
                    isDoctorAddIT(), info.getMention().getAddITType());
            //2006/09/07 [Tozo Tanaka] : replace end
            
            if (super.changeDoctor(e)) {
                // ��Ë@�֕ύX����̏ꍇ
                //2006/09/07 [Tozo Tanaka] : replace begin
                //boolean nowDoctorIsAddIt = isDoctorAddIT();
                boolean nowDoctorIsAddIt = IkenshoCommon.canAddIT(formatKubun,
                        isDoctorAddIT(), info.getMention().getAddITType());
                // 2006/09/07 [Tozo Tanaka] : replace end
                if (oldDoctorIsAddIt != nowDoctorIsAddIt) {
                    // �d�q�����Z�ΏۂɕύX����
                    info = (IkenshoIkenshoInfo) getMasterAffair();
                    if (info.getBill() != null) {
                        // �����^�u����`����Ă���ꍇ
                        double addIT = info.getBill().getShosinAddIT();
                        double shosinHos = info.getBill().getShosinHospital();
                        double shosinSin = info.getBill().getShosinSinryoujo();
                        if (oldDoctorIsAddIt) {
                            // ���͓d�q�����Z���聨�d�q�����Z�Ȃ�
                            // ���f�_������I�����Ă���ی��҂̓d�q�����Z�_�������Z����
                            shosinHos -= addIT;
                            shosinSin -= addIT;
                        } else {
                            // ���͓d�q�����Z�Ȃ����d�q�����Z����
                            // ���f�_���ɑI�����Ă���ی��҂̓d�q�����Z�_�������Z����
                            shosinHos += addIT;
                            shosinSin += addIT;
                        }
                        info.getBill().setShosinHospital(shosinHos);
                        info.getBill().setShosinSinryoujo(shosinSin);
                    }
                }
                return true;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return false;
    }
    
    /**
     * �d�q�����Z�̑ΏۂƂȂ��Ë@�ւł��邩��Ԃ��܂��B
     * @return �d�q�����Z�̑ΏۂƂȂ��Ë@�ւł��邩
     */
    public boolean isDoctorAddIT(){
        return doctorAddIT.isSelected();
    }
    /**
     * �d�q�����Z�̑ΏۂƂȂ��Ë@�ւł��邩��ݒ肵�܂��B
     * @param val �d�q�����Z�̑ΏۂƂȂ��Ë@�ւł��邩
     */
    public void setDoctorAddIT(boolean val){
        doctorAddIT.setSelected(val);
    }
    
    /**
     * ���ݑI�𒆂̈�Ë@�փR�[�h��Ԃ��܂��B
     * @return
     * @throws Exception
     */
    public int getDoctorCode() throws Exception{
        return ACCastUtilities.toInt(doctorCode.getText(),0);
    }
    
    /**
     * ���ݑI�𒆂̈�Ë@�փR�[�h��ݒ肵�܂��B
     * @param val
     * @throws Exception
     */
    public void setDoctorCode(int val) throws Exception{
        doctorCode.setText(ACCastUtilities.toString(val));
    }
    
    

}
