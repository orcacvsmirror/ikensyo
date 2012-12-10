package jp.nichicom.ac.component.event;

import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * イベント発生元の選択状態に追随して関連コンポーネントの有効状態を変更するリスト選択リスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ListSelectionListener
 */
public class ACFollowDisableSelectionListener implements ListSelectionListener {
    private JComponent[] disableTargets;
    private int enabledTriggerIndex;

    /**
     * コンストラクタです。
     * 
     * @param disableTargets インデックスenabledTriggerIndex番を選択したときに無効にするコンポーネント
     */
    public ACFollowDisableSelectionListener(JComponent[] disableTargets) {
        this.disableTargets = disableTargets;
        enabledTriggerIndex = 0;
    }

    /**
     * コンストラクタです。
     * 
     * @param disableTargets インデックスenabledTriggerIndex番を選択したときに有効にするコンポーネント
     * @param enabledTriggerIndex 有効トリガとなるインデックス
     */
    public ACFollowDisableSelectionListener(JComponent[] disableTargets,
            int enabledTriggerIndex) {
        this.disableTargets = disableTargets;
        this.enabledTriggerIndex = enabledTriggerIndex;
    }

    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }
        if (disableTargets != null) {
            boolean enabled = e.getFirstIndex() == enabledTriggerIndex;
            int end = disableTargets.length;
            for (int i = 0; i < end; i++) {
                disableTargets[i].setEnabled(enabled);
            }
        }
    }

}
