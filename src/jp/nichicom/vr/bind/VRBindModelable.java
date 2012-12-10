/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import java.text.ParseException;

import jp.nichicom.vr.bind.event.VRBindModelEventListener;

/**
 * モデルバインド機構インターフェースです。
 * <p>
 * モデルバインド機構に対応したコントロールが実装します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindPathParser
 * @see VRBindSource
 * @see VRBindModelEventListener
 */
public interface VRBindModelable {
    /**
     * モデルバインドイベントリスナを追加します。
     * 
     * @param listener モデルバインドイベントリスナ
     */
    public void addBindModelEventListener(VRBindModelEventListener listener);

    /**
     * コントロールのモデルの値をモデル参照先ソースに適用します。
     * 
     * @throws ParseException 解析例外
     */
    public void applyModelSource() throws ParseException;

    /**
     * モデル参照先ソースの値をコントロールのモデルに流し込みます。
     * 
     * @throws ParseException 解析例外
     */
    public void bindModelSource() throws ParseException;

    /**
     * デフォルトデータを格納したソースインスタンスを生成します。
     * 
     * @return 子要素インスタンス
     */
    public Object createModelSource();

    /**
     * モデルバインドパスを返します。
     * 
     * @return モデルバインドパス
     */
    public String getModelBindPath();

    /**
     * モデル参照先ソースを返します。
     * 
     * @return モデル参照先ソース
     */
    public VRBindSource getModelSource();

    /**
     * モデルバインドイベントリスナを削除します。
     * 
     * @param listener モデルバインドイベントリスナ
     */
    public void removeBindModelEventListener(VRBindModelEventListener listener);


    /**
     * モデルバインドパスを設定します。
     * 
     * @param modelBindPath モデルバインドパス
     */
    public void setModelBindPath(String modelBindPath);
    
    /**
     * モデル参照先ソースを設定します。
     * 
     * @param modelSource モデル参照先ソース
     */
    public void setModelSource(VRBindSource modelSource);
}