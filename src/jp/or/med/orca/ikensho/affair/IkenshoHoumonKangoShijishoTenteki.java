package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoTenteki
    extends IkenshoTabbableChildAffairContainer {
  // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  //private IkenshoDocumentTabTitleLabel title = new IkenshoDocumentTabTitleLabel();
  //private ACGroupBox group = new ACGroupBox();
  protected IkenshoDocumentTabTitleLabel title;
  protected ACGroupBox group;
  // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  private IkenshoHoumonKangoShijishoInstructContainer tenteki = new
      IkenshoHoumonKangoShijishoInstructContainer();
  private IkenshoHoumonKangoShijishoInstructContainer tokki = new
      IkenshoHoumonKangoShijishoInstructContainer();

  public IkenshoHoumonKangoShijishoTenteki() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    tenteki.setShowSelectMnemonic('I');
    tenteki.setShowSelectText("�I��(I)");
    tenteki.setCaption("�ݑ�ҖK��_�H���˂Ɋւ���w��");
    tenteki.setTextBindPath("TENTEKI_SIJI");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    tenteki.setTitle("�ݑ�ҖK��_�H���˂Ɋւ���w��(�S����{0}�����ȏ�^{1}�s�ȏ�̓��͂ł́A���[��2���ň������܂�)(���� {2}���� {3}�s)");
    tenteki.setPageBreakLimitProperty(201, 5);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    tenteki.setCheckVisible(false);
    tenteki.setCode(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    tenteki.setMaxLength(250);
//    tenteki.setRows(4);
//    tenteki.setMaxRows(tenteki.getRows());
    tenteki.setMaxRows(5);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    tokki.setMaxLength(500);
//    tokki.setRows(4);
//    tokki.setMaxRows(tokki.getRows());
    tokki.setMaxRows(10);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    tokki.setShowSelectMnemonic('J');
    tokki.setShowSelectText("�I��(J)");
    tokki.setCaption("���L���ׂ����ӎ���");
    tokki.setTextBindPath("SIJI_TOKKI");
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    //[ID:0000688][Shin Fujihara] 2012/03/12 replace begin ���C�A�E�g�ύX�Ή�
    //tokki.setTitle("���L���ׂ����ӎ����i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��Ă��������j" + ACConstants.LINE_SEPARATOR + "(���� {2}���� {3}�s)");
    tokki.setTitle("���L���ׂ����ӎ����i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊����A�������E�����Ή��^�K��"
            + ACConstants.LINE_SEPARATOR
            + "���Ō�y�ё�ꍆ�����^�T�[�r�X���p���̗��ӎ�����������΋L�ڂ��ĉ������B�j(���� {2}���� {3}�s)");
    //[ID:0000688][Shin Fujihara] 2012/03/12 replace end
    tokki.setPageBreakLimitProperty(201, 5);
    //[ID:0000634][Masahiko.Higuchi] 2011/02/24 replace end
    tokki.setCheckVisible(false);
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    title.setText("�K��_�H���ˁE���L���ׂ����ӎ���");
//    group.setLayout(new VRLayout());
//    group.add(tenteki, VRLayout.NORTH);
//    group.add(tokki, VRLayout.NORTH);
//    this.add(title, VRLayout.NORTH);
//    this.add(group, VRLayout.CLIENT);
    getTitle().setText("�K��_�H���ˁE���L���ׂ����ӎ���");
    getGroup().setLayout(new VRLayout());
    getGroup().add(tenteki, VRLayout.NORTH);
    getGroup().add(tokki, VRLayout.NORTH);
    this.add(getTitle(), VRLayout.NORTH);
    this.add(getGroup(), VRLayout.CLIENT);

    //[ID:0000634][Masahiko.higuchi] 2011/02/24 replace begin �y2011�N�x�Ή��F�K��Ō�w�����z���[�󎚕������̊g��
    tenteki.setRows(6);
    tokki.setRows(11);
	//[ID:0000634][Masahiko.higuchi] 2011/02/24 replace end
    tenteki.setColumns(100);
    tokki.setColumns(100);
    
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
  }

  // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    /**
     * group ��Ԃ��܂��B
     * 
     * @return group
     */
    protected ACGroupBox getGroup() {
        if (group == null) {
            group = new ACGroupBox();
        }
        return group;
    }

    /**
     * title ��Ԃ��܂��B
     * 
     * @return title
     */
    protected IkenshoDocumentTabTitleLabel getTitle() {
        if (title == null) {
            title = new IkenshoDocumentTabTitleLabel();
        }
        return title;
    }

    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        tenteki.setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TOKUBETSU_CHUSHA_SHIJI",
                "1",
                "�Ǎ�(L)",
                'L',
                "�ŐV�̓��ʖK��Ō�w�����ɓo�^�����u�_�H���ˎw�����e�v��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                        + "��낵���ł����H", true);
        
        tokki.setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "TOKUBETSU_RYUI",
                "1",
                "�Ǎ�(M)",
                'M',
                "�ŐV�̓��ʖK��Ō�w�����ɓo�^�����u���ӎ����y�юw�������v��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                        + "��낵���ł����H", true);
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
    	
    	//�ݑ�ҖK��_�H���˂Ɋւ���w��
    	setWarningMessageText(owner, tenteki, "�ݑ�ҖK��_�H���˂Ɋւ���w��");
    	//���L���ׂ����ӎ���
    	setWarningMessageText(owner, tokki, "���L���ׂ����ӎ���");
  		
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
