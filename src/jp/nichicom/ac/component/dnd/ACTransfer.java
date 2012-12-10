package jp.nichicom.ac.component.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * ���̃t���[�o�����N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACTransfer implements Transferable {
    private DataFlavor[] supportedFlavors;
    private Object value;
    /**
     * �R���X�g���N�^�ł��B
     */
    public ACTransfer() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * @param value �����f�[�^
     */
    public ACTransfer(Object value) {
        setValue(value);
    }


    public DataFlavor[] getTransferDataFlavors() {
        if (supportedFlavors == null) {
            try {
                supportedFlavors = createSupportFlavors();
            } catch (ClassNotFoundException cnfe) {
                supportedFlavors = new DataFlavor[] {};
            }
        }
        return supportedFlavors;
    }

    /**
     * �����f�[�^ ��Ԃ��܂��B
     * 
     * @return value �����f�[�^
     */
    public Object getValue() {
        return value;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        DataFlavor[] flavors = getTransferDataFlavors();
        if ((flavor != null) && (flavors != null)) {
            int end = flavors.length;
            for (int i = 0; i < end; i++) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * �����f�[�^ ��ݒ肵�܂��B
     * 
     * @param value �����f�[�^
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * �T�|�[�g���Ă���t���[�o�N���X���Ƃɓ������\�b�h��override���Ēl��Ԃ��܂��B
     * <p>
     * �Y������overload���Ȃ��ꍇ�AUnsupportedFlavorException�𔭍s���܂��B
     * </p>
     * 
     * @param flavor �t���[�o
     * @return �f�[�^
     * @throws UnsupportedFlavorException �T�|�[�g�ΏۊO��O
     */
    protected Object getSupportedTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException {
        return value;
    }

    public Object getTransferData(DataFlavor flavor) throws IOException,
            UnsupportedFlavorException {
        return getSupportedTransferData(flavor);
    }
    
    /**
     * �T�|�[�g�Ώۂ̃t���[�o�N���X�z���Ԃ��܂��B
     * 
     * @return �t���[�o�N���X�z��
     * @throws ClassNotFoundException �N���X������O
     */
    protected DataFlavor[] createSupportFlavors() throws ClassNotFoundException {
        return new DataFlavor[] {  };
    }

}
