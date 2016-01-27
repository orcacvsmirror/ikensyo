package jp.nichicom.ac.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.core.ACDialogChaine;
import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.ac.util.splash.ACSplashChaine;
import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * メッセージダイアログの実装クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/07/09
 */
public class ACMessageBoxDialog extends JDialog {

    private VRPanel buttons = new VRPanel();
    private VRButton cancel = new VRButton();
    private VRPanel contents = new VRPanel();
    private VRPanel expands = new VRPanel();
    private VRLabel icon = new VRLabel();

    private JTextArea message = new JTextArea();
    private VRPanel messages = new VRPanel();
    private VRButton no = new VRButton();
    private VRButton ok = new VRButton();
    private int result = ACMessageBox.RESULT_CANCEL;
    private JLabel spacer = new JLabel();

    private VRButton yes = new VRButton();

    protected KeyListener buttonKeyListener;

    /**
     * cancelボタンボタン を返します。
     * @return cancel
     */
    public VRButton getCancelButton() {
        return cancel;
    }

    /**
     * cancelボタン を設定します。
     * @param cancel cancelボタン
     */
    public void setCancelButton(VRButton cancel) {
        this.cancel = cancel;
    }

    /**
     * noボタン を返します。
     * @return noボタン
     */
    public VRButton getNoButton() {
        return no;
    }

    /**
     * noボタン を設定します。
     * @param no noボタン
     */
    public void setNoButton(VRButton no) {
        this.no = no;
    }

    /**
     * okボタン を返します。
     * @return okボタン
     */
    public VRButton getOKButton() {
        return ok;
    }

    /**
     * okボタン を設定します。
     * @param ok okボタン
     */
    public void setOKButton(VRButton ok) {
        this.ok = ok;
    }

    /**
     * yesボタン を返します。
     * @return yesボタン
     */
    public VRButton getYesButton() {
        return yes;
    }

    /**
     * yesボタン を設定します。
     * @param yes yesボタン
     */
    public void setYesButton(VRButton yes) {
        this.yes = yes;
    }

