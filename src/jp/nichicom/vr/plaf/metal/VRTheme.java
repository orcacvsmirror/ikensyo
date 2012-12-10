/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalTheme;

/**
 * VRデザインを実装するテーマです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see MetalTheme
 */
public class VRTheme extends MetalTheme {

    // container colors
    private ColorUIResource containerDefaultBackground;

    private ColorUIResource containerDefaultForeground;

    private ColorUIResource containerErrorBackground;

    private ColorUIResource containerErrorForeground;

    private ColorUIResource containerWarningBackground;

    private ColorUIResource containerWarningForeground;

    // focus colors
    private ColorUIResource focus1;

    private ColorUIResource focus2;

    private ColorUIResource focus3;
    // primary colors
    private ColorUIResource primary1;

    private ColorUIResource primary2;

    private ColorUIResource primary3;

    // secondary colors
    private ColorUIResource secondary1;

    private ColorUIResource secondary2;

    private ColorUIResource secondary3;

    // fonts
    protected FontUIResource controlTextFont;

    protected FontUIResource menuTextFont;

    protected FontUIResource subTextFont;

    protected FontUIResource systemTextFont;

    protected FontUIResource userTextFont;

    /**
     * コンストラクタ
     */
    public VRTheme() {
        this(12, 12, 12, 12);
    }

    /**
     * コンストラクタ
     * 
     * @param fontSize フォントサイズ
     */
    public VRTheme(int fontSize) {
        this(fontSize, fontSize, fontSize, fontSize);
    }

    /**
     * コンストラクタ
     * 
     * @param ctrlFontSize コントロールフォントサイズ
     * @param sysFontSize システムフォントサイズ
     * @param userFontSize ユーザーフォントサイズ
     * @param menuFontSize メニューフォントサイズ
     */
    public VRTheme(int ctrlFontSize, int sysFontSize, int userFontSize,
            int menuFontSize) {

        super();

        controlTextFont = new FontUIResource("Dialog", Font.PLAIN, ctrlFontSize);
        systemTextFont = new FontUIResource("Dialog", Font.PLAIN, sysFontSize);
        userTextFont = new FontUIResource("Dialog", Font.PLAIN, userFontSize);
        menuTextFont = new FontUIResource("Dialog", Font.PLAIN, menuFontSize);
        subTextFont = new FontUIResource("Dialog", Font.PLAIN,
                (int) (sysFontSize * 0.8) + 1);

        primary1 = new ColorUIResource(40, 90, 160);
        primary2 = new ColorUIResource(90, 120, 230);
        primary3 = new ColorUIResource(120, 160, 255);

        secondary1 = new ColorUIResource(120, 120, 120);
        secondary2 = new ColorUIResource(200, 200, 200);
        secondary3 = new ColorUIResource(230, 230, 230);

        //ベージュ
        focus1 = new ColorUIResource(255, 255, 64);
        //オレンジ
        focus2 = new ColorUIResource(255, 200, 0);
        //みどり
        focus3 = new ColorUIResource(0, 200, 0);

    }

    /**
     * コンストラクタ
     * 
     * @param fontSize フォントサイズのセット
     * @throws ArrayIndexOutOfBoundsException 配列インデックス範囲外エラー
     */
    public VRTheme(int[] fontSize) throws ArrayIndexOutOfBoundsException {
        this(fontSize[0], fontSize[1], fontSize[2], fontSize[3]);
    }

    /**
     * コンテナのデフォルト背景色 を返します。
     * 
     * @return コンテナのデフォルト背景色
     */
    public ColorUIResource getContainerDefaultBackground() {
        return containerDefaultBackground;
    }

    /**
     * コンテナのデフォルト前景色 を返します。
     * 
     * @return コンテナのデフォルト前景色
     */
    public ColorUIResource getContainerDefaultForeground() {
        return containerDefaultForeground;
    }

