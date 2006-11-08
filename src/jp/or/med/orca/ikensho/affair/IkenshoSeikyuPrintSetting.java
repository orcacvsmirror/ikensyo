package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Date;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** <HEAD_IKENSYO> */

public class IkenshoSeikyuPrintSetting extends IkenshoDialog implements
        ActionListener, ListSelectionListener {
    /**
     * ����{�^���萔
     */
    public static final int BUTTON_PRINT = 1;
    /**
     * �L�����Z���{�^���萔
     */
    public static final int BUTTON_CANCEL = 2;
    private int buttonAction = 0;
    private int output_pattern = 0;

    private VRPanel contents = new VRPanel();
    // �����p�^�[���E������
    private ACGroupBox billPatternGroup = new ACGroupBox();
    // /�����p�^�[��
    private ACLabelContainer billPatterns = new ACLabelContainer();
    private VRLabel billPattern = new VRLabel();
    private VRLabel billPatternHead = new VRLabel();
    // /�ӌ����쐬��������
    private ACLabelContainer toCreateCosts = new ACLabelContainer();
    VRLabel toCreateCost = new VRLabel();
    VRLabel toCreateCostCode = new VRLabel();
    private ACParentHesesPanelContainer toCreateCostCodeHeses = new ACParentHesesPanelContainer();
    private VRLabel toCreateCostHead = new VRLabel();
    // /�f�@�E������������
    private ACLabelContainer toCheckCosts = new ACLabelContainer();
    VRLabel toCheckCost = new VRLabel();
    VRLabel toCheckCostCode = new VRLabel();
    private ACParentHesesPanelContainer toCheckCostCodeHeses = new ACParentHesesPanelContainer();
    private VRLabel toCheckCostHead = new VRLabel();

    // �ӌ����쐬������
    private ACGroupBox billCreateCostsGroup = new ACGroupBox();
    // ������
    private ACGroupBox createSummaryGroup = new ACGroupBox();
    // //���������W�I�{�^��
    ACClearableRadioButtonGroup createSummaryRadio = new ACClearableRadioButtonGroup();
    // //�������`�F�b�N�R���e�i�[
    private ACLabelContainer createSummaryPrints = new ACLabelContainer();
    // ///�������`�F�b�N
    ACIntegerCheckBox createSummaryPrint = new ACIntegerCheckBox();
    // /���׏����
    private ACGroupBox createDetailsGroup = new ACGroupBox();
    // //���׏����W�I�{�^��
    ACClearableRadioButtonGroup createDetailsRadio = new ACClearableRadioButtonGroup();
    // //���׏���ރ`�F�b�N�R���e�i�[
    private ACLabelContainer createDetailsPrints = new ACLabelContainer();
    // ///���׏���ރ`�F�b�N�{�b�N�X
    ACIntegerCheckBox createDetailsPrint = new ACIntegerCheckBox();

    // �f�@�E����������
    private ACGroupBox billInspectionCostsGroup = new ACGroupBox();
    // ������
    private ACGroupBox inspectionSummaryGroup = new ACGroupBox();
    // //���������W�I�{�^��
    ACClearableRadioButtonGroup inspectionSummaryRadio = new ACClearableRadioButtonGroup();
    // //�������`�F�b�N�R���e�i�[
    private ACLabelContainer inspectionSummaryPrints = new ACLabelContainer();
    // ///�������`�F�b�N
    ACIntegerCheckBox inspectionSummaryPrint = new ACIntegerCheckBox();
    // /���׏����
    private ACGroupBox inspectionDetailsGroup = new ACGroupBox();
    // //���׏����W�I�{�^��
    ACClearableRadioButtonGroup inspectionDetailsRadio = new ACClearableRadioButtonGroup();
    // //���׏���ރ`�F�b�N�R���e�i�[
    private ACLabelContainer inspectionDetailsPrints = new ACLabelContainer();
    // ///���׏���ރ`�F�b�N�{�b�N�X
    ACIntegerCheckBox inspectionDetailsPrint = new ACIntegerCheckBox();

    // /�������o�͓��t
    private ACGroupBox billPrintDateGroup = new ACGroupBox();
    private VRPanel dateRange = new VRPanel();
    // //�����̓��t�E���t�����{�^��
    private VRPanel billPrintDateButtons = new VRPanel();
    private ACButton nowDate = new ACButton();
    private ACButton clearDate = new ACButton();
    // �o�͓��t�͈�
    protected ACLabelContainer billPrintDatesRange = new ACLabelContainer();
    protected ACParentHesesPanelContainer billPrintDateRangeHeses = new ACParentHesesPanelContainer();
    protected IkenshoEraDateTextField billPrintDateRangeFrom = new IkenshoEraDateTextField();
    protected IkenshoEraDateTextField billPrintDateRangeTo = new IkenshoEraDateTextField();
    // //�o�͓��t
    protected ACParentHesesPanelContainer billPrintDateHeses = new ACParentHesesPanelContainer();
    protected ACLabelContainer billPrintDates = new ACLabelContainer();
    protected IkenshoEraDateTextField billPrintDate = new IkenshoEraDateTextField();
    // //������
    protected ACParentHesesPanelContainer billDetailPrintDateHeses = new ACParentHesesPanelContainer();
    protected IkenshoEraDateTextField billDetailPrintDate = new IkenshoEraDateTextField();

    // �{�^���p�l��
    private VRPanel buttons = new VRPanel();
    private ACButton ok = new ACButton();
    private ACButton cancel = new ACButton();

    private VRLayout contentsLayout = new VRLayout();
    private VRLayout billPatternGroupLayout = new VRLayout();
    private VRLayout billCreateCostsGroupLayout = new VRLayout();
    private VRLayout billPanelLayout = new VRLayout();
    private VRLayout createSummaryGroupLayout = new VRLayout();
    private VRLayout createSummaryRadioLayout = new VRLayout();
    private VRLayout createDetailsRadioLayout = new VRLayout();
    private VRLayout billInspectionGroupLayout = new VRLayout();
    private VRLayout inspectionSummaryGroupLayout = new VRLayout();
    private VRLayout inspectionSummaryRadioLayout = new VRLayout();
    private VRLayout inspectionDetailsRadioLayout = new VRLayout();

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @throws HeadlessException ��������O
     */
    public IkenshoSeikyuPrintSetting() throws HeadlessException {
        super(ACFrame.getInstance());
        // super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        // IkenshoSeikyuPrintSetting_actionAdapter actionAdapter = new
        // IkenshoSeikyuPrintSetting_actionAdapter(this);
        // IkenshoSeikyuPrintSetting_listSelectionAdapter listSelectionAdapter =
        // new IkenshoSeikyuPrintSetting_listSelectionAdapter(this);
        contentsLayout.setFitVLast(true);
        contentsLayout.setFitHLast(true);
        contents.setLayout(contentsLayout);
        billPatternGroup.setLayout(billPatternGroupLayout);
        billPrintDateGroup.setLayout(new VRLayout());
        billPrintDateButtons.setLayout(new VRLayout());

        billCreateCostsGroupLayout.setFitHLast(true);
        billCreateCostsGroupLayout.setAutoWrap(false);
        billCreateCostsGroupLayout.setFitVLast(true);
        billCreateCostsGroup.setLayout(billCreateCostsGroupLayout);
        createSummaryGroupLayout.setHgrid(140);
        createSummaryGroup.setLayout(createSummaryGroupLayout);
        createDetailsGroup.setLayout(new VRLayout());

        billInspectionGroupLayout.setFitHLast(true);
        billInspectionGroupLayout.setAutoWrap(false);
        billInspectionGroupLayout.setFitVLast(true);
        billInspectionCostsGroup.setLayout(billInspectionGroupLayout);
        inspectionSummaryGroupLayout.setHgrid(140);
        inspectionSummaryGroup.setLayout(inspectionSummaryGroupLayout);
        inspectionDetailsGroup.setLayout(new VRLayout());

        billPanelLayout.setFitVLast(true);
        billPanelLayout.setFitHLast(true);
        billPanelLayout.setAutoWrap(false);

        this.setTitle("�u�������v����ݒ�");

        // ---�����p�^�[���E������
        billPatternGroup.setText("�����p�^�[���E������");
        billPatternGroup.setForeground(Color.BLUE);
        billPatternGroupLayout.setFitHGrid(true);
        billPatternGroupLayout.setHgrid(200);

        billPatterns.setText("�����p�^�[��");
        billPatternHead.setText("�F");
        billPatterns.add(billPatternHead, null);
        billPatterns.add(billPattern, null);

        toCreateCosts.setText("�ӌ����쐬��������(�ԍ�)");
        toCreateCostHead.setText("�F");
        toCreateCost.setBindPath("ISS_INSURER_NM");
        toCreateCostCode.setBindPath("ISS_INSURER_NO");
        toCreateCostCodeHeses.add(toCreateCostCode);
        toCreateCosts.add(toCreateCostHead, null);
        toCreateCosts.add(toCreateCost, null);
        toCreateCosts.add(toCreateCostCodeHeses, null);

        toCheckCosts.setText("�f�@�E������������(�ԍ�)");
        toCheckCostHead.setText("�F");
        toCheckCost.setBindPath("SKS_INSURER_NM");
        toCheckCostCode.setBindPath("SKS_INSURER_NO");
        toCheckCostCodeHeses.add(toCheckCostCode);
        toCheckCosts.add(toCheckCostHead, null);
        toCheckCosts.add(toCheckCost, null);
        toCheckCosts.add(toCheckCostCodeHeses, null);

        billPatternGroup.add(billPatterns, VRLayout.FLOW_INSETLINE_RETURN);
        billPatternGroup.add(toCreateCosts, VRLayout.FLOW_INSETLINE_RETURN);
        billPatternGroup.add(toCheckCosts, VRLayout.FLOW_INSETLINE_RETURN);

        // ---�ӌ����쐬�� ������
        billCreateCostsGroup.setText("�ӌ����쐬�� ������");
        billCreateCostsGroup.setForeground(Color.BLUE);
        createSummaryGroup.setText("������");
        createSummaryRadioLayout.setAutoWrap(false);
        createSummaryRadioLayout.setHgap(2);
        createSummaryRadio.setLayout(createSummaryRadioLayout);
        createSummaryRadio.setUseClearButton(false);
        createSummaryRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "����������", "�������Ȃ�" }))));
        createSummaryRadio.setBindPath("SOUKATUHYOU_PRT");
        createSummaryRadio.addListSelectionListener(this);

        createSummaryPrint.setText("�U����������");
        createSummaryPrint.setBindPath("SOUKATU_FURIKOMI_PRT");
        createSummaryPrints.add(createSummaryPrint, null);

        ACLabelContainer panel1 = new ACLabelContainer();
        panel1.add(createSummaryRadio, null);
        createSummaryGroup.add(panel1, VRLayout.FLOW_RETURN);
        createSummaryGroup.add(createSummaryPrints, VRLayout.FLOW_RETURN);

        createDetailsGroup.setText("���׏����");
        createDetailsRadioLayout.setAutoWrap(false);
        createDetailsRadioLayout.setHgap(2);
        createDetailsRadio.setLayout(createDetailsRadioLayout);
        createDetailsRadio.setUseClearButton(false);
        createDetailsRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "���׏��̂�", "�ꗗ�\�̂�", "���׏��ƈꗗ�\" }))));
        createDetailsRadio.setBindPath("MEISAI_KIND");

        createDetailsPrint.setText("�U����������");
        createDetailsPrint.setBindPath("FURIKOMISAKI_PRT");

        createDetailsPrints.add(createDetailsPrint, null);
        ACLabelContainer panel2 = new ACLabelContainer();
        panel2.add(createDetailsRadio, null);
        createDetailsGroup.add(panel2, VRLayout.FLOW_RETURN);
        createDetailsGroup.add(createDetailsPrints, VRLayout.FLOW_RETURN);

        billCreateCostsGroup.add(createSummaryGroup, VRLayout.WEST);
        billCreateCostsGroup.add(createDetailsGroup, VRLayout.CLIENT);

        // ---�f�@�E������ ������
        billInspectionCostsGroup.setText("�f�@�E������ ������");
        billInspectionCostsGroup.setForeground(Color.BLUE);
        inspectionSummaryGroup.setText("������");
        inspectionSummaryRadioLayout.setAutoWrap(false);
        inspectionSummaryRadioLayout.setHgap(2);
        inspectionSummaryRadio.setLayout(inspectionSummaryRadioLayout);
        inspectionSummaryRadio.setUseClearButton(false);
        inspectionSummaryRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "����������", "�������Ȃ�" }))));
        inspectionSummaryRadio.setBindPath("SOUKATUHYOU_PRT2");
        inspectionSummaryRadio.addListSelectionListener(this);

        inspectionSummaryPrint.setText("�U����������");
        inspectionSummaryPrint.setBindPath("SOUKATU_FURIKOMI_PRT2");
        inspectionSummaryPrints.add(inspectionSummaryPrint, null);
        ACLabelContainer panel3 = new ACLabelContainer();
        panel3.add(inspectionSummaryRadio, null);
        inspectionSummaryGroup.add(panel3, VRLayout.FLOW_RETURN);
        inspectionSummaryGroup.add(inspectionSummaryPrints,
                VRLayout.FLOW_RETURN);
        inspectionDetailsGroup.setText("���׏����");
        inspectionDetailsRadioLayout.setAutoWrap(false);
        inspectionDetailsRadioLayout.setHgap(2);
        inspectionDetailsRadio.setLayout(inspectionDetailsRadioLayout);
        inspectionDetailsRadio.setUseClearButton(false);
        inspectionDetailsRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "���׏��̂�", "�ꗗ�\�̂�", "���׏��ƈꗗ�\" }))));
        inspectionDetailsRadio.setBindPath("MEISAI_KIND2");

        inspectionDetailsPrint.setText("�U����������");
        inspectionDetailsPrint.setBindPath("FURIKOMISAKI_PRT2");

        inspectionDetailsPrints.add(inspectionDetailsPrint, null);
        ACLabelContainer panel4 = new ACLabelContainer();
        panel4.add(inspectionDetailsRadio, null);
        inspectionDetailsGroup.add(panel4, VRLayout.FLOW_RETURN);
        inspectionDetailsGroup.add(inspectionDetailsPrints,
                VRLayout.FLOW_RETURN);

        billInspectionCostsGroup.add(inspectionSummaryGroup, VRLayout.WEST);
        billInspectionCostsGroup.add(inspectionDetailsGroup, VRLayout.CLIENT);

        // ---�������אU���ݐ�^�����o�͓��t
        billPrintDateGroup.setText("�o�͓��t");
        billPrintDateGroup.setForeground(Color.BLUE);
        // �o�͔͈͓��t
        VRLayout billPrintDatesRangeLayout = new VRLayout();
        billPrintDatesRangeLayout.setAutoWrap(false);
        billPrintDatesRange.setLayout(billPrintDatesRangeLayout);
        billPrintDateRangeHeses.setBeginText("�F");
        billPrintDateRangeHeses.setEndText("�`");
        billPrintDatesRange.setText("�o�͔͈͓��t");
        billPrintDateRangeFrom.setAgeVisible(false);
        billPrintDateRangeFrom.setAllowedFutureDate(true);
        billPrintDateRangeFrom
                .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        billPrintDateRangeTo.setAgeVisible(false);
        billPrintDateRangeTo.setAllowedFutureDate(true);
        billPrintDateRangeTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        ACLabelContainer panel5 = new ACLabelContainer();
        panel5.add(billPrintDateRangeFrom, null);
        billPrintDateRangeHeses.add(panel5, null);
        ACLabelContainer panel6 = new ACLabelContainer();
        panel6.add(billPrintDateRangeTo, null);
        billPrintDatesRange.add(billPrintDateRangeHeses, VRLayout.FLOW);
        billPrintDatesRange.add(panel6, VRLayout.FLOW);

        billPrintDatesRange
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        billPrintDatesRange
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        billPrintDatesRange.setContentAreaFilled(true);
        // ���t�o��
        VRLayout billPrintDatesLayout = new VRLayout();
        billPrintDatesLayout.setAutoWrap(false);
        billPrintDates.setLayout(billPrintDatesLayout);
        billPrintDateHeses.setBeginText("�F");
        billPrintDateHeses.setEndText("");
        billPrintDates.setText("�o�͓��t�@�@");
        billPrintDate.setAgeVisible(false);
        billPrintDate.setAllowedFutureDate(true);
        billPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        billDetailPrintDate.setAgeVisible(false);
        billDetailPrintDate.setAllowedFutureDate(true);
        billDetailPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        billDetailPrintDate.setDayVisible(false);
        billDetailPrintDateHeses.setEndText("��)");
        ACLabelContainer panel7 = new ACLabelContainer();
        panel7.add(billPrintDate, null);
        billPrintDateHeses.add(panel7, null);
        ACLabelContainer panel8 = new ACLabelContainer();
        panel8.add(billDetailPrintDate, null);
        billDetailPrintDateHeses.add(panel8, null);
        billPrintDates.add(billPrintDateHeses, VRLayout.FLOW);
        billPrintDates.add(billDetailPrintDateHeses, VRLayout.FLOW);

        billPrintDates
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        billPrintDates
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        billPrintDates.setContentAreaFilled(true);

        dateRange.setLayout(new VRLayout());
        dateRange
                .add(billPrintDatesRange, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);
        dateRange.add(billPrintDates, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);

        nowDate.setMnemonic('D');
        nowDate.setText("�����̓��t(D)");
        nowDate.addActionListener(this);
        clearDate.setText("���t����(E)");
        clearDate.setMnemonic('E');
        clearDate.addActionListener(this);
        billPrintDateButtons.add(nowDate, VRLayout.NORTH);
        billPrintDateButtons.add(clearDate, VRLayout.NORTH);

        billPrintDateGroup.add(dateRange, VRLayout.CLIENT);
        billPrintDateGroup.add(billPrintDateButtons, VRLayout.EAST);

        // �{�^��
        ok.setMnemonic('O');
        ok.setText("���(O)");
        ok.addActionListener(this);
        cancel.setText("�L�����Z��(C)");
        cancel.addActionListener(this);
        cancel.setMnemonic('C');
        buttons.add(ok, null);
        buttons.add(cancel, null);

        // �p�l���ɒǉ�����
        this.getContentPane().add(contents, BorderLayout.CENTER);
        contents.add(billPatternGroup, VRLayout.NORTH);
        contents.add(billCreateCostsGroup, VRLayout.NORTH);
        contents.add(billInspectionCostsGroup, VRLayout.NORTH);
        contents.add(billPrintDateGroup, VRLayout.NORTH);
        contents.add(buttons, VRLayout.SOUTH);
    }

    // �R���|�[�l���g�̏�����
    private void init() {
        // �E�B���h�E�̃T�C�Y
        setSize(new Dimension(730, 440));
        // �E�B���h�E�𒆉��ɔz�u
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

    }

    public int showDialog(String insurerNo, String insurerType) throws Exception {
        doSelect(insurerNo, insurerType);
        setTodayAll(true);
        super.setVisible(true);
        return buttonAction;
    }

    /**
     * DB����f�[�^���擾���܂��B
     * 
     * @throws Exception
     */
    private void doSelect(String insurerNo, String insurerType) throws Exception {

        // �L�[�����ɁADB����f�[�^���擾
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" SEIKYUSHO_OUTPUT_PATTERN,");
        sb.append(" ISS_INSURER_NO,");
        sb.append(" ISS_INSURER_NM,");
        sb.append(" SKS_INSURER_NO,");
        sb.append(" SKS_INSURER_NM,");
        sb.append(" SOUKATUHYOU_PRT,");
        sb.append(" MEISAI_KIND,");
        sb.append(" FURIKOMISAKI_PRT,");
        sb.append(" SOUKATU_FURIKOMI_PRT,");
        sb.append(" SOUKATUHYOU_PRT2,");
        sb.append(" MEISAI_KIND2,");
        sb.append(" FURIKOMISAKI_PRT2,");
        sb.append(" SOUKATU_FURIKOMI_PRT2");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" WHERE");
        sb.append(" INSURER_NO='" + insurerNo + "'");
        sb.append(" AND(INSURER_TYPE=" + insurerType+")");
        sb.append(" ORDER BY");
        sb.append(" INSURER_NO");
        VRArrayList insurerArray = (VRArrayList) dbm
                .executeQuery(sb.toString());

        if (insurerArray.getDataSize() != 1) {
            throw new Exception("�ی��҂̏��擾���s:�擾����" + insurerArray.getDataSize());
        }
        VRMap map = (VRMap) insurerArray.get(0);

        // �������ʂ��o�C���h
        contents.setSource(map);
        contents.bindSource();

        // �����p�^�[���̕\��
        output_pattern = Integer.parseInt(String.valueOf(VRBindPathParser.get(
                "SEIKYUSHO_OUTPUT_PATTERN", map)));
        setOutputPattern(output_pattern);
        toCreateCostCodeHeses.setVisible(!"".equals(String.valueOf(VRBindPathParser.get(
                "ISS_INSURER_NO", map))));
        // �ӌ����쐬���E������(1��)�̎��́A�����������ΏۂɈӌ����쐬�������Ώۂ�\������B
        if (output_pattern == 1) {
            toCheckCost.setText(String.valueOf(VRBindPathParser.get(
                    "ISS_INSURER_NM", map)));
            toCheckCostCode.setText(String.valueOf(VRBindPathParser.get(
                    "ISS_INSURER_NO", map)));
        } else {
            toCheckCostCodeHeses.setVisible(!"".equals(String.valueOf(VRBindPathParser.get(
                    "SKS_INSURER_NO", map))));
        }
    }

    private void setOutputPattern(int outputPattern) throws Exception {
        switch (outputPattern) {
        case 1:
            billPattern.setText("�ӌ����쐬���E������(1��)");
            turnGrpPnl(true, false);
            billCreateCostsGroup.setText("�ӌ����쐬���^�f�@�E������ ������");
            // ���W�I�{�^���̑I����Ԃɂ��`�F�b�N�{�b�N�X��Enebled�ύX
            turnCheckEnabled(createSummaryPrint, createSummaryRadio
                    .getSelectedIndex());
            break;
        case 2:
            billPattern.setText("�ӌ����쐬��(1��)�E������(1��)");
            turnGrpPnl(true, true);
            // ���W�I�{�^���̑I����Ԃɂ��`�F�b�N�{�b�N�X��Enebled�ύX
            turnCheckEnabled(createSummaryPrint, createSummaryRadio
                    .getSelectedIndex());
            turnCheckEnabled(inspectionSummaryPrint, inspectionSummaryRadio
                    .getSelectedIndex());

            break;
        case 3:
            billPattern.setText("�ӌ����쐬���̂�");
            turnGrpPnl(true, false);
            // ���W�I�{�^���̑I����Ԃɂ��`�F�b�N�{�b�N�X��Enebled�ύX
            turnCheckEnabled(createSummaryPrint, createSummaryRadio
                    .getSelectedIndex());

            break;
        case 4:
            billPattern.setText("�������̂�");
            turnGrpPnl(false, true);
            // ���W�I�{�^���̑I����Ԃɂ��`�F�b�N�{�b�N�X��Enebled�ύX
            turnCheckEnabled(inspectionSummaryPrint, inspectionSummaryRadio
                    .getSelectedIndex());

            break;
        }
    }

    /**
     * ���������s�p�^�[����ԋp���܂��B <BR>
     * 
     * @return int ���������s�p�^�[��
     */
    public int getOutputPattern() {
        return output_pattern;
    }

    private void turnGrpPnl(boolean iken, boolean sinryo) throws Exception {
        billCreateCostsGroup.setEnabled(iken);
        createSummaryGroup.setEnabled(iken);
        createSummaryRadio.setEnabled(iken);
        createSummaryPrint.setEnabled(iken);
        createDetailsGroup.setEnabled(iken);
        createDetailsRadio.setEnabled(iken);
        createDetailsPrint.setEnabled(iken);
        // �ӌ����쐬�����g�p�s�ł���΁A�ݒ肵���l��j������B
        if (!iken) {
            billCreateCostsGroup.setSource((VRBindSource) billCreateCostsGroup
                    .createSource());
            billCreateCostsGroup.bindSource();
        }

        billInspectionCostsGroup.setEnabled(sinryo);
        inspectionSummaryGroup.setEnabled(sinryo);
        inspectionSummaryRadio.setEnabled(sinryo);
        inspectionSummaryPrint.setEnabled(sinryo);
        inspectionDetailsGroup.setEnabled(sinryo);
        inspectionDetailsRadio.setEnabled(sinryo);
        inspectionDetailsPrint.setEnabled(sinryo);

        // �������쐬�����g�p�s�ł���΁A�ݒ肵���l��j������B
        if (!sinryo) {
            billInspectionCostsGroup
                    .setSource((VRBindSource) billInspectionCostsGroup
                            .createSource());
            billInspectionCostsGroup.bindSource();
        }

    }

    private void setTodayAll(boolean startDefault) throws Exception {
        Date today = new Date();
        if (startDefault) {
            setDefaultDay(billPrintDateRangeFrom);
        }
        billPrintDateRangeTo.setDate(today);
        billPrintDate.setDate(today);
        billDetailPrintDate.setDate(today);
        billDetailPrintDate.setDay("");
    }

    private void setDefaultDayAll() throws Exception {
        setDefaultDay(billPrintDateRangeFrom);
        setDefaultDay(billPrintDateRangeTo);
        setDefaultDay(billPrintDate);
        setDefaultDay(billDetailPrintDate);
    }

    private void setDefaultDay(IkenshoEraDateTextField date) {
        date.setDate(new Date());
        date.setYear("");
        date.setMonth("");
        date.setDay("");
    }

    private void turnCheckEnabled(ACIntegerCheckBox check, int selectIndex) {
        if (check != null) {
            switch (selectIndex) {
            case 1:
                check.setEnabled(true);
                break;
            case 2:
                check.setEnabled(false);
                break;
            }
        }
    }

    /**
     * ���̓`�F�b�N����
     */
    private boolean isValidInput() throws Exception {
        boolean outputFromInput = false;
        boolean outputToInput = false;
        // ---���t���̓`�F�b�N
        // �o�͔͈͊J�n
        if (isTaisyoKikanInput(billPrintDateRangeFrom)) {
            billPrintDateRangeFrom
                    .setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (billPrintDateRangeFrom.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billPrintDateRangeFrom.transferFocus();
                ACMessageBox.showExclamation("�J�n���t�̓��͂Ɍ�肪����܂��B");
                billPrintDateRangeFrom
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billPrintDateRangeFrom
                    .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            outputFromInput = true;
        }
        // �o�͔͈͏I��
        if (isTaisyoKikanInput(billPrintDateRangeTo)) {
            billPrintDateRangeTo
                    .setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (billPrintDateRangeTo.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billPrintDateRangeTo.transferFocus();
                ACMessageBox.showExclamation("�I�����t�̓��͂Ɍ�肪����܂��B");
                billPrintDateRangeTo
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billPrintDateRangeTo
                    .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            outputToInput = true;
        }
        // �o�͓��t
        if (isTaisyoKikanInput(billPrintDate)) {
            billPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (billPrintDate.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billPrintDate.transferFocus();
                ACMessageBox.showExclamation("�o�͓��t�̓��͂Ɍ�肪����܂��B");
                billPrintDate
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }
        // ������
        if (isTaisyoKikanInput(billDetailPrintDate)) {
            billDetailPrintDate
                    .setRequestedRange(IkenshoEraDateTextField.RNG_MONTH);
            if (billDetailPrintDate.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billDetailPrintDate.transferFocus();
                ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
                billDetailPrintDate
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billDetailPrintDate
                    .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }
        // ���t�̑O��֌W�`�F�b�N
        if (outputFromInput && outputToInput) {
            if (billPrintDateRangeFrom.getDate().after(
                    billPrintDateRangeTo.getDate())) {
                billPrintDateRangeFrom.transferFocus();
                ACMessageBox.showExclamation("�J�n���t�ƏI�����t���t�]���Ă��܂��B");
                return false;
            }
        }
        return true;
    }

    private boolean isTaisyoKikanInput(IkenshoEraDateTextField date) {
        return (!date.getEra().equals("") && !date.getYear().equals(""));
    }

    // �E�B���h�E������ꂽ�Ƃ���Dispose����悤�ɃI�[�o�[���C�h
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
            buttonAction = BUTTON_CANCEL;
        }
    }

    /**
     * �E�B���h�E�����
     */
    protected void closeWindow() {
        // ���g��j������
        //2006-09-20 begin-replace Tozo TANAKA
        //��ʔj����Ɉꗗ����R���{�̒l���Q�Ƃ���Ă��܂����߁A�j���O�ɑޔ����č����߂��B
        //this.dispose();
        String from = billPrintDateRangeFrom.getEra();
        String to = billPrintDateRangeTo.getEra();
        String print = billPrintDate.getEra();
        String detail = billDetailPrintDate.getEra();
        this.dispose();
        try{
            billPrintDateRangeFrom.reloadEras();
            billPrintDateRangeTo.reloadEras();
            billPrintDate.reloadEras();
            billDetailPrintDate.reloadEras();
        }catch(Exception ex){
            
        }
        billPrintDateRangeFrom.setEra(from);
        billPrintDateRangeTo.setEra(to);
        billPrintDate.setEra(print);
        billDetailPrintDate.setEra(detail);
        //2006-09-20 end-replace Tozo TANAKA
    }

    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        try {
            if (eventSource.equals(nowDate)) {
                setTodayAll(false);
            } else if (eventSource.equals(clearDate)) {
                setDefaultDayAll();
            } else if (eventSource.equals(ok)) {
                // ���t�̓��͂Ɍ�肪����ꍇ
                if (!isValidInput()) {
                    return;
                }
                closeWindow();
                buttonAction = BUTTON_PRINT;
            } else if (eventSource.equals(cancel)) {
                closeWindow();
                buttonAction = BUTTON_CANCEL;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        Object eventSource = e.getSource();
        // IkenshoIntegerCheckBox check = null;
        // int selectIndex = 0;
        try {
            // �ӌ����쐬�����������W�I�{�^��
            if (eventSource.equals(createSummaryRadio)) {
                turnCheckEnabled(createSummaryPrint, createSummaryRadio
                        .getSelectedIndex());
                // �f�@�����������W�I�{�^��
            } else if (eventSource.equals(inspectionSummaryRadio)) {
                turnCheckEnabled(inspectionSummaryPrint, inspectionSummaryRadio
                        .getSelectedIndex());
            }

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

    }

}
