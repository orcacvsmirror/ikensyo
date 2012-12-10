package jp.nichicom.ac.component.table;

import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

/**
 * �������w��\�ȃe�[�u���w�b�_�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/27
 */
public class ACTableHeader extends JTableHeader {
    private int rows = 0;
    private int charHeight = 0;
    /**
     * Constructs a <code>JTableHeader</code> with a default
     * <code>TableColumnModel</code>.
     * 
     * @see #createDefaultColumnModel
     */
    public ACTableHeader() {
        super();
    }

    /**
     * Constructs a <code>JTableHeader</code> which is initialized with
     * <code>cm</code> as the column model. If <code>cm</code> is
     * <code>null</code> this method will initialize the table header with a
     * default <code>TableColumnModel</code>.
     * 
     * @param cm the column model for the table
     * @see #createDefaultColumnModel
     */
    public ACTableHeader(TableColumnModel cm) {
        super(cm);
    }

    /**
     * �m�ۂ��鍂���𕶎��� ��ݒ肵�܂��B
     * @param rows �m�ۂ��鍂���𕶎���
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * �m�ۂ��鍂���𕶎��� �ŕԂ��܂��B
     * @return �m�ۂ��鍂���𕶎���
     */
    public int getRows() {
        return rows;
    }

    /**
     * �s�̍������v�Z���ĕԂ��܂��B
     * 
     * @return �s�̍���
     */
    protected int getCharHeight() {
        if (charHeight == 0) {
            FontMetrics fm = getFontMetrics(getFont());
            charHeight = fm.getHeight();
        }
        return charHeight;
    }
    
    public Dimension getPreferredSize() {
        Dimension d=super.getPreferredSize();
        if(d==null){
            d = new Dimension(10,10);
        }
        if(getRows()>0){
            d.height =(int)Math.max(d.height, getCharHeight()*getRows());
        }
        return d;
    }

}
