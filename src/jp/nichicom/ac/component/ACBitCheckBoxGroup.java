package jp.nichicom.ac.component;

import java.text.ParseException;

import javax.swing.JCheckBox;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.component.VRCheckBoxGroup;

/**
 * �r�b�g�t���O�Œl���Ǘ�����`�F�b�N�{�b�N�X�O���[�v�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRCheckBoxGroup
 */
public class ACBitCheckBoxGroup extends VRCheckBoxGroup {
    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), new Integer(
                getSelectedBits()))) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindSource() throws ParseException {
        Object obj = VRBindPathParser.get(getBindPath(), source);

        int val;
        if (obj == null) {
            return;
        } else if (obj instanceof Integer) {
            val = ((Integer) obj).intValue();
        } else {
            val = Integer.parseInt(String.valueOf(obj));
        }

        setSelectedBits(val);
        fireBindSource(new VRBindEvent(this));

    }

    public Object createSource() {
        return new Integer(0);
    }

    /**
     * �I����Ԃ��r�b�g�t���O�ŕ\�������ꍇ��OR�l��Ԃ��܂��B
     * 
     * @return �r�b�g�t���OOR
     */
    public int getSelectedBits() {
        int value = 0;
        int bit = 1;
        int end = getCheckBoxCount();
        for (int i = 0; i < end; i++) {
            JCheckBox check = getCheckBox(i);
            if (check.isSelected()) {
                value += bit;
            }
            bit = bit << 1;
        }
        return value;
    }

    /**
     * �I����Ԃ��r�b�g�t���O�ŕ\�������ꍇ��OR�l�Őݒ肵�܂��B
     * 
     * @param select �r�b�g�t���OOR
     */
    public void setSelectedBits(int select) {
        int i;
        int bit = 1;
        int end = getCheckBoxCount();
        for (i = 0; i < end; i++) {
            if (bit > select) {
                break;
            }

            JCheckBox check = getCheckBox(i);
            check.setSelected((select & bit) != 0);

            bit = bit << 1;
        }
        if (i < end) {
            // �r�b�g�̂ق�����ɐs�������c��̃`�F�b�N�͑S�Ă͂���
            for (; i < end; i++) {
                JCheckBox check = getCheckBox(i);
                check.setSelected(false);
            }
        }
    }

    protected JCheckBox createItem() {
        return new ACCheckBox();
    }

}
