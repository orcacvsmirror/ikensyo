/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.JComponent;
import javax.swing.JTextField;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
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

    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//    private static final ACPassiveKey PASSIVE_CHECK_KEY_SIJISYO = new ACPassiveKey(
//            "SIS_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO" },
//            new Format[] { null, null }, "SIJISYO_LAST_TIME", "LAST_TIME");
//    private IkenshoHoumonKangoShijishoApplicant applicant = new IkenshoHoumonKangoShijishoApplicant();
//    private IkenshoHoumonKangoShijishoSick shoubyou = new IkenshoHoumonKangoShijishoSick();
//    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin ��ʒ����ɔ�������
//    private IkenshoHoumonKangoShijishoSick2 shoubyou2 = new IkenshoHoumonKangoShijishoSick2();
//    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
//    private IkenshoHoumonKangoShijishoMindBody1 jiritudo = new IkenshoHoumonKangoShijishoMindBody1();
//    private IkenshoHoumonKangoShijishoSpecial tokubetu = new IkenshoHoumonKangoShijishoSpecial();
//    private IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji = new IkenshoHoumonKangoShijishoRyuiShiji();
//    private IkenshoHoumonKangoShijishoTenteki tenteki = new IkenshoHoumonKangoShijishoTenteki();
//    private IkenshoHoumonKangoShijishoIryoukikan iryoukikan = new IkenshoHoumonKangoShijishoIryoukikan();
    protected static final ACPassiveKey PASSIVE_CHECK_KEY_SIJISYO = new ACPassiveKey(
            "SIS_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO", "FORMAT_KBN" },
            new Format[] { null, null, null }, "SIJISYO_LAST_TIME", "LAST_TIME");
    protected IkenshoHoumonKangoShijishoApplicant applicant;
    protected IkenshoHoumonKangoShijishoSick shoubyou;
    protected IkenshoHoumonKangoShijishoSick2 shoubyou2;
    protected IkenshoHoumonKangoShijishoMindBody1 jiritudo;
    protected IkenshoHoumonKangoShijishoSpecial tokubetu;
    protected IkenshoHoumonKangoShijishoRyuiShiji ryuiShiji;
    protected IkenshoHoumonKangoShijishoTenteki tenteki;
    protected IkenshoHoumonKangoShijishoIryoukikan iryoukikan;
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

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
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
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
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
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
//              [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
//                VRMap data = (VRMap) array.getData();
//                originalData.putAll(data);
//                String fromSpanValue = "";
//                Object obj = VRBindPathParser
//                .get("SIJI_KIKAN_TO", originalData);
                for(int i=0, iEnd=array.size(); i<iEnd; i++){
                    VRMap data = (VRMap) array.getData(i);
                    if (getFormatKubun().equals(
                            ACCastUtilities.toString(VRBindPathParser.get(
                                    "FORMAT_KBN", data)))) {
                        //���敪������������p��
                        originalData.putAll(data);
                        break;
                    }
                }
                
                VRMap recentData = (VRMap) array.getData(0);
                String fromSpanValue = "";
                //�w�����ԊJ�n���͋敪�Ɋ֌W�Ȃ����߂̕�������Q�Ƃ���
                Object obj = VRBindPathParser.get("SIJI_KIKAN_TO", recentData);
