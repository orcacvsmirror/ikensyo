package jp.or.med.orca.ikensho.component;

import java.beans.PropertyEditorSupport;

public class IkenshoEraDateTextFieldRequestedRangeEditor
    extends PropertyEditorSupport {
  private static String[] resourceStrings = {
      "<なし>",
      "元号まで必須",
      "年まで必須",
      "月まで必須",
      "日まで必須",
  };
  private static int[] intValues = {
      0,
      IkenshoEraDateTextField.RNG_ERA,
      IkenshoEraDateTextField.RNG_YEAR,
      IkenshoEraDateTextField.RNG_MONTH,
      IkenshoEraDateTextField.RNG_DAY,
  };
  private static String[] sourceCodeStrings = {
      "\"\"",
      "DatePanel.RNG_ERA",
      "DatePanel.RNG_YEAR",
      "DatePanel.RNG_MONTH",
      "DatePanel.RNG_DAY",
  };

  public IkenshoEraDateTextFieldRequestedRangeEditor() {
  }

  public String[] getTags() {
    return resourceStrings;
  }

  public String getJavaInitializationString() {
    Object value = getValue();
    for (int i = 0; i < intValues.length; i++) {
      if (value.equals(new Integer(intValues[i]))) {
        return sourceCodeStrings[i];
      }
    }
    return null;
  }

  public String getAsText() {
    Object value = getValue();
    for (int i = 0; i < intValues.length; i++) {
      if (value.equals(new Integer(intValues[i]))) {
        return resourceStrings[i];
      }
    }
    return null;
  }

  public void setAsText(String text) throws IllegalArgumentException {
    for (int i = 0; i < resourceStrings.length; i++) {
      if (text.equals(resourceStrings[i])) {
        setValue(new Integer(intValues[i]));
        return;
      }
    }
    throw new IllegalArgumentException();
  }
}
