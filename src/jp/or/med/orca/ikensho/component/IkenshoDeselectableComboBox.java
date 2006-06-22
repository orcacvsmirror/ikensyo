package jp.or.med.orca.ikensho.component;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import jp.nichicom.ac.component.ACComboBox;


/** TODO <HEAD_IKENSYO> */
public class IkenshoDeselectableComboBox
    extends ACComboBox {
  protected ComboBoxModel model;
  public IkenshoDeselectableComboBox() {
    super();
  }

  public IkenshoDeselectableComboBox(ComboBoxModel aModel) {
    super(aModel);
  }

  public IkenshoDeselectableComboBox(Object[] items) {
    super(items);
  }

  public IkenshoDeselectableComboBox(Vector items) {
    super(items);
  }

  public void setModel(ComboBoxModel model) {
    this.model = model;
    super.setModel(new IkenshoDeselectableComboBoxModelAdapter(model));
  }

  public ComboBoxModel getModel() {
    return model;
  }

  protected class IkenshoDeselectableComboBoxModelAdapter
      implements ComboBoxModel {
    protected ComboBoxModel adaptee;
    protected final String BLANK_ITEM = "";
    protected boolean blankSelected = false;

    public IkenshoDeselectableComboBoxModelAdapter(ComboBoxModel adaptee) {
      this.adaptee = adaptee;
    }

    public Object getSelectedItem() {
      if(blankSelected){
        return BLANK_ITEM;
      }
      return adaptee.getSelectedItem();
    }

    public void setSelectedItem(Object anItem) {
      if(BLANK_ITEM.equals(anItem)){
        blankSelected = true;
        return;
      }
      blankSelected = false;

//      if("".equals(anItem)){
//        anItem = null;
//      }
      adaptee.setSelectedItem(anItem);
    }

    public int getSize() {
      int size = adaptee.getSize();
      if (size == 0) {
        return 0;
      }
      return size + 1;
    }

    public Object getElementAt(int index) {
      if (index < 0) {
        return null;
      }
      if (index == 0) {
        return BLANK_ITEM;
      }
      return adaptee.getElementAt(index - 1);
    }

    public void addListDataListener(ListDataListener l) {
      adaptee.addListDataListener(l);
    }

    public void removeListDataListener(ListDataListener l) {
      adaptee.removeListDataListener(l);
    }
  }
}
