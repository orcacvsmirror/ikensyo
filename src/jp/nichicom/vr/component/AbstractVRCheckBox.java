/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.text.ParseException;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * �o�C���h�@�\�����������`�F�b�N�{�b�N�X�̊��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see JCheckBox
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 */
public abstract class AbstractVRCheckBox extends JCheckBox implements
        VRBindable {

    protected boolean autoApplySource = false;

    protected String bindPath;

    protected VRBindSource source;

    /**
     * Creates an initially unselected check box button with no text, no icon.
     */
    public AbstractVRCheckBox() {
        super();
        initComponent();
    }

    /**
     * Creates a check box where properties are taken from the Action supplied.
     * 
     * @since 1.3
     */
    public AbstractVRCheckBox(Action a) {
        super(a);
        initComponent();
    }

    /**
     * Creates an initially unselected check box with an icon.
     * 
     * @param icon the Icon image to display
     */
    public AbstractVRCheckBox(Icon icon) {
        super(icon);
        initComponent();
    }

    /**
     * Creates a check box with an icon and specifies whether or not it is
     * initially selected.
     * 
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */

    public AbstractVRCheckBox(Icon icon, boolean selected) {
        super(icon, selected);
        initComponent();
    }

    /**
     * Creates an initially unselected check box with text.
     * 
     * @param text the text of the check box.
     */

    public AbstractVRCheckBox(String text) {
        super(text);
        initComponent();
    }

    /**
     * Creates a check box with text and specifies whether or not it is
     * initially selected.
     * 
     * @param text the text of the check box.
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public AbstractVRCheckBox(String text, boolean selected) {
        super(text, selected);
        initComponent();
    }

    /**
     * Creates an initially unselected check box with the specified text and
     * icon.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     */
    public AbstractVRCheckBox(String text, Icon icon) {
        super(text, icon);
        initComponent();
    }

    /**
     * Creates a check box with text and icon, and specifies whether or not it
     * is initially selected.
     * 
     * @param text the text of the check box.
     * @param icon the Icon image to display
     * @param selected a boolean value indicating the initial selection state.
     *            If <code>true</code> the check box is selected
     */
    public AbstractVRCheckBox(String text, Icon icon, boolean selected) {
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
     * 
     * @return �o�C���h�C�x���g���X�i
     */
    public synchronized VRBindEventListener[] getBindEventListeners() {
        return (VRBindEventListener[]) (getListeners(VRBindEventListener.class));
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������applySource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireApplySource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].applySource(e);
        }
    }

    /**
     * �o�C���h�C�x���g���X�i��S��������bindSource�C�x���g���Ăяo���܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireBindSource(VRBindEvent e) {
        VRBindEventListener[] listeners = getBindEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].bindSource(e);
        }
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {

    }
}