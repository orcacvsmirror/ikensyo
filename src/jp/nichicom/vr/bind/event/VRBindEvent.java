/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventObject;

import jp.nichicom.vr.bind.VRBindable;

/**
 * �o�C���h�R���g���[���Ɋ֘A����C�x���g�I�u�W�F�N�g�ł��B
 * <p>
 * �o�C���h�R���g���[���ɑ΂��čs�Ȃ�������ɑΉ�����C�x���g�����i�[���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindable
 * @see VRBindEventListener
 */
public class VRBindEvent extends EventObject {

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param source �C�x���g������
     */
    public VRBindEvent(Object source) {
        super(source);
    }

    /**
     * �C�x���g��������ݒ肵�܂��B
     * 
     * @param source �C�x���g������
     */
    public void setSource(Object source) {
        this.source = source;
    }

}
