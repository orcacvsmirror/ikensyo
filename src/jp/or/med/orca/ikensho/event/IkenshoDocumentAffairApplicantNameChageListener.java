package jp.or.med.orca.ikensho.event;

import java.util.EventListener;
import java.util.EventObject;

/** TODO <HEAD_IKENSYO> */
public interface IkenshoDocumentAffairApplicantNameChageListener extends EventListener {
  public void nameChanged(EventObject e);
}
