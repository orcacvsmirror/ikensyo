package jp.or.med.orca.ikensho.component.picture;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.border.VRFrameBorder;
import jp.nichicom.vr.layout.VRBorderLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHumanPicture extends JPanel implements VRBindable {
    protected ArrayList listeners = new ArrayList();

    protected VRBindSource source;

    protected String bindPath;

    protected boolean autoApplySource = false;

    protected static final int TYPE_UNKOWN = 0;
    protected static final int TYPE_RISE_LINE = 1;
    protected static final int TYPE_DOWN_LINE = 2;
    protected static final int TYPE_HORIZONTAL_LINE = 3;
    protected static final int TYPE_VERTICAL_LINE = 4;
    protected static final int TYPE_RISE_LINE_ELIPSE = 5;
    protected static final int TYPE_AREA = 6;
    protected static final int TYPE_DOWN_LINE_ELIPSE = 7;
    protected static final int TYPE_DOT = 8;
    protected static final int TYPE_ELIPSE = 9;
    protected static final int TYPE_TEXT = 10;
    protected static final int TYPE_LINE = 11;
    protected static final int TYPE_RISE_LINE_LINE = 12;
    protected static final int TYPE_DOWN_LINE_LINE = 13;
    protected static final int TYPE_BOLD_LINE_AREA = 14;

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    public void applySource() throws ParseException {
        VRArrayList array = new VRArrayList();

        int end = getPictureEditor().getDrawCount();
        for (int i = 0; i < end; i++) {
            IkenshoHumanPictureDrawable draw = getPictureEditor().getDraw(i);
            int pos;
            if (draw instanceof IkenshoHumanPictureDrawFactory.DrawRiseLine) {
                pos = TYPE_RISE_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawDownLine) {
                pos = TYPE_DOWN_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawHorizontalLine) {
                pos = TYPE_HORIZONTAL_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawVerticalLine) {
                pos = TYPE_VERTICAL_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawRiseLineElipse) {
                pos = TYPE_RISE_LINE_ELIPSE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawArea) {
                pos = TYPE_AREA;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawDownLineElipse) {
                pos = TYPE_DOWN_LINE_ELIPSE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawDot) {
                pos = TYPE_DOT;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawEllipse) {
                pos = TYPE_ELIPSE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawText) {
                pos = TYPE_TEXT;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawLine) {
                pos = TYPE_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawRiseLineLine) {
                pos = TYPE_RISE_LINE_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawDownLineLine) {
                pos = TYPE_DOWN_LINE_LINE;
            } else if (draw instanceof IkenshoHumanPictureDrawFactory.DrawBoldLineArea) {
                pos = TYPE_BOLD_LINE_AREA;
            } else {
                continue;
            }
            VRMap row = new VRHashMap();
            row.setData("BUI", new Integer(pos));
            // 初期値代入
            row.setData("X", new Integer(0));
            row.setData("Y", new Integer(0));
            row.setData("SX", new Integer(0));
            row.setData("SY", new Integer(0));
            row.setData("STRING", "");
            draw.output(row);
            array.add(row);
        }
        if (VRBindPathParser.set(getBindPath(), getSource(), array)) {
            fireApplySource();
        }
    }

    public void bindSource() throws ParseException {
        Object obj = VRBindPathParser.get(getBindPath(), source);
        if (!(obj instanceof VRArrayList)) {
            return;
        }

        getPictureEditor().clear();

        VRArrayList array = (VRArrayList) obj;
        Iterator it = array.iterator();
        while (it.hasNext()) {
            VRMap row = (VRMap) it.next();
            IkenshoHumanPictureDrawable draw = null;
            switch (((Integer) VRBindPathParser.get("BUI", row)).intValue()) {
            case TYPE_RISE_LINE: // 四肢欠損(／)
                draw = new IkenshoHumanPictureDrawFactory.DrawRiseLine();
                break;
            case TYPE_DOWN_LINE: // 四肢欠損(＼)
                draw = new IkenshoHumanPictureDrawFactory.DrawDownLine();
                break;
            case TYPE_HORIZONTAL_LINE: // 四肢欠損(－)
                draw = new IkenshoHumanPictureDrawFactory.DrawHorizontalLine();
                break;
            case TYPE_VERTICAL_LINE: // 四肢欠損(｜)
                draw = new IkenshoHumanPictureDrawFactory.DrawVerticalLine();
                break;
            case TYPE_RISE_LINE_ELIPSE: // 麻痺
                draw = new IkenshoHumanPictureDrawFactory.DrawRiseLineElipse();
                break;
            case TYPE_AREA: // 筋力の低下
                draw = new IkenshoHumanPictureDrawFactory.DrawArea();
                break;
            case TYPE_DOWN_LINE_ELIPSE: // 褥瘡
                draw = new IkenshoHumanPictureDrawFactory.DrawDownLineElipse();
                break;
            case TYPE_DOT: // その他の皮膚疾患
                draw = new IkenshoHumanPictureDrawFactory.DrawDot();
                break;
            case TYPE_ELIPSE: // 円(任意)
                draw = new IkenshoHumanPictureDrawFactory.DrawEllipse();
                break;
            case TYPE_TEXT: // コメント(任意)
                draw = new IkenshoHumanPictureDrawFactory.DrawText();
                break;
            case TYPE_LINE: // 拡張四肢欠損
                draw = new IkenshoHumanPictureDrawFactory.DrawLine();
                break;
            case TYPE_RISE_LINE_LINE: // 拡張麻痺
                draw = new IkenshoHumanPictureDrawFactory.DrawRiseLineLine();
                break;
            case TYPE_DOWN_LINE_LINE: // 拡張褥瘡
                draw = new IkenshoHumanPictureDrawFactory.DrawDownLineLine();
                break;
            case TYPE_BOLD_LINE_AREA: // 拡張その他の皮膚疾患
                draw = new IkenshoHumanPictureDrawFactory.DrawBoldLineArea();
                break;
            default:
                continue;
            }

            draw.input(row);

            getPictureEditor().addDraw(draw);
        }

        fireBindSource();
    }

    public VRBindSource getSource() {
        return source;
    }

    public String getBindPath() {
        return bindPath;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listeners.add(listener);
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listeners.remove(listener);
    }

    protected void fireBindSource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ((VRBindEventListener) it.next()).bindSource(e);
        }
    }

    protected void fireApplySource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ((VRBindEventListener) it.next()).applySource(e);
        }
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    public Object createSource() {
        return new VRArrayList();
    }

    private JPanel jPanel = null;
    private IkenshoHumanPictureEditor pictureEditor = null;
    private JPanel jPanel1 = null;
    private JLabel lblFill = null;
    private JLabel lblLine = null;
    private JLabel lblDash = null;
    private JLabel lblRise = null;
    private JLabel lblDown = null;
    private JButton btnEdit = null;
    private JLabel lblFill2 = null;
    private JPanel pnlEtc = null;

    /**
     * コンストラクタ
     */
    public IkenshoHumanPicture() {
        super();
        // TODO 自動生成されたコンストラクター・スタブ

        initialize();
    }

    /**
     * コンストラクタです。
     * 
     * @param isDoubleBuffered ダブルバッファリングを行うか
     */
    public IkenshoHumanPicture(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        // TODO 自動生成されたコンストラクター・スタブ

        initialize();
    }

    /**
     * コンストラクタです。
     * 
     * @param layout レイアウトマネージャ
     */
    public IkenshoHumanPicture(LayoutManager layout) {
        super(layout);
        // TODO 自動生成されたコンストラクター・スタブ

        initialize();
    }

    /**
     * コンストラクタ
     * 
     * @param layout レイアウトマネージャ
     * @param isDoubleBuffered ダブルバッファリングを行うか
     */
    public IkenshoHumanPicture(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        // TODO 自動生成されたコンストラクター・スタブ

        initialize();
    }

    /**
     * コンポーネントを初期化します。
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(350, 264);
        this.add(getJPanel(), java.awt.BorderLayout.WEST);
        this.add(getPictureEditor(), java.awt.BorderLayout.CENTER);
        this.setOpaque(false);
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new VRBorderLayout());
            jPanel.add(getJPanel1(), VRBorderLayout.NORTH);
            jPanel.add(getBtnEdit(), VRBorderLayout.SOUTH);
        }
        return jPanel;
    }

    /**
     * This method initializes VRPictureEditor
     * 
     * @return IkenshoHumanPictureEditor
     */
    public IkenshoHumanPictureEditor getPictureEditor() {
        if (pictureEditor == null) {
            pictureEditor = new IkenshoHumanPictureEditor();
            try {
                pictureEditor.setImage(ImageIO.read(getClass().getClassLoader()
                        .getResource(IkenshoConstants.BODY_IMAGE_PATH_BODY)));
                pictureEditor.setStretchImage(true);
                pictureEditor
                        .setBorder(javax.swing.BorderFactory
                                .createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return pictureEditor;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            lblFill2 = new JLabel();
            lblDown = new JLabel();
            lblRise = new JLabel();
            lblDash = new JLabel();
            lblLine = new JLabel();
            lblFill = new JLabel();
            jPanel1 = new JPanel();
            jPanel1.setLayout(new VRBorderLayout(0, 8));
            jPanel1.setBorder(new VRFrameBorder("凡例"));
            lblRise.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_LEGEND_RISE));
            lblDash.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_LEGEND_DASH));
            lblFill.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_LEGEND_FILL));
            lblFill.setText("その他の");
            lblLine.setText("四肢欠損");
            lblLine.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_LEGEND_LINE));
            lblDash.setText("筋力の低下");
            lblRise.setText("麻酔");
            lblDown.setText("褥瘡");
            lblDown.setIcon(ACResourceIconPooler.getInstance().getImage(
                    IkenshoConstants.BODY_IMAGE_PATH_LEGEND_DOWN));
            lblFill2.setText("皮膚疾患");
            lblFill2
                    .setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
            lblFill2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            lblFill2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
            jPanel1.add(lblLine, VRBorderLayout.NORTH);
            jPanel1.add(lblRise, VRBorderLayout.NORTH);
            jPanel1.add(lblDash, VRBorderLayout.NORTH);
            jPanel1.add(lblDown, VRBorderLayout.NORTH);
            jPanel1.add(getPnlEtc(), VRBorderLayout.CLIENT);
        }
        return jPanel1;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBtnEdit() {
        if (btnEdit == null) {
            btnEdit = new JButton();
            btnEdit.setText("全身図の編集");
            btnEdit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    IkenshoHumanPictureEditorDialog ip = new IkenshoHumanPictureEditorDialog(
                            ACFrame.getInstance(), true);
                    ip.getPictureEditor().clear();
                    // キャンセルに耐える為にクローンで渡す
                    try {
                        for (int i = 0; i < pictureEditor.getDrawCount(); i++) {
                            ip.getPictureEditor().addDraw(
                                    (IkenshoHumanPictureDrawable) pictureEditor
                                            .getDraw(i).clone());
                        }
                    } catch (Exception ex) {
                        IkenshoCommon.showExceptionMessage(ex);
                    }
                    if (ip.showDialog()) {
                        pictureEditor.clear();
                        for (int i = 0; i < ip.getPictureEditor()
                                .getDrawCount(); i++) {
                            pictureEditor.addDraw(ip.getPictureEditor()
                                    .getDraw(i));
                        }
                    }

                }
            });
        }
        return btnEdit;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getPnlEtc() {
        if (pnlEtc == null) {
            GridLayout gridLayout11 = new GridLayout();
            pnlEtc = new JPanel();
            pnlEtc.setLayout(gridLayout11);
            gridLayout11.setRows(2);
            gridLayout11.setColumns(1);
            pnlEtc.add(lblFill, VRBorderLayout.NORTH);
            pnlEtc.add(lblFill2, VRBorderLayout.NORTH);

        }
        return pnlEtc;
    }

    /**
     * 描画画像を取得
     * 
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        return getPictureEditor().getDrawImage();
    }

    /**
     * 描画画像を取得
     * 
     * @return BufferedImage
     */
    public byte[] getImageByteArray() {

        BufferedImage image = getPictureEditor().getDrawImage();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ImageIO.write(image, "jpeg", baos);
        } catch (IOException e) {
            throw new IllegalStateException(e.toString());
        }
        byte[] b = baos.toByteArray();
        return b;

    }

} // @jve:decl-index=0:visual-constraint="10,10"
