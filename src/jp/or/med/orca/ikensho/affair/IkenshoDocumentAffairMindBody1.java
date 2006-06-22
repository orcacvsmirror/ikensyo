package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHintButton;
import jp.or.med.orca.ikensho.component.IkenshoHintContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoHashableComboFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoDocumentAffairMindBody1 extends IkenshoTabbableChildAffairContainer {
  protected VRArrayList renkeiDoctors;

  private VRLabel mindBody1Title = new VRLabel();
  private ACGroupBox mindBody1JiritsuGroup = new ACGroupBox();
  private ACGroupBox mindBody1RikaiKiokuGroup = new ACGroupBox();
  private ACGroupBox mindBody1MondaiGroup = new ACGroupBox();
  private ACGroupBox mindBody1ShinkeiGroup = new ACGroupBox();
  private IkenshoHintButton mindBody1SyougaiJiritsuHelp = new
      IkenshoHintButton();
  private IkenshoHintButton mindBody1ChihouJiritsuHelp = new
      IkenshoHintButton();

  private VRListModelAdapter existEmptyListModel = new VRListModelAdapter(new
      VRArrayList(Arrays.asList(new String[] {"�L", "��"})));

  private VRLayout mindBody1Layout = new VRLayout();
  private IkenshoHintButton mindBody1TankiKiokuHelp = new IkenshoHintButton();
  private ACIntegerCheckBox mindBody1MondaiHushimatsu = new
      ACIntegerCheckBox();
  private ACButton mindBody1JiritsuClear = new ACButton();
  private ACIntegerCheckBox mindBody1MondaiBoukou = new
      ACIntegerCheckBox();
  private ACClearableRadioButtonGroup mindBody1HasShinkeiProJyusin = new
      ACClearableRadioButtonGroup();
  private ACComboBox mindBody1Shinkei;
  private ACClearableRadioButtonGroup mindBody1Syokuji = new
      ACClearableRadioButtonGroup();
  private IkenshoHintButton mindBody1NinchiHelp = new IkenshoHintButton();
  private ACClearableRadioButtonGroup mindBody1Ninchi = new ACClearableRadioButtonGroup();
  private ACLabelContainer mindBody1NinchiPanel = new ACLabelContainer();
  private VRLayout mindBody1RikaiKiokuLayout = new VRLayout();
  private ACClearableRadioButtonGroup mindBody1SyougaiJiritsu = new
      ACClearableRadioButtonGroup();
  private ACLabelContainer mindBody1DentatsuPanel = new ACLabelContainer();
  private VRLabel mindBody1ShinkeiProJyusinTail = new VRLabel();
  private ACClearableRadioButtonGroup mindBody1TankiKioku = new
      ACClearableRadioButtonGroup();
  private VRLayout mindBody1JiritsuGroupLayout = new VRLayout();
  private ACLabelContainer mindBody1SyougaiJiritsuPanel = new ACLabelContainer();
  private IkenshoHintButton mindBody1SyokujiHelp = new IkenshoHintButton();
  private ACValueArrayRadioButtonGroup mindBody1HasMondai = new
      ACValueArrayRadioButtonGroup();
  private ACIntegerCheckBox mindBody1MondaiGyakuten = new
      ACIntegerCheckBox();
  private IkenshoHintButton mindBody1DentatsuHelp = new IkenshoHintButton();
  private ACIntegerCheckBox mindBody1MondaiSeiteki = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody1MondaiGensi = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody1MondaiOther = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody1TankiKiokuPanel = new ACLabelContainer();
  private ACValueArrayRadioButtonGroup mindBody1HasShinkei;
  private ACIntegerCheckBox mindBody1MondaiBougen = new
      ACIntegerCheckBox();
  private VRLayout mindBody1MondaiOtherLayout = new VRLayout();
  private ACClearableRadioButtonGroup mindBody1ChihouJiritsu = new
      ACClearableRadioButtonGroup();
  private ACLabelContainer mindBody1ShinkeiProJyusinPanel = new
      ACLabelContainer();
  private ACIntegerCheckBox mindBody1MondaiTeikou = new
      ACIntegerCheckBox();
  private VRPanel mindBody1MondaiPanel = new VRPanel();
  private ACIntegerCheckBox mindBody1MondaiMousou = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody1MondaiIsyoku = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody1SyokujiPanel = new ACLabelContainer();
  private ACClearableRadioButtonGroup mindBody1Dentatsu = new
      ACClearableRadioButtonGroup();
  private ACIntegerCheckBox mindBody1MondaiFuketsu = new
      ACIntegerCheckBox();
  private VRPanel mindBody1MondaiOtherPanel = new VRPanel();
  private ACButton mindBody1RikaiKiokuClear = new ACButton();
  private ACLabelContainer mindBody1ChihouJiritsuPanel = new ACLabelContainer();
  private ACTextField mindBody1MondaiOtherVal = new ACTextField();
  private ACComboBox mindBody1ShinkeiProJyusin = new ACComboBox();
  private ACIntegerCheckBox mindBody1MondaiHaikai = new
      ACIntegerCheckBox();
  private ACParentHesesPanelContainer mindBody1MondaiOtherValHeses = new
      ACParentHesesPanelContainer();
  private ACGroupBox hint = new ACGroupBox();
  private IkenshoHintContainer ikenshoHintPanel1 = new IkenshoHintContainer();
  private VRPanel mindBody1SyougaiJiritsuHelpPanel = new VRPanel();
  private VRPanel mindBody1ChihouJiritsuHelpPanel = new VRPanel();
  private VRPanel mindBody1TankiKiokuHelpPanel = new VRPanel();
  private VRPanel mindBody1NinchiHelpPanel = new VRPanel();
  private VRPanel mindBody1DentatsuHelpPanel = new VRPanel();
  private VRPanel mindBody1SyokujiHelpPanel = new VRPanel();

  /**
   * override���ė����L���Ɋւ���N���A�{�^���̊i�[��R���e�i��Ԃ��܂��B
   * @return �����L���Ɋւ���N���A�{�^���̊i�[��R���e�i
   */
  protected Container getRikaiKiokuClearButtonContainer(){
    return mindBody1SyokujiPanel;
  }

  /**
   * �^�C�g���\�����x����Ԃ��܂��B
   * @return �^�C�g���\�����x��
   */
  protected VRLabel getTitle(){
    return mindBody1Title;
  }

  /**
   * ���퐶�������x�O���[�v��Ԃ��܂��B
   * @return ���퐶�������x�O���[�v
   */
  protected ACGroupBox getJiritsuGroup(){
    return mindBody1JiritsuGroup;
  }
  /**
   * ��������ыL���O���[�v��Ԃ��܂��B
   * @return ��������ыL���O���[�v
   */
  protected ACGroupBox getRikaiKiokuGroup(){
    return mindBody1RikaiKiokuGroup;
  }
  /**
   * ���s���O���[�v��Ԃ��܂��B
   * @return ���s���O���[�v
   */
  protected ACGroupBox getMondaiGroup(){
    return mindBody1MondaiGroup;
  }
  /**
   * ���_�E�_�o�Ǐ�O���[�v��Ԃ��܂��B
   * @return ���_�E�_�o�Ǐ�O���[�v
   */
  protected ACGroupBox getShinkeiGroup(){
    return mindBody1ShinkeiGroup;
  }
  /**
   * ��Q�V�l�̓��퐶�������x�w���v�{�^����Ԃ��܂��B
   * @return ��Q�V�l�̓��퐶�������x�w���v�{�^��
   */
  protected IkenshoHintButton getSyougaiJiritsuHelp(){
    return mindBody1SyougaiJiritsuHelp;
  }
  /**
   * �s�𐫘V�l�̓��퐶�������x�w���v�{�^����Ԃ��܂��B
   * @return  �s�𐫘V�l�̓��퐶�������x�w���v�{�^��
   */
  protected IkenshoHintButton getChihouJiritsuHelp(){
    return mindBody1ChihouJiritsuHelp;
  }
  /**
   * ��������ыL��-�H����Ԃ��܂��B
   * @return ��������ыL��-�H��
   */
  protected ACLabelContainer getRikaiKiokuSyokuji(){
    return mindBody1SyokujiPanel;
  }
  /**
   * ���퐶�������x-��Q�V�l��Ԃ��܂��B
   * @return ���퐶�������x-��Q�V�l
   */
  protected ACLabelContainer getSyougaiRoujinJiritsu(){
    return mindBody1SyougaiJiritsuPanel;
  }
  /**
   * ���퐶�������x-�s�𐫘V�l��Ԃ��܂��B
   * @return ���퐶�������x-�s�𐫘V�l
   */
  protected ACLabelContainer getChihouRoujinJiritsu(){
    return mindBody1ChihouJiritsuPanel;
  }

  /**
   * ���s���̗L����Ԃ��܂��B
   * @return ���s���̗L��
   */
  protected ACValueArrayRadioButtonGroup getMindBody1HasMondai(){
    return mindBody1HasMondai;
  }

  /**
   * ���̑��̐��_�E�_�o�Ǐ�̗L����Ԃ��܂��B
   * @return ���̑��̐��_�E�_�o�Ǐ�̗L��
   */
  protected ACValueArrayRadioButtonGroup getMindBody1HasShinkei(){
    if(mindBody1HasShinkei==null){
      mindBody1HasShinkei = new ACValueArrayRadioButtonGroup();
    }
    return mindBody1HasShinkei;
  }

  /**
   * override���ė����L���̓��͂��N���A����N���A�{�^���̒ǉ��������`���܂��B
   */
  protected void addRikaiKiokuClearButton(){
    mindBody1SyokujiHelpPanel.add(mindBody1RikaiKiokuClear, null);
  }

  /**
   * �����L��-�ӎv�`�B�w���v�p�l����Ԃ��܂��B
   * @return �����L��-�ӎv�`�B�w���v�p�l��
   */
  protected VRPanel getRikaiKiokuDentatsuHelpPanel(){
    return mindBody1DentatsuHelpPanel;
  }
  /**
   * �����L��-�N���A�{�^����Ԃ��܂��B
   * @return �����L��-�N���A�{�^��
   */
  protected ACButton getRikaiKiokuClearButton(){
    return mindBody1RikaiKiokuClear;
  }

  /**
   * override���Đ����擾�̂��߂̖@�����Ή��敪��Ԃ��܂��B
   * @return �����擾�̂��߂̖@�����Ή��敪
   */
  protected int getFomratKubun(){
    return 0;
  }

  /**
   * ���̑��̐��_�E�_�o�Ǐ�R���{��Ԃ��܂��B
   * @return ���̑��̐��_�E�_�o�Ǐ�R���{
   */
  protected ACComboBox getMindBody1Shinkei(){
    if(mindBody1Shinkei==null){
      mindBody1Shinkei = new ACComboBox();
    }
    return mindBody1Shinkei;
  }


  /**
   * override���Ă��̑��̐��_�_�o�Ǐ�̗L�����W�I�O���[�v�̒ǉ��������`���܂��B
   */
  protected void addMindBody1HasShinkei(){
    getMindBody1HasShinkei().add(getMindBody1Shinkei(), null, 1);
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    applyPoolTeikeibun(getMindBody1Shinkei(), IkenshoCommon.TEIKEI_MIND_SICK_NAME);

    IkenshoCommon.setHintButtons(dbm, new String[] {"1", "2", "3", "4", "5",
                                 "6", }
                                 , getFomratKubun(), new IkenshoHintButton[] {
      mindBody1SyougaiJiritsuHelp, mindBody1ChihouJiritsuHelp,
          mindBody1TankiKiokuHelp, mindBody1NinchiHelp, mindBody1DentatsuHelp,
          mindBody1SyokujiHelp
    });

    StringBuffer sb = new StringBuffer();
    sb.append("SELECT");
    sb.append(" *");
    sb.append(" FROM");
    sb.append(" RENKEII");
    sb.append(" ORDER BY");
    sb.append(" RENKEII_CD");
    renkeiDoctors = (VRArrayList) dbm.executeQuery(sb.toString());
    mindBody1ShinkeiProJyusin.setFormat(new IkenshoHashableComboFormat(renkeiDoctors, "SINRYOUKA"));
    IkenshoCommon.applyComboModel(mindBody1ShinkeiProJyusin, renkeiDoctors);


  }

  public VRMap createSourceInnerBindComponent() {
    VRMap map = super.createSourceInnerBindComponent();
    map.setData("SEISIN", new Integer(2));

    return map;
  }

  protected void applySourceInnerBindComponent() throws Exception {
    super.applySourceInnerBindComponent();

    VRBindPathParser.set("SENMONI_NM", getMasterSource(),
                         mindBody1ShinkeiProJyusin.getEditor().getItem());

  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    if (mindBody1MondaiGensi.isSelected() ||
        mindBody1MondaiMousou.isSelected() ||
        mindBody1MondaiGyakuten.isSelected() ||
        mindBody1MondaiBougen.isSelected() ||
        mindBody1MondaiBoukou.isSelected() ||
        mindBody1MondaiTeikou.isSelected() ||
        mindBody1MondaiHaikai.isSelected() ||
        mindBody1MondaiHushimatsu.isSelected() ||
        mindBody1MondaiFuketsu.isSelected() ||
        mindBody1MondaiIsyoku.isSelected() ||
        mindBody1MondaiSeiteki.isSelected() ||
        mindBody1MondaiOther.isSelected()) {
      mindBody1HasMondai.setSelectedIndex(1);
    }else{
      mindBody1HasMondai.setSelectedIndex(2);
    }

    if(mindBody1HasShinkei.getSelectedIndex()==1){
      mindBody1Shinkei.setEnabled (true);
      mindBody1HasShinkeiProJyusin.setEnabled(true);
    }else{
      mindBody1Shinkei.setEnabled (false);
      mindBody1HasShinkeiProJyusin.setEnabled(false);
    }

//    mindBody1ShinkeiProJyusin.getEditor().setItem(senmoniName.getText());
    if( IkenshoCommon.isNullText(mindBody1ShinkeiProJyusin.getEditor().getItem())){
      if(mindBody1HasShinkeiProJyusin.isEnabled()){
        mindBody1HasShinkeiProJyusin.setSelectedIndex(2);
      }else{
        mindBody1HasShinkeiProJyusin.setSelectedIndex(0);
      }
    }else{
      mindBody1HasShinkeiProJyusin.setSelectedIndex(1);
    }


  }

  /**
   * override���Ė��s���O���[�v������킷�^�C�g����Ԃ��܂��B
   * @return ���s���O���[�v������킷�^�C�g��
   */
  protected String getProblemActionCaption(){
    return "���s��";
  }

  public boolean noControlError() {

    //�G���[�`�F�b�N

    if (mindBody1HasMondai.getSelectedIndex() == 1) {
      if (mindBody1MondaiOther.isSelected()) {
        if (IkenshoCommon.isNullText(mindBody1MondaiOtherVal.getText())) {
          ACMessageBox.showExclamation("�u"+getProblemActionCaption()+"�i���̑��j�v�Ŗ��L��������܂��B");
          mindBody1MondaiOther.requestFocus();
          return false;
        }
      }else{

        boolean check = false;
        int end = mindBody1MondaiPanel.getComponentCount();
        for (int i = 0; i < end; i++) {
          Component comp = mindBody1MondaiPanel.getComponent(i);
          if (comp instanceof ACIntegerCheckBox) {
            check = ( (ACIntegerCheckBox) comp).isSelected();
            if (check) {
              break;
            }
          }
        }
        if (!check) {
          ACMessageBox.showExclamation("�u"+getProblemActionCaption()+"�v�Ŗ��L��������܂��B");
          mindBody1HasMondai.requestChildFocus();
          return false;
        }
      }

    }

    if (getMindBody1HasShinkei().getSelectedIndex() == 1) {
      if (IkenshoCommon.isNullText(getMindBody1Shinkei().getEditor().getItem())) {
        ACMessageBox.showExclamation("�u���_�E�_�o�Ǐ�v�Ŗ��L��������܂��B");
        getMindBody1HasShinkei().requestChildFocus();
        return false;
      }
      if (mindBody1HasShinkeiProJyusin.getSelectedIndex() == 1) {
        if (IkenshoCommon.isNullText(mindBody1ShinkeiProJyusin.getEditor().getItem())) {
          ACMessageBox.showExclamation("�u����v�Ŗ��L��������܂��B");
          mindBody1HasShinkeiProJyusin.requestChildFocus();
          return false;
        }
      }
    }

    return true;
  }

  /**
   * �q���g�{�^���ƘA�����Ė����ɂȂ�R���g���[���W����ݒ肵�܂��B
   *
   * @param components �q���g�{�^���ƘA�����Ė����ɂȂ�R���g���[���W��
   * @throws Exception ������O
   */
  public void setFollowDisabledComponents(JComponent[] components) {
    mindBody1SyougaiJiritsuHelp.setFollowDisabledComponents(components);
    mindBody1ChihouJiritsuHelp.setFollowDisabledComponents(components);
    mindBody1TankiKiokuHelp.setFollowDisabledComponents(components);
    mindBody1NinchiHelp.setFollowDisabledComponents(components);
    mindBody1DentatsuHelp.setFollowDisabledComponents(components);
    mindBody1SyokujiHelp.setFollowDisabledComponents(components);
  }

  public IkenshoDocumentAffairMindBody1() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    hint.setVisible(false);

    mindBody1ShinkeiProJyusin.setRenderer(new ACBindListCellRenderer(
        "SINRYOUKA"));

    getMindBody1HasShinkei().addListSelectionListener(new ListSelectionListener(){
      public void valueChanged(ListSelectionEvent e) {
        if(e.getValueIsAdjusting()){
          return;
        }
        int select = getMindBody1HasShinkei().getSelectedIndex();
//        if( (e.getFirstIndex()<0)||(e.getFirstIndex()>=mindBody1HasShinkei.getValues().length)){
//          select = 0;
//        }else{
//          select = mindBody1HasShinkei.getValues()[e.getFirstIndex()];
//        }
        switch(select){
//        switch( e.getFirstIndex()){
//          case 0:
          case 1:
            mindBody1HasShinkeiProJyusin.setSelectedIndex(2);
            getMindBody1Shinkei().setEnabled(true);
            mindBody1HasShinkeiProJyusin.setEnabled(true);
            break;
          case 2:
//          case 1:
            mindBody1HasShinkeiProJyusin.setSelectedIndex(0);
            getMindBody1Shinkei().setEnabled(false);
            mindBody1HasShinkeiProJyusin.setEnabled(false);
            mindBody1ShinkeiProJyusin.setEnabled(false);
            break;
          default:
            getMindBody1Shinkei().setEnabled(false);
            mindBody1HasShinkeiProJyusin.setEnabled(false);
            mindBody1ShinkeiProJyusin.setEnabled(false);
        }
      }

    });

    mindBody1TankiKiokuHelp.setFollowHideComponents(new JComponent[] {
        mindBody1JiritsuGroup, mindBody1MondaiGroup, mindBody1ShinkeiGroup});
    mindBody1NinchiHelp.setFollowHideComponents(new JComponent[] {
                                                mindBody1JiritsuGroup,
                                                mindBody1MondaiGroup,
                                                mindBody1ShinkeiGroup});
    mindBody1DentatsuHelp.setFollowHideComponents(new JComponent[] {
                                                  mindBody1JiritsuGroup,
                                                  mindBody1MondaiGroup,
                                                  mindBody1ShinkeiGroup});
    mindBody1SyokujiHelp.setFollowHideComponents(new JComponent[] {
                                                 mindBody1JiritsuGroup,
                                                 mindBody1MondaiGroup,
                                                 mindBody1ShinkeiGroup});


    mindBody1HasShinkeiProJyusin.addListSelectionListener(new
        ACFollowDisableSelectionListener(new JComponent[] {
                                              mindBody1ShinkeiProJyusin}));
    mindBody1MondaiOther.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {mindBody1MondaiOtherVal, mindBody1MondaiOtherValHeses}));

    mindBody1JiritsuClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          getMindBody1SyougaiJiritsu().setSelectedIndex(getMindBody1SyougaiJiritsu().getNoSelectIndex());
        getMindBody1ChihouJiritsu().setSelectedIndex(getMindBody1ChihouJiritsu().
                                                getNoSelectIndex());
      }
    });
    mindBody1RikaiKiokuClear.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        mindBody1TankiKioku.setSelectedIndex(mindBody1TankiKioku.
                                             getNoSelectIndex());
        mindBody1Ninchi.setSelectedIndex(mindBody1Ninchi.getNoSelectIndex());
        mindBody1Dentatsu.setSelectedIndex(mindBody1Dentatsu.getNoSelectIndex());
        mindBody1Syokuji.setSelectedIndex(mindBody1Syokuji.getNoSelectIndex());
      }
    });
    mindBody1HasMondai.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
          return;
        }
        boolean enabled = mindBody1HasMondai.getSelectedIndex()==1;
