package jp.or.med.orca.ikensho.component.picture;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;

import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.component.VRTextField;
import jp.nichicom.vr.layout.VRBorderLayout;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHumanPictureEditorDialog extends JDialog {

    private javax.swing.JPanel jContentPane = null;

    private IkenshoHumanPictureEditor pictureEditor = null;

    private JPanel pnlTools = null;

    private JToggleButton btnDownLine = null;

    private JToggleButton btnRiseLine = null;

    private IkenshoHumanPictureDrawable draw;

    private JButton btnUndo = null;

    private JButton btnRedo = null;

    private JToggleButton btnFillRise = null;

    private Point pressPoint = null;

    private static IkenshoHumanPictureDrawable RISELINE = new IkenshoHumanPictureDrawFactory.DrawRiseLine();

    private static IkenshoHumanPictureDrawable DOWNLINE = new IkenshoHumanPictureDrawFactory.DrawDownLine();

    private static IkenshoHumanPictureDrawable HORIZONTAL = new IkenshoHumanPictureDrawFactory.DrawHorizontalLine();

    private static IkenshoHumanPictureDrawable VERTICAL = new IkenshoHumanPictureDrawFactory.DrawVerticalLine();

    private static IkenshoHumanPictureDrawable CIRCLE = new IkenshoHumanPictureDrawFactory.DrawCircle();

    private static IkenshoHumanPictureDrawable DOT = new IkenshoHumanPictureDrawFactory.DrawDot();

    private static IkenshoHumanPictureDrawable FILL_DOWNLINE = new IkenshoHumanPictureDrawFactory.DrawDownLineElipse();

    private static IkenshoHumanPictureDrawable FILL_RISELINE = new IkenshoHumanPictureDrawFactory.DrawRiseLineElipse();

    private static IkenshoHumanPictureDrawable AREA = new IkenshoHumanPictureDrawFactory.DrawArea();

    private static IkenshoHumanPictureDrawable TEXT = new IkenshoHumanPictureDrawFactory.DrawText();

    private static IkenshoHumanPictureDrawable BOLDLINE = new IkenshoHumanPictureDrawFactory.DrawBoldLineArea();

    private static IkenshoHumanPictureDrawable BOLDLINE_DOWNLINE = new IkenshoHumanPictureDrawFactory.DrawDownLineLine();

    private static IkenshoHumanPictureDrawable BOLDLINE_RISELINE = new IkenshoHumanPictureDrawFactory.DrawRiseLineLine();

    private static IkenshoHumanPictureDrawable LINE = new IkenshoHumanPictureDrawFactory.DrawLine();

    private static IkenshoHumanPictureDrawable ELLIPSE = new IkenshoHumanPictureDrawFactory.DrawEllipse();

    private JToggleButton btnFillDown = null;

    private JToggleButton btnDot = null;

    private JToggleButton btnArea = null;

    private JToggleButton btnHorizontal = null;

    private JToggleButton btnVertical = null;

    private JToggleButton btnEllipse = null;

    private JToggleButton btnText = null;

    private ButtonGroup grpButton = null; // @jve:decl-index=0:visual-constraint="622,190"

    private JPanel pnlBtns = null;

    private JButton btnCancel = null;

    private JButton btnOK = null;

    private JButton btnClear = null;

    private JCheckBox chkStretch = null;

    private boolean ok = false;

    private static Cursor BLANCCURSOR = Toolkit.getDefaultToolkit()
            .createCustomCursor(
                    new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                    new Point(0, 0), "blanc");

    private JTabbedPane jTabbedPane = null;
    private JPanel pnlBasic = null;
    private JPanel pnlExtends = null;
    private JToggleButton btnText2 = null;
    private JToggleButton btnLine = null;
    private JToggleButton btnEllipse2 = null;
    private JToggleButton btnBoldLine = null;
    private JToggleButton btnRiseLineLine = null;
    private JToggleButton btnDownLineline = null;
    private JToggleButton btnArea2 = null;

    /**
     * コンストラクタ
     * 
     * @throws java.awt.HeadlessException
     */
    public IkenshoHumanPictureEditorDialog() throws HeadlessException {
        super();
        initialize();
    }

    /**
     * コンストラクタ
     * 
     * @param owner ダイアログオーナー
     * @throws java.awt.HeadlessException 処理例外
     */
    public IkenshoHumanPictureEditorDialog(Dialog owner)
            throws HeadlessException {
        this(owner, false);
        initialize();
    }

    /**
     * コンストラクタ
     * 
     * @param owner ダイアログオーナー
     * @param modal モーダルモードで表示するか
     * @throws java.awt.HeadlessException 処理例外
     */
    public IkenshoHumanPictureEditorDialog(Dialog owner, boolean modal)
            throws HeadlessException {
        super(owner, modal);
        initialize();
    }

    /**
     * コンストラクタ
     * 
     * @param owner ダイアログオーナー
     * @throws java.awt.HeadlessException 処理例外
     */
    public IkenshoHumanPictureEditorDialog(Frame owner)
            throws HeadlessException {
        this(owner, false);
        initialize();
    }

    /**
     * コンストラクタ
     * 
     * @param owner ダイアログオーナー
     * @param modal モーダルモードで表示するか
     * @throws java.awt.HeadlessException 処理例外
     */
    public IkenshoHumanPictureEditorDialog(Frame owner, boolean modal)
            throws HeadlessException {
        super(owner, modal);
        initialize();
    }

    /**
     * This method initializes this
     */
    private void initialize() {
        this.setSize(557, 451);
        this.setContentPane(getJContentPane());
        this.setTitle("全身図の編集");
        getGrpButton();
    }

    public void show() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int h = getSize().height;
        int w = getSize().width;

        if (h > screenSize.height) {
            h = screenSize.height;
        }
        if (w > screenSize.width) {
            w = screenSize.width;
        }
        setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2, w, h);
        setVisible(true);
    }

    public boolean showDialog() {
        this.show();
        return ok;
    }

    /**
     * ok を返します。
     * 
     * @return ok
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private javax.swing.JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new javax.swing.JPanel();
            jContentPane.setLayout(new VRLayout());
            jContentPane.add(getPictureEditor(), VRLayout.CLIENT);
            jContentPane.add(getPnlBtns(), VRLayout.SOUTH);
            jContentPane.add(getPnlTools(), VRLayout.WEST);
        }
        return jContentPane;
    }

    /**
     * This method initializes IkenshoPictureEditor
     * 
     * @return IkenshoHumanPictureEditor
     */
    public IkenshoHumanPictureEditor getPictureEditor() {
        if (pictureEditor == null) {
            pictureEditor = new IkenshoHumanPictureEditor();
            BufferedImage bi = null;
            try {
                bi = ImageIO.read(getClass().getClassLoader().getResource(
                        IkenshoConstants.BODY_IMAGE_PATH_BODY));

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            pictureEditor.setImage(bi);

            pictureEditor.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
            pictureEditor.setBackground(java.awt.SystemColor.controlDkShadow);

            pictureEditor
                    .addChangeListener(new javax.swing.event.ChangeListener() {
                        public void stateChanged(javax.swing.event.ChangeEvent e) {
                            getBtnClear().setEnabled(pictureEditor.canUndo());
                            getBtnUndo().setEnabled(pictureEditor.canUndo());
                            getBtnRedo().setEnabled(pictureEditor.canRedo());
                        }
                    });
            pictureEditor
                    .addMouseMotionListener(new java.awt.event.MouseMotionListener() {
                        public void mouseDragged(java.awt.event.MouseEvent e) {

                            IkenshoHumanPictureDrawable nd = pictureEditor
                                    .getActiveDraw();
                            if (nd != null) {
                                Point p = pictureEditor.pointToImagePos(e
                                        .getX(), e.getY());
                                if (nd instanceof AbstractIkenshoHumanPictureDrawArea) {
                                    AbstractIkenshoHumanPictureDrawArea dr = (AbstractIkenshoHumanPictureDrawArea) nd;
                                    if (pressPoint != null) {
                                        int x = pressPoint.x;
                                        int y = pressPoint.y;
                                        Rectangle r = pictureEditor
                                                .getImageRect();
                                        int x2 = (int) (Math.min(Math.max(p.x,
                                                r.x), r.x + r.width - 1));
                                        int y2 = (int) (Math.min(Math.max(p.y,
                                                r.y), r.y + r.height - 1));

                                        dr.setX((int) Math.min(x, x2));
                                        dr.setY((int) Math.min(y, y2));
                                        dr.setWidth((int) Math.abs(x2 - x));
                                        dr.setHeight((int) Math.abs(y2 - y));
                                    }
                                    pictureEditor.repaint();
                                } else if (nd instanceof AbstractIkenshoHumanPictureDrawLine) {
                                    AbstractIkenshoHumanPictureDrawLine dl = (AbstractIkenshoHumanPictureDrawLine) nd;
                                    Rectangle r = pictureEditor.getImageRect();
                                    int x2 = (int) (Math.min(
                                            Math.max(p.x, r.x), r.x + r.width
                                                    - 1));
                                    int y2 = (int) (Math.min(
                                            Math.max(p.y, r.y), r.y + r.height
                                                    - 1));

                                    dl.setX2(x2);
                                    dl.setY2(y2);
                                    pictureEditor.repaint();

                                } else {

                                    nd.setX(p.x);
                                    nd.setY(p.y);

                                    pictureEditor.repaint();
                                }

                            } else {

                                if (draw != null) {

                                    Point p = pictureEditor.pointToImagePos(e
                                            .getX(), e.getY());

                                    if (draw instanceof AbstractIkenshoHumanPictureDrawArea) {
                                    } else if (draw instanceof AbstractIkenshoHumanPictureDrawLine) {
                                    } else if (draw instanceof AbstractIkenshoHumanPictureDrawText) {
                                    } else {
                                        // pictureEditor.setCursor(DEFAULTCURSOR);
                                        pictureEditor.setCursor(BLANCCURSOR);
                                        draw.setX(p.x);
                                        draw.setY(p.y);
                                        draw.setColor(Color.red);
                                        pictureEditor.setActiveDraw(draw);
                                        // pictureEditor.setToolTipText("マウスで描画位置を選択");

                                    }
                                }

                            }

                        }

                        public void mouseMoved(java.awt.event.MouseEvent e) {
                            Point p = pictureEditor.pointToImagePos(e.getX(), e
                                    .getY());
                            IkenshoHumanPictureDrawable nd = pictureEditor
                                    .getActiveDraw();
                            if (nd != null) {
                                nd.setX(p.x);
                                nd.setY(p.y);
                                pictureEditor.repaint();
                                if (nd instanceof AbstractIkenshoHumanPictureDrawLine) {
                                    AbstractIkenshoHumanPictureDrawLine dl = (AbstractIkenshoHumanPictureDrawLine) nd;
                                    dl.setX2(p.x);
                                    dl.setY2(p.y);
                                }

                            } else {
                                // エディターにドローオブジェクトが未設定の場合、新規作成
                                if (draw != null) {

                                    if (draw instanceof AbstractIkenshoHumanPictureDrawArea) {
                                        pictureEditor
                                                .setCursor(new java.awt.Cursor(
                                                        java.awt.Cursor.CROSSHAIR_CURSOR));
                                        // pictureEditor.setToolTipText("範囲をマウスでドラッグして選択してください");
                                    } else if (draw instanceof AbstractIkenshoHumanPictureDrawLine) {
                                        AbstractIkenshoHumanPictureDrawLine dl = (AbstractIkenshoHumanPictureDrawLine) draw;
                                        dl.setX(p.x);
                                        dl.setY(p.y);
                                        dl.setX2(p.x);
                                        dl.setY2(p.y);
                                        dl.setColor(Color.red);
                                        pictureEditor.setActiveDraw(dl);
                                        pictureEditor
                                                .setCursor(new java.awt.Cursor(
                                                        java.awt.Cursor.CROSSHAIR_CURSOR));
                                        // pictureEditor.setToolTipText("マウスでドラッグして選択してください");
                                    } else if (draw instanceof AbstractIkenshoHumanPictureDrawText) {
                                        pictureEditor
                                                .setCursor(new java.awt.Cursor(
                                                        java.awt.Cursor.TEXT_CURSOR));
                                        // pictureEditor.setToolTipText("マウスでテキスト描画位置を選択");
                                    } else {
                                        // pictureEditor.setCursor(DEFAULTCURSOR);
                                        pictureEditor.setCursor(BLANCCURSOR);
                                        draw.setX(p.x);
                                        draw.setY(p.y);
                                        draw.setColor(Color.red);
                                        pictureEditor.setActiveDraw(draw);
                                        // pictureEditor.setToolTipText("マウスで描画位置を選択");

                                    }

                                } else {
                                    pictureEditor
                                            .setCursor(new java.awt.Cursor(
                                                    java.awt.Cursor.DEFAULT_CURSOR));
                                }
                            }

                        }
                    });
            pictureEditor.addMouseListener(new java.awt.event.MouseListener() {
                public void mouseClicked(java.awt.event.MouseEvent e) {

                }

                public void mouseEntered(java.awt.event.MouseEvent e) {
                }

                public void mouseExited(java.awt.event.MouseEvent e) {
                    if (pressPoint == null) {
                        pictureEditor.setActiveDraw(null);
                    }
                }

                public void mousePressed(java.awt.event.MouseEvent e) {
                    // 新規に作成する

                    pressPoint = pictureEditor.pointToImagePos(e.getX(), e
                            .getY());

                    Rectangle r = pictureEditor.getImageViewRect();
                    if (r.contains(e.getX(), e.getY())) {

                        if (draw instanceof AbstractIkenshoHumanPictureDrawText) {
                            try {

                                PopupTextInput pt = new PopupTextInput(
                                        pictureEditor,
                                        (AbstractIkenshoHumanPictureDrawText) draw,
                                        pressPoint.x, pressPoint.y);

                                FontMetrics fm = getFontMetrics(pictureEditor
                                        .getFont());

                                pt.show(e.getX(),
                                        e.getY() - fm.getHeight() / 2, r.x
                                                + r.width - e.getX(), fm
                                                .getHeight());

                            } catch (Exception ex) {
                                IkenshoCommon.showExceptionMessage(ex);
                            }

                        } else if (draw instanceof AbstractIkenshoHumanPictureDrawArea) {
                            AbstractIkenshoHumanPictureDrawArea da = (AbstractIkenshoHumanPictureDrawArea) draw;
                            da.setX(pressPoint.x);
                            da.setY(pressPoint.y);
                            da.setWidth(0);
                            da.setHeight(0);
                            da.setColor(Color.red);

                            pictureEditor.setActiveDraw(draw);
                        } else if (draw instanceof AbstractIkenshoHumanPictureDrawLine) {
                            AbstractIkenshoHumanPictureDrawLine da = (AbstractIkenshoHumanPictureDrawLine) draw;
                            da.setX(pressPoint.x);
                            da.setY(pressPoint.y);
                            da.setX2(pressPoint.x);
                            da.setY2(pressPoint.y);
                            da.setColor(Color.red);

                            pictureEditor.setActiveDraw(draw);
                        } else {
                            IkenshoHumanPictureDrawable nd = pictureEditor
                                    .getActiveDraw();
                            if (nd != null) {
                                nd.setColor(Color.black);
                                pictureEditor.addActiveDraw(true);
                                pictureEditor.setActiveDraw(null);
                                pressPoint = null;
                            }
                        }
                    }
                }

                public void mouseReleased(java.awt.event.MouseEvent e) {
                    if (pressPoint != null) {
                        IkenshoHumanPictureDrawable nd = pictureEditor
                                .getActiveDraw();
                        if (nd != null) {

                            if (nd instanceof AbstractIkenshoHumanPictureDrawArea) {
                                nd.setColor(Color.black);
                                AbstractIkenshoHumanPictureDrawArea dr = (AbstractIkenshoHumanPictureDrawArea) nd;
                                // 4Pixcel以下の場合、描画しない
                                if (dr.getWidth() > 4 && dr.getHeight() > 4) {
                                    pictureEditor.addActiveDraw(true);
                                    pictureEditor.setActiveDraw(null);
                                }
                            } else if (nd instanceof AbstractIkenshoHumanPictureDrawLine) {
                                nd.setColor(Color.black);
                                pictureEditor.addActiveDraw(true);
                                pictureEditor.setActiveDraw(null);
                            }
                            // else {
                            // Rectangle r = pictureEditor.getImageViewRect();
                            // if (r.contains(e.getX(), e.getY())) {
                            // pictureEditor.addActiveDraw(true);
                            // }
                            // }

                        }
                    }
                    pressPoint = null;
                }
            });

        }
        return pictureEditor;
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlTools() {
        if (pnlTools == null) {
            pnlTools = new JPanel();
            pnlTools.setLayout(new VRBorderLayout(0, 0));
            pnlTools.add(getJTabbedPane(), VRBorderLayout.CLIENT);
            pnlTools.add(getChkStretch(), VRBorderLayout.SOUTH);
        }
        return pnlTools;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnDownLine() {
        if (btnDownLine == null) {
            btnDownLine = new JToggleButton();
            btnDownLine.setText("四肢欠損");
            btnDownLine.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_DOWN_LINE));

            btnDownLine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnDownLine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = DOWNLINE;
                }
            });
        }
        return btnDownLine;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnRiseLine() {
        if (btnRiseLine == null) {
            btnRiseLine = new JToggleButton();
            btnRiseLine.setText("四肢欠損");
            btnRiseLine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnRiseLine.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_RISE_LINE));
            btnRiseLine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = RISELINE;
                }
            });
        }
        return btnRiseLine;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnUndo() {
        if (btnUndo == null) {
            btnUndo = new JButton();
            btnUndo.setText("元に戻す");
            btnUndo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    getPictureEditor().undo();

                }
            });
        }
        return btnUndo;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnRedo() {
        if (btnRedo == null) {
            btnRedo = new JButton();
            btnRedo.setText("やり直す");
            btnRedo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    getPictureEditor().redo();

                }
            });
        }
        return btnRedo;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnFillRise() {
        if (btnFillRise == null) {
            getPictureEditor().undo();
            btnFillRise = new JToggleButton();
            btnFillRise.setText("麻痺");
            btnFillRise.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_FILL_RISE_LINE));

            btnFillRise.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnFillRise.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = FILL_RISELINE;

                }
            });
        }
        return btnFillRise;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnFillDown() {
        if (btnFillDown == null) {
            btnFillDown = new JToggleButton();
            btnFillDown.setText("褥瘡");
            btnFillDown.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_FILL_DOWN_LINE));
            btnFillDown.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnFillDown.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = FILL_DOWNLINE;
                }
            });
        }
        return btnFillDown;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnDot() {
        if (btnDot == null) {
            btnDot = new JToggleButton();
            btnDot.setText("その他の皮膚疾患");
            btnDot.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_DOT));
            btnDot.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnDot.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = DOT;
                }
            });
        }
        return btnDot;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnArea() {
        if (btnArea == null) {
            btnArea = new JToggleButton();
            btnArea.setText("筋力の低下");
            btnArea.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_AREA));

            btnArea.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnArea.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = AREA;
                }
            });

        }
        return btnArea;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnHorizontal() {
        if (btnHorizontal == null) {
            btnHorizontal = new JToggleButton();
            btnHorizontal.setText("四肢欠損");
            btnHorizontal.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_HORIZONTAL_LINE));
            btnHorizontal
                    .setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnHorizontal
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            draw = HORIZONTAL;
                        }
                    });
        }
        return btnHorizontal;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnVertical() {
        if (btnVertical == null) {
            btnVertical = new JToggleButton();
            btnVertical.setText("四肢欠損");
            btnVertical.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_VERTICAL_LINE));
            btnVertical.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnVertical.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = VERTICAL;
                }
            });
        }
        return btnVertical;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnEllipse() {
        if (btnEllipse == null) {
            btnEllipse = new JToggleButton();
            btnEllipse.setText("円（任意）");
            btnEllipse.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_ELLIPSE));

            btnEllipse.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnEllipse.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = CIRCLE;
                }
            });
        }
        return btnEllipse;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnText() {
        if (btnText == null) {
            btnText = new JToggleButton();
            btnText.setText("コメント(任意)");
            btnText.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_TEXT));

            btnText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnText.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = TEXT;
                }
            });
        }
        return btnText;
    }

    /**
     * This method initializes buttonGroup
     * 
     * @return javax.swing.ButtonGroup
     */
    private ButtonGroup getGrpButton() {
        if (grpButton == null) {

            grpButton = new ButtonGroup();
            grpButton.add(getBtnRiseLine());
            grpButton.add(getBtnDownLine());
            grpButton.add(getBtnHorizontal());
            grpButton.add(getBtnVertical());
            grpButton.add(getBtnFillRise());
            grpButton.add(getBtnFillDown());
            grpButton.add(getBtnDot());
            grpButton.add(getBtnEllipse());
            grpButton.add(getBtnArea());
            grpButton.add(getBtnText());
            grpButton.add(getBtnLine());
            grpButton.add(getBtnRiseLineLine());
            grpButton.add(getBtnArea2());
            grpButton.add(getBtnDownLineline());
            grpButton.add(getBtnBoldLine());
            grpButton.add(getBtnEllipse2());
            grpButton.add(getBtnText2());
        }
        return grpButton;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlBtns() {
        if (pnlBtns == null) {
            FlowLayout flowLayout1 = new FlowLayout();
            pnlBtns = new JPanel();
            pnlBtns.setLayout(new VRBorderLayout(4, 4));
            flowLayout1.setAlignment(java.awt.FlowLayout.RIGHT);
            pnlBtns.setBackground(new java.awt.Color(0, 51, 153));
            pnlBtns.add(getBtnOK(), VRBorderLayout.EAST);
            pnlBtns.add(getBtnCancel(), VRBorderLayout.EAST);
            pnlBtns.add(getBtnClear(), VRBorderLayout.WEST);
            pnlBtns.add(getBtnUndo(), VRBorderLayout.WEST);
            pnlBtns.add(getBtnRedo(), VRBorderLayout.WEST);
        }
        return pnlBtns;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnCancel() {
        if (btnCancel == null) {
            btnCancel = new JButton();
            btnCancel.setText("キャンセル");
            btnCancel.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return btnCancel;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnOK() {
        if (btnOK == null) {
            btnOK = new JButton();
            btnOK.setText("確定");
            btnOK.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    ok = true;
                    getPictureEditor().resetRedo();
                    dispose();
                }
            });
        }
        return btnOK;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnClear() {
        if (btnClear == null) {
            btnClear = new JButton();
            btnClear.setText("全消去");
            btnClear.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    pictureEditor.clear();

                }
            });
        }
        return btnClear;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getChkStretch() {
        if (chkStretch == null) {
            chkStretch = new JCheckBox();
            chkStretch.setText("画面に合わせる");
            chkStretch.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    pictureEditor.setStretchImage(chkStretch.isSelected());
                }
            });
        }
        return chkStretch;
    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("標準", null, getPnlBasic(), null);
            jTabbedPane.addTab("拡張", null, getPnlExtends(), null);
        }
        return jTabbedPane;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlBasic() {
        if (pnlBasic == null) {
            pnlBasic = new JPanel();
            pnlBasic.setLayout(new VRBorderLayout(0, 0));
            pnlBasic.add(getBtnRiseLine(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnDownLine(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnHorizontal(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnVertical(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnFillRise(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnArea(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnFillDown(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnDot(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnEllipse(), VRBorderLayout.NORTH);
            pnlBasic.add(getBtnText(), VRBorderLayout.NORTH);

        }
        return pnlBasic;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlExtends() {
        if (pnlExtends == null) {
            pnlExtends = new JPanel();
            pnlExtends.setLayout(new VRBorderLayout(0, 0));
            pnlExtends.add(getBtnLine(), VRBorderLayout.NORTH);
            pnlExtends.add(getBtnRiseLineLine(), VRBorderLayout.NORTH);
            pnlExtends.add(getBtnArea2(), VRBorderLayout.NORTH);
            pnlExtends.add(getBtnDownLineline(), VRBorderLayout.NORTH);
            pnlExtends.add(getBtnBoldLine(), VRBorderLayout.NORTH);
            pnlExtends.add(getBtnEllipse2(), VRBorderLayout.NORTH);
            pnlExtends.add(getBtnText2(), VRBorderLayout.NORTH);
        }
        return pnlExtends;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnText2() {
        if (btnText2 == null) {
            btnText2 = new JToggleButton();
            btnText2.setText("コメント（任意）");
            btnText2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnText2.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_TEXT));
            btnText2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = TEXT;

                }
            });
        }
        return btnText2;
    }

    /**
     * This method initializes jButton1
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnLine() {
        if (btnLine == null) {
            btnLine = new JToggleButton();
            btnLine.setText("四肢欠損");
            btnLine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnLine.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_RISE_LINE));
            btnLine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = LINE;
                }
            });
        }
        return btnLine;
    }

    /**
     * This method initializes jButton2
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnEllipse2() {
        if (btnEllipse2 == null) {
            btnEllipse2 = new JToggleButton();
            btnEllipse2.setText("円（任意）");
            btnEllipse2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnEllipse2.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_ELLIPSE));
            btnEllipse2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = ELLIPSE;

                }
            });
        }
        return btnEllipse2;
    }

    /**
     * This method initializes jButton3
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnBoldLine() {
        if (btnBoldLine == null) {
            btnBoldLine = new JToggleButton();
            btnBoldLine.setText("その他の皮膚疾患");
            btnBoldLine.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnBoldLine.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_DOT));
            btnBoldLine.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = BOLDLINE;

                }
            });
        }
        return btnBoldLine;
    }

    /**
     * This method initializes jButton4
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnRiseLineLine() {
        if (btnRiseLineLine == null) {
            btnRiseLineLine = new JToggleButton();
            btnRiseLineLine.setText("麻痺");
            btnRiseLineLine
                    .setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnRiseLineLine.setIcon(ACResourceIconPooler.getInstance()
                    .getImage(IkenshoConstants.BODY_IMAGE_PATH_FILL_RISE_LINE));
            btnRiseLineLine
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            draw = BOLDLINE_RISELINE;
                        }
                    });
        }
        return btnRiseLineLine;
    }

    /**
     * This method initializes jButton5
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnDownLineline() {
        if (btnDownLineline == null) {
            btnDownLineline = new JToggleButton();
            btnDownLineline.setText("褥瘡");
            btnDownLineline
                    .setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnDownLineline.setIcon(ACResourceIconPooler.getInstance()
                    .getImage(IkenshoConstants.BODY_IMAGE_PATH_FILL_DOWN_LINE));
            btnDownLineline
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            draw = BOLDLINE_DOWNLINE;

                        }
                    });

        }
        return btnDownLineline;
    }

    /**
     * This method initializes jButton6
     * 
     * @return javax.swing.JButton
     */
    private JToggleButton getBtnArea2() {
        if (btnArea2 == null) {
            btnArea2 = new JToggleButton();
            btnArea2.setText("筋力の低下");
            btnArea2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            btnArea2.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_AREA));
            btnArea2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    draw = AREA;

                }
            });
        }
        return btnArea2;
    }
} // @jve:decl-index=0:visual-constraint="10,10"

