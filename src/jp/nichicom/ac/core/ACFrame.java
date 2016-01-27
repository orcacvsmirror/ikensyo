package jp.nichicom.ac.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.FocusManager;
import javax.swing.JFrame;
import javax.swing.UIManager;

import jp.nichicom.ac.ACOSInfo;
import jp.nichicom.ac.container.ACStatusBar;
import jp.nichicom.ac.core.debugger.ACFrameEventDebugger;
import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * ���C���t���[���ł��B
 * <p>
 * ��ʑJ�ڂɂ�����Ǘ��������������Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public class ACFrame extends JFrame {

    protected static ACFrame singleton;

    /**
     * �f�o�b�O���[�h�ŋƖ����N�����܂��B
     * <p>
     * VRLook&Feel��K�p���A�w��̋Ɩ����J�n���܂��B
     * </p>
     * 
     * @param affair �Ɩ����
     */
    public static void debugStart(ACAffairInfo affair) {
        try {
            setVRLookAndFeel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ACFrame frame = ACFrame.getInstance();
        try {
            frame.next(affair);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        frame.setVisible(true);
    }

    /**
     * �B��̃C���X�^���X��Ԃ��܂��B
     * 
     * @return �B��̃C���X�^���X
     */
    public static ACFrame getInstance() {
        if (singleton == null) {
            singleton = new ACFrame();
        }
        return singleton;
    }

    /**
     * VR Look & Feel ��K�p���܂��B
     * 
     * @throws Exception ������O
     */
    public static void setVRLookAndFeel() throws Exception {
    	// Mac�ȊO��Look&Feel���g�p����
        if (!ACOSInfo.isMac()) {
            UIManager.setLookAndFeel("jp.nichicom.vr.plaf.metal.VRLookAndFeel");
        }
    }

    protected String exeFolderPath;

    protected ACFrameEventProcesser frameEventProcesser;

    protected ACAffairable nowPanel;

    protected List traceAffairs;
    
    
	// --- ��ʃT�C�Y�萔
	public static final String MODE_SMALL = "Small";
	public static final String MODE_MIDDLE = "Middle";
	public static final String MODE_LARGE = "Large";
	public static final String MODE_OVERSIZE = "OverSize";
	
	// ���ݑI�����ꂦ�Ă����ʃT�C�Y
	private String screenMode = MODE_SMALL;
	
	// �e��ʃT�C�Y�ł̃t�H���g�w��
	private Map screenFont;
	private Map screenSize;
	
	// �X�N���[���T�C�Y��ۑ�����v���p�e�B�t�@�C���̃p�X
	private static final String CONFIG_SCREEN_SIZE = "Config/ScreenSize";
	

    /**
     * �R���X�g���N�^�ł��B <br />
     * singleton Pattern
     * 
     * @throws HeadlessException ��������O
     */
    private ACFrame() throws HeadlessException {
        super();
        setTraceAffairs(new VRArrayList());

        File currentFolder = new File(".").getAbsoluteFile().getParentFile();
        if (currentFolder != null) {
            setExeFolderPath(currentFolder.getAbsolutePath());
        }
        
        // ��ʃT�C�Y�ύX�֘A�̒萔������
		screenFont = new HashMap();
		screenSize = new HashMap();
		
        if (ACOSInfo.isMac()) {
        	screenFont.put(MODE_SMALL, new Font("Dialog", Font.PLAIN, 12));
			screenFont.put(MODE_MIDDLE, new Font("Dialog", Font.PLAIN, 15));
			screenFont.put(MODE_LARGE, new Font("Dialog", Font.PLAIN, 20));
			screenFont.put(MODE_OVERSIZE, new Font("Dialog", Font.PLAIN, 22));
			
        } else {
        	screenFont.put(MODE_SMALL, new Font("Dialog", Font.PLAIN, 12));
			screenFont.put(MODE_MIDDLE, new Font("Meiryo", Font.PLAIN, 15));
			screenFont.put(MODE_LARGE, new Font("Meiryo", Font.PLAIN, 20));
			screenFont.put(MODE_OVERSIZE, new Font("Meiryo", Font.PLAIN, 22));
        }
        
        screenSize.put(MODE_SMALL, new Dimension(800,600));
		screenSize.put(MODE_MIDDLE, new Dimension(1024,730));
		screenSize.put(MODE_LARGE, new Dimension(1280,960));
		screenSize.put(MODE_OVERSIZE, new Dimension(1440,1050));
        
		// �N�����͉�ʃT�C�Y��
		setSmall();
		
    }

    /**
     * ���ݑΏۂƂ��Ă���Ɩ����̉�ʑJ�ڃp�����^�Ɏw��p�����^��ǉ����܂��B
     * 
     * @param bindPath �ǉ�����p�����^�̃L�[�ƂȂ�o�C���h�p�X
     * @param parameter �ǉ�����p�����^�l
     * @throws ParseException ��͗�O
     */
    public void addNowAffairParameter(String bindPath, Object parameter)
            throws ParseException {
        ACAffairInfo affair = getNowAffair();
        if (affair != null) {
            VRMap parameters = affair.getParameters();
            if (parameters == null) {
                // �n��p�����^�����݂��Ȃ���΁A���Map�Ƃ��Đ�������
                parameters = new VRHashMap();
                affair.setParameters(parameters);
            }

            // ���݂̋Ɩ����̃p�����[�^�Ƃ��Ēǉ�
            VRBindPathParser.set(bindPath, parameters, parameter);
        }
    }

    /**
     * ���O�̉�ʂɑJ�ڂ��܂��B
     * 
     * @throws InstantiationException �Ɩ��N���X�̃C���X�^���X�����s
     * @throws ClassCastException ��ʑJ�ڂł��Ȃ��Ɩ��N���X
     */
    public void back() throws ClassCastException, InstantiationException {
        // 2006/02/06[Tozo Tanaka] : replace begin
//        int size = getTraceAffairs().size();
//        if (size < 2) {
//            return;
//        }
//        try {
//            if (getNowPanel() == null) {
//                return;
//            }
//
//            ACAffairInfo affair = (ACAffairInfo) getTraceAffairs()
//                    .get(size - 2);
//            if (affair.getParameters() == null) {
//                affair.setParameters(new VRHashMap());
//            }
//            if (!getNowPanel().canBack(affair.getParameters())) {
//                return;
//            }
//
//            change(affair);
//        } catch (Exception ex) {
//            showExceptionMessage(ex);
//            return;
//        }
//
//        getTraceAffairs().remove(size - 1);
        int size = getTraceAffairs().size();
        if (size < 2) {
            return;
        }
        ACAffairInfo oldAffair = getNowAffair();
        try {
            if (getNowPanel() == null) {
                return;
            }

            ACAffairInfo affair = getBackAffair();
            if (affair.getParameters() == null) {
                affair.setParameters(new VRHashMap());
            }
            if (!getNowPanel().canBack(affair.getParameters())) {
                return;
            }
            // canBack���ł̗��𐧌���l�����čĎ擾
            affair = getBackAffair();
            size = getTraceAffairs().size();

            logOfAffair("back(now): " + oldAffair);
            logOfAffair("back(new): " + affair);
            
            //������͉��J�ڂ�����
            getTraceAffairs().remove(size - 1);
            change(affair);
        } catch (Throwable ex) {
            if(size>getTraceAffairs().size()){
                //�J�ڂɎ��s�����ꍇ�A�Ō�ɍ폜�����J�ڗ�����ǉ����Ȃ���
                getTraceAffairs().add(oldAffair);
            }
 
            showExceptionMessage(ex);
            return;
        }
        // 2006/02/06[Tozo Tanaka] : replace end

        

    }

    /**
     * �Ɩ��֘A�̃��O���o�͂��܂��B
     * 
     * @param message ���O
     */
    protected void logOfAffair(Object message) {
        ACFrameEventProcesser eventProcesser = getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventDebugger) {
            VRLogger.log(((ACFrameEventDebugger) eventProcesser)
                    .getAffairLogLevel(), message);
        }
    }

    /**
     * �O��ʂ̋Ɩ�����Ԃ��܂��B
     * 
     * @return �O��ʂ̋Ɩ����
     */
    public ACAffairInfo getBackAffair() {
        int size = getTraceAffairs().size();
        if (size < 2) {
            return null;
        }
        return (ACAffairInfo) getTraceAffairs().get(size - 2);
    }

    /**
     * ���s�t�H���_�p�X��Ԃ��܂��B
     * 
     * @return ���s�t�H���_�p�X
     */
    public String getExeFolderPath() {
        return exeFolderPath;
    }

    /**
     * �J�X�^���C�x���g�����C���X�^���X��Ԃ��܂��B
     * 
     * @return �J�X�^���C�x���g�����C���X�^���X
     */
    public ACFrameEventProcesser getFrameEventProcesser() {
        return frameEventProcesser;
    }

    /**
     * ���ݑΏۂƂ��Ă���Ɩ�����Ԃ��܂��B
     * 
     * @return ���ݑΏۂƂ��Ă���Ɩ����
     */
    public ACAffairInfo getNowAffair() {
        int size = getTraceAffairs().size();
        if (size < 1) {
            return null;
        }
        return (ACAffairInfo) getTraceAffairs().get(size - 1);
    }

    /**
     * ���ݑΏۂƂ��Ă���Ɩ��p�l���N���X��Ԃ��܂��B
     * 
     * @return �Ɩ��p�l���N���X
     */
    public ACAffairable getNowPanel() {
        return nowPanel;
    }

    /**
     * �v���p�e�B�t�@�C������l���擾���܂��B
     * 
     * @param path �L�[
     * @throws Exception ������O
     * @return �L�[�ɑΉ�����l
     */
    public String getProperty(String path) throws Exception {
        ACPropertyXML xml = getPropertyXML();
        if (xml != null) {
            return xml.getValueAt(path);
        }
        return null;
    }

    /**
     * �ݒ�t�@�C����Ԃ��܂��B
     * 
     * @throws Exception ������O
     * @return �ݒ�t�@�C��
     */
    public ACPropertyXML getPropertyXML() throws Exception {
        if (getFrameEventProcesser() != null) {
            return getFrameEventProcesser().getPropertyXML();
        }
        return null;
    }

    /**
     * ��ʑJ�ڌo�H ��Ԃ��܂��B
     * 
     * @return ��ʑJ�ڌo�H
     */
    public List getTraceAffairs() {
        return traceAffairs;
    }

    /**
     * �v���p�e�B�t�@�C������L�[�ɑΉ�����l����`����Ă��邩���擾���܂��B
     * 
     * @param path �L�[
     * @throws Exception ������O
     * @return �L�[�ɑΉ�����l����`����Ă��邩
     */
    public boolean hasProperty(String path) throws Exception {
        ACPropertyXML xml = getPropertyXML();
        if (xml != null) {
            return xml.hasValueAt(path);
        }
        return false;
    }

    /**
     * ���̉�ʂɑJ�ڂ��܂��B
     * 
     * @param affair �J�ڐ�Ɩ����
     * @throws InstantiationException �Ɩ��N���X�̃C���X�^���X�����s
     * @throws ClassCastException ��ʑJ�ڂł��Ȃ��Ɩ��N���X
     */
    public void next(ACAffairInfo affair) throws ClassCastException,
            InstantiationException {
        int oldSize = getTraceAffairs().size();
        try {
            if (getTraceAffairs().size() == 0) {
                // ����̑J�ڎ��ɃV�X�e��������
                initSystem();
            }
            logOfAffair("back(now): " + getNowAffair());
            logOfAffair("back(new): " + affair);

            //������͉��J�ڂ�����
            getTraceAffairs().add(affair);
            change(affair);
        } catch (Throwable ex) {
            if(oldSize<getTraceAffairs().size()){
                //�J�ڂɎ��s�����ꍇ�A�Ō�ɒǉ������J�ڗ�����j������
                getTraceAffairs().remove(oldSize);
            }
            showExceptionMessage(ex);
            return;
        }

    }

    /**
     * ���ݑΏۂƂ��Ă���Ɩ����̉�ʑJ�ڃp�����^����w��p�����^���������܂��B
     * 
     * @param bindPath ��������p�����^�̃L�[�ƂȂ�o�C���h�p�X
     */
    public void removeNowAffairParameter(String bindPath) {
        ACAffairInfo affair = getNowAffair();
        if (affair != null) {
            VRMap parameters = affair.getParameters();
            if (parameters != null) {
                parameters.removeData(bindPath);
            }
        }
    }

    /**
     * �J�X�^���C�x���g�����C���X�^���X��ݒ肵�܂��B
     * 
     * @param flameEventProcesser �J�X�^���C�x���g�����C���X�^���X
     */
    public void setFrameEventProcesser(
            ACFrameEventProcesser flameEventProcesser) {
        this.frameEventProcesser = flameEventProcesser;
        refreshFrameEventProcesser();
    }

    /**
     * ��O���b�Z�[�W��\�����܂��B
     * 
     * @param ex ��O
     */
    public void showExceptionMessage(Throwable ex) {
        if (getFrameEventProcesser() != null) {
            getFrameEventProcesser().showExceptionMessage(ex);
        }
    }

    public void validate() {
        if (getFrameEventProcesser() != null) {
            Dimension minSize = getFrameEventProcesser().getMinimumWindowSize();
            if (minSize != null) {
                // ���݂̃t���[���T�C�Y���擾
                Rectangle r = super.getBounds();
                Dimension newSize = null;
                // �t���[�����ŏ��l��菬������΁A�ŏ��l�ɐݒ肵����
                if (minSize.getWidth() > r.width) {
                    if (minSize.getHeight() > r.height) {
                        newSize = new Dimension((int) minSize.getWidth(),
                                (int) minSize.getHeight());
                    } else {
                        newSize = new Dimension((int) minSize.getWidth(),
                                r.height);
                    }
                } else if (minSize.getHeight() > r.height) {
                    newSize = new Dimension(r.width, (int) minSize.getHeight());
                }
                if (newSize != null) {
                    setBounds(r.x, r.y, (int) newSize.getWidth(), (int) newSize
                            .getHeight());
                }
            }
        }

        super.validate();
    }

    /**
     * ��ʑJ�ڂ����s���܂��B
     * 
     * @param newAffair �J�ڐ�Ɩ����
     * @throws InstantiationException �Ɩ��N���X�̃C���X�^���X�����s
     * @throws ClassCastException ��ʑJ�ڂł��Ȃ��Ɩ��N���X
     * @throws Throwable ���̑��̗�O
     */
    private void change(ACAffairInfo newAffair) throws InstantiationException,
            ClassCastException, Throwable {

        ACFrameEventProcesser eventProcesser = getFrameEventProcesser();

        if (eventProcesser instanceof ACFrameEventDebugger) {
            // ���O�o�̓��x�����Đݒ�
            ((ACFrameEventDebugger) eventProcesser).refleshSetting();
        }

        Object affair;
        try {
            String className = newAffair.getClassName();
            if (eventProcesser instanceof ACFrameEventProcesser) {
                ACAffairReplacable replacer = eventProcesser
                        .getAffairReplacer();
                if (replacer instanceof ACAffairReplacable) {
                    // �Ɩ��u���N���X����`����Ă���΁A�G�f�B�V�������ɉ����ēK�؂ȋƖ��N���X���ɒu������
                    className = replacer.getValidClassName(className);
                }
            }

            affair = Class.forName(className).newInstance();
        } catch (Exception ex) {
            throw new InstantiationException(newAffair.getClassName()
                    + " �̓C���X�^���X���ł��Ȃ��N���X�ł�");
        }
        if (!(affair instanceof ACAffairable) || !(affair instanceof Component)) {
            throw new ClassCastException(newAffair.getClassName()
                    + " �͑J�ډ\�ȉ�ʃR���|�[�l���g�ł͂���܂���");
        }

        ACSplashable splash = null;
        if (newAffair.isSplashed()) {
            if (eventProcesser instanceof ACFrameEventProcesser) {
                // �Ɩ��J�ڎ��ɃX�v���b�V�����g�p����
                splash = eventProcesser.createSplash(newAffair.getTitle());
            }
        }

        if (getNowPanel() instanceof ACAffairContainer) {
            // �X�e�[�^�X�o�[�̃^�C�}�X���b�h�𖾎��I�ɒ�~�����A�������[���[�N��h�~����
            ((ACAffairContainer) getNowPanel()).cancelTimer();
        }
        setNowPanel(null);
        getContentPane().removeAll();

        // �C���X�^���X�̃t�@�C�i���C�Y��GC�����s���A�O�Ɩ����N���A����B
        System.runFinalization();
        System.gc();

        ACAffairable panel = (ACAffairable) affair;
        getContentPane().add((Component) panel);
        setNowPanel(panel);
        
        //[ID:0000754][Shin Fujihara] 2012/09 edit begin 2012�N�x�Ή� �{�����O�o�͋@�\
        //��panel.initAffair���ŁA�p�����[�^���N���A�����p�^�[��������̂ŁA�����ŏo�͎��s
        if (panel instanceof ACBrowseLogWritable) {
        	((ACBrowseLogWritable)panel).writeBrowseLog(newAffair);
        }
        //[ID:0000754][Shin Fujihara] 2012/09 edit end 2012�N�x�Ή� �{�����O�o�͋@�\

        if (panel instanceof ACAffairContainer) {
            // �X�e�[�^�X�o�[�̍ĕ`������s����
            ACStatusBar statusBar = ((ACAffairContainer) panel).getStatusBar();
            if (statusBar instanceof ACStatusBar) {
                statusBar.checkLookingKeyState();
            }
        }

        String newTitle = newAffair.getTitle();
        if ((newTitle != null) && (!"".equals(newTitle))) {
            // �J�ڌ��Ɩ�����^�C�g����ݒ肳����ꍇ
            setTitle(newTitle);
        }

        panel.initAffair(newAffair);
        // �����č\��
        getContentPane().invalidate();
        getContentPane().validate();

        Component focusComp = panel.getFirstFocusComponent();
        if (focusComp != null) {
            // ��ʓW�J��̃t�H�[�J�X�ʒu�w��
            focusComp.requestFocus();
        }

        if (splash != null) {
            splash.close();
            splash = null;
        }
    }

    /**
     * ����̉�ʑJ�ڎ��ɃV�X�e�����������������s���܂��B
     * 
     * @throws Exception ������O
     */
    protected void initSystem() throws Exception {
        ACFrameEventProcesser eventProcesser = getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventProcesser) {
            // �V�X�e��������
            eventProcesser.initSystem();
        }
    }

    // �E�B���h�E������ꂽ�Ƃ��ɏI������悤�ɃI�[�o�[���C�h
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (getNowPanel() != null) {
                try {
                    FocusManager fm = FocusManager.getCurrentManager();
                    if (fm != null) {
                        Component cmp = fm.getFocusOwner();
                        if (cmp != null) {
                            // �t�H�[�J�X���X�g�O�Ɂ~�{�^���ŕ���ꍇ��z�肵�A�[���I��FocusLost�𔭍s����
                            cmp.dispatchEvent(new FocusEvent(cmp,
                                    FocusEvent.FOCUS_LOST));
                        }
                    }

                    if (!getNowPanel().canClose()) {
                        return;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            super.processWindowEvent(e);
            System.exit(0);
            return;
        }
        super.processWindowEvent(e);
    }

    /**
     * �J�X�^���C�x���g�����C���X�^���X�̏����ēK�p���܂��B
     */
    protected void refreshFrameEventProcesser() {
        ACFrameEventProcesser flameEventProcesser = getFrameEventProcesser();
        if (flameEventProcesser != null) {
            // �f�t�H���g�T�C�Y�̔��f
            Dimension defaultSize = flameEventProcesser.getDefaultWindowSize();
            if (defaultSize != null) {
                setSize(defaultSize);
            }
            // �t���[���A�C�R���̔��f
            Image icon = flameEventProcesser.getFrameIcon();
            if (icon != null) {
                // �A�C�R�����Z�b�g
                setIconImage(icon);
            }
        }
    }

    /**
     * ���s�t�H���_�p�X��ݒ肵�܂��B
     * 
     * @param exeFolderPath ���s�t�H���_�p�X
     */
    protected void setExeFolderPath(String exeFolderPath) {
        this.exeFolderPath = exeFolderPath;
    }

    /**
     * ���ݑΏۂƂ��Ă���Ɩ��p�l���N���X��ݒ肵�܂��B
     * 
     * @param nowPanel �Ɩ��p�l���N���X
     */
    protected void setNowPanel(ACAffairable nowPanel) {
        this.nowPanel = nowPanel;
    }

    /**
     * ��ʑJ�ڌo�H ��ݒ肵�܂��B
     * 
     * @param traceAffairs ��ʑJ�ڌo�H
     */
    protected void setTraceAffairs(List traceAffairs) {
        this.traceAffairs = traceAffairs;
    }
    
    
    
    // --- ��ʃT�C�Y�ύX�@�\�Ή�
	// ��ʃ��[�h�@�W��
	public boolean isSmall() {
		return MODE_SMALL.equals(getScreenMode());
	}
	
	// ��ʃ��[�h�@��
	public boolean isMiddle() {
		return MODE_MIDDLE.equals(getScreenMode());
	}
	
	// ��ʃ��[�h�@��
	public boolean isLarge() {
		return MODE_LARGE.equals(getScreenMode());
	}
	
	// ��ʃ��[�h�@����
	public boolean isOverSize() {
		return MODE_OVERSIZE.equals(getScreenMode());
	}
	
	
	// ��ʃ��[�h�����ɕύX
	public void setSmall() {
		setScreenMode(MODE_SMALL);
	}
	
	// ��ʃ��[�h�𒆂ɕύX
	public void setMiddle() {
		setScreenMode(MODE_MIDDLE);
	}
	
	// ��ʃ��[�h���ɕύX
	public void setLarge() {
		setScreenMode(MODE_LARGE);
	}
	
	// ��ʃ��[�h�����ɕύX
	public void setOverSize() {
		setScreenMode(MODE_OVERSIZE);
	}
	
	
	// �I�����ꂽ��ʃ��[�h�ɏ]���A�g�p����t�H���g���擾����
	public Font getViewFont() {
		return (Font)screenFont.get(getScreenMode());
	}
	
	// �I�����ꂽ��ʃ��[�h�ɓK������ʃT�C�Y���擾
	public Dimension getScreenSize() {
		return (Dimension)screenSize.get(getScreenMode());
	}
	
	private String getScreenMode() {
		return screenMode;
	}
	
	private void setScreenMode(String mode) {
		screenMode = mode;
		
		// �ݒ�t�@�C���ւ̕ۑ����s
		// �G���[�͖�������
		try {
			if (getPropertyXML() != null) {
				getPropertyXML().setForceValueAt(CONFIG_SCREEN_SIZE, screenMode);
				getPropertyXML().writeWithCheck();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		Font f = getViewFont();
		java.util.Enumeration keys = UIManager.getDefaults().keys();
		
    	while (keys.hasMoreElements()) {
    		Object key = keys.nextElement();
    		Object value = UIManager.get (key);
    		if (value instanceof Font) {
    			UIManager.put (key, f);
    		}
    	}
    	
    	
        this.setSize(getScreenSize());

        // �E�B���h�E�𒆉��ɔz�u
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
		
	}
    
	/**
	 * �X�N���[���T�C�Y�̕���
	 */
	public void resumeScreenSize() {
		
		try {
		
			if (!hasProperty(CONFIG_SCREEN_SIZE)) {
				return;
			}
			
			String size = getProperty(CONFIG_SCREEN_SIZE);
			
			if (MODE_SMALL.equals(size)) {
				// �f�t�H���g�̂܂܂Ȃ̂ŏ����Ȃ������AMac�̏ꍇ�͔O����
				if (ACOSInfo.isMac()) {
					setSmall();
				}
				
			} else if (MODE_MIDDLE.equals(size)) {
				setMiddle();
				
			} else if (MODE_LARGE.equals(size)) {
				setLarge();
				
			} else if (MODE_OVERSIZE.equals(size)) {
				setOverSize();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    

}