//        if((e.getFirstIndex()<0) ||(e.getFirstIndex()>=mindBody1HasMondai.getValues().length)){
//          enabled = false;
//        }else{
//          enabled = mindBody1HasMondai.getValues()[e.getFirstIndex()] == 1;
//        }

//        boolean enabled = e.getFirstIndex() == 0;
        mindBody1MondaiGensi.setEnabled(enabled);
        mindBody1MondaiMousou.setEnabled(enabled);
        mindBody1MondaiGyakuten.setEnabled(enabled);
        mindBody1MondaiBougen.setEnabled(enabled);
        mindBody1MondaiBoukou.setEnabled(enabled);
        mindBody1MondaiTeikou.setEnabled(enabled);
        mindBody1MondaiHaikai.setEnabled(enabled);
        mindBody1MondaiHushimatsu.setEnabled(enabled);
        mindBody1MondaiFuketsu.setEnabled(enabled);
        mindBody1MondaiIsyoku.setEnabled(enabled);
        mindBody1MondaiSeiteki.setEnabled(enabled);
        mindBody1MondaiOther.setEnabled(enabled);
        if (mindBody1MondaiOther.isSelected()) {
          mindBody1MondaiOtherVal.setEnabled(enabled);
          mindBody1MondaiOtherValHeses.setEnabled(enabled);
        }
      }
    });

    mindBody1SyougaiJiritsuHelp.setFollowPressedButtons(new IkenshoHintButton[] {
        mindBody1ChihouJiritsuHelp});
    mindBody1ChihouJiritsuHelp.setFollowPressedButtons(new IkenshoHintButton[] {
        mindBody1SyougaiJiritsuHelp});
    mindBody1TankiKiokuHelp.setFollowPressedButtons(new IkenshoHintButton[] {
        mindBody1NinchiHelp, mindBody1DentatsuHelp, mindBody1SyokujiHelp});
    mindBody1NinchiHelp.setFollowPressedButtons(new IkenshoHintButton[] {
                                                mindBody1TankiKiokuHelp,
                                                mindBody1DentatsuHelp,
                                                mindBody1SyokujiHelp});
    mindBody1DentatsuHelp.setFollowPressedButtons(new IkenshoHintButton[] {
                                                  mindBody1TankiKiokuHelp,
                                                  mindBody1NinchiHelp,
                                                  mindBody1SyokujiHelp});
    mindBody1SyokujiHelp.setFollowPressedButtons(new IkenshoHintButton[] {
                                                 mindBody1TankiKiokuHelp,
                                                 mindBody1NinchiHelp,
                                                 mindBody1DentatsuHelp});

    Component comp = mindBody1ShinkeiProJyusin.getEditor().getEditorComponent();
    if (comp instanceof AbstractVRTextField) {
      AbstractVRTextField field = (AbstractVRTextField) comp;
      field.addKeyListener(new KeyListener() {
        public void keyPressed(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            mindBody1ShinkeiProJyusin.hidePopup();
          }
        }

        public void keyReleased(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            mindBody1ShinkeiProJyusin.hidePopup();
          }
        }

        public void keyTyped(KeyEvent e) {
          if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            mindBody1ShinkeiProJyusin.hidePopup();
          }
        }
      });
    }

  }
  
  /**
   * ��Q�V�l�̓��퐶�������x��Ԃ��܂��B
   * @return ��Q�V�l�̓��퐶�������x
   */
  protected ACClearableRadioButtonGroup getMindBody1SyougaiJiritsu(){
      if(mindBody1SyougaiJiritsu==null){
          mindBody1SyougaiJiritsu = new ACClearableRadioButtonGroup();
      }
      return mindBody1SyougaiJiritsu; 
  }
  
  /**
   * �s�𐫘V�l�̓��퐶�������x��Ԃ��܂��B
   * @return �s�𐫘V�l�̓��퐶�������x
   */
  protected ACClearableRadioButtonGroup getMindBody1ChihouJiritsu(){
      if(mindBody1ChihouJiritsu==null){
          mindBody1ChihouJiritsu = new ACClearableRadioButtonGroup();
      }
      return mindBody1ChihouJiritsu; 
  }
  private void jbInit() throws Exception {

//    mindBody1SyougaiJiritsu.setModel(new VRListModelAdapter(new
//        VRArrayList(Arrays.asList(new
//                                  String[] {"����", "�i�P", "�i�Q", "�`�P", "�`�Q", "�a�P",
//                                  "�a�Q", "�b�P", "�b�Q"}))));
//    getMindBody1ChihouJiritsu().setModel(new VRListModelAdapter(new
//        VRArrayList(Arrays.asList(new
//                                  String[] {"����", "I", "II��", "II��", "III��", "III��",
//                                  "IV", "�l"}))));
    mindBody1TankiKioku.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"���Ȃ�", "��肠��"}))));
    mindBody1Dentatsu.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"�`������", "�����炩����", "��̓I�v���Ɍ�����",
                                  "�`�����Ȃ�"}))));
    mindBody1Syokuji.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"�����Ȃ������Ƃ������ŐH�ׂ���", "�S�ʉ"}))));
    mindBody1Ninchi.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"����", "�����炩����", "����肪�K�v", "���f�ł��Ȃ�"}))));

    mindBody1HasMondai.setUseClearButton(false);
    mindBody1HasMondai.setBindPath("MONDAI_FLAG");
    mindBody1HasMondai.setModel(existEmptyListModel);
    mindBody1HasMondai.setValues(new int[]{1,2});
    getMindBody1HasShinkei().setModel(existEmptyListModel);
    getMindBody1HasShinkei().setValues(new int[]{1,2});
    getMindBody1HasShinkei().setUseClearButton(false);
    mindBody1HasShinkeiProJyusin.setModel(existEmptyListModel);
    mindBody1HasShinkeiProJyusin.setEnabled(false);
    mindBody1HasShinkeiProJyusin.setUseClearButton(false);

    setLayout(mindBody1Layout);
    mindBody1HasShinkeiProJyusin.setBindPath("SENMONI");
    mindBody1MondaiBoukou.setBindPath("BOUKOU");
    mindBody1MondaiBoukou.setEnabled(false);
    mindBody1MondaiBoukou.setText("�\�s");
    mindBody1ChihouJiritsuHelp.setToolTipText("�ڍא�����\�����܂��B");
    mindBody1ChihouJiritsuHelp.setHintArea(ikenshoHintPanel1);
    mindBody1ChihouJiritsuHelp.setHintContainer(hint);
    mindBody1JiritsuClear.setMargin(new Insets(0,0,0,0));
    mindBody1JiritsuClear.setText("�N���A");
    mindBody1JiritsuClear.setToolTipText("�u���퐶���̎����x���ɂ��āv�̑S���ڂ̑I�����������܂��B");
    mindBody1MondaiHushimatsu.setBindPath("FUSIMATU");
    mindBody1MondaiHushimatsu.setEnabled(false);
    mindBody1MondaiHushimatsu.setText("�΂̕s�n��");
    mindBody1TankiKiokuHelp.setToolTipText("�ڍא�����\�����܂��B");
    mindBody1TankiKiokuHelp.setHintArea(ikenshoHintPanel1);
    mindBody1TankiKiokuHelp.setHintContainer(hint);
    mindBody1Layout.setFitVLast(true);
    mindBody1Layout.setFitHLast(true);
    mindBody1RikaiKiokuGroup.setLayout(mindBody1RikaiKiokuLayout);
    mindBody1RikaiKiokuGroup.setText("��������ыL��");
    getMindBody1Shinkei().setEnabled(false);
    getMindBody1Shinkei().setPreferredSize(new Dimension(400, 19));
    getMindBody1Shinkei().setMaxLength(30);
    getMindBody1Shinkei().setBindPath("SEISIN_NM");
    getMindBody1Shinkei().setIMEMode(InputSubset.KANJI);
    mindBody1Syokuji.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"�����Ȃ������Ƃ������ŐH�ׂ���", "�S�ʉ"}))));
    mindBody1Syokuji.setBindPath("SHOKUJI");
    mindBody1Syokuji.setNoSelectIndex(0);
    mindBody1Syokuji.setUseClearButton(false);
    mindBody1NinchiHelp.setToolTipText("�ڍא�����\�����܂��B");
    mindBody1NinchiHelp.setHintArea(ikenshoHintPanel1);
    mindBody1NinchiHelp.setHintContainer(hint);
    mindBody1JiritsuGroup.setLayout(mindBody1JiritsuGroupLayout);
    mindBody1JiritsuGroup.setText("���퐶���̎����x���ɂ���");
    mindBody1Ninchi.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"����", "�����炩����", "����肪�K�v", "���f�ł��Ȃ�"}))));
    mindBody1Ninchi.setBindPath("NINCHI");
    mindBody1Ninchi.setNoSelectIndex(0);
    mindBody1Ninchi.setUseClearButton(false);
    mindBody1NinchiPanel.setText("����̈ӎv������s�����߂�\r\n�F�m�\��");
    mindBody1NinchiPanel.setHorizontalAlignment(4);
    mindBody1RikaiKiokuLayout.setFitHLast(true);
    mindBody1RikaiKiokuLayout.setHgrid(200);
    mindBody1RikaiKiokuLayout.setHgap(0);
    mindBody1RikaiKiokuLayout.setLabelMargin(100);
    getMindBody1SyougaiJiritsu().setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"����", "�i�P", "�i�Q", "�`�P", "�`�Q", "�a�P",
                                  "�a�Q", "�b�P", "�b�Q"}))));
    getMindBody1SyougaiJiritsu().setBindPath("NETAKIRI");
    getMindBody1SyougaiJiritsu().setUseClearButton(false);
    getMindBody1SyougaiJiritsu().setSelectedIndex(0);
    mindBody1DentatsuPanel.setText("�����̈ӎv�̓`�B�\��");
    mindBody1ShinkeiProJyusinTail.setText("(15�����ȓ�)");
    mindBody1ShinkeiProJyusinTail.setForeground(IkenshoConstants.
                                                COLOR_MESSAGE_TEXT_FOREGROUND);
    getTitle().setBackground(IkenshoConstants.COLOR_PANEL_TITLE_BACKGROUND);
    getTitle().setOpaque(true);
    getTitle().setVisible(true);
    getTitle().setForeground(IkenshoConstants.COLOR_PANEL_TITLE_FOREGROUND);
    getTitle().setText("�R�D�S�g�̏�ԂɊւ���ӌ�");
    mindBody1TankiKioku.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"���Ȃ�", "��肠��"}))));
    mindBody1TankiKioku.setBindPath("TANKI_KIOKU");
    mindBody1TankiKioku.setNoSelectIndex(0);
    mindBody1TankiKioku.setUseClearButton(false);
    mindBody1JiritsuGroupLayout.setFitHLast(true);
    mindBody1JiritsuGroupLayout.setHgap(0);
    mindBody1SyougaiJiritsuPanel.setLayout(new BorderLayout());
