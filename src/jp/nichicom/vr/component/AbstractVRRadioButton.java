/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.text.ParseException;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * �o�C���h�@�\�������������W�I�{�^���̊��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see JRadioButton
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public abstract class AbstractVRRadioButton extends JRadioButton implements
        VRBindable {
    protected boolean autoApplySource = false;

    protected String bindPath;

    protected VRBindSource source;

    public AbstractVRRadioButton() {
        super();
        initComponent();
    }

    public AbstractVRRadioButton(Action a) {
        super(a);
        initComponent();
    }

    public AbstractVRRadioButton(Icon icon) {
        super(icon);
        initComponent();
    }

    public AbstractVRRadioButton(Icon icon, boolean selected) {
        super(icon, selected);
        initComponent();
    }

    public AbstractVRRadioButton(String text) {
        super(text);
        initComponent();
    }

    public AbstractVRRadioButton(String text, boolean selected) {
        super(text, selected);
        initComponent();
    }

    public AbstractVRRadioButton(String text, Icon icon) {
        super(text, icon);
        initComponent();
    }

    public AbstractVRRadioButton(String text, Icon icon, boolean selected) {
        super(text, icon, selected);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), new Boolean(super
                .isSelected()))) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), getSource())) {
            return;
        }
        Object obj = VRBindPathParser.get(getBindPath(), getSource());

        boolean val;
        if (obj == null) {
            return;
        } else if (obj instanceof Boolean) {
            val = ((Boolean) obj).booleanValue();
        } else {
            val = Boolean.valueOf(String.valueOf(obj)).booleanValue();
        }

        super.setSelected(val);
        fireBindSource(new VRBindEvent(this));
    }

    public Object createSource() {
        return new Boolean(false);
    }

    public String getBindPath() {
        return bindPath;
    }

    public VRBindSource getSource() {
        return source;
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listenerList.remove(VRBindEventListener.class, listener);
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    /**
     * �o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     */
    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }
    /**
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * @param e �C�x���g���
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].applySource(e);
        }
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������bindSource�C�x���g���Ăяo���܂��B
     * @param e �C�x���g���
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for(int i=0; i<listeners.length; i++){
            listeners[i].bindSource(e);
        }
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {

    }
}