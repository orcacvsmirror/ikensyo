package jp.nichicom.ac.core;

/**
 * �Ɩ��u���C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACAffairReplacable {
    /**
     * �G�f�B�V�������ɉ����āA�K�؂ȋƖ��N���X����Ԃ��܂��B
     * 
     * @param className �����Ώۂ̋Ɩ��N���X��
     * @return �K�؂ȋƖ��N���X��
     */
    String getValidClassName(String className);

}
