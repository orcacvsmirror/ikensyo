/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionListener;

public abstract class AbstractIkenshoAffairContainerActionAdapter implements ActionListener {
    protected IkenshoAffairContainer adaptee;
    protected boolean lockFlag = false;

}
