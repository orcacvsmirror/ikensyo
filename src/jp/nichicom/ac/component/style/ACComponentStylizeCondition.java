package jp.nichicom.ac.component.style;


/**
 * コンポーネントのスタイルを変更可能か判断する条件であることをあらわすインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public interface ACComponentStylizeCondition {
    /**
     * スタイル変更を許可する値であるかを判定して返します。
     * 
     * @param value 検査値
     * @return スタイル変更を許可する値であるか
     */
    public boolean isMatch(Object value);

}
