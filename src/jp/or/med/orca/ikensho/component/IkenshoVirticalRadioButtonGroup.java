package jp.or.med.orca.ikensho.component;

import javax.swing.JRadioButton;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.vr.layout.VRLayout;

// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoVirticalRadioButtonGroup extends
        ACClearableRadioButtonGroup {
    public IkenshoVirticalRadioButtonGroup() {
        super();
        setLayout(new VRLayout());
    }

    protected void addRadioButton(JRadioButton item) {
        this.add(item, VRLayout.FLOW_RETURN);
    }

}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
