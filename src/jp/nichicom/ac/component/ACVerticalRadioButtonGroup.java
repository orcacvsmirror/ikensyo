package jp.nichicom.ac.component;

import javax.swing.JRadioButton;

import jp.nichicom.vr.layout.VRLayout;

/**
 * �c�����ɓW�J���郉�W�I�O���[�v�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACClearableRadioButtonGroup
 */

public class ACVerticalRadioButtonGroup extends ACClearableRadioButtonGroup {
  public ACVerticalRadioButtonGroup() {
    super();
    setLayout(new VRLayout());
  }

  protected void addRadioButton(JRadioButton item) {
    this.add(item, VRLayout.FLOW_RETURN);
  }

}
