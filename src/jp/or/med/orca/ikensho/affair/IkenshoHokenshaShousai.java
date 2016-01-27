/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;
import java.text.Format;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACOneDecimalDoubleFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoInsurerBridgeFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoInsurerRelation;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

public class IkenshoHokenshaShousai extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel tabPnl = new VRPanel();
    private JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP,
            JTabbedPane.SCROLL_TAB_LAYOUT);

    private VRPanel tab1 = new VRPanel();
    private VRPanel tab1North = new VRPanel();
    private ACLabelContainer insurerNmContainer = new ACLabelContainer();
    private ACTextField insurerNmField = new ACTextField();
    private ACLabelContainer insurerNoContainer = new ACLabelContainer();
    private ACTextField insurerNoField = new ACTextField();

    private ACGroupBox seikyuPatternGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup seikyuPattern = new ACClearableRadioButtonGroup();

    private ACGroupBox issGrp = new ACGroupBox();
    private ACLabelContainer issInsurerNmContainer = new ACLabelContainer();
    private ACTextField issInsurerNm = new ACTextField();
    private ACLabelContainer issInsurerNoContainer = new ACLabelContainer();
    private ACTextField issInsurerNo = new ACTextField();
    private ACButton issInsurerSame = new ACButton();
    private VRPanel seikyusakiPanel = new VRPanel();
    private ACGroupBox soukatuhyouGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup soukatuhyou = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox soukatuhyouPrint = new ACIntegerCheckBox();
    private ACGroupBox meisaiKindGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup meisaiKind = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox meisaiKindPrint = new ACIntegerCheckBox();

    private ACGroupBox sksGrp = new ACGroupBox();
    private ACLabelContainer sksInsurerNmContainer = new ACLabelContainer();
    private ACTextField sksInsurerNm = new ACTextField();
    private ACLabelContainer sksInsurerNoContainer = new ACLabelContainer();
    private ACTextField sksInsurerNo = new ACTextField();
    private ACButton sksInsurerSame = new ACButton();
    private VRPanel seikyusakiPanel2 = new VRPanel();
    private ACGroupBox soukatuhyouGrp2 = new ACGroupBox();
    private ACClearableRadioButtonGroup soukatuhyou2 = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox soukatuhyouPrint2 = new ACIntegerCheckBox();
    private ACGroupBox meisaiKindGrp2 = new ACGroupBox();
    private ACClearableRadioButtonGroup meisaiKind2 = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox meisaiKindPrint2 = new ACIntegerCheckBox();

    private VRPanel tab2 = new VRPanel();

    private VRPanel seikyushoCsvPnl = new VRPanel();
    private ACGroupBox seikyushoPrintGrp = new ACGroupBox();
    private ACIntegerCheckBox seikyushoPrint = new ACIntegerCheckBox();
    private ACGroupBox csvGrp = new ACGroupBox();
    private ACIntegerCheckBox csv = new ACIntegerCheckBox();

    private ACGroupBox printOptionGrp = new ACGroupBox();
    private ACGroupBox drNameGrp = new ACGroupBox();
    private ACIntegerCheckBox drName = new ACIntegerCheckBox();
    private ACGroupBox pageHeaderPrintGrp = new ACGroupBox();
    private ACIntegerCheckBox pageHeaderPrint = new ACIntegerCheckBox();
    private ACGroupBox pageHeaderPrintGrp2 = new ACGroupBox();
    private ACIntegerCheckBox pageHeaderPrint2 = new ACIntegerCheckBox();

    private ACGroupBox pointsGrp = new ACGroupBox();
    private VRPanel sakuseiryoShosinryoPnl = new VRPanel();

    private ACGroupBox ikenshoSakuseiryouGrp = new ACGroupBox();
    private JPanel zaitakuPnl = new JPanel();
    private JLabel zaitaku = new JLabel();
    private VRPanel zaitakuTextPnl = new VRPanel();
    private ACLabelContainer zaitakuSinkiChargeContainer = new ACLabelContainer();
    private ACTextField zaitakuSinkiCharge = new ACTextField();
    private JLabel zaitakuSinkiChargeUnit = new JLabel();
    private ACLabelContainer zaitakuKeizokuChargeContainer = new ACLabelContainer();
    private ACTextField zaitakuKeizokuCharge = new ACTextField();
    private JLabel zaitakuKeizokuChargeUnit = new JLabel();
    private JPanel sisetuPnl = new JPanel();
    private JLabel sisetu = new JLabel();
    private VRPanel sisetuTextPnl = new VRPanel();
    private ACLabelContainer sisetuSinkiChargeContainer = new ACLabelContainer();
    private ACTextField sisetuSinkiCharge = new ACTextField();
    private JLabel sisetuSinkiChargeUnit = new JLabel();
    private ACLabelContainer sisetuKeizokuChargeContainer = new ACLabelContainer();
    private ACTextField sisetuKeizokuCharge = new ACTextField();
    private JLabel sisetuKeizokuChargeUnit = new JLabel();

    private ACGroupBox shosinGrp = new ACGroupBox();
    private JPanel shosinPnl = new JPanel();
    private JLabel shosin = new JLabel();
    private VRPanel shosinTextPnl = new VRPanel();
    private ACLabelContainer shosinSinryoujoContainer = new ACLabelContainer();
    private ACTextField shosinSinryoujo = new ACTextField();
    private JLabel shosinSinryoujoUnit = new JLabel();
    private ACLabelContainer shosinHospitalContainer = new ACLabelContainer();
    private ACTextField shosinHospital = new ACTextField();
    private JLabel shosinHospitalUnit = new JLabel();

    private ACLabelContainer shosinAddItContainer = new ACLabelContainer();
    private ACTextField shosinAddIt = new ACTextField();
    private JLabel shosinAddItUnit = new JLabel();
    private ACIntegerCheckBox addITType = new ACIntegerCheckBox();


    private ACGroupBox sinsatuPointsGrp = new ACGroupBox();
    private VRPanel sinsatuPointsLeftPnl = new VRPanel();
    private VRPanel sinsatuPointsRightPnl = new VRPanel();
    private ACLabelContainer expKsContainer = new ACLabelContainer();
    private ACTextField expKs = new ACTextField();
    private JLabel expKsUnit = new JLabel();
    private ACLabelContainer expKikMkiContainer = new ACLabelContainer();
    private ACTextField expKikMki = new ACTextField();
    private JLabel expKikMkiUnit = new JLabel();
    private ACLabelContainer expKikKekkContainer = new ACLabelContainer();
    private ACTextField expKikKekk = new ACTextField();
    private JLabel expKikKekkUnit = new JLabel();
    private ACLabelContainer expKkkKkkContainer = new ACLabelContainer();
    private ACTextField expKkkKkk = new ACTextField();
    private JLabel expKkkKkkUnit = new JLabel();
    private ACLabelContainer expKkkSkkContainer = new ACLabelContainer();
    private ACTextField expKkkSkk = new ACTextField();
    private JLabel expKkkSkkUnit = new JLabel();
    private ACLabelContainer expNitkContainer = new ACLabelContainer();
    private ACTextField expNitk = new ACTextField();
    private JLabel expNitkUnit = new JLabel();
    private ACLabelContainer expXrayTsContainer = new ACLabelContainer();
    private ACTextField expXrayTs = new ACTextField();
    private JLabel expXrayTsUnit = new JLabel();
    private ACLabelContainer expXraySsContainer = new ACLabelContainer();
    private ACTextField expXraySs = new ACTextField();
    private JLabel expXraySsUnit = new JLabel();
    private ACLabelContainer expXrayFilmContainer = new ACLabelContainer();
    private ACTextField expXrayFilm = new ACTextField();
    private JLabel expXrayFilmUnit = new JLabel();

    //2009/01/06 [Tozo Tanaka] Add - begin
    private jp.nichicom.ac.container.ACPanel sinsatuPointsRight2Pnl = new jp.nichicom.ac.container.ACPanel();
    //�f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�摜�L�^�p�t�B����(��p)
    private ACLabelContainer expXrayDigitalFilmContainer = new ACLabelContainer();
    private ACTextField expXrayDigitalFilm = new ACTextField();
    private JLabel expXrayDigitalFilmUnit = new JLabel();
    //�f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�f�W�^���f�����������Z
    private ACLabelContainer expXrayDigitalImagingContainer = new ACLabelContainer();
    private ACTextField expXrayDigitalImaging = new ACTextField();
    private JLabel expXrayDigitalImagingUnit = new JLabel();
    //�f�W�^���B�e�̏ꍇ�F�t�B�������X�̏ꍇ�F�d�q�摜�Ǘ����Z
    private ACLabelContainer expXrayDigitalImageManagementContainer = new ACLabelContainer();
    private ACTextField expXrayDigitalImageManagement = new ACTextField();
    private JLabel expXrayDigitalImageManagementUnit = new JLabel();
    //2009/01/06 [Tozo Tanaka] Add - end
    // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
    // �P���B�e�i�f�W�^���j
    protected ACLabelContainer expXrayTsDigitalContainer = new ACLabelContainer();
    protected ACTextField expXrayTsDigital = new ACTextField();
    protected JLabel expXrayTsUnitDigital = new JLabel();
    // [ID:0000600][Masahiko Higuchi] 2010/02 add end
    
    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
    private ACGroupBox kindPrintGrp = new ACGroupBox();
    private ACIntegerCheckBox kindPrint = new ACIntegerCheckBox();
    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
    
    private ACLabelContainer insurertypeContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup insurerTypes = new ACClearableRadioButtonGroup();
    private ACLabel insurerInfo = new ACLabel();

    
    private VRPanel rollBackPnl = new VRPanel();
    private ACButton rollBack = new ACButton();

    private VRMap insurerData; // �ی��҃f�[�^
    private String insurerNo; // �ی��Ҕԍ�
    private int insurerType; //�ی��ҋ敪
    private String insurerNm; // �ی��Җ�
    private boolean isUpdate; // true : �X�V, false : �ǉ�
    private boolean hasData; // true : �L, false : ��
    protected VRMap prevData; // �O��ʃL���b�V��

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "INSURER", new String[] { "INSURER_NO","INSURER_TYPE" },
            new Format[] { IkenshoConstants.FORMAT_PASSIVE_STRING, IkenshoConstants.FORMAT_PASSIVE_STRING },
            "LAST_TIME", "LAST_TIME");
    
    // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
    // �ی��҃}�X�^DB
    private IkenshoInsurerBridgeFirebirdDBManager masterInsurerDbm;
    // �ڑ��̉�
    private boolean insurerDbmConnect = false;
    // �ی��ґI���{�^��
    private ACButton insurerSelectButton = new ACButton();
    private ACButton insurerSelectButtonIkensyo = new ACButton();
    private ACButton insurerSelectButtonKensa = new ACButton();
    // �ϊ��N���X
    private IkenshoInsurerRelation insurerRelation;
    private IkenshoInsurerRelation insurerRelationIkensyo;
    private IkenshoInsurerRelation insurerRelationKensa;
    // [ID:0000513][Masahiko Higuchi] 2009/09 add end

    private static final String DOUBLE_INPUT_PERSER = "(\\d+)|((\\d+)(\\.\\d{0,1}))";

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoHokenshaShousai() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(tabPnl, VRLayout.CLIENT);

        buttons.setTitle("�ی��ҏڍ�");
        buttons.add(update, VRLayout.EAST);

//        update.setText("�o�^(S) ");
        update.setText("�@�o�^(S)�@");
        update.setMnemonic('S');
        update.setActionCommand("�o�^(S)");
        update.setToolTipText("���݂̓��e��o�^���܂��B");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        tabPnl.setLayout(new BorderLayout());
        tabPnl.add(tab, BorderLayout.CENTER);
        tab.addTab("�ی��ҏ��1", tab1);
        tab.addTab("�ی��ҏ��2", tab2);

        // tab1
        tab1.setLayout(new VRLayout());
        tab1.add(tab1North, VRLayout.NORTH);
        tab1.add(seikyuPatternGrp, VRLayout.CLIENT);

        tab1North.setLayout(new VRLayout());
        tab1North.add(insurerNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
        tab1North.add(insurerNoContainer, VRLayout.FLOW_INSETLINE);
        tab1North.add(insurerSelectButton, VRLayout.FLOW_INSETLINE_RETURN);
        // [ID:0000513][Masahiko Higuchi] 2009/09 add end

        tab1North.add(insurertypeContainer, VRLayout.FLOW_INSETLINE_RETURN);
        tab1North.add(insurerInfo, VRLayout.FLOW_INSETLINE_RETURN);

        // �ی��Җ���
        insurerNmContainer.setText("�ی��Җ���(�˗�����������)");
        insurerNmContainer.setLayout(new BorderLayout());
        insurerNmContainer.add(insurerNmField);
        insurerNmField.setColumns(40);
        insurerNmField.setMaxLength(40);
        insurerNmField.setIMEMode(InputSubset.KANJI);
        insurerNmField.setBindPath("INSURER_NM");

        // �ی��Ҕԍ�
        insurerNoContainer.setText("�R�[�h(�ی��Ҕԍ�)(����6��)");
        insurerNoContainer.setLayout(new VRLayout());
        insurerNoContainer.add(insurerNoField, VRLayout.CLIENT);
        insurerNoField.setColumns(6);
        insurerNoField.setMaxLength(6);
        insurerNoField.setIMEMode(InputSubset.LATIN_DIGITS);
        insurerNoField.setCharType(VRCharType.ONLY_DIGIT);
        insurerNoField.setBindPath("INSURER_NO");
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
        // �ی��ґI��
        insurerSelectButton.setText("�ی��ґI��(I)");
        insurerSelectButton.setMnemonic('I');
        // [ID:0000513][Masahiko Higuchi] 2009/09 add end

        //�ی��ҋ敪
        insurertypeContainer.setText("�ی��ҋ敪");
        insurerTypes.setBindPath("INSURER_TYPE");
        insurerTypes.setModel(new ACListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "�厡��ӌ����̂�", "��t�㌩���̂�" }))));
        insurerInfo.setIconPath(ACConstants.ICON_PATH_INFORMATION_16);
        insurerInfo.setText("�g�厡��h�g��t�h�̒�o���@���قȂ�ꍇ�́A�ی��ҋ敪�Ƀ`�F�b�N�����A��o����o�^���Ă��������B");
