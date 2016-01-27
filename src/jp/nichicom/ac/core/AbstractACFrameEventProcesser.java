package jp.nichicom.ac.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.PrintWriter;
import java.io.StringWriter;

import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.util.splash.ACSplashable;

/**
 * 基底のシステムイベント処理クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACFrameEventProcesser
 */

public abstract class AbstractACFrameEventProcesser implements
        ACFrameEventProcesser {

    protected Color containerDefaultForeground = Color.black;
    protected Color containerErrorForeground = Color.white;
    protected Color containerWarningForeground = Color.black;

    protected Color containerDefaultBackground = Color.black;
    protected Color containerErrorBackground = Color.red;
    protected Color containerWarningBackground = Color.yellow;

    /**
     * コンストラクタです。
     */
    public AbstractACFrameEventProcesser() {
    }

    public void initSystem() throws Exception {

    }

    public void showExceptionMessage(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
    }

    public ACPropertyXML getPropertyXML() throws Exception {
        return null;
    }

    public Color getContainerDefaultForeground() {
        return containerDefaultForeground;
    }

    public Color getContainerDefaultBackground() {
        return containerDefaultBackground;
    }

    public Color getContainerWarningForeground() {
        return containerWarningForeground;
    }

    public Color getContainerWarningBackground() {
        return containerWarningBackground;
    }

    public Color getContainerErrorForeground() {
        return containerErrorForeground;
    }

    public Color getContainerErrorBackground() {
        return containerErrorBackground;
    }

    public String getDefaultMessageBoxTitle() {
        return "メッセージ";
    }

    public Dimension getDefaultWindowSize() {
//        	return new Dimension(800, 600);
        	return ACFrame.getInstance().getScreenSize();
    }

    public Dimension getMinimumWindowSize() {

//        	return new Dimension(800, 600);
        	return ACFrame.getInstance().getScreenSize();
    }

    public ACAffairReplacable getAffairReplacer() {
        return null;
    }

    public ACSplashable createSplash(ACAffairInfo newAffair) throws Exception {
        return null;
    }


    /**
     * メインフレームのアイコンパスを返します。
     * 
     * @return メインフレームのアイコンパス
     */
    public String getFrameIconPath() {
        return null;
    }

    public Image getFrameIcon() {
        String path = getFrameIconPath();
        if ((path == null) || ("".equals(path))) {
            return null;
        }
        try {
            // アイコンの読み込み
            return Toolkit.getDefaultToolkit().createImage(
                    getClass().getClassLoader().getResource(path));
        } catch (Exception ex) {
            return null;
        }
    }

}