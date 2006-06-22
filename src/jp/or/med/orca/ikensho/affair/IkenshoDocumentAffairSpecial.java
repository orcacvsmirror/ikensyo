package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.im.InputSubset;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoSpecialSijiContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoDocumentAffairSpecial extends IkenshoTabbableChildAffairContainer {

  protected VRLabel specialMessage1 = new VRLabel();
  protected VRLabel specialMessage2 = new VRLabel();
  protected VRLabel specialTitle = new VRLabel();
  private ACIntegerCheckBox specialTentekiKannri = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox specialJyoumyakuEiyou = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox specialOtherUse = new ACIntegerCheckBox();
  private ACComboBox specialCannulaSize = new ACComboBox();
  private ACIntegerCheckBox specialJokusou = new ACIntegerCheckBox();
  private ACIntegerCheckBox specialKoumon = new ACIntegerCheckBox();
  private ACComboBox specialDorenPos = new ACComboBox();
  private ACComboBox specialRyuchiCatheterChange = new ACComboBox();
  private ACIntegerCheckBox specialKeikanEiyou = new ACIntegerCheckBox();
  private VRLabel specialMonitorMessage2 = new VRLabel();
  private ACComboBox specialRyuchiCatheterSize = new ACComboBox();
  private VRLabel specialJinkouKokyuMiddle = new VRLabel();
  private ACComboBox specialKeikanEiyouSize = new ACComboBox();
  private ACTextField specialOther = new ACTextField();
  private FlowLayout specialRyuchiCatheterLaout = new FlowLayout();
  private ACIntegerCheckBox specialCannula = new ACIntegerCheckBox();
  private ACIntegerCheckBox specialKyuinnki = new ACIntegerCheckBox();
  private ACComboBox specialKeikanEiyouChange = new ACComboBox();
  private VRLayout specialLayout = new VRLayout();
  private VRPanel specialMonitorPanel = new VRPanel();
  private VRLayout specialGroupLayout = new VRLayout();
  private ACIntegerCheckBox specialTousinKango = new
      ACIntegerCheckBox();
  private ACComboBox specialJinkouKokyuHousiki = new ACComboBox();
  private VRPanel specialOtherPanel = new VRPanel();
  private ACIntegerCheckBox specialKikanSekkai = new ACIntegerCheckBox();
  private VRLabel specialMonitorMessage1 = new VRLabel();
  private ACIntegerCheckBox specialDoren = new ACIntegerCheckBox();
  private ACIntegerCheckBox specialTouseki = new
      ACIntegerCheckBox();
  private ACComboBox specialKeikanEiyouMethod = new ACComboBox();
  private FlowLayout specialOxRyouhouRyoouLaout = new FlowLayout();
  private ACTextField specialOxRyouhouRyou = new ACTextField();
  private ACIntegerCheckBox specialOxRyouhou = new ACIntegerCheckBox();
  private ACIntegerCheckBox specialJinkouBoukou = new
      ACIntegerCheckBox();
  private VRLabel specialKeikanEiyouMiddle2 = new VRLabel();
  private ACIntegerCheckBox specialYuekiPump = new ACIntegerCheckBox();
  private ACParentHesesPanelContainer specialOtherHeses = new ACParentHesesPanelContainer();
  private VRLayout specialOtherLaout = new VRLayout();
  private ACIntegerCheckBox specialJinkouKokyu = new
      ACIntegerCheckBox();
  private ACComboBox specialJinkouKokyuSettei = new ACComboBox();
  private VRLabel specialRyuchiCatheterSizeHead = new VRLabel();
  private VRLabel specialRyuchiCatheterMiddle = new VRLabel();
  private VRLabel specialRyuchiCatheterFoot = new VRLabel();
  private ACIntegerCheckBox specialTousekiKyoukyuu = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox specialJidoFukumaku = new
      ACIntegerCheckBox();
  private ACIntegerCheckBox specialRyuchiCatheter = new
      ACIntegerCheckBox();
  private ACGroupBox specialGroup = new ACGroupBox();
  private ACIntegerCheckBox specialCatheter = new ACIntegerCheckBox();
  private VRLabel specialKeikanEiyouMiddle1 = new VRLabel();
  private ACIntegerCheckBox specialMonitor = new ACIntegerCheckBox();
  private ACParentHesesPanelContainer specialOxRyouhouRyoous = new ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer specialJinkouKokyus = new ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer specialKeikanEiyous = new ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer specialCannulaSizes = new ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer specialDorenPoss = new ACParentHesesPanelContainer();
  private ACParentHesesPanelContainer specialRyuchiCatheters = new ACParentHesesPanelContainer();
  private ACLabelContainer processs = new ACLabelContainer();
  private ACLabelContainer specialTentekiKannris = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialTentekiKannriSiji = new IkenshoSpecialSijiContainer();
  private VRPanel specialMessages = new VRPanel();
  private ACLabelContainer specialJyoumyakuEiyous = new ACLabelContainer();
  private ACLabelContainer specialTousekis = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialTousekiSiji = new IkenshoSpecialSijiContainer();
  private IkenshoSpecialSijiContainer specialJyoumyakuEiyouSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialKoumonPanel = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialKoumonSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialOxRyouhouRyoouss = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialOxRyouhouRyoousSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialJinkouKokyuPanel = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialJinkouKokyuSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialKikanSekkais = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialKikanSekkaiSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialTousinKangos = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialTousinKangoSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialKeikanEiyouPanel = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialKeikanEiyouSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specials = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialMonitorSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer specialMonitors = new ACLabelContainer();
  private VRPanel specialMonitorCaption = new VRPanel();
  private ACLabelContainer specialJokusous = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialJokusouSiji = new IkenshoSpecialSijiContainer();
  private ACLabelContainer sikkins = new ACLabelContainer();
  private ACLabelContainer specialCatheters = new ACLabelContainer();
  private IkenshoSpecialSijiContainer specialCatheterSiji = new IkenshoSpecialSijiContainer();
  private VRLayout processsLayout = new VRLayout();
  private  VRLayout specialProcessLayout = new VRLayout();
  private VRLayout sikkinLayout = new VRLayout();
  private VRLayout specialMonitorCaptionLayout = new VRLayout();

  public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
    applyPoolTeikeibun(specialJinkouKokyuHousiki, IkenshoCommon.TEIKEI_RESPIRATOR_TYPE);
    applyPoolTeikeibun(specialJinkouKokyuSettei, IkenshoCommon.TEIKEI_RESPIRATOR_SETTING);
    applyPoolTeikeibun(specialKeikanEiyouMethod, IkenshoCommon.TEIKEI_TUBE_TYPE);
    applyPoolTeikeibun(specialKeikanEiyouSize, IkenshoCommon.TEIKEI_TUBE_SIZE);
    applyPoolTeikeibun(specialKeikanEiyouChange, IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN);
    applyPoolTeikeibun(specialCannulaSize, IkenshoCommon.TEIKEI_CANURE_SIZE);
    applyPoolTeikeibun(specialDorenPos, IkenshoCommon.TEIKEI_DOREN_POS_NAME);
    applyPoolTeikeibun(specialRyuchiCatheterSize, IkenshoCommon.TEIKEI_CATHETER_SIZE);
    applyPoolTeikeibun(specialRyuchiCatheterChange, IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN);


  }

  protected void bindSourceInnerBindComponent() throws Exception {
    super.bindSourceInnerBindComponent();

    if (! ("".equals(specialOther.getText()))) {
      specialOtherUse.setSelected(true);
    }
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoDocumentAffairSpecial() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    specialOxRyouhou.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialOxRyouhouRyoous, specialOxRyouhouRyou}));

    specialJinkouKokyu.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialJinkouKokyus, specialJinkouKokyuHousiki,
        specialJinkouKokyuMiddle, specialJinkouKokyuSettei}));

    specialKeikanEiyou.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialKeikanEiyouMethod, specialKeikanEiyouMiddle1,
        specialKeikanEiyouSize, specialKeikanEiyouMiddle2,
        specialKeikanEiyouChange, specialKeikanEiyous}));

    specialCannula.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialCannulaSizes, specialCannulaSize}));

    specialDoren.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialDorenPos, specialDorenPoss}));

    specialOtherUse.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialOther, specialOtherHeses}));

    specialRyuchiCatheter.addItemListener(new ACFollowDisabledItemListener(new
        JComponent[] {specialRyuchiCatheterSizeHead, specialRyuchiCatheterSize,
        specialRyuchiCatheterMiddle, specialRyuchiCatheterChange,
        specialRyuchiCatheterFoot}));

  }
  private void jbInit() throws Exception {
    specialGroupLayout.setFitHLast(true);
    specialGroupLayout.setAutoWrap(false);
    processsLayout.setAutoWrap(false);
    processsLayout.setFitHLast(true);
    processsLayout.setHgap(0);
    processsLayout.setVgap(0);
    specialProcessLayout.setAutoWrap(false);
    specialProcessLayout.setFitHLast(true);
    specialProcessLayout.setHgap(0);
    specialProcessLayout.setVgap(0);
    sikkinLayout.setAutoWrap(false);
    sikkinLayout.setFitHLast(true);
    sikkinLayout.setHgap(0);
    sikkinLayout.setVgap(0);
    specialMonitorCaptionLayout.setVgap(0);
    specialMonitorCaptionLayout.setHgap(0);
    specialOtherLaout.setVgap(0);
    specialOtherLaout.setAutoWrap(false);
    specialOtherLaout.setHgap(0);

    specialGroup.setLayout(specialGroupLayout);
    specials.setLayout(specialProcessLayout);
    processs.setLayout(processsLayout);
    sikkins.setLayout(sikkinLayout);
    setLayout(specialLayout);
    specialMonitorCaption.setLayout(specialMonitorCaptionLayout);
    specialOtherPanel.setLayout(specialOtherLaout);
    specialOxRyouhouRyoouss.setLayout(new BorderLayout());
    specialJinkouKokyuPanel.setLayout(new BorderLayout());
    specialKeikanEiyouPanel.setLayout(new BorderLayout());
    specialJyoumyakuEiyous.setLayout(new BorderLayout());
    specialTentekiKannris.setLayout(new BorderLayout());
    specialKikanSekkais.setLayout(new BorderLayout());
    specialTousinKangos.setLayout(new BorderLayout());
    specialKoumonPanel.setLayout(new BorderLayout());
    specialMessages.setLayout(new BorderLayout());
    specialTousekis.setLayout(new BorderLayout());
    specialMonitors.setLayout(new BorderLayout());
    specialJokusous.setLayout(new BorderLayout());

    processs.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
    processs.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    processs.setLabelColumns(7);
    processs.setContentAreaFilled(true);
    specials.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
    specials.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    specials.setLabelColumns(7);
    specials.setContentAreaFilled(true);
    sikkins.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
    sikkins.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    sikkins.setContentAreaFilled(true);

   ((VRLayout)specialRyuchiCatheters.getLayout()).setAutoWrap(false);
   ((VRLayout) specialKeikanEiyous.getLayout()).setAutoWrap(false);
   ((VRLayout)specialJinkouKokyus.getLayout()).setAutoWrap(false);

    specialMonitorCaption.setOpaque(false);
    specialMonitorCaption.setPreferredSize(new Dimension(140, 60));
    sikkins.setText("���ււ̑Ή�");
    sikkins.setLabelColumns(7);
    specialCatheters.setLayout(new BorderLayout());
    specialMonitorPanel.setOpaque(false);
    specialKeikanEiyou.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    specialLayout.setAutoWrap(false);
    specialYuekiPump.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
    specialOxRyouhouRyoous.setEnabled(false);
    specialOxRyouhouRyou.setEnabled(false);
    specialJinkouKokyuHousiki.setEnabled(false);
    specialJinkouKokyuHousiki.setPreferredSize(new Dimension(100, 19));
    specialJinkouKokyuMiddle.setEnabled(false);
    specialJinkouKokyuSettei.setEnabled(false);
    specialJinkouKokyuSettei.setPreferredSize(new Dimension(250, 19));
    specialJinkouKokyus.setEnabled(false);
    specialKeikanEiyouMethod.setEnabled(false);
    specialKeikanEiyouMiddle1.setEnabled(false);
    specialKeikanEiyouSize.setEnabled(false);
    specialKeikanEiyouMiddle2.setEnabled(false);
    specialKeikanEiyous.setEnabled(false);
    specialCannulaSizes.setEnabled(false);
    specialCannulaSize.setEnabled(false);
    specialCannulaSize.setMinimumSize(new Dimension(60, 19));
    specialCannulaSize.setPreferredSize(new Dimension(120, 19));
    specialDorenPoss.setEnabled(false);
    specialDorenPos.setEnabled(false);
    specialDorenPos.setPreferredSize(new Dimension(200, 19));
    specialOtherHeses.setOpaque(false);
    specialOtherHeses.setEnabled(false);
    specialRyuchiCatheterSize.setEnabled(false);
    specialRyuchiCatheterChange.setEnabled(false);
    specialRyuchiCatheterSizeHead.setEnabled(false);
    specialRyuchiCatheterMiddle.setEnabled(false);
    specialRyuchiCatheterFoot.setEnabled(false);
    specialKeikanEiyouChange.setEnabled(false);
    specialOther.setEnabled(false);
    specialOtherUse.setBindPath("SOUCHAKU_OTHER_FLAG");
    specialJidoFukumaku.setPreferredSize(new Dimension(150, 17));
    specialGroup.add(processs, VRLayout.NORTH);
    specialGroup.add(specials, VRLayout.NORTH);
    specialGroup.add(sikkins, VRLayout.NORTH);
    specialKeikanEiyous.add(specialKeikanEiyouMethod, null);
    specialKeikanEiyous.add(specialKeikanEiyouMiddle1, null);
    specialKeikanEiyous.add(specialKeikanEiyouSize, null);
    specialKeikanEiyous.add(specialKeikanEiyouMiddle2, null);
    specialKeikanEiyous.add(specialKeikanEiyouChange, null);
    specialKeikanEiyouPanel.add(specialKeikanEiyou, BorderLayout.WEST);
    specialKeikanEiyouPanel.add(specialKeikanEiyouSiji, BorderLayout.CENTER);
    specialDorenPoss.add(specialDorenPos, null);
    specialCannulaSizes.add(specialCannulaSize, null);
    specialTousinKangos.add(specialTousinKangoSiji, BorderLayout.CENTER);
    specialTousinKangos.add(specialTousinKango, BorderLayout.WEST);
    specialKikanSekkais.add(specialKikanSekkaiSiji, BorderLayout.CENTER);
    specialKikanSekkais.add(specialKikanSekkai, BorderLayout.WEST);
    specialJinkouKokyus.add(specialJinkouKokyuHousiki, VRLayout.WEST);
    specialJinkouKokyus.add(specialJinkouKokyuMiddle, VRLayout.WEST);
    specialJinkouKokyus.add(specialJinkouKokyuSettei, VRLayout.CLIENT);
    specialKeikanEiyouSiji.add(specialKeikanEiyous, VRLayout.FLOW_RETURN);
    specialKeikanEiyouSiji.add(specialCannula, VRLayout.FLOW);
    specialKeikanEiyouSiji.add(specialCannulaSizes, VRLayout.FLOW_RETURN);
    
    ((VRLayout)specialKeikanEiyouSiji.getLayout()).setVLineAlign(VRLayout.CENTER);

    specialKeikanEiyouSiji.add(specialDoren, VRLayout.FLOW);
    specialKeikanEiyouSiji.add(specialDorenPoss, VRLayout.FLOW_RETURN);
    specialKeikanEiyouSiji.add(specialOtherPanel, VRLayout.FLOW_RETURN);
    specialKikanSekkaiSiji.add(specialKyuinnki, null);
    specialJinkouKokyuSiji.add(specialJinkouKokyus, null);
    specialJinkouKokyuPanel.add(specialJinkouKokyu, BorderLayout.WEST);
    specialJinkouKokyuPanel.add(specialJinkouKokyuSiji, BorderLayout.CENTER);
    specialOxRyouhouRyoouss.add(specialOxRyouhouRyoousSiji, BorderLayout.CENTER);
    specialOxRyouhouRyoousSiji.add(specialOxRyouhouRyoous, VRLayout.FLOW_RETURN);
    specialOxRyouhouRyoous.add(specialOxRyouhouRyou, null);
    specialOxRyouhouRyoouss.add(specialOxRyouhou, BorderLayout.WEST);
    specialKoumonPanel.add(specialKoumonSiji, BorderLayout.CENTER);
    specialKoumonSiji.add(specialJinkouBoukou, VRLayout.FLOW_RETURN);
    specialKoumonPanel.add(specialKoumon, BorderLayout.WEST);
    specialTousekiSiji.add(specialJidoFukumaku, VRLayout.FLOW);
    specialTousekiSiji.add(specialTousekiKyoukyuu, VRLayout.FLOW_RETURN);
    specialTousekis.add(specialTouseki, BorderLayout.WEST);
    specialTousekis.add(specialTousekiSiji, BorderLayout.CENTER);
    specialJyoumyakuEiyous.add(specialJyoumyakuEiyou, BorderLayout.WEST);
    specialJyoumyakuEiyous.add(specialJyoumyakuEiyouSiji, BorderLayout.CENTER);
    specialMessages.add(specialMessage1, BorderLayout.NORTH);
    specialMessages.add(specialMessage2, BorderLayout.CENTER);
    processs.add(specialTentekiKannris, VRLayout.FLOW_RETURN);
    processs.add(specialJyoumyakuEiyous, VRLayout.FLOW_RETURN);
    processs.add(specialTousekis, VRLayout.FLOW_RETURN);
    processs.add(specialKoumonPanel, VRLayout.FLOW_RETURN);
    processs.add(specialOxRyouhouRyoouss, VRLayout.FLOW_RETURN);
    processs.add(specialJinkouKokyuPanel, VRLayout.FLOW_RETURN);
    processs.add(specialKikanSekkais, VRLayout.FLOW_RETURN);
    processs.add(specialTousinKangos, VRLayout.FLOW_RETURN);
    processs.add(specialKeikanEiyouPanel, VRLayout.FLOW_RETURN);
    specialTentekiKannriSiji.add(specialYuekiPump,  VRLayout.WEST);
    specialTentekiKannriSiji.add(specialMessages, VRLayout.EAST);
    specialTentekiKannris.add(specialTentekiKannri, BorderLayout.WEST);
    specialTentekiKannris.add(specialTentekiKannriSiji, BorderLayout.CENTER);
    specialMonitorPanel.add(specialMonitorMessage1, BorderLayout.NORTH);
    specialMonitorPanel.add(specialMonitorMessage2, BorderLayout.CENTER);
    specialMonitorCaption.add(specialMonitor, VRLayout.FLOW_RETURN);
    specialMonitorCaption.add(specialMonitorPanel, VRLayout.FLOW_RETURN);
    specialMonitors.add(specialMonitorSiji, BorderLayout.CENTER);
    specialMonitors.add(specialMonitorCaption, BorderLayout.WEST);
    specials.add(specialMonitors, VRLayout.FLOW_RETURN);
    specials.add(specialJokusous, VRLayout.FLOW_RETURN);
    specialJokusous.add(specialJokusou,  BorderLayout.WEST);


    specialTitle.setText("�Q�D���ʂȈ�Ái�ߋ�14���Ԉȓ��Ɏ󂯂���Â̂��ׂĂɃ`�F�b�N�j");
    specialTitle.setForeground(IkenshoConstants.COLOR_PANEL_TITLE_FOREGROUND);
    specialTitle.setOpaque(true);
    specialTitle.setBackground(IkenshoConstants.COLOR_PANEL_TITLE_BACKGROUND);
    specialCannulaSize.setBindPath("CANNULA_SIZE");
    specialCannulaSize.setMaxLength(5);
    specialCannulaSize.setIMEMode(InputSubset.KANJI);
    specialOtherUse.setText("���̑��i���̑��̑�����g�p��Ë@��j");
    specialOtherUse.setOpaque(false);
    specialJyoumyakuEiyou.setBindPath("CHU_JOU_EIYOU");
    specialJyoumyakuEiyou.setPreferredSize(new Dimension(140, 20));
    specialJyoumyakuEiyou.setText("���S�Ö��h�{");
    specialTentekiKannri.setBindPath("TNT_KNR");
    specialTentekiKannri.setPreferredSize(new Dimension(140, 25));
    specialTentekiKannri.setText("�_�H�̊Ǘ�");
    specialTentekiKannri.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
    specialJokusou.setPreferredSize(new Dimension(140, 20));
    specialJokusou.setText("��ጂ̏��u");
    specialJokusou.setBindPath("JOKUSOU_SHOCHI");
    specialKoumon.setPreferredSize(new Dimension(140, 20));
    specialKoumon.setText("�X�g�[�}�̏��u");
    specialKoumon.setBindPath("JINKOU_KOUMON");
    specialDorenPos.setActionCommand("comboBoxChanged");
    specialDorenPos.setIMEMode(InputSubset.KANJI);
    specialDorenPos.setMaxLength(10);
    specialDorenPos.setBindPath("DOREN_BUI");
    specialRyuchiCatheterChange.setIMEMode(InputSubset.KANJI);
    specialRyuchiCatheterChange.setBindPath("RYU_CAT_CHG");
    specialRyuchiCatheterChange.setMaxLength(5);
    specialRyuchiCatheterChange.setPreferredSize(new Dimension(80, 19));
    specialKeikanEiyou.setPreferredSize(new Dimension(140, 20));
    specialKeikanEiyou.setText("�o�ǉh�{");
    specialKeikanEiyou.setBindPath("KEKN_EIYOU");
    specialMonitorMessage2.setText("�@�_�f�O�a�x���j");
    specialRyuchiCatheterSize.setIMEMode(InputSubset.KANJI);
    specialRyuchiCatheterSize.setBindPath("RYU_CAT_SIZE");
    specialRyuchiCatheterSize.setMaxLength(5);
    specialRyuchiCatheterSize.setPreferredSize(new Dimension(80, 19));
    specialJinkouKokyuMiddle.setOpaque(false);
    specialJinkouKokyuMiddle.setText("�F�ݒ�");
    specialKeikanEiyouSize.setIMEMode(InputSubset.KANJI);
    specialKeikanEiyouSize.setPreferredSize(new Dimension(80, 19));
    specialKeikanEiyouSize.setMaxLength(5);
    specialKeikanEiyouSize.setBindPath("KEKN_EIYOU_SIZE");
    specialOther.setIMEMode(InputSubset.KANJI);
    specialOther.setColumns(20);
    specialOther.setBindPath("SOUCHAKU_OTHER");
    specialOther.setMaxLength(15);
    specialRyuchiCatheterLaout.setAlignment(FlowLayout.LEFT);
    specialCannula.setOpaque(false);
    specialCannula.setPreferredSize(new Dimension(120, 30));
    specialCannula.setText("�C�ǃJ�j���[��");
    specialCannula.setBindPath("CANNULA");
    specialKyuinnki.setOpaque(false);
    specialKyuinnki.setText("�z����");
    specialKyuinnki.setBindPath("KYUINKI");
    specialKeikanEiyouChange.setIMEMode(InputSubset.KANJI);
    specialKeikanEiyouChange.setPreferredSize(new Dimension(80, 19));
    specialKeikanEiyouChange.setMaxLength(5);
    specialKeikanEiyouChange.setBindPath("KEKN_EIYOU_CHG");
    specialLayout.setFitHLast(true);
    specialLayout.setFitVLast(true);
    specialMonitorPanel.setLayout(new BorderLayout());
    specialTousinKango.setPreferredSize(new Dimension(140, 20));
    specialTousinKango.setText("�u�ɂ̊Ō�");
    specialTousinKango.setBindPath("TOUTU");
    specialJinkouKokyuHousiki.setIMEMode(InputSubset.KANJI);
    specialJinkouKokyuHousiki.setMaxLength(5);
    specialJinkouKokyuHousiki.setBindPath("JINKOU_KKY_HOUSIKI");
    specialOtherPanel.setOpaque(false);
    specialKikanSekkai.setPreferredSize(new Dimension(140, 20));
    specialKikanSekkai.setText("�C�ǐ؊J�̏��u");
    specialKikanSekkai.setBindPath("KKN_SEK_SHOCHI");
    specialMonitorMessage1.setMinimumSize(new Dimension(45, 15));
    specialMonitorMessage1.setText("�i�����A�S���A");
    specialDoren.setOpaque(false);
    specialDoren.setText("�h���[��");
    specialDoren.setBindPath("DOREN");
    specialTouseki.setPreferredSize(new Dimension(140, 20));
    specialTouseki.setText("����");
    specialTouseki.setBindPath("TOUSEKI");
    specialKeikanEiyouMethod.setIMEMode(InputSubset.KANJI);
    specialKeikanEiyouMethod.setMinimumSize(new Dimension(119, 19));
    specialKeikanEiyouMethod.setPreferredSize(new Dimension(80, 19));
    specialKeikanEiyouMethod.setMaxLength(5);
    specialKeikanEiyouMethod.setBindPath("KEKN_EIYOU_METHOD");
//    specialSettings1.setLayout(specialSetting1Layout);
    specialOxRyouhouRyoouLaout.setAlignment(FlowLayout.LEFT);
    specialOxRyouhouRyou.setColumns(5);
    specialOxRyouhouRyou.setHorizontalAlignment(SwingConstants.RIGHT);
    specialOxRyouhouRyou.setIMEMode(InputSubset.LATIN_DIGITS);
    specialOxRyouhouRyou.setBindPath("OX_RYO_RYO");
    specialOxRyouhouRyou.setMaxLength(5);
    specialOxRyouhou.setPreferredSize(new Dimension(140, 20));
    specialOxRyouhou.setText("�_�f�Ö@");
    specialOxRyouhou.setBindPath("OX_RYO");
    specialMessage1.setForeground(Color.blue);
    specialMessage1.setToolTipText("");
    specialMessage1.setText("�u�K��Ō�w�����v�쐬�ɕK�v�ȍ��ڂł��B");
    specialJinkouBoukou.setOpaque(false);
    specialJinkouBoukou.setText("�l�H�N��");
    specialJinkouBoukou.setBindPath("JINKOU_BOUKOU");
    specialKeikanEiyouMiddle2.setText("�A");
    specialYuekiPump.setOpaque(false);
    specialYuekiPump.setText("�A�t�|���v");
    specialYuekiPump.setBindPath("YUEKI_PUMP");
    specialOtherLaout.setAlignment(FlowLayout.LEFT);
    specialOtherLaout.setHgap(0);
    specialJinkouKokyu.setPreferredSize(new Dimension(140, 20));
    specialJinkouKokyu.setText("���X�s���[�^�[");
    specialJinkouKokyu.setBindPath("JINKOU_KOKYU");
    specialJinkouKokyuSettei.setIMEMode(InputSubset.KANJI);
    specialJinkouKokyuSettei.setMaxLength(10);
    specialJinkouKokyuSettei.setBindPath("JINKOU_KKY_SET");
    specialRyuchiCatheterSizeHead.setText("�T�C�Y");
    specialRyuchiCatheterMiddle.setText("�A");
    specialRyuchiCatheterFoot.setText("����1�����");
    specialTousekiKyoukyuu.setOpaque(false);
    specialTousekiKyoukyuu.setPreferredSize(new Dimension(150, 17));
    specialTousekiKyoukyuu.setText("���͉t�������u");
    specialTousekiKyoukyuu.setBindPath("TOU_KYOUKYU");
    specialJidoFukumaku.setOpaque(false);
    specialJidoFukumaku.setText("���������󗬑��u");
    specialJidoFukumaku.setBindPath("JD_FUKU");
    specialRyuchiCatheter.setOpaque(false);
    specialRyuchiCatheter.setText("���u�J�e�[�e���F");
    specialRyuchiCatheter.setBindPath("RYU_CATHETER");
    specialCatheter.setPreferredSize(new Dimension(140, 20));
    specialCatheter.setText("�J�e�[�e��");
    specialCatheter.setBindPath("CATHETER");
    specialMessage2.setForeground(Color.blue);
    specialMessage2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
    specialMessage2.setText("�u�厡��ӌ����v�ł͈������܂���B");
    specialKeikanEiyouMiddle1.setText("�jl/min");
    specialKeikanEiyouMiddle1.setText("�F�`���[�u�T�C�Y");
    specialMonitor.setText("���j�^�[����");
    specialMonitor.setBindPath("MONITOR");

    specialOxRyouhouRyoous.setMinimumSize(new Dimension(54, 21));
    specialOxRyouhouRyoous.setOpaque(false);
    specialOxRyouhouRyoous.setEndText("�jl/min");
    specialJinkouKokyus.setMinimumSize(new Dimension(300, 19));
    specialJinkouKokyus.setOpaque(false);
    specialKeikanEiyous.setOpaque(false);
    specialKeikanEiyous.setEndText("����1������j");
    specialCannulaSizes.setOpaque(false);
    specialCannulaSizes.setBeginText("�i�T�C�Y");
    specialDorenPoss.setOpaque(false);
    specialDorenPoss.setBeginText("�i����");
    specialRyuchiCatheters.setOpaque(false);
    processs.setText("���u���e");
    specials.setText("���ʂȑΉ�");

    specialMessages.setOpaque(false);


    specialMonitorPanel.add(specialMonitorMessage1, BorderLayout.NORTH);
    specialMonitorPanel.add(specialMonitorMessage2, BorderLayout.CENTER);
    specialOtherPanel.add(specialOtherUse, VRLayout.FLOW);
    specialOtherPanel.add(specialOtherHeses, VRLayout.FLOW);
    specialOtherHeses.add(specialOther, null);
    specialRyuchiCatheters.add(specialRyuchiCatheter, null);
    specialRyuchiCatheters.add(specialRyuchiCatheterSizeHead, null);
    specialRyuchiCatheters.add(specialRyuchiCatheterSize, null);
    specialRyuchiCatheters.add(specialRyuchiCatheterMiddle, null);
    specialRyuchiCatheters.add(specialRyuchiCatheterChange, null);
    specialRyuchiCatheters.add(specialRyuchiCatheterFoot, null);
    this.add(specialTitle, VRLayout.NORTH);
    this.add(specialGroup, VRLayout.CLIENT);
    specialMessages.add(specialMessage1,  BorderLayout.NORTH);
    specialMessages.add(specialMessage2, BorderLayout.SOUTH);
    specialJokusous.add(specialJokusouSiji,  BorderLayout.CENTER);
    sikkins.add(specialCatheters, null);
    specialCatheters.add(specialCatheter, BorderLayout.WEST);
    specialCatheters.add(specialCatheterSiji,  BorderLayout.CENTER);
    specialCatheterSiji.add(specialRyuchiCatheters, VRLayout.FLOW_RETURN);
  }
}
