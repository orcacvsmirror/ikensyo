package jp.nichicom.ac.component.table.event;

import java.awt.event.ActionEvent;
import java.util.EventListener;

/**
 * �`�F�b�N�{�b�N�X�e�[�u���J�����p�|�b�v�A�b�v���j���[�̃C�x���g���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see EventListener
 */

public interface ACCheckBoxTableColumnPopupMenuListener extends EventListener {

    /**
     * �\�[�g���j���[�̃N���b�N�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void sortMenuActionPerformed(ActionEvent e);

    /**
     * �S�I�����j���[�̃N���b�N�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void allCheckMenuActionPerformed(ActionEvent e);

    /**
     * �S�������j���[�̃N���b�N�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void allUncheckMenuActionPerformed(ActionEvent e);

    /**
     * �I�𔽓]���j���[�̃N���b�N�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void reverseCheckMenuActionPerformed(ActionEvent e);

    /**
     * �L�����Z�����j���[�̃N���b�N�C�x���g�ł��B
     * 
     * @param e �C�x���g���
     */
    public void cancelMenuActionPerformed(ActionEvent e);
}
