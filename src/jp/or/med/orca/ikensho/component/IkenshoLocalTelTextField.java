package jp.or.med.orca.ikensho.component;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.text.VRCharType;

/** TODO <HEAD_IKENSYO> */
public class IkenshoLocalTelTextField extends JPanel implements VRBindable {
    private ACTextField local = new ACTextField();
    private ACTextField subscriber = new ACTextField();
    private JLabel sep = new JLabel();

    /**
     * �R���X�g���N�^
     */
    public IkenshoLocalTelTextField() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ������
     * @throws Exception
     */
    private void jbInit() throws Exception {
        FlowLayout flowLayout1 = new FlowLayout();
        flowLayout1.setVgap(0);
        flowLayout1.setHgap(0);
        this.setLayout(flowLayout1);
        this.setOpaque(false);
        this.add(local);
        this.add(sep);
        this.add(subscriber);

        local.setText("");
        local.setColumns(4);
        local.setMaxLength(4);
        local.setIMEMode(InputSubset.LATIN_DIGITS);
        local.setCharType(VRCharType.ONLY_DIGIT);
        sep.setText("-");
        subscriber.setText("");
        subscriber.setColumns(4);
        subscriber.setMaxLength(4);
        subscriber.setIMEMode(InputSubset.LATIN_DIGITS);
        subscriber.setCharType(VRCharType.ONLY_DIGIT);
    }

    /**
     * �l���N���A���܂��B
     */
    public void clear() {
        setLocal("");
        setSubscriver("");
    }

    /**
     * �s���ǔԂ�ݒ肵�܂��B
     * @param local �s���ǔ�
     */
    public void setLocal(String local) {
        this.local.setText(local);
    }

    /**
     * �����Ҕԍ���ݒ肵�܂��B
     * @param subscriver �����Ҕԍ�
     */
    public void setSubscriver(String subscriver) {
        this.subscriber.setText(subscriver);
    }

    /**
     * �d�b�ԍ���ݒ肵�܂��B
     * @param number �d�b�ԍ�
     */
    public void setNumber(String number)  {
        try {
            //�󔒓��͎��̓N���A����
            if (number.equals("")) {
                clear();
                return;
            }

            //�������Ċi�[����
            String[] tel = number.split(sep.getText());

            if (tel.length == 2) {
                setLocal(tel[0]);
                setSubscriver(tel[1]);
            }
            else if(tel.length == 1) {
                //XXX- �� -YYYY �Ή�
                clear(); //�N���A
                int p = number.lastIndexOf(sep.getText()); //�Ō�̋�؂蕶���ʒu���擾
                if (p == 0) {
                    setSubscriver(tel[0]);
                }
                else if( p == number.length() - 1) {
                    setLocal(tel[0]);
                }
            }
            else {
                clear();
            }
        }
        catch (Exception ex) {
          ACCommon.getInstance().showExceptionMessage(ex);
        }
    }

    /**
     * ���͗���enabled��ݒ肵�܂��B
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        local.setEnabled(enabled);
        subscriber.setEnabled(enabled);
        sep.setEnabled(enabled);
    }

    /**
     * ���͗���editable��ݒ肵�܂��B
     * @param editable boolean
     */
    public void setEditable(boolean editable) {
        local.setEditable(editable);
        subscriber.setEditable(editable);
    }

    public void setChildrenFocusable(boolean focusable) {
        local.setFocusable(focusable);
        subscriber.setFocusable(focusable);
    }

    /**
     * �s���ǔԂ��擾���܂��B
     * @return �s���ǔ�
     */
    public String getLocal() {
        String local = this.local.getText();
        if (local == null) {
            local = "";
        }
        return local.trim();
    }

    /**
     * �����Ҕԍ����擾���܂��B
     * @return �����Ҕԍ�
     */
    public String getSubscriver() {
        String subscriver = this.subscriber.getText();
        if (subscriver == null) {
            subscriver = "";
        }
        return subscriver.trim();
    }

    /**
     * �d�b�ԍ����擾���܂��B
     * @return �d�b�ԍ�
     */
    public String getNumber() {
        String local = getLocal();
        String subscriver = getSubscriver();

        //�����͎��͋󔒂�Ԃ�
        if (local.equals("")) {
            if (subscriver.equals("")) {
                return "";
            }
        }

        //LLL-SSSS��Ԃ�
        return local + sep.getText() + subscriver;
    }

    /**
     * ���͗���enabled���擾���܂��B
     * @return boolean
     */
    public boolean isEnabled() {
        return local.isEnabled() && sep.isEnabled() && subscriber.isEnabled();
    }

    /**
     * ���͗���editable���擾���܂��B
     * @return boolean
     */
    public boolean isEditable() {
        return local.isEditable() && subscriber.isEditable();
    }

    public boolean isChildrenFocusable() {
        return local.isFocusable() && subscriber.isFocusable();
    }

    protected boolean autoApplySource = false;
    protected String bindPath;
    protected ArrayList listeners = new ArrayList();
    protected VRBindSource source;

    public void addBindEventListener(VRBindEventListener listener) {
        listeners.add(listener);
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
        listeners.remove(listener);
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

    protected void fireApplySource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ( (VRBindEventListener) it.next()).applySource(e);
        }
    }

    protected void fireBindSource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ( (VRBindEventListener) it.next()).bindSource(e);
        }
    }

    public Object createSource() {
        return "";
    }

    public void bindSource() throws ParseException {
        //null�`�F�b�N
        Object obj = VRBindPathParser.get(getBindPath(), source);
        if (obj == null) {
          clear();
          return;
        }

        //�l��ݒ�
        this.setNumber(String.valueOf(obj));

        //�C�x���g���s
        fireBindSource();
    }

    public void applySource() throws ParseException {
        if (VRBindPathParser.set(getBindPath(), getSource(), this.getNumber())) {
            fireApplySource();
        }
    }
}
