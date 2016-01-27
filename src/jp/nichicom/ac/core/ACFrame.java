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
 * メインフレームです。
 * <p>
 * 画面遷移にかかる管理処理を実装しています。
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
     * デバッグモードで業務を起動します。
     * <p>
     * VRLook&Feelを適用し、指定の業務を開始します。
     * </p>
     * 
     * @param affair 業務情報
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
     * 唯一のインスタンスを返します。
     * 
     * @return 唯一のインスタンス
     */
    public static ACFrame getInstance() {
        if (singleton == null) {
            singleton = new ACFrame();
        }
        return singleton;
    }

    /**
     * VR Look & Feel を適用します。
     * 
     * @throws Exception 処理例外
     */
    public static void setVRLookAndFeel() throws Exception {
    	// Mac以外はLook&Feelを使用する
        if (!ACOSInfo.isMac()) {
            UIManager.setLookAndFeel("jp.nichicom.vr.plaf.metal.VRLookAndFeel");
        }
    }

    protected String exeFolderPath;

    protected ACFrameEventProcesser frameEventProcesser;

    protected ACAffairable nowPanel;

    protected List traceAffairs;
    
    
	// --- 画面サイズ定数
	public static final String MODE_SMALL = "Small";
	public static final String MODE_MIDDLE = "Middle";
	public static final String MODE_LARGE = "Large";
	public static final String MODE_OVERSIZE = "OverSize";
	
	// 現在選択されえている画面サイズ
	private String screenMode = MODE_SMALL;
	
	// 各画面サイズでのフォント指定
	private Map screenFont;
	private Map screenSize;
	
	// スクリーンサイズを保存するプロパティファイルのパス
	private static final String CONFIG_SCREEN_SIZE = "Config/ScreenSize";
	

    /**
     * コンストラクタです。 <br />
     * singleton Pattern
     * 
     * @throws HeadlessException 初期化例外
     */
    private ACFrame() throws HeadlessException {
        super();
        setTraceAffairs(new VRArrayList());

        File currentFolder = new File(".").getAbsoluteFile().getParentFile();
        if (currentFolder != null) {
            setExeFolderPath(currentFolder.getAbsolutePath());
        }
        
        // 画面サイズ変更関連の定数初期化
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
        
		// 起動時は画面サイズ小
		setSmall();
		
    }

    /**
     * 現在対象としている業務情報の画面遷移パラメタに指定パラメタを追加します。
     * 
     * @param bindPath 追加するパラメタのキーとなるバインドパス
     * @param parameter 追加するパラメタ値
     * @throws ParseException 解析例外
     */
    public void addNowAffairParameter(String bindPath, Object parameter)
            throws ParseException {
        ACAffairInfo affair = getNowAffair();
        if (affair != null) {
            VRMap parameters = affair.getParameters();
            if (parameters == null) {
                // 渡りパラメタが存在しなければ、空のMapとして生成する
                parameters = new VRHashMap();
                affair.setParameters(parameters);
            }

            // 現在の業務情報のパラメータとして追加
            VRBindPathParser.set(bindPath, parameters, parameter);
        }
    }

    /**
     * 直前の画面に遷移します。
     * 
     * @throws InstantiationException 業務クラスのインスタンス化失敗
     * @throws ClassCastException 画面遷移できない業務クラス
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
            // canBack内での履歴制御を考慮して再取得
            affair = getBackAffair();
            size = getTraceAffairs().size();

            logOfAffair("back(now): " + oldAffair);
            logOfAffair("back(new): " + affair);
            
            //履歴上は仮遷移させる
            getTraceAffairs().remove(size - 1);
            change(affair);
        } catch (Throwable ex) {
            if(size>getTraceAffairs().size()){
                //遷移に失敗した場合、最後に削除した遷移履歴を追加しなおす
                getTraceAffairs().add(oldAffair);
            }
 
            showExceptionMessage(ex);
            return;
        }
        // 2006/02/06[Tozo Tanaka] : replace end

        

    }

    /**
     * 業務関連のログを出力します。
     * 
     * @param message ログ
     */
    protected void logOfAffair(Object message) {
        ACFrameEventProcesser eventProcesser = getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventDebugger) {
            VRLogger.log(((ACFrameEventDebugger) eventProcesser)
                    .getAffairLogLevel(), message);
        }
    }

    /**
     * 前画面の業務情報を返します。
     * 
     * @return 前画面の業務情報
     */
    public ACAffairInfo getBackAffair() {
        int size = getTraceAffairs().size();
        if (size < 2) {
            return null;
        }
        return (ACAffairInfo) getTraceAffairs().get(size - 2);
    }

    /**
     * 実行フォルダパスを返します。
     * 
     * @return 実行フォルダパス
     */
    public String getExeFolderPath() {
        return exeFolderPath;
    }

    /**
     * カスタムイベント処理インスタンスを返します。
     * 
     * @return カスタムイベント処理インスタンス
     */
    public ACFrameEventProcesser getFrameEventProcesser() {
        return frameEventProcesser;
    }

    /**
     * 現在対象としている業務情報を返します。
     * 
     * @return 現在対象としている業務情報
     */
    public ACAffairInfo getNowAffair() {
        int size = getTraceAffairs().size();
        if (size < 1) {
            return null;
        }
        return (ACAffairInfo) getTraceAffairs().get(size - 1);
    }

    /**
     * 現在対象としている業務パネルクラスを返します。
     * 
     * @return 業務パネルクラス
     */
    public ACAffairable getNowPanel() {
        return nowPanel;
    }

    /**
     * プロパティファイルから値を取得します。
     * 
     * @param path キー
     * @throws Exception 処理例外
     * @return キーに対応する値
     */
    public String getProperty(String path) throws Exception {
        ACPropertyXML xml = getPropertyXML();
        if (xml != null) {
            return xml.getValueAt(path);
        }
        return null;
    }

    /**
     * 設定ファイルを返します。
     * 
     * @throws Exception 処理例外
     * @return 設定ファイル
     */
    public ACPropertyXML getPropertyXML() throws Exception {
        if (getFrameEventProcesser() != null) {
            return getFrameEventProcesser().getPropertyXML();
        }
        return null;
    }

    /**
     * 画面遷移経路 を返します。
     * 
     * @return 画面遷移経路
     */
    public List getTraceAffairs() {
        return traceAffairs;
    }

    /**
     * プロパティファイルからキーに対応する値が定義されているかを取得します。
     * 
     * @param path キー
     * @throws Exception 処理例外
     * @return キーに対応する値が定義されているか
     */
    public boolean hasProperty(String path) throws Exception {
        ACPropertyXML xml = getPropertyXML();
        if (xml != null) {
            return xml.hasValueAt(path);
        }
        return false;
    }

    /**
     * 次の画面に遷移します。
     * 
     * @param affair 遷移先業務情報
     * @throws InstantiationException 業務クラスのインスタンス化失敗
     * @throws ClassCastException 画面遷移できない業務クラス
     */
    public void next(ACAffairInfo affair) throws ClassCastException,
            InstantiationException {
        int oldSize = getTraceAffairs().size();
        try {
            if (getTraceAffairs().size() == 0) {
                // 初回の遷移時にシステム初期化
                initSystem();
            }
            logOfAffair("back(now): " + getNowAffair());
            logOfAffair("back(new): " + affair);

            //履歴上は仮遷移させる
            getTraceAffairs().add(affair);
            change(affair);
        } catch (Throwable ex) {
            if(oldSize<getTraceAffairs().size()){
                //遷移に失敗した場合、最後に追加した遷移履歴を破棄する
                getTraceAffairs().remove(oldSize);
            }
            showExceptionMessage(ex);
            return;
        }

    }

    /**
     * 現在対象としている業務情報の画面遷移パラメタから指定パラメタを除去します。
     * 
     * @param bindPath 除去するパラメタのキーとなるバインドパス
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
     * カスタムイベント処理インスタンスを設定します。
     * 
     * @param flameEventProcesser カスタムイベント処理インスタンス
     */
    public void setFrameEventProcesser(
            ACFrameEventProcesser flameEventProcesser) {
        this.frameEventProcesser = flameEventProcesser;
        refreshFrameEventProcesser();
    }

    /**
     * 例外メッセージを表示します。
     * 
     * @param ex 例外
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
                // 現在のフレームサイズを取得
                Rectangle r = super.getBounds();
                Dimension newSize = null;
                // フレームが最小値より小さければ、最小値に設定し直す
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
     * 画面遷移を実行します。
     * 
     * @param newAffair 遷移先業務情報
     * @throws InstantiationException 業務クラスのインスタンス化失敗
     * @throws ClassCastException 画面遷移できない業務クラス
     * @throws Throwable その他の例外
     */
    private void change(ACAffairInfo newAffair) throws InstantiationException,
            ClassCastException, Throwable {

        ACFrameEventProcesser eventProcesser = getFrameEventProcesser();

        if (eventProcesser instanceof ACFrameEventDebugger) {
            // ログ出力レベルを再設定
            ((ACFrameEventDebugger) eventProcesser).refleshSetting();
        }

        Object affair;
        try {
            String className = newAffair.getClassName();
            if (eventProcesser instanceof ACFrameEventProcesser) {
                ACAffairReplacable replacer = eventProcesser
                        .getAffairReplacer();
                if (replacer instanceof ACAffairReplacable) {
                    // 業務置換クラスが定義されていれば、エディション等に応じて適切な業務クラス名に置換する
                    className = replacer.getValidClassName(className);
                }
            }

            affair = Class.forName(className).newInstance();
        } catch (Exception ex) {
            throw new InstantiationException(newAffair.getClassName()
                    + " はインスタンス化できないクラスです");
        }
        if (!(affair instanceof ACAffairable) || !(affair instanceof Component)) {
            throw new ClassCastException(newAffair.getClassName()
                    + " は遷移可能な画面コンポーネントではありません");
        }

        ACSplashable splash = null;
        if (newAffair.isSplashed()) {
            if (eventProcesser instanceof ACFrameEventProcesser) {
                // 業務遷移時にスプラッシュを使用する
                splash = eventProcesser.createSplash(newAffair.getTitle());
            }
        }

        if (getNowPanel() instanceof ACAffairContainer) {
            // ステータスバーのタイマスレッドを明示的に停止させ、メモリーリークを防止する
            ((ACAffairContainer) getNowPanel()).cancelTimer();
        }
        setNowPanel(null);
        getContentPane().removeAll();

        // インスタンスのファイナライズとGCを実行し、前業務をクリアする。
        System.runFinalization();
        System.gc();

        ACAffairable panel = (ACAffairable) affair;
        getContentPane().add((Component) panel);
        setNowPanel(panel);
        
        //[ID:0000754][Shin Fujihara] 2012/09 edit begin 2012年度対応 閲覧ログ出力機能
        //※panel.initAffair内で、パラメータがクリアされるパターンがあるので、ここで出力実行
        if (panel instanceof ACBrowseLogWritable) {
        	((ACBrowseLogWritable)panel).writeBrowseLog(newAffair);
        }
        //[ID:0000754][Shin Fujihara] 2012/09 edit end 2012年度対応 閲覧ログ出力機能

        if (panel instanceof ACAffairContainer) {
            // ステータスバーの再描画を実行する
            ACStatusBar statusBar = ((ACAffairContainer) panel).getStatusBar();
            if (statusBar instanceof ACStatusBar) {
                statusBar.checkLookingKeyState();
            }
        }

        String newTitle = newAffair.getTitle();
        if ((newTitle != null) && (!"".equals(newTitle))) {
            // 遷移元業務からタイトルを設定させる場合
            setTitle(newTitle);
        }

        panel.initAffair(newAffair);
        // 強制再構成
        getContentPane().invalidate();
        getContentPane().validate();

        Component focusComp = panel.getFirstFocusComponent();
        if (focusComp != null) {
            // 画面展開後のフォーカス位置指定
            focusComp.requestFocus();
        }

        if (splash != null) {
            splash.close();
            splash = null;
        }
    }

    /**
     * 初回の画面遷移時にシステム初期化処理を実行します。
     * 
     * @throws Exception 処理例外
     */
    protected void initSystem() throws Exception {
        ACFrameEventProcesser eventProcesser = getFrameEventProcesser();
        if (eventProcesser instanceof ACFrameEventProcesser) {
            // システム初期化
            eventProcesser.initSystem();
        }
    }

    // ウィンドウが閉じられたときに終了するようにオーバーライド
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (getNowPanel() != null) {
                try {
                    FocusManager fm = FocusManager.getCurrentManager();
                    if (fm != null) {
                        Component cmp = fm.getFocusOwner();
                        if (cmp != null) {
                            // フォーカスロスト前に×ボタンで閉じる場合を想定し、擬似的にFocusLostを発行する
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
     * カスタムイベント処理インスタンスの情報を再適用します。
     */
    protected void refreshFrameEventProcesser() {
        ACFrameEventProcesser flameEventProcesser = getFrameEventProcesser();
        if (flameEventProcesser != null) {
            // デフォルトサイズの反映
            Dimension defaultSize = flameEventProcesser.getDefaultWindowSize();
            if (defaultSize != null) {
                setSize(defaultSize);
            }
            // フレームアイコンの反映
            Image icon = flameEventProcesser.getFrameIcon();
            if (icon != null) {
                // アイコンをセット
                setIconImage(icon);
            }
        }
    }

    /**
     * 実行フォルダパスを設定します。
     * 
     * @param exeFolderPath 実行フォルダパス
     */
    protected void setExeFolderPath(String exeFolderPath) {
        this.exeFolderPath = exeFolderPath;
    }

    /**
     * 現在対象としている業務パネルクラスを設定します。
     * 
     * @param nowPanel 業務パネルクラス
     */
    protected void setNowPanel(ACAffairable nowPanel) {
        this.nowPanel = nowPanel;
    }

    /**
     * 画面遷移経路 を設定します。
     * 
     * @param traceAffairs 画面遷移経路
     */
    protected void setTraceAffairs(List traceAffairs) {
        this.traceAffairs = traceAffairs;
    }
    
    
    
    // --- 画面サイズ変更機能対応
	// 画面モード　標準
	public boolean isSmall() {
		return MODE_SMALL.equals(getScreenMode());
	}
	
	// 画面モード　中
	public boolean isMiddle() {
		return MODE_MIDDLE.equals(getScreenMode());
	}
	
	// 画面モード　大
	public boolean isLarge() {
		return MODE_LARGE.equals(getScreenMode());
	}
	
	// 画面モード　特大
	public boolean isOverSize() {
		return MODE_OVERSIZE.equals(getScreenMode());
	}
	
	
	// 画面モードを小に変更
	public void setSmall() {
		setScreenMode(MODE_SMALL);
	}
	
	// 画面モードを中に変更
	public void setMiddle() {
		setScreenMode(MODE_MIDDLE);
	}
	
	// 画面モードを大に変更
	public void setLarge() {
		setScreenMode(MODE_LARGE);
	}
	
	// 画面モードを特大に変更
	public void setOverSize() {
		setScreenMode(MODE_OVERSIZE);
	}
	
	
	// 選択された画面モードに従い、使用するフォントを取得する
	public Font getViewFont() {
		return (Font)screenFont.get(getScreenMode());
	}
	
	// 選択された画面モードに適した画面サイズを取得
	public Dimension getScreenSize() {
		return (Dimension)screenSize.get(getScreenMode());
	}
	
	private String getScreenMode() {
		return screenMode;
	}
	
	private void setScreenMode(String mode) {
		screenMode = mode;
		
		// 設定ファイルへの保存実行
		// エラーは無視する
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

        // ウィンドウを中央に配置
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
	 * スクリーンサイズの復元
	 */
	public void resumeScreenSize() {
		
		try {
		
			if (!hasProperty(CONFIG_SCREEN_SIZE)) {
				return;
			}
			
			String size = getProperty(CONFIG_SCREEN_SIZE);
			
			if (MODE_SMALL.equals(size)) {
				// デフォルトのままなので処理なしだが、Macの場合は念押し
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