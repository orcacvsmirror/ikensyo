package jp.or.med.orca.ikensho.component.picture;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** TODO <HEAD> */
public class IkenshoHumanPictureEditor extends JPanel {

    private static final Stroke DEFAULTSTROKE = new BasicStroke(1);

    private Image image = null;

    private BufferedImage drawImage = null;

    private boolean stretchImage;

    private Dimension preferredSize;

    private Rectangle drawRect;

    private ArrayList draws = new ArrayList();

    private int doCount = 0;

    private IkenshoHumanPictureDrawable activeDraw;

    private ChangeEvent changeEvent = new ChangeEvent(this);

    private transient Vector changeListeners;

    /**
     * コンストラクタ
     *
     */
    public IkenshoHumanPictureEditor() {
        super();
        setBackground(Color.white);
        setLayout(null);
        setFocusable(true);
    }

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(getBackground());
        g2.fill(g.getClipBounds());
        int l = 0;
        int t = 0;
        int w = getWidth();
        int h = getHeight();


        if (getBorder() != null) {
            Insets is = getBorder().getBorderInsets(this);
            l = is.left;
            t = is.top;
            w = w - is.left - is.right;
            h = h - is.top - is.bottom;
        }

        if(w>0 || h>0){

        if (drawImage != null && image != null) {
            int iw = image.getWidth(null);
            int ih = image.getHeight(null);


            int tl = l;
            int tt = t;
            double sw = 1;
            double sh = 1;

            AffineTransform at = g2.getTransform();

            if (stretchImage) {
                sw = (double) w / iw;
                sh = (double) h / ih;
                if (sw > sh) {
                    sw = sh;
                } else {
                    sh = sw;

                }

            }

            tl = l + (int) ((w - sw * iw) / 2);
            tt = t + (int) ((h - sh * ih) / 2);

            g2.translate(tl, tt);
            g2.scale(sw, sh);

            if(sw!=1){
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION ,
             RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            }
            g2.drawImage(drawImage, 0, 0, null);

            if(sw!=1){
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION ,
             RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            }


	            if (activeDraw != null) {
	                activeDraw.execute(g2);
	            }

            g2.setTransform(at);

        }


        g2.setStroke(DEFAULTSTROKE);

        if (getBorder() != null) {
            getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
        }
    }

    public Point pointToImagePos(int x, int y) {
        if (image != null) {

            int l = 0;
            int t = 0;
            int w = getWidth();
            int h = getHeight();

            if (getBorder() != null) {
                Insets is = getBorder().getBorderInsets(this);
                l = is.left;
                t = is.top;
                w = w - is.left - is.right;
                h = h - is.top - is.bottom;
            }

            int iw = image.getWidth(null);
            int ih = image.getHeight(null);

            double sw = 1;
            double sh = 1;

            if (stretchImage) {
                sw = (double) w / iw;
                sh = (double) h / ih;
                if (sw > sh) {
                    sw = sh;
                } else {
                    sh = sw;
                }
            }

            return new Point((int) ((x - (w - sw * iw) / 2 - l) / sw),
                    (int) ((y - (h - sh * ih) / 2 - t) / sh));

        }
        return new Point();
    }

    public Rectangle getImageViewRect() {
        if (image != null) {

            int l = 0;
            int t = 0;
            int w = getWidth();
            int h = getHeight();

            if (getBorder() != null) {
                Insets is = getBorder().getBorderInsets(this);
                l = is.left;
                t = is.top;
                w = w - is.left - is.right;
                h = h - is.top - is.bottom;
            }

            int iw = image.getWidth(null);
            int ih = image.getHeight(null);
            if (stretchImage) {
                return new Rectangle(l, t, w, h);

            } else {
                return new Rectangle(l + (w - iw) / 2, t + (h - ih) / 2, iw, ih);

            }
        }
        return new Rectangle();
    }

    public Rectangle getImageRect() {
        if (image != null) {

            int iw = image.getWidth(null);
            int ih = image.getHeight(null);
                return new Rectangle(0, 0, iw, ih);


        }
        return new Rectangle();
    }


    public void ResetBuffer() {

        if (drawImage != null && image != null) {

            Graphics2D g2 = drawImage.createGraphics();
            int w = getWidth();
            int h = getHeight();
            int iw = image.getWidth(null);
            int ih = image.getHeight(null);

            g2.drawImage(image, 0, 0, null);

            for (int i = 0; i < doCount; i++) {
                IkenshoHumanPictureDrawable dc = (IkenshoHumanPictureDrawable) draws.get(i);
                dc.execute(g2);
            }

        }
        repaint();
        fireStateChanged(changeEvent);
    }

