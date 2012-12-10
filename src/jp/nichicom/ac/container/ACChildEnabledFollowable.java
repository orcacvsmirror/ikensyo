package jp.nichicom.ac.container;

import jp.nichicom.vr.component.VRContainar;


/**
 * 子の有効状態を連動するコンテナインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRContainar
 */
public interface ACChildEnabledFollowable extends VRContainar {
    /**
     * パネルのEnabledに連動して内包項目のEnabledを設定するか を返します。
     * 
     * @return パネルのEnabledに連動して内包項目のEnabledを設定するか
     */
    public boolean isFollowChildEnabled();
}