//        insurerInfo.setText("�u�厡��ӌ����v�u��t�ӌ����v�������o�\�ȕی��҂́u�N���A�v�Ŗ��I���ɂ��܂��B");
        insurertypeContainer.add(insurerTypes, VRLayout.FLOW);
        
        // �����p�^�[��
        seikyuPatternGrp.setLayout(new VRLayout());
        seikyuPatternGrp.setText("�����p�^�[��");
        seikyuPatternGrp.setForeground(Color.BLUE);
        seikyuPatternGrp.add(seikyuPattern, VRLayout.NORTH);
        seikyuPatternGrp.add(issGrp, VRLayout.NORTH);
        seikyuPatternGrp.add(sksGrp, VRLayout.NORTH);

        seikyuPattern.setLayout(new VRLayout());
        seikyuPattern.setUseClearButton(false);
        seikyuPattern.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "�ӌ����쐬���E������(1��)", "�ӌ����쐬��(1��)�E������(1��)",
                        "�ӌ����쐬���̂�", "�������̂�" }))));
        seikyuPattern.setBindPath("SEIKYUSHO_OUTPUT_PATTERN");

        // �ӌ���������
        issGrp.setLayout(new VRLayout());
        issGrp.setForeground(Color.BLUE);
        issGrp.setText("�ӌ����쐬��������");
        issGrp.add(seikyusakiPanel, BorderLayout.NORTH);
        issGrp.add(soukatuhyouGrp, BorderLayout.NORTH);
        issGrp.add(meisaiKindGrp, BorderLayout.NORTH);
        VRLayout seikyusakiLayout = new VRLayout();
        seikyusakiLayout.setHgap(0);
        seikyusakiLayout.setAutoWrap(false);
        seikyusakiPanel.setLayout(seikyusakiLayout);
        seikyusakiPanel.add(issInsurerNmContainer, VRLayout.FLOW);
        seikyusakiPanel.add(issInsurerNoContainer, VRLayout.FLOW);
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit begin 2009�N�x�Ή�
        //seikyusakiPanel.add(issInsurerSame, VRLayout.FLOW_RETURN);
        seikyusakiPanel.add(issInsurerSame, VRLayout.FLOW);
        // �{�^����ǉ�
        seikyusakiPanel.add(insurerSelectButtonIkensyo, VRLayout.FLOW_RETURN);
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit end
        
        issInsurerNmContainer.setText("����");
        issInsurerNmContainer.setLayout(new BorderLayout());
        issInsurerNmContainer.add(issInsurerNm);
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit begin 2009�N�x�Ή�
        //issInsurerNm.setColumns(40);
        issInsurerNm.setColumns(23);
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit end
        
        issInsurerNm.setMaxLength(40);
        issInsurerNm.setIMEMode(InputSubset.KANJI);
        issInsurerNm.setBindPath("ISS_INSURER_NM");
        
        issInsurerNoContainer.setText("�ی��Ҕԍ�");
        issInsurerNoContainer.setLayout(new BorderLayout());
        issInsurerNoContainer.add(issInsurerNo);
        issInsurerNo.setColumns(6);
        issInsurerNo.setMaxLength(6);
        issInsurerNo.setIMEMode(InputSubset.LATIN_DIGITS);
        issInsurerNo.setCharType(VRCharType.ONLY_DIGIT);
        issInsurerNo.setBindPath("ISS_INSURER_NO");

        issInsurerSame.setText("�˗����Ɠ���(C)");
        issInsurerSame.setMnemonic('C');

        // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
        insurerSelectButtonIkensyo.setText("�ی��ґI��(J)");
        insurerSelectButtonIkensyo.setMnemonic('J');
        // [ID:0000513][Masahiko Higuchi] 2009/09 add end
        
        // �ӌ���������E������
        soukatuhyouGrp.setLayout(new VRLayout());
        soukatuhyouGrp.setText("������(�������ҕ��̐������𓯎��쐬����ꍇ�̕\��)");
        soukatuhyouGrp.add(soukatuhyou, VRLayout.CLIENT);
        soukatuhyouGrp.add(soukatuhyouPrint, VRLayout.CLIENT);
        VRLayout soukatuhyouLayout = new VRLayout();
        soukatuhyouLayout.setAutoWrap(false);
        soukatuhyou.setLayout(soukatuhyouLayout);
        soukatuhyou.setUseClearButton(false);
        soukatuhyou.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "����������", "�������Ȃ�" }))));
        soukatuhyou.setBindPath("SOUKATUHYOU_PRT");
        soukatuhyouPrint.setText("�U����������");
        soukatuhyouPrint.setBindPath("SOUKATU_FURIKOMI_PRT");

        // �ӌ���������E���׏�
        meisaiKindGrp.setLayout(new VRLayout());
        meisaiKindGrp.setText("���׏����");
        meisaiKindGrp.add(meisaiKind, VRLayout.CLIENT);
        meisaiKindGrp.add(meisaiKindPrint, VRLayout.CLIENT);
        VRLayout meisaiKindLayout = new VRLayout();
        meisaiKindLayout.setAutoWrap(false);
        meisaiKind.setLayout(meisaiKindLayout);
        meisaiKind.setUseClearButton(false);
        meisaiKind.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "���׏��̂�", "�ꗗ�\�̂�", "���׏��ƈꗗ�\" }))));
        meisaiKind.setBindPath("MEISAI_KIND");
        meisaiKindPrint.setText("�U����������");
        meisaiKindPrint.setBindPath("FURIKOMISAKI_PRT");

        // �f�@�E������p������
        sksGrp.setText("�f�@�E������p������");
        sksGrp.setForeground(Color.BLUE);
        sksGrp.setLayout(new VRLayout());
        sksGrp.add(seikyusakiPanel2, BorderLayout.NORTH);
        sksGrp.add(soukatuhyouGrp2, BorderLayout.NORTH);
        sksGrp.add(meisaiKindGrp2, BorderLayout.NORTH);

        // �u�ӌ���������v�u�f�@�E������p�v
        VRLayout seikyusakiLayout2 = new VRLayout();
        seikyusakiLayout2.setHgap(0);
        seikyusakiLayout2.setAutoWrap(false);
        seikyusakiPanel2.setLayout(seikyusakiLayout2);
        seikyusakiPanel2.add(sksInsurerNmContainer, VRLayout.FLOW);
        seikyusakiPanel2.add(sksInsurerNoContainer, VRLayout.FLOW);
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit begin 2009�N�x�Ή�
        //seikyusakiPanel2.add(sksInsurerSame, VRLayout.FLOW_RETURN);
        seikyusakiPanel2.add(sksInsurerSame, VRLayout.FLOW);
        seikyusakiPanel2.add(insurerSelectButtonKensa, VRLayout.FLOW_RETURN);
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit end

        sksInsurerNmContainer.setText("����");
        sksInsurerNmContainer.setLayout(new BorderLayout());
        sksInsurerNmContainer.add(sksInsurerNm);
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit begin 2009�N�x�Ή�
        //sksInsurerNm.setColumns(40);
        sksInsurerNm.setColumns(23);
        // [ID:0000513][Masahiko Higuchi] 2009/09 edit end
        
        sksInsurerNm.setMaxLength(40);
        sksInsurerNm.setIMEMode(InputSubset.KANJI);
        sksInsurerNm.setBindPath("SKS_INSURER_NM");

        sksInsurerNoContainer.setText("�ی��Ҕԍ�");
        sksInsurerNoContainer.setLayout(new BorderLayout());
        sksInsurerNoContainer.add(sksInsurerNo);
        sksInsurerNo.setColumns(6);
        sksInsurerNo.setMaxLength(6);
        sksInsurerNo.setIMEMode(InputSubset.LATIN_DIGITS);
        sksInsurerNo.setCharType(VRCharType.ONLY_DIGIT);
        sksInsurerNo.setBindPath("SKS_INSURER_NO");
        
        sksInsurerSame.setText("�˗����Ɠ���(P)");
        sksInsurerSame.setMnemonic('P');

        // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
        insurerSelectButtonKensa.setText("�ی��ґI��(K)");
        insurerSelectButtonKensa.setMnemonic('K');
        // [ID:0000513][Masahiko Higuchi] 2009/09 add end
        
        
        // �ӌ���������E������
        soukatuhyouGrp2.setText("������(�������ҕ��̐������𓯎��쐬����ꍇ�̕\��)");
        soukatuhyouGrp2.setLayout(new VRLayout());
        soukatuhyouGrp2.add(soukatuhyou2, VRLayout.CLIENT);
        soukatuhyouGrp2.add(soukatuhyouPrint2, VRLayout.CLIENT);
        VRLayout soukatuhyouLayout2 = new VRLayout();
        soukatuhyouLayout2.setAutoWrap(false);
        soukatuhyou2.setLayout(soukatuhyouLayout2);
        soukatuhyou2.setUseClearButton(false);
        soukatuhyou2.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "����������", "�������Ȃ�" }))));
        soukatuhyou2.setBindPath("SOUKATUHYOU_PRT2");
        soukatuhyouPrint2.setText("�U����������");
        soukatuhyouPrint2.setBindPath("SOUKATU_FURIKOMI_PRT2");

        // �ӌ���������E���׏����
        meisaiKindGrp2.setLayout(new VRLayout());
        meisaiKindGrp2.setText("���׏����");
        meisaiKindGrp2.add(meisaiKind2, VRLayout.CLIENT);
        meisaiKindGrp2.add(meisaiKindPrint2, VRLayout.CLIENT);
        VRLayout meisaiKindLayout2 = new VRLayout();
        meisaiKindLayout2.setAutoWrap(false);
        meisaiKind2.setLayout(meisaiKindLayout2);
        meisaiKind2.setUseClearButton(false);
        meisaiKind2.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "���׏��̂�", "�ꗗ�\�̂�", "���׏��ƈꗗ�\" }))));
        meisaiKind2.setBindPath("MEISAI_KIND2");
        meisaiKindPrint2.setText("�U����������");
        meisaiKindPrint2.setBindPath("FURIKOMISAKI_PRT2");

        // tab2
        tab2.setLayout(new VRLayout());
        tab2.add(seikyushoCsvPnl, VRLayout.NORTH);
        tab2.add(printOptionGrp, VRLayout.NORTH);
        tab2.add(pointsGrp, VRLayout.NORTH);

        // ��������� + CSV
        seikyushoCsvPnl.setLayout(new VRLayout());
        seikyushoCsvPnl.add(seikyushoPrintGrp, VRLayout.CLIENT);
        seikyushoCsvPnl.add(csvGrp, VRLayout.CLIENT);
        // ���������
        seikyushoPrintGrp.setText("���������(�u�厡��ӌ����v�u��t�ӌ����v�����)");
        seikyushoPrintGrp.setForeground(Color.BLUE);
        seikyushoPrintGrp.setLayout(new BorderLayout());
        seikyushoPrintGrp.add(seikyushoPrint);
        seikyushoPrint.setText("�������");
        seikyushoPrint.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        // CSV�t�@�C���ł̒�o
        csvGrp.setText("CSV�t�@�C���ł́u�厡��ӌ����v�u��t�ӌ����v�̒�o");
        csvGrp.setForeground(Color.BLUE);
        csvGrp.setLayout(new BorderLayout());
        csvGrp.add(csv);
        csv.setText("��o����");
        csv.setBindPath("FD_OUTPUT_UMU");

        // �厡��ӌ����v����I�v�V����
        printOptionGrp.setText("�u�厡��ӌ����v�u��t�ӌ����v����I�v�V����");
        printOptionGrp.setForeground(Color.BLUE);
        printOptionGrp.setLayout(new VRLayout());
        //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
