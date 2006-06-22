package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.JRadioButton;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoUndelineIntegerCheckBox;
import jp.or.med.orca.ikensho.component.IkenshoUnderlineFormatableLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoCare2 extends
        IkenshoTabbableChildAffairContainer {
    private IkenshoUndelineIntegerCheckBox care2HoumonShikaEisei = new IkenshoUndelineIntegerCheckBox();
    private ACValueArrayRadioButtonGroup care2Kansen;
    private ACComboBox care2KansenName;
    private ACLabelContainer care2HoumonEiyous = new ACLabelContainer();
    private IkenshoUnderlineFormatableLabel care2HoumonAbstract2 = new IkenshoUnderlineFormatableLabel();
    private IkenshoUndelineIntegerCheckBox care2HoumonYakuzai = new IkenshoUndelineIntegerCheckBox();
    private IkenshoDocumentTabTitleLabel care2Title = new IkenshoDocumentTabTitleLabel();
    private ACGroupBox care2KansenGroup = new ACGroupBox();
    private ACTextField care2HoumonOtherValue = new ACTextField();
    private ACGroupBox care2ServiceGroup = new ACGroupBox();
    private VRLayout careLayout = new VRLayout();
    private IkenshoUndelineIntegerCheckBox care2HoumonsKango = new IkenshoUndelineIntegerCheckBox();
    private ACComboBox care2ServiceOtherValue = new ACComboBox();
    private ACComboBox care2SesshokuValue = new ACComboBox();
    private IkenshoUndelineIntegerCheckBox care2TsuushyoReha = new IkenshoUndelineIntegerCheckBox();
    private IkenshoUndelineIntegerCheckBox care2TankiNyuushyoKaigo = new IkenshoUndelineIntegerCheckBox();
    private ACLabelContainer care2HoumonOthers = new ACLabelContainer();
    private ACLabelContainer care2HoumonRehas = new ACLabelContainer();
    private ACComboBox care2KetsuattsuValue = new ACComboBox();
    private ACLabelContainer care2ServiceOthers = new ACLabelContainer();
    private IkenshoAddWestClearableRadioButtonGroup care2Move = new IkenshoAddWestClearableRadioButtonGroup();
    // private NCClearableRadioButtonGroup care2Move = new
    // NCClearableRadioButtonGroup();
    private ACLabelContainer care2HoumonShikaYakuzais = new ACLabelContainer();
    private VRLayout care2GroupLayout1 = new VRLayout();
    private ACLabelContainer care2Enges = new ACLabelContainer();
    private ACGroupBox needManagementGroup;
    private ACLabelContainer care2TsuushyoRehas = new ACLabelContainer();
    private ACLabelContainer care2Sesshokus = new ACLabelContainer();
    private ACLabelContainer care2Ketsuattsus = new ACLabelContainer();
    private ACLabelContainer care2Moves = new ACLabelContainer();
    private VRPanel care2Needs3 = new VRPanel();
    private ACLabelContainer care2HoumonKangos = new ACLabelContainer();
    private ACComboBox care2EngeValue = new ACComboBox();
    private IkenshoAddWestClearableRadioButtonGroup care2Sesshoku = new IkenshoAddWestClearableRadioButtonGroup();
    // private NCClearableRadioButtonGroup care2Sesshoku = new
    // NCClearableRadioButtonGroup();
    private ACLabelContainer care2Kansens = new ACLabelContainer();
    private IkenshoUndelineIntegerCheckBox care2HoumonShikaShinryou = new IkenshoUndelineIntegerCheckBox();
    private IkenshoUndelineIntegerCheckBox care2HoumonOther;
    private IkenshoUndelineIntegerCheckBox care2HoumonReha = new IkenshoUndelineIntegerCheckBox();
    private ACLabelContainer care2HoumonShinryous = new ACLabelContainer();
    private VRLayout care2ServiceGroupLayout = new VRLayout();
    private VRPanel care2Needs2 = new VRPanel();
    private IkenshoUnderlineFormatableLabel care2HoumonAbstract1 = new IkenshoUnderlineFormatableLabel();
    private IkenshoUndelineIntegerCheckBox care2HoumonShinryou = new IkenshoUndelineIntegerCheckBox();
    private IkenshoAddWestClearableRadioButtonGroup care2Enge = new IkenshoAddWestClearableRadioButtonGroup();
    // private NCClearableRadioButtonGroup care2Enge = new
    // NCClearableRadioButtonGroup();
    private ACComboBox care2MoveValue = new ACComboBox();
    private IkenshoAddWestClearableRadioButtonGroup care2Ketsuattsu = new IkenshoAddWestClearableRadioButtonGroup();
    // private NCClearableRadioButtonGroup care2Ketsuattsu = new
    // NCClearableRadioButtonGroup();
    private ACLabelContainer care2HoumonShikaEiseis = new ACLabelContainer();
    private VRLayout care2Needs1Layout = new VRLayout();
    private VRLayout care2Needs2Layout = new VRLayout();
    private VRLayout care2Needs3Layout = new VRLayout();
    private IkenshoUndelineIntegerCheckBox care2HoumonEiyou = new IkenshoUndelineIntegerCheckBox();
    private ACLabelContainer care2TankiNyuushyoKaigos = new ACLabelContainer();
    private ACLabelContainer care2HoumonShikaShinryous = new ACLabelContainer();
    private VRPanel care2Needs1 = new VRPanel();

    private VRListModelAdapter care2KansenModel = new VRListModelAdapter(
            new VRArrayList(Arrays.asList(new String[] { "�L", "��", "�s��" })));
    private VRListModelAdapter careServiceListModel;
    // = new VRListModelAdapter(new
    // VRArrayList(Arrays.asList(new
    // String[] {"���ɂȂ� ", "�L"})));
    private ACParentHesesPanelContainer care2HoumonOtherHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer care2ServiceOtherHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer care2MoveHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer care2SesshokuHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer care2KetsuattsuHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer care2EngeHeses = new ACParentHesesPanelContainer();


    //2006/02/07[Tozo Tanaka] : add begin 
    /**
     * ���̑��̈�w�I�Ǘ��̕K�v����Ԃ��܂��B
     * 
     * @return ���̑��̈�w�I�Ǘ��̕K�v��
     */
    protected IkenshoUndelineIntegerCheckBox getCare2HoumonOther() {
        if (care2HoumonOther == null) {
            care2HoumonOther = new IkenshoUndelineIntegerCheckBox();
        }
        return care2HoumonOther;
    }
    /**
     * ��w�I�Ǘ��̕K�v���O���[�v��Ԃ��܂��B
     * 
     * @return ���̑��̈�w�I�Ǘ��̕K�v��
     */
    protected ACGroupBox getNeedManagementGroup() {
        if (needManagementGroup == null) {
            needManagementGroup = new ACGroupBox();
        }
        return needManagementGroup;
    }

    //2006/02/07[Tozo Tanaka] : add end 
    
    /**
     * �^���ɂ��ă��W�I�O���[�v���f����Ԃ��܂��B
     * 
     * @return �^���ɂ��ă��W�I�O���[�v���f��
     */
    protected VRListModelAdapter getCareServiceListModel() {
        if (careServiceListModel == null) {
            careServiceListModel = new VRListModelAdapter(new VRArrayList(
                    Arrays.asList(new String[] { "���ɂȂ��@", "�L" })));
        }
        return careServiceListModel;
    }

    /**
     * �^�C�g���\�����x����Ԃ��܂��B
     * 
     * @return �^�C�g���\�����x��
     */
    protected IkenshoDocumentTabTitleLabel getTitle() {
        return care2Title;
    }

    /**
     * �����R���e�i��Ԃ��܂��B
     * 
     * @return �����R���e�i
     */
    protected ACLabelContainer getKetsuattsus() {
        return care2Ketsuattsus;
    }

    /**
     * �����R���e�i��Ԃ��܂��B
     * 
     * @return �����R���e�i
     */
    protected ACLabelContainer getEnges() {
        return care2Enges;
    }

    /**
     * �ېH�R���e�i��Ԃ��܂��B
     * 
     * @return �ېH�R���e�i
     */
    protected ACLabelContainer getSesshokus() {
        return care2Sesshokus;
    }

    /**
     * �ړ��R���e�i��Ԃ��܂��B
     * 
     * @return �ړ��R���e�i
     */
    protected ACLabelContainer getMoves() {
        return care2Moves;
    }

    /**
     * ���̑��̃T�[�r�X�R���e�i��Ԃ��܂��B
     * 
     * @return ���̑��̃T�[�r�X�R���e�i
     */
    protected ACLabelContainer getServiceOthers() {
        return care2ServiceOthers;
    }


    /**
     * �T�[�r�X�O���[�v��Ԃ��܂��B
     * 
     * @return �T�[�r�X�O���[�v
     */
    protected ACGroupBox getServiceGroup() {
        return care2ServiceGroup;
    }

    /**
     * �����ǃO���[�v��Ԃ��܂��B
     * 
     * @return �����ǃO���[�v
     */
    protected ACGroupBox getKansenGroup() {
        return care2KansenGroup;
    }

    /**
     * �K��f�ÃR���e�i��Ԃ��܂��B
     * 
     * @return �K��f�ÃR���e�i
     */
    protected ACLabelContainer getHoumonsShinryous() {
        return care2HoumonShinryous;
    }

    /**
     * �K��Ō�R���e�i��Ԃ��܂��B
     * 
     * @return �K��Ō�R���e�i
     */
    protected ACLabelContainer getHoumonsKangos() {
        return care2HoumonKangos;
    }

    /**
     * v���n�r���e�[�V�����R���e�i��Ԃ��܂��B
     * 
     * @return �K�⃊�n�r���e�[�V����
     */
    protected ACLabelContainer getHoumonRehas() {
        return care2HoumonRehas;
    }

    /**
     * �ʏ����n�r���e�[�V�����R���e�i��Ԃ��܂��B
     * 
     * @return �ʏ����n�r���e�[�V����
     */
    protected ACLabelContainer getTsuushyoRehas() {
        return care2TsuushyoRehas;
    }

    /**
     * �Z�������×{�Ō�R���e�i��Ԃ��܂��B
     * 
     * @return �Z�������×{�Ō�R���e�i
     */
    protected ACLabelContainer getTankiNyuushyoKaigos() {
        return care2TankiNyuushyoKaigos;
    }

    /**
     * �K�⎕�Ȑf�ÃR���e�i��Ԃ��܂��B
     * 
     * @return �K�⎕�Ȑf�ÃR���e�i
     */
    protected ACLabelContainer getHoumonShikaShinryous() {
        return care2HoumonShikaShinryous;
    }

    /**
     * �K�⎕�ȉq���w���R���e�i��Ԃ��܂��B
     * 
     * @return �K�⎕�ȉq���w���R���e�i
     */
    protected ACLabelContainer getHoumonShikaEiseis() {
        return care2HoumonShikaEiseis;
    }

    /**
     * �K���܊Ǘ��w���R���e�i��Ԃ��܂��B
     * 
     * @return �K���܊Ǘ��w���R���e�i
     */
    protected ACLabelContainer getHoumonYakuzais() {
        return care2HoumonShikaYakuzais;
    }

    /**
     * �K��h�{�H���w���R���e�i��Ԃ��܂��B
     * 
     * @return �K��h�{�H���w���R���e�i
     */
    protected ACLabelContainer getHoumonEiyous() {
        return care2HoumonEiyous;
    }

    /**
     * ��w�I�Ǘ��̕K�v��-���̑��R���e�i��Ԃ��܂��B
     * 
     * @return ��w�I�Ǘ��̕K�v��-���̑�
     */
    protected ACLabelContainer getHoumonOthers() {
        return care2HoumonOthers;
    }

    /**
     * ��w�I�Ǘ��̕K�v��-�⑫1��Ԃ��܂��B
     * 
     * @return ��w�I�Ǘ��̕K�v��-�⑫1
     */
    protected IkenshoUnderlineFormatableLabel getHoumonAbstract1() {
        return care2HoumonAbstract1;
    }

    /**
     * ��w�I�Ǘ��̕K�v��-�⑫2��Ԃ��܂��B
     * 
     * @return ��w�I�Ǘ��̕K�v��-�⑫2
     */
    protected IkenshoUnderlineFormatableLabel getHoumonAbstract2() {
        return care2HoumonAbstract2;
    }

    /**
     * �����ǂ̗L�����W�I�O���[�v��Ԃ��܂��B
     * 
     * @return �����ǂ̗L�����W�I�O���[�v
     */
    protected ACValueArrayRadioButtonGroup getCare2Kansen() {
        if (care2Kansen == null) {
            care2Kansen = new ACValueArrayRadioButtonGroup();
        }
        return care2Kansen;
    }

    /**
     * override���Ĉ�w�I�ϓ_����̗��ӎ����̒ǉ��������`���܂��B
     */
    protected void addRyuijiko() {
        care2ServiceGroup.add(getKetsuattsus(), VRLayout.FLOW_INSETLINE_RETURN);
        care2ServiceGroup.add(getEnges(), VRLayout.FLOW_INSETLINE_RETURN);
        care2ServiceGroup.add(getSesshokus(), VRLayout.FLOW_INSETLINE_RETURN);
        care2ServiceGroup.add(getMoves(), VRLayout.FLOW_INSETLINE_RETURN);
        care2ServiceGroup.add(getServiceOthers(), VRLayout.FLOW_RETURN);

    }

    /**
     * override���Ĉ�w�I�Ǘ��̕K�v���̒ǉ��������`���܂��B
     */
    protected void addNeedMnagement() {
        care2Needs1.add(care2HoumonShinryous, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs1.add(care2HoumonKangos, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs1.add(care2HoumonRehas, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs1.add(care2TsuushyoRehas, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs2.add(care2TankiNyuushyoKaigos,
                VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs2.add(care2HoumonShikaShinryous,
                VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs2.add(care2HoumonShikaEiseis, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs2.add(care2HoumonShikaYakuzais,
                VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs3.add(care2HoumonEiyous, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs3.add(care2HoumonOthers, VRLayout.FLOW_INSETLINE_RETURN);
        care2Needs3.add(care2HoumonAbstract1, VRLayout.FLOW_RETURN);
        care2Needs3.add(care2HoumonAbstract2, VRLayout.FLOW_RETURN);
        getNeedManagementGroup().add(care2Needs1, VRLayout.CLIENT);
        getNeedManagementGroup().add(care2Needs2, VRLayout.CLIENT);
        getNeedManagementGroup().add(care2Needs3, VRLayout.CLIENT);
    }

    /**
     * override���Ċ�ՃO���[�v�̒ǉ��������`���܂��B
     */
    protected void addGroup() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getNeedManagementGroup(), VRLayout.NORTH);
        this.add(getServiceGroup(), VRLayout.NORTH);
        this.add(getKansenGroup(), VRLayout.NORTH);
    }

    /**
     * �����ǖ���Ԃ��܂��B
     * 
     * @return �����ǖ�
     */
    protected ACComboBox getCare2KansenName() {
        if (care2KansenName == null) {
            care2KansenName = new ACComboBox();
        }
        return care2KansenName;
    }

    /**
     * override���Ċ����ǂ̗L�����W�I�O���[�v�ւ̒ǉ��������`���܂��B
     */
    protected void addCare2Kansen() {
        getCare2Kansen().add(getCare2KansenName(), null, 1);
    }

    public boolean noControlWarning() throws Exception {
        if (care2Ketsuattsu.getSelectedIndex() == 2) {
            if (isNullText(care2KetsuattsuValue.getEditor().getItem())) {
                if (ACMessageBox.show("�u���ӎ����i�����j�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
                    care2Ketsuattsu.requestChildFocus();
                    return false;
                }
            }
        }
        if (care2Enge.getSelectedIndex() == 2) {
            if (isNullText(care2EngeValue.getEditor().getItem())) {
                if (ACMessageBox.show("�u���ӎ����i�����j�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
                    care2Enge.requestChildFocus();
                    return false;
                }
            }
        }
        if (care2Sesshoku.getSelectedIndex() == 2) {
            if (isNullText(care2SesshokuValue.getEditor().getItem())) {
                if (ACMessageBox.show("�u���ӎ����i�ېH�j�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
                    care2Sesshoku.requestChildFocus();
                    return false;
                }
            }
        }
        if (care2Move.getSelectedIndex() == 2) {
            if (isNullText(care2MoveValue.getEditor().getItem())) {
                if (ACMessageBox.show("�u���ӎ����i�ړ��j�v�Ŗ��L��������܂��B\n���̂܂܎��s���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
                    care2Move.requestChildFocus();
                    return false;
                }
            }
        }

        return true;
    }

    public boolean noControlError() {

        // �G���[�`�F�b�N

        if (getCare2Kansen().getSelectedIndex() == 1) {
            if (isNullText(getCare2KansenName().getEditor().getItem())) {
                ACMessageBox.show("�u�����ǁv�Ŗ��L��������܂��B");
                getCare2Kansen().requestChildFocus();
                return false;
            }
        }

        return true;
    }

    public VRMap createSourceInnerBindComponent() {
        VRMap map = super.createSourceInnerBindComponent();
        map.setData("KANSENSHOU", new Integer(2));
        map.setData("KETUATU", new Integer(1));
        map.setData("ENGE", new Integer(1));
        map.setData("SESHOKU", new Integer(1));
        map.setData("IDOU", new Integer(1));

        return map;
    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        applyPoolTeikeibun(care2KetsuattsuValue,
                IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME);
        applyPoolTeikeibun(care2EngeValue,
                IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME);
        applyPoolTeikeibun(care2SesshokuValue,
                IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME);
        applyPoolTeikeibun(care2MoveValue,
                IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME);
        applyPoolTeikeibun(care2ServiceOtherValue,
                IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME);
        applyPoolTeikeibun(getCare2KansenName(),
                IkenshoCommon.TEIKEI_INFECTION_NAME);

    }

    /**
     * �����ǂ̗L�����W�I�O���[�v�ւ̃��X�i�ǉ����s�Ȃ��܂��B
     */
    protected void setCare2KanseEventListener() {
        getCare2Kansen().addListSelectionListener(
                new ACFollowDisableSelectionListener(
                        new JComponent[] { getCare2KansenName() }, 0));
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIkenshoInfoCare2() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setCare2KanseEventListener();
        care2Ketsuattsu
                .addListSelectionListener(new ACFollowDisableSelectionListener(
                        new JComponent[] { care2KetsuattsuValue,
                                care2KetsuattsuHeses }, 1));
        care2Enge
                .addListSelectionListener(new ACFollowDisableSelectionListener(
                        new JComponent[] { care2EngeValue, care2EngeHeses }, 1));
        care2Sesshoku
                .addListSelectionListener(new ACFollowDisableSelectionListener(
                        new JComponent[] { care2SesshokuValue,
                                care2SesshokuHeses }, 1));
        care2Move
                .addListSelectionListener(new ACFollowDisableSelectionListener(
                        new JComponent[] { care2MoveValue, care2MoveHeses }, 1));
        getCare2HoumonOther().getCheckBox().addItemListener(
                new ACFollowDisabledItemListener(new JComponent[] {
                        care2HoumonOtherHeses, care2HoumonOtherValue }));
    }

    private void jbInit() throws Exception {
        // Model

        getCare2Kansen().setModel(care2KansenModel);
        getCare2Kansen().setValues(new int[] { 1, 2, 3 });
        getCare2Kansen().setUseClearButton(false);
        care2Ketsuattsu.setModel(getCareServiceListModel());
        care2Ketsuattsu.setUseClearButton(false);
        care2Move.setModel(getCareServiceListModel());
        care2Move.setUseClearButton(false);
        care2Sesshoku.setModel(getCareServiceListModel());
        care2Sesshoku.setUseClearButton(false);
        care2Enge.setModel(getCareServiceListModel());
        care2Enge.setUseClearButton(false);

        setLayout(careLayout);

        care2Needs1Layout.setLabelMargin(0);
        care2Needs2Layout.setLabelMargin(0);
        care2Needs3Layout.setLabelMargin(0);

        getCare2KansenName().setIMEMode(InputSubset.KANJI);
        getCare2KansenName().setMaxLength(30);
        getCare2KansenName().setBindPath("KANSENSHOU_NM");
        getCare2KansenName().setEnabled(false);
        getCare2KansenName().setPreferredSize(new Dimension(400, 19));
        getCare2Kansen().setBindPath("KANSENSHOU");
        care2HoumonShikaEisei.setUnderlineBindPath("HOUMONSIKA_EISEISIDOU_UL");
        care2HoumonShikaEisei.setText("�K�⎕�ȉq���w��");
        care2HoumonShikaEisei.setCheckBindPath("HOUMONSIKA_EISEISIDOU");
        care2HoumonAbstract2.setText("�����̏���N���b�N���Ă��������B");
        care2HoumonAbstract2
                .setForeground(IkenshoConstants.COLOR_MESSAGE_WARNING_TEXT_FOREGROUND);
        care2HoumonYakuzai.setCheckBindPath("HOUMONYAKUZAI_KANRISIDOU");
        care2HoumonYakuzai.setText("�K���܊Ǘ��w��");
        care2HoumonYakuzai.setUnderlineBindPath("HOUMONYAKUZAI_KANRISIDOU_UL");
        care2Title.setText("�S�D���Ɋւ���ӌ��i�����j");
        care2KansenGroup.setLayout(new VRLayout());
        care2KansenGroup.setText("�����ǂ̗L���i�L�̏ꍇ�͋�̓I�ɋL�����Ă��������j�i30�����ȓ��j");
        care2HoumonOtherValue.setEnabled(false);
        care2HoumonOtherValue.setColumns(20);
        care2HoumonOtherValue.setBindPath("IGAKUTEKIKANRI_OTHER_NM");
        care2HoumonOtherValue.setMaxLength(15);
        care2HoumonOtherValue.setIMEMode(InputSubset.KANJI);
        care2ServiceGroup.setLayout(new VRLayout());
        care2ServiceGroup.setLayout(care2ServiceGroupLayout);
        care2ServiceGroup
                .setText("���T�[�r�X�i�����T�[�r�X�A�K���쓙�j�ɂ������w�I�ϓ_����̗��ӎ����i30�����ȓ��B�u���̑��v��50�����j");
        careLayout.setFitHLast(true);
        careLayout.setFitVLast(true);
        care2HoumonsKango.setCheckBindPath("HOUMON_KANGO");
        care2HoumonsKango.setText("�K��Ō�");
        care2HoumonsKango.setUnderlineBindPath("HOUMON_KANGO_UL");
        care2ServiceOtherValue.setIMEMode(InputSubset.KANJI);
        care2ServiceOtherValue.setMaxLength(50);
        care2ServiceOtherValue.setBindPath("KAIGO_OTHER");
        care2SesshokuValue.setEnabled(false);
        care2SesshokuValue.setPreferredSize(new Dimension(380, 19));
        care2SesshokuValue.setIMEMode(InputSubset.KANJI);
        care2SesshokuValue.setMaxLength(30);
        care2SesshokuValue.setBindPath("SESHOKU_RYUIJIKOU");
        care2TsuushyoReha.setCheckBindPath("TUUSHO_REHA");
        care2TsuushyoReha.setText("�ʏ����n�r���e�[�V����");
        care2TsuushyoReha.setUnderlineBindPath("TUUSHO_REHA_UL");
        care2TankiNyuushyoKaigo.setCheckBindPath("TANKI_NYUSHO_RYOUYOU");
        care2TankiNyuushyoKaigo.setText("�Z�������×{���");
        care2TankiNyuushyoKaigo.setUnderlineBindPath("TANKI_NYUSHO_RYOUYOU_UL");
        care2KetsuattsuValue.setEnabled(false);
        care2KetsuattsuValue.setPreferredSize(new Dimension(380, 19));
        care2KetsuattsuValue.setIMEMode(InputSubset.KANJI);
        care2KetsuattsuValue.setMaxLength(30);
        care2KetsuattsuValue.setBindPath("KETUATU_RYUIJIKOU");
        getServiceOthers().setText("�E���̑�");
        care2Move.setBindPath("IDOU");
        care2GroupLayout1.setAutoWrap(false);
        care2GroupLayout1.setHgap(0);
        care2GroupLayout1.setHgrid(200);
        care2Enges.setText("�E�����ɂ���");
        getNeedManagementGroup().setLayout(care2GroupLayout1);
        getNeedManagementGroup().setText("��w�I�Ǘ��̕K�v���i���ɕK�v���̍������̂ɂ͉����j");
        care2Sesshokus.setText("�E�ېH�ɂ���");
        care2Ketsuattsus.setText("�E�����ɂ���");
        care2Moves.setText("�E�ړ��ɂ���");
        care2Needs3.setLayout(care2Needs3Layout);
        care2EngeValue.setEnabled(false);
        care2EngeValue.setPreferredSize(new Dimension(380, 19));
        care2EngeValue.setIMEMode(InputSubset.KANJI);
        care2EngeValue.setMaxLength(30);
        care2EngeValue.setBindPath("ENGE_RYUIJIKOU");
        care2Sesshoku.setBindPath("SESHOKU");
        care2HoumonShikaShinryou.setCheckBindPath("HOUMONSIKA_SINRYOU");
        care2HoumonShikaShinryou.setText("�K�⎕�Ȑf��");
        care2HoumonShikaShinryou.setUnderlineBindPath("HOUMONSIKA_SINRYOU_UL");
        getCare2HoumonOther().setCheckBindPath("IGAKUTEKIKANRI_OTHER");
        getCare2HoumonOther().setText("���̑�");
        getCare2HoumonOther().setUnderlineBindPath("IGAKUTEKIKANRI_OTHER_UL");
        care2HoumonReha.setCheckBindPath("HOUMON_REHA");
        care2HoumonReha.setText("�K�⃊�n�r���e�[�V����");
        care2HoumonReha.setUnderlineBindPath("HOUMON_REHA_UL");
        care2ServiceGroupLayout.setFitHLast(true);
        care2ServiceGroupLayout.setHgap(0);
        care2ServiceGroupLayout.setLabelMargin(0);
        care2Needs2.setLayout(care2Needs2Layout);

        care2HoumonAbstract1.setText("�����������Ƃ��ɂ́A�����������ڂ�");
        care2HoumonAbstract1
                .setForeground(IkenshoConstants.COLOR_MESSAGE_WARNING_TEXT_FOREGROUND);
        care2HoumonShinryou.setCheckBindPath("HOUMON_SINRYOU");
        care2HoumonShinryou.setText("�K��f��");
        care2HoumonShinryou.setUnderlineBindPath("HOUMON_SINRYOU_UL");
        care2Enge.setBindPath("ENGE");
        care2MoveValue.setEnabled(false);
        care2MoveValue.setPreferredSize(new Dimension(380, 19));
        care2MoveValue.setIMEMode(InputSubset.KANJI);
        care2MoveValue.setMaxLength(30);
        care2MoveValue.setBindPath("IDOU_RYUIJIKOU");
        care2Ketsuattsu.setBindPath("KETUATU");
        care2HoumonEiyou.setCheckBindPath("HOUMONEIYOU_SHOKUJISIDOU");
        care2HoumonEiyou.setText("�K��h�{�H���w��");
        care2HoumonEiyou.setUnderlineBindPath("HOUMONEIYOU_SHOKUJISIDOU_UL");
        care2Needs1.setLayout(care2Needs1Layout);
        care2Enges.add(care2Enge, null);
        care2Ketsuattsus.add(care2Ketsuattsu, null);
        care2Sesshokus.add(care2Sesshoku, null);
        care2HoumonShinryous.add(care2HoumonShinryou, null);
        care2HoumonKangos.add(care2HoumonsKango, null);
        care2HoumonRehas.add(care2HoumonReha, null);
        care2TsuushyoRehas.add(care2TsuushyoReha, null);
        care2TankiNyuushyoKaigos.add(care2TankiNyuushyoKaigo, null);
        care2HoumonShikaShinryous.add(care2HoumonShikaShinryou, null);
        care2HoumonShikaEiseis.add(care2HoumonShikaEisei, null);
        care2HoumonShikaYakuzais.add(care2HoumonYakuzai, null);
        care2HoumonEiyous.add(care2HoumonEiyou, null);
        care2HoumonOthers.add(getCare2HoumonOther(), null);
        care2HoumonOthers.add(care2HoumonOtherHeses, null);
        care2HoumonOtherHeses.add(care2HoumonOtherValue, null);
        care2KansenGroup.add(care2Kansens, VRLayout.FLOW_INSETLINE_RETURN);
        care2Kansens.add(getCare2Kansen(), null);
        care2Ketsuattsu.add(care2KetsuattsuHeses, VRLayout.CLIENT);
        // care2Ketsuattsu.add(care2KetsuattsuHeses, null, 2);
        care2KetsuattsuHeses.add(care2KetsuattsuValue, VRLayout.CLIENT);
        care2Enge.add(care2EngeHeses, VRLayout.CLIENT);
        // care2Enge.add(care2EngeHeses, null, 2);
        care2EngeHeses.add(care2EngeValue, VRLayout.CLIENT);
        care2Sesshoku.add(care2SesshokuHeses, VRLayout.CLIENT);
        // care2Sesshoku.add(care2SesshokuHeses, null, 2);
        care2SesshokuHeses.add(care2SesshokuValue, VRLayout.CLIENT);
        getServiceOthers().add(care2ServiceOtherHeses, null);
        care2ServiceOtherHeses.add(care2ServiceOtherValue, VRLayout.CLIENT);
        care2Moves.add(care2Move, null);
        care2Move.add(care2MoveHeses, VRLayout.CLIENT);
        // care2Move.add(care2MoveHeses, null, 2);
        care2MoveHeses.add(care2MoveValue, VRLayout.CLIENT);

        addCare2Kansen();

        addRyuijiko();

        addNeedMnagement();

        addGroup();

        addInnerBindComponent(care2KetsuattsuValue);
        addInnerBindComponent(care2EngeValue);
        addInnerBindComponent(care2SesshokuValue);
        addInnerBindComponent(care2MoveValue);
        addInnerBindComponent(getCare2KansenName());

    }

    protected class IkenshoAddWestClearableRadioButtonGroup extends
            ACClearableRadioButtonGroup {
        protected void addRadioButton(JRadioButton item) {
            add(item, VRLayout.WEST);
        }
    }
}
