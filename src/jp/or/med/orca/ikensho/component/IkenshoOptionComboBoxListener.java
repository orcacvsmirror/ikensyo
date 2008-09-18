package jp.or.med.orca.ikensho.component;

import java.util.EventListener;

/**
 * 
 * IkenshoOptionComboBoxListenerです。
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public interface IkenshoOptionComboBoxListener extends EventListener {
    /**
     * オプション項目を取得します。
     * @param e イベント発生元
     */
    public void optionSelected(IkenshoOptionComboBoxEvent e);
    
}
