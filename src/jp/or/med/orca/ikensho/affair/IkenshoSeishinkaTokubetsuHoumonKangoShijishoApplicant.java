package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaTokubetsuHoumonKangoShijishoApplicant extends IkenshoTokubetsuHoumonKangoShijishoApplicant {
    
    /**
     * 指示書種類キャプション名取得処理
     * @return 指示書種類キャプション名
     */
    protected String getHoumonSijiSyoText() {
        return "精神科特別訪問看護指示書";
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End