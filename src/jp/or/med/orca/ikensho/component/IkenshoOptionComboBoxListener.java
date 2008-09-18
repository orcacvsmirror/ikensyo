package jp.or.med.orca.ikensho.component;

import java.util.EventListener;

/**
 * 
 * IkenshoOptionComboBoxListener�ł��B
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public interface IkenshoOptionComboBoxListener extends EventListener {
    /**
     * �I�v�V�������ڂ��擾���܂��B
     * @param e �C�x���g������
     */
    public void optionSelected(IkenshoOptionComboBoxEvent e);
    
}
