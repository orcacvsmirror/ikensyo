package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACOneDecimalDoubleTextField;
import jp.nichicom.ac.component.ACRadioButtonItem;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoInitialNegativeIntegerTextField;
import jp.or.med.orca.ikensho.component.IkenshoUnderlineFormatableLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoConsultationInfo extends IkenshoDialog {
    public static final int STATE_ZAITAKU = 1;
    public static final int STATE_SHISETSU = 2;
    public static final int STATE_FIRST = 4;
    public static final int STATE_EVER = 8;
    public static final int STATE_FIRST_SINRYOUJYO = 16;
    public static final int STATE_FIRST_HOSPITAL = 32;

    private VRPanel root = new VRPanel();
    private VRLayout rootLayout = new VRLayout();
    private VRPanel buttons = new VRPanel();
    private ACGroupBox toGroup = new ACGroupBox();
    private ACGroupBox bodyGroup = new ACGroupBox();
    private ACGroupBox costGroup = new ACGroupBox();
    private VRPanel taxPanel = new VRPanel();
    private ACGroupBox taxGroup = new ACGroupBox();
    private ACLabelContainer taxs = new ACLabelContainer();
    private VRLabel taxUnit = new VRLabel();
    private ACOneDecimalDoubleTextField tax = new ACOneDecimalDoubleTextField();
    private VRLabel toConsultationHead = new VRLabel();
    private VRLabel toIkenshoHead = new VRLabel();
    private ACLabelContainer costs = new ACLabelContainer();
    private ACTextField cost = new ACTextField();
    private ACTextField costType = new ACTextField();
    private ACTextField costTarget = new ACTextField();
    private VRLabel costUnit = new VRLabel();
    private ACButton cancel = new ACButton();
    private ACButton ok = new ACButton();
    private ACButton reset = new ACButton();
    // 2009/01/13 [Tozo Tanaka] Replace - begin
//    private VRPanel points = new VRPanel();
//    private VRPanel pointsLeft = new VRPanel();
//    private VRPanel pointsRight = new VRPanel();
    private ACPanel points = new ACPanel();
    private ACPanel pointsLeft = new ACPanel();
    private ACPanel pointsRight = new ACPanel();
    private VRLabel[] pointsSpacer = { new VRLabel(" "), new VRLabel(" "),
            new VRLabel(" "), new VRLabel(" "), new VRLabel(" "),
            new VRLabel(" "), new VRLabel(" "), new VRLabel(" "),
            new VRLabel(" "), new VRLabel(" ") };
    // 2009/01/13 [Tozo Tanaka] Replace - end

    private VRPanel calcRoot = new VRPanel();
    private GridBagLayout calcRootLayout = new GridBagLayout();
    // 2009/01/13 [Tozo Tanaka] Delete - begin
//    private VRLayout pointsLayout = new VRLayout();
//    private GridBagLayout pointsLeftLayout = new GridBagLayout();
//    private GridBagLayout pointsRightLayout = new GridBagLayout();
//    private VRLabel pointsRightSpacer1 = new VRLabel();
    // 2009/01/13 [Tozo Tanaka] Delete - end
    private ACIntegerCheckBox xrayFilm = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodBasic = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodBasicTest = new ACIntegerCheckBox();
    private ACIntegerCheckBox ketsuekigakuTestCost = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodChemistry = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodChemistryTest = new ACIntegerCheckBox();
    private ACIntegerCheckBox seikagakuTestCost = new ACIntegerCheckBox();
    private ACIntegerCheckBox nyouTest = new ACIntegerCheckBox();
    private ACIntegerCheckBox xray = new ACIntegerCheckBox();
    private ACIntegerCheckBox xrayBasic = new ACIntegerCheckBox();
    private ACIntegerCheckBox xrayPhotoCheck = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodSaishu = new ACIntegerCheckBox();
    private VRLabel bloodBasicSpacer = new VRLabel();
    private VRLabel xraySpacer = new VRLabel();
    private ACButton bloodChemistryTestPointChange = new ACButton();
    private VRPanel bloodChemistryTestPoints = new VRPanel();
    private ACOneDecimalDoubleTextField bloodChemistryTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel bloodChemistryTestPointUnit = new VRLabel();
    private VRPanel seikagakuTestCostPoints = new VRPanel();
    private ACOneDecimalDoubleTextField seikagakuTestCostPoint = new ACOneDecimalDoubleTextField();
    private VRLabel seikagakuTestCostPointUnit = new VRLabel();
    private VRPanel ketsuekigakuTestCostPoints = new VRPanel();
    private ACOneDecimalDoubleTextField ketsuekigakuTestCostPoint = new ACOneDecimalDoubleTextField();
    private VRLabel ketsuekigakuTestCostPointUnit = new VRLabel();
    private VRPanel bloodBasicTestPoints = new VRPanel();
    private ACOneDecimalDoubleTextField bloodBasicTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel bloodBasicTestPointUnit = new VRLabel();
    private VRPanel bloodSaishuPoints = new VRPanel();
    private ACOneDecimalDoubleTextField bloodSaishuPoint = new ACOneDecimalDoubleTextField();
    private VRLabel bloodSaishuPointUnit = new VRLabel();
    private VRPanel nyouTestPoints = new VRPanel();
    private ACOneDecimalDoubleTextField nyouTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel nyouTestPointUnit = new VRLabel();
    private VRPanel xrayBasicPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayBasicPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayBasicPointUnit = new VRLabel();
    private VRPanel xrayPhotoCheckPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayPhotoCheckPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayPhotoCheckPointUnit = new VRLabel();
    private VRPanel xrayFilmPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayFilmPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayFilmPointUnit = new VRLabel();

    // 2009/01/06 [Tozo Tanaka] Add - begin
    // �f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�摜�L�^�p�t�B����(��p)
    private ACIntegerCheckBox xrayDigitalFilm = new ACIntegerCheckBox();
    private VRPanel xrayDigitalFilmPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalFilmPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalFilmPointUnit = new VRLabel();

    // �f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�f�W�^���f�����������Z
    private ACIntegerCheckBox xrayDigitalImaging = new ACIntegerCheckBox();
    private VRPanel xrayDigitalImagingPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalImagingPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalImagingPointUnit = new VRLabel();

    // �f�W�^���B�e�̏ꍇ�F�t�B�������X�̏ꍇ�F�d�q�摜�Ǘ����Z
    private ACIntegerCheckBox xrayDigitalImageManagement = new ACIntegerCheckBox();
    private VRPanel xrayDigitalImageManagementPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalImageManagementPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalImageManagementPointUnit = new VRLabel();
    // 2009/01/06 [Tozo Tanaka] Add - end
    
    // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
    private ACIntegerCheckBox xrayDigital = new ACIntegerCheckBox();
    private VRPanel xrayDigitalPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalPointUnit = new VRLabel();
    // [ID:0000601][Masahiko Higuchi] 2010/02 add end
    
    private VRLabel category = new VRLabel();
    private VRLabel point = new VRLabel();
    private VRLabel summary = new VRLabel();
    private VRLabel calcXRay = new VRLabel();
    private VRLabel calcCheckTotalCostMessage = new VRLabel();
    private VRLabel calcCheckTotal = new VRLabel();
    private VRLabel calcNyoTest = new VRLabel();
    private VRLabel calcBloodChemistry = new VRLabel();
    private VRLabel calcBloodBasic = new VRLabel();
    private ACTextField spaceType = new ACTextField();
    private ACOneDecimalDoubleTextField calcXRayPoint = new ACOneDecimalDoubleTextField();
    private ACOneDecimalDoubleTextField calcBloodBasicPoint = new ACOneDecimalDoubleTextField();
    private ACOneDecimalDoubleTextField calcBloodChemistryPoint = new ACOneDecimalDoubleTextField();
    private ACOneDecimalDoubleTextField calcCheckTotalPoint = new ACOneDecimalDoubleTextField();
    private ACTextField firstTestSummary = new ACTextField();
    private ACTextField calcCheckTotalCost = new ACTextField();
    private ACOneDecimalDoubleTextField firstTestPoint = new ACOneDecimalDoubleTextField();
    private ACIntegerCheckBox firstTest = new ACIntegerCheckBox();
    private ACOneDecimalDoubleTextField calcNyoTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel calcCheckTotalCostUnit = new VRLabel();
    private ACTextField calcBloodBasicSummary = new ACTextField();
    private ACTextField calcBloodChemistrySummary = new ACTextField();
    private ACTextField calcXRaySummary = new ACTextField();
    private ACTextField calcNyoTestSummary = new ACTextField();
    private VRPanel calcCheckMessages = new VRPanel();
    private VRLabel calcCheckMessage2 = new VRLabel();
    private VRLabel calcCheckMessage1 = new VRLabel();
    private VRPanel toConsultationPanel = new VRPanel();
    private VRPanel toIkenshoPanel = new VRPanel();
    private VRLayout costGroupLayout = new VRLayout();
    private VRLayout taxGroupLayout = new VRLayout();
    private VRLayout toIkenshoPanelLayout = new VRLayout();
    private VRLayout toConsultationPanelLayout = new VRLayout();
    private IkenshoUnderlineFormatableLabel toIkenshoName = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toIkenshoNoHead = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toIkenshoNo = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toIkenshoNoFoot = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationName = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationNo = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationNoFoot = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationNoHead = new IkenshoUnderlineFormatableLabel();
    private ACGroupBox hiddenParameters = new ACGroupBox();
    private IkenshoInitialNegativeIntegerTextField outputPattern = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField hakkouKubun = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField fdOutputKubun = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField shoshin = new IkenshoInitialNegativeIntegerTextField();
    private VRLayout bloodChemistryTestPointsLayout = new VRLayout();

    private String lastInputedFirstTestPoint = "0";
    protected boolean changed = false;
    protected VRMap source;
    protected IkenshoTreeFollowChecker bloodBasicChecker = new IkenshoTreeFollowChecker(
            bloodBasic,
            new JCheckBox[] { bloodBasicTest, ketsuekigakuTestCost });
    protected IkenshoTreeFollowChecker bloodChemistryChecker = new IkenshoTreeFollowChecker(
            bloodChemistry, new JCheckBox[] { bloodChemistryTest,
                    seikagakuTestCost });
    //2009/01/13 [Tozo Tanaka] Replace - begin
//    protected IkenshoTreeFollowChecker xrayChecker = new IkenshoTreeFollowChecker(
//            xray, new JCheckBox[] { xrayBasic, xrayPhotoCheck, xrayFilm});
    // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
    protected IkenshoTreeFollowChecker xrayChecker = new IkenshoTreeFollowChecker(
            xray, new JCheckBox[] { xrayBasic, xrayDigital, xrayPhotoCheck,
                    xrayFilm, xrayDigitalImageManagement, xrayDigitalFilm,
                    xrayDigitalImaging }, new JCheckBox[] { xrayBasic,
                    xrayPhotoCheck, xrayFilm, });
    // [ID:0000601][Masahiko Higuchi] 2010/02 edit end
    //2009/01/13 [Tozo Tanaka] Replace - begin
    protected VRMap defaultInsure;
    protected VRMap pointMap = new VRHashMap();
    protected HashMap checkItemListenerMap = new HashMap();
    protected String firstTestName;
    protected String firstTestKey;
    protected int stateFlag;
    protected int formatKubun;

    protected IkenshoInitialNegativeIntegerTextField ikenshoCharge = new IkenshoInitialNegativeIntegerTextField();

    /**
     * /** ���[�_�����[�h�ŕ\�����A�ύX������������Ԃ��܂��B
     * 
     * @return �ύX����������
     */
    public boolean showModal() {
        root.setSource(source);
        // TODO SET
        // calcRoot��source��ݒ� ���͈͂��傫���̂ŗv����
        calcRoot.setSource(source);
        try {

            // �t�B�[���h�|��
            source.setData("SEIKYUSHO_OUTPUT_PATTERN", source
                    .getData("OUTPUT_PATTERN"));

            boolean loadTax = true;
            if (VRBindPathParser.has("TAX", source)) {
                Object obj = VRBindPathParser.get("TAX", source);
                if (obj instanceof Double) {
                    if (((Double) obj).doubleValue() >= 0) {
                        loadTax = false;
                    }
                }
                if (loadTax) {
                    // �ŋ�
                    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                    IkenshoCommon.setTax(dbm, source);
                    dbm.finalize();
                }

            }

            followBind();
            // �E�v���̓`�F�b�N�ŏ㏑�������̂Ń��o�C���h
            calcXRaySummary.bindSource();
            calcBloodBasicSummary.bindSource();
            calcBloodChemistrySummary.bindSource();
            calcNyoTestSummary.bindSource();

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }

        setVisible(true);
        // show();
        return changed;
    }

    /**
     * �ŐV�̒l��ǂݍ��݂܂��B
     * 
     * @throws Exception ������O
     */
    protected void loadDefault() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" WHERE");
        sb.append(" (INSURER.INSURER_NO=");
        sb.append(IkenshoCommon.getDBSafeString("INSURER_NO", source));
        sb.append(")");
        sb.append(" AND(INSURER.INSURER_TYPE=");
        sb.append(IkenshoCommon.getDBSafeNumber("INSURER_TYPE", source));
        sb.append(")");

        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        if (array.size() == 0) {
            IkenshoCommon.showExceptionMessage(new IllegalArgumentException(
                    "���݂��Ȃ��ی��҂��w�肳��܂����B"));
            return;
        }
        defaultInsure = (VRMap) array.getData();
        // �t�B�[���h�|��
        defaultInsure.setData("OUTPUT_PATTERN", defaultInsure
                .getData("SEIKYUSHO_OUTPUT_PATTERN"));

        source.putAll((VRMap) bodyGroup.createSource());

        dbm.finalize();
    }

    /**
     * ��ԃt���O����͂��܂��B
     * 
     * @throws ParseException ������O
     */
    protected void paraseState() throws ParseException {
        StringBuffer costSB = new StringBuffer();
        int flag = 0;
        if ((stateFlag & IkenshoConsultationInfo.STATE_ZAITAKU) != 0) {
            source.put("AFFAIR_CARE_TYPE", "�ݑ�");
            costSB.append("ZAITAKU_");
        } else if ((stateFlag & IkenshoConsultationInfo.STATE_SHISETSU) != 0) {
            source.put("AFFAIR_CARE_TYPE", "�{��");
            costSB.append("SISETU_");
            flag += 2;
        }
        if ((stateFlag & IkenshoConsultationInfo.STATE_FIRST) != 0) {
            source.put("AFFAIR_FIRST_CREATE", "�V�K");
            costSB.append("SINKI_CHARGE");
        } else if ((stateFlag & IkenshoConsultationInfo.STATE_EVER) != 0) {
            source.put("AFFAIR_FIRST_CREATE", "�p��");
            costSB.append("KEIZOKU_CHARGE");
            flag += 1;
        }
        source.put("IKN_CHARGE", new Integer(flag));
        ikenshoCharge.setText(String.valueOf(flag));

        cost.setBindPath(costSB.toString());
        cost.setFormat(NumberFormat.getIntegerInstance());

        Object kubunObj = VRBindPathParser.get("MI_KBN", source);
        int kubun = 0;
        if (kubunObj instanceof Integer) {
            kubun = ((Integer) kubunObj).intValue();
        }

        int shoshin = 0;
        switch (kubun) {
        case 1:
            firstTestName = "�f�Ï�";
            firstTestKey = "SHOSIN_SINRYOUJO";
            shoshin = 1;
            break;
        case 2:
            firstTestName = "�a�@";
            firstTestKey = "SHOSIN_HOSPITAL";
            shoshin = 2;
            break;
        default:
            firstTestName = "���̑��̎{��";
            firstTestKey = "SHOSIN_OTHER";
            if (!pointMap.containsKey("SHOSIN_OTHER")) {
                pointMap.setData("SHOSIN_OTHER", new Double(0));
            } else {
                lastInputedFirstTestPoint = String.valueOf(pointMap
                        .getData("SHOSIN_OTHER"));
            }
            shoshin = 3;
        }
        source.put("SHOSIN", new Integer(shoshin));
        this.shoshin.setText(String.valueOf(shoshin));

    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param src �f�[�^�\�[�X
     * @param stateFlg ��ԃt���O
     * @param formatKubun �����敪
     * @see stateFlag�͒萔�̑g�ݍ��킹�Ŏw�肵�܂��B
     */
    public IkenshoConsultationInfo(VRMap src, int stateFlg, int formatKubun) {
        super(ACFrame.getInstance(), "�f�@�E�������e����", true);
        this.source = src;
        this.stateFlag = stateFlg;
        this.formatKubun = formatKubun;

        pointMap.clear();
        pointMap.putAll(source);

        try {
            jbInit();
            pack();
            init();
            paraseState();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        hiddenParameters.setVisible(false);

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (IkenshoCommon.isNullText(tax.getText())) {
                        ACMessageBox.show("����ł��s���ł��B", ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);
                        tax.requestFocus();
                        return;
                    }
                    if ((!IkenshoCommon.isNullText(calcCheckTotalPoint
                            .getText()))
                            && ((stateFlag & IkenshoConsultationInfo.STATE_FIRST) != 0)) {
                        // ����
                        if (!firstTest.isSelected()) {
                            if (ACMessageBox.show("���f���I������Ă��܂���B\n�I�����܂����H",
                                    ACMessageBox.BUTTON_YES
                                            | ACMessageBox.BUTTON_NO,
                                    ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                                firstTest.setSelected(true);
                                return;
                            }
                        }
                    }

                    // 2006/02/11[Tozo Tanaka] : replace begin
                    // canEdit?
                    // root.applySource();
                    // ���t���w�������󗓂ł��Anull�ɏ㏑�������Ȃ�
                    Object old = source.getData(bloodChemistryTestPoint
                            .getBindPath());
                    root.applySource();
                    Object now = source.getData(bloodChemistryTestPoint
                            .getBindPath());
                    if ((now == null) || ("".equals(now))) {
                        source.setData(bloodChemistryTestPoint.getBindPath(),
                                old);
                    }
                    // 2006/02/11[Tozo Tanaka] : replace end
                    if ("SHOSIN_OTHER".equals(firstTestKey)
                            && firstTest.isSelected()) {
                        firstTestPoint.setBindPath(firstTestKey);
                        firstTestPoint.setSource(source);
                        firstTestPoint.applySource();
                    }

                    // //�t�B�[���h�|��
                    // source.setData("OUTPUT_PATTERN",
                    // source.getData("SEIKYUSHO_OUTPUT_PATTERN"));

                    changed = true;
                    dispose();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ACMessageBox.show("�ŐV�̕ی��҂̏��𔽉f�����܂����H\n�������z���ς��\��������܂��B",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {
                    try {
                        // �K�p�O�̃f�[�^��ޔ�
                        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
                        // �\�[�X�̎擾�O�ɍ폜�ƂȂ����f�W�^���f�����������Z�͏��O
                        xrayDigitalImaging.setSelected(false);
                        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
                        
                        VRBindSource old = calcRoot.getSource();
                        VRBindSource tmp = (VRBindSource) calcRoot
                                .createSource();
                        calcRoot.setSource(tmp);
                        calcRoot.applySource();
                        calcRoot.setSource(old);

                        useDefaultPoint();
                        followBind();
                        
                        // 2006/08/03 TODO
                        // useDefaultPoint()���Ŏ擾����SHOSIN_TAISHOU�`�F�b�N�̒l��ێ�����B
                        // �����Ɍ��݂̓_���ōČv�Z�����l��\������B
                        // Addition - begin [Masahiko Higuchi]
                        tmp.setData("SHOSIN_TAISHOU", source
                                .getData("SHOSIN_TAISHOU"));
                        source.putAll((Map) tmp);
                        // ���f�`�F�b�N�����Ă���ꍇ�̂ݍČv�Z����B
                        // �`�F�b�N�����̏�Ԃł̕\���ύX�΍�
                        if (new Integer(1).equals(source
                                .getData("SHOSIN_TAISHOU"))) {
                            firstTestPoint
                                    .setText(IkenshoConstants.FORMAT_DOUBLE1
                                            .format(new Double(String
                                                    .valueOf(VRBindPathParser
                                                            .get(firstTestKey,
                                                                    source)))));
                            calcCost();
                        }
                        // Addition - end
                        // �E�v���������߂�
                        old = calcRoot.getSource();
                        calcRoot.setSource(tmp);
                        calcRoot.bindSource();
                        calcRoot.setSource(old);
                    } catch (Exception ex) {
                        IkenshoCommon.showExceptionMessage(ex);
                    }
                }
            }
        });

        bloodChemistryTestPointChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (new IkenshoBloodChemistry(pointMap).showModal()) {
                    // �ύX
                    getCheckItemListener(bloodChemistryTest).follow(true);
                }
            }
        });

        firstTest.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // ���f�_��

                    try {
                        if ("SHOSIN_OTHER".equals(firstTestKey)) {
                            firstTestPoint.setText(lastInputedFirstTestPoint);
                            firstTestPoint.setEditable(true);
                        } else {
                            firstTestPoint
                                    .setText(IkenshoConstants.FORMAT_DOUBLE1
                                            .format(new Double(String
                                                    .valueOf(VRBindPathParser
                                                            .get(firstTestKey,
                                                                    pointMap)))));
                            firstTestPoint.setEditable(false);
                        }
                        spaceType.setText(firstTestName);
                    } catch (ParseException ex) {
                        IkenshoCommon.showExceptionMessage(ex);
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    if (!"".equals(firstTestPoint.getText())) {
                        lastInputedFirstTestPoint = firstTestPoint.getText();
                    }
                    firstTestPoint.setEditable(false);
                    spaceType.setText("");
                    firstTestPoint.setText("");
                }
                calcCost();
            }
        });

        firstTestPoint.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        calcCost();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        calcCost();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        calcCost();
                    }
                });

        addCheckItemListenerMap(pointMap, new String[] { "EXP_KS",
                "EXP_KIK_MKI", "EXP_KIK_KEKK" }, new JCheckBox[] { bloodSaishu,
                bloodBasicTest, ketsuekigakuTestCost }, new JTextField[] {
                bloodSaishuPoint, bloodBasicTestPoint,
                ketsuekigakuTestCostPoint }, new String[] {
                bloodSaishu.getText(), bloodBasicTest.getText(),
                ketsuekigakuTestCost.getText() }, calcBloodBasicPoint,
                calcBloodBasicSummary);

        addCheckItemListenerMap(pointMap, new String[] { "EXP_KKK_KKK",
                "EXP_KKK_SKK" }, new JCheckBox[] { bloodChemistryTest,
                seikagakuTestCost }, new JTextField[] {
                bloodChemistryTestPoint, seikagakuTestCostPoint },
                new String[] { bloodChemistryTest.getText() + "(10���ڈȏ�)",
                        seikagakuTestCost.getText() }, calcBloodChemistryPoint,
                calcBloodChemistrySummary);

        addCheckItemListenerMap(pointMap, new String[] { "EXP_NITK" },
                new JCheckBox[] { nyouTest },
                new JTextField[] { nyouTestPoint }, new String[] { "" },
                calcNyoTestPoint, calcNyoTestSummary);
        // 2009/01/09[Tozo Tanaka] : replace begin
        // addCheckItemListenerMap(pointMap, new String[] { "EXP_XRAY_TS",
        // "EXP_XRAY_SS", "EXP_XRAY_FILM" }, new JCheckBox[] { xrayBasic,
        // xrayPhotoCheck, xrayFilm }, new JTextField[] { xrayBasicPoint,
        // xrayPhotoCheckPoint, xrayFilmPoint }, new String[] {
        // xrayBasic.getText(), xrayPhotoCheck.getText(),
        // xrayFilm.getText() }, calcXRayPoint, calcXRaySummary);
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
        addCheckItemListenerMap(pointMap, new String[] { "EXP_XRAY_TS",
                "EXP_XRAY_TS_DIGITAL", "EXP_XRAY_SS", "EXP_XRAY_FILM",
                "EXP_XRAY_DIGITAL_MANAGEMENT", "EXP_XRAY_DIGITAL_FILM",
                "EXP_XRAY_DIGITAL_IMAGING" },
                new JCheckBox[] { xrayBasic, xrayDigital, xrayPhotoCheck,
                        xrayFilm, xrayDigitalImageManagement, xrayDigitalFilm,
                        xrayDigitalImaging }, new JTextField[] {
                        xrayBasicPoint, xrayDigitalPoint, xrayPhotoCheckPoint,
                        xrayFilmPoint, xrayDigitalImageManagementPoint,
                        xrayDigitalFilmPoint, xrayDigitalImagingPoint },
                new String[] { xrayBasic.getText(), xrayDigital.getText(),
                        xrayPhotoCheck.getText(), xrayFilm.getText(),
                        xrayDigitalImageManagement.getText(),
                        xrayDigitalFilm.getText(),
                        xrayDigitalImaging.getText(), }, calcXRayPoint,
                calcXRaySummary);
