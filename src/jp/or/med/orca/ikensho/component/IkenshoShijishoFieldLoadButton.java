package jp.or.med.orca.ikensho.component;

import java.sql.SQLException;
import java.util.Map;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.util.VRArrayList;

//[ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
public class IkenshoShijishoFieldLoadButton extends ACButton {
    protected String loadRecentFormatKbn = "0";
    protected String loadRecentMessage = "";
    protected String loadRecentFieldName = "";
    protected ACDBManager loadRecentDBManager;
    protected String loadRecentPatientNo;

    /**
     * ����Ǎ��̑ΏۂƂȂ�t�B�[���h�� ��Ԃ��܂��B
     * 
     * @return ����Ǎ��̑ΏۂƂȂ�t�B�[���h��
     */
    protected String getLoadRecentFieldName() {
        return loadRecentFieldName;
    }

    /**
     * ����Ǎ��̑ΏۂƂȂ�t�B�[���h�� ��ݒ肵�܂��B
     * 
     * @param loadRecentFieldName ����Ǎ��̑ΏۂƂȂ�t�B�[���h��
     */
    public void setLoadRecentFieldName(String loadRecentFieldName) {
        this.loadRecentFieldName = loadRecentFieldName;
    }

    /**
     * ����Ǎ��̑ΏۂƂȂ镶���敪 ��Ԃ��܂��B
     * 
     * @return ����Ǎ��̑ΏۂƂȂ镶���敪
     */
    public String getLoadRecentFormatKbn() {
        return loadRecentFormatKbn;
    }

    /**
     * ����Ǎ��̑ΏۂƂȂ镶���敪 ��ݒ肵�܂��B
     * 
     * @param loadRecentFormatKbn ����Ǎ��̑ΏۂƂȂ镶���敪
     */
    public void setLoadRecentFormatKbn(String loadRecentFormatKbn) {
        this.loadRecentFormatKbn = loadRecentFormatKbn;
    }

    /**
     * ����Ǎ��m�F���̃��b�Z�[�W ��Ԃ��܂��B
     * 
     * @return ����Ǎ��m�F���̃��b�Z�[�W
     */
    public String getLoadRecentMessage() {
        return loadRecentMessage;
    }

    /**
     * ����Ǎ��m�F���̃��b�Z�[�W ��ݒ肵�܂��B
     * 
     * @param loadRecentMessage ����Ǎ��m�F���̃��b�Z�[�W
     */
    public void setLoadRecentMessage(String loadRecentMessage) {
        this.loadRecentMessage = loadRecentMessage;
    }

    /**
     * ����Ǎ��p��DBManager ��Ԃ��܂��B
     * 
     * @return ����Ǎ��p��DBManager
     */
    public ACDBManager getLoadRecentDBManager() {
        return loadRecentDBManager;
    }

    /**
     * ����Ǎ��p��DBManager ��ݒ肵�܂��B
     * 
     * @param loadRecentDBManager ����Ǎ��p��DBManager
     */
    public void setLoadRecentDBManager(ACDBManager loadRecentDBManager) {
        this.loadRecentDBManager = loadRecentDBManager;
    }

    /**
     * ����Ǎ��̑ΏۂƂȂ銳�Ҕԍ� ��Ԃ��܂��B
     * 
     * @return ����Ǎ��̑ΏۂƂȂ銳�Ҕԍ�
     */
    public String getLoadRecentPatientNo() {
        return loadRecentPatientNo;
    }

    /**
     * ����Ǎ��̑ΏۂƂȂ銳�Ҕԍ� ��ݒ肵�܂��B
     * 
     * @param loadRecentPatientNo ����Ǎ��̑ΏۂƂȂ銳�Ҕԍ�
     */
    public void setLoadRecentPatientNo(String loadRecentPatientNo) {
        this.loadRecentPatientNo = loadRecentPatientNo;
    }

