package jp.nichicom.ac.component;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/**
 * ���X�g�`���̃o�C���h�\�[�X��ΏۂƂ����Z�������_���ł��B
 * <p>
 * �����_�[�o�C���h�p�X��ݒ肷�邱�ƂŁA�o�C���h�\�[�X�{�̂ł͂Ȃ��A�C�ӂ̃p�X�ɑΉ������l��`�悳���邱�Ƃ��ł��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see DefaultListCellRenderer
 */
public class ACBindListCellRenderer extends DefaultListCellRenderer {
    protected String renderBindPath;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACBindListCellRenderer() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param renderBindPath �`��ΏۂƂ���o�C���h�p�X
     */
    public ACBindListCellRenderer(String renderBindPath) {
        super();
        setRenderBindPath(renderBindPath);
    }

    /**
     * �`��ΏۂƂ���t�B�[���h����Ԃ��܂��B
     * 
     * @return �`��ΏۂƂ���t�B�[���h��
     */
    public String getRenderBindPath() {
        return renderBindPath;
    }

    /**
     * �`��ΏۂƂ���t�B�[���h����ݒ肵�܂��B
     * 
     * @param renderBindPath �`��ΏۂƂ���o�C���h�p�X
     */
    public void setRenderBindPath(String renderBindPath) {
        this.renderBindPath = renderBindPath;
    }

    /**
     * �l����͂��ăv���~�e�B�u���b�p�[�Ƃ��ĕԂ��܂��B
     * 
     * @param value ��͑Ώ�
     * @return ��͌���
     */
    public Object toPremitive(Object value) {
        if (value instanceof VRMap) {
            VRMap map = (VRMap) value;
            try {
                if (VRBindPathParser.has(getRenderBindPath(), map)) {
                    value = VRBindPathParser.get(getRenderBindPath(), map);
                }
            } catch (Exception ex) {
                // ���s�����ꍇ�Avalue�͖��ύX
            }
            // value = ( (VRHashMap) value).getData(getField());
        }
        return value;
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        value = toPremitive(value);

        if ("".equals(value)) {
            value = " ";
        }

        return super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);
    }
}
