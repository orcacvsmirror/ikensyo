package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.JScrollPane;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoRenkeiIJyouhouShousai extends IkenshoAffairContainer
        implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel client = new VRPanel();
    private ACLabelContainer drNmContainer = new ACLabelContainer();
    private ACTextField drNm = new ACTextField();
    private ACLabelContainer sinryoukaContainer = new ACLabelContainer();
    private ACLabelContainer sinryoukaPanel = new ACLabelContainer();
    private ACTextField sinryouka = new ACTextField();
    private VRLabel sinryoukaNote = new VRLabel();
    private ACLabelContainer nmContainer = new ACLabelContainer();
    private ACTextField nm = new ACTextField();
    private ACLabelContainer postCdContainer = new ACLabelContainer();
    private IkenshoZipTextField postCd = new IkenshoZipTextField();
    private ACLabelContainer addrContainer = new ACLabelContainer();
    private ACTextField addr = new ACTextField();
    private ACLabelContainer telContainer = new ACLabelContainer();
    private IkenshoTelTextField tel = new IkenshoTelTextField();
    private ACLabelContainer faxContainer = new ACLabelContainer();
    private IkenshoTelTextField fax = new IkenshoTelTextField();
    private ACLabelContainer celTelContainer = new ACLabelContainer();
    private IkenshoTelTextField celTel = new IkenshoTelTextField();
    private ACLabelContainer kinkyuRenrakuContainer = new ACLabelContainer();
    private ACTextField kinkyuRenraku = new ACTextField();
    private ACLabelContainer fuzaijiTaiouContainer = new ACLabelContainer();
    private ACTextField fuzaijiTaiou = new ACTextField();
    private ACLabelContainer bikouContainer = new ACLabelContainer();
    private JScrollPane bikouScrPane = new JScrollPane();
    private ACRowMaximumableTextArea bikou = new ACRowMaximumableTextArea();
    private VRLabel blank1 = new VRLabel();
    private VRLabel blank2 = new VRLabel();
    private VRLabel blank3 = new VRLabel();
    private VRLabel blank4 = new VRLabel();

    private VRMap renkeiiData;
    private String renkeiiCd; // �A�g��R�[�h
    private boolean isUpdate; // true : �X�V, false : �ǉ�
    private boolean hasData; // true : �L, false : ��
    protected VRMap prevData; // �O��ʃL���b�V��

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "RENKEII", new String[] { "RENKEII_CD" }, new Format[] { null },
            "LAST_TIME", "LAST_TIME");

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoRenkeiIJyouhouShousai() {
        try {
            jbInit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        buttons.setTitle("�A�g����ڍ�");
        buttons.add(update, VRLayout.EAST);

        // ���C��
        VRLayout ll = new VRLayout();
        ll.setFitHLast(true);
        ll.setFitVLast(true);
        client.setLayout(ll);
        client.add(drNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(sinryoukaPanel, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);
        client.add(blank1, VRLayout.FLOW_RETURN);
        client.add(nmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(postCdContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank2, VRLayout.FLOW_RETURN);
        client.add(addrContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(telContainer, VRLayout.FLOW_INSETLINE);
        client.add(faxContainer, VRLayout.FLOW);
        client.add(blank3, VRLayout.FLOW_RETURN);
        client.add(celTelContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank4, VRLayout.FLOW_RETURN);
        client.add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(bikouContainer, VRLayout.FLOW_INSETLINE_RETURN);

        // �o�^�{�^��
//        update.setText("�o�^(S)");
        update.setText("�@�o�^(S)�@");
        update.setMnemonic('S');
        update.setActionCommand("�o�^(S)");
        update.setToolTipText("���݂̓��e��o�^���܂��B");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        // ��t����
        drNmContainer.add(drNm);
        drNmContainer.setText("��t����");
        drNm.setColumns(15);
        drNm.setMaxLength(15);
        drNm.setBindPath("DR_NM");
        drNm.setIMEMode(InputSubset.KANJI);

        // �f�É�
        sinryoukaPanel.add(sinryoukaContainer);
        sinryoukaPanel.add(sinryoukaNote);
        sinryoukaPanel.setLabelFilled(true);
        sinryoukaPanel.setContentAreaFilled(true);
        sinryoukaPanel.setBackground(new Color(0x99cc99));
        sinryoukaPanel.setFocusBackground(new Color(0x99e099));

        sinryoukaContainer.add(sinryouka);
        sinryoukaContainer.setText("�f�ÉȖ�");
        sinryouka.setColumns(10);
        sinryouka.setMaxLength(10);
        sinryouka.setBindPath("SINRYOUKA");
        sinryouka.setIMEMode(InputSubset.KANJI);

        sinryoukaNote.setText("�u�厡��ӌ����v�u��t�㌩���v�Ɉ������鍀��");
        sinryoukaNote.setForeground(new Color(0x003300));

        // ��Ë@�֖�
        nmContainer.add(nm);
        nmContainer.setText("��Ë@�֖�");
        nm.setColumns(30);
        nm.setMaxLength(30);
        nm.setBindPath("MI_NM");
        nm.setIMEMode(InputSubset.KANJI);

        // �X�֔ԍ�
        postCdContainer.add(postCd);
        postCdContainer.setText("�X�֔ԍ�");
        postCd.setAddressTextField(addr);
        postCd.setBindPath("MI_POST_CD");

        // ���ݒn
        addrContainer.add(addr);
        addrContainer.setText("���ݒn");
        addr.setColumns(45);
        addr.setMaxLength(45);
        addr.setBindPath("MI_ADDRESS");
        addr.setIMEMode(InputSubset.KANJI);

        // �A����(TEL)
        telContainer.add(tel);
        telContainer.setText("�A����(TEL)");
        tel.setBindPath("MI_TEL1", "MI_TEL2");

        // �A����(FAX)
        faxContainer.add(fax);
        faxContainer.setText("�A����(FAX)");
        fax.setBindPath("MI_FAX1", "MI_FAX2");

        // �A����(�g��)
        celTelContainer.add(celTel);
        celTelContainer.setText("�A����(�g��)");
        celTel.setBindPath("MI_CEL_TEL1", "MI_CEL_TEL2");

        // �ً}���A����
        kinkyuRenrakuContainer.add(kinkyuRenraku);
        kinkyuRenrakuContainer.setText("�ً}���A����");
        kinkyuRenraku.setColumns(40);
        kinkyuRenraku.setMaxLength(40);
        kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
        kinkyuRenraku.setIMEMode(InputSubset.KANJI);

        // �s�ݎ��Ή��@
        fuzaijiTaiouContainer.add(fuzaijiTaiou);
        fuzaijiTaiouContainer.setText("�s�ݎ��Ή��@");
        fuzaijiTaiou.setColumns(40);
        fuzaijiTaiou.setMaxLength(40);
        fuzaijiTaiou.setBindPath("FUZAIJI_TAIOU");
        fuzaijiTaiou.setIMEMode(InputSubset.KANJI);

        bikouContainer.setLayout(new BorderLayout());
        bikouScrPane.getViewport().add(bikou);
        bikouContainer.add(bikouScrPane, java.awt.BorderLayout.CENTER);
        bikouContainer.setText("���l");
        bikou.setBindPath("BIKOU");
        bikou.setIMEMode(InputSubset.KANJI);
        bikou.setMaxLength(200);
        bikou.setLineWrap(true);

    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // �X�i�b�v�V���b�g�Ώېݒ�
        IkenshoSnapshot.getInstance().setRootContainer(client);
        // ���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        addUpdateTrigger(update);

        // �O��ʂ�����p���̃f�[�^���擾�E�ݒ�
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            if (VRBindPathParser.has("PREV_DATA", params)) {
                prevData = (VRMap) VRBindPathParser
                        .get("PREV_DATA", params);
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

            // �n�莞�̏��S����RENKEII_CD���擾
            if (hasData) {
                renkeiiCd = String.valueOf(VRBindPathParser.get("RENKEII_CD",
                        params));
            }

            // ���͗��Ƀf�[�^��ݒ�
            doSelect();
        } else {
            throw new Exception("�s���ȃp�����[�^���n����܂����B");
        }
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return drNm;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        String key = "RENKEII_CD";
        String value = renkeiiCd; // (UPDATE�O�̒l)

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
                boolean updateFlg = doUpdate(); // UPDATE����:true, ���s:false
                parameters.put(key, renkeiiCd); // �l��UPDATE��̂��̂łȂ���΂Ȃ�Ȃ�
                return updateFlg;
            } else if (result == ACMessageBox.RESULT_NO) { // �ۑ����Ȃ��Ŗ߂�
                if (!isNullText(value)) {
                    parameters.put(key, value);
                }
                return true;
            } else { // �߂�Ȃ�
                return false;
            }
        } else { // �߂�
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
     * �f�[�^�擾�������s���܂�
     * 
     * @throws Exception
     */
    private void doSelect() throws Exception {
        // �X�i�b�v�V���b�g�B�e(�R�s�[���p)
        IkenshoSnapshot.getInstance().snapshot();

        // DB����f�[�^���擾
        doSelectFromDB();

        // �R�s�[���ȊO�́A���߂ăX�i�b�v�V���b�g���B��Ȃ���
        if (isUpdate) {
            IkenshoSnapshot.getInstance().snapshot();
        } else if (!hasData) {
            IkenshoSnapshot.getInstance().snapshot();
        }

        // �X�e�[�^�X�o�[
        setStatusText("�A�g����ڍ�");

    }

    /**
     * DB����f�[�^���擾���܂��B
     * 
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        VRArrayList renkeiiArray = null;
        if (hasData) {
            // �L�[�����ɁADB����f�[�^���擾
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT ");
            sb.append(" RENKEII_CD,");
            sb.append(" DR_NM,");
            sb.append(" SINRYOUKA,");
            sb.append(" MI_NM,");
            sb.append(" MI_POST_CD,");
            sb.append(" MI_ADDRESS,");
            sb.append(" MI_TEL1,");
            sb.append(" MI_TEL2,");
            sb.append(" MI_FAX1,");
            sb.append(" MI_FAX2,");
            sb.append(" MI_CEL_TEL1,");
            sb.append(" MI_CEL_TEL2,");
            sb.append(" KINKYU_RENRAKU,");
            sb.append(" FUZAIJI_TAIOU,");
            sb.append(" BIKOU,");
            sb.append(" LAST_TIME");
            sb.append(" FROM");
            sb.append(" RENKEII");
            sb.append(" WHERE");
            sb.append(" RENKEII_CD=" + renkeiiCd);

            renkeiiArray = (VRArrayList) dbm.executeQuery(sb.toString());

            if (renkeiiArray.getDataSize() > 0) { // DB��Ƀf�[�^�L
                renkeiiData = (VRMap) renkeiiArray.getData();
                // �p�b�V�u�`�F�b�N�p
                clearReservedPassive();
                reservedPassive(PASSIVE_CHECK_KEY, renkeiiArray);
            } else {
                renkeiiData = (VRMap) client.createSource(); // DB��Ƀf�[�^��
            }
        } else { // �n��f�[�^��
            renkeiiData = (VRMap) client.createSource();
        }
        client.setSource(renkeiiData);
        client.bindSource();
    }

    /**
     * DB�X�V
     */
    private boolean doUpdate() throws Exception {
        // ���̓`�F�b�N
        if (isValidInput()) {
            // DBUPDATE
            if (!doUpdateToDB()) {
                return false;
            }

            // �㏈��(�u�V�K�v�A�y�сu�����v���[�h�œo�^������A�u�X�V�v���[�h�ֈڍs����)
            String msg = new String();
            if (isUpdate) {
                msg = "�X�V����܂����B";
            } else {
                msg = "�o�^����܂����B";
                isUpdate = true;
                hasData = true;
                update.setText("�X�V(S)");
                update.setToolTipText("���݂̓��e���X�V���܂��B");
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
     * DB�ɑ΂��čX�V���s���B
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        // update
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        try {
            // ���̓f�[�^���擾
            client.applySource();

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
                sb.append(" UPDATE");
                sb.append(" RENKEII");
                sb.append(" SET");
                sb.append(" DR_NM=" + getDBSafeString("DR_NM", renkeiiData));
                sb.append(" ,SINRYOUKA="
                        + getDBSafeString("SINRYOUKA", renkeiiData));
                sb.append(" ,MI_NM=" + getDBSafeString("MI_NM", renkeiiData));
                sb.append(" ,MI_POST_CD="
                        + getDBSafeString("MI_POST_CD", renkeiiData));
                sb.append(" ,MI_ADDRESS="
                        + getDBSafeString("MI_ADDRESS", renkeiiData));
                sb.append(" ,MI_TEL1="
                        + getDBSafeString("MI_TEL1", renkeiiData));
                sb.append(" ,MI_TEL2="
                        + getDBSafeString("MI_TEL2", renkeiiData));
                sb.append(" ,MI_FAX1="
                        + getDBSafeString("MI_FAX1", renkeiiData));
                sb.append(" ,MI_FAX2="
                        + getDBSafeString("MI_FAX2", renkeiiData));
                sb.append(" ,MI_CEL_TEL1="
                        + getDBSafeString("MI_CEL_TEL1", renkeiiData));
                sb.append(" ,MI_CEL_TEL2="
                        + getDBSafeString("MI_CEL_TEL2", renkeiiData));
                sb.append(" ,KINKYU_RENRAKU="
                        + getDBSafeString("KINKYU_RENRAKU", renkeiiData));
                sb.append(" ,FUZAIJI_TAIOU="
                        + getDBSafeString("FUZAIJI_TAIOU", renkeiiData));
                sb.append(" ,BIKOU=" + getDBSafeString("BIKOU", renkeiiData));
                sb.append(" ,LAST_TIME=CURRENT_TIMESTAMP");
                sb.append(" WHERE");
                sb.append(" RENKEII_CD=" + renkeiiCd);
            } else {
                // �ǉ���
                sb.append("INSERT INTO");
                sb.append(" RENKEII");
                sb.append(" (");
                sb.append(" DR_NM,");
                sb.append(" SINRYOUKA,");
                sb.append(" MI_NM,");
                sb.append(" MI_POST_CD,");
                sb.append(" MI_ADDRESS,");
                sb.append(" MI_TEL1,");
                sb.append(" MI_TEL2,");
                sb.append(" MI_FAX1,");
                sb.append(" MI_FAX2,");
                sb.append(" MI_CEL_TEL1,");
                sb.append(" MI_CEL_TEL2,");
                sb.append(" KINKYU_RENRAKU,");
                sb.append(" FUZAIJI_TAIOU,");
                sb.append(" BIKOU,");
                sb.append(" LAST_TIME");
                sb.append(" )");
                sb.append(" VALUES(");
                sb.append(" " + getDBSafeString("DR_NM", renkeiiData));
                sb.append("," + getDBSafeString("SINRYOUKA", renkeiiData));
                sb.append("," + getDBSafeString("MI_NM", renkeiiData));
                sb.append("," + getDBSafeString("MI_POST_CD", renkeiiData));
                sb.append("," + getDBSafeString("MI_ADDRESS", renkeiiData));
                sb.append("," + getDBSafeString("MI_TEL1", renkeiiData));
                sb.append("," + getDBSafeString("MI_TEL2", renkeiiData));
                sb.append("," + getDBSafeString("MI_FAX1", renkeiiData));
                sb.append("," + getDBSafeString("MI_FAX2", renkeiiData));
                sb.append("," + getDBSafeString("MI_CEL_TEL1", renkeiiData));
                sb.append("," + getDBSafeString("MI_CEL_TEL2", renkeiiData));
                sb.append("," + getDBSafeString("KINKYU_RENRAKU", renkeiiData));
                sb.append("," + getDBSafeString("FUZAIJI_TAIOU", renkeiiData));
                sb.append("," + getDBSafeString("BIKOU", renkeiiData));
                sb.append(",CURRENT_TIMESTAMP");
                sb.append(" )");
            }

            // �X�V�pSQL���s
            dbm.executeUpdate(sb.toString());

            // �u�V�K�v�u�����v�̏ꍇ�A���ǉ������f�[�^�̘A�g��CD���擾���Ă���
            if (!isUpdate) {
                // �A�g��CD(�I�[�g�i���o�[)�擾�p��SQL��
                String sql = "SELECT GEN_ID(GEN_RENKEII, 0) FROM rdb$database";

                // �A�g��CD�擾�pSQL���s
                VRArrayList tmpArray = (VRArrayList) dbm.executeQuery(sql);

                // �A�g��CD���擾
                renkeiiCd = ((VRMap) tmpArray.get(0)).get("GEN_ID").toString();
            }

            // �R�~�b�g
            dbm.commitTransaction();
        } catch (Exception ex) {
            // ���[���o�b�N
            dbm.rollbackTransaction();
            throw new Exception(ex);
        }
        return true;
    }

    /**
     * SQL���̒l�p�̕�����𐶐����܂��B
     * 
     * @param columnName ��
     * @return String
     * @throws Exception
     */
    private String createSqlParam(String columnName) throws Exception {
        return String.valueOf(VRBindPathParser.get(columnName, renkeiiData))
                .replaceAll("'", "''");
    }

    /**
     * ���̓`�F�b�N����
     */
    private boolean isValidInput() {
        boolean valid = true;
        Component target = null;
        String msg = "";

        // �G���[�`�F�b�N
        if (isNullText(drNm.getText())) {
            valid = false;
            target = drNm;
            msg = "��������͂��Ă��������B";
        }

        // �G���[�_�C�A���O�\��
        if (!valid) {
            ACMessageBox.show(msg, ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            target.requestFocus();
            return false;
        }
        return true;
    }
}
