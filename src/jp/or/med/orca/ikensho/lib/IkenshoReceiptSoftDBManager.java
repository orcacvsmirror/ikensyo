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
 * 開発者: 田中統蔵
 * 作成日: 2005/12/01  日本コンピュータ株式会社 田中統蔵 新規作成
 * 更新日: ----/--/--
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
    // 定数値定義
    // --------------------------------------------------------------------------
    // デフォルトポート番号（2003/08/14現在。）
    public static final int DEFAULT_PORT = 8013;

    // デフォルトサーバアドレス（localhost）
    public static final String DEFAULT_HOST = "127.0.0.1";

    // デフォルトDBバージョン
    public static final String DEFAULT_DBS_VERSION = "1.2.2";

    // エンコードを行なう際のデフォルト文字コード
    public static final String DEFAULT_ENCODE = "EUCJIS";

    // デコードを行なう際のデフォルト文字コード
    public static final String DEFAULT_DECODE = "EUCJIS";

    // セッション開設時に送信する固定文字列（ver.1.2.0以降）
    public static final String SESSION_OPENING_CONSTANT = "stringe";

    // セッション開設成功時の固定文字列
    public static final String SESSION_OPENING_SUCCESS = "Connect: OK";

    // 状態通知コード（OK）
    public static final int STATUS_CODE_OK = 0;

    // 状態通知コード（EOF）
    public static final int STATUS_CODE_EOF = 1;

    // 状態通知コード（NONFATAL）
    public static final int STATUS_CODE_NONFATAL = 2;

    // 状態通知コード（引数不正）
    public static final int STATUS_CODE_ARGUMENT_ERROR = -1;

    // 状態通知コード（処理名不正）
    public static final int STATUS_CODE_FUNCTION_ERROR = -2;

    // 状態通知コード（SQL不正）
    public static final int STATUS_CODE_SQL_ERROR = -3;

    // 状態通知コード（その他不正）
    public static final int STATUS_CODE_OTHER_ERROR = -4;

    // コマンド（データベース処理の開設）
    public static final String COMMAND_OPEN = "DBOPEN";

    // コマンド（トランザクションの開始）
    public static final String COMMAND_BEGINTRANS = "DBSTART";

    // コマンド（トランザクション終了）
    public static final String COMMAND_ENDTRANS = "DBEND";

    // コマンド（トランザクションCommit）
    public static final String COMMAND_COMMIT = "DBCOMMIT";

    // コマンド（セッションの破棄）
    public static final String COMMAND_DBDISCONNECT = "DBDISCONNECT";

    // コマンド実行
    public static final String COMMAND_EXEC = "Exec:";

    // 処理の終了
    public static final String COMMAND_END = "End";

    // コマンド実行結果
    public static final String COMMAND_RECEIVE = "Exec: ";

    // 標準定義コマンド（DBINSERT）
    public static final String COMMAND_DBSELECT = "DBSELECT";

    // 標準定義コマンド（DBINSERT）
    public static final String COMMAND_DBINSERT = "DBINSERT";

    // 標準定義コマンド（DBUPDATE）
    public static final String COMMAND_DBUPDATE = "DBUPDATE";

    // 標準定義コマンド（DBDELETE）
    public static final String COMMAND_DBDELETE = "DBDELETE";

    // 標準定義コマンド（DBFETCH）
    public static final String COMMAND_DBFETCH = "DBFETCH";

    // エラー（認証エラー）
    public static final String ERROR_AUTHENTICATION = "authentication";

    // エラー（サーバ、クライアントのバージョン不整合）
    public static final String ERROR_VERSION = "version";

    // エラー固定文字列
    public static final String ERROR_CONSTANT = "Error: ";

    // 改行
    public static final String LINE_CR = "\n";

    // コマンド終了
    public static final String END_OF_COMMAND = "\n";

    // データ終了
    public static final String END_OF_DATA = "";

    // NULLコード(0x01)
    public static final String NULL_CODE = "%01";

    // --------------------------------------------------------------------------
    // メンバ変数の定義
    // --------------------------------------------------------------------------
    // DBSポート番号
    private int port = DEFAULT_PORT;

    // DBSサーバーアドレス
    private String host = DEFAULT_HOST;

    // DBSバージョン
    private String dbsVersion = DEFAULT_DBS_VERSION;

    // 入出力ソケット
    private Socket dbsSocket = null;

    // DBSへのデータ送信ストリーム
    private PrintWriter dbsOut = null;

    // DBSからのデータ受信ストリーム
    private BufferedReader dbsIn = null;

    // dbs認証ユーザ
    private String userName = null;

    // dbs認証パスワード
    private String password = null;

    // 最後に受けとった状態通知コード
    private int lastStatusCode;

    // デコード文字列
    private String dec = DEFAULT_ENCODE;

    // エンコード文字列
    private String enc = DEFAULT_DECODE;

    // ソケットアドレス
    private InetSocketAddress socketAddress;

    // フックが登録済か？
    private static boolean hooked = false;

    // DBDISCONNECTが呼び出されたか？
    private boolean dbDisconnected = false;

    // close()メソッドが呼び出されたか？
    private boolean closed = false;

    // 接続済みであるか
    private boolean connected = false;

    /**
     * dbsサーバーに接続するコンストラクタです。
     * 
     * @throws Exception サーバーに接続できない場合
     */
    public IkenshoReceiptSoftDBManager() throws Exception {
        this(DEFAULT_HOST, DEFAULT_PORT, "", "", DEFAULT_DBS_VERSION);
    }

    /**
     * dbsサーバーに接続するコンストラクタです。
     * 
     * @param userName 認証ユーザー名
     * @param password 認証パスワード
     * @param dbsVersion 認証用のDBSバージョン
     * @throws Exception サーバーに接続できない場合
     */
    public IkenshoReceiptSoftDBManager(String userName, String password,
            String dbsVersion) throws Exception {
        this(DEFAULT_HOST, DEFAULT_PORT, userName, password, dbsVersion);
    }

    /**
     * dbsサーバーに接続するコンストラクタです。
     * 
     * @param host dbsが起動しているサーバーアドレス
     * @param port dbsの使用ポート
     * @throws Exception サーバーに接続できない場合
     */
    public IkenshoReceiptSoftDBManager(String host, int port) throws Exception {
        this(host, port, "", "", DEFAULT_DBS_VERSION);
    }

    /**
     * サーバーアドレスとポートで指定されたdbsサーバーに接続するコンストラクタです。
     * 
     * @param host dbsが起動しているサーバーアドレス
     * @param port dbsの使用ポート
     * @param userName 認証ユーザー名
     * @param password 認証パスワード
     * @param dbsVersion 認証用のDBSバージョン
     * @throws Exception サーバーに接続できない場合
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
                // 終了時の処理。
                // 必ずclose()が呼び出されるようにする。
                // ※close()が呼び出されなかった場合、サーバ側にdbsのプロセスが
                // 残る可能性がある。
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
        // 開く前にリソースをクローズしておく。
        // ------------------------------------------------------------
        try {
            closeConnection();
        } catch (Exception ignore) {
        }

        try {
            socketAddress = new InetSocketAddress(host, port);
            // ------------------------------------------------------------
            // 例外が発生したらリソースを破棄する。
            // ------------------------------------------------------------
        } catch (Exception e) {
            closeConnection();
            throw new Exception(e);
        }
    }

    /**
     * dbsサーバーに接続する。
     * <P>
     * 
     * @throws Exception サーバに接続できない場合
     */
    protected void setConnection() throws Exception {
        // ------------------------------------------------------------
        // 開く前にリソースをクローズしておく。
        // ------------------------------------------------------------
        try {
            // socketAddr = null;
            closeConnection();
        } catch (Exception ignore) {
        }

        try {
            dbsSocket = new Socket();
            // タイムアウト値をセットできるようにする
            dbsSocket.connect(socketAddress);

            // 手動でFlash
            dbsOut = new PrintWriter(dbsSocket.getOutputStream(), false);

            dbsIn = new BufferedReader(new InputStreamReader(dbsSocket
                    .getInputStream()));

            // ------------------------------------------------------------
            // 例外が発生したらリソースを閉じる
            // ------------------------------------------------------------
        } catch (Exception e) {
            close();
            throw new Exception(e);
        }
        closed = false;
    }

    /**
     * dbsに対して認証を行う。
     * <P>
     * ※認証に失敗した場合、closeメソッドが呼び出されます。注意して下さい。
     * <P>
     * 
     * @throws Exception 認証エラーが発生した場合
     * @throws IOException 入出力エラーが発生した場合
     */
    protected void authenticate() throws Exception, IOException {

        try {
            // バージョン番号 ユーザ名 パスワード stringe
            String cmd = dbsVersion + " " + userName + " " + password + " "
                    + SESSION_OPENING_CONSTANT + LINE_CR;

            sendMessage(cmd);
            String result = recvMessage();

            // ------------------------------------------------
            // 接続失敗
            // ------------------------------------------------
            if (result == null) {
                throw new Exception("authentication result is null.");
            } else if (!result.equals(SESSION_OPENING_SUCCESS)) {
                // バージョンエラー
                if (result.equals(ERROR_CONSTANT + ERROR_VERSION)) {
                    throw new Exception("invalid version.");

                    // 認証エラー
                } else if (result.equals(ERROR_CONSTANT + ERROR_AUTHENTICATION)) {
                    throw new Exception("authentication error.");

                    // その他
                } else {
                    throw new Exception("other error.");
                }
            }
            // ------------------------------------------------------------
            // 例外が発生したらリソースを閉じる
            // ------------------------------------------------------------
        } catch (Exception e) {
            close();
            throw e;
        }
    }

    /**
     * dbs状態通知コード取得。
     * <P>
     * dbsからの状態通知コードを取得します。<br>
     * <b>このメソッドを呼び出すことでdbsからの応答がなくなる場合があります。 通信手順はDBSのドキュメントをご覧下さい。</b>
     * <P>
     * 
     * @return 状態通知コード<br>
     *         0: OK<br>
     *         1: EOF<br>
     *         2: NONFATAL<br>
     *         3: 引数不正<br>
     *         -2: 処理名不正<br>
     *         -3: SQL不正<br>
     *         -4: その他不正（ORCAチームにお問い合わせください。）<br>
     *         その他： エラー内容不明（ORCAチームにお問い合わせください。）<br>
     *         <P>
     * @throws Exception 状態通知コードを取得することができなかった場合
     * @throws IOException 入出力エラーが発生した場合
     */
    protected int getStatus() throws Exception, IOException {
        int status;

        try {
            String result = recvMessage();

            if (result == null) {
                throw new Exception("result is null.");
            }
            /** Exec: XXXと一致するか? */
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
     * DBから切断する。
     * <P>
     * 
     * @return 状態通知コード
     * @throws IOException 接続エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合やDBのオープンに失敗した場合
     */
    protected int disconnect() throws IOException, Exception {
        this.dbDisconnected = false;
        int status;

        try {
            status = sendCommand(COMMAND_DBDISCONNECT);

            // 状態コードがOK以外の場合
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
     * DBをオープンする。
     * <P>
     * 
     * @return 状態通知コード
     * @throws IOException 接続エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合やDBのオープンに失敗した場合
     */
    protected int connect() throws IOException, Exception {
        if (isClosed()) {
            setConnection();
        }
        // ---------------------------------------------------
        // DBDISCONNECTが呼び出されていない状態で
        // 2回以上DBOPENを呼び出すことはできない。
        // ---------------------------------------------------
        if (dbDisconnected) {
            return STATUS_CODE_OK;
        }
        int status;

        try {
            // 認証を行なう
            authenticate();

            status = sendCommand(COMMAND_OPEN);

            // 状態コードがOK以外の場合
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
     * トランザクション開始。
     * <P>
     * 
     * @return 状態通知コード
     * @throws IOException 接続エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合や何らかの例外が発生した場合
     */
    public int beginTransaction() throws IOException, Exception {
        int status;

        try {
            status = sendCommand(COMMAND_BEGINTRANS);

            // 状態コードがOK以外の場合
            if (status != STATUS_CODE_OK) {
                throw new Exception("begin transaction error.[" + status + "]");
            }
        } catch (Exception e) {
            throw e;
        }
        return status;
    }

    /**
     * トランザクションCOMMIT。
     * <P>
     * 
     * @return 状態通知コード
     * @throws IOException 接続エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合や何らかの例外が発生した場合
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
     * ソケットにデータを送る
     * 
     * @param cmd 送信データ
     * @throws IOException 接続エラーが発生した場合
     * @throws SocketException ソケットエラーが発生した場合
     */
    protected void sendMessage(String cmd) throws IOException, SocketException {
        dbsOut.print(cmd);
        dbsOut.flush();
    }

    /**
     * コマンドの送信を終了する。
     * <P>
     * このコマンドが正常に受け付けられた場合、サーバー側で生成されたdbsのプロセスは
     * 破棄されます。これ以降通信を続ける場合はopen()処理からやり直して下さい。<br>
     * ※close()メソッド内で呼び出されます。
     * <P>
     * 
     * @throws IOException 接続エラーが発生した場合
     */
    protected void endCommand() throws IOException {
        sendMessage(COMMAND_END + LINE_CR);
        dbDisconnected = true;
    }

    /**
     * コマンドの実行を行なう。
     * 
     * @param functionName 処理名
     * @param tableName テーブル名
     * @param pathName パス名
     * @throws IOException 接続エラーが発生した場合
     * @throws Exception 何らかの例外が発生した場合
     */
    protected void execCommand(String functionName, String tableName,
            String pathName) throws Exception, IOException {
        sendMessage(COMMAND_EXEC + " " + functionName + ":" + tableName + ":"
                + pathName + LINE_CR);
    }

    /**
     * データの受信終了を通知する。
     * 
     * @throws IOException 接続エラーが発生した場合
     * @throws Exception 何らかの例外が発生した場合
     */
    protected void recvDataEnd() throws Exception, IOException {
        sendMessage(END_OF_COMMAND);
    }

    /**
     * ソケットからデータを取得する
     * 
     * @return 受信データ
     * @throws IOException 入力エラーが発生した場合
     */
    protected String recvMessage() throws IOException {
        String msg = dbsIn.readLine();
        return msg;
    }

    /**
     * 文字列デコード。
     * <P>
     * 
     * @param str デコードを行う文字列
     * @return デコード結果
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
     * 文字列エンコード。
     * <P>
     * 
     * @param str エンコードを行う文字列
     * @return エンコード結果
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
     * dbsへの接続をクローズする。
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
     * 入出力ストリームを閉じる。
     */
    protected void closeConnection() {
        // 出力ストリームクローズ
        if (dbsOut != null) {
            try {
                dbsOut.close();
                dbsOut = null;
            } catch (Exception ignore) {
            }
        }
        // 入力ストリームクローズ
        if (dbsIn != null) {
            try {
                dbsIn.close();
                dbsIn = null;
            } catch (Exception ignore) {
            }
        }
        // ソケットクローズ
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
     * dbs使用定義体で定義されたファンクションに渡すパラメータを送信する。
     * <P>
     * 
     * @param rec 送信するデータセット（キー=フィールド名、値=送信値）
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 上記以外の例外が発生した場合
     * @deprecated 送信する値にnullを指定できません。execData(HashMap rec)を使用するようにして下さい。
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
     * dbs使用定義体で定義されたファンクションに渡すパラメータを送信する。
     * <P>
     * 
     * @param fieldName フィールド名
     * @param value 送信する値
     *            <P>
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 上記以外の例外が発生した場合
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
     * dbsインターフェース標準処理を実行する。
     * <P>
     * 
     * @param func 標準処理名
     * @return 状態通知コード
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合や上記以外の例外が発生した場合
     */
    protected int sendCommand(String func) throws IOException, Exception {
        int status;
        int stage = 0;

        try {
            stage = 1;
            sendMessage(COMMAND_EXEC + " " + func + LINE_CR);
            stage = 2;
            // コマンド送信終了
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
     * データ受信。
     * <P>
     * 指定された項目に下位項目があった場合、下位項目全てを受信します。 受信した値はアプリケーション側でキャストして下さい。
     * <P>
     * ※戻り値がHashtableからHashMapに変更になりました。
     * <P>
     * 
     * @param name データを取得する項目名
     * @return 名前と値のペア(型情報は含まれない)
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 何らかの例外が発生した場合
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
     * データ受信。
     * <P>
     * 指定された項目全てを受信します。 受信した値はアプリケーション側でキャストして下さい。
     * <P>
     * 
     * @param name データを取得する項目名
     * @return 名前と値のペア(型情報は含まれない)
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 何らかの例外が発生した場合
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
     * 指定されたファンクションを実行し、テーブル名で指定された項目を取得する。
     * <P>
     * ※戻り値がHashtableからHashMapに変更されました。
     * <P>
     * できるだけpublic HashMap recordops(String function, String table, String path,
     * HashMap sendData) を使用するようにして下さい。
     * <P>
     * 
     * @param function 実行するファンクション名
     * @param table テーブル名
     * @param path パス名
     * @param sendData 送信データ
     * @return 受信データ
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合や何らかの例外が発生した場合
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
     * 文字コード変換結果を返します。
     * @param data 変換元
     * @param encode 変換を実行するか
     * @return 変換結果
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
                VRLogger.warning("文字コードの変換に失敗しました。");
            }
        }
        return result;
    }

    //2006/02/09[Tozo Tanaka] : replace begin
//    /**
//     * SELECT 文を発行する。
//     * 
//     * @param table テーブル名
//     * @param whereCondition WHERE句マップ
//     * @return 実行結果
//     * @throws Exception 実行時例外
//     */
//    public VRArrayList executeQuery(String table, HashMap whereCondition)
//            throws Exception {
//        return executeQuery(table, "", whereCondition);
//    }
//
//    /**
//     * SELECTストアドプロシージャを発行する。
//     * 
//     * @param procedure 発行するプロシージャ名
//     * @return 実行結果
//     * @throws Exception 実行時例外
//     */
//    public VRArrayList executeQueryProcedure(String procedure) throws Exception {
//        return executeQuery("", procedure, null);
//    }
   
//    /**
//     * SELECT 文を発行する。
//     * 
//     * @param table テーブル名
//     * @param procedure 発行するプロシージャ名
//     * @param whereCondition WHERE句マップ
//     * @param splash スプラッシュ
//     * @return 実行結果
//     * @throws Exception 実行時例外
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
//        // カーソルを設定
//        HashMap map = execute(COMMAND_DBSELECT, table, procedure,
//                whereCondition);
//
//
        //        // 1行ずつ取得
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
//  * dbs使用定義体で定義されたファンクションに渡すパラメータを送信する。
//  * <P>
//  * (1)Hashtableではnullを表現することができない<br>
//  * (2)dbsでnullを扱うことができるようになった<br>
//  * 上記の理由のため新しく実装されました。
//  * <P>
//  * 
//  * @param rec 送信するデータセット（キー=レコード名、値=送信する値）
//  * @throws IOException 入出力エラーが発生した場合
//  * @throws Exception 上記以外の例外が発生した場合
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
 * dbs使用定義体で定義されたファンクションに渡すパラメータを送信する。
 * <P>
 * (1)Hashtableではnullを表現することができない<br>
 * (2)dbsでnullを扱うことができるようになった<br>
 * 上記の理由のため新しく実装されました。
 * <P>
 * 
 * @param rec 送信するデータセット（キー=レコード名、値=送信する値）
 * @throws IOException 入出力エラーが発生した場合
 * @throws Exception 上記以外の例外が発生した場合
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
     * SELECT 文を発行する。
     * 
     * @param table テーブル名
     * @param whereCondition WHERE句マップ
     * @return 実行結果
     * @throws Exception 実行時例外
     */
    public int executeQuery(String table, Map whereCondition)
            throws Exception {
        return executeQuery(table, "", whereCondition);
    }

    /**
     * SELECTストアドプロシージャを発行する。
     * 
     * @param procedure 発行するプロシージャ名
     * @return 実行結果
     * @throws Exception 実行時例外
     */
    public int executeQueryProcedure(String procedure) throws Exception {
        return executeQuery("", procedure, null);
    }
    /**
     * SELECT 文を発行する。
     * 
     * @param table テーブル名
     * @param procedure 発行するプロシージャ名
     * @param whereCondition WHERE句マップ
     * @return 実行結果
     * @throws Exception 実行時例外
     */
    public int executeQuery(String table, String procedure,
            Map whereCondition) throws Exception {
        return executeQuery(table, procedure, whereCondition, null);
    }
    /**
     * 中間テーブルを初期化します。
     * @param dbm DBマネージャ
     * @throws Exception 処理例外
     */
    public static void clearAccessSpace(IkenshoFirebirdDBManager dbm) throws Exception{
        try{
            //ローカルIPに該当するレコードを消去
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
            //存在しなければ作成してみる
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
            //コミットしないとCREATEが反映されない
            dbm.beginTransaction();
        }
    }
    /**
     * ローカルホストのIPアドレスを返します。
     * @return IPアドレス
     * @throws UnknownHostException ホスト解決例外
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
     * SELECT 文を発行する。
     * 
     * @param table テーブル名
     * @param procedure 発行するプロシージャ名
     * @param whereCondition WHERE句マップ
     * @param splash スプラッシュ
     * @return 実行結果
     * @throws Exception 実行時例外
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

        // カーソルを設定
        HashMap map = execute(COMMAND_DBSELECT, table, procedure,
                whereCondition);

        int count = 1;
        IkenshoFirebirdDBManager dbm = null;
        try {
            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();
            // 移行領域をいったんすべて初期化
            clearAccessSpace(dbm);

            //2006/02/11[Tozo Tanaka] : add begin
            //TODO canChange?
            boolean encode = false;
            String osName = System.getProperty("os.name");
            //Mac以外であれば文字コード変換を行う。
            if ((osName != null) && (osName.indexOf("Mac") < 0)) {
                encode = true;
            }
            //2006/02/11[Tozo Tanaka] : add end
            
            
            String ip = getLocalIP();
            // 1行ずつ取得
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
                    // 状況表示
                    String message = (count - 1) + " 件目";
                    if (patientName != null) {
                        message += " / " + patientName;
                    }
                    ((ACSplash) splash).setMessage(message);
                    
                    if(splash instanceof ACStopButtonSplash){
                        if(((ACStopButtonSplash)splash).isStopRequested()){
                            //停止要求を監視する
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
     * 引数のマップ値をVRHashMapとして返します。
     * 
     * @param map 変換元
     * @return 変換結果
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
     * 指定されたファンクションを実行し、受信する項目名一覧で指定された項目を取得する。
     * <P>
     * public HashMap recordops(String function, String table, String path,
     * HashMap sendData) よりも適切な実装です。
     * <P>
     * 
     * @param function 実行するファンクション名
     * @param table テーブル名
     * @param path パス名
     * @param sendData 送信データ
     * @param getValues 受信する項目名一覧
     * @return 受信データ
     * @throws IOException 入出力エラーが発生した場合
     * @throws Exception 無効な状態通知コードを受け取った場合や何らかの例外が発生した場合
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
     * 最後に受信した状態通知コードを返す。
     * <P>
     * 
     * @return 最後に受信した状態通知コード
     */
    public int getLastStatusCode() {
        return lastStatusCode;
    }

    /**
     * URLDecode時の文字エンコーディングを取得する。
     * 
     * @return URLDecode時の文字エンコーディング
     */
    public String getDecodeCharCode() {
        return dec;
    }

    /**
     * URLEncode時の文字エンコーディングを取得する。
     * 
     * @return URLEncode時の文字エンコーディング
     */
    public String getEncodeCharCode() {
        return enc;
    }

    /**
     * URLDecode時の文字エンコーディングをセットする。
     * 
     * @param dec URLDecode時の文字エンコーディング
     */
    public void setDecodeCharCode(String dec) {
        this.dec = dec;
    }

    /**
     * URLEncode時の文字エンコーディングをセットする。
     * 
     * @param enc URLEncode時の文字エンコーディング
     */
    public void setEncodeCharCode(String enc) {
        this.enc = enc;
    }

    /**
     * このオブジェクトが破棄される際に呼び出されるメソッド。
     * 
     * @throws Throwable 処理例外
     */
    protected void finalize() throws Throwable {
        close();
    }

    /**
     * close()メソッドが呼び出されたかどうかを返す。
     * 
     * @return close()メソッドが呼び出された場合はtrue
     */
    protected boolean isClosed() {
        return closed;
    }

    /**
     * 接続済みであるかを返す。
     * 
     * @return 接続済みの場合はtrue
     */
    protected boolean isConnected() {
        return connected;
    }

    // /**
    // * Statementを生成して返す。
    // * @return Statementインスタンス
    // */
    // public DBSStatement createStatement() {
    // return new DBSStatement(this);
    // }

    // /**
    // * PreparedStatementを生成して返す。
    // * @return PreparedStatementインスタンス
    // */
    // public DBSPreparedStatement prepareStatement(DBSFunction function) {
    // return new DBSPreparedStatement(this, function);
    // }

    /**
     * 認証用のパスワードを取得します。
     * 
     * @return 認証用のパスワード
     */
    public String getPassword() {
        return password;
    }

    /**
     * 認証用のパスワードを設定します。
     * 
     * @param password 認証用のパスワード
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 認証用のユーザー名を取得します。
     * 
     * @return 認証用のユーザー名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 認証用のユーザー名を設定します。
     * 
     * @param userName 認証用のユーザー名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 認証用のDBSバージョンを取得します。
     * 
     * @return 認証用のDBSバージョン
     */
    public String getDBSVersion() {
        return dbsVersion;
    }

    /**
     * 認証用のDBSバージョンを設定します。
     * 
     * @param dbsVersion 認証用のDBSバージョン
     */
    public void setDBSVersion(String dbsVersion) {
        this.dbsVersion = dbsVersion;
    }

}
