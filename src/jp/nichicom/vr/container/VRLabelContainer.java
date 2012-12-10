/** TODO <HEAD> */
package jp.nichicom.vr.container;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * �����R���g���[���Ƀt�H�[�J�X������ꍇ�Ɉ͂ݘg��\������L���v�V�����t���R���e�i�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see JPanel
 */
public class VRLabelContainer extends JPanel implements
        javax.swing.SwingConstants {

    private int cachedColumnWidth = 0;

    private int cachedRowHeight = 0;

    private boolean childFocusedOwner = false;

    private int columns = 0;

    private boolean contentAreaFilled = false;

    private Color focusBackground;

    private Color focusForeground;

    private int horizontalAlignment = LEFT;

    private int labelColumns = 0;

    private boolean labelFilled = false;

    private int labelMargin = 8;

    private Dimension preferredSize;

    private int rows = 1;

    private String text;

    private JLabel textRenderer;

    protected ContainerListener containerListener;

    protected FocusListener focusListener;

    /**
     * �R���X�g���N�^
     */
    public VRLabelContainer() {
        this(null);
    }

    /**
     * �R���X�g���N�^
     * 
     * @param text �L���v�V�����e�L�X�g
     */
    public VRLabelContainer(String text) {
        super();
        initComponent();
        setText(text);
    }

    /**
     * �����J��������Ԃ��܂��B
     * 
     * @return columns �����J������
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return focusBackground ��Ԃ��܂��B
     */
    public Color getFocusBackground() {
        return focusBackground;
    }

    /**
     * �t�H�[�J�X�������F��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X���w�i�F
     */
    public Color getFocusForeground() {
        return focusForeground;
    }

    /**
     * ���x���̃A���C�����g��Ԃ��܂��B
     * 
     * @return alignment ��߂��܂��B
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public Insets getInsets() {

        return new Insets(1, getLabelWidth(), 1, labelMargin);
    }

    /**
     * @return labelColumns ��߂��܂��B
     */
    public int getLabelColumns() {
        return labelColumns;
    }

    /**
     * �e�L�X�g�`��ɗp���郌���_�����x�� ��Ԃ��܂��B
     * 
     * @return �����_�����x��
     */
    public JLabel getLabelRenderer() {
        return textRenderer;
    }

    public Dimension getPreferredSize() {

        if (preferredSize != null) {
            return preferredSize;
        }

        Insets is = getInsets();

        Dimension dm;

        if (getLayout() != null) {
            dm = getLayout().preferredLayoutSize(this);
        } else {
            dm = new Dimension(0, 0);
        }

        if (columns > 0) {
            dm.width = is.left + is.right + columns * getColumnWidth();
        }

        int r = rows;

        r = r * getRowHeight();
        if (getLabelRenderer() != null) {
            Dimension d = getLabelRenderer().getPreferredSize();
            if (d != null) {
                r = (int) Math.max(d.height, r);
            }
        }

        dm.height = (int) Math.max(dm.height, is.top + is.bottom + r);

        return dm;

    }

    /**
     * �s����Ԃ��܂��B
     * 
     * @return rows �s��
     */
    public int getRows() {
        return rows;
    }

    /**
     * �L���v�V�����̃e�L�X�g��Ԃ��܂��B
     * 
     * @return text �L���v�V�����̃e�L�X�g
     */
    public String getText() {
        return text;
    }

    /**
     * �q�R���|�[�l���g���t�H�[�J�X��������Ԃ��܂��B
     * 
     * @return childFocusedOwner �q�R���|�[�l���g���t�H�[�J�X������
     */
    public boolean isChildFocusedOwner() {
        return childFocusedOwner;
    }

    /**
     * �R���e���c�̈��h��Ԃ�����Ԃ��܂��B
     * 
     * @return �R���e���c�̈��h��Ԃ���
     */
    public boolean isContentAreaFilled() {
        return contentAreaFilled;
    }

    /**
     * ���x���̈��h��Ԃ�����Ԃ��܂��B
     * 
     * @return ���x���̈��h��Ԃ�����Ԃ��܂��B
     */
    public boolean isLabelFilled() {
        return labelFilled;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getLabelWidth() > 0) {
            int h = getHeight();
            int w = getWidth();
            Insets is = getInsets();

            Color fc = getForeground();

            //�t�H�[�J�X������ꍇ
            if (isChildFocusedOwner() || isLabelFilled()) {
                Graphics2D g2 = (Graphics2D) g;

                Color bc = getBackground();
                if (isChildFocusedOwner()) {
                    bc = getFocusBackground();
                    fc = getFocusForeground();

                }

                g2.setColor(bc);

                if (isOpaque()) {
                    g2.fill(new Rectangle(0, 0, w, h));
                } else {

                    Shape sp = new RoundRectangle2D.Double(0, 0, w, h,
                            labelMargin * 2, labelMargin * 2);
                    GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
                    gp.append(sp, true);

                    if (!contentAreaFilled) {
                        Shape sp2 = new RoundRectangle2D.Double(is.left,
                                is.top, w - is.left - is.right, h - is.top
                                        - is.bottom, labelMargin, labelMargin);
                        gp.append(sp2, true);
                    }

                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.fill(gp);
                    //                  g2.fill(new RoundRectangle2D.Double(labelMargin/2,
                    // is.top, is.left-labelMargin,
                    // getRowHeight()*lines.length+is.top+is.bottom, labelMargin
                    // , labelMargin ));
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_OFF);
                }

            }

            if (textRenderer != null) {
                //            if (lines.length > 0) {
                //�`��p���x���ɓ]��
                textRenderer.setFont(getFont());
                textRenderer.setHorizontalAlignment(getHorizontalAlignment());
                textRenderer.setForeground(fc);
                textRenderer.setEnabled(isEnabled());
                int drawX = 0, drawY = 0;

                int lw = is.left - labelMargin * 2;
                int keepW = Math.min(lw, w - labelMargin * 2);
                FontMetrics fm = getFontMetrics(getFont());

                textRenderer.setBounds(0, 0, keepW, fm.getHeight());
                g.translate(labelMargin, drawY);
                textRenderer.paint(g);
                g.translate(-labelMargin, -drawY);
            }

        }
    }

    /**
     * �q�R���|�[�l���g���t�H�[�J�X��������ݒ肵�܂��B
     * 
     * @param childFocusedOwner �q�R���|�[�l���g���t�H�[�J�X������
     */
    public void setChildFocusedOwner(boolean childFocusedOwner) {
        this.childFocusedOwner = childFocusedOwner;
        repaint();
    }

    /**
     * �����J��������ݒ�B
     * 
     * @param columns �����J������
     */
    public void setColumns(int columns) {
        this.columns = columns;
        invalidate();
    }

    /**
     * �R���e���c�̈��h��Ԃ�����Ԃ��܂��B
     * 
     * @param contentAreaFilled �R���e���c�̈��h��Ԃ���
     */
    public void setContentAreaFilled(boolean contentAreaFilled) {
        this.contentAreaFilled = contentAreaFilled;
        repaint();
    }

    /**
     * �t�H�[�J�X���w�i�F��ݒ肵�܂��B
     * 
     * @param focusBackground �t�H�[�J�X���w�i�F
     */
    public void setFocusBackground(Color focusBackground) {
        this.focusBackground = focusBackground;
    }

    /**
     * �t�H�[�J�X�������F��ݒ肵�܂��B
     * 
     * @param focusForeground �t�H�[�J�X�������F
     */
    public void setFocusForeground(Color focusForeground) {
        this.focusForeground = focusForeground;
    }

    public void setFont(Font f) {
        super.setFont(f);
        cachedColumnWidth = 0;
        cachedRowHeight = 0;
    }

    /**
     * ���x���̐��������̕���������ݒ肵�܂��B
     * 
     * @param horizontalAlignment ���x���̐��������̕������� ��ݒ�B
     */
    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * ���x���̕��𕶎����Őݒ肵�܂��B
     * 
     * @param labelColumns ���x���̕�
     */
    public void setLabelColumns(int labelColumns) {
        this.labelColumns = labelColumns;
    }

    /**
     * ���x���̈��h��Ԃ�����ݒ肵�܂��B
     * 
     * @param labelFilled ���x���̈��h��Ԃ���
     */
    public void setLabelFilled(boolean labelFilled) {
        this.labelFilled = labelFilled;
        repaint();
    }

    /**
     * �e�L�X�g�`��ɗp���郌���_�����x�� ��ݒ肵�܂��B
     * 
     * @param labelRenderer �����_�����x��
     */
    public void setLabelRenderer(JLabel labelRenderer) {
        this.textRenderer = labelRenderer;
        if (getLabelRenderer() != null) {
            getLabelRenderer().setText(getText());
        }
    }

    /**
     * �s����ݒ肵�܂��B
     * 
     * @param rows �s��
     */
    public void setRows(int rows) {
        this.rows = rows;
        invalidate();
    }

    /**
     * �L���v�V�����̃e�L�X�g��ݒ肵�܂��B
     * 
     * @param text �L���v�V�����̃e�L�X�g
     */
    public void setText(String text) {
        this.text = text;
        if (getLabelRenderer() != null) {
            getLabelRenderer().setText(text);
        }
        revalidate();
        repaint();

    }

    /**
     * �q�R���|�[�l���g�Ɏw�肵�����X�i�[��ǉ��E�폜���܂��B
     * 
     * @param c �ݒ�J�n�R���|�[�l���g
     * @param focuslis �w��t�H�[�J�X���X�i�[
     * @param containerlis �w��R���e�i���X�i�[
     * @param add �ǉ����邩�ǂ���
     */
    protected void childListenerControl(Component c, FocusListener focuslis,
            ContainerListener containerlis, boolean add) {

        if (add) {
            c.addFocusListener(focuslis);
        } else {
            c.removeFocusListener(focuslis);
        }
        if (c instanceof Container) {

            if (add) {
                ((Container) c).addContainerListener(containerlis);
            } else {
                ((Container) c).removeContainerListener(containerlis);
            }

            Container cont = (Container) c;
            for (int i = 0; i < cont.getComponentCount(); i++) {
                childListenerControl(cont.getComponent(i), focuslis,
                        containerlis, add);
            }
        }
    }

    /**
     * �e�L�X�g�`��ɗp���郌���_�����x���𐶐����܂��B
     * 
     * @return �����_�����x��
     */
    protected JLabel createTextRenderer() {
        return new JLabel();
    }

    /**
     * �J���������v�Z���ĕԂ��܂��B
     * 
     * @return �J������
     */
    protected int getColumnWidth() {
        if (cachedColumnWidth == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            cachedColumnWidth = fm.charWidth('m');
        }
        return cachedColumnWidth;
    }

    /**
     * �����R���e�i�ȉ��Ńt�H�[�J�X�����R���g���[����Ԃ��܂��B
     * 
     * @param c �����R���e�i
     * @return �t�H�[�J�X�����R���g���[����Ԃ��܂��B
     */
    protected Component getFocusedChild(Container c) {
        for (int i = 0; i < c.getComponentCount(); i++) {
            Component cmp = c.getComponent(i);
            if (cmp.isEnabled() && cmp.isVisible()) {
                if (cmp.hasFocus()) {
                    return cmp;
                } else {
                    if (cmp instanceof Container) {
                        Component ret = getFocusedChild((Container) cmp);
                        if (ret != null) {
                            return ret;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * ���x������Ԃ��܂��B
     * 
     * @return ���x����
     */
    protected int getLabelWidth() {
        int lw = labelMargin;
        if (labelColumns != 0) {
            lw += labelColumns * getColumnWidth() + labelMargin;
        }

        if (getLabelRenderer() != null) {
            Dimension d = getLabelRenderer().getPreferredSize();
            if (d != null) {
                lw = (int) Math.max(d.width + labelMargin * 2, lw);
            }
        }

        return lw;
    }

    /**
     * �s�̍������v�Z���ĕԂ��܂��B
     * 
     * @return �J������
     */
    protected int getRowHeight() {
        if (cachedRowHeight == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            cachedRowHeight = fm.getHeight();
        }
        return cachedRowHeight;
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setOpaque(false);

        focusBackground = UIManager.getColor("activeCaptionBorder");
        focusForeground = UIManager.getColor("activeCaptionText");

        containerListener = new LabelContainerContainerAdapter(this);
        focusListener = new LabelContainerFocusAdapter(this);

        this.addMouseListener(new LabelContainerMouseAdapter(this));
        this.addContainerListener(containerListener);

        setLabelRenderer(createTextRenderer());
    }

    /**
     * �����R���e�i�ȉ��̍ŏ��̃R���|�[�l���g�Ƀt�H�[�J�X��ݒ肵�܂�
     * 
     * @param c �����R���e�i
     * @return �t�H�[�J�X��ݒ�ł������ǂ�����Ԃ��܂��B
     */
    protected boolean setMainFocus(Container c) {
        for (int i = 0; i < c.getComponentCount(); i++) {
            Component cmp = c.getComponent(i);
            if (cmp.isEnabled() && cmp.isVisible()) {
                if (cmp.isFocusable()) {
                    cmp.requestFocus();
                    return true;
                } else {
                    if (cmp instanceof Container) {
                        if (setMainFocus((Container) cmp)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * <code>VRLabelContainer</code> �p�̃R���e�i���X�i�ł��B
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Susumu Nakahara
     * @version 1.0 2005/10/31
     * @see ContainerListener
     */
    protected class LabelContainerContainerAdapter implements ContainerListener {
        VRLabelContainer adaptee;

        /**
         * �R���X�g���N�^
         * 
         * @param adaptee �ΏۃR���g���[��
         */
        protected LabelContainerContainerAdapter(VRLabelContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void componentAdded(ContainerEvent e) {

            adaptee.childListenerControl(e.getChild(), adaptee.focusListener,
                    adaptee.containerListener, true);
        }

        public void componentRemoved(ContainerEvent e) {
            adaptee.childListenerControl(e.getChild(), adaptee.focusListener,
                    adaptee.containerListener, false);

        }
    }

    /**
     * <code>VRLabelContainer</code> �p�̃t�H�[�J�X���X�i�ł��B
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Susumu Nakahara
     * @version 1.0 2005/10/31
     * @see FocusListener
     */
    protected class LabelContainerFocusAdapter implements FocusListener {
        VRLabelContainer adaptee;

        /**
         * �R���X�g���N�^
         * 
         * @param adaptee �ΏۃR���g���[��
         */
        protected LabelContainerFocusAdapter(VRLabelContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void focusGained(FocusEvent e) {
            setChildFocusedOwner(true);
        }

        public void focusLost(FocusEvent e) {
            //            Component cmp = getFocusedChild(adaptee);
            //            setChildFocusedOwner((cmp != null));
            setChildFocusedOwner(false);
        }
    }

    /**
     * <code>VRLabelContainer</code> �p�̃}�E�X���X�i�ł��B
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Susumu Nakahara
     * @version 1.0 2005/10/31
     * @see MouseAdapter
     */
    protected class LabelContainerMouseAdapter extends
            java.awt.event.MouseAdapter {
        VRLabelContainer adaptee;

        /**
         * �R���X�g���N�^
         * 
         * @param adaptee �ΏۃR���g���[��
         */
        protected LabelContainerMouseAdapter(VRLabelContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void mousePressed(MouseEvent e) {
            //���Ƀt�H�[�J�X�������𒲍�
            Component cmp = adaptee.getFocusedChild(adaptee);
            if (cmp == null) {
                //�t�H�[�J�X�������Ȃ��ꍇ�A�t�H�[�J�X�擾
                adaptee.transferFocus();
                //                adaptee.setMainFocus(adaptee);
            }
        }
    }

}