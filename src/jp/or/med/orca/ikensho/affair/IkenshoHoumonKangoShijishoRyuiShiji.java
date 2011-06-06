package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoRyuiShiji
    extends IkenshoTabbableChildAffairContainer {
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//  private IkenshoDocumentTabTitleLabel ryuuiShijiTitle = new
//      IkenshoDocumentTabTitleLabel();
//  private ACGroupBox ryuiShijiGrp = new ACGroupBox();
    protected IkenshoDocumentTabTitleLabel ryuuiShijiTitle;
    protected ACGroupBox ryuiShijiGrp;
  // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

  private IkenshoHoumonKangoShijishoInstructContainer seikatsuShidouRyuijikou = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer rehabilitation = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer jyokusou = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer kikiSousaShien = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer other = new
      IkenshoHoumonKangoShijishoInstructContainer();
//  private IkenshoHoumonKangoShijishoInstructContainer tokki = new
//      IkenshoHoumonKangoShijishoInstructContainer();

  public IkenshoHoumonKangoShijishoRyuiShiji() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    //���ӎ�������юw������
    this.setLayout(new VRLayout());
    seikatsuShidouRyuijikou.setCode(IkenshoCommon.
                                    TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU);
    seikatsuShidouRyuijikou.setShowSelectMnemonic('C');
    seikatsuShidouRyuijikou.setShowSelectText("�I��(C)");
    seikatsuShidouRyuijikou.setCaption("�×{�����w����̗��ӎ���");
    seikatsuShidouRyuijikou.setTextBindPath("RSS_RYUIJIKOU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    seikatsuShidouRyuijikou.setTitle(
        " I  �×{�����w����̗��ӎ���(�S����{0}�����ȏ�^{1}�s�ȏ�̓��͂ł́A���[��2���ň������܂�)(���� {2}���� {3}�s)");
    seikatsuShidouRyuijikou.setPageBreakLimitProperty(151, 4);
    seikatsuShidouRyuijikou.setMaxLength(250);
    seikatsuShidouRyuijikou.setMaxRows(5);
    seikatsuShidouRyuijikou.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    seikatsuShidouRyuijikou.setCheckVisible(false);
    rehabilitation.setCheckBindPath("REHA_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    rehabilitation.setCheckText("�P�D���n�r���e�[�V����(���� {2}���� {3}�s)");
    rehabilitation.setPageBreakLimitProperty(151, 4);
    rehabilitation.setMaxLength(250);
    rehabilitation.setMaxRows(5);
    rehabilitation.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    rehabilitation.setCode(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION);
    rehabilitation.setShowSelectMnemonic('D');
    rehabilitation.setShowSelectText("�I��(D)");
    rehabilitation.setCaption("���n�r���e�[�V����");
    rehabilitation.setTextBindPath("REHA_SIJI");
    rehabilitation.setTitle("II  ");
    jyokusou.setCheckBindPath("JOKUSOU_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    jyokusou.setCheckText("�Q�D��ጂ̏��u��(���� {2}���� {3}�s)");
    jyokusou.setPageBreakLimitProperty(151, 4);
    jyokusou.setMaxLength(250);
    jyokusou.setMaxRows(5);
    jyokusou.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    jyokusou.setCode(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU);
    jyokusou.setShowSelectMnemonic('E');
    jyokusou.setShowSelectText("�I��(E)");
    jyokusou.setCaption("��ጂ̏��u��");
    jyokusou.setTextBindPath("JOKUSOU_SIJI");
    jyokusou.setTitle("�@");
    kikiSousaShien.setCheckBindPath("SOUCHAKU_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    kikiSousaShien.setCheckText("�R�D�����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�(���� {2}���� {3}�s)");
    kikiSousaShien.setPageBreakLimitProperty(151, 4);
    kikiSousaShien.setMaxLength(250);
    kikiSousaShien.setMaxRows(5);
    kikiSousaShien.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    kikiSousaShien.setCode(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO);
    kikiSousaShien.setShowSelectMnemonic('G');
    kikiSousaShien.setShowSelectText("�I��(G)");
    kikiSousaShien.setCaption("�����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�");
    kikiSousaShien.setText("");
    kikiSousaShien.setTextBindPath("SOUCHAKU_SIJI");
    kikiSousaShien.setTitle("�@");
    other.setCheckBindPath("RYUI_SIJI_UMU");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    other.setCheckText("�S�D���̑�(���� {2}���� {3}�s)");
    other.setPageBreakLimitProperty(151, 4);
    other.setMaxLength(250);
    other.setMaxRows(5);
    other.setColumns(100);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    other.setCode(IkenshoCommon.TEIKEI_HOUMON_SONOTA);
    other.setShowSelectMnemonic('H');
    other.setShowSelectText("�I��(H)");
    other.setCaption("���̑�");
    other.setTextBindPath("RYUI_SIJI");
    other.setTitle("�@");
//    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
//    tokki.setMaxLength(200);
//    tokki.setMaxRows(4);
//    tokki.setRows(5);
//    tokki.setShowSelectMnemonic('I');
//    tokki.setShowSelectText("�I��(I)");
//    tokki.setCaption("���L���ׂ����ӎ���");
//    tokki.setTextBindPath("SIJI_TOKKI");
//    tokki.setTitle("���L���ׂ����ӎ����i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��Ă��������B�j");
//    tokki.setCheckVisible(false);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    ryuuiShijiTitle.setText("���ӎ����y�юw������");
//    ryuiShijiGrp.setLayout(new VRLayout());
//    ryuiShijiGrp.add(seikatsuShidouRyuijikou, VRLayout.NORTH);
//    ryuiShijiGrp.add(rehabilitation, VRLayout.NORTH);
//    ryuiShijiGrp.add(jyokusou, VRLayout.NORTH);
//    ryuiShijiGrp.add(kikiSousaShien, VRLayout.NORTH);
//    ryuiShijiGrp.add(other, VRLayout.NORTH);
////    ryuiShijiGrp.add(tokki, VRLayout.NORTH);
//    this.add(ryuuiShijiTitle, VRLayout.NORTH);
//    this.add(ryuiShijiGrp, VRLayout.CLIENT);
    getRyuuiShijiTitle().setText("���ӎ����y�юw������");
    getRyuiShijiGrp().setLayout(new VRLayout());
    getRyuiShijiGrp().add(seikatsuShidouRyuijikou, VRLayout.NORTH);
    getRyuiShijiGrp().add(rehabilitation, VRLayout.NORTH);
    getRyuiShijiGrp().add(jyokusou, VRLayout.NORTH);
    getRyuiShijiGrp().add(kikiSousaShien, VRLayout.NORTH);
    getRyuiShijiGrp().add(other, VRLayout.NORTH);
//    ryuiShijiGrp.add(tokki, VRLayout.NORTH);
    this.add(getRyuuiShijiTitle(), VRLayout.NORTH);
    this.add(getRyuiShijiGrp(), VRLayout.CLIENT);
    
    seikatsuShidouRyuijikou.setRows(4);
    rehabilitation.setRows(4);
    jyokusou.setRows(4);
    kikiSousaShien.setRows(4);
    other.setRows(4);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  }

  // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
    /**
     * ryuiShijiGrp ��Ԃ��܂��B
     * 
     * @return ryuiShijiGrp
     */
    protected ACGroupBox getRyuiShijiGrp() {
        if (ryuiShijiGrp == null) {
            ryuiShijiGrp = new ACGroupBox();
        }
        return ryuiShijiGrp;
    }

    /**
     * ryuuiShijiTitle ��Ԃ��܂��B
     * 
     * @return ryuuiShijiTitle
     */
    protected IkenshoDocumentTabTitleLabel getRyuuiShijiTitle() {
        if (ryuuiShijiTitle == null) {
            ryuuiShijiTitle = new IkenshoDocumentTabTitleLabel();
        }
        return ryuuiShijiTitle;
    }
    
    // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
  

    //[ID:0000635][Shin Fujihara] 2011/02/28 add begin �y2010�N�x�v�]�Ή��z
    // --- Override ---
    public boolean noControlWarning() throws Exception {
    	if (getMasterAffair() == null) {
    		return true;
    	}
    	
    	if (getMasterAffair().getCanUpdateCheckStatus() != IkenshoTabbableAffairContainer.CAN_UPDATE_CHECK_STATUS_PRINT) {
    		return true;
    	}
    	
    	if (!(getMasterAffair() instanceof IkenshoHoumonKangoShijisho)) {
    		return true;
    	}
    	
    	IkenshoHoumonKangoShijisho owner = (IkenshoHoumonKangoShijisho)getMasterAffair();
    	
  		//�×{�����w����̗��ӎ���
    	setWarningMessageText(owner, seikatsuShidouRyuijikou, "�×{�����w����̗��ӎ���");
  		//���n�r���e�[�V����
    	setWarningMessageText(owner, rehabilitation, "���n�r���e�[�V����");
  		//��ጂ̏��u��
    	setWarningMessageText(owner, jyokusou, "��ጂ̏��u��");
  		//�����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�
    	setWarningMessageText(owner, kikiSousaShien, "�����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�");
    	//���̑�
    	setWarningMessageText(owner, other, "���̑�");
  		
  		return true;
    }
    
    private void setWarningMessageText(
    		IkenshoHoumonKangoShijisho owner,
    		IkenshoHoumonKangoShijishoInstructContainer target,
    		String contentName) {
    	
  		if (target.isPageBreak()) {
  			owner.setWarningMessage(contentName);
  		}
    }
    //[ID:0000635][Shin Fujihara] 2011/02/28 add end �y2010�N�x�v�]�Ή��z
    
}