    /**
     * コンテナのエラー背景色 を返します。
     * 
     * @return コンテナのエラー背景色
     */
    public ColorUIResource getContainerErrorBackground() {
        return containerErrorBackground;
    }

    /**
     * コンテナのエラー前景色 を返します。
     * 
     * @return ンテナのエラー前景色
     */
    public ColorUIResource getContainerErrorForeground() {
        return containerErrorForeground;
    }

    /**
     * コンテナの警告背景色 を返します。
     * 
     * @return コンテナの警告背景色
     */
    public ColorUIResource getContainerWarningBackground() {
        return containerWarningBackground;
    }

    /**
     * コンテナの警告前景色 を返します。
     * 
     * @return コンテナの警告前景色
     */
    public ColorUIResource getContainerWarningForeground() {
        return containerWarningForeground;
    }

    public FontUIResource getControlTextFont() {
        return controlTextFont;
    }

    public ColorUIResource getHighlightedTextColor() {
        return getWhite();
    }

    public ColorUIResource getMenuDisabledForeground() {
        return getSecondary2();
    }

    public ColorUIResource getMenuSelectedBackground() {
        return getPrimary2();
    }

    public ColorUIResource getMenuSelectedForeground() {
        return getWhite();
    }

    public FontUIResource getMenuTextFont() {
        return menuTextFont;
    }

    public String getName() {
        return "Musi_chanTheme";
    }

    public FontUIResource getSubTextFont() {
        return subTextFont;
    }

    public FontUIResource getSystemTextFont() {
        return systemTextFont;
    }

    public ColorUIResource getTextHighlightColor() {
        return getPrimary1();
    }

    public FontUIResource getUserTextFont() {
        return userTextFont;
    }

    public ColorUIResource getWindowTitleBackground() {
        return getPrimary2();
    }

    public FontUIResource getWindowTitleFont() {
        return systemTextFont;
    }

    public ColorUIResource getWindowTitleForeground() {
        return getWhite();
    }

    /**
     * コンテナのデフォルト背景色 を設定します。
     * 
     * @param containerDefaultBackground コンテナのデフォルト背景色
     */
    public void setContainerDefaultBackground(
            ColorUIResource containerDefaultBackground) {
        this.containerDefaultBackground = containerDefaultBackground;
    }

    /**
     * コンテナのデフォルト前景色 を設定します。
     * 
     * @param containerDefaultForeground コンテナのデフォルト前景色
     */
    public void setContainerDefaultForeground(
            ColorUIResource containerDefaultForeground) {
        this.containerDefaultForeground = containerDefaultForeground;
    }

    /**
     * コンテナのエラー背景色 を設定します。
     * 
     * @param containerErrorBackground コンテナのエラー背景色
     */
    public void setContainerErrorBackground(
            ColorUIResource containerErrorBackground) {
        this.containerErrorBackground = containerErrorBackground;
    }

    /**
     * ンテナのエラー前景色 を設定します。
     * 
     * @param containerErrorForeground ンテナのエラー前景色
     */
    public void setContainerErrorForeground(
            ColorUIResource containerErrorForeground) {
        this.containerErrorForeground = containerErrorForeground;
    }

    /**
     * コンテナの警告背景色 を設定します。
     * 
     * @param containerWarningBackground コンテナの警告背景色
     */
    public void setContainerWarningBackground(
            ColorUIResource containerWarningBackground) {
        this.containerWarningBackground = containerWarningBackground;
    }

    /**
     * コンテナの警告前景色 を設定します。
     * 
     * @param containerWarningForeground コンテナの警告前景色
     */
    public void setContainerWarningForeground(
            ColorUIResource containerWarningForeground) {
        this.containerWarningForeground = containerWarningForeground;
    }

    /**
     * コントロールテキストのフォントを設定します。
     * 
     * @param controlTextFont コントロールテキストのフォント
     */
    public void setControlTextFont(FontUIResource controlTextFont) {
        this.controlTextFont = controlTextFont;
    }

