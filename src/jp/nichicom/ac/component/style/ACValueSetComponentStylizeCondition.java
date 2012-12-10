package jp.nichicom.ac.component.style;

import java.util.Collection;
import java.util.HashSet;

/**
 * 登録された値であればコンポーネントのスタイルを変更可能と判断するスタイライズ条件です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACValueSetComponentStylizeCondition extends HashSet implements
        ACComponentStylizeCondition {

    /**
     * Constructs a new, empty set; the backing <tt>HashMap</tt> instance has
     * default initial capacity (16) and load factor (0.75).
     */
    public ACValueSetComponentStylizeCondition() {
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
    public ACValueSetComponentStylizeCondition(int initialCapacity) {
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
    public ACValueSetComponentStylizeCondition(int initialCapacity,
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
    public ACValueSetComponentStylizeCondition(Collection c) {
        super(c);
    }

    public boolean isMatch(Object value) {
        // 値と合致するものが登録されていればスタイルを適用する
        return contains(value);
    }

}
