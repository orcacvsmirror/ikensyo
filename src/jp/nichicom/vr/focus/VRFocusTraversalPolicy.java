/** TODO <HEAD> */
package jp.nichicom.vr.focus;

import java.awt.Component;
import java.awt.Container;

import javax.swing.LayoutFocusTraversalPolicy;

import jp.nichicom.vr.component.VRRadioButtonGroup;

/**
 * ���W�I�O���[�v���̏����t�H�[�J�X��������������t�H�[�J�X����N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/10/31
 * @see LayoutFocusTraversalPolicy
 * @see VRRadioButtonGroup
 */
public class VRFocusTraversalPolicy extends LayoutFocusTraversalPolicy {
    public Component getComponentAfter(Container focusCycleRoot,
            Component aComponent) {
        Component c = super.getComponentAfter(focusCycleRoot, aComponent);
        if (c != null) {

            if (c.getParent() instanceof VRRadioButtonGroup) {
                VRRadioButtonGroup g = (VRRadioButtonGroup) c.getParent();
                if (!(aComponent.getParent() instanceof VRRadioButtonGroup)) {
                    if (g.getSelectedIndex() > 0) {
                        return g.getSelectedButton();
                    } else {
                        return c;
                    }
                } else {
                    return super.getComponentAfter(focusCycleRoot, g
                            .getLastButton());
                }
            }
        }
        return c;
    }

    public Component getComponentBefore(Container focusCycleRoot,
            Component aComponent) {
        return super.getComponentBefore(focusCycleRoot, aComponent);
    }

    public Component getDefaultComponent(Container focusCycleRoot) {
        return super.getDefaultComponent(focusCycleRoot);
    }

    public Component getFirstComponent(Container focusCycleRoot) {
        return super.getFirstComponent(focusCycleRoot);
    }

    public Component getLastComponent(Container focusCycleRoot) {
        return super.getLastComponent(focusCycleRoot);
    }
}