package jp.nichicom.ac.component.style;

import java.awt.Component;


/**
 * コンポーネントのスタイルを変更する機能を有することをあらわすインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACComponentStylizeCondition
 */
public interface ACComponentStylizer {

    /**
     * コンポーネントにスタイルを適用します。
     * @param comp コンポーネント
     * @param value 値
     * @return スタイル適用後のコンポーネント
     */
    public Component stylize(Component comp, Object value);
    
}