//              [ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

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
                // [ID:0000517][Tozo TANAKA] 2009/09/04 delete begin �y2009�N�x�Ή��F�K��Ō�w�����z�w�����ڂ̈��p��
                //VRBindPathParser.set("TENTEKI_SIJI", originalData, "");
                // [ID:0000517][Tozo TANAKA] 2009/09/04 delete end �y2009�N�x�Ή��F�K��Ō�w�����z�w�����ڂ̈��p��
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
    	
    	//[ID:0000635][Shin Fujihara] 2011/02/25 add begin �y2010�N�x�v�]�Ή��z
    	//���[�����y�[�W���邩�̃t���O��ݒ肵�āA�����ʂɓn��
    	//���y�[�W�̌x���Ȃ��Ȃ�FALSE
    	if (warningMessage.length() == 0) {
    		originalData.put("IS_PAGE_BREAK", "FALSE");
    	} else {
    		originalData.put("IS_PAGE_BREAK", "TRUE");
    	}
        //[ID:0000635][Shin Fujihara] 2011/02/25 add end �y2010�N�x�v�]�Ή��z
    	
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
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin ����21�N4���@�����Ή�
        sb.append(",JOKUSOU_NPUAP");
        sb.append(",JOKUSOU_DESIGN");
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(",FORMAT_KBN");
        
        appendInsertShijishoKeys(sb);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

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
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin ����21�N4���@�����Ή�
        sb.append(",");
        sb.append(getDBSafeString("JOKUSOU_NPUAP", originalData));
        sb.append(",");
        sb.append(getDBSafeString("JOKUSOU_DESIGN", originalData));
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(",");
        sb.append(getFormatKubun());

        appendInsertShijishoValues(sb);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

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
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin ����21�N4���@�����Ή�
        sb.append(",JOKUSOU_NPUAP = ");
        sb.append(getDBSafeString("JOKUSOU_NPUAP", originalData));
        sb.append(",JOKUSOU_DESIGN = ");
        sb.append(getDBSafeString("JOKUSOU_DESIGN", originalData));
        
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        appendUpdateShijishoStetement(sb);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(SIS_ORIGIN.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begijn �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

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

        // [ID:0000514][Tozo TANAKA] 2009/09/07 remove begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        tabArray.add(applicant);
//        tabArray.add(shoubyou);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 add begin ��ʒ����ɔ�������
//        tabArray.add(shoubyou2);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 add end
//        tabArray.add(jiritudo);
//        tabArray.add(tokubetu);
//        tabArray.add(ryuiShiji);
//        tabArray.add(tenteki);
//        tabArray.add(iryoukikan);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 remove end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

        // [ID:0000514][Tozo TANAKA] 2009/09/09 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        jiritudo.setFollowDisabledComponents(new JComponent[] { tabs, update,
//                print, buttons.getBackButton() });
//
//        applicant
//                .addWriteDateChangeListener(new IkenshoWriteDateChangeListener() {
//                    public void writeDataChanged(EventObject e) {
//                        try {
//                            editorDates.setText(IkenshoConstants.FORMAT_ERA_YMD
//                                    .format(((IkenshoEraDateTextField) e
//                                            .getSource()).getDate()));
//                        } catch (Exception ex) {
//                        }
//                    }
//                });
//        applicant
//                .addApplicantNameChageListener(new IkenshoDocumentAffairApplicantNameChageListener() {
//                    public void nameChanged(EventObject e) {
//                        editorName.setText(((JTextField) e.getSource())
//                                .getText());
//                    }
//                });
        getJiritudo().setFollowDisabledComponents(new JComponent[] { tabs, update,
                print, buttons.getBackButton() });

        getApplicant()
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
        getApplicant()
                .addApplicantNameChageListener(new IkenshoDocumentAffairApplicantNameChageListener() {
                    public void nameChanged(EventObject e) {
                        editorName.setText(((JTextField) e.getSource())
                                .getText());
                    }
                });
        // [ID:0000514][Tozo TANAKA] 2009/09/09 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

    }

    private void jbInit() throws Exception {
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        // tab
//        tabs.addTab("����", applicant);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 edit begin ��ʒ����ɔ�������
//        tabs.addTab("���a�P", shoubyou);
//        tabs.addTab("���a�Q", shoubyou2);
//        // [ID:0000509][Masahiko Higuchi] 2009/06 edit end
//        // [ID:0000463][Tozo TANAKA] 2009/03/20 replace begin ����21�N4���@�����Ή�
//        //tabs.addTab("���퐶�������x", jiritudo);
//        tabs.addTab("���퐶�������x�E��ጂ̐[��", jiritudo);
//        // [ID:0000463][Tozo TANAKA] 2009/03/20 replace end
//        tabs.addTab("���ʂȈ��", tokubetu);
//        tabs.addTab("���ӎ����E�w������", ryuiShiji);
//        tabs.addTab("���L�E�K��_�H����", tenteki);
//        tabs.addTab("��Ë@��", iryoukikan);
        addTabs();
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

    }
    
    // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    protected void addTabs() {

        tabs.addTab("����", getApplicant());
        tabs.addTab("���a�P", getShoubyou());
        tabs.addTab("���a�Q", getShoubyou2());
        tabs.addTab("���퐶�������x�E��ጂ̐[��", getJiritudo());
        tabs.addTab("���ʂȈ��", getTokubetu());
        tabs.addTab("���ӎ����E�w������", getRyuiShiji());
        tabs.addTab("���L�E�K��_�H����", getTenteki());
        tabs.addTab("��Ë@��", getIryoukikan());

        
        tabArray.add(getApplicant());
        tabArray.add(getShoubyou());
        tabArray.add(getShoubyou2());
        tabArray.add(getJiritudo());
        tabArray.add(getTokubetu());
        tabArray.add(getRyuiShiji());
        tabArray.add(getTenteki());
        tabArray.add(getIryoukikan());

    }
    
    /**
     * ���҃^�u ��Ԃ��܂��B
     * @return ���҃^�u
     */
    public IkenshoHoumonKangoShijishoApplicant getApplicant() {
        if(applicant==null){
            applicant = new IkenshoHoumonKangoShijishoApplicant();
        }
        return applicant;
    }

    /**
     * ��Ë@�փ^�u ��Ԃ��܂��B
     * @return ��Ë@�փ^�u
     */
    public IkenshoHoumonKangoShijishoIryoukikan getIryoukikan() {
        if(iryoukikan==null){
            iryoukikan = new IkenshoHoumonKangoShijishoIryoukikan();
        }
        return iryoukikan;
    }

    /**
     * ���퐶�������x�E��ጂ̐[���^�u ��Ԃ��܂��B
     * @return ���퐶�������x�E��ጂ̐[���^�u
     */
    public IkenshoHoumonKangoShijishoMindBody1 getJiritudo() {
        if(jiritudo==null){
            jiritudo = new IkenshoHoumonKangoShijishoMindBody1();
        }
        return jiritudo;
    }
    /**
     * ���ӎ����E�w�������^�u ��Ԃ��܂��B
     * @return ���ӎ����E�w�������^�u
     */
    public IkenshoHoumonKangoShijishoRyuiShiji getRyuiShiji() {
        if(ryuiShiji==null){
            ryuiShiji = new IkenshoHoumonKangoShijishoRyuiShiji();
        }
        return ryuiShiji;
    }

    /**
     * ���a�P�^�u ��Ԃ��܂��B
     * @return ���a�P�^�u
     */
    public IkenshoHoumonKangoShijishoSick getShoubyou() {
        if(shoubyou==null){
            shoubyou = new IkenshoHoumonKangoShijishoSick();
        }
        return shoubyou;
    }

    /**
     * ���a�Q�^�u ��Ԃ��܂��B
     * @return ���a�Q�^�u
     */
    public IkenshoHoumonKangoShijishoSick2 getShoubyou2() {
        if(shoubyou2==null){
            shoubyou2 = new IkenshoHoumonKangoShijishoSick2();
        }
        return shoubyou2;
    }

    /**
     * ���L�E�K��_�H���˃^�u ��Ԃ��܂��B
     * @return ���L�E�K��_�H���˃^�u
     */
    public IkenshoHoumonKangoShijishoTenteki getTenteki() {
        if(tenteki==null){
            tenteki = new IkenshoHoumonKangoShijishoTenteki();
        }
        return tenteki;
    }

    /**
     * ���ʂȈ�Ã^�u ��Ԃ��܂��B
     * @return ���ʂȈ�Ã^�u
     */
    public IkenshoHoumonKangoShijishoSpecial getTokubetu() {
        if(tokubetu==null){
            tokubetu = new IkenshoHoumonKangoShijishoSpecial();
        }
        return tokubetu;
    }

    /**
     * �K��Ō�w�����̃t�H�[�}�b�g�敪��Ԃ��܂��B
     * @return �K��Ō�w�����̃t�H�[�}�b�g�敪 
     */
    protected String getFormatKubun() {
        //�K��Ō�w����
        return "0";
    }

    /**
     * override���Ďw�����}������SQL�L�[���ǉ����܂��B
     * 
     * @param sb �ǉ���
     */
    protected void appendInsertShijishoKeys(StringBuffer sb) {

    }

    /**
     * override���Ďw�����}������SQL�o�����[���ǉ����܂��B
     * 
     * @throws ParseException ��͗�O
     * @param sb �ǉ���
     */
    protected void appendInsertShijishoValues(StringBuffer sb)
            throws ParseException {

    }

    /**
     * override���Ďw�����X�V����SQL�L�[���ǉ����܂��B
     * 
     * @param sb �ǉ���
     * @throws ParseException ��͗�O
     */
    protected void appendUpdateShijishoStetement(StringBuffer sb)
            throws ParseException {

    }
    // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    


    //2009/01/16 [Tozo Tanaka] Add - begin
    protected int getMedicineViewCount() {
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
//        int ikenshoCount = super.getMedicineViewCount();
//        int shijishoCount = ikenshoCount;
//        try {
//            if (
//                    ACFrame
//                    .getInstance()
//                    .hasProperty(
//                            "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6")
//                    && ACCastUtilities
//                            .toBoolean(
//                                    ACFrame
//                                            .getInstance()
//                                            .getProperty(
//                                                    "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6"),
//                                    false)
//            ) {
//                //�w�����ݒ��D��
//                shijishoCount = 6;
//            }
//        } catch (Exception e) {
//        }
//        return shijishoCount;
        return 8;
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
  }
    //2009/01/16 [Tozo Tanaka] Add - end

