package jp.nichicom.ac.core.version;

import java.util.Map;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.vr.util.VRList;

/**
 * デフォルトのバージョン情報差異補正リスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/28
 */
public class ACDefaultVersionAdjustmentListener implements
        ACVersionAdjustmentListener {
    private ACDBManager dbm;
    private String masterDataVersion;
    private String schemeVersion;
    private ACSplashable progressSplash;
    private boolean useSplash = true;

    public ACSplashable getProgressSplash() {
        if (isUseSplash()) {
            if (progressSplash == null) {
                ACFrameEventProcesser processer = ACFrame.getInstance()
                        .getFrameEventProcesser();
                if (processer != null) {
                    try {
                        progressSplash = processer.createSplash("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return progressSplash;
        }
        return null;
    }

    /**
     * 進捗報告用のスプラッシュ を設定します。
     * 
     * @param splash 進捗報告用のスプラッシュ
     */
    public void setProgressSplash(ACSplash progressSplash) {
        this.progressSplash = progressSplash;
    }

    /**
     * 進捗報告用のスプラッシュを使用するか を返します。
     * 
     * @return useSplash
     */
    public boolean isUseSplash() {
        return useSplash;
    }

    /**
     * 進捗報告用のスプラッシュを使用するか を設定します。
     * 
     * @param useSplash 進捗報告用のスプラッシュを使用するか
     */
    public void setUseSplash(boolean useSplash) {
        this.useSplash = useSplash;
    }

    /**
     * コンストラクタです。
     */
    public ACDefaultVersionAdjustmentListener() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param dbm DBマネージャ
     */
    public ACDefaultVersionAdjustmentListener(ACDBManager dbm) {
        super();
        setDBManager(dbm);
    }

    /**
     * DBマネージャ を返します。
     * 
     * @return DBマネージャ
     */
    public ACDBManager getDBManager() {
        return dbm;
    }

    public String getVersion(String key) {
        try {
            if ("system".equals(key)) {
                // システムバージョン
                return getSystemVersion();
            } else if ("scheme".equals(key)) {
                // スキーマバージョン
                return getSchemeVersion();
            } else if ("master".equals(key)) {
                // マスタデータバージョン
                return getMasterDataVersion();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * DBマネージャ を設定します。
     * 
     * @param manager DBマネージャ
     */
    public void setDBManager(ACDBManager dbm) {
        this.dbm = dbm;
    }

    public boolean setVersion(String key, String value) {
        try {
            if ("system".equals(key)) {
                // システムバージョン
                setSystemVersion(value);
            } else if ("scheme".equals(key)) {
                // スキーマバージョン
                setSchemeVersion(value);
            } else if ("master".equals(key)) {
                // マスタデータバージョン
                setMasterDataVersion(value);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * DBのバージョン情報を取得するSQLクエリを取得します。
     * 
     * @return DBのバージョン情報を取得するSQLクエリ
     */
    protected String getDBVersionQuerySQL() {
        return "SELECT * FROM " + getDBVersionTableName();
    }

    /**
     * DBのバージョン情報を格納するテーブル名を取得します。
     * 
     * @return DBのバージョン情報を格納するテーブル名
     */
    protected String getDBVersionTableName() {
        return "M_VERSION";
    }

    /**
     * マスタデータバージョン を返します。
     * 
     * @throws Exception 処理例外
     * @return マスタデータバージョン
     */
    protected String getMasterDataVersion() throws Exception {
        if (masterDataVersion == null) {
            refreshDBVersion();
        }
        return masterDataVersion;
    }

    /**
     * マスタデータバージョンをあらわすフィールド名を返します。
     * 
     * @return マスタデータバージョンをあらわすフィールド名
     */
    protected String getMasterDataVersionKey() {
        return "MASTER_DATA_VERSION";
    }

    /**
     * マスタデータバージョンを更新するSQLを取得します。
     * 
     * @param version バージョン
     * @return マスタデータバージョンを更新するSQL
     */
    protected String getMasterDataVersionUpdateSQL(String version) {
        return "UPDATE " + getDBVersionTableName() + " SET "
                + getMasterDataVersionKey() + " = '" + version + "'";
    }

    /**
     * スキーマバージョン を返します。
     * 
     * @throws Exception 処理例外
     * @return スキーマバージョン
     */
    protected String getSchemeVersion() throws Exception {
        if (schemeVersion == null) {
            refreshDBVersion();
        }
        return schemeVersion;
    }

    /**
     * スキーマバージョンをあらわすフィールド名を返します。
     * 
     * @return スキーマバージョンをあらわすフィールド名
     */
    protected String getSchemeVersionKey() {
        return "SCHEME_VERSION";
    }

    /**
     * スキーマバージョンを更新するSQLを取得します。
     * 
     * @param version バージョン
     * @return スキーマバージョンを更新するSQL
     */
    protected String getSchemeVersionUpdateSQL(String version) {
        return "UPDATE " + getDBVersionTableName() + " SET "
                + getSchemeVersionKey() + " = '" + version + "'";
    }

    /**
     * システムのバージョン情報を返します。
     * 
     * @return システムのバージョン情報
     * @throws Exception 処理例外
     */
    protected String getSystemVersion() throws Exception {
        return ACFrame.getInstance().getProperty(getSystemVersionKey());
    }

    /**
     * システムのバージョンをあらわすバインドパスを返します。
     * 
     * @return システムのバージョンをあらわすバインドパス
     */
    protected String getSystemVersionKey() {
        return "Version/No";
    }

    /**
     * DBバージョン情報を取得します。
     * 
     * @throws Exception 処理例外
     */
    protected void refreshDBVersion() throws Exception {
        VRList list = getDBManager().executeQuery(getDBVersionQuerySQL());
        if (!list.isEmpty()) {
            Map map = (Map) list.getData();
            Object obj = map.get(getSchemeVersionKey());
            if (obj != null) {
                schemeVersion = String.valueOf(obj);
            }
            obj = map.get(getMasterDataVersionKey());
            if (obj != null) {
                masterDataVersion = String.valueOf(obj);
            }
        }
    }

    /**
     * マスタデータバージョン を設定します。
     * 
     * @param masterVersion マスタデータバージョン
     * @throws Exception 処理例外
     */
    protected void setMasterDataVersion(String masterVersion) throws Exception {
        getDBManager().executeUpdate(
                getMasterDataVersionUpdateSQL(masterVersion));
        this.masterDataVersion = masterVersion;
    }

    /**
     * スキーマバージョン を設定します。
     * 
     * @param schemeVersion スキーマバージョン
     * @throws Exception 処理例外
     */
    protected void setSchemeVersion(String schemeVersion) throws Exception {
        getDBManager().executeUpdate(getSchemeVersionUpdateSQL(schemeVersion));
        this.schemeVersion = schemeVersion;
    }

    /**
     * システムのバージョン情報を設定します。
     * 
     * @param システムのバージョン情報
     * @throws Exception 処理例外
     */
    protected void setSystemVersion(String version) throws Exception {
        ACFrame.getInstance().getPropertyXML().setValueAt(
                getSystemVersionKey(), version);
        ACFrame.getInstance().getPropertyXML().write();
    }

}
