package jp.nichicom.ac.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * 大量データを対象とするテーブルを自動分割して管理するクラスです。
 * <p>
 * 年や年度単位でテーブルを分割生成する際に使用します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACSeparateTablar
 */
public class ACSeparateTableManager {
    /**
     * 年月単位の管理を表す日付チェックモード定数です。
     */
    public static final int CHECK_DATE_SPAN_MONTH = 1;

    /**
     * 年単位の管理を表す日付チェックモード定数です。
     */
    public static final int CHECK_DATE_SPAN_YEAR = 0;

    private int checkDateSpan = ACSeparateTableManager.CHECK_DATE_SPAN_YEAR;
    private boolean fiscalYear;
    private String managedTableNameFieldName = "TABLE_NAME";
    private HashMap managedTables;
    private String manageTableName;
    private String maximumModifierFieldName = "MAXIMUM_YEAR";
    private String minimumModifierFieldName = "MINIMUM_YEAR";
    private String timeStampFieldName = "LAST_TIME";
    private boolean useManagementTable;
    private boolean useTimestampField;

    /**
     * 分割対象のテーブル集合を追加します。
     * 
     * @param tableID 引数のテーブル集合を表すID
     * @param tables 分割対象のテーブル集合
     */
    public void addSeparateTable(String tableID, List tables) {
        HashMap map = getManagedTables();
        if (map == null) {
            map = new HashMap();
            setManagedTables(map);
        }
        map.put(tableID, tables);
    }

    /**
     * 分割対象のテーブル集合を追加します。
     * 
     * @param tableID 引数のテーブル集合を表すID
     * @param tables 分割対象のテーブル集合
     */
    public void addSeparateTable(String tableID, ACSeparateTablar[] tables) {
        addSeparateTable(tableID, java.util.Arrays.asList(tables));
    }

    /**
     * 日付チェックモード を返します。
     * <p>
     * <code>
     * CHECK_DATE_SPAN_YEAR : 年単位
     * CHECK_DATE_SPAN_MONTH : 月単位
     * </code>
     * </p>
     * 
     * @return 日付チェックモード
     */
    public int getCheckDateSpan() {
        return checkDateSpan;
    }

    /**
     * 管理対象テーブル名フィールド名 を返します。
     * 
     * @return 管理対象テーブル名フィールド名
     */
    public String getManagedTableNameFieldName() {
        return managedTableNameFieldName;
    }

    /**
     * 管理対象のテーブル集合 を返します。
     * 
     * @return 管理対象のテーブル集合
     */
    public HashMap getManagedTables() {
        return managedTables;
    }

    /**
     * 管理テーブル名 を返します。
     * 
     * @return 管理テーブル名
     */
    public String getManageTableName() {
        return manageTableName;
    }

    /**
     * 登録済みの最も大きな修飾語フィールド名 を返します。
     * 
     * @return 登録済みの最も大きな修飾語フィールド名
     */
    public String getMaximumModifierFieldName() {
        return maximumModifierFieldName;
    }

    /**
     * 登録済みの最も小さな修飾語フィールド名 を返します。
     * 
     * @return 登録済みの最も小さな修飾語フィールド名
     */
    public String getMinimumModifierFieldName() {
        return minimumModifierFieldName;
    }

    /**
     * 分割対象のテーブル集合を返します。
     * 
     * @param tableID テーブル集合を表すID
     * @return 分割対象のテーブル集合
     */
    public ArrayList getSeparateTable(String tableID) {
        HashMap map = getManagedTables();
        if (map == null) {
            return null;
        }
        return (ArrayList) map.get(tableID);

    }

    /**
     * 日付を基準にアクセスすべき分割テーブルの修飾語を返します。
     * <p>
     * 分割テーブル管理テーブルの値を確認し、分割対象のテーブルが作成されていない場合は作成します。管理テーブルの更新も行ないますので、必要であれば呼び出し前後にトランザクションの開始とコミットを実行してください。
     * </p>
     * <p>
     * 失敗した場合は空文字が返ります。
     * </p>
     * 
     * @param dbm DBManager
     * @param tableID 分割テーブルのキー
     * @param targetDate 対象年月日
     * @return サフィックス。"_"+年度
     * @throws Exception 処理例外
     */
    public int getTableModifyFromDate(ACDBManager dbm, String tableID,
            Calendar targetDate) throws Exception {

        if (isFiscalYear()) {
            // 3減算することで、2005/4を200/1とみなし、年度を取得できる
            targetDate.add(Calendar.MONTH, -3);
        }
        int date;

        switch (getCheckDateSpan()) {
        case CHECK_DATE_SPAN_YEAR:
            date = targetDate.get(Calendar.YEAR);
            break;
        case CHECK_DATE_SPAN_MONTH:
            date = targetDate.get(Calendar.YEAR) * 10
                    + (targetDate.get(Calendar.MONTH) + 1);
            break;
        default:
            throw new IllegalStateException("不正な日付チェックモードです。");
        }

        if (!updateManagementTable(dbm, tableID, date)) {
            return -1;
        }

        if (!checkSeparateTable(dbm, tableID, date)) {
            return -1;
        }

        return date;
    }

