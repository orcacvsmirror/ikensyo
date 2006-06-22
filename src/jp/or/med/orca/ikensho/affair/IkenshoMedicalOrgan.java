package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;

/** TODO <HEAD_IKENSYO> */
public class IkenshoMedicalOrgan extends IkenshoAffairContainer implements ACAffairable {
  private ACAffairButtonBar buttons = new ACAffairButtonBar();
  private JTabbedPane tabs = new JTabbedPane();
  private ACAffairButton update = new ACAffairButton();
  private VRPanel baseInfo = new VRPanel();
  private VRPanel billInfo = new VRPanel();
  private VRLabel ikenshoSijishoAbstraction = new VRLabel();
  private VRPanel ikenshoSijishoPanel = new VRPanel();
  private VRLayout baseInfoLayout =  new VRLayout();
  private VRLayout contactPanelLayout =  new VRLayout();
  private VRPanel mobileTELPanel = new VRPanel();
  private VRPanel contactPanel = new VRPanel();
  private ACLabelContainer mobileTELs = new ACLabelContainer();
  private VRPanel notePanel = new VRPanel();
  private ACLabelContainer tels = new ACLabelContainer();
  private ACLabelContainer faxs = new ACLabelContainer();
  private ACLabelContainer addresss = new ACLabelContainer();
  private ACLabelContainer zips = new ACLabelContainer();
  private ACLabelContainer organNames = new ACLabelContainer();
  private ACLabelContainer doctorNames = new ACLabelContainer();
  private ACTextField doctorName = new ACTextField();
  private ACTextField organName = new ACTextField();
  private ACTextField address = new ACTextField();
  private IkenshoZipTextField zipPanel1 = new IkenshoZipTextField();
  private IkenshoTelTextField telPanel1 = new IkenshoTelTextField();
  private IkenshoTelTextField fax = new IkenshoTelTextField();
  private IkenshoTelTextField mobileTEL = new IkenshoTelTextField();
  private VRLabel sijishoAbstraction = new VRLabel();
  private ACLabelContainer emergencyContacts = new ACLabelContainer();
  private ACTextField emergencyContact = new ACTextField();
  private ACLabelContainer absenceContacts = new ACLabelContainer();
  private ACTextField absenceContact = new ACTextField();
  private ACLabelContainer notes = new ACLabelContainer();
  private ACLabelContainer mainDoctors = new ACLabelContainer();
  private ACIntegerCheckBox mainDoctor = new ACIntegerCheckBox();
  private VRPanel mainDoctorAbstractions = new VRPanel();
  private VRLabel mainDoctorAbstraction1 = new VRLabel();
  private VRLabel mainDoctorAbstraction2 = new VRLabel();
  private ACRowMaximumableTextArea note = new ACRowMaximumableTextArea();
  private JScrollPane noteScroll = new JScrollPane();
  private VRLabel billAbstraction = new VRLabel();
  private ACGroupBox categoryGroup = new ACGroupBox();
  private ACClearableRadioButtonGroup category = new ACClearableRadioButtonGroup();
  private ACLabelContainer openerNames = new ACLabelContainer();
  private ACTextField openerName = new ACTextField();
  private ACTextField bankName = new ACTextField();
  private ACLabelContainer bankNames = new ACLabelContainer();
  private VRPanel categoryPanel = new VRPanel();
  private ACLabelContainer bankOfficeNames = new ACLabelContainer();
  private ACTextField bankOfficeName = new ACTextField();
  private ACLabelContainer bankAccounts = new ACLabelContainer();
  private ACTextField bankAccount = new ACTextField();
  private ACLabelContainer bankUsrerNames = new ACLabelContainer();
  private ACTextField bankUsrerName = new ACTextField();
  private ACLabelContainer bankTypes = new ACLabelContainer();
  private ACLabelContainer doctorNos = new ACLabelContainer();
  private ACTextField doctorNo = new ACTextField();
  private ACClearableRadioButtonGroup bankType = new ACClearableRadioButtonGroup();
  private ACParentHesesPanelContainer doctorNoHeses = new ACParentHesesPanelContainer();
  private ACGroupBox officeGroup = new ACGroupBox();
  private ACTable offices = new ACTable();
  private VRPanel officeButtons = new VRPanel();
  private BorderLayout borderLayout1 = new BorderLayout();
  private ACButton officeDelete = new ACButton();
  private ACButton officeEdit = new ACButton();
  private ACButton officeInsert = new ACButton();
  private VRLayout notePanelLayout = new VRLayout();
  private VRLayout billInfoLayout = new VRLayout();
  private VRLabel doctorNoSpacer = new VRLabel();
  private VRLabel bankTypeSpacer = new VRLabel();
  private VRLabel bankAccountSpacer = new VRLabel();
  private void jbInit() throws Exception {
    category.setModel(new VRListModelAdapter(new
                                            VRArrayList(Arrays.asList(new
    String[] {"診療所", "病院", "その他の施設"}))));
bankType.setModel(new VRListModelAdapter(new
                                            VRArrayList(Arrays.asList(new
    String[] {"普通", "当座"}))));


    baseInfoLayout.setFitVLast(true);
    baseInfoLayout.setFitHLast(true);
    baseInfo.setLayout(baseInfoLayout);
    ikenshoSijishoPanel.setLayout(new VRLayout());
    mobileTELPanel.setLayout(new BorderLayout());
    mainDoctorAbstractions.setLayout(new BorderLayout());
    billInfoLayout.setFitVLast(true);
    billInfoLayout.setFitHLast(true);
    billInfo.setLayout(billInfoLayout);
    categoryPanel.setLayout(new VRLayout());
    officeButtons.setLayout(new VRLayout());
    notePanelLayout.setFitHLast(true);
    notePanel.setLayout(notePanelLayout);



    contactPanelLayout.setFitHLast(true);
    contactPanel.setLayout(contactPanelLayout);

    buttons.setTitle("医療機関情報詳細");
    update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);
    update.setMnemonic('S');
    update.setText("登録(S)");
    update.setToolTipText("現在の内容を登録します。");
    ikenshoSijishoAbstraction.setHorizontalAlignment(SwingConstants.RIGHT);
    ikenshoSijishoAbstraction.setText("↓「主治医意見書」「訪問看護指示書」に印刷される項目");
    ikenshoSijishoPanel.setBackground(IkenshoConstants.COLOR_RANGE_PANEL_BACKGROUND);
    ikenshoSijishoPanel.setBorder(BorderFactory.createEtchedBorder());
    contactPanel.setBackground(IkenshoConstants.COLOR_RANGE_PANEL_BACKGROUND);
    contactPanel.setBorder(BorderFactory.createEtchedBorder());
    mobileTELs.setText("連絡先(携帯)");
    tels.setText("連絡先(TEL)");
    faxs.setText("連絡先(FAX)");
    addresss.setText("所在地");
    zips.setText("郵便番号");
    organNames.setText("医療機関名");
    doctorNames.setText("医師氏名");
    doctorName.setIMEMode(InputSubset.KANJI);
    organName.setIMEMode(InputSubset.KANJI);
    address.setIMEMode(InputSubset.KANJI);
    openerName.setIMEMode(InputSubset.KANJI);
    emergencyContact.setIMEMode(InputSubset.KANJI);
    absenceContact.setIMEMode(InputSubset.KANJI);
    note.setIMEMode(InputSubset.KANJI);
    bankName.setIMEMode(InputSubset.KANJI);
    bankOfficeName.setIMEMode(InputSubset.KANJI);
    bankUsrerName.setIMEMode(InputSubset.KANJI);
    doctorName.setColumns(15);
    doctorName.setBindPath("DR_NM");
    doctorName.setMaxLength(15);
    organName.setMaxLength(30);
    organName.setColumns(30);
    organName.setBindPath("MI_NM");
    address.setMaxLength(50);
    address.setColumns(50);
    address.setBindPath("MI_ADDRESS");
    fax.setBindPathArea("MI_FAX1");
    fax.setBindPathNumber("MI_FAX2");
    telPanel1.setBindPathArea("MI_TEL1");
    telPanel1.setBindPathNumber("MI_TEL2");
    zipPanel1.setAddressTextField(address);
    zipPanel1.setBindPath("MI_POST_CD");
    mobileTEL.setBindPathArea("MI_CAL_TEL1");
    mobileTEL.setBindPathNumber("MI_CAL_TEL2");
    mainDoctorAbstraction1.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
    mainDoctorAbstraction2.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
    sijishoAbstraction.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
    sijishoAbstraction.setText("↓「訪問看護指示書」に印刷される項目");
    sijishoAbstraction.setHorizontalAlignment(SwingConstants.RIGHT);
    emergencyContacts.setText("緊急時連絡先");
    emergencyContact.setMaxLength(40);
    emergencyContact.setColumns(40);
    emergencyContact.setBindPath("KINKYU_RENRAKU");
    absenceContacts.setText("不在時対応法");
    absenceContact.setMaxLength(40);
    absenceContact.setColumns(40);
    absenceContact.setBindPath("FUZAIJI_TAIOU");
    notes.setAlignmentY((float) 0.47058824);
    notes.setText("備考");
    notes.setColumns(20);
    mainDoctor.setBindPath("MI_DEFAULT");
    mainDoctorAbstraction1.setText("この医師・医療機関が主として使用します。");
    mainDoctorAbstraction2.setText("（チェックすると「主治医意見書」「訪問看護指示書」作成時に予め選択されます。）");
    note.setRows(5);
    note.setMaxLength(170);
    mobileTELPanel.setMaximumSize(new Dimension(2147483647, 2147483647));
    billAbstraction.setText("★自治体(保険者)によって必要な項目は異なります。");
    categoryGroup.setText("診療所・病院区分");
    category.setBindPath("MI_KBN");
    openerNames.setText("開設者氏名");
    openerName.setColumns(15);
    openerName.setBindPath("KAISETUSHA_NM");
    openerName.setMaxLength(15);
    bankName.setMaxLength(25);
    bankName.setBindPath("BANK_NM");
    bankName.setColumns(25);
    bankNames.setText("振込先金融機関名");
    bankOfficeNames.setText("振込先金融機関支店名");
    bankOfficeName.setMaxLength(25);
    bankOfficeName.setBindPath("BANK_SITEN_NM");
    bankOfficeName.setColumns(25);
    bankAccounts.setText("振込先口座番号");
    bankAccount.setMaxLength(10);
    bankAccount.setBindPath("BANK_KOUZA_NO");
    bankAccount.setColumns(10);
    bankUsrerNames.setText("振込先名義人");
    bankUsrerName.setMaxLength(15);
    bankUsrerName.setBindPath("FURIKOMI_MEIGI");
    bankUsrerName.setColumns(15);
    bankTypes.setText("振込先口座種類");
    doctorNos.setText("");
    doctorNo.setMaxLength(10);
    doctorNo.setBindPath("DR_NO");
    doctorNo.setColumns(10);
    bankType.setBindPath("BANK_KOUZA_KIND");
    doctorNoHeses.setBeginText("（医師番号");
    officeGroup.setText("事業所番号");
    officeGroup.setLayout(borderLayout1);
    officeDelete.setHorizontalTextPosition(SwingConstants.TRAILING);
    officeDelete.setMnemonic('D');
    officeDelete.setText("削除(D)");
    officeEdit.setMnemonic('E');
    officeEdit.setText("編集(E)");
    officeInsert.setMnemonic('T');
    officeInsert.setText("登録(T)");
    doctorNos.add(doctorNoHeses, null);
    doctorNoHeses.add(doctorNo, null);
    contactPanel.add(emergencyContacts, VRLayout.FLOW_INSETLINE_RETURN);
    emergencyContacts.add(emergencyContact, null);
    contactPanel.add(absenceContacts, VRLayout.FLOW_INSETLINE_RETURN);
    absenceContacts.add(absenceContact, null);
    mobileTELPanel.add(mobileTELs, BorderLayout.WEST);
    mobileTELs.add(mobileTEL, null);
    mobileTELPanel.add(sijishoAbstraction, BorderLayout.CENTER);
    this.add(buttons,  VRLayout.NORTH);
    this.add(tabs, VRLayout.CLIENT);
    tabs.add(baseInfo, "基本情報");
    tabs.add(billInfo, "請求書関連情報");
    billAbstraction.setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
    billInfo.add(categoryPanel, VRLayout.NORTH);
    categoryPanel.add(billAbstraction, VRLayout.FLOW_INSETLINE_RETURN);
    categoryPanel.add(openerNames, VRLayout.FLOW_INSETLINE_RETURN);
    categoryPanel.add(categoryGroup, VRLayout.EAST);
    categoryPanel.add(bankNames, VRLayout.FLOW_INSETLINE_RETURN);
    categoryGroup.add(category, null);
    buttons.add(update, VRLayout.EAST);
    baseInfo.add(ikenshoSijishoAbstraction, VRLayout.NORTH);
    baseInfo.add(ikenshoSijishoPanel, VRLayout.NORTH);
    ikenshoSijishoPanel.add(doctorNames, VRLayout.FLOW_INSETLINE_RETURN);
    doctorNames.add(doctorName, null);
    ikenshoSijishoPanel.add(organNames, VRLayout.FLOW_INSETLINE_RETURN);
    ikenshoSijishoPanel.add(zips, VRLayout.FLOW_INSETLINE_RETURN);
    zips.add(zipPanel1, null);
    ikenshoSijishoPanel.add(addresss, VRLayout.FLOW_INSETLINE_RETURN);
    ikenshoSijishoPanel.add(tels, VRLayout.FLOW_INSETLINE);
    tels.add(telPanel1, null);
    ikenshoSijishoPanel.add(faxs, VRLayout.FLOW_INSETLINE);
    faxs.add(fax, null);
    baseInfo.add(mobileTELPanel, VRLayout.NORTH);
    baseInfo.add(contactPanel, VRLayout.NORTH);
    baseInfo.add(notePanel, VRLayout.NORTH);
    notePanel.add(notes, VRLayout.FLOW_INSETLINE_RETURN);
    notePanel.add(mainDoctors, VRLayout.FLOW_INSETLINE_RETURN);
    mainDoctors.add(mainDoctor, null);
    mainDoctors.add(mainDoctorAbstractions, null);
    mainDoctorAbstractions.add(mainDoctorAbstraction1,  BorderLayout.NORTH);
    mainDoctorAbstractions.add(mainDoctorAbstraction2,  BorderLayout.SOUTH);
    organNames.add(organName, null);
    addresss.add(address, null);
    notes.add(noteScroll, null);
    noteScroll.getViewport().add(note, null);
    openerNames.add(openerName, null);
    bankNames.add(bankName, null);
    bankOfficeNames.add(bankOfficeName, null);
    bankAccounts.add(bankAccount, null);
    bankUsrerNames.add(bankUsrerName, null);
    bankTypes.add(bankType, null);
    billInfo.add(bankOfficeNames, VRLayout.FLOW_INSETLINE_RETURN);
    billInfo.add(bankAccounts, VRLayout.FLOW_INSETLINE);
    billInfo.add(bankAccountSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    billInfo.add(bankTypes, VRLayout.FLOW_INSETLINE);
    billInfo.add(bankTypeSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    billInfo.add(bankUsrerNames, VRLayout.FLOW_INSETLINE);
    billInfo.add(doctorNos, VRLayout.FLOW_INSETLINE);
    billInfo.add(doctorNoSpacer, VRLayout.FLOW_INSETLINE_RETURN);
    billInfo.add(officeGroup, VRLayout.FLOW_RETURN);
    officeGroup.add(officeButtons,  BorderLayout.EAST);
    officeButtons.add(officeInsert, VRLayout.FLOW_RETURN);
    officeButtons.add(officeEdit, VRLayout.FLOW_RETURN);
    officeButtons.add(officeDelete, VRLayout.FLOW_RETURN);
    officeGroup.add(offices, BorderLayout.CENTER);
    ikenshoSijishoAbstraction.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
    note.setBindPath("BIKOU");



  }

  /**
   * コンストラクタです。
   */
  public IkenshoMedicalOrgan() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Component getFirstFocusComponent() {
    return doctorName;
  }

  public boolean canBack(VRMap parameters) {


    return true;
  }

  public ACAffairButtonBar getButtonBar() {
    return buttons;
  }


  public void initAffair(ACAffairInfo affair) {
    offices.setColumnModel(new VRTableColumnModel(
        new VRTableColumn[] {
        new VRTableColumn(0, 100, "保険者番号"),
        new VRTableColumn(1, 380, "保険者名称"),
        new VRTableColumn(2, 100, "事業所番号"),
    })
        );

  }

}
