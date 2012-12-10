/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import java.text.ParseException;

import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * バインド機構インターフェースです。
 * <p>
 * バインド機構に対応したコントロールが実装します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindPathParser
 * @see VRBindSource
 * @see VRBindEventListener
 */
public interface VRBindable {
    /**
     * バインドイベントリスナを追加します。
     * 
     * @param listener バインドイベントリスナ
     */
    public void addBindEventListener(VRBindEventListener listener);

    /**
     * コントロールの値を参照先ソースに適用します。
     * 
     * @throws ParseException 解析例外
     */
    public void applySource() throws ParseException;

    /**
     * 参照先ソースの値をコントロールに流し込みます。
     * 
     * @throws ParseException 解析例外
     */
    public void bindSource() throws ParseException;

    /**
     * デフォルトデータを格納したソースインスタンスを生成します。
     * 
     * @return 子要素インスタンス
     */
    public Object createSource();

    /**
     * バインドパスを返します。
     * 
     * @return バインドパス
     */
    public String getBindPath();

    /**
     * 参照先ソースを返します。
     * 
     * @return 参照先ソース
     */
    public VRBindSource getSource();

    /**
     * 自動適用するかを返します。
     * 
     * @return 自動適用するか
     */
    public boolean isAutoApplySource();

    /**
     * バインドイベントリスナを削除します。
     * 
     * @param listener バインドイベントリスナ
     */
    public void removeBindEventListener(VRBindEventListener listener);

    /**
     * 自動適用するかを設定します。
     * 
     * @param autoApplySource 自動適用するか
     */
    public void setAutoApplySource(boolean autoApplySource);

    /**
     * バインドパスを設定します。
     * 
     * @param bindPath バインドパス
     */
    public void setBindPath(String bindPath);

    /**
     * 参照先ソースを設定します。
     * 
     * @param source 参照先ソース
     */
    public void setSource(VRBindSource source);
}