//            xrayFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayDigitalImageManagement, xrayDigitalFilm,
//                            xrayDigitalImaging }));
//            xrayDigitalImageManagement.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayFilm, xrayDigitalFilm,
//                            xrayDigitalImaging }));
//            xrayDigitalFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayFilm, xrayDigitalImageManagement },
//                            new JCheckBox[] { xrayDigitalImaging }));
//            xrayDigitalImaging.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayFilm, xrayDigitalImageManagement },
//                            new JCheckBox[] { xrayDigitalFilm }));
        xrayFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayDigitalImageManagement, xrayDigitalFilm }));
        xrayDigitalImageManagement.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayFilm, xrayDigitalFilm }));
        xrayDigitalFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayFilm, xrayDigitalImageManagement },
                        new JCheckBox[] { }));
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit end
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        xrayBasic.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayDigital},
                        new JCheckBox[] {}));        
        xrayDigital.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayBasic},
                        new JCheckBox[] {}));
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        
//        xrayDigitalFilm.addItemListener(new IkenshoFollowCheckItemListener(
//                new JCheckBox[] { xrayDigitalImaging }));
//        xrayDigitalImaging.addItemListener(new IkenshoFollowCheckItemListener(
//                new JCheckBox[] { xrayDigitalFilm }));
        // 2009/01/09[Tozo Tanaka] : replace end

        bloodChemistryTest.addItemListener(new ACFollowDisabledItemListener(
                new JComponent[] { bloodChemistryTestPointChange }));

    }

    // 2009/01/13[Tozo Tanaka] : add begin
    protected class IkenshoExclusionDisabledItemListener implements ItemListener{
        private JComponent[] disableTargets;
        private JCheckBox[] followTargets;
        public IkenshoExclusionDisabledItemListener(JComponent[] disableTargets) {
            this.disableTargets = disableTargets;
        }
        public IkenshoExclusionDisabledItemListener(JComponent[] disableTargets, JCheckBox[] followTargets) {
            this.disableTargets = disableTargets;
            this.followTargets = followTargets;
        }
     
        public void itemStateChanged(ItemEvent e) {
            boolean enabled;
            if (e.getStateChange() == ItemEvent.SELECTED) {
                enabled = false;
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                enabled = true;
                if(followTargets!=null){
                    for(int i=0; i<followTargets.length; i++){
                        if(followTargets[i].isSelected()){
                            //�A������`�F�b�N�̂����ꂩ��ɂ܂��`�F�b�N�����Ă�����A������Ԃ��������Ȃ�
                            return;
                        }
                    }
                }
            } else {
                return;
            }
            if (disableTargets != null) {
                int end = disableTargets.length;
                for (int i = 0; i < end; i++) {
                    disableTargets[i].setEnabled(enabled);
                }
            }
        }
        
    }
    protected class IkenshoFollowCheckItemListener implements ItemListener{
        private JCheckBox[] followTargets;
        public IkenshoFollowCheckItemListener(JCheckBox[] followTargets) {
            this.followTargets = followTargets;
        }
     
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if(followTargets!=null){
                    for(int i=0; i<followTargets.length; i++){
                        if(!followTargets[i].isSelected()){
                            //�A������`�F�b�N�ɖ��I���̂��̂�����΁A�I����Ԃɂ���
                            followTargets[i].setSelected(true);
                        }
                    }
                }
            }
        }
        
    }
    // 2009/01/13[Tozo Tanaka] : add end
    
    public void calcCost() {
        JTextField[] totalPoints = new JTextField[] { firstTestPoint,
                calcXRayPoint, calcBloodBasicPoint, calcBloodChemistryPoint,
                calcNyoTestPoint };
        JTextField fullTotalPoint = calcCheckTotalPoint;
        JTextField fullTotalCost = calcCheckTotalCost;

        double val = 0;
        int end = totalPoints.length;
        for (int i = 0; i < end; i++) {
            String text = totalPoints[i].getText();
            if (!("".equals(text))) {
                val += Double.parseDouble(text);
            }
        }
        if (val == 0) {
            fullTotalPoint.setText("");
            fullTotalCost.setText("");
        } else {
            fullTotalPoint.setText(IkenshoConstants.FORMAT_DOUBLE1
                    .format(new Double(val)));

            double dblValue = new Double(val * 10).doubleValue();
            int intValue = (int) dblValue;
            double dblTmp = Double.parseDouble(String.valueOf(intValue));

            if (dblValue == dblTmp) {
                fullTotalCost.setText(new DecimalFormat("#").format(dblValue));
            } else {
                fullTotalCost
                        .setText(new DecimalFormat("#.#").format(dblValue));
            }
        }
    }

    /**
     * �w��`�F�b�N�Ɋ֘A�t����ꂽ�`�F�b�N���X�i��Ԃ��܂��B
     * 
     * @param check �Ώۂ̃`�F�b�N
     * @return �`�F�b�N���X�i
     */
    protected IkenshoFollowCalcItemListener getCheckItemListener(JCheckBox check) {
        return (IkenshoFollowCalcItemListener) checkItemListenerMap.get(check);
    }

    /**
     * �w�肵���֘A�R���|�[�l���g�Q�Ƀ`�F�b�N���X�i��ǉ����܂��B
     * 
     * @param source �_���}�b�v
     * @param keys �Ή�����_���L�[�Q
     * @param checks �t�����Ċ֘A����`�F�b�N�Q
     * @param points �t�����Ċ֘A����_�����Q
     * @param summarys �t�����ďo�͂���E�v�Q
     * @param totalPoint ���v�_����
     * @param totalSummary ���v�̓E�v��
     */
    protected void addCheckItemListenerMap(VRMap source, String[] keys,
            JCheckBox[] checks, JTextField[] points, String[] summarys,
            JTextField totalPoint, JTextField totalSummary) {
        int end = keys.length;
        for (int i = 0; i < end; i++) {
            IkenshoFollowCalcItemListener l = new IkenshoFollowCalcItemListener(
                    points[i], source, keys[i], checks, points, summarys,
                    totalPoint, totalSummary, this);
            checkItemListenerMap.put(checks[i], l);
            checks[i].addItemListener(l);
        }
    }

    /**
     * �O��ʂ�������p�����\�[�X���A�f�t�H���g�l�̓K�p���܂߂Đݒ肵�܂��B
     * 
     * @throws Exception ������O
     */
    protected void useDefaultPoint() throws Exception {
        // �f�t�H���g�ݒ�
        // pointMap.clear();

        VRMap newSource = (VRMap) points.createSource();
        newSource.setData("SHOSIN_TAISHOU", ACCastUtilities.toInteger(firstTest
                .getValue()));
        loadDefault();

        // VRMap newSource = (VRMap) points.createSource();
        points.setSource(newSource);
        points.applySource();
        tax.applySource();
        firstTest.applySource();
        // newSource.setData("SHOSIN_TAISHOU",
        // source.getData("SHOSIN_TAISHOU"));
        newSource.setData("TAX", source.getData("TAX"));
        points.setSource(source);

        pointMap.putAll(defaultInsure);
        if (!pointMap.containsKey("SHOSIN_OTHER")) {
            pointMap.setData("SHOSIN_OTHER", new Double(0));
        }
        source.putAll(defaultInsure);

        // �����l��Ǎ��񂾌�ɉ��߂ď㏑������B
        // TODO ���I�𒆂̈�Ë@�ւ̓d�q�����Z�̗L���ɂ���āA���f�_���̉��Z���s����
        // �d�q�����Z�t���O���n���ė����ꍇ
        if (VRBindPathParser.has("DR_ADD_IT", source)) {
            // �d�q�����Z�_����double�̒l�Ƃ��ĕێ�����
            // 2006/02/07[Tozo Tanaka] : replace begin
            // if (ACCastUtilities.toInt(VRBindPathParser.get("DR_ADD_IT",
            // source)) == 1){
            if (IkenshoCommon.canAddIT(formatKubun, ACCastUtilities
                    .toInt(VRBindPathParser.get("DR_ADD_IT", source)) == 1,
                    source)) {
                // 2006/02/07[Tozo Tanaka] : replace end
                double addIT = ACCastUtilities.toDouble(source
                        .getData("SHOSIN_ADD_IT"), 0);
                double shoshinHos = ACCastUtilities.toDouble(source
                        .getData("SHOSIN_HOSPITAL"), 0);
                double shoshinSin = ACCastUtilities.toDouble(source
                        .getData("SHOSIN_SINRYOUJO"), 0);

              // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
//                double shoshinHosAdd = shoshinHos += addIT;
//                double shoshinSinAdd = shoshinSin += addIT;
              double shoshinHosAdd = shoshinHos;
              double shoshinSinAdd = shoshinSin;
              // [ID:0000601][Masahiko Higuchi] 2010/02 edit end

                source.setData("SHOSIN_HOSPITAL", new Double(shoshinHosAdd));
                source.setData("SHOSIN_SINRYOUJO", new Double(shoshinSinAdd));
                pointMap.setData("SHOSIN_HOSPITAL", new Double(shoshinHosAdd));
                pointMap.setData("SHOSIN_SINRYOUJO", new Double(shoshinSinAdd));
            }
        }

        source.putAll(newSource);

        if ("SHOSIN_OTHER".equals(firstTestKey)) {
            lastInputedFirstTestPoint = "0";
            if (firstTestPoint.isEditable()) {
                firstTestPoint.setText("0");
            }
        }

        // 2006/09/09 [Tozo Tanaka] : remove begin
        // //2006/09/07 [Tozo Tanaka] : add begin
        // source.put("NEW_DR_ADD_IT", source.get("DR_ADD_IT"));
        // //2006/09/07 [Tozo Tanaka] : add end
        // 2006/09/09 [Tozo Tanaka] : remove end

    }

    /**
     * ���ڃo�C���h���s�\�ȃR���g���[���ɒl��ݒ肵�܂��B
     * 
     * @throws ParseException ��͗�O
     */
    protected void followBind() throws ParseException {
        root.bindSource();

        Iterator it = checkItemListenerMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ((IkenshoFollowCalcItemListener) entry.getValue())
                    .follow(((JCheckBox) entry.getKey()).isSelected());
        }

        if ("".equals(toIkenshoName.getText())) {
            toIkenshoNoHead.setVisible(false);
            toIkenshoNoFoot.setVisible(false);
        } else {
            toIkenshoNoHead.setVisible(true);
            toIkenshoNoFoot.setVisible(true);
        }
        if ("".equals(toConsultationName.getText())) {
            toConsultationNoHead.setVisible(false);
            toConsultationNoFoot.setVisible(false);
        } else {
            toConsultationNoHead.setVisible(true);
            toConsultationNoFoot.setVisible(true);
        }

    }

    /**
     * �ʒu�����������܂��B
     */
    private void init() {
        // �E�B���h�E�̃T�C�Y
        // 2009/01/06 [Tozo Tanaka] Replace - begin
        setSize(new Dimension(770, getHeight()));
        // setSize(new Dimension(770, 590));
        // 2009/01/06 [Tozo Tanaka] Replace - end
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

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ��������O
     */
    private void jbInit() throws Exception {
        bodyGroup.setLayout(new VRLayout());
        buttons.setLayout(new VRLayout());
        taxPanel.setLayout(new VRLayout());
        toGroup.setLayout(new VRLayout());
        taxGroup.setLayout(taxGroupLayout);
        taxGroupLayout.setAlignment(VRLayout.RIGHT);
        costGroup.setLayout(costGroupLayout);
        costGroupLayout.setAlignment(VRLayout.LEFT);
        bloodChemistryTestPointsLayout.setHgap(5);
        bloodChemistryTestPointsLayout.setAutoWrap(false);
        bloodChemistryTestPoints.setLayout(bloodChemistryTestPointsLayout);

        this.setTitle("�f�@�E�������e����");
        root.setLayout(rootLayout);
        toGroup.setText("������");
        bodyGroup.setText("�f�@�E�������e�i�ӌ����쐬�̂��߂ɍs�Ȃ�����{�I�Ȑf�@�E������p����͂��Ă��������j");
        costGroup.setText("�ӌ����쐬��");
        taxGroup.setText("����ŗ�");
        taxUnit.setText("��");
        // tax.setMargin(new Insets(1, 5, 2, 4));
        tax.setColumns(3);
        tax.setHorizontalAlignment(SwingConstants.RIGHT);
        tax.setBindPath("TAX");
        tax.setMaxLength(3);
        toConsultationHead.setText("�f�@�E������������i�ԍ��j�F");
        toIkenshoHead.setText("�ӌ����쐬��������i�ԍ��j�F");
        toConsultationName.setBindPath("SKS_INSURER_NM");
        toConsultationName.setText("�e�X�g������");
        costType.setEditable(false);
        costType.setColumns(2);
        costType.setBindPath("AFFAIR_FIRST_CREATE");
        costTarget.setEditable(false);
        costTarget.setColumns(2);
        costTarget.setBindPath("AFFAIR_CARE_TYPE");
        costUnit.setText("�~");
        cost.setEditable(false);
        cost.setColumns(8);
        cost.setHorizontalAlignment(SwingConstants.RIGHT);
        cancel.setMnemonic('C');
        cancel.setText("�L�����Z��(C)");
        ok.setMnemonic('S');
        ok.setText("�m��(S)");
        reset.setMnemonic('T');
        reset.setText("������E�_���Đݒ�(T)");
        calcRoot.setLayout(calcRootLayout);
        // 2009/01/13 [Tozo Tanaka] Delete - begin
//        points.setLayout(pointsLayout);
//        pointsRight.setLayout(pointsRightLayout);
//        pointsLeft.setLayout(pointsLeftLayout);
//        pointsRightSpacer1.setPreferredSize(new Dimension(100, 70));
        // 2009/01/13 [Tozo Tanaka] Delete - end
        rootLayout.setVgap(0);
        points.add(pointsRight, VRLayout.EAST);
        points.add(pointsLeft, VRLayout.WEST);
        xrayFilm.setText("�t�B����(��p)");
        xrayFilm.setBindPath("XRAY_FILM");
        bloodBasic.setText("���t��ʌ���");
        bloodBasic.setBindPath("");
        bloodBasicTest.setText("�������t��ʌ���");
        bloodBasicTest.setBindPath("BLD_IPPAN_MASHOU_KETUEKI");
        ketsuekigakuTestCost.setText("���t�w�I�������f��");
        ketsuekigakuTestCost.setBindPath("BLD_IPPAN_EKIKAGAKUTEKIKENSA");
        bloodChemistry.setText("���t���w����");
        bloodChemistryTest.setText("���t���w����");
        bloodChemistryTest.setBindPath("BLD_KAGAKU_KETUEKIKAGAKUKENSA");
        seikagakuTestCost.setText("�����w�I����(I)���f��");
        seikagakuTestCost.setBindPath("BLD_KAGAKU_SEIKAGAKUTEKIKENSA");
        nyouTest.setText("�A����ʕ����萫����ʌ���");
        nyouTest.setBindPath("NYO_KENSA");
        xray.setText("�����P��X���B�e");
        xray.setBindPath("");
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
        //xrayBasic.setText("�P���B�e");
        xrayBasic.setText("�P���B�e(�A�i���O)");
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
        xrayBasic.setBindPath("XRAY_TANJUN_SATUEI");
        xrayPhotoCheck.setText("�ʐ^�f�f(����)");
        xrayPhotoCheck.setBindPath("XRAY_SHASIN_SINDAN");
        bloodSaishu.setText("���t�̎�(�Ö�)");
        bloodSaishu.setBindPath("BLD_SAISHU");
        bloodBasicSpacer.setText("�@�@");
        xraySpacer.setText("�@");
        bloodChemistryTestPointChange.setEnabled(false);
        bloodChemistryTestPointChange.setMnemonic('U');
        bloodChemistryTestPointChange.setText("�ύX(U)");
        bloodChemistryTestPoint.setEditable(false);
        // bloodChemistryTestPoint.setMargin(new Insets(1, 5, 2, 4));
        bloodChemistryTestPoint.setColumns(7);
        bloodChemistryTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        // 2006/02/06[Tozo Tanaka] : add begin
        bloodChemistryTestPoint.setBindPath("EXP_KKK_KKK");
        // 2006/02/06[Tozo Tanaka] : add end
        bloodChemistryTestPointUnit.setText("�_");
        seikagakuTestCostPoint.setEditable(false);
        // seikagakuTestCostPoint.setMargin(new Insets(1, 5, 2, 4));
        seikagakuTestCostPoint.setColumns(7);
        seikagakuTestCostPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        seikagakuTestCostPointUnit.setText("�_");
        ketsuekigakuTestCostPoint.setEditable(false);
        // ketsuekigakuTestCostPoint.setMargin(new Insets(1, 5, 2, 4));
        ketsuekigakuTestCostPoint.setColumns(7);
        ketsuekigakuTestCostPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        ketsuekigakuTestCostPointUnit.setText("�_");
        bloodBasicTestPoint.setEditable(false);
        // bloodBasicTestPoint.setMargin(new Insets(1, 5, 2, 4));
        bloodBasicTestPoint.setColumns(7);
        bloodBasicTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        bloodBasicTestPointUnit.setText("�_");
        bloodSaishuPoint.setEditable(false);
        // bloodSaishuPoint.setMargin(new Insets(1, 5, 2, 4));
        bloodSaishuPoint.setColumns(7);
        bloodSaishuPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        bloodSaishuPointUnit.setText("�_");
        nyouTestPoint.setEditable(false);
        // nyouTestPoint.setMargin(new Insets(1, 5, 2, 4));
        nyouTestPoint.setColumns(7);
        nyouTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        nyouTestPointUnit.setText("�_");
        xrayBasicPoint.setEditable(false);
        // xrayBasicPoint.setMargin(new Insets(1, 5, 2, 4));
        xrayBasicPoint.setColumns(7);
        xrayBasicPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayBasicPointUnit.setText("�_");
        xrayPhotoCheckPoint.setEditable(false);
        // xrayPhotoCheckPoint.setMargin(new Insets(1, 5, 2, 4));
        xrayPhotoCheckPoint.setColumns(7);
        xrayPhotoCheckPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayPhotoCheckPointUnit.setText("�_");
        xrayFilmPoint.setEditable(false);
        // xrayFilmPoint.setMargin(new Insets(1, 5, 2, 4));
        xrayFilmPoint.setColumns(7);
        xrayFilmPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayFilmPointUnit.setText("�_");

        // 2009/01/06 [Tozo Tanaka] Add - begin
        // �f�W�^���B�e�̏ꍇ�F�t�B�������X�̏ꍇ�F�d�q�摜�Ǘ����Z
        xrayDigitalImageManagement.setText("�d�q�摜�Ǘ����Z");
        xrayDigitalImageManagement.setBindPath("XRAY_DIGITAL_MANAGEMENT");
        xrayDigitalImageManagementPoint.setEditable(false);
        xrayDigitalImageManagementPoint.setColumns(7);
        xrayDigitalImageManagementPoint
                .setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalImageManagementPointUnit.setText("�_");

        // �f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�摜�L�^�p�t�B����(��p)
        xrayDigitalFilm.setText("�摜�L�^�p�t�B����(��p)");
        xrayDigitalFilm.setBindPath("XRAY_DIGITAL_FILM");
        xrayDigitalFilmPoint.setEditable(false);
        xrayDigitalFilmPoint.setColumns(7);
        xrayDigitalFilmPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalFilmPointUnit.setText("�_");

        // �f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�f�W�^���f�����������Z
        xrayDigitalImaging.setText("�f�W�^���f�����������Z");
        xrayDigitalImaging.setBindPath("XRAY_DIGITAL_IMAGING");
        xrayDigitalImagingPoint.setEditable(false);
        xrayDigitalImagingPoint.setColumns(7);
        xrayDigitalImagingPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalImagingPointUnit.setText("�_");

        xrayDigitalFilmPoints.add(xrayDigitalFilmPoint, null);
        xrayDigitalFilmPoints.add(xrayDigitalFilmPointUnit, null);
        xrayDigitalImagingPoints.add(xrayDigitalImagingPoint, null);
        xrayDigitalImagingPoints.add(xrayDigitalImagingPointUnit, null);
        xrayDigitalImageManagementPoints.add(xrayDigitalImageManagementPoint,
                null);
        xrayDigitalImageManagementPoints.add(
                xrayDigitalImageManagementPointUnit, null);
        // 2009/01/06 [Tozo Tanaka] Add - end

        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        // �P���B�e�i�f�W�^���j
        xrayDigital.setText("�P���B�e(�f�W�^��)");
        xrayDigital.setBindPath("XRAY_TANJUN_SATUEI_DIGITAL");
        xrayDigitalPoint.setEditable(false);
        xrayDigitalPoint.setColumns(7);
        xrayDigitalPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalPointUnit.setText("�_");
        // �R���|�[�l���g�z�u
        xrayDigitalPoints.add(xrayDigitalPoint, null);
        xrayDigitalPoints.add(xrayDigitalPointUnit, null);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        
        category.setText("����");
        category.setOpaque(true);
        category.setHorizontalAlignment(SwingConstants.CENTER);
        category.setBackground(Color.lightGray);
        category.setBorder(BorderFactory.createLineBorder(Color.black));
        point.setBorder(BorderFactory.createLineBorder(Color.black));
        point.setBackground(Color.lightGray);
        point.setOpaque(true);
        point.setHorizontalAlignment(SwingConstants.CENTER);
        point.setText("�_��");
        summary.setBorder(BorderFactory.createLineBorder(Color.black));
        summary.setBackground(Color.lightGray);
        summary.setOpaque(true);
        summary.setHorizontalAlignment(SwingConstants.CENTER);
        summary.setText("�E�v");
        calcXRay.setBorder(BorderFactory.createLineBorder(Color.black));
        calcXRay.setOpaque(true);
        calcXRay.setBackground(Color.lightGray);
        calcXRay.setText("�����P��X���B�e");
        calcCheckTotalCostMessage.setBorder(BorderFactory
                .createLineBorder(Color.black));
        calcCheckTotalCostMessage.setBackground(Color.lightGray);
        calcCheckTotalCostMessage.setOpaque(true);
        calcCheckTotalCostMessage.setPreferredSize(new Dimension(200, 17));
        calcCheckTotalCostMessage.setText("�_�����v�~10�~");
        calcCheckTotal.setText("���v");
        calcCheckTotal.setOpaque(true);
        calcCheckTotal.setBackground(Color.lightGray);
        calcCheckTotal.setBorder(BorderFactory.createLineBorder(Color.black));
        calcNyoTest.setText("�A����ʕ����萫����ʌ���");
        calcNyoTest.setOpaque(true);
        calcNyoTest.setBackground(Color.lightGray);
        calcNyoTest.setBorder(BorderFactory.createLineBorder(Color.black));
        calcBloodChemistry.setText("���t���w����");
        calcBloodChemistry.setOpaque(true);
        calcBloodChemistry.setBackground(Color.lightGray);
        calcBloodChemistry.setBorder(BorderFactory
                .createLineBorder(Color.black));
        calcBloodBasic.setText("���t��ʌ���");
        calcBloodBasic.setOpaque(true);
        calcBloodBasic.setBackground(Color.lightGray);
        calcBloodBasic.setBorder(BorderFactory.createLineBorder(Color.black));
        spaceType.setEditable(false);
        spaceType.setColumns(4);
        spaceType.setHorizontalAlignment(SwingConstants.CENTER);
        calcXRayPoint.setColumns(7);
        calcXRayPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcXRayPoint.setEditable(false);
        calcBloodBasicPoint.setColumns(7);
        calcBloodBasicPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcBloodBasicPoint.setEditable(false);
        calcBloodChemistryPoint.setColumns(7);
        calcBloodChemistryPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcBloodChemistryPoint.setEditable(false);
        calcCheckTotalPoint.setColumns(7);
        calcCheckTotalPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcCheckTotalPoint.setEditable(false);
        firstTestSummary.setColumns(50);
        firstTestSummary.setBindPath("SHOSIN_TEKIYOU");
        firstTestSummary.setMaxLength(50);
        calcCheckTotalCost.setEditable(false);
        calcCheckTotalCost.setColumns(13);
        calcCheckTotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
        calcCheckTotalCost.setText("2700");
        firstTestPoint.setEditable(false);
        firstTestPoint.setColumns(7);
        firstTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        firstTestPoint.setMaxLength(6);
        // firstTestPoint.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        // firstTestPoint.setFormat(IkenshoConstants.FORMAT_DOUBLE1);
        firstTest.setBackground(Color.white);
        firstTest.setBorder(BorderFactory.createLineBorder(Color.black));
        firstTest.setActionCommand("���f");
        firstTest.setText("���f�@�@�@");
        firstTest.setBindPath("SHOSIN_TAISHOU");
        rootLayout.setFitHGrid(false);
        rootLayout.setFitVLast(true);
        rootLayout.setFitHLast(true);
        calcNyoTestPoint.setEditable(false);
        calcNyoTestPoint.setColumns(7);
        calcNyoTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcCheckTotalCostUnit.setText("�~");
        calcCheckTotalCostUnit.setOpaque(true);
        calcCheckTotalCostUnit.setPreferredSize(new Dimension(13, 17));
        calcCheckTotalCostUnit.setBackground(Color.lightGray);
        calcCheckTotalCostUnit.setBorder(BorderFactory
                .createLineBorder(Color.black));
        calcBloodBasicSummary.setColumns(40);
        calcBloodBasicSummary.setBindPath("BLD_IPPAN_TEKIYOU");
        calcBloodBasicSummary.setMaxLength(50);
        calcBloodChemistrySummary.setColumns(50);
        calcBloodChemistrySummary.setBindPath("BLD_KAGAKU_TEKIYOU");
        calcBloodChemistrySummary.setMaxLength(50);
        calcXRaySummary.setColumns(50);
        calcXRaySummary.setBindPath("XRAY_TEKIYOU");
        calcXRaySummary.setMaxLength(50);
        calcNyoTestSummary.setMaxLength(50);
        calcNyoTestSummary.setColumns(50);
        calcNyoTestSummary.setBindPath("NYO_KENSA_TEKIYOU");
        calcCheckMessage2.setText("��");
        calcCheckMessage2.setOpaque(false);
        calcCheckMessage1.setMinimumSize(new Dimension(11, 60));
        calcCheckMessage1.setText("��");
        calcCheckMessages.setBackground(Color.lightGray);
        calcCheckMessages
                .setBorder(BorderFactory.createLineBorder(Color.black));
        toIkenshoName.setBindPath("ISS_INSURER_NM");
        toIkenshoName.setText("�e�X�g������");
        toIkenshoNoHead.setText("�i");
        toIkenshoNo.setBindPath("ISS_INSURER_NO");
        toIkenshoNo.setText("123456");
        toIkenshoNoFoot.setText("�j");
        toIkenshoPanelLayout.setHgap(0);
        toConsultationNo.setBindPath("SKS_INSURER_NO");
        toConsultationNo.setText("123456");
        toConsultationNoFoot.setText("�j");
        toConsultationNoHead.setText("�i");
        hiddenParameters.setText("�B���p�����^");
        outputPattern.setBindPath("SEIKYUSHO_OUTPUT_PATTERN");
        hakkouKubun.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        fdOutputKubun.setBindPath("FD_OUTPUT_UMU");
        ikenshoCharge.setBindPath("IKN_CHARGE");
        shoshin.setBindPath("SHOSIN");
        // 2009/01/13 [Tozo Tanaka] Delete - begin
//        pointsRightSpacer1.setText(" ");
        // 2009/01/13 [Tozo Tanaka] Delete - end
        hiddenParameters.add(shoshin, null);
        hiddenParameters.add(ikenshoCharge, null);
        hiddenParameters.add(fdOutputKubun, null);
        hiddenParameters.add(hakkouKubun, null);
        bloodBasicTestPoints.add(bloodBasicTestPoint, null);
        bloodBasicTestPoints.add(bloodBasicTestPointUnit, null);
        ketsuekigakuTestCostPoints.add(ketsuekigakuTestCostPoint, null);
        ketsuekigakuTestCostPoints.add(ketsuekigakuTestCostPointUnit, null);
        // 2009/01/13 [Tozo Tanaka] Replace - begin
//        pointsLeft.add(bloodChemistryTestPoints, new GridBagConstraints(2, 5,
//                1, 1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(ketsuekigakuTestCostPoints, new GridBagConstraints(2, 3,
//                1, 1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasicTestPoints, new GridBagConstraints(2, 2, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodSaishu, new GridBagConstraints(0, 0, 2, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasic, new GridBagConstraints(0, 1, 2, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasicTest, new GridBagConstraints(1, 2, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(ketsuekigakuTestCost, new GridBagConstraints(1, 3, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodChemistry, new GridBagConstraints(0, 4, 2, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodChemistryTest, new GridBagConstraints(1, 5, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(seikagakuTestCost, new GridBagConstraints(1, 6, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasicSpacer, new GridBagConstraints(0, 2, 1, 2,
//                0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodSaishuPoints, new GridBagConstraints(2, 0, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(seikagakuTestCostPoints, new GridBagConstraints(2, 6, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayBasicPoints, new GridBagConstraints(2, 2, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(nyouTest, new GridBagConstraints(0, 0, 2, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xray, new GridBagConstraints(0, 1, 2, 1, 100.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        pointsRight.add(xrayBasic, new GridBagConstraints(1, 2, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayPhotoCheck, new GridBagConstraints(1, 3, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayFilm, new GridBagConstraints(1, 4, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xraySpacer, new GridBagConstraints(0, 2, 1, 3, 0.0,
//                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayPhotoCheckPoints, new GridBagConstraints(2, 3, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayFilmPoints, new GridBagConstraints(2, 4, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(nyouTestPoints, new GridBagConstraints(2, 0, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(pointsRightSpacer1, new GridBagConstraints(0, 5, 3, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
        pointsLeft.setAutoWrap(false);
        pointsRight.setAutoWrap(false);
        pointsLeft.setHgrid(0);
        pointsLeft.setHgap(0);
        pointsRight.setHgrid(0);
        pointsRight.setHgap(0);
        //�`�F�b�N�{�b�N�X�̃T�C�Y���K��
        Dimension checkSpaceDimension = new Dimension(20, 10);
        for(int i=0; i<pointsSpacer.length; i++){
            pointsSpacer[i].setPreferredSize(checkSpaceDimension);
        }
        Dimension nyouTestDimension = nyouTest.getPreferredSize();
        Dimension checkMinDimension = new Dimension((int) Math.max(200,
                nyouTestDimension.getWidth() + 20), (int) Math.max(19,
                nyouTestDimension.getHeight()));
        ACIntegerCheckBox[] checks = new ACIntegerCheckBox[] { bloodSaishu,
                bloodBasic, bloodChemistry, nyouTest, xray, };
        for (int i = 0; i < checks.length; i++) {
            checks[i].setMinimumSize(checkMinDimension);
            checks[i].setPreferredSize(checkMinDimension);
        }
        checkMinDimension = new Dimension(
                (int) (checkMinDimension.getWidth() - checkSpaceDimension.getWidth()),
                (int) checkMinDimension.getHeight());
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
        checks = new ACIntegerCheckBox[] { bloodBasicTest,
                ketsuekigakuTestCost, bloodChemistryTest, seikagakuTestCost,
                xrayBasic, xrayDigital, xrayPhotoCheck, xrayFilm,
                xrayDigitalImageManagement, xrayDigitalFilm,
                xrayDigitalImaging, };
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit end
        for (int i = 0; i < checks.length; i++) {
            checks[i].setMinimumSize(checkMinDimension);
            checks[i].setPreferredSize(checkMinDimension);
        }
        
        int pointsSpacerIndex = 0;
        //���t�̎�(�Ö�)
        pointsLeft.add(bloodSaishu, VRLayout.FLOW);
        pointsLeft.add(bloodSaishuPoints, VRLayout.FLOW_RETURN);
        //���t��ʌ���
        pointsLeft.add(bloodBasic, VRLayout.FLOW_RETURN);
        //���t��ʌ������������t��ʌ���
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(bloodBasicTest, VRLayout.FLOW);
        pointsLeft.add(bloodBasicTestPoints, VRLayout.FLOW_RETURN);
        //���t��ʌ��������t�w�I�������f��
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(ketsuekigakuTestCost, VRLayout.FLOW);
        pointsLeft.add(ketsuekigakuTestCostPoints, VRLayout.FLOW_RETURN);
        //���t���w����
        pointsLeft.add(bloodChemistry, VRLayout.FLOW_RETURN);
        //���t���w���������t���w����
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(bloodChemistryTest, VRLayout.FLOW);
        pointsLeft.add(bloodChemistryTestPoints, VRLayout.FLOW_RETURN);
        //���t���w�����������w�I����(I)���f��
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(seikagakuTestCost, VRLayout.FLOW);
        pointsLeft.add(seikagakuTestCostPoints, VRLayout.FLOW_RETURN);

        //�A����ʕ����萫����ʌ���
//        pointsLeft.add(nyouTest, VRLayout.FLOW);
//        pointsLeft.add(nyouTestPoints, VRLayout.FLOW_RETURN);
        pointsRight.add(nyouTest, VRLayout.FLOW);
        pointsRight.add(nyouTestPoints, VRLayout.FLOW_RETURN);
        
        //�����P��X���B�e
        pointsRight.add(xray, VRLayout.FLOW_RETURN);
        //�����P��X���B�e���P���B�e
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayBasic, VRLayout.FLOW);
        pointsRight.add(xrayBasicPoints, VRLayout.FLOW_RETURN);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        //�����P��X���B�e���P���B�e(�f�W�^���j
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayDigital, VRLayout.FLOW);
        pointsRight.add(xrayDigitalPoints, VRLayout.FLOW_RETURN);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        //�����P��X���B�e���ʐ^�f�f(����)
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayPhotoCheck, VRLayout.FLOW);
        pointsRight.add(xrayPhotoCheckPoints, VRLayout.FLOW_RETURN);
        //�����P��X���B�e���t�B����(��p)
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayFilm, VRLayout.FLOW);
        pointsRight.add(xrayFilmPoints, VRLayout.FLOW_RETURN);
        //�����P��X���B�e���d�q�摜�Ǘ����Z
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayDigitalImageManagement, VRLayout.FLOW);
        pointsRight.add(xrayDigitalImageManagementPoints, VRLayout.FLOW_RETURN);
        //�����P��X���B�e���摜�L�^�p�t�B����(��p)
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayDigitalFilm, VRLayout.FLOW);
        pointsRight.add(xrayDigitalFilmPoints, VRLayout.FLOW_RETURN);
        //�����P��X���B�e���f�W�^���f�����������Z
        // [ID:0000601][Masahiko Higuchi] 2010/02 del begin �f�Õ�V�P���̕ύX�Ή�
        // ���̃X�y�[�X�������Mac�Ō��؂��̂�Add���Ȃ�
        //pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        // [ID:0000601][Masahiko Higuchi] 2010/02 del end
        pointsRight.add(xrayDigitalImaging, VRLayout.FLOW);
        pointsRight.add(xrayDigitalImagingPoints, VRLayout.FLOW_RETURN);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        // ��\����
        xrayDigitalImaging.setVisible(false);
        xrayDigitalImagingPoints.setVisible(false);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        // 2009/01/13 [Tozo Tanaka] Replace - end

        seikagakuTestCostPoints.add(seikagakuTestCostPoint, null);
        seikagakuTestCostPoints.add(seikagakuTestCostPointUnit, null);
        bloodSaishuPoints.add(bloodSaishuPoint, null);
        bloodSaishuPoints.add(bloodSaishuPointUnit, null);
        nyouTestPoints.add(nyouTestPoint, null);
        nyouTestPoints.add(nyouTestPointUnit, null);
        xrayBasicPoints.add(xrayBasicPoint, null);
        xrayBasicPoints.add(xrayBasicPointUnit, null);
        xrayPhotoCheckPoints.add(xrayPhotoCheckPoint, null);
        xrayPhotoCheckPoints.add(xrayPhotoCheckPointUnit, null);
        xrayFilmPoints.add(xrayFilmPoint, null);
        xrayFilmPoints.add(xrayFilmPointUnit, null);
        bloodChemistryTestPoints.add(bloodChemistryTestPoint, null);
        bloodChemistryTestPoints.add(bloodChemistryTestPointUnit, null);
        bloodChemistryTestPoints.add(bloodChemistryTestPointChange, null);
        costs.add(costTarget, null);
        costs.add(costType, null);
        costs.add(cost, null);
        costs.add(costUnit, null);
        taxs.add(tax, null);
        taxs.add(taxUnit, null);
        toIkenshoPanelLayout.setAutoWrap(false);
        toIkenshoPanel.setLayout(toIkenshoPanelLayout);
        toIkenshoPanel.add(toIkenshoHead, VRLayout.FLOW);
        toConsultationPanelLayout.setAutoWrap(false);
        toConsultationPanelLayout.setHgap(0);
        toConsultationPanel.setLayout(toConsultationPanelLayout);
        
        // 2009/01/13 [Tozo Tanaka] Replace - begin
