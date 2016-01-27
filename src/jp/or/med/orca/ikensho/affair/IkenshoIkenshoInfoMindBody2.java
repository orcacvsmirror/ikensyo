package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;

import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.component.IkenshoBodyStatusContainer;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.picture.IkenshoHumanPicture;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMindBody2 extends IkenshoTabbableChildAffairContainer {
  private VRListModelAdapter rightLeftListModel = new VRListModelAdapter(new
      VRArrayList(Arrays.asList(new String[] {"�E", "��"})));


  private ACIntegerCheckBox mindBody2ConnectMataLeft = new
      ACIntegerCheckBox();
  private VRLayout mindBody2Layout = new VRLayout();
  private ACTextField mindBody2ConnectWeight = new ACTextField();
  private ACIntegerCheckBox mindBody2DownTaikanRight = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody2DownKashiLeft = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2ConnectKata = new ACLabelContainer();
  private ACIntegerCheckBox mindBody2ConnectHizaRight = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2ConnectHiji = new ACLabelContainer();
  private ACLabelContainer mindBody2ConnectHeightPanel = new ACLabelContainer();
  private ACIntegerCheckBox mindBody2ConnectHijiRight = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody2DownJyoushiRight = new
      ACIntegerCheckBox();
  private VRPanel mindBody2Pos = new VRPanel();
  private VRPanel mindBody2Connect = new VRPanel();
  private ACIntegerCheckBox mindBody2DownJyoushiLeft = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody2DownKashiRight = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2DownJyoushi = new ACLabelContainer();
  private ACIntegerCheckBox mindBody2ConnectKoushuku = new
      ACIntegerCheckBox();
  private ACGroupBox mindBody2Group;
  private IkenshoDocumentTabTitleLabel mindBody2Title = new IkenshoDocumentTabTitleLabel();
  private ACIntegerCheckBox mindBody2ConnectHijiLeft = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2ConnectWeightPanel = new ACLabelContainer();
  private ACTextField mindBody2ConnectHeight = new ACTextField();
  private ACIntegerCheckBox mindBody2DownFuzuii = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2ConnectMata = new ACLabelContainer();
  private ACIntegerCheckBox mindBody2ConnectHizaLeft = new
      ACIntegerCheckBox();
  private VRLabel mindBody2ConnectHeightUnit = new VRLabel();
  private ACLabelContainer mindBody2DownKashi = new ACLabelContainer();
  private ACIntegerCheckBox mindBody2ConnectMataRight = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2DownTaikan = new ACLabelContainer();
  private ACIntegerCheckBox mindBody2ConnectKataLeft = new
      ACIntegerCheckBox();
  private ACClearableRadioButtonGroup mindBody2ConnectHand = new
      ACClearableRadioButtonGroup();
  private ACIntegerCheckBox mindBody2DownTaikanLeft = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox mindBody2ConnectKataRight = new
      ACIntegerCheckBox();
  private ACLabelContainer mindBody2ConnectHiza = new ACLabelContainer();
  private IkenshoHumanPicture mindBody2Picture = new IkenshoHumanPicture();
  private VRPanel mindBody2Client = new VRPanel();
  private VRLabel mindBody2ConnectWeightUnit = new VRLabel();
  private ACLabelContainer mindBody2ConnectHandPanel = new ACLabelContainer();
  private VRLayout mindBody2PosLayout = new VRLayout();
  private VRLayout mindBody2ConnectLayout = new VRLayout();

  private ACParentHesesPanelContainer mindBody2ConnectHandHeses = new ACParentHesesPanelContainer();
  private IkenshoBodyStatusContainer mindBody2Pos1 = new IkenshoBodyStatusContainer();
  private IkenshoBodyStatusContainer mindBody2Pos2 = new IkenshoBodyStatusContainer();
  private IkenshoBodyStatusContainer mindBody2Pos3 = new IkenshoBodyStatusContainer();
  private IkenshoBodyStatusContainer mindBody2Pos4 = new IkenshoBodyStatusContainer();
  private IkenshoBodyStatusContainer mindBody2Pos5 = new IkenshoBodyStatusContainer();
  private ACLabelContainer mindBody2ConnectKoushukus = new ACLabelContainer();
  private ACLabelContainer mindBody2DownFuzuiis = new ACLabelContainer();


  /**
   * �l�������R���e�i��Ԃ��܂��B
   * @return �l�������R���e�i
   */
  protected IkenshoBodyStatusContainer getShishiKesson(){
    return mindBody2Pos1;
  }
  /**
   * ��ჃR���e�i��Ԃ��܂��B
   * @return ��ჃR���e�i
   */
  protected IkenshoBodyStatusContainer getMahi(){
    return mindBody2Pos2;
  }
  /**
   * �ؗ͂̒ቺ�R���e�i��Ԃ��܂��B
   * @return �ؗ͂̒ቺ�R���e�i
   */
  protected IkenshoBodyStatusContainer getMastleDown(){
    return mindBody2Pos3;
  }
  /**
   * ��ጃR���e�i��Ԃ��܂��B
   * @return ��ጃR���e�i
   */
  protected IkenshoBodyStatusContainer getJyokusou(){
    return mindBody2Pos4;
  }
  /**
   * ���̑��̐S�g�̏�ԃR���e�i��Ԃ��܂��B
   * @return ���̑��̐S�g�̏�ԃR���e�i
   */
  protected IkenshoBodyStatusContainer getMindBodyStatusOthers(){
    return mindBody2Pos5;
  }
  /**
   * �֐߂̍S�k�R���e�i��Ԃ��܂��B
   * @return �֐߂̍S�k�R���e�i
   */
  protected ACLabelContainer getConnectKoushukus(){
    return mindBody2ConnectKoushukus;
  }
  /**
   * �����E�s���Ӊ^���R���e�i��Ԃ��܂��B
   * @return �����E�s���Ӊ^���R���e�i
   */
  protected ACLabelContainer getDownFuzuiis(){
    return mindBody2DownFuzuiis;
  }

  /**
   * �����E�s���Ӊ^��(�`�F�b�N)��Ԃ��܂��B
   * @return �����E�s���Ӊ^��(�`�F�b�N)
   */
  protected ACIntegerCheckBox getDownFuzuii() {
    return mindBody2DownFuzuii;
  }
  /**
   * �����E�s���Ӊ^��(�㎈)��Ԃ��܂��B
   * @return �����E�s���Ӊ^��(�㎈)
   */
  protected ACLabelContainer getDownFuzuiiJyoushi() {
    return mindBody2DownJyoushi;
  }
  /**
   * �����E�s���Ӊ^��(�̊�)��Ԃ��܂��B
   * @return �����E�s���Ӊ^��(�̊�)
   */
  protected ACLabelContainer getDownFuzuiiTaikan() {
    return mindBody2DownTaikan;
  }
  /**
   * �����E�s���Ӊ^��(����)��Ԃ��܂��B
   * @return �����E�s���Ӊ^��(����)
   */
  protected ACLabelContainer getDownFuzuiiKashi() {
    return mindBody2DownKashi;
  }


  /**
   * �S�g�}��Ԃ��܂��B
   * @return �S�g�}
   */
  protected IkenshoHumanPicture getHumanPicture(){
    return mindBody2Picture;
  }

  /**
   * �����r�R���e�i��Ԃ��܂��B
   * @return �����r�R���e�i
   */
  protected ACLabelContainer getDominantHand(){
    return mindBody2ConnectHandPanel;
  }
  /**
   * �̏d�R���e�i��Ԃ��܂��B
   * @return �̏d�R���e�i
   */
  protected ACLabelContainer getBodyWeightContainer(){
    return mindBody2ConnectWeightPanel;
  }

  /**
   * �g���R���e�i��Ԃ��܂��B
   * @return �g���R���e�i
   */
  protected ACLabelContainer getBodyHeightContainer(){
    return mindBody2ConnectHeightPanel;
  }



  /**
   * ��ՃO���[�v��Ԃ��܂��B
   * @return ��ՃO���[�v
   */
  protected ACGroupBox getContaintsGroup(){
      if(mindBody2Group == null){
          mindBody2Group = new ACGroupBox();
      }
    return mindBody2Group;
  }
  /**
   * �S�g�̏��(����)�O���[�v��Ԃ��܂��B
   * @return �S�g�̏��(����)�O���[�v
   */
  protected VRPanel getMindBodyStatusContainer(){
    return mindBody2Pos;
  }
  /**
   * �S�g�̏��(�l�̐}��)�O���[�v��Ԃ��܂��B
   * @return �S�g�̏��(�l�̐}��)�O���[�v
   */
  protected VRPanel getMindBodyClientContainer(){
    return mindBody2Client;
  }
  /**
   * �S�g�̏��(�֐ߓ�)�O���[�v��Ԃ��܂��B
   * @return �S�g�̏��(�֐ߓ�)�O���[�v
   */
  protected VRPanel getMindBodyConnectContainer(){
    return mindBody2Connect;
  }


  /**
   * override���ĐS�g�̏�Ԃ̒ǉ��������`���܂��B
   */
  protected void addContaints(){

    getContaintsGroup().add(getMindBodyClientContainer(), BorderLayout.CENTER);
    getContaintsGroup().add(getMindBodyStatusContainer(), BorderLayout.NORTH);
     getMindBodyConnectContainer().add(getConnectKoushukus(), null);
     getMindBodyConnectContainer().add(getDownFuzuiis(), null);
     getMindBodyConnectContainer().add(getDominantHand(), VRLayout.FLOW_INSETLINE_RETURN);
     getMindBodyConnectContainer().add(getBodyWeightContainer(), VRLayout.FLOW_INSETLINE);
     getMindBodyConnectContainer().add(getBodyHeightContainer(), VRLayout.FLOW_INSETLINE);
    getMindBodyClientContainer().add(getHumanPicture(), BorderLayout.CENTER);
    getMindBodyClientContainer().add(getMindBodyConnectContainer(), BorderLayout.WEST);
    getMindBodyStatusContainer().add(getShishiKesson(), VRLayout.FLOW_INSETLINE_RETURN);
    getMindBodyStatusContainer().add(getMahi(), VRLayout.FLOW_INSETLINE_RETURN);
    getMindBodyStatusContainer().add(getMastleDown(), VRLayout.FLOW_INSETLINE_RETURN);
    getMindBodyStatusContainer().add(getJyokusou(), VRLayout.FLOW_INSETLINE_RETURN);
    getMindBodyStatusContainer().add(getMindBodyStatusOthers(), VRLayout.FLOW_INSETLINE_RETURN);
  }

  /**
   * override���Ď����E�s���Ӊ^���̒ǉ��������`���܂��B
   */
  protected void addDownFuzuii(){
    getDownFuzuiis().add(getDownFuzuii(), VRLayout.WEST);
    getDownFuzuiis().add(getDownFuzuiiJyoushi(), VRLayout.FLOW_INSETLINE_RETURN);
    getDownFuzuiis().add(getDownFuzuiiTaikan(), VRLayout.FLOW_INSETLINE_RETURN);
    getDownFuzuiis().add(getDownFuzuiiKashi(), VRLayout.FLOW_INSETLINE_RETURN);
  }

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    applyPoolTeikeibun(mindBody2Pos1.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME);
    applyPoolTeikeibun(mindBody2Pos2.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_PARALYSIS_NAME);
    applyPoolTeikeibun(mindBody2Pos3.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME);
    applyPoolTeikeibun(mindBody2Pos4.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME);
    applyPoolTeikeibun(mindBody2Pos5.getPosCombo(), IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME);
  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();
    if (mindBody2ConnectKataRight.isSelected() ||
        mindBody2ConnectKataLeft.isSelected() ||
        mindBody2ConnectHijiRight.isSelected() ||
        mindBody2ConnectHijiLeft.isSelected() ||
        mindBody2ConnectMataRight.isSelected() ||
        mindBody2ConnectMataLeft.isSelected() ||
        mindBody2ConnectHizaRight.isSelected() ||
        mindBody2ConnectHizaLeft.isSelected()) {
      mindBody2ConnectKoushuku.setSelected(true);
    }else{
      mindBody2ConnectKoushuku.setSelected(false);
    }
    if (mindBody2DownJyoushiRight.isSelected() ||
        mindBody2DownJyoushiLeft.isSelected() ||
        mindBody2DownTaikanRight.isSelected() ||
        mindBody2DownTaikanLeft.isSelected() ||
        mindBody2DownKashiRight.isSelected() ||
        mindBody2DownKashiLeft.isSelected()) {
      mindBody2DownFuzuii.setSelected(true);
    }else{
      mindBody2DownFuzuii.setSelected(false);
    }

  }

  public IkenshoHumanPicture getPicture(){
    return mindBody2Picture;
  }

  public IkenshoIkenshoInfoMindBody2() {
    try {
      jbInit();
  }
    catch(Exception e) {
      e.printStackTrace();
    }
    mindBody2ConnectKoushuku.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED){
          select = true;
        }else if (e.getStateChange() == ItemEvent.DESELECTED){
          select = false;
        }else{
          return;
        }
        mindBody2ConnectKataRight.setEnabled(select);
        mindBody2ConnectKataLeft.setEnabled(select);
        mindBody2ConnectHijiRight.setEnabled(select);
        mindBody2ConnectHijiLeft.setEnabled(select);
        mindBody2ConnectMataRight.setEnabled(select);
        mindBody2ConnectMataLeft.setEnabled(select);
        mindBody2ConnectHizaRight.setEnabled(select);
        mindBody2ConnectHizaLeft.setEnabled(select);
        mindBody2ConnectKata.setEnabled(select);
        mindBody2ConnectHiji.setEnabled(select);
        mindBody2ConnectMata.setEnabled(select);
        mindBody2ConnectHiza.setEnabled(select);
      }
    });

    mindBody2DownFuzuii.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED){
          select = true;
        }else if (e.getStateChange() == ItemEvent.DESELECTED){
          select = false;
        }else{
          return;
        }
        mindBody2DownJyoushiRight.setEnabled(select);
        mindBody2DownJyoushiLeft.setEnabled(select);
        mindBody2DownTaikanRight.setEnabled(select);
        mindBody2DownTaikanLeft.setEnabled(select);
        mindBody2DownKashiRight.setEnabled(select);
        mindBody2DownKashiLeft.setEnabled(select);
        mindBody2DownJyoushi.setEnabled(select);
        mindBody2DownTaikan.setEnabled(select);
        mindBody2DownKashi.setEnabled(select);
      }
    });

  }

  private void jbInit() throws Exception {
    //Model

    mindBody2ConnectHand.setModel(rightLeftListModel);

    setLayout(mindBody2Layout);
    mindBody2PosLayout.setVgap(0);
    mindBody2PosLayout.setAutoWrap(false);
    mindBody2PosLayout.setLabelMargin(0);
    mindBody2PosLayout.setAlignment(VRLayout.LEFT);
    mindBody2PosLayout.setHgap(0);
    mindBody2ConnectLayout.setHgrid(20);
    mindBody2ConnectLayout.setHgap(0);
    mindBody2ConnectLayout.setVgap(0);
    mindBody2ConnectLayout.setAutoWrap(false);
    mindBody2ConnectLayout.setLabelMargin(0);
    mindBody2Connect.setLayout(mindBody2ConnectLayout);
    mindBody2ConnectKoushukus.setLayout(new VRLayout());
    mindBody2DownFuzuiis.setLayout(new VRLayout());

    mindBody2ConnectMataLeft.setEnabled(false);
    mindBody2ConnectMataLeft.setText("��");
    mindBody2ConnectMataLeft.setBindPath("MATA_KOUSHU_HIDARI");
    mindBody2Layout.setFitHLast(true);
    mindBody2Layout.setFitVLast(true);
    mindBody2ConnectWeight.setColumns(6);
    mindBody2ConnectWeight.setHorizontalAlignment(SwingConstants.RIGHT);
    mindBody2ConnectWeight.setBindPath("WEIGHT");
    mindBody2ConnectWeight.setMaxLength(5);
    mindBody2DownTaikanRight.setEnabled(false);
    mindBody2DownTaikanRight.setText("�E");
    mindBody2DownTaikanRight.setBindPath("TAIKAN_SICCHOU_MIGI");
    mindBody2DownKashiLeft.setEnabled(false);
    mindBody2DownKashiLeft.setText("��");
    mindBody2DownKashiLeft.setBindPath("KASI_SICCHOU_HIDARI");
    mindBody2ConnectKata.setEnabled(false);
    mindBody2ConnectKata.setText("�E���֐�");
    mindBody2ConnectHizaRight.setEnabled(false);
    mindBody2ConnectHizaRight.setText("�E");
    mindBody2ConnectHizaRight.setBindPath("HIZA_KOUSHU_MIGI");
    mindBody2ConnectHiji.setEnabled(false);
    mindBody2ConnectHiji.setText("�E�I�֐�");
    mindBody2ConnectHeightPanel.setText("�g��=");
    mindBody2ConnectHijiRight.setEnabled(false);
    mindBody2ConnectHijiRight.setText("�E");
    mindBody2ConnectHijiRight.setBindPath("HIJI_KOUSHU_MIGI");
    mindBody2DownJyoushiRight.setEnabled(false);
    mindBody2DownJyoushiRight.setText("�E");
    mindBody2DownJyoushiRight.setBindPath("JOUSI_SICCHOU_MIGI");
    mindBody2Pos.setLayout(mindBody2PosLayout);
    mindBody2DownJyoushiLeft.setEnabled(false);
    mindBody2DownJyoushiLeft.setText("��");
    mindBody2DownJyoushiLeft.setPreferredSize(new Dimension(70, 20));
    mindBody2DownJyoushiLeft.setBindPath("JOUSI_SICCHOU_HIDARI");
    mindBody2DownKashiRight.setEnabled(false);
    mindBody2DownKashiRight.setText("�E");
    mindBody2DownKashiRight.setPreferredSize(new Dimension(70, 20));
    mindBody2DownKashiRight.setBindPath("KASI_SICCHOU_MIGI");
    mindBody2DownJyoushi.setEnabled(false);
    mindBody2DownJyoushi.setText("�E�㎈");
    mindBody2ConnectKoushuku.setPreferredSize(new Dimension(130, 23));
    mindBody2ConnectKoushuku.setText("�֐߂̍S�k");
    mindBody2ConnectKoushuku.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    mindBody2ConnectKoushuku.setBindPath("KOUSHU_FLAG");
    getContaintsGroup().setLayout(new BorderLayout());
    getContaintsGroup().setText("�g�̂̏��");
    getContaintsGroup().setVgap(20);
    mindBody2Title.setText("�R�D�S�g�̏�ԂɊւ���ӌ��i�����j");
    mindBody2ConnectHijiLeft.setEnabled(false);
    mindBody2ConnectHijiLeft.setText("��");
    mindBody2ConnectHijiLeft.setBindPath("HIJI_KOUSHU_HIDARI");
    mindBody2ConnectWeightPanel.setBackground(Color.pink);
    mindBody2ConnectWeightPanel.setText("�̏d=");
    mindBody2ConnectHeight.setMaxLength(5);
    mindBody2ConnectHeight.setColumns(6);
    mindBody2ConnectHeight.setHorizontalAlignment(SwingConstants.RIGHT);
    mindBody2ConnectHeight.setBindPath("HEIGHT");
//    mindBody2DownFuzuii.setPreferredSize(new Dimension(130, 23));
    mindBody2DownFuzuii.setPreferredSize(new Dimension(210, 32));
    mindBody2DownFuzuii.setText("�����E�s���Ӊ^��");
    mindBody2DownFuzuii.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    mindBody2DownFuzuii.setBindPath("SICCHOU_FLAG");
    mindBody2ConnectMata.setEnabled(false);
    mindBody2ConnectMata.setText("�E�Ҋ֐�");
    mindBody2ConnectHizaLeft.setEnabled(false);
    mindBody2ConnectHizaLeft.setText("��");
    mindBody2ConnectHizaLeft.setBindPath("HIZA_KOUSHU_HIDARI");
    mindBody2ConnectHeightUnit.setText("cm");
    mindBody2DownKashi.setEnabled(false);
    mindBody2DownKashi.setText("�E����");
    mindBody2ConnectMataRight.setEnabled(false);
    mindBody2ConnectMataRight.setText("�E");
    mindBody2ConnectMataRight.setBindPath("MATA_KOUSHU_MIGI");
    mindBody2DownTaikan.setEnabled(false);
    mindBody2DownTaikan.setText("�E�̊�");
    mindBody2ConnectKataLeft.setEnabled(false);
    mindBody2ConnectKataLeft.setText("��");
    mindBody2ConnectKataLeft.setBindPath("KATA_KOUSHU_HIDARI");
    mindBody2ConnectHand.setBindPath("KIKIUDE");
    mindBody2DownTaikanLeft.setEnabled(false);
    mindBody2DownTaikanLeft.setText("��");
    mindBody2DownTaikanLeft.setBindPath("TAIKAN_SICCHOU_HIDARI");
    mindBody2DownTaikanLeft.setPreferredSize(new Dimension(70, 20));
    mindBody2ConnectKataRight.setEnabled(false);
    mindBody2ConnectKataRight.setText("�E");
    mindBody2ConnectKataRight.setBindPath("KATA_KOUSHU_MIGI");
    mindBody2ConnectKataRight.setPreferredSize(new Dimension(70, 20));
    mindBody2ConnectHiza.setEnabled(false);
    mindBody2ConnectHiza.setText("�E�G�֐�");
    mindBody2Client.setLayout(new BorderLayout());
    mindBody2ConnectWeightUnit.setText("kg");
    mindBody2ConnectHandPanel.setText("�����r");
    mindBody2Pos.setOpaque(true);
//    mindBody2Pos.setBounds(new Rectangle(0, 0, 0, 2));
//    mindBody2Client.setBounds(new Rectangle(0, 0, 1, 1));
//    mindBody2Connect.setBounds(new Rectangle(0, 0, 0, 2));
    mindBody2Picture.setBindPath("GRAPHICS_COMMAND");
    mindBody2Picture.setBounds(new Rectangle(0, 0, 413, 295));
    mindBody2Pos1.setCheckBindPath("SISIKESSON");
    mindBody2Pos1.setCheckText("�l������");
    mindBody2Pos1.setPosBindPath("SISIKESSON_BUI");
    mindBody2Pos1.setRankBindPath("SISIKESSON_TEIDO");
    mindBody2Pos2.setCheckBindPath("MAHI");
    mindBody2Pos2.setCheckText("���");
    mindBody2Pos2.setPosBindPath("MAHI_BUI");
    mindBody2Pos2.setRankBindPath("MAHI_TEIDO");
    mindBody2Pos3.setCheckBindPath("KINRYOKU_TEIKA");
    mindBody2Pos3.setCheckText("�ؗ͂̒ቺ");
    mindBody2Pos3.setPosBindPath("KINRYOKU_TEIKA_BUI");
    mindBody2Pos3.setRankBindPath("KINRYOKU_TEIKA_TEIDO");
    mindBody2Pos4.setCheckBindPath("JOKUSOU");
    mindBody2Pos4.setCheckText("���");
    mindBody2Pos4.setPosBindPath("JOKUSOU_BUI");
    mindBody2Pos4.setRankBindPath("JOKUSOU_TEIDO");
    mindBody2Pos5.setCheckBindPath("HIFUSIKKAN");
    mindBody2Pos5.setCheckText("���̑��̔畆����");
    mindBody2Pos5.setPosBindPath("HIFUSIKKAN_BUI");
    mindBody2Pos5.setRankBindPath("HIFUSIKKAN_TEIDO");
    mindBody2DownFuzuiis.setContentAreaFilled(true);
    mindBody2DownFuzuiis.setFocusBackground(new Color(204, 204, 255));
    mindBody2ConnectKoushukus.setContentAreaFilled(true);
    mindBody2ConnectKoushukus.setFocusBackground(new Color(204, 204, 255));
    mindBody2ConnectHiza.add(mindBody2ConnectHizaRight, null);
    mindBody2ConnectHiza.add(mindBody2ConnectHizaLeft, null);
    mindBody2ConnectMata.add(mindBody2ConnectMataRight, null);
    mindBody2ConnectMata.add(mindBody2ConnectMataLeft, null);
    mindBody2ConnectHiji.add(mindBody2ConnectHijiRight, null);
    mindBody2ConnectHiji.add(mindBody2ConnectHijiLeft, null);
    mindBody2ConnectKata.add(mindBody2ConnectKataRight, null);
    mindBody2ConnectKata.add(mindBody2ConnectKataLeft, null);
    mindBody2ConnectKoushukus.add(mindBody2ConnectKoushuku, VRLayout.WEST);
    mindBody2ConnectKoushukus.add(mindBody2ConnectKata, VRLayout.FLOW_INSETLINE_RETURN);
    mindBody2ConnectKoushukus.add(mindBody2ConnectHiji, VRLayout.FLOW_INSETLINE_RETURN);
    mindBody2ConnectKoushukus.add(mindBody2ConnectMata, VRLayout.FLOW_INSETLINE_RETURN);
    mindBody2ConnectKoushukus.add(mindBody2ConnectHiza, VRLayout.FLOW_INSETLINE_RETURN);

    mindBody2DownJyoushi.add(mindBody2DownJyoushiRight, null);
    mindBody2DownJyoushi.add(mindBody2DownJyoushiLeft, null);
    mindBody2DownTaikan.add(mindBody2DownTaikanRight, null);
    mindBody2DownTaikan.add(mindBody2DownTaikanLeft, null);
    mindBody2DownKashi.add(mindBody2DownKashiRight, null);
    mindBody2DownKashi.add(mindBody2DownKashiLeft, null);
    mindBody2ConnectHandPanel.add(mindBody2ConnectHandHeses, null);
    mindBody2ConnectHandHeses.add(mindBody2ConnectHand, null);
    mindBody2ConnectHeightPanel.add(mindBody2ConnectHeight, null);
    mindBody2ConnectHeightPanel.add(mindBody2ConnectHeightUnit, null);
    mindBody2ConnectWeightPanel.add(mindBody2ConnectWeight, null);
    mindBody2ConnectWeightPanel.add(mindBody2ConnectWeightUnit, null);

    addDownFuzuii();

    addContaints();
    this.add(mindBody2Title, VRLayout.NORTH);
    this.add(getContaintsGroup(), VRLayout.CLIENT);
    
  }
  /**
   * �^�C�g�����x����Ԃ��܂��B
   * @return
   */
    protected IkenshoDocumentTabTitleLabel getMindBody2Title() {
        // 2006/07/10
        // ��t�ӌ����Ή� - �^�C�g�����x�������ύX�̂���Getter�ǉ�
        // Addition - begin [Masahiko Higuchi]
        return mindBody2Title;
        // Addition - end
    }
    /**
     * mindBody2Pos1 ��Ԃ��܂��B
     * @return mindBody2Pos1
     * @author Masahiko Higuchi
     * @version 3.0.5
     */
    protected IkenshoBodyStatusContainer getMindBody2Pos1() {
        if(mindBody2Pos1 == null){
            mindBody2Pos1 = new IkenshoBodyStatusContainer();
        }
        return mindBody2Pos1;
    }
    /**
     * mindBody2Pos2 ��Ԃ��܂��B
     * @return mindBody2Pos2
     * @author Masahiko Higuchi
     * @version 3.0.5
     */
    protected IkenshoBodyStatusContainer getMindBody2Pos2() {
        if(mindBody2Pos2 == null){
            mindBody2Pos2 = new IkenshoBodyStatusContainer();
        }
        return mindBody2Pos2;
    }
    /**
     * mindBody2Pos3 ��Ԃ��܂��B
     * @return mindBody2Pos3
     * @author Masahiko Higuchi
     * @version 3.0.5
     */
    protected IkenshoBodyStatusContainer getMindBody2Pos3() {
        if(mindBody2Pos3 == null){
            mindBody2Pos3 = new IkenshoBodyStatusContainer();
        }
        return mindBody2Pos3;
    }
    /**
     * mindBody2Pos4 ��Ԃ��܂��B
     * @return mindBody2Pos4
     * @author Masahiko Higuchi
     * @version 3.0.5
     */
    protected IkenshoBodyStatusContainer getMindBody2Pos4() {
        if(mindBody2Pos4 == null){
            mindBody2Pos4 = new IkenshoBodyStatusContainer();
        }
        return mindBody2Pos4;
    }
    /**
     * mindBody2Pos5 ��Ԃ��܂��B
     * @return mindBody2Pos5
     * @author Masahiko Higuchi
     * @version 3.0.5
     */
    protected IkenshoBodyStatusContainer getMindBody2Pos5() {
        if(mindBody2Pos5 == null){
            mindBody2Pos5 = new IkenshoBodyStatusContainer();
        }
        return mindBody2Pos5;
    }

}
