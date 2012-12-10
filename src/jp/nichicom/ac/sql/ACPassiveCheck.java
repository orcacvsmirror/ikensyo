package jp.nichicom.ac.sql;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * パッシブチェッククラスです。
 * <p>
 * パッシブチェックとは、ある時点で退避しておいたデータと現在のDBの内容を比較して
 * 内容に変化があった場合は「他者が書き込んでいた」と見なし、データの整合性保持のために処理を中止させることを目的して行なうチェックです。
 * </p>
 * <p>
 * 利用手順の概要は以下の通りです。<br />
 * <code>
 * 【SELECT発行後】<br />
 * reservedPassiveメソッドで比較用のデータを退避。<br />
 * このとき、退避した比較データを表すパッシブキーも登録します。<br />
 * 【UPDATE前】<br />
 * clearPassiveTaskメソッドで比較処理をクリア。<br />
 * チェックするSQLの種類に応じてaddPassive～Taskメソッドを実行。<br />
 * 実行するパッシブタスクには、対応する比較データを表すパッシブキーもあわせて設定します。<br />
 * すべてのパッシブタスクを登録したら、トランザクションを開始したDBManagerを引数にpassiveメソッドを実行。<br />
 * ・パッシブチェックに成功すれば、trueが返ります。<br />
 * ・パッシブチェックに失敗すれば、falseが返ります。
 * </code>
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACPassiveKey
 * @see ACPassiveTask
 */

public class ACPassiveCheck {

    protected HashMap passiveSpace = new HashMap();
    protected ArrayList passiveTasks = new ArrayList();

    /**
     * コンストラクタです。
     */
    public ACPassiveCheck() {
    }

