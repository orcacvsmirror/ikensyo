package jp.nichicom.ac.core;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.container.ACStatusBar;
import jp.nichicom.vr.layout.VRLayout;

/**
 * 業務クラスです。
 * <p>
 * 基盤によって遷移可能なクラスとするには、さらに<code>NCAffairble</code>を実装する必要があります。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACConstants
 * @see ACAffairable
 */

public class ACAffairContainer extends ACPanel implements ACConstants {
    protected ACStatusBar statusBar = new ACStatusBar();

    /**
     * コンストラクタです。
     */
    public ACAffairContainer() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ステータスバーのタイマーを停止させます。
     * <p>
     * 開放時に自動で呼ばれないので、明示的に呼びます。
     * </p>
     */
    public void cancelTimer() {
        ACStatusBar statusBar = getStatusBar();
        if (statusBar instanceof ACStatusBar) {
            statusBar.cancelTimer();
        }
    }

    public boolean canClose() throws Exception {
        return true;
    }

    /**
     * ステータスバーを返します。
     * 
     * @return ステータスバー
     */
    public ACStatusBar getStatusBar() {
        return statusBar;
    }

    /**
     * ウィンドウタイトルを返します。
     * 
     * @return ウィンドウタイトル
     */
    public String getTitle() {
        return ACFrame.getInstance().getTitle();
    }

    /**
     * ウィンドウタイトルを設定します。
     * 
     * @param title ウィンドウタイトル
     */
    public void setTitle(String title) {
        ACFrame.getInstance().setTitle(title);
    }

    /**
     * コンポーネントを設定します。
     * 
     * @throws Exception 設定例外
     */
    private void jbInit() throws Exception{
        statusBar.setForeground(java.awt.Color.white);
        statusBar.setBackground(new java.awt.Color(0, 51, 153));
        this.add(statusBar, VRLayout.SOUTH);
        this.setBackground(new java.awt.Color(0, 51, 153));

    }

    /**
     * ステータスバーの文字列を返します。
     * 
     * @return ステータスバーの文字列
     */
    protected String getStatusText() {
        ACStatusBar statusBar = getStatusBar();
        if (statusBar instanceof ACStatusBar) {
            statusBar.getText();
        }
        return "";
    }

    /**
     * ステータスバーに文字列を設定します。
     * 
     * @param text ステータスバーの文字列
     */
    protected void setStatusText(String text) {
        ACStatusBar statusBar = getStatusBar();
        if (statusBar instanceof ACStatusBar) {
            statusBar.setText(text);
        }
    }
}