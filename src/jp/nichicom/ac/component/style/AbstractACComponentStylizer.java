package jp.nichicom.ac.component.style;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * コンポーネントのスタイルを変更するクラスです。
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
     * コンストラクタです。
     */
    public AbstractACComponentStylizer() {
        super();
    }

    /**
     * 条件付スタイルをクリアします。
     */
    public void clearCondition() {
        getConditionStyle().clear();
    }

    /**
     * 条件付スタイルを追加します。
     * 
     * @param condition 適用条件
     * @param style スタイル
     */
    public void addCondition(ACComponentStylizeCondition condition,
            AbstractACComponentStylizer style) {
        getConditionStyle().put(condition, style);
    }

    /**
     * 条件に合致するスタイルがあるかを返します。
     * 
     * @param condition 適用条件
     * @return スタイル
     */
    public boolean hasCondition(ACComponentStylizeCondition condition) {
        return getConditionStyle().containsKey(condition);
    }

    /**
     * 条件付きスタイルを除去します。
     * 
     * @param condition 適用条件
     */
    public void removeCondition(ACComponentStylizeCondition condition) {
        getConditionStyle().remove(condition);
    }

    /**
     * 条件集合を返します。
     * <p>
     * 返却した条件集合を編集しても、内部で保持した条件に逆反映はされません。
     * </p>
     * 
     * @return 条件集合
     */
    public List getConditions() {
        return new ArrayList(Arrays.asList(getConditionStyle().keySet()
                .toArray()));
    }

    /**
     * 条件付スタイルマップを返します。
     * 
     * @return 条件付スタイルマップ
     */
    protected Map getConditionStyle() {
        return conditionStyle;
    }

    /**
     * コンポーネントにスタイルを適用します。
     * @param comp コンポーネント
     * @param value 値
     * @return スタイル適用後のコンポーネント
     */
    public Component stylize(Component comp, Object value) {
        AbstractACComponentStylizer style = this;
        Iterator it = getConditionStyle().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry ent = (Map.Entry) it.next();
            ACComponentStylizeCondition condition = (ACComponentStylizeCondition) ent
                    .getKey();
            if (condition.isMatch(value)) {
                // 下位条件がなくなるまで再帰する
                return ((AbstractACComponentStylizer) ent.getValue()).stylize(comp,
                        value);
            }
        }
        return style.stylizeImpl(comp);
    }

    /**
     * コンポーネントにスタイルを適用します。
     * @param comp コンポーネント
     * @return スタイル適用後のコンポーネント
     */
    protected abstract Component stylizeImpl(Component comp);

}
