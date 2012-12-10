package jp.nichicom.ac.util.splash;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * 中断キー付きスプラッシュです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/02/09
 */
public class ACStopButtonSplash extends ACSplash {
    private ACLabel stopMessageLabel;
    private boolean stopRequested;
    private int stopKeyCode;
    private boolean firstLock;

    /**
     * 中断を要求するキーコード を返します。
     * <p>
     * 次の値のみ許容します。<br/> java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK<br/>
     * java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK<br/>
     * java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK<br/>
     * java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK
     * </p>
     * 
     * @return 中断を要求するキーコード
     */
    public int getStopKeyCode() {
        return stopKeyCode;
    }

    /**
     * 中断を要求するキーコード を設定します。
     * <p>
     * 次の値のみ許容します。<br/> java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK<br/>
     * java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK<br/>
     * java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK<br/>
     * java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK
     * </p>
     * 
     * @param stopKeyCode 中断を要求するキーコード
     */
    public void setStopKeyCode(int stopKeyCode) {
        this.stopKeyCode = stopKeyCode;
    }

    /**
     * 中断を要求したか を返します。
     * 
     * @return 中断を要求したか
     */
    public boolean isStopRequested() {
        return stopRequested;
    }

    /**
     * 中断を要求したか を設定します。
     * 
     * @param stopRequest 中断を要求したか
     */
    public void setStopRequested(boolean stopRequest) {
        this.stopRequested = stopRequest;
    }

    /**
     * 中断メッセージ領域 を返します。
     * 
     * @return 中断メッセージ領域
     */
    protected ACLabel getStopMessageLabel() {
        if (stopMessageLabel == null) {
            stopMessageLabel = createStop();
            stopMessageLabel.setText("ScrollLock キーで中断します。");
        }
        return stopMessageLabel;
    }

    /**
     * 中断メッセージ領域を生成します。
     * 
     * @return 中断メッセージ領域
     */
    public ACLabel createStop() {
        return new ACLabel();
    }

    /**
     * コンストラクタです。
     */
    public ACStopButtonSplash() {
        super();
    }
    protected void beginThread() {
        firstLock = Toolkit.getDefaultToolkit().getLockingKeyState(getStopKeyCode());
    }

    protected void processThread() {
        if (Toolkit.getDefaultToolkit().getLockingKeyState(getStopKeyCode())!=firstLock) {
            setStopRequested(true);
            setValidThread(false);
        }
    }

    protected void initComponent() {
        super.initComponent();
        stopRequested = false;
        stopKeyCode = KeyEvent.VK_SCROLL_LOCK;
    }

    protected boolean addExpands(ACPanel container) {
        container.add(getStopMessageLabel(), VRLayout.CLIENT);
        return true;
    }

    protected Dimension createDefaultWindowSize() {
        return new Dimension(400, 140);
    }
}
