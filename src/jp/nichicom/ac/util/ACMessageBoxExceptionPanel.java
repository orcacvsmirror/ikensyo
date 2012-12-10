package jp.nichicom.ac.util;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRootPane;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * ��O�������ɗ��p�\�Ȋg�����b�Z�[�W�̈�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACMessageBoxExceptionPanel extends ACPanel {
    private ACPanel buttons;
    private ACGroupBox details;
    private ACLabel environments;
    private ACButton expandsDetail;
    private ACTextArea infomation;
    private Dimension minimumSize;
    private ACButton copyDetail;
    private ACPanel buttonArea;

    /**
     * Creates a new <code>JPanel</code> with a double buffer and a flow
     * layout.
     */
    public ACMessageBoxExceptionPanel() {
        super();
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code> and
     * the specified buffering strategy. If <code>isDoubleBuffered</code> is
     * true, the <code>JPanel</code> will use a double buffer.
     * 
     * @param isDoubleBuffered a boolean, true for double-buffering, which uses
     *            additional memory space to achieve fast, flicker-free updates
     */
    public ACMessageBoxExceptionPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     * 
     * @param layout the LayoutManager to use
     */
    public ACMessageBoxExceptionPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     * 
     * @param layout the LayoutManager to use
     * @param isDoubleBuffered a boolean, true for double-buffering, which uses
     *            additional memory space to achieve fast, flicker-free updates
     */
    public ACMessageBoxExceptionPanel(LayoutManager layout,
            boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    /**
     * �����𒲍����܂��B
     */
    public void checkEnvironment() {
        environments.setText(getEnvironment());
    }

    /**
     * �\���������ݒ�B
     * 
     * @param info �\��������
     */
    public void setInfomation(String info) {
        infomation.setText(info);
    }

    /**
     * �������擾���܂��B
     * 
     * @return �����
     */
    protected String getEnvironment() {
        StringBuffer sb = new StringBuffer();
        sb.append("OS: ");
        sb.append(System.getProperty("os.name", "unknown"));
        String patch = System.getProperty("sun.os.patch.level", "");
        if("unknown".equals(patch)){
            //�p�b�`��unknown�Ȃ牽���\�����Ȃ�
            patch = "";
        }
        sb.append(" " + patch);
        sb.append("(");
        sb.append(System.getProperty("os.version", "-"));
        sb.append("), ");
        sb.append("VM: ");
        sb.append(System.getProperty("java.vendor", "unknown"));
        sb.append("(");
        sb.append(System.getProperty("java.version", "-"));
        sb.append(")");
        return sb.toString();
    }

    protected void initComponent() {
        super.initComponent();
        buttons = new ACPanel();
        details = new ACGroupBox("�T�|�[�g���");
        infomation = new ACTextArea();
        environments = new ACLabel();
        expandsDetail = new ACButton("�ڍ�(D)");
        copyDetail = new ACButton("�R�s�[(C)");
        buttonArea = new ACPanel();

        expandsDetail.setMnemonic('D');
        infomation.setVisible(false);
        infomation.setEditable(false);
        
        copyDetail.setMnemonic('C');

        buttonArea.setAlignment(VRLayout.RIGHT);
        buttonArea.add(expandsDetail, VRLayout.FLOW);
        buttonArea.add(copyDetail, VRLayout.FLOW);
        buttons.add(buttonArea, VRLayout.EAST);
        buttons.add(environments, VRLayout.CLIENT);
        details.add(buttons, VRLayout.NORTH);
        details.add(infomation, VRLayout.CLIENT);
        this.add(details, VRLayout.CLIENT);
        
        
        expandsDetail.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                swapDetailVisible();    
            }
             
        });
        copyDetail.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                copyDetail();    
            }             
        });
    }

    protected void copyDetail(){
        Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
        if(cb!=null){
            StringSelection ss=new StringSelection(environments.getText() + ACConstants.LINE_SEPARATOR + infomation.getText());
            cb.setContents(ss, ss);
            ACMessageBox.show("�N���b�v�{�[�h�ɃT�|�[�g�����R�s�[���܂����B");
        }else{
            ACMessageBox.show("�N���b�v�{�[�h�ւ̃A�N�Z�X�Ɏ��s���܂����B");
        }
    }
    
    /**
     * �ڍׂ̕\��Ԃ𔽓]���A�e��ʂ̕\���ʒu��␳���܂��B
     */
    protected void swapDetailVisible() {
        JRootPane pnl = this.getRootPane();
        if ((pnl != null) && (pnl.getParent() != null)) {
            if (minimumSize == null) {
                minimumSize = pnl.getParent().getSize();
            }
            // �ύX�O�ƌ�̃T�C�Y���擾
            Dimension oldD = this.getPreferredSize();
            infomation.setVisible(!infomation.isVisible());
            Dimension newD = this.getPreferredSize();

            // ��ʗ̈���擾
            Point corner = new Point(0, 0);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            GraphicsEnvironment genv = GraphicsEnvironment
                    .getLocalGraphicsEnvironment();
            if (genv != null) {
                Rectangle screenRect = genv.getMaximumWindowBounds();
                if (screenRect != null) {
                    corner = screenRect.getLocation();
                    screenSize = screenRect.getSize();
                }
            }
            // �v�������T�C�Y���v�Z
            int newH = (int) Math.max(minimumSize.getHeight(), pnl.getParent()
                    .getHeight()
                    + newD.getHeight() - oldD.getHeight());
            Point pos = pnl.getParent().getLocationOnScreen();
            int newY = (int) pos.getY();

            if (newH > screenSize.getHeight()) {
                // �v�����鍂�������E�̏ꍇ
                newY = (int) corner.getY();
                newH = (int) screenSize.getHeight() - 20;
            } else {
                if (newY + newH > corner.getY() + screenSize.getHeight()) {
                    // ���[�𒴂���ꍇ�͏�ւ��炷
                    newY = (int) (screenSize.getHeight() - newH);
                } else if (newY < corner.getY()) {
                    // ��[�ɂ߂荞��ł��ꍇ�͉��ւ��炷
                    newY = (int) corner.getY() + 20;
                }
            }
            pnl.getParent().setBounds((int) pos.getX(), newY,
                    pnl.getParent().getWidth(), newH);
            // �ĕ`��
            pnl.getParent().validate();
        } else {
            infomation.setVisible(!infomation.isVisible());
        }

    }

}
