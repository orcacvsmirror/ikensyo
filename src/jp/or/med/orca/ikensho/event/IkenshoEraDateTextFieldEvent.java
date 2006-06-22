package jp.or.med.orca.ikensho.event;

import java.util.EventObject;

/** TODO <HEAD_IKENSYO> */
public class IkenshoEraDateTextFieldEvent extends EventObject {
    private boolean valid;
    private int state;

    public IkenshoEraDateTextFieldEvent(Object source, boolean valid, int state) {
        super(source);
        this.valid = valid;
        this.state = state;
    }

    public boolean isValid() {
        return valid;
    }

    public int getState() {
        return state;
    }
}
