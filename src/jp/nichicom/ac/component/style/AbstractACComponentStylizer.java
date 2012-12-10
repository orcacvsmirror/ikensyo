package jp.nichicom.ac.component.style;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * �R���|�[�l���g�̃X�^�C����ύX����N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACComponentStylizeCondition
 */
public abstract class AbstractACComponentStylizer implements ACComponentStylizer{
    private Map conditionStyle = new LinkedHashMap();

    /**
     * �R���X�g���N�^�ł��B
     */
    public AbstractACComponentStylizer() {
        super();
    }

    /**
     * �����t�X�^�C�����N���A���܂��B
     */
    public void clearCondition() {
        getConditionStyle().clear();
    }

    /**
     * �����t�X�^�C����ǉ����܂��B
     * 
     * @param condition �K�p����
     * @param style �X�^�C��
     */
    public void addCondition(ACComponentStylizeCondition condition,
            AbstractACComponentStylizer style) {
        getConditionStyle().put(condition, style);
    }

    /**
     * �����ɍ��v����X�^�C�������邩��Ԃ��܂��B
     * 
     * @param condition �K�p����
     * @return �X�^�C��
     */
    public boolean hasCondition(ACComponentStylizeCondition condition) {
        return getConditionStyle().containsKey(condition);
    }

    /**
     * �����t���X�^�C�����������܂��B
     * 
     * @param condition �K�p����
     */
    public void removeCondition(ACComponentStylizeCondition condition) {
        getConditionStyle().remove(condition);
    }

    /**
     * �����W����Ԃ��܂��B
     * <p>
     * �ԋp���������W����ҏW���Ă��A�����ŕێ����������ɋt���f�͂���܂���B
     * </p>
     * 
     * @return �����W��
     */
    public List getConditions() {
        return new ArrayList(Arrays.asList(getConditionStyle().keySet()
                .toArray()));
    }

    /**
     * �����t�X�^�C���}�b�v��Ԃ��܂��B
     * 
     * @return �����t�X�^�C���}�b�v
     */
    protected Map getConditionStyle() {
        return conditionStyle;
    }

    /**
     * �R���|�[�l���g�ɃX�^�C����K�p���܂��B
     * @param comp �R���|�[�l���g
     * @param value �l
     * @return �X�^�C���K�p��̃R���|�[�l���g
     */
    public Component stylize(Component comp, Object value) {
        AbstractACComponentStylizer style = this;
        Iterator it = getConditionStyle().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry ent = (Map.Entry) it.next();
            ACComponentStylizeCondition condition = (ACComponentStylizeCondition) ent
                    .getKey();
            if (condition.isMatch(value)) {
                // ���ʏ������Ȃ��Ȃ�܂ōċA����
                return ((AbstractACComponentStylizer) ent.getValue()).stylize(comp,
                        value);
            }
        }
        return style.stylizeImpl(comp);
    }

    /**
     * �R���|�[�l���g�ɃX�^�C����K�p���܂��B
     * @param comp �R���|�[�l���g
     * @return �X�^�C���K�p��̃R���|�[�l���g
     */
    protected abstract Component stylizeImpl(Component comp);

}
