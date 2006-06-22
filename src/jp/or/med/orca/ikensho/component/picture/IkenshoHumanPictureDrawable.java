package jp.or.med.orca.ikensho.component.picture;

import java.awt.Color;
import java.awt.Graphics;
import java.text.ParseException;

import jp.nichicom.vr.util.VRMap;

/** TODO <HEAD> */
public interface IkenshoHumanPictureDrawable extends Cloneable {

    /**
     * color ��Ԃ��܂��B
     * @return color
     */
    public Color getColor();

    /**
     * color ��ݒ肵�܂�
     * @param color color
     */
    public void setColor(Color color);

    /**
     * x ��Ԃ��܂��B
     * @return x
     */
    public int getX();

    /**
     * x ��ݒ肵�܂�
     * @param x x
     */
    public void setX(int x);

    /**
     * y ��Ԃ��܂��B
     * @return y
     */
    public int getY();

    /**
     * y ��ݒ肵�܂�
     * @param y y
     */
    public void setY(int y);

    /**
     * �`����s
     * @param g
     */
    public void execute(Graphics g);

    /**
     * �N���[������
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException;

    /**
     * ���E�f�[�^����͂��ăv���p�e�B��ݒ肵�܂��B
     * @param row ���E�f�[�^
     * @throws ParseException ������O
     */
    public void input(VRMap row) throws ParseException;

    /**
     * ���E�f�[�^�Ƀv���p�e�B��ݒ肵�܂��B
     * @param row ���E�f�[�^
     * @throws ParseException ������O
     */
    public void output(VRMap row) throws ParseException;
}
