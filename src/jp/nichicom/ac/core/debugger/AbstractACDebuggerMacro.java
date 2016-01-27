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
 * �}�N���̊��N���X�ł��B
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
     * �ϊ��L�[�̃L�[�R�[�h ��Ԃ��܂��B
     * 
     * @return convertKeyCode �ϊ��L�[�̃L�[�R�[�h
     */
    public int getConvertKeyCode() {
        return convertKeyCode;
    }

    /**
     * �ϊ��L�[�̃L�[�R�[�h ��ݒ肵�܂��B
     * 
     * @param convertKeyCode �ϊ��L�[�̃L�[�R�[�h
     */
    public void setConvertKeyCode(int convertKeyCode) {
        this.convertKeyCode = convertKeyCode;
    }

    /**
     * �R���X�g���N�^�ł��B
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
     * �}�N���������������܂��B
     * 
     * @param arg ����
     * @throws Throwable ������O
     */
    public abstract void executeMacroImpl(String arg) throws Throwable;

    public void stopMacro() {
        setRequestMacroStop(true);
        setMacroThread(null);
    }

    /**
     * �t�@�C���Ď����� ��Ԃ��܂��B
     * 
     * @return �t�@�C���Ď�����
     */
    private File[] getSnapshotFiles() {
        return snapshotFiles;
    }

    /**
     * �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X�t�B���^ ��Ԃ��܂��B
     * 
     * @return �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X�t�B���^
     */
    private ACSuffixFilenameFilter getSnapshotSuffixFilter() {
        return snapshotSuffixFilter;
    }

    /**
     * �Ď��Ώۂ̃t�H���_ ��ݒ肵�܂��B
     * 
     * @param snapshotDirectory �Ď��Ώۂ̃t�H���_
     */
    private void setSnapshotDirectory(File snapshotDirectory) {
        this.snapshotDirectory = snapshotDirectory;
    }

    /**
     * �t�@�C���Ď����� ��ݒ肵�܂��B
     * 
     * @param snapshotFiles �t�@�C���Ď�����
     */
    private void setSnapshotFiles(File[] snapshotFiles) {
        this.snapshotFiles = snapshotFiles;
    }

    /**
     * �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X ��ݒ肵�܂��B
     * 
     * @param snapshotSuffix �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X
     */
    private void setSnapshotSuffix(String snapshotSuffix) {
        this.snapshotSuffix = snapshotSuffix;
        setSnapshotSuffixFilter(new ACSuffixFilenameFilter(snapshotSuffix));
    }

    /**
     * �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X�t�B���^ ��ݒ肵�܂��B
     * 
     * @param snapshotSuffixFilter �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X�t�B���^
     */
    private void setSnapshotSuffixFilter(
            ACSuffixFilenameFilter snapshotSuffixFilter) {
        this.snapshotSuffixFilter = snapshotSuffixFilter;
    }

    /**
     * �w��R���|�[�l���g���N���b�N���܂��B
     * <p>
     * �N���b�N����}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param component �R���|�[�l���g
     * @throws InterruptedException ������O
     */
    protected void clickForComponent(Object component)
            throws InterruptedException {
        clickForComponent(component, InputEvent.BUTTON1_MASK);
    }

    /**
     * �w��R���|�[�l���g���N���b�N���܂��B
     * 
     * @param component �R���|�[�l���g
     * @param buttons �N���b�N����}�E�X�{�^��
     * @throws InterruptedException ������O
     */
    protected void clickForComponent(Object component, int buttons)
            throws InterruptedException {
        Point p = ((Component) component).getLocationOnScreen();
        getRobot().mouseMove(p.x, p.y);
        getRobot().mousePress(buttons);
        getRobot().mouseRelease(buttons);
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g���N���b�N���܂��B
     * <p>
     * �N���b�N����}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     */
    protected void clickForField(Object owner, String fieldName)
            throws Exception {
        clickForComponent(getFiled(owner, fieldName));
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g���N���b�N���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param buttons �N���b�N����}�E�X�{�^��
     * @throws Exception ������O
     */
    protected void clickForField(Object owner, String fieldName, int buttons)
            throws Exception {
        clickForComponent(getFiled(owner, fieldName), buttons);
    }

    /**
     * �w��R���|�[�l���g���h���b�O���܂��B
     * <p>
     * ��������}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param component �R���|�[�l���g
     * @throws InterruptedException ������O
     */
    protected void dragForComponent(Object component)
            throws InterruptedException {
        dragForComponent(component, InputEvent.BUTTON1_MASK);
    }

    /**
     * �w��R���|�[�l���g���h���b�O���܂��B
     * 
     * @param component �R���|�[�l���g
     * @param buttons ��������}�E�X�{�^��
     * @throws InterruptedException ������O
     */
    protected void dragForComponent(Object component, int buttons)
            throws InterruptedException {
        Point p = ((Component) component).getLocationOnScreen();
        getRobot().mouseMove(p.x, p.y);
        getRobot().mousePress(buttons);
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g���h���b�O���܂��B
     * <p>
     * ��������}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     */
    protected void dragForField(Object owner, String fieldName)
            throws Exception {
        dragForComponent(getFiled(owner, fieldName));
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g���h���b�O���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param buttons ��������}�E�X�{�^��
     * @throws Exception ������O
     */
    protected void dragForField(Object owner, String fieldName, int buttons)
            throws Exception {
        dragForComponent(getFiled(owner, fieldName), buttons);
    }

    /**
     * �w�胊�X�g�{�b�N�X�̗v�f���h���b�O���܂��B
     * <p>
     * ��������}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param component ���X�g�{�b�N�X
     * @param index �h���b�O�v�f�̃C���f�b�N�X
     * @throws Exception ������O
     */
    protected void dragForListBox(Object component, int index) throws Exception {
        dragForListBox(component, index, InputEvent.BUTTON1_MASK);
    }

    /**
     * �w�胊�X�g�{�b�N�X�̗v�f���h���b�O���܂��B
     * 
     * @param component ���X�g�{�b�N�X
     * @param index �h���b�O�v�f�̃C���f�b�N�X
     * @param buttons ��������}�E�X�{�^��
     * @throws Exception ������O
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
     * �w��t�B�[���h�̃��X�g�{�b�N�X�̗v�f���h���b�O���܂��B
     * <p>
     * ��������}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param index �h���b�O�v�f�̃C���f�b�N�X
     * @throws Exception ������O
     */
    protected void dragForListBoxField(Object owner, String fieldName, int index)
            throws Exception {
        dragForListBox(getFiled(owner, fieldName), index,
                InputEvent.BUTTON1_MASK);
    }

    /**
     * �w��t�B�[���h�̃��X�g�{�b�N�X�̗v�f���h���b�O���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param index �h���b�O�v�f�̃C���f�b�N�X
     * @param buttons ��������}�E�X�{�^��
     * @throws Exception ������O
     */
    protected void dragForListBoxField(Object owner, String fieldName,
            int index, int buttons) throws Exception {
        dragForListBox(getFiled(owner, fieldName), index, buttons);
    }

    /**
     * �w��R���|�[�l���g�Ƀh���b�v���܂��B
     * <p>
     * �����}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param component �R���|�[�l���g
     * @throws InterruptedException ������O
     */
    protected void dropForComponent(Object component)
            throws InterruptedException {
        dropForComponent(component, InputEvent.BUTTON1_MASK);
    }

    /**
     * �w��R���|�[�l���g�Ƀh���b�v���܂��B
     * 
     * @param component �R���|�[�l���g
     * @param buttons �����}�E�X�{�^��
     * @throws InterruptedException ������O
     */
    protected void dropForComponent(Object component, int buttons)
            throws InterruptedException {
        Point p = ((Component) component).getLocationOnScreen();
        getRobot().mouseMove(p.x, p.y);
        getRobot().mouseRelease(buttons);
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g�Ƀh���b�v���܂��B
     * <p>
     * �����}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     */
    protected void dropForField(Object owner, String fieldName)
            throws Exception {
        dropForComponent(getFiled(owner, fieldName));
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g�Ƀh���b�v���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param buttons �����}�E�X�{�^��
     * @throws Exception ������O
     */
    protected void dropForField(Object owner, String fieldName, int buttons)
            throws Exception {
        dropForComponent(getFiled(owner, fieldName), buttons);
    }

    /**
     * �w�胊�X�g�{�b�N�X�Ƀh���b�v���܂��B
     * <p>
     * �����}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param component ���X�g�{�b�N�X
     * @param index �h���b�v��̗v�f�C���f�b�N�X
     * @throws Exception ������O
     */
    protected void dropForListBox(Object component, int index) throws Exception {
        dropForListBox(component, index, InputEvent.BUTTON1_MASK);
    }

    /**
     * �w�胊�X�g�{�b�N�X�Ƀh���b�v���܂��B
     * 
     * @param component ���X�g�{�b�N�X
     * @param index �h���b�v��̗v�f�C���f�b�N�X
     * @param buttons �����}�E�X�{�^��
     * @throws Exception ������O
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
     * �w��t�B�[���h�̃��X�g�{�b�N�X�Ƀh���b�v���܂��B
     * <p>
     * �����}�E�X�{�^���Ƃ���1�{�^��(��)���g�p���܂��B
     * </p>
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param index �h���b�O�v�f�̃C���f�b�N�X
     * @throws Exception ������O
     */
    protected void dropForListBoxField(Object owner, String fieldName, int index)
            throws Exception {
        dropForListBox(getFiled(owner, fieldName), index,
                InputEvent.BUTTON1_MASK);
    }

    /**
     * �w��t�B�[���h�̃��X�g�{�b�N�X�Ƀh���b�v���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param index �h���b�O�v�f�̃C���f�b�N�X
     * @param buttons �����}�E�X�{�^��
     * @throws Exception ������O
     */
    protected void dropForListBoxField(Object owner, String fieldName,
            int index, int buttons) throws Exception {
        dropForListBox(getFiled(owner, fieldName), index, buttons);
    }

    /**
     * ���g�̓t�H�[�J�X��L���Ȃ��R���e�i��̃R���|�[�l���g�Ƀt�H�[�J�X�����Ă܂��B
     * 
     * @param container �R���e�i
     * @throws Exception ������O
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
     * �w��t�B�[���h�́A���g�̓t�H�[�J�X��L���Ȃ��R���e�i��̃R���|�[�l���g�Ƀt�H�[�J�X�����Ă܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     */
    protected void focusContainerField(Object owner, String fieldName)
            throws Exception {
        focusContainer((Component) getFiled(owner, fieldName));
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g�Ƀt�H�[�J�X�����Ă܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     */
    protected void focusField(Object owner, String fieldName) throws Exception {
        requestFocus(getFiled(owner, fieldName));
    }

    /**
     * �w�胁�\�b�h�Ŏ擾�ł���R���|�[�l���g�Ƀt�H�[�J�X�����Ă܂��B
     * 
     * @param owner ���L��
     * @param methodName ���\�b�h��
     * @throws Exception ������O
     */
    protected void focusMethod(Object owner, String methodName)
            throws Exception {
        requestFocus(getMethod(owner, methodName));
    }

    /**
     * �w�胁�\�b�h�Ŏ擾�ł���R���|�[�l���g�Ƀt�H�[�J�X�����Ă܂��B
     * 
     * @param owner ���L��
     * @param methodName ���\�b�h��
     * @param paramClasses �p�����^�^�z��
     * @param paramValues �p�����^�l�z��
     * @throws Exception ������O
     */
    protected void focusMethod(Object owner, String methodName,
            Class[] paramClasses, Object[] paramValues) throws Exception {
        requestFocus(getMethod(owner, methodName, paramClasses, paramValues));
    }

    /**
     * �Ō�ɕ\�������_�C�A���O�Ƀt�H�[�J�X�����ĂĕԂ��܂��B
     * 
     * @return �Ō�ɕ\�������_�C�A���O
     * @throws InterruptedException ������O
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
     * ���݂̋Ɩ��p�l���Ƀt�H�[�J�X�����ĂĕԂ��܂��B
     * 
     * @return ���݂̋Ɩ��p�l��
     * @throws InterruptedException ������O
     */
    protected Component focusNowPanel() throws InterruptedException {
        Component affair = (Component) ACFrame.getInstance().getNowPanel();
        requestFocus(affair);
        return affair;
    }

    /**
     * �w��t�B�[���h�̒l��Ԃ��܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @return �l
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
     * �}�N�����s�X���b�h ��Ԃ��܂��B
     * 
     * @return �}�N�����s�X���b�h
     */
    protected Thread getMacroThread() {
        return macroThread;
    }

    /**
     * �w�胁�\�b�h�Ŏ擾�ł���l��Ԃ��܂��B
     * 
     * @param owner ���L��
     * @param methodName ���\�b�h��
     * @return �l
     * @throws Exception ������O
     */
    protected Object getMethod(Object owner, String methodName)
            throws Exception {
        return getMethod(owner, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * �w�胁�\�b�h�Ŏ擾�ł���l��Ԃ��܂��B
     * 
     * @param owner ���L��
     * @param methodName ���\�b�h��
     * @param paramClasses �p�����^�^�z��
     * @param paramValues �p�����^�l�z��
     * @return �l
     * @throws Exception ������O
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
     * ���{�b�g�N���X��Ԃ��܂��B
     * 
     * @return ���{�b�g�N���X
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
     * �w��R���|�[�l���g��getSelectedIndex���\�b�h���Ăяo���܂��B
     * 
     * @param component �R���|�[�l���g
     * @throws Exception ������O
     * @return �l
     */
    protected int getSelectedIndexForComponent(Object component)
            throws Exception {
        return ACCastUtilities.toInt(getMethod(component, "getSelectedIndex"),
                -1);
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g��getSelectedIndex���\�b�h���Ăяo���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     * @return �l
     */
    protected int getSelectedIndexForField(Object owner, String fieldName)
            throws Exception {
        return getSelectedIndexForComponent(getFiled(owner, fieldName));
    }

    /**
     * �Ď��Ώۂ̃t�H���_ ��Ԃ��܂��B
     * 
     * @return �Ď��Ώۂ̃t�H���_
     */
    protected File getSnapshotDirectory() {
        return snapshotDirectory;
    }

    /**
     * �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X ��Ԃ��܂��B
     * 
     * @return �Ď��Ώۂ̃t�@�C���T�t�B�b�N�X
     */
    protected String getSnapshotSuffix() {
        return snapshotSuffix;
    }

    /**
     * �}�N����~�v�����o���� ��Ԃ��܂��B
     * 
     * @return �}�N����~�v�����o����
     */
    protected boolean isRequestMacroStop() {
        return requestMacroStop;
    }

    /**
     * �w��R���|�[�l���g��isSelectedIndex���\�b�h���Ăяo���܂��B
     * 
     * @param component �R���|�[�l���g
     * @throws Exception ������O
     * @return �l
     */
    protected boolean isSelectedForComponent(Object component) throws Exception {
        return ACCastUtilities.toBoolean(getMethod(component, "isSelected"),
                false);
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g��isSelected���\�b�h���Ăяo���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     * @return �l
     */
    protected boolean isSelectedForField(Object owner, String fieldName)
            throws Exception {
        return isSelectedForComponent(getFiled(owner, fieldName));
    }

    /**
     * �Ō�Ɏ��s�����t�@�C�����X�i�b�v�V���b�g�ƍ����݂̃X�i�b�v�V���b�g���r���A�ύX�̂������ŐV�̃t�@�C����Ԃ��܂��B
     * <p>
     * �ύX���Ȃ����null��Ԃ��܂��B
     * </p>
     * 
     * @return �ύX�̂������ŐV�̃t�@�C��
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
     * ���b�Z�[�W�{�b�N�X��Cancel�{�^�����������܂��B
     * 
     * @throws InterruptedException ������O
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
     * ���b�Z�[�W�{�b�N�X��No�{�^�����������܂��B
     * 
     * @throws InterruptedException ������O
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
     * ���b�Z�[�W�{�b�N�X��OK�{�^�����������܂��B
     * 
     * @throws InterruptedException ������O
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
     * ���b�Z�[�W�{�b�N�X��Yes�{�^�����������܂��B
     * 
     * @throws InterruptedException ������O
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
     * �w��t�B�[���h�̃{�^�����������܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @throws Exception ������O
     */
    protected void pushButtonField(Object owner, String fieldName)
            throws Exception {
        focusField(owner, fieldName);
        waitForIdle(50);
        sendKey(KeyEvent.VK_ENTER);
        getRobot().waitForIdle();
    }

    /**
     * �w��R���|�[�l���g�Ƀt�H�[�J�X�����Ă܂��B
     * 
     * @param component �R���|�[�l���g
     * @throws InterruptedException ������O
     */
    protected void requestFocus(Object component) throws InterruptedException {
        waitForIdle();
        ((Component) component).requestFocus();
    }

    /**
     * �w��L�[���͂��������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     */
    protected void sendKey(int keyCode) {
        getRobot().keyPress(keyCode);
        getRobot().keyRelease(keyCode);
    }

    /**
     * �w��L�[���͂𕡐��񏈗����܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @param count �J��Ԃ���
     */
    protected void sendKey(int keyCode, int count) {
        for (int i = 0; i < count; i++) {
            sendKey(keyCode);
        }
    }

    /**
     * �w��L�[���͂��C���L�[�t���ŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @param modified �C���L�[
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
     * �w��L�[���͂�ALT����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyAlt(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT });
        waitForIdle();
    }

    /**
     * �w��L�[���͂�ALT+Ctrl����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyAltControl(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT, KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * �w��L�[���͂�ALT+Shift����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyAltShift(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT, KeyEvent.VK_SHIFT });
        waitForIdle();
    }

    /**
     * �w��L�[���͂�ALT+Shift+Ctrl����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyAltShiftControl(int keyCode)
            throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_ALT, KeyEvent.VK_SHIFT,
                KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * �w��L�[���͂�Ctrl����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyControl(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * �w��L�[���͂�Shift����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyShift(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_SHIFT });
        waitForIdle();
    }

    /**
     * �w��L�[���͂�Shift+Ctrl����������Ԃŏ������܂��B
     * 
     * @param keyCode �L�[�R�[�h
     * @throws InterruptedException ������O
     */
    protected void sendKeyShiftControl(int keyCode) throws InterruptedException {
        waitForIdle();
        sendKey(keyCode, new int[] { KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL });
        waitForIdle();
    }

    /**
     * ���ϊ��L�[������ɁA�w��̕������1�������L�[���͂Ƃ��ď������܂��B
     * 
     * @param text ���͕�����
     * @throws InterruptedException ������O
     */
    protected void sendSingleByteText(String text) throws InterruptedException {
        sendKey(KeyEvent.VK_NONCONVERT);
        sendText(text);
    }

    /**
     * �w��̕������1�������L�[���͂Ƃ��ď������܂��B
     * 
     * @param text ���͕�����
     * @throws InterruptedException ������O
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
     * ���ȃL�[������ɁA�w��̕������1�������L�[���͂Ƃ��ď������A�����ĕϊ��L�[�ƃG���^�[�L�[���������܂��B
     * 
     * @param text ���͕�����
     * @throws InterruptedException ������O
     */
    protected void sendWideByteConvertText(String text)
            throws InterruptedException {
        sendWideByteText(text);
        sendKey(getConvertKeyCode());
        sendKey(KeyEvent.VK_ENTER);
    }

    /**
     * ���ȃL�[������ɁA�w��̕������1�������L�[���͂Ƃ��ď������܂��B
     * 
     * @param text ���͕�����
     * @throws InterruptedException ������O
     */
    protected void sendWideByteText(String text) throws InterruptedException {
        sendKey(KeyEvent.VK_HIRAGANA);
        sendText(text);
    }

    /**
     * �}�N�����s�X���b�h ��ݒ肵�܂��B
     * 
     * @param macroThread �}�N�����s�X���b�h
     */
    protected void setMacroThread(Thread macroThread) {
        this.macroThread = macroThread;
    }

    /**
     * �}�N����~�v�����o���� ��ݒ肵�܂��B
     * 
     * @param macroStoped �}�N����~�v�����o����
     */
    protected void setRequestMacroStop(boolean macroStoped) {
        this.requestMacroStop = macroStoped;
    }

    /**
     * ���{�b�g�N���X��ݒ肵�܂��B
     * 
     * @param robot ���{�b�g�N���X
     */
    protected void setRobot(Robot robot) {
        this.robot = robot;
    }

    /**
     * �w��R���|�[�l���g��setSelected���\�b�h���Ăяo���܂��B
     * 
     * @param component �R���|�[�l���g
     * @param selected �l
     * @throws Exception ������O
     */
    protected void setSelectedForComponent(Object component, boolean selected)
            throws Exception {
        getMethod(component, "setSelected", new Class[] { boolean.class },
                new Object[] { new Boolean(selected) });
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g��setSelected���\�b�h���Ăяo���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param selected �l
     * @throws Exception ������O
     */
    protected void setSelectedForField(Object owner, String fieldName,
            boolean selected) throws Exception {
        setSelectedForComponent(getFiled(owner, fieldName), selected);
    }

    /**
     * �w��R���|�[�l���g��setSelectedIndex���\�b�h���Ăяo���܂��B
     * 
     * @param component �R���|�[�l���g
     * @param index �l
     * @throws Exception ������O
     */
    protected void setSelectedIndexForComponent(Object component, int index)
            throws Exception {
        getMethod(component, "setSelectedIndex", new Class[] { int.class },
                new Object[] { new Integer(index) });
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g��setSelectedIndex���\�b�h���Ăяo���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param index �l
     * @throws Exception ������O
     */
    protected void setSelectedIndexForField(Object owner, String fieldName,
            int index) throws Exception {
        setSelectedIndexForComponent(getFiled(owner, fieldName), index);
    }

    /**
     * �w��R���|�[�l���g��setText���\�b�h���Ăяo���܂��B
     * 
     * @param component �R���|�[�l���g
     * @param text �e�L�X�g
     * @throws Exception ������O
     */
    protected void setTextForComponent(Object component, String text)
            throws Exception {
        getMethod(component, "setText", new Class[] { String.class },
                new Object[] { text });
    }

    /**
     * �w��t�B�[���h�̃R���|�[�l���g��setText���\�b�h���Ăяo���܂��B
     * 
     * @param owner ���L��
     * @param fieldName �t�B�[���h��
     * @param text �e�L�X�g
     * @throws Exception ������O
     */
    protected void setTextForField(Object owner, String fieldName, String text)
            throws Exception {
        setTextForComponent(getFiled(owner, fieldName), text);
    }

    /**
     * �t�@�C�����X�i�b�v�V���b�g�����s���܂��B
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
     * �t�@�C�����X�i�b�v�V���b�g�����s���܂��B
     * 
     * @param snapshotDirectory �X�i�b�v�V���b�g�Ώۂ̃f�B���N�g��
     * @param snapshotSuffix �X�i�b�v�V���b�g�Ώۂ̃t�@�C���T�t�B�b�N�X
     */
    protected void snapshotFileName(String snapshotDirectory,
            String snapshotSuffix) {
        snapshotFileNameSetting(snapshotDirectory, snapshotSuffix);
        snapshotFileName();
    }

    /**
     * �t�@�C�����X�i�b�v�V���b�g�̐ݒ���s���܂��B
     * 
     * @param snapshotDirectory �X�i�b�v�V���b�g�Ώۂ̃f�B���N�g��
     * @param snapshotSuffix �X�i�b�v�V���b�g�Ώۂ̃t�@�C���T�t�B�b�N�X
     */
    protected void snapshotFileNameSetting(String snapshotDirectory,
            String snapshotSuffix) {
        setSnapshotDirectory(new File(snapshotDirectory));
        setSnapshotSuffix(snapshotSuffix);
    }

    /**
     * Java�v���Z�X���t�H�[�J�X�����L����܂őҋ@���܂��B
     * 
     * @throws InterruptedException ������O
     * @return �}�N����~�v�����o����
     */
    protected boolean waitForActive() throws InterruptedException {
        while (FocusManager.getCurrentManager().getFocusOwner() == null) {
            waitForIdle(200);
        }
        return isRequestMacroStop();
    }

    /**
     * ���͂���t�\�ɂȂ�܂őҋ@���܂��B
     * 
     * @throws InterruptedException ������O
     * @return �}�N����~�v�����o����
     */
    protected boolean waitForIdle() throws InterruptedException {
        getRobot().waitForIdle();
        return waitForActive();
    }

    /**
     * �w��~���b�҂�����A���͂���t�\�ɂȂ�܂őҋ@���܂��B
     * 
     * @param millis �ҋ@����~���b
     * @throws InterruptedException ������O
     * @return �}�N����~�v�����o����
     */
    protected boolean waitForIdle(long millis) throws InterruptedException {
        Thread.sleep(millis);
        return waitForIdle();
    }

}
