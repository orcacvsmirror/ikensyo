package jp.nichicom.ac.container;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import jp.nichicom.vr.component.VRWindowExpander;
import jp.nichicom.vr.container.VRStatusBar;
import jp.nichicom.vr.layout.VRLayout;

/**
 * ステータスバーです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 * @see ACPanel
 */

public class ACStatusBar extends VRStatusBar {

    private boolean canLookingKeyState;
    private JLabel capsLock;
    private VRWindowExpander expander;

    private String dateAndTime;

    private SimpleDateFormat dateFormatObject; // フォーマット用オブジェクト
    private JLabel nowTime;
    private JLabel numLock;

    private NCStatusBarWindowAdapter statusWindowsAdapter;

    private TimerTask task;

    private Timer tmrMain;

    /**
     * タイマーを停止させます。
     * <p>
     * 開放時に自動的には呼ばれないので明示的に呼びます。
     * </p>
     */
    public void cancelTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    /**
     * 監視キーの状態を確認します。
     */
    public void checkLookingKeyState() {
        // Mac対応
        if (canLookingKeyState) {
            try {
                getCapsLock().setEnabled(
                        Toolkit.getDefaultToolkit().getLockingKeyState(
                                KeyEvent.VK_CAPS_LOCK));
                getNumLock().setEnabled(
                        Toolkit.getDefaultToolkit().getLockingKeyState(
                                KeyEvent.VK_NUM_LOCK));
                repaint();
            } catch (Exception ex) {
                getCapsLock().setEnabled(false);
                getNumLock().setEnabled(false);
                canLookingKeyState = false;
            }
        }
    }

    /**
     * 画面拡大縮小部品 を返します。
     * 
     * @return 画面拡大縮小部品
     */
    public VRWindowExpander getExpander() {
        if (expander == null) {
            expander = new VRWindowExpander();
        }
        return expander;
    }

    /**
     * CAPS_LOCK表示用のラベル を返します。
     * 
     * @return CAPS_LOCK表示用のラベル
     */
    public JLabel getCapsLock() {
        if (capsLock == null) {
            capsLock = new JLabel();
            capsLock
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            capsLock.setText(" CAPS ");
        }
        return capsLock;
    }

    /**
     * 現在日時表示用のラベル を返します。
     * 
     * @return 現在日時表示用のラベル
     */
    public JLabel getNowTime() {
        if (nowTime == null) {
            nowTime = new JLabel();
            nowTime
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            nowTime.setText("0000/00/00 00:00");

        }
        return nowTime;
    }

    /**
     * NUM_LOCK表示用のラベル を返します。
     * 
     * @return NUM_LOCK表示用のラベル
     */
    public JLabel getNumLock() {
        if (numLock == null) {
            numLock = new JLabel();
            numLock
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
            numLock.setText("  NUM  ");

        }
        return numLock;
    }

    /**
     * キーロック状態を表示するかを返します。
     * 
     * @return キーロック状態を表示するか
     */
    public boolean isLockStatusVisible() {
        return getCapsLock().isVisible() || getNumLock().isVisible();
    }

    /**
     * 現在日時を表示するかを返します。
     * 
     * @return 現在日時を表示するか
     */
    public boolean isNowTimeVisible() {
        return getNowTime().isVisible();
    }

    /**
     * 画面拡大縮小部品 を表示するかを返します。
     * 
     * @return 画面拡大縮小部品を表示するか
     */
    public boolean isExpanderVisible() {
        return getExpander().isVisible();
    }

    /**
     * 画面拡大縮小部品 を設定します。
     * 
     * @param expander 画面拡大縮小部品
     */
    public void setExpander(VRWindowExpander expander) {
        this.expander = expander;
    }

    /**
     * CAPS_LOCK表示用のラベル を設定します。
     * 
     * @param capLock CAPS_LOCK表示用のラベル
     */
    public void setCapsLock(JLabel capLock) {
        this.capsLock = capLock;
    }

