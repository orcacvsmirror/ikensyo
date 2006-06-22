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
package jp.or.med.orca.ikensho.sql;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.sql.ACPassiveTask;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

public class IkenshoPassiveCheck {
    protected HashMap passiveSpace = new HashMap();
    protected ArrayList passiveTasks = new ArrayList();

    /**
     * コンストラクタです。
     */
    public IkenshoPassiveCheck() {
    }

    /**
     * パッシブチェック用の検索結果を退避します。
     * 
     * @param key 比較キー
     * @param data 検索結果
     */
    public void reservedPassive(ACPassiveKey key, VRArrayList data) {
        VRArrayList clone = new VRArrayList();
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
     * パッシブチェック用の検索結果を初期化します。
     */
    public void clearReservedPassive() {
        passiveSpace.clear();
    }

    /**
     * パッシブチェックタスクを初期化します。
     */
    public void clearPassiveTask() {
        passiveTasks.clear();
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
     * 追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
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

    /**
     * パッシブチェックを実行します。
     * 
     * @throws Exception 処理例外
     * @return boolean チェック成功
     */
    public IkenshoFirebirdDBManager getPassiveCheckedDBManager()
            throws Exception {
        IkenshoFirebirdDBManager dbm = null;

        if (passiveTasks.size() == 0) {
            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();
            return dbm;
        }

        Iterator it = passiveTasks.iterator();
        while (it.hasNext()) {
            ACPassiveTask task = (ACPassiveTask) it.next();
            Object passiveDataObj = matchPassiveKey(task.getKey());
            if (!(passiveDataObj instanceof VRArrayList)) {
                throw new IllegalArgumentException("事前に検索結果を退避していないタスク["
                        + task.toString() + "]がパッシブチェック対象に指定されました。");
            }
            VRArrayList passiveArray = (VRArrayList) passiveDataObj;
            if (passiveArray.getDataSize() <= task.getTargetRow()) {
                throw new IllegalArgumentException("退避済みデータ数を超える行数指定がなされたタスク["
                        + task.toString() + "]がパッシブチェック対象に指定されました。");
            }
            passiveDataObj = passiveArray.getData(task.getTargetRow());
            if (!(passiveDataObj instanceof VRMap)) {
                throw new IllegalArgumentException(
                        "退避済みデータがVRArrayList<VRHashMap>形式でないタスク["
                                + task.toString() + "]がパッシブチェック対象に指定されました。");
            }
            VRMap passiveMap = (VRMap) passiveDataObj;

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" " + task.getKey().getServerPassiveTimeField());
            sb.append(" FROM ");
            sb.append(task.getKey().getTable());
            sb.append(" WHERE");
            int size = task.getKey().getFields().length;
            for (int i = 0; i < size; i++) {
                String field = task.getKey().getFields()[i];
                Object data = VRBindPathParser.get(field, passiveMap);
                if (i == 0) {
                    sb.append(" (");
                } else {
                    sb.append(" AND (");
                }
                sb.append(field);
                sb.append("=");
                Format format = task.getKey().getFormats()[i];
                if (format == null) {
                    sb.append(data);
                } else {
                    sb.append(format.format(data));
                }
                sb.append(")");
            }
            String sql = sb.toString();

            dbm = new IkenshoFirebirdDBManager();
            try {
                dbm.beginTransaction();
                VRArrayList result = (VRArrayList) dbm.executeQuery(sql);

                switch (task.getCommandType()) {
                case ACPassiveTask.PASSIVE_DELETE:
                case ACPassiveTask.PASSIVE_UPDATE: {
                    // 削除・更新タスク→更新チェック
                    if (result.getDataSize() == 0) {
                        return null;
                    }
                    Object old = VRBindPathParser.get(task.getKey()
                            .getClientPassiveTimeField(), passiveMap);
                    Object now = VRBindPathParser.get(task.getKey()
                            .getServerPassiveTimeField(), (VRMap) result
                            .getData());
                    if (!old.equals(now)) {
                        return null;
                    }
                    break;
                }
                case ACPassiveTask.PASSIVE_INSERT:

                    // 追加タスク→不在チェック
                    if (result.getDataSize() != 0) {
                        return null;
                    }
                default:
                    throw new IllegalArgumentException("非対応のタスク["
                            + task.toString() + "]がパッシブチェック対象に指定されました。");
                }
            } catch (Exception ex) {
                dbm.rollbackTransaction();
                return null;
            }
        }

        return dbm;
    }

}
