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
 * �J����: �c������
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �c������ �V�K�쐬
 * �X�V��: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.ac.util.splash.ACStopButtonSplash;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoReceiptSoftDBManager {
    // --------------------------------------------------------------------------
    // �萔�l��`
    // --------------------------------------------------------------------------
    // �f�t�H���g�|�[�g�ԍ��i2003/08/14���݁B�j
    public static final int DEFAULT_PORT = 8013;

    // �f�t�H���g�T�[�o�A�h���X�ilocalhost�j
    public static final String DEFAULT_HOST = "127.0.0.1";

    // �f�t�H���gDB�o�[�W����
    public static final String DEFAULT_DBS_VERSION = "1.2.2";

    // �G���R�[�h���s�Ȃ��ۂ̃f�t�H���g�����R�[�h
    public static final String DEFAULT_ENCODE = "EUCJIS";

    // �f�R�[�h���s�Ȃ��ۂ̃f�t�H���g�����R�[�h
    public static final String DEFAULT_DECODE = "EUCJIS";

    // �Z�b�V�����J�ݎ��ɑ��M����Œ蕶����iver.1.2.0�ȍ~�j
    public static final String SESSION_OPENING_CONSTANT = "stringe";

    // �Z�b�V�����J�ݐ������̌Œ蕶����
    public static final String SESSION_OPENING_SUCCESS = "Connect: OK";

    // ��Ԓʒm�R�[�h�iOK�j
    public static final int STATUS_CODE_OK = 0;

    // ��Ԓʒm�R�[�h�iEOF�j
    public static final int STATUS_CODE_EOF = 1;

    // ��Ԓʒm�R�[�h�iNONFATAL�j
    public static final int STATUS_CODE_NONFATAL = 2;

    // ��Ԓʒm�R�[�h�i�����s���j
    public static final int STATUS_CODE_ARGUMENT_ERROR = -1;

    // ��Ԓʒm�R�[�h�i�������s���j
    public static final int STATUS_CODE_FUNCTION_ERROR = -2;

    // ��Ԓʒm�R�[�h�iSQL�s���j
    public static final int STATUS_CODE_SQL_ERROR = -3;

    // ��Ԓʒm�R�[�h�i���̑��s���j
    public static final int STATUS_CODE_OTHER_ERROR = -4;

    // �R�}���h�i�f�[�^�x�[�X�����̊J�݁j
    public static final String COMMAND_OPEN = "DBOPEN";

    // �R�}���h�i�g�����U�N�V�����̊J�n�j
    public static final String COMMAND_BEGINTRANS = "DBSTART";

    // �R�}���h�i�g�����U�N�V�����I���j
    public static final String COMMAND_ENDTRANS = "DBEND";

    // �R�}���h�i�g�����U�N�V����Commit�j
    public static final String COMMAND_COMMIT = "DBCOMMIT";

    // �R�}���h�i�Z�b�V�����̔j���j
    public static final String COMMAND_DBDISCONNECT = "DBDISCONNECT";

    // �R�}���h���s
    public static final String COMMAND_EXEC = "Exec:";

    // �����̏I��
    public static final String COMMAND_END = "End";

    // �R�}���h���s����
    public static final String COMMAND_RECEIVE = "Exec: ";

    // �W����`�R�}���h�iDBINSERT�j
    public static final String COMMAND_DBSELECT = "DBSELECT";

    // �W����`�R�}���h�iDBINSERT�j
    public static final String COMMAND_DBINSERT = "DBINSERT";

    // �W����`�R�}���h�iDBUPDATE�j
    public static final String COMMAND_DBUPDATE = "DBUPDATE";

    // �W����`�R�}���h�iDBDELETE�j
    public static final String COMMAND_DBDELETE = "DBDELETE";

    // �W����`�R�}���h�iDBFETCH�j
    public static final String COMMAND_DBFETCH = "DBFETCH";

    // �G���[�i�F�؃G���[�j
    public static final String ERROR_AUTHENTICATION = "authentication";

    // �G���[�i�T�[�o�A�N���C�A���g�̃o�[�W�����s�����j
    public static final String ERROR_VERSION = "version";

    // �G���[�Œ蕶����
    public static final String ERROR_CONSTANT = "Error: ";

    // ���s
    public static final String LINE_CR = "\n";

    // �R�}���h�I��
    public static final String END_OF_COMMAND = "\n";

    // �f�[�^�I��
    public static final String END_OF_DATA = "";

    // NULL�R�[�h(0x01)
    public static final String NULL_CODE = "%01";

    // --------------------------------------------------------------------------
    // �����o�ϐ��̒�`
    // --------------------------------------------------------------------------
    // DBS�|�[�g�ԍ�
    private int port = DEFAULT_PORT;

    // DBS�T�[�o�[�A�h���X
    private String host = DEFAULT_HOST;

    // DBS�o�[�W����
    private String dbsVersion = DEFAULT_DBS_VERSION;

    // ���o�̓\�P�b�g
    private Socket dbsSocket = null;

    // DBS�ւ̃f�[�^���M�X�g���[��
    private PrintWriter dbsOut = null;

    // DBS����̃f�[�^��M�X�g���[��
    private BufferedReader dbsIn = null;

    // dbs�F�؃��[�U
    private String userName = null;

    // dbs�F�؃p�X���[�h
    private String password = null;

    // �Ō�Ɏ󂯂Ƃ�����Ԓʒm�R�[�h
    private int lastStatusCode;

    // �f�R�[�h������
    private String dec = DEFAULT_ENCODE;

    // �G���R�[�h������
    private String enc = DEFAULT_DECODE;

    // �\�P�b�g�A�h���X
    private InetSocketAddress socketAddress;

    // �t�b�N���o�^�ς��H
    private static boolean hooked = false;

    // DBDISCONNECT���Ăяo���ꂽ���H
    private boolean dbDisconnected = false;

    // close()���\�b�h���Ăяo���ꂽ���H
    private boolean closed = false;

    // �ڑ��ς݂ł��邩
    private boolean connected = false;

    /**
     * dbs�T�[�o�[�ɐڑ�����R���X�g���N�^�ł��B
     * 
     * @throws Exception �T�[�o�[�ɐڑ��ł��Ȃ��ꍇ
     */
    public IkenshoReceiptSoftDBManager() throws Exception {
        this(DEFAULT_HOST, DEFAULT_PORT, "", "", DEFAULT_DBS_VERSION);
    }

    /**
     * dbs�T�[�o�[�ɐڑ�����R���X�g���N�^�ł��B
     * 
     * @param userName �F�؃��[�U�[��
     * @param password �F�؃p�X���[�h
     * @param dbsVersion �F�ؗp��DBS�o�[�W����
     * @throws Exception �T�[�o�[�ɐڑ��ł��Ȃ��ꍇ
     */
    public IkenshoReceiptSoftDBManager(String userName, String password,
            String dbsVersion) throws Exception {
        this(DEFAULT_HOST, DEFAULT_PORT, userName, password, dbsVersion);
    }

    /**
     * dbs�T�[�o�[�ɐڑ�����R���X�g���N�^�ł��B
     * 
     * @param host dbs���N�����Ă���T�[�o�[�A�h���X
     * @param port dbs�̎g�p�|�[�g
     * @throws Exception �T�[�o�[�ɐڑ��ł��Ȃ��ꍇ
     */
    public IkenshoReceiptSoftDBManager(String host, int port) throws Exception {
        this(host, port, "", "", DEFAULT_DBS_VERSION);
    }

    /**
     * �T�[�o�[�A�h���X�ƃ|�[�g�Ŏw�肳�ꂽdbs�T�[�o�[�ɐڑ�����R���X�g���N�^�ł��B
     * 
     * @param host dbs���N�����Ă���T�[�o�[�A�h���X
     * @param port dbs�̎g�p�|�[�g
     * @param userName �F�؃��[�U�[��
     * @param password �F�؃p�X���[�h
     * @param dbsVersion �F�ؗp��DBS�o�[�W����
     * @throws Exception �T�[�o�[�ɐڑ��ł��Ȃ��ꍇ
     */
    public IkenshoReceiptSoftDBManager(String host, int port, String userName,
            String password, String dbsVersion) throws Exception {
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.dbsVersion = dbsVersion;
        this.socketAddress = null;
        this.dbDisconnected = false;

        if (!hooked) {
            synchronized (this) {
                // ------------------------------------------------------------
                // �I�����̏����B
                // �K��close()���Ăяo�����悤�ɂ���B
                // ��close()���Ăяo����Ȃ������ꍇ�A�T�[�o����dbs�̃v���Z�X��
                // �c��\��������B
                // ------------------------------------------------------------
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    public void run() {
                        close();
                    }
                });
                hooked = true;
            }
        }
        // ------------------------------------------------------------
        // �J���O�Ƀ��\�[�X���N���[�Y���Ă����B
        // ------------------------------------------------------------
        try {
            closeConnection();
        } catch (Exception ignore) {
        }

        try {
            socketAddress = new InetSocketAddress(host, port);
            // ------------------------------------------------------------
            // ��O�����������烊�\�[�X��j������B
            // ------------------------------------------------------------
        } catch (Exception e) {
            closeConnection();
            throw new Exception(e);
        }
    }

    /**
     * dbs�T�[�o�[�ɐڑ�����B
     * <P>
     * 
     * @throws Exception �T�[�o�ɐڑ��ł��Ȃ��ꍇ
     */
    protected void setConnection() throws Exception {
        // ------------------------------------------------------------
        // �J���O�Ƀ��\�[�X���N���[�Y���Ă����B
        // ------------------------------------------------------------
        try {
            // socketAddr = null;
            closeConnection();
        } catch (Exception ignore) {
        }

        try {
            dbsSocket = new Socket();
            // �^�C���A�E�g�l���Z�b�g�ł���悤�ɂ���
            dbsSocket.connect(socketAddress);

            // �蓮��Flash
            dbsOut = new PrintWriter(dbsSocket.getOutputStream(), false);

            dbsIn = new BufferedReader(new InputStreamReader(dbsSocket
                    .getInputStream()));

            // ------------------------------------------------------------
            // ��O�����������烊�\�[�X�����
            // ------------------------------------------------------------
        } catch (Exception e) {
            close();
            throw new Exception(e);
        }
        closed = false;
    }

    /**
     * dbs�ɑ΂��ĔF�؂��s���B
     * <P>
     * ���F�؂Ɏ��s�����ꍇ�Aclose���\�b�h���Ăяo����܂��B���ӂ��ĉ������B
     * <P>
     * 
     * @throws Exception �F�؃G���[�����������ꍇ
     * @throws IOException ���o�̓G���[�����������ꍇ
     */
    protected void authenticate() throws Exception, IOException {

        try {
            // �o�[�W�����ԍ� ���[�U�� �p�X���[�h stringe
            String cmd = dbsVersion + " " + userName + " " + password + " "
                    + SESSION_OPENING_CONSTANT + LINE_CR;

            sendMessage(cmd);
            String result = recvMessage();

            // ------------------------------------------------
            // �ڑ����s
            // ------------------------------------------------
            if (result == null) {
                throw new Exception("authentication result is null.");
            } else if (!result.equals(SESSION_OPENING_SUCCESS)) {
                // �o�[�W�����G���[
                if (result.equals(ERROR_CONSTANT + ERROR_VERSION)) {
                    throw new Exception("invalid version.");

                    // �F�؃G���[
                } else if (result.equals(ERROR_CONSTANT + ERROR_AUTHENTICATION)) {
                    throw new Exception("authentication error.");

                    // ���̑�
                } else {
                    throw new Exception("other error.");
                }
            }
            // ------------------------------------------------------------
            // ��O�����������烊�\�[�X�����
            // ------------------------------------------------------------
        } catch (Exception e) {
            close();
            throw e;
        }
    }

    /**
     * dbs��Ԓʒm�R�[�h�擾�B
     * <P>
     * dbs����̏�Ԓʒm�R�[�h���擾���܂��B<br>
     * <b>���̃��\�b�h���Ăяo�����Ƃ�dbs����̉������Ȃ��Ȃ�ꍇ������܂��B �ʐM�菇��DBS�̃h�L�������g�������������B</b>
     * <P>
     * 
     * @return ��Ԓʒm�R�[�h<br>
     *         0: OK<br>
     *         1: EOF<br>
     *         2: NONFATAL<br>
     *         3: �����s��<br>
     *         -2: �������s��<br>
     *         -3: SQL�s��<br>
     *         -4: ���̑��s���iORCA�`�[���ɂ��₢���킹���������B�j<br>
     *         ���̑��F �G���[���e�s���iORCA�`�[���ɂ��₢���킹���������B�j<br>
     *         <P>
     * @throws Exception ��Ԓʒm�R�[�h���擾���邱�Ƃ��ł��Ȃ������ꍇ
     * @throws IOException ���o�̓G���[�����������ꍇ
     */
    protected int getStatus() throws Exception, IOException {
        int status;

        try {
            String result = recvMessage();

            if (result == null) {
                throw new Exception("result is null.");
            }
            /** Exec: XXX�ƈ�v���邩? */
            int i = result.indexOf(COMMAND_RECEIVE);
            // printDebug("result=[" + result + "]");

            if (i >= 0) {
                status = Integer.parseInt(result.substring(COMMAND_RECEIVE
                        .length()
                        + i));
            } else {
                throw new Exception("puttern without agreeing.["
                        + COMMAND_RECEIVE + "]");
            }

            lastStatusCode = status;
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    /**
     * DB����ؒf����B
     * <P>
     * 
     * @return ��Ԓʒm�R�[�h
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ��DB�̃I�[�v���Ɏ��s�����ꍇ
     */
    protected int disconnect() throws IOException, Exception {
        this.dbDisconnected = false;
        int status;

        try {
            status = sendCommand(COMMAND_DBDISCONNECT);

            // ��ԃR�[�h��OK�ȊO�̏ꍇ
            if (status != STATUS_CODE_OK) {
                throw new Exception("DB disconnect error.[" + status + "]");
            }
        } catch (Exception e) {
            throw e;
        }
        this.dbDisconnected = true;
        connected = false;

        return status;
    }

    /**
     * DB���I�[�v������B
     * <P>
     * 
     * @return ��Ԓʒm�R�[�h
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ��DB�̃I�[�v���Ɏ��s�����ꍇ
     */
    protected int connect() throws IOException, Exception {
        if (isClosed()) {
            setConnection();
        }
        // ---------------------------------------------------
        // DBDISCONNECT���Ăяo����Ă��Ȃ���Ԃ�
        // 2��ȏ�DBOPEN���Ăяo�����Ƃ͂ł��Ȃ��B
        // ---------------------------------------------------
        if (dbDisconnected) {
            return STATUS_CODE_OK;
        }
        int status;

        try {
            // �F�؂��s�Ȃ�
            authenticate();

            status = sendCommand(COMMAND_OPEN);

            // ��ԃR�[�h��OK�ȊO�̏ꍇ
            if (status != STATUS_CODE_OK) {
                throw new Exception("DB open error.[" + status + "]");
            }
            connected = true;
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    /**
     * �g�����U�N�V�����J�n�B
     * <P>
     * 
     * @return ��Ԓʒm�R�[�h
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ�≽�炩�̗�O�����������ꍇ
     */
    public int beginTransaction() throws IOException, Exception {
        int status;

        try {
            status = sendCommand(COMMAND_BEGINTRANS);

            // ��ԃR�[�h��OK�ȊO�̏ꍇ
            if (status != STATUS_CODE_OK) {
                throw new Exception("begin transaction error.[" + status + "]");
            }
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    /**
     * �g�����U�N�V����COMMIT�B
     * <P>
     * 
     * @return ��Ԓʒm�R�[�h
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ�≽�炩�̗�O�����������ꍇ
     */
    public int commitTransaction() throws IOException, Exception {
        int status;

        try {
            status = sendCommand(COMMAND_COMMIT);

            if (status != STATUS_CODE_OK) {
                throw new Exception("commit transaction error.[" + status + "]");
            }
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    /**
     * �\�P�b�g�Ƀf�[�^�𑗂�
     * 
     * @param cmd ���M�f�[�^
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws SocketException �\�P�b�g�G���[�����������ꍇ
     */
    protected void sendMessage(String cmd) throws IOException, SocketException {
        dbsOut.print(cmd);
        dbsOut.flush();
    }

    /**
     * �R�}���h�̑��M���I������B
     * <P>
     * ���̃R�}���h������Ɏ󂯕t����ꂽ�ꍇ�A�T�[�o�[���Ő������ꂽdbs�̃v���Z�X��
     * �j������܂��B����ȍ~�ʐM�𑱂���ꍇ��open()���������蒼���ĉ������B<br>
     * ��close()���\�b�h���ŌĂяo����܂��B
     * <P>
     * 
     * @throws IOException �ڑ��G���[�����������ꍇ
     */
    protected void endCommand() throws IOException {
        sendMessage(COMMAND_END + LINE_CR);
        dbDisconnected = true;
    }

    /**
     * �R�}���h�̎��s���s�Ȃ��B
     * 
     * @param functionName ������
     * @param tableName �e�[�u����
     * @param pathName �p�X��
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws Exception ���炩�̗�O�����������ꍇ
     */
    protected void execCommand(String functionName, String tableName,
            String pathName) throws Exception, IOException {
        sendMessage(COMMAND_EXEC + " " + functionName + ":" + tableName + ":"
                + pathName + LINE_CR);
    }

    /**
     * �f�[�^�̎�M�I����ʒm����B
     * 
     * @throws IOException �ڑ��G���[�����������ꍇ
     * @throws Exception ���炩�̗�O�����������ꍇ
     */
    protected void recvDataEnd() throws Exception, IOException {
        sendMessage(END_OF_COMMAND);
    }

    /**
     * �\�P�b�g����f�[�^���擾����
     * 
     * @return ��M�f�[�^
     * @throws IOException ���̓G���[�����������ꍇ
     */
    protected String recvMessage() throws IOException {
        String msg = dbsIn.readLine();
        return msg;
    }

    /**
     * ������f�R�[�h�B
     * <P>
     * 
     * @param str �f�R�[�h���s��������
     * @return �f�R�[�h����
     */
    protected String urlDecode(String str) {
        if (str == null || str.equals(NULL_CODE))
            return null;
        String ret = str;

        try {
            ret = new String(URLDecoder.decode(str, dec));
        } catch (java.io.UnsupportedEncodingException e) {
        }
        return ret;
    }

    /**
     * ������G���R�[�h�B
     * <P>
     * 
     * @param str �G���R�[�h���s��������
     * @return �G���R�[�h����
     */
    protected String urlEncode(String str) {
        if (str == null)
            return NULL_CODE;
        String ret = str;

        try {
            ret = URLEncoder.encode(str, enc);
        } catch (java.io.UnsupportedEncodingException e) {
        }
        return ret;
    }

    /**
     * dbs�ւ̐ڑ����N���[�Y����B
     */
    public void close() {
        try {
            disconnect();
        } catch (Exception ignore) {
        }
        try {
            endCommand();
        } catch (Exception ignore) {
        }

        closeConnection();
    }

    /**
     * ���o�̓X�g���[�������B
     */
    protected void closeConnection() {
        // �o�̓X�g���[���N���[�Y
        if (dbsOut != null) {
            try {
                dbsOut.close();
                dbsOut = null;
            } catch (Exception ignore) {
            }
        }
        // ���̓X�g���[���N���[�Y
        if (dbsIn != null) {
            try {
                dbsIn.close();
                dbsIn = null;
            } catch (Exception ignore) {
            }
        }
        // �\�P�b�g�N���[�Y
        if (dbsSocket != null) {
            try {
                dbsSocket.close();
                dbsSocket = null;
            } catch (Exception ignore) {
            }
        }
        closed = true;
    }

    /**
     * dbs�g�p��`�̂Œ�`���ꂽ�t�@���N�V�����ɓn���p�����[�^�𑗐M����B
     * <P>
     * 
     * @param rec ���M����f�[�^�Z�b�g�i�L�[=�t�B�[���h���A�l=���M�l�j
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception ��L�ȊO�̗�O�����������ꍇ
     * @deprecated ���M����l��null���w��ł��܂���BexecData(HashMap rec)���g�p����悤�ɂ��ĉ������B
     */
    protected void execData(Hashtable rec) throws IOException, Exception {
        try {
            if (rec != null) {
                for (Enumeration e = rec.keys(); e.hasMoreElements();) {
                    String key = (String) e.nextElement();
                    String value = (rec.get(key)).toString();

                    String cmd = key + ": " + urlEncode(value) + LINE_CR;
                    sendMessage(cmd);
                }
            }
            sendMessage(LINE_CR);

        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * dbs�g�p��`�̂Œ�`���ꂽ�t�@���N�V�����ɓn���p�����[�^�𑗐M����B
     * <P>
     * 
     * @param fieldName �t�B�[���h��
     * @param value ���M����l
     *            <P>
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception ��L�ȊO�̗�O�����������ꍇ
     */
    protected void execData(String fieldName, String value) throws IOException,
            Exception {
        try {
            String cmd = fieldName + ": " + urlEncode(value) + LINE_CR;
            sendMessage(LINE_CR);

        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * dbs�C���^�[�t�F�[�X�W�����������s����B
     * <P>
     * 
     * @param func �W��������
     * @return ��Ԓʒm�R�[�h
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ���L�ȊO�̗�O�����������ꍇ
     */
    protected int sendCommand(String func) throws IOException, Exception {
        int status;
        int stage = 0;

        try {
            stage = 1;
            sendMessage(COMMAND_EXEC + " " + func + LINE_CR);
            stage = 2;
            // �R�}���h���M�I��
            sendMessage(END_OF_COMMAND);

            stage = 3;
            status = getStatus();

            stage = 4;
            sendMessage(END_OF_COMMAND);

            stage = 5;

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                switch (stage) {
                case 1:
                    sendMessage(END_OF_COMMAND);
                case 2:
                    break;
                case 3:
                    sendMessage(END_OF_COMMAND);
                case 4:
                    break;
                case 5:
                    break;
                }
            } catch (Exception ignore) {
            }
        }
        return status;
    }

    /**
     * �f�[�^��M�B
     * <P>
     * �w�肳�ꂽ���ڂɉ��ʍ��ڂ��������ꍇ�A���ʍ��ڑS�Ă���M���܂��B ��M�����l�̓A�v���P�[�V�������ŃL���X�g���ĉ������B
     * <P>
     * ���߂�l��Hashtable����HashMap�ɕύX�ɂȂ�܂����B
     * <P>
     * 
     * @param name �f�[�^���擾���鍀�ږ�
     * @return ���O�ƒl�̃y�A(�^���͊܂܂�Ȃ�)
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception ���炩�̗�O�����������ꍇ
     */
    protected HashMap getValues(String name) throws IOException, Exception {
        try {
            return getValues(new String[] { name });
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * �f�[�^��M�B
     * <P>
     * �w�肳�ꂽ���ڑS�Ă���M���܂��B ��M�����l�̓A�v���P�[�V�������ŃL���X�g���ĉ������B
     * <P>
     * 
     * @param name �f�[�^���擾���鍀�ږ�
     * @return ���O�ƒl�̃y�A(�^���͊܂܂�Ȃ�)
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception ���炩�̗�O�����������ꍇ
     */
    protected HashMap getValues(String[] name) throws IOException, Exception {
        HashMap rec = new HashMap();
        String line;

        try {
            if (name != null) {
                for (int i = 0; i < name.length; i++) {
                    if (name[i] == null || name[i].length() == 0)
                        continue;

                    sendMessage(name[i] + LINE_CR);

                    while ((line = dbsIn.readLine()) != null) {

                        if (line.equals(END_OF_DATA))
                            break;

                        StringTokenizer st = new StringTokenizer(line, ";",
                                false);

                        String fieldName = ((String) st.nextElement());
                        // String valueField = decode((String)
                        // st.nextElement());
                        String valueField = (String) st.nextElement();

                        int pos1 = valueField.indexOf(": ");

                        if (pos1 >= 0) {
                            String dbstype = valueField.substring(0, pos1)
                                    .trim().toUpperCase();
                            String value = urlDecode(valueField
                                    .substring(pos1 + 2));
                            if (value == null) {
                                rec.put(fieldName, null);
                            } else if (dbstype.indexOf("NUMBER") >= 0) {
                                rec.put(fieldName, Long.valueOf(value));
                            } else if (dbstype.indexOf("INTEGER") >= 0) {
                                rec.put(fieldName, Integer.valueOf(value));
                            } else if ((dbstype.indexOf("DATE") >= 0)
                                    || ((dbstype.indexOf("TIMESTAMP") >= 0))) {
                                rec.put(fieldName, VRDateParser.parse(value));
                            } else {
                                rec.put(fieldName, value);
                            }
                        } else {
                            rec.put(fieldName, urlDecode(valueField));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw new Exception(e);
        }
        return rec;
    }

    /**
     * �w�肳�ꂽ�t�@���N�V���������s���A�e�[�u�����Ŏw�肳�ꂽ���ڂ��擾����B
     * <P>
     * ���߂�l��Hashtable����HashMap�ɕύX����܂����B
     * <P>
     * �ł��邾��public HashMap recordops(String function, String table, String path,
     * HashMap sendData) ���g�p����悤�ɂ��ĉ������B
     * <P>
     * 
     * @param function ���s����t�@���N�V������
     * @param table �e�[�u����
     * @param path �p�X��
     * @param sendData ���M�f�[�^
     * @return ��M�f�[�^
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ�≽�炩�̗�O�����������ꍇ
     */
    protected HashMap execute(String function, String table, String path,
            Map sendData) throws IOException, Exception {
        HashMap retValeus = new HashMap();

        try {
            String cmd = COMMAND_EXEC + " " + function + ":" + table + ":"
                    + path + LINE_CR;

            sendMessage(cmd);

            if ((sendData != null) && (sendData.size() > 0)) {
                execData(sendData);
            } else {
                execData((HashMap) null);
            }

            int rc = getStatus();

            if (rc == STATUS_CODE_OK) {
                retValeus = getValues(table);
            } else if (rc == STATUS_CODE_EOF) {
            } else {
                String msg = null;
                if (rc == STATUS_CODE_NONFATAL) {
                    msg = "fatal error.";
                } else if (rc == STATUS_CODE_ARGUMENT_ERROR) {
                    msg = "illegal argument.";
                } else if (rc == STATUS_CODE_FUNCTION_ERROR) {
                    msg = "illegal function name.";
                } else if (rc == STATUS_CODE_SQL_ERROR) {
                    msg = "sql error.";
                } else {
                    msg = "other error.[" + rc + "]";
                }
                if (msg != null) {
                    throw new Exception(msg);
                }
            }
            // printDebug("rc=[" + rc + "]");

            // retValeus = getValues(table);
            sendMessage(LINE_CR);
        } catch (Exception e) {
            throw e;
        }
        return retValeus;
    }
    /**
     * �����R�[�h�ϊ����ʂ�Ԃ��܂��B
     * @param data �ϊ���
     * @param encode �ϊ������s���邩
     * @return �ϊ�����
     */
    private Object getORCADecodeString(Object data, boolean encode){
        if(data==null){
            return data;
        }
        String result = String.valueOf(data);
        if (encode) {
            try{
                result = new String(result.getBytes("Shift_JIS"),"MS932");
            } catch(Exception e){
                VRLogger.warning("�����R�[�h�̕ϊ��Ɏ��s���܂����B");
            }
        }
        return result;
    }

    //2006/02/09[Tozo Tanaka] : replace begin
//    /**
//     * SELECT ���𔭍s����B
//     * 
//     * @param table �e�[�u����
//     * @param whereCondition WHERE��}�b�v
//     * @return ���s����
//     * @throws Exception ���s����O
//     */
//    public VRArrayList executeQuery(String table, HashMap whereCondition)
//            throws Exception {
//        return executeQuery(table, "", whereCondition);
//    }
//
//    /**
//     * SELECT�X�g�A�h�v���V�[�W���𔭍s����B
//     * 
//     * @param procedure ���s����v���V�[�W����
//     * @return ���s����
//     * @throws Exception ���s����O
//     */
//    public VRArrayList executeQueryProcedure(String procedure) throws Exception {
//        return executeQuery("", procedure, null);
//    }
   
//    /**
//     * SELECT ���𔭍s����B
//     * 
//     * @param table �e�[�u����
//     * @param procedure ���s����v���V�[�W����
//     * @param whereCondition WHERE��}�b�v
//     * @param splash �X�v���b�V��
//     * @return ���s����
//     * @throws Exception ���s����O
//     */
//    public VRArrayList executeQuery(String table, String procedure,
//            HashMap whereCondition) throws Exception {
//        VRArrayList array = new VRArrayList();
//        if (!isConnected()) {
//            if (connect() != STATUS_CODE_OK) {
//                return array;
//            }
//        }
//
//        if (beginTransaction() != STATUS_CODE_OK) {
//            return array;
//        }
//
//        // �J�[�\����ݒ�
//        HashMap map = execute(COMMAND_DBSELECT, table, procedure,
//                whereCondition);
//
//
        //        // 1�s���擾
        //        while (true) {
        //            map = execute(COMMAND_DBFETCH, table, procedure, whereCondition);
        //            if ((map == null) || (map.size() <= 0)) {
        //                break;
        //            }
        //            array.add(toVRHashMap(map));
        //        }
//
//        
//        if (commitTransaction() != STATUS_CODE_OK) {
//            return array;
//        }
//
//        return array;
//    }
//  /**
//  * dbs�g�p��`�̂Œ�`���ꂽ�t�@���N�V�����ɓn���p�����[�^�𑗐M����B
//  * <P>
//  * (1)Hashtable�ł�null��\�����邱�Ƃ��ł��Ȃ�<br>
//  * (2)dbs��null���������Ƃ��ł���悤�ɂȂ���<br>
//  * ��L�̗��R�̂��ߐV������������܂����B
//  * <P>
//  * 
//  * @param rec ���M����f�[�^�Z�b�g�i�L�[=���R�[�h���A�l=���M����l�j
//  * @throws IOException ���o�̓G���[�����������ꍇ
//  * @throws Exception ��L�ȊO�̗�O�����������ꍇ
//  */
// protected void execData(final HashMap rec) throws IOException, Exception {
//     try {
//         if (rec != null) {
//             for (Iterator it = rec.keySet().iterator(); it.hasNext();) {
//                 String key = (String) it.next();
//                 Object obj = rec.get(key);
//                 if (obj == null) {
//                     String cmd = key + ": " + urlEncode(null) + LINE_CR;
//                     sendMessage(cmd);
//                 } else {
//                     String cmd = key + ": " + urlEncode(obj.toString())
//                             + LINE_CR;
//                     sendMessage(cmd);
//                 }
//             }
//         }
//         sendMessage(LINE_CR);
//
//     } catch (IOException e) {
//         throw e;
//     }
// }
/*
 * dbs�g�p��`�̂Œ�`���ꂽ�t�@���N�V�����ɓn���p�����[�^�𑗐M����B
 * <P>
 * (1)Hashtable�ł�null��\�����邱�Ƃ��ł��Ȃ�<br>
 * (2)dbs��null���������Ƃ��ł���悤�ɂȂ���<br>
 * ��L�̗��R�̂��ߐV������������܂����B
 * <P>
 * 
 * @param rec ���M����f�[�^�Z�b�g�i�L�[=���R�[�h���A�l=���M����l�j
 * @throws IOException ���o�̓G���[�����������ꍇ
 * @throws Exception ��L�ȊO�̗�O�����������ꍇ
 */
protected void execData(final Map rec) throws IOException, Exception {
    try {
        if (rec != null) {
            for (Iterator it = rec.keySet().iterator(); it.hasNext();) {
                String key = (String) it.next();
                Object obj = rec.get(key);
                if (obj == null) {
                    String cmd = key + ": " + urlEncode(null) + LINE_CR;
                    sendMessage(cmd);
                } else {
                    String cmd = key + ": " + urlEncode(obj.toString())
                            + LINE_CR;
                    sendMessage(cmd);
                }
            }
        }
        sendMessage(LINE_CR);

    } catch (IOException e) {
        throw e;
    }
}
    /**
     * SELECT ���𔭍s����B
     * 
     * @param table �e�[�u����
     * @param whereCondition WHERE��}�b�v
     * @return ���s����
     * @throws Exception ���s����O
     */
    public int executeQuery(String table, Map whereCondition)
            throws Exception {
        return executeQuery(table, "", whereCondition);
    }

    /**
     * SELECT�X�g�A�h�v���V�[�W���𔭍s����B
     * 
     * @param procedure ���s����v���V�[�W����
     * @return ���s����
     * @throws Exception ���s����O
     */
    public int executeQueryProcedure(String procedure) throws Exception {
        return executeQuery("", procedure, null);
    }
    /**
     * SELECT ���𔭍s����B
     * 
     * @param table �e�[�u����
     * @param procedure ���s����v���V�[�W����
     * @param whereCondition WHERE��}�b�v
     * @return ���s����
     * @throws Exception ���s����O
     */
    public int executeQuery(String table, String procedure,
            Map whereCondition) throws Exception {
        return executeQuery(table, procedure, whereCondition, null);
    }
    /**
     * ���ԃe�[�u�������������܂��B
     * @param dbm DB�}�l�[�W��
     * @throws Exception ������O
     */
    public static void clearAccessSpace(IkenshoFirebirdDBManager dbm) throws Exception{
        try{
            //���[�J��IP�ɊY�����郌�R�[�h������
            StringBuffer sb = new StringBuffer();
            sb.append(" WHERE");
            sb.append("(RECEIPT_ACCESS_SPACE.LOCAL_IP='");
            sb.append(getLocalIP());
            sb.append("')");
            String where = sb.toString();
            sb = new StringBuffer();
            sb.append("DELETE FROM");
            sb.append(" RECEIPT_ACCESS_SPACE");
            sb.append(where);
            dbm.executeUpdate(sb.toString());
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" COUNT(*)");
            sb.append(" FROM");
            sb.append(" RECEIPT_ACCESS_SPACE");
            sb.append(where);
            dbm.executeQuery(sb.toString());
        }catch(Exception ex){
            //���݂��Ȃ���΍쐬���Ă݂�
            StringBuffer sb = new StringBuffer();
            sb.append("CREATE TABLE");
            sb.append(" RECEIPT_ACCESS_SPACE");
            sb.append(" (");
            sb.append(" LOCAL_IP VARCHAR(30) NOT NULL");
            sb.append(",SERIAL_ID INTEGER NOT NULL");
            sb.append(",PTID VARCHAR(10)");
            sb.append(",NAME VARCHAR(100)");
            sb.append(",KANANAME VARCHAR(100)");
            sb.append(",SEX CHAR(1)");
            sb.append(",BIRTHDAY CHAR(8)");
            sb.append(",HOME_POST VARCHAR(7)");
            sb.append(",HOME_ADRS VARCHAR(200)");
            sb.append(",HOME_BANTI VARCHAR(200)");
            sb.append(",HOME_TEL1 VARCHAR(15)");
            sb.append(",LAST_TIME TIMESTAMP");
            sb.append(",PRIMARY KEY (");
            sb.append(" LOCAL_IP");
            sb.append(",SERIAL_ID");
            sb.append(" )");
            sb.append(")");
            dbm.executeUpdate(sb.toString());
            dbm.commitTransaction();
            //�R�~�b�g���Ȃ���CREATE�����f����Ȃ�
            dbm.beginTransaction();
        }
    }
    /**
     * ���[�J���z�X�g��IP�A�h���X��Ԃ��܂��B
     * @return IP�A�h���X
     * @throws UnknownHostException �z�X�g������O
     */
    protected static String getLocalIP() throws UnknownHostException{
        InetAddress local=InetAddress.getLocalHost();
        if(local!=null){
            String address =local.getHostAddress();
            if(address!=null){
                if(address.length()>30){
                    address = address.substring(0,30);
                }
                return address;
            }
        }
        return "localhost";
    }
    
    /**
     * SELECT ���𔭍s����B
     * 
     * @param table �e�[�u����
     * @param procedure ���s����v���V�[�W����
     * @param whereCondition WHERE��}�b�v
     * @param splash �X�v���b�V��
     * @return ���s����
     * @throws Exception ���s����O
     */
    public int executeQuery(String table, String procedure,
            Map whereCondition, ACSplashable splash) throws Exception {
        if (!isConnected()) {
            if (connect() != STATUS_CODE_OK) {
                return -1;
            }
        }

        if (beginTransaction() != STATUS_CODE_OK) {
            return -2;
        }

        // �J�[�\����ݒ�
        HashMap map = execute(COMMAND_DBSELECT, table, procedure,
                whereCondition);

        int count = 1;
        IkenshoFirebirdDBManager dbm = null;
        try {
            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();
            // �ڍs�̈���������񂷂ׂď�����
            clearAccessSpace(dbm);

            //2006/02/11[Tozo Tanaka] : add begin
            //TODO canChange?
            boolean encode = false;
            String osName = System.getProperty("os.name");
            //Mac�ȊO�ł���Ε����R�[�h�ϊ����s���B
            if ((osName != null) && (osName.indexOf("Mac") < 0)) {
                encode = true;
            }
            //2006/02/11[Tozo Tanaka] : add end
            
            
            String ip = getLocalIP();
            // 1�s���擾
            while (true) {
                map = execute(COMMAND_DBFETCH, table, procedure, whereCondition);
                if ((map == null) || (map.size() <= 0)) {
                    break;
                }
                Object patientName = map.get("tbl_ptinf.NAME");

                StringBuffer sb = new StringBuffer();
                sb.append("INSERT INTO");
                sb.append(" RECEIPT_ACCESS_SPACE");
                sb.append(" (");
                sb.append(" LOCAL_IP");
                sb.append(",SERIAL_ID");
                sb.append(",PTID");
                sb.append(",NAME");
                sb.append(",KANANAME");
                sb.append(",SEX");
                sb.append(",BIRTHDAY");
                sb.append(",HOME_POST");
                sb.append(",HOME_ADRS");
                sb.append(",HOME_BANTI");
                sb.append(",HOME_TEL1");
                sb.append(",LAST_TIME");
                sb.append(" )");
                sb.append(" VALUES");
                sb.append(" (");
                sb.append("'"+ip+"'");
                sb.append(",");
                sb.append(count++);
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map.get("tbl_ptinf.PTID")));
                sb.append(",");
                
                
                // 2006/02/11[Tozo Tanaka] : replace begin
                // TODO canChange?
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(patientName));
                // sb.append(",");
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                // .get("tbl_ptinf.KANANAME")));
                // sb.append(",");
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                // .get("tbl_ptinf.SEX")));
                // sb.append(",");
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                // .get("tbl_ptinf.BIRTHDAY")));
                // sb.append(",");
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                // .get("tbl_ptinf.HOME_POST")));
                // sb.append(",");
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                // .get("tbl_ptinf.HOME_ADRS")));
                // sb.append(",");
                // sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                // .get("tbl_ptinf.HOME_BANTI")));
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING
                        .format(getORCADecodeString(patientName, encode)));
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING
                        .format(getORCADecodeString(map
                                .get("tbl_ptinf.KANANAME"), encode)));
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                        .get("tbl_ptinf.SEX")));
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                        .get("tbl_ptinf.BIRTHDAY")));
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                        .get("tbl_ptinf.HOME_POST")));
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING
                        .format(getORCADecodeString(map
                                .get("tbl_ptinf.HOME_ADRS"), encode)));
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING
                        .format(getORCADecodeString(map
                                .get("tbl_ptinf.HOME_BANTI"), encode)));
                // 2006/02/11[Tozo Tanaka] : replace end
                
                
                sb.append(",");
                sb.append(IkenshoConstants.FORMAT_PASSIVE_STRING.format(map
                        .get("tbl_ptinf.HOME_TEL1")));
                sb.append(",CURRENT_TIMESTAMP");
                sb.append(")");

                dbm.executeUpdate(sb.toString());
                
                if (splash instanceof ACSplash) {
                    // �󋵕\��
                    String message = (count - 1) + " ����";
                    if (patientName != null) {
                        message += " / " + patientName;
                    }
                    ((ACSplash) splash).setMessage(message);
                    
                    if(splash instanceof ACStopButtonSplash){
                        if(((ACStopButtonSplash)splash).isStopRequested()){
                            //��~�v�����Ď�����
                            break;
                        }
                    }
                }
            }

            dbm.commitTransaction();
        } catch (Exception ex) {
            if(dbm!=null){
                dbm.rollbackTransaction();
            }
            throw ex;
        }

        
        if (commitTransaction() != STATUS_CODE_OK) {
            return -3;
        }

        return count-1;
    }
    //2006/02/09[Tozo Tanaka] : replace end 

    /**
     * �����̃}�b�v�l��VRHashMap�Ƃ��ĕԂ��܂��B
     * 
     * @param map �ϊ���
     * @return �ϊ�����
     */
    protected VRMap toVRHashMap(HashMap map) {
        VRMap vrMap = new VRHashMap();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry ent = (Map.Entry) it.next();
            vrMap.put(ent.getKey(), ent.getValue());
        }
        return vrMap;
    }

    /**
     * �w�肳�ꂽ�t�@���N�V���������s���A��M���鍀�ږ��ꗗ�Ŏw�肳�ꂽ���ڂ��擾����B
     * <P>
     * public HashMap recordops(String function, String table, String path,
     * HashMap sendData) �����K�؂Ȏ����ł��B
     * <P>
     * 
     * @param function ���s����t�@���N�V������
     * @param table �e�[�u����
     * @param path �p�X��
     * @param sendData ���M�f�[�^
     * @param getValues ��M���鍀�ږ��ꗗ
     * @return ��M�f�[�^
     * @throws IOException ���o�̓G���[�����������ꍇ
     * @throws Exception �����ȏ�Ԓʒm�R�[�h���󂯎�����ꍇ�≽�炩�̗�O�����������ꍇ
     */
    protected HashMap execute(String function, String table, String path,
            HashMap sendData, String[] getValues) throws IOException, Exception {
        HashMap retValeus = new HashMap();

        try {
            String cmd = COMMAND_EXEC + " " + function + ":" + table + ":"
                    + path + LINE_CR;

            sendMessage(cmd);

            if ((sendData != null) && (sendData.size() > 0)) {
                execData(sendData);
            }

            int rc = getStatus();

            if (rc == STATUS_CODE_OK) {
                // retValeus = getValues(table);
                retValeus = getValues(getValues);
            } else if (rc == STATUS_CODE_EOF) {
            } else {
                String msg = null;
                if (rc == STATUS_CODE_NONFATAL) {
                    msg = "fatal error.";
                } else if (rc == STATUS_CODE_ARGUMENT_ERROR) {
                    msg = "illegal argument.";
                } else if (rc == STATUS_CODE_FUNCTION_ERROR) {
                    msg = "illegal function name.";
                } else if (rc == STATUS_CODE_SQL_ERROR) {
                    msg = "sql error.";
                } else {
                    msg = "other error.[" + rc + "]";
                }
                if (msg != null) {
                    throw new Exception(msg);
                }
            }

            // retValeus = getValues(getValues);
            sendMessage(LINE_CR);
        } catch (Exception e) {
            throw e;
        }
        return retValeus;
    }

    /**
     * �Ō�Ɏ�M������Ԓʒm�R�[�h��Ԃ��B
     * <P>
     * 
     * @return �Ō�Ɏ�M������Ԓʒm�R�[�h
     */
    public int getLastStatusCode() {
        return lastStatusCode;
    }

    /**
     * URLDecode���̕����G���R�[�f�B���O���擾����B
     * 
     * @return URLDecode���̕����G���R�[�f�B���O
     */
    public String getDecodeCharCode() {
        return dec;
    }

    /**
     * URLEncode���̕����G���R�[�f�B���O���擾����B
     * 
     * @return URLEncode���̕����G���R�[�f�B���O
     */
    public String getEncodeCharCode() {
        return enc;
    }

    /**
     * URLDecode���̕����G���R�[�f�B���O���Z�b�g����B
     * 
     * @param dec URLDecode���̕����G���R�[�f�B���O
     */
    public void setDecodeCharCode(String dec) {
        this.dec = dec;
    }

    /**
     * URLEncode���̕����G���R�[�f�B���O���Z�b�g����B
     * 
     * @param enc URLEncode���̕����G���R�[�f�B���O
     */
    public void setEncodeCharCode(String enc) {
        this.enc = enc;
    }

    /**
     * ���̃I�u�W�F�N�g���j�������ۂɌĂяo����郁�\�b�h�B
     * 
     * @throws Throwable ������O
     */
    protected void finalize() throws Throwable {
        close();
    }

    /**
     * close()���\�b�h���Ăяo���ꂽ���ǂ�����Ԃ��B
     * 
     * @return close()���\�b�h���Ăяo���ꂽ�ꍇ��true
     */
    protected boolean isClosed() {
        return closed;
    }

    /**
     * �ڑ��ς݂ł��邩��Ԃ��B
     * 
     * @return �ڑ��ς݂̏ꍇ��true
     */
    protected boolean isConnected() {
        return connected;
    }

    // /**
    // * Statement�𐶐����ĕԂ��B
    // * @return Statement�C���X�^���X
    // */
    // public DBSStatement createStatement() {
    // return new DBSStatement(this);
    // }

    // /**
    // * PreparedStatement�𐶐����ĕԂ��B
    // * @return PreparedStatement�C���X�^���X
    // */
    // public DBSPreparedStatement prepareStatement(DBSFunction function) {
    // return new DBSPreparedStatement(this, function);
    // }

    /**
     * �F�ؗp�̃p�X���[�h���擾���܂��B
     * 
     * @return �F�ؗp�̃p�X���[�h
     */
    public String getPassword() {
        return password;
    }

    /**
     * �F�ؗp�̃p�X���[�h��ݒ肵�܂��B
     * 
     * @param password �F�ؗp�̃p�X���[�h
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * �F�ؗp�̃��[�U�[�����擾���܂��B
     * 
     * @return �F�ؗp�̃��[�U�[��
     */
    public String getUserName() {
        return userName;
    }

    /**
     * �F�ؗp�̃��[�U�[����ݒ肵�܂��B
     * 
     * @param userName �F�ؗp�̃��[�U�[��
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * �F�ؗp��DBS�o�[�W�������擾���܂��B
     * 
     * @return �F�ؗp��DBS�o�[�W����
     */
    public String getDBSVersion() {
        return dbsVersion;
    }

    /**
     * �F�ؗp��DBS�o�[�W������ݒ肵�܂��B
     * 
     * @param dbsVersion �F�ؗp��DBS�o�[�W����
     */
    public void setDBSVersion(String dbsVersion) {
        this.dbsVersion = dbsVersion;
    }

}
