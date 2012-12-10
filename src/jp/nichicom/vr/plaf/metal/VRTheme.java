/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalTheme;

/**
 * VR�f�U�C������������e�[�}�ł��B
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
     * �R���X�g���N�^
     */
    public VRTheme() {
        this(12, 12, 12, 12);
    }

    /**
     * �R���X�g���N�^
     * 
     * @param fontSize �t�H���g�T�C�Y
     */
    public VRTheme(int fontSize) {
        this(fontSize, fontSize, fontSize, fontSize);
    }

    /**
     * �R���X�g���N�^
     * 
     * @param ctrlFontSize �R���g���[���t�H���g�T�C�Y
     * @param sysFontSize �V�X�e���t�H���g�T�C�Y
     * @param userFontSize ���[�U�[�t�H���g�T�C�Y
     * @param menuFontSize ���j���[�t�H���g�T�C�Y
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

        //�x�[�W��
        focus1 = new ColorUIResource(255, 255, 64);
        //�I�����W
        focus2 = new ColorUIResource(255, 200, 0);
        //�݂ǂ�
        focus3 = new ColorUIResource(0, 200, 0);

    }

    /**
     * �R���X�g���N�^
     * 
     * @param fontSize �t�H���g�T�C�Y�̃Z�b�g
     * @throws ArrayIndexOutOfBoundsException �z��C���f�b�N�X�͈͊O�G���[
     */
    public VRTheme(int[] fontSize) throws ArrayIndexOutOfBoundsException {
        this(fontSize[0], fontSize[1], fontSize[2], fontSize[3]);
    }

    /**
     * �R���e�i�̃f�t�H���g�w�i�F ��Ԃ��܂��B
     * 
     * @return �R���e�i�̃f�t�H���g�w�i�F
     */
    public ColorUIResource getContainerDefaultBackground() {
        return containerDefaultBackground;
    }

    /**
     * �R���e�i�̃f�t�H���g�O�i�F ��Ԃ��܂��B
     * 
     * @return �R���e�i�̃f�t�H���g�O�i�F
     */
    public ColorUIResource getContainerDefaultForeground() {
        return containerDefaultForeground;
    }

    /**
     * �R���e�i�̃G���[�w�i�F ��Ԃ��܂��B
     * 
     * @return �R���e�i�̃G���[�w�i�F
     */
    public ColorUIResource getContainerErrorBackground() {
        return containerErrorBackground;
    }

    /**
     * �R���e�i�̃G���[�O�i�F ��Ԃ��܂��B
     * 
     * @return ���e�i�̃G���[�O�i�F
     */
    public ColorUIResource getContainerErrorForeground() {
        return containerErrorForeground;
    }

    /**
     * �R���e�i�̌x���w�i�F ��Ԃ��܂��B
     * 
     * @return �R���e�i�̌x���w�i�F
     */
    public ColorUIResource getContainerWarningBackground() {
        return containerWarningBackground;
    }

    /**
     * �R���e�i�̌x���O�i�F ��Ԃ��܂��B
     * 
     * @return �R���e�i�̌x���O�i�F
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
     * �R���e�i�̃f�t�H���g�w�i�F ��ݒ肵�܂��B
     * 
     * @param containerDefaultBackground �R���e�i�̃f�t�H���g�w�i�F
     */
    public void setContainerDefaultBackground(
            ColorUIResource containerDefaultBackground) {
        this.containerDefaultBackground = containerDefaultBackground;
    }

    /**
     * �R���e�i�̃f�t�H���g�O�i�F ��ݒ肵�܂��B
     * 
     * @param containerDefaultForeground �R���e�i�̃f�t�H���g�O�i�F
     */
    public void setContainerDefaultForeground(
            ColorUIResource containerDefaultForeground) {
        this.containerDefaultForeground = containerDefaultForeground;
    }

    /**
     * �R���e�i�̃G���[�w�i�F ��ݒ肵�܂��B
     * 
     * @param containerErrorBackground �R���e�i�̃G���[�w�i�F
     */
    public void setContainerErrorBackground(
            ColorUIResource containerErrorBackground) {
        this.containerErrorBackground = containerErrorBackground;
    }

    /**
     * ���e�i�̃G���[�O�i�F ��ݒ肵�܂��B
     * 
     * @param containerErrorForeground ���e�i�̃G���[�O�i�F
     */
    public void setContainerErrorForeground(
            ColorUIResource containerErrorForeground) {
        this.containerErrorForeground = containerErrorForeground;
    }

    /**
     * �R���e�i�̌x���w�i�F ��ݒ肵�܂��B
     * 
     * @param containerWarningBackground �R���e�i�̌x���w�i�F
     */
    public void setContainerWarningBackground(
            ColorUIResource containerWarningBackground) {
        this.containerWarningBackground = containerWarningBackground;
    }

    /**
     * �R���e�i�̌x���O�i�F ��ݒ肵�܂��B
     * 
     * @param containerWarningForeground �R���e�i�̌x���O�i�F
     */
    public void setContainerWarningForeground(
            ColorUIResource containerWarningForeground) {
        this.containerWarningForeground = containerWarningForeground;
    }

    /**
     * �R���g���[���e�L�X�g�̃t�H���g��ݒ肵�܂��B
     * 
     * @param controlTextFont �R���g���[���e�L�X�g�̃t�H���g
     */
    public void setControlTextFont(FontUIResource controlTextFont) {
        this.controlTextFont = controlTextFont;
    }

    /**
     * �t�H�[�J�X1�̐F��ݒ肵�܂��B
     * 
     * @param focus1 �t�H�[�J�X1�F
     */
    public void setFocus1(ColorUIResource focus1) {
        this.focus1 = focus1;
    }

    /**
     * �t�H�[�J�X2�̐F��ݒ肵�܂��B
     * 
     * @param focus2 �t�H�[�J�X2�F
     */
    public void setFocus2(ColorUIResource focus2) {
        this.focus2 = focus2;
    }

    /**
     * �t�H�[�J�X3�̐F��ݒ肵�܂��B
     * 
     * @param focus3 �t�H�[�J�X3�F
     */
    public void setFocus3(ColorUIResource focus3) {
        this.focus3 = focus3;
    }

    /**
     * ���j���[�e�L�X�g�̃t�H���g��ݒ肵�܂��B
     * 
     * @param menuTextFont ���j���[�e�L�X�g�̃t�H���g
     */
    public void setMenuTextFont(FontUIResource menuTextFont) {
        this.menuTextFont = menuTextFont;
    }

    /**
     * �v���C�}��1�̐F��ݒ肵�܂��B
     * 
     * @param primary1 �v���C�}��1�F
     */
    public void setPrimary1(ColorUIResource primary1) {
        this.primary1 = primary1;
    }

    /**
     * �v���C�}��2�̐F��ݒ肵�܂��B
     * 
     * @param primary2 �v���C�}��2�F
     */
    public void setPrimary2(ColorUIResource primary2) {
        this.primary2 = primary2;
    }

    /**
     * �v���C�}��3�̐F��ݒ肵�܂��B
     * 
     * @param primary3 �v���C�}��3�F
     */
    public void setPrimary3(ColorUIResource primary3) {
        this.primary3 = primary3;
    }

    /**
     * �Z�J���_��1�̐F��ݒ肵�܂��B
     * 
     * @param secondary1 �Z�J���_��3�F
     */
    public void setSecondary1(ColorUIResource secondary1) {
        this.secondary1 = secondary1;
    }

    /**
     * �Z�J���_��2�̐F��ݒ肵�܂��B
     * 
     * @param secondary2 �Z�J���_��2�F
     */
    public void setSecondary2(ColorUIResource secondary2) {
        this.secondary2 = secondary2;
    }

    /**
     * �Z�J���_��3�̐F��ݒ肵�܂��B
     * 
     * @param secondary3 �Z�J���_��3�F
     */
    public void setSecondary3(ColorUIResource secondary3) {
        this.secondary3 = secondary3;
    }

    /**
     * �T�u�e�L�X�g�̃t�H���g��ݒ肵�܂��B
     * 
     * @param subTextFont �T�u�e�L�X�g�̃t�H���g
     */
    public void setSubTextFont(FontUIResource subTextFont) {
        this.subTextFont = subTextFont;
    }

    /**
     * �V�X�e���e�L�X�g�̃t�H���g��ݒ肵�܂��B
     * 
     * @param systemTextFont �V�X�e���e�L�X�g�̃t�H���g
     */
    public void setSystemTextFont(FontUIResource systemTextFont) {
        this.systemTextFont = systemTextFont;
    }

    /**
     * ���[�U�[�e�L�X�g�̃t�H���g��ݒ肵�܂��B
     * 
     * @param userTextFont ���[�U�[�e�L�X�g�̃t�H���g
     */
    public void setUserTextFont(FontUIResource userTextFont) {
        this.userTextFont = userTextFont;
    }

    /**
     * �t�H�[�J�X1�̐F��Ԃ��܂�
     * 
     * @return �t�H�[�J�X1�F
     */
    protected ColorUIResource getFocus1() {
        return focus1;
    }

    /**
     * �t�H�[�J�X2�̐F��Ԃ��܂�
     * 
     * @return �t�H�[�J�X2�F
     */
    protected ColorUIResource getFocus2() {
        return focus2;
    }

    /**
     * �t�H�[�J�X3�̐F��Ԃ��܂�
     * 
     * @return �t�H�[�J�X3�F
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