    /**
     * 全件削除パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     */
    public void addPassiveDeleteTask(ACPassiveKey key) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_DELETE, key));
    }

    /**
     * 削除パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     */
    public void addPassiveDeleteTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_DELETE, key,
                row));
    }

    /**
     * 削除パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param targetValue 対象値
     */
    public void addPassiveDeleteTask(ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_DELETE, key,
                targetBindPath, targetValue));
    }

    /**
     * 全件追加パッシブチェックタスクを追加します。
     * 
     * @deprecated #addPassiveInsertTask(ACPassiveKey key, VRList
     *             reserveData)を使用してください。
     * @param key 比較キー
     */
    public void addPassiveInsertTask(ACPassiveKey key) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key));
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @deprecated #addPassiveInsertTask(ACPassiveKey key, int row, VRList
     *             reserveData)を使用してください。
     * @param key 比較キー
     * @param row 対象行番号
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     * @param reserveData 追加しようとしているデータ
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row,
            VRList reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     * @param reserveData 追加しようとしているデータ
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row,
            VRMap reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @deprecated #addPassiveInsertTask(ACPassiveKey key, String
     *             targetBindPath, Object targetValue, VRList
     *             reserveData)を使用してください。
     * @param key 比較キー
     * @param targetValue 対象値
     */
    public void addPassiveInsertTask(ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                targetBindPath, targetValue));
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param targetValue 対象値
     * @param reserveData 追加しようとしているデータ
     */
    public void addPassiveInsertTask(ACPassiveKey key, String targetBindPath,
            Object targetValue, VRList reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                targetBindPath, targetValue));
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param targetValue 対象値
     * @param reserveData 追加しようとしているデータ
     */
    public void addPassiveInsertTask(ACPassiveKey key, String targetBindPath,
            Object targetValue, VRMap reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                targetBindPath, targetValue));
    }

    /**
     * 全件追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param reserveData 追加しようとしているデータ
     */
    public void addPassiveInsertTask(ACPassiveKey key, VRList reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key));
    }

    /**
     * 全件追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param reserveData 追加しようとしているデータ
     */
    public void addPassiveInsertTask(ACPassiveKey key, VRMap reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key));
    }

    /**
     * 全件更新パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     */
    public void addPassiveUpdateTask(ACPassiveKey key) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_UPDATE, key));
    }

    /**
     * 更新パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     */
    public void addPassiveUpdateTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_UPDATE, key,
                row));
    }

    /**
     * 更新パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param targetValue 対象値
     */
    public void addPassiveUpdateTask(ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_UPDATE, key,
                targetBindPath, targetValue));
    }

    /**
     * パッシブチェックタスクを初期化します。
     */
    public void clearPassiveTask() {
        passiveTasks.clear();
    }

    /**
     * パッシブチェック用の検索結果を初期化します。
     */
    public void clearReservedPassive() {
        passiveSpace.clear();
    }

    // /**
    // * パッシブチェックを実行します。
    // * <p>
    // * 成功した場合はトランザクションを開始したDBManagerを返します。
    // * </p>
    // * <p>
    // * 失敗した場合はnullを返します。
    // * </p>
    // *
    // * @throws Exception 処理例外
    // * @return トランザクションを開始したDBManager
    // * @deprecated 推奨されません。passiveCheck(NCDBManagerable dbm)を使用してください。
    // */
    // public NCDBManagerable getPassiveCheckedDBManager() throws Exception {
    // NCDBManagerable dbm = getTransactionBeginedDBManager();
    // if (dbm != null) {
    // if (passiveCheck(dbm)) {
    // return dbm;
    // }
    // dbm.commitTransaction();
    // }
    // return null;
    // }

    /**
     * 退避データから、指定の値を含む行番号を返します。
     * <p>
     * 該当しない場合は-1を返します。
     * </p>
     * 
     * @param key 比較キー
     * @param targetBindPath 対象行を求めるための値列名
     * @param targetValue 対象行を求めるための値
     * @return 該当行番号
     * @throws Exception 処理例外
     */
    public int getResevedRowIndex(ACPassiveKey key, String targetBindPath,
            Object targetValue) throws Exception {
        Object passiveDataObj = matchPassiveKey(key);
        if (!(passiveDataObj instanceof VRList)) {
            return -1;
        }
        return getResevedRowIndex((VRList) passiveDataObj, targetBindPath,
                targetValue);
    }

    /**
     * パッシブチェックを実行します。
     * <p>
     * 成功した場合はtrueを、失敗した場合はfalseを返します。
     * </p>
     * 
     * @param dbm トランザクションを開始したDBManager
     * @throws Exception 処理例外
     * @return 成功したか
     */
    public boolean passiveCheck(ACDBManager dbm) throws Exception {
        if (dbm == null) {
            // Nullチェック
            return false;
        }

        if (passiveTasks.size() == 0) {
            // パッシブタスクが無ければ無条件で許可
            return true;
        }

        Iterator it = passiveTasks.iterator();
        while (it.hasNext()) {
            // 依頼されたパッシブタスク全てを検査する
            ACPassiveTask task = (ACPassiveTask) it.next();
            ACPassiveKey taskKey = task.getKey();
            Object passiveDataObj = matchPassiveKey(taskKey);
            if (!(passiveDataObj instanceof VRList)) {
                throw new IllegalArgumentException("事前に検索結果を退避していないタスク["
                        + task.toString() + "]がパッシブチェック対象に指定されました。");
            }

            VRList passiveArray = (VRList) passiveDataObj;

            VRMap[] passiveMaps;
            switch (task.getCheckTargetType()) {
            case ACPassiveTask.CHECK_TARGET_ALL: {
                // 全件チェック用にデータを検査する
                int end = passiveArray.size();
                passiveMaps = new VRHashMap[end];
                for (int i = 0; i < end; i++) {
                    passiveDataObj = passiveArray.getData(i);
                    if (!(passiveDataObj instanceof VRMap)) {
                        throw new IllegalArgumentException(
                                "退避済みデータがVRList<VRMap>形式でないタスク["
                                        + task.toString()
                                        + "]がパッシブチェック対象に指定されました。");
                    }
                    passiveMaps[i] = (VRMap) passiveDataObj;
                }
                break;
            }
            case ACPassiveTask.CHECK_TARGET_ROW: {
                // 行単件チェック用にデータを抽出する
                passiveMaps = new VRHashMap[1];
                int row = task.getTargetRow();

                if (passiveArray.getDataSize() <= row) {
                    throw new IllegalArgumentException(
                            "退避済みデータ数を超える行数指定がなされたタスク[" + task.toString()
                                    + "]がパッシブチェック対象に指定されました。");
                }
                passiveDataObj = passiveArray.getData(row);
                if (!(passiveDataObj instanceof VRMap)) {
                    throw new IllegalArgumentException(
                            "退避済みデータがVRList<VRMap>形式でないタスク[" + task.toString()
                                    + "]がパッシブチェック対象に指定されました。");
                }
                passiveMaps[0] = (VRMap) passiveDataObj;
                break;
            }
            case ACPassiveTask.CHECK_TARGET_VALUE: {
                // データ単件チェック用にデータを抽出する
                passiveMaps = new VRHashMap[1];
                int row = getResevedRowIndex(passiveArray, task
                        .getTargetBindPath(), task.getTargetValue());
                if (row < 0) {
                    throw new IllegalArgumentException("タスク[" + task.toString()
                            + "]に該当するデータが退避されていません。");
                }
                if (passiveArray.getDataSize() <= row) {
                    throw new IllegalArgumentException(
                            "退避済みデータ数を超える行数指定がなされたタスク[" + task.toString()
                                    + "]がパッシブチェック対象に指定されました。");
                }
                passiveDataObj = passiveArray.getData(row);
                if (!(passiveDataObj instanceof VRMap)) {
                    throw new IllegalArgumentException(
                            "退避済みデータがVRList<VRMap>形式でないタスク[" + task.toString()
                                    + "]がパッシブチェック対象に指定されました。");
                }
                passiveMaps[0] = (VRMap) passiveDataObj;
                break;
            }
            default:
                throw new IllegalArgumentException("非対応のタスク[" + task.toString()
                        + "]がパッシブチェック対象に指定されました。");

            }

            try {
                int end = passiveMaps.length;
                for (int i = 0; i < end; i++) {
                    // パッシブチェック用のSQL文を構築する
                    VRMap passiveMap = passiveMaps[i];

                    StringBuffer sb = new StringBuffer();
                    sb.append("SELECT");
                    sb.append(" " + taskKey.getServerPassiveTimeField());
                    sb.append(" FROM ");
                    sb.append(taskKey.getTable());
                    sb.append(" WHERE");

                    Format[] formats = taskKey.getFormats();
                    int size = taskKey.getFields().length;
                    for (int j = 0; j < size; j++) {
                        String field = taskKey.getFields()[j];
                        Object data = VRBindPathParser.get(field, passiveMap);
                        if (j == 0) {
                            sb.append(" (");
                        } else {
                            sb.append(" AND (");
                        }
                        sb.append(field);
                        sb.append("=");
                        if (formats != null) {
                            // フォーマット配列のnullチェック
                            Format format = formats[j];
                            if (format != null) {
                                // フォーマットのnullチェック
                                sb.append(format.format(data));
                            } else {
                                // フォーマットをかけずに出力
                                sb.append(data);
                            }
                        } else {
                            // フォーマットをかけずに出力
                            sb.append(data);
                        }
                        sb.append(")");
                    }

                    String sql = sb.toString();

                    VRList result = dbm.executeQuery(sql);

                    switch (task.getCommandType()) {
                    case ACPassiveTask.PASSIVE_DELETE:
                    case ACPassiveTask.PASSIVE_UPDATE: {
                        // 削除・更新タスク→更新チェック
                        if (result.getDataSize() == 0) {
                            return false;
                        }
                        Object old = VRBindPathParser.get(taskKey
                                .getClientPassiveTimeField(), passiveMap);
                        Object now = VRBindPathParser.get(taskKey
                                .getServerPassiveTimeField(), (VRMap) result
                                .getData());
                        if (!old.equals(now)) {
                            return false;
                        }
                        break;
                    }
                    case ACPassiveTask.PASSIVE_INSERT:

                        // 追加タスク→不在チェック
                        if (result.getDataSize() != 0) {
                            return false;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("非対応のタスク["
                                + task.toString() + "]がパッシブチェック対象に指定されました。");
                    }
                }
            } catch (Exception ex) {
                if (dbm != null) {
                    dbm.rollbackTransaction();
                    // dbm.finalize();
                }
                return false;
            }

        }

        return true;
    }

    /**
     * パッシブチェック用の検索結果を退避します。
     * 
     * @param key 比較キー
     * @param data 検索結果
     */
    public void reservedPassive(ACPassiveKey key, VRList data) {
        VRList clone = new VRArrayList();
        Iterator it = data.iterator();
        while (it.hasNext()) {
            VRMap srcRow = (VRMap) it.next();
            VRMap newRow = new VRHashMap();
            Iterator mit = srcRow.entrySet().iterator();
            while (mit.hasNext()) {
                Entry entry = (Entry) mit.next();
                Object obj = entry.getValue();
                newRow.setData(entry.getKey(), obj);
            }
            clone.addData(newRow);
        }
        passiveSpace.put(key, clone);
    }

    /**
     * 1レコード分のパッシブチェック用の検索結果を退避します。
     * 
     * @param key 比較キー
     * @param data 検索結果
     */
    public void reservedPassive(ACPassiveKey key, VRMap data) {
        VRList list = new VRArrayList();
        list.add(data);
        reservedPassive(key, list);
    }

    // /**
    // * トランザクションを開始したDBManagerを返します。
    // *
    // * @return DBManager
    // * @throws Exception 処理例外
    // * @deprecated システム規定のDBManagerに限定されるため、推奨されません。
    // */
    // protected NCDBManagerable getTransactionBeginedDBManager() throws
    // Exception {
    // NCFrameEventProcessable processer = NCFrame.getInstance()
    // .getFrameEventProcesser();
    // if (processer == null) {
    // return null;
    // }
    // NCDBManagerable dbm = processer.getDBManager();
    // if (dbm == null) {
    // return null;
    // }
    // dbm.beginTransaction();
    // return dbm;
    // }

    /**
     * 退避データから、指定の値を含む行番号を返します。
     * <p>
     * 該当しない場合は-1を返します。
     * </p>
     * 
     * @param list 比較元
     * @param targetBindPath 対象行を求めるための値列名
     * @param targetValue 対象行を求めるための値
     * @return 該当行番号
     * @throws Exception 処理例外
     */
    protected int getResevedRowIndex(VRList list, String targetBindPath,
            Object targetValue) throws Exception {
        // NCCommon.getInstance().getMa
        int i = 0;
        Iterator it = list.iterator();
        if (targetValue == null) {
            while (it.hasNext()) {
                VRMap row = (VRMap) it.next();
                if (VRBindPathParser.get(targetBindPath, row) == null) {
                    return i;
                }
                i++;
            }

        } else {
            while (it.hasNext()) {
                VRMap row = (VRMap) it.next();
                if (targetValue.equals(VRBindPathParser
                        .get(targetBindPath, row))) {
                    return i;
                }
                i++;
            }
        }
        return -1;

    }

    /**
     * パッシブキーマップから該当する検索結果オブジェクトを返します。
     * 
     * @param key 比較キー
     * @return 検索結果オブジェクト
     */
    protected Object matchPassiveKey(ACPassiveKey key) {
        Iterator it = passiveSpace.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Object o = entry.getKey();
            if (o instanceof ACPassiveKey) {
                ACPassiveKey targetKey = (ACPassiveKey) o;
                if (targetKey.equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

}