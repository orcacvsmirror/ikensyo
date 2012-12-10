/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventListener;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;

/**
 * �o�C���h�@�\�Ɋ֘A����C�x���g���X�i�ł��B
 * <p>
 * �o�C���h�@�\�ɑΉ������R���g���[�����֘A�C�x���g�𔭐������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 * @see VRBindable
 */
public interface VRBindEventListener extends EventListener{

    /**
     * �R���g���[�����\�[�X�ɒl��K�p(apply)�����ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void applySource(VRBindEvent e);

    /**
     * �\�[�X���R���g���[����bind�����ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void bindSource(VRBindEvent e);

}