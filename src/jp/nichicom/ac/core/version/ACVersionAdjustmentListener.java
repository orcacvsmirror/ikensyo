package jp.nichicom.ac.core.version;

import java.util.EventListener;

import jp.nichicom.ac.util.splash.ACSplashable;
/**
 * バージョン情報の差異補正イベントリスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/03/28
 */
public interface ACVersionAdjustmentListener extends EventListener{
    /**
     * キーに対応したバージョン情報を返します。
     * @param key キー
     * @return バージョン情報
     */
    public String getVersion(String key);
    /**
     * キーに対応したバージョン情報を設定します。
     * @param key キー
     * @param value バージョン情報
     * @return 設定を認めるか
     */
    public boolean setVersion(String key, String value);
    /**
     * 進捗表示用のスプラッシュを返します。
     * @return 進捗表示用のスプラッシュ
     */
    public ACSplashable getProgressSplash();
}
