/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.text.Format;
import java.text.ParseException;

import javax.swing.Icon;
import javax.swing.JLabel;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.component.event.VRFormatEventListener;

/**
 * �o�C���h�@�\�������������x���̊��N���X�ł��B
 * <p>
 * Format�w��ɂ����͒l�̃t�H�[�}�b�g�ϊ��������������Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JLabel
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see VRFormatable
 * @see Format
 * @see VRFormatEventListener
 */
public abstract class AbstractVRLabel extends JLabel implements VRBindable,
        VRFormatable {
    private boolean autoApplySource = false;
    private String bindPath;
    private Format format;
    private Object model;
    private VRBindSource source;

    /**
     * Creates a <code>JLabel</code> instance with no image and with an empty
     * string for the title. The label is centered vertically in its display
     * area. The label's contents, once set, will be displayed on the leading
     * edge of the label's display area.
     */
    public AbstractVRLabel() {
        super();
        initComponent();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image. The
     * label is centered vertically and horizontally in its display area.
     * 
     * @param image The image to be displayed by the label.
     */
    public AbstractVRLabel(Icon image) {
        super(image);
        initComponent();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified image and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param image The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public AbstractVRLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        initComponent();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text. The
     * label is aligned against the leading edge of its display area, and
     * centered vertically.
     * 
     * @param text The text to be displayed by the label.
     */
    public AbstractVRLabel(String text) {
        super(text);
        initComponent();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text, image,
     * and horizontal alignment. The label is centered vertically in its display
     * area. The text is on the trailing edge of the image.
     * 
     * @param text The text to be displayed by the label.
     * @param icon The image to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public AbstractVRLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        initComponent();
    }

    /**
     * Creates a <code>JLabel</code> instance with the specified text and
     * horizontal alignment. The label is centered vertically in its display
     * area.
     * 
     * @param text The text to be displayed by the label.
     * @param horizontalAlignment One of the following constants defined in
     *            <code>SwingConstants</code>: <code>LEFT</code>,
     *            <code>CENTER</code>, <code>RIGHT</code>,
     *            <code>LEADING</code> or <code>TRAILING</code>.
     */
    public AbstractVRLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listenerList.add(VRBindEventListener.class, listener);
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
     */
    public void addFormatEventListener(VRFormatEventListener listener) {
        listenerList.add(VRFormatEventListener.class, listener);
    }

    public void applySource() throws ParseException {
        Format fmt = getFormat();
        if (fmt == null) {
            if (VRBindPathParser.set(getBindPath(), getSource(), getText())) {
                fireApplySource(new VRBindEvent(this));
            }
        } else {
            if (VRBindPathParser.set(getBindPath(), getSource(), getModel())) {
                fireApplySource(new VRBindEvent(this));
            }
        }

    }

    public void bindSource() throws ParseException {
        if (!VRBindPathParser.has(getBindPath(), getSource())) {
            return;
        }

        Format fmt = getFormat();
        if (fmt == null) {
            Object obj = VRBindPathParser.get(getBindPath(), getSource());

            // 2006/02/06[Tozo Tanaka] : replace begin
            // if (obj == null) {
            // return;
            // }
            // super.setText(String.valueOf(obj));
            if (obj == null) {
                super.setText("");
            } else {
                super.setText(String.valueOf(obj));
            }
            // 2006/02/06[Tozo Tanaka] : replace end
            fireBindSource(new VRBindEvent(this));
        } else {
            Object obj = VRBindPathParser.get(getBindPath(), getSource());

            if (obj == null) {
                super.setText(null);
                setModel(null);
            } else {
                super.setText(fmt.format(obj));
                setModel(obj);

            }

            fireBindSource(new VRBindEvent(this));
        }
    }

    public Object createSource() {
        if (getFormat() == null) {
            return "";
        } else {
            return null;
        }
    }

    public String getBindPath() {
        return bindPath;
    }

    /**
     * �t�H�[�}�b�g��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
     */
    public Format getFormat() {
        return this.format;
    }

    /**
     * ���f���f�[�^��Ԃ��܂��B
     * 
     * @return ���f���f�[�^
     */
    public Object getModel() {
        return model;
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

    /**
     * �t�H�[�}�b�g�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �t�H�[�}�b�g�C�x���g���X�i
     */
    public void removeFormatEventListener(VRFormatEventListener listener) {
        listenerList.remove(VRFormatEventListener.class, listener);
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

    /**
     * �t�H�[�}�b�g��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    public void setText(String text) {
        Format fmt = getFormat();
        if (fmt != null) {
            setModelValueOnFormat(text);
        } else {
            setModel(text);
            super.setText(text);
        }
        if (isAutoApplySource()) {
            try {
                applySource();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
     * �t�H�[�}�b�g�C�x���g���X�i��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g�C�x���g���X�i
     */
    public synchronized VRFormatEventListener[] getFormatEventListeners() {
        return (VRFormatEventListener[]) (getListeners(VRFormatEventListener.class));
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
     * �t�H�[�}�b�g�C�x���g���X�i��S��������formatError�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireFormatInvalid(VRFormatEvent e) {
        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatInvalid(e);
        }
    }

    /**
     * �t�H�[�}�b�g�C�x���g���X�i��S��������formatValid�C�x���g�𔭉΂��܂��B
     * 
     * @param e �C�x���g���
     */
    protected void fireFormatValid(VRFormatEvent e) {
        VRFormatEventListener[] listeners = getFormatEventListeners();
        for (int i = 0; i < listeners.length; i++) {
            listeners[i].formatValid(e);
        }
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {

    }

    /**
     * ���f���f�[�^��ݒ肵�܂��B
     * 
     * @param model ���f���f�[�^
     */
    protected void setModel(Object model) {
        this.model = model;
    }

    /**
     * ���f���Ƀt�H�[�}�b�^��ʂ��ăe�L�X�g��ݒ肵�܂��B
     * 
     * @param text �ݒ�l
     */
    protected void setModelValueOnFormat(String text) {
        Format fmt = getFormat();
        String val = text.trim();
        try {
            Object mdl = fmt.parseObject(val);
            String formated = fmt.format(mdl);

            Object old = getModel();
            setModel(mdl);

            super.setText(formated);
            // FormatValid�C�x���g����
            fireFormatValid(new VRFormatEvent(this, old, val));
        } catch (ParseException e) {
            // FormatError�C�x���g����
            Object old = getModel();
            fireFormatInvalid(new VRFormatEvent(this, old, val));
        }
    }

}