    /**
     * ������ǂݍ��݂܂��B
     * 
     * @return �ǂݍ��݌���(���s����null)
     */
    public String getRecentData() {
        ACDBManager dbm = getLoadRecentDBManager();
        if (ACTextUtilities.isNullText(getLoadRecentFieldName()) || dbm == null) {
            return null;
        }
        if (!ACTextUtilities.isNullText(getLoadRecentMessage())) {
            if (ACMessageBox.showOkCancel(getLoadRecentMessage()) != ACMessageBox.RESULT_YES) {
                return null;
            }
        }

        StringBuffer sb = new StringBuffer();
        sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(getLoadRecentFieldName());
        sb.append(" FROM");
        
        if (!ACTextUtilities.isNullText(getLoadRecentFormatKbn())) {
            sb.append(" SIS_ORIGIN");
            sb.append(" JOIN");
            sb.append(" COMMON_IKN_SIS");
            sb.append(" ON");
            sb.append(" (SIS_ORIGIN.PATIENT_NO = COMMON_IKN_SIS.PATIENT_NO)");
            sb.append("AND(SIS_ORIGIN.EDA_NO = COMMON_IKN_SIS.EDA_NO)");
            sb.append("AND(COMMON_IKN_SIS.DOC_KBN = 2)");
            sb.append(" WHERE");
            sb.append(" (SIS_ORIGIN.PATIENT_NO = ");
            sb.append(getLoadRecentPatientNo());
            sb.append(")");
            sb.append(" AND(SIS_ORIGIN.FORMAT_KBN = ");
            sb.append(getLoadRecentFormatKbn());
            sb.append(")");
            sb.append(" ORDER BY");
            sb.append(" SIS_ORIGIN.EDA_NO DESC");
        }else{
            //�����敪�̎w�肪�Ȃ����S������Ώۂ�CREATE_DT���ŐV�œ��ʖK��Ō�w�����ȊO�𒊏o
            sb.append(" COMMON_IKN_SIS");
            sb.append(" LEFT JOIN");
            sb.append(" SIS_ORIGIN");
            sb.append(" ON");
            sb.append(" (COMMON_IKN_SIS.DOC_KBN = 2)");
            sb.append("AND(COMMON_IKN_SIS.PATIENT_NO = SIS_ORIGIN.PATIENT_NO)");
            sb.append("AND(COMMON_IKN_SIS.EDA_NO = SIS_ORIGIN.EDA_NO)");
            sb.append(" LEFT JOIN");
            sb.append(" IKN_ORIGIN");
            sb.append(" ON");
            sb.append(" (COMMON_IKN_SIS.DOC_KBN = 1)");
            sb.append("AND(COMMON_IKN_SIS.PATIENT_NO = IKN_ORIGIN.PATIENT_NO)");
            sb.append("AND(COMMON_IKN_SIS.EDA_NO = IKN_ORIGIN.EDA_NO)");
            sb.append(" WHERE");
            sb.append(" (COMMON_IKN_SIS.PATIENT_NO = ");
            sb.append(getLoadRecentPatientNo());
            sb.append(")");
            sb.append("AND(");
            sb.append(" (COMMON_IKN_SIS.DOC_KBN != 2)");
            sb.append(" OR(SIS_ORIGIN.FORMAT_KBN != 1)");
            sb.append(")");
            sb.append(" ORDER BY");
            sb.append(" COALESCE(IKN_ORIGIN.CREATE_DT, SIS_ORIGIN.CREATE_DT) DESC");
        }

        VRArrayList array;
        try {
            array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.size() > 0) {
                Map row = (Map) array.get(0);
                return ACCastUtilities.toString(row
                        .get(getLoadRecentFieldName()), "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ����Ǎ��֘A�̐ݒ���ꊇ�w�肵�܂��B
     * 
     * @param dbm DB�}�l�[�W��
     * @param patientNo ���Ҕԍ�
     * @param fieldName �t�B�[���h��
     * @param formatKbn �����敪
     * @param text �{�^���e�L�X�g
     * @param mnemonic �{�^���j�[���j�b�N
     * @param message �m�F���b�Z�[�W
     */
    public void setLoadRecentSetting(ACDBManager dbm, String patientNo,
            String fieldName, String formatKbn, String text, char mnemonic,
            String message) {
        setLoadRecentDBManager(dbm);
        setLoadRecentPatientNo(patientNo);
        setLoadRecentFieldName(fieldName);
        setLoadRecentFormatKbn(formatKbn);
        setLoadRecentMessage(message);
        setText(text);
        setMnemonic(mnemonic);
    }

}
// [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
