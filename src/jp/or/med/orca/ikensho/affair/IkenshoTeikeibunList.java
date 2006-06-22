package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;


/** TODO <HEAD_IKENSYO> */
public class IkenshoTeikeibunList extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();

    private VRPanel client = new VRPanel();
    private VRPanel formatKbnPnl = new VRPanel();
    private ACLabelContainer formatKbnContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup formatKbn = new ACClearableRadioButtonGroup();
    private ACTable table = new ACTable();
    private VRArrayList data;

    private final String h17 = "����17�N�x";
    private final String h18 = "����18�N�x";

    public IkenshoTeikeibunList() {
        try {
            jbInit();
            event();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() {
        buttons.setTitle("���L�����ꗗ");
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        //�{�^��
        buttons.add(detail, BorderLayout.EAST);
        detail.setText("�ҏW(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("�ڍ׏��(E)");
        detail.setToolTipText("�I�����ꂽ��^���̕ҏW��ʂɈڂ�܂��B");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        //client
        client.setLayout(new VRLayout());
//        client.add(formatKbnPnl, VRLayout.NORTH);
        client.add(table, VRLayout.CLIENT);

/*
        //�V�E���I��
        formatKbnPnl.setLayout(new VRLayout());
        formatKbnPnl.add(formatKbnContainer, VRLayout.FLOW);
        formatKbnContainer.setText("�Ή��N�x");
        formatKbnContainer.add(formatKbn, null);
        VRLayout formatKbnLayout = new VRLayout();
        formatKbnLayout.setAutoWrap(false);
        formatKbn.setLayout(formatKbnLayout);
        formatKbn.setUseClearButton(false);
        formatKbn.setModel(new VRListModelAdapter(
            new VRArrayList(Arrays.asList(new String[] {
                                          h18,
                                          h17}))));
        formatKbn.setSelectedIndex(1);
*/

        //�X�e�[�^�X�o�[
        setStatusText("�ҏW��������^�����ڂ�I�����A�u�ҏW�v�{�^���������Ă��������B");
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        addDetailTrigger(detail);

        setTeikeibunData();
    }

    private void event() throws Exception {
        //�敪�I��
        formatKbn.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setTeikeibunData();
            }
        });

        //�e�[�u���N���b�N
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        //�ҏWDLG��
                        showIkenshoOptionDialog();
                    }
                    catch (Exception ex) {
                      IkenshoCommon.showExceptionMessage(ex);
                    }
                }
            }
        });
    }

    public ACAffairButtonBar getButtonBar() {
        return null;
    }

    public Component getFirstFocusComponent() {
        return formatKbn;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        return true;
    }

    public boolean canClose() {
        return true;
    }

    protected void detailActionPerformed(ActionEvent e) throws Exception {
        //�ҏWDLG��
        showIkenshoOptionDialog();
    }

    /**
     * ��^���f�[�^���e�[�u���ɐݒ肵�܂��B
     */
    private void setTeikeibunData() {
//        switch (formatKbn.getSelectedIndex()) {
//            case 1:
//                createDataH18();
//                break;
//            case 2:
//                createDataH17();
//                break;
//        }
        createDataH18();
        //�e�[�u���̃J�����𐶐�
        createColumn();
        //�s�̑I��
        setSelectedRow();
    }

    /**
     * �e�[�u���ɐݒ肷��f�[�^�𐶐����܂��B(H18�����O)
     */
    private void createDataH17() {
        data = new VRArrayList();
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_NAME, 30, "1-(1)", "", "�f�f��", "���a��"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_NAME, 12, "1-(4)", "", "��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e", "��ܖ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT,  4, "1-(4)", "", "��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e", "�p�ʒP��"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10, "1-(4)", "", "��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e", "�p�@"));
        data.add(createRow(IkenshoCommon.TEIKEI_MIND_SICK_NAME, 30, "3-(4)", "", "���_�E�_�o�Ǐ�̗L��", "���_�E�_�o�Ǐ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME, 10, "3-(5)", "", "�g�̂̏��", "�l�������E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_PARALYSIS_NAME, 10, "3-(5)", "", "�g�̂̏��", "��ჁE����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME, 10, "3-(5)", "", "�g�̂̏��", "�ؗ͂̒ቺ�E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME, 10, "3-(5)", "", "�g�̂̏��", "��ጁE����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME, 10, "3-(5)", "", "�g�̂̏��", "�畆�����E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "����"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�]�|�E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�p�j"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "���"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PNEUMONIA_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�������x��"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INTESTINES_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "����"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�Պ�����"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�S�x�@�\�̒ቺ"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PAIN_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�ɂ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "�E��"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME, 30, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "���̑��E�ڍ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15, "4-(1)", "", "���݁A�����̉\���������a�ԂƂ��̑Ώ����j", "���̑��E���ږ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30, "4-(3)", "", "���T�[�r�X�ɂ������w�I�ϓ_����̗��ӎ���", "�����ɂ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30, "4-(3)", "", "���T�[�r�X�ɂ������w�I�ϓ_����̗��ӎ���", "�����ɂ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30, "4-(3)", "", "���T�[�r�X�ɂ������w�I�ϓ_����̗��ӎ���", "�ېH�ɂ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30, "4-(3)", "", "���T�[�r�X�ɂ������w�I�ϓ_����̗��ӎ���", "�ړ��ɂ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50, "4-(3)", "", "���T�[�r�X�ɂ������w�I�ϓ_����̗��ӎ���", "���̑�"));
        data.add(createRow(IkenshoCommon.TEIKEI_INFECTION_NAME, 30, "4-(4)", "", "�����ǂ̗L��", "�L�̏ꍇ"));
        data.add(createRow(IkenshoCommon.TEIKEI_MENTION_NAME, 50, "5", "", "���̑����L���ׂ�����", "���̑����L���ׂ�����"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_TYPE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�l�H�ċz�����"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�l�H�ċz��ݒ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_TYPE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�o�ǉh�{���@"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_SIZE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�o�ǉh�{�T�C�Y"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�o�ǉh�{����"));
        data.add(createRow(IkenshoCommon.TEIKEI_CANURE_SIZE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�C�ǃJ�j���[���T�C�Y"));
        data.add(createRow(IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�h���[������"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_SIZE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "���u�J�e�[�e�[���T�C�Y"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "���u�J�e�[�e������"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU, 50, "", "��", "���ӎ����y�юw������", "�×{�����w����̗��ӎ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION, 50, "", "��", "���ӎ����y�юw������", "���n�r���e�[�V����"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU, 50, "", "��", "���ӎ����y�юw������", "��ጂ̏��u��"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO, 50, "", "��", "���ӎ����y�юw������", "���u�E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_SONOTA, 50, "", "��", "���ӎ����y�юw������", "���̑�"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TOKKI, 50, "", "��", "���ӎ����y�юw������", "���L���ׂ����ӎ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA, 50, "", "��", "�ݑ�ҖK��_�H���˂Ɋւ���w��", "�ݑ�K��_�H����"));
    }

    /**
     * �e�[�u���ɐݒ肷��f�[�^�𐶐����܂��B(H18������)
     */
    private void createDataH18() {
        data = new VRArrayList();
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_NAME, 30, "1-(1)", "", "�f�f��", "���a��"));
        data.add(createRow(IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME, 30, "1-(2)", "", "�Ǐ�Ƃ��Ă̈��萫", "�u�s����v�Ƃ����ꍇ�̋�̓I��"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_NAME, 12, "1-(3)", "", "�����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a�܂��͓��莾�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e", "��ܖ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT,  4, "1-(3)", "", "�����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a�܂��͓��莾�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e", "�p�ʒP��"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10, "1-(3)", "", "�����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a�܂��͓��莾�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e", "�p�@"));
        data.add(createRow(IkenshoCommon.TEIKEI_MIND_SICK_NAME, 30, "3-(4)", "", "���̑��̐��_�E�_�o�Ǐ�", "���_�E�_�o�Ǐ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME, 10, "3-(5)", "", "�g�̂̏��", "�l�������E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME, 10, "3-(5)", "", "�g�̂̏��", "���(���̑�)�E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME, 10, "3-(5)", "", "�g�̂̏��", "�ؗ͂̒ቺ�E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME, 10, "3-(5)", "", "�g�̂̏��", "�֐߂̍S�k�E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME, 10, "3-(5)", "", "�g�̂̏��", "�֐߂̒ɂ݁E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME, 10, "3-(5)", "", "�g�̂̏��", "��ጁE����"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME, 10, "3-(5)", "", "�g�̂̏��", "���̑��̔畆�����E����"));
//        data.add(createRow(49, 25, "4-(2)", "", "�h�{�E�H����", "����̐H���ێ��"));
//        data.add(createRow(50, 25, "4-(2)", "", "�h�{�E�H����", "�H�~"));
//        data.add(createRow(51, 25, "4-(2)", "", "�h�{�E�H����", "���݂̉h�{���"));
        data.add(createRow(IkenshoCommon.TEIKEI_EATING_RYUIJIKOU_NAME, 30, "4-(2)", "", "�h�{�E�H����", "�h�{�E�H������̗��ӓ_"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�A����"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�]�|�E����"));
        data.add(createRow(IkenshoCommon.TEIKEI_MOVILITY_DOWN_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�ړ��\�͂̒ቺ"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "���"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�S�x�@�\�̒ቺ"));
        data.add(createRow(IkenshoCommon.TEIKEI_TOJIKOMORI_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "��������"));
        data.add(createRow(IkenshoCommon.TEIKEI_IYOKU_DOWN_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�ӗ~�ቺ"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�p�j"));
        data.add(createRow(IkenshoCommon.TEIKEI_LOW_ENERGY_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "��h�{"));
        data.add(createRow(IkenshoCommon.TEIKEI_SESSHOKU_ENGE_DOWN_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�ېH�E�����@�\�ቺ"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�E��"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "�Պ�����"));
        data.add(createRow(IkenshoCommon.TEIKEI_GAN_TOTSU_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "���񓙂ɂ���u��"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME, 30, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "���̑��E�ڍ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15, "4-(3)", "", "���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j", "���̑��E���ږ�"));
//        data.add(createRow(IkenshoCommon.TEIKEI_OUTLOOK_SERVISE_NAME, 30, "4-(4)", "", "���̕K�v�̒��x�Ɋւ���\��̌��ʂ�", "���P�ւ̊�^�����҂ł���T�[�r�X"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30, "4-(6)", "", "�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ���", "����"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30, "4-(6)", "", "�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ���", "�ېH"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30, "4-(6)", "", "�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ���", "����"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30, "4-(6)", "", "�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ���", "�ړ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_UNDOU_NAME, 30, "4-(6)", "", "�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ���", "�^��"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50, "4-(6)", "", "�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ���", "���̑�"));
        data.add(createRow(IkenshoCommon.TEIKEI_INFECTION_NAME, 30, "4-(7)", "", "�����ǂ̗L��", "�L�̏ꍇ"));
        data.add(createRow(IkenshoCommon.TEIKEI_MENTION_NAME, 50, "5", "", "���̑����L���ׂ�����", "���̑����L���ׂ�����"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_TYPE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�l�H�ċz�����"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�l�H�ċz��ݒ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_TYPE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�o�ǉh�{���@"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_SIZE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�o�ǉh�{�T�C�Y"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�o�ǉh�{����"));
        data.add(createRow(IkenshoCommon.TEIKEI_CANURE_SIZE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�C�ǃJ�j���[���T�C�Y"));
        data.add(createRow(IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "�h���[������"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_SIZE,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "���u�J�e�[�e�[���T�C�Y"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN,  5, "", "��", "���݂̏󋵁^�����E��Ë@�퓙", "���u�J�e�[�e������"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU, 50, "", "��", "���ӎ����y�юw������", "�×{�����w����̗��ӎ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION, 50, "", "��", "���ӎ����y�юw������", "���n�r���e�[�V����"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU, 50, "", "��", "���ӎ����y�юw������", "��ጂ̏��u��"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO, 50, "", "��", "���ӎ����y�юw������", "���u�E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_SONOTA, 50, "", "��", "���ӎ����y�юw������", "���̑�"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TOKKI, 50, "", "��", "���ӎ����y�юw������", "���L���ׂ����ӎ���"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA, 50, "", "��", "�ݑ�ҖK��_�H���˂Ɋւ���w��", "�ݑ�K��_�H����"));
    }

    /**
     * 1�s���̃f�[�^�𐶐�
     * @param tkb_kbn ��^���敪
     * @param ikn_kbn �ӌ���
     * @param sis_kbn �w����
     * @param group ����
     * @param item ����
     * @return 1�s
     */
    private VRMap createRow(int tkb_kbn, int length, String ikn_kbn, String sis_kbn, String group, String item) {
        VRMap tmp = new VRHashMap();
        tmp.setData("TKB_KBN", String.valueOf(tkb_kbn));
        tmp.setData("LENGTH", String.valueOf(length));
        tmp.setData("IKN_KBN", ikn_kbn);
        tmp.setData("SIS_KBN", sis_kbn);
        tmp.setData("GROUP", group);
        tmp.setData("ITEM", item);
        return tmp;
    }

    /**
     * ��̐���
     */
    private void createColumn() {
        //�e�[�u���̐���
        table.setModel(new ACTableModelAdapter(data, new String[] {
                                                    "TKB_KBN",
                                                    "LENGTH",
                                                    "IKN_KBN",
                                                    "SIS_KBN",
                                                    "GROUP",
                                                    "ITEM"}));

        //ColumnModel�̐���
        table.setColumnModel(new VRTableColumnModel(
            new VRTableColumn[] {
            new VRTableColumn(2, 50, "�ӌ���", SwingConstants.CENTER),
            new VRTableColumn(3, 50, "�w����", SwingConstants.CENTER),
            new VRTableColumn(4, 395, "����"),
            new VRTableColumn(5, 270, "���ږ�")
            }));
    }

    /**
     * ��^���ҏWDLG��\�����܂��B
     * @throws Exception
     */
    private void showIkenshoOptionDialog() throws Exception {
        //�I���s�̎擾
        int selectedRow = table.getSelectedModelRow();
        if (selectedRow < 0) {
            return;
        }
        if (selectedRow >= table.getRowCount()) {
            return;
        }

        //Row�̎擾�`DLG�\��
        VRMap row = (VRMap)data.getData(table.getSelectedModelRow());
        int selectedTkbKbn = Integer.parseInt(row.getData("TKB_KBN").toString());
        IkenshoTeikeibunEdit dlg = new IkenshoTeikeibunEdit(
            row.getData("ITEM").toString(),
            IkenshoTeikeibunEdit.TEIKEIBUN,
            selectedTkbKbn,
            Integer.parseInt(row.getData("LENGTH").toString()));
        dlg.setVisible(true);
//        dlg.show();
    }

    /**
     * �e�[�u���̍s��I�����܂��B
     * @throws Exception
     */
    private void setSelectedRow(){
        if (table.getRowCount() > 0) {
//        if (table.getTable().getRowCount() > 0) {
            detail.setEnabled(true);
            table.setSelectedModelRow(0);
        }
        else {
            detail.setEnabled(false);
        }
    }
}