//        printOptionGrp.add(drNameGrp, VRLayout.CLIENT);
//        printOptionGrp.add(pageHeaderPrintGrp, VRLayout.CLIENT);
//        printOptionGrp.add(pageHeaderPrintGrp2, VRLayout.CLIENT);
        printOptionGrp.add(drNameGrp, VRLayout.WEST);
        printOptionGrp.add(pageHeaderPrintGrp, VRLayout.CLIENT);
        printOptionGrp.add(kindPrintGrp, VRLayout.CLIENT);
        printOptionGrp.add(pageHeaderPrintGrp2, VRLayout.CLIENT);
        // �ݑ�E�{�݋敪
        kindPrintGrp.setText("�Ńw�b�_(�ݑ�E�{�݋敪)");
        kindPrintGrp.setLayout(new BorderLayout());
        kindPrintGrp.add(kindPrint);
        kindPrint.setText("�������");
        kindPrint.setBindPath("KIND_OUTPUT_UMU");
        //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�  
        // ��t����
        drNameGrp.setText("��t����");
        drNameGrp.setLayout(new BorderLayout());
        drNameGrp.add(drName);
        drName.setText("�������");
        drName.setBindPath("DR_NM_OUTPUT_UMU");
        // 1�Ŗڃw�b�_
        pageHeaderPrintGrp.setText("�Ńw�b�_(�ی��ҁE��ی��Ҕԍ�)");
        pageHeaderPrintGrp.setLayout(new BorderLayout());
        pageHeaderPrintGrp.add(pageHeaderPrint);
        pageHeaderPrint.setText("�������");
        pageHeaderPrint.setBindPath("HEADER_OUTPUT_UMU1");
        // 2�Ŗڃw�b�_
        pageHeaderPrintGrp2.setText("2�Ŗڃw�b�_(�����A�N��A�L����)");
        pageHeaderPrintGrp2.setLayout(new BorderLayout());
        pageHeaderPrintGrp2.add(pageHeaderPrint2);
        pageHeaderPrint2.setText("�������");
        pageHeaderPrint2.setBindPath("HEADER_OUTPUT_UMU2");

        // �ӌ����쐬��/�f�@�E������p�_��
        pointsGrp.setText("�ӌ����쐬��/�f�@�E������p�_��");
        pointsGrp.setForeground(Color.BLUE);
        pointsGrp.setLayout(new VRLayout());
        pointsGrp.add(sakuseiryoShosinryoPnl, VRLayout.NORTH);
        pointsGrp.add(sinsatuPointsGrp, VRLayout.NORTH);
        pointsGrp.add(rollBackPnl, VRLayout.NORTH);

        // �쐬�� + ���f��
        sakuseiryoShosinryoPnl.setLayout(new VRLayout());
        sakuseiryoShosinryoPnl.add(ikenshoSakuseiryouGrp, VRLayout.CLIENT);
        sakuseiryoShosinryoPnl.add(shosinGrp, VRLayout.CLIENT);

        // �ӌ����쐬��
        ikenshoSakuseiryouGrp.setText("�ӌ����쐬��");
        ikenshoSakuseiryouGrp.setLayout(new VRLayout());
        ikenshoSakuseiryouGrp.add(zaitakuPnl, VRLayout.CLIENT);
        ikenshoSakuseiryouGrp.add(sisetuPnl, VRLayout.CLIENT);

        zaitakuPnl.setLayout(new BorderLayout());
        zaitakuPnl.add(zaitaku, BorderLayout.WEST);
        zaitakuPnl.add(zaitakuTextPnl, BorderLayout.CENTER);

        zaitaku.setText("�ݑ�");
        zaitaku.setVerticalAlignment(JTextField.NORTH);
        zaitakuTextPnl.setLayout(new VRLayout());
        zaitakuTextPnl.add(zaitakuSinkiChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        zaitakuTextPnl.add(zaitakuKeizokuChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        zaitakuSinkiChargeContainer.setText("�V�K");
        zaitakuSinkiChargeContainer.setLayout(new BorderLayout());
        zaitakuSinkiChargeContainer
                .add(zaitakuSinkiCharge, BorderLayout.CENTER);
        zaitakuSinkiChargeContainer.add(zaitakuSinkiChargeUnit,
                BorderLayout.EAST);
        zaitakuSinkiCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        zaitakuSinkiCharge.setCharType(VRCharType.ONLY_DIGIT);
        zaitakuSinkiCharge.setColumns(5);
        zaitakuSinkiCharge.setMaxLength(5);
        zaitakuSinkiCharge.setHorizontalAlignment(JTextField.RIGHT);
        zaitakuSinkiCharge.setBindPath("ZAITAKU_SINKI_CHARGE");
        zaitakuSinkiChargeUnit.setText("�~");
        zaitakuKeizokuChargeContainer.setText("�p��");
        zaitakuKeizokuChargeContainer.setLayout(new BorderLayout());
        zaitakuKeizokuChargeContainer.add(zaitakuKeizokuCharge,
                BorderLayout.CENTER);
        zaitakuKeizokuChargeContainer.add(zaitakuKeizokuChargeUnit,
                BorderLayout.EAST);
        zaitakuKeizokuCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        zaitakuKeizokuCharge.setCharType(VRCharType.ONLY_DIGIT);
        zaitakuKeizokuCharge.setColumns(5);
        zaitakuKeizokuCharge.setMaxLength(5);
        zaitakuKeizokuCharge.setHorizontalAlignment(JTextField.RIGHT);
        zaitakuKeizokuCharge.setBindPath("ZAITAKU_KEIZOKU_CHARGE");
        zaitakuKeizokuChargeUnit.setText("�~");

        sisetuPnl.setLayout(new BorderLayout());
        sisetuPnl.add(sisetu, BorderLayout.WEST);
        sisetuPnl.add(sisetuTextPnl, BorderLayout.CENTER);
        sisetu.setText("�{��");
        sisetu.setVerticalAlignment(JTextField.NORTH);
        sisetuTextPnl.setLayout(new VRLayout());
        sisetuTextPnl.add(sisetuSinkiChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sisetuTextPnl.add(sisetuKeizokuChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sisetuSinkiChargeContainer.setText("�V�K");
        sisetuSinkiChargeContainer.setLayout(new BorderLayout());
        sisetuSinkiChargeContainer.add(sisetuSinkiCharge, BorderLayout.CENTER);
        sisetuSinkiChargeContainer
                .add(sisetuSinkiChargeUnit, BorderLayout.EAST);
        sisetuSinkiCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        sisetuSinkiCharge.setCharType(VRCharType.ONLY_DIGIT);
        sisetuSinkiCharge.setColumns(5);
        sisetuSinkiCharge.setMaxLength(5);
        sisetuSinkiCharge.setHorizontalAlignment(JTextField.RIGHT);
        sisetuSinkiCharge.setBindPath("SISETU_SINKI_CHARGE");
        sisetuSinkiChargeUnit.setText("�~");
        sisetuKeizokuChargeContainer.setText("�p��");
        sisetuKeizokuChargeContainer.setLayout(new BorderLayout());
        sisetuKeizokuChargeContainer.add(sisetuKeizokuCharge,
                BorderLayout.CENTER);
        sisetuKeizokuChargeContainer.add(sisetuKeizokuChargeUnit,
                BorderLayout.EAST);
        sisetuKeizokuCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        sisetuKeizokuCharge.setCharType(VRCharType.ONLY_DIGIT);
        sisetuKeizokuCharge.setColumns(5);
        sisetuKeizokuCharge.setMaxLength(5);
        sisetuKeizokuCharge.setHorizontalAlignment(JTextField.RIGHT);
        sisetuKeizokuCharge.setBindPath("SISETU_KEIZOKU_CHARGE");
        sisetuKeizokuChargeUnit.setText("�~");

        // �f�@��p�_��
        shosinGrp.setText("�f�@��p�_�� - �����_���ʂ܂�");
        shosinGrp.setLayout(new BorderLayout());
        shosinGrp.add(shosinPnl, BorderLayout.WEST);
        shosinGrp.add(shosinTextPnl, BorderLayout.CENTER);
        shosinPnl.setLayout(new BorderLayout());
        shosinPnl.add(shosin);
        shosin.setText("���f��");
        shosin.setVerticalAlignment(JTextField.NORTH);
        shosinTextPnl.setLayout(new VRLayout(VRLayout.LEFT, 4, 2));
        shosinTextPnl.add(shosinSinryoujoContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        shosinTextPnl.add(shosinHospitalContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        shosinTextPnl.add(shosinAddItContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        shosinAddItContainer.setVisible(false);
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end
        shosinSinryoujoContainer.setText("�f�Ï�");
        shosinSinryoujoContainer.setLayout(new BorderLayout());
        shosinSinryoujoContainer.add(shosinSinryoujo, BorderLayout.CENTER);
        shosinSinryoujoContainer.add(shosinSinryoujoUnit, BorderLayout.EAST);
        shosinSinryoujo.setIMEMode(InputSubset.LATIN_DIGITS);
        shosinSinryoujo.setFormat(new ACOneDecimalDoubleFormat());
        shosinSinryoujo.setCharType(new VRCharType("DOUBLE1",
                DOUBLE_INPUT_PERSER));
        shosinSinryoujo.setColumns(6);
        shosinSinryoujo.setMaxLength(6);
        shosinSinryoujo.setHorizontalAlignment(JTextField.RIGHT);
        shosinSinryoujo.setBindPath("SHOSIN_SINRYOUJO");
        shosinSinryoujoUnit.setText("�_");
        shosinHospitalContainer.setText("�a�@");
        shosinHospitalContainer.setLayout(new BorderLayout());
        shosinHospitalContainer.add(shosinHospital, BorderLayout.CENTER);
        shosinHospitalContainer.add(shosinHospitalUnit, BorderLayout.EAST);
        shosinHospital.setIMEMode(InputSubset.LATIN_DIGITS);
        shosinHospital.setFormat(new ACOneDecimalDoubleFormat());
        shosinHospital.setCharType(new VRCharType("SHOUSUU1",
                DOUBLE_INPUT_PERSER));
        shosinHospital.setColumns(6);
        shosinHospital.setMaxLength(6);
        shosinHospital.setHorizontalAlignment(JTextField.RIGHT);
        shosinHospital.setBindPath("SHOSIN_HOSPITAL");
        shosinHospitalUnit.setText("�_");

        shosinAddItContainer.setText("�d�q�����Z");
        shosinAddItContainer.setLayout(new BorderLayout());
        shosinAddItContainer.add(shosinAddIt, BorderLayout.CENTER);
        shosinAddItContainer.add(shosinAddItUnit, BorderLayout.EAST);
        shosinAddIt.setIMEMode(InputSubset.LATIN_DIGITS);
        shosinAddIt.setFormat(new ACOneDecimalDoubleFormat());
        shosinAddIt.setCharType(new VRCharType("SHOUSUU1",
                DOUBLE_INPUT_PERSER));
        shosinAddIt.setColumns(6);
        shosinAddIt.setMaxLength(6);
        shosinAddIt.setHorizontalAlignment(JTextField.RIGHT);
        shosinAddIt.setBindPath("SHOSIN_ADD_IT");
        shosinAddItUnit.setText("�_");
        

        // 2006/02/07[Tozo Tanaka] : add begin
        addITType.setBindPath("SHOSIN_ADD_IT_TYPE");
        addITType.setText("�d�q�����Z�͈�t�ӌ����ł̂ݎZ�肷��");
        addITType.setSelected(true);
        shosinTextPnl.add(addITType,VRLayout.FLOW_RETURN);
        // [ID:0000600][Masahiko Higuchi] 2010/02 del begin �f�Õ�V�P���̕ύX�Ή�
        addITType.setVisible(false);
        // [ID:0000600][Masahiko Higuchi] 2010/02 del end
        //shosinGrp.add(addITType, BorderLayout.SOUTH);
        // 2006/09/07[Tozo Tanaka] : add end

        
        // �f�@�E������p�_��
        sinsatuPointsGrp.setText("�f�@�E������p�_�� - �����_���ʂ܂�");
        sinsatuPointsGrp.setLayout(new VRLayout());
        sinsatuPointsGrp.add(sinsatuPointsLeftPnl, VRLayout.CLIENT);
        sinsatuPointsGrp.add(sinsatuPointsRightPnl, VRLayout.CLIENT);
        sinsatuPointsGrp.add(sinsatuPointsRight2Pnl, VRLayout.CLIENT);

        VRLayout sinsatuPointsLeftPnlLayout = new VRLayout();
        sinsatuPointsLeftPnlLayout.setHgrid(100);
        sinsatuPointsLeftPnl.setLayout(sinsatuPointsLeftPnlLayout);
        sinsatuPointsLeftPnl
                .add(expKsContainer, VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKikMkiContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKikKekkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKkkKkkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKkkSkkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);

        expKsContainer.setText("���t�̎�(�Ö�)");
        expKsContainer.setLayout(new BorderLayout());
        expKsContainer.add(expKs, BorderLayout.CENTER);
        expKsContainer.add(expKsUnit, BorderLayout.EAST);
        expKs.setIMEMode(InputSubset.LATIN_DIGITS);
        expKs.setFormat(new ACOneDecimalDoubleFormat());
        expKs.setCharType(new VRCharType("SHOUSUU1", DOUBLE_INPUT_PERSER));
        expKs.setColumns(4);
        expKs.setMaxLength(6);
        expKs.setHorizontalAlignment(JTextField.RIGHT);
        expKs.setBindPath("EXP_KS");
        expKsUnit.setText("�_");
        expKikMkiContainer.setText("�������t��ʌ���");
        expKikMkiContainer.setLayout(new BorderLayout());
        expKikMkiContainer.add(expKikMki, BorderLayout.CENTER);
        expKikMkiContainer.add(expKikMkiUnit, BorderLayout.EAST);
        expKikMki.setIMEMode(InputSubset.LATIN_DIGITS);
        expKikMki.setFormat(new ACOneDecimalDoubleFormat());
        expKikMki.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKikMki.setColumns(4);
        expKikMki.setMaxLength(6);
        expKikMki.setHorizontalAlignment(JTextField.RIGHT);
        expKikMki.setBindPath("EXP_KIK_MKI");
        expKikMkiUnit.setText("�_");
        expKikKekkContainer.setText("���t�w�I�������f��");
        expKikKekkContainer.setLayout(new BorderLayout());
        expKikKekkContainer.add(expKikKekk, BorderLayout.CENTER);
        expKikKekkContainer.add(expKikKekkUnit, BorderLayout.EAST);
        expKikKekk.setIMEMode(InputSubset.LATIN_DIGITS);
        expKikKekk.setFormat(new ACOneDecimalDoubleFormat());
        expKikKekk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKikKekk.setColumns(4);
        expKikKekk.setMaxLength(6);
        expKikKekk.setHorizontalAlignment(JTextField.RIGHT);
        expKikKekk.setBindPath("EXP_KIK_KEKK");
        expKikKekkUnit.setText("�_");
        expKkkKkkContainer.setText("���t���w����(10���ڈȏ�)");
        expKkkKkkContainer.setLayout(new BorderLayout());
        expKkkKkkContainer.add(expKkkKkk, BorderLayout.CENTER);
        expKkkKkkContainer.add(expKkkKkkUnit, BorderLayout.EAST);
        expKkkKkk.setIMEMode(InputSubset.LATIN_DIGITS);
        expKkkKkk.setFormat(new ACOneDecimalDoubleFormat());
        expKkkKkk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKkkKkk.setColumns(4);
        expKkkKkk.setMaxLength(6);
        expKkkKkk.setHorizontalAlignment(JTextField.RIGHT);
        expKkkKkk.setBindPath("EXP_KKK_KKK");
        expKkkKkkUnit.setText("�_");
        expKkkSkkContainer.setText("�����w�I����(I)���f��");
        expKkkSkkContainer.setLayout(new BorderLayout());
        expKkkSkkContainer.add(expKkkSkk, BorderLayout.CENTER);
        expKkkSkkContainer.add(expKkkSkkUnit, BorderLayout.EAST);
        expKkkSkk.setIMEMode(InputSubset.LATIN_DIGITS);
        expKkkSkk.setFormat(new ACOneDecimalDoubleFormat());
        expKkkSkk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKkkSkk.setColumns(4);
        expKkkSkk.setMaxLength(6);
        expKkkSkk.setHorizontalAlignment(JTextField.RIGHT);
        expKkkSkk.setBindPath("EXP_KKK_SKK");
        expKkkSkkUnit.setText("�_");

        VRLayout sinsatuPointsRightPnlLayout = new VRLayout();
        sinsatuPointsRightPnlLayout.setHgrid(200);
        sinsatuPointsRightPnl.setLayout(sinsatuPointsRightPnlLayout);
        sinsatuPointsRightPnl.add(expNitkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRightPnl.add(expXrayTsContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή� 
        // �P���B�e�i�f�W�^���j
        sinsatuPointsRightPnl.add(expXrayTsDigitalContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end
        sinsatuPointsRightPnl.add(expXraySsContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRightPnl.add(expXrayFilmContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        
        expNitkContainer.setText("�A����ʕ����萫����ʌ���");
        expNitkContainer.setLayout(new BorderLayout());
        expNitkContainer.add(expNitk, BorderLayout.CENTER);
        expNitkContainer.add(expNitkUnit, BorderLayout.EAST);
        expNitk.setIMEMode(InputSubset.LATIN_DIGITS);
        expNitk.setFormat(new ACOneDecimalDoubleFormat());
        expNitk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expNitk.setColumns(4);
        expNitk.setMaxLength(6);
        expNitk.setHorizontalAlignment(JTextField.RIGHT);
        expNitk.setBindPath("EXP_NITK");
        expNitkUnit.setText("�_");
        // [ID:0000600][Masahiko Higuchi] 2010/02 edit begin �f�Õ�V�P���̕ύX�Ή�
        expXrayTsContainer.setText("�P���B�e(�A�i���O)");
        // [ID:0000600][Masahiko Higuchi] 2010/02 edit end
        expXrayTsContainer.setLayout(new BorderLayout());
        expXrayTsContainer.add(expXrayTs, BorderLayout.CENTER);
        expXrayTsContainer.add(expXrayTsUnit, BorderLayout.EAST);
        expXrayTs.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayTs.setFormat(new ACOneDecimalDoubleFormat());
        expXrayTs.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayTs.setColumns(4);
        expXrayTs.setMaxLength(6);
        expXrayTs.setHorizontalAlignment(JTextField.RIGHT);
        expXrayTs.setBindPath("EXP_XRAY_TS");
        expXrayTsUnit.setText("�_");
        expXraySsContainer.setText("�ʐ^�f�f(����)");
        expXraySsContainer.setLayout(new BorderLayout());
        expXraySsContainer.add(expXraySs, BorderLayout.CENTER);
        expXraySsContainer.add(expXraySsUnit, BorderLayout.EAST);
        expXraySs.setIMEMode(InputSubset.LATIN_DIGITS);
        expXraySs.setFormat(new ACOneDecimalDoubleFormat());
        expXraySs.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXraySs.setColumns(4);
        expXraySs.setMaxLength(6);
        expXraySs.setHorizontalAlignment(JTextField.RIGHT);
        expXraySs.setBindPath("EXP_XRAY_SS");
        expXraySsUnit.setText("�_");
        expXrayFilmContainer.setText("�t�B����(��p)");
        expXrayFilmContainer.setLayout(new BorderLayout());
        expXrayFilmContainer.add(expXrayFilm, BorderLayout.CENTER);
        expXrayFilmContainer.add(expXrayFilmUnit, BorderLayout.EAST);
        expXrayFilm.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayFilm.setFormat(new ACOneDecimalDoubleFormat());
        expXrayFilm.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayFilm.setColumns(4);
        expXrayFilm.setMaxLength(6);
        expXrayFilm.setHorizontalAlignment(JTextField.RIGHT);
        expXrayFilm.setBindPath("EXP_XRAY_FILM");
        expXrayFilmUnit.setText("�_");

        //2009/01/06 [Tozo Tanaka] Add - begin
        //�f�W�^���B�e�̏ꍇ
        sinsatuPointsRight2Pnl.setHgrid(200);
        sinsatuPointsRight2Pnl.add(expXrayDigitalImageManagementContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRight2Pnl.add(expXrayDigitalFilmContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRight2Pnl.add(expXrayDigitalImagingContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        expXrayDigitalImagingContainer.setVisible(false);
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end
        
        //�f�W�^���B�e�̏ꍇ�F�t�B�������X�̏ꍇ�F�d�q�摜�Ǘ����Z
        expXrayDigitalImageManagementContainer.setText("�d�q�摜�Ǘ����Z");
        expXrayDigitalImageManagementContainer.setLayout(new BorderLayout());
        expXrayDigitalImageManagementContainer.add(expXrayDigitalImageManagement, BorderLayout.CENTER);
        expXrayDigitalImageManagementContainer.add(expXrayDigitalImageManagementUnit, BorderLayout.EAST);
        expXrayDigitalImageManagement.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayDigitalImageManagement.setFormat(new ACOneDecimalDoubleFormat());
        expXrayDigitalImageManagement.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayDigitalImageManagement.setColumns(4);
        expXrayDigitalImageManagement.setMaxLength(6);
        expXrayDigitalImageManagement.setHorizontalAlignment(JTextField.RIGHT);
        expXrayDigitalImageManagement.setBindPath("EXP_XRAY_DIGITAL_MANAGEMENT");
        expXrayDigitalImageManagementUnit.setText("�_");        
        
        //�f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�摜�L�^�p�t�B����(��p)
        expXrayDigitalFilmContainer.setText("�摜�L�^�p�t�B����(��p)");
        expXrayDigitalFilmContainer.setLayout(new BorderLayout());
        expXrayDigitalFilmContainer.add(expXrayDigitalFilm, BorderLayout.CENTER);
        expXrayDigitalFilmContainer.add(expXrayDigitalFilmUnit, BorderLayout.EAST);
        expXrayDigitalFilm.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayDigitalFilm.setFormat(new ACOneDecimalDoubleFormat());
        expXrayDigitalFilm.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayDigitalFilm.setColumns(4);
        expXrayDigitalFilm.setMaxLength(6);
        expXrayDigitalFilm.setHorizontalAlignment(JTextField.RIGHT);
        expXrayDigitalFilm.setBindPath("EXP_XRAY_DIGITAL_FILM");
        expXrayDigitalFilmUnit.setText("�_");

        //�f�W�^���B�e�̏ꍇ�F�t�B�������Z�肷��ꍇ�F�f�W�^���f�����������Z
        expXrayDigitalImagingContainer.setText("�f�W�^���f�����������Z");
        expXrayDigitalImagingContainer.setLayout(new BorderLayout());
        expXrayDigitalImagingContainer.add(expXrayDigitalImaging, BorderLayout.CENTER);
        expXrayDigitalImagingContainer.add(expXrayDigitalImagingUnit, BorderLayout.EAST);
        expXrayDigitalImaging.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayDigitalImaging.setFormat(new ACOneDecimalDoubleFormat());
        expXrayDigitalImaging.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayDigitalImaging.setColumns(4);
        expXrayDigitalImaging.setMaxLength(6);
        expXrayDigitalImaging.setHorizontalAlignment(JTextField.RIGHT);
        expXrayDigitalImaging.setBindPath("EXP_XRAY_DIGITAL_IMAGING");
        expXrayDigitalImagingUnit.setText("�_");
        //2009/01/06 [Tozo Tanaka] Add - end
        
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        // �P���B�e�i�f�W�^���j
        expXrayTsDigitalContainer.setText("�P���B�e(�f�W�^��)");
        expXrayTsDigitalContainer.setLayout(new BorderLayout());
        expXrayTsDigitalContainer.add(expXrayTsDigital, BorderLayout.CENTER);
        expXrayTsDigitalContainer.add(expXrayTsUnitDigital, BorderLayout.EAST);
        expXrayTsDigital.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayTsDigital.setFormat(new ACOneDecimalDoubleFormat());
        expXrayTsDigital.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayTsDigital.setColumns(4);
        expXrayTsDigital.setMaxLength(6);
        expXrayTsDigital.setHorizontalAlignment(JTextField.RIGHT);
        expXrayTsDigital.setBindPath("EXP_XRAY_TS_DIGITAL");
        expXrayTsUnitDigital.setText("�_");
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end
        
        // �߂��{�^��
        rollBackPnl.setLayout(new VRLayout());
        rollBackPnl.add(rollBack, VRLayout.EAST);
        insurerNmContainer.add(insurerNmField, java.awt.BorderLayout.CENTER);
        //[ID:0000775][Masahiko Higuchi] 2014/03 edit begin �f�Õ�V�P���̕ύX�Ή�
        rollBack.setText("H26�N�f�Õ�V�P��(D)");
        //[ID:0000775][Masahiko Higuchi] 2014/03 edit end
        rollBack.setMnemonic('D');

    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // �X�i�b�v�V���b�g�Ώېݒ�
        IkenshoSnapshot.getInstance().setRootContainer(tabPnl);

        // ���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        addUpdateTrigger(update);

        // �X�e�[�^�X�o�[
        setStatusText("�ی��ҏڍ�");

        // �O��ʂ�����p���̃f�[�^���擾�E�ݒ�
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            if (VRBindPathParser.has("PREV_DATA", params)) {
                prevData = (VRMap) VRBindPathParser.get("PREV_DATA", params);
            }

            // �O��ʂɂāA�ǂ̃{�^����������Ă̑J�ڂ����擾
            String act = String.valueOf(params.get("ACT"));
            if (act.equals("insert")) {
                isUpdate = false;
                hasData = false;
            } else if (act.equals("copy")) {
                isUpdate = false;
                hasData = true;
            } else if (act.equals("detail")) {
                isUpdate = true;
                hasData = true;
            }

            // �{�^���̃L���v�V����
            if (isUpdate) {
                update.setText("�X�V(S)");
                update.setToolTipText("���݂̓��e���X�V���܂��B");
            } else {
                update.setText("�o�^(S)");
                update.setToolTipText("���݂̓��e��o�^���܂��B");
            }

            // �n��f�[�^�̗L���ŕ���
            if (hasData) {
                // �n�莞�̏��S����INSURER_NO���擾
                insurerNo = String.valueOf(VRBindPathParser.get("INSURER_NO",
                        params));
                insurerType = ACCastUtilities.toInt(VRBindPathParser.get("INSURER_TYPE",
                        params), 0); 

                // ���͗��Ƀf�[�^��ݒ�
                doSelect();

                // �ӌ����쐬���E������(1��)�̏ꍇ�A���i�͏���
                if (seikyuPattern.getSelectedIndex() == 1) {
                    sksInsurerNm.setText("");
                    sksInsurerNo.setText("");
                    soukatuhyou2.setSelectedIndex(0);
                    soukatuhyouPrint2.setSelected(false);
                    meisaiKind2.setSelectedIndex(0);
                    meisaiKindPrint2.setSelected(false);
                }
            } else {
                // ���͗��Ƀf�[�^(��)��ݒ�
                doSelect();

                // �ی��ҏ��2��CheckBox�̐ݒ�
                seikyushoPrint.setSelected(false);
                csv.setSelected(false);
                drName.setSelected(false);
                pageHeaderPrint.setSelected(true);
                pageHeaderPrint2.setSelected(true);

                // �_���̏����l���擾�E�ݒ�
                loadMKingakuTensu();
            }

            // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
            setInsurerDbmConnect(false);
            // �ڑ��������̂�
            if(getMasterInsurerDbm() != null && getMasterInsurerDbm().canConnect()) {
                // �t���O��ڑ��ς݂�
                setInsurerDbmConnect(true);
                // �V�K���[�h
                // �����[�V�����ݒ��ǉ�
                // �ϊ�����{�`�F�b�N����
                setInsurerRelation(new IkenshoInsurerRelation(
                        getMasterInsurerDbm(), insurerNoField,
                        insurerNmField, true, true, true, true));
                setInsurerRelationIkensyo(new IkenshoInsurerRelation(
                        getMasterInsurerDbm(), issInsurerNo, issInsurerNm,
                        true, true, true, true));
                setInsurerRelationKensa(new IkenshoInsurerRelation(
                        getMasterInsurerDbm(), sksInsurerNo, sksInsurerNm,
                        true, true, true, true));
                // ��ʏ�Ԑ���
                insurerSelectButton.setEnabled(true);
                // �`�F�b�N�𑖂点��
                getInsurerRelation().checkInsurerNo();
                getInsurerRelation().checkInsurerName();
                getInsurerRelationIkensyo().checkInsurerNo();
                getInsurerRelationIkensyo().checkInsurerName();
                getInsurerRelationKensa().checkInsurerNo();
                getInsurerRelationKensa().checkInsurerName();
                // �ĕ`��
                revalidate();
                repaint();                

            } else {
                // �ڑ��ł��Ă��Ȃ��̂Ŗ�����
                insurerSelectButton.setEnabled(false);
            }
            // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            
            // �����p�^�[������Enabled�̐ݒ�
            setseikyuPatternEnabled();

            // �X�i�b�v�V���b�g�B�e
            IkenshoSnapshot.getInstance().snapshot();
        } else {
            throw new Exception("�s���ȃp�����[�^���n����܂����B");
        }
    }

    private void event() throws Exception {
        // �����f�Õ�V�P���ɖ߂�
        rollBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // [ID:0000775][Masahiko Higuchi] 2014/03 edit begin �f�Õ�V�P���̕ύX�Ή�
                int result = ACMessageBox.show(
                        "�ӌ����쐬���^�f�@�E������p�_����"+ ACConstants.LINE_SEPARATOR + "H26�N�f�Õ�V�P����ݒ肵�܂��B��낵���ł����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK);
                // [ID:0000775][Masahiko Higuchi] 2014/03 edit end
                if (result == ACMessageBox.RESULT_OK) {
                    try {
                        loadMKingakuTensu();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // �u�˗����Ɠ���v(�ӌ����쐬��)
        issInsurerSame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                issInsurerNm.setText(insurerNmField.getText());
                issInsurerNo.setText(insurerNoField.getText());
                // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
                if(getInsurerRelationIkensyo() != null) {
                    try {
                        getInsurerRelationIkensyo().checkInsurerNo();
                        getInsurerRelationIkensyo().checkInsurerName();
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    revalidate();
                    repaint();
                }
                // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            }
        });

        // �u�˗����Ɠ���v(�f�@�E������p)
        sksInsurerSame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sksInsurerNm.setText(insurerNmField.getText());
                sksInsurerNo.setText(insurerNoField.getText());
                // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
                if(getInsurerRelationKensa() != null) {
                    try {
                        getInsurerRelationKensa().checkInsurerNo();
                        getInsurerRelationKensa().checkInsurerName();
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    revalidate();
                    repaint();
                }
                // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            }
        });

        // �����p�^�[��
        seikyuPattern.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setseikyuPatternEnabled();
            }
        });

        // �����[(�ӌ����쐬��)
        soukatuhyou.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSoukatuhyouValue(soukatuhyou, soukatuhyouPrint);
            }
        });

        // �����[(�f�@�E������p)
        soukatuhyou2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSoukatuhyouValue(soukatuhyou2, soukatuhyouPrint2);
            }
        });
        
        // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
        insurerSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    // �ی��ґI����ʂ𐶐�����B
                    IkenshoInsurerSelect insurerSelectDialog = new IkenshoInsurerSelect();
                    VRMap selectData = insurerSelectDialog.showModal(getMasterInsurerDbm());
                    // �I������Ă��Ȃ��ꍇ
                    if(selectData == null) {
                        return;
                    }
                    // �~�{�^���΍�
                    if(selectData.isEmpty()) {
                        return;
                    }
                    // �ی��Ҕԍ�
                    insurerNoField.setText(
                            ACCastUtilities.toString(VRBindPathParser.get("INSURER_NO",
                                    selectData), ""));
                    // �ی��Җ���
                    insurerNmField.setText(
                            ACCastUtilities.toString(VRBindPathParser.get("INSURER_NAME",
                                    selectData), ""));
                    getInsurerRelation().validDataNo(insurerNoField,
                            insurerNoField.getText());
                    getInsurerRelation().validDataName(insurerNmField,
                            insurerNmField.getText());
                    revalidate();
                    repaint();
                } catch(Exception ex) {
                    // �G���[
                    ex.printStackTrace();
                }
            }            
        });

        // �ӌ����쐬���̐�����ی��ґI��
        insurerSelectButtonIkensyo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    // �ی��ґI����ʂ𐶐�����B
                    IkenshoInsurerSelect insurerSelectDialog = new IkenshoInsurerSelect();
                    VRMap selectData = insurerSelectDialog.showModal(getMasterInsurerDbm());
                    // �I������Ă��Ȃ��ꍇ
                    if(selectData == null) {
                        return;
                    }
                    // �~�{�^���΍�
                    if(selectData.isEmpty()) {
                        return;
                    }
                    // �ی��Ҕԍ�
                    issInsurerNo.setText(
                            ACCastUtilities.toString(VRBindPathParser.get("INSURER_NO",
                                    selectData), ""));
                    // �ی��Җ���
                    issInsurerNm.setText(
                            ACCastUtilities.toString(VRBindPathParser.get("INSURER_NAME",
                                    selectData), ""));
                    getInsurerRelationIkensyo().validDataNo(issInsurerNo,
                            issInsurerNo.getText());
                    getInsurerRelationIkensyo().validDataName(issInsurerNm,
                            issInsurerNm.getText());
                    revalidate();
                    repaint();
                } catch(Exception ex) {
                    // �G���[
                    ex.printStackTrace();
                }
            }            
        });
        
        // ������p�_���̐�����ی���
        insurerSelectButtonKensa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try{
                    // �ی��ґI����ʂ𐶐�����B
                    IkenshoInsurerSelect insurerSelectDialog = new IkenshoInsurerSelect();
                    VRMap selectData = insurerSelectDialog.showModal(getMasterInsurerDbm());
                    // �I������Ă��Ȃ��ꍇ
                    if(selectData == null) {
                        return;
                    }
                    // �~�{�^���΍�
                    if(selectData.isEmpty()) {
                        return;
                    }
                    // �ی��Ҕԍ�
                    sksInsurerNo.setText(
                            ACCastUtilities.toString(VRBindPathParser.get("INSURER_NO",
                                    selectData), ""));
                    // �ی��Җ���
                    sksInsurerNm.setText(
                            ACCastUtilities.toString(VRBindPathParser.get("INSURER_NAME",
                                    selectData), ""));
                    getInsurerRelationKensa().validDataNo(sksInsurerNo,
                            sksInsurerNo.getText());
                    getInsurerRelationKensa().validDataName(sksInsurerNm,
                            sksInsurerNm.getText());
                    revalidate();
                    repaint();
                } catch(Exception ex) {
                    // �G���[
                    ex.printStackTrace();
                }
            }            
        });
        // [ID:0000513][Masahiko Higuchi] 2009/09 add end

    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return insurerNmField;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        String key = "INSURER_NO";
        String value = insurerNo;
        String key2 = "INSURER_TYPE";
        Integer value2 = ACCastUtilities.toInteger(insurerType);

        // �O��ʃL���b�V�����Đݒ�
        if (prevData != null) {
            parameters.put("PREV_DATA", prevData);
        }

        if (IkenshoSnapshot.getInstance().isModified()) {
            // MSG, Caption�̐ݒ�
            String msg;
            String btnCaption;
            if (isUpdate) {
                msg = "�ύX����Ă��܂��B�ۑ����܂����H";
                btnCaption = "�X�V���Ė߂�(S)";
            } else {
                msg = "�o�^���e��ۑ����܂����H";
                btnCaption = "�o�^���Ė߂�(S)";
            }
            int result = ACMessageBox.showYesNoCancel(msg, btnCaption, 'S',
                    "�j�����Ė߂�(R)", 'R');

            // DLG����
            if (result == ACMessageBox.RESULT_YES) { // �ۑ����Ė߂�
                boolean updateFlg = doUpdate(); // DB�X�V����:true, ���s:false
                parameters.put(key, insurerNo);
                parameters.put(key2, ACCastUtilities.toInteger(insurerType));
                return updateFlg;
            } else if (result == ACMessageBox.RESULT_NO) { // �ۑ����Ȃ��Ŗ߂�
                if (!isNullText(value)) {
                    parameters.put(key, value);
                    parameters.put(key2, value2);
                }
                return true;
            } else { // �߂�Ȃ�
                return false;
            }
        } else { // �߂�
            if (!isNullText(value)) {
                parameters.put(key, value);
                parameters.put(key2, value2);
            }
            return true;
        }
    }

    public boolean canClose() throws Exception {
        if (IkenshoSnapshot.getInstance().isModified()) {
            String msg = "";
            msg = "�X�V���ꂽ���e�͔j������܂��B\n�I�����Ă���낵���ł����H";
            int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (doUpdate()) { // update�������A�ꗗ��ʂɖ߂�
            ACFrame.getInstance().back();
        }
    }

    /**
     * M_KINGAKU_TENSU�e�[�u������l���擾����
     * 
     * @throws Exception
     */
    private void loadMKingakuTensu() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" ZAITAKU_SINKI_CHARGE,");
        sb.append(" ZAITAKU_KEIZOKU_CHARGE,");
        sb.append(" SISETU_SINKI_CHARGE,");
        sb.append(" SISETU_KEIZOKU_CHARGE,");
        sb.append(" SHOSIN_SINRYOUJO,");
        sb.append(" SHOSIN_HOSPITAL,");
        sb.append(" EXP_KS,");
        sb.append(" EXP_KIK_MKI,");
        sb.append(" EXP_KIK_KEKK,");
        sb.append(" EXP_KKK_KKK,");
        sb.append(" EXP_KKK_SKK,");
        sb.append(" EXP_NITK,");
        sb.append(" EXP_XRAY_TS,");
        sb.append(" EXP_XRAY_SS,");
        sb.append(" EXP_XRAY_FILM,");
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(" EXP_XRAY_DIGITAL_MANAGEMENT,");
        sb.append(" EXP_XRAY_DIGITAL_FILM,");
        sb.append(" EXP_XRAY_DIGITAL_IMAGING,");
        // 2009/01/09[Tozo Tanaka] : add end
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        sb.append(" EXP_XRAY_TS_DIGITAL,");
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end
        sb.append(" LAST_TIME");
        sb.append(" FROM");
        sb.append(" M_KINGAKU_TENSU");
        VRArrayList kingakuTensuArray = (VRArrayList) dbm.executeQuery(sb
                .toString());

        VRMap kingakuTensuData;
        if (kingakuTensuArray.getDataSize() > 0) { // DB��Ƀf�[�^�L
            kingakuTensuData = (VRMap) kingakuTensuArray.getData();
//            // ����18�N�x������p�_�� 
//            // 2006/06/20
//            // Addition - [Masahiko Higuchi]
//            // 2008/02/25 [Masahiko_Higuchi] edit - begin V3.0.6 ����20�N�x�f�Õ�V�P���ύX�Ή�
//            kingakuTensuData.put("EXP_KS",new Double(11.0)); // ���t�̎�(�Ö�)
//            kingakuTensuData.put("EXP_KIK_MKI",new Double(22.0)); // �������t��ʌ���
//            kingakuTensuData.put("EXP_KIK_KEKK",new Double(125.0)); // ���t�w�I�������f��
//            kingakuTensuData.put("EXP_KKK_KKK",new Double(129.0)); // ���t���w����(10���ڈȏ�)
//            kingakuTensuData.put("EXP_KKK_SKK",new Double(144.0)); // �����w�I����(I)���f��
//            kingakuTensuData.put("EXP_NITK",new Double(26.0)); // �A����ʕ����萫����ʌ���
//            // 2008/02/25 [Masahiko_Higuchi] edit - end
//            kingakuTensuData.put("EXP_XRAY_TS",new Double(65.0)); // �P���B�e
//            kingakuTensuData.put("EXP_XRAY_SS",new Double(85.0)); // �ʐ^�f�f(����)
//            kingakuTensuData.put("EXP_XRAY_FILM",new Double(13.0));// �t�B����(��p)
//            // 2009/01/09[Tozo Tanaka] : add begin
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_MANAGEMENT",new Double(60.0));// �d�q�摜�Ǘ����Z
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_FILM",new Double(24.1));// �摜�L�^�p�t�B����(��p)
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_IMAGING",new Double(15.0));// �f�W�^���f�����������Z
//            // 2009/01/09[Tozo Tanaka] : add end
//            kingakuTensuData.put("SHOSIN_SINRYOUJO", new Double(270.0)); // ���f��(�f�Ï�)
//            kingakuTensuData.put("SHOSIN_HOSPITAL", new Double(270.0)); // ���f��(�a�@)
//            kingakuTensuData.put("SHOSIN_ADD_IT", new Double(3.0)); // �d�q�����Z
//            // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
//            kingakuTensuData.put("EXP_XRAY_TS_DIGITAL", new Double(68.0)); // �P���B�e�i�f�W�^���j 
//            // [ID:0000600][Masahiko Higuchi] 2010/02 add end

            // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
            // [ID:0000686][Masahiko Higuchi] 2012/03 del-Start
            // Ikensyo.jar�������߂��ĉߋ��̐������쐬���s���ꍇ��z�肵�Ă��邽�߁A�\�[�X���œ_�����L��
            // �ǐ����������̂ł܂Ƃ߂ē]�L
//            kingakuTensuData.put("EXP_KS",new Double(13.0)); // ���t�̎�(�Ö�)
//            kingakuTensuData.put("EXP_KIK_MKI",new Double(21.0)); // �������t��ʌ���
//            kingakuTensuData.put("EXP_KIK_KEKK",new Double(125.0)); // ���t�w�I�������f��
//            kingakuTensuData.put("EXP_KKK_KKK",new Double(123.0)); // ���t���w����(10���ڈȏ�)
//            kingakuTensuData.put("EXP_KKK_SKK",new Double(144.0)); // �����w�I����(I)���f��
//            kingakuTensuData.put("EXP_NITK",new Double(26.0)); // �A����ʕ����萫����ʌ���
//            kingakuTensuData.put("EXP_XRAY_TS",new Double(60.0)); // �P���B�e�i�A�i���O�j
//            kingakuTensuData.put("EXP_XRAY_TS_DIGITAL", new Double(68.0)); // �P���B�e�i�f�W�^���j
//            kingakuTensuData.put("EXP_XRAY_SS",new Double(85.0)); // �ʐ^�f�f(����)
//            kingakuTensuData.put("EXP_XRAY_FILM",new Double(11));// �t�B����(��p)
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_MANAGEMENT",new Double(57.0));// �d�q�摜�Ǘ����Z
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_FILM",new Double(22));// �摜�L�^�p�t�B����(��p)
//            kingakuTensuData.put("SHOSIN_SINRYOUJO", new Double(270.0)); // ���f��(�f�Ï�)
//            kingakuTensuData.put("SHOSIN_HOSPITAL", new Double(270.0)); // ���f��(�a�@)
//            // �폜���� �l��0�Őݒ�
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_IMAGING",new Double(0.0));// �f�W�^���f�����������Z
//            kingakuTensuData.put("SHOSIN_ADD_IT", new Double(0.0)); // �d�q�����Z
            // [ID:0000686][Masahiko Higuchi] 2012/03 del-End
            // [ID:0000600][Masahiko Higuchi] 2010/02 add end
            
            // [ID:0000686][Masahiko Higuchi] 2012/03 add-Start ����24�N�f�Õ�V����
//            kingakuTensuData.put("EXP_KS",new Double(16.0)); // ���t�̎�(�Ö�)
//            kingakuTensuData.put("EXP_KIK_MKI",new Double(21.0)); // �������t��ʌ���
//            kingakuTensuData.put("EXP_KIK_KEKK",new Double(125.0)); // ���t�w�I�������f��
//            kingakuTensuData.put("EXP_KKK_KKK",new Double(121.0)); // ���t���w����(10���ڈȏ�)
//            kingakuTensuData.put("EXP_KKK_SKK",new Double(144.0)); // �����w�I����(I)���f��
//            kingakuTensuData.put("EXP_NITK",new Double(26.0)); // �A����ʕ����萫����ʌ���
//            kingakuTensuData.put("EXP_XRAY_TS",new Double(60.0)); // �P���B�e�i�A�i���O�j
//            kingakuTensuData.put("EXP_XRAY_TS_DIGITAL", new Double(68.0)); // �P���B�e�i�f�W�^���j
//            kingakuTensuData.put("EXP_XRAY_SS",new Double(85.0)); // �ʐ^�f�f(����)
//            kingakuTensuData.put("EXP_XRAY_FILM",new Double(11));// �t�B����(��p)
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_MANAGEMENT",new Double(57.0));// �d�q�摜�Ǘ����Z
//            kingakuTensuData.put("EXP_XRAY_DIGITAL_FILM",new Double(21));// �摜�L�^�p�t�B����(��p)
//            kingakuTensuData.put("SHOSIN_SINRYOUJO", new Double(270.0)); // ���f��(�f�Ï�)
//            kingakuTensuData.put("SHOSIN_HOSPITAL", new Double(270.0)); // ���f��(�a�@)
            // [ID:0000686][Masahiko Higuchi] 2012/03 add-End
            
            // [ID:0000775][Masahiko Higuchi] 2014/03 add-Start ����24�N�f�Õ�V����
            kingakuTensuData.put("EXP_KS",new Double(20.0)); // ���t�̎�(�Ö�)
            kingakuTensuData.put("EXP_KIK_MKI",new Double(21.0)); // �������t��ʌ���
            kingakuTensuData.put("EXP_KIK_KEKK",new Double(125.0)); // ���t�w�I�������f��
            kingakuTensuData.put("EXP_KKK_KKK",new Double(117.0)); // ���t���w����(10���ڈȏ�)
            kingakuTensuData.put("EXP_KKK_SKK",new Double(144.0)); // �����w�I����(I)���f��
            kingakuTensuData.put("EXP_NITK",new Double(26.0)); // �A����ʕ����萫����ʌ���
            kingakuTensuData.put("EXP_XRAY_TS",new Double(60.0)); // �P���B�e�i�A�i���O�j
            kingakuTensuData.put("EXP_XRAY_TS_DIGITAL", new Double(68.0)); // �P���B�e�i�f�W�^���j
            kingakuTensuData.put("EXP_XRAY_SS",new Double(85.0)); // �ʐ^�f�f(����)
            kingakuTensuData.put("EXP_XRAY_FILM",new Double(12.0));// �t�B����(��p)
            kingakuTensuData.put("EXP_XRAY_DIGITAL_MANAGEMENT",new Double(57.0));// �d�q�摜�Ǘ����Z
            kingakuTensuData.put("EXP_XRAY_DIGITAL_FILM",new Double(21));// �摜�L�^�p�t�B����(��p)
            kingakuTensuData.put("SHOSIN_SINRYOUJO", new Double(282.0)); // ���f��(�f�Ï�)
            kingakuTensuData.put("SHOSIN_HOSPITAL", new Double(282.0)); // ���f��(�a�@)
            // [ID:0000775][Masahiko Higuchi] 2014/03 add-End
            
            
            
            // �폜���� �l��0�Őݒ�
            kingakuTensuData.put("EXP_XRAY_DIGITAL_IMAGING",new Double(0.0));// �f�W�^���f�����������Z
            kingakuTensuData.put("SHOSIN_ADD_IT", new Double(0.0)); // �d�q�����Z
            
            
        } else {
            kingakuTensuData = (VRMap) pointsGrp.createSource(); // DB��Ƀf�[�^��
        }
        pointsGrp.setSource(kingakuTensuData);
        pointsGrp.bindSource();
        pointsGrp.setSource(insurerData);
    }

    /**
     * DB����f�[�^���擾���܂��B
     * 
     * @throws Exception
     */
    private void doSelect() throws Exception {
        VRArrayList insurerArray = null;

        // �L�[�����ɁADB����f�[�^���擾
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" INSURER_NO,");
        sb.append(" INSURER_NM,");
        sb.append(" INSURER_TYPE,");
        sb.append(" FD_OUTPUT_UMU,");
        sb.append(" SEIKYUSHO_HAKKOU_PATTERN,");
        sb.append(" SEIKYUSHO_OUTPUT_PATTERN,");
        sb.append(" DR_NM_OUTPUT_UMU,");
        sb.append(" HEADER_OUTPUT_UMU1,");
        sb.append(" HEADER_OUTPUT_UMU2,");
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
        sb.append(" KIND_OUTPUT_UMU,");
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
        sb.append(" ISS_INSURER_NO,");
        sb.append(" ISS_INSURER_NM,");
        sb.append(" SKS_INSURER_NO,");
        sb.append(" SKS_INSURER_NM,");
        sb.append(" ZAITAKU_SINKI_CHARGE,");
        sb.append(" ZAITAKU_KEIZOKU_CHARGE,");
        sb.append(" SISETU_SINKI_CHARGE,");
        sb.append(" SISETU_KEIZOKU_CHARGE,");
        sb.append(" SHOSIN_SINRYOUJO,");
        sb.append(" SHOSIN_HOSPITAL,");
        sb.append(" SHOSIN_ADD_IT,");
        // 2006/02/07[Tozo Tanaka] : add begin
        sb.append(" SHOSIN_ADD_IT_TYPE,");
        // 2006/09/07[Tozo Tanaka] : add end
        sb.append(" EXP_KS,");
        sb.append(" EXP_KIK_MKI,");
        sb.append(" EXP_KIK_KEKK,");
        sb.append(" EXP_KKK_KKK,");
        sb.append(" EXP_KKK_SKK,");
        sb.append(" EXP_NITK,");
        sb.append(" EXP_XRAY_TS,");
        sb.append(" EXP_XRAY_SS,");
        sb.append(" EXP_XRAY_FILM,");
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(" EXP_XRAY_DIGITAL_MANAGEMENT,");
        sb.append(" EXP_XRAY_DIGITAL_FILM,");
        sb.append(" EXP_XRAY_DIGITAL_IMAGING,");
        // 2009/01/09[Tozo Tanaka] : add end
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        sb.append(" EXP_XRAY_TS_DIGITAL,");
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end
        sb.append(" SOUKATUHYOU_PRT,");
        sb.append(" MEISAI_KIND,");
        sb.append(" FURIKOMISAKI_PRT,");
        sb.append(" SOUKATU_FURIKOMI_PRT,");
        sb.append(" SOUKATUHYOU_PRT2,");
        sb.append(" MEISAI_KIND2,");
        sb.append(" FURIKOMISAKI_PRT2,");
        sb.append(" SOUKATU_FURIKOMI_PRT2,");
        sb.append(" LAST_TIME");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" WHERE");
        sb.append(" INSURER_NO='" + insurerNo + "'");
        sb.append(" AND INSURER_TYPE=" + insurerType);
        sb.append(" ORDER BY");
        sb.append(" INSURER_NO");
        insurerArray = (VRArrayList) dbm.executeQuery(sb.toString());

        if (insurerArray.getDataSize() > 0) { // DB��Ƀf�[�^�L
            insurerData = (VRMap) insurerArray.getData();
            insurerNm = insurerData.getData("INSURER_NM").toString(); // �ی��Җ���ޔ�
            // �p�b�V�u�`�F�b�N�p
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, insurerArray);
        } else {
            insurerData = (VRMap) tabPnl.createSource(); // DB��Ƀf�[�^��
            hasData = false;
            // 2006/02/07[Tozo Tanaka] : add begin
            //�d�q�����Z�敪�̏����l��1
            VRBindPathParser.set("SHOSIN_ADD_IT_TYPE", insurerData, new Integer(1));
            // 2006/09/07[Tozo Tanaka] : add end
        }

        tabPnl.setSource(insurerData);
        tabPnl.bindSource();
    }

    /**
     * DB���X�V���܂��B
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdate() throws Exception {
        // ���̓`�F�b�N
        // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
        if (isValidInput() && isValidSave()) {
        // [ID:0000513][Masahiko Higuchi] 2009/09 add end

            String msg = "";

            // update
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            try {
                // ���̓f�[�^���擾
                tabPnl.applySource();
                String INSURER_NO = getDBSafeString("INSURER_NO", insurerData);
                String INSURER_NM = getDBSafeString("INSURER_NM", insurerData);
                String INSURER_TYPE = getDBSafeNumber("INSURER_TYPE", insurerData);
                String FD_OUTPUT_UMU = getDBSafeNumber("FD_OUTPUT_UMU",
                        insurerData);
                String SEIKYUSHO_HAKKOU_PATTERN = getDBSafeNumber(
                        "SEIKYUSHO_HAKKOU_PATTERN", insurerData);
                String SEIKYUSHO_OUTPUT_PATTERN = getDBSafeNumber(
                        "SEIKYUSHO_OUTPUT_PATTERN", insurerData);
                String DR_NM_OUTPUT_UMU = getDBSafeNumber("DR_NM_OUTPUT_UMU",
                        insurerData);
                String HEADER_OUTPUT_UMU1 = getDBSafeNumber(
                        "HEADER_OUTPUT_UMU1", insurerData);
                String HEADER_OUTPUT_UMU2 = getDBSafeNumber(
                        "HEADER_OUTPUT_UMU2", insurerData);

                String ISS_INSURER_NO = "''";
                String ISS_INSURER_NM = "''";
                String SOUKATUHYOU_PRT = "0";
                String MEISAI_KIND = "0";
                String FURIKOMISAKI_PRT = "0";
                String SOUKATU_FURIKOMI_PRT = "0";
                if (issGrp.isEnabled()) {
                    ISS_INSURER_NO = getDBSafeString("ISS_INSURER_NO",
                            insurerData);
                    ISS_INSURER_NM = getDBSafeString("ISS_INSURER_NM",
                            insurerData);
                    SOUKATUHYOU_PRT = getDBSafeNumber("SOUKATUHYOU_PRT",
                            insurerData);
                    MEISAI_KIND = getDBSafeNumber("MEISAI_KIND", insurerData);
                    FURIKOMISAKI_PRT = getDBSafeNumber("FURIKOMISAKI_PRT",
                            insurerData);
                    if (soukatuhyouPrint.isEnabled()) {
                        SOUKATU_FURIKOMI_PRT = getDBSafeNumber(
                                "SOUKATU_FURIKOMI_PRT", insurerData);
                    }
                }

                String SKS_INSURER_NO = "''";
                String SKS_INSURER_NM = "''";
                String SOUKATUHYOU_PRT2 = "0";
                String MEISAI_KIND2 = "0";
                String FURIKOMISAKI_PRT2 = "0";
                String SOUKATU_FURIKOMI_PRT2 = "0";
                if (sksGrp.isEnabled()) {
                    SKS_INSURER_NO = getDBSafeString("SKS_INSURER_NO",
                            insurerData);
                    SKS_INSURER_NM = getDBSafeString("SKS_INSURER_NM",
                            insurerData);
                    SOUKATUHYOU_PRT2 = getDBSafeNumber("SOUKATUHYOU_PRT2",
                            insurerData);
                    MEISAI_KIND2 = getDBSafeNumber("MEISAI_KIND2", insurerData);
                    FURIKOMISAKI_PRT2 = getDBSafeNumber("FURIKOMISAKI_PRT2",
                            insurerData);
                    if (soukatuhyouPrint2.isEnabled()) {
                        SOUKATU_FURIKOMI_PRT2 = getDBSafeNumber(
                                "SOUKATU_FURIKOMI_PRT2", insurerData);
                    }
                } else if (seikyuPattern.getSelectedIndex() == 1) {
                    SKS_INSURER_NO = ISS_INSURER_NO;
                    SKS_INSURER_NM = ISS_INSURER_NM;
                    SOUKATUHYOU_PRT2 = SOUKATUHYOU_PRT;
                    MEISAI_KIND2 = MEISAI_KIND;
                    FURIKOMISAKI_PRT2 = FURIKOMISAKI_PRT;
                    if (soukatuhyouPrint.isEnabled()) {
                        SOUKATU_FURIKOMI_PRT2 = SOUKATU_FURIKOMI_PRT;
                    }
                }

                String ZAITAKU_SINKI_CHARGE = getDBSafeNumber(
                        "ZAITAKU_SINKI_CHARGE", insurerData);
                String ZAITAKU_KEIZOKU_CHARGE = getDBSafeNumber(
                        "ZAITAKU_KEIZOKU_CHARGE", insurerData);
                String SISETU_SINKI_CHARGE = getDBSafeNumber(
                        "SISETU_SINKI_CHARGE", insurerData);
                String SISETU_KEIZOKU_CHARGE = getDBSafeNumber(
                        "SISETU_KEIZOKU_CHARGE", insurerData);
                String SHOSIN_SINRYOUJO = getDBSafeNumber("SHOSIN_SINRYOUJO",
                        insurerData);
                String SHOSIN_HOSPITAL = getDBSafeNumber("SHOSIN_HOSPITAL",
                        insurerData);
                String SHOSIN_ADD_IT = getDBSafeNumber("SHOSIN_ADD_IT",
                        insurerData); 
                // 2006/02/07[Tozo Tanaka] : add begin
                String SHOSIN_ADD_IT_TYPE = getDBSafeNumber("SHOSIN_ADD_IT_TYPE",
                        insurerData); 
                // 2006/09/07[Tozo Tanaka] : add end

                String EXP_KS = getDBSafeNumber("EXP_KS", insurerData);
                String EXP_KIK_MKI = getDBSafeNumber("EXP_KIK_MKI", insurerData);
                String EXP_KIK_KEKK = getDBSafeNumber("EXP_KIK_KEKK",
                        insurerData);
                String EXP_KKK_KKK = getDBSafeNumber("EXP_KKK_KKK", insurerData);
                String EXP_KKK_SKK = getDBSafeNumber("EXP_KKK_SKK", insurerData);
                String EXP_NITK = getDBSafeNumber("EXP_NITK", insurerData);
                String EXP_XRAY_TS = getDBSafeNumber("EXP_XRAY_TS", insurerData);
                String EXP_XRAY_SS = getDBSafeNumber("EXP_XRAY_SS", insurerData);
                String EXP_XRAY_FILM = getDBSafeNumber("EXP_XRAY_FILM",
                        insurerData);
                // 2009/01/09[Tozo Tanaka] : add begin
                String EXP_XRAY_DIGITAL_MANAGEMENT = getDBSafeNumber("EXP_XRAY_DIGITAL_MANAGEMENT", insurerData);
                String EXP_XRAY_DIGITAL_FILM = getDBSafeNumber("EXP_XRAY_DIGITAL_FILM", insurerData);
                String EXP_XRAY_DIGITAL_IMAGING = getDBSafeNumber("EXP_XRAY_DIGITAL_IMAGING", insurerData);
                // 2009/01/09[Tozo Tanaka] : add end
                // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
                String EXP_XRAY_TS_DIGITAL = getDBSafeNumber("EXP_XRAY_TS_DIGITAL", insurerData);
                // [ID:0000600][Masahiko Higuchi] 2010/02 add end
                // �p�b�V�u�`�F�b�N / �g�����U�N�V�����J�n
                if (isUpdate) {
                    // �X�V��
                    clearPassiveTask();
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return false;
                    }
                } else {
                    // �ǉ���
                    dbm.beginTransaction(); // �g�����U�N�V�����J�n
                }

                // SQL���𐶐� + �����ʒmMsg�ݒ�
                StringBuffer sb = new StringBuffer();
                if (isUpdate) {
                    // �X�V��
                    msg = "�X�V����܂����B";
                    sb.append(" UPDATE");
                    sb.append(" INSURER");
                    sb.append(" SET");
                    sb.append(" INSURER_NO=" + INSURER_NO);
                    sb.append(",INSURER_NM=" + INSURER_NM);
                    sb.append(",INSURER_TYPE=" + INSURER_TYPE);
                    sb.append(",FD_OUTPUT_UMU=" + FD_OUTPUT_UMU);
                    sb.append(",SEIKYUSHO_HAKKOU_PATTERN="
                            + SEIKYUSHO_HAKKOU_PATTERN);
                    sb.append(",SEIKYUSHO_OUTPUT_PATTERN="
                            + SEIKYUSHO_OUTPUT_PATTERN);
                    sb.append(",DR_NM_OUTPUT_UMU=" + DR_NM_OUTPUT_UMU);
                    sb.append(",HEADER_OUTPUT_UMU1=" + HEADER_OUTPUT_UMU1);
                    sb.append(",HEADER_OUTPUT_UMU2=" + HEADER_OUTPUT_UMU2);
                    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
                    sb.append(",KIND_OUTPUT_UMU="
                            + getDBSafeNumber("KIND_OUTPUT_UMU", insurerData));
                    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
                    sb.append(",ISS_INSURER_NO=" + ISS_INSURER_NO);
                    sb.append(",ISS_INSURER_NM=" + ISS_INSURER_NM);
                    sb.append(",SKS_INSURER_NO=" + SKS_INSURER_NO);
                    sb.append(",SKS_INSURER_NM=" + SKS_INSURER_NM);
                    sb.append(",ZAITAKU_SINKI_CHARGE=" + ZAITAKU_SINKI_CHARGE);
                    sb.append(",ZAITAKU_KEIZOKU_CHARGE="
                            + ZAITAKU_KEIZOKU_CHARGE);
                    sb.append(",SISETU_SINKI_CHARGE=" + SISETU_SINKI_CHARGE);
                    sb
                            .append(",SISETU_KEIZOKU_CHARGE="
                                    + SISETU_KEIZOKU_CHARGE);
                    sb.append(",SHOSIN_SINRYOUJO=" + SHOSIN_SINRYOUJO);
                    sb.append(",SHOSIN_HOSPITAL=" + SHOSIN_HOSPITAL);
                    sb.append(",SHOSIN_ADD_IT=" + SHOSIN_ADD_IT);
                    // 2006/02/07[Tozo Tanaka] : add begin
                    sb.append(",SHOSIN_ADD_IT_TYPE=" + SHOSIN_ADD_IT_TYPE);
                    // 2006/09/07[Tozo Tanaka] : add end
                    
                    sb.append(",EXP_KS=" + EXP_KS);
                    sb.append(",EXP_KIK_MKI=" + EXP_KIK_MKI);
                    sb.append(",EXP_KIK_KEKK=" + EXP_KIK_KEKK);
                    sb.append(",EXP_KKK_KKK=" + EXP_KKK_KKK);
                    sb.append(",EXP_KKK_SKK=" + EXP_KKK_SKK);
                    sb.append(",EXP_NITK=" + EXP_NITK);
                    sb.append(",EXP_XRAY_TS=" + EXP_XRAY_TS);
                    sb.append(",EXP_XRAY_SS=" + EXP_XRAY_SS);
                    sb.append(",EXP_XRAY_FILM=" + EXP_XRAY_FILM);
                    // 2009/01/09[Tozo Tanaka] : add begin
                    sb.append(",EXP_XRAY_DIGITAL_MANAGEMENT=" + EXP_XRAY_DIGITAL_MANAGEMENT);
                    sb.append(",EXP_XRAY_DIGITAL_FILM=" + EXP_XRAY_DIGITAL_FILM);
                    sb.append(",EXP_XRAY_DIGITAL_IMAGING=" + EXP_XRAY_DIGITAL_IMAGING);
                    // 2009/01/09[Tozo Tanaka] : add end
//                  [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
                    sb.append(",EXP_XRAY_TS_DIGITAL=" + EXP_XRAY_TS_DIGITAL);
//                  [ID:0000600][Masahiko Higuchi] 2010/02 add end
                    sb.append(",SOUKATUHYOU_PRT=" + SOUKATUHYOU_PRT);
                    sb.append(",MEISAI_KIND=" + MEISAI_KIND);
                    sb.append(",FURIKOMISAKI_PRT=" + FURIKOMISAKI_PRT);
                    sb.append(",SOUKATU_FURIKOMI_PRT=" + SOUKATU_FURIKOMI_PRT);
                    sb.append(",SOUKATUHYOU_PRT2=" + SOUKATUHYOU_PRT2);
                    sb.append(",MEISAI_KIND2=" + MEISAI_KIND2);
                    sb.append(",FURIKOMISAKI_PRT2=" + FURIKOMISAKI_PRT2);
                    sb
                            .append(",SOUKATU_FURIKOMI_PRT2="
                                    + SOUKATU_FURIKOMI_PRT2);
                    sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
                    sb.append(" WHERE");
                    sb.append(" INSURER_NO='" + insurerNo + "'");
                    sb.append(" AND INSURER_TYPE=" + insurerType);
                } else {
                    // �ǉ���
                    msg = "�o�^����܂����B";
                    sb.append("INSERT INTO");
                    sb.append(" INSURER");
                    sb.append(" (");
                    sb.append(" INSURER_NO,");
                    sb.append(" INSURER_NM,");
                    sb.append(" INSURER_TYPE,");
                    sb.append(" FD_OUTPUT_UMU,");
                    sb.append(" SEIKYUSHO_HAKKOU_PATTERN,");
                    sb.append(" SEIKYUSHO_OUTPUT_PATTERN,");
                    sb.append(" DR_NM_OUTPUT_UMU,");
                    sb.append(" HEADER_OUTPUT_UMU1,");
                    sb.append(" HEADER_OUTPUT_UMU2,");
                    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
                    sb.append(" KIND_OUTPUT_UMU,");
                    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
                    sb.append(" ISS_INSURER_NO,");
                    sb.append(" ISS_INSURER_NM,");
                    sb.append(" SKS_INSURER_NO,");
                    sb.append(" SKS_INSURER_NM,");
                    sb.append(" ZAITAKU_SINKI_CHARGE,");
                    sb.append(" ZAITAKU_KEIZOKU_CHARGE,");
                    sb.append(" SISETU_SINKI_CHARGE,");
                    sb.append(" SISETU_KEIZOKU_CHARGE,");
                    sb.append(" SHOSIN_SINRYOUJO,");
                    sb.append(" SHOSIN_HOSPITAL,");
                    sb.append(" SHOSIN_ADD_IT,");
                    // 2006/02/07[Tozo Tanaka] : add begin
                    sb.append(" SHOSIN_ADD_IT_TYPE,");
                    // 2006/09/07[Tozo Tanaka] : add end
                    sb.append(" EXP_KS,");
                    sb.append(" EXP_KIK_MKI,");
                    sb.append(" EXP_KIK_KEKK,");
                    sb.append(" EXP_KKK_KKK,");
                    sb.append(" EXP_KKK_SKK,");
                    sb.append(" EXP_NITK,");
                    sb.append(" EXP_XRAY_TS,");
                    sb.append(" EXP_XRAY_SS,");
                    sb.append(" EXP_XRAY_FILM,");
                    // 2009/01/09[Tozo Tanaka] : add begin
                    sb.append(" EXP_XRAY_DIGITAL_MANAGEMENT,");
                    sb.append(" EXP_XRAY_DIGITAL_FILM,");
                    sb.append(" EXP_XRAY_DIGITAL_IMAGING,");
                    // 2009/01/09[Tozo Tanaka] : add end
                    // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
                    sb.append(" EXP_XRAY_TS_DIGITAL,");
                    // [ID:0000600][Masahiko Higuchi] 2010/02 add end
                    sb.append(" SOUKATUHYOU_PRT,");
                    sb.append(" MEISAI_KIND,");
                    sb.append(" FURIKOMISAKI_PRT,");
                    sb.append(" SOUKATU_FURIKOMI_PRT,");
                    sb.append(" SOUKATUHYOU_PRT2,");
                    sb.append(" MEISAI_KIND2,");
                    sb.append(" FURIKOMISAKI_PRT2,");
                    sb.append(" SOUKATU_FURIKOMI_PRT2,");
                    sb.append(" LAST_TIME");
                    sb.append(" )");
                    sb.append(" VALUES(");
                    sb.append(" " + INSURER_NO);
                    sb.append("," + INSURER_NM);
                    sb.append("," + INSURER_TYPE);
                    sb.append("," + FD_OUTPUT_UMU);
                    sb.append("," + SEIKYUSHO_HAKKOU_PATTERN);
                    sb.append("," + SEIKYUSHO_OUTPUT_PATTERN);
                    sb.append("," + DR_NM_OUTPUT_UMU);
                    sb.append("," + HEADER_OUTPUT_UMU1);
                    sb.append("," + HEADER_OUTPUT_UMU2);
                    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
                    sb.append("," + getDBSafeNumber("KIND_OUTPUT_UMU", insurerData));
                    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�
                    sb.append("," + ISS_INSURER_NO);
                    sb.append("," + ISS_INSURER_NM);
                    sb.append("," + SKS_INSURER_NO);
                    sb.append("," + SKS_INSURER_NM);
                    sb.append("," + ZAITAKU_SINKI_CHARGE);
                    sb.append("," + ZAITAKU_KEIZOKU_CHARGE);
                    sb.append("," + SISETU_SINKI_CHARGE);
                    sb.append("," + SISETU_KEIZOKU_CHARGE);
                    sb.append("," + SHOSIN_SINRYOUJO);
                    sb.append("," + SHOSIN_HOSPITAL);
                    sb.append("," + SHOSIN_ADD_IT);
                    // 2006/02/07[Tozo Tanaka] : add begin
                    sb.append("," + SHOSIN_ADD_IT_TYPE);
                    // 2006/09/07[Tozo Tanaka] : add end
                    sb.append("," + EXP_KS);
                    sb.append("," + EXP_KIK_MKI);
                    sb.append("," + EXP_KIK_KEKK);
                    sb.append("," + EXP_KKK_KKK);
                    sb.append("," + EXP_KKK_SKK);
                    sb.append("," + EXP_NITK);
                    sb.append("," + EXP_XRAY_TS);
                    sb.append("," + EXP_XRAY_SS);
                    sb.append("," + EXP_XRAY_FILM);
                    // 2009/01/09[Tozo Tanaka] : add begin
                    sb.append("," + EXP_XRAY_DIGITAL_MANAGEMENT);
                    sb.append("," + EXP_XRAY_DIGITAL_FILM);
                    sb.append("," + EXP_XRAY_DIGITAL_IMAGING);
                    // 2009/01/09[Tozo Tanaka] : add end
                    // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
                    sb.append("," + EXP_XRAY_TS_DIGITAL);
                    // [ID:0000600][Masahiko Higuchi] 2010/02 add end
                    sb.append("," + SOUKATUHYOU_PRT);
                    sb.append("," + MEISAI_KIND);
                    sb.append("," + FURIKOMISAKI_PRT);
                    sb.append("," + SOUKATU_FURIKOMI_PRT);
                    sb.append("," + SOUKATUHYOU_PRT2);
                    sb.append("," + MEISAI_KIND2);
                    sb.append("," + FURIKOMISAKI_PRT2);
                    sb.append("," + SOUKATU_FURIKOMI_PRT2);
                    sb.append(",CURRENT_TIMESTAMP");
                    sb.append(" )");
                }

                // �X�V�pSQL���s
                dbm.executeUpdate(sb.toString());

                // �㏈��(�u�V�K�v�A�y�сu�����v���[�h�œo�^������A�u�X�V�v���[�h�ֈڍs����)
                if (!isUpdate) {
                    isUpdate = true;
                    hasData = true;
                    update.setText("�X�V(S)");
                    update.setToolTipText("���݂̓��e���X�V���܂��B");
                }

                // �R�~�b�g
                dbm.commitTransaction();

                // insurerNo���X�V
                insurerNo = INSURER_NO.replaceAll("'", "");
                // insurerType���X�V
                insurerType = ACCastUtilities.toInt(INSURER_TYPE,0);
            } catch (Exception ex) {
                // ���[���o�b�N
                dbm.rollbackTransaction();
                throw new Exception(ex);
            }

            // �X�i�b�v�V���b�g�B�e
            IkenshoSnapshot.getInstance().snapshot();

            // ���������ʒmMsg�\��
            ACMessageBox.show(msg, ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
            return true;
        }
        return false;
    }

    /**
     * ���̓`�F�b�N����
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean isValidInput() throws Exception {
        boolean valid = false;

        // �G���[�`�F�b�N
        valid = checkErr();

        if (valid) {
            valid = checkWarning();
        }

        return valid;
    }

    /**
     * �G���[���b�Z�[�W��\�����܂��B
     * 
     * @param tabIndex int
     * @param tgt ���b�Z�[�W�\����Ƀt�H�[�J�X�𓖂Ă�R���|�[�l���g
     * @param msg �G���[���b�Z�[�W
     */
    private void showErrMessageBox(int tabIndex, Component tgt, String msg) {
        ACMessageBox.show(msg, ACMessageBox.BUTTON_OK,
                ACMessageBox.ICON_EXCLAMATION);
        if (tgt != null) {
            if (tabIndex >= 0) {
                if (tabIndex < tab.getTabCount()) {
                    tab.setSelectedIndex(tabIndex);
                }
            }
            tgt.requestFocus();
        }
    }

    /**
     * �x�����b�Z�[�W��\�����܂��B
     * 
     * @param msg �G���[���b�Z�[�W
     * @return true:OK, false:CANCEL
     */
    private boolean showWarningMessageBox(String msg) {
        int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK);
        if (result == ACMessageBox.RESULT_OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * �G���[�`�F�b�N
     * 
     * @return valid / true:�L��(OK), false:����(ERR)
     * @throws Exception
     */
    private boolean checkErr() throws Exception {
        boolean doCheckFlg = false;

        // �ی��Җ����݃`�F�b�N(�����̓`�F�b�N)
        if (isNullText(insurerNmField.getText())) {
            showErrMessageBox(0, insurerNmField, "�ی��Җ��̂���͂��Ă��������B");
            return false;
        }

        // ���̃`�F�b�N�́A�u�X�V�v�u�ی��Ҕԍ��ύX�Ȃ��v�ȊO�̃P�[�X�ōs��
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNoField.getText().equals(insurerNo)) {
                doCheckFlg = false; // �X�V�E�ύX�Ȃ��̏ꍇ�͏d���`�F�b�N�͍s��Ȃ�
            }
        }
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm;
            StringBuffer sb;
            VRArrayList tmpArray;
            VRMap tmpHashMap;
            int cntInsurerNo;

            // �ӌ����ŗL���(IKN_ORIGIN)�Ŏg�p����Ă���ԍ����`�F�b�N
            dbm = new IkenshoFirebirdDBManager();
            sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_NO");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");
            sb.append(" INSURER_NO = '" + insurerNo + "'");
            tmpArray = (VRArrayList) dbm.executeQuery(sb.toString());
            tmpHashMap = (VRMap) tmpArray.getData();
            cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NO")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNoField,
                        "�ӌ����Ɏg�p����Ă���ی��Ҕԍ��͕ύX�ł��܂���B");
                return false;
            }
        }

        // ���̃`�F�b�N�́A�u�X�V�v�u�ی��Ҕԍ��ύX�Ȃ��v�ȊO�̃P�[�X�ōs��
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNoField.getText().equals(insurerNo)) {
                doCheckFlg = false; // �X�V�E�ύX�Ȃ��̏ꍇ�͏d���`�F�b�N�͍s��Ȃ�
            }
        }
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm;
            StringBuffer sb;
            VRArrayList tmpArray;
            VRMap tmpHashMap;
            int cntInsurerNo;

            // ���Ǝ҃e�[�u��(JIGYOUSHA)�Ŏg�p����Ă���ԍ����`�F�b�N
            dbm = new IkenshoFirebirdDBManager();
            sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_NO");
            sb.append(" FROM");
            sb.append(" JIGYOUSHA");
            sb.append(" WHERE");
            sb.append(" INSURER_NO = '" + insurerNo + "'");
            tmpArray = (VRArrayList) dbm.executeQuery(sb.toString());
            tmpHashMap = (VRMap) tmpArray.getData();
            cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NO")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNoField,
                        "���Ǝ҃}�X�^�Ɏg�p����Ă���ی��Ҕԍ��͕ύX�ł��܂���B");
                return false;
            }
        }

        // ���̃`�F�b�N�́A�u�X�V�v�u�ی��Җ��ύX�Ȃ��v�ȊO�̃P�[�X�ōs��
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNmField.getText().equals(insurerNm)) {
                doCheckFlg = false; // �X�V�A���ύX�Ȃ��̏ꍇ�͏d���`�F�b�N�͍s��Ȃ�
            }
        }
        // �ی��Җ��d���`�F�b�N
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm;
            StringBuffer sb = new StringBuffer();
            VRArrayList tmpArray;
            VRMap tmpHashMap;
            int cntInsurerNo;

            dbm = new IkenshoFirebirdDBManager();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NM) AS CNT_INSURER_NM");
            sb.append(" FROM");
            sb.append(" INSURER");
            sb.append(" WHERE");
            sb.append(" INSURER_NM = '" + insurerNmField.getText() + "'");
            sb.append(getInsurerTypeCheckSQL());
            tmpArray = (VRArrayList) dbm.executeQuery(sb.toString());
            tmpHashMap = (VRMap) tmpArray.getData();
            cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NM")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNmField, "�ی��Җ����d�����Ă��܂��B");
                return false;
            }
        }

        // ���̃`�F�b�N�́A�u�X�V�v�u�ی��Ҕԍ��ύX�Ȃ��v�ȊO�̃P�[�X�ōs��
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNoField.getText().equals(insurerNo)) {
                doCheckFlg = false; // �X�V�E�ύX�Ȃ��̏ꍇ�͏d���`�F�b�N�͍s��Ȃ�
            }
        }
        // �ی��Ҕԍ��d���`�F�b�N
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_NO");
            sb.append(" FROM");
            sb.append(" INSURER");
            sb.append(" WHERE");
            sb.append(" INSURER_NO = '" + insurerNoField.getText() + "'");
            sb.append(getInsurerTypeCheckSQL());
            VRArrayList tmpArray = (VRArrayList) dbm
                    .executeQuery(sb.toString());
            VRMap tmpHashMap = (VRMap) tmpArray.getData();
            int cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NO")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNoField, "�ی��Ҕԍ����d�����Ă��܂��B");
                return false;
            }
        }

        // �ی��Ҕԍ����݃`�F�b�N(�����̓`�F�b�N)
        if (isNullText(insurerNoField.getText())) {
            showErrMessageBox(0, insurerNoField, "�ی��Ҕԍ�����͂��Ă��������B");
            return false;
        }
        

        // ���̃`�F�b�N�́A�u�X�V�v�u�ی��ҋ敪�ύX�Ȃ��v�ȊO�̃P�[�X�ōs��
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerTypes.getSelectedIndex()==insurerType) {
                doCheckFlg = false; // �X�V�E�ύX�Ȃ��̏ꍇ�͏d���`�F�b�N�͍s��Ȃ�
            }
        }
        // �ی��ҋ敪�d���`�F�b�N
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_TYPE");
            sb.append(" FROM");
            sb.append(" INSURER");
            sb.append(" WHERE");
            
            sb.append("(");
            sb.append(" (INSURER_NO = '" + insurerNoField.getText() + "'");
            sb.append(getInsurerTypeCheckSQL());
            sb.append(")OR");
            sb.append(" (INSURER_NM = '" + insurerNmField.getText() + "'");
            sb.append(getInsurerTypeCheckSQL());
            sb.append(")");
            sb.append(")AND INSURER_TYPE!=" + insurerType);

            VRArrayList tmpArray = (VRArrayList) dbm
                    .executeQuery(sb.toString());
            VRMap tmpHashMap = (VRMap) tmpArray.getData();
            int cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_TYPE")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0,  insurerTypes, "�ی��ҋ敪���d�����Ă��܂��B");
                return false;
            }
        }

        
        // �ی��Ҕԍ����K���̓`�F�b�N(�����`�F�b�N)
        /*
         * Pattern pattern = Pattern.compile("[0-9]*"); Matcher matcher =
         * pattern.matcher(insurerNoField.getText()); if (!matcher.matches()) {
         * showErrMessageBox(0, insurerNoField, "�ی��Ҕԍ��́A�����œ��͂��Ă��������B"); return
         * false; }
         */

        // �o�̓p�^�[�����I���`�F�b�N
        if (seikyuPattern.getSelectedIndex() <= 0) {
            showErrMessageBox(0, seikyuPattern, "�����p�^�[�����I������Ă��܂���B");
            return false;
        }

        // �ӌ����쐬�������� / �����於�̓��̓`�F�b�N(�g�p�\�ȏꍇ�̂�)
        if (issInsurerNm.isEnabled()) {
            if (isNullText(issInsurerNm.getText())) {
                showErrMessageBox(0, issInsurerNm, "�����於�̂���͂��Ă��������B");
                return false;
            }
        }
        // �ӌ����쐬�������� / �ӌ��������\�o�͏�ԃ`�F�b�N
        if (soukatuhyou.isEnabled()) {
            if (soukatuhyou.getSelectedIndex() <= 0) {
                showErrMessageBox(0, soukatuhyou, issGrp.getText()
                        + "�̑����\�o�͂��I������Ă��܂���B");
                return false;
            }
        }
        // �ӌ����쐬�������� / ���׏���ޏ�ԃ`�F�b�N
        if (meisaiKind.isEnabled()) {
            if (meisaiKind.getSelectedIndex() <= 0) {
                showErrMessageBox(0, meisaiKind, issGrp.getText()
                        + "�̖��׏���ނ��I������Ă��܂���B");
                return false;
            }
        }

        // �f�@�E������p������ / �����於��2���̓`�F�b�N(�g�p�\�ȏꍇ�̂�)
        if (sksInsurerNm.isEnabled()) {
            if (isNullText(sksInsurerNm.getText())) {
                showErrMessageBox(0, sksInsurerNm, "�����於�̂���͂��Ă��������B");
                return false;
            }
        }
        // �f�@�E������p������ / �����\�o�͏�ԃ`�F�b�N
        if (soukatuhyou2.isEnabled()) {
            if (soukatuhyou2.getSelectedIndex() <= 0) {
                showErrMessageBox(0, soukatuhyou2, sksGrp.getText()
                        + "�̑����\�o�͂��I������Ă��܂���B");
                return false;
            }
        }
        // �f�@�E������p������ / ���׏��o�͏�ԃ`�F�b�N
        if (meisaiKind2.isEnabled()) {
            if (meisaiKind2.getSelectedIndex() <= 0) {
                showErrMessageBox(0, meisaiKind2, sksGrp.getText()
                        + "�̖��ו\��ނ��I������Ă��܂���B");
                return false;
            }
        }

        String pat = "[0-9]+";
        String msg = "�ݒ�l���s���ł��B";
        // �ݑ�V�K���̓`�F�b�N
        if (!isValidPattern(pat, 1, zaitakuSinkiCharge, msg)) {
            return false;
        }
        // �ݑ�p�����̓`�F�b�N
        if (!isValidPattern(pat, 1, zaitakuKeizokuCharge, msg)) {
            return false;
        }
        // �{�ݐV�K���̓`�F�b�N
        if (!isValidPattern(pat, 1, sisetuSinkiCharge, msg)) {
            return false;
        }
        // �{�݌p�����̓`�F�b�N
        if (!isValidPattern(pat, 1, sisetuKeizokuCharge, msg)) {
            return false;
        }

        pat = "(\\d+)|((\\d+)(\\.\\d))";
        msg = "�_���͏����_�ȉ�1���܂ł̐��̐�����͂��Ă��������B";
        // ���f�� �f�Ï����̓`�F�b�N
        if (!isValidPattern(pat, 1, shosinSinryoujo, msg)) {
            return false;
        }
        // ���f�� �a�@���̓`�F�b�N
        if (!isValidPattern(pat, 1, shosinHospital, msg)) {
            return false;
        }
        // [ID:0000600][Masahiko Higuchi] 2010/02 del begin �f�Õ�V�P���̕ύX�Ή�
