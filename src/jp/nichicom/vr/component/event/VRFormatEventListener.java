/** TODO <HEAD> */
package jp.nichicom.vr.component.event;

import java.util.EventListener;


/**
 * �t�H�[�}�b�g�Ɋ֘A����C�x���g���X�i�ł��B
 * <p>
 * ���͒l�̃t�H�[�}�b�g�ɑΉ������R���g���[�����֘A�C�x���g�𔭐������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRFormatEvent
 */
public interface VRFormatEventListener extends EventListener{

    /**
     * �t�H�[�}�b�g�Ɏ��s�����ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void formatInvalid(VRFormatEvent e);

    /**
     * �t�H�[�}�b�g�ɐ��������ۂɌĂ΂��C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    void formatValid(VRFormatEvent e);
}