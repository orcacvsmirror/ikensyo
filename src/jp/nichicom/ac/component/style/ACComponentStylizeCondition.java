package jp.nichicom.ac.component.style;


/**
 * �R���|�[�l���g�̃X�^�C����ύX�\�����f��������ł��邱�Ƃ�����킷�C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public interface ACComponentStylizeCondition {
    /**
     * �X�^�C���ύX��������l�ł��邩�𔻒肵�ĕԂ��܂��B
     * 
     * @param value �����l
     * @return �X�^�C���ύX��������l�ł��邩
     */
    public boolean isMatch(Object value);

}
