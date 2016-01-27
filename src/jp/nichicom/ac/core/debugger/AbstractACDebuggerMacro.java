package jp.nichicom.ac.core.debugger;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.swing.FocusManager;
import javax.swing.JDialog;

import jp.nichicom.ac.component.AbstractACClearableRadioButtonGroup;
import jp.nichicom.ac.core.ACDialogChaine;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.io.ACFileUtilities;
import jp.nichicom.ac.io.ACSuffixFilenameFilter;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBoxDialog;

/**
 * マクロの基底クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/07/03
 */
public abstract class AbstractACDebuggerMacro implements ACDebuggerMacro {
    private Thread macroThread;
    private boolean requestMacroStop;
    private Robot robot;
    private File snapshotDirectory;
    private File[] snapshotFiles;
    private String snapshotSuffix;
    private ACSuffixFilenameFilter snapshotSuffixFilter;
    private int convertKeyCode = KeyEvent.VK_SPACE;

    /**
     * 変換キーのキーコード を返します。
     * 
     * @return convertKeyCode 変換キーのキーコード
     */
    public int getConvertKeyCode() {
        return convertKeyCode;
    }

    /**
     * 変換キーのキーコード を設定します。
     * 
     * @param convertKeyCode 変換キーのキーコード
     */
    public void setConvertKeyCode(int convertKeyCode) {
        this.convertKeyCode = convertKeyCode;
    }

    /**
     * コンストラクタです。
     */
    public AbstractACDebuggerMacro() {
        super();
    }

