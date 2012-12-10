package jp.nichicom.ac.component;

import java.awt.Font;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListCellRenderer;
import javax.swing.text.Document;

import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRComboBox;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.adapter.VRBindSourceAdapter;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;

/**
 * 空行指定やレンダーバインドパス等に対応したコンボボックスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRComboBox
 */
public class ACComboBox extends VRComboBox {
    private ACBindListCellRenderer bindPathRenderer;
    private boolean blankable;
    private Object blankItem;
    private ListCellRenderer setedRenderer;

    /**
     * Creates a <code>JComboBox</code> with a default data model. The default
     * data model is an empty list of objects. Use <code>addItem</code> to add
     * items. By default the first item in the data model becomes selected.
     * 
     * @see DefaultComboBoxModel
     */
    public ACComboBox() {
        super();
    }

    /**
     * Creates a <code>JComboBox</code> that takes it's items from an existing
     * <code>ComboBoxModel</code>. Since the <code>ComboBoxModel</code> is
     * provided, a combo box created using this constructor does not create a
     * default combo box model and may impact how the insert, remove and add
     * methods behave.
     * 
     * @param aModel the <code>ComboBoxModel</code> that provides the
     *            displayed list of items
     * @see DefaultComboBoxModel
     */
    public ACComboBox(ComboBoxModel aModel) {
        super(aModel);
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements in the
     * specified array. By default the first item in the array (and therefore
     * the data model) becomes selected.
     * 
     * @param items an array of objects to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public ACComboBox(Object[] items) {
        super(items);
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements in the
     * specified Vector. By default the first item in the vector and therefore
     * the data model) becomes selected.
     * 
     * @param items an array of vectors to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public ACComboBox(Vector items) {
        super(items);
    }

    /**
     * 空選択を表す項目を返します。
     * 
     * @return 空選択を表す項目
     */
    public Object getBlankItem() {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            return ((ACComboBoxModelAdapter) getModel()).getBlankItem();
        }
        return blankItem;
    }

    /**
     * バインドパスで抽出した値が引数と一致するモデル内データを返します。
     * 
     * @param bindData 比較対象
     * @throws ParseException 解析失敗
     * @return 引数と一致するモデル内データ。該当無しならばnull
     */
    public Object getDataFromBindPath(Object bindData) throws ParseException {
        int index = getIndexFromBindPath(bindData);
        if (index >= 0) {
            return getItemAt(index);
        }
        return null;
    }