//    mindBody1SyougaiJiritsuPanel.setText("��Q�V�l�̓��퐶�������x" +
//                                         IkenshoConstants.LINE_SEPARATOR +
//                                         "�i�Q������x�j");
    mindBody1SyougaiJiritsuPanel.setText("��Q����҂̓��퐶�������x" +
            IkenshoConstants.LINE_SEPARATOR +
            "�i�Q������x�j");
    mindBody1SyougaiJiritsuPanel.setHorizontalAlignment(SwingConstants.RIGHT);
    mindBody1SyokujiHelp.setToolTipText("�ڍא�����\�����܂��B");
    mindBody1SyokujiHelp.setHintArea(ikenshoHintPanel1);
    mindBody1SyokujiHelp.setHintContainer(hint);
    mindBody1MondaiGyakuten.setEnabled(false);
    mindBody1MondaiGyakuten.setText("����t�]");
    mindBody1MondaiGyakuten.setBindPath("CHUYA");
    mindBody1MondaiGroup.setLayout(new VRLayout());
    mindBody1MondaiGroup.setText("���s���̗L��");
    mindBody1DentatsuHelp.setToolTipText("�ڍא�����\�����܂��B");
    mindBody1DentatsuHelp.setHintArea(ikenshoHintPanel1);
    mindBody1DentatsuHelp.setHintContainer(hint);
    mindBody1MondaiSeiteki.setEnabled(false);
    mindBody1MondaiSeiteki.setText("���I���s��");
    mindBody1MondaiSeiteki.setBindPath("SEITEKI_MONDAI");
    mindBody1MondaiGensi.setEnabled(false);
    mindBody1MondaiGensi.setText("�����E����");
    mindBody1MondaiGensi.setBindPath("GNS_GNC");
    mindBody1MondaiOther.setEnabled(false);
    mindBody1MondaiOther.setFocusPainted(true);
    mindBody1MondaiOther.setText("���̑�");
    mindBody1MondaiOther.setBindPath("MONDAI_OTHER");
    mindBody1TankiKiokuPanel.setText("�Z���L��");
    getMindBody1HasShinkei().setBindPath("SEISIN");
    mindBody1MondaiBougen.setEnabled(false);
    mindBody1MondaiBougen.setText("�\��");
    mindBody1MondaiBougen.setBindPath("BOUGEN");
    mindBody1MondaiOtherLayout.setHgap(5);
    mindBody1MondaiOtherLayout.setVgap(0);
    mindBody1MondaiOtherLayout.setAutoWrap(false);
    mindBody1ShinkeiGroup.setLayout(new VRLayout());
    mindBody1ShinkeiGroup.setText("���_�E�_�o�Ǐ�̗L���i30�����ȓ��j");
    getMindBody1ChihouJiritsu().setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"����", "I", "II��", "II��", "III��", "III��",
                                  "IV", "�l"}))));
    getMindBody1ChihouJiritsu().setBindPath("CHH_STS");
    getMindBody1ChihouJiritsu().setUseClearButton(false);
    getMindBody1ChihouJiritsu().setSelectedIndex(0);
    mindBody1ShinkeiProJyusinPanel.setText("�i�L�̏ꍇ�j�������f�̗L��");
    mindBody1MondaiTeikou.setEnabled(false);
    mindBody1MondaiTeikou.setText("���ւ̒�R");
    mindBody1MondaiTeikou.setBindPath("TEIKOU");
    mindBody1MondaiPanel.setLayout(new VRLayout());
    mindBody1MondaiMousou.setEnabled(false);
    mindBody1MondaiMousou.setText("�ϑz");
    mindBody1MondaiMousou.setBindPath("MOUSOU");
    mindBody1MondaiIsyoku.setEnabled(false);
    mindBody1MondaiIsyoku.setText("�ِH�s��");
    mindBody1MondaiIsyoku.setBindPath("ISHOKU");
    mindBody1SyokujiPanel.setLayout(new BorderLayout());
    mindBody1SyokujiPanel.setText("�H��");
    mindBody1Dentatsu.setModel(new VRListModelAdapter(new
        VRArrayList(Arrays.asList(new
                                  String[] {"�`������", "�����炩����", "��̓I�v���Ɍ�����",
                                  "�`�����Ȃ�"}))));
    mindBody1Dentatsu.setBindPath("DENTATU");
    mindBody1Dentatsu.setNoSelectIndex(0);
    mindBody1Dentatsu.setUseClearButton(false);
    mindBody1MondaiFuketsu.setEnabled(false);
    mindBody1MondaiFuketsu.setText("�s���s��");
    mindBody1MondaiFuketsu.setBindPath("FUKETU");
    mindBody1MondaiOtherPanel.setLayout(mindBody1MondaiOtherLayout);
    mindBody1RikaiKiokuClear.setToolTipText("�u��������ыL���v�̑S���ڂ̑I�����������܂��B");
    mindBody1RikaiKiokuClear.setText("�N���A");
    mindBody1RikaiKiokuClear.setMargin(new Insets(0,0,0,0));
    mindBody1ChihouJiritsuPanel.setLayout(new BorderLayout());
