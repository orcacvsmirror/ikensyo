package jp.or.med.orca.ikensho.event;

import java.util.EventListener;

/** TODO <HEAD_IKENSYO> */
public interface IkenshoModifiedCheckTextFieldListener extends EventListener {
    public void textChanged(IkenshoModifiedCheckTextFieldEvent event);
}
