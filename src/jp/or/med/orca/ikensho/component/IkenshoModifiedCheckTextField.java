package jp.or.med.orca.ikensho.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import jp.nichicom.ac.component.ACTextField;
import jp.or.med.orca.ikensho.event.IkenshoModifiedCheckTextFieldEvent;
import jp.or.med.orca.ikensho.event.IkenshoModifiedCheckTextFieldListener;

/** TODO <HEAD_IKENSYO> */
/**
 * <p>�ύX�Ď��e�L�X�g�t�B�[���h</p>
 * <p>Create : 2005/10/31</p>
 * <p>Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.</p>
 * @author Mizuki Tsutsumi
 * @version 1.0
 */
public class IkenshoModifiedCheckTextField extends ACTextField implements KeyListener {
    private String oldValue = "";

    public IkenshoModifiedCheckTextField() {
        oldValue = this.getText();
        addKeyListener(this);
    }

    public void setText(String str) {
        super.setText(str);
        checkTextChange(str); //�X�V�`�F�b�N
    }

    /**
     * �V���̒l���r���A�X�V���ꂽ���ǂ������`�F�b�N����B<br />
     * �X�V����Ă���΁ATextChanged�C�x���g�𔭍s����B
     * @param newValue �V�����l
     */
    private void checkTextChange(String newValue) {
        if (!oldValue.equals(newValue)) {
            oldValue = newValue;
            fireTextChanged(new IkenshoModifiedCheckTextFieldEvent(this));
        }
    }

    transient Vector modiliedCheckTextFieldListeners = new Vector();
    public synchronized void addModiliedCheckTextFieldListener(IkenshoModifiedCheckTextFieldListener l) {
        modiliedCheckTextFieldListeners.add(l);
    }
    public synchronized void removeModiliedCheckTextFieldListener(IkenshoModifiedCheckTextFieldListener l) {
        modiliedCheckTextFieldListeners.remove(l);
    }
    protected void fireTextChanged(IkenshoModifiedCheckTextFieldEvent event) {
        if (modiliedCheckTextFieldListeners != null) {
            Vector listeners = modiliedCheckTextFieldListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ( (IkenshoModifiedCheckTextFieldListener) listeners.elementAt(i)).textChanged(event);
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
        checkTextChange(this.getText()); //�X�V�`�F�b�N
    }
}
