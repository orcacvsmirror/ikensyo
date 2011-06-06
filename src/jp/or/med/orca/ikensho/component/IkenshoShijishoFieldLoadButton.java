package jp.or.med.orca.ikensho.component;

import java.sql.SQLException;
import java.util.Map;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.util.VRArrayList;

//[ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
public class IkenshoShijishoFieldLoadButton extends ACButton {
    protected String loadRecentFormatKbn = "0";
    protected String loadRecentMessage = "";
    protected String loadRecentFieldName = "";
    protected ACDBManager loadRecentDBManager;
    protected String loadRecentPatientNo;

    /**
     * 履歴読込の対象となるフィールド名 を返します。
     * 
     * @return 履歴読込の対象となるフィールド名
     */
    protected String getLoadRecentFieldName() {
        return loadRecentFieldName;
    }

    /**
     * 履歴読込の対象となるフィールド名 を設定します。
     * 
     * @param loadRecentFieldName 履歴読込の対象となるフィールド名
     */
    public void setLoadRecentFieldName(String loadRecentFieldName) {
        this.loadRecentFieldName = loadRecentFieldName;
    }

    /**
     * 履歴読込の対象となる文書区分 を返します。
     * 
     * @return 履歴読込の対象となる文書区分
     */
    public String getLoadRecentFormatKbn() {
        return loadRecentFormatKbn;
    }

    /**
     * 履歴読込の対象となる文書区分 を設定します。
     * 
     * @param loadRecentFormatKbn 履歴読込の対象となる文書区分
     */
    public void setLoadRecentFormatKbn(String loadRecentFormatKbn) {
        this.loadRecentFormatKbn = loadRecentFormatKbn;
    }

    /**
     * 履歴読込確認時のメッセージ を返します。
     * 
     * @return 履歴読込確認時のメッセージ
     */
    public String getLoadRecentMessage() {
        return loadRecentMessage;
    }

    /**
     * 履歴読込確認時のメッセージ を設定します。
     * 
     * @param loadRecentMessage 履歴読込確認時のメッセージ
     */
    public void setLoadRecentMessage(String loadRecentMessage) {
        this.loadRecentMessage = loadRecentMessage;
    }

    /**
     * 履歴読込用のDBManager を返します。
     * 
     * @return 履歴読込用のDBManager
     */
    public ACDBManager getLoadRecentDBManager() {
        return loadRecentDBManager;
    }

    /**
     * 履歴読込用のDBManager を設定します。
     * 
     * @param loadRecentDBManager 履歴読込用のDBManager
     */
    public void setLoadRecentDBManager(ACDBManager loadRecentDBManager) {
        this.loadRecentDBManager = loadRecentDBManager;
    }

    /**
     * 履歴読込の対象となる患者番号 を返します。
     * 
     * @return 履歴読込の対象となる患者番号
     */
    public String getLoadRecentPatientNo() {
        return loadRecentPatientNo;
    }

    /**
     * 履歴読込の対象となる患者番号 を設定します。
     * 
     * @param loadRecentPatientNo 履歴読込の対象となる患者番号
     */
    public void setLoadRecentPatientNo(String loadRecentPatientNo) {
        this.loadRecentPatientNo = loadRecentPatientNo;
    }

    /**
     * 履歴を読み込みます。
     * 
     * @return 読み込み結果(失敗時はnull)
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
            //文書区分の指定がない＝全文書を対象にCREATE_DTが最新で特別訪問看護指示書以外を抽出
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
     * 履歴読込関連の設定を一括指定します。
     * 
     * @param dbm DBマネージャ
     * @param patientNo 患者番号
     * @param fieldName フィールド名
     * @param formatKbn 文書区分
     * @param text ボタンテキスト
     * @param mnemonic ボタンニーモニック
     * @param message 確認メッセージ
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
// [ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能
