package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Map;

import javax.swing.SwingConstants;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoInitialNegativeIntegerTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMention extends
        IkenshoTabbableChildAffairContainer {
    private ACTextField mentionHasegawa1 = new ACTextField();
    private ACLabelContainer mentionCreateDates = new ACLabelContainer();
    private IkenshoEraDateTextField mentionShinseiDate = new IkenshoEraDateTextField();
    private IkenshoEraDateTextField mentionHasegawaDay1 = new IkenshoEraDateTextField();
    private ACTextField mentionHokenNo = new ACTextField();
    private VRLayout mentionLayout = new VRLayout();
    private VRLayout mentionSeikyushoGroupLayout = new VRLayout();
    // private JScrollPane mentionTokkiScroll = new JScrollPane();
    private IkenshoEraDateTextField mentionHasegawaDay2 = new IkenshoEraDateTextField();
    private VRPanel mentionTokkiMoreAbstractions = new VRPanel();
    private ACTextField mentionHiHokenNo = new ACTextField();
    private ACLabelContainer mentionHasegawas = new ACLabelContainer();
    private ACGroupBox mentionSeikyushoGroup = new ACGroupBox();
    private ACComboBox mentionShisetsu2 = new ACComboBox();
    private ACLabelContainer mentionShisetsus = new ACLabelContainer();
    private VRLabel mentionShisetsu2Head = new VRLabel();
    // private VRLabel mentionTokkiAbstraction2 = new VRLabel();
    private ACComboBox mentionHokenName = new ACComboBox();
    // private IkenshoDeselectableComboBox mentionHokenName = new
    // IkenshoDeselectableComboBox();
    private ACTextField mentionOrderNo = new ACTextField();
    private ACComboBox mentionShisetsu1 = new ACComboBox();
    private ACGroupBox mentionTokkiGroup = new ACGroupBox();
    private ACTextField mentionHasegawa2 = new ACTextField();
    private ACLabelContainer mentionSendDates = new ACLabelContainer();
    private VRLabel mentionHasegawa1Tail = new VRLabel();
    private IkenshoEraDateTextField mentionCreateDate = new IkenshoEraDateTextField();
    private ACTextArea mentionTokki = new ACTextArea();
    private ACButton mentionAddHokensha = new ACButton();
    private VRLabel mentionHasegawa2Tail = new VRLabel();
    private ACLabelContainer mentionShinseiDates = new ACLabelContainer();
    private ACButton mentionShowTokkiMoreAbstractionChooser = new ACButton();
    private VRLabel mentionTokkiMoreAbstraction = new VRLabel();
    private VRLabel mentionShisetsu1Head = new VRLabel();
    private ACLabelContainer mentionTokkis = new ACLabelContainer();
    private ACClearableRadioButtonGroup mentionCareType = new ACClearableRadioButtonGroup();
    private ACLabelContainer mentionOrderNos = new ACLabelContainer();
    private IkenshoEraDateTextField mentionSendDate = new IkenshoEraDateTextField();
    private ACLabelContainer mentionHiHokenNos = new ACLabelContainer();
    private IkenshoDocumentTabTitleLabel mentionTitle = new IkenshoDocumentTabTitleLabel();
    private ACButton mentionAddKensa = new ACButton();
    private ACLabelContainer mentionHokenNames = new ACLabelContainer();
    private VRLayout mentionTokkiGroupLayout = new VRLayout();
    private ACLabelContainer mentionCareTypes = new ACLabelContainer();
    private ACParentHesesPanelContainer mentionHokenNoHeses = new ACParentHesesPanelContainer();
    private VRLayout mentionShisetsusLayout = new VRLayout();
    private VRPanel mentionTokkiAbstractions = new VRPanel();
    private ACLabel mentionTokkiAbstraction1 = new ACLabel();
    private VRLabel mentionTokkiAbstraction3 = new VRLabel();
    private VRPanel mentionDates = new VRPanel();
    private VRPanel mentionInsurers = new VRPanel();
    private VRLayout mentionInsurersLayout = new VRLayout();
    private ACLabelContainer mentionHasegawaDays1 = new ACLabelContainer();
    private ACLabelContainer mentionHasegawaDays2 = new ACLabelContainer();
    private ACLabelContainer mentionHasegawas2 = new ACLabelContainer();
    private ACParentHesesPanelContainer mentionHasegawaDayHeses2 = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer mentionHasegawaDaysHeses1 = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer mentionHasegawasHeses2 = new ACParentHesesPanelContainer();
    private VRArrayList insurers;
    private Object oldSelectHokenName;
    private ACLabelContainer mentionHasegawas1 = new ACLabelContainer();
    private VRLayout mentionHasegawasLayout = new VRLayout();
    private ACGroupBox hiddenParameters = new ACGroupBox();
    private ACGroupBox insureParameters = new ACGroupBox();
    private IkenshoInitialNegativeIntegerTextField s18 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s19 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s110 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s111 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s112 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s115 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s117 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s118 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s119 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1110 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1111 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1112 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1113 = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField s1114 = new IkenshoInitialNegativeIntegerTextField();
    private ACGroupBox ikenshoGroupBox1 = new ACGroupBox();
    private ACTextField insurerName = new ACTextField();

    protected boolean binding = false;

    /**
     * �^�C�g���\�����x����Ԃ��܂��B
     * 
     * @return �^�C�g���\�����x��
     */
    protected VRLabel getTitle() {
        return mentionTitle;
    }

    /**
     * ���L��������1��Ԃ��܂��B
     * 
     * @return ���L��������1
     */
    protected ACLabel getTokkiAbstraction1() {
        return mentionTokkiAbstraction1;
    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

        VRHashMapArrayToConstKeyArrayAdapter adapter = IkenshoCommon
                .geArrayAdapter(dbm, "INSTITUTION_NM", "M_INSTITUTION",
                        "INSTITUTION_KBN", IkenshoCommon.INSTITUTION_DEFAULTT,
                        "");
        if (adapter.getDataSize() > 0) {
            VRMap map = new VRHashMap();
            map.put("INSTITUTION_NM", "");
            ((VRArrayList) adapter.getAdaptee()).add(0, map);
            IkenshoCommon.applyComboModel(mentionShisetsu1, adapter);
        }

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" ORDER BY");
        sb.append(" INSURER_NM");
        insurers = (VRArrayList) dbm.executeQuery(sb.toString());

        if (insurers.getDataSize() > 0) {
            VRMap blank = new VRHashMap();
            blank.putAll((Map) insurers.get(0));
            blank.setData("INSURER_NO", "");
            blank.setData("INSURER_NM", "");
            insurers.add(0, blank);
            IkenshoCommon.applyComboModel(mentionHokenName, insurers);
        }
    }

    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();

        binding = true;
        IkenshoCommon.followComboIndex(insurers, "INSURER_NO",
                (VRMap) getMasterSource(), mentionHokenName);
        binding = false;
    }

    public boolean noControlError() {
        // �G���[�`�F�b�N
        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();

        switch (mentionHasegawaDay1.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("�����̓��t�ł��B");
            mentionHasegawaDay1.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("���t�Ɍ�肪����܂��B");
            mentionHasegawaDay1.requestChildFocus();
            return false;
        }

        switch (mentionHasegawaDay2.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("�����̓��t�ł��B");
            mentionHasegawaDay2.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("���t�Ɍ�肪����܂��B");
            mentionHasegawaDay2.requestChildFocus();
            return false;
        }

        switch (mentionShinseiDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
            break;
        case IkenshoEraDateTextField.STATE_VALID:
            if (mentionShinseiDate.getDate().compareTo(
                    info.getApplicant().getWriteDate()) > 0) {
                // �L�����̂ق����Â�
                ACMessageBox.show("�\�����͋L�����ȑO�łȂ���΂����܂���B");
                mentionShinseiDate.requestChildFocus();
                return false;
            }
            if (mentionCreateDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
                if (mentionShinseiDate.getDate().compareTo(
                        mentionCreateDate.getDate()) > 0) {
                    // �쐬�˗����̕����Â�
                    ACMessageBox.show("�\�����͍쐬�˗����ȑO�łȂ���΂����܂���B");
                    mentionShinseiDate.requestChildFocus();
                    return false;
                }
            }
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("�����̓��t�ł��B");
            mentionShinseiDate.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("���t�Ɍ�肪����܂��B");
            mentionShinseiDate.requestChildFocus();
            return false;
        }

        switch (mentionCreateDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
            break;
        case IkenshoEraDateTextField.STATE_VALID:
            if (mentionCreateDate.getDate().compareTo(
                    info.getApplicant().getWriteDate()) > 0) {
                // �L�����̂ق����Â�
                ACMessageBox.show("�쐬�˗����͋L�����ȑO�łȂ���΂����܂���B");
                mentionCreateDate.requestChildFocus();
                return false;
            }
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("�����̓��t�ł��B");
            mentionCreateDate.requestChildFocus();
            return false;
        default:
            ACMessageBox.show("���t�Ɍ�肪����܂��B");
            mentionCreateDate.requestChildFocus();
            return false;
        }

        switch (mentionSendDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_FUTURE:
            break;
        case IkenshoEraDateTextField.STATE_VALID:
            if (info.getApplicant().getWriteDate().compareTo(
                    mentionSendDate.getDate()) > 0) {
                // �L�����̂ق����Â�
                ACMessageBox.show("�L�����͑��t���ȑO�łȂ���΂����܂���B");
                mentionSendDate.requestChildFocus();
                return false;
            }
            break;
        // case NCEraDateTextField.STATE_FUTURE:
        // NCMessageBox.showExclamation("�����̓��t�ł��B");
        // mentionSendDate.requestChildFocus();
        // return false;
        default:
            ACMessageBox.show("���t�Ɍ�肪����܂��B");
            mentionSendDate.requestChildFocus();
            return false;
        }

        return true;
    }

    /**
     * �����_���̖��w��`�F�b�N���s���A�x���𔭂��܂��B
     * 
     * @return �����𑱍s���ėǂ���
     */
    public boolean isTestPointCheckWarning() {
        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();

        try {
            // if (
            // (!IkenshoCommon.isNullText(mentionHokenName.getSelectedItem()))
            // &&
            if ((mentionHokenName.getSelectedIndex() > 0)
                    && (mentionCareType.getSelectedIndex() != mentionCareType
                            .getNoSelectIndex())
                    && (info.getOrgan().hasDoctorName())) {

                // �����_���w��v���𖞂����Ă���
                if (!IkenshoCommon.isBillSeted((VRMap) getMasterSource())) {
                    // �����_�����w��
                    if (ACMessageBox.show("������p�_�����ݒ肳��Ă��܂��񂪂�낵���ł����H",
                            ACMessageBox.BUTTON_OKCANCEL,
                            ACMessageBox.ICON_QUESTION,
                            ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return false;
                    }
                }
                if ((info.getBillHakkouKubun() == null)
                        || (info.getBillHakkouKubun().intValue() < 1)) {
                    // �����s�ɂ���
                    info.setBillHakkouKubun(new Integer(1));
                }

            } else {
                // ���s�ΏۊO
                info.setBillHakkouKubun(new Integer(0));
            }
        } catch (ParseException ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }
        return true;
    }

    public boolean noControlWarning() {

        if (!IkenshoCommon.isNullText(mentionHiHokenNo.getText())) {
            if (mentionHiHokenNo.getText().length() < 10) {
                if (ACMessageBox.show("��ی��Ҕԍ���10�����͂���Ă��܂���B\n��낵���ł����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    mentionHiHokenNo.requestFocus();
                    return false;
                }
            }
        }
        // 2006/12/11[Tozo Tanaka] : add begin
        if (IkenshoCommon.isConvertedNoBill(getMasterSource())) {
            // �ی��ґI���ς݂��������o�̓p�^�[����0���ȍ~����Ő����f�[�^���s��
            if (ACMessageBox.showOkCancel("�����f�[�^�͈ڍs����Ă��Ȃ����߁A�ی��҂���ш�Ë@�ւ�"
                    + ACConstants.LINE_SEPARATOR + "�đI�����Ȃ����萿����������ł��܂���B"
                    + ACConstants.LINE_SEPARATOR + "��낵���ł����H",
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {

                return false;
            }
        }
        // 2006/12/11[Tozo Tanaka] : add end

        return true;
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIkenshoInfoMention() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        hiddenParameters.setVisible(false);

        mentionHokenName.setRenderer(new ACBindListCellRenderer("INSURER_NM"));

        mentionShisetsu1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                changeShisetsu(e);
            }
        });

        mentionShowTokkiMoreAbstractionChooser
                .addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        IkenshoExtraSpecialNoteDialog form = new IkenshoExtraSpecialNoteDialog(
                                "���̑����L���ׂ�����",
                                IkenshoCommon.TEIKEI_MENTION_NAME, 400, 100, 8,
                                50);
                        mentionTokki.setText(form.showModal(mentionTokki
                                .getText()));
                        mentionTokki.requestFocus();
                    }
                });

        mentionAddHokensha.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRegistHokensya();
            }
        });

        mentionAddKensa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInputTestPoint();
            }
        });

        mentionHokenName.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                changeInsurerName(e);
            }
        });
    }

    /**
     * �ی��Җ��̕ύX���������܂��B
     * 
     * @param e �C�x���g���
     */
    protected void changeInsurerName(ItemEvent e) {

        try {

            Object selectItem = mentionHokenName.getSelectedItem();
            if (oldSelectHokenName == selectItem) {
                // �ϓ��Ȃ�
                return;
            }
            // boolean blankSelected = IkenshoCommon.isNullText(selectItem);
            boolean blankSelected = (mentionHokenName.getSelectedIndex() <= 0)
                    || IkenshoCommon.isNullText(selectItem);

            final int NO_ERROR = 0;
            final int WARNING_CLEAR = 2 << 0;
            final int WARNING_CHANGE = 2 << 1;
            final int MUST_CHANGE = 2 << 2;
            int checkFlag = NO_ERROR;

            switch (e.getStateChange()) {
            case ItemEvent.DESELECTED:
                if ((oldSelectHokenName != null) && blankSelected) {
                    if (mentionHokenName.hasFocus()) {
                        checkFlag |= WARNING_CLEAR;
                    }
                } else {
                    // �ϓ��Ȃ�
                    return;
                }
                break;
            case ItemEvent.SELECTED:
                if (insurers != null) {
                    if (IkenshoCommon.isNullText(oldSelectHokenName)) {
                        // �O�񖢑I��
                        if (blankSelected) {
                            // �ϓ��Ȃ�
                            return;
                        }
                        checkFlag |= MUST_CHANGE;
                    } else {
                        // �O��I������̕ύX
                        if (mentionHokenName.hasFocus()) {
                            if (blankSelected) {
                                // �I�����u�����N��
                                checkFlag |= WARNING_CLEAR;
                            } else {
                                checkFlag |= WARNING_CHANGE;
                            }
                        }
                    }
                }
                break;
            default:
                return;
            }

            if ((checkFlag & WARNING_CLEAR) > 0) {
                // �����x��
                if (ACMessageBox.show(
                        "�ݒ肳��Ă��鐿������A�ӌ����쐬�������\n�f�@�E�����_�����N���A����܂��B�ی��҂��N���A���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    // �L�����Z��
                    mentionHokenName
                            .setSelectedItemAbsolute(oldSelectHokenName);
                    return;
                }

            } else if ((checkFlag & WARNING_CHANGE) > 0) {
                // �ύX�x��
                if (ACMessageBox.show(
                        "�ӌ����쐬������ѐf�@�E�����_�����ς��\��������܂��B\n�ی��҂�ύX���܂����H\n\n["
                                + String.valueOf(((VRMap) oldSelectHokenName)
                                        .getData("INSURER_NM"))
                                + "]��["
                                + String.valueOf(((VRMap) e.getItem())
                                        .getData("INSURER_NM")) + "]",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    // �L�����Z��
                    mentionHokenName
                            .setSelectedItemAbsolute(oldSelectHokenName);
                    return;
                }
            }

            IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
            VRBindSource oldSource = getMasterSource();
            ACGroupBox pointGroup = info.getBill().getPointGroup();

            if (blankSelected) {
                oldSelectHokenName = null;

                // �����l�ɐU�蒼��
                insureParameters.setSource((VRBindSource) insureParameters
                        .createSource());
                insureParameters.bindSource();
                insureParameters.setSource(oldSource);
                insureParameters.applySource();

                mentionHokenNo.setText("");
                mentionHokenNo.applySource();

                // �_��������
                // 2006/02/06[Tozo Tanaka] : replace begin
                // pointGroup.setSource((VRBindSource)pointGroup.createSource());
                VRBindSource source = (VRBindSource) pointGroup.createSource();
                Object tax = null;
                if (oldSource != null) {
                    // ����ł͈ێ�����
                    tax = oldSource.getData("TAX");
                }
                source.setData("TAX", tax);
                pointGroup.setSource(source);
                // 2006/02/06[Tozo Tanaka] : replace end

                pointGroup.bindSource();
                pointGroup.setSource(oldSource);
                pointGroup.applySource();

                if (!binding) {
                    // ���[�U�ɂ��I��(bind�ȊO)�̏ꍇ�͐؂�ւ���
                    info.getBill().setOutputPattern(null);
                }
            } else {
                oldSelectHokenName = selectItem;

                VRMap insurer = null;
                insurer = (VRMap) insurers.getData(mentionHokenName
                        .getSelectedIndex());
                // insurer = (VRHashMap) insurers.getData(mentionHokenName.
                // getSelectedIndex() - 1);
                insureParameters.setSource(insurer);
                insureParameters.bindSource();
                insureParameters.setSource(oldSource);
                insureParameters.applySource();

                mentionHokenNo.setText(String.valueOf(VRBindPathParser.get(
                        mentionHokenNo.getBindPath(), insurer)));
                mentionHokenNo.applySource();

                insurerName.setText(String.valueOf(VRBindPathParser.get(
                        insurerName.getBindPath(), insurer)));

                if ((!binding)
                        || (Integer.parseInt(String.valueOf(VRBindPathParser
                                .get("OUTPUT_PATTERN", oldSource))) < 0)) {
                    // ���[�U�ɂ��I��(bind�ȊO)�̏ꍇ�͐؂�ւ���
                    oldSource.setData("OUTPUT_PATTERN", insurer
                            .getData("SEIKYUSHO_OUTPUT_PATTERN"));
                    info.getBill().setOutputPattern(
                            oldSource.getData("OUTPUT_PATTERN"));
                }

                if ((checkFlag & (WARNING_CHANGE | MUST_CHANGE)) > 0) {
                    if (binding) {
                        Object taxObj = oldSource.getData("TAX");
                        if (taxObj instanceof Double) {
                            if (((Double) taxObj).doubleValue() < 0) {
                                binding = false;
                            }
                        }
                    }

                    if (!binding) {
                        // �ӌ����_���ݒ�ς݂̏ꍇ�Ɍ���
                        VRMap map = new VRHashMap();
                        map.putAll((VRHashMap) oldSource);
                        map.putAll(insurer);
                        pointGroup.setSource(map);
                        pointGroup.bindSource();
                        pointGroup.setSource(oldSource);
                        pointGroup.applySource();
                    }
                }

            }

            info.getOrgan().setJigyoushas();

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    protected void applySourceInnerBindComponent() throws Exception {
        super.applySourceInnerBindComponent();

        IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();
        if (info == null) {
            return;
        }

        VRBindSource source = getMasterSource();
        if (source == null) {
            return;
        }

        // ����E�����_�����͒��O�Ɍv�Z

        VRMap insurer = getSelectedInsurer();
        if (insurer != null) {
            // //�t�B�[���h�|��
            // if(VRBindPathParser.has("SEIKYUSHO_OUTPUT_PATTERN", insurer)){
            // source.setData("OUTPUT_PATTERN",
            // new VRInteger(String.valueOf(
            // VRBindPathParser.get("SEIKYUSHO_OUTPUT_PATTERN",
            // insurer))));
            // }

            VRPanel pointGroup = info.getBill();
            pointGroup.setSource(source);
            pointGroup.bindSource();

            // �ی��ґI��
            int flag;
            flag = 0;
            if (mentionCareType.getSelectedIndex() == 2) {
                flag += 2;
            }
            if (!info.getApplicant().isFirstCreate()) {
                flag += 1;
            }
            source.setData("IKN_CHARGE", new Integer(flag));

            if (VRBindPathParser.has("TAX", source)) {
                // �ی��ҕύX�̍ۂ�TAX���ݒ肳��Ă��Ȃ���΁A���̎��_��TAX��ݒ肷��B
                Object obj = VRBindPathParser.get("TAX", source);
                if (obj instanceof Double) {
                     if (((Double) obj).doubleValue()<0) {
                        // �ŋ�
                        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                        IkenshoCommon.setTax(dbm, (VRMap) source);
                        dbm.finalize();
                    }
                }
            }

        } else {
            // �ی��Җ��I��
            source.setData("IKN_CHARGE", new Integer(0));
        }

        // VRPanel pointGroup = info.getBill();
        // pointGroup.setSource(source);
        // pointGroup.bindSource();

    }

    /**
     * ���{�݂̕ύX���������܂��B
     * 
     * @param e �C�x���g���
     */
    protected void changeShisetsu(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            Object nowSelect = mentionShisetsu2.getSelectedItem();

            Object[] newArray = new Object[mentionShisetsu1.getItemCount() - 1];
            int i = 0;
            int end = mentionShisetsu1.getSelectedIndex();
            for (; i < end; i++) {
                newArray[i] = mentionShisetsu1.getItemAt(i);
            }
            end = newArray.length;
            for (; i < end; i++) {
                newArray[i] = mentionShisetsu1.getItemAt(i + 1);
            }

            VRArrayList array = new VRArrayList(Arrays.asList(newArray));
            mentionShisetsu2.setModel(new ACComboBoxModelAdapter(array));

            if (mentionShisetsu1.getSelectedItem().equals(nowSelect)) {
                mentionShisetsu2.setSelectedIndex(-1);
            } else if (IkenshoCommon.isNullText(mentionShisetsu1
                    .getSelectedItem())) {
                // ���s
                mentionShisetsu2.setEnabled(false);
                mentionShisetsu2Head.setEnabled(false);
                mentionShisetsu2.setSelectedIndex(-1);
            } else {
                mentionShisetsu2.setSelectedItem(nowSelect);
                mentionShisetsu2.setEnabled(true);
                mentionShisetsu2Head.setEnabled(true);
            }

        }

    }

    public Component getPreviewFocusComponent() {
        return mentionAddHokensha;
    }

    /**
     * �I�𒆂̕ی��҃f�[�^��Ԃ��܂��B
     * 
     * @return �I�𒆂̕ی��҃f�[�^
     * @see �Y�����Ȃ����null��Ԃ��܂��B
     */
    public VRMap getSelectedInsurer() {
        if ((mentionHokenName.getSelectedIndex() < 1) || (insurers == null)) {
            return null;
        }
        return (VRMap) insurers.getData(mentionHokenName.getSelectedIndex());
        // return (VRHashMap)
        // insurers.getData(mentionHokenName.getSelectedIndex()-1);
    }

    /**
     * �����_�����͉�ʂ�\�����܂��B
     */
    protected void showInputTestPoint() {
        if (mentionCareType.getSelectedIndex() == mentionCareType
                .getNoSelectIndex()) {
            ACMessageBox.showExclamation("��ʂ�I�����Ă��������B");
            mentionCareType.requestChildFocus();
            return;
        }
        // if ( (mentionHokenName.getSelectedItem() == null) ||
        if ((mentionHokenName.getSelectedIndex() < 1)
                || ("".equals(mentionHokenName.getSelectedItem()))) {
            ACMessageBox.showExclamation("�ی��҂����X�g���I�����Ă��������B");
            mentionHokenName.requestFocus();
            return;
        }

        if (getMasterAffair() != null) {
            IkenshoIkenshoInfo info = (IkenshoIkenshoInfo) getMasterAffair();

            if (!info.getOrgan().hasDoctorName()) {
                ACMessageBox.showExclamation("��t���������X�g���I�����Ă��������B");
                getMasterAffair().setSelctedTab(info.getOrgan());
                info.getOrgan().getDoctorName().requestFocus();
                return;
            }

            try {
                info.fullApplySource();

                // ���p���p�����[�^�̐ݒ�
                int flag = 0;
                switch (mentionCareType.getSelectedIndex()) {
                case 1:
                    flag |= IkenshoConsultationInfo.STATE_ZAITAKU;
                    break;
                case 2:
                    flag |= IkenshoConsultationInfo.STATE_SHISETSU;
                    break;
                }
                if (info.getApplicant().isFirstCreate()) {
                    flag |= IkenshoConsultationInfo.STATE_FIRST;
                } else {
                    flag |= IkenshoConsultationInfo.STATE_EVER;
                }

                VRMap map = (VRMap) getMasterSource();
                
                // 2006/12/11[Tozo Tanaka] : add begin
                if (IkenshoCommon.isConvertedNoBill(map)) {
                    //�ی��ґI���ς݂��������o�̓p�^�[����0���ȍ~����Ő����f�[�^���s��
                    ACMessageBox
                            .showExclamation("�����f�[�^���ڍs����Ă��Ȃ����߁A������p�̓��͂��o���܂���B"
                                    + ACConstants.LINE_SEPARATOR
                                    + "�ی��҂���ш�Ë@�ւ��đI�����Ă��������B");
                    return;
                }
                // 2006/12/11[Tozo Tanaka] : add end
                
                
                if (new IkenshoConsultationInfo(map, flag).showModal()) {
                    // �X�V����
                    info.getBill().bindSource();
                }
            } catch (Exception ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }
        }
    }

    /**
     * ��Ë@�֓o�^��ʂ�\�����܂��B
     */
    public void showRegistHokensya() {
        try {
            VRMap param = new VRHashMap();
            param.setData("ACT", "insert");

            savePreviewData(param);

            ACFrame.getInstance().next(
                    new ACAffairInfo(IkenshoHokenshaShousai.class.getName(),
                            param, "�ی��ҏڍ�"));

        } catch (Exception ex1) {
            IkenshoCommon.showExceptionMessage(ex1);
        }
    }

    private void jbInit() throws Exception {
        mentionCareType.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "�P�D�ݑ�", "�Q�D�{��" }))));
        mentionCareType.setUseClearButton(false);

        setLayout(mentionLayout);
        mentionShisetsusLayout.setAutoWrap(false);
        mentionShisetsus.setLayout(mentionShisetsusLayout);

        mentionTokkiAbstractions.setLayout(new VRLayout());
        VRLayout mentionDatesLayout = new VRLayout();
        mentionDatesLayout.setHgap(0);
        mentionDates.setLayout(mentionDatesLayout);
        mentionInsurers.setLayout(mentionInsurersLayout);

        mentionCreateDates.setText("�쐬�˗���");
        mentionHasegawa1.setMaxLength(3);
        mentionHasegawa1.setBindPath("HASE_SCORE");
        mentionHasegawa1.setColumns(3);
        mentionHasegawa1.setHorizontalAlignment(SwingConstants.RIGHT);
        mentionHasegawa1.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        mentionHasegawa1.setIMEMode(InputSubset.LATIN_DIGITS);
        mentionShinseiDate.setBindPath("SINSEI_DT");
        mentionShinseiDate.setAgeVisible(false);
        mentionHasegawaDay1.setBindPath("HASE_SCR_DT");
        mentionHasegawaDay1.setAgeVisible(false);
        mentionHasegawaDay1.setDayVisible(false);
        mentionHasegawaDay1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        mentionHokenNo.setEditable(false);
        mentionHokenNo.setColumns(10);
        mentionHokenNo.setBindPath("INSURER_NO");
        mentionLayout.setFitHLast(true);
        mentionLayout.setFitVLast(true);
        mentionHasegawaDay2.setAgeVisible(false);
        mentionHasegawaDay2.setDayVisible(false);
        mentionHasegawaDay2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
        mentionHasegawaDay2.setBindPath("P_HASE_SCR_DT");
        mentionTokkiMoreAbstractions.setLayout(new BorderLayout());
        mentionHiHokenNo.setColumns(10);
        mentionHiHokenNo.setCharType(VRCharType.ONLY_ALNUM);
        mentionHiHokenNo.setBindPath("INSURED_NO");
        mentionHiHokenNo.setMaxLength(10);
        mentionHasegawasLayout.setAutoWrap(false);
        mentionHasegawas.setLayout(mentionHasegawasLayout);
        mentionHasegawas.setText("���J�쎮 ��");
        mentionHasegawas
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        mentionHasegawas
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        mentionHasegawas.setContentAreaFilled(true);
        mentionSeikyushoGroup.setLayout(mentionSeikyushoGroupLayout);
        mentionSeikyushoGroup
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        mentionSeikyushoGroup.setText("�ȉ��̍��ڂ͐������쐬�p�̍��ڂł��i�K�{���ڂ͎����̂ɂ��قȂ�܂��j");
        mentionShisetsu2.setEnabled(false);
        mentionShisetsu2.setEditable(false);
        mentionShisetsu2.setBindPath("INST_SEL_PR2");
        mentionShisetsus.setText("�{�ݑI���i�D��x�j");
        mentionShisetsu2Head.setEnabled(false);
        mentionShisetsu2Head.setText("�@�Q�D");
        // mentionTokkiAbstraction2.setText("���ʂ��L�ڂ��ĉ������B");
        mentionHokenName.setEditable(false);
        mentionHokenName.setIMEMode(InputSubset.KANJI);
        mentionHokenName.setMinLength(100);
        mentionOrderNo.setColumns(10);
        mentionOrderNo.setBindPath("REQ_NO");
        mentionOrderNo.setMaxLength(10);
        mentionTokkiMoreAbstraction
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        mentionOrderNo.setCharType(VRCharType.ONLY_ALNUM);
        mentionShisetsu1.setPreferredSize(new Dimension(250, 19));
        mentionShisetsu1.setEditable(false);
        mentionShisetsu1.setBindPath("INST_SEL_PR1");
        mentionTokkiGroup.setLayout(mentionTokkiGroupLayout);
        mentionTokkiGroup.setText("���̑����L���ׂ�����");
        mentionHasegawa2.setColumns(3);
        mentionHasegawa2.setHorizontalAlignment(SwingConstants.RIGHT);
        mentionHasegawa2.setBindPath("P_HASE_SCORE");
        mentionHasegawa2.setMaxLength(3);
        mentionHasegawa2.setIMEMode(InputSubset.LATIN_DIGITS);
        mentionHasegawa2.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        mentionSendDates.setText("���t��");
        mentionHasegawa1Tail.setText("�_");
        mentionCreateDate.setBindPath("REQ_DT");
        mentionCreateDate.setAgeVisible(false);
        mentionTokki.setColumns(100);
        mentionTokki.setLineWrap(true);
        mentionTokki.setRows(8);
        mentionTokki.setBindPath("IKN_TOKKI");
        mentionTokki.setMaxLength(400);
        // mentionTokki.setMaxColumns(100);
        // mentionTokki.setUseMaxRows(true);
        mentionTokki.setMaxRows(mentionTokki.getRows());
        mentionTokki.setIMEMode(InputSubset.KANJI);
        mentionAddHokensha.setToolTipText("�u�ی��ғo�^�v��ʂ�\�����܂��B");
        mentionAddHokensha.setMnemonic('H');
        mentionAddHokensha.setText("�ی��ғo�^(H)");
        mentionHasegawa2Tail.setText("�_");
        mentionShinseiDates.setText("�\����");
        mentionShowTokkiMoreAbstractionChooser.setBounds(new Rectangle(0, 0,
                113, 23));
        mentionShowTokkiMoreAbstractionChooser
                .setToolTipText("�u���̑����L�����v�I����ʂ�\�����܂��B");
        mentionShowTokkiMoreAbstractionChooser.setMnemonic('T');
        mentionShowTokkiMoreAbstractionChooser.setText("���L�����I��(T)");
        mentionTokkiMoreAbstraction.setText("�ȉ��̍��ڂ́u�厡��ӌ����v�̕K�{���ڂł͂���܂���B");
        mentionTokkiMoreAbstraction.setBounds(new Rectangle(0, 0, 297, 15));
        mentionShisetsu1Head.setText("�P�D");
        mentionCareType.setMinimumSize(new Dimension(27, 23));
        mentionCareType.setBindPath("KIND");
        mentionOrderNos.setText("�˗��ԍ�");
        mentionSendDate.setBindPath("SEND_DT");
        mentionSendDate.setAllowedFutureDate(true);
        mentionSendDate.setAgeVisible(false);

        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.indexOf("Mac") >= 0)) {
            // Mac��"�����\�������"
            mentionHiHokenNos.setText("��ی��Ҕԍ�");
        } else {
            mentionHiHokenNos.setText("��ی��Ҕԍ��i�p��10���j");
        }

        mentionHiHokenNos.setContentAreaFilled(true);
        mentionTitle.setText("�T�D���̑����L���ׂ�����");
        mentionAddKensa.setText("�f�@�E�������e����(K)");
        mentionAddKensa.setToolTipText("�u�f�@�E�������e���́v��ʂ�\�����܂��B");
        mentionAddKensa.setMnemonic('K');
        mentionAddKensa
                .setBackground(IkenshoConstants.COLOR_BUTTON_GREEN_FOREGROUND);
        mentionHokenNames.setText("�ی��ҁi�ԍ��j");
        mentionHokenNames.setLayout(new BorderLayout());
        mentionTokkiGroupLayout.setFitVLast(true);
        mentionTokkiGroupLayout.setFitHLast(true);
        mentionCareTypes.setText("���");
        mentionSeikyushoGroupLayout.setHgrid(0);
        mentionSeikyushoGroupLayout.setHgap(0);
        mentionSeikyushoGroupLayout.setAutoWrap(false);
        mentionSeikyushoGroupLayout.setLabelMargin(0);
        mentionTokkiAbstraction1.setAutoWrap(true);
        mentionTokkiAbstraction1
                .setText("�v���F��ɕK�v�Ȉ�w�I�Ȉӌ������L�ڂ��ĉ������B�Ȃ��A���㓙�ɕʓr�ӌ������߂��ꍇ�͂��̓��e�A���ʂ��L�ڂ��ĉ������B");
        mentionTokkiAbstraction3.setText("�i400�����܂���8�s�ȓ��j");
        mentionTokkiAbstraction3
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        mentionHasegawaDays2.setChildFocusedOwner(false);
        ((VRLayout) mentionHasegawasHeses2.getLayout()).setAutoWrap(false);
        mentionHasegawasHeses2.setBeginText("�i�O��");
        hiddenParameters.setText("�B���p�����[�^");
        insureParameters.setText("Integer");
        s18.setBindPath("FD_OUTPUT_UMU");
        s19.setBindPath("HEADER_OUTPUT_UMU2");
        s110.setBindPath("HEADER_OUTPUT_UMU1");
        s111.setBindPath("SEIKYUSHO_OUTPUT_PATTERN");
        s112.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        s115.setBindPath("DR_NM_OUTPUT_UMU");
        s117.setBindPath("SOUKATU_FURIKOMI_PRT2");
        s118.setBindPath("FURIKOMISAKI_PRT2");
        s119.setBindPath("MEISAI_KIND2");
        s1110.setBindPath("SOUKATUHYOU_PRT2");
        s1111.setBindPath("FURIKOMISAKI_PRT");
        s1112.setBindPath("MEISAI_KIND");
        s1113.setBindPath("SOUKATUHYOU_PRT");
        s1114.setBindPath("SOUKATU_FURIKOMI_PRT");
        ikenshoGroupBox1.setText("String");
        insurerName.setBindPath("INSURER_NM");
        mentionInsurersLayout.setHgap(0);
        mentionInsurersLayout.setAutoWrap(false);
        mentionInsurersLayout.setLabelMargin(0);
        insureParameters.add(s1114, null);
        insureParameters.add(s1113, null);
        insureParameters.add(s1112, null);
        insureParameters.add(s1111, null);
        insureParameters.add(s1110, null);
        insureParameters.add(s119, null);
        insureParameters.add(s118, null);
        insureParameters.add(s117, null);
        insureParameters.add(s115, null);
        insureParameters.add(s112, null);
        insureParameters.add(s111, null);
        insureParameters.add(s110, null);
        insureParameters.add(s19, null);
        insureParameters.add(s18, null);
        mentionHasegawas.add(mentionHasegawas1, VRLayout.FLOW);
        mentionHasegawas.add(mentionHasegawaDays1, VRLayout.FLOW);
        mentionHasegawaDays1.add(mentionHasegawaDaysHeses1, null);
        mentionHasegawaDaysHeses1.add(mentionHasegawaDay1, null);
        mentionHasegawas1.add(mentionHasegawa1, null);
        mentionHasegawas1.add(mentionHasegawa1Tail, null);
        mentionTokkiAbstractions.add(mentionTokkiAbstraction1, VRLayout.NORTH);
        // mentionTokkiAbstractions.add(mentionTokkiAbstraction2,
        // VRLayout.FLOW);
        mentionTokkiAbstractions.add(mentionTokkiAbstraction3,
                VRLayout.FLOW_RETURN);
        mentionTokkiAbstractions
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        mentionTokkis.setLayout(new VRLayout());
        mentionTokkis.add(mentionTokki, VRLayout.LEFT);
        mentionTokkiMoreAbstractions.add(mentionTokkiMoreAbstraction,
                BorderLayout.CENTER);
        mentionTokkiMoreAbstractions.add(
                mentionShowTokkiMoreAbstractionChooser, BorderLayout.EAST);
        mentionTokkiGroup.add(mentionTokkiAbstractions, VRLayout.NORTH);
        mentionTokkiGroup.add(mentionTokkis, VRLayout.NORTH);
        mentionTokkiGroup.add(mentionTokkiMoreAbstractions, VRLayout.NORTH);
        mentionTokkiGroup.add(mentionHasegawas, VRLayout.NORTH);
        mentionTokkiGroup.add(mentionShisetsus, VRLayout.NORTH);
        mentionShisetsus.add(mentionShisetsu1Head, VRLayout.WEST);
        mentionShisetsus.add(mentionShisetsu1, VRLayout.WEST);
        mentionShisetsus.add(mentionShisetsu2Head, VRLayout.WEST);
        mentionShisetsus.add(mentionShisetsu2, VRLayout.CLIENT);
        mentionShinseiDates.add(mentionShinseiDate, null);
        mentionHiHokenNos.add(mentionHiHokenNo, null);
        mentionDates.add(mentionShinseiDates, VRLayout.FLOW_INSETLINE);
        mentionDates.add(mentionCreateDates, VRLayout.FLOW_INSETLINE);
        mentionDates.add(mentionSendDates, VRLayout.FLOW_INSETLINE);
        mentionSeikyushoGroup.add(mentionDates, VRLayout.WEST);

        mentionInsurers.add(mentionHiHokenNos, VRLayout.FLOW);
        mentionInsurers.add(mentionCareTypes, VRLayout.FLOW_RETURN);
        mentionInsurers.add(mentionOrderNos, VRLayout.FLOW);
        mentionInsurers.add(mentionAddHokensha, VRLayout.FLOW);
        mentionInsurers.add(mentionAddKensa, VRLayout.FLOW_RETURN);
        mentionInsurers.add(mentionHokenNames, VRLayout.FLOW_RETURN);
        mentionSeikyushoGroup.add(mentionInsurers, VRLayout.CLIENT);
        mentionCareTypes.add(mentionCareType, null);
        mentionCreateDates.add(mentionCreateDate, null);
        mentionOrderNos.add(mentionOrderNo, null);
        mentionSendDates.add(mentionSendDate, null);
        mentionHokenNames.add(mentionHokenName, BorderLayout.CENTER);
        mentionHokenNames.add(mentionHokenNoHeses, BorderLayout.EAST);
        mentionHokenNoHeses.add(mentionHokenNo, null);
        hiddenParameters.add(ikenshoGroupBox1, null);
        ikenshoGroupBox1.add(insurerName, null);
        hiddenParameters.add(insureParameters, null);
        this.add(mentionTitle, VRLayout.NORTH);
        this.add(mentionTokkiGroup, VRLayout.NORTH);
        this.add(mentionSeikyushoGroup, VRLayout.NORTH);
        this.add(hiddenParameters, VRLayout.SOUTH);
        mentionHasegawas2.add(mentionHasegawa2, null);
        mentionHasegawas2.add(mentionHasegawa2Tail, null);
        mentionHasegawas.add(mentionHasegawasHeses2, VRLayout.FLOW);
        mentionHasegawasHeses2.add(mentionHasegawas2, null);
        mentionHasegawasHeses2.add(mentionHasegawaDays2, null);
        mentionHasegawaDays2.add(mentionHasegawaDayHeses2, null);
        mentionHasegawaDayHeses2.add(mentionHasegawaDay2, null);
    }

}