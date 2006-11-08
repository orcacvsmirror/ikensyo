/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JTextField;

import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.event.IkenshoDocumentAffairApplicantNameChageListener;
import jp.or.med.orca.ikensho.event.IkenshoWriteDateChangeListener;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

public class IkenshoHoumonKangoShijisho extends IkenshoTabbableAffairContainer {
    private static final ACPassiveKey PASSIVE_CHECK_KEY_SIJISYO = new ACPassiveKey(
            "SIS_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO" },
            new Format[] { null, null }, "SIJISYO_LAST_TIME", "LAST_TIME");

    private IkenshoHoumonKangoShijishoApplicant applicant = new IkenshoHoumonKangoShijishoApplicant();
    private IkenshoHoumonKangoShijishoSick shoubyou = new IkenshoHoumonKangoShijishoSick();
    private IkenshoHoumonKangoShijishoMindBody1 jiritudo = new IkenshoHoumonKangoShijishoMindBody1();
    private IkenshoHoumonKangoShijishoSpecial tokubetu = new IkenshoHoumonKangoShijishoSpecial();
    private IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji = new IkenshoHoumonKangoShijishoRyuiShiji();
    private IkenshoHoumonKangoShijishoTenteki tenteki = new IkenshoHoumonKangoShijishoTenteki();
    private IkenshoHoumonKangoShijishoIryoukikan iryoukikan = new IkenshoHoumonKangoShijishoIryoukikan();

    protected void reselectForPassiveCustom(IkenshoFirebirdDBManager dbm,
            VRMap data) throws Exception {
        StringBuffer sb;
        VRArrayList array;

        // �ŗL����
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" LAST_TIME AS SIJISYO_LAST_TIME");
        sb.append(",CREATE_DT");
        sb.append(" FROM");
        sb.append(" SIS_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (SIS_ORIGIN.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        array = (VRArrayList) dbm.executeQuery(sb.toString());
        hasOriginalDocument = array.getDataSize() > 0;
        if (hasOriginalDocument) {
            data.putAll((VRMap) array.getData());
        }

    }

    protected void doSelectCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" SIS_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (SIS_ORIGIN.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        hasOriginalDocument = array.getDataSize() > 0;
        if (hasOriginalDocument) {
            VRMap data = (VRMap) array.getData();
            data.setData("SIJISYO_LAST_TIME", VRBindPathParser.get("LAST_TIME",
                    data));

            originalData.putAll(data);
        }

    }

    protected void doSelectBeforeCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        String patientNo = getPatientNo();
        if (patientNo != null) {

            StringBuffer sb = new StringBuffer();
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM");
            sb.append(" SIS_ORIGIN");
            sb.append(" WHERE");
            sb.append(" (PATIENT_NO = ");
            sb.append(patientNo);
            sb.append(")");
            sb.append(" ORDER BY");
            sb.append(" EDA_NO DESC");
            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.size() > 0) {
                VRMap data = (VRMap) array.getData();
                originalData.putAll(data);

                String fromSpanValue = "";
                Object obj = VRBindPathParser
                        .get("SIJI_KIKAN_TO", originalData);
                if (obj instanceof String) {
                    String textVal = (String) obj;
                    if (!(IkenshoCommon.isNullText(textVal) || "0000�N00��00��"
                            .equals(textVal))) {
                        try {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(VRDateParser.parse(textVal));
                            if (cal != null) {
                                // �O��̖K��Ō�w�����ԏI���̗����Ƃ���
                                cal.add(Calendar.DATE, 1);
                                fromSpanValue = IkenshoConstants.FORMAT_ERA_YMD
                                        .format(cal.getTime());
                            }
                        } catch (Exception ex) {
                        }
                    }
                }

                VRBindPathParser.set("HOUMON_SIJISYO", originalData,
                        new Integer(0));
                VRBindPathParser.set("TENTEKI_SIJISYO", originalData,
                        new Integer(0));
                VRBindPathParser.set("SIJI_KIKAN_FROM", originalData,
                        fromSpanValue);
                VRBindPathParser.set("SIJI_KIKAN_TO", originalData, "");
                VRBindPathParser.set("TENTEKI_FROM", originalData, "");
                VRBindPathParser.set("TENTEKI_TO", originalData, "");
                VRBindPathParser.set("TENTEKI_SIJI", originalData, "");
                VRBindPathParser.set("OTHER_STATION_SIJI", originalData,
                        new Integer(1));
                VRBindPathParser.set("OTHER_STATION_NM", originalData, "");

                // �L�����͖{��
                VRBindPathParser.set("KINYU_DT", originalData, new Date());

            }
        }
    }

    protected void doReservedPassiveCustom() throws ParseException {
        reservedPassive(PASSIVE_CHECK_KEY_SIJISYO, originalArray);
    }

    protected boolean showPrintDialogCustom() throws Exception {
        return new IkenshoHoumonKangoShijishoPrintSetting(originalData)
                .showModal();
    }

    protected String getCustomDocumentTableName() {
        return "SIS_ORIGIN";
    }

    protected String getAnotherDocumentTableName() {
        return "IKN_ORIGIN";
    }

    protected void doSelectDefaultCustomDocument(IkenshoFirebirdDBManager dbm) {

    }

    protected void addPassiveInsertTaskCustom() {
        addPassiveInsertTask(PASSIVE_CHECK_KEY_SIJISYO, 0);
    }

    protected void addPassiveUpdateTaskCustom() {
        addPassiveUpdateTask(PASSIVE_CHECK_KEY_SIJISYO, 0);
    }

    protected void doInsertCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        doInsertSijisho(dbm);
    }

    protected void doUpdateCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        doUpdateSijisho(dbm);
    }

    protected String getCustomDocumentType() {
        return IkenshoConstants.DOC_KBN_SIJISHO;
    }

    protected String getAnotherDocumentType() {
        return IkenshoConstants.DOC_KBN_IKENSHO;
    }

    /**
     * �w��������ǉ����܂��B
     * 
     * @param dbm DBManager
     * @throws ParseException ��͗�O
     * @throws SQLException SQL��O
     */
    protected void doInsertSijisho(IkenshoFirebirdDBManager dbm)
            throws ParseException, SQLException {
        // �ӌ���
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO");
        sb.append(" SIS_ORIGIN");
        sb.append(" (");
        sb.append(" PATIENT_NO");

        sb.append(",EDA_NO");
        sb.append(",KINYU_DT");
        sb.append(",SIJI_CREATE_CNT");
        sb.append(",SIJI_YUKOU_KIKAN");
        sb.append(",SIJI_KANGO_KBN");
        sb.append(",STATION_NM");
        sb.append(",KINKYU_RENRAKU");
        sb.append(",FUZAIJI_TAIOU");
        sb.append(",RSS_RYUIJIKOU");
        sb.append(",REHA_SIJI_UMU");
        sb.append(",REHA_SIJI");
        sb.append(",JOKUSOU_SIJI_UMU");
        sb.append(",JOKUSOU_SIJI");
        sb.append(",SOUCHAKU_SIJI_UMU");
        sb.append(",SOUCHAKU_SIJI");
        sb.append(",RYUI_SIJI_UMU");
        sb.append(",RYUI_SIJI");
        sb.append(",SIJI_TOKKI");
        sb.append(",CREATE_DT");
        sb.append(",KOUSIN_DT");
        sb.append(",SIJI_KIKAN_FROM");
        sb.append(",SIJI_KIKAN_TO");
        sb.append(",YOUKAIGO_JOUKYOU");
        sb.append(",HOUMON_SIJISYO");
        sb.append(",TENTEKI_SIJISYO");
        sb.append(",TENTEKI_FROM");
        sb.append(",TENTEKI_TO");
        sb.append(",TENTEKI_SIJI");
        sb.append(",OTHER_STATION_SIJI");
        sb.append(",OTHER_STATION_NM");
        sb.append(",LAST_TIME");

        sb.append(" )");
        sb.append(" VALUES");
        sb.append(" (");

        sb.append(getPatientNo());
        sb.append(",");
        sb.append(getEdaNo());
        sb.append(",");
        sb.append(getDBSafeDate("KINYU_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SIJI_CREATE_CNT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SIJI_YUKOU_KIKAN", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SIJI_KANGO_KBN", originalData));
        sb.append(",");
        sb.append(getDBSafeString("STATION_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("KINKYU_RENRAKU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("FUZAIJI_TAIOU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("RSS_RYUIJIKOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("REHA_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("REHA_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("JOKUSOU_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("JOKUSOU_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SOUCHAKU_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SOUCHAKU_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("RYUI_SIJI_UMU", originalData));
        sb.append(",");
        sb.append(getDBSafeString("RYUI_SIJI", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SIJI_TOKKI", originalData));
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",");
        sb.append(getDBSafeString("SIJI_KIKAN_FROM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SIJI_KIKAN_TO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("YOUKAIGO_JOUKYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_SIJISYO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("TENTEKI_SIJISYO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TENTEKI_FROM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TENTEKI_TO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("TENTEKI_SIJI", originalData));
        // sb.append(",");
        // sb.append(getDBSafeNumber("OTHER_STATION_SIJI", originalData));
        // sb.append(",");
        // sb.append(getDBSafeString("OTHER_STATION_NM", originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "OTHER_STATION_SIJI", new String[] { "OTHER_STATION_NM" }, 2,
                true);
        sb.append(",CURRENT_TIMESTAMP");

        sb.append(")");

        dbm.executeUpdate(sb.toString());

        hasOriginalDocument = true;
    }

    /**
     * �w���������X�V���܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    protected void doUpdateSijisho(IkenshoFirebirdDBManager dbm)
            throws Exception {

        // �ӌ���
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE");
        sb.append(" SIS_ORIGIN");
        sb.append(" SET");

        sb.append(" KINYU_DT = ");
        sb.append(getDBSafeDate("KINYU_DT", originalData));

        sb.append(",SIJI_CREATE_CNT = ");
        sb.append(getDBSafeNumber("SIJI_CREATE_CNT", originalData));
        sb.append(",SIJI_YUKOU_KIKAN = ");
        sb.append(getDBSafeNumber("SIJI_YUKOU_KIKAN", originalData));
        sb.append(",SIJI_KANGO_KBN = ");
        sb.append(getDBSafeNumber("SIJI_KANGO_KBN", originalData));
        sb.append(",STATION_NM = ");
        sb.append(getDBSafeString("STATION_NM", originalData));
        sb.append(",KINKYU_RENRAKU = ");
        sb.append(getDBSafeString("KINKYU_RENRAKU", originalData));
        sb.append(",FUZAIJI_TAIOU = ");
        sb.append(getDBSafeString("FUZAIJI_TAIOU", originalData));
        sb.append(",RSS_RYUIJIKOU = ");
        sb.append(getDBSafeString("RSS_RYUIJIKOU", originalData));
        sb.append(",REHA_SIJI_UMU = ");
        sb.append(getDBSafeNumber("REHA_SIJI_UMU", originalData));
        sb.append(",REHA_SIJI = ");
        sb.append(getDBSafeString("REHA_SIJI", originalData));
        sb.append(",JOKUSOU_SIJI_UMU = ");
        sb.append(getDBSafeNumber("JOKUSOU_SIJI_UMU", originalData));
        sb.append(",JOKUSOU_SIJI = ");
        sb.append(getDBSafeString("JOKUSOU_SIJI", originalData));
        sb.append(",SOUCHAKU_SIJI_UMU = ");
        sb.append(getDBSafeNumber("SOUCHAKU_SIJI_UMU", originalData));
        sb.append(",SOUCHAKU_SIJI = ");
        sb.append(getDBSafeString("SOUCHAKU_SIJI", originalData));
        sb.append(",RYUI_SIJI_UMU = ");
        sb.append(getDBSafeNumber("RYUI_SIJI_UMU", originalData));
        sb.append(",RYUI_SIJI = ");
        sb.append(getDBSafeString("RYUI_SIJI", originalData));
        sb.append(",SIJI_TOKKI = ");
        sb.append(getDBSafeString("SIJI_TOKKI", originalData));
        // sb.append(",CREATE_DT = ");
        // sb.append(getDBSafeTime("CREATE_DT", originalData));
        sb.append(",KOUSIN_DT = CURRENT_TIMESTAMP");
        sb.append(",SIJI_KIKAN_FROM = ");
        sb.append(getDBSafeString("SIJI_KIKAN_FROM", originalData));
        sb.append(",SIJI_KIKAN_TO = ");
        sb.append(getDBSafeString("SIJI_KIKAN_TO", originalData));
        sb.append(",YOUKAIGO_JOUKYOU = ");
        sb.append(getDBSafeNumber("YOUKAIGO_JOUKYOU", originalData));
        sb.append(",HOUMON_SIJISYO = ");
        sb.append(getDBSafeNumber("HOUMON_SIJISYO", originalData));
        sb.append(",TENTEKI_SIJISYO = ");
        sb.append(getDBSafeNumber("TENTEKI_SIJISYO", originalData));
        sb.append(",TENTEKI_FROM = ");
        sb.append(getDBSafeString("TENTEKI_FROM", originalData));
        sb.append(",TENTEKI_TO = ");
        sb.append(getDBSafeString("TENTEKI_TO", originalData));
        sb.append(",TENTEKI_SIJI = ");
        sb.append(getDBSafeString("TENTEKI_SIJI", originalData));
        // sb.append(",OTHER_STATION_SIJI = ");
        // sb.append(getDBSafeNumber("OTHER_STATION_SIJI", originalData));
        // sb.append(",OTHER_STATION_NM = ");
        // sb.append(getDBSafeString("OTHER_STATION_NM", originalData));
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "OTHER_STATION_SIJI", new String[] { "OTHER_STATION_NM" }, 2,
                true);
        sb.append(",LAST_TIME = CURRENT_TIMESTAMP");
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(SIS_ORIGIN.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");

        dbm.executeUpdate(sb.toString());

    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoHoumonKangoShijisho() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        setStatusText("�K��Ō�w����");
        buttons.setTitle("�K��Ō�w����");

        tabArray.add(applicant);
        tabArray.add(shoubyou);
        tabArray.add(jiritudo);
        tabArray.add(tokubetu);
        tabArray.add(ryuiShiji);
        tabArray.add(tenteki);
        tabArray.add(iryoukikan);

        jiritudo.setFollowDisabledComponents(new JComponent[] { tabs, update,
                print, buttons.getBackButton() });

        applicant
                .addWriteDateChangeListener(new IkenshoWriteDateChangeListener() {
                    public void writeDataChanged(EventObject e) {
                        try {
                            editorDates.setText(IkenshoConstants.FORMAT_ERA_YMD
                                    .format(((IkenshoEraDateTextField) e
                                            .getSource()).getDate()));
                        } catch (Exception ex) {
                        }
                    }
                });
        applicant
                .addApplicantNameChageListener(new IkenshoDocumentAffairApplicantNameChageListener() {
                    public void nameChanged(EventObject e) {
                        editorName.setText(((JTextField) e.getSource())
                                .getText());
                    }
                });
    }

    private void jbInit() throws Exception {
        // tab
        tabs.addTab("����", applicant);
        tabs.addTab("���a", shoubyou);
        tabs.addTab("���퐶�������x", jiritudo);
        tabs.addTab("���ʂȈ��", tokubetu);
        tabs.addTab("���ӎ����E�w������", ryuiShiji);
        tabs.addTab("���L�E�K��_�H����", tenteki);
        tabs.addTab("��Ë@��", iryoukikan);

    }

}