package jp.nichicom.ac.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.util.splash.ACSplashable;

/**
 * システムイベント処理インターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACFrameEventProcesser {
    /**
     * スプラッシュダイアログを生成して返します。
     * 
     * @return スプラッシュダイアログ
     */
    public ACSplashable createSplash(String title) throws Exception;

    /**
     * 業務置換クラスを返します。
     * 
     * @return 業務置換クラス
     */
    public ACAffairReplacable getAffairReplacer();

    /**
     * コンテナのデフォルト背景色を返します。
     * 
     * @return コンテナのデフォルト背景色
     */
    public Color getContainerDefaultBackground();

    /**
     * コンテナのデフォルト前景色を返します。
     * 
     * @return コンテナのデフォルト前景色
     */
    public Color getContainerDefaultForeground();

    /**
     * コンテナのエラー背景色を返します。
     * 
     * @return コンテナのエラー背景色
     */
    public Color getContainerErrorBackground();

    /**
     * コンテナのエラー前景色を返します。
     * 
     * @return コンテナのエラー前景色
     */
    public Color getContainerErrorForeground();

    /**
     * コンテナのワーニング背景色を返します。
     * 
     * @return コンテナのワーニング背景色
     */
    public Color getContainerWarningBackground();

    /**
     * コンテナのワーニング前景色を返します。
     * 
     * @return コンテナのワーニング前景色
     */
    public Color getContainerWarningForeground();

    /**
     * メッセージボックスのデフォルトウィンドウタイトルを返します。
     * 
     * @return メッセージボックスのデフォルトウィンドウタイトル
     */
    public String getDefaultMessageBoxTitle();

    /**
     * 初期画面サイズを返します。
     * 
     * @return 初期画面サイズ
     */
    public Dimension getDefaultWindowSize();

    /**
     * 最小画面サイズを返します。
     * 
     * @return 最小画面サイズ
     */
    public Dimension getMinimumWindowSize();

    /**
     * 設定ファイルを返します。
     * 
     * @throws Exception 処理例外
     * @return 設定ファイル
     */
    public ACPropertyXML getPropertyXML() throws Exception;

    /**
     * システム初期設定を処理します。
     * 
     * @throws Exception 処理例外
     */
    public void initSystem() throws Exception;

    /**
     * 例外メッセージを表示します。
     * 
     * @param ex 例外
     */
    public void showExceptionMessage(Throwable ex);

    /**
     * メインフレーム用のアイコンを返します。
     * <p>
     * 変更しない場合はnullもしくは空文字を返します。
     * </p>
     * 
     * @return メインフレーム用のアイコン
     */
    public Image getFrameIcon();

}