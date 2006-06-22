package jp.or.med.orca.ikensho.component;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/** TODO <HEAD_IKENSYO> */
public class IkenshoEraDateTextFieldBeanInfo extends SimpleBeanInfo {
  Class beanClass = IkenshoEraDateTextField.class;
  String iconColor16x16Filename;
  String iconColor32x32Filename;
  String iconMono16x16Filename;
  String iconMono32x32Filename;

  public IkenshoEraDateTextFieldBeanInfo() {
  }
  public PropertyDescriptor[] getPropertyDescriptors() {
    try {
      PropertyDescriptor _age = new PropertyDescriptor("age", beanClass, "getAge", "setAge");
      PropertyDescriptor _ageVisible = new PropertyDescriptor("ageVisible", beanClass, "isAgeVisible", "setAgeVisible");
      PropertyDescriptor _allowedUnknown = new PropertyDescriptor("allowedUnknown", beanClass, "isAllowedUnknown", "setAllowedUnknown");
      PropertyDescriptor _autoApplySource = new PropertyDescriptor("autoApplySource", beanClass, "isAutoApplySource", "setAutoApplySource");
      PropertyDescriptor _bindPath = new PropertyDescriptor("bindPath", beanClass, "getBindPath", "setBindPath");
      PropertyDescriptor _childrenFocusable = new PropertyDescriptor("childrenFocusable", beanClass, "isChildrenFocusable", "setChildrenFocusable");
      PropertyDescriptor _date = new PropertyDescriptor("date", beanClass, "getDate", "setDate");
      PropertyDescriptor _day = new PropertyDescriptor("day", beanClass, "getDay", "setDay");
      PropertyDescriptor _dayVisible = new PropertyDescriptor("dayVisible", beanClass, "isDayVisible", "setDayVisible");
      PropertyDescriptor _editable = new PropertyDescriptor("editable", beanClass, "isEditable", "setEditable");
      PropertyDescriptor _enabled = new PropertyDescriptor("enabled", beanClass, "isEnabled", "setEnabled");
      PropertyDescriptor _era = new PropertyDescriptor("era", beanClass, "getEra", "setEra");
      PropertyDescriptor _inputStatus = new PropertyDescriptor("inputStatus", beanClass, "getInputStatus", null);
      PropertyDescriptor _month = new PropertyDescriptor("month", beanClass, "getMonth", "setMonth");
      PropertyDescriptor _monthVisible = new PropertyDescriptor("monthVisible", beanClass, "isMonthVisible", "setMonthVisible");
      PropertyDescriptor _requestedRange = new PropertyDescriptor("requestedRange", beanClass, "getRequestedRange", "setRequestedRange");
      PropertyDescriptor _source = new PropertyDescriptor("source", beanClass, "getSource", "setSource");
      PropertyDescriptor _year = new PropertyDescriptor("year", beanClass, "getYear", "setYear");
      PropertyDescriptor _yearVisible = new PropertyDescriptor("yearVisible", beanClass, "isYearVisible", "setYearVisible");
      _requestedRange.setPropertyEditorClass(IkenshoEraDateTextFieldRequestedRangeEditor.class);
      PropertyDescriptor[] pds = new PropertyDescriptor[] {
        _age,
        _ageVisible,
        _allowedUnknown,
        _autoApplySource,
        _bindPath,
        _childrenFocusable,
        _date,
        _day,
        _dayVisible,
        _editable,
        _enabled,
        _era,
        _inputStatus,
        _month,
        _monthVisible,
        _requestedRange,
        _source,
        _year,
        _yearVisible};
      return pds;
    }
    catch(IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }
  public java.awt.Image getIcon(int iconKind) {
    switch (iconKind) {
      case BeanInfo.ICON_COLOR_16x16:
        return iconColor16x16Filename != null ? loadImage(iconColor16x16Filename) : null;
      case BeanInfo.ICON_COLOR_32x32:
        return iconColor32x32Filename != null ? loadImage(iconColor32x32Filename) : null;
      case BeanInfo.ICON_MONO_16x16:
        return iconMono16x16Filename != null ? loadImage(iconMono16x16Filename) : null;
      case BeanInfo.ICON_MONO_32x32:
        return iconMono32x32Filename != null ? loadImage(iconMono32x32Filename) : null;
    }
    return null;
  }
  public BeanInfo[] getAdditionalBeanInfo() {
    Class superclass = beanClass.getSuperclass();
    try {
      BeanInfo superBeanInfo = Introspector.getBeanInfo(superclass);
      return new BeanInfo[] { superBeanInfo };
    }
    catch(IntrospectionException ex) {
      ex.printStackTrace();
      return null;
    }
  }
}