//        calcRoot.add(category, new GridBagConstraints(0, 0, 3, 1, 30.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(point, new GridBagConstraints(3, 0, 1, 1, 100.0, 100.0,
//                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
//                        0, 0, 0, 0), 0, 0));
//        calcRoot.add(summary, new GridBagConstraints(4, 0, 3, 1, 100.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(calcXRay, new GridBagConstraints(1, 2, 2, 1, 20.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodBasic, new GridBagConstraints(1, 3, 2, 1, 20.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodChemistry, new GridBagConstraints(1, 4, 2, 1,
//                20.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcNyoTest, new GridBagConstraints(1, 5, 2, 1, 20.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotal, new GridBagConstraints(0, 6, 3, 1, 30.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalCostMessage, new GridBagConstraints(4, 6, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(spaceType, new GridBagConstraints(2, 1, 1, 1, 10.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(calcXRayPoint, new GridBagConstraints(3, 2, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodBasicPoint, new GridBagConstraints(3, 3, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodChemistryPoint, new GridBagConstraints(3, 4, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalPoint, new GridBagConstraints(3, 6, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(firstTestSummary, new GridBagConstraints(4, 1, 3, 1,
//                300.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalCost, new GridBagConstraints(5, 6, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(firstTest, new GridBagConstraints(0, 1, 2, 1, 20.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(firstTestPoint, new GridBagConstraints(3, 1, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 3));
//        calcRoot.add(calcNyoTestPoint, new GridBagConstraints(3, 5, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalCostUnit, new GridBagConstraints(6, 6, 1, 1,
//                10.0, 100.0, GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcXRaySummary, new GridBagConstraints(4, 2, 3, 1, 300.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodBasicSummary, new GridBagConstraints(4, 3, 3, 1,
//                300.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodChemistrySummary, new GridBagConstraints(4, 4, 3,
//                1, 300.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcNyoTestSummary, new GridBagConstraints(4, 5, 3, 1,
//                300.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckMessages, new GridBagConstraints(0, 2, 1, 4, 0.0,
//                0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));        
        calcRoot.add(category, new GridBagConstraints(0, 0, 3, 1, 30.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(point, new GridBagConstraints(3, 0, 1, 1, 200.0, 100.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        calcRoot.add(summary, new GridBagConstraints(4, 0, 3, 1, 2000.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(calcXRay, new GridBagConstraints(1, 2, 2, 1, 20.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(calcBloodBasic, new GridBagConstraints(1, 3, 2, 1, 20.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodChemistry, new GridBagConstraints(1, 4, 2, 1,
                20.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcNyoTest, new GridBagConstraints(1, 5, 2, 1, 20.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotal, new GridBagConstraints(0, 6, 3, 1, 30.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalCostMessage, new GridBagConstraints(4, 6, 1,
                1, 100.0, 100.0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(spaceType, new GridBagConstraints(2, 1, 1, 1, 10.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(calcXRayPoint, new GridBagConstraints(3, 2, 1, 1, 200.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodBasicPoint, new GridBagConstraints(3, 3, 1, 1,
                200.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodChemistryPoint, new GridBagConstraints(3, 4, 1,
                1, 200.0, 100.0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalPoint, new GridBagConstraints(3, 6, 1, 1,
                200.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(firstTestSummary, new GridBagConstraints(4, 1, 3, 1,
                2000.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalCost, new GridBagConstraints(5, 6, 1, 1,
                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(firstTest, new GridBagConstraints(0, 1, 2, 1, 20.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(firstTestPoint, new GridBagConstraints(3, 1, 1, 1, 200.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 3));
        calcRoot.add(calcNyoTestPoint, new GridBagConstraints(3, 5, 1, 1,
                200.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalCostUnit, new GridBagConstraints(6, 6, 1, 1,
                10.0, 100.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcXRaySummary, new GridBagConstraints(4, 2, 3, 1, 2000.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodBasicSummary, new GridBagConstraints(4, 3, 3, 1,
                2000.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodChemistrySummary, new GridBagConstraints(4, 4, 3,
                1, 2000.0, 100.0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcNyoTestSummary, new GridBagConstraints(4, 5, 3, 1,
                2000.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckMessages, new GridBagConstraints(0, 2, 1, 4, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        // 2009/01/13 [Tozo Tanaka] Replace - end
        calcCheckMessages.add(calcCheckMessage1, BorderLayout.NORTH);
        calcCheckMessages.add(calcCheckMessage2, BorderLayout.SOUTH);
        root.add(hiddenParameters, null);
        toGroup.add(toIkenshoPanel, VRLayout.FLOW_RETURN);
        toGroup.add(toConsultationPanel, VRLayout.FLOW_RETURN);
        taxGroup.add(taxs, null);
        costGroup.add(costs, null);
        taxPanel.add(costGroup, VRLayout.CLIENT);
        taxPanel.add(taxGroup, VRLayout.CLIENT);
        bodyGroup.add(points, VRLayout.NORTH);
        bodyGroup.add(calcRoot, VRLayout.NORTH);
        buttons.add(reset, VRLayout.WEST);
        buttons.add(cancel, VRLayout.EAST);
        buttons.add(ok, VRLayout.EAST);

        root.add(toGroup, VRLayout.NORTH);
        root.add(taxPanel, VRLayout.NORTH);
        root.add(bodyGroup, VRLayout.CLIENT);
        root.add(buttons, VRLayout.SOUTH);
        this.getContentPane().add(root, BorderLayout.CENTER);

        toIkenshoPanel.add(toIkenshoName, VRLayout.FLOW);
        toIkenshoPanel.add(toIkenshoNoHead, VRLayout.FLOW);
        toIkenshoPanel.add(toIkenshoNo, VRLayout.FLOW);
        toIkenshoPanel.add(toIkenshoNoFoot, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationHead, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationName, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationNoHead, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationNo, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationNoFoot, VRLayout.FLOW);
        hiddenParameters.add(outputPattern, null);

    }

    protected class IkenshoTreeFollowChecker {

        /**
         * �R���X�g���N�^�ł��B
         * 
         * @param parent �e�Ƃ��ă`�F�b�N��ǐ�����R���|�[�l���g
         * @param children �q�Ƃ��ă`�F�b�N��ǐ�����R���|�[�l���g�Q
         */
        public IkenshoTreeFollowChecker(JCheckBox parent, JCheckBox[] children) {
            IkenshoTreeFollowChildrenListener childrenListener = new IkenshoTreeFollowChildrenListener(
                    parent, children);
            IkenshoTreeFollowPatientListener parentListener = new IkenshoTreeFollowPatientListener(
                    parent, children);

            parent.addItemListener(parentListener);
            int end = children.length;
            for (int i = 0; i < end; i++) {
                children[i].addItemListener(childrenListener);
            }

        }
        
        //2009/01/13 [Tozo Tanaka] Add - begin
        public IkenshoTreeFollowChecker(JCheckBox parent, JCheckBox[] children, JCheckBox[] parentCheckedFollowChildren) {
            IkenshoTreeFollowChildrenListener childrenListener = new IkenshoTreeFollowChildrenListener(
                    parent, children);
            IkenshoTreeFollowPatientListener parentListener = new IkenshoTreeFollowPatientListener(
                    parent, children, parentCheckedFollowChildren);

            parent.addItemListener(parentListener);
            int end = children.length;
            for (int i = 0; i < end; i++) {
                children[i].addItemListener(childrenListener);
            }

        }
        //2009/01/13 [Tozo Tanaka] Add - end
        
        protected class IkenshoTreeFollowChildrenListener implements
                ItemListener {
            private JCheckBox parent;
            private JCheckBox[] children;

            public IkenshoTreeFollowChildrenListener(JCheckBox parent,
                    JCheckBox[] children) {
                this.parent = parent;
                this.children = children;
            }

            public void itemStateChanged(ItemEvent e) {
                boolean select;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    select = true;
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    select = false;
                } else {
                    return;
                }
                if (parent != null) {
                    if (!parent.isFocusOwner()) {
                        if (parent.isSelected() != select) {
                            // �e��ǐ�
                            if (!select) {
                                // �������邩�ǂ����͎q��S����
                                int selectCount = 0;
                                int end = children.length;
                                for (int i = 0; i < end; i++) {
                                    if (children[i].isSelected()) {
                                        selectCount++;
                                    }
                                }
                                if (selectCount > 0) {
                                    // ���̎q���I������Ă���̂Őe�͒ǐ������Ȃ��B
                                    return;
                                }
                            }
                            parent.setSelected(select);
                        }
                    }
                }
            }
        }

        //2009/01/13 [Tozo Tanaka] Replace - begin
//        protected class IkenshoTreeFollowPatientListener implements
//                ItemListener {
//            private JCheckBox parent;
//            private JCheckBox[] children;
//
//            public IkenshoTreeFollowPatientListener(JCheckBox parent,
//                    JCheckBox[] children) {
//                this.parent = parent;
//                this.children = children;
//            }
//
//            public void itemStateChanged(ItemEvent e) {
//                boolean select;
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    select = true;
//                    if (!parent.isFocusOwner()) {
//                        return;
//                    }
//                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
//                    select = false;
//                } else {
//                    return;
//                }
//                if (children != null) {
//                    // �q��ǐ�
//                    int end = children.length;
//                    for (int i = 0; i < end; i++) {
//                        if (children[i].isSelected() != select) {
//                            children[i].setSelected(select);
//                        }
//                    }
//                }
//            }
//        }
        protected class IkenshoTreeFollowPatientListener implements
                ItemListener {
            private JCheckBox parent;
            private JCheckBox[] children;
            private JCheckBox[] parentCheckedFollowChildren;

            public IkenshoTreeFollowPatientListener(JCheckBox parent,
                    JCheckBox[] children) {
                this.parent = parent;
                this.children = children;
            }

            public IkenshoTreeFollowPatientListener(JCheckBox parent,
                    JCheckBox[] children, JCheckBox[] parentCheckedFollowChildren) {
                this.parent = parent;
                this.children = children;
                this.parentCheckedFollowChildren = parentCheckedFollowChildren;
            }

            public void itemStateChanged(ItemEvent e) {
                boolean select;
                JCheckBox[] targetChildren = null;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    select = true;
                    if (!parent.isFocusOwner()) {
                        return;
                    }
                    if(parentCheckedFollowChildren!=null){
                        targetChildren = parentCheckedFollowChildren;
                    }else{
                        targetChildren = children;
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    select = false;
                    targetChildren = children;
                } else {
                    return;
                }
                if (targetChildren != null) {
                    // �q��ǐ�
                    int end = targetChildren.length;
                    for (int i = 0; i < end; i++) {
                        if (targetChildren[i].isSelected() != select) {
                            targetChildren[i].setSelected(select);
                        }
                    }
                }
            }
        }
        //2009/01/13 [Tozo Tanaka] Replace - end

    }

    protected class IkenshoFollowCalcItemListener implements ItemListener {
        protected JTextField point;
        protected VRMap source;
        protected String key;
        protected JCheckBox[] checks;
        protected JTextField[] points;
        protected String[] summarys;
        protected JTextField totalPoint;
        protected JTextField totalSummary;
        protected IkenshoConsultationInfo adaptee;

        /**
         * �R���X�g���N�^�ł��B
         * 
         * @param point �`�F�b�N���ڂ����ڊ֌W����_����
         * @param source �_���}�b�v
         * @param key �Ή�����_���L�[
         * @param checks �t�����Ċ֘A����`�F�b�N�Q
         * @param points �t�����Ċ֘A����_�����Q
         * @param summarys �t�����ďo�͂���E�v�Q
         * @param totalPoint ���v�_����
         * @param totalSummary ���v�̓E�v��
         * @param adaptee �A�_�v�^
         */
        public IkenshoFollowCalcItemListener(JTextField point, VRMap source,
                String key, JCheckBox[] checks, JTextField[] points,
                String[] summarys, JTextField totalPoint,
                JTextField totalSummary, IkenshoConsultationInfo adaptee) {
            this.point = point;
            this.source = source;
            this.key = key;
            this.points = points;
            this.checks = checks;
            this.summarys = summarys;
            this.totalPoint = totalPoint;
            this.totalSummary = totalSummary;
            this.adaptee = adaptee;
        }

        public void follow(boolean select) {
            try {
                if (select) {
                    Object val = VRBindPathParser.get(key, source);
                    if (val == null) {
                        point.setText("");
                    } else {
                        point.setText(IkenshoConstants.FORMAT_DOUBLE1
                                .format(new Double(String.valueOf(val))));
                    }
                } else {
                    point.setText("");
                }

                ArrayList array = new ArrayList();
                double total = 0;
                int end = checks.length;
                for (int i = 0; i < end; i++) {
                    if (checks[i].isSelected()) {
                        array.add(summarys[i]);
                        // 2006/02/11[Tozo Tanaka] : replace begin
                        // TODO canEdit?
                        // total += Double.parseDouble(points[i].getText());
                        String val = points[i].getText();
                        if (!"".equals(val)) {
                            total += Double.parseDouble(val);
                        }
                        // 2006/02/11[Tozo Tanaka] : replace end
                    }
                }

                end = array.size();
                if (end == 0) {
                    totalPoint.setText("");
                    totalSummary.setText("");
                } else {
                    totalPoint.setText(IkenshoConstants.FORMAT_DOUBLE1
                            .format(new Double(total)));
                    StringBuffer sb = new StringBuffer(String.valueOf(array
                            .get(0)));
                    for (int i = 1; i < end; i++) {
                        String val = String.valueOf(array.get(i));
                        if (!"".equals(val)) {
                            sb.append("�A");
                            sb.append(val);
                        }
                    }
                    totalSummary.setText(sb.toString());
                    totalSummary.setSelectionStart(0);
                    totalSummary.setSelectionEnd(0);
                }
                adaptee.calcCost();
            } catch (ParseException ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }
        }

        public void itemStateChanged(ItemEvent e) {
            follow(e.getStateChange() == ItemEvent.SELECTED);
        }
    }

}
