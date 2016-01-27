/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import jp.or.med.orca.ikensho.affair.IkenshoMainMenu;

/**
 * バインド機構を実装したラジオボタングループです。
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
 * @see VRBindModelable
 * @see VRBindModelEventListener
 */
public class VRRadioButtonGroup extends JPanel implements ListDataListener,
        ItemListener, VRBindable, VRBindModelable {
    protected boolean autoApplySource = false;
    protected String bindPath;
    protected ButtonGroup buttonGroup = new ButtonGroup();
    protected KeyListener buttonKeyListener;
    protected ListModel dataModel;
    protected transient Vector listSelectionListeners;
    protected String modelBindPath;
    protected VRBindSource modelSource;
    protected JRadioButton none = new JRadioButton();
    protected ArrayList radioButtons = new ArrayList();
    protected VRBindSource source;

    /**
     * コンストラクタ
     */
    public VRRadioButtonGroup() {
        super();
        initComponent();
        setModel(new DefaultComboBoxModel());
    }

    /**
     * コンストラクタ
     * 
     * @param aModel ラジオ生成元モデル
     */
    public VRRadioButtonGroup(ListModel aModel) {
        super();
        initComponent();
        setModel(aModel);
    }
    /**
     * コンストラクタ
     * 
     * @param items ラジオ生成元配列
     */
    public VRRadioButtonGroup(final Object items[]) {
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

    /**
     * コンストラクタ
     * 
     * @param items ラジオ生成元ベクタ
     */
    public VRRadioButtonGroup(final Vector items) {
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
    }

    public void addBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.add(VRBindModelEventListener.class, listener);
    }

    /**
     * リスト選択リスナを追加します。
     * 
     * @param l リスナ
     */
    public synchronized void addListSelectionListener(ListSelectionListener l) {
        Vector v = listSelectionListeners == null ? new Vector(2)
                : (Vector) listSelectionListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            listSelectionListeners = v;
        }
    }

    public void applyModelSource() throws ParseException {
        if (VRBindPathParser.set(getModelBindPath(), getModelSource(),
                getModel())) {
            fireModelApplySource(new VRBindModelEvent(this));
        }
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), new Integer(
                getSelectedIndex()))) {
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
        int val;
        if (obj == null) {
            return;
        } else if (obj instanceof Integer) {
            val = ((Integer) obj).intValue();
        } else {
            val = Integer.valueOf(String.valueOf(obj)).intValue();
        }
        setSelectedIndex(val);
        fireBindSource(new VRBindEvent(this));
    }

    /**
     * 選択を解除します。
     */
    public void clearSelection() {
        none.setSelected(true);
        //noneは、画面上より設定できない為、イベント経由せず、ここでイベントを発行させる
        fireValueChanged(new ListSelectionEvent(this, -1, -1, false));
    }

    /**
     * 指定のラジオボタンを含むかを返します。
     * 
     * @param c 検索対象
     * @return 指定のラジオボタンを含むか
     */
    public boolean containsButton(Component c) {
        return radioButtons.contains(c);
    }

    //構成を変更する
    public void contentsChanged(ListDataEvent e) {
        refreshRadioButton();
    }

    public Object createModelSource() {
        return new VRArrayList();
    }

    public Object createSource() {
        return new Integer(0);
    }

    /**
     * バインドイベントリスナを返します。
     * @return バインドイベントリスナ
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    /**
     * バインドモデルイベントリスナを返します。
     * @return バインドモデルイベントリスナ
     */
    public synchronized VRBindModelEventListener[] getBindModelEventListeners() {
        return (VRBindModelEventListener[]) (getListeners(VRBindModelEventListener.class));
    }

    public String getBindPath() {
        return bindPath;
    }

    /**
     * 指定番号のラジオボタンを返します。
     * 
     * @param index 番号
     * @return ラジオボタン
     */
    public JRadioButton getButton(int index) {
        if ((index < 0) || (index >= radioButtons.size())) {
            return null;
        }
        return (JRadioButton) radioButtons.get(index);
    }

    /**
     * ラジオボタン数を返します。
     * 
     * @return ラジオボタン数
     */
    public int getButtonCount() {
        return radioButtons.size();
    }

    /**
     * 最初のラジオボタンを返します。
     * 
     * @return 最初のラジオボタン
     */
    public JRadioButton getFirstButton() {
        if (radioButtons.size() <= 0) {
            return null;
        }
        return (JRadioButton) radioButtons.get(0);
    }

    /**
     * 最後のラジオボタンを返します。
     * 
     * @return 最後のラジオボタン
     */
    public JRadioButton getLastButton() {
        if (radioButtons.size() <= 0) {
            return null;
        }
        return (JRadioButton) radioButtons.get(radioButtons.size() - 1);
    }

    /**
     * ラジオ生成元モデルを返します。
     * 
     * @return ラジオ生成元モデル
     */
    public ListModel getModel() {
        return dataModel;
    }

    public String getModelBindPath() {
        return modelBindPath;
    }

    public VRBindSource getModelSource() {
        return modelSource;
    }

    public JRadioButton getSelectedButton() {
        if (getSelectedIndex() > 0) {
            return (JRadioButton) radioButtons.get(getSelectedIndex());
        } else {
            return null;
        }
    }

    /**
     * 選択中のラジオ番号を返します。
     * 
     * @return 選択中のラジオ番号
     */
    public int getSelectedIndex() {
        for (int i = 0; i < radioButtons.size(); i++) {
            JRadioButton rb = (JRadioButton) radioButtons.get(i);
            if (rb.isSelected()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 選択中のラジオに対応する項目を返します。
     * 
     * @return モデル内の項目
     */
    public Object getSelectedValue() {
        for (int i = 0; i < radioButtons.size(); i++) {
            JRadioButton rb = (JRadioButton) radioButtons.get(i);
            if (rb.isSelected()) {
                return dataModel.getElementAt(i);
            }
        }
        return null;
    }

    public VRBindSource getSource() {
        return source;
    }

    public void intervalAdded(ListDataEvent e) {
        refreshRadioButton();
    }

    public void intervalRemoved(ListDataEvent e) {
        refreshRadioButton();
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    /**
     * 項目を選択しているかを返します。
     * 
     * @return 項目を選択しているか
     */
    public boolean isSelected() {
        if(none==null){
            return false;
        }
        return !none.isSelected();
    }

    public void itemStateChanged(ItemEvent e) {
        int idx = radioButtons.indexOf(e.getSource());
        if (idx == getSelectedIndex()) {
            fireValueChanged(new ListSelectionEvent(this, idx, idx, false));
        }
    }

    /**
     * 保持モデルを元にラジオボタンを再生成します。
     */
    public void refreshRadioButton() {
        int selected = getSelectedIndex();
        //ラジオの追加
        if (radioButtons.size() < dataModel.getSize()) {
            int n = dataModel.getSize() - radioButtons.size();
            for (int i = 0; i < n; i++) {
                JRadioButton rb = createItem();
                rb.getActionMap();
                rb.addKeyListener(buttonKeyListener);
                rb.setEnabled(this.isEnabled());
                rb.setMargin(new java.awt.Insets(0, 0, 0, 0));
                rb.addItemListener(this);
                addRadioButton(rb);
                buttonGroup.add(rb);
                radioButtons.add(rb);
            }
        } else if (radioButtons.size() > dataModel.getSize()) {
            int n = radioButtons.size() - dataModel.getSize();
            int m = radioButtons.size();
            for (int i = 0; i < n; i++) {
                JRadioButton rb = (JRadioButton) radioButtons.get(m - i - 1);
                rb.removeItemListener(this);
                rb.removeKeyListener(buttonKeyListener);
                remove(rb);
                buttonGroup.remove(rb);
                radioButtons.remove(rb);
            }
        }
        for (int i = 0; i < dataModel.getSize(); i++) {
            JRadioButton rb = (JRadioButton) radioButtons.get(i);
            rb.setText(dataModel.getElementAt(i).toString());
        }
        setSelectedIndex(selected);
        revalidate();
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
//        listeners.remove(listener);
    }

    public void removeBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.remove(VRBindModelEventListener.class, listener);
    }

    /**
     * リスト選択リスナを削除します。
     * 
     * @param l リスナ
     */
    public synchronized void removeListSelectionListener(ListSelectionListener l) {
        if (listSelectionListeners != null
                && listSelectionListeners.contains(l)) {
            Vector v = (Vector) listSelectionListeners.clone();
            v.removeElement(l);
            listSelectionListeners = v;
        }
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (int i = 0; i < radioButtons.size(); i++) {
            JRadioButton rb = (JRadioButton) radioButtons.get(i);
            rb.setEnabled(enabled);
        }
    }

    /**
     * ラジオ生成元モデルを設定します。
     * 
     * @param aModel ラジオ生成元モデル
     */
    public void setModel(ListModel aModel) {
        ListModel oldModel = dataModel;
        if (oldModel != null) {
            oldModel.removeListDataListener(this);
        }
        dataModel = aModel;
        dataModel.addListDataListener(this);
        refreshRadioButton();
        firePropertyChange("model", oldModel, dataModel);
    }

    /**
     * モデルバインドパスを設定します。
     * @param modelBindPath モデルバインドパス
     */
    public void setModelBindPath(String modelBindPath) {
        this.modelBindPath = modelBindPath;
    }

    public void setModelSource(VRBindSource modelSource) {
        this.modelSource = modelSource;
    }

    /**
     * 選択するラジオ番号を設定します。
     * 
     * @param selectedIndex 選択するラジオ番号
     */
    public void setSelectedIndex(int selectedIndex) {
        if (selectedIndex < 0 || selectedIndex >= radioButtons.size()) {
            clearSelection();
        } else {
            JRadioButton rb = (JRadioButton) radioButtons.get(selectedIndex);
            rb.setSelected(true);
        }
    }

    /**
     * 指定した項目に対応するラジオを選択します。
     * 
     * @param item モデル内の項目
     */
    public void setSelectedValue(Object item) {
        int idx = -1;
        for (int i = 0; i < dataModel.getSize(); i++) {
            if (dataModel.getElementAt(i).equals(item)) {
                idx = i;
                break;
            }
        }
        setSelectedIndex(idx);
    }
    
    public void setSource(VRBindSource source) {
        this.source = source;
    }

    /**
     * 管理するラジオボタンをコンテナである自分自身に追加します。 <br />
     * template method pattern
     * 
     * @param item 追加するラジオボタン
     */
    protected void addRadioButton(JRadioButton item) {
        this.add(item, null);
    }

    /**
     * ラジオボタンに関連付けるキーリスナを生成します。
     * @return キーリスナ
     */
    protected KeyListener createButtonKeyLisener(){
        return new VRRadioButtonGroupKeyListener(this);
    }

    /**
     * 管理するラジオボタンを生成して返します。 < br/> factory method pattern
     * 
     * @return 生成したラジオボタンインスタンス
     */
    protected JRadioButton createItem() {
        return new JRadioButton();
    }
    /**
     * 使用するレイアウトマネージャを返します。
     * 
     * @return レイアウトマネージャ
     */
    protected LayoutManager createLayoutManager() {
        return new FlowLayout(FlowLayout.LEFT, 8, 0);
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
    }

    /**
     * モデルアプライイベントを発火します。
     */
    protected void fireModelApplySource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].applyModelSource(e);
        }
    }

    /**
     * モデルバインドイベントを発火します。
     */
    protected void fireModelBindSource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindModelSource(e);
        }
    }

    /**
     * 値変化イベントを発火します。
     * 
     * @param e イベント情報
     */
    protected void fireValueChanged(ListSelectionEvent e) {
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        if (listSelectionListeners != null) {
            Vector listeners = listSelectionListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ListSelectionListener) listeners.elementAt(i))
                        .valueChanged(e);
            }
        }
    }

    protected ArrayList getRadioButtons() {
        return radioButtons;
    }

    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {
        setOpaque(false);
        setLayout(createLayoutManager());
        none.setMargin(new java.awt.Insets(0, 0, 0, 0));
        buttonGroup.add(none);
        buttonKeyListener = createButtonKeyLisener();
    }

    protected void setRadioButtons(ArrayList radioButtons) {
        this.radioButtons = radioButtons;
    }

    /**
     * ラジオボタングループ用のキーリスナです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Susumu Nakahara
     * @version 1.0 2005/10/31
     */
    protected class VRRadioButtonGroupKeyListener extends KeyAdapter {
        private VRRadioButtonGroup g;

        /**
         * コンストラクタです。
         * 
         * @param g ラジオボタングループ
         */
        public VRRadioButtonGroupKeyListener(VRRadioButtonGroup g) {
            this.g = g;
        }

        public void keyPressed(KeyEvent e) {
            if (e.getSource() != null) {
                JRadioButton b = (JRadioButton) e.getSource();
                int index = g.getRadioButtons().indexOf(b);
                switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (g.getFirstButton().equals(b)) {
                        //最初のボタンなのでローテーション
                        JRadioButton lb = g.getLastButton();
                        if (lb != null) {
                            lb.requestFocus();
                        }
                    } else {
                        ((JRadioButton) g.radioButtons.get(index - 1))
                                .requestFocus();
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (g.getLastButton().equals(b)) {
                        //最後のボタンなのでローテーション
                        JRadioButton fb = g.getFirstButton();
                        if (fb != null) {
                            fb.requestFocus();
                        }
                    } else {
                        ((JRadioButton) g.radioButtons.get(index + 1))
                                .requestFocus();
                    }
                    break;
                }
            }
        }
    }
}