package jp.nichicom.ac.util;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;

/**
 * スナップショットクラスです。
 * <p>
 * スナップショットとは、あるタイミングの画面状態を退避しておき、退避したデータと比較して 現在の画面状態は更新されているかを調べる処理です。
 * </p>
 * <p>
 * 比較・退避対象は、以下の条件をすべて満たしているコンポーネントのデータに限定されます。<br />
 * ・setRootContainerメソッドで指定したコンテナ以下に属するコンポーネント。<br />
 * ・スナップショットの除外対象に指定したコンテナ以下には属さないコンポーネント。<br />
 * ・バインドパスを指定しているコンポーネント。
 * </p>
 * <p>
 * 利用手順の概要は以下の通りです。<br />
 * <code>
 * 【初期設定完了後】<br />
 * setRootContainerメソッドで退避対象のコンテナを設定。<br />
 * このとき、除外したいコンテナがある場合はsetExclusionsメソッドで除外対象を指定します。<br />
 * snapshotメソッドで現在の状態を退避。<br />
 * 【強制終了前】<br />
 * isModifiedメソッドで変化があったかを確認。<br />
 * ・変化があった場合はtrueが返るため、保存して閉じるかの確認を行なうなど、適宜利用してください。
 * </code>
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACSnapshot {

    private List exclusions;

    private Map memento;

    private Container rootContainer;

    /**
     * コンストラクタです。
     */
    public ACSnapshot() {
    }

    /**
     * 初期化します。
     */
    public void clear() {
        exclusions = null;
        rootContainer = null;
        memento = null;
    }

    /**
     * スナップショットの除外対象を返します。
     * 
     * @return スナップショットの除外対象
     */
    public List getExclusions() {
        return exclusions;
    }

    /**
     * スナップショット比較元の値を返します。
     * 
     * @return スナップショット比較元の値
     */
    protected Map getMemento() {
        return memento;
    }

    /**
     * スナップショット対象の基底パネルを返します。
     * 
     * @return スナップショット対象の基底パネル
     */
    public Container getRootContainer() {
        return rootContainer;
    }

    /**
     * 過去のスナップショットと現在の値を比較して、変更があるかを返します。
     * 
     * @return 変更があるか
     * @throws ParseException
     */
    public boolean isModified() throws ParseException {
        if (memento == null) {
            // 未スナップショット
            return true;
        }
        if (rootContainer == null) {
            // コンテナ未設定
            return true;
        }

        HashMap newMement = new HashMap();
        snapshotComponent(rootContainer, newMement);

        // 不要なキーを除外する
        removeExclusionKeys(newMement, createExclutionKeys(getExclusions()));

        return isModifiedEnum(newMement, memento);
    }

    /**
     * スナップショットを適用します。
     * 
     * @throws ParseException 解析例外
     */
    public void restore() throws ParseException {
        if (rootContainer == null) {
            return;
        }
        restoreComponent(rootContainer, memento);
    }

    /**
     * スナップショットの除外対象を設定します。
     * <p>
     * 除外したいコンポーネントをリスト化して指定します。
     * </p>
     * 
     * @param exclusions スナップショットの除外対象
     */
    public void setExclusions(List exclusions) {
        this.exclusions = exclusions;
    }

    /**
     * スナップショットの除外対象を設定します。
     * <p>
     * 除外したいコンポーネントをリスト化して指定します。
     * </p>
     * 
     * @param exclusions スナップショットの除外対象
     */
    public void setExclusions(Component[] exclusions) {
        setExclusions(Arrays.asList(exclusions));
    }

    /**
     * スナップショット比較元の値を返します。
     * 
     * @param memento スナップショット比較元の値
     */
    protected void setMemento(Map memento) {
        this.memento = memento;
    }

    /**
     * スナップショット対象の基底パネルを設定します。
     * 
     * @param rootContainer スナップショット対象の基底パネル
     */
    public void setRootContainer(Container rootContainer) {
        this.rootContainer = rootContainer;
    }

    /**
     * スナップショット対象の基底パネルを設定します。
     * <p>
     * 除外したいコンポーネントをリスト化して指定します。
     * </p>
     * 
     * @param rootContainer スナップショット対象の基底パネル
     * @param exclusions スナップショットの除外対象
     */
    public void setRootContainer(Container rootContainer, List exclusions) {
        setRootContainer(rootContainer);
        setExclusions(exclusions);
    }

    /**
     * スナップショット対象の基底パネルを設定します。
     * <p>
     * 除外したいコンポーネントをリスト化して指定します。
     * </p>
     * 
     * @param rootContainer スナップショット対象の基底パネル
     * @param exclusions スナップショットの除外対象
     */
    public void setRootContainer(Container rootContainer, Component[] exclusions) {
        setRootContainer(rootContainer);
        setExclusions(exclusions);
    }

    /**
     * スナップショットを取得します。
     * 
     * @throws ParseException 解析例外
     */
    public void snapshot() throws ParseException {
        if (rootContainer == null) {
            return;
        }
        memento = new HashMap();
        snapshotComponent(rootContainer, memento);

        // 不要なキーを除外する
        removeExclusionKeys(memento, createExclutionKeys(getExclusions()));
    }

    /**
     * 除外キーを削除します。
     * 
     * @param map キー除外対象
     * @param keys 除外キー集合
     */
    protected void removeExclusionKeys(Map map, List keys) {
        if (keys.size() == 0) {
            // 除外キーが存在しなければ何もしない
            return;
        }

        // 除外対象のメメントはMap{キー=Component, 値=Object}である。
        // この値を全走査する。
        Iterator mapIt = map.values().iterator();
        while (mapIt.hasNext()) {
            Object obj = mapIt.next();
            if (obj instanceof Map) {
                // キーがパネルならば値はMapであり、このMapのキーを除外する。
                Map target = (Map) obj;
                Iterator it = keys.iterator();
                while (it.hasNext()) {
                    target.remove(it.next());
                }
            }
        }
    }

    /**
     * 再帰的に二つのオブジェクトを比較し、差をがあるかを返します。
     * 
     * @param x 比較対象1
     * @param y 比較対象2
     * @return 差をがあるか
     */
    protected boolean isModifiedEnum(Object x, Object y) {
        if (x instanceof Map) {
            if (!(y instanceof Map)) {
                return true;
            }

            Map xM = (Map) x;
            Map yM = (Map) y;
            if (xM.size() != yM.size()) {
                return true;
            }
            Iterator it = xM.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (!yM.containsKey(entry.getKey())) {
                    return true;
                }
                if (isModifiedEnum(entry.getValue(), yM.get(entry.getKey()))) {
                    return true;
                }
            }
        } else if (x instanceof List) {
            if (!(y instanceof List)) {
                return true;
            }
            List xL = (List) x;
            List yL = (List) y;
            int end = xL.size();
            if (end != yL.size()) {
                return true;
            }
            for (int i = 0; i < end; i++) {
                if (isModifiedEnum(xL.get(i), yL.get(i))) {
                    return true;
                }
            }
        } else {
            if (x == null) {
                return y != null;
            }
            return !x.equals(y);
        }
        return false;

    }

    /**
     * 再帰的にスナップショットを適用します。
     * 
     * @param comp 対象コンポーネント
     * @param map スナップショットデータ取得元
     * @throws ParseException 解析例外
     */
    protected void restoreComponent(Component comp, Map map)
            throws ParseException {
        if ((getExclusions() != null) && getExclusions().contains(comp)) {
            return;
        }

        if (comp instanceof VRBindable) {
            VRBindable bind = (VRBindable) comp;
            VRBindSource oldSource = bind.getSource();
            bind.setSource((VRBindSource) map.get(comp));
            bind.bindSource();
            bind.setSource(oldSource);
            return;
        }
        if (comp instanceof Container) {
            restoreContainer((Container) comp, map);
        }
    }

    /**
     * 再帰的にスナップショットを適用します。
     * 
     * @param container 親コンテナ
     * @param map スナップショットデータ取得元
     * @throws ParseException 解析例外
     */
    protected void restoreContainer(Container container, Map map)
            throws ParseException {

        int end = container.getComponentCount();
        for (int i = 0; i < end; i++) {
            restoreComponent(container.getComponent(i), map);
        }
    }

    /**
     * 再帰的にスナップショットを取得します。
     * 
     * @param comp 対象コンポーネント
     * @param map スナップショットデータ格納先
     * @throws ParseException 解析例外
     */
    protected void snapshotComponent(Component comp, Map map)
            throws ParseException {
        if ((getExclusions() != null) && getExclusions().contains(comp)) {
            return;
        }

        if (comp instanceof VRBindable) {
            VRBindable bind = (VRBindable) comp;
            VRBindSource oldSource = bind.getSource();
            VRBindSource newSource;
            Object obj = bind.createSource();
            if (obj instanceof VRBindSource) {
                newSource = (VRBindSource) obj;
            } else {
                newSource = new VRHashMap();
            }
            bind.setSource(newSource);
            bind.applySource();
            bind.setSource(oldSource);

            map.put(bind, newSource);
            return;
        }
        if (comp instanceof Container) {
            snapshotContainer((Container) comp, map);
        }
    }

    /**
     * 再帰的に除外キーを検索して返します。
     * 
     * @param exclusions 除外キー検索対象のコンポーネント集合
     * @return 除外キー集合
     * @throws ParseException 解析例外
     */
    protected List createExclutionKeys(List exclusions) throws ParseException {
        List list = new ArrayList();
        if (exclusions != null) {
            Iterator it = exclusions.iterator();
            while (it.hasNext()) {
                Component cmp = (Component) it.next();
                addExclutionKeysComponent(cmp, list);
            }
        }
        return list;
    }

    /**
     * 再帰的に除外キーを追加します。
     * 
     * @param container 親コンテナ
     * @param adder 除外キー追加先
     * @throws ParseException 解析例外
     */
    protected void addExclutionKeysContainer(Container container, List adder)
            throws ParseException {
        int end = container.getComponentCount();
        for (int i = 0; i < end; i++) {
            addExclutionKeysComponent(container.getComponent(i), adder);
        }
    }

    /**
     * 再帰的に除外キーを追加します。
     * 
     * @param comp 対象コンポーネント
     * @param adder 除外キー追加先
     * @throws ParseException 解析例外
     */
    protected void addExclutionKeysComponent(Component comp, List adder)
            throws ParseException {
        if (comp instanceof VRBindable) {
            VRBindable bind = (VRBindable) comp;
            String bindPath = bind.getBindPath();
            if ((bindPath != null) && (!"".equals(bindPath))) {
                adder.add(bindPath);

                Object obj = bind.createSource();
                if (obj instanceof Map) {
                    adder.addAll(Arrays.asList(((Map) obj).keySet().toArray()));
                }
                return;
            }
        }
        if (comp instanceof Container) {
            addExclutionKeysContainer((Container) comp, adder);
        }
    }

    /**
     * 再帰的にスナップショットを取得します。
     * 
     * @param container 親コンテナ
     * @param map スナップショットデータ格納先
     * @throws ParseException 解析例外
     */
    protected void snapshotContainer(Container container, Map map)
            throws ParseException {

        int end = container.getComponentCount();
        for (int i = 0; i < end; i++) {
            snapshotComponent(container.getComponent(i), map);
        }
    }
}