//    mindBody1ChihouJiritsuPanel.setText("�s�𐫘V�l�̓��퐶�������x");
    mindBody1ChihouJiritsuPanel.setText("�F�m�Ǎ���҂̓��퐶�������x");
    mindBody1SyougaiJiritsuHelp.setToolTipText("�ڍא�����\�����܂��B");
    mindBody1SyougaiJiritsuHelp.setHintArea(ikenshoHintPanel1);
    mindBody1SyougaiJiritsuHelp.setHintContainer(hint);
    mindBody1MondaiOtherVal.setEnabled(false);
    mindBody1MondaiOtherVal.setColumns(10);
    mindBody1MondaiOtherVal.setIMEMode(InputSubset.KANJI);
    mindBody1MondaiOtherVal.setBindPath("MONDAI_OTHER_NM");
    mindBody1MondaiOtherVal.setMaxLength(10);
    mindBody1ShinkeiProJyusin.setEnabled(false);
    mindBody1ShinkeiProJyusin.setPreferredSize(new Dimension(200, 19));
    mindBody1ShinkeiProJyusin.setBindPath("SENMONI_NM");
    mindBody1ShinkeiProJyusin.setMaxLength(15);
    mindBody1ShinkeiProJyusin.setIMEMode(InputSubset.KANJI);
    mindBody1MondaiHaikai.setEnabled(false);
    mindBody1MondaiHaikai.setText("�p�j");
    mindBody1MondaiHaikai.setBindPath("HAIKAI");
    hint.setLayout(new BorderLayout());
    mindBody1MondaiOtherValHeses.setEnabled(false);
    mindBody1TankiKiokuPanel.setLayout(new BorderLayout());
    mindBody1NinchiPanel.setLayout(new BorderLayout());
    mindBody1NinchiPanel.add(mindBody1Ninchi, BorderLayout.CENTER);
    mindBody1NinchiPanel.add(mindBody1NinchiHelpPanel, BorderLayout.EAST);
    mindBody1NinchiHelpPanel.add(mindBody1NinchiHelp, null);
    mindBody1TankiKiokuPanel.add(mindBody1TankiKioku, BorderLayout.CENTER);
    mindBody1TankiKiokuPanel.add(mindBody1TankiKiokuHelpPanel, BorderLayout.EAST);
    mindBody1TankiKiokuHelpPanel.add(mindBody1TankiKiokuHelp, null);
    mindBody1RikaiKiokuGroup.add(mindBody1TankiKiokuPanel,
                                 VRLayout.FLOW_INSETLINE_RETURN);
    mindBody1RikaiKiokuGroup.add(mindBody1NinchiPanel,
                                 VRLayout.FLOW_INSETLINE_RETURN);
    mindBody1RikaiKiokuGroup.add(mindBody1DentatsuPanel,
                                 VRLayout.FLOW_INSETLINE_RETURN);
    mindBody1RikaiKiokuGroup.add(mindBody1SyokujiPanel,
                                 VRLayout.FLOW_INSETLINE_RETURN);

    mindBody1SyougaiJiritsuPanel.add(getMindBody1SyougaiJiritsu(), BorderLayout.CENTER);
    mindBody1SyougaiJiritsuPanel.add(mindBody1SyougaiJiritsuHelpPanel, BorderLayout.EAST);
    mindBody1SyougaiJiritsuHelpPanel.add(mindBody1SyougaiJiritsuHelp, null);

    mindBody1DentatsuPanel.setLayout(new BorderLayout());
    mindBody1DentatsuPanel.add(mindBody1Dentatsu, BorderLayout.CENTER);
    mindBody1DentatsuPanel.add(getRikaiKiokuDentatsuHelpPanel(), BorderLayout.EAST);
    addRikaiKiokuClearButton();
    getRikaiKiokuDentatsuHelpPanel().add(mindBody1DentatsuHelp, null);

    mindBody1SyokujiPanel.add(mindBody1Syokuji, BorderLayout.CENTER);
    mindBody1SyokujiPanel.add(mindBody1SyokujiHelpPanel, BorderLayout.EAST);
    mindBody1SyokujiHelpPanel.add(mindBody1SyokujiHelp, null);


    mindBody1JiritsuGroup.add(mindBody1SyougaiJiritsuPanel,
                              VRLayout.FLOW_INSETLINE_RETURN);
    mindBody1JiritsuGroup.add(mindBody1ChihouJiritsuPanel,
                              VRLayout.FLOW_INSETLINE_RETURN);
    mindBody1ChihouJiritsuPanel.add(getMindBody1ChihouJiritsu(), BorderLayout.CENTER);
    mindBody1ChihouJiritsuPanel.add(mindBody1ChihouJiritsuHelpPanel, BorderLayout.EAST);
    mindBody1ChihouJiritsuHelpPanel.add(mindBody1JiritsuClear, null);
    mindBody1ChihouJiritsuHelpPanel.add(mindBody1ChihouJiritsuHelp, null);
    mindBody1MondaiGroup.add(mindBody1HasMondai, VRLayout.NORTH);
    mindBody1MondaiGroup.add(mindBody1MondaiPanel, VRLayout.CLIENT);
    mindBody1MondaiPanel.add(mindBody1MondaiGensi, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiMousou, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiGyakuten, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiBougen, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiBoukou, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiTeikou, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiHaikai, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiHushimatsu, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiFuketsu, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiIsyoku, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiSeiteki, VRLayout.FLOW);
    mindBody1MondaiPanel.add(mindBody1MondaiOtherPanel, VRLayout.FLOW);
    mindBody1MondaiOtherPanel.add(mindBody1MondaiOther, VRLayout.WEST);
    mindBody1MondaiOtherPanel.add(mindBody1MondaiOtherValHeses, VRLayout.CLIENT);
    mindBody1MondaiOtherValHeses.add(mindBody1MondaiOtherVal, null);
    mindBody1ShinkeiGroup.add(getMindBody1HasShinkei(), VRLayout.FLOW_INSETLINE_RETURN);
    addMindBody1HasShinkei();
    mindBody1ShinkeiGroup.add(mindBody1ShinkeiProJyusinPanel, VRLayout.FLOW_INSETLINE_RETURN);
    mindBody1ShinkeiProJyusinPanel.add(mindBody1HasShinkeiProJyusin, null);
    mindBody1HasShinkeiProJyusin.add(mindBody1ShinkeiProJyusin, 1);
    mindBody1HasShinkeiProJyusin.add(mindBody1ShinkeiProJyusinTail, 2);
    this.add(getTitle(), VRLayout.NORTH);
    this.add(mindBody1JiritsuGroup, VRLayout.NORTH);
    this.add(mindBody1RikaiKiokuGroup, VRLayout.NORTH);
    this.add(mindBody1MondaiGroup, VRLayout.NORTH);
    this.add(mindBody1ShinkeiGroup, VRLayout.NORTH);
    this.add(hint, VRLayout.CLIENT);
    hint.add(ikenshoHintPanel1, BorderLayout.CENTER);

    addInnerBindComponent(getMindBody1Shinkei());
    addInnerBindComponent(mindBody1ShinkeiProJyusin);

    //Select
    getMindBody1SyougaiJiritsu().setSelectedIndex(0);
    getMindBody1ChihouJiritsu().setSelectedIndex(0);
    getMindBody1HasShinkei().setSelectedIndex(2);
    mindBody1HasMondai.setSelectedIndex(2);
  }

}
