package jp.nichicom.ac.component.table.event;

import java.awt.event.ActionEvent;
import java.util.EventListener;

/**
 * チェックボックステーブルカラム用ポップアップメニューのイベントリスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see EventListener
 */

public interface ACCheckBoxTableColumnPopupMenuListener extends EventListener {

    /**
     * ソートメニューのクリックイベントです。
     * 
     * @param e イベント情報
     */
    public void sortMenuActionPerformed(ActionEvent e);

    /**
     * 全選択メニューのクリックイベントです。
     * 
     * @param e イベント情報
     */
    public void allCheckMenuActionPerformed(ActionEvent e);

    /**
     * 全解除メニューのクリックイベントです。
     * 
     * @param e イベント情報
     */
    public void allUncheckMenuActionPerformed(ActionEvent e);

    /**
     * 選択反転メニューのクリックイベントです。
     * 
     * @param e イベント情報
     */
    public void reverseCheckMenuActionPerformed(ActionEvent e);

    /**
     * キャンセルメニューのクリックイベントです。
     * 
     * @param e イベント情報
     */
    public void cancelMenuActionPerformed(ActionEvent e);
}
