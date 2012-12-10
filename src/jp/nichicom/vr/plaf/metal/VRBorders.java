/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.text.JTextComponent;

/**
 * VR Look&Feel�ɂ�����{�[�_�[�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRBorders {
    private static Border buttonBorder;

    private static Border simpleButtonBorder;

    private static Border textFieldBorder;

    private static Border spinnerButtonBorder;

    /**
     * �{�^���{�[�_�[��Ԃ�
     * @return �{�^���{�[�_�[
     */
    public static Border getButtonBorder() {
        if (buttonBorder == null) {
            buttonBorder = new BorderUIResource.CompoundBorderUIResource(new VRBorders.ButtonBorder(), new BasicBorders.MarginBorder());
        }
        return buttonBorder;
    }
    
    /**
     * �V���v���{�^���{�[�_�[��Ԃ�
     * @return �V���v���{�^���{�[�_�[
     */
    public static Border getSimpleButtonBorder() {
        if (simpleButtonBorder == null) {
        	simpleButtonBorder = new BorderUIResource.CompoundBorderUIResource(new VRBorders.SimpleButtonBorder(), new BasicBorders.MarginBorder());
        }
        return simpleButtonBorder;
    }

    /**
     * �e�L�X�g�{�[�_�[��Ԃ�
     * @return �e�L�X�g�{�[�_�[
     */
    public static Border getTextFieldBorder() {
        if (textFieldBorder == null) {
            textFieldBorder = new BorderUIResource.CompoundBorderUIResource(new VRBorders.TextFieldBorder(), new BasicBorders.MarginBorder());
        }
        return textFieldBorder;
    }

    /**
     * �X�s�i�[�{�[�_�[��Ԃ�
     * @return �X�s�i�[�{�[�_�[
     */
    public static Border getSpinnerButtonBorder() {
        if (spinnerButtonBorder == null) {
            spinnerButtonBorder = new BorderUIResource.CompoundBorderUIResource(new VRBorders.SpinnerButtonBorder(), new BasicBorders.MarginBorder());
        }
        return spinnerButtonBorder;
    }

    /**
     * �|�b�v�A�b�v���j���[�{�[�_�[ <br />
     * �|�b�v�A�b�v���j���[�p�̃{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class PopupMenuBorder extends AbstractBorder implements
            UIResource {
        protected static Insets borderInsets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            VRDraw.drawButtonBorder(g, x, y, w, h);

        }

        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        public Insets getBorderInsets(Component c, Insets newInsets) {
            newInsets.top = borderInsets.top;
            newInsets.left = borderInsets.left;
            newInsets.bottom = borderInsets.bottom;
            newInsets.right = borderInsets.right;
            return newInsets;
        }
    }

    /**
     * ���j���[�o�[�{�[�_�[ <br />
     * ���j���[�o�[�p�̃{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class MenuBarBorder extends AbstractBorder implements
            UIResource {
        protected static Insets borderInsets = new Insets(0, 0, 1, 0);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            g.setColor(VRLookAndFeel.getControlDarkShadow());
            g.drawLine(x, y + h - 1, x + w, y + h - 1);

        }

        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        public Insets getBorderInsets(Component c, Insets newInsets) {
            newInsets.top = borderInsets.top;
            newInsets.left = borderInsets.left;
            newInsets.bottom = borderInsets.bottom;
            newInsets.right = borderInsets.right;
            return newInsets;
        }
    }

    /**
     * �@�荞�݃{�[�_�[ <br />
     * �@�荞�݃{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class VRFlush3DBorder extends AbstractBorder implements
            UIResource {

        private static final Insets insets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            if (c.isEnabled()) {
                VRDraw.drawFlush3DBorder(g, x, y, w, h);
            } else {
                VRDraw.drawDisabledBorder(g, x, y, w, h);
            }
        }

        public Insets getBorderInsets(Component c) {
            return insets;
        }

        public Insets getBorderInsets(Component c, Insets newInsets) {
            newInsets.top = insets.top;
            newInsets.left = insets.left;
            newInsets.bottom = insets.bottom;
            newInsets.right = insets.right;
            return newInsets;
        }
    }

    /**
     * �V���v���{�^���{�[�_�[ <br />
     * �V���v���{�^���{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class SimpleButtonBorder extends AbstractBorder implements
            UIResource {

        private static final Insets insets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            VRDraw.drawButtonBorder(g, x, y, w, h);
            

        }

        public Insets getBorderInsets(Component c) {
            return insets;
        }

        public Insets getBorderInsets(Component c, Insets newInsets) {
            newInsets.top = insets.top;
            newInsets.left = insets.left;
            newInsets.bottom = insets.bottom;
            newInsets.right = insets.right;
            return newInsets;
        }
    }

    
    /**
     * �{�^���{�[�_�[ <br />
     * �{�^���{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class ButtonBorder extends AbstractBorder implements
            UIResource {

        protected static Insets borderInsets = new Insets(3, 8, 3, 8);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            //            AbstractButton b=(AbstractButton)c;
            //            
            //            VRDraw.drawButtonBase(g, b.getBackground(), VRLookAndFeel
            //                    .getFocusColor(), VRLookAndFeel.getHoverColor(),
            //                    new Rectangle(x,y,w,h), 8+8, (3+3) * 4, VRDraw.BUTTONSHAPE_WROUND, b);
            //            

        }

        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        public Insets getBorderInsets(Component c, Insets newInsets) {
            newInsets.top = borderInsets.top;
            newInsets.left = borderInsets.left;
            newInsets.bottom = borderInsets.bottom;
            newInsets.right = borderInsets.right;
            return newInsets;
        }
    }

    /**
     * �e�L�X�g�{�[�_�[ <br />
     * �e�L�X�g�{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class TextFieldBorder extends VRFlush3DBorder {

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            if (!(c instanceof JTextComponent)) {
                // special case for non-text components (bug ID 4144840)
                if (c.isEnabled()) {
                    VRDraw.drawFlush3DBorder(g, x, y, w, h);
                } else {
                    VRDraw.drawDisabledBorder(g, x, y, w, h);
                }
                return;
            }

            if (c.isEnabled() && ((JTextComponent) c).isEditable()) {
                VRDraw.drawFlush3DBorder(g, x, y, w, h);
            } else {
                VRDraw.drawDisabledBorder(g, x, y, w, h);
            }

        }
    }

    /**
     * �X�s�i�[�{�[�_�[ <br />
     * �X�s�i�[�{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class SpinnerButtonBorder extends AbstractBorder implements
            UIResource {

        protected static Insets borderInsets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();

            if (model.isEnabled()) {
                boolean isPressed = model.isPressed() && model.isArmed();
                boolean isDefault = (button instanceof JButton && ((JButton) button).isDefaultButton());

                if (isPressed && isDefault) {
                    VRDraw.drawPressedButtonBorder(g, x + 1, y + 1, w - 2, h - 2);
                } else if (isPressed) {
                    VRDraw.drawPressedButtonBorder(g, x + 1, y + 1, w - 2, h - 2);
                } else if (isDefault) {
                    VRDraw.drawButtonBorder(g, x + 1, y + 1, w - 2, h - 2);
                } else {
                    VRDraw.drawButtonBorder(g, x + 1, y + 1, w - 2, h - 2);
                }
            } else { // disabled state
                VRDraw.drawDisabledBorder(g, x + 1, y + 1, w - 2, h - 2);
            }
        }

        public Insets getBorderInsets(Component c) {
            return borderInsets;
        }

        public Insets getBorderInsets(Component c, Insets newInsets) {
            newInsets.top = borderInsets.top;
            newInsets.left = borderInsets.left;
            newInsets.bottom = borderInsets.bottom;
            newInsets.right = borderInsets.right;
            return newInsets;
        }
    }

    /**
     * �X�N���[���y�C���{�[�_�[ <br />
     * �X�N���[���y�C���{�[�_�[�N���X�ł��B <br />
     * 
     * �쐬�� 2005/02/26 <br />
     * �X�V�� <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public static class ScrollPaneBorder extends AbstractBorder implements
            UIResource {

        private static final Insets insets = new Insets(1, 1, 1, 1);

        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

            VRDraw.drawFlush3DBorder(g, x, y, w, h);

            //             
            //             JScrollPane scroll = (JScrollPane)c;
            //             JComponent colHeader = scroll.getColumnHeader();
            //             int colHeaderHeight = 0;
            //             if (colHeader != null)
            //                colHeaderHeight = colHeader.getHeight();
            //
            //             JComponent rowHeader = scroll.getRowHeader();
            //             int rowHeaderWidth = 0;
            //             if (rowHeader != null)
            //                rowHeaderWidth = rowHeader.getWidth();
            //
            //
            //             g.translate( x, y);
            //
            //             g.setColor( VRLookAndFeel.getControlDarkShadow() );
            //             g.drawRect( 0, 0, w-2, h-2 );
            //             g.setColor( VRLookAndFeel.getControlHighlight() );
            //
            //             g.drawLine( w-1, 1, w-1, h-1);
            //             g.drawLine( 1, h-1, w-1, h-1);
            //
            //             g.setColor( VRLookAndFeel.getControl() );
            //             g.drawLine( w-2, 2+colHeaderHeight, w-2, 2+colHeaderHeight );
            //             g.drawLine( 1+rowHeaderWidth, h-2, 1+rowHeaderWidth, h-2 );
            //
            //             g.translate( -x, -y);

        }

        public Insets getBorderInsets(Component c) {
            return insets;
        }
    }

}