package jp.nichicom.ac.util;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.ACOSInfo;
import jp.nichicom.ac.core.ACDialogChaine;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.ac.util.splash.ACSplashChaine;
import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class ACMessageBoxDialogPlus extends JDialog {

    private VRPanel contents = new VRPanel();
    private VRLabel icon = new VRLabel();
    private JTextArea message = new JTextArea();
    private VRPanel messages = new VRPanel();
	private VRPanel buttons = new VRPanel();
    private VRPanel selects = new VRPanel();
    private JLabel spacer = new JLabel();
    private VRButton select1 = new VRButton();
    private VRButton select2 = new VRButton();
    private VRButton select3 = new VRButton();
    private VRButton select4 = new VRButton();
    private VRPanel cancels = new VRPanel();
    private VRButton cancel = new VRButton();

    private int result = ACMessageBox.RESULT_CANCEL;

    private KeyListener buttonKeyListener;

    public static final int RESULT_SELECT1 = 0;
    public static final int RESULT_SELECT2 = 1;
    public static final int RESULT_SELECT3 = 3;
    public static final int RESULT_SELECT4 = 4;

    /**
     * コンストラクタです。
     *
     * @param frame 親フレーム
     * @param title ダイアログタイトル
     * @param modal モーダル表示するか
     */
    public ACMessageBoxDialogPlus(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // イベント関連付け
        select1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(RESULT_SELECT1);
                dispose();
            }
        });

        select2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(RESULT_SELECT2);
                dispose();
            }
        });

        select3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(RESULT_SELECT3);
                dispose();
            }
        });

        select4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(RESULT_SELECT4);
                dispose();
            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setResult(ACMessageBox.RESULT_CANCEL);
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

        select1.addKeyListener(buttonKeyListener);
        select2.addKeyListener(buttonKeyListener);
        select3.addKeyListener(buttonKeyListener);
        select4.addKeyListener(buttonKeyListener);
        cancel.addKeyListener(buttonKeyListener);
    }

    /**
     * コンポーネントを設定します。
     *
     * @throws Exception 処理例外
     */
    private void jbInit() throws Exception {

        ((JPanel) this.getContentPane()).add(contents);
        contents.setLayout(new BorderLayout());
        contents.add(icon, BorderLayout.WEST);
        contents.add(messages, BorderLayout.CENTER);
        contents.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        icon.setOpaque(false);
        icon.setPreferredSize(new Dimension(50, 50));
        icon.setIcon(ACResourceIconPooler.getInstance().getImage(ACConstants.ICON_PATH_QUESTION_48));
        VRLayout lay = new VRLayout();
        lay.setVAlignment(VRLayout.CENTER);
        lay.setFitHLast(true);
        lay.setHgrid(0);
        lay.setHgap(0);
        lay.setVgap(0);
        lay.setAutoWrap(false);
        lay.setLabelMargin(0);
        messages.setLayout(lay);
        messages.add(message, VRLayout.FLOW);
        message.setFocusable(false);
        message.setOpaque(false);
        message.setRequestFocusEnabled(true);
        message.setEditable(false);
        message.setText("");
        message.setLineWrap(true);
        getContentPane().add(buttons, BorderLayout.SOUTH);
        buttons.setLayout(new VRLayout());
        buttons.add(selects, VRLayout.NORTH);
        buttons.add(cancels, VRLayout.NORTH);
        VRLayout buttonsLayout = new VRLayout(VRLayout.CENTER, 4, 0);
        buttonsLayout.setLabelMargin(0);
        buttonsLayout.setHgap(0);
        buttonsLayout.setHgrid(1);
        buttonsLayout.setVgap(0);
        buttonsLayout.setAutoWrap(false);
        selects.setLayout(buttonsLayout);
        selects.add(spacer, VRLayout.FLOW_RETURN);
        selects.add(select1, VRLayout.FLOW);
        selects.add(select2, VRLayout.FLOW_RETURN);
        selects.add(select3, VRLayout.FLOW);
        selects.add(select4, VRLayout.FLOW_RETURN);
        selects.setBorder(BorderFactory.createEmptyBorder(4, 4, 0, 4));
        selects.setMaximumSize(new Dimension(32767, 32767));
        cancels.setLayout(new VRLayout());
        cancels.add(cancel, VRLayout.EAST);
        cancels.setBorder(BorderFactory.createEmptyBorder(0, 4, 4, 4));
        cancels.setMaximumSize(new Dimension(32767, 32767));
        spacer.setMinimumSize(new Dimension(0, 0));
        spacer.setPreferredSize(new Dimension(300, 0));
        spacer.setText("");
        select1.setHorizontalAlignment(JButton.LEFT);
        select2.setHorizontalAlignment(JButton.LEFT);
        select3.setHorizontalAlignment(JButton.LEFT);
        select4.setHorizontalAlignment(JButton.LEFT);
        if (!ACOSInfo.isMac()) {
            select1.setBackground(IkenshoConstants.COLOR_BASE);
            select2.setBackground(IkenshoConstants.COLOR_BASE);
            select3.setBackground(IkenshoConstants.COLOR_DISTINCTION);
            select4.setBackground(IkenshoConstants.COLOR_DISTINCTION);
            select1.setForeground(Color.white);
            select2.setForeground(Color.white);
            select3.setForeground(Color.white);
            select4.setForeground(Color.white);
        }
        cancel.setMnemonic('C');
        cancel.setText("キャンセル(C)");
        cancel.setHorizontalAlignment(JButton.LEFT);
        setResizable(false);
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
     * @param expands 拡張領域
     */
    public void showInner(String message,
            String text1, char mnemonic1, String text2, char mnemonic2,
            String text3, char mnemonic3, String text4, char mnemonic4) {
        select1.setText(text1);
        select1.setMnemonic(mnemonic1);
        select2.setText(text2);
        select2.setMnemonic(mnemonic2);
        select3.setText(text3);
        select3.setMnemonic(mnemonic3);
        select4.setText(text4);
        select4.setMnemonic(mnemonic4);
        this.message.setText(message);
        initWindow();
    }

    /**
     * 画面サイズと位置を初期化します。
     */
    private void initWindow() {

        // ボタンのサイズ
        select1.setPreferredSize(new Dimension(select3.getPreferredSize().width, cancel.getPreferredSize().height));
        select2.setPreferredSize(new Dimension(select4.getPreferredSize().width, cancel.getPreferredSize().height));

        // ウィンドウのサイズ
    	ACFrame frame = ACFrame.getInstance();
    	if (frame.isSmall()){
    		setSize(new Dimension(395, 185));
    	}
    	else if (frame.isMiddle()){
    		setSize(new Dimension(477, 205));
    	}
    	else if (frame.isLarge()){
    		setSize(new Dimension(601, 228));
    	}
    	else {
    		setSize(new Dimension(652, 238));
    	}

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

        // メッセージボックスを表示するときは、スプラッシュを強制解除する
        ACSplashChaine.getInstance().closeAll();

        ACDialogChaine.getInstance().add(this);

        setVisible(true);
    }

    public void dispose() {
        ACDialogChaine.getInstance().remove(this);
        super.dispose();
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End