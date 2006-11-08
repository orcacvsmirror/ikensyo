package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

/**
 * 
 * 簡易スナップショットクラスです。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/06/22
 */
public class IkenshoIkenshoSimpleSnapshot extends HashMap {
    /**
     * 比較コンポーネント格納用変数
     */
    private ArrayList compList = new ArrayList(); 
    
    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public IkenshoIkenshoSimpleSnapshot() {
        super();
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public IkenshoIkenshoSimpleSnapshot(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     *
     * @param  initialCapacity The initial capacity.
     * @param  loadFactor      The load factor.
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive.
     */
    public IkenshoIkenshoSimpleSnapshot(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new <tt>HashMap</tt> with the same mappings as the
     * specified <tt>Map</tt>.  The <tt>HashMap</tt> is created with
     * default load factor (0.75) and an initial capacity sufficient to
     * hold the mappings in the specified <tt>Map</tt>.
     *
     * @param   m the map whose mappings are to be placed in this map.
     * @throws  NullPointerException if the specified map is null.
     */
    public IkenshoIkenshoSimpleSnapshot(Map m) {
        super(m);
    }
    
    /**
     * スナップショットの対象となるコンポーネントを指定します。
     * @param c
     */
    public void addComponent(Component c){
        compList.add(c);
    }

    /**
     * 対象コンポーネントより値を取得し記憶します。
     */
    public void simpleSnapshot() throws Exception{
        for(int i = 0; i < compList.size();i++){
            Object comp = compList.get(i);
            if (comp instanceof VRBindable) {
                // 新規Map用
                VRMap maps = new VRHashMap();
                VRBindable bind = (VRBindable) comp;
                // バインドパスをキーにして内部のソースを退避する。
                VRMap taihi = (VRHashMap)bind.getSource();
                bind.setSource(maps);
                bind.applySource();
                bind.setSource(taihi);
                this.put(bind.getBindPath(),maps.getData(bind.getBindPath()));
            }
        }
    }
    
    /**
     * 渡されたオブジェクトを解析して対応するバインドパスと比較します。
     * @return
     * @throws Exception
     */
    public boolean simpleIsModefield() throws Exception{
        for(int i = 0; i<compList.size();i++){
            Object comp = compList.get(i);
            if (comp instanceof VRBindable) {
                VRBindable newBind = (VRBindable) comp;
                // 保存済みの値と比較
                Iterator it = this.entrySet().iterator();
                Map.Entry key = null;
                // 新規ソース
                VRMap mapSource = new VRHashMap();
                // 差し戻しのため一時的に退避する
                VRMap taihi = (VRHashMap)newBind.getSource();
                newBind.setSource(mapSource);
                // データを取得
                newBind.applySource();
                // ソース差し戻し
                newBind.setSource(taihi);
                while(it.hasNext()){
                    key = (Map.Entry)it.next();
                    String sKey = ACCastUtilities.toString(key.getKey());
                    // 同一のキーが存在している場合は比較する。
                    if(sKey.equals(newBind.getBindPath())){
                        if(newBind.getSource() instanceof HashMap){
                            // 値を比較
                            if(!ACCastUtilities.toString(this.get(sKey)).equals(mapSource.get(newBind.getBindPath()))){
                                // 変更点あり
                                return true;
                            }
                        }
                    }
                }
            }
        }
        
        return false;
    }
}
