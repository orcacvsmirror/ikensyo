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
 * 内包するコントロールにフォーカスがある場合に囲み枠を表示するキャプション付きコンテナクラスです。
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
     * コンストラクタ
     */
    public VRLabelContainer() {
        this(null);
    }

    /**
     * コンストラクタ
     * 
     * @param text キャプションテキスト
     */
    public VRLabelContainer(String text) {
        super();
        initComponent();
        setText(text);
    }

    /**
     * 文字カラム数を返します。
     * 
     * @return columns 文字カラム数
     */
    public int getColumns() {
        return columns;
    }

    /**
     * @return focusBackground を返します。
     */
    public Color getFocusBackground() {
        return focusBackground;
    }

    /**
     * フォーカス時文字色を返します。
     * 
     * @return フォーカス時背景色
     */
    public Color getFocusForeground() {
        return focusForeground;
    }

    /**
     * ラベルのアライメントを返します。
     * 
     * @return alignment を戻します。
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public Insets getInsets() {

        return new Insets(1, getLabelWidth(), 1, labelMargin);
    }

    /**
     * @return labelColumns を戻します。
     */
    public int getLabelColumns() {
        return labelColumns;
    }

    /**
     * テキスト描画に用いるレンダララベル を返します。
     * 
     * @return レンダララベル
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
     * 行数を返します。
     * 
     * @return rows 行数
     */
    public int getRows() {
        return rows;
    }

    /**
     * キャプションのテキストを返します。
     * 
     * @return text キャプションのテキスト
     */
    public String getText() {
        return text;
    }

    /**
     * 子コンポーネントがフォーカスを持つかを返します。
     * 
     * @return childFocusedOwner 子コンポーネントがフォーカスを持つか
     */
    public boolean isChildFocusedOwner() {
        return childFocusedOwner;
    }

    /**
     * コンテンツ領域を塗りつぶすかを返します。
     * 
     * @return コンテンツ領域を塗りつぶすか
     */
    public boolean isContentAreaFilled() {
        return contentAreaFilled;
    }

    /**
     * ラベル領域を塗りつぶすかを返します。
     * 
     * @return ラベル領域を塗りつぶすかを返します。
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

            //フォーカスがある場合
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
                //描画用ラベルに転写
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
     * 子コンポーネントがフォーカスを持つかを設定します。
     * 
     * @param childFocusedOwner 子コンポーネントがフォーカスを持つか
     */
    public void setChildFocusedOwner(boolean childFocusedOwner) {
        this.childFocusedOwner = childFocusedOwner;
        repaint();
    }

    /**
     * 文字カラム数を設定。
     * 
     * @param columns 文字カラム数
     */
    public void setColumns(int columns) {
        this.columns = columns;
        invalidate();
    }

    /**
     * コンテンツ領域を塗りつぶすかを返します。
     * 
     * @param contentAreaFilled コンテンツ領域を塗りつぶすか
     */
    public void setContentAreaFilled(boolean contentAreaFilled) {
        this.contentAreaFilled = contentAreaFilled;
        repaint();
    }

    /**
     * フォーカス時背景色を設定します。
     * 
     * @param focusBackground フォーカス時背景色
     */
    public void setFocusBackground(Color focusBackground) {
        this.focusBackground = focusBackground;
    }

    /**
     * フォーカス時文字色を設定します。
     * 
     * @param focusForeground フォーカス時文字色
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
     * ラベルの水平方向の文字揃えを設定します。
     * 
     * @param horizontalAlignment ラベルの水平方向の文字揃え を設定。
     */
    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * ラベルの幅を文字数で設定します。
     * 
     * @param labelColumns ラベルの幅
     */
    public void setLabelColumns(int labelColumns) {
        this.labelColumns = labelColumns;
    }

    /**
     * ラベル領域を塗りつぶすかを設定します。
     * 
     * @param labelFilled ラベル領域を塗りつぶすか
     */
    public void setLabelFilled(boolean labelFilled) {
        this.labelFilled = labelFilled;
        repaint();
    }

    /**
     * テキスト描画に用いるレンダララベル を設定します。
     * 
     * @param labelRenderer レンダララベル
     */
    public void setLabelRenderer(JLabel labelRenderer) {
        this.textRenderer = labelRenderer;
        if (getLabelRenderer() != null) {
            getLabelRenderer().setText(getText());
        }
    }

    /**
     * 行数を設定します。
     * 
     * @param rows 行数
     */
    public void setRows(int rows) {
        this.rows = rows;
        invalidate();
    }

    /**
     * キャプションのテキストを設定します。
     * 
     * @param text キャプションのテキスト
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
     * 子コンポーネントに指定したリスナーを追加・削除します。
     * 
     * @param c 設定開始コンポーネント
     * @param focuslis 指定フォーカスリスナー
     * @param containerlis 指定コンテナリスナー
     * @param add 追加するかどうか
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
     * テキスト描画に用いるレンダララベルを生成します。
     * 
     * @return レンダララベル
     */
    protected JLabel createTextRenderer() {
        return new JLabel();
    }

    /**
     * カラム幅を計算して返します。
     * 
     * @return カラム幅
     */
    protected int getColumnWidth() {
        if (cachedColumnWidth == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            cachedColumnWidth = fm.charWidth('m');
        }
        return cachedColumnWidth;
    }

    /**
     * 調査コンテナ以下でフォーカスを持つコントロールを返します。
     * 
     * @param c 調査コンテナ
     * @return フォーカスを持つコントロールを返します。
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
     * ラベル幅を返します。
     * 
     * @return ラベル幅
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
     * 行の高さを計算して返します。
     * 
     * @return カラム幅
     */
    protected int getRowHeight() {
        if (cachedRowHeight == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            cachedRowHeight = fm.getHeight();
        }
        return cachedRowHeight;
    }

    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
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
     * 調査コンテナ以下の最初のコンポーネントにフォーカスを設定します
     * 
     * @param c 調査コンテナ
     * @return フォーカスを設定できたかどうかを返します。
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
     * <code>VRLabelContainer</code> 用のコンテナリスナです。
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
         * コンストラクタ
         * 
         * @param adaptee 対象コントロール
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
     * <code>VRLabelContainer</code> 用のフォーカスリスナです。
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
         * コンストラクタ
         * 
         * @param adaptee 対象コントロール
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
     * <code>VRLabelContainer</code> 用のマウスリスナです。
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
         * コンストラクタ
         * 
         * @param adaptee 対象コントロール
         */
        protected LabelContainerMouseAdapter(VRLabelContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void mousePressed(MouseEvent e) {
            //既にフォーカスを持つかを調査
            Component cmp = adaptee.getFocusedChild(adaptee);
            if (cmp == null) {
                //フォーカスを持たない場合、フォーカス取得
                adaptee.transferFocus();
                //                adaptee.setMainFocus(adaptee);
            }
        }
    }

}