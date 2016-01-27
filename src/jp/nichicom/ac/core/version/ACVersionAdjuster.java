package jp.nichicom.ac.core.version;

import java.text.Format;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroDoubleFormat;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.io.VRCSVFile;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * バージョン情報の差異を補正するクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/28
 */
public class ACVersionAdjuster {
    /**
     * SQL文におけるパラメタへの置き換えタグです。
     */
    public static final String TAG_OF_SQL_PARAMETER = "\\?";

//    /**
//     * バージョン情報における区切り文字です。
//     */
//    public final String TAG_OF_VERSION_SEPARATER = "\\.";

    /**
     * SQL文の発行をあらわす更新処理タイプです。
     */
    public static final String TASK_TYPE_OF_SQL = "sql";

    /**
     * クエリ件数をあらわす比較対象タイプです。
     */
    public static final String TARGET_TYPE_OF_QUERY_COUNT = "query_count";

    /**
     * クエリの成功可否をあらわす比較対象タイプです。
     */
    public static final String TARGET_TYPE_OF_QUERY_SUCCESS = "query_success";

    /**
     * コンストラクタです。
     */
    public ACVersionAdjuster() {
        super();
    }

    /**
     * バージョン差異を補正します。
     * 
     * @param dbm DBマネージャ
     * @param taskFile 差異補正定義
     * @param listener 差異補正リスナ
     * @param splash プログレススプラッシュ
     * @throws Exception 処理例外
     */
    public void adjustment(ACDBManager dbm, ACPropertyXML taskFile,
            ACVersionAdjustmentListener listener) throws Exception {
        // バージョン補正タスクを全走査
        int updateCount = taskFile.size();
        int updateIndex = 0;
        Iterator rootIt = taskFile.entrySet().iterator();
        while (rootIt.hasNext()) {
            updateIndex++;
            Map.Entry rootEnt = (Map.Entry) rootIt.next();
            VRMap check = (VRMap) rootEnt.getValue();
            Object obj = VRBindPathParser.get("compares", check);
            if (obj instanceof Map) {
                Map updateHash = new HashMap();
                boolean match = true;
                Iterator it = ((Map) obj).values().iterator();
                while (it.hasNext()) {
                    // 比較タスクを走査
                    VRMap comapare = (VRMap) it.next();
                    String target = String.valueOf(VRBindPathParser.get("target", comapare));
                    String operaiton = String.valueOf(VRBindPathParser.get("operaiton",
                            comapare));
                    String before = String.valueOf(VRBindPathParser.get("before", comapare));

                    if(TARGET_TYPE_OF_QUERY_COUNT.equalsIgnoreCase(target)){
                        //クエリ結果の件数で比較
                        VRList result =dbm.executeQuery(String.valueOf(VRBindPathParser.get("process", comapare)));
                        String size;
                        if(result==null){
                            size = "0";
                        }else{
                            size = String.valueOf(result.size()); 
                        }
                        if (!ACTextUtilities.compareVersionText(size, operaiton, before)) {
                            match = false;
                            break;
                        }
                    }else if(TARGET_TYPE_OF_QUERY_SUCCESS.equalsIgnoreCase(target)){
                        //クエリの成功可否で比較
                        if("true".equalsIgnoreCase(before)){
                            //成功した場合に実行
                            before = "1.0";
                        }else{
                            //失敗した場合に実行
                            before = "0.0";
                        }
                        String now = "1.0";
                        try{
                            dbm.executeQuery(String.valueOf(VRBindPathParser.get("process", comapare)));
                            //クエリ成功
                        }catch(Exception ex){
                            //クエリ失敗
                            now = "0.0";
                        }
                        if (!ACTextUtilities.compareVersionText(now, operaiton, before)) {
                            match = false;
                            break;
                        }
                    }else{
                        // 現在のバージョンと更新対象のバージョンを比較
                        if (!ACTextUtilities.compareVersionText(listener.getVersion(target), operaiton, before)) {
                            match = false;
                            break;
                        }
                        Object after = VRBindPathParser.get("after", comapare);
                        if (after != null) {
                            // バージョン情報の書替予約
                            updateHash.put(target, String.valueOf(after));
                        }
                    }

                }
                if (match) {
                    // 更新すべき場合
                    dbm.beginTransaction();
                    try {
                        Map defineMap = new TreeMap();
                        obj = VRBindPathParser.get("define", check);
                        if (obj instanceof Map) {
                            // 変数定義
                            it = ((Map) obj).entrySet().iterator();
                            while (it.hasNext()) {
                                // 定義タスクを走査
                                Map.Entry taskEnt = (Map.Entry) it.next();
                                VRMap task = (VRMap) taskEnt.getValue();
                                try {
                                    parseDefine(dbm, defineMap, String
                                            .valueOf(taskEnt.getKey()),
                                            VRBindPathParser.get("type", task),
                                            VRBindPathParser.get("process",
                                                    task));
                                } catch (Exception ex) {
                                    if (!"true".equalsIgnoreCase(String
                                            .valueOf(VRBindPathParser.get(
                                                    "ignoreError", task)))) {
                                        // エラーを無視するオプションが付加されていなければ例外
                                        throw ex;
                                    }
                                }
                            }
                        }                        
                        
                        
                        obj = VRBindPathParser.get("tasks", check);
                        if (obj instanceof Map) {
                            String updateName = "[" + updateIndex + "/"
                                    + updateCount + "]"
                                    + String.valueOf(rootEnt.getKey()) + "-";
                            int taskCount = ((Map) obj).size();
                            int taskIndex = 0;
                            it = ((Map) obj).entrySet().iterator();
                            while (it.hasNext()) {
                                // 更新タスクを走査
                                taskIndex++;
                                Map.Entry taskEnt = (Map.Entry) it.next();
                                String taskName = updateName + "[" + taskIndex
                                        + "/" + taskCount + "]"
                                        + String.valueOf(taskEnt.getKey())
                                        + "-";
                                VRMap task = (VRMap) taskEnt.getValue();
                                try {
                                    adjustment(dbm, listener, defineMap, taskName,
                                            VRBindPathParser.get("type", task),
                                            VRBindPathParser.get("process",
                                                    task), VRBindPathParser
                                                    .get("parameter", task));
                                } catch (Exception ex) {
                                    if (!"true".equalsIgnoreCase(String
                                            .valueOf(VRBindPathParser.get(
                                                    "ignoreError", task)))) {
                                        // エラーを無視するオプションが付加されていなければ例外
                                        throw ex;
                                    }
                                }
                            }
                        }
                        // バージョン情報の書替
                        boolean canCommit = true;
                        it = updateHash.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry ent = (Map.Entry) it.next();
                            if (!listener.setVersion(String.valueOf(ent
                                    .getKey()), String.valueOf(ent.getValue()))) {
                                // 新しいバージョンの設定を拒否された場合
                                canCommit = false;
                                break;
                            }
                        }
                        if (canCommit) {
                            dbm.commitTransaction();
                        } else {
                            dbm.rollbackTransaction();
                        }
                    } catch (Exception ex) {
                        dbm.rollbackTransaction();
                        throw ex;
                    }
                }
            }
        }
        progress(listener, "バージョン補正完了...", -1,-1);
    }
    /**
     * 変数定義を解析します。
     * @param dbm DBマネージャ
     * @param defineMap 変数定義先
     * @param defineName 定義変数名
     * @param processType 処理タイプ 
     * @param process 処理
     * @throws Exception 処理例外
     */
    protected void parseDefine(ACDBManager dbm,Map defineMap, String defineName,
            Object processType, Object process)
    throws Exception {
        String typeText = String.valueOf(processType);
        if (TASK_TYPE_OF_SQL.equalsIgnoreCase(typeText)) {
            // SQL文
            String processText = String.valueOf(process);
            VRList list=dbm.executeQuery(processText);
            if(!list.isEmpty()){
                Object row=list.getData();
                if(row instanceof Map){
                    Object[] vals = ((Map)row).values().toArray();
                    if(vals.length==1){
                        //TODO 現在はクエリの戻りとして1フィールドに限定して対応
                        defineMap.put(defineName, vals[0]);
                    }
                }
            }
        }        
    }

    /**
     * リスナのスプラッシュに進捗を報告します。
     * 
     * @param listener リスナ
     * @param taskName タスク名
     * @param index 処理番号
     * @param count 処理個数
     */
    protected void progress(ACVersionAdjustmentListener listener,
            String taskName, int index, int count) {
        if ((listener != null)
                && (listener.getProgressSplash() instanceof ACSplash)) {
            ACSplash splash = (ACSplash) listener.getProgressSplash();
            if(count>0){
                taskName = taskName  + "[" + index + "/" + count + "]";
            }
            splash.setMessage(taskName);
        }
    }

    /**
     * 更新タスクを処理します。
     * 
     * @param dbm DBマネージャ
     * @param listener 進捗リスナ
     * @param defineMap 変数定義
     * @param processType 処理タイプ
     * @param process 更新処理
     * @param paramterFile 更新パラメタファイルパス
     * @throws Exception 処理例外
     */
    public void adjustment(ACDBManager dbm,
            ACVersionAdjustmentListener listener, 
            Map defineMap,
            String taskName,
            Object processType, Object process, Object paramterFile)
            throws Exception {
        String typeText = String.valueOf(processType);
        if (TASK_TYPE_OF_SQL.equalsIgnoreCase(typeText)) {
            // SQL文
            String processText = String.valueOf(process);
            
            //定義変数を置換
            Iterator it = defineMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry ent=(Map.Entry)it.next();
                processText = processText.replaceAll(String.valueOf(ent.getKey()), String.valueOf(ent.getValue()));
            }
            
            if ((paramterFile == null) || "".equals(paramterFile)) {
                // パラメタファイル未使用
                progress(listener, taskName, 1, 1);
                dbm.executeUpdate(processText);
            } else {
                // パラメタファイル使用
                VRCSVFile f = new VRCSVFile(String.valueOf(paramterFile));
                f.read(true);
                if (!f.isEmpty()) {
                    int columns = f.getColumnCount();
                    Format[] formats = new Format[columns];
                    for (int i = 0; i < columns; i++) {
                        // カラムヘッダからスキーマを解析
                        String fmt = f.getColumnName(i);
                        if ("VARCHAR".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_STRING;
                        } else if ("INTEGER".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_NULL_TO_ZERO_INTEGER;
                        } else if ("TIMESTAMP".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_FULL_YMD_HMS;
                        } else if ("DATE".equalsIgnoreCase(fmt)) {
                            formats[i] = ACConstants.FORMAT_SQL_FULL_YMD;
                        } else if ("DOUBLE PRECISION".equalsIgnoreCase(fmt)) {
                            formats[i] = ACSQLSafeNullToZeroDoubleFormat
                                    .getInstance();
                        }
                    }
                    String[] sqls = processText.split(TAG_OF_SQL_PARAMETER);
                    int end = sqls.length;
                    int rows = f.getRowCount();
                    for (int r = 0; r < rows; r++) {
                        // 行単位に走査
                        StringBuffer sb = new StringBuffer();
                        for (int c = 0; c < columns; c++) {
                            Object val = f.getValueAt(r, c);
                            Format fmt = formats[c];
                            if (fmt != null) {
                                // 割り当てられたフォーマットがあれば書式化
                                val = fmt.format(val);
                            }
                            sb.append(sqls[c]);
                            sb.append(val);
                        }
                        for (int i = columns; i < end; i++) {
                            sb.append(sqls[i]);
                        }
                        if (sb.length() > 0) {
                            // SQL文発行
                            progress(listener, taskName, r, rows);
                            dbm.executeUpdate(sb.toString());
                        }
                    }
                }
            }
        }
    }

    /**
     * バージョン差異を補正します。
     * 
     * @param dbm DBマネージャ
     * @param taskFile 差異補正定義ファイルパス
     * @param listener 差異補正リスナ
     * @throws Exception 処理例外
     */
    public void adjustment(ACDBManager dbm, String taskFile,
            ACVersionAdjustmentListener listener) throws Exception {
        ACPropertyXML verAdj = new ACPropertyXML(taskFile);
        if (verAdj.canRead()) {
            verAdj.read();
            adjustment(dbm, verAdj, listener);
        }
    }

