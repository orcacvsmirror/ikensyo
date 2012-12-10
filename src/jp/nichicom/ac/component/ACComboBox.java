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
 * ��s�w��⃌���_�[�o�C���h�p�X���ɑΉ������R���{�{�b�N�X�ł��B
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
     * ��I����\�����ڂ�Ԃ��܂��B
     * 
     * @return ��I����\������
     */
    public Object getBlankItem() {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            return ((ACComboBoxModelAdapter) getModel()).getBlankItem();
        }
        return blankItem;
    }

    /**
     * �o�C���h�p�X�Œ��o�����l�������ƈ�v���郂�f�����f�[�^��Ԃ��܂��B
     * 
     * @param bindData ��r�Ώ�
     * @throws ParseException ��͎��s
     * @return �����ƈ�v���郂�f�����f�[�^�B�Y�������Ȃ��null
     */
    public Object getDataFromBindPath(Object bindData) throws ParseException {
        int index = getIndexFromBindPath(bindData);
        if (index >= 0) {
            return getItemAt(index);
        }
        return null;
    }

    /**
     * �G�f�B�^��IME���[�h��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
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
     * �o�C���h�p�X�Œ��o�����l�������ƈ�v���郂�f�����f�[�^�̃C���f�b�N�X��Ԃ��܂��B
     * 
     * @param bindData ��r�Ώ�
     * @throws ParseException ��͎��s
     * @return �����ƈ�v���郂�f�����f�[�^�̃C���f�b�N�X�B�Y�������Ȃ��-1
     */
    public int getIndexFromBindPath(Object bindData) throws ParseException {
        String selBindPath = getBindPath();
        int end = getItemCount();
        if (bindData != null) {
            for (int i = 0; i < end; i++) {
                Object obj = getItemAt(i);
                if (obj instanceof VRBindSource) {
                    // �o�C���h�\�[�X�`���Ȃ�Ύw��L�[�̃f�[�^�Ŕ�r
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
                    // �o�C���h�\�[�X�`���Ȃ�Ύw��L�[�̃f�[�^�Ŕ�r
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
     * ���f����̍��ڂ�Ԃ��܂��B
     * <p>
     * Editable��true�̏ꍇ�A���f����̓��R�[�h�ł�<code>getItemAt</code>�͕������Ԃ��܂��BEditable�Ɋւ��Ȃ����f���̍��ڂ��擾����ꍇ��<code>getModelItem</code>���g���܂��B
     * </p>
     * 
     * @return �I�����Ă��郂�f����̍���
     */
    public Object getModelItem(int index) {
        ComboBoxModel model = getModel();
        if (model == null) {
            return null;
        }
        if (isUseRenderBindPath()) {
            // ���f���Ƀ��b�v���ꂽ���R�[�h�W�����g�p���Ă���ꍇ
            if (model instanceof VRBindSourceAdapter) {
                VRBindSourceAdapter adapter = (VRBindSourceAdapter) model;
                do {
                    if (adapter instanceof VRHashMapArrayToConstKeyArrayAdapter) {
                        // �}�b�v�̃��X�g���A�_�v�^�𔭌������ꍇ�A���X�g�����ꂽ�v�f�ł͂Ȃ��f�̗v�f����}�b�v��Ԃ�
                        return adapter.getAdaptee().getData(index);
                    }
                    if (adapter != null) {
                        if (adapter.getAdaptee() instanceof VRBindSourceAdapter) {
                            // ���b�v����Ă���ꍇ�͐[�x��[�߂�
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
     * �`��Ώۂ̃o�C���h�p�X��Ԃ��܂��B
     * 
     * @return �`��Ώۂ̃o�C���h�p�X
     */
    public String getRenderBindPath() {
        return getBindPathRenderer().getRenderBindPath();
    }

    /**
     * �I�����Ă��郂�f����̍��ڂ�Ԃ��܂��B
     * <p>
     * Editable��true�̏ꍇ�A���f����̓��R�[�h�ł�<code>getSelectedItem</code>�͕������Ԃ��܂��BEditable�Ɋւ��Ȃ����f���̍��ڂ��擾����ꍇ��<code>getSelectedModelItem</code>���g���܂��B
     * </p>
     * <p>
     * ���I���Ȃ��null��Ԃ��܂��B
     * </p>
     * 
     * @return �I�����Ă��郂�f����̍���
     */
    public Object getSelectedModelItem() {
        int index = getSelectedIndex();
        if (index < 0) {
            return null;
        }
        return getModelItem(index);
    }

    /**
     * �R���{�̓��͓��e�𕶎���ŕԂ��܂��B
     * 
     * @return �R���{�̓��͓��e
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
     * ��I���������邩��ݒ肵�܂��B
     * 
     * @return ��I���������邩
     */
    public boolean isBlankable() {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            return ((ACComboBoxModelAdapter) getModel()).isBlankable();
        }
        return blankable;
    }

    /**
     * �����_�[�o�C���h�p�X�ɂ��`����s�Ȃ�����Ԃ��܂��B
     * 
     * @return �����_�[�o�C���h�p�X�ɂ��`����s�Ȃ���
     */
    public boolean isUseRenderBindPath() {
        return (getRenderBindPath() != null)
                && (!"".equals(getRenderBindPath()));
    }

    /**
     * ��I���������邩��Ԃ��܂��B
     * 
     * @param blankable ��I���������邩
     */
    public void setBlankable(boolean blankable) {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            ((ACComboBoxModelAdapter) getModel()).setBlankable(blankable);
        }
        this.blankable = blankable;
    }

    /**
     * ��I����\�����ڂ�ݒ肵�܂��B
     * 
     * @param blankItem ��I����\������
     */
    public void setBlankItem(Object blankItem) {
        if (getModel() instanceof ACComboBoxModelAdapter) {
            ((ACComboBoxModelAdapter) getModel()).setBlankItem(blankItem);
        }
        this.blankItem = blankItem;
    }

    /**
     * �G�f�B�^��IME���[�h��ݒ肵�܂��B
     * 
     * @param imeMode �t�H�[�J�X�擾���Ɏ����ݒ肷��IME���[�h
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
     * �A�_�v�^�N���X�𐶐����AList���R���{���f���Ƃ��Đݒ肵�܂��B
     * 
     * @param model ���f��
     */
    public void setModel(List model) {
        setModel(new VRArrayList(model));
    }

    /**
     * �A�_�v�^�N���X�𐶐����A�z����R���{���f���Ƃ��Đݒ肵�܂��B
     * 
     * @param model ���f��
     */
    public void setModel(Object[] model) {
        setModel(Arrays.asList(model));
    }

    /**
     * �A�_�v�^�N���X�𐶐����AVRList���R���{���f���Ƃ��Đݒ肵�܂��B
     * 
     * @param model ���f��
     */
    public void setModel(VRList model) {
        setModel(new ACComboBoxModelAdapter(model));
    }

    /**
     * �`��Ώۂ̃o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param renderBindPath �`��Ώۂ̃o�C���h�p�X
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
     * �ߋ��̑I�����ڂ𖳎����ċ����I�ɍ��ڂ�I�������܂��B
     * 
     * @param anObject �I�����鍀��
     */
    public void setSelectedItemAbsolute(Object anObject) {
        selectedItemReminder = null;
        setSelectedItem(anObject);
    }

    /**
     * �R���{�̓��͓��e�𕶎���Őݒ肵�܂��B
     * 
     * @param text �R���{�̓��͓��e
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
     * �o�C���h�p�X�w��ɂ��`�惌���_����Ԃ��܂��B
     * 
     * @return �o�C���h�p�X�w��ɂ��`�惌���_��
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
     * �o�C���h�p�X�����_���ƍ����ς���������_����Ԃ��܂��B
     * 
     * @return �o�C���h�p�X�����_���ƍ����ς���������_��
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
                // 10.4������"Osaka"
                Font nowFont = getFont();
                if (nowFont == null) {
                    setFont(new Font("Osaka", Font.PLAIN, 12));
                } else {
                    setFont(new Font("Osaka", nowFont.getStyle(), nowFont
                            .getSize()));
                }
            } else {
                // 10.4�ȏ�͉������Ȃ�
            }
        }

    }

    /**
     * �o�C���h�p�X�����_���ƍ����ς���������_����ݒ肵�܂��B
     * 
     * @param setedRenderer �o�C���h�p�X�����_���ƍ����ς���������_��
     */
    protected void setSetedRenderer(ListCellRenderer setedRenderer) {
        this.setedRenderer = setedRenderer;
    }

    /**
     * ���ڂ�I�����Ă��邩��Ԃ��܂��B
     * 
     * @return ���ڂ�I�����Ă��邩
     */
    public boolean isSelected() {
        return getSelectedModelItem() != null;
    }

    /**
     * �I�����������܂��B
     */
    public void clearSelection() {
        setSelectedItem(null);
    }

    /**
     * �G�f�B�^�̃h�L�������g�I�u�W�F�N�g��Ԃ��܂��B
     * 
     * @return �h�L�������g�I�u�W�F�N�g
     */
    public Document getDocument() {
        AbstractVRTextField edit = getEditorField();
        if (edit == null) {
            return null;
        }
        return edit.getDocument();
    }

    /**
     * �G�f�B�^�̃h�L�������g�I�u�W�F�N�g��ݒ肵�܂��B
     * 
     * @return �h�L�������g�I�u�W�F�N�g
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