    /**
     * キーロック状態を表示するかを設定します。
     * 
     * @param lockStatusVisible キーロック状態を表示するか
     */
    public void setLockStatusVisible(boolean lockStatusVisible) {
        getCapsLock().setVisible(lockStatusVisible);
        getNumLock().setVisible(lockStatusVisible);
    }

    /**
     * 現在日時表示用のラベル を設定します。
     * 
     * @param nowTime 現在日時表示用のラベル
     */
    public void setNowTime(JLabel nowTime) {
        this.nowTime = nowTime;
    }

    /**
     * 現在日時を表示するかを設定します。
     * 
     * @param lockStatusVisible 現在日時を表示するか
     */
    public void setNowTimeVisible(boolean lockStatusVisible) {
        getNowTime().setVisible(lockStatusVisible);
    }

    /**
     * NUM_LOCK表示用のラベル を設定します。
     * 
     * @param numLock NUM_LOCK表示用のラベル
     */
    public void setNumLock(JLabel numLock) {
        this.numLock = numLock;
    }

    protected void finalize() throws Throwable {
        cancelTimer();
        super.finalize();
    }

    protected void initComponent() {
        super.initComponent();

        add(getExpander(), VRLayout.EAST);
        add(getNowTime(), VRLayout.EAST);
        add(getCapsLock(), VRLayout.EAST);
        add(getNumLock(), VRLayout.EAST);

        InputMap im = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_CAPS_LOCK,
                KeyEvent.SHIFT_DOWN_MASK), "lock");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_NUM_LOCK, 0), "lock");

        this.getActionMap().put("lock", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                checkLookingKeyState();
            }
        });

        statusWindowsAdapter = new NCStatusBarWindowAdapter(this);

        addHierarchyListener(new NCStatusBarHierarchyAdapter(this));

        dateFormatObject = new SimpleDateFormat(" yyyy/M/d k:mm ");

        setDate();

        tmrMain = new Timer();

        task = new TimerTask() {
            public void run() {
                // タイマーイベント
                setDate();
            }
        };

        tmrMain.schedule(task, 1000, 1000);

        // Mac対応(MacキーボードではCapsLock/NumLockの概念がない)
        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.startsWith("Mac"))) {
            canLookingKeyState = false;
        } else {
            canLookingKeyState = true;
            checkLookingKeyState();
        }
    }

    /**
     * 現在日時の表示を設定します。
     */
    protected synchronized void setDate() {
        Calendar ca = Calendar.getInstance(TimeZone.getDefault(), Locale.JAPAN);
        String s = dateFormatObject.format(ca.getTime());

        if (!s.equals(dateAndTime)) {
            dateAndTime = s;
            getNowTime().setText(s);
            repaint();
        }
    }

    public void setForeground(Color foreground) {
        super.setForeground(foreground);
        getCapsLock().setForeground(foreground);
        getNumLock().setForeground(foreground);
        getNowTime().setForeground(foreground);
        getExpander().setForeground(foreground);
    }

    public void setBackground(Color background) {
        super.setBackground(background);
        getCapsLock().setBackground(background);
        getNumLock().setBackground(background);
        getNowTime().setBackground(background);
        getExpander().setBackground(background);
    }

    protected class NCStatusBarHierarchyAdapter implements HierarchyListener {
        private ACStatusBar adaptee;
        private Window oldFW;

        public NCStatusBarHierarchyAdapter(ACStatusBar adaptee) {
            this.adaptee = adaptee;
        }

        public void hierarchyChanged(HierarchyEvent e) {
            Window fw = SwingUtilities.getWindowAncestor(adaptee);
            if (oldFW != null) {
                oldFW.removeWindowListener(statusWindowsAdapter);
            }
            oldFW = fw;
            if (oldFW != null) {
                oldFW.addWindowListener(statusWindowsAdapter);
            }
        }
    }

    protected class NCStatusBarWindowAdapter extends WindowAdapter {
        private ACStatusBar adaptee;

        public NCStatusBarWindowAdapter(ACStatusBar adaptee) {
            this.adaptee = adaptee;
        }

        public void windowActivated(WindowEvent e) {
            adaptee.checkLookingKeyState();
        }

    }
}