//    /**
//     * ドット(.)区切りのバージョン情報を比較し、更新が必要であるかを返します。
//     * 
//     * @param now 現在のバージョン情報
//     * @param operation 比較演算子
//     * @param value 比較対象のバージョン情報
//     * @return 更新が必要であるか
//     */
//    protected boolean mustUpdate(String now, String operation, String value) {
//        if ((now == null) || "".equals(now) || (value == null)
//                || "".equals(value)) {
//            // NULLチェック
//            return false;
//        }
//
//        String[] nows = now.split(TAG_OF_VERSION_SEPARATER);
//        String[] values = value.split(TAG_OF_VERSION_SEPARATER);
//        int nowLen = nows.length;
//        int valLen = values.length;
//        int maxLen = Math.max(nowLen, valLen);
//        if (maxLen == 0) {
//            return false;
//        }
//
//        // マイナーバージョンまでそろえる
//        int[] nowVers = new int[maxLen];
//        int[] valVers = new int[maxLen];
//        for (int i = 0; i < nowLen; i++) {
//            nowVers[i] = Integer.parseInt(nows[i]);
//        }
//        for (int i = 0; i < valLen; i++) {
//            valVers[i] = Integer.parseInt(values[i]);
//        }
//
//        // 比較開始
//        // 完全一致か検査
//        boolean equal = true;
//        for (int i = 0; i < maxLen; i++) {
//            if (nowVers[i] != valVers[i]) {
//                equal = false;
//                break;
//            }
//        }
//
//        if (operation.indexOf("!") >= 0) {
//            // 一致しなければ更新対象
//            if (!equal) {
//                return true;
//            }
//        } else {
//            if (operation.indexOf("=") >= 0) {
//                // 一致したら更新対象
//                if (equal) {
//                    return true;
//                }
//            } else {
//                // 一致は更新対象でないのに一致していた場合は対象外
//                if (equal) {
//                    return false;
//                }
//            }
//            if (operation.indexOf("<") >= 0) {
//                // 現在のバージョンのほうが低ければ更新対象
//                boolean match = true;
//                for (int i = 0; i < maxLen; i++) {
//                    if (nowVers[i] < valVers[i]) {
//                        break;
//                    }
//                    if (nowVers[i] > valVers[i]) {
//                        match = false;
//                        break;
//                    }
//                }
//                if (match) {
//                    return true;
//                }
//            }
//            if (operation.indexOf(">") >= 0) {
//                // 現在のバージョンのほうが高ければ更新対象
//                boolean match = true;
//                for (int i = 0; i < maxLen; i++) {
//                    if (nowVers[i] > valVers[i]) {
//                        break;
//                    }
//                    if (nowVers[i] < valVers[i]) {
//                        match = false;
//                        break;
//                    }
//                }
//                if (match) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }

}
