package jp.nichicom.ac.core;

/**
 * �v���O�����N�����O�A�l���{�����O���o�͂̃C���^�[�t�F�[�X�B
 * <p>
 * ��Ƃ��āAACAffairable�C���^�[�t�F�[�X�Ƃ��킹�ăV�X�e���C�x���g�����N���X�Ɏ������܂��B
 * </p>
 * <p>
 * Copyright (c) 2012 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2012/09/19
 * @see ACAffairable
 * @see ACFrame
 */
public interface ACBrowseLogWritable {
	
	/**
	 * �v���O�����N���A�l���{�����O���o�͂���
	 * @param affair ����ʏ��
	 */
	public void writeBrowseLog(ACAffairInfo affair);

}