class PopupTextInput extends JPopupMenu implements FocusListener, KeyListener {
    private VRTextField editor = new VRTextField();

    private IkenshoHumanPictureEditor myPic;

    private AbstractIkenshoHumanPictureDrawText drawText;

    private int myX;

    private int myY;

    public PopupTextInput(IkenshoHumanPictureEditor myPic,
            AbstractIkenshoHumanPictureDrawText drawText, int x, int y) {
        super();
        this.myPic = myPic;
        this.drawText = drawText;
        this.myX = x;
        this.myY = y;
        editor.setMaxLength(25);
        editor.setFont(myPic.getFont());
        // editor.setColumns(20);
        editor.setBorder(null);
        editor.setMargin(new Insets(0, 0, 0, 0));
        add(editor);
        // setSize(editor.getPreferredSize());
        setBorder(BorderFactory.createEmptyBorder());
        editor.addFocusListener(this);
        editor.addKeyListener(this);
    }

    /*
     * (非 Javadoc)
     * 
     * @see javax.swing.JPopupMenu#show(java.awt.Component, int, int)
     */
    public void show(int x, int y, int w, int h) {

        editor.setPreferredSize(new Dimension(w, h));
        setSize(editor.getPreferredSize());
        super.show(myPic, x, y);
        editor.requestFocus();
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
     */
    public void focusGained(FocusEvent e) {

    }

    /*
     * (非 Javadoc)
     * 
     * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
     */
    public void focusLost(FocusEvent e) {

        decision();

    }

    private void decision() {

        if (editor.getText() != null && editor.getText().trim().length() > 0) {

            try {
                AbstractIkenshoHumanPictureDrawText dt = (AbstractIkenshoHumanPictureDrawText) drawText
                        .clone();
                dt.setColor(Color.black);
                dt.setText(editor.getText());
                dt.setX(myX);
                dt.setY(myY);
                myPic.addDraw(dt);

            } catch (Exception ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }

        }

        myPic.setActiveDraw(null);
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            setVisible(false);
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            editor.setText(null);
        }
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
    }

    /*
     * (非 Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
    }
}
