/*
 * Project code name "ORCA"
 * 主治医意見書作成ソフト ITACHI（JMA IKENSYO software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: ITACHI
 * 開発者: 藤原伸
 * 作成日: 2005/12/01  日本コンピュータ株式会社 藤原伸 新規作成
 * 更新日: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.sql;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.sql.ACResultSetForwardBindSource;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.logging.VRLogger;

import org.firebirdsql.pool.FBConnectionPoolDataSource;

public class IkenshoFirebirdDBManager implements ACDBManager {

    private static IkenshoFirebirdDBManagerImp fbDBManager = null;

    /** オブジェクト内で使用するコネクション */
    private Connection con = null;

    /**
     * コンストラクタ 設定ファイルから、<BR>
     * DBConfig/Server - DBサーバ名<BR>
     * DBConfig/Port - 接続ポート<BR>
     * DBConfig/UserName - ユーザ名<BR>
     * DBConfig/Password - パスワード<BR>
     * DBConfig/Path - DBパス(ファイルパス)<BR>
     * を取得し、FirebirdDBManager のインスタンスを作成する。
     * 
     * @throws Exception DB設定失敗
     */
    public IkenshoFirebirdDBManager() throws Exception {
        if (fbDBManager != null) {
            return;
        }
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
    public IkenshoFirebirdDBManager(String server, int port, String userName,
            String pass, String path, int loginTimeOut, int maxPoolSize)
            throws Exception {
        if (fbDBManager != null) {
            return;
        }
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize);
    }

    /**
     * FireBird DBManager の作成を行います。
     * 
     * @throws Exception 実行時例外
     */
    private void createDBManager() throws Exception {
        ACPropertyXML xml = ACFrame.getInstance().getProperityXML();
        try {
            xml.read();
            String server = xml.getValueAt("DBConfig/Server");
            int port = Integer.parseInt(xml.getValueAt("DBConfig/Port"));
            String userName = xml.getValueAt("DBConfig/UserName");
            String pass = xml.getValueAt("DBConfig/Password");
            String path = xml.getValueAt("DBConfig/Path");
            fbDBManager = new IkenshoFirebirdDBManagerImp(server, port, path,
                    userName, pass);

            fbDBManager.setLoginTimeOut(Integer.parseInt(xml
                    .getValueAt("DBConfig/LoginTimeOut")));
            fbDBManager.setMaxPoolSize(Integer.parseInt(xml
                    .getValueAt("DBConfig/MaxPoolSize")));
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
        try {
            fbDBManager = new IkenshoFirebirdDBManagerImp(server, port, path,
                    userName, pass);
            fbDBManager.setLoginTimeOut(loginTimeOut);
            fbDBManager.setMaxPoolSize(maxPoolSize);
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
            return fbDBManager.getConnection();
        } catch (Exception e) {
            // プールからの取得に失敗した場合
            // try{
            // //いったんプールを開放
            // try{
            // vrFbDBManager.releaseAll();
            // } catch(Exception ex){
            // VRLogger.warning(ex.toString());
            // } finally{
            // vrFbDBManager = null;
            // }
            // try {
            // createDBManager();
            // } catch (Exception exx) {
            // VRLogger.warning(exx.toString());
            // }
            //
            // return vrFbDBManager.getConnection();
            // } catch(SQLException ex){
            // throw ex;
            // }
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
            stmt.close();
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
            pstmt.close();
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
        VRBindSource tbl = null;
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
                    .executeQuery("SELECT CURRENT_TIMESTAMP FROM RDB$DataBase");
            tbl = ACResultSetForwardBindSource.forward(rs);
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
    
    //2006/02/08[Shin Fujihara] : add begin
    
    /**
     * DBのバージョン名称を取得する。
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
    //2006/02/08[Shin Fujihara] : add end

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
        if (fbDBManager != null) {
            fbDBManager.releaseAll();
            fbDBManager = null;
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

    /** TODO <HEAD> */
    public class IkenshoFirebirdDBManagerImp {

        private int port;

        private String path;

        private String server;

        private String userName;

        private String password;

        private FBConnectionPoolDataSource dataSource;

        /**
         * コンストラクタです。
         * 
         * @param server String 接続サーバ名
         * @param port int
         * @param path String
         * @param userName String
         * @param password String
         */
        public IkenshoFirebirdDBManagerImp(String server, int port,
                String path, String userName, String password) {
            this.server = server;
            this.port = port;
            this.path = path;
            this.userName = userName;
            this.password = password;
            dataSource = new FBConnectionPoolDataSource();
            dataSource.setDatabase(server + "/" + port + ":" + path);
            dataSource.setUserName(userName);
            dataSource.setPassword(password);
            //add Mac機種依存文字登録時の文字化けは解消されるが、
            //既存データが文字化けしてしまうため、一旦コメント
            //dataSource.setNonStandardProperty("isc_dpb_lc_ctype","UNICODE_FSS");
            //add
        }

        /**
         * ログインのタイムアウト時間(s)を設定します。
         * 
         * @param timeOut int ログインタイムアウト時間
         */
        public void setLoginTimeOut(int timeOut) {
            dataSource.setLoginTimeout(timeOut);
        }

        /**
         * プールするコネクションの最少数を指定します。
         * 
         * @param size int コネクションの最少数
         */
        public void setMinPoolSize(int size) {
            dataSource.setMinPoolSize(size);
        }

        /**
         * プールするコネクションの最大数を指定します。
         * 
         * @param size int コネクションの最大数
         */
        public void setMaxPoolSize(int size) {
            dataSource.setMaxPoolSize(size);
        }

        /**
         * コネクションをプールから取得します。
         * 
         * @return Connection 取得したコネクション
         * @throws SQLException 実行時エラー
         */
        public Connection getConnection() throws SQLException {
            return dataSource.getPooledConnection().getConnection();
        }

        /**
         * コネクションプールの破棄を行います。
         * 
         * @throws SQLException 実行時エラー
         */
        public void releaseAll() throws SQLException {
            try {
                dataSource.shutdown();
            } finally {
                dataSource = null;
            }
        }

        public int executeUpdate(String sql) throws SQLException {
            int r = 0;

            // コネクション取得
            Connection c = getConnection();

            // ステートメント生成
            Statement s = c.createStatement();

            r = s.executeUpdate(sql);

            // ステートメント破棄
            s.close();
            // コネクション破棄
            c.close();

            return r;
        }

        /**
         * ユーザ名を返します。
         * 
         * @return ユーザ名
         */
        public String getUserName() {
            return userName;
        }

        /**
         * パスワードを返します。
         * 
         * @return パスワード
         */
        public String getPassword() {
            return password;
        }

        /**
         * データベースPathを返します。
         * 
         * @return データベースPath
         */
        public String getPath() {
            return path;
        }

        /**
         * ポート番号を返します。
         * 
         * @return ポート番号
         */
        public int getPort() {
            return port;
        }

        /**
         * サーバ名を返します。
         * 
         * @return サーバ名
         */
        public String getServer() {
            return server;
        }
    }
}
