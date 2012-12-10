package jp.nichicom.ac.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.text.View;

import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.component.VRLabel;

/**
 * 自動折り返しに対応したラベルです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRLabel
 */
public class ACLabel extends VRLabel {

    private String[] lines = new String[0];

    private int columns = 0;

    private int rows = 1;

    private int columnWidth = 0;

    private int rowHeight = 0;

    private Dimension preferredSize;

    private JLabel textRenderer;

    private boolean autoWrap = false;

    private boolean foregroundIcon = false;
    private String iconType = "";
    
    private Insets margin;

    /**
     * 描画余白 を返します。
     * @return 描画余白
     */
    public Insets getMargin() {
        return margin;
    }

    /**
     * 描画余白 を設定します。
     * @param margin 描画余白
     */
    public void setMargin(Insets margin) {
        this.margin = margin;
    }

    /**
     * Creates a <code>JLabel</code> instance with no image and with an empty
     * string for the title. The label is centered vertically in its display
     * area. The label's contents, once set, will be displayed on the leading
     * edge of the label's display area.
     */
    public ACLabel() {
        super();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image. The
     * label is centered vertically and horizontally in its display area.
     * 
     * @param image The image to be displayed by the label.
     */
    public ACLabel(Icon image) {
        super(image);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text. The
     * label is aligned against the leading edge of its display area, and
     * centered vertically.
     * 
     * @param text The text to be displayed by the label.
     */
    public ACLabel(String text) {
        super(text);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text, image,
     * and horizontal alignment. The label is centered vertically in its display
     * area. The text is on the trailing edge of the image.
     * 
     * @param text The text to be displayed by the label.
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACLabel(String text, Icon image, int horizontalAlignment) {
        super(text, image, horizontalAlignment);
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param text The text to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public ACLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
    }

    public void setText(String text) {

        super.setText(text);
        separateText();
    }

    /**
     * 設定されたテキストを改行単位に分割します。
     */
    protected void separateText() {
        String text = getText();
        // 改行単位に削っておく
        if (text != null) {
            this.lines = text.split("("+(System.getProperty("line.separator"))+")|\r|\n");
        } else {
            this.lines = new String[0];
        }
    }

    public void bindSource() throws ParseException {
        super.bindSource();
        separateText();
    }

    /**
     * テキスト描画に用いるレンダララベルを生成します。
     * 
     * @return レンダララベル
     */
    protected JLabel createTextRenderer() {
        return new JLabel();
    }

    protected void initComponent() {
        super.initComponent();
        setTextRenderer(createTextRenderer());
    }

    /**
     * テキスト描画に用いるレンダララベル を返します。
     * 
     * @return レンダララベル
     */
    public JLabel getTextRenderer() {
        return textRenderer;
    }

    /**
     * テキスト描画に用いるレンダララベル を設定します。
     * 
     * @param textRenderer レンダララベル
     */
    public void setTextRenderer(JLabel textRenderer) {
        this.textRenderer = textRenderer;
    }

    /**
     * カラム幅を計算して返します。
     * 
     * @return カラム幅
     */
    protected int getColumnWidth() {
        if (columnWidth == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            columnWidth = fm.charWidth('m');
            // 日本語文字対応のため、1.1倍する
            columnWidth = (int) (columnWidth * 1.1);
        }
        return columnWidth;
    }

    /**
     * 行の高さを計算して返します。
     * 
     * @return 行の高さ
     */
    protected int getRowHeight() {
        if (rowHeight == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            rowHeight = fm.getHeight();
        }
        return rowHeight;
    }

    /**
     * 改行による行間を計算して返します。
     * 
     * @param fm フォントメトリクス
     * @return 行間
     */
    protected int getNewLineHeightStep(FontMetrics fm) {
        return fm.getHeight() + fm.getDescent() / 2;
    }

    /**
     * 文字カラム数を返します。
     * 
     * @return 文字カラム数
     */
    public int getColumns() {
        return columns;
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
     * 行数を返します。
     * 
     * @return 行数
     */
    public int getRows() {
        return rows;
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
     * 複数行文字列の描画可能範囲を計算し、描画用に加工した文字列を返します。
     * 
     * @param label 描画部品
     * @param srcLines 描画文字列
     * @param viewR 可視領域
     * @param iconR アイコン領域
     * @param textR 文字列領域
     * @param autoWrap 自動折り返しを行なうか
     * @return 描画用文字列
     */
    public String[] calculateText(JLabel label, String[] srcLines,
            Rectangle viewR, Rectangle iconR, Rectangle textR, boolean autoWrap) {
        return calculateText(label, srcLines, viewR, iconR, textR, autoWrap, 0,
                0);
    }

    /**
     * 複数行文字列の描画可能範囲を計算し、描画用に加工した文字列を返します。
     * 
     * @param label 描画部品
     * @param srcLines 描画文字列
     * @param viewR 可視領域
     * @param iconR アイコン領域
     * @param textR 文字列領域
     * @param autoWrap 自動折り返しを行なうか
     * @param minimumWidth 最低保障幅
     * @param minimumHeight 最低保障高
     * @return 描画用文字列
     */
    public String[] calculateText(JLabel label, String[] srcLines,
            Rectangle viewR, Rectangle iconR, Rectangle textR,
            boolean autoWrap, int minimumWidth, int minimumHeight) {
        return calculateText(label, label.getFontMetrics(label.getFont()),
                srcLines, label.isEnabled() ? label.getIcon() : label
                        .getDisabledIcon(), label.getVerticalAlignment(), label
                        .getHorizontalAlignment(), label
                        .getVerticalTextPosition(), label
                        .getHorizontalTextPosition(), viewR, iconR, textR,
                label.getIconTextGap(), autoWrap, minimumWidth, minimumHeight);
    }

    /**
     * 複数行文字列の描画可能範囲を計算し、描画用に加工した文字列を返します。
     * 
     * @param label 描画部品
     * @param fontMetrics メトリクス
     * @param srcLines 描画文字列
     * @param icon アイコン
     * @param viewR 可視領域
     * @param iconR アイコン領域
     * @param textR 文字列領域
     * @param autoWrap 自動折り返しを行なうか
     * @return 描画用文字列
     */
    public String[] calculateText(JLabel label, FontMetrics fontMetrics,
            String[] srcLines, Icon icon, Rectangle viewR, Rectangle iconR,
            Rectangle textR, boolean autoWrap) {
        return calculateText(label, fontMetrics, srcLines, icon, viewR, iconR,
                textR, autoWrap, 0, 0);
    }

    /**
     * 複数行文字列の描画可能範囲を計算し、描画用に加工した文字列を返します。
     * 
     * @param label 描画部品
     * @param fontMetrics メトリクス
     * @param srcLines 描画文字列
     * @param icon アイコン
     * @param viewR 可視領域
     * @param iconR アイコン領域
     * @param textR 文字列領域
     * @param autoWrap 自動折り返しを行なうか
     * @param minimumWidth 最低保障幅
     * @param minimumHeight 最低保障高
     * @return 描画用文字列
     */
    public String[] calculateText(JLabel label, FontMetrics fontMetrics,
            String[] srcLines, Icon icon, Rectangle viewR, Rectangle iconR,
            Rectangle textR, boolean autoWrap, int minimumWidth,
            int minimumHeight) {
        return calculateText(label, fontMetrics, srcLines, icon, label
                .getVerticalAlignment(), label.getHorizontalAlignment(), label
                .getVerticalTextPosition(), label.getHorizontalTextPosition(),
                viewR, iconR, textR, label.getIconTextGap(), autoWrap,
                minimumWidth, minimumHeight);
    }

    /**
     * 複数行文字列の描画可能範囲を計算し、描画用に加工した文字列を返します。
     * 
     * @param c 描画部品
     * @param fm メトリクス
     * @param srcLines 描画文字列
     * @param icon アイコン
     * @param verticalAlignment 垂直位置
     * @param horizontalAlignment 水平位置
     * @param verticalTextPosition 垂直文字揃え
     * @param horizontalTextPosition 水平文字揃え
     * @param viewR 可視領域
     * @param iconR アイコン領域
     * @param textR 文字列領域
     * @param textIconGap アイコンと文字列の間隔
     * @param autoWrap 自動折り返しを行なうか
     * @param minimumWidth 最低保障幅
     * @param minimumHeight 最低保障高
     * @return 描画用文字列
     */
    public String[] calculateText(JComponent c, FontMetrics fm,
            String[] srcLines, Icon icon, int verticalAlignment,
            int horizontalAlignment, int verticalTextPosition,
            int horizontalTextPosition, Rectangle viewR, Rectangle iconR,
            Rectangle textR, int textIconGap, boolean autoWrap,
            int minimumWidth, int minimumHeight) {

        // 元の行数
        int linesSize = srcLines.length;

        // アイコンとの間隔
        int gap;

        // アイコンサイズの転記
        if (icon != null) {
            iconR.width = icon.getIconWidth();
            iconR.height = icon.getIconHeight();
            gap = textIconGap;
        } else {
            iconR.width = iconR.height = 0;
            gap = 0;
        }

        /*
         * Initialize the text bounds rectangle textR. If a null or and empty
         * String was specified we substitute "" here and use 0,0,0,0 for textR.
         */
        boolean textIsEmpty = linesSize <= 0;
        for (int i = 0; i < linesSize; i++) {
            // 空文字列であるか走査する
            String text = srcLines[i];
            if ((text == null) || ("".equals(text))) {
                textIsEmpty = true;
                break;
            }
        }

        int maxLsb = 0;
        View v = null;
        if (textIsEmpty) {
            // すべて空文字列
            textR.width = textR.height = 0;
        } else {
            v = (c != null) ? (View) c.getClientProperty("html") : null;
            if (v != null) {
                // HTMLビューに計算させる
                textR.width = (int) v.getPreferredSpan(View.X_AXIS);
                textR.height = (int) v.getPreferredSpan(View.Y_AXIS);
            } else {
                // 自前で計算する

                // 許容する最大サイズを計測
                int availTextWidth;
                if (horizontalTextPosition == CENTER) {
                    availTextWidth = viewR.width;
                } else {
                    availTextWidth = viewR.width - (iconR.width + gap);
                }

                ArrayList newLines = new ArrayList();
                int lineHeight = getNewLineHeightStep(fm);
                if (autoWrap) {
                    // 自動折り返し
                    for (int i = 0; i < linesSize; i++) {
                        // テキスト矩形を計算
                        String text = srcLines[i];
                        int rowW = SwingUtilities.computeStringWidth(fm, text);

                        while (rowW > availTextWidth) {
                            // 有効長を超えた文字列の場合、折り返す
                            int totalWidth = 0;
                            int nChars;
                            for (nChars = 0; nChars < text.length(); nChars++) {
                                totalWidth += fm.charWidth(text.charAt(nChars));
                                if (totalWidth > availTextWidth) {
                                    break;
                                }
                            }
                            // 折り返す直前までの文字列を登録
                            newLines.add(text.substring(0, nChars));
                            text = text.substring(nChars);
                            // 再計測
                            rowW = SwingUtilities.computeStringWidth(fm, text);

                            // 一度でも有効長を超えた場合、有効長が最大長となる
                            textR.width = availTextWidth;

                            textR.height += lineHeight;
                        }
                        if (!"".equals(text)) {
                            // 残る文字が空でなければ追加
                            newLines.add(text);
                            textR.height += lineHeight;
                            if (textR.width < rowW) {
                                // 走査した中で最長の文字列
                                textR.width = rowW;
                            }
                        }

                    }

                } else {
                    // 行単位でクリップ計算
                    final String clipString = "...";
                    final int clipWidth = SwingUtilities.computeStringWidth(fm,
                            clipString);

                    for (int i = 0; i < linesSize; i++) {
                        // テキスト矩形を計算
                        String text = srcLines[i];
                        int rowW = SwingUtilities.computeStringWidth(fm, text);

                        int lsb = 0;
                        // SwingUtilities2.getLeftSideBearing
                        // はJDK1.4と1.5で引数が異なるため
                        // ランタイムの差によって実行時例外が発生する。
                        // try{
                        // lsb =
                        // com.sun.java.swing.SwingUtilities2.getLeftSideBearing(fm.getFont(),
                        // text);
                        // if (lsb < 0) {
                        // // If lsb is negative, add it to the width, the
                        // // text bounds will later be adjusted accordingly.
                        // rowW -= lsb;
                        // }
                        // }catch(Exception ex){
                        // lsb = 0;
                        // }

                        /*
                         * If the label text string is too wide to fit within
                         * the available space "..." and as many characters as
                         * will fit will be displayed instead.
                         */

                        if (rowW > availTextWidth) {
                            // 有効長を超えた文字列の場合、クリップする
                            int totalWidth = clipWidth;
                            int nChars;
                            for (nChars = 0; nChars < text.length(); nChars++) {
                                totalWidth += fm.charWidth(text.charAt(nChars));
                                if (totalWidth > availTextWidth) {
                                    break;
                                }
                            }
                            text = text.substring(0, nChars) + clipString;
                            // 再計測
                            rowW = Math.max(rowW, SwingUtilities
                                    .computeStringWidth(fm, text));
                        }
                        newLines.add(text);

                        if (textR.width < rowW) {
                            // 走査した中で最長の文字列
                            textR.width = rowW;

                            if (lsb < 0) {
                                maxLsb = lsb;
                            }
                        }
                        textR.height += lineHeight;
                    }
                }
                // 調整した文字列に置き換える
                Object[] buf = newLines.toArray();
                srcLines = new String[buf.length];
                System.arraycopy(buf, 0, srcLines, 0, buf.length);
            }
        }

        if (minimumWidth > 0) {
            // Columnsによる横幅確保
            textR.width = Math.min(viewR.width, Math.max(textR.width,
                    getColumns() * getColumnWidth()));
        }

        if (minimumHeight > 0) {
            // Columnsによる横幅確保
            textR.height = Math.min(viewR.height, Math.max(textR.height,
                    minimumHeight));
        }

        /*
         * Compute textR.x,y given the verticalTextPosition and
         * horizontalTextPosition properties
         */

        if (verticalTextPosition == TOP) {
            if (horizontalTextPosition != CENTER) {
                textR.y = 0;
            } else {
                textR.y = -(textR.height + gap);
            }
        } else if (verticalTextPosition == CENTER) {
            textR.y = (iconR.height / 2) - (textR.height / 2);
        } else { // (verticalTextPosition == BOTTOM)
            if (horizontalTextPosition != CENTER) {
                textR.y = iconR.height - textR.height;
            } else {
                textR.y = (iconR.height + gap);
            }
        }

        if (horizontalTextPosition == LEFT) {
            textR.x = -(textR.width + gap);
        } else if (horizontalTextPosition == CENTER) {
            textR.x = (iconR.width / 2) - (textR.width / 2);
        } else { // (horizontalTextPosition == RIGHT)
            textR.x = (iconR.width + gap);
        }

        /*
         * labelR is the rectangle that contains iconR and textR. Move it to its
         * proper position given the labelAlignment properties. To avoid
         * actually allocating a Rectangle, Rectangle.union has been inlined
         * below.
         */
        int labelR_x = Math.min(iconR.x, textR.x);
        int labelR_width = Math.max(iconR.x + iconR.width, textR.x
                + textR.width)
                - labelR_x;
        int labelR_y = Math.min(iconR.y, textR.y);
        int labelR_height = Math.max(iconR.y + iconR.height, textR.y
                + textR.height)
                - labelR_y;

        int dx, dy;

        if (verticalAlignment == TOP) {
            dy = viewR.y - labelR_y;
        } else if (verticalAlignment == CENTER) {
            dy = (viewR.y + (viewR.height / 2))
                    - (labelR_y + (labelR_height / 2));
        } else { // (verticalAlignment == BOTTOM)
            dy = (viewR.y + viewR.height) - (labelR_y + labelR_height);
        }

        // 水平方向の位置あわせ
        switch (horizontalAlignment) {
        case CENTER:
            dx = (viewR.x + (viewR.width / 2))
                    - (labelR_x + (labelR_width / 2));
            break;
        case TRAILING:
        case RIGHT:
            dx = (viewR.x + viewR.width) - (labelR_x + labelR_width);
            break;
        default:
            dx = viewR.x - labelR_x;
            break;
        }

        /*
         * Translate textR and glypyR by dx,dy.
         */

        textR.x += dx;
        textR.y += dy;

        iconR.x += dx;
        iconR.y += dy;

        if (maxLsb < 0) {
            // lsb is negative. We previously adjusted the bounds by lsb,
            // we now need to shift the x location so that the text is
            // drawn at the right location. The result is textR does not
            // line up with the actual bounds (on the left side), but we will
            // have provided enough space for the text.
            textR.width += maxLsb;
            textR.x -= maxLsb;
        }
        
        
        Insets ins=getMargin();
        if(ins!=null){
            //マージンをつける
            viewR.width += ins.left+ins.right;
            textR.x += ins.left;
            textR.width -= ins.right;
            iconR.x += ins.left;
            viewR.height += ins.top+ins.bottom;
            textR.y += ins.top;
            textR.height -= ins.bottom;
            iconR.y += ins.top;
        }
        

        return srcLines;
    }

    public void setPreferredSize(Dimension preferredSize) {
        // overrideのため再定義
        this.preferredSize = preferredSize;
    }

    public Dimension getPreferredSize() {
        // overrideのため再定義
        if (preferredSize != null) {
            return preferredSize;
        }

        JLabel label = this;
        String text = label.getText();
        Icon icon = (label.isEnabled()) ? label.getIcon() : label
                .getDisabledIcon();
        Insets insets = label.getInsets();
        Font font = label.getFont();

        int dx = insets.left + insets.right;
        int dy = insets.top + insets.bottom;

        if ((icon == null)
                && ((text == null) || ((text != null) && (font == null)))) {
            // 文字もアイコンも未設定
            int d = 0;
            d++;
        } else if ((text == null) || ((icon != null) && (font == null))) {
            // アイコンのみ設定済み
            dx += icon.getIconWidth();
            dy += icon.getIconHeight();
        } else {

            Rectangle viewR = new Rectangle();
            Rectangle iconR = new Rectangle();
            Rectangle textR = new Rectangle();
            // 構造体の初期化
            iconR.x = iconR.y = iconR.width = iconR.height = 0;
            textR.x = textR.y = textR.width = textR.height = 0;
            viewR.width = viewR.height = Short.MAX_VALUE;
            viewR.x = dx;
            viewR.y = dy;

            Dimension d = label.getSize();
            if (d instanceof Dimension) {
                if (d.width > 0) {
                    // 自分の横幅が分かっているならば制限する
                    viewR.width = Math.min(viewR.width, d.width);
                }
            }

            int minW = getColumnWidth() * getColumns();
            int minH = getRowHeight() * getRows();
            if (minW > 0) {
                // Columnsによる最大幅制限
                viewR.width = Math.min(viewR.width, minW);
                // 高さは折り返し等を含めて計算で求めるため制限しない
            }

            // 要求領域を計算
            calculateText(label, lines, viewR, iconR, textR, isAutoWrap(),
                    minW, minH);

            // 最終的な要求矩形を計算
            dx = insets.left + insets.right;
            dy = insets.top + insets.bottom;
            if (getVerticalTextPosition() == SwingConstants.CENTER) {
                if (icon != null) {
                    dy += Math.max(iconR.height, textR.height);
                } else {
                    dy += textR.height;
                }
            } else {
                dy += textR.height;
                if (icon != null) {
                    dy += iconR.height + getIconTextGap();
                }
            }
            if (getHorizontalTextPosition() == SwingConstants.CENTER) {
                if (icon != null) {
                    dx += Math.max(iconR.width, textR.width);
                } else {
                    dx += textR.width;
                }
            } else {
                dx += textR.width;
                if (icon != null) {
                    dx += iconR.width + getIconTextGap();
                }
            }
        }

        return new Dimension(dx, dy);
    }

    public void paintComponent(Graphics g) {
        JComponent c = this;
        JLabel label = this;
        String text = label.getText();
        Icon icon = (label.isEnabled()) ? label.getIcon() : label
                .getDisabledIcon();

        if ((icon == null) && (text == null)) {
            return;
        }

        FontMetrics fm = g.getFontMetrics();
        Insets insets = c.getInsets();

        Rectangle viewR = new Rectangle();
        Rectangle iconR = new Rectangle();
        Rectangle textR = new Rectangle();
        // 構造体の初期化
        iconR.x = iconR.y = iconR.width = iconR.height = 0;
        textR.x = textR.y = textR.width = textR.height = 0;
        viewR.x = insets.left;
        viewR.y = insets.top;
        viewR.width = c.getWidth() - (insets.left + insets.right);
        viewR.height = c.getHeight() - (insets.left + insets.right);

        // 描画領域を算出
        String[] clippedText = calculateText(label, fm, lines, icon, viewR,
                iconR, textR, isAutoWrap(), getColumnWidth() * getColumns(),
                getRowHeight() * getRows());
        // 背景を描画
        paintBackground(g);

        if ((icon != null) && (!isForegroundIcon())) {
            // アイコンを描画
            icon.paintIcon(c, g, iconR.x, iconR.y);
        }

        if (text != null) {
            View v = (View) c.getClientProperty(BasicHTML.propertyKey);
            if (v != null) {
                // ビューが定義されていれば描画を委譲する
                v.paint(g, textR);
            } else {
                int linesSize = clippedText.length;
                int[] drawLefts = new int[linesSize];
                // 文字揃えによって書き出し位置を調整
                switch (getHorizontalAlignment()) {
                case SwingConstants.CENTER:
                    for (int i = 0; i < linesSize; i++) {
                        int w = fm.stringWidth(clippedText[i]);
                        drawLefts[i] = textR.x + textR.width
                                - (textR.width + w) / 2;
                    }
                    break;
                case SwingConstants.TRAILING:
                case SwingConstants.RIGHT:
                    for (int i = 0; i < linesSize; i++) {
                        int w = fm.stringWidth(clippedText[i]);
                        drawLefts[i] = textR.x + textR.width - w;
                    }
                    break;
                default:
                    for (int i = 0; i < linesSize; i++) {
                        drawLefts[i] = textR.x;
                    }
                    break;
                }

                int textY = textR.y + fm.getAscent();
                int lineHeight = getNewLineHeightStep(fm);
                // 1行ずつ描画
                if (label.isEnabled()) {
                    for (int i = 0; i < linesSize; i++) {
                        paintEnabledText(label, g, clippedText[i],
                                drawLefts[i], textY);
                        textY += lineHeight;
                    }
                } else {
                    for (int i = 0; i < linesSize; i++) {
                        paintDisabledText(label, g, clippedText[i],
                                drawLefts[i], textY);
                        textY += lineHeight;
                    }
                }
            }
        }

        if ((icon != null) && (isForegroundIcon())) {
            // アイコンを描画
            icon.paintIcon(c, g, iconR.x, iconR.y);
        }
    }

    /**
     * Paint clippedText at textX, textY with the labels foreground color.
     * 
     * @param l 描画ラベル
     * @param g 描画先
     * @param s 描画文字列
     * @param textX 描画X座標
     * @param textY 描画Y座標
     * @see #paint
     * @see #paintDisabledText
     */
    protected void paintEnabledText(JLabel l, Graphics g, String s, int textX,
            int textY) {
        int mnemIndex = l.getDisplayedMnemonicIndex();
        g.setColor(l.getForeground());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, mnemIndex, textX,
                textY);
    }

    /**
     * Paint clippedText at textX, textY with background.lighter() and then
     * shifted down and to the right by one pixel with background.darker().
     * 
     * @param l 描画ラベル
     * @param g 描画先
     * @param s 描画文字列
     * @param textX 描画X座標
     * @param textY 描画Y座標
     * @see #paint
     * @see #paintEnabledText
     */
    protected void paintDisabledText(JLabel l, Graphics g, String s, int textX,
            int textY) {
        int accChar = l.getDisplayedMnemonicIndex();
        Color background = l.getBackground();
        g.setColor(background.brighter());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, accChar, textX + 1,
                textY + 1);
        g.setColor(background.darker());
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, s, accChar, textX,
                textY);
    }

    /**
     * 自動改行を行なうかを返します。
     * 
     * @return 自動改行を行なうか
     */
    public boolean isAutoWrap() {
        return autoWrap;
    }

    /**
     * 自動改行を行なうかを設定します。
     * 
     * @param autoWrap 自動改行を行なうか
     */
    public void setAutoWrap(boolean autoWrap) {
        this.autoWrap = autoWrap;
    }

    public void paintBackground(Graphics g) {
        if (isOpaque()) {
            // 背景を描画
            Color oldColor = g.getColor();
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(oldColor);
        }
    }

    /**
     * アイコンを文字列の前面に描画するかを返します。
     * 
     * @return アイコンを文字列の前面に描画するか
     */
    public boolean isForegroundIcon() {
        return foregroundIcon;
    }

    /**
     * アイコンを文字列の前面に描画するかを設定します。
     * 
     * @param foregroundIcon アイコンを文字列の前面に描画するか
     */
    public void setForegroundIcon(boolean foregroundIcon) {
        this.foregroundIcon = foregroundIcon;
    }

    /**
     * アイコンのリソースパスを返します。
     * 
     * @return アイコンのリソースパス
     */
    public String getIconPath() {
        return iconType;
    }

    /**
     * アイコンのリソースパスを設定します。
     * 
     * @param iconPath アイコンのリソースパス
     */
    public void setIconPath(String iconPath) {
        this.iconType = iconPath;
        if ((iconPath == null) || ("".equals(iconPath))) {
            setIcon(null);
        } else {
            try {
                setIcon(ACResourceIconPooler.getInstance().getImage(iconPath));
            } catch (Exception ex) {
                ex.printStackTrace();
                setIcon(null);
                this.iconType = "";
            }
        }
    }
}
