/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 * VR Look&Feel�ɂ�����R���{�|�b�v�A�b�vUI�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRComboPopup extends BasicComboPopup {

    /**
     * �R���X�g���N�^
     * @param combo �R���{�{�b�N�X
     */
    public VRComboPopup(JComboBox combo) {
        super(combo);
    }

    protected void configureList() {
        super.configureList();
        list.setBackground(VRDraw.blend(VRLookAndFeel.getTextFocusBackground(), comboBox.getBackground(), 0.5));
    }
}