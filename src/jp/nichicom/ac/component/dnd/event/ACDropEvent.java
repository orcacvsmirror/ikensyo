package jp.nichicom.ac.component.dnd.event;

import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTargetDropEvent;
import java.util.EventObject;

/**
 * ドロップイベント情報です。
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
     * ドロップに成功した値 を返します。
     * @return ドロップに成功した値
     */
    public Object[] getDropSuccessValues() {
        return dropSuccessValues;
    }

    /**
     * ドロップに成功した値 を設定します。
     * @param dropSuccessValues ドロップに成功した値
     */
    public void setDropSuccessValues(Object[] dropSuccessValues) {
        this.dropSuccessValues = dropSuccessValues;
    }

    /**
     * データ搬送体 を返します。
     * @return データ搬送体
     */
    public Transferable getTransfer() {
        return transfer;
    }

    /**
     * データ搬送体 を設定します。
     * @param transfer データ搬送体
     */
    public void setTransfer(Transferable transfer) {
        this.transfer = transfer;
    }

    /**
     * ドロップ規定イベント情報 を返します。
     * 
     * @return ドロップ規定イベント情報
     */
    public DropTargetDropEvent getDropTargetDropEvent() {
        return dropTargetDropEvent;
    }

    /**
     * ドロップ規定イベント情報 を設定します。
     * 
     * @param dropTargetDropEvent ドロップ規定イベント情報
     */
    public void setDropTargetDropEvent(DropTargetDropEvent dropTargetDropEvent) {
        this.dropTargetDropEvent = dropTargetDropEvent;
    }

    /**
     * ドロップを要求した値 を返します。
     * 
     * @return ドロップを要求した値
     */
    public Object[] getDropRequestValues() {
        return dropRequestValues;
    }

    /**
     * ドロップを要求した値 を設定します。
     * 
     * @param dropRequestValues ドロップを要求した値
     */
    public void setDropRequestValues(Object[] dropRequestValues) {
        this.dropRequestValues = dropRequestValues;
    }

    /**
     * コンストラクタです。
     * 
     * @param source イベント発生元
     */
    public ACDropEvent(Object source) {
        super(source);
    }

    /**
     * コンストラクタです。
     * 
     * @param source イベント発生元
     * @param dropTargetDropEvent ドロップ規定イベント情報
     * @param dropValue ドロップした値
     */
    public ACDropEvent(Object source, DropTargetDropEvent dropTargetDropEvent,
            Object[] dropValue) {
        super(source);
        setDropTargetDropEvent(dropTargetDropEvent);
        setDropRequestValues(dropValue);
    }

}
