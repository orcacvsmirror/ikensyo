package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouShousai extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel tabPnl = new VRPanel();
    private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    private IkenshoTabbableChildAffairContainer kihon = new IkenshoIryouKikanJouhouShousaiKihon();
    private IkenshoTabbableChildAffairContainer kanren = new IkenshoIryouKikanJouhouShousaiKanren();
    private ArrayList tabArray = new ArrayList();

    private VRMap doctorData;

    private String drCd; //�A�g��R�[�h
    private boolean isUpdate; //true : �X�V, false : �ǉ�
    private boolean hasData; //true : �L, false : ��
    private int initJigyoushaCnt; //�����\�����̎��ƎҐ�
    protected VRMap prevData; //�O��ʃL���b�V��

    private static final ACPassiveKey PASSIVE_CHECK_KEY_DOCTOR = new
        ACPassiveKey("DOCTOR", new String[] {"DR_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_KEY_JIGYOUSHA = new
        ACPassiveKey("JIGYOUSHA", new String[] {"DR_CD", "INSURER_NO", "JIGYOUSHA_NO"}
                          , new Format[] {null, IkenshoConstants.FORMAT_PASSIVE_STRING, IkenshoConstants.FORMAT_PASSIVE_STRING}
                          , "LAST_TIME", "LAST_TIME");

    public IkenshoIryouKikanJouhouShousai() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        //ArrayList�ɒǉ�
        tabArray.add(kihon);
        tabArray.add(kanren);

    }

    private void jbInit() throws Exception {
        this.add(buttons, VRLayout.NORTH);
        this.add(tabPnl, VRLayout.CLIENT);

        buttons.setTitle("��Ë@�֏��ڍ�");
        buttons.add(update, VRLayout.EAST);

        update.setText("�o�^(S)");
        update.setMnemonic('S');
        update.setActionCommand("�o�^(S)");
        update.setToolTipText("���݂̓��e��o�^���܂��B");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        tabPnl.setLayout(new BorderLayout());
        tabPnl.add(tabs, BorderLayout.CENTER);
        tabs.addTab("��{���", kihon);
        tabs.addTab("�������֘A���", kanren);
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        //�X�i�b�v�V���b�g�Ώېݒ�
        IkenshoSnapshot.getInstance().setRootContainer(tabPnl);
        //���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        addUpdateTrigger(update);
        addTableSelectedTrigger(((IkenshoIryouKikanJouhouShousaiKanren) kanren).getTable());

        //�O��ʂ�����p���̃f�[�^���擾�E�ݒ�
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            if (VRBindPathParser.has("PREV_DATA", params)) {
                prevData = (VRMap) VRBindPathParser.get("PREV_DATA", params);
            }

            //�O��ʂɂāA�ǂ̃{�^����������Ă̑J�ڂ����擾
            String act = String.valueOf(params.get("ACT"));
            if (act.equals("insert")) {
                isUpdate = false;
                hasData = false;
            }
            else if (act.equals("copy")) {
                isUpdate = false;
                hasData = true;
            }
            else if (act.equals("detail")) {
                isUpdate = true;
                hasData = true;
            }

            //�{�^���̃L���v�V����
            if (isUpdate) {
                update.setText("�X�V(S)");
                update.setToolTipText("���݂̓��e���X�V���܂��B");
            }
            else {
                update.setText("�o�^(S)");
                update.setToolTipText("���݂̓��e��o�^���܂��B");
            }

            //�n�莞�̏��S����DR_CD���擾
            if (hasData) {
                drCd = String.valueOf(VRBindPathParser.get("DR_CD", params));
            }

            //���͗��Ƀf�[�^��ݒ�
            doSelect();

            //�����\�����̎��ƎҐ���ޔ�
            initJigyoushaCnt = ((IkenshoIryouKikanJouhouShousaiKanren) kanren).getJigyoushoData().getDataSize();
        }
        else {
            throw new Exception("�s���ȃp�����[�^���n����܂����B");
        }
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return ((IkenshoIryouKikanJouhouShousaiKihon)kihon).getFirstFocusComponent();
    }

    public boolean canBack(VRMap parameters) throws Exception {
        String key = "DR_CD";
        String value = drCd; //(UPDATE�O�̒l)

        //�O��ʃL���b�V�����Đݒ�
        if (prevData != null) {
          parameters.put("PREV_DATA", prevData);
        }

        if (IkenshoSnapshot.getInstance().isModified()) {
            //MSG, Caption�̐ݒ�
            String msg;
            String btnCaption;
            if (isUpdate) {
                msg = "�ύX����Ă��܂��B�ۑ����܂����H";
                btnCaption = "�X�V���Ė߂�(S)";
            }
            else {
                msg = "�o�^���e��ۑ����܂����H";
                btnCaption = "�o�^���Ė߂�(S)";
            }
            int result = ACMessageBox.showYesNoCancel(msg,
                btnCaption, 'S',
                "�j�����Ė߂�(R)", 'R');

            //DLG����
            if (result == ACMessageBox.RESULT_YES) { //�ۑ����Ė߂�
                boolean updateFlg = doUpdate(); //UPDATE����:true, ���s:false
                parameters.put(key, drCd); //�l��UPDATE��̂��̂łȂ���΂Ȃ�Ȃ�
                return updateFlg;
            }
            else if (result == ACMessageBox.RESULT_NO) { //�ۑ����Ȃ��Ŗ߂�
                if (!isNullText(value)) {
                    parameters.put(key, value);
                }
                return true;
            }
            else { //�߂�Ȃ�
                return false;
            }
        }
        else { //�߂�
            if (!isNullText(value)) {
                parameters.put(key, value);
            }
            return true;
        }
    }

    public boolean canClose() throws Exception {
        if (IkenshoSnapshot.getInstance().isModified()) {
            String msg = "";
            msg = "�X�V����Ă��܂��B\n�I�����Ă���낵���ł����H";
            int result = ACMessageBox.show(msg,
                                                ACMessageBox.
                                                BUTTON_OKCANCEL,
                                                ACMessageBox.ICON_QUESTION,
                                                ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (doUpdate()) { //update�������A�ꗗ��ʂɖ߂�
            ACFrame.getInstance().back();
        }
    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        ((IkenshoIryouKikanJouhouShousaiKanren)kanren).setButtonsEnabled();
    }

    /**
     * �f�[�^�擾�������s���܂�
     * @throws Exception
     */
    private void doSelect() throws Exception {
        //�X�i�b�v�V���b�g�B�e(�R�s�[���p)
        IkenshoSnapshot.getInstance().snapshot();

        //DB����f�[�^���擾
        doSelectFromDBAsDoctor();
        doSelectFromDBAsJigyousha();

        //�R�s�[���ȊO�́A���߂ăX�i�b�v�V���b�g���B��Ȃ���
        if (isUpdate) {
            IkenshoSnapshot.getInstance().snapshot();
        }
        else if(!hasData) {
            IkenshoSnapshot.getInstance().snapshot();
        }

        //�X�e�[�^�X�o�[
        setStatusText("��Ë@�֏��ڍ�");
    }

    /**
     * DB����f�[�^���擾���܂��B
     * @throws Exception
     */
    private void doSelectFromDBAsDoctor() throws Exception {
        VRArrayList doctorArray = null;
        if (hasData) {
            //�L�[�����ɁADB����f�[�^���擾
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append( " SELECT" );
            sb.append( " DR_CD" );
            sb.append( ",DR_NM" );
            sb.append( ",MI_NM" );
            sb.append( ",MI_POST_CD" );
            sb.append( ",MI_ADDRESS" );
            sb.append( ",MI_TEL1" );
            sb.append( ",MI_TEL2" );
            sb.append( ",MI_FAX1" );
            sb.append( ",MI_FAX2" );
            sb.append( ",MI_CEL_TEL1" );
            sb.append( ",MI_CEL_TEL2" );
            sb.append( ",KINKYU_RENRAKU" );
            sb.append( ",FUZAIJI_TAIOU" );
            sb.append( ",BIKOU" );
            sb.append( ",JIGYOUSHA_NO" );
            sb.append( ",MI_DEFAULT" );
            sb.append( ",KAISETUSHA_NM" );
            sb.append( ",DR_NO" );
            sb.append( ",MI_KBN" );
            sb.append( ",BANK_NM" );
            sb.append( ",BANK_SITEN_NM" );
            sb.append( ",BANK_KOUZA_NO" );
            sb.append( ",BANK_KOUZA_KIND" );
            sb.append( ",FURIKOMI_MEIGI" );
            sb.append( ",LAST_TIME" );
            sb.append( " FROM" );
            sb.append( " DOCTOR" );
            sb.append( " WHERE" );
            sb.append( " DR_CD=" + drCd );

            doctorArray = (VRArrayList) dbm.executeQuery(sb.toString());

            if (doctorArray.getDataSize() > 0) { //DB��Ƀf�[�^�L
                doctorData = (VRMap)doctorArray.getData();
                doctorData.setData("TABLE_CHANGE_FLG", new Boolean(false));
                doctorData.setData("BANK_KOUZA_KIND",
                                   getBankKouzaKindPropertyValue(String.valueOf(doctorData.getData("BANK_KOUZA_KIND")))); //�����l������Ȃ��߁B
                //�p�b�V�u�`�F�b�N�p
                clearReservedPassive();
                reservedPassive(PASSIVE_CHECK_KEY_DOCTOR, doctorArray);
            }
            else {
                doctorData = (VRMap)tabPnl.createSource(); //DB��Ƀf�[�^��
            }
        }
        else { //�n��f�[�^��
            doctorData = (VRMap)tabPnl.createSource();
        }
        tabPnl.setSource(doctorData);
        tabPnl.bindSource();
    }

    /**
     * DB����f�[�^���擾���܂��B
     * @throws Exception
     */
    private void doSelectFromDBAsJigyousha() throws Exception {
        //�L�[�Ƃ����t�R�[�h��ݒ肷��B�V�K�̏ꍇ��-1(�q�b�g���Ȃ��L�[)�Ƃ��Ă���
        String drCdTmp;
        if (hasData) {
            drCdTmp = drCd;
        }
        else {
            drCdTmp = "-1";
        }

        //�e�[�u���Ƀf�[�^��ݒ肷��
        ((IkenshoIryouKikanJouhouShousaiKanren)kanren).doSelect(drCdTmp);

        VRArrayList jigyoushoData = ((IkenshoIryouKikanJouhouShousaiKanren)kanren).getJigyoushoData();
        initJigyoushaCnt = jigyoushoData.getDataSize();
        if (jigyoushoData.getDataSize() > 0) {
            //�p�b�V�u�`�F�b�N
            reservedPassive(PASSIVE_CHECK_KEY_JIGYOUSHA, jigyoushoData);
        }
    }

    /**
     * �o�^�E�X�V����
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdate() throws Exception {
        //���̓`�F�b�N
        if (canControlUpdate()) {
            //DBUPDATE
            if (!doUpdateToDB()) {
                return false;
            }

            //�㏈��(�u�V�K�v�A�y�сu�����v���[�h�œo�^������A�u�X�V�v���[�h�ֈڍs����)
            String msg = new String();
            if (isUpdate){
                msg = "�X�V����܂����B";
            }
            else {
                msg = "�o�^����܂����B";
                isUpdate = true;
                hasData = true;
                update.setText("�X�V(S)");
                update.setToolTipText("���݂̓��e���X�V���܂��B");
            }

            //�X�i�b�v�V���b�g�B�e
            IkenshoSnapshot.getInstance().snapshot();

            //���������ʒmMsg�\��
            ACMessageBox.show(msg,
                                   ACMessageBox.BUTTON_OK,
                                   ACMessageBox.ICON_INFOMATION);
            return true;
        }
        return false;
    }

    /**
     * DB�ɑ΂��čX�V���s���B
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        //update
        String drCdOld = drCd;
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

        try {
            //���̓f�[�^���擾
            tabPnl.applySource();

            //�p�b�V�u�`�F�b�N
            if (isUpdate) {
                clearPassiveTask();
                addPassiveUpdateTask(PASSIVE_CHECK_KEY_DOCTOR, 0);
                for (int i=0; i<initJigyoushaCnt; i++) {
                    addPassiveDeleteTask(PASSIVE_CHECK_KEY_JIGYOUSHA, i);
                }

                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    return false;
                }
            }
            else {
                dbm.beginTransaction(); //�g�����U�N�V�����J�n
            }

            //�u�ʏ�g����t�v�Ƀ`�F�b�N�������Ă���ꍇ�A���ׂẮu�ʏ�g����t�v�̃t���O��������
            if (getDBSafeNumber("MI_DEFAULT", doctorData).equals("1")) {
                dbm.executeUpdate("UPDATE DOCTOR SET MI_DEFAULT=0,LAST_TIME=CURRENT_TIMESTAMP");
            }

            //SQL���𐶐� + �����ʒmMsg�ݒ�
            StringBuffer sbDoctor = new StringBuffer();
            if (isUpdate) {
                //�X�V��
                sbDoctor.append(" UPDATE");
                sbDoctor.append(" DOCTOR");
                sbDoctor.append(" SET");
                sbDoctor.append(" DR_NM=" + getDBSafeString("DR_NM", doctorData));
                sbDoctor.append(",MI_NM=" + getDBSafeString("MI_NM", doctorData));
                sbDoctor.append(",MI_POST_CD=" + getDBSafeString("MI_POST_CD", doctorData));
                sbDoctor.append(",MI_ADDRESS=" + getDBSafeString("MI_ADDRESS", doctorData));
                sbDoctor.append(",MI_TEL1=" + getDBSafeString("MI_TEL1", doctorData));
                sbDoctor.append(",MI_TEL2=" + getDBSafeString("MI_TEL2", doctorData));
                sbDoctor.append(",MI_FAX1=" + getDBSafeString("MI_FAX1", doctorData));
                sbDoctor.append(",MI_FAX2=" + getDBSafeString("MI_FAX2", doctorData));
                sbDoctor.append(",MI_CEL_TEL1=" + getDBSafeString("MI_CEL_TEL1", doctorData));
                sbDoctor.append(",MI_CEL_TEL2=" + getDBSafeString("MI_CEL_TEL2", doctorData));
                sbDoctor.append(",KINKYU_RENRAKU=" + getDBSafeString("KINKYU_RENRAKU", doctorData));
                sbDoctor.append(",FUZAIJI_TAIOU=" + getDBSafeString("FUZAIJI_TAIOU", doctorData));
                sbDoctor.append(",BIKOU=" + getDBSafeString("BIKOU", doctorData));
                sbDoctor.append(",MI_DEFAULT=" + getDBSafeNumber("MI_DEFAULT", doctorData));
                sbDoctor.append(",KAISETUSHA_NM=" + getDBSafeString("KAISETUSHA_NM", doctorData));
                sbDoctor.append(",DR_NO=" + getDBSafeString("DR_NO", doctorData));
                sbDoctor.append(",MI_KBN=" + getDBSafeNumber("MI_KBN", doctorData));
                sbDoctor.append(",BANK_NM=" + getDBSafeString("BANK_NM", doctorData));
                sbDoctor.append(",BANK_SITEN_NM=" + getDBSafeString("BANK_SITEN_NM", doctorData));
                sbDoctor.append(",BANK_KOUZA_NO=" + getDBSafeString("BANK_KOUZA_NO", doctorData));
                sbDoctor.append(",BANK_KOUZA_KIND=" + getBankKouzaKindDBValue(getDBSafeNumber("BANK_KOUZA_KIND", doctorData)));
                sbDoctor.append(",FURIKOMI_MEIGI=" + getDBSafeString("FURIKOMI_MEIGI", doctorData));
                sbDoctor.append(",LAST_TIME=CURRENT_TIMESTAMP");
                sbDoctor.append(" WHERE");
                sbDoctor.append(" DR_CD=" + drCd);
            }
            else {
                //�ǉ���
                sbDoctor.append("INSERT INTO");
                sbDoctor.append(" DOCTOR");
                sbDoctor.append(" (");
                sbDoctor.append(" DR_NM,");
                sbDoctor.append(" MI_NM,");
                sbDoctor.append(" MI_POST_CD,");
                sbDoctor.append(" MI_ADDRESS,");
                sbDoctor.append(" MI_TEL1,");
                sbDoctor.append(" MI_TEL2,");
                sbDoctor.append(" MI_FAX1,");
                sbDoctor.append(" MI_FAX2,");
                sbDoctor.append(" MI_CEL_TEL1,");
                sbDoctor.append(" MI_CEL_TEL2,");
                sbDoctor.append(" KINKYU_RENRAKU,");
                sbDoctor.append(" FUZAIJI_TAIOU,");
                sbDoctor.append(" BIKOU,");
                sbDoctor.append(" MI_DEFAULT,");
                sbDoctor.append(" KAISETUSHA_NM,");
                sbDoctor.append(" DR_NO,");
                sbDoctor.append(" MI_KBN,");
                sbDoctor.append(" BANK_NM,");
                sbDoctor.append(" BANK_SITEN_NM,");
                sbDoctor.append(" BANK_KOUZA_NO,");
                sbDoctor.append(" BANK_KOUZA_KIND,");
                sbDoctor.append(" FURIKOMI_MEIGI,");
                sbDoctor.append(" LAST_TIME");
                sbDoctor.append(" )");
                sbDoctor.append(" VALUES(");
                sbDoctor.append(" " + getDBSafeString("DR_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_POST_CD", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_ADDRESS", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_TEL1", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_TEL2", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_FAX1", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_FAX2", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_CEL_TEL1", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_CEL_TEL2", doctorData));
                sbDoctor.append("," + getDBSafeString("KINKYU_RENRAKU", doctorData));
                sbDoctor.append("," + getDBSafeString("FUZAIJI_TAIOU", doctorData));
                sbDoctor.append("," + getDBSafeString("BIKOU", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_DEFAULT", doctorData));
                sbDoctor.append("," + getDBSafeString("KAISETUSHA_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("DR_NO", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_KBN", doctorData));
                sbDoctor.append("," + getDBSafeString("BANK_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("BANK_SITEN_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("BANK_KOUZA_NO", doctorData));
                sbDoctor.append("," + getBankKouzaKindDBValue(getDBSafeNumber("BANK_KOUZA_KIND", doctorData)));
                sbDoctor.append("," + getDBSafeString("FURIKOMI_MEIGI", doctorData));
                sbDoctor.append(",CURRENT_TIMESTAMP");
                sbDoctor.append(" )");
            }

            //�X�V�pSQL���s(DOCTOR�e�[�u��)
            dbm.executeUpdate(sbDoctor.toString());

            //�u�V�K�v�u�����v�̏ꍇ�A���ǉ������f�[�^�̈�tCD���擾���Ă���
            if (!isUpdate) {
                String sql = "SELECT GEN_ID(GEN_DOCTOR, 0) FROM rdb$database";
                VRArrayList tmpArray = (VRArrayList) dbm.executeQuery(sql);
                drCd = ((VRMap) tmpArray.get(0)).get("GEN_ID").toString();
            }

            //���Ǝ҃e�[�u��DELETE
            dbm.executeUpdate("DELETE FROM JIGYOUSHA WHERE DR_CD=" + drCd);

            //���Ǝ҃e�[�u��INSERT
            VRArrayList jigyoushoData = ((IkenshoIryouKikanJouhouShousaiKanren)kanren).getJigyoushoData();
            for (int i=0; i<jigyoushoData.getDataSize(); i++) {
                //�p�����[�^�擾
                VRMap jigyoushaRow = (VRMap)jigyoushoData.getData(i);
                String INSURER_NO = jigyoushaRow.getData("INSURER_NO").toString();
                String JIGYOUSHA_NO = jigyoushaRow.getData("JIGYOUSHA_NO").toString();

                //���Ǝ҃e�[�u���pSQL
                StringBuffer sbJigyousha = new StringBuffer();
                sbJigyousha.append("INSERT INTO");
                sbJigyousha.append(" JIGYOUSHA");
                sbJigyousha.append(" (");
                sbJigyousha.append(" DR_CD");
                sbJigyousha.append(",INSURER_NO");
                sbJigyousha.append(",JIGYOUSHA_NO");
                sbJigyousha.append(",LAST_TIME");
                sbJigyousha.append(")");
                sbJigyousha.append(" VALUES(");
                sbJigyousha.append(" " + drCd);
                sbJigyousha.append(",'" + INSURER_NO + "'");
                sbJigyousha.append(",'" + JIGYOUSHA_NO + "'");
                sbJigyousha.append(",CURRENT_TIMESTAMP");
                sbJigyousha.append(")");

                //INSERT
                dbm.executeUpdate(sbJigyousha.toString());
            }

            //�R�~�b�g
            dbm.commitTransaction();
        }
        catch (Exception ex) {
            //���[���o�b�N
            drCd = drCdOld;
            dbm.rollbackTransaction();
            throw new Exception(ex);
        }
        return true;
    }

    /**
     * Apply�O�̒i�K�ōX�V�����\����Ԃ��܂��B
     * @throws Exception ������O
     * @return �X�V�����\��
     */
    private boolean canControlUpdate() throws Exception {
        //�G���[�`�F�b�N
        Iterator it = tabArray.iterator();
        while (it.hasNext()) {
            IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
            if (!tab.noControlError()) {
                tabs.setSelectedComponent(tab);
                return false;
            }
        }

        //�x���`�F�b�N
        it = tabArray.iterator();
        while (it.hasNext()) {
            IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
            if (!tab.noControlWarning()) {
                tabs.setSelectedComponent(tab);
                return false;
            }
        }
        return true;
    }

    /**
     * BANK_KOUZA_KIND�Ɋւ��āAIkenshoRadioButtonGroup��selectedIndex����ADB�̒l�֕ϊ����܂��B(SQL����������)
     * @param propertyValue IkenshoRadioButtonGroup��selectedIndex
     * @return DB�̒l
     */
    private String getBankKouzaKindDBValue(String propertyValue) {
        String dbValue = "2";

        if (propertyValue.equals("0")) { //���I��
            dbValue = "2"; //�u�s���v
        }
        else if (propertyValue.equals("1")) { //1�Ԗڂ�I��
            dbValue = "1"; //�u���ʁv
        }
        else if (propertyValue.equals("2")) { //2�Ԗڂ�I��
            dbValue = "0"; //�u�����v
        }

        return dbValue;
    }

    /**
     * BANK_KOUZA_KIND�Ɋւ��āADB�̒l����AIkenshoRadioButtonGroup��selectedIndex�֕ϊ����܂��B
     * @param dbValue DB�̒l
     * @return IkenshoRadioButtonGroup��selectedIndex
     */
    private String getBankKouzaKindPropertyValue(String dbValue) {
        String propertyValue = "0";
        if (dbValue.equals("2")) { //�u�s���v
            propertyValue = "0"; //���I��
        }
        else if (dbValue.equals("1")) { //�u���ʁv
            propertyValue = "1"; //1�Ԗڂ�I��
        }
        else if (dbValue.equals("0")) { //�u�����v
            propertyValue = "2"; //2�Ԗڂ�I��
        }
        return propertyValue;
    }
}