    /**
     * 日付を基準にアクセスすべき分割テーブルの修飾語を返します。
     * <p>
     * 分割テーブル管理テーブルの値を確認し、分割対象のテーブルが作成されていない場合は作成します。管理テーブルの更新も行ないますので、必要であれば呼び出し前後にトランザクションの開始とコミットを実行してください。
     * </p>
     * <p>
     * 失敗した場合は空文字が返ります。
     * </p>
     * 
     * @param dbm DBManager
     * @param tableID 分割テーブルのキー
     * @param targetDate 対象年月日
     * @return サフィックス。"_"+年度
     * @throws Exception 処理例外
     */
    public int getTableModifyFromDate(ACDBManager dbm, String tableID,
            Date targetDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        return getTableModifyFromDate(dbm, tableID, cal);
    }

    /**
     * 更新日時を管理するフィールド名 を返します。
     * 
     * @return 更新日時を管理するフィールド名
     */
    public String getTimeStampFieldName() {
        return timeStampFieldName;
    }

    /**
     * 年ではなく年度で判断するか を返します。
     * 
     * @return 年ではなく年度で判断するか
     */
    public boolean isFiscalYear() {
        return fiscalYear;
    }

    /**
     * 管理テーブルを使用するか を返します。
     * 
     * @return 管理テーブルを使用するか
     */
    public boolean isUseManagementTable() {
        return useManagementTable;
    }

    /**
     * 更新日時を管理するか を返します。
     * 
     * @return 更新日時を管理するか名
     */
    public boolean isUseTimestampField() {
        return useTimestampField;
    }

    /**
     * 日付チェックモード を設定します。
     * <p>
     * <code>
     * CHECK_DATE_SPAN_YEAR : 年単位
     * CHECK_DATE_SPAN_MONTH : 月単位
     * </code>
     * </p>
     * 
     * @param checkDateSpan 日付チェックモード
     */
    public void setCheckDateSpan(int checkDateSpan) {
        this.checkDateSpan = checkDateSpan;
    }

