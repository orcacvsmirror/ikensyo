/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.text.ParseException;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * �o�C���h�@�\�����������{�^���̊��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see JButton
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public abstract class AbstractVRButton extends JButton implements VRButtonar {

    private boolean autoApplySource = false;
    private String bindPath;
    private VRBindSource source;

    /**
     * Creates a button with no set text or icon.
     */
    public AbstractVRButton() {
        super();
        initComponent();
    }

    /**
     * Creates a button where properties are taken from the 
     * <code>Action</code> supplied.
     *
     * @param a the <code>Action</code> used to specify the new button
     *
     * @since 1.3
     */
    public AbstractVRButton(Action a) {
        super(a);
        initComponent();
    }

    /**
     * Creates a button with an icon.
     *
     * @param icon  the Icon image to display on the button
     */
    public AbstractVRButton(Icon icon) {
        super(icon);
        initComponent();
    }

    /**
     * Creates a button with text.
     *
     * @param text  the text of the button
     */
    public AbstractVRButton(String text) {
        super(text);
        initComponent();
    }

    /**
     * Creates a button with initial text and an icon.
     *
     * @param text  the text of the button
     * @param icon  the Icon image to display on the button
     */
    public AbstractVRButton(String text, Icon icon) {
        super(text, icon);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), getText())) {
            fireApplySource(new VRBindEvent(this));
        }
    }

    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), getSource())) {
            return;
        }

        Object obj = VRBindPathParser.get(getBindPath(), getSource());
        if (obj == null) {
            return;
        }
        super.setText(String.valueOf(obj));
        fireBindSource(new VRBindEvent(this));
    }

    public Object createSource() {
        return "";
    }

    /**
     * �o�C���h�C�x���g���X�i��Ԃ��܂��B
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
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

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }
    public void setText(String text) {
        super.setText(text);
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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