//  [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    public void doSelect()throws Exception {
        if(originalData!=null){
            originalData.put("FORMAT_KBN", getFormatKubun());
        }
        super.doSelect();
    }
//  [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
    
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin �y2010�N�x�v�]�Ή��z
    private StringBuffer warningMessage = null;
    public void setWarningMessage(String msg) {
    	warningMessage.append("�E" + msg + IkenshoConstants.LINE_SEPARATOR);
    }
    // --- Override ---
	protected boolean canControlUpdate() throws Exception {
		// �G���[�`�F�b�N
		Iterator it = tabArray.iterator();
		while (it.hasNext()) {
			IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer)it.next();
			if (!tab.noControlError()) {
				tabs.setSelectedComponent(tab);
				return false;
			}
		}
		// �x���`�F�b�N
		warningMessage = new StringBuffer();
		it = tabArray.iterator();
		while (it.hasNext()) {
			IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer)it.next();
			if (!tab.noControlWarning()) {
				tabs.setSelectedComponent(tab);
				return false;
			}
		}
		
		if (warningMessage.length() != 0) {
			 
			ACMessageBox.show("�ȉ��̍��ڂ�1������̕����������𒴂��Ă��邽��2���ŏo�͂��܂��B"
							  + IkenshoConstants.LINE_SEPARATOR
							  + warningMessage.toString()
							  + "1������Ɏ��߂����ꍇ�́A�L�����Z�����āA���͂��Ȃ����Ă��������B");
		}

		return true;
	}
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end �y2010�N�x�v�]�Ή��z
}