    /**
     * エディタのIMEモードを返します。
     * 
     * @return フォーカス取得時に自動設定するIMEモード
     */
    public InputSubset getIMEMode() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        if (edit instanceof ACTextField) {
            return ((ACTextField) edit).getIMEMode();
        }
        return null;
    }

    /**
     * バインドパスで抽出した値が引数と一致するモデル内データのインデックスを返します。
     * 
     * @param bindData 比較対象
     * @throws ParseException 解析失敗
     * @return 引数と一致するモデル内データのインデックス。該当無しならば-1
     */
    public int getIndexFromBindPath(Object bindData) throws ParseException {
        String selBindPath = getBindPath();
        int end = getItemCount();
        if (bindData != null) {
            for (int i = 0; i < end; i++) {
                Object obj = getItemAt(i);
                if (obj instanceof VRBindSource) {
                    // バインドソース形式ならば指定キーのデータで比較
                    if (bindData.equals(VRBindPathParser.get(selBindPath,
                            (VRBindSource) obj))) {
                        return i;
                    }
                } else {
                    if (bindData.equals(String.valueOf(obj))) {
                        return i;
                    }
                }
            }
        } else {
            for (int i = 0; i < end; i++) {
                Object obj = getItemAt(i);
                if (obj instanceof VRBindSource) {
                    // バインドソース形式ならば指定キーのデータで比較
                    if (VRBindPathParser.get(selBindPath, (VRBindSource) obj) == null) {
                        return i;
                    }
                } else {
                    if (obj == null) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * モデル上の項目を返します。
     * <p>
     * Editableがtrueの場合、モデル上はレコードでも<code>getItemAt</code>は文字列を返します。Editableに関わりなくモデルの項目を取得する場合は<code>getModelItem</code>を使います。
     * </p>
     * 
     * @return 選択しているモデル上の項目
     */
    public Object getModelItem(int index) {
        ComboBoxModel model = getModel();
        if (model == null) {
            return null;
        }
        if (isUseRenderBindPath()) {
            // モデルにラップされたレコード集合を使用している場合
            if (model instanceof VRBindSourceAdapter) {
                VRBindSourceAdapter adapter = (VRBindSourceAdapter) model;
                do {
                    if (adapter instanceof VRHashMapArrayToConstKeyArrayAdapter) {
                        // マップのリスト化アダプタを発見した場合、リスト化された要素ではなく素の要素たるマップを返す
                        return adapter.getAdaptee().getData(index);
                    }
                    if (adapter != null) {
                        if (adapter.getAdaptee() instanceof VRBindSourceAdapter) {
                            // ラップされている場合は深度を深める
                            adapter = (VRBindSourceAdapter) adapter
                                    .getAdaptee();
                        } else {
                            break;
                        }
                    }
                } while (adapter != null);
            }
        }

        return model.getElementAt(index);
    }

    /**
     * 描画対象のバインドパスを返します。
     * 
     * @return 描画対象のバインドパス
     */
    public String getRenderBindPath() {
        return getBindPathRenderer().getRenderBindPath();
    }

    /**
     * 選択しているモデル上の項目を返します。
     * <p>
     * Editableがtrueの場合、モデル上はレコードでも<code>getSelectedItem</code>は文字列を返します。Editableに関わりなくモデルの項目を取得する場合は<code>getSelectedModelItem</code>を使います。
     * </p>
     * <p>
     * 未選択ならばnullを返します。
     * </p>
     * 
     * @return 選択しているモデル上の項目
     */
    public Object getSelectedModelItem() {
        int index = getSelectedIndex();
        if (index < 0) {
            return null;
        }
        return getModelItem(index);
    }

    /**
     * コンボの入力内容を文字列で返します。
     * 
     * @return コンボの入力内容
     */
    public String getText() {
        if (!isEditable()) {
            try {
                return String.valueOf(getBindObjectFromItem(getSelectedItem()));
            } catch (ParseException ex) {
                return "";
            }
        }

        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return "";
        }

        return edit.getText();
    }

    /**
     * 空選択を許可するかを設定します。
     * 
     * @return 空選択を許可するか
     */
    public boolean isBlankable() {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            return ((ACComboBoxModelAdapter) getModel()).isBlankable();
        }
        return blankable;
    }

    /**
     * レンダーバインドパスによる描画を行なうかを返します。
     * 
     * @return レンダーバインドパスによる描画を行なうか
     */
    public boolean isUseRenderBindPath() {
        return (getRenderBindPath() != null)
                && (!"".equals(getRenderBindPath()));
    }

    /**
     * 空選択を許可するかを返します。
     * 
     * @param blankable 空選択を許可するか
     */
    public void setBlankable(boolean blankable) {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            ((ACComboBoxModelAdapter) getModel()).setBlankable(blankable);
        }
        this.blankable = blankable;
    }

    /**
     * 空選択を表す項目を設定します。
     * 
     * @param blankItem 空選択を表す項目
     */
    public void setBlankItem(Object blankItem) {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            ((ACComboBoxModelAdapter) getModel()).setBlankItem(blankItem);
        }
        this.blankItem = blankItem;
    }

    /**
     * エディタのIMEモードを設定します。
     * 
     * @param imeMode フォーカス取得時に自動設定するIMEモード
     */
    public void setIMEMode(InputSubset imeMode) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        if (edit instanceof ACTextField) {
            ((ACTextField) edit).setIMEMode(imeMode);
        }
    }

    public void setModel(ComboBoxModel model) {
        if (model instanceof ACComboBoxModelAdapter) {
            ACComboBoxModelAdapter adapt = (ACComboBoxModelAdapter) model;
            adapt.setBlankable(isBlankable());
            adapt.setBlankItem(getBlankItem());
        }
        if (isEditable() && isUseRenderBindPath()) {

            Object parent = model;
            Object inner = model;
            while (true) {
                if (inner instanceof VRHashMapArrayToConstKeyArrayAdapter) {
                    break;
                } else if (inner instanceof VRListModelAdapter) {
                    parent = inner;
                    inner = ((VRListModelAdapter) inner).getAdaptee();
                } else if (inner instanceof VRBindSource) {
                    if (parent instanceof VRListModelAdapter) {
                        ((VRListModelAdapter) parent)
                                .setAdaptee(new VRHashMapArrayToConstKeyArrayAdapter(
                                        (VRBindSource) inner,
                                        getRenderBindPath()));
                    }
                    break;
                } else {
                    break;
                }
            }

        }
        super.setModel(model);
    }

    protected Object formatBindModel(Object src) {
        if (src instanceof ComboBoxModel) {
            return src;
        } else if (src instanceof VRBindSource) {
            return new ACComboBoxModelAdapter((VRBindSource) src);
        } else if (src instanceof List) {
            return new ACComboBoxModelAdapter(new VRArrayList((List) src));
        }
        return src;
    }

    /**
     * アダプタクラスを生成し、Listをコンボモデルとして設定します。
     * 
     * @param model モデル
     */
    public void setModel(List model) {
        setModel(new VRArrayList(model));
    }

    /**
     * アダプタクラスを生成し、配列をコンボモデルとして設定します。
     * 
     * @param model モデル
     */
    public void setModel(Object[] model) {
        setModel(Arrays.asList(model));
    }

    /**
     * アダプタクラスを生成し、VRListをコンボモデルとして設定します。
     * 
     * @param model モデル
     */
    public void setModel(VRList model) {
        setModel(new ACComboBoxModelAdapter(model));
    }

    /**
     * 描画対象のバインドパスを設定します。
     * 
     * @param renderBindPath 描画対象のバインドパス
     */
    public void setRenderBindPath(String renderBindPath) {
        getBindPathRenderer().setRenderBindPath(renderBindPath);
        if (isUseRenderBindPath()) {
            if (getRenderer() != getBindPathRenderer()) {
                setSetedRenderer(getRenderer());
                super.setRenderer(getBindPathRenderer());
            }
        } else {
            if (getRenderer() == getBindPathRenderer()) {
                super.setRenderer(getSetedRenderer());
            }
        }
    }

    public void setRenderer(ListCellRenderer renderer) {
        super.setRenderer(renderer);
        setSetedRenderer(renderer);
    }

    /**
     * 過去の選択項目を無視して強制的に項目を選択させます。
     * 
     * @param anObject 選択する項目
     */
    public void setSelectedItemAbsolute(Object anObject) {
        selectedItemReminder = null;
        setSelectedItem(anObject);
    }

    /**
     * コンボの入力内容を文字列で設定します。
     * 
     * @param text コンボの入力内容
     */
    public void setText(String text) {
        if (!isEditable()) {
            try {
                setSelectedItem(getItemFromBindObject(text));
            } catch (ParseException ex) {
            }
            return;
        }

        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }

        edit.setText(text);
    }

    protected AbstractVRTextField createEditorField() {
        return new ACTextField();
    }

    /**
     * バインドパス指定による描画レンダラを返します。
     * 
     * @return バインドパス指定による描画レンダラ
     */
    protected ACBindListCellRenderer getBindPathRenderer() {
        if (bindPathRenderer == null) {
            bindPathRenderer = new ACBindListCellRenderer();
        }
        return bindPathRenderer;
    }

    protected Object getItemFromBindObject(Object data) throws ParseException {
        if (!isUseRenderBindPath()) {
            return super.getItemFromBindObject(data);
        }
        return getDataFromBindPath(data);
    }

    /**
     * バインドパスレンダラと差し変わったレンダラを返します。
     * 
     * @return バインドパスレンダラと差し変わったレンダラ
     */
    protected ListCellRenderer getSetedRenderer() {
        return setedRenderer;
    }

    protected Object getBindObjectFromItem(Object item) throws ParseException {
        if (!isUseRenderBindPath()) {
            return super.getBindObjectFromItem(item);
        }

        if (item instanceof VRBindSource) {
            item = VRBindPathParser.get(getBindPath(), (VRBindSource) item);
        }
        return item;
    }

    protected void initComponent() {
        super.initComponent();

        String osName = System.getProperty("os.name", "").toLowerCase();
        if (osName.indexOf("mac") >= 0) {
            // Mac
            String ver = System.getProperty("os.version", "");
            if ("10.4.0".compareTo(ver) >= 0) {
                // 10.4未満は"Osaka"
                Font nowFont = getFont();
                if (nowFont == null) {
                    setFont(new Font("Osaka", Font.PLAIN, 12));
                } else {
                    setFont(new Font("Osaka", nowFont.getStyle(), nowFont
                            .getSize()));
                }
            } else {
                // 10.4以上は何もしない
            }
        }

    }

    /**
     * バインドパスレンダラと差し変わったレンダラを設定します。
     * 
     * @param setedRenderer バインドパスレンダラと差し変わったレンダラ
     */
    protected void setSetedRenderer(ListCellRenderer setedRenderer) {
        this.setedRenderer = setedRenderer;
    }

    /**
     * 項目を選択しているかを返します。
     * 
     * @return 項目を選択しているか
     */
    public boolean isSelected() {
        return getSelectedModelItem() != null;
    }

    /**
     * 選択を解除します。
     */
    public void clearSelection() {
        setSelectedItem(null);
    }

    /**
     * エディタのドキュメントオブジェクトを返します。
     * 
     * @return ドキュメントオブジェクト
     */
    public Document getDocument() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getDocument();
    }

    /**
     * エディタのドキュメントオブジェクトを設定します。
     * 
     * @return ドキュメントオブジェクト
     */
    public void setDocument(Document document) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setDocument(document);
    }
    
//    public void removeNotify(){
//        if((getModel()!=null)&&(getModel().getSize()>0)){
//            setModel(new DefaultComboBoxModel());
//        }
//    }
}
