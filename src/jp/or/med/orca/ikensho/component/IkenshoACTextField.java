package jp.or.med.orca.ikensho.component;

import javax.swing.text.Document;

import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.component.AbstractVRTextField;

public class IkenshoACTextField extends ACTextField {

    public IkenshoACTextField() {
    }

    public IkenshoACTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public IkenshoACTextField(int columns) {
        super(columns);
    }

    public IkenshoACTextField(String text) {
        super(text);
    }

    public IkenshoACTextField(String text, int columns) {
        super(text, columns);
    }

    protected AbstractVRTextField createEditorField() {
        return new IkenshoACTextField();
    }
    public void setModelForce(Object model) {
        setModel(model);
    }
}
