/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.im.InputSubset;
import java.text.Format;
import java.text.ParseException;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxEditor;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxEditor;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindModelEvent;
import jp.nichicom.vr.bind.event.VRBindModelEventListener;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.text.AbstractVRTextDocument;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRComboBoxModelAdapter;

/**
 * バインド機構を実装したコンボボックスの基底クラスです。
 * <p>
 * モデルバインド機構も実装しています。
 * </p>
 * <p>
 * AbstractVRTextDocumentの導入によって入力可能な文字種別や最小・最大文字列長を制限する機能を実装しています。
 * </p>
 * <p>
 * InputSubset指定によるIMEモード制御を実装しています。
 * </p>
 * <p>
 * Format指定による入力値のフォーマット変換処理を実装しています。
 * </p>
 * <p>
 * テキストフィールドと同じような幅(Columns)指定を実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see JComboBox
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see VRBindModelable
 * @see VRBindModelEventListener
 * @see InputSubset
 * @see Format
 * @see VRFormatEventListener
 * @see VRCharType
 * @see AbstractVRTextDocument
 * @see VRFormatable
 */
public abstract class AbstractVRComboBox extends JComboBox implements
        VRBindable, VRBindModelable, VRJComponentar, VRFormatable {

    private int columnWidth;

    private String modelBindPath;

    private VRBindSource modelSource;

    /**
     * Creates a <code>JComboBox</code> with a default data model. The default
     * data model is an empty list of objects. Use <code>addItem</code> to add
     * items. By default the first item in the data model becomes selected.
     * 
     * @see DefaultComboBoxModel
     */
    public AbstractVRComboBox() {
        super();
        initComponent();
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
    public AbstractVRComboBox(ComboBoxModel aModel) {
        super(aModel);
        initComponent();
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements in the
     * specified array. By default the first item in the array (and therefore
     * the data model) becomes selected.
     * 
     * @param items an array of objects to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public AbstractVRComboBox(Object[] items) {
        super(items);
        initComponent();
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements in the
     * specified Vector. By default the first item in the vector and therefore
     * the data model) becomes selected.
     * 
     * @param items an array of vectors to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public AbstractVRComboBox(Vector items) {
        super(items);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.addBindEventListener(listener);
    }

    public void addBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.add(VRBindModelEventListener.class, listener);
    }

    public void addFormatEventListener(VRFormatEventListener listener) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.addFormatEventListener(listener);
    }

    public void applyModelSource() throws ParseException {
        if (VRBindPathParser.set(getModelBindPath(), getModelSource(),
                getModel())) {
            fireModelApplySource(new VRBindModelEvent(this));
        }
    }

    public void applySource() throws ParseException {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }

        if (!isEditable()) {
            // 記入不可の場合はテキストに依存しないbindを用いる
            Object obj = getBindObjectFromItem(getSelectedItem());
            edit.setText(String.valueOf(obj));
            if (VRBindPathParser.set(getBindPath(), getSource(), obj)) {
                fireApplySource(new VRBindEvent(this));
            }
        } else {
            edit.applySource();
        }
    }

    public void bindModelSource() throws ParseException {
        if (!VRBindPathParser.has(getModelBindPath(), getModelSource())) {
            return;
        }
        Object obj = VRBindPathParser.get(getModelBindPath(), getModelSource());

        obj = formatBindModel(obj);
        if (obj instanceof ComboBoxModel) {
            setModel((ComboBoxModel) obj);
        } else if (obj instanceof VRBindSource) {
            setModel(new VRComboBoxModelAdapter((VRBindSource) obj));
        } else if (obj instanceof List) {
            setModel(new VRComboBoxModelAdapter(new VRArrayList((List) obj)));
        } else if (obj == null) {
            setModel(null);
        } else {
            return;
        }
        fireModelBindSource(new VRBindModelEvent(this));
    }

    public void bindSource() throws ParseException {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        if (isEditable()) {
            edit.bindSource();
        } else {
            if (!VRBindPathParser.has(getBindPath(), getSource())) {
                return;
            }
            Object obj = VRBindPathParser.get(getBindPath(), getSource());
            setSelectedItem(getItemFromBindObject(obj));
            fireBindSource(new VRBindEvent(this));
        }

    }

    public Object createModelSource() {
        return new VRArrayList();
    }

    public Object createSource() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.createSource();
    }

    /**
     * バインドイベントリスナを返します。
     * 
     * @return バインドイベントリスナ
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return new VRBindEventListener[0];
        }
        return edit.getBindEventListeners();
    }

    /**
     * バインドモデルイベントリスナを返します。
     * 
     * @return バインドモデルイベントリスナ
     */
    public synchronized VRBindModelEventListener[] getBindModelEventListeners() {
        return (VRBindModelEventListener[]) (getListeners(VRBindModelEventListener.class));
    }

    public String getBindPath() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getBindPath();
    }

    /**
     * エディタの文字種制限を返します。
     * 
     * @return 文字種制限
     */
    public VRCharType getCharType() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getCharType();
    }

    /**
     * Returns the number of columns in this <code>TextField</code>.
     * 
     * @return the number of columns >= 0
     */
    public int getColumns() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return 0;
        }
        return edit.getColumns();
    }

    /**
     * エディタのフォーマットを返します。
     * 
     * @return フォーマット
     */
    public Format getFormat() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getFormat();
    }

    /**
     * フォーマットイベントリスナを返します。
     * 
     * @return フォーマットイベントリスナ
     */
    public synchronized VRFormatEventListener[] getFormatEventListeners() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return new VRFormatEventListener[0];
        }
        return edit.getFormatEventListeners();
    }

    /**
     * エディタの最大文字数を返します。
     * 
     * @return 最大文字数
     */
    public int getMaxLength() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return 0;
        }
        return edit.getMaxLength();
    }

    /**
     * エディタの最小文字数を返します。
     * 
     * @return 最小文字数
     */
    public int getMinLength() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return 0;
        }
        return edit.getMinLength();
    }

    public String getModelBindPath() {
        return modelBindPath;
    }

    public VRBindSource getModelSource() {
        return modelSource;
    }

    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return d;
        }

        Dimension ed = edit.getPreferredSize();
        if (ed != null) {
            // テキストフィールドの幅にボタンサイズを加算
            Insets insets = getInsets();
            int buttonSize = ed.height - (insets.top + insets.bottom);
            int addWidth = insets.left + insets.right + buttonSize;
            ed.width += addWidth;

            if (d == null) {
                d = ed;
            } else {
                d.width = Math.max(d.width, ed.width);
                d.height = Math.max(d.height, ed.height);
            }
            
            if(getMaxLength()>0){
                int maxW=getMaxLength() * getColumnWidth()+addWidth;
                //最大幅で制限する
                d.width = Math.min(d.width, maxW);
            }
        }

        return d;
    }
    public VRBindSource getSource() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getSource();
    }

    public boolean isAutoApplySource() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return false;
        }
        return edit.isAutoApplySource();
    }
    public void removeBindEventListener(VRBindEventListener listener) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.removeBindEventListener(listener);
    }

    public void removeBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.remove(VRBindModelEventListener.class, listener);
    }

    public void removeFormatEventListener(VRFormatEventListener listener) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.removeFormatEventListener(listener);
    }

    public void setAutoApplySource(boolean autoApplySource) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setAutoApplySource(autoApplySource);
    }

    /**
     * バインドパスを設定します。
     * 
     * @param bindPath バインドパス
     */
    public void setBindPath(String bindPath) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setBindPath(bindPath);
    }

    /**
     * エディタの文字種制限を設定します。
     * 
     * @param charType 文字種制限
     */
    public void setCharType(VRCharType charType) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setCharType(charType);
    }

    /**
     * Sets the number of columns in this <code>TextField</code>, and then
     * invalidate the layout.
     * 
     * @param columns the number of columns >= 0
     * @exception IllegalArgumentException if <code>columns</code> is less
     *                than 0
     */
    public void setColumns(int columns) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setColumns(columns);
    }

    public void setEditable(boolean editable) {
        getEditorField().setBorderless(editable);
        super.setEditable(editable);
    }

    /**
     * エディタのフォーマットを設定します。
     * 
     * @param format フォーマット
     */
    public void setFormat(Format format) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setFormat(format);
    }

    /**
     * エディタの最大文字数を設定します。
     * 
     * @param maxLength 最大文字数
     */
    public void setMaxLength(int maxLength) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setMaxLength(maxLength);
    }

    /**
     * エディタの最小文字数を設定します。
     * 
     * @param minLength 最小文字数
     */
    public void setMinLength(int minLength) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setMinLength(minLength);
    }

    public void setModelBindPath(String modelBindPath) {
        this.modelBindPath = modelBindPath;
    }

    public void setModelSource(VRBindSource modelSource) {
        this.modelSource = modelSource;
    }

    public void setSource(VRBindSource source) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setSource(source);
    }

    /**
     * エディタコンポーネントを生成します。
     * <p>
     * FactoryMethod Pattern
     * </p>
     * 
     * @return エディタコンポーネント
     */
    protected AbstractVRTextField createEditorField() {
        AbstractVRTextField ef = new VRTextField();
        return ef;
    }

    /**
     * バインドイベントリスナを全走査してapplySourceイベントを呼び出します。
     * 
     * @param e イベント情報
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applySource(e);
        }
    }

    /**
     * バインドイベントリスナを全走査してapplySourceイベントを呼び出します。
     * 
     * @param e イベント情報
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindSource(e);
        }
    }

    /**
     * モデルバインドイベントリスナを全走査してapplyModelSourceイベントを呼び出します。
     * 
     * @param e イベント情報
     */
    protected void fireModelApplySource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applyModelSource(e);
        }
    }

    /**
     * モデルバインドイベントリスナを全走査してbindModelSourceイベントを呼び出します。
     * 
     * @param e イベント情報
     */
    protected void fireModelBindSource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindModelSource(e);
        }
    }

    /**
     * バインドするモデルを必要に応じて変換して返します。
     * 
     * @param src バインドするモデル
     * @return 変換結果のバインドするモデル
     */
    protected Object formatBindModel(Object src) {
        return src;
    }

    /**
     * 項目をバインドの対象となる形式に変換して返します。
     * 
     * @param item 項目
     * @return 項目のバインド表現
     * @throws ParseException 解析例外
     */
    protected Object getBindObjectFromItem(Object item) throws ParseException {
        if (item == null) {
            return "";
        } else {
            return item;
        }
    }

    /**
     * Returns the column width. The meaning of what a column is can be
     * considered a fairly weak notion for some fonts. This method is used to
     * define the width of a column. By default this is defined to be the width
     * of the character <em>m</em> for the font used. This method can be
     * redefined to be some alternative amount
     * 
     * @return the column width >= 1
     */
    protected int getColumnWidth() {
        if (columnWidth == 0) {
            FontMetrics metrics = getFontMetrics(getFont());
            columnWidth = metrics.charWidth('m');
        }
        // 全角文字を考慮して幅計算させるため、1.1倍して返す
        return (int) (columnWidth * 1.1);
    }

    /**
     * エディタコンポーネントを返します。
     * 
     * @return エディタコンポーネント
     */
    protected AbstractVRTextField getEditorField() {
        ComboBoxEditor edit = getEditor();
        if (edit != null) {
            Component comp = edit.getEditorComponent();
            if (comp instanceof AbstractVRTextField) {
                return (AbstractVRTextField) comp;
            }
        }
        return null;
    }

    /**
     * バインド対象のデータを対応する項目に変換して返します。
     * 
     * @param data バインド対象のデータ
     * @return 項目
     * @throws ParseException 解析例外
     */
    protected Object getItemFromBindObject(Object data) throws ParseException {
        int end = getItemCount();
        if (data != null) {
            for (int i = 0; i < end; i++) {
                Object obj = getItemAt(i);
                if (obj != null) {
                    if (data.equals(obj)) {
                        return obj;
                    }
                }
            }
        } else {
            for (int i = 0; i < end; i++) {
                Object obj = getItemAt(i);
                if (obj == null) {
                    return obj;
                }
            }
        }

        return null;
    }

    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {
        VRComboBoxEditor edit = new VRComboBoxEditor();
        edit.setEditorComponent(createEditorField());
        setEditor(edit);
        setEditable(true);
    }

    /**
     * エディタコンポーネントを設定します。
     * 
     * @param editField エディタコンポーネント
     */
    protected void setEditorField(AbstractVRTextField editField) {
        if (getEditor() instanceof VRComboBoxEditor) {
            VRComboBoxEditor edit = (VRComboBoxEditor) getEditor();
            edit.setEditorComponent(editField);
            setEditor(edit);
        }
    }

    /**
     * Editorを変更可能なコンボ用エディタクラスです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2005/10/31
     * @see BasicComboBoxEditor
     * @see JTextField
     */
    protected class VRComboBoxEditor extends BasicComboBoxEditor {
        /**
         * エディタコンポーネントを設定します。
         * 
         * @param editor エディタコンポーネント
         */
        public void setEditorComponent(JTextField editor) {
            super.editor = editor;
        }
    }

}