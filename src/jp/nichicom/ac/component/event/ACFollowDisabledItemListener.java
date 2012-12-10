package jp.nichicom.ac.component.event;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;

/**
 * イベント発生元の選択状態に追随して関連コンポーネントの有効状態を変更するアイテムリスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ItemListener
 */

public class ACFollowDisabledItemListener implements ItemListener {
    private JComponent[] disableTargets;

    /**
     * コンストラクタです。
     * 
     * @param disableTargets 選択を解除したときに無効にするコンポーネント
     */
    public ACFollowDisabledItemListener(JComponent[] disableTargets) {
        this.disableTargets = disableTargets;
    }

    public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED) {
            select = true;
        } else if (e.getStateChange() == ItemEvent.DESELECTED) {
            select = false;
        } else {
            return;
        }
        if (disableTargets != null) {
            int end = disableTargets.length;
            for (int i = 0; i < end; i++) {
                disableTargets[i].setEnabled(select);
            }
        }
    }

}
