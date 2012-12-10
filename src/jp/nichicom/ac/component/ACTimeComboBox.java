package jp.nichicom.ac.component;

import java.util.Date;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import jp.nichicom.ac.text.ACTimeFormat;
import jp.nichicom.vr.component.AbstractVRTextField;

/**
 * �����p�ɐݒ肵���R���{�{�b�N�X�ł��B
 * <p>
 * �����Ƃ��ĕs�K�؂Ȓl����͂����ꍇ�A�e�R���e�i�𑖍����Ē��F���܂��B
 * </p>
 * <p>
 * ���E���̓��͂�ΏۂƂ��Ă���A�N������b�̒l�͕ۏ؂��܂���B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACComboBox
 * @see ACTextField
 * @see ACTimeFormat
 */
public class ACTimeComboBox extends ACComboBox {

    /**
     * Creates a <code>JComboBox</code> with a default data model. The default
     * data model is an empty list of objects. Use <code>addItem</code> to add
     * items. By default the first item in the data model becomes selected.
     * 
     * @see DefaultComboBoxModel
     */
    public ACTimeComboBox() {
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
    public ACTimeComboBox(ComboBoxModel aModel) {
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
    public ACTimeComboBox(Object[] items) {
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
    public ACTimeComboBox(Vector items) {
        super(items);
    }

    /**
     * ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t
     */
    public Date getBaseDate() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getBaseDate();
        }
        return null;
    }

    /**
     * ���͒l��Date�^�Ŏ擾���܂��B
     * 
     * @return ���͒l
     * @throws Exception ������O
     */
    public Date getDate() throws Exception {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getDate();
        }
        return null;
    }

    /**
     * �o�͏��� ��Ԃ��܂��B
     * 
     * @return �o�͏���
     */
    public String getFormatedFormat() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getFormatedFormat();
        }
        return null;
    }

    /**
     * �G���[���Ɍ�������e�K�w�̐���Ԃ��܂��B
     * 
     * @return �G���[���Ɍ�������e�K�w
     */
    public int getParentFindCount() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getParentFindCount();
        }
        return 0;
    }

    /**
     * ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏��� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏���
     */
    public String getPargedFormat() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getPargedFormat();
        }
        return null;
    }

    /**
     * ��͌��ʂ̌^ ��Ԃ��܂��B
     * <p>
     * VALUE_TYPE_STRING : ������<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @return ��͌��ʂ̌^
     */
    public int getParsedValueType() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getParsedValueType();
        }
        return 0;
    }

    /**
     * �����͂������邩 ��Ԃ��܂��B
     * 
     * @return �����͂������邩
     */
    public boolean isAllowedBlank() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).isAllowedBlank();
        }
        return false;
    }

    /**
     * �L���ȓ��t�����͂���Ă��邩��Ԃ��܂��B
     * 
     * @return �L���ȓ��t�����͂���Ă��邩
     */
    public boolean isValidDate() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).isValidDate();
        }
        return false;
    }

    /**
     * �����͂������邩 ��ݒ肵�܂��B
     * 
     * @param allowedBlank �����͂������邩
     */
    public void setAllowedBlank(boolean allowedBlank) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setAllowedBlank(allowedBlank);
        }
    }

    /**
     * ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t ��ݒ肵�܂��B
     * 
     * @param baseDate ��͌��ʂ�Date�^�Ƃ���ꍇ�Ɋ�ƂȂ���t
     */
    public void setBaseDate(Date baseDate) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setBaseDate(baseDate);
        }
    }

    /**
     * ���͒l��Date�^�Őݒ肵�܂��B
     * 
     * @param value ���͒l
     * @throws Exception ������O
     */
    public void setDate(Date value) throws Exception {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setDate(value);
        }
    }

    /**
     * �o�͏��� ��ݒ肵�܂��B
     * 
     * @param formatedFormat �o�͏���
     */
    public void setFormatedFormat(String formatedFormat) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField())
                    .setFormatedFormat(formatedFormat);
        }
    }

    /**
     * �G���[���Ɍ�������e�K�w�̐���ݒ肵�܂��B
     * 
     * @param parentFindCount �G���[���Ɍ�������e�K�w
     */
    public void setParentFindCount(int parentFindCount) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField())
                    .setParentFindCount(parentFindCount);
        }
    }

    /**
     * ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏��� ��ݒ肵�܂��B
     * 
     * @param pargedFormat ��͌��ʂ𕶎���Ƃ���ꍇ�̏o�͏���
     */
    public void setPargedFormat(String pargedFormat) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setPargedFormat(pargedFormat);
        }
    }

    /**
     * ��͌��ʂ̌^ ��ݒ肵�܂��B
     * <p>
     * VALUE_TYPE_STRING : ������<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @param parsedValueType ��͌��ʂ̌^
     */
    public void setParsedValueType(int parsedValueType) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField())
                    .setParsedValueType(parsedValueType);
        }
    }

    protected AbstractVRTextField createEditorField() {
        return new ACTimeTextField();
    }
}
