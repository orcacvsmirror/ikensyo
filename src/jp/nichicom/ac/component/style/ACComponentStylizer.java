package jp.nichicom.ac.component.style;

import java.awt.Component;


/**
 * �R���|�[�l���g�̃X�^�C����ύX����@�\��L���邱�Ƃ�����킷�C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACComponentStylizeCondition
 */
public interface ACComponentStylizer {

    /**
     * �R���|�[�l���g�ɃX�^�C����K�p���܂��B
     * @param comp �R���|�[�l���g
     * @param value �l
     * @return �X�^�C���K�p��̃R���|�[�l���g
     */
    public Component stylize(Component comp, Object value);
    
}
