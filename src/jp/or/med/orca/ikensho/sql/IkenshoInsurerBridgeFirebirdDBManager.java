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
 * �A�v��: Ikensho
 * �J����: ����@��F
 * �쐬��: 2009  ���{�R���s���[�^�[������� ��� ��F �V�K�쐬
 * @since V3.0.9
 *
 *****************************************************************
 */
public class IkenshoInsurerBridgeFirebirdDBManager implements ACDBManager {

    private static FBWrappingDataSource fbwds = null;
    
    private static String masterPropertyPath = "masterProperty.xml";

    /** �I�u�W�F�N�g���Ŏg�p����R�l�N�V���� */
    private Connection con = null;

    /**
     * DB�̃o�[�W�������̂��擾����B
     * 
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

    /**
     * �R���X�g���N�^ �ݒ�t�@�C������A<BR>
     * DBConfig/Server - DB�T�[�o��<BR>
     * DBConfig/Port - �ڑ��|�[�g<BR>
     * DBConfig/UserName - ���[�U��<BR>
     * DBConfig/Password - �p�X���[�h<BR>
     * DBConfig/Path - DB�p�X(�t�@�C���p�X)<BR>
     * ���擾���AVRFirebirdDBManager �̃C���X�^���X���쐬����B
     * 
     * @throws Exception DB�ݒ莸�s
     */
    public IkenshoInsurerBridgeFirebirdDBManager() throws Exception {
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
    public IkenshoInsurerBridgeFirebirdDBManager(String server, int port,
            String userName, String pass, String path, int loginTimeOut,
            int maxPoolSize) throws Exception {
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize);
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
     * @param charSet �����R�[�h
     * @throws Exception ���s��O
     */
    public IkenshoInsurerBridgeFirebirdDBManager(String server, int port,
            String userName, String pass, String path, int loginTimeOut,
            int maxPoolSize, String charSet) throws Exception {
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize, charSet);
    }

    /**
     * FireBird DBManager �̍쐬���s���܂��B
     * 
     * @throws Exception ���s����O
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
            // ���l
            lengthPoint = path.lastIndexOf("\\");
            if(lengthPoint == -1) {
                lengthPoint = path.lastIndexOf("/");
            }
            String rootDataPath = "";
            if(path.length() > lengthPoint + 1) {
                // DB���܂ł��擾
                rootDataPath = path.substring(0,lengthPoint+1);
            }
            // �ڑ�����`����B
            String insurerPath = "";
            ACPropertyXML masterPropertyXML = new ACPropertyXML(masterPropertyPath);
            if (new File(masterPropertyPath).exists()) {
                // property�t�@�C�����ڑ���f�[�^�x�[�X�����`����B
                masterPropertyXML.read();
                String insurerPropPath = "";
                // �菑���ŕύX���s�����ꍇ�͐ڑ����ύX�ł���d�l
                if(masterPropertyXML.hasValueAt("Config/InsurerDBPath")) {
                    insurerPropPath = masterPropertyXML.getValueAt("Config/InsurerDBPath");
                }
                String dbName = masterPropertyXML.getValueAt("Config/InsurerDBName");
                // �ڑ���̒�`
                if(insurerPropPath == null || "".equals(insurerPropPath) || "default".equals(insurerPropPath)){
                    // ���Ɏw��ύX���Ȃ��ꍇ�i�ʏ�j
                    insurerPath = rootDataPath + dbName;
                } else {
                    // ���[�U�[���Ǝ��������݂��Ă���P�[�X�i�菑���Ή��j
                    insurerPath = insurerPropPath + dbName;
                }
            } else {
                insurerPath = "";
            }
            // �g�p��͏�����
            masterPropertyXML=null;
            
            int loginTimeout = Integer.parseInt(xml
                    .getValueAt("DBConfig/LoginTimeOut"));
            int maxPoolSize = Integer.parseInt(xml
                    .getValueAt("DBConfig/MaxPoolSize"));

            String charSet = null;
            if (xml.hasValueAt("DBConfig/CharSet")) {
                charSet = xml.getValueAt("DBConfig/CharSet");
            }
            // �}�X�^�[�f�[�^�x�[�X�̏ꍇ�����ݒ�
            if(charSet == null || "".equals(charSet)) {
                charSet = "SJIS_0208";
            }
            
            // DB�R�l�N�V����
            createDBManager(server, port, userName, pass, insurerPath, loginTimeout,
                    maxPoolSize, charSet);
            
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
        createDBManager(server, port, userName, pass, path, loginTimeOut,
                maxPoolSize, null);
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
     * @param charSet �����R�[�h
     * @throws Exception ���s��O
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
            if (stmt != null) {
                stmt.close();
            }
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
            if (pstmt != null) {
                pstmt.close();
            }
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
        if (fbwds != null) {
            fbwds.shutdown();
            fbwds = null;
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

    public boolean canConnect() throws Exception {
        return isAvailableDBConnection();
    }

}
