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
    tenteki.setTitle("�ݑ�ҖK��_�H���˂Ɋւ���w���i�S����200����/4�s�ȓ��j");
    tenteki.setCheckVisible(false);
    tenteki.setCode(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA);
    tenteki.setMaxLength(200);
//    tenteki.setUseMaxRows(true);
    tenteki.setRows(4);
    tenteki.setMaxRows(tenteki.getRows());
    tokki.setCode(IkenshoCommon.TEIKEI_HOUMON_TOKKI);
    tokki.setMaxLength(200);
//    tokki.setUseMaxRows(true);
    tokki.setRows(4);
    tokki.setMaxRows(tokki.getRows());
    tokki.setShowSelectMnemonic('J');
    tokki.setShowSelectText("�I��(J)");
    tokki.setCaption("���L���ׂ����ӎ���");
    tokki.setTextBindPath("SIJI_TOKKI");
    tokki.setTitle("���L���ׂ����ӎ����i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��Ă��������B�j");
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
    
    tenteki.setRows(5);
    tokki.setRows(5);
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


}
