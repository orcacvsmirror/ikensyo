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
 * �o�C���h�@�\�����������R���{�{�b�N�X�̊��N���X�ł��B
 * <p>
 * ���f���o�C���h�@�\���������Ă��܂��B
 * </p>
 * <p>
 * AbstractVRTextDocument�̓����ɂ���ē��͉\�ȕ�����ʂ�ŏ��E�ő啶���񒷂𐧌�����@�\���������Ă��܂��B
 * </p>
 * <p>
 * InputSubset�w��ɂ��IME���[�h������������Ă��܂��B
 * </p>
 * <p>
 * Format�w��ɂ����͒l�̃t�H�[�}�b�g�ϊ��������������Ă��܂��B
 * </p>
 * <p>
 * �e�L�X�g�t�B�[���h�Ɠ����悤�ȕ�(Columns)�w����������Ă��܂��B
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
            // �L���s�̏ꍇ�̓e�L�X�g�Ɉˑ����Ȃ�bind��p����
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
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return new VRBindEventListener[0];
        }
        return edit.getBindEventListeners();
    }

    /**
     * �o�C���h���f���C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �o�C���h���f���C�x���g���X�i
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
     * �G�f�B�^�̕����퐧����Ԃ��܂��B
     * 
     * @return �����퐧��
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
     * �G�f�B�^�̃t�H�[�}�b�g��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
     */
    public Format getFormat() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getFormat();
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g�C�x���g���X�i
     */
    public synchronized VRFormatEventListener[] getFormatEventListeners() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return new VRFormatEventListener[0];
        }
        return edit.getFormatEventListeners();
    }

    /**
     * �G�f�B�^�̍ő啶������Ԃ��܂��B
     * 
     * @return �ő啶����
     */
    public int getMaxLength() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return 0;
        }
        return edit.getMaxLength();
    }

    /**
     * �G�f�B�^�̍ŏ���������Ԃ��܂��B
     * 
     * @return �ŏ�������
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
            // �e�L�X�g�t�B�[���h�̕��Ƀ{�^���T�C�Y�����Z
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
                //�ő啝�Ő�������
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
     * �o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     */
    public void setBindPath(String bindPath) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setBindPath(bindPath);
    }

    /**
     * �G�f�B�^�̕����퐧����ݒ肵�܂��B
     * 
     * @param charType �����퐧��
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
     * �G�f�B�^�̃t�H�[�}�b�g��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setFormat(format);
    }

    /**
     * �G�f�B�^�̍ő啶������ݒ肵�܂��B
     * 
     * @param maxLength �ő啶����
     */
    public void setMaxLength(int maxLength) {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return;
        }
        edit.setMaxLength(maxLength);
    }

    /**
     * �G�f�B�^�̍ŏ���������ݒ肵�܂��B
     * 
     * @param minLength �ŏ�������
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
     * �G�f�B�^�R���|�[�l���g�𐶐����܂��B
     * <p>
     * FactoryMethod Pattern
     * </p>
     * 
     * @return �G�f�B�^�R���|�[�l���g
     */
    protected AbstractVRTextField createEditorField() {
        AbstractVRTextField ef = new VRTextField();
        return ef;
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
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
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

    /**
     * ���ڂ��o�C���h�̑ΏۂƂȂ�`���ɕϊ����ĕԂ��܂��B
     * 
     * @param item ����
     * @return ���ڂ̃o�C���h�\��
     * @throws ParseException ��͗�O
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
        // �S�p�������l�����ĕ��v�Z�����邽�߁A1.1�{���ĕԂ�
        return (int) (columnWidth * 1.1);
    }

    /**
     * �G�f�B�^�R���|�[�l���g��Ԃ��܂��B
     * 
     * @return �G�f�B�^�R���|�[�l���g
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
     * �o�C���h�Ώۂ̃f�[�^��Ή����鍀�ڂɕϊ����ĕԂ��܂��B
     * 
     * @param data �o�C���h�Ώۂ̃f�[�^
     * @return ����
     * @throws ParseException ��͗�O
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
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        VRComboBoxEditor edit = new VRComboBoxEditor();
        edit.setEditorComponent(createEditorField());
        setEditor(edit);
        setEditable(true);
    }

    /**
     * �G�f�B�^�R���|�[�l���g��ݒ肵�܂��B
     * 
     * @param editField �G�f�B�^�R���|�[�l���g
     */
    protected void setEditorField(AbstractVRTextField editField) {
        if (getEditor() instanceof VRComboBoxEditor) {
            VRComboBoxEditor edit = (VRComboBoxEditor) getEditor();
            edit.setEditorComponent(editField);
            setEditor(edit);
        }
    }

    /**
     * Editor��ύX�\�ȃR���{�p�G�f�B�^�N���X�ł��B
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
         * �G�f�B�^�R���|�[�l���g��ݒ肵�܂��B
         * 
         * @param editor �G�f�B�^�R���|�[�l���g
         */
        public void setEditorComponent(JTextField editor) {
            super.editor = editor;
        }
    }

}