/*
 * Project code name "ORCA"
 * �厡��ӌ����쐬�\�t�g ITACHI�iJMA IKENSYO software�j
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
 * �A�v��: ITACHI
 * �J����: �����L
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �����L �V�K�쐬
 * �X�V��: ----/--/--
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

    /** �I�u�W�F�N�g���Ŏg�p����R�l�N�V���� */
    private Connection con = null;

    /**
     * �R���X�g���N�^ �ݒ�t�@�C������A<BR>
     * DBConfig/Server - DB�T�[�o��<BR>
     * DBConfig/Port - �ڑ��|�[�g<BR>
     * DBConfig/UserName - ���[�U��<BR>
     * DBConfig/Password - �p�X���[�h<BR>
     * DBConfig/Path - DB�p�X(�t�@�C���p�X)<BR>
     * ���擾���AFirebirdDBManager �̃C���X�^���X���쐬����B
     * 
     * @throws Exception DB�ݒ莸�s
     */
    public IkenshoFirebirdDBManager() throws Exception {
        if (fbDBManager != null) {
            return;
        }
        createDBManager();
    }

    /**
     * �R���X�g���N�^
     * 
     * @param server DB�T�[�o��
     * @param port �ڑ��|�[�g
     * @param userName ���[�U��
     * @param pass �p�X���[�h
     * @param path DB�p�X(�t�@�C���p�X)
     * @param loginTimeOut ���O�C���^�C���A�E�g
     * @param maxPoolSize �ő�v�[���T�C�Y
     * @throws Exception ���s��O
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
     * FireBird DBManager �̍쐬���s���܂��B
     * 
     * @throws Exception ���s����O
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
     * FireBird DBManager �̍쐬���s���܂��B
     * 
     * @param server DB�T�[�o��
     * @param port �ڑ��|�[�g
     * @param userName ���[�U��
     * @param pass �p�X���[�h
     * @param path DB�p�X(�t�@�C���p�X)
     * @param loginTimeOut ���O�C���^�C���A�E�g
     * @param maxPoolSize �ő�v�[���T�C�Y
     * @throws Exception ���s��O
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
     * �R�l�N�V�������擾����B
     * 
     * @return Connection FireBird Connection
     * @throws SQLException Connection �擾����O
     */
    public Connection getConnection() throws SQLException {
        return getConnectionPool();
    }

    /**
     * �v�[������R�l�N�V�������擾���܂��B
     * 
     * @return Connection FireBird Connection
     * @throws SQLException PooledConnection �擾����O
     */
    private Connection getConnectionPool() throws SQLException {
        // �v�[������̃R�l�N�V�����擾�����s
        try {
            return fbDBManager.getConnection();
        } catch (Exception e) {
            // �v�[������̎擾�Ɏ��s�����ꍇ
            // try{
            // //��������v�[�����J��
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
     * UPDATE ���̎��s���s���B
     * 
     * @param sql String ���s���� SQL ��
     * @return int ���s�ɂ���ĉe�����󂯂���
     * @throws SQLException UPDATE ���s����O
     */
    public int executeUpdate(String sql) throws SQLException {
        int r = 0;
        boolean blnTran = true;
        Statement stmt = null;

        try {
            // �R�l�N�V�����擾
            if (con == null) {
                setConnection();
                blnTran = false;
            }

            // �X�e�[�g�����g����
            stmt = con.createStatement();
            // SQL�����s
            r = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            VRLogger.warning("executeUpdate���s:\nSQL:" + sql + "\n"
                    + e.toString());
            throw e;
        } finally {
            stmt.close();
            // �R�l�N�V�����j��
            if (!blnTran)
                releaseConnection();
        }
        return r;
    }

    /**
     * BLOB�^�f�[�^�� UPDATE ���̎��s���s���B
     * 
     * @param sql String ���s���� SQL ��(�p�����[�V�����N�G��)
     * @param is BLOB��ɒǉ�����InputStream�I�u�W�F�N�g
     * @param length �ǉ�����I�u�W�F�N�g�̒���
     * @return int ���s�ɂ���ĉe�����󂯂���
     * @throws SQLException UPDATE ���s����O
     */
    public int executeUpdateBLOB(String sql, InputStream is, int length)
            throws SQLException {
        int r = 0;
        boolean blnTran = true;
        PreparedStatement pstmt = null;

        try {
            // �R�l�N�V�����擾
            if (con == null) {
                setConnection();
                blnTran = false;
            }
            // �X�e�[�g�����g����
            pstmt = con.prepareStatement(sql);
            pstmt.clearParameters();
            // BLOB�I�u�W�F�N�g�̐ݒ�
            pstmt.setBinaryStream(1, is, length);
            // SQL�����s
            r = pstmt.executeUpdate();

        }
        // SQL�����s�G���[
        catch (SQLException e) {
            VRLogger.warning("executeUpdateBLOB���s:\nSQL:" + sql + "\n"
                    + e.toString());
            throw e;
        }

        finally {
            pstmt.close();
            // �R�l�N�V�����j��
            if (!blnTran)
                releaseConnection();
        }
        return r;
    }

    /**
     * SELECT ���̎��s���s���B
     * 
     * @param sql String ���s���� SELECT ��
     * @return ���s����
     * @throws SQLException SELECT �����s����O
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
            VRLogger.warning("executeQuery���s:\nSQL:" + sql + "\n"
                    + e.toString());
            throw e;
        } catch (NullPointerException e) {
            ACMessageBox
                    .showExclamation("�f�[�^�x�[�X�t�@�C����������܂���B\n�f�[�^�x�[�X�t�@�C�����w�肵�����Ă��������B");
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
                    .warning("�R�l�N�V�����e�X�g(isAvailableDBConnection)���s:\nSQL:SELECT CURRENT_TIMESTAMP FROM RDB$DataBase\n"
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
     * DB�̃o�[�W�������̂��擾����B
     * @return �擾�����o�[�W��������
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
                    .warning("�f�[�^�x�[�X�o�[�W�����擾(getDBMSVersion)���s:\nDataBaseMetaData�擾�G���[\n"
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
     * �R�l�N�V�����̐������s���B
     * 
     * @throws SQLException
     */
    private void setConnection() throws SQLException {
        if (con == null) {
            con = getConnection();
        }
    }

    /**
     * �R�l�N�V���������B
     */
    private void releaseConnection() {
        if (con != null) {
            try {
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (Exception e) {
                VRLogger.warning("�R�l�N�V�����̃����[�X�Ɏ��s:" + e.toString());
            } finally {
                con = null;
            }
        }
    }

    /**
     * �v�[�����Ă���R�l�N�V�������J�����܂��B
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
     * �g�����U�N�V�������J�n����B
     * 
     * @throws SQLException
     */
    public void beginTransaction() throws SQLException {
        con = getConnection();
        con.setAutoCommit(false);
    }

    /**
     * �g�����U�N�V�������R�~�b�g����B
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
     * �g�����U�N�V���������[���o�b�N����B
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
     * �I�u�W�F�N�g�j�����A�R�l�N�V�����I�u�W�F�N�g�������ɔj������B
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
         * �R���X�g���N�^�ł��B
         * 
         * @param server String �ڑ��T�[�o��
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
            //add Mac�@��ˑ������o�^���̕��������͉�������邪�A
            //�����f�[�^�������������Ă��܂����߁A��U�R�����g
            //dataSource.setNonStandardProperty("isc_dpb_lc_ctype","UNICODE_FSS");
            //add
        }

        /**
         * ���O�C���̃^�C���A�E�g����(s)��ݒ肵�܂��B
         * 
         * @param timeOut int ���O�C���^�C���A�E�g����
         */
        public void setLoginTimeOut(int timeOut) {
            dataSource.setLoginTimeout(timeOut);
        }

        /**
         * �v�[������R�l�N�V�����̍ŏ������w�肵�܂��B
         * 
         * @param size int �R�l�N�V�����̍ŏ���
         */
        public void setMinPoolSize(int size) {
            dataSource.setMinPoolSize(size);
        }

        /**
         * �v�[������R�l�N�V�����̍ő吔���w�肵�܂��B
         * 
         * @param size int �R�l�N�V�����̍ő吔
         */
        public void setMaxPoolSize(int size) {
            dataSource.setMaxPoolSize(size);
        }

        /**
         * �R�l�N�V�������v�[������擾���܂��B
         * 
         * @return Connection �擾�����R�l�N�V����
         * @throws SQLException ���s���G���[
         */
        public Connection getConnection() throws SQLException {
            return dataSource.getPooledConnection().getConnection();
        }

        /**
         * �R�l�N�V�����v�[���̔j�����s���܂��B
         * 
         * @throws SQLException ���s���G���[
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

            // �R�l�N�V�����擾
            Connection c = getConnection();

            // �X�e�[�g�����g����
            Statement s = c.createStatement();

            r = s.executeUpdate(sql);

            // �X�e�[�g�����g�j��
            s.close();
            // �R�l�N�V�����j��
            c.close();

            return r;
        }

        /**
         * ���[�U����Ԃ��܂��B
         * 
         * @return ���[�U��
         */
        public String getUserName() {
            return userName;
        }

        /**
         * �p�X���[�h��Ԃ��܂��B
         * 
         * @return �p�X���[�h
         */
        public String getPassword() {
            return password;
        }

        /**
         * �f�[�^�x�[�XPath��Ԃ��܂��B
         * 
         * @return �f�[�^�x�[�XPath
         */
        public String getPath() {
            return path;
        }

        /**
         * �|�[�g�ԍ���Ԃ��܂��B
         * 
         * @return �|�[�g�ԍ�
         */
        public int getPort() {
            return port;
        }

        /**
         * �T�[�o����Ԃ��܂��B
         * 
         * @return �T�[�o��
         */
        public String getServer() {
            return server;
        }
    }
}
