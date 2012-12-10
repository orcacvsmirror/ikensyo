package jp.nichicom.ac.container;

import jp.nichicom.vr.container.VRLabelContainer;

/**
 * �w�ʂɏd�˂�p�r��z�肵�����x���R���e�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRLabelContainer
 */

public class ACBackLabelContainer extends ACLabelContainer {
    /**
     * �R���X�g���N�^�ł��B
     */
    public ACBackLabelContainer() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ��������O
     */
    private void jbInit() throws Exception {
        this.setContentAreaFilled(true);
        this.setFocusBackground(new java.awt.Color(204, 204, 255));
    }

}
