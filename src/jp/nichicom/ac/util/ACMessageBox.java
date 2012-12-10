package jp.nichicom.ac.util;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JOptionPane;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;

/**
 * �ėp���b�Z�[�W�{�b�N�X�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JOptionPane
 */

public class ACMessageBox extends JOptionPane {

    /**
     * �L�����Z���{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_CANCEL = 2;
    /**
     * �������{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_NO = 8;
    /**
     * �{�^���Ȃ���\���{�^���萔�ł��B
     */
    public static final int BUTTON_NONE = 0;
    /**
     * OK�{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_OK = 1;
    /**
     * OK�E�L�����Z���{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_OKCANCEL = BUTTON_OK | BUTTON_CANCEL;
    /**
     * �͂��{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_YES = 4;
    /**
     * �͂��E�������{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_YESNO = BUTTON_YES | BUTTON_NO;
    /**
     * �͂��E�������E�L�����Z���{�^����\���{�^���萔�ł��B
     */
    public static final int BUTTON_YESNOCANCEL = BUTTON_YES | BUTTON_NO
            | BUTTON_CANCEL;

    /**
     * �L�����Z���{�^����\���t�H�[�J�X�萔�ł��B
     */
    public static final int FOCUS_CANCEL = 0;
    /**
     * �������{�^����\���t�H�[�J�X�萔�ł��B
     */
    public static final int FOCUS_NO = 3;
    /**
     * �w��Ȃ���\���t�H�[�J�X�萔�ł��B
     */
    public static final int FOCUS_NONE = -1;
    /**
     * OK�{�^����\���t�H�[�J�X�萔�ł��B
     */
    public static final int FOCUS_OK = 1;
    /**
     * �͂��{�^����\���t�H�[�J�X�萔�ł��B
     */
    public static final int FOCUS_YES = 2;

    /**
     * ���Q����\���A�C�R���萔�ł��B
     */
    public static final int ICON_EXCLAMATION = 3;
    /**
     * ����\���A�C�R���萔�ł��B
     */
    public static final int ICON_INFOMATION = 1;
    /**
     * �w��Ȃ���\���A�C�R���萔�ł��B
     */
    public static final int ICON_NONE = 0;
    /**
     * �^�╄��\���A�C�R���萔�ł��B
     */
    public static final int ICON_QUESTION = 2;

    /**
     * ��~��\���A�C�R���萔�ł��B
     */
    public static final int ICON_STOP = 4;

    /**
     * �L�����Z���{�^��������\�����ʒ萔�ł��B
     */
    public static final int RESULT_CANCEL = JOptionPane.CANCEL_OPTION;
    /**
     * �������{�^����\�����ʒ萔�ł��B
     */
    public static final int RESULT_NO = JOptionPane.NO_OPTION;
    /**
     * OK�{�^��������\�����ʒ萔�ł��B
     */
    public static final int RESULT_OK = JOptionPane.OK_OPTION;
    /**
     * �͂��{�^��������\�����ʒ萔�ł��B
     */
    public static final int RESULT_YES = JOptionPane.YES_OPTION;

    /**
     * ����̃{�^����\���{�^���萔�ł��B
     */
    public static final int DEFAULT_BUTTON = BUTTON_OK;
    /**
     * ����̃t�H�[�J�X��\���t�H�[�J�X�萔�ł��B
     */
    public static final int DEFAULT_FOCUS = FOCUS_NONE;
    /**
     * ����̃A�C�R����\���A�C�R���萔�ł��B
     */
    public static final int DEFAULT_ICON = ICON_INFOMATION;

