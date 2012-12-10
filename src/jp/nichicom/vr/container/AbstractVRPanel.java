/** TODO <HEAD> */
package jp.nichicom.vr.container;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.VRBindableContainer;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindModelEventListener;
import jp.nichicom.vr.util.VRHashMap;

/**
 * �o�C���h�@�\�����������p�l���̊��N���X�ł��B
 * <p>
 * ���f���o�C���h�@�\���������Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see JPanel
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindableContainer
 * @see VRBindSource
 * @see VRBindModelable
 * @see VRBindModelEventListener
 */
public class AbstractVRPanel extends JPanel implements VRBindableContainer {
    private VRBindSource modelSource;
    private VRBindSource source;

    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public AbstractVRPanel() {
        super();
        initComponent();
    }

    /**
     * Creates a new <code>JPanel</code> with <code>FlowLayout</code>
     * and the specified buffering strategy.
     * If <code>isDoubleBuffered</code> is true, the <code>JPanel</code>
     * will use a double buffer.
     *
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free 
     *        updates
     */
    public AbstractVRPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        initComponent();
    }

    /**
     * Create a new buffered JPanel with the specified layout manager
     *
     * @param layout  the LayoutManager to use
     */
    public AbstractVRPanel(LayoutManager layout) {
        super(layout);
        initComponent();
    }

    /**
     * Creates a new JPanel with the specified layout manager and buffering
     * strategy.
     *
     * @param layout  the LayoutManager to use
     * @param isDoubleBuffered  a boolean, true for double-buffering, which
     *        uses additional memory space to achieve fast, flicker-free 
     *        updates
     */
    public AbstractVRPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        initComponent();
    }

    public void addBindEventListener(VRBindEventListener listener) {
        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            comp.addBindEventListener(listener);
        }
    }

    public void addBindModelEventListener(VRBindModelEventListener listener) {
        ArrayList coms = getModelBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindModelable comp = (VRBindModelable) it.next();
            comp.addBindModelEventListener(listener);
        }
    }

    public void applyModelSource() throws ParseException {
        ArrayList coms = getModelBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindModelable comp = (VRBindModelable) it.next();
            comp.applyModelSource();
        }
    }

    public void applySource() throws ParseException {
        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            comp.applySource();
        }
    }

    public void bindModelSource() throws ParseException {
        ArrayList coms = getModelBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindModelable comp = (VRBindModelable) it.next();
            comp.bindModelSource();
        }
    }

    public void bindSource() throws ParseException {
        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            comp.bindSource();
        }
    }

    public Object createModelSource() {
        VRBindSource bs = new VRHashMap();

        ArrayList coms = getModelBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindModelable comp = (VRBindModelable) it.next();
            bs.setData(comp.getModelBindPath(), comp.createModelSource());
        }

        return bs;
    }

    public Object createSource() {
        VRBindSource bs = new VRHashMap();

        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            bs.setData(comp.getBindPath(), comp.createSource());
        }

        return bs;
    }

    public String getBindPath() {
        return null;
    }

    public String getModelBindPath() {
        return null;
    }

    public VRBindSource getModelSource() {
        return modelSource;
    }

    public VRBindSource getSource() {
        return source;
    }

    public boolean isAutoApplySource() {
        return false;
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            comp.removeBindEventListener(listener);
        }
    }

    public void removeBindModelEventListener(VRBindModelEventListener listener) {
        ArrayList coms = getModelBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindModelable comp = (VRBindModelable) it.next();
            comp.removeBindModelEventListener(listener);
        }
    }

    public void setAutoApplySource(boolean autoApplySource) {
        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            comp.setAutoApplySource(autoApplySource);
        }
    }

    /**
     * �o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     */
    public void setBindPath(String bindPath) {
    }

    /**
     * ���f���o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param modelBindPath ���f���o�C���h�p�X
     */
    public void setModelBindPath(String modelBindPath) {
    }

    public void setModelSource(VRBindSource modelSource) {
        ArrayList coms = getModelBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindModelable comp = (VRBindModelable) it.next();
            comp.setModelSource(modelSource);
        }
        this.modelSource = modelSource;
    }

    public void setSource(VRBindSource source) {
        ArrayList coms = getBindableComponents();
        Iterator it = coms.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            comp.setSource(source);
        }
        this.source = source;
    }

    /**
     * �o�C���h�Ώۂ̕ێ��R���|�[�l���g��񋓂��Ēǉ����܂��B
     * 
     * @param array �ǉ���
     * @param container �񋓃R���e�i
     */
    protected void appendBindableComponents(ArrayList array, Container container) {

        int compSize = container.getComponentCount();
        for (int i = 0; i < compSize; i++) {

            Component comp = container.getComponent(i);
            if (comp instanceof VRBindable) {
                String path = ((VRBindable) comp).getBindPath();
                if ((path != null) && (!"".equals(path))) {
                    array.add(comp);
                    continue;
                }
            }
            if (comp instanceof Container) {
                appendBindableComponents(array, (Container) comp);
            }
        }

    }

    /**
     * ���f���o�C���h�Ώۂ̕ێ��R���|�[�l���g��񋓂��Ēǉ����܂��B
     * 
     * @param array �ǉ���
     * @param container �񋓃R���e�i
     */
    protected void appendModelBindableComponents(ArrayList array,
            Container container) {

        int compSize = container.getComponentCount();
        for (int i = 0; i < compSize; i++) {

            Component comp = container.getComponent(i);
            if (comp instanceof VRBindModelable) {
                String path = ((VRBindModelable) comp).getModelBindPath();
                if ((path != null) && (!"".equals(path))) {
                    array.add(comp);
                    continue;
                }
            }
            if (comp instanceof Container) {
                appendModelBindableComponents(array, (Container) comp);
            }
        }

    }

    /**
     * �o�C���h�Ώۂ̕ێ��R���|�[�l���g��񋓂��܂��B
     * 
     * @return �R���|�[�l���g�W��
     */
    protected ArrayList getBindableComponents() {
        ArrayList array = new ArrayList();
        appendBindableComponents(array, this);
        return array;
    }

    /**
     * ���f���o�C���h�Ώۂ̕ێ��R���|�[�l���g��񋓂��܂��B
     * 
     * @return �R���|�[�l���g�W��
     */
    protected ArrayList getModelBindableComponents() {
        ArrayList array = new ArrayList();
        appendModelBindableComponents(array, this);
        return array;
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {

    }
}