    /**
     * フォーカス1の色を設定します。
     * 
     * @param focus1 フォーカス1色
     */
    public void setFocus1(ColorUIResource focus1) {
        this.focus1 = focus1;
    }

    /**
     * フォーカス2の色を設定します。
     * 
     * @param focus2 フォーカス2色
     */
    public void setFocus2(ColorUIResource focus2) {
        this.focus2 = focus2;
    }

    /**
     * フォーカス3の色を設定します。
     * 
     * @param focus3 フォーカス3色
     */
    public void setFocus3(ColorUIResource focus3) {
        this.focus3 = focus3;
    }

    /**
     * メニューテキストのフォントを設定します。
     * 
     * @param menuTextFont メニューテキストのフォント
     */
    public void setMenuTextFont(FontUIResource menuTextFont) {
        this.menuTextFont = menuTextFont;
    }

    /**
     * プライマリ1の色を設定します。
     * 
     * @param primary1 プライマリ1色
     */
    public void setPrimary1(ColorUIResource primary1) {
        this.primary1 = primary1;
    }

    /**
     * プライマリ2の色を設定します。
     * 
     * @param primary2 プライマリ2色
     */
    public void setPrimary2(ColorUIResource primary2) {
        this.primary2 = primary2;
    }

    /**
     * プライマリ3の色を設定します。
     * 
     * @param primary3 プライマリ3色
     */
    public void setPrimary3(ColorUIResource primary3) {
        this.primary3 = primary3;
    }

    /**
     * セカンダリ1の色を設定します。
     * 
     * @param secondary1 セカンダリ3色
     */
    public void setSecondary1(ColorUIResource secondary1) {
        this.secondary1 = secondary1;
    }

    /**
     * セカンダリ2の色を設定します。
     * 
     * @param secondary2 セカンダリ2色
     */
    public void setSecondary2(ColorUIResource secondary2) {
        this.secondary2 = secondary2;
    }

    /**
     * セカンダリ3の色を設定します。
     * 
     * @param secondary3 セカンダリ3色
     */
    public void setSecondary3(ColorUIResource secondary3) {
        this.secondary3 = secondary3;
    }

    /**
     * サブテキストのフォントを設定します。
     * 
     * @param subTextFont サブテキストのフォント
     */
    public void setSubTextFont(FontUIResource subTextFont) {
        this.subTextFont = subTextFont;
    }

    /**
     * システムテキストのフォントを設定します。
     * 
     * @param systemTextFont システムテキストのフォント
     */
    public void setSystemTextFont(FontUIResource systemTextFont) {
        this.systemTextFont = systemTextFont;
    }

    /**
     * ユーザーテキストのフォントを設定します。
     * 
     * @param userTextFont ユーザーテキストのフォント
     */
    public void setUserTextFont(FontUIResource userTextFont) {
        this.userTextFont = userTextFont;
    }

    /**
     * フォーカス1の色を返します
     * 
     * @return フォーカス1色
     */
    protected ColorUIResource getFocus1() {
        return focus1;
    }

    /**
     * フォーカス2の色を返します
     * 
     * @return フォーカス2色
     */
    protected ColorUIResource getFocus2() {
        return focus2;
    }

    /**
     * フォーカス3の色を返します
     * 
     * @return フォーカス3色
     */
    protected ColorUIResource getFocus3() {
        return focus3;
    }

    protected ColorUIResource getPrimary1() {
        return primary1;
    }

    protected ColorUIResource getPrimary2() {
        return primary2;
    }

    protected ColorUIResource getPrimary3() {
        return primary3;
    }

    protected ColorUIResource getSecondary1() {
        return secondary1;
    }

    protected ColorUIResource getSecondary2() {
        return secondary2;
    }

    protected ColorUIResource getSecondary3() {
        return secondary3;
    }
}