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

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaTokubetsuHoumonKangoShijishoApplicant extends IkenshoTokubetsuHoumonKangoShijishoApplicant {
    
    /**
     * �w������ރL���v�V�������擾����
     * @return �w������ރL���v�V������
     */
    protected String getHoumonSijiSyoText() {
        return "���_�ȓ��ʖK��Ō�w����";
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End