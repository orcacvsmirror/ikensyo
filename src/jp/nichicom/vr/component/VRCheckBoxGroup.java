/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindModelEvent;
import jp.nichicom.vr.bind.event.VRBindModelEventListener;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;

/**
 * バインド機構を実装したチェックボックスグループです。
 * <p>
 * モデルバインド機構も実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see JPanel
 * @see ListDataListener
 * @see ItemListener
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public class VRCheckBoxGroup extends JPanel implements ListDataListener,
        ItemListener, VRBindable, VRBindModelable {

    private ArrayList checkBoxs = new ArrayList();

    private transient Vector listSelectionListeners;

    protected boolean autoApplySource = false;

    protected String bindPath;

    protected ListModel dataModel;
//    protected ArrayList listeners = new ArrayList();
    protected String modelBindPath;

//    protected ArrayList modelListeners = new ArrayList();
    protected VRBindSource modelSource;

    protected VRBindSource source;

    public VRCheckBoxGroup() {
        super();
        initComponent();
        setModel(new DefaultComboBoxModel());
    }

    public VRCheckBoxGroup(ListModel aModel) {
        super();
        initComponent();
        setModel(aModel);
    }

    public VRCheckBoxGroup(final Object items[]) {
        super();
        initComponent();
        setModel(new AbstractListModel() {

            public Object getElementAt(int i) {
                return items[i];
            }

            public int getSize() {
                return items.length;
            }
        });
    }

    public VRCheckBoxGroup(final Vector items) {
        super();
        initComponent();
        setModel(new AbstractListModel() {

            public Object getElementAt(int i) {
                return items.elementAt(i);
            }

            public int getSize() {
                return items.size();
            }
        });
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
//        listeners.add(listener);
    }

    public synchronized void addListSelectionListener(ListSelectionListener l) {
        Vector v = listSelectionListeners == null ? new Vector(2)
                : (Vector) listSelectionListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            listSelectionListeners = v;
        }
    }

    public void addBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.add(VRBindModelEventListener.class, listener);
//        modelListeners.add(listener);
    }

    public void applyModelSource() throws ParseException {
        if (VRBindPathParser.set(getModelBindPath(), getModelSource(),
                getModel())) {
            fireModelApplySource(new VRBindModelEvent(this));
        }
    }
    /**
     * バインドモデルイベントリスナを返します。
     * @return バインドモデルイベントリスナ
     */
    public synchronized VRBindModelEventListener[] getBindModelEventListeners() {
        return (VRBindModelEventListener[]) (getListeners(VRBindModelEventListener.class));
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(),
                getSelectedIndicesVector())) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindModelSource() throws ParseException {
        Object obj = VRBindPathParser.get(getModelBindPath(), getModelSource());

        if (obj instanceof ListModel) {
            setModel((ListModel) obj);
        } else if (obj instanceof VRBindSource) {
            setModel(new VRListModelAdapter((VRBindSource) obj));
        } else if (obj instanceof List) {
            setModel(new VRListModelAdapter(new VRArrayList((List) obj)));
        } else if (obj == null) {
            setModel(null);
        } else {
            return;
        }
        fireModelBindSource(new VRBindModelEvent(this));
    }

    public void bindSource() throws ParseException {
        Object obj = VRBindPathParser.get(getBindPath(), source);

        ArrayList val;
        if (obj == null) {
            return;
        } else if (obj instanceof ArrayList) {
            val = (ArrayList) obj;
        } else if (obj instanceof Integer[]) {
            Integer[] src = (Integer[]) obj;
            val = new VRArrayList();
            int end = src.length;
            for (int i = 0; i < end; i++) {
                val.add(src[i]);
            }
        } else if (obj instanceof int[]) {
            int[] src = (int[]) obj;
            val = new VRArrayList();
            int end = src.length;
            for (int i = 0; i < end; i++) {
                val.add(new Integer(src[i]));
            }
        } else {
            return;
        }

        setSelectedIndices(val);
        fireBindSource(new VRBindEvent(this));
    }

    //構成を変更する

    public void contentsChanged(ListDataEvent e) {
        refreshCheckBox();
    }

    public Object createModelSource() {
        return new VRArrayList();
    }

    public Object createSource() {
        return new VRArrayList();
    }

    public String getBindPath() {
        return bindPath;
    }

    /**
     * 指定番号のチェックボックスを返します。
     * 
     * @param index 番号
     * @return チェックボックス
     */
    public JCheckBox getCheckBox(int index) {
        return (JCheckBox) checkBoxs.get(index);
    }

    /**
     * チェックボックスの数を返します。
     * 
     * @return チェックボックスの数
     */
    public int getCheckBoxCount() {
        return checkBoxs.size();
    }

    public ListModel getModel() {
        return dataModel;
    }

    public String getModelBindPath() {
        return modelBindPath;
    }

    public VRBindSource getModelSource() {
        return modelSource;
    }

    /**
     * @return インデックスセットを配列で戻します。
     */
    public int[] getSelectedIndices() {
        int[] ret = new int[checkBoxs.size()];
        int cnt = 0;

        for (int i = 0; i < checkBoxs.size(); i++) {
            JCheckBox rb = (JCheckBox) checkBoxs.get(i);
            if (rb.isSelected()) {
                ret[cnt] = i;
                cnt++;
            }
        }
        int[] retn = new int[cnt];

        System.arraycopy(ret, 0, retn, 0, cnt);

        return retn;
    }

    /**
     * @return インデックスセットを配列で戻します。
     */
    public VRArrayList getSelectedIndicesVector() {
        int[] indeices = getSelectedIndices();
        VRArrayList array = new VRArrayList();
        int end = indeices.length;
        for (int i = 0; i < end; i++) {
            array.add(new Integer(indeices[i]));
        }
        return array;
    }

    /**
     * @return 選択オブジェクトを戻します。
     */
    public Object[] getSelectedValues() {

        int[] idxs = getSelectedIndices();

        Object[] ret = new Object[idxs.length];

        for (int i = 0; i < idxs.length; i++) {
//            JCheckBox rb = (JCheckBox) checkBoxs.get(idxs[i]);
            ret[i] = dataModel.getElementAt(idxs[i]);

        }

        return ret;
    }

    public VRBindSource getSource() {
        return source;
    }

    public void intervalAdded(ListDataEvent e) {
        refreshCheckBox();
    }

    public void intervalRemoved(ListDataEvent e) {
        refreshCheckBox();
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    public void itemStateChanged(ItemEvent e) {
        int[] idxs = getSelectedIndices();
        if (idxs.length > 0) {
            fireValueChanged(new ListSelectionEvent(this, idxs[0],
                    idxs[idxs.length - 1], false));
        } else {
            fireValueChanged(new ListSelectionEvent(this, -1, -1, false));
        }

    }

    public void refreshCheckBox() {

        //ラジオの追加
        if (checkBoxs.size() < dataModel.getSize()) {
            int n = dataModel.getSize() - checkBoxs.size();
            for (int i = 0; i < n; i++) {
                JCheckBox rb = createItem();
                rb.setEnabled(this.isEnabled());
                rb.setMargin(new java.awt.Insets(0, 0, 0, 0));
                rb.addItemListener(this);
                addItem(rb);
                checkBoxs.add(rb);
            }

        } else if (checkBoxs.size() > dataModel.getSize()) {
            int n = checkBoxs.size() - dataModel.getSize();
            int m = checkBoxs.size();
            for (int i = 0; i < n; i++) {
                JCheckBox rb = (JCheckBox) checkBoxs.get(m - i - 1);
                rb.removeItemListener(this);
                this.remove(rb);
                checkBoxs.remove(rb);
            }

        }

        for (int i = 0; i < dataModel.getSize(); i++) {
            JCheckBox rb = (JCheckBox) checkBoxs.get(i);
            rb.setText(dataModel.getElementAt(i).toString());
        }

        revalidate();
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
//        listeners.remove(listener);
    }

    public synchronized void removeListSelectionListener(ListSelectionListener l) {
        if (listSelectionListeners != null
                && listSelectionListeners.contains(l)) {
            Vector v = (Vector) listSelectionListeners.clone();
            v.removeElement(l);
            listSelectionListeners = v;
        }
    }

    public void removeBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.remove(VRBindModelEventListener.class, listener);
//        modelListeners.remove(listener);
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < checkBoxs.size(); i++) {
            JCheckBox rb = (JCheckBox) checkBoxs.get(i);
            rb.setEnabled(enabled);
        }
    }

    public void setModel(ListModel aModel) {
        ListModel oldModel = dataModel;

        if (oldModel != null) {
            oldModel.removeListDataListener(this);
        }

        dataModel = aModel;

        dataModel.addListDataListener(this);

        refreshCheckBox();

        firePropertyChange("model", oldModel, dataModel);
    }

    /**
     * モデルバインドパスを設定します。
     * 
     * @param modelBindPath モデルバインドパス
     */
    public void setModelBindPath(String modelBindPath) {
        this.modelBindPath = modelBindPath;
    }

    public void setModelSource(VRBindSource modelSource) {
        this.modelSource = modelSource;
    }

    /**
     * 選択位置をインデックス配列で設定します。
     * 
     * @param indices 選択位置
     */
    public void setSelectedIndices(ArrayList indices) {
        int end = indices.size();
        int[] array = new int[end];
        for (int i = 0; i < end; i++) {
            array[i] = ((Integer) indices.get(i)).intValue();
        }
        setSelectedIndices(array);
    }

    /**
     * 選択位置をインデックス配列で設定します。
     * 
     * @param indices 選択位置
     */
    public void setSelectedIndices(int[] indices) {
        for (int i = 0; i < checkBoxs.size(); i++) {
            JCheckBox rb = (JCheckBox) checkBoxs.get(i);
            boolean flg = false;
            for (int j = 0; j < indices.length; j++) {
                if (indices[j] == i) {
                    flg = true;
                    break;
                }
            }
            if (flg != rb.isSelected()) {
                rb.setSelected(flg);
            }
        }
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 選択位置をインデックス配列で設定します。
     * 
     * @param indices 選択位置
     */
    public void setSelectedIndices(Integer[] indices) {
        int end = indices.length;
        int[] array = new int[end];
        for (int i = 0; i < end; i++) {
            array[i] = indices[i].intValue();
        }
        setSelectedIndices(array);
    }

    /**
     * 選択項目をオブジェクト配列で設定します。
     * @param items 選択項目
     */
    public void setSelectedValues(Object[] items) {

        for (int i = 0; i < checkBoxs.size(); i++) {
            JCheckBox rb = (JCheckBox) checkBoxs.get(i);
            boolean flg = false;
            for (int j = 0; j < items.length; j++) {
                if (dataModel.getElementAt(i).equals(items[j])) {
                    flg = true;
                    break;
                }
            }
            if (flg != rb.isSelected()) {
                rb.setSelected(flg);
            }
        }

    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    /**
     * 管理するチェックボックスをコンテナである自分自身に追加します。
     * <p>
     * template method pattern
     * </p>
     * 
     * @param item 追加するチェックボックス
     */
    protected void addItem(JCheckBox item) {
        this.add(item, null);
    }

    /**
     * 管理するチェックボックスを生成して返します。
     * <p>
     * factory method pattern
     * </p>
     * 
     * @return 生成したチェックボックスインスタンス
     */
    protected JCheckBox createItem() {
        return new JCheckBox();
    }
    /**
     * バインドイベントリスナを返します。
     * @return バインドイベントリスナ
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    /**
     * バインドイベントリスナを全走査してapplySourceイベントを呼び出します。
     * @param e イベント情報
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].applySource(e);
        }
//        Iterator it = listeners.iterator();
//        EventObject e = new EventObject(this);
//        while (it.hasNext()) {
//            ((VRBindEventListener) it.next()).applySource(e);
//        }
    }

    /**
     * バインドイベントリスナを全走査してapplySourceイベントを呼び出します。
     * @param e イベント情報
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindSource(e);
        }
//        Iterator it = listeners.iterator();
//        EventObject e = new EventObject(this);
//        while (it.hasNext()) {
//            ((VRBindEventListener) it.next()).bindSource(e);
//        }
    }

    /**
     * モデルアプライイベントを発火します。
     * @param e イベント情報
     */
    protected void fireModelApplySource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].applyModelSource(e);
        }
//        Iterator it = modelListeners.iterator();
//        EventObject e = new EventObject(this);
//        while (it.hasNext()) {
//            ((VRModelBindEventListener) it.next()).applyModelSource(e);
//        }
    }

    /**
     * モデルバインドイベントを発火します。
     * @param e イベント情報
     */
    protected void fireModelBindSource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindModelSource(e);
        }
//        Iterator it = modelListeners.iterator();
//        EventObject e = new EventObject(this);
//        while (it.hasNext()) {
//            ((VRModelBindEventListener) it.next()).bindModelSource(e);
//        }
    }

    /**
     * リスト選択イベントを発火します。
     * @param e イベント情報
     */
    protected void fireValueChanged(ListSelectionEvent e) {
        if (listSelectionListeners != null) {
            Vector listeners = listSelectionListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ListSelectionListener) listeners.elementAt(i))
                        .valueChanged(e);
            }
        }
    }

    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

}

