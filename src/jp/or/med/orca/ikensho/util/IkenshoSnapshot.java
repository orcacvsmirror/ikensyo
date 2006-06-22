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
package jp.or.med.orca.ikensho.util;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;

public class IkenshoSnapshot {
    private static IkenshoSnapshot singleton;

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static IkenshoSnapshot getInstance() {
        if (singleton == null) {
            singleton = new IkenshoSnapshot();
        }
        return singleton;
    }

    protected List exclusions;

    protected Map mement;

    protected Container rootContainer;

    /**
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected IkenshoSnapshot() {
    }

    /**
     * 初期化します。
     */
    public void clear() {
        exclusions = null;
        rootContainer = null;
        mement = null;
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
    public Map getMemento() {
        return mement;
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
        if (mement == null) {
            // 未スナップショット
            return true;
        }
        if (rootContainer == null) {
            // コンテナ未設定
            return true;
        }

        HashMap newMement = new HashMap();
        snapshotComponent(rootContainer, newMement);

        return isModifiedEnum(newMement, mement);
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
        restoreComponent(rootContainer, mement);
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
     * スナップショット比較元の値を返します。
     * 
     * @param mement スナップショット比較元の値
     */
    public void setMemento(Map mement) {
        this.mement = mement;
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
     * スナップショットを取得します。
     * 
     * @throws ParseException 解析例外
     */
    public void snapshot() throws ParseException {
        if (rootContainer == null) {
            return;
        }
        mement = new HashMap();
        snapshotComponent(rootContainer, mement);
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
        if (getExclusions().contains(comp)) {
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
