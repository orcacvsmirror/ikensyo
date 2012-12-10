package jp.nichicom.ac.component.table;

import java.awt.Component;

/**
 * セル単位にテーブル設定を変更できる数r表示セル単位の表示設定です。
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
     * コンストラクタです。
     * <p>
     * セルレンダラ未指定、セルエディタ未指定、編集不可、選択色を適用する設定で生成します。
     * </p>
     */
    public ACTableCellViewerCustomCell() {

    }

    /**
     * コンストラクタです。
     * <p>
     * セルエディタ未指定、編集不可、選択色を適用する設定で生成します。
     * </p>
     * 
     * @param renderer
     */
    public ACTableCellViewerCustomCell(Component renderer) {
        setRenderer(renderer);

    }

    /**
     * コンストラクタです。
     * 
     * @param renderer セルレンダラとして使用するコンポーネント
     * @param editor セルエディタとして使用するコンポーネント
     * @param editable 編集可能か
     * @param ignoreSelectColor セル選択時に選択色への着色を無視するか
     */
    public ACTableCellViewerCustomCell(Component renderer, Component editor,
            boolean editable, boolean ignoreSelectColor) {
        setRenderer(renderer);
        setEditor(editor);
        setEditable(editable);
        setIgnoreSelectColor(ignoreSelectColor);
    }

    /**
     * 編集可能か を返します。
     * 
     * @return 編集可能か
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * 編集可能か を設定します。
     * 
     * @param editable 編集可能か
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * セルエディタとして使用するコンポーネント を返します。
     * 
     * @return セルエディタとして使用するコンポーネント
     */
    public Component getEditor() {
        return editor;
    }

    /**
     * セルエディタとして使用するコンポーネント を設定します。
     * 
     * @param editor セルエディタとして使用するコンポーネント
     */
    public void setEditor(Component editor) {
        this.editor = editor;
    }

    /**
     * セル選択時に選択色への着色を無視するか を返します。
     * 
     * @return セル選択時に選択色への着色を無視するか
     */
    public boolean isIgnoreSelectColor() {
        return ignoreSelectColor;
    }

    /**
     * セル選択時に選択色への着色を無視するか を設定します。
     * 
     * @param ignoreSelectColor セル選択時に選択色への着色を無視するか
     */
    public void setIgnoreSelectColor(boolean ignoreSelectColor) {
        this.ignoreSelectColor = ignoreSelectColor;
    }

    /**
     * セルレンダラとして使用するコンポーネント を返します。
     * 
     * @return セルレンダラとして使用するコンポーネント
     */
    public Component getRenderer() {
        return renderer;
    }

    /**
     * セルレンダラとして使用するコンポーネント を設定します。
     * 
     * @param renderer セルレンダラとして使用するコンポーネント
     */
    public void setRenderer(Component renderer) {
        this.renderer = renderer;
    }

}
