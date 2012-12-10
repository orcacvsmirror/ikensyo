/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventListener;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * �o�C���h�p�̃f�[�^�Ɋ֘A����C�x���g���X�i�ł��B
 * <p>
 * �o�C���h�p�̃f�[�^�ɑ΂��čs�Ȃ�������ɑΉ�����C�x���g���ʒm����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 * @see VRBindSourceEvent
 */
public interface VRBindSourceEventListener extends EventListener{

    /**
     * �f�[�^���ǉ����ꂽ�ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void addSource(VRBindSourceEvent e);

    /**
     * �f�[�^�ɒl���ݒ肳�ꂽ�ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void changeSource(VRBindSourceEvent e);

    /**
     * �f�[�^�ɏ��������ꂽ�ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void clearSource(VRBindSourceEvent e);

    /**
     * �f�[�^���폜���ꂽ�ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void removeSource(VRBindSourceEvent e);
}