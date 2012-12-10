package jp.nichicom.ac.util;

import java.awt.Component;
import java.awt.Frame;

import javax.swing.JOptionPane;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;

/**
 * 汎用メッセージボックスクラスです。
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
     * キャンセルボタンを表すボタン定数です。
     */
    public static final int BUTTON_CANCEL = 2;
    /**
     * いいえボタンを表すボタン定数です。
     */
    public static final int BUTTON_NO = 8;
    /**
     * ボタンなしを表すボタン定数です。
     */
    public static final int BUTTON_NONE = 0;
    /**
     * OKボタンを表すボタン定数です。
     */
    public static final int BUTTON_OK = 1;
    /**
     * OK・キャンセルボタンを表すボタン定数です。
     */
    public static final int BUTTON_OKCANCEL = BUTTON_OK | BUTTON_CANCEL;
    /**
     * はいボタンを表すボタン定数です。
     */
    public static final int BUTTON_YES = 4;
    /**
     * はい・いいえボタンを表すボタン定数です。
     */
    public static final int BUTTON_YESNO = BUTTON_YES | BUTTON_NO;
    /**
     * はい・いいえ・キャンセルボタンを表すボタン定数です。
     */
    public static final int BUTTON_YESNOCANCEL = BUTTON_YES | BUTTON_NO
            | BUTTON_CANCEL;

    /**
     * キャンセルボタンを表すフォーカス定数です。
     */
    public static final int FOCUS_CANCEL = 0;
    /**
     * いいえボタンを表すフォーカス定数です。
     */
    public static final int FOCUS_NO = 3;
    /**
     * 指定なしを表すフォーカス定数です。
     */
    public static final int FOCUS_NONE = -1;
    /**
     * OKボタンを表すフォーカス定数です。
     */
    public static final int FOCUS_OK = 1;
    /**
     * はいボタンを表すフォーカス定数です。
     */
    public static final int FOCUS_YES = 2;

    /**
     * 感嘆符を表すアイコン定数です。
     */
    public static final int ICON_EXCLAMATION = 3;
    /**
     * 情報を表すアイコン定数です。
     */
    public static final int ICON_INFOMATION = 1;
    /**
     * 指定なしを表すアイコン定数です。
     */
    public static final int ICON_NONE = 0;
    /**
     * 疑問符を表すアイコン定数です。
     */
    public static final int ICON_QUESTION = 2;

    /**
     * 停止を表すアイコン定数です。
     */
    public static final int ICON_STOP = 4;

    /**
     * キャンセルボタン押下を表す結果定数です。
     */
    public static final int RESULT_CANCEL = JOptionPane.CANCEL_OPTION;
    /**
     * いいえボタンを表す結果定数です。
     */
    public static final int RESULT_NO = JOptionPane.NO_OPTION;
    /**
     * OKボタン押下を表す結果定数です。
     */
    public static final int RESULT_OK = JOptionPane.OK_OPTION;
    /**
     * はいボタン押下を表す結果定数です。
     */
    public static final int RESULT_YES = JOptionPane.YES_OPTION;

    /**
     * 既定のボタンを表すボタン定数です。
     */
    public static final int DEFAULT_BUTTON = BUTTON_OK;
    /**
     * 既定のフォーカスを表すフォーカス定数です。
     */
    public static final int DEFAULT_FOCUS = FOCUS_NONE;
    /**
     * 既定のアイコンを表すアイコン定数です。
     */
    public static final int DEFAULT_ICON = ICON_INFOMATION;

    private static ACMessageBox singleton;

    /**
     * メッセージボックスを表示します。
     * 
     * @param message 本文
     * @return 選択したオペレーション
     */
    public static int show(String message) {
        return show(getDefaultTitle(), message, DEFAULT_BUTTON, DEFAULT_ICON,
                DEFAULT_FOCUS);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param message 本文
     * @param expands 拡張領域
     * @param resizable 拡大縮小可能か
     * @return 選択したオペレーション
     */
    public static int show(String message, Component expands, boolean resizable) {
        return show(getDefaultTitle(), message, DEFAULT_BUTTON, DEFAULT_ICON,
                DEFAULT_FOCUS, expands, resizable);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param message 本文
     * @param buttons ボタン形式
     * @return 選択したオペレーション
     */
    public static int show(String message, int buttons) {
        return show(getDefaultTitle(), message, buttons, DEFAULT_ICON,
                DEFAULT_FOCUS);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @return 選択したオペレーション
     */
    public static int show(String message, int buttons, int icon) {
        return show(message, buttons, icon, DEFAULT_FOCUS);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @param focus フォーカスを当てるボタン
     * @return 選択したオペレーション
     */
    public static int show(String message, int buttons, int icon, int focus) {
        ACMessageBoxDialog dialog = getInstance().createDialog(
                ACFrame.getInstance(), getDefaultTitle(), true);
        dialog.showInner(message, buttons, icon, focus);
        return dialog.getResult();
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param title タイトル
     * @param message 本文
     * @param buttons ボタン形式
     * @return 選択したオペレーション
     */
    public static int show(String title, String message, int buttons) {
        return show(title, message, buttons, DEFAULT_ICON);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param title タイトル
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @return 選択したオペレーション
     */
    public static int show(String title, String message, int buttons, int icon) {
        return show(message, buttons, icon, DEFAULT_FOCUS);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param title タイトル
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @param focus フォーカスを当てるボタン
     * @return 選択したオペレーション
     */
    public static int show(String title, String message, int buttons, int icon,
            int focus) {
        return show(title, message, buttons, icon, focus, null, false);
    }

    /**
     * メッセージボックスを表示します。
     * 
     * @param title タイトル
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @param focus フォーカスを当てるボタン
     * @param expands 拡張領域
     * @param resizable 拡大縮小可能か
     * @return 選択したオペレーション
     */
    public static int show(String title, String message, int buttons, int icon,
            int focus, Component expands, boolean resizable) {
        ACMessageBoxDialog dialog = getInstance().createDialog(
                ACFrame.getInstance(), title, true);
        dialog.showInner(message, buttons, icon, focus, expands, resizable);
        return dialog.getResult();
    }

    /**
     * 警告用のメッセージボックスを表示します。
     * 
     * @param message 本文
     * @return 選択したオペレーション
     */
    public static int showExclamation(String message) {
        return show(getDefaultTitle(), message, DEFAULT_BUTTON,
                ICON_EXCLAMATION);
    }

    /**
     * OK・キャンセル形式のメッセージボックスを表示します。
     * <p>
     * フォーカスは「OK」ボタンに設定されます。
     * </p>
     * 
     * @param message 本文
     * @return 選択したオペレーション
     */
    public static int showOkCancel(String message) {
        return show(getDefaultTitle(), message, BUTTON_OKCANCEL, ICON_QUESTION);
    }

    /**
     * OK・キャンセル形式のメッセージボックスをフォーカスボタンを指定して表示します。
     * 
     * @param message 本文
     * @param focus フォーカスを当てるボタン
     * @return 選択したオペレーション
     */
    public static int showOkCancel(String message, int focus) {
        return show(getDefaultTitle(), message, BUTTON_OKCANCEL, ICON_QUESTION,
                focus);
    }

    /**
     * OK・キャンセルのキャプションを変更したメッセージボックスを表示します。
     * <p>
     * フォーカスは「OK」ボタンに設定されます。
     * </p>
     * 
     * @param message 本文
     * @param okText OKボタンのキャプション
     * @param okMnemonic OKボタンのニーモニック
     * @return 選択したオペレーション
     */
    public static int showOkCancel(String message, String okText,
            char okMnemonic) {
        return showOkCancel(getDefaultTitle(), message, okText, okMnemonic);
    }

    /**
     * OK・キャンセルのキャプションを変更したメッセージボックスを表示します。
     * <p>
     * フォーカスは「OK」ボタンに設定されます。
     * </p>
     * 
     * @param title タイトル
     * @param message 本文
     * @param okText OKボタンのキャプション
     * @param okMnemonic OKボタンのニーモニック
     * @return 選択したオペレーション
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
     * OK・キャンセル形式のメッセージボックスを表示します。
     * <p>
     * フォーカスは「OK」ボタンに設定されます。
     * </p>
     * 
     * @param message 本文
     * @return 選択したオペレーション
     */
    public static int showYesNoCancel(String message) {
        return show(getDefaultTitle(), message, BUTTON_YESNOCANCEL,
                ICON_QUESTION);
    }

    /**
     * OK・キャンセル形式のメッセージボックスをフォーカスボタンを指定して表示します。
     * 
     * @param message 本文
     * @param focus フォーカスを当てるボタン
     * @return 選択したオペレーション
     */
    public static int showYesNoCancel(String message, int focus) {
        return show(getDefaultTitle(), message, BUTTON_YESNOCANCEL,
                ICON_QUESTION, focus);
    }

    /**
     * はい・いいえ・キャンセルのキャプションを変更したメッセージボックスを表示します。
     * <p>
     * フォーカスは「はい」ボタンに設定されます。
     * </p>
     * 
     * @param message 本文
     * @param yesText はいボタンのキャプション
     * @param yesMnemonic はいボタンのニーモニック
     * @param noText いいえボタンのキャプション
     * @param noMnemonic いいえボタンのニーモニック
     * @return 選択したオペレーション
     */
    public static int showYesNoCancel(String message, String yesText,
            char yesMnemonic, String noText, char noMnemonic) {
        return showYesNoCancel(getDefaultTitle(), message, yesText,
                yesMnemonic, noText, noMnemonic);
    }

    /**
     * はい・いいえ・キャンセルのキャプションを変更したメッセージボックスをフォーカスボタンを指定して表示します。
     * 
     * @param message 本文
     * @param yesText はいボタンのキャプション
     * @param yesMnemonic はいボタンのニーモニック
     * @param noText いいえボタンのキャプション
     * @param noMnemonic いいえボタンのニーモニック
     * @param focus フォーカスを当てるボタン
     * @return 選択したオペレーション
     */
    public static int showYesNoCancel(String message, String yesText,
            char yesMnemonic, String noText, char noMnemonic, int focus) {
        return showYesNoCancel(getDefaultTitle(), message, yesText,
                yesMnemonic, noText, noMnemonic, focus);
    }

    /**
     * はい・いいえ・キャンセルのキャプションを変更したメッセージボックスを表示します。
     * <p>
     * フォーカスは「はい」ボタンに設定されます。
     * </p>
     * 
     * @param title タイトル
     * @param message 本文
     * @param yesText はいボタンのキャプション
     * @param yesMnemonic はいボタンのニーモニック
     * @param noText いいえボタンのキャプション
     * @param noMnemonic いいえボタンのニーモニック
     * @return 選択したオペレーション
     */
    public static int showYesNoCancel(String title, String message,
            String yesText, char yesMnemonic, String noText, char noMnemonic) {
        return showYesNoCancel(title, message, yesText, yesMnemonic, noText,
                noMnemonic, DEFAULT_FOCUS);
    }

    /**
     * はい・いいえ・キャンセルのキャプションを変更したメッセージボックスを表示します。
     * 
     * @param title タイトル
     * @param message 本文
     * @param yesText はいボタンのキャプション
     * @param yesMnemonic はいボタンのニーモニック
     * @param noText いいえボタンのキャプション
     * @param noMnemonic いいえボタンのニーモニック
     * @param focus フォーカスを当てるボタン
     * @return 選択したオペレーション
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
     * デフォルトで設定されるウィンドウタイトルを返します。
     * 
     * @return デフォルトで設定されるウィンドウタイトル
     */
    protected static String getDefaultTitle() {
        ACFrameEventProcesser eventProcesser = ACFrame.getInstance()
                .getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventProcesser) {
            // 画面遷移前にメッセージボックスのデフォルトタイトルを設定する
            return eventProcesser.getDefaultMessageBoxTitle();
        }
        return "メッセージ";
    }

    /**
     * インスタンスを返します。
     * 
     * @return インスタンス
     */
    protected static ACMessageBox getInstance() {
        if (singleton == null) {
            singleton = new ACMessageBox();
        }
        return singleton;
    }

    /**
     * 拡張ダイアログを生成します。
     * 
     * @param frame 親フレーム
     * @param title タイトル
     * @param modal モーダルであるか
     * @return ダイアログ
     */
    protected ACMessageBoxDialog createDialog(Frame frame, String title,
            boolean modal) {
        return new ACMessageBoxDialog(frame, title, modal);
    }

}
