package jp.nichicom.ac.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;

/**
 * ���X�g���f���p�̃A�_�v�^�ł��B
 * <p>
 * �R���e�i��add���\�b�h���[�Ԃ��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRListModelAdapter
 */
public class ACListModelAdapter extends VRListModelAdapter {
    /**
     * �R���X�g���N�^�ł��B
     */
    public ACListModelAdapter() {
        super(new VRArrayList());
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
     */
    public ACListModelAdapter(VRBindSource adaptee) {
        super(adaptee);
    }

    /**
     * ���ڂ�ǉ����܂��B
     * 
     * @param data ����
     * @param dummy �_�~�[����
     */
    public void add(Object data, Object dummy) {
        add(data);
    }

    /**
     * ���ڂ�ǉ����܂��B
     * 
     * @param data ����
     */
    public void add(Object data) {
        super.addData(data);
    }

}
