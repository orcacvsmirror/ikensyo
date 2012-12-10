/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

/**
 * �e�[�u���̃��f���̃\�[�g��������������C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 * @see VRTableModelar
 */
public interface VRTableSortable {
    /**
     * <code>�����������萔</code>
     */
    public static final int ORDER_ASC = 0;

    /**
     * <code>�~���������萔</code>
     */
    public static final int ORDER_DESC = 1;

    /**
     * <code>����������������萔</code>
     */
    public static final String ORDER_ASC_VALUE = "ASC";

    /**
     * <code>�~��������������萔</code>
     */
    public static final String ORDER_DESC_VALUE = "DESC";

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param model �e�[�u�����f��
     * @param columnIndexs �\�[�g�D�揇�ʃC���f�b�N�X
     * @throws Exception ������O
     */
    public void sort(VRSortableTableModelar model, int[] columnIndexs, int[] orders);
}