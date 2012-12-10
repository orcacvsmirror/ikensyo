package jp.nichicom.ac.component;

import java.util.Map;

import jp.nichicom.vr.util.VRHashMap;

/**
 * リストやコンボ用の項目です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/19
 */
public class ACListItem extends VRHashMap {
    private boolean simpleValueMode = true;
    private String text = "";

    public String toString() {
        if (isSimpleValueMode()) {
            return getText();
        }
        return super.toString();
    }

    /**
     * 基本値 を返します。
     * 
     * @return 基本値
     */
    public String getText() {
        return text;
    }

    /**
     * 基本値 を設定します。
     * 
     * @param simpleValue 基本値
     */
    public void setText(String simpleValue) {
        this.text = simpleValue;
    }

    /**
     * toStringの結果として基本値を返すか を返します。
     * 
     * @return toStringの結果として基本値を返すか
     */
    public boolean isSimpleValueMode() {
        return simpleValueMode;
    }

    /**
     * toStringの結果として基本値を返すか を設定します。
     * 
     * @param simpleValueMode toStringの結果として基本値を返すか
     */
    public void setSimpleValueMode(boolean simpleValueMode) {
        this.simpleValueMode = simpleValueMode;
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public ACListItem() {
        super();
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     * 
     * @param initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public ACListItem(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     * 
     * @param initialCapacity The initial capacity.
     * @param loadFactor The load factor.
     * @throws IllegalArgumentException if the initial capacity is negative or
     *             the load factor is nonpositive.
     */
    public ACListItem(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new <tt>HashMap</tt> with the same mappings as the
     * specified <tt>Map</tt>. The <tt>HashMap</tt> is created with default
     * load factor (0.75) and an initial capacity sufficient to hold the
     * mappings in the specified <tt>Map</tt>.
     * 
     * @param m the map whose mappings are to be placed in this map.
     * @throws NullPointerException if the specified map is null.
     */
    public ACListItem(Map m) {
        super(m);
    }

}
