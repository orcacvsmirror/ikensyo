package jp.or.med.orca.ikensho.component;

import javax.swing.text.Document;

import jp.nichicom.ac.component.ACIntegerTextField;

/** TODO <HEAD_IKENSYO> */
public class IkenshoInitialNegativeIntegerTextField extends ACIntegerTextField {
  public IkenshoInitialNegativeIntegerTextField() {
    super();
  }

  public IkenshoInitialNegativeIntegerTextField(Document doc, String text, int columns) {
    super(doc, text, columns);
  }

  public IkenshoInitialNegativeIntegerTextField(int columns) {
    super(columns);
  }

  public IkenshoInitialNegativeIntegerTextField(String text) {
    super(text);
  }

  public IkenshoInitialNegativeIntegerTextField(String text, int columns) {
    super(text, columns);
  }
  protected void initComponent() {
    super.initComponent();
    setCreateSourceValue(-1);
  }


}
