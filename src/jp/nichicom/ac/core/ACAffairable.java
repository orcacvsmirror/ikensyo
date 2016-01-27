package jp.nichicom.ac.core;

import java.awt.Component;

import jp.nichicom.vr.util.VRMap;

/**
 * 基盤によって遷移可能な業務インターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACAffairInfo
 */

public interface ACAffairable {

    /**
     * 業務初期化処理を実装します。
     * <p>
     * 画面遷移直後に呼ばれます。
     * </p>
     * <p>
     * 前画面からのパラメータは<code>affair.getParameters()</code>で取得してください。
     * </p>
     * 
     * @param affair 業務情報
     * @throws Exception 処理例外
     */
    void initAffair(ACAffairInfo affair) throws Exception;

    /**
     * 前画面に遷移しても良いかを返します。
     * <p>
     * 前画面に受け渡すパラメータは、<code>parameters</code>に格納してください。
     * </p>
     * 
     * @param parameters パラメータ
     * @throws Exception 処理例外
     * @return 前画面に遷移しても良いか
     */
    boolean canBack(VRMap parameters) throws Exception;

    /**
     * システムを終了しても良いかを返します。
     * 
     * @return システムを終了しても良いか
     * @throws Exception 処理例外
     */
    boolean canClose() throws Exception;

    /**
     * 業務開始時にフォーカスをセットするコンポーネントを返します。
     * 
     * @return 業務開始時にフォーカスをセットするコンポーネント
     */
    Component getFirstFocusComponent();
}
