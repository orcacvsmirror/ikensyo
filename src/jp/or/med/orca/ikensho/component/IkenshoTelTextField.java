package jp.or.med.orca.ikensho.component;

import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.text.VRCharType;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTelTextField extends JPanel {
    private FlowLayout flowLayout1 = new FlowLayout();
    private ACTextField area = new ACTextField();
    private IkenshoLocalTelTextField number = new IkenshoLocalTelTextField();
    private JLabel sep = new JLabel();

    /**
     * �R���X�g���N�^
     */
    public IkenshoTelTextField() {
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
        flowLayout1.setVgap(0);
        flowLayout1.setHgap(0);
        this.setLayout(flowLayout1);
        this.setOpaque(false);
        this.add(area);
        this.add(sep);
        this.add(number);

        area.setText("");
        area.setColumns(5);
        area.setMaxLength(5);
        area.setIMEMode(InputSubset.LATIN_DIGITS);
        area.setCharType(VRCharType.ONLY_DIGIT);
        sep.setText("-");
    }

    /**
     * �l���N���A���܂��B
     */
    public void clear() {
        setArea("");
        number.clear();
    }

    /**
     * �s�O�ǔԂ�ݒ肵�܂��B
     * @param area String
     */
    public void setArea(String area) {
        this.area.setText(area);
    }

    /**
     * �s���ǔԂ�ݒ肵�܂��B
     * @param local �s���ǔ�
     */
    public void setLocal(String local) {
        number.setLocal(local);
    }

    /**
     * �����Ҕԍ���ݒ肵�܂��B
     * @param subscriver �����Ҕԍ�
     */
    public void setSubscriver(String subscriver) {
        number.setSubscriver(subscriver);
    }

    /**
     * �s���ǔԁA�����Ҕԍ���ݒ肵�܂��B
     * @param number (�s���ǔ�)-(�����Ҕԍ�)
     */
    public void setNumber(String number) {
        this.number.setNumber(number);
    }

    /**
     * �s���ǔԁA�����Ҕԍ���ݒ肵�܂��B
     * @param local �s���ǔ�
     * @param subscriver �����Ҕԍ�
     */
    public void setNumber(String local, String subscriver) {
        setLocal(local);
        setSubscriver(subscriver);
    }

    /**
     * �d�b�ԍ���ݒ肵�܂��B
     * @param telNo �d�b�ԍ�
     */
    public void setTelNo(String telNo) {
        try {
            //�󔒓��͎��̓N���A����
            if (telNo.equals("")) {
                clear();
                return;
            }

            //�������Ċi�[����
            String[] tel = telNo.split(sep.getText());

            if (tel.length == 3) {
                setArea(tel[0]);
                setLocal(tel[1]);
                setSubscriver(tel[2]);
            }
            else if (tel.length == 2) {
                setArea("");
                setLocal(tel[0]);
                setSubscriver(tel[1]);
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
        area.setEnabled(enabled);
        number.setEnabled(enabled);
        sep.setEnabled(enabled);
    }

    /**
     * ���͗���editable��ݒ肵�܂��B
     * @param editable boolean
     */
    public void setEditable(boolean editable) {
        area.setEditable(editable);
        number.setEditable(editable);
    }

    public void setChildrenFocusable(boolean focusable) {
        area.setFocusable(focusable);
        number.setChildrenFocusable(focusable);
    }

    /**
     * �s�O�ǔԂ��擾���܂��B
     * @return �s�O�ǔ�
     */
    public String getArea() {
        String area = this.area.getText();
        if (area == null) {
            area = "";
        }
        return area.trim();
    }

    /**
     * �s���ǔԂ��擾���܂��B
     * @return String
     */
    public String getLocal() {
        return number.getLocal();
    }

    /**
     * �����Ҕԍ����擾���܂��B
     * @return String
     */
    public String getSubscriver() {
        return number.getSubscriver();
    }

    /**
     * �s���ǔԁA�����Ҕԍ����擾���܂��B
     * @return (�s���ǔ�)-(�����Ҕԍ�)
     */
    public String getNumber() {
        return number.getNumber();
    }

    /**
     * �d�b�ԍ����擾���܂��B
     * @return �d�b�ԍ�
     */
    public String getTelNo() {
        String area = getArea();
        String num = getNumber();

        //�����͎��͋󔒂�Ԃ�
        if (area.equals("")) {
            if (num.equals("")) {
                return "";
            }
        }
        else {
            area += sep.getText();
        }

        //AAA-LLL-SSSS��Ԃ�
        return area + num;
    }

    /**
     * ���͗���enabled���擾���܂��B
     * @return boolean
     */
    public boolean isEnabled() {
        return area.isEnabled() && sep.isEnabled() && number.isEnabled();
    }

    /**
     * ���͗���editable���擾���܂��B
     * @return boolean
     */
    public boolean isEditable() {
        return area.isEditable() && number.isEditable();
    }

    public boolean isChildrenFocusable() {
        return area.isFocusable() && number.isChildrenFocusable();
    }

    public String bindPathArea;
    public String bindPathNumber;

    public void setAutoApplySource(
        boolean autoApplySourceArea, boolean autoApplySourceNumber) {
            area.setAutoApplySource(autoApplySourceArea);
            number.setAutoApplySource(autoApplySourceNumber);
    }

    public void setBindPath(String bindPathArea, String bindPathNumber) {
        area.setBindPath(bindPathArea);
        number.setBindPath(bindPathNumber);
    }

    public void setBindPathArea(String bindPathArea) {
        area.setBindPath(bindPathArea);
    }

    public void setBindPathNumber(String bindPathNumber) {
        number.setBindPath(bindPathNumber);
    }

    public String getBindPathArea() {
        return area.getBindPath();
    }

    public String getBindPathNumber() {
        return number.getBindPath();
    }

    public Object createSource() {
        area.createSource();
        number.createSource();
        return "";
    }

    public void bindSource() throws ParseException {
        area.bindSource();
        number.bindSource();
    }

    public void applySource() throws ParseException {
        area.applySource();
        number.applySource();
    }

    public boolean isFocusable(){
     return super.isFocusable()||area.isFocusable()||number.isChildrenFocusable();
    }
    public void setFocusable(boolean focusable){
      super.setFocusable(focusable);
      area.setFocusable(focusable);
      number.setChildrenFocusable(focusable);
    }

}
