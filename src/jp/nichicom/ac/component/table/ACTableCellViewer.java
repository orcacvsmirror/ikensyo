package jp.nichicom.ac.component.table;

import java.awt.Component;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACStateIconLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableCellViewerDelegate;

/**
 * セル単位の表示変更や連番にも対応したセルビューワです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTableCellViewer
 */

public class ACTableCellViewer extends VRTableCellViewer {
    /**
     * ハッシュアイコン形式を表すレンダラ定数です。
     */
    public static final String EDITOR_TYPE_ICON = "Icon";

    /**
     * 連番形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_SERIAL_NO = "SerialNo";
    /**
     * 状態アイコン形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_STATE_ICON = "StateIcon";
    /**
     * トグルボタン形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_TOGGLE_BUTTON = "ToggleButton";
    /**
     * ハッシュアイコン形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_ICON = "Icon";
    /**
     * 連番形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_SERIAL_NO = "SerialNo";
    /**
     * 状態アイコン形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_STATE_ICON = "StateIcon";
    /**
     * トグルボタン形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_TOGGLE_BUTTON = "ToggleButton";

    /**
     * セルエディタをあらわす取得定数です。
     */
    protected static final int GET_TYPE_EDITOR = 1;
    /**
     * セルレンダラをあらわす取得定数です。
     */
    protected static final int GET_TYPE_RENDERER = 0;
    private HashMap customCellEditorCache = new HashMap();

    private HashMap customCellRendererCache = new HashMap();

    private List customCells;

    /**
     * コンストラクタです。
     */
    public ACTableCellViewer() {
    }

    /**
     * カスタム行レンダラ・エディタ用のコンポーネントキャッシュをクリアします。
     */
    public void clearCustomCellCache() {
        getCustomCellRendererCache().clear();
    }

    public VRTableCellViewerDelegate createEditorDelegate(final String type) {
        if (VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD.equals(type)) {
            return createEditorDelegate(new ACTextField());
        } else if (VRTableCellViewer.EDITOR_TYPE_LABEL.equals(type)) {
            ACLabel lbl = new ACLabel();
            lbl.setMargin(new Insets(0, 2, 0, 4));
            return createEditorDelegate(lbl);
        } else if (VRTableCellViewer.EDITOR_TYPE_CHECK_BOX.equals(type)) {
            return createEditorDelegate(new ACCheckBox());
        } else if (VRTableCellViewer.EDITOR_TYPE_COMBO_BOX.equals(type)) {
            return createEditorDelegate(new ACComboBox());
        } else if (ACTableCellViewer.EDITOR_TYPE_TOGGLE_BUTTON.equals(type)) {
            return createEditorDelegate(new JToggleButton());
        } else if (ACTableCellViewer.EDITOR_TYPE_SERIAL_NO.equals(type)) {
            return createSerialEditorDelegate();
        } else if (ACTableCellViewer.EDITOR_TYPE_STATE_ICON.equals(type)) {
            return createStateIconEditorDelegate();
        } else if (ACTableCellViewer.EDITOR_TYPE_ICON.equals(type)) {
            return createIconEditorDelegate();
        }
        return super.createEditorDelegate(type);
    }

    public VRTableCellViewerDelegate createRendererDelegate(final String type) {
        if (VRTableCellViewer.RENDERER_TYPE_TEXT_FIELD.equals(type)) {
            return createEditorDelegate(new ACTextField());
        } else if (VRTableCellViewer.RENDERER_TYPE_LABEL.equals(type)) {
            ACLabel lbl = new ACLabel();
            lbl.setMargin(new Insets(0, 2, 0, 2));
            return createEditorDelegate(lbl);
        } else if (VRTableCellViewer.RENDERER_TYPE_CHECK_BOX.equals(type)) {
            return createEditorDelegate(new ACCheckBox());
        } else if (VRTableCellViewer.RENDERER_TYPE_COMBO_BOX.equals(type)) {
            return createEditorDelegate(new ACComboBox());
        } else if (ACTableCellViewer.RENDERER_TYPE_TOGGLE_BUTTON.equals(type)) {
            return createEditorDelegate(new JToggleButton());
        } else if (ACTableCellViewer.RENDERER_TYPE_SERIAL_NO.equals(type)) {
            return createSerialEditorDelegate();
        } else if (ACTableCellViewer.RENDERER_TYPE_STATE_ICON.equals(type)) {
            return createStateIconEditorDelegate();
        } else if (ACTableCellViewer.RENDERER_TYPE_ICON.equals(type)) {
            return createIconEditorDelegate();
        }
        return super.createRendererDelegate(type);
    }

    /**
     * 指定行のカスタムセル情報を返します。
     * <p>
     * 設定済みのカスタムセル数よりも指定行が大きい場合、nullを返します。
     * </p>
     * 
     * @param row 行番号
     * @return カスタムセル情報
     */
    public ACTableCellViewerCustomCell getCustomCell(int row) {
        List cells = getCustomCells();
        if ((cells != null) && (cells.size() > row)) {
            // カスタム行エディタを設定している場合
            Object obj = cells.get(row);
            if (obj instanceof ACTableCellViewerCustomCell) {
                return (ACTableCellViewerCustomCell) obj;
            }
        }
        return null;
    }

