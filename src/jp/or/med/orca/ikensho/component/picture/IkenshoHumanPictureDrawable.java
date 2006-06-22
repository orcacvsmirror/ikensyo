package jp.or.med.orca.ikensho.component.picture;

import java.awt.Color;
import java.awt.Graphics;
import java.text.ParseException;

import jp.nichicom.vr.util.VRMap;

/** TODO <HEAD> */
public interface IkenshoHumanPictureDrawable extends Cloneable {

    /**
     * color を返します。
     * @return color
     */
    public Color getColor();

    /**
     * color を設定します
     * @param color color
     */
    public void setColor(Color color);

    /**
     * x を返します。
     * @return x
     */
    public int getX();

    /**
     * x を設定します
     * @param x x
     */
    public void setX(int x);

    /**
     * y を返します。
     * @return y
     */
    public int getY();

    /**
     * y を設定します
     * @param y y
     */
    public void setY(int y);

    /**
     * 描画実行
     * @param g
     */
    public void execute(Graphics g);

    /**
     * クローン生成
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException;

    /**
     * ロウデータを解析してプロパティを設定します。
     * @param row ロウデータ
     * @throws ParseException 処理例外
     */
    public void input(VRMap row) throws ParseException;

    /**
     * ロウデータにプロパティを設定します。
     * @param row ロウデータ
     * @throws ParseException 処理例外
     */
    public void output(VRMap row) throws ParseException;
}
