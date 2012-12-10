/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventListener;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindSource;

/**
 * ���f���o�C���h�@�\�Ɋ֘A����C�x���g���X�i�ł��B
 * <p>
 * ���f���o�C���h�@�\�ɑΉ������R���g���[�����֘A�C�x���g�𔭐������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindSource
 * @see VRBindModelable
 */
public interface VRBindModelEventListener extends EventListener {

    /**
     * �R���g���[�����\�[�X�ɒl��K�p(apply)�����ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void applyModelSource(VRBindModelEvent e);

    /**
     * �\�[�X���R���g���[����bind�����ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void bindModelSource(VRBindModelEvent e);
}