    /**
     * カスタムセル設定集合 を返します。
     * <p>
     * <code>NCTableCellViewerCustomCell</code>を連ねたListもしくはnullが返ります。
     * </p>
     * 
     * @return カスタムセル設定
     */
    public List getCustomCells() {
        return customCells;
    }

    /**
     * エディタ用の描画対象のバインドパスを返します。
     * <p>
     * エディタが<code>ACComboBox</code>を継承している場合に有効です。
     * </p>
     * 
     * @return 描画対象のバインドパス
     */
    public String getEditorRenderBindPath() {
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                return ((ACComboBox) cmp).getRenderBindPath();
            }
        }
        return null;
    }

    /**
     * レンダラ用の描画対象のバインドパスを返します。
     * <p>
     * レンダラが<code>ACComboBox</code>を継承している場合に有効です。
     * </p>
     * 
     * @return 描画対象のバインドパス
     */
    public String getRendererRenderBindPath() {
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                return ((ACComboBox) cmp).getRenderBindPath();
            }
        }
        return null;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        ACTableCellViewerCustomCell cell = getCustomCell(row);
        if ((cell != null) && (!cell.isEditable())) {
            return null;
        }
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, column,
                cell, ACTableCellViewer.GET_TYPE_EDITOR);
        Component cmp;
        if (ed instanceof VRTableCellViewerDelegate) {
            // デリゲートが得られた場合は優先して使用する。
            delegate = ed;
            cmp = ed.getComponent();
        } else {
            cmp = super.getTableCellEditorComponent(table, value, isSelected,
                    row, column);
        }
        return cmp;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        ACTableCellViewerCustomCell cell = getCustomCell(row);
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, column,
                cell, ACTableCellViewer.GET_TYPE_RENDERER);
        if (ed instanceof VRTableCellViewerDelegate) {
            // デリゲートが得られた場合は優先して使用する。
            Component cmp = ed.getComponent();
            if (!cell.isIgnoreSelectColor()) {
                // 着色を無視しないならば選択色を反映
                // Color fore = cmp.getForeground();
                // Color back = cmp.getBackground();
                applyTableCellColor(table, value, isSelected, hasFocus, row,
                        column, cmp);
                // if (fore != null) {
                // cmp.setForeground(fore);
                // }
                // if (back != null) {
                // cmp.setBackground(back);
                // }
            }
            return cmp;
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
            boolean isSelected, boolean expanded, boolean leaf, int row) {
        ACTableCellViewerCustomCell cell = getCustomCell(row);
        if ((cell != null) && (!cell.isEditable())) {
            return null;
        }
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, 0,
                cell, ACTableCellViewer.GET_TYPE_EDITOR);
        if (ed instanceof VRTableCellViewerDelegate) {
            delegate = ed;
            return ed.getComponent();
        }

        return super.getTreeCellEditorComponent(tree, value, isSelected,
                expanded, leaf, row);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, 0,
                getCustomCell(row), ACTableCellViewer.GET_TYPE_RENDERER);
        if (ed instanceof VRTableCellViewerDelegate) {
            return ed.getComponent();
        }

        return super.getTreeCellRendererComponent(tree, value, selected,
                expanded, leaf, row, hasFocus);
    }

    /**
     * カスタムセル設定集合 を設定します。
     * <p>
     * <code>NCTableCellViewerCustomCell</code>を連ねたListを設定します。
     * </p>
     * 
     * @param customCells カスタムセル設定集合
     */
    public void setCustomCells(List customCells) {
        this.customCells = customCells;
        clearCustomCellCache();
    }
    /**
     * エディタ用の描画対象のバインドパスを設定します。
     * <p>
     * エディタが<code>ACComboBox</code>を継承している場合に有効です。
     * </p>
     * 
     * @param renderBindPath 描画対象のバインドパス
     */
    public void setEditorRenderBindPath(String renderBindPath) {
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                ((ACComboBox) cmp).setRenderBindPath(renderBindPath);
            }
        }
    }

    /**
     * レンダラ用の描画対象のバインドパスを設定します。
     * <p>
     * レンダラが<code>ACComboBox</code>を継承している場合に有効です。
     * </p>
     * 
     * @param renderBindPath 描画対象のバインドパス
     */
    public void setRendererRenderBindPath(String renderBindPath) {
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                ((ACComboBox) cmp).setRenderBindPath(renderBindPath);
            }
        }
    }

    /**
     * アイコン型エディタを生成して返します。
     * 
     * @return アイコン型エディタ
     */
    protected VRTableCellViewerDelegate createIconEditorDelegate() {
        final ACLabel component = new ACLabel();
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {
            public Object getValue() {
                return parseValue(component.getIconPath());
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValue(Object value, int row, int column) {
                value = formatValue(value);
                if (value == null) {
                    component.setIconPath(null);
                } else {
                    component.setIconPath(String.valueOf(value));
                }
            }

        };
        return ed;
    }

    /**
     * シリアル型エディタを生成して返します。
     * 
     * @return シリアル型エディタ
     */
    protected VRTableCellViewerDelegate createSerialEditorDelegate() {
        final ACLabel component = new ACLabel();
        // component.setOpaque(true);
        // component.setBackground(UIManager
        // .getColor("TableHeader.background"));
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {
            public Object getValue() {
                return parseValue(component.getText());
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValue(Object value, int row, int column) {
                value = formatValue(value);
                component.setText(String.valueOf(row + 1));
            }

        };
        return ed;
    }

    /**
     * 状態アイコン型エディタを生成して返します。
     * 
     * @return 状態アイコン型エディタ
     */
    protected VRTableCellViewerDelegate createStateIconEditorDelegate() {
        final ACStateIconLabel component = new ACStateIconLabel();
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValue() {
                return parseValue(new Integer(component.getState()));
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValue(Object value, int row, int column) {
                value = formatValue(value);
                if (value == null) {
                    component.setState(ACStateIconLabel.STATE_NONE);
                } else {
                    component.setState(Integer.parseInt(String.valueOf(value)));
                }
            }

        };
        return ed;
    }

    /**
     * エディタ用カスタムセルキャッシュ を返します。
     * 
     * @return エディタ用カスタムセルキャッシュ
     */
    protected HashMap getCustomCellEditorCache() {
        return customCellEditorCache;
    }

    /**
     * レンダラ用カスタムセルキャッシュ を返します。
     * 
     * @return レンダラ用カスタムセルキャッシュ
     */
    protected HashMap getCustomCellRendererCache() {
        return customCellRendererCache;
    }

    /**
     * カスタム行レンダラ・エディタによるデリゲート定義を返します。
     * <p>
     * 該当しなければnullを返します。
     * </p>
     * <p>
     * <code>getType</code>には、以下の値を指定します。<br />
     * NCTableCellViewer.GET_TYPE_RENDERER : セルレンダラ<br />
     * NCTableCellViewer.GET_TYPE_EDITOR : セルエディタ<br />
     * </p>
     * 
     * @param value 値
     * @param row 対象行番号
     * @param column 対象列番号
     * @param cell カスタムセル設定
     * @param getType 取得する形式
     * @return デリゲート
     */
    protected VRTableCellViewerDelegate getCustomRowDelegate(Object value,
            int row, int column, ACTableCellViewerCustomCell cell, int getType) {
        if (cell == null) {
            return null;
        }
        // 取得形式によってキャッシュを変更
        HashMap cache;
        switch (getType) {
        case ACTableCellViewer.GET_TYPE_RENDERER:
            cache = getCustomCellRendererCache();
            break;
        case ACTableCellViewer.GET_TYPE_EDITOR:
            cache = getCustomCellEditorCache();
            break;
        default:
            return null;
        }

        // カスタム行エディタを設定している場合
        Object cached = cache.get(cell);
        VRTableCellViewerDelegate ed = null;
        if (cached instanceof VRTableCellViewerDelegate) {
            // キャッシュされている場合はそちらを使う
            ed = (VRTableCellViewerDelegate) cached;
        } else {
            Component obj;
            switch (getType) {
            case ACTableCellViewer.GET_TYPE_RENDERER:
                obj = cell.getRenderer();
                break;
            case ACTableCellViewer.GET_TYPE_EDITOR:
                obj = cell.getEditor();
                break;
            default:
                return null;
            }

            // クラス別にデリゲートを生成
            if (obj instanceof JTextField) {
                ed = createEditorDelegate((JTextField) obj);
            } else if (obj instanceof JLabel) {
                ed = createEditorDelegate((JLabel) obj);
            } else if (obj instanceof JCheckBox) {
                ed = createEditorDelegate((JCheckBox) obj);
            } else if (obj instanceof JComboBox) {
                ed = createEditorDelegate((JComboBox) obj);
            } else {
                return null;
            }
        }
        if (ed instanceof VRTableCellViewerDelegate) {
            // デリゲートが得られた場合はキャッシュにセットし使用する。
            cache.put(cell, ed);
            ed.setValue(value, row, column);
            return ed;
        }

        return null;
    }

    /**
     * エディタ用カスタムセルキャッシュ を設定します。
     * 
     * @param customCellEditorCache エディタ用カスタムセルキャッシュ
     */
    protected void setCustomCellEditorCache(HashMap customCellEditorCache) {
        this.customCellEditorCache = customCellEditorCache;
    }

    /**
     * レンダラ用カスタムセルキャッシュ を設定します。
     * 
     * @param customCellRendererCache レンダラ用カスタムセルキャッシュ
     */
    protected void setCustomCellRendererCache(HashMap customCellRendererCache) {
        this.customCellRendererCache = customCellRendererCache;
    }
}
