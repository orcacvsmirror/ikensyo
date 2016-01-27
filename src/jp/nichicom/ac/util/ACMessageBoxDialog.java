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
 * ���b�Z�[�W�_�C�A���O�̎����N���X�ł��B
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
     * cancel�{�^���{�^�� ��Ԃ��܂��B
     * @return cancel
     */
    public VRButton getCancelButton() {
        return cancel;
    }

    /**
     * cancel�{�^�� ��ݒ肵�܂��B
     * @param cancel cancel�{�^��
     */
    public void setCancelButton(VRButton cancel) {
        this.cancel = cancel;
    }

    /**
     * no�{�^�� ��Ԃ��܂��B
     * @return no�{�^��
     */
    public VRButton getNoButton() {
        return no;
    }

    /**
     * no�{�^�� ��ݒ肵�܂��B
     * @param no no�{�^��
     */
    public void setNoButton(VRButton no) {
        this.no = no;
    }

    /**
     * ok�{�^�� ��Ԃ��܂��B
     * @return ok�{�^��
     */
    public VRButton getOKButton() {
        return ok;
    }

    /**
     * ok�{�^�� ��ݒ肵�܂��B
     * @param ok ok�{�^��
     */
    public void setOKButton(VRButton ok) {
        this.ok = ok;
    }

    /**
     * yes�{�^�� ��Ԃ��܂��B
     * @return yes�{�^��
     */
    public VRButton getYesButton() {
        return yes;
    }

    /**
     * yes�{�^�� ��ݒ肵�܂��B
     * @param yes yes�{�^��
     */
    public void setYesButton(VRButton yes) {
        this.yes = yes;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param frame �e�t���[��
     * @param title �_�C�A���O�^�C�g��
     * @param modal ���[�_���\�����邩
     */
    public ACMessageBoxDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // �C�x���g�֘A�t��
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
     * �{�^���̉�����Ԃ�Ԃ��܂��B
     * 
     * @return �{�^���̉������
     */
    public int getResult() {
        return result;
    }

    /**
     * �{�^���̉�����Ԃ�ݒ肵�܂��B
     * 
     * @param result �{�^���̉������
     */
    public void setResult(int result) {
        this.result = result;
    }

    /**
     * ��ʂ�\�����܂��B
     * 
     * @param message �{��
     * @param buttons �{�^���`��
     */
    public void showInner(String message, int buttons) {
        this.showInner(message, buttons, ACMessageBox.ICON_NONE,
                ACMessageBox.DEFAULT_FOCUS);
    }

    /**
     * ��ʂ�\�����܂��B
     * 
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     */
    public void showInner(String message, int buttons, int icon, int focus) {
        this.showInner(message, buttons, icon, focus, null, false);
    }

    /**
     * ��ʂ�\�����܂��B
     * 
     * @param message �{��
     * @param buttons �{�^���`��
     * @param icon �A�C�R���`��
     * @param focus �t�H�[�J�X�𓖂Ă�{�^��
     * @param expands �g���̈�
     * @param resizable �g��k���\��
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
     * ��ʃT�C�Y�ƈʒu�����������܂��B
     */
    private void initWindow() {
        Dimension win = this.getSize();
        Dimension contS = getContentPane().getSize();

        Dimension contC = new Dimension((int) (win.getWidth() - contS
                .getWidth()), (int) (win.getHeight() - contS.getHeight()));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (expands != null) {
            // �g���R���|�[�l���g��ݒ肵�Ă���ꍇ�̓T�C�Y���g������
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

        // �E�B���h�E�𒆉��ɔz�u
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

        // ���b�Z�[�W�{�b�N�X��\������Ƃ��́A�X�v���b�V����������������
        ACSplashChaine.getInstance().closeAll();
        
        ACDialogChaine.getInstance().add(this);

        setVisible(true);
    }

    /**
     * �R���|�[�l���g��ݒ肵�܂��B
     * 
     * @throws Exception ������O
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
        cancel.setText("�L�����Z��(C)");
        yes.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_YES_24));
        yes.setMargin(new Insets(0, 14, 0, 14));
        yes.setMnemonic('Y');
        yes.setText("�͂�(Y)");
        no.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_NO_24));
        no.setMargin(new Insets(0, 14, 0, 14));
        no.setMnemonic('N');
        no.setText("������(N)");
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
