package jp.or.med.orca.ikensho.component;

import java.util.EventObject;

/**
 * 
 * IkenshoOptionComboBoxEventです。
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public class IkenshoOptionComboBoxEvent extends EventObject {

    private Object optionItem;

    /**
     * 
     * コンストラクタです。
     * @param source
     * @param optionItem
     */
    public IkenshoOptionComboBoxEvent(Object source, Object optionItem) {
        super(source);
        this.optionItem = optionItem;
    }

    /**
     * 項目を返します。
     * @return オプション項目
     */
    public Object getOptionItem() {
        return optionItem;
    }
}