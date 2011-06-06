package jp.or.med.orca.ikensho.sql;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box.Filler;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.sql.ACResultSetForwardBindSource;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.logging.VRLogger;

import org.firebirdsql.pool.FBWrappingDataSource;

/**
 *****************************************************************
 * アプリ: Ikensho
 * 開発者: 樋口　雅彦
 * 作成日: 2009  日本コンピューター株式会社 樋口 雅彦 新規作成
 * @since V3.0.9
 *
 *****************************************************************
 */
public class IkenshoInsurerBridgeFirebirdDBManager implements ACDBManager {

    private static FBWrappingDataSource fbwds = null;
    
    private static String masterPropertyPath = "masterProperty.xml";

    /** オブジェクト内で使用するコネクション */
    private Connection con = null;

    /**
     * DBのバージョン名称を取得する。
     * 
     * @return 取得したバージョン名称
     * @throws Exception
     */
    public String getDBMSVersion() throws Exception {

        String dbVersion = "unknown";
        boolean blnTran = true;

        try {
            if (con == null) {
                setConnection();
                blnTran = false;
            }
            dbVersion = (con.getMetaData()).getDatabaseProductVersion();

        } catch (SQLException e) {
            VRLogger
                    .warning("データベースバージョン取得(getDBMSVersion)失敗:\nDataBaseMetaData取得エラー\n"
                            + e.toString());
            throw e;
        } finally {
            if (!blnTran)
                releaseConnection();
        }

        return dbVersion;
    }

    /**
     * コンストラクタ 設定ファイルから、<BR>
     * DBConfig/Server - DBサーバ名<BR>
     * DBConfig/Port - 接続ポート<BR>
     * DBConfig/UserName - ユーザ名<BR>
     * DBConfig/Password - パスワード<BR>
     * DBConfig/Path - DBパス(ファイルパス)<BR>
     * を取得し、VRFirebirdDBManager のインスタンスを作成する。
     * 
     * @throws Exception DB設定失敗
     */
    public IkenshoInsurerBridgeFirebirdDBManager() throws Exception {
        createDBManager();
    }

