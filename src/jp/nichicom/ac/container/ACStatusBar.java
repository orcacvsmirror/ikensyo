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
 * �X�e�[�^�X�o�[�ł��B
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

    private SimpleDateFormat dateFormatObject; // �t�H�[�}�b�g�p�I�u�W�F�N�g
    private JLabel nowTime;
    private JLabel numLock;

    private NCStatusBarWindowAdapter statusWindowsAdapter;

    private TimerTask task;

    private Timer tmrMain;

    /**
     * �^�C�}�[���~�����܂��B
     * <p>
     * �J�����Ɏ����I�ɂ͌Ă΂�Ȃ��̂Ŗ����I�ɌĂт܂��B
     * </p>
     */
    public void cancelTimer() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    /**
     * �Ď��L�[�̏�Ԃ��m�F���܂��B
     */
    public void checkLookingKeyState() {
        // Mac�Ή�
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
     * ��ʊg��k�����i ��Ԃ��܂��B
     * 
     * @return ��ʊg��k�����i
     */
    public VRWindowExpander getExpander() {
        if (expander == null) {
            expander = new VRWindowExpander();
        }
        return expander;
    }

    /**
     * CAPS_LOCK�\���p�̃��x�� ��Ԃ��܂��B
     * 
     * @return CAPS_LOCK�\���p�̃��x��
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
     * ���ݓ����\���p�̃��x�� ��Ԃ��܂��B
     * 
     * @return ���ݓ����\���p�̃��x��
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
     * NUM_LOCK�\���p�̃��x�� ��Ԃ��܂��B
     * 
     * @return NUM_LOCK�\���p�̃��x��
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
     * �L�[���b�N��Ԃ�\�����邩��Ԃ��܂��B
     * 
     * @return �L�[���b�N��Ԃ�\�����邩
     */
    public boolean isLockStatusVisible() {
        return getCapsLock().isVisible() || getNumLock().isVisible();
    }

    /**
     * ���ݓ�����\�����邩��Ԃ��܂��B
     * 
     * @return ���ݓ�����\�����邩
     */
    public boolean isNowTimeVisible() {
        return getNowTime().isVisible();
    }

    /**
     * ��ʊg��k�����i ��\�����邩��Ԃ��܂��B
     * 
     * @return ��ʊg��k�����i��\�����邩
     */
    public boolean isExpanderVisible() {
        return getExpander().isVisible();
    }

    /**
     * ��ʊg��k�����i ��ݒ肵�܂��B
     * 
     * @param expander ��ʊg��k�����i
     */
    public void setExpander(VRWindowExpander expander) {
        this.expander = expander;
    }

    /**
     * CAPS_LOCK�\���p�̃��x�� ��ݒ肵�܂��B
     * 
     * @param capLock CAPS_LOCK�\���p�̃��x��
     */
    public void setCapsLock(JLabel capLock) {
        this.capsLock = capLock;
    }

    /**
     * �L�[���b�N��Ԃ�\�����邩��ݒ肵�܂��B
     * 
     * @param lockStatusVisible �L�[���b�N��Ԃ�\�����邩
     */
    public void setLockStatusVisible(boolean lockStatusVisible) {
        getCapsLock().setVisible(lockStatusVisible);
        getNumLock().setVisible(lockStatusVisible);
    }

    /**
     * ���ݓ����\���p�̃��x�� ��ݒ肵�܂��B
     * 
     * @param nowTime ���ݓ����\���p�̃��x��
     */
    public void setNowTime(JLabel nowTime) {
        this.nowTime = nowTime;
    }

    /**
     * ���ݓ�����\�����邩��ݒ肵�܂��B
     * 
     * @param lockStatusVisible ���ݓ�����\�����邩
     */
    public void setNowTimeVisible(boolean lockStatusVisible) {
        getNowTime().setVisible(lockStatusVisible);
    }

    /**
     * NUM_LOCK�\���p�̃��x�� ��ݒ肵�܂��B
     * 
     * @param numLock NUM_LOCK�\���p�̃��x��
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
                // �^�C�}�[�C�x���g
                setDate();
            }
        };

        tmrMain.schedule(task, 1000, 1000);

        // Mac�Ή�(Mac�L�[�{�[�h�ł�CapsLock/NumLock�̊T�O���Ȃ�)
        String osName = System.getProperty("os.name");
        if ((osName != null) && (osName.startsWith("Mac"))) {
            canLookingKeyState = false;
        } else {
            canLookingKeyState = true;
            checkLookingKeyState();
        }
    }

    /**
     * ���ݓ����̕\����ݒ肵�܂��B
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