    /**
     * 年ではなく年度で判断するか を設定します。
     * 
     * @param fiscalYear 年ではなく年度で判断するか
     */
    public void setFiscalYear(boolean fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    /**
     * 管理対象テーブル名フィールド名 を設定します。
     * 
     * @param managedTableNameFieldName 管理対象テーブル名フィールド名
     */
    public void setManagedTableNameFieldName(String managedTableNameFieldName) {
        this.managedTableNameFieldName = managedTableNameFieldName;
    }

    /**
     * 管理対象のテーブル集合 を設定します。
     * 
     * @param managedTables 管理対象のテーブル集合
     */
    public void setManagedTables(HashMap managedTables) {
        this.managedTables = managedTables;
    }

    /**
     * 管理テーブル名 を設定します。
     * 
     * @param manageTableName 管理テーブル名
     */
    public void setManageTableName(String manageTableName) {
        this.manageTableName = manageTableName;
    }

    /**
     * 登録済みの最も大きな修飾語フィールド名 を設定します。
     * 
     * @param maximumModifierFieldName 登録済みの最も大きな修飾語フィールド名
     */
    public void setMaximumModifierFieldName(String maximumModifierFieldName) {
        this.maximumModifierFieldName = maximumModifierFieldName;
    }

    /**
     * 登録済みの最も小さな修飾語フィールド名 を設定します。
     * 
     * @param minimumModifierFieldName 登録済みの最も小さな修飾語フィールド名
     */
    public void setMinimumModifierFieldName(String minimumModifierFieldName) {
        this.minimumModifierFieldName = minimumModifierFieldName;
    }

    /**
     * 更新日時を管理するフィールド名 を設定します。
     * 
     * @param timeStampFieldName 更新日時を管理するフィールド名
     */
    public void setTimeStampFieldName(String timeStampFieldName) {
        this.timeStampFieldName = timeStampFieldName;
    }

    /**
     * 管理テーブルを使用するか を設定します。
     * 
     * @param useManagementTable 管理テーブルを使用するか
     */
    public void setUseManagementTable(boolean useManagementTable) {
        this.useManagementTable = useManagementTable;
    }

    /**
     * 更新日時を管理するか を設定します。
     * 
     * @param useTimestampField 更新日時を管理するか
     */
    public void setUseTimestampField(boolean useTimestampField) {
        this.useTimestampField = useTimestampField;
    }

    /**
     * テーブルを分割すべきかチェックします。
     * 
     * @param dbm DBManager
     * @param tableID 分割テーブルのキー
     * @param newValue 管理値
     * @return 処理に成功したか
     * @throws SQLException 処理例外
     */
    protected boolean checkSeparateTable(ACDBManager dbm, String tableID,
            int newValue) throws SQLException {
        Object obj = getManagedTables().get(tableID);
        if (!(obj instanceof List)) {
            throw new IllegalStateException("テーブルIDに対応するテーブル集合が見つかりません。");
        }
        List detailTables = (List) obj;

        // 対象テーブルの存在を確認する
        int end = detailTables.size();
        for (int i = 0; i < end; i++) {
            obj = detailTables.get(i);
            if (!(obj instanceof ACSeparateTablar)) {
                return false;
            }
            ACSeparateTablar table = (ACSeparateTablar) obj;
            if(!table.isExistTable(dbm, newValue)){
                // テーブルが存在しない
                table.createTable(dbm, newValue);
            }
        }
        return true;
    }

    /**
     * 管理テーブルを更新します。
     * 
     * @param dbm DBManager
     * @param tableID 分割テーブルのキー
     * @param newValue 管理値
     * @return 成功したか
     * @throws Exception
     */
    protected boolean updateManagementTable(ACDBManager dbm,
            String tableID, int newValue) throws Exception {
        if (isUseManagementTable()) {
            // 管理テーブルを使用する場合
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ");
            sb.append(getManageTableName() + "."
                    + getManagedTableNameFieldName());
            sb.append("," + getManageTableName() + "."
                    + getMinimumModifierFieldName());
            sb.append("," + getManageTableName() + "."
                    + getMaximumModifierFieldName());
            sb.append(" FROM ");
            sb.append(getManageTableName());
            sb.append(" WHERE");
            sb.append(" (" + getManageTableName() + "."
                    + getManagedTableNameFieldName() + " = '" + tableID + "')");
            VRList ret = dbm.executeQuery(sb.toString());
            if (ret.getDataSize() <= 0) {
                // 該当するレコードがない
                sb = new StringBuffer();
                sb.append("INSERT INTO ");
                sb.append(getManageTableName() + "( ");
                sb.append(getManagedTableNameFieldName());
                sb.append("," + getMinimumModifierFieldName());
                sb.append("," + getMaximumModifierFieldName());
                if (isUseTimestampField()) {
                    sb.append("," + getTimeStampFieldName());
                }
                sb.append(" )VALUES( ");
                sb.append("'" + tableID + "'");
                sb.append("," + newValue);
                sb.append("," + newValue);
                if (isUseTimestampField()) {
                    sb.append(",CURRENT_TIMESTAMP");
                }
                sb.append(")");
                if (dbm.executeUpdate(sb.toString()) <= 0) {
                    // INSERT失敗
                    return false;
                }
            } else {
                VRMap row = (VRMap) ret.getData();
                int min = Integer.parseInt(String.valueOf(VRBindPathParser.get(
                        getMinimumModifierFieldName(), row)));
                int max = Integer.parseInt(String.valueOf(VRBindPathParser.get(
                        getMaximumModifierFieldName(), row)));
                if (newValue < min) {
                    // 作成済み最小年度より小さい
                    sb = new StringBuffer();
                    sb.append("UPDATE ");
                    sb.append(getManageTableName());
                    sb.append(" SET ");
                    sb.append(getMinimumModifierFieldName() + " = " + newValue);
                    if (isUseTimestampField()) {
                        sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                    }
                    sb.append(" WHERE");
                    sb.append(" (TABLE_NAME = '" + tableID + "')");
                    if (dbm.executeUpdate(sb.toString()) <= 0) {
                        return false;
                    }
                } else if (newValue > max) {
                    // 作成済み最大年度より大きい
                    sb = new StringBuffer();
                    sb.append("UPDATE ");
                    sb.append(getManageTableName());
                    sb.append(" SET ");
                    sb.append(getMaximumModifierFieldName() + " = " + newValue);
                    if (isUseTimestampField()) {
                        sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                    }
                    sb.append(" WHERE");
                    sb.append(" (TABLE_NAME = '" + tableID + "')");
                    if (dbm.executeUpdate(sb.toString()) <= 0) {
                        return false;
                    }
                }
            }

        }
        return true;
    }
}