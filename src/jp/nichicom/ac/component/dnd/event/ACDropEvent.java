package jp.nichicom.ac.component.dnd.event;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDropEvent;
import java.util.EventObject;

/**
 * �h���b�v�C�x���g���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/03
 */
public class ACDropEvent extends EventObject {
    private Object[] dropRequestValues;
    private DropTargetDropEvent dropTargetDropEvent;
    private Transferable transfer;
    private  Object[] dropSuccessValues;

    /**
     * �h���b�v�ɐ��������l ��Ԃ��܂��B
     * @return �h���b�v�ɐ��������l
     */
    public Object[] getDropSuccessValues() {
        return dropSuccessValues;
    }

    /**
     * �h���b�v�ɐ��������l ��ݒ肵�܂��B
     * @param dropSuccessValues �h���b�v�ɐ��������l
     */
    public void setDropSuccessValues(Object[] dropSuccessValues) {
        this.dropSuccessValues = dropSuccessValues;
    }

    /**
     * �f�[�^������ ��Ԃ��܂��B
     * @return �f�[�^������
     */
    public Transferable getTransfer() {
        return transfer;
    }

    /**
     * �f�[�^������ ��ݒ肵�܂��B
     * @param transfer �f�[�^������
     */
    public void setTransfer(Transferable transfer) {
        this.transfer = transfer;
    }

    /**
     * �h���b�v�K��C�x���g��� ��Ԃ��܂��B
     * 
     * @return �h���b�v�K��C�x���g���
     */
    public DropTargetDropEvent getDropTargetDropEvent() {
        return dropTargetDropEvent;
    }

    /**
     * �h���b�v�K��C�x���g��� ��ݒ肵�܂��B
     * 
     * @param dropTargetDropEvent �h���b�v�K��C�x���g���
     */
    public void setDropTargetDropEvent(DropTargetDropEvent dropTargetDropEvent) {
        this.dropTargetDropEvent = dropTargetDropEvent;
    }

    /**
     * �h���b�v��v�������l ��Ԃ��܂��B
     * 
     * @return �h���b�v��v�������l
     */
    public Object[] getDropRequestValues() {
        return dropRequestValues;
    }

    /**
     * �h���b�v��v�������l ��ݒ肵�܂��B
     * 
     * @param dropRequestValues �h���b�v��v�������l
     */
    public void setDropRequestValues(Object[] dropRequestValues) {
        this.dropRequestValues = dropRequestValues;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param source �C�x���g������
     */
    public ACDropEvent(Object source) {
        super(source);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param source �C�x���g������
     * @param dropTargetDropEvent �h���b�v�K��C�x���g���
     * @param dropValue �h���b�v�����l
     */
    public ACDropEvent(Object source, DropTargetDropEvent dropTargetDropEvent,
            Object[] dropValue) {
        super(source);
        setDropTargetDropEvent(dropTargetDropEvent);
        setDropRequestValues(dropValue);
    }

}