    private static ACMessageBox singleton;

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param message �{��
     * @return �I�������I�y���[�V����
     */
    public static int show(String message) {
        return show(getDefaultTitle(), message, DEFAULT_BUTTON, DEFAULT_ICON,
                DEFAULT_FOCUS);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param message �{��
     * @param expands �g���̈�
     * @param resizable �g��k���\��
     * @return �I�������I�y���[�V����
     */
    public static int show(String message, Component expands, boolean resizable) {
        return show(getDefaultTitle(), message, DEFAULT_BUTTON, DEFAULT_ICON,
                DEFAULT_FOCUS, expands, resizable);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param message �{��
     * @param buttons �{�^���`��
     * @return �I�������I�y���[�V����
     */
    public static int show(String message, int buttons) {
        return show(getDefaultTitle(), message, buttons, DEFAULT_ICON,
                DEFAULT_FOCUS);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @return �I�������I�y���[�V����
     */
    public static int show(String message, int buttons, int icon) {
        return show(message, buttons, icon, DEFAULT_FOCUS);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @return �I�������I�y���[�V����
     */
    public static int show(String message, int buttons, int icon, int focus) {
        ACMessageBoxDialog dialog = getInstance().createDialog(
                ACFrame.getInstance(), getDefaultTitle(), true);
        dialog.showInner(message, buttons, icon, focus);
        return dialog.getResult();
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param buttons �{�^���`��
     * @return �I�������I�y���[�V����
     */
    public static int show(String title, String message, int buttons) {
        return show(title, message, buttons, DEFAULT_ICON);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @return �I�������I�y���[�V����
     */
    public static int show(String title, String message, int buttons, int icon) {
        return show(message, buttons, icon, DEFAULT_FOCUS);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @return �I�������I�y���[�V����
     */
    public static int show(String title, String message, int buttons, int icon,
            int focus) {
        return show(title, message, buttons, icon, focus, null, false);
    }

    /**
     * ���b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @param expands �g���̈�
     * @param resizable �g��k���\��
     * @return �I�������I�y���[�V����
     */
    public static int show(String title, String message, int buttons, int icon,
            int focus, Component expands, boolean resizable) {
        ACMessageBoxDialog dialog = getInstance().createDialog(
                ACFrame.getInstance(), title, true);
        dialog.showInner(message, buttons, icon, focus, expands, resizable);
        return dialog.getResult();
    }

    /**
     * �x���p�̃��b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param message �{��
     * @return �I�������I�y���[�V����
     */
    public static int showExclamation(String message) {
        return show(getDefaultTitle(), message, DEFAULT_BUTTON,
                ICON_EXCLAMATION);
    }

    /**
     * OK�E�L�����Z���`���̃��b�Z�[�W�{�b�N�X��\�����܂��B
     * <p>
     * �t�H�[�J�X�́uOK�v�{�^���ɐݒ肳��܂��B
     * </p>
     * 
     * @param message �{��
     * @return �I�������I�y���[�V����
     */
    public static int showOkCancel(String message) {
        return show(getDefaultTitle(), message, BUTTON_OKCANCEL, ICON_QUESTION);
    }

    /**
     * OK�E�L�����Z���`���̃��b�Z�[�W�{�b�N�X���t�H�[�J�X�{�^�����w�肵�ĕ\�����܂��B
     * 
     * @param message �{��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @return �I�������I�y���[�V����
     */
    public static int showOkCancel(String message, int focus) {
        return show(getDefaultTitle(), message, BUTTON_OKCANCEL, ICON_QUESTION,
                focus);
    }

    /**
     * OK�E�L�����Z���̃L���v�V������ύX�������b�Z�[�W�{�b�N�X��\�����܂��B
     * <p>
     * �t�H�[�J�X�́uOK�v�{�^���ɐݒ肳��܂��B
     * </p>
     * 
     * @param message �{��
     * @param okText OK�{�^���̃L���v�V����
     * @param okMnemonic OK�{�^���̃j�[���j�b�N
     * @return �I�������I�y���[�V����
     */
    public static int showOkCancel(String message, String okText,
            char okMnemonic) {
        return showOkCancel(getDefaultTitle(), message, okText, okMnemonic);
    }

    /**
     * OK�E�L�����Z���̃L���v�V������ύX�������b�Z�[�W�{�b�N�X��\�����܂��B
     * <p>
     * �t�H�[�J�X�́uOK�v�{�^���ɐݒ肳��܂��B
     * </p>
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param okText OK�{�^���̃L���v�V����
     * @param okMnemonic OK�{�^���̃j�[���j�b�N
     * @return �I�������I�y���[�V����
     */
    public static int showOkCancel(String title, String message, String okText,
            char okMnemonic) {
        ACMessageBoxDialog dialog = getInstance().createDialog(
                ACFrame.getInstance(), title, true);
        dialog.getOKButton().setText(okText);
        dialog.getOKButton().setMnemonic(okMnemonic);
        dialog
                .showInner(message, BUTTON_OKCANCEL, ICON_QUESTION,
                        DEFAULT_FOCUS);
        return dialog.getResult();
    }

    /**
     * OK�E�L�����Z���`���̃��b�Z�[�W�{�b�N�X��\�����܂��B
     * <p>
     * �t�H�[�J�X�́uOK�v�{�^���ɐݒ肳��܂��B
     * </p>
     * 
     * @param message �{��
     * @return �I�������I�y���[�V����
     */
    public static int showYesNoCancel(String message) {
        return show(getDefaultTitle(), message, BUTTON_YESNOCANCEL,
                ICON_QUESTION);
    }

    /**
     * OK�E�L�����Z���`���̃��b�Z�[�W�{�b�N�X���t�H�[�J�X�{�^�����w�肵�ĕ\�����܂��B
     * 
     * @param message �{��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @return �I�������I�y���[�V����
     */
    public static int showYesNoCancel(String message, int focus) {
        return show(getDefaultTitle(), message, BUTTON_YESNOCANCEL,
                ICON_QUESTION, focus);
    }

    /**
     * �͂��E�������E�L�����Z���̃L���v�V������ύX�������b�Z�[�W�{�b�N�X��\�����܂��B
     * <p>
     * �t�H�[�J�X�́u�͂��v�{�^���ɐݒ肳��܂��B
     * </p>
     * 
     * @param message �{��
     * @param yesText �͂��{�^���̃L���v�V����
     * @param yesMnemonic �͂��{�^���̃j�[���j�b�N
     * @param noText �������{�^���̃L���v�V����
     * @param noMnemonic �������{�^���̃j�[���j�b�N
     * @return �I�������I�y���[�V����
     */
    public static int showYesNoCancel(String message, String yesText,
            char yesMnemonic, String noText, char noMnemonic) {
        return showYesNoCancel(getDefaultTitle(), message, yesText,
                yesMnemonic, noText, noMnemonic);
    }

    /**
     * �͂��E�������E�L�����Z���̃L���v�V������ύX�������b�Z�[�W�{�b�N�X���t�H�[�J�X�{�^�����w�肵�ĕ\�����܂��B
     * 
     * @param message �{��
     * @param yesText �͂��{�^���̃L���v�V����
     * @param yesMnemonic �͂��{�^���̃j�[���j�b�N
     * @param noText �������{�^���̃L���v�V����
     * @param noMnemonic �������{�^���̃j�[���j�b�N
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @return �I�������I�y���[�V����
     */
    public static int showYesNoCancel(String message, String yesText,
            char yesMnemonic, String noText, char noMnemonic, int focus) {
        return showYesNoCancel(getDefaultTitle(), message, yesText,
                yesMnemonic, noText, noMnemonic, focus);
    }

    /**
     * �͂��E�������E�L�����Z���̃L���v�V������ύX�������b�Z�[�W�{�b�N�X��\�����܂��B
     * <p>
     * �t�H�[�J�X�́u�͂��v�{�^���ɐݒ肳��܂��B
     * </p>
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param yesText �͂��{�^���̃L���v�V����
     * @param yesMnemonic �͂��{�^���̃j�[���j�b�N
     * @param noText �������{�^���̃L���v�V����
     * @param noMnemonic �������{�^���̃j�[���j�b�N
     * @return �I�������I�y���[�V����
     */
    public static int showYesNoCancel(String title, String message,
            String yesText, char yesMnemonic, String noText, char noMnemonic) {
        return showYesNoCancel(title, message, yesText, yesMnemonic, noText,
                noMnemonic, DEFAULT_FOCUS);
    }

    /**
     * �͂��E�������E�L�����Z���̃L���v�V������ύX�������b�Z�[�W�{�b�N�X��\�����܂��B
     * 
     * @param title �^�C�g��
     * @param message �{��
     * @param yesText �͂��{�^���̃L���v�V����
     * @param yesMnemonic �͂��{�^���̃j�[���j�b�N
     * @param noText �������{�^���̃L���v�V����
     * @param noMnemonic �������{�^���̃j�[���j�b�N
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @return �I�������I�y���[�V����
     */
    public static int showYesNoCancel(String title, String message,
            String yesText, char yesMnemonic, String noText, char noMnemonic,
            int focus) {
        ACMessageBoxDialog dialog = getInstance().createDialog(
                ACFrame.getInstance(), title, true);
        dialog.getYesButton().setText(yesText);
        dialog.getYesButton().setMnemonic(yesMnemonic);
        dialog.getNoButton().setText(noText);
        dialog.getNoButton().setMnemonic(noMnemonic);
        dialog.showInner(message, BUTTON_YESNOCANCEL, ICON_QUESTION, focus);
        return dialog.getResult();
    }

    /**
     * �f�t�H���g�Őݒ肳���E�B���h�E�^�C�g����Ԃ��܂��B
     * 
     * @return �f�t�H���g�Őݒ肳���E�B���h�E�^�C�g��
     */
    protected static String getDefaultTitle() {
        ACFrameEventProcesser eventProcesser = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventProcesser) {
            // ��ʑJ�ڑO�Ƀ��b�Z�[�W�{�b�N�X�̃f�t�H���g�^�C�g����ݒ肷��
            return eventProcesser.getDefaultMessageBoxTitle();
        }
        return "���b�Z�[�W";
    }

    /**
     * �C���X�^���X��Ԃ��܂��B
     * 
     * @return �C���X�^���X
     */
    protected static ACMessageBox getInstance() {
        if (singleton == null) {
            singleton = new ACMessageBox();
        }
        return singleton;
    }

    /**
     * �g���_�C�A���O�𐶐����܂��B
     * 
     * @param frame �e�t���[��
     * @param title �^�C�g��
     * @param modal ���[�_���ł��邩
     * @return �_�C�A���O
     */
    protected ACMessageBoxDialog createDialog(Frame frame, String title,
            boolean modal) {
        return new ACMessageBoxDialog(frame, title, modal);
    }

}