    /**
     * コンストラクタです。
     * 
     * @param frame 親フレーム
     * @param title ダイアログタイトル
     * @param modal モーダル表示するか
     */
    public ACMessageBoxDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // イベント関連付け
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(ACMessageBox.RESULT_OK);
                dispose();
            }
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(ACMessageBox.RESULT_CANCEL);
                dispose();
            }
        });
        yes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(ACMessageBox.RESULT_YES);
                dispose();
            }
        });
        no.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(ACMessageBox.RESULT_NO);
                dispose();
            }
        });

        buttonKeyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (!(e.getSource() instanceof VRButton)) {
                    return;
                }
                VRButton btn = (VRButton) e.getSource();

                switch (e.getKeyCode()) {
                case KeyEvent.VK_RIGHT:
                    btn.transferFocus();
                    break;
                case KeyEvent.VK_LEFT:
                    btn.transferFocusBackward();
                    break;
                case KeyEvent.VK_ESCAPE:
                    dispose();
                    break;
                }
            }
        };

        ok.addKeyListener(buttonKeyListener);
        yes.addKeyListener(buttonKeyListener);
        no.addKeyListener(buttonKeyListener);
        cancel.addKeyListener(buttonKeyListener);

    }
    
    public void dispose() {
        ACDialogChaine.getInstance().remove(this);
        super.dispose();
    }
    
    /**
     * ボタンの押下状態を返します。
     * 
     * @return ボタンの押下状態
     */
    public int getResult() {
        return result;
    }

    /**
     * ボタンの押下状態を設定します。
     * 
     * @param result ボタンの押下状態
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * 画面を表示します。
     * 
     * @param message 本文
     * @param buttons ボタン形式
     */
    public void showInner(String message, int buttons) {
        this.showInner(message, buttons, ACMessageBox.ICON_NONE,
                ACMessageBox.DEFAULT_FOCUS);
    }

    /**
     * 画面を表示します。
     * 
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @param focus フォーカスを当てるボタン
     */
    public void showInner(String message, int buttons, int icon, int focus) {
        this.showInner(message, buttons, icon, focus, null, false);
    }

    /**
     * 画面を表示します。
     * 
     * @param message 本文
     * @param buttons ボタン形式
     * @param icon アイコン形式
     * @param focus フォーカスを当てるボタン
     * @param expands 拡張領域
     * @param resizable 拡大縮小可能か
     */
    public void showInner(String message, int buttons, int icon, int focus,
            Component expands, boolean resizable) {
        this.message.setText(message);

        switch (icon) {
        case ACMessageBox.ICON_INFOMATION:
            this.icon.setIcon(ACResourceIconPooler.getInstance().getImage(
                    ACConstants.ICON_PATH_INFORMATION_48));
            break;
        case ACMessageBox.ICON_QUESTION:
            this.icon.setIcon(ACResourceIconPooler.getInstance().getImage(
                    ACConstants.ICON_PATH_QUESTION_48));
            break;
        case ACMessageBox.ICON_EXCLAMATION:
            this.icon.setIcon(ACResourceIconPooler.getInstance().getImage(
                    ACConstants.ICON_PATH_EXCLAMATION_48));
            break;
        case ACMessageBox.ICON_STOP:
            this.icon.setIcon(ACResourceIconPooler.getInstance().getImage(
                    ACConstants.ICON_PATH_STOP_48));
            break;
        default:
            this.icon.setVisible(false);
            break;
        }

        ok.setVisible((buttons & ACMessageBox.BUTTON_OK) != 0);
        cancel.setVisible((buttons & ACMessageBox.BUTTON_CANCEL) != 0);
        yes.setVisible((buttons & ACMessageBox.BUTTON_YES) != 0);
        no.setVisible((buttons & ACMessageBox.BUTTON_NO) != 0);

        switch (focus) {
        case ACMessageBox.FOCUS_CANCEL:
            cancel.requestFocus();
            break;
        case ACMessageBox.FOCUS_OK:
            ok.requestFocus();
            break;
        case ACMessageBox.FOCUS_YES:
            yes.requestFocus();
            break;
        case ACMessageBox.FOCUS_NO:
            no.requestFocus();
            break;
        }

        this.expands.removeAll();
        if (expands != null) {
            this.expands.add(expands, BorderLayout.CENTER);
        }
        setResizable(resizable);

        initWindow();
    }

    /**
     * 画面サイズと位置を初期化します。
     */
    private void initWindow() {
        Dimension win = this.getSize();
        Dimension contS = getContentPane().getSize();

        Dimension contC = new Dimension((int) (win.getWidth() - contS
                .getWidth()), (int) (win.getHeight() - contS.getHeight()));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (expands != null) {
            // 拡張コンポーネントを設定している場合はサイズを拡張する
            Dimension exSize = expands.getPreferredSize();
            contC.setSize(Math.max(contC.getWidth(), exSize.getWidth()), contC
                    .getHeight()
                    + exSize.getHeight());
        }

        message.setLineWrap(false);

        Insets is = contents.getInsets();

        int w = (int) (icon.getPreferredSize().width
                + message.getPreferredSize().getWidth() + is.left + is.right + contC
                .getWidth());
        if (w > screenSize.getWidth()) {
            message.setLineWrap(true);
            message
                    .setSize(new Dimension((int) (screenSize.getWidth()
                            - contC.getWidth() - icon.getPreferredSize()
                            .getWidth()), 0));

            this.setSize((int) screenSize.getWidth(), (int) (Math.max(message
                    .getPreferredSize().getHeight(), icon.getPreferredSize()
                    .getHeight())
                    + buttons.getPreferredSize().getHeight()
                    + is.top
                    + is.bottom + contC.getHeight()));
        } else {
            this.invalidate();

            this.setSize((int) Math.max(w, buttons.getPreferredSize()
                    .getWidth()
                    + contC.getHeight()), (int) (Math.max(message
                    .getPreferredSize().getHeight(), icon.getPreferredSize()
                    .getHeight())
                    + buttons.getPreferredSize().getHeight()
                    + is.top
                    + is.bottom + contC.getHeight()));
        }
        this.validate();

        // ウィンドウを中央に配置
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

        // メッセージボックスを表示するときは、スプラッシュを強制解除する
        ACSplashChaine.getInstance().closeAll();
        
        ACDialogChaine.getInstance().add(this);

        setVisible(true);
    }

    /**
     * コンポーネントを設定します。
     * 
     * @throws Exception 処理例外
     */
    private void jbInit() throws Exception {
        ok.setMargin(new Insets(0, 14, 0, 14));
        ok.setMnemonic('O');
        ok.setText("OK");
        icon.setOpaque(false);
        icon.setPreferredSize(new Dimension(50, 50));
        contents.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        buttons.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        buttons.setMaximumSize(new Dimension(32767, 32767));
        cancel.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_CANCEL_24));
        cancel.setMargin(new Insets(0, 14, 0, 14));
        cancel.setMnemonic('C');
        cancel.setText("キャンセル(C)");
        yes.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_YES_24));
        yes.setMargin(new Insets(0, 14, 0, 14));
        yes.setMnemonic('Y');
        yes.setText("はい(Y)");
        no.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_NO_24));
        no.setMargin(new Insets(0, 14, 0, 14));
        no.setMnemonic('N');
        no.setText("いいえ(N)");
        message.setFocusable(false);
        message.setOpaque(false);
        message.setRequestFocusEnabled(true);
        message.setEditable(false);
        message.setText("");
        message.setLineWrap(true);
        VRLayout lay = new VRLayout();
        lay.setVAlignment(VRLayout.CENTER);
        lay.setFitHLast(true);
        lay.setHgrid(0);
        lay.setHgap(0);
        lay.setVgap(0);
        lay.setAutoWrap(false);
        lay.setLabelMargin(0);
        spacer.setMinimumSize(new Dimension(0, 0));
        spacer.setPreferredSize(new Dimension(300, 0));
        spacer.setText("");
        getContentPane().add(buttons, BorderLayout.SOUTH);
        VRLayout buttonsLayout = new VRLayout(VRLayout.CENTER, 4, 0);
        buttonsLayout.setLabelMargin(0);
        buttonsLayout.setHgap(0);
        buttonsLayout.setHgrid(1);
        buttonsLayout.setVgap(0);
        buttonsLayout.setAutoWrap(false);
        buttons.setLayout(buttonsLayout);
        contents.setLayout(new BorderLayout());
        buttons.add(spacer, VRLayout.FLOW_RETURN);
        buttons.add(yes, VRLayout.FLOW);
        buttons.add(no, VRLayout.FLOW);
        buttons.add(ok, VRLayout.FLOW);
        buttons.add(cancel, VRLayout.FLOW);

        expands.setLayout(new BorderLayout());
        getContentPane().add(contents, BorderLayout.NORTH);
        // getContentPane().add(contents, BorderLayout.CENTER);
        getContentPane().add(expands, BorderLayout.CENTER);
        contents.add(icon, BorderLayout.WEST);
        contents.add(messages, BorderLayout.CENTER);
        messages.setLayout(lay);
        messages.add(message, VRLayout.FLOW);
        ok.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_OK_24));
        
        setResizable(false);
        
    }

}