    public void executeMacro(String arg) {
        setRequestMacroStop(false);
        final String bridgeArg = arg;
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    executeMacroImpl(bridgeArg);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        });
        setMacroThread(t);
        getMacroThread().start();
    }

    /**
     * マクロ処理を実装します。
     * 
     * @param arg 引数
     * @throws Throwable 処理例外
     */
    public abstract void executeMacroImpl(String arg) throws Throwable;

    public void stopMacro() {
        setRequestMacroStop(true);
        setMacroThread(null);
    }

    /**
     * ファイル監視結果 を返します。
     * 
     * @return ファイル監視結果
     */
    private File[] getSnapshotFiles() {
        return snapshotFiles;
    }

    /**
     * 監視対象のファイルサフィックスフィルタ を返します。
     * 
     * @return 監視対象のファイルサフィックスフィルタ
     */
    private ACSuffixFilenameFilter getSnapshotSuffixFilter() {
        return snapshotSuffixFilter;
    }

    /**
     * 監視対象のフォルダ を設定します。
     * 
     * @param snapshotDirectory 監視対象のフォルダ
     */
    private void setSnapshotDirectory(File snapshotDirectory) {
        this.snapshotDirectory = snapshotDirectory;
    }

    /**
     * ファイル監視結果 を設定します。
     * 
     * @param snapshotFiles ファイル監視結果
     */
    private void setSnapshotFiles(File[] snapshotFiles) {
        this.snapshotFiles = snapshotFiles;
    }

    /**
     * 監視対象のファイルサフィックス を設定します。
     * 
     * @param snapshotSuffix 監視対象のファイルサフィックス
     */
    private void setSnapshotSuffix(String snapshotSuffix) {
        this.snapshotSuffix = snapshotSuffix;
        setSnapshotSuffixFilter(new ACSuffixFilenameFilter(snapshotSuffix));
    }

    /**
     * 監視対象のファイルサフィックスフィルタ を設定します。
     * 
     * @param snapshotSuffixFilter 監視対象のファイルサフィックスフィルタ
     */
    private void setSnapshotSuffixFilter(
            ACSuffixFilenameFilter snapshotSuffixFilter) {
        this.snapshotSuffixFilter = snapshotSuffixFilter;
    }

    /**
     * 指定コンポーネントをクリックします。
     * <p>
     * クリックするマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param component コンポーネント
     * @throws InterruptedException 処理例外
     */
    protected void clickForComponent(Object component)
            throws InterruptedException {
        clickForComponent(component, InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定コンポーネントをクリックします。
     * 
     * @param component コンポーネント
     * @param buttons クリックするマウスボタン
     * @throws InterruptedException 処理例外
     */
    protected void clickForComponent(Object component, int buttons)
            throws InterruptedException {
        Point p = ((Component) component).getLocationOnScreen();
        getRobot().mouseMove(p.x, p.y);
        getRobot().mousePress(buttons);
        getRobot().mouseRelease(buttons);
    }

    /**
     * 指定フィールドのコンポーネントをクリックします。
     * <p>
     * クリックするマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     */
    protected void clickForField(Object owner, String fieldName)
            throws Exception {
        clickForComponent(getFiled(owner, fieldName));
    }

    /**
     * 指定フィールドのコンポーネントをクリックします。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param buttons クリックするマウスボタン
     * @throws Exception 処理例外
     */
    protected void clickForField(Object owner, String fieldName, int buttons)
            throws Exception {
        clickForComponent(getFiled(owner, fieldName), buttons);
    }

    /**
     * 指定コンポーネントをドラッグします。
     * <p>
     * 押下するマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param component コンポーネント
     * @throws InterruptedException 処理例外
     */
    protected void dragForComponent(Object component)
            throws InterruptedException {
        dragForComponent(component, InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定コンポーネントをドラッグします。
     * 
     * @param component コンポーネント
     * @param buttons 押下するマウスボタン
     * @throws InterruptedException 処理例外
     */
    protected void dragForComponent(Object component, int buttons)
            throws InterruptedException {
        Point p = ((Component) component).getLocationOnScreen();
        getRobot().mouseMove(p.x, p.y);
        getRobot().mousePress(buttons);
    }

    /**
     * 指定フィールドのコンポーネントをドラッグします。
     * <p>
     * 押下するマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     */
    protected void dragForField(Object owner, String fieldName)
            throws Exception {
        dragForComponent(getFiled(owner, fieldName));
    }

    /**
     * 指定フィールドのコンポーネントをドラッグします。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param buttons 押下するマウスボタン
     * @throws Exception 処理例外
     */
    protected void dragForField(Object owner, String fieldName, int buttons)
            throws Exception {
        dragForComponent(getFiled(owner, fieldName), buttons);
    }

    /**
     * 指定リストボックスの要素をドラッグします。
     * <p>
     * 押下するマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param component リストボックス
     * @param index ドラッグ要素のインデックス
     * @throws Exception 処理例外
     */
    protected void dragForListBox(Object component, int index) throws Exception {
        dragForListBox(component, index, InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定リストボックスの要素をドラッグします。
     * 
     * @param component リストボックス
     * @param index ドラッグ要素のインデックス
     * @param buttons 押下するマウスボタン
     * @throws Exception 処理例外
     */
    protected void dragForListBox(Object component, int index, int buttons)
            throws Exception {
        setSelectedIndexForComponent(component, index);
        getMethod(component, "ensureIndexIsVisible", new Class[] { int.class },
                new Object[] { new Integer(index) });

        Object rect = getMethod(component, "getCellBounds", new Class[] {
                int.class, int.class }, new Object[] { new Integer(index),
                new Integer(index) });
        if (rect instanceof Rectangle) {
            Point p = ((Component) component).getLocationOnScreen();
            p.translate(((Rectangle) rect).x, ((Rectangle) rect).y);
            getRobot().mouseMove(p.x, p.y);
            getRobot().mousePress(buttons);
        }
    }

    /**
     * 指定フィールドのリストボックスの要素をドラッグします。
     * <p>
     * 押下するマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param index ドラッグ要素のインデックス
     * @throws Exception 処理例外
     */
    protected void dragForListBoxField(Object owner, String fieldName, int index)
            throws Exception {
        dragForListBox(getFiled(owner, fieldName), index,
                InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定フィールドのリストボックスの要素をドラッグします。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param index ドラッグ要素のインデックス
     * @param buttons 押下するマウスボタン
     * @throws Exception 処理例外
     */
    protected void dragForListBoxField(Object owner, String fieldName,
            int index, int buttons) throws Exception {
        dragForListBox(getFiled(owner, fieldName), index, buttons);
    }

    /**
     * 指定コンポーネントにドロップします。
     * <p>
     * 離すマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param component コンポーネント
     * @throws InterruptedException 処理例外
     */
    protected void dropForComponent(Object component)
            throws InterruptedException {
        dropForComponent(component, InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定コンポーネントにドロップします。
     * 
     * @param component コンポーネント
     * @param buttons 離すマウスボタン
     * @throws InterruptedException 処理例外
     */
    protected void dropForComponent(Object component, int buttons)
            throws InterruptedException {
        Point p = ((Component) component).getLocationOnScreen();
        getRobot().mouseMove(p.x, p.y);
        getRobot().mouseRelease(buttons);
    }

    /**
     * 指定フィールドのコンポーネントにドロップします。
     * <p>
     * 離すマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     */
    protected void dropForField(Object owner, String fieldName)
            throws Exception {
        dropForComponent(getFiled(owner, fieldName));
    }

    /**
     * 指定フィールドのコンポーネントにドロップします。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param buttons 離すマウスボタン
     * @throws Exception 処理例外
     */
    protected void dropForField(Object owner, String fieldName, int buttons)
            throws Exception {
        dropForComponent(getFiled(owner, fieldName), buttons);
    }

    /**
     * 指定リストボックスにドロップします。
     * <p>
     * 離すマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param component リストボックス
     * @param index ドロップ先の要素インデックス
     * @throws Exception 処理例外
     */
    protected void dropForListBox(Object component, int index) throws Exception {
        dropForListBox(component, index, InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定リストボックスにドロップします。
     * 
     * @param component リストボックス
     * @param index ドロップ先の要素インデックス
     * @param buttons 離すマウスボタン
     * @throws Exception 処理例外
     */
    protected void dropForListBox(Object component, int index, int buttons)
            throws Exception {
        setSelectedIndexForComponent(component, index);
        getMethod(component, "ensureIndexIsVisible", new Class[] { int.class },
                new Object[] { new Integer(index) });

        Object rect = getMethod(component, "getCellBounds", new Class[] {
                int.class, int.class }, new Object[] { new Integer(index),
                new Integer(index) });
        if (rect instanceof Rectangle) {
            Point p = ((Component) component).getLocationOnScreen();
            p.translate(((Rectangle) rect).x, ((Rectangle) rect).y);
            getRobot().mouseMove(p.x, p.y);
            getRobot().mouseRelease(buttons);
        }
    }

    /**
     * 指定フィールドのリストボックスにドロップします。
     * <p>
     * 離すマウスボタンとして1ボタン(左)を使用します。
     * </p>
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param index ドラッグ要素のインデックス
     * @throws Exception 処理例外
     */
    protected void dropForListBoxField(Object owner, String fieldName, int index)
            throws Exception {
        dropForListBox(getFiled(owner, fieldName), index,
                InputEvent.BUTTON1_MASK);
    }

    /**
     * 指定フィールドのリストボックスにドロップします。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param index ドラッグ要素のインデックス
     * @param buttons 離すマウスボタン
     * @throws Exception 処理例外
     */
    protected void dropForListBoxField(Object owner, String fieldName,
            int index, int buttons) throws Exception {
        dropForListBox(getFiled(owner, fieldName), index, buttons);
    }

    /**
     * 自身はフォーカスを有しないコンテナ状のコンポーネントにフォーカスをあてます。
     * 
     * @param container コンテナ
     * @throws Exception 処理例外
     */
    protected void focusContainer(Component container) throws Exception {
        if (container instanceof AbstractACClearableRadioButtonGroup) {
            ((AbstractACClearableRadioButtonGroup) container)
                    .requestChildFocus();
            waitForIdle(50);
        } else {
            container.transferFocus();
        }
        getRobot().waitForIdle();
    }

    /**
     * 指定フィールドの、自身はフォーカスを有しないコンテナ状のコンポーネントにフォーカスをあてます。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     */
    protected void focusContainerField(Object owner, String fieldName)
            throws Exception {
        focusContainer((Component) getFiled(owner, fieldName));
    }

    /**
     * 指定フィールドのコンポーネントにフォーカスをあてます。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     */
    protected void focusField(Object owner, String fieldName) throws Exception {
        requestFocus(getFiled(owner, fieldName));
    }

    /**
     * 指定メソッドで取得できるコンポーネントにフォーカスをあてます。
     * 
     * @param owner 所有者
     * @param methodName メソッド名
     * @throws Exception 処理例外
     */
    protected void focusMethod(Object owner, String methodName)
            throws Exception {
        requestFocus(getMethod(owner, methodName));
    }

    /**
     * 指定メソッドで取得できるコンポーネントにフォーカスをあてます。
     * 
     * @param owner 所有者
     * @param methodName メソッド名
     * @param paramClasses パラメタ型配列
     * @param paramValues パラメタ値配列
     * @throws Exception 処理例外
     */
    protected void focusMethod(Object owner, String methodName,
            Class[] paramClasses, Object[] paramValues) throws Exception {
        requestFocus(getMethod(owner, methodName, paramClasses, paramValues));
    }

    /**
     * 最後に表示したダイアログにフォーカスをあてて返します。
     * 
     * @return 最後に表示したダイアログ
     * @throws InterruptedException 処理例外
     */
    protected Component focusNowDialog() throws InterruptedException {
        waitForIdle();
        JDialog affair = ACDialogChaine.getInstance().getLastItem();
        if (affair != null) {
            Component f = affair;
            if (affair.getContentPane() != null) {
                f = affair.getContentPane();
            }
            requestFocus(f);
        }
        return affair;
    }

    /**
     * 現在の業務パネルにフォーカスをあてて返します。
     * 
     * @return 現在の業務パネル
     * @throws InterruptedException 処理例外
     */
    protected Component focusNowPanel() throws InterruptedException {
        Component affair = (Component) ACFrame.getInstance().getNowPanel();
        requestFocus(affair);
        return affair;
    }

    /**
     * 指定フィールドの値を返します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @return 値
     * @throws Exception
     */
    protected Object getFiled(Object owner, String fieldName) throws Exception {
        Class nowClass = owner.getClass();
        Field f;
        try {
            f = nowClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException ex) {
            f = null;
        }
        while (f == null) {
            nowClass = nowClass.getSuperclass();
            if (nowClass == null) {
                break;
            }
            try {
                f = nowClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                f = null;
            }
        }
        if (f != null) {
            f.setAccessible(true);
            return f.get(owner);
        }
        throw new NoSuchFieldException(fieldName);
    }

    /**
     * マクロ実行スレッド を返します。
     * 
     * @return マクロ実行スレッド
     */
    protected Thread getMacroThread() {
        return macroThread;
    }

    /**
     * 指定メソッドで取得できる値を返します。
     * 
     * @param owner 所有者
     * @param methodName メソッド名
     * @return 値
     * @throws Exception 処理例外
     */
    protected Object getMethod(Object owner, String methodName)
            throws Exception {
        return getMethod(owner, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * 指定メソッドで取得できる値を返します。
     * 
     * @param owner 所有者
     * @param methodName メソッド名
     * @param paramClasses パラメタ型配列
     * @param paramValues パラメタ値配列
     * @return 値
     * @throws Exception 処理例外
     */
    protected Object getMethod(Object owner, String methodName,
            Class[] paramClasses, Object[] paramValues) throws Exception {
        Class nowClass = owner.getClass();
        Method m;
        try {
            m = nowClass.getDeclaredMethod(methodName, paramClasses);
        } catch (NoSuchMethodException ex) {
            m = null;
        }
        while (m == null) {
            nowClass = nowClass.getSuperclass();
            if (nowClass == null) {
                break;
            }
            try {
                m = nowClass.getDeclaredMethod(methodName, paramClasses);
            } catch (NoSuchMethodException ex) {
                m = null;
            }
        }
        if (m != null) {
            m.setAccessible(true);
            return m.invoke(owner, paramValues);
        }
        throw new NoSuchMethodException(owner.getClass().getName() + "."
                + methodName);
    }

    /**
     * ロボットクラスを返します。
     * 
     * @return ロボットクラス
     */
    protected Robot getRobot() {
        try {
            if (robot == null) {
                robot = new Robot();
                robot.setAutoWaitForIdle(true);
                robot.setAutoDelay(15);
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return robot;
    }

    /**
     * 指定コンポーネントのgetSelectedIndexメソッドを呼び出します。
     * 
     * @param component コンポーネント
     * @throws Exception 処理例外
     * @return 値
     */
    protected int getSelectedIndexForComponent(Object component)
            throws Exception {
        return ACCastUtilities.toInt(getMethod(component, "getSelectedIndex"),
                -1);
    }

    /**
     * 指定フィールドのコンポーネントのgetSelectedIndexメソッドを呼び出します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     * @return 値
     */
    protected int getSelectedIndexForField(Object owner, String fieldName)
            throws Exception {
        return getSelectedIndexForComponent(getFiled(owner, fieldName));
    }

    /**
     * 監視対象のフォルダ を返します。
     * 
     * @return 監視対象のフォルダ
     */
    protected File getSnapshotDirectory() {
        return snapshotDirectory;
    }

    /**
     * 監視対象のファイルサフィックス を返します。
     * 
     * @return 監視対象のファイルサフィックス
     */
    protected String getSnapshotSuffix() {
        return snapshotSuffix;
    }

    /**
     * マクロ停止要求が出たか を返します。
     * 
     * @return マクロ停止要求が出たか
     */
    protected boolean isRequestMacroStop() {
        return requestMacroStop;
    }

    /**
     * 指定コンポーネントのisSelectedIndexメソッドを呼び出します。
     * 
     * @param component コンポーネント
     * @throws Exception 処理例外
     * @return 値
     */
    protected boolean isSelectedForComponent(Object component) throws Exception {
        return ACCastUtilities.toBoolean(getMethod(component, "isSelected"),
                false);
    }

    /**
     * 指定フィールドのコンポーネントのisSelectedメソッドを呼び出します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     * @return 値
     */
    protected boolean isSelectedForField(Object owner, String fieldName)
            throws Exception {
        return isSelectedForComponent(getFiled(owner, fieldName));
    }

    /**
     * 最後に実行したファイル名スナップショットと今現在のスナップショットを比較し、変更のあった最新のファイルを返します。
     * <p>
     * 変更がなければnullを返します。
     * </p>
     * 
     * @return 変更のあった最新のファイル
     */
    protected File modifiedFileNameMostLastUpdate() {
        File[] mement = getSnapshotFiles();
        snapshotFileName();
        File f = ACFileUtilities.getMostLastModifiedFile(ACFileUtilities
                .compareFileNames(mement, getSnapshotFiles()));
        setSnapshotFiles(mement);
        return f;
    }

    /**
     * メッセージボックスのCancelボタンを押下します。
     * 
     * @throws InterruptedException 処理例外
     */
    protected void pressMessageBoxCancelButton() throws InterruptedException {
        waitForIdle();
        Object dlg = ACDialogChaine.getInstance().getLastItem(
                ACMessageBoxDialog.class);
        if (dlg instanceof ACMessageBoxDialog) {
            ((ACMessageBoxDialog) dlg).getCancelButton().requestFocus();
            sendKey(KeyEvent.VK_ENTER);
            waitForIdle();
        }
    }

    /**
     * メッセージボックスのNoボタンを押下します。
     * 
     * @throws InterruptedException 処理例外
     */
    protected void pressMessageBoxNoButton() throws InterruptedException {
        waitForIdle();
        Object dlg = ACDialogChaine.getInstance().getLastItem(
                ACMessageBoxDialog.class);
        if (dlg instanceof ACMessageBoxDialog) {
            ((ACMessageBoxDialog) dlg).getNoButton().requestFocus();
            sendKey(KeyEvent.VK_ENTER);
            waitForIdle();
        }
    }

    /**
     * メッセージボックスのOKボタンを押下します。
     * 
     * @throws InterruptedException 処理例外
     */
    protected void pressMessageBoxOKButton() throws InterruptedException {
        waitForIdle();
        Object dlg = ACDialogChaine.getInstance().getLastItem(
                ACMessageBoxDialog.class);
        if (dlg instanceof ACMessageBoxDialog) {
            ((ACMessageBoxDialog) dlg).getOKButton().requestFocus();
            sendKey(KeyEvent.VK_ENTER);
            waitForIdle();
        }
    }

    /**
     * メッセージボックスのYesボタンを押下します。
     * 
     * @throws InterruptedException 処理例外
     */
    protected void pressMessageBoxYesButton() throws InterruptedException {
        waitForIdle();
        Object dlg = ACDialogChaine.getInstance().getLastItem(
                ACMessageBoxDialog.class);
        if (dlg instanceof ACMessageBoxDialog) {
            ((ACMessageBoxDialog) dlg).getYesButton().requestFocus();
            sendKey(KeyEvent.VK_ENTER);
            waitForIdle();
        }
    }

    /**
     * 指定フィールドのボタンを押下します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @throws Exception 処理例外
     */
    protected void pushButtonField(Object owner, String fieldName)
            throws Exception {
        focusField(owner, fieldName);
        waitForIdle(50);
        sendKey(KeyEvent.VK_ENTER);
        getRobot().waitForIdle();
    }

    /**
     * 指定コンポーネントにフォーカスをあてます。
     * 
     * @param component コンポーネント
     * @throws InterruptedException 処理例外
     */
    protected void requestFocus(Object component) throws InterruptedException {
        waitForIdle();
        ((Component) component).requestFocus();
    }

    /**
     * 指定キー入力を処理します。
     * 
     * @param keyCode キーコード
     */
    protected void sendKey(int keyCode) {
        getRobot().keyPress(keyCode);
        getRobot().keyRelease(keyCode);
    }

    /**
     * 指定キー入力を複数回処理します。
     * 
     * @param keyCode キーコード
     * @param count 繰り返し回数
     */
    protected void sendKey(int keyCode, int count) {
        for (int i = 0; i < count; i++) {
            sendKey(keyCode);
        }
    }

    /**
     * 指定キー入力を修飾キー付きで処理します。
     * 
     * @param keyCode キーコード
     * @param modified 修飾キー
     * @see #sendKeyAlt
     * @see #sendKeyShift
     * @see #sendKeyControl
     */
    protected void sendKey(int keyCode, int[] modified) {
        int end = modified.length;
        for (int i = 0; i < end; i++) {
            getRobot().keyPress(modified[i]);
        }
        sendKey(keyCode);
        for (int i = 0; i < end; i++) {
            getRobot().keyRelease(modified[i]);
        }
    }

    /**
     * 指定キー入力をALTを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyAlt(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT });
        waitForIdle();
    }

    /**
     * 指定キー入力をALT+Ctrlを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyAltControl(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * 指定キー入力をALT+Shiftを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyAltShift(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT, KeyEvent.VK_SHIFT });
        waitForIdle();
    }

    /**
     * 指定キー入力をALT+Shift+Ctrlを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyAltShiftControl(int keyCode)
            throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT, KeyEvent.VK_SHIFT,
                KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * 指定キー入力をCtrlを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyControl(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * 指定キー入力をShiftを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyShift(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_SHIFT });
        waitForIdle();
    }

    /**
     * 指定キー入力をShift+Ctrlを押した状態で処理します。
     * 
     * @param keyCode キーコード
     * @throws InterruptedException 処理例外
     */
    protected void sendKeyShiftControl(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * 無変換キー押下後に、指定の文字列を1文字ずつキー入力として処理します。
     * 
     * @param text 入力文字列
     * @throws InterruptedException 処理例外
     */
    protected void sendSingleByteText(String text) throws InterruptedException {
        sendKey(KeyEvent.VK_NONCONVERT);
        sendText(text);
    }

    /**
     * 指定の文字列を1文字ずつキー入力として処理します。
     * 
     * @param text 入力文字列
     * @throws InterruptedException 処理例外
     */
    protected void sendText(String text) throws InterruptedException {
        char[] chars = text.toCharArray();
        int end = chars.length;
        for (int i = 0; i < end; i++) {
            sendKey(chars[i]);
        }
        waitForIdle();
    }

    /**
     * かなキー押下後に、指定の文字列を1文字ずつキー入力として処理し、続けて変換キーとエンターキーを処理します。
     * 
     * @param text 入力文字列
     * @throws InterruptedException 処理例外
     */
    protected void sendWideByteConvertText(String text)
            throws InterruptedException {
        sendWideByteText(text);
        sendKey(getConvertKeyCode());
        sendKey(KeyEvent.VK_ENTER);
    }

    /**
     * かなキー押下後に、指定の文字列を1文字ずつキー入力として処理します。
     * 
     * @param text 入力文字列
     * @throws InterruptedException 処理例外
     */
    protected void sendWideByteText(String text) throws InterruptedException {
        sendKey(KeyEvent.VK_HIRAGANA);
        sendText(text);
    }

    /**
     * マクロ実行スレッド を設定します。
     * 
     * @param macroThread マクロ実行スレッド
     */
    protected void setMacroThread(Thread macroThread) {
        this.macroThread = macroThread;
    }

    /**
     * マクロ停止要求が出たか を設定します。
     * 
     * @param macroStoped マクロ停止要求が出たか
     */
    protected void setRequestMacroStop(boolean macroStoped) {
        this.requestMacroStop = macroStoped;
    }

    /**
     * ロボットクラスを設定します。
     * 
     * @param robot ロボットクラス
     */
    protected void setRobot(Robot robot) {
        this.robot = robot;
    }

    /**
     * 指定コンポーネントのsetSelectedメソッドを呼び出します。
     * 
     * @param component コンポーネント
     * @param selected 値
     * @throws Exception 処理例外
     */
    protected void setSelectedForComponent(Object component, boolean selected)
            throws Exception {
        getMethod(component, "setSelected", new Class[] { boolean.class },
                new Object[] { new Boolean(selected) });
    }

    /**
     * 指定フィールドのコンポーネントのsetSelectedメソッドを呼び出します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param selected 値
     * @throws Exception 処理例外
     */
    protected void setSelectedForField(Object owner, String fieldName,
            boolean selected) throws Exception {
        setSelectedForComponent(getFiled(owner, fieldName), selected);
    }

    /**
     * 指定コンポーネントのsetSelectedIndexメソッドを呼び出します。
     * 
     * @param component コンポーネント
     * @param index 値
     * @throws Exception 処理例外
     */
    protected void setSelectedIndexForComponent(Object component, int index)
            throws Exception {
        getMethod(component, "setSelectedIndex", new Class[] { int.class },
                new Object[] { new Integer(index) });
    }

    /**
     * 指定フィールドのコンポーネントのsetSelectedIndexメソッドを呼び出します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param index 値
     * @throws Exception 処理例外
     */
    protected void setSelectedIndexForField(Object owner, String fieldName,
            int index) throws Exception {
        setSelectedIndexForComponent(getFiled(owner, fieldName), index);
    }

    /**
     * 指定コンポーネントのsetTextメソッドを呼び出します。
     * 
     * @param component コンポーネント
     * @param text テキスト
     * @throws Exception 処理例外
     */
    protected void setTextForComponent(Object component, String text)
            throws Exception {
        getMethod(component, "setText", new Class[] { String.class },
                new Object[] { text });
    }

    /**
     * 指定フィールドのコンポーネントのsetTextメソッドを呼び出します。
     * 
     * @param owner 所有者
     * @param fieldName フィールド名
     * @param text テキスト
     * @throws Exception 処理例外
     */
    protected void setTextForField(Object owner, String fieldName, String text)
            throws Exception {
        setTextForComponent(getFiled(owner, fieldName), text);
    }

    /**
     * ファイル名スナップショットを実行します。
     */
    protected void snapshotFileName() {
        if (getSnapshotDirectory() != null) {
            if (getSnapshotDirectory().isDirectory()) {
                setSnapshotFiles(getSnapshotDirectory().listFiles(
                        getSnapshotSuffixFilter()));
            } else {
                setSnapshotFiles(new File[] { getSnapshotDirectory() });
            }
        }
    }

    /**
     * ファイル名スナップショットを実行します。
     * 
     * @param snapshotDirectory スナップショット対象のディレクトリ
     * @param snapshotSuffix スナップショット対象のファイルサフィックス
     */
    protected void snapshotFileName(String snapshotDirectory,
            String snapshotSuffix) {
        snapshotFileNameSetting(snapshotDirectory, snapshotSuffix);
        snapshotFileName();
    }

    /**
     * ファイル名スナップショットの設定を行います。
     * 
     * @param snapshotDirectory スナップショット対象のディレクトリ
     * @param snapshotSuffix スナップショット対象のファイルサフィックス
     */
    protected void snapshotFileNameSetting(String snapshotDirectory,
            String snapshotSuffix) {
        setSnapshotDirectory(new File(snapshotDirectory));
        setSnapshotSuffix(snapshotSuffix);
    }

    /**
     * Javaプロセスがフォーカスを所有するまで待機します。
     * 
     * @throws InterruptedException 処理例外
     * @return マクロ停止要求が出たか
     */
    protected boolean waitForActive() throws InterruptedException {
        while (FocusManager.getCurrentManager().getFocusOwner() == null) {
            waitForIdle(200);
        }
        return isRequestMacroStop();
    }

    /**
     * 入力を受付可能になるまで待機します。
     * 
     * @throws InterruptedException 処理例外
     * @return マクロ停止要求が出たか
     */
    protected boolean waitForIdle() throws InterruptedException {
        getRobot().waitForIdle();
        return waitForActive();
    }

    /**
     * 指定ミリ秒待った後、入力を受付可能になるまで待機します。
     * 
     * @param millis 待機するミリ秒
     * @throws InterruptedException 処理例外
     * @return マクロ停止要求が出たか
     */
    protected boolean waitForIdle(long millis) throws InterruptedException {
        Thread.sleep(millis);
        return waitForIdle();
    }

}