//        // �d�q�����Z���̓`�F�b�N
//        if (!isValidPattern(pat, 1, shosinAddIt, msg)) {
//            return false;
//        }
        // [ID:0000600][Masahiko Higuchi] 2010/02 del end
        // ���t�̎�(�Ö�)���̓`�F�b�N
        if (!isValidPattern(pat, 1, expKs, msg)) {
            return false;
        }
        // �������t��ʌ������̓`�F�b�N
        if (!isValidPattern(pat, 1, expKikMki, msg)) {
            return false;
        }
        // ���t�w�I�������f�����̓`�F�b�N
        if (!isValidPattern(pat, 1, expKikKekk, msg)) {
            return false;
        }
        // ���t���w����(10���ڈȏ�)���̓`�F�b�N
        if (!isValidPattern(pat, 1, expKkkKkk, msg)) {
            return false;
        }
        // �����w�I����(I)���f�����̓`�F�b�N
        if (!isValidPattern(pat, 1, expKkkSkk, msg)) {
            return false;
        }
        // �A����ʕ����萫����ʌ������̓`�F�b�N
        if (!isValidPattern(pat, 1, expNitk, msg)) {
            return false;
        }
        // �P���B�e���̓`�F�b�N
        if (!isValidPattern(pat, 1, expXrayTs, msg)) {
            return false;
        }
        // �ʐ^�f�f(����)���̓`�F�b�N
        if (!isValidPattern(pat, 1, expXraySs, msg)) {
            return false;
        }
        // �t�B����(��p)���̓`�F�b�N
        if (!isValidPattern(pat, 1, expXrayFilm, msg)) {
            return false;
        }
        
        // [ID:0000600][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        // �P���B�e�i�f�W�^���j���̓`�F�b�N
        if (!isValidPattern(pat, 1, expXrayTsDigital, msg)) {
            return false;
        }
        if (!isValidPattern(pat, 1, expXrayDigitalFilm, msg)) {
            return false;
        }
        if (!isValidPattern(pat, 1, expXrayDigitalImageManagement, msg)) {
            return false;
        }
        // [ID:0000600][Masahiko Higuchi] 2010/02 add end

        return true;
    }

    /**
     * �ی��ҋ敪�̏d���`�F�b�N���s��SQL��(WHERE��)��Ԃ��܂��B
     * @return �ی��ҋ敪�̏d���`�F�b�N���s��SQL��(WHERE��)
     */
    private String getInsurerTypeCheckSQL(){
        StringBuffer sb = new StringBuffer();
        switch(insurerTypes.getSelectedIndex()){
        case 1:
            sb.append(" AND(INSURER_TYPE=0 OR INSURER_TYPE=1)");
            break;
        case 2:
            sb.append(" AND(INSURER_TYPE=0 OR INSURER_TYPE=2)");
            break;
        }
        return sb.toString();
    }

    /**
     * �����`�F�b�N(�K�{���x��)
     * 
     * @param pat String
     * @param tabIdx int
     * @param txtFld IkenshoTextField
     * @param msg String
     * @return boolean
     */
    private boolean isValidPattern(String pat, int tabIdx, ACTextField txtFld,
            String msg) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(txtFld.getText());
        if (!matcher.matches()) {
            showErrMessageBox(tabIdx, txtFld, msg);
            return false;
        }
        return true;
    }

    /**
     * ���[�j���O�`�F�b�N
     * 
     * @return valid / true:�L��(OK), false:����(ERR)
     * @throws Exception
     */
    private boolean checkWarning() throws Exception {
        // �ی��Ҕԍ������`�F�b�N
        Pattern pattern = Pattern.compile("[0-9]{6}");
        Matcher matcher = pattern.matcher(insurerNoField.getText());
        if (!matcher.matches()) {
            if (!showWarningMessageBox("�ی��Ҕԍ�������6�����͂���Ă��܂���B\n��낵���ł����H")) {
                return false;
            }
        }

        // �ی��Ҕԍ�1
        if (issInsurerNo.isEnabled()) {
            matcher = pattern.matcher(issInsurerNo.getText());
            if (!matcher.matches()) {
                if (!showWarningMessageBox(issGrp.getText()
                        + "�̕ی��Ҕԍ�������6�����͂���Ă��܂���B\n��낵���ł����H")) {
                    return false;
                }
            }
        }

        // �ی��Ҕԍ�2
        if (sksInsurerNo.isEnabled()) {
            matcher = pattern.matcher(sksInsurerNo.getText());
            if (!matcher.matches()) {
                if (!showWarningMessageBox(sksGrp.getText()
                        + "�̕ی��Ҕԍ�������6�����͂���Ă��܂���B\n��낵���ł����H")) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * �u�񎦊z�ɖ߂��v�{�^������
     * 
     * @param e ActionEvent
     */
    public void rollBack_actionPerformed(ActionEvent e) {
        String msg = "�ӌ����쐬���^�f�@�E������p�_����\n���J�Ȓ񎦊z��ݒ肵�܂��B��낵���ł����H";
        int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK);
        if (result == ACMessageBox.RESULT_OK) {
            try {
                loadMKingakuTensu();
            } catch (Exception ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }
        }
    }

    /**
     * �u�˗����Ɠ���v(�ӌ����쐬��)
     * 
     * @param e ActionEvent
     */
    public void issInsurerSame_actionPerformed(ActionEvent e) {
        issInsurerNm.setText(insurerNmField.getText());
        issInsurerNo.setText(insurerNoField.getText());
    }

    /**
     * �u�˗����Ɠ���v(�f�@�E������p)
     * 
     * @param e ActionEvent
     */
    public void sksInsurerSame_actionPerformed(ActionEvent e) {
        sksInsurerNm.setText(insurerNmField.getText());
        sksInsurerNo.setText(insurerNoField.getText());
    }

    /**
     * �u�����p�^�[���vRadio�I����
     * 
     * @param e ListSelectionEvent
     */
    public void seikyuPattern_valueChanged(ListSelectionEvent e) {
        setseikyuPatternEnabled();
    }

    /**
     * �����p�^�[�����̊e�R���|�[�l���g��Enabled��ݒ肵�܂��B
     */
    public void setseikyuPatternEnabled() {
        switch (seikyuPattern.getSelectedIndex()) {
        case 1: // �u�ӌ����쐬���E������(1��)�v
            issGrp.setText("�ӌ����쐬���^�f�@�E������p������");
            setIssGrpEnabled(true);
            setSksGrpEnabled(false);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add begin �ی��Ҕԍ��I��
            setSeikyuPatternInsurerSelectButtonState(true , false);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            break;

        case 2: // �u�ӌ����쐬��(1��)�E������(1��)�v
            issGrp.setText("�ӌ����쐬��������");
            setIssGrpEnabled(true);
            setSksGrpEnabled(true);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add begin �ی��Ҕԍ��I��
            setSeikyuPatternInsurerSelectButtonState(true , true);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            break;

        case 3: // �u�ӌ����쐬���̂݁v
            issGrp.setText("�ӌ����쐬��������");
            setIssGrpEnabled(true);
            setSksGrpEnabled(false);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add begin �ی��Ҕԍ��I��
            setSeikyuPatternInsurerSelectButtonState(true , false);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            break;

        case 4: // �u�������̂݁v

            // issGrp.setText("�ӌ����쐬��������");
            setIssGrpEnabled(false);
            setSksGrpEnabled(true);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add begin �ی��Ҕԍ��I��
            setSeikyuPatternInsurerSelectButtonState(false , true);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            break;

        default:
            setIssGrpEnabled(false);
            setSksGrpEnabled(false);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add begin �ی��Ҕԍ��I��
            setSeikyuPatternInsurerSelectButtonState(false , false);
            // [ID:0000513][Masahiko Higuchi] 2009/09 add end
            break;
        }
    }

    /**
     * �ӌ����쐬���������Enabled��ݒ肵�܂��B
     * 
     * @param enabled boolean
     */
    public void setIssGrpEnabled(boolean enabled) {
        issGrp.setEnabled(enabled);
        issInsurerNmContainer.setEnabled(enabled);
        issInsurerNm.setEnabled(enabled);
        issInsurerNoContainer.setEnabled(enabled);
        issInsurerNo.setEnabled(enabled);
        issInsurerSame.setEnabled(enabled);
        soukatuhyouGrp.setEnabled(enabled);
        soukatuhyou.setEnabled(enabled);
        setSoukatuhyouPrintEnabled(enabled, soukatuhyou, soukatuhyouPrint);
        meisaiKindGrp.setEnabled(enabled);
        meisaiKind.setEnabled(enabled);
        meisaiKindPrint.setEnabled(enabled);
    }

    /**
     * �f�@�E������p�������Enabled��ݒ肵�܂��B
     * 
     * @param enabled boolean
     */
    public void setSksGrpEnabled(boolean enabled) {
        sksGrp.setEnabled(enabled);
        sksInsurerNmContainer.setEnabled(enabled);
        sksInsurerNm.setEnabled(enabled);
        sksInsurerNoContainer.setEnabled(enabled);
        sksInsurerNo.setEnabled(enabled);
        sksInsurerSame.setEnabled(enabled);
        soukatuhyouGrp2.setEnabled(enabled);
        soukatuhyou2.setEnabled(enabled);
        setSoukatuhyouPrintEnabled(enabled, soukatuhyou2, soukatuhyouPrint2);
        meisaiKindGrp2.setEnabled(enabled);
        meisaiKind2.setEnabled(enabled);
        meisaiKindPrint2.setEnabled(enabled);
    }

    /**
     * �����\�U��������Enabled��ݒ肵�܂��B
     * 
     * @param enabled boolean
     * @param rdo IkenshoRadioButtonGroup
     * @param chk IkenshoIntegerCheckBox
     */
    public void setSoukatuhyouPrintEnabled(boolean enabled,
            ACClearableRadioButtonGroup rdo, ACIntegerCheckBox chk) {
        if (enabled) {
            switch (rdo.getSelectedIndex()) {
            case 1:
                chk.setEnabled(true);
                break;
            case 2:
                chk.setEnabled(false);
                break;
            default:
                chk.setEnabled(false);
                break;
            }
        } else {
            chk.setEnabled(false);
        }
    }

    /**
     * �����\Group�̒l�ύX���̐ݒ���s���܂��B
     * 
     * @param skh �����\RadioButtonGroup
     * @param skhPrint �����\�U������CheckBox
     */
    public void setSoukatuhyouValue(ACClearableRadioButtonGroup skh,
            ACIntegerCheckBox skhPrint) {
        switch (skh.getSelectedIndex()) {
        case 1:
            skhPrint.setEnabled(true);
            skhPrint.setSelected(true);
            break;

        case 2:
            skhPrint.setEnabled(false);
            break;

        default:
            skhPrint.setEnabled(false);
            skhPrint.setSelected(false);
            break;
        }
    }
    
    // [ID:0000513][Masahiko Higuchi] 2009/09 add begin 2009�N�x�Ή�
    /**
     * �ۑ��O�Ó����m�F
     * 
     * @return
     * @throws Exception
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public boolean isValidSave() throws Exception {
        boolean isSkip = false;
        // �ی��Ҕԍ��̓��̓`�F�b�N
        if (isInsurerDbmConnect()) {
            // �����̊m��
            String msgType = "";
            if(!isUpdate) {
                msgType = "�o�^";
            } else {
                msgType = "�X�V";
            }
            // �m�F
            if (!isSkip && insurerNoField.isEnabled()
                    && !getInsurerRelation().isValidInsurer()) {
                if (!showWarningMessageBox("���͂���Ă���ی��Ҕԍ��A�������͕ی��Җ��̂Ɍ�肪����\��������܂��B"
                        + ACConstants.LINE_SEPARATOR + msgType +"���Ă���낵���ł����H")) {
                    getInsurerRelation().checkInsurerNo();
                    getInsurerRelation().checkInsurerName();
                    return false;
                }
                // �x�����ꂽ���X���[�����ꍇ
                isSkip = true;
            }
            
            if (!isSkip && issInsurerNo.isEnabled()
                    && !getInsurerRelationIkensyo().isValidInsurer()) {
                if (!showWarningMessageBox("���͂���Ă���ی��Ҕԍ��A�������͕ی��Җ��̂Ɍ�肪����\��������܂��B"
                        + ACConstants.LINE_SEPARATOR +  msgType + "���Ă���낵���ł����H")) {
                    getInsurerRelationIkensyo().checkInsurerNo();
                    getInsurerRelationIkensyo().checkInsurerName();
                    return false;
                }
                // �x�����ꂽ���X���[�����ꍇ
                isSkip = true;
            }
            
            if (!isSkip && sksInsurerNo.isEnabled()
                    && !getInsurerRelationKensa().isValidInsurer()) {
                if (!showWarningMessageBox("���͂���Ă���ی��Ҕԍ��A�������͕ی��Җ��̂Ɍ�肪����\��������܂��B"
                        + ACConstants.LINE_SEPARATOR +  msgType + "���Ă���낵���ł����H")) {
                    getInsurerRelationKensa().checkInsurerNo();
                    getInsurerRelationKensa().checkInsurerName();
                    return false;
                }
                // �x�����ꂽ���X���[�����ꍇ
                isSkip = true;
            }
            
        }
        
        return true;
    }
    
    /**
     * �ی��ґI���{�^���̏�Ԑ��䏈��
     * 
     * @param issEnabled
     * @param sksEnabled
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public void setSeikyuPatternInsurerSelectButtonState(boolean issEnabled, boolean sksEnabled) {
        // �ڑ���Ԃŉ�ʏ�Ԑ����ύX
        if(isInsurerDbmConnect()) {
            // �ڑ��ł��Ă���ꍇ
            // ���̑��̓`�F�b�N��Ԃɂ�萧��
            insurerSelectButtonIkensyo.setEnabled(issEnabled);
            insurerSelectButtonKensa.setEnabled(sksEnabled);
        } else {
            // �ڑ��ł��Ă��Ȃ��ꍇ
            insurerSelectButton.setEnabled(false);
            insurerSelectButtonIkensyo.setEnabled(false);
            insurerSelectButtonKensa.setEnabled(false);
        }
        
        
    }
    /**
     * �ی��Ҕԍ��}�X�^�f�[�^�[�x�[�X���擾���܂��B
     * 
     * @return
     * @throws Exception
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public IkenshoInsurerBridgeFirebirdDBManager getMasterInsurerDbm() throws Exception {
        if(masterInsurerDbm == null) {
            masterInsurerDbm = new IkenshoInsurerBridgeFirebirdDBManager();
        }
        return masterInsurerDbm;
    }

    /**
     * �ی��Ҕԍ��}�X�^�f�[�^�x�[�X��ݒ肵�܂��B
     * 
     * @param masterInsurerDbm
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public void setMasterInsurerDbm(IkenshoInsurerBridgeFirebirdDBManager masterInsurerDbm) {
        this.masterInsurerDbm = masterInsurerDbm;
    }
    
    /**
     * �ی��҃}�X�^�̐ڑ��ۂ�ԋp���܂��B
     * 
     * @return
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public boolean isInsurerDbmConnect() {
        return insurerDbmConnect;
    }

    /**
     * �ی��҃}�X�^�̐ڑ��ۂ�ݒ肵�܂��B
     * 
     * @param insurerDbmConnect
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public void setInsurerDbmConnect(boolean insurerDbmConnet) {
        this.insurerDbmConnect = insurerDbmConnet;
    }
    
    /**
     * �ی��ҕϊ��N���X��ԋp���܂��B
     * 
     * @return
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public IkenshoInsurerRelation getInsurerRelation() {
        return insurerRelation;
    }

    /**
     * �ی��ҕϊ��N���X��ݒ肵�܂��B
     * 
     * @param insurerRelation
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public void setInsurerRelation(IkenshoInsurerRelation insurerRelation) {
        this.insurerRelation = insurerRelation;
    }
    
    /**
     * �ی��ҕϊ��N���X��ԋp���܂��B
     * 
     * @return
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public IkenshoInsurerRelation getInsurerRelationIkensyo() {
        return insurerRelationIkensyo;
    }

    /**
     * �ی��ҕϊ��N���X��ݒ肵�܂��B
     * 
     * @param insurerRelationIkensyo
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public void setInsurerRelationIkensyo(
            IkenshoInsurerRelation insurerRelationIkensyo) {
        this.insurerRelationIkensyo = insurerRelationIkensyo;
    }

    /**
     * �ی��ҕϊ��N���X��ԋp���܂��B
     * 
     * @return
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public IkenshoInsurerRelation getInsurerRelationKensa() {
        return insurerRelationKensa;
    }

    /**
     * �ی��ҕϊ��N���X��ݒ肵�܂��B
     * 
     * @param insurerRelationKensa
     * @author Masahiko Higuchi
     * @since V3.1.0
     */
    public void setInsurerRelationKensa(IkenshoInsurerRelation insurerRelationKensa) {
        this.insurerRelationKensa = insurerRelationKensa;
    }


}
