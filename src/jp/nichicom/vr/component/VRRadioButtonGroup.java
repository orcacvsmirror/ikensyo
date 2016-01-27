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
 * �o�C���h�@�\�������������W�I�{�^���O���[�v�ł��B
 * <p>
 * ���f���o�C���h�@�\���������Ă��܂��B
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
     * �R���X�g���N�^
     */
    public VRRadioButtonGroup() {
        super();
        initComponent();
        setModel(new DefaultComboBoxModel());
    }

    /**
     * �R���X�g���N�^
     * 
     * @param aModel ���W�I���������f��
     */
    public VRRadioButtonGroup(ListModel aModel) {
        super();
        initComponent();
        setModel(aModel);
    }
    /**
     * �R���X�g���N�^
     * 
     * @param items ���W�I�������z��
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
     * �R���X�g���N�^
     * 
     * @param items ���W�I�������x�N�^
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
     * ���X�g�I�����X�i��ǉ����܂��B
     * 
     * @param l ���X�i
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
     * �I�����������܂��B
     */
    public void clearSelection() {
        none.setSelected(true);
        //none�́A��ʏ���ݒ�ł��Ȃ��ׁA�C�x���g�o�R�����A�����ŃC�x���g�𔭍s������
        fireValueChanged(new ListSelectionEvent(this, -1, -1, false));
    }

    /**
     * �w��̃��W�I�{�^�����܂ނ���Ԃ��܂��B
     * 
     * @param c �����Ώ�
     * @return �w��̃��W�I�{�^�����܂ނ�
     */
    public boolean containsButton(Component c) {
        return radioButtons.contains(c);
    }

    //�\����ύX����
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
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    /**
     * �o�C���h���f���C�x���g���X�i��Ԃ��܂��B
     * @return �o�C���h���f���C�x���g���X�i
     */
    public synchronized VRBindModelEventListener[] getBindModelEventListeners() {
        return (VRBindModelEventListener[]) (getListeners(VRBindModelEventListener.class));
    }

    public String getBindPath() {
        return bindPath;
    }

    /**
     * �w��ԍ��̃��W�I�{�^����Ԃ��܂��B
     * 
     * @param index �ԍ�
     * @return ���W�I�{�^��
     */
    public JRadioButton getButton(int index) {
        if ((index < 0) || (index >= radioButtons.size())) {
            return null;
        }
        return (JRadioButton) radioButtons.get(index);
    }

    /**
     * ���W�I�{�^������Ԃ��܂��B
     * 
     * @return ���W�I�{�^����
     */
    public int getButtonCount() {
        return radioButtons.size();
    }

    /**
     * �ŏ��̃��W�I�{�^����Ԃ��܂��B
     * 
     * @return �ŏ��̃��W�I�{�^��
     */
    public JRadioButton getFirstButton() {
        if (radioButtons.size() <= 0) {
            return null;
        }
        return (JRadioButton) radioButtons.get(0);
    }

    /**
     * �Ō�̃��W�I�{�^����Ԃ��܂��B
     * 
     * @return �Ō�̃��W�I�{�^��
     */
    public JRadioButton getLastButton() {
        if (radioButtons.size() <= 0) {
            return null;
        }
        return (JRadioButton) radioButtons.get(radioButtons.size() - 1);
    }

    /**
     * ���W�I���������f����Ԃ��܂��B
     * 
     * @return ���W�I���������f��
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
     * �I�𒆂̃��W�I�ԍ���Ԃ��܂��B
     * 
     * @return �I�𒆂̃��W�I�ԍ�
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
     * �I�𒆂̃��W�I�ɑΉ����鍀�ڂ�Ԃ��܂��B
     * 
     * @return ���f�����̍���
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
     * ���ڂ�I�����Ă��邩��Ԃ��܂��B
     * 
     * @return ���ڂ�I�����Ă��邩
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
     * �ێ����f�������Ƀ��W�I�{�^�����Đ������܂��B
     */
    public void refreshRadioButton() {
        int selected = getSelectedIndex();
        //���W�I�̒ǉ�
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
     * ���X�g�I�����X�i���폜���܂��B
     * 
     * @param l ���X�i
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
     * ���W�I���������f����ݒ肵�܂��B
     * 
     * @param aModel ���W�I���������f��
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
     * ���f���o�C���h�p�X��ݒ肵�܂��B
     * @param modelBindPath ���f���o�C���h�p�X
     */
    public void setModelBindPath(String modelBindPath) {
        this.modelBindPath = modelBindPath;
    }

    public void setModelSource(VRBindSource modelSource) {
        this.modelSource = modelSource;
    }

    /**
     * �I�����郉�W�I�ԍ���ݒ肵�܂��B
     * 
     * @param selectedIndex �I�����郉�W�I�ԍ�
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
     * �w�肵�����ڂɑΉ����郉�W�I��I�����܂��B
     * 
     * @param item ���f�����̍���
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
     * �Ǘ����郉�W�I�{�^�����R���e�i�ł��鎩�����g�ɒǉ����܂��B <br />
     * template method pattern
     * 
     * @param item �ǉ����郉�W�I�{�^��
     */
    protected void addRadioButton(JRadioButton item) {
        this.add(item, null);
    }

    /**
     * ���W�I�{�^���Ɋ֘A�t����L�[���X�i�𐶐����܂��B
     * @return �L�[���X�i
     */
    protected KeyListener createButtonKeyLisener(){
        return new VRRadioButtonGroupKeyListener(this);
    }

    /**
     * �Ǘ����郉�W�I�{�^���𐶐����ĕԂ��܂��B < br/> factory method pattern
     * 
     * @return �����������W�I�{�^���C���X�^���X
     */
    protected JRadioButton createItem() {
        return new JRadioButton();
    }
    /**
     * �g�p���郌�C�A�E�g�}�l�[�W����Ԃ��܂��B
     * 
     * @return ���C�A�E�g�}�l�[�W��
     */
    protected LayoutManager createLayoutManager() {
        return new FlowLayout(FlowLayout.LEFT, 8, 0);
    }
    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * @param e �C�x���g���
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].applySource(e);
        }
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * @param e �C�x���g���
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindSource(e);
        }
    }

    /**
     * ���f���A�v���C�C�x���g�𔭉΂��܂��B
     */
    protected void fireModelApplySource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].applyModelSource(e);
        }
    }

    /**
     * ���f���o�C���h�C�x���g�𔭉΂��܂��B
     */
    protected void fireModelBindSource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindModelSource(e);
        }
    }

    /**
     * �l�ω��C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
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
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
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
     * ���W�I�{�^���O���[�v�p�̃L�[���X�i�ł��B
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
         * �R���X�g���N�^�ł��B
         * 
         * @param g ���W�I�{�^���O���[�v
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
                        //�ŏ��̃{�^���Ȃ̂Ń��[�e�[�V����
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
                        //�Ō�̃{�^���Ȃ̂Ń��[�e�[�V����
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