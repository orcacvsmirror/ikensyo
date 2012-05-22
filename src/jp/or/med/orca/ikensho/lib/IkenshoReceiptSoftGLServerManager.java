package jp.or.med.orca.ikensho.lib;

import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.SocketException;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.SAXParserFactory;

import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACKanaConvert;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

public class IkenshoReceiptSoftGLServerManager {

    private String host = "192.168.1.1";
    private int port = 8000;
    private String user = "ormaster";
    private String pass = "ormaster123";
    
    private String APIResult = null;
    private int targetPatientCount = 0;

    public IkenshoReceiptSoftGLServerManager(String host, int port,
            String user, String pass) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;

        // Basic認証設定
        setUserPassword();
    }

    private void setUserPassword() {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass.toCharArray());
            }
        });
    }

    /**
     * 患者IDをキーに、日レセから患者情報を取得します。
     * 
     * @param id
     *            患者ID
     * @return 取得した患者リスト
     * @throws Exception
     */
    public VRList getPatientForID(String id) throws Exception {
        String targetUrl = "api01r/patientlst2?class=01";

        StringBuffer param = new StringBuffer();

        param.append("<data>");
        param.append("<record>");
        param.append("<record name=\"patientlst2req\">");
        param.append("<array name=\"Patient_ID_Information\">");

        param.append("<record>");
        param.append("<string name=\"Patient_ID\">");
        param.append(id);
        param.append("</string>");
        param.append("</record>");

        param.append("</array>");
        param.append("</record>");
        param.append("</record>");
        param.append("</data>");

        GLServerSaxHandler h = getResult(targetUrl, param.toString());
        
        //実行結果を退避
        APIResult = h.getAPIResult();
        targetPatientCount = h.getTargetPatientCount();

        return toIkenshoData(h.getResult());
    }

    /**
     * 日レセから、指定された患者氏名を含む患者情報を取得します。
     * 
     * @param name
     *            患者氏名
     * @return 取得した患者リスト
     * @throws Exception
     */
    public VRList getPatientForName(String name) throws Exception {
        String targetUrl = "api01r/patientlst3?class=01";

        StringBuffer param = new StringBuffer();

        param.append("<data>");
        param.append("<record>");
        param.append("<record name=\"patientlst3req\">");

        // 名前
        param.append("<string name=\"WholeName\">");
        param.append(ACKanaConvert.toKatakana(name));
        param.append("</string>");

        // 生年月日開始(現在未使用)
        param.append("<string name=\"Birth_StartDate\">");
        param.append("</string>");

        // 生年月日終了(現在未使用)
        param.append("<string name=\"Birth_EndDate\">");
        param.append("</string>");

        // 性別(現在未使用)
        param.append("<string name=\"Sex\">");
        param.append("</string>");

        // 入院・外来区分(現在未使用)
        param.append("<string name=\"InOut\">");
        param.append("</string>");

        param.append("</record>");
        param.append("</record>");
        param.append("</data>");

        GLServerSaxHandler h = getResult(targetUrl, param.toString());
        
        //実行結果を退避
        APIResult = h.getAPIResult();
        targetPatientCount = h.getTargetPatientCount();
        
        return toIkenshoData(h.getResult());
    }

    private GLServerSaxHandler getResult(String targetUrl, String param)
            throws Exception {

        URL url = new URL("http://" + host + ":" + port + "/" + targetUrl);

        HttpURLConnection urlconn = (HttpURLConnection) url.openConnection();
        urlconn.setRequestMethod("POST");
        urlconn.setRequestProperty("CONTENT-TYPE", "application/xml");

        urlconn.setDoOutput(true);
        OutputStreamWriter writer = null;

        try {
            writer = new OutputStreamWriter(urlconn.getOutputStream(), "UTF-8");
        } catch (ConnectException ex) {
            throw new Exception("日レセサーバーとの接続に失敗しました。ホストの設定を確認してください。", ex);
        }

        writer.write(param);
        writer.close();

        int responseCode = 0;
        try {
            responseCode = urlconn.getResponseCode();
        } catch (SocketException ex) {
            throw new Exception("日レセサーバーとの接続に失敗しました。ポート番号の設定を確認してください。", ex);
        }

        switch (responseCode) {
        // 通信OK
        case HttpURLConnection.HTTP_OK:
            break;
        case HttpURLConnection.HTTP_UNAUTHORIZED:
            throw new Exception("日レセサーバーとの接続に失敗しました。ID、パスワードを確認してください。");
        default:
            throw new Exception("日レセサーバーとの接続に失敗しました。(レスポンス:" + responseCode + ")");
        }

        /*
         * for debug java.io.BufferedReader br = new java.io.BufferedReader(new
         * java.io.InputStreamReader(urlconn.getInputStream(), "UTF-8")); String
         * line = null; while ((line = br.readLine()) != null) {
         * System.out.println(line); }
         */

        // SAXパーサーを生成
        SAXParserFactory spfactory = SAXParserFactory.newInstance();
        GLServerSaxHandler h = new GLServerSaxHandler();

        try {
            spfactory.newSAXParser().parse(urlconn.getInputStream(), h);

        } catch (Exception e) {
            throw new Exception("検索結果を解析中にエラーが発生しました。", e);
        }

        return h;
    }
    
    public String getAPIResult() {
        return APIResult;
    }
    public int getTargetPatientCount() {
        return targetPatientCount;
    }

    protected VRList toIkenshoData(VRList src) throws Exception {
        int size = src.size();
        if (size <= 0) {
            return src;
        }
        
        VRList retList = new VRArrayList();
        
        for (int i = 0; i < size; i++) {
            VRMap srcRow = (VRMap)src.getData(i);
            VRMap row = convertRow(srcRow);
            
            retList.add(row);
        }
        
        return retList;
    }
    
    
    private VRMap convertRow(VRMap row) throws Exception {
        
        String strPtid = (String)row.get("Patient_ID");
        
        int ptid = 0;
        try {
            ptid = Integer.parseInt(strPtid);
        } catch (Exception e) {
            throw new Exception("患者IDの変換に失敗しました。(" + strPtid + ")");
        }
        
        //生年月日
        Date birthday = null;
        //Calendar target = Calendar.getInstance();
        try {
            /*
            target.setTime(VRDateParser.parse(toStringValue(row.get("BirthDate"))));
            birthday = String.valueOf(target.get(Calendar.YEAR));
            birthday += "-";
            birthday += String.valueOf(target.get(Calendar.MONTH) + 1);
            birthday += "-";
            birthday += String.valueOf(target.get(Calendar.DATE));
            */
            birthday = ACCastUtilities.toDate(row.get("BirthDate"), null);
        } catch (Exception e){
            birthday = null;
        }
        
        //郵便番号
        String zipCode = toStringValue(row.get("Address_ZipCode"));
        if (zipCode.length() < 7) {
            if (zipCode.length() >= 3) {
                zipCode = zipCode.substring(0, 3) + "-" + zipCode.substring(3);
            }
        } else {
            zipCode = zipCode.substring(0, 3) + "-" + zipCode.substring(3, 7);
        }
        
        
        //住所
        String address = toStringValue(row.get("WholeAddress1")) + toStringValue(row.get("WholeAddress2"));
        
        String tel = toNotTelCharReplace(row.get("PhoneNumber1"));
        String[] tels = tel.split("-");
        //tel1
        //tel2
        String tel1 = "";
        String tel2 = "";
        
        if (tels.length != 0) {
            if (tels[0].length() > 5) {
                String[] telSrc;
                if (tels.length < 2) {
                    telSrc = new String[2];
                } else {
                    telSrc = tels;
                }
                telSrc[0] = tels[0].substring(0, 5);
                if (tels[0].length() > 9) {
                    telSrc[1] = tels[0].substring(5, 9) + "-"
                            + tels[0].substring(9, tels[0].length());
                } else {
                    telSrc[1] = tels[0].substring(5, tels[0].length())
                            + "-";
                }
                tels = telSrc;
            }
    
            tel1 = tels[0];
            switch (tels.length) {
            case 0:
            case 1:
                break;
            case 2:
                tel2 = tels[1];
                break;
            case 3:
                tel2 = tels[1] + "-" + tels[2];
                break;
            default:
                tel2 = tels[1] + "-" + tels[2];
                break;
            }
        }
        
        VRMap ret = new VRHashMap();
        ret.put("CHART_NO", cutString(strPtid, 20));
        ret.put("PATIENT_NM", cutString(row.get("WholeName"), 15));
        ret.put("PATIENT_KN", ACKanaConvert.toHiragana(cutString(row.get("WholeName_inKana"), 30)));
        ret.put("SEX", ACCastUtilities.toInteger(row.get("Sex"), 0));
        ret.put("BIRTHDAY", birthday);
        ret.put("POST_CD", zipCode);
        ret.put("ADDRESS", cutString(address, 50));
        ret.put("TEL1", tel1);
        ret.put("TEL2", tel2);
        ret.put("PTID", new Integer(ptid));
        ret.put("HOSPNUM", new Integer(1));
        
        return ret;
    }
    
    private String cutString(Object value, int length) throws Exception {
        
        if (value == null) {
            return "";
        }
        
        String ret = toStringValue(value);
        
        if (ret.length() > length) {
            // 文字制限
            ret = ret.substring(0, length);
        }
        return ret;
    }
    
    private String toNotTelCharReplace(Object obj) throws ParseException {
        String src = toStringValue(obj);
        StringBuffer sb = new StringBuffer();
        int end = src.length();
        
        for (int i = 0; i < end; i++) {
            char c = src.charAt(i);
            if ((c == '-') || ((c >= '0') && (c <= '9'))) {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    

    private String toStringValue(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof String) {
            return (String) obj;
        }
        return String.valueOf(obj);
    }

}
