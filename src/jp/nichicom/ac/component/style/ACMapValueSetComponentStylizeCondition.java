package jp.nichicom.ac.component.style;

import java.text.ParseException;
import java.util.Collection;
import java.util.Map;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;

/**
 * Map形式の検証値から特定キーに対応する値を取り出し、その値が自身に登録されているかを基準にコンポーネントのスタイルを変更可能か判断するスタイライズ条件です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACMapValueSetComponentStylizeCondition extends
        ACValueSetComponentStylizeCondition {
    private String bindPath;

    /**
     * Mapから値を取り出すキーとなるバインドパス を返します。
     * 
     * @return Mapから値を取り出すキーとなるバインドパス
     */
    public String getBindPath() {
        return bindPath;
    }

    /**
     * Mapから値を取り出すキーとなるバインドパス を設定します。
     * 
     * @param bindPath Mapから値を取り出すキーとなるバインドパス
     */
    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     * 
     * @param bindPath Mapから値を取り出すキーとなるバインドパス
     */
    public ACMapValueSetComponentStylizeCondition(String bindPath) {
        super();
        setBindPath(bindPath);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and default load factor, which is
     * <tt>0.75</tt>.
     * 
     * @param bindPath Mapから値を取り出すキーとなるバインドパス
     * @param initialCapacity the initial capacity of the hash table.
     * @throws IllegalArgumentException if the initial capacity is less than
     *             zero.
     */
    public ACMapValueSetComponentStylizeCondition(String bindPath,
            int initialCapacity) {
        super(initialCapacity);
        setBindPath(bindPath);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and the specified load factor.
     * 
     * @param bindPath Mapから値を取り出すキーとなるバインドパス
     * @param initialCapacity the initial capacity of the hash map.
     * @param loadFactor the load factor of the hash map.
     * @throws IllegalArgumentException if the initial capacity is less than
     *             zero, or if the load factor is nonpositive.
     */
    public ACMapValueSetComponentStylizeCondition(String bindPath,
            int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        setBindPath(bindPath);
    }

    /**
     * Constructs a new set containing the elements in the specified collection.
     * The <tt>HashMap</tt> is created with default load factor (0.75) and an
     * initial capacity sufficient to contain the elements in the specified
     * collection.
     * 
     * @param bindPath Mapから値を取り出すキーとなるバインドパス
     * @param c the collection whose elements are to be placed into this set.
     * @throws NullPointerException if the specified collection is null.
     */
    public ACMapValueSetComponentStylizeCondition(String bindPath, Collection c) {
        super(c);
        setBindPath(bindPath);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     */
    public ACMapValueSetComponentStylizeCondition() {
        super();
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and default load factor, which is
     * <tt>0.75</tt>.
     * 
     * @param initialCapacity the initial capacity of the hash table.
     * @throws IllegalArgumentException if the initial capacity is less than
     *             zero.
     */
    public ACMapValueSetComponentStylizeCondition(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * the specified initial capacity and the specified load factor.
     * 
     * @param initialCapacity the initial capacity of the hash map.
     * @param loadFactor the load factor of the hash map.
     * @throws IllegalArgumentException if the initial capacity is less than
     *             zero, or if the load factor is nonpositive.
     */
    public ACMapValueSetComponentStylizeCondition(int initialCapacity,
            float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new set containing the elements in the specified collection.
     * The <tt>HashMap</tt> is created with default load factor (0.75) and an
     * initial capacity sufficient to contain the elements in the specified
     * collection.
     * 
     * @param c the collection whose elements are to be placed into this set.
     * @throws NullPointerException if the specified collection is null.
     */
    public ACMapValueSetComponentStylizeCondition(Collection c) {
        super(c);
    }

    public boolean isMatch(Object value) {
        if (value instanceof VRBindSource) {
            try {
                return super.isMatch(VRBindPathParser.get(getBindPath(),
                        (VRBindSource) value));
            } catch (ParseException e) {
            }
        }
        if (value instanceof Map) {
            return super.isMatch(((Map) value).get(getBindPath()));
        }
        return super.isMatch(value);
    }

}
