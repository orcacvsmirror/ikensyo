package jp.nichicom.ac.util.splash;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * ���f�L�[�t���X�v���b�V���ł��B
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
     * ���f��v������L�[�R�[�h ��Ԃ��܂��B
     * <p>
     * ���̒l�̂݋��e���܂��B<br/> java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK<br/>
     * java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK<br/>
     * java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK<br/>
     * java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK
     * </p>
     * 
     * @return ���f��v������L�[�R�[�h
     */
    public int getStopKeyCode() {
        return stopKeyCode;
    }

    /**
     * ���f��v������L�[�R�[�h ��ݒ肵�܂��B
     * <p>
     * ���̒l�̂݋��e���܂��B<br/> java.awt.event.KeyEvent#VK_CAPS_LOCK VK_CAPS_LOCK<br/>
     * java.awt.event.KeyEvent#VK_NUM_LOCK VK_NUM_LOCK<br/>
     * java.awt.event.KeyEvent#VK_SCROLL_LOCK VK_SCROLL_LOCK<br/>
     * java.awt.event.KeyEvent#VK_KANA_LOCK VK_KANA_LOCK
     * </p>
     * 
     * @param stopKeyCode ���f��v������L�[�R�[�h
     */
    public void setStopKeyCode(int stopKeyCode) {
        this.stopKeyCode = stopKeyCode;
    }

    /**
     * ���f��v�������� ��Ԃ��܂��B
     * 
     * @return ���f��v��������
     */
    public boolean isStopRequested() {
        return stopRequested;
    }

    /**
     * ���f��v�������� ��ݒ肵�܂��B
     * 
     * @param stopRequest ���f��v��������
     */
    public void setStopRequested(boolean stopRequest) {
        this.stopRequested = stopRequest;
    }

    /**
     * ���f���b�Z�[�W�̈� ��Ԃ��܂��B
     * 
     * @return ���f���b�Z�[�W�̈�
     */
    protected ACLabel getStopMessageLabel() {
        if (stopMessageLabel == null) {
            stopMessageLabel = createStop();
            stopMessageLabel.setText("ScrollLock �L�[�Œ��f���܂��B");
        }
        return stopMessageLabel;
    }

    /**
     * ���f���b�Z�[�W�̈�𐶐����܂��B
     * 
     * @return ���f���b�Z�[�W�̈�
     */
    public ACLabel createStop() {
        return new ACLabel();
    }

    /**
     * �R���X�g���N�^�ł��B
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
