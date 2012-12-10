package jp.nichicom.ac.util.splash;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.layout.VRLayout;

/**
 * �ėp�X�v���b�V���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACSplashable
 * @see JWindow
 */

public class ACSplash extends JWindow implements Runnable, ACSplashable {
    protected static Icon[] splashIcons;
    private ACPanel contents;
    private ACPanel expands = new ACPanel();
    private ACLabel message = new ACLabel();

    protected long baseTimeMillis = 0;
    protected String[] iconPathes;
    protected boolean tryFlag = true;
    protected boolean validThread = true;
    protected Dimension windowSize;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACSplash() {
        super(ACFrame.getInstance());
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);

        ACSplashChaine.getInstance().add(this);
        
        initComponent();
        pack();
        initPosition();
    }

    /**
     * �v���O���X�������I�����ĕ��܂��B
     */
    public void close() {
        dispose();
        setValidThread(false);
    }

    /**
     * �A�C�R���摜�p�X��z��ŕԂ��܂��B
     * 
     * @return �A�C�R���摜�p�X
     */
    public String[] getIconPathes() {
        return iconPathes;
    }

    /**
     * �\�����郁�b�Z�[�W��Ԃ��܂��B
     * 
     * @return �\�����郁�b�Z�[�W
     */
    public String getMessage() {
        return this.getMessageLabel().getText();
    }

    /**
     * �v������E�B���h�E�T�C�Y ��Ԃ��܂��B
     * 
     * @return �v������E�B���h�E�T�C�Y
     */
    public Dimension getWindowSize() {
        return windowSize;
    }

    /**
     * �E�B���h�E�T�C�Y���X�V���܂��B
     * 
     * @param winSize �V�����T�C�Y
     */
    public void refreshSize(Dimension winSize) {
        // �E�B���h�E�T�C�Y�̍X�V
        setWindowSize(winSize);
        initPosition();
    }

    /**
     * �X�v���b�V���������I�ɍēǂݍ��݂��܂��B
     */
    public void reload() {
        splashIcons = null;
        loadSplash();
    }

    /**
     * �X���b�h���s�����ł��B
     */
    public void run() {
        beginThread();
        while (isValidThread()) {
            try {
                if (splashIcons != null) {
                    long current = System.currentTimeMillis();
                    int index = (int) ((current - baseTimeMillis) / 100)
                            % splashIcons.length;
                    getMessageLabel().setIcon(splashIcons[index]);
                } else {
                    if (tryFlag) {
                        tryFlag = false;
                        baseTimeMillis = System.currentTimeMillis();
                        loadSplash();
                        continue;
                    }
                }
                processThread();
                repaint();
                getContents().paintImmediately(getContents().getVisibleRect());

                Thread.sleep(100);
            } catch (Throwable ex) {
                close();
            }
        }
        endThread();
    }

    /**
     * �A�C�R���摜�p�X��z��Őݒ肵�܂��B
     * 
     * @param iconPathes �A�C�R���摜�p�X
     */
    public void setIconPathes(String[] iconPathes) {
        this.iconPathes = iconPathes;
    }

    /**
     * �\�����郁�b�Z�[�W��ݒ肵�܂��B
     * 
     * @param message �\�����郁�b�Z�[�W
     */
    public void setMessage(String message) {
        this.getMessageLabel().setText(message);
    }

    /**
     * �v������E�B���h�E�T�C�Y ��ݒ肵�܂��B
     * 
     * @param windowSize �v������E�B���h�E�T�C�Y
     */
    public void setWindowSize(Dimension windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * �X�v���b�V�����J�n���܂��B
     * 
     * @param title �^�C�g��
     */
    public void showModaless(String title) {
        if ((title == null) || "".equals(title)) {
            getMessageLabel().setText("����ʓW�J��...");
        } else {
            getMessageLabel().setText("�u" + title + "�v�W�J��...");
        }

        setVisible(true);
        setValidThread(true);

        baseTimeMillis = System.currentTimeMillis();

        new Thread(this).start();
    }

    /**
     * �X�v���b�V�����I�����܂��B
     */
    public void stop() {
        close();
    }

    /**
     * �ʒu�����������܂��B
     */
    private void initPosition() {
        // �E�B���h�E�̃T�C�Y
        setSize(getWindowSize());
        // �E�B���h�E�𒆉��ɔz�u
        Point corner = new Point(0, 0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsEnvironment genv = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        if (genv != null) {
            // GraphicsEnvironment �̓^�X�N�o�[���̐�L�̈���l������
            Rectangle screenRect = genv.getMaximumWindowBounds();
            if (screenRect != null) {
                corner = screenRect.getLocation();
                screenSize = screenRect.getSize();
            }
        }
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this
                .setLocation(
                        (int) (corner.getX() + (screenSize.width - frameSize.width) / 2),
                        (int) (corner.getY() + (screenSize.height - frameSize.height) / 2));

    }

    /**
     * �g���̈�ւ̒ǉ��������������܂��B
     * 
     * @param container �g���̈�
     * @return �g���̈��\�����邩
     */
    protected boolean addExpands(ACPanel container) {
        return false;
    }

    /**
     * ���ڗ̈�𐶐����܂��B
     * 
     * @return ���ڗ̈�
     */
    protected ACPanel createContents() {
        return new ACPanel();
    }

    /**
     * �f�t�H���g�E�B���h�E�T�C�Y�𐶐����܂��B
     * 
     * @return �f�t�H���g�E�B���h�E�T�C�Y
     */
    protected Dimension createDefaultWindowSize() {
        return new Dimension(400, 120);
    }

    /**
     * �g���̈�𐶐����܂��B
     * 
     * @return �g���̈�
     */
    protected ACPanel createExpands() {
        return new ACPanel();
    }

    /**
     * ���b�Z�[�W�\���̈�𐶐����܂��B
     * 
     * @return ���b�Z�[�W�\���̈�
     */
    protected ACLabel createMessageLabel() {
        return new ACLabel();
    }

    /**
     * ���ڗ̈��Ԃ��܂��B
     * 
     * @return ���ڗ̈�
     */
    protected ACPanel getContents() {
        if (contents == null) {
            contents = createContents();
        }
        return contents;
    }

    /**
     * �g���̈��Ԃ��܂��B
     * 
     * @return �g���̈�
     */
    protected ACPanel getExpands() {
        if (expands == null) {
            expands = createExpands();
        }
        return expands;
    }

    /**
     * ���b�Z�[�W�\���̈��Ԃ��܂��B
     * 
     * @return ���b�Z�[�W�\���̈�
     */
    protected ACLabel getMessageLabel() {
        if (message == null) {
            message = createMessageLabel();
        }
        return message;
    }

    /**
     * �R���|�[�l���g�����������܂��B
     */
    protected void initComponent() {
        setWindowSize(createDefaultWindowSize());
        getMessageLabel().setOpaque(true);
        getMessageLabel().setHorizontalAlignment(SwingConstants.LEADING);
        getMessageLabel().setHorizontalTextPosition(SwingConstants.CENTER);
        getMessageLabel().setIconTextGap(4);
        getMessageLabel().setVerticalTextPosition(
                javax.swing.SwingConstants.TOP);

        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(getContents(), BorderLayout.CENTER);
        getContents().add(getMessageLabel(), VRLayout.CLIENT);
        getContents().add(getExpands(), VRLayout.SOUTH);

        getExpands().setVisible(addExpands(getExpands()));
        
         
    }

    /**
     * �X���b�h���L���ł��邩 ��Ԃ��܂��B
     * 
     * @return �X���b�h���L���ł��邩
     */
    protected boolean isValidThread() {
        return validThread;
    }

    /**
     * �X�v���b�V���摜��ǂݍ��݂܂��B
     */
    protected void loadSplash() {
        try {
            if (splashIcons == null) {
                String[] paths = getIconPathes();
                if ((paths != null) && (paths.length > 0)) {
                    Icon[] icons = new Icon[paths.length];
                    int count = 0;
                    for (int i = 0; i < paths.length; i++) {
                        try {
                            Icon ico = new ImageIcon(ACSplash.class
                                    .getClassLoader().getResource(paths[i]));

                            if (ico != null) {
                                icons[count] = ico;
                                count++;
                            }
                        } catch (Exception ex) {
                        }
                    }
                    splashIcons = new Icon[count];
                    System.arraycopy(icons, 0, splashIcons, 0, count);
                }
            }
        } catch (Exception ex) {
            splashIcons = null;
        }
    }

    /**
     * �X���b�h���s���̒���������������܂��B
     */
    protected void processThread() {

    }

    /**
     * �X���b�h�J�n���̒���������������܂��B
     */
    protected void beginThread() {

    }

    /**
     * �X���b�h�I�����̒���������������܂��B
     */
    protected void endThread() {

    }

    // �E�B���h�E������ꂽ�Ƃ��ɏI������悤�ɃI�[�o�[���C�h
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            close();
        }
        super.processWindowEvent(e);
    }

    /**
     * �X���b�h���L���ł��邩 ��ݒ肵�܂��B
     * 
     * @param validThread �X���b�h���L���ł��邩
     */
    protected void setValidThread(boolean validThread) {
        this.validThread = validThread;
    }
}