    /**
     * コンストラクタ
     * 
     * @param server DBサーバ名
     * @param port 接続ポート
     * @param userName ユーザ名
     * @param pass パスワード
     * @param path DBパス(ファイルパス)
     * @param loginTimeOut ログインタイムアウト
     * @param maxPoolSize 最大プールサイズ
     * @throws Exception 実行例外
     */
    public IkenshoInsurerBridgeFirebirdDBManager(String server, int port,
            String userName, String pass, String path, int loginTimeOut,
            int maxPoolSize) throws Exception {
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize);
    }

    /**
     * コンストラクタ
     * 
     * @param server DBサーバ名
     * @param port 接続ポート
     * @param userName ユーザ名
     * @param pass パスワード
     * @param path DBパス(ファイルパス)
     * @param loginTimeOut ログインタイムアウト
     * @param maxPoolSize 最大プールサイズ
     * @param charSet 文字コード
     * @throws Exception 実行例外
     */
    public IkenshoInsurerBridgeFirebirdDBManager(String server, int port,
            String userName, String pass, String path, int loginTimeOut,
            int maxPoolSize, String charSet) throws Exception {
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize, charSet);
    }

    /**
     * FireBird DBManager の作成を行います。
     * 
     * @throws Exception 実行時例外
     */
    private void createDBManager() throws Exception {
        ACPropertyXML xml = ACFrame.getInstance().getPropertyXML();
        try {
            xml.read();
            String server = xml.getValueAt("DBConfig/Server");
            int port = Integer.parseInt(xml.getValueAt("DBConfig/Port"));
            String userName = xml.getValueAt("DBConfig/UserName");
            String pass = xml.getValueAt("DBConfig/Password");
            String path = xml.getValueAt("DBConfig/Path");
            int lengthPoint = -1;
            // 数値
            lengthPoint = path.lastIndexOf("\\");
            if(lengthPoint == -1) {
                lengthPoint = path.lastIndexOf("/");
            }
            String rootDataPath = "";
            if(path.length() > lengthPoint + 1) {
                // DB名までを取得
                rootDataPath = path.substring(0,lengthPoint+1);
            }
            // 接続先を定義する。
            String insurerPath = "";
            ACPropertyXML masterPropertyXML = new ACPropertyXML(masterPropertyPath);
            if (new File(masterPropertyPath).exists()) {
                // propertyファイルより接続先データベース名を定義する。
                masterPropertyXML.read();
                String insurerPropPath = "";
                // 手書きで変更を行った場合は接続先を変更できる仕様
                if(masterPropertyXML.hasValueAt("Config/InsurerDBPath")) {
                    insurerPropPath = masterPropertyXML.getValueAt("Config/InsurerDBPath");
                }
                String dbName = masterPropertyXML.getValueAt("Config/InsurerDBName");
                // 接続先の定義
                if(insurerPropPath == null || "".equals(insurerPropPath) || "default".equals(insurerPropPath)){
                    // 特に指定変更がない場合（通常）
                    insurerPath = rootDataPath + dbName;
                } else {
                    // ユーザーが独自書き込みしているケース（手書き対応）
                    insurerPath = insurerPropPath + dbName;
                }
            } else {
                insurerPath = "";
            }
            // 使用後は初期化
            masterPropertyXML=null;
            
            int loginTimeout = Integer.parseInt(xml
                    .getValueAt("DBConfig/LoginTimeOut"));
            int maxPoolSize = Integer.parseInt(xml
                    .getValueAt("DBConfig/MaxPoolSize"));

            String charSet = null;
            if (xml.hasValueAt("DBConfig/CharSet")) {
                charSet = xml.getValueAt("DBConfig/CharSet");
            }
            // マスターデータベースの場合強制設定
            if(charSet == null || "".equals(charSet)) {
                charSet = "SJIS_0208";
            }
            
            // DBコネクション
            createDBManager(server, port, userName, pass, insurerPath, loginTimeout,
                    maxPoolSize, charSet);
            
        } catch (Exception e) {
            VRLogger.warning(e.toString());
            throw e;
        }
        xml = null;
    }

    /**
     * FireBird DBManager の作成を行います。
     * 
     * @param server DBサーバ名
     * @param port 接続ポート
     * @param userName ユーザ名
     * @param pass パスワード
     * @param path DBパス(ファイルパス)
     * @param loginTimeOut ログインタイムアウト
     * @param maxPoolSize 最大プールサイズ
     * @throws Exception 実行例外
     */
    private void createDBManager(String server, int port, String userName,
            String pass, String path, int loginTimeOut, int maxPoolSize)
            throws Exception {
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize, null);
    }

    /**
     * FireBird DBManager の作成を行います。
     * 
     * @param server DBサーバ名
     * @param port 接続ポート
     * @param userName ユーザ名
     * @param pass パスワード
     * @param path DBパス(ファイルパス)
     * @param loginTimeOut ログインタイムアウト
     * @param maxPoolSize 最大プールサイズ
     * @param charSet 文字コード
     * @throws Exception 実行例外
     */
    private void createDBManager(String server, int port, String userName,
            String pass, String path, int loginTimeOut, int maxPoolSize,
            String charSet) throws Exception {
        try {
            if (fbwds == null) {
                fbwds = new FBWrappingDataSource();
            } else {
                return;
            }

            fbwds.setType("type4");
            fbwds.setDatabase(server + "/" + port + ":" + path);
            fbwds.setUserName(userName);
            fbwds.setPassword(pass);
            fbwds.setPooling(true);
            fbwds.setLoginTimeout(loginTimeOut);
            fbwds.setMaxPoolSize(maxPoolSize);

            if (charSet != null) {
                fbwds.setNonStandardProperty("isc_dpb_lc_ctype", charSet);
            }

        } catch (Exception ex) {
            VRLogger.warning(ex.toString());
            throw ex;
        }
    }

    /**
     * コネクションを取得する。
     * 
     * @return Connection FireBird Connection
     * @throws SQLException Connection 取得時例外
     */
    public Connection getConnection() throws SQLException {
        return getConnectionPool();
    }

    /**
     * プールからコネクションを取得します。
     * 
     * @return Connection FireBird Connection
     * @throws SQLException PooledConnection 取得時例外
     */
    private Connection getConnectionPool() throws SQLException {
        // プールからのコネクション取得を実行
        try {
            if (fbwds == null) {
                createDBManager();
            }
            return fbwds.getConnection();
        } catch (Exception e) {
            VRLogger.warning(e.getMessage());
            return null;
        }
    }

    /**
     * UPDATE 文の実行を行う。
     * 
     * @param sql String 実行する SQL 文
     * @return int 実行によって影響を受けた列数
     * @throws SQLException UPDATE 実行時例外
     */
    public int executeUpdate(String sql) throws SQLException {
        int r = 0;
        boolean blnTran = true;
        Statement stmt = null;

        try {
            // コネクション取得
            if (con == null) {
                setConnection();
                blnTran = false;
            }

            // ステートメント生成
            stmt = con.createStatement();
            // SQL文実行
            r = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            VRLogger.warning("executeUpdate失敗:\nSQL:" + sql + "\n"
                    + e.toString());
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            // コネクション破棄
            if (!blnTran)
                releaseConnection();
        }
        return r;
    }

    /**
     * BLOB型データの UPDATE 文の実行を行う。
     * 
     * @param sql String 実行する SQL 文(パラメーションクエリ)
     * @param is BLOB列に追加するInputStreamオブジェクト
     * @param length 追加するオブジェクトの長さ
     * @return int 実行によって影響を受けた列数
     * @throws SQLException UPDATE 実行時例外
     */
    public int executeUpdateBLOB(String sql, InputStream is, int length)
            throws SQLException {
        int r = 0;
        boolean blnTran = true;
        PreparedStatement pstmt = null;

        try {
            // コネクション取得
            if (con == null) {
                setConnection();
                blnTran = false;
            }
            // ステートメント生成
            pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            // BLOBオブジェクトの設定
            pstmt.setBinaryStream(1, is, length);
            // SQL文実行
            r = pstmt.executeUpdate();

        }
        // SQL文発行エラー
        catch (SQLException e) {
            VRLogger.warning("executeUpdateBLOB失敗:\nSQL:" + sql + "\n"
                    + e.toString());
            throw e;
        }

        finally {
            if (pstmt != null) {
                pstmt.close();
            }
            // コネクション破棄
            if (!blnTran)
                releaseConnection();
        }
        return r;
    }

    /**
     * SELECT 文の実行を行う。
     * 
     * @param sql String 実行する SELECT 文
     * @return 実行結果
     * @throws SQLException SELECT 文実行時例外
     */
    public VRList executeQuery(String sql) throws SQLException {
        VRList tbl = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean blnTran = true;

        try {
            if (con == null) {
                setConnection();
                blnTran = false;
            }
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            tbl = ACResultSetForwardBindSource.forward(rs);
        } catch (SQLException e) {
            VRLogger.warning("executeQuery失敗:\nSQL:" + sql + "\n"
                    + e.toString());
            throw e;
        } catch (NullPointerException e) {
            ACMessageBox
                    .showExclamation("データベースファイルが見つかりません。\nデータベースファイルを指定し直してください。");
            tbl = new VRArrayList();
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (!blnTran)
                releaseConnection();
        }

        return tbl;
    }

    public boolean isAvailableDBConnection() throws SQLException {
        boolean valid = false;
        // VRBindSource tbl = null;
        Statement stmt = null;
        ResultSet rs = null;
        boolean blnTran = true;

        try {
            if (con == null) {
                setConnection();
                blnTran = false;
            }
            stmt = con.createStatement();
            rs = stmt
                    .executeQuery("SELECT CURRENT_TIMESTAMP DB_TIME FROM RDB$DataBase");
            // tbl = ACResultSetForwardBindSource.forward(rs);
            valid = true;
        } catch (SQLException e) {
            VRLogger
                    .warning("コネクションテスト(isAvailableDBConnection)失敗:\nSQL:SELECT CURRENT_TIMESTAMP FROM RDB$DataBase\n"
                            + e.toString());
            throw e;
        } catch (NullPointerException e) {
        } finally {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
            if (!blnTran)
                releaseConnection();
        }
        return valid;
    }

    /**
     * コネクションの生成を行う。
     * 
     * @throws SQLException
     */
    private void setConnection() throws SQLException {
        if (con == null) {
            con = getConnection();
        }
    }

    /**
     * コネクションを閉じる。
     */
    private void releaseConnection() {
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (Exception e) {
                VRLogger.warning("コネクションのリリースに失敗:" + e.toString());
            } finally {
                con = null;
            }
        }
    }

    /**
     * プールしているコネクションを開放します。
     * 
     * @throws SQLException
     */
    public void releaseAll() throws SQLException {
        if (fbwds != null) {
            fbwds.shutdown();
            fbwds = null;
        }
    }

    /**
     * トランザクションを開始する。
     * 
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        con = getConnection();
        con.setAutoCommit(false);
    }

    /**
     * トランザクションをコミットする。
     * 
     * @throws SQLException
     */
    public void commitTransaction() throws SQLException {
        if (con != null) {
            con.commit();
        }
        releaseConnection();
    }

    /**
     * トランザクションをロールバックする。
     * 
     * @throws SQLException
     */
    public void rollbackTransaction() throws SQLException {
        if (con != null) {
            con.rollback();
        }
        releaseConnection();
    }

    /**
     * オブジェクト破棄時、コネクションオブジェクトも同時に破棄する。
     */
    public void finalize() {
        releaseConnection();
    }

    public boolean canConnect() throws Exception {
        return isAvailableDBConnection();
    }

}
