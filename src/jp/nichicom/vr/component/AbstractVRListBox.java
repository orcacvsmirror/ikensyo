package jp.nichicom.vr.component;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

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
 * �o�C���h�@�\�������������X�g�{�b�N�X�̊��N���X�ł��B
 * <p>
 * ���f���o�C���h�@�\���������Ă��܂��B
 * </p>
 * <p>
 * �I�����[�h��ListSelectionModel.SINGLE_SELECTION�̏ꍇ�͑I�����Ă���P��I�W�F�N�g��bind�ΏۂƂȂ�A����ȊO�̑I�����[�h�Ȃ�ΑI�����Ă���I�u�W�F�N�g�̔z��bind�ΏۂƂȂ�܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see JList
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see VRBindModelable
 * @see VRBindModelEventListener
 */
public class AbstractVRListBox extends JList implements VRListBoxar {

    private boolean autoApplySource = false;
    private String bindPath;
    private VRBindSource source;
    private String modelBindPath;
    private VRBindSource modelSource;
    private boolean shouldScrollOnSelect = true;

    /**
     * Constructs a <code>JList</code> with an empty model.
     */
    public AbstractVRListBox() {
        super();
        initComponent();
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified array. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the array of Objects to be loaded into the data model
     */
    public AbstractVRListBox(Object[] listData) {
        super(listData);
        initComponent();
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified <code>Vector</code>. This constructor just delegates to the
     * <code>ListModel</code> constructor.
     * 
     * @param listData the <code>Vector</code> to be loaded into the data
     *            model
     */
    public AbstractVRListBox(Vector listData) {
        super(listData);
        initComponent();
    }

    /**
     * Constructs a <code>JList</code> that displays the elements in the
     * specified, non-<code>null</code> model. All <code>JList</code>
     * constructors delegate to this one.
     * 
     * @param dataModel the data model for this list
     * @exception IllegalArgumentException if <code>dataModel</code> is
     *                <code>null</code>
     */
    public AbstractVRListBox(ListModel dataModel) {
        super(dataModel);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    /**
     * �P��I�����[�h(<code>ListSelectionModel.SINGLE_SELECTION</code>)�ł��邩��Ԃ��܂��B
     * 
     * @return �P��I�����[�h�ł��邩
     */
    public boolean isSingleSelection() {
        return getSelectionMode() == ListSelectionModel.SINGLE_SELECTION;
    }

    public void applySource() throws ParseException {
        Object data;
        if (isSingleSelection()) {
            // �P��I�����[�h�Ȃ�ΒP��f�[�^��apply
            data = getSelectedValue();
        } else {
            // �����I�����[�h�Ȃ�Ε����f�[�^��apply
            data = getSelectedValues();
        }
        if (VRBindPathParser.set(getBindPath(), getSource(), data)) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), source)) {
            return;
        }
        Object obj = VRBindPathParser.get(getBindPath(), source);

        ListSelectionModel selModel = getSelectionModel();
        if (selModel != null) {
            // �܂��͑I�����N���A����
            selModel.clearSelection();
            if (obj instanceof List) {
                // List�\���ł���Δz�񉻂���
                obj = ((List) obj).toArray();
            }

            if (obj instanceof Object[]) {
                // �z���̃f�[�^��bind���ꂽ�ꍇ
                ListModel mdl = getModel();
                if (mdl != null) {
                    // �f�[�^���f���𑖍����đI�����ׂ����ڂ��
                    Object[] array = (Object[]) obj;
                    int end = mdl.getSize();
                    for (int i = 0; i < end; i++) {
                        int find = Arrays.binarySearch(array, mdl
                                .getElementAt(i));
                        if (find >= 0) {
                            // �Y���������ڂ�I������
                            selModel.addSelectionInterval(find, find);
                        }
                    }
                }
            } else {
                // �P��̃f�[�^��bind���ꂽ�ꍇ
                setSelectedValue(obj, isShouldScrollOnSelect());
            }
            fireBindSource(new VRBindEvent(this));
        }
    }

    /**
     * bind�ɂ��P��I�����ɑI���������ڂ�������悤�ɃX�N���[�����邩��Ԃ��܂��B
     * 
     * @param shouldScrollOnSelect �I���������ڂ�������悤�ɃX�N���[�����邩
     */
    public void setShouldScrollOnSelect(boolean shouldScrollOnSelect) {
        this.shouldScrollOnSelect = shouldScrollOnSelect;
    }

    /**
     * bind�ɂ��P��I�����ɑI���������ڂ�������悤�ɃX�N���[�����邩��Ԃ��܂��B
     * 
     * @return �I���������ڂ�������悤�ɃX�N���[�����邩
     */
    public boolean isShouldScrollOnSelect() {
        return shouldScrollOnSelect;
    }

    public Object createSource() {
        // ���I��(null)��Ԃ�
        return null;
    }

    /**
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    public String getBindPath() {
        return bindPath;
    }

    public VRBindSource getSource() {
        return source;
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applySource(e);
        }
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������bindSource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindSource(e);
        }
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {

    }

    public void addBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.add(VRBindModelEventListener.class, listener);
    }

    public void applyModelSource() throws ParseException {
        if (VRBindPathParser.set(getModelBindPath(), getModelSource(),
                getModel())) {
            fireModelApplySource(new VRBindModelEvent(this));
        }
    }

    public void bindModelSource() throws ParseException {
        if (!VRBindPathParser.has(getModelBindPath(), getModelSource())) {
            return;
        }
        Object obj = VRBindPathParser.get(getModelBindPath(), getModelSource());

        obj = formatBindModel(obj);
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

    public Object createModelSource() {
        return new VRArrayList();
    }

    /**
     * �o�C���h���f���C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �o�C���h���f���C�x���g���X�i
     */
    public synchronized VRBindModelEventListener[] getBindModelEventListeners() {
        return (VRBindModelEventListener[]) (getListeners(VRBindModelEventListener.class));
    }

    public VRBindSource getModelSource() {
        return modelSource;
    }

    public void removeBindModelEventListener(VRBindModelEventListener listener) {
        listenerList.remove(VRBindModelEventListener.class, listener);
    }

    /**
     * ���f���o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param modelBindPath ���f���o�C���h�p�X
     */
    public void setModelBindPath(String modelBindPath) {
        this.modelBindPath = modelBindPath;
    }

    public void setModelSource(VRBindSource modelSource) {
        this.modelSource = modelSource;
    }

    /**
     * ���f���o�C���h�C�x���g���X�i��S��������applyModelSource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireModelApplySource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applyModelSource(e);
        }
    }

    /**
     * ���f���o�C���h�C�x���g���X�i��S��������bindModelSource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireModelBindSource(VRBindModelEvent e) {
        VRBindModelEventListener[] listeners = getBindModelEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindModelSource(e);
        }
    }

    /**
     * �o�C���h���郂�f����K�v�ɉ����ĕϊ����ĕԂ��܂��B
     * 
     * @param src �o�C���h���郂�f��
     * @return �ϊ����ʂ̃o�C���h���郂�f��
     */
    protected Object formatBindModel(Object src) {
        return src;
    }

    public String getModelBindPath() {
        return modelBindPath;
    }
}
