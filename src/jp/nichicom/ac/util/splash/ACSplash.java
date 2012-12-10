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
 * 汎用スプラッシュです。
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
     * コンストラクタです。
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
     * プログレス処理を終了して閉じます。
     */
    public void close() {
        dispose();
        setValidThread(false);
    }

    /**
     * アイコン画像パスを配列で返します。
     * 
     * @return アイコン画像パス
     */
    public String[] getIconPathes() {
        return iconPathes;
    }

    /**
     * 表示するメッセージを返します。
     * 
     * @return 表示するメッセージ
     */
    public String getMessage() {
        return this.getMessageLabel().getText();
    }

    /**
     * 要求するウィンドウサイズ を返します。
     * 
     * @return 要求するウィンドウサイズ
     */
    public Dimension getWindowSize() {
        return windowSize;
    }

    /**
     * ウィンドウサイズを更新します。
     * 
     * @param winSize 新しいサイズ
     */
    public void refreshSize(Dimension winSize) {
        // ウィンドウサイズの更新
        setWindowSize(winSize);
        initPosition();
    }

    /**
     * スプラッシュを強制的に再読み込みします。
     */
    public void reload() {
        splashIcons = null;
        loadSplash();
    }

    /**
     * スレッド実行処理です。
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
     * アイコン画像パスを配列で設定します。
     * 
     * @param iconPathes アイコン画像パス
     */
    public void setIconPathes(String[] iconPathes) {
        this.iconPathes = iconPathes;
    }

    /**
     * 表示するメッセージを設定します。
     * 
     * @param message 表示するメッセージ
     */
    public void setMessage(String message) {
        this.getMessageLabel().setText(message);
    }

    /**
     * 要求するウィンドウサイズ を設定します。
     * 
     * @param windowSize 要求するウィンドウサイズ
     */
    public void setWindowSize(Dimension windowSize) {
        this.windowSize = windowSize;
    }

    /**
     * スプラッシュを開始します。
     * 
     * @param title タイトル
     */
    public void showModaless(String title) {
        if ((title == null) || "".equals(title)) {
            getMessageLabel().setText("次画面展開中...");
        } else {
            getMessageLabel().setText("「" + title + "」展開中...");
        }

        setVisible(true);
        setValidThread(true);

        baseTimeMillis = System.currentTimeMillis();

        new Thread(this).start();
    }

    /**
     * スプラッシュを終了します。
     */
    public void stop() {
        close();
    }

    /**
     * 位置を初期化します。
     */
    private void initPosition() {
        // ウィンドウのサイズ
        setSize(getWindowSize());
        // ウィンドウを中央に配置
        Point corner = new Point(0, 0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsEnvironment genv = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        if (genv != null) {
            // GraphicsEnvironment はタスクバー等の占有領域も考慮する
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
     * 拡張領域への追加処理を実装します。
     * 
     * @param container 拡張領域
     * @return 拡張領域を表示するか
     */
    protected boolean addExpands(ACPanel container) {
        return false;
    }

    /**
     * 項目領域を生成します。
     * 
     * @return 項目領域
     */
    protected ACPanel createContents() {
        return new ACPanel();
    }

    /**
     * デフォルトウィンドウサイズを生成します。
     * 
     * @return デフォルトウィンドウサイズ
     */
    protected Dimension createDefaultWindowSize() {
        return new Dimension(400, 120);
    }

    /**
     * 拡張領域を生成します。
     * 
     * @return 拡張領域
     */
    protected ACPanel createExpands() {
        return new ACPanel();
    }

    /**
     * メッセージ表示領域を生成します。
     * 
     * @return メッセージ表示領域
     */
    protected ACLabel createMessageLabel() {
        return new ACLabel();
    }

    /**
     * 項目領域を返します。
     * 
     * @return 項目領域
     */
    protected ACPanel getContents() {
        if (contents == null) {
            contents = createContents();
        }
        return contents;
    }

    /**
     * 拡張領域を返します。
     * 
     * @return 拡張領域
     */
    protected ACPanel getExpands() {
        if (expands == null) {
            expands = createExpands();
        }
        return expands;
    }

    /**
     * メッセージ表示領域を返します。
     * 
     * @return メッセージ表示領域
     */
    protected ACLabel getMessageLabel() {
        if (message == null) {
            message = createMessageLabel();
        }
        return message;
    }

    /**
     * コンポーネントを初期化します。
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
     * スレッドが有効であるか を返します。
     * 
     * @return スレッドが有効であるか
     */
    protected boolean isValidThread() {
        return validThread;
    }

    /**
     * スプラッシュ画像を読み込みます。
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
     * スレッド実行中の定期処理を実装します。
     */
    protected void processThread() {

    }

    /**
     * スレッド開始時の定期処理を実装します。
     */
    protected void beginThread() {

    }

    /**
     * スレッド終了時の定期処理を実装します。
     */
    protected void endThread() {

    }

    // ウィンドウが閉じられたときに終了するようにオーバーライド
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            close();
        }
        super.processWindowEvent(e);
    }

    /**
     * スレッドが有効であるか を設定します。
     * 
     * @param validThread スレッドが有効であるか
     */
    protected void setValidThread(boolean validThread) {
        this.validThread = validThread;
    }
}
