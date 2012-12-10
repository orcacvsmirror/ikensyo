package jp.nichicom.ac.component.table;

import java.awt.Component;

/**
 * �Z���P�ʂɃe�[�u���ݒ��ύX�ł��鐔r�\���Z���P�ʂ̕\���ݒ�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public class ACTableCellViewerCustomCell {
    private Component editor;
    private Component renderer;
    private boolean ignoreSelectColor = false;
    private boolean editable = false;

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �Z�������_�����w��A�Z���G�f�B�^���w��A�ҏW�s�A�I��F��K�p����ݒ�Ő������܂��B
     * </p>
     */
    public ACTableCellViewerCustomCell() {

    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �Z���G�f�B�^���w��A�ҏW�s�A�I��F��K�p����ݒ�Ő������܂��B
     * </p>
     * 
     * @param renderer
     */
    public ACTableCellViewerCustomCell(Component renderer) {
        setRenderer(renderer);

    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param renderer �Z�������_���Ƃ��Ďg�p����R���|�[�l���g
     * @param editor �Z���G�f�B�^�Ƃ��Ďg�p����R���|�[�l���g
     * @param editable �ҏW�\��
     * @param ignoreSelectColor �Z���I�����ɑI��F�ւ̒��F�𖳎����邩
     */
    public ACTableCellViewerCustomCell(Component renderer, Component editor,
            boolean editable, boolean ignoreSelectColor) {
        setRenderer(renderer);
        setEditor(editor);
        setEditable(editable);
        setIgnoreSelectColor(ignoreSelectColor);
    }

    /**
     * �ҏW�\�� ��Ԃ��܂��B
     * 
     * @return �ҏW�\��
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * �ҏW�\�� ��ݒ肵�܂��B
     * 
     * @param editable �ҏW�\��
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * �Z���G�f�B�^�Ƃ��Ďg�p����R���|�[�l���g ��Ԃ��܂��B
     * 
     * @return �Z���G�f�B�^�Ƃ��Ďg�p����R���|�[�l���g
     */
    public Component getEditor() {
        return editor;
    }

    /**
     * �Z���G�f�B�^�Ƃ��Ďg�p����R���|�[�l���g ��ݒ肵�܂��B
     * 
     * @param editor �Z���G�f�B�^�Ƃ��Ďg�p����R���|�[�l���g
     */
    public void setEditor(Component editor) {
        this.editor = editor;
    }

    /**
     * �Z���I�����ɑI��F�ւ̒��F�𖳎����邩 ��Ԃ��܂��B
     * 
     * @return �Z���I�����ɑI��F�ւ̒��F�𖳎����邩
     */
    public boolean isIgnoreSelectColor() {
        return ignoreSelectColor;
    }

    /**
     * �Z���I�����ɑI��F�ւ̒��F�𖳎����邩 ��ݒ肵�܂��B
     * 
     * @param ignoreSelectColor �Z���I�����ɑI��F�ւ̒��F�𖳎����邩
     */
    public void setIgnoreSelectColor(boolean ignoreSelectColor) {
        this.ignoreSelectColor = ignoreSelectColor;
    }

    /**
     * �Z�������_���Ƃ��Ďg�p����R���|�[�l���g ��Ԃ��܂��B
     * 
     * @return �Z�������_���Ƃ��Ďg�p����R���|�[�l���g
     */
    public Component getRenderer() {
        return renderer;
    }

    /**
     * �Z�������_���Ƃ��Ďg�p����R���|�[�l���g ��ݒ肵�܂��B
     * 
     * @param renderer �Z�������_���Ƃ��Ďg�p����R���|�[�l���g
     */
    public void setRenderer(Component renderer) {
        this.renderer = renderer;
    }

}
