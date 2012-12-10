package jp.nichicom.ac.component;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/**
 * リスト形式のバインドソースを対象としたセルレンダラです。
 * <p>
 * レンダーバインドパスを設定することで、バインドソース本体ではなく、任意のパスに対応した値を描画させることができます。
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
     * コンストラクタです。
     */
    public ACBindListCellRenderer() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param renderBindPath 描画対象とするバインドパス
     */
    public ACBindListCellRenderer(String renderBindPath) {
        super();
        setRenderBindPath(renderBindPath);
    }

    /**
     * 描画対象とするフィールド名を返します。
     * 
     * @return 描画対象とするフィールド名
     */
    public String getRenderBindPath() {
        return renderBindPath;
    }

    /**
     * 描画対象とするフィールド名を設定します。
     * 
     * @param renderBindPath 描画対象とするバインドパス
     */
    public void setRenderBindPath(String renderBindPath) {
        this.renderBindPath = renderBindPath;
    }

    /**
     * 値を解析してプリミティブラッパーとして返します。
     * 
     * @param value 解析対象
     * @return 解析結果
     */
    public Object toPremitive(Object value) {
        if (value instanceof VRMap) {
            VRMap map = (VRMap) value;
            try {
                if (VRBindPathParser.has(getRenderBindPath(), map)) {
                    value = VRBindPathParser.get(getRenderBindPath(), map);
                }
            } catch (Exception ex) {
                // 失敗した場合、valueは無変更
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
