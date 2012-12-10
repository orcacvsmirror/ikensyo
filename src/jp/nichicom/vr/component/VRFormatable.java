package jp.nichicom.vr.component;

import java.text.Format;

import jp.nichicom.vr.component.event.VRFormatEventListener;

/**
 * フォーマットを指定可能なコントロールであることをあらわすインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see Format
 */
public interface VRFormatable {
    /**
     * フォーマットを返します。
     * 
     * @return フォーマット
     */
    public Format getFormat();

    /**
     * フォーマットを設定します。
     * 
     * @param format フォーマット
     */
    public void setFormat(Format format);

    /**
     * フォーマットイベントリスナを追加します。
     * 
     * @param listener フォーマットイベントリスナ
     */
    public void addFormatEventListener(VRFormatEventListener listener);

    /**
     * フォーマットイベントリスナを削除します。
     * 
     * @param listener フォーマットイベントリスナ
     */
    public void removeFormatEventListener(VRFormatEventListener listener);

}
