package jp.nichicom.ac.component;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.io.ACResourceIconPooler;

/**
 * �e�L�X�g�n�R���|�[�l���g�p�̃|�b�v�A�b�v���j���[�Z�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACTextComponentPopupMenu extends JPopupMenu implements
        MouseListener, FocusListener {
    private JMenuItem copy;
    private JMenuItem cut;
    private JMenuItem delete;
    private JTextComponent lastInvoker;
    private int lastInvokerSelectionEnd;
    private int lastInvokerSelectionStart;
    private JMenuItem paste;

    /**
     * Constructs a <code>JPopupMenu</code> without an "invoker".
     */
    public ACTextComponentPopupMenu() {
        super();
        initComponent();
    }

    /**
     * Constructs a <code>JPopupMenu</code> with the specified title.
     * 
     * @param label the string that a UI may use to display as a title for the
     *            popup menu.
     */
    public ACTextComponentPopupMenu(String label) {
        super(label);
        initComponent();
    }

    /**
     * �|�b�v�A�b�v�g���K��ǉ����܂��B
     * 
     * @param invoker �|�b�v�A�b�v�g���K
     */
    public void addInvoker(JTextComponent invoker) {
        if (invoker != null) {
            invoker.addMouseListener(this);
            invoker.addFocusListener(this);
        }
    }

    public void focusGained(FocusEvent e) {
        if (e.getComponent() == getLastInvoker()) {
            getLastInvoker().setSelectionStart(getLastInvokerSelectionStart());
            getLastInvoker().setSelectionEnd(getLastInvokerSelectionEnd());
        }
    }

    public void focusLost(FocusEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        showPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
        showPopup(e);
    }

    /**
     * �|�b�v�A�b�v�g���K���폜���܂��B
     * 
     * @param invoker �|�b�v�A�b�v�g���K
     */
    public void removeInvoker(JTextComponent invoker) {
        if (invoker != null) {
            invoker.removeMouseListener(this);
            invoker.removeFocusListener(this);
        }
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
        boolean canPaste=false;
        Clipboard clip=Toolkit.getDefaultToolkit().getSystemClipboard();
        if(clip!=null){
            Transferable t= clip.getContents(this);
            if(t!=null){
                if(t.isDataFlavorSupported(DataFlavor.stringFlavor)){
                    canPaste = true;
                }
            }
        }
        paste.setEnabled(canPaste);
    }

    /**
     * �|�b�v�A�b�v���j���[��\�����܂��B
     * 
     * @param e �|�b�v�A�b�v���j���[
     */
    public void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            if (e.getComponent() instanceof JTextComponent) {
                JTextComponent comp = (JTextComponent) e.getComponent();
                setLastInvoker(comp);
                int start = comp.getSelectionStart();
                int end = comp.getSelectionEnd();
                setLastInvokerSelectionStart(start);
                setLastInvokerSelectionEnd(end);
                boolean selected = end - start > 0;
                cut.setEnabled(selected);
                copy.setEnabled(selected);
                delete.setEnabled(selected);

                show(e.getComponent(), e.getX(), e.getY());
                comp.requestFocus();
            }
        }
    }

    /**
     * �|�b�v�A�b�v���j���[�̏��L�� ��Ԃ��܂��B
     * 
     * @return �|�b�v�A�b�v���j���[�̏��L��
     */
    protected JTextComponent getLastInvoker() {
        return lastInvoker;
    }

    /**
     * �|�b�v�A�b�v���L�҂̑I���I���ʒu ��Ԃ��܂��B
     * 
     * @return �|�b�v�A�b�v���L�҂̑I���I���ʒu
     */
    protected int getLastInvokerSelectionEnd() {
        return lastInvokerSelectionEnd;
    }

    /**
     * �|�b�v�A�b�v���L�҂̑I���J�n�ʒu ��Ԃ��܂��B
     * 
     * @return �|�b�v�A�b�v���L�҂̑I���J�n�ʒu
     */
    protected int getLastInvokerSelectionStart() {
        return lastInvokerSelectionStart;
    }

    /**
     * �R���|�[�l���g�����������܂��B
     */
    protected void initComponent() {
        cut = new JMenuItem("�؂���(T)", 'T');
        copy = new JMenuItem("�R�s�[(C)", 'C');
        paste = new JMenuItem("�\��t��(P)", 'P');
        delete = new JMenuItem("�폜(D)", 'D');
        cut.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_CUT_16));
        copy.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_COPY_16));
        paste.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_PASTE_16));
        delete.setIcon(ACResourceIconPooler.getInstance().getImage(
                ACConstants.ICON_PATH_CANCEL_16));

        cut.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getLastInvoker() != null) {
                    getLastInvoker().cut();
                }
            }
        });
        copy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getLastInvoker() != null) {
                    getLastInvoker().copy();
                }
            }
        });
        paste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getLastInvoker() != null) {
                    getLastInvoker().paste();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getLastInvoker() != null) {
                    int start = getLastInvoker().getSelectionStart();
                    int end = getLastInvoker().getSelectionEnd();
                    if ((start < end) && (start >= 0)) {
                        StringBuffer sb = new StringBuffer(getLastInvoker()
                                .getText());
                        if (sb.length() >= end) {
                            sb.replace(start, end, "");
                            getLastInvoker().setText(sb.toString());
                        }
                    }
                }
            }
        });
        add(cut);
        add(copy);
        add(paste);
        add(delete);
    }

    /**
     * �|�b�v�A�b�v���j���[�̏��L�� ��ݒ肵�܂��B
     * 
     * @param owner �|�b�v�A�b�v���j���[�̏��L��
     */
    protected void setLastInvoker(JTextComponent owner) {
        this.lastInvoker = owner;
    }

    /**
     * �|�b�v�A�b�v���L�҂̑I���I���ʒu ��ݒ肵�܂��B
     * 
     * @param lastInvokerSelectionEnd �|�b�v�A�b�v���L�҂̑I���I���ʒu
     */
    protected void setLastInvokerSelectionEnd(int lastInvokerSelectionEnd) {
        this.lastInvokerSelectionEnd = lastInvokerSelectionEnd;
    }

    /**
     * �|�b�v�A�b�v���L�҂̑I���J�n�ʒu ��ݒ肵�܂��B
     * 
     * @param lastInvokerSelectionStart �|�b�v�A�b�v���L�҂̑I���J�n�ʒu
     */
    protected void setLastInvokerSelectionStart(int lastInvokerSelectionStart) {
        this.lastInvokerSelectionStart = lastInvokerSelectionStart;
    }
}
