package jp.or.med.orca.ikensho.component;

import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * 一覧編集可能コンボボックスモデルデコレータークラス <br />
 * 一覧編集可能コンボボックスモデルクラスをデコレートし、編集項目を追加したモデルクラスです。 <br />
 *
 * 作成日 2007/10/11 <br />
 * 更新日 <br />
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved. <br />
 *
 * @author Masahiko Higuchi
 * @version 1.0
 */
public class IkenshoOptionComboBoxModelDecorator extends AbstractListModel
        implements ComboBoxModel, ListDataListener {

    private ComboBoxModel originalModel;

    private ArrayList options = new ArrayList();

    private Object selectedItem;

    public IkenshoOptionComboBoxModelDecorator(ComboBoxModel originalModel) {
        super();
        setOriginalModel(originalModel);
    }

    /**
     * originalModel を返します。
     *
     * @return originalModel
     */
    public ComboBoxModel getOriginalModel() {
        return originalModel;
    }

    /**
     * originalModel を設定します
     *
     * @param originalModel originalModel
     */
    public void setOriginalModel(ComboBoxModel originalModel) {

        if (this.originalModel != null) {
            this.originalModel.removeListDataListener(this);
        }
        this.originalModel = originalModel;
        this.originalModel.addListDataListener(this);

    }

    public Object getSelectedItem() {
        return originalModel.getSelectedItem();

    }

    public void setSelectedItem(Object anItem) {
        originalModel.setSelectedItem(anItem);
    }

    public Object getElementAt(int index) {
        if (index >= originalModel.getSize()) {
            return options.get(index - originalModel.getSize());
        } else {
            return originalModel.getElementAt(index);
        }
    }

    public int getSize() {
        return originalModel.getSize() + options.size();
    }

    //イベント翻訳
    public void contentsChanged(ListDataEvent e) {
        fireContentsChanged(this, e.getIndex0(), e.getIndex1());
    }

    //イベント翻訳
    public void intervalAdded(ListDataEvent e) {
        fireIntervalAdded(this, e.getIndex0(), e.getIndex1());
    }

    //イベント翻訳
    public void intervalRemoved(ListDataEvent e) {
        fireIntervalRemoved(this, e.getIndex0(), e.getIndex1());

    }

    /**
     * customizable を返します。
     *
     * @return customizable
     */
    public ArrayList getOptions() {
        return options;
    }

}
