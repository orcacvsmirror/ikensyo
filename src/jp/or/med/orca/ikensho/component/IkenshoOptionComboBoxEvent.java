package jp.or.med.orca.ikensho.component;

import java.util.EventObject;

/**
 * 
 * IkenshoOptionComboBoxEvent�ł��B
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
     * �R���X�g���N�^�ł��B
     * @param source
     * @param optionItem
     */
    public IkenshoOptionComboBoxEvent(Object source, Object optionItem) {
        super(source);
        this.optionItem = optionItem;
    }

    /**
     * ���ڂ�Ԃ��܂��B
     * @return �I�v�V��������
     */
    public Object getOptionItem() {
        return optionItem;
    }
}