    public void addActiveDraw(boolean clone) {
        if(activeDraw!=null){
	        IkenshoHumanPictureDrawable vd = activeDraw;
	        if(clone){
	            try{
	                vd=(IkenshoHumanPictureDrawable)vd.clone();
	            }catch(Exception ex){
	                ex.printStackTrace();
	            }

	        }else{
		        activeDraw = null;
	        }
	        addDraw(vd);
        }
    }

    public void addDraw(IkenshoHumanPictureDrawable drawCommand) {
        if (drawImage != null && drawCommand != null) {

            Graphics2D g2 = drawImage.createGraphics();
            drawCommand.execute(g2);

            draws.add(doCount, drawCommand);
            doCount++;

            resetRedo();
        }
        repaint();
        fireStateChanged(changeEvent);
    }

    public void resetRedo(){
        //再設定した場合、履歴を消す
        for (int i = draws.size() - 1; i >= doCount; i--) {
            draws.remove(i);
        }

    }


    public void removeDraw(int index) {
        if (drawImage != null && index < draws.size()) {
            draws.remove(index);
        }
        repaint();
        fireStateChanged(changeEvent);
    }

    public int getDrawCount() {
        return draws.size();
    }

    public IkenshoHumanPictureDrawable getDraw(int index) {
        return (IkenshoHumanPictureDrawable) draws.get(index);
    }

    public void undo() {
        if (canUndo()) {
            doCount--;
        }
        ResetBuffer();

    }

    public void redo() {
        if (canRedo()) {
            doCount++;
        }
        ResetBuffer();

    }

    public void clear() {
        doCount = 0;
        ResetBuffer();

    }

    public boolean canRedo() {
        return (doCount < draws.size());
    }

    public boolean canUndo() {
        return (doCount > 0);
    }

    /**
     * preferredSize を設定します
     *
     * @param preferredSize
     *            preferredSize
     */
    public void setPreferredSize(Dimension preferredSize) {
        this.preferredSize = preferredSize;
    }

    /**
     * サイズ設定
     *
     * @return Dimension 推奨サイズ
     */
    public Dimension getPreferredSize() {
        if (preferredSize != null) {
            return preferredSize;
        }

        if (image != null) {
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            return new Dimension(w, h);
        } else {
            return new Dimension(32, 32);
        }

    }

    /**
     * image を返します。
     *
     * @return image
     */
    public Image getImage() {
        return image;
    }

    /**
     * image を設定します
     *
     * @param image
     *            image
     */
    public void setImage(Image image) {
        this.image = image;
        if (this.image != null) {
            try {
                int w = this.image.getWidth(null);
                int h = this.image.getHeight(null);
                if(w>0 && h>0){
	                drawImage = new BufferedImage(w, h,
	                        BufferedImage.TYPE_INT_RGB);
	                ResetBuffer();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * activeDraw を返します。
     *
     * @return activeDraw
     */
    public IkenshoHumanPictureDrawable getActiveDraw() {
        return activeDraw;
    }

    /**
     * activeDraw を設定します
     *
     * @param activeDraw
     *            activeDraw
     */
    public void setActiveDraw(IkenshoHumanPictureDrawable activeDraw) {
        this.activeDraw = activeDraw;
        repaint();
    }

    /**
     * stretchImage を返します。
     *
     * @return stretchImage
     */
    public boolean isStretchImage() {
        return stretchImage;
    }

    /**
     * stretchImage を設定します
     *
     * @param stretchImage
     *            stretchImage
     */
    public void setStretchImage(boolean stretchImage) {
        this.stretchImage = stretchImage;
        repaint();
    }


    /**
	 * @return 描画画像を返します。
	 */
	public BufferedImage getDrawImage() {
		return drawImage;
	}



    public synchronized void removeChangeListener(ChangeListener l) {
        if (changeListeners != null && changeListeners.contains(l)) {
            Vector v = (Vector) changeListeners.clone();
            v.removeElement(l);
            changeListeners = v;
        }
    }

    public synchronized void addChangeListener(ChangeListener l) {
        Vector v = changeListeners == null ? new Vector(2)
                : (Vector) changeListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            changeListeners = v;
        }
    }

    protected void fireStateChanged(ChangeEvent e) {
        if (changeListeners != null) {
            Vector listeners = changeListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ChangeListener) listeners.elementAt(i)).stateChanged(e);
            }
